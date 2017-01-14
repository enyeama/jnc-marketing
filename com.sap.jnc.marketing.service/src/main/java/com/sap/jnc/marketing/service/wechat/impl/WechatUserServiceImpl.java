package com.sap.jnc.marketing.service.wechat.impl;

import com.sap.jnc.marketing.persistence.enumeration.UserStatus;
import com.sap.jnc.marketing.persistence.model.User;
import com.sap.jnc.marketing.persistence.repository.UserRepository;
import com.sap.jnc.marketing.service.wechat.WechatUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WechatUserServiceImpl implements WechatUserService {

	@Autowired
	private UserRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<User> findByOpenId(String openId, Optional<UserStatus> active) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findByPhoneNo(String username) {
		return null;
	}

	@Override
	public List<User> findByUsername(String username) {
		return repository.findByUserName(username);
	}

}
