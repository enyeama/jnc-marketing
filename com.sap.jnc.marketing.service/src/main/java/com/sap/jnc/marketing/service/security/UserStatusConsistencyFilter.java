package com.sap.jnc.marketing.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alex
 */
public class UserStatusConsistencyFilter extends OncePerRequestFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserStatusConsistencyFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws ServletException, IOException {
		/*
		 * IdmUser idmUser = (IdmUser) SecurityContextHolderUtil.getUserDetails(); if (idmUser != null) { User user = idmUser.getUser(); User fresh =
		 * null; if (user != null) { UserService userService = SpringContextHolderUtil.getApplicationContext().getBean(UserService.class); fresh =
		 * userService.findOne(user.getId()); } if (user == null || fresh.getAuthStatus() != user.getAuthStatus() || fresh.getStatus() !=
		 * user.getStatus()) { LOGGER.warn("Inconsistent user status detected: {}, removing principal", user);
		 * SecurityContextHolder.getContext().setAuthentication(null); } }
		 */
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
}
