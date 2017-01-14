package com.sap.jnc.marketing.service.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

import com.sap.jnc.marketing.service.util.JsonResponseUtils;
import com.sap.jnc.marketing.service.util.RequestUtils;

@Component("apiAuthenticationEntryPoint")
public class ApiAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException,
		ServletException {
		if (!RequestUtils.isAjax(request)) {
			final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
			final String queryString = request.getQueryString();
			String servletPath = request.getServletPath();
			servletPath = servletPath.endsWith("/") ? servletPath : servletPath + "/";
			String redirectUrl = servletPath + "oauth_login";
			if (StringUtils.isNotBlank(queryString)) {
				redirectUrl += "?" + queryString;
				LOGGER.debug("haven't authencate, redirect to: {}", redirectUrl);
			}
			redirectStrategy.sendRedirect(request, response, redirectUrl);
			return;
		}
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		JsonResponseUtils.writeJson(response, authException);
	}
}
