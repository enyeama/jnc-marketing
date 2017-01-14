package com.sap.jnc.marketing.persistence.data.factories.master;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.sap.jnc.marketing.persistence.criteria.migration.EmployeeAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.data.factories.EntityFactory;
import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.model.User;
import com.sap.jnc.marketing.persistence.model.UserReference;
import com.sap.jnc.marketing.persistence.model.UserReferenceToEmployee;
import com.sap.jnc.marketing.persistence.repository.EmployeeRepository;
import com.sap.jnc.marketing.persistence.repository.EmployeeViewRepository;
import com.sap.jnc.marketing.persistence.repository.UserReferenceRepository;
import com.sap.jnc.marketing.persistence.repository.UserRepository;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserReferenceEntityFactory extends GeneralMasterDataEntityFactory<UserReference> {

	@Autowired
	private UserEntityFactory userEntityFactory;

	@Autowired
	protected UserReferenceRepository userReferenceRepository;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected EmployeeRepository employeeRepository;

	@Autowired
	protected EmployeeViewRepository employeeViewRepository;

	@SuppressWarnings("serial")
	@Override
	protected void fillEntitiesForInitialCreation(List<UserReference> creatingEntities) {
		for (final User user : this.userEntityFactory.createdEntities()) {
			final Page<Employee> foundEmployees = this.employeeRepository.advanceSearch(new EmployeeAdvanceSearchKeywordNode() {
				{
					this.setName(StringUtils.remove(StringUtils.remove(user.getName(), "测试"), "01"));
				}
			}, new PageRequest(0, 1));
			if ((foundEmployees != null) && CollectionUtils.isEmpty(foundEmployees.getContent())) {
				continue;
			}
			final Employee employee = foundEmployees.getContent().get(0);
			final EmployeeView employeeView = this.employeeViewRepository.findOneById(employee.getId());
			if (employeeView == null) {
				continue;
			}
			final UserReferenceToEmployee userReference = new UserReferenceToEmployee();
			userReference.setEmployee(employeeView);
			userReference.setUsers(new ArrayList<User>(Arrays.asList(user)));
			creatingEntities.add(userReference);
			this.userReferenceRepository.save(userReference);
		}
		this.userReferenceRepository.flush();
	}

	@Override
	protected JpaRepository<UserReference, Long> getGeneralJpaRepository() {
		return this.userReferenceRepository;
	}

	@Override
	protected EntityFactory<?>[] getDependEntityFactories() {
		return new EntityFactory<?>[] { this.userEntityFactory };
	}
}
