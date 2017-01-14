package com.sap.jnc.marketing.service.security;

import com.sap.jnc.marketing.persistence.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface IdmUser extends UserDetails {

	UserId getId();

	/*
	 * 注册时间
	 */
	Date getRegisterDate();

	/*
	 * 上次登录时间
	 */
	Date getLastLoginDate();

	boolean hasRole(String role);

	User getUser();
}
