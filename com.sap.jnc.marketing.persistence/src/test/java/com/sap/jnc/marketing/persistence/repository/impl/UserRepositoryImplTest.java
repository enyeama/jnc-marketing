package com.sap.jnc.marketing.persistence.repository.impl;

import com.sap.jnc.marketing.persistence.PersistenceH2DatabaseTest;
import com.sap.jnc.marketing.persistence.model.LoginAccount;
import com.sap.jnc.marketing.persistence.model.User;
import com.sap.jnc.marketing.persistence.repository.UserRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Alex
 */
public class UserRepositoryImplTest extends PersistenceH2DatabaseTest {
	@Autowired
	private UserRepository userRepository;

	private String userName = "Alex";

	@Before
	public void setUp() throws Exception {
		User user = new User();
		LoginAccount loginAccount = new LoginAccount();
		loginAccount.setUserName(userName);
		loginAccount.setPassword("test1234");
		user.setLoginAccount(loginAccount);
		userRepository.save(user);
	}

	@Test
	public void testFindByUserName() throws Exception {
		final User user = this.userRepository.findOneByLoginKeyword(this.userName);
		Assert.assertNotNull(user);
		Assert.assertEquals(user.getLoginAccount().getUserName(), this.userName);
	}
}