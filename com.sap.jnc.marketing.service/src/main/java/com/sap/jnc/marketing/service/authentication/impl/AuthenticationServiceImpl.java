package com.sap.jnc.marketing.service.authentication.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.authentication.UserProfileChangeRequest;
import com.sap.jnc.marketing.persistence.model.User;
import com.sap.jnc.marketing.persistence.repository.UserRepository;
import com.sap.jnc.marketing.service.authentication.AuthenticationService;

/**
 * @author I071053 Diouf Du
 */
@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public User findOneByLoginKeyword(String loginKeyword) {
		return this.userRepository.findOneByLoginKeyword(loginKeyword);
	}

	@Override
	@Transactional
	public void changeProfile(UserProfileChangeRequest request) {
		if ((request == null) || StringUtils.isBlank(request.getUserName())) {
			return;
		}
		final User user = this.userRepository.findOneByLoginKeyword(request.getUserName());
		// 修改信息
		if (user.getLoginAccount() != null) {
			user.getLoginAccount().setPassword(request.getNewPassword());
		}
		this.userRepository.saveAndFlush(user);
	}
}
