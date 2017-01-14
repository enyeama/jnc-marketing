package com.sap.jnc.marketing.service.security;

import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

public class UserHttpSessionSecurityContextRepository extends HttpSessionSecurityContextRepository {

	/*
	 * private UserMapper userMapper; public void setUserMapper(UserMapper userMapper) { this.userMapper = userMapper; }
	 * @Override public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) { SecurityContext context =
	 * super.loadContext(requestResponseHolder); checkContext(requestResponseHolder, context); return context; } private void
	 * checkContext(HttpRequestResponseHolder requestResponseHolder, SecurityContext context) { HttpServletRequest request =
	 * requestResponseHolder.getRequest(); if (!request.getMethod().equalsIgnoreCase("GET")) { Authentication authentication =
	 * context.getAuthentication(); if (authentication != null) { Object principal = authentication.getPrincipal(); long userId = ((IdmUser)
	 * principal).getId(); User user = userMapper.selectByPrimaryKey(userId); if (user == null) { context.setAuthentication(null); } } } }
	 */

}
