package com.sap.jnc.marketing.persistence.data.factories.transaction;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.sap.jnc.marketing.persistence.data.factories.EntityFactory;
import com.sap.jnc.marketing.persistence.data.factories.master.RoleAndPrivilegeEntityFactory;
import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.Role;
import com.sap.jnc.marketing.persistence.model.ValidityPeriod;
import com.sap.jnc.marketing.persistence.repository.EmployeeRepository;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EmployeeEntityFactory extends GeneralTransactionalDataEntityFactory<Employee> implements TransactionalDataEntityFactory<Employee> {

	private static final Calendar VALID_FROM = Calendar.getInstance();
	{
		VALID_FROM.add(Calendar.DATE, -30);
	}
	private static final Calendar VALID_TO = Calendar.getInstance();
	{
		VALID_TO.set(Calendar.YEAR, 9999);
	}

	@Autowired
	protected RoleAndPrivilegeEntityFactory roleAndPrivilegeEntityFactory;

	@Autowired
	protected EmployeeRepository employeeRepository;

	@Override
	protected void fillEntitiesForInitialCreation(List<Employee> allCreatedEntities) {
		final List<Role> roles = this.roleAndPrivilegeEntityFactory.createdEntities();
		for (int i = 0; i < 1000; i++) {
			allCreatedEntities.add(this.createEmployeeEntity(roles.get(RandomUtils.nextInt(0, roles.size()))));
		}
	}

	protected Employee createEmployeeEntity(Role role) {
		final Employee employee = new Employee();
		// Scalar Fields - Employee
		employee.setName(RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(0, 10) + 5));
		employee.setExternalId(String.valueOf(RandomUtils.nextInt(100000, 999999)));
		employee.setIdCardNO(String.valueOf(RandomUtils.nextLong(100000000000000000L, 599999999999999999L)));
		employee.setLoginAccountRole(role.getName());
		employee.setPhone(String.valueOf(RandomUtils.nextLong(13000000000L, 18999999999L)));
		employee.setValidityPeriod(new ValidityPeriod() {
			private static final long serialVersionUID = 3233605430727658485L;
			{
				this.setValidFrom(VALID_FROM);
				this.setValidTo(VALID_TO);
			}
		});
		// Return
		return employee;
	}

	@Override
	protected JpaRepository<Employee, Long> getGeneralJpaRepository() {
		return this.employeeRepository;
	}

	@Override
	protected EntityFactory<?>[] getDependEntityFactories() {
		return new EntityFactory<?>[] { this.roleAndPrivilegeEntityFactory };
	}
}
