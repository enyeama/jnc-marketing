package com.sap.jnc.marketing.service.security;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.sap.jnc.marketing.infrastructure.shared.constant.MessageId;
import com.sap.jnc.marketing.service.util.JsonResponseUtils;

@Component("apiAuthenticationFailureHandler")
public class ApiAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiAuthenticationFailureHandler.class);

	@Autowired
	private MessageSource messageSource;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
		throws IOException, ServletException {
		LOGGER.info("unauthencated request found");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		JsonResponseUtils.writeJson(response, this.messageSource.getMessage(MessageId.E_EX_MM_1001, new Object[] {}, Locale.getDefault()));
	}

}