package com.sap.jnc.marketing.persistence.data.factories.master;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.sap.jnc.marketing.infrastructure.shared.utils.wechat.MD5;
import com.sap.jnc.marketing.persistence.data.factories.EntityFactory;
import com.sap.jnc.marketing.persistence.model.LoginAccount;
import com.sap.jnc.marketing.persistence.model.Role;
import com.sap.jnc.marketing.persistence.model.User;
import com.sap.jnc.marketing.persistence.repository.RoleRepository;
import com.sap.jnc.marketing.persistence.repository.UserRepository;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserEntityFactory extends GeneralMasterDataEntityFactory<User> {

	@Autowired
	private RoleAndPrivilegeEntityFactory rolePrivilegeEntityFactory;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected RoleRepository roleRepository;

	@Override
	protected void fillEntitiesForInitialCreation(List<User> creatingEntities) {
		for (final Role role : this.rolePrivilegeEntityFactory.createdEntities()) {
			final User user = new User();
			user.setName("测试" + role.getName() + "01");
			user.setLoginAccount(new LoginAccount());
			user.getLoginAccount().setUserName(user.getName());
			user.getLoginAccount().setPassword(MD5.MD5Encode("123456"));
			user.setRoles(new ArrayList<>(1));
			if (role.getUsers() == null) {
				role.setUsers(new ArrayList<>(1));
			}
			role.getUsers().add(user);
			this.userRepository.save(user);
			this.roleRepository.save(role);
			creatingEntities.add(user);
		}
		this.roleRepository.flush();
	}

	@Override
	protected JpaRepository<User, Long> getGeneralJpaRepository() {
		return this.userRepository;
	}

	@Override
	protected EntityFactory<?>[] getDependEntityFactories() {
		return new EntityFactory<?>[] { this.rolePrivilegeEntityFactory };
	}
}
