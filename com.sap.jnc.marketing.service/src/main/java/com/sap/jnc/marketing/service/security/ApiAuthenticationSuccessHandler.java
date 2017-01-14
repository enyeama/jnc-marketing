package com.sap.jnc.marketing.service.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component("apiAuthenticationSuccessHandler")
public class ApiAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiAuthenticationSuccessHandler.class);

	private final RequestCache requestCache = new HttpSessionRequestCache();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException,
	ServletException {
		LOGGER.info(String.format("User <%s> login", request.getRemoteUser()));
		final SavedRequest savedRequest = this.requestCache.getRequest(request, response);
		if (savedRequest == null) {
			this.clearAuthenticationAttributes(request);
			return;
		}
		final String targetUrlParameter = this.getTargetUrlParameter();
		if (this.isAlwaysUseDefaultTargetUrl() || ((targetUrlParameter != null) && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
			this.requestCache.removeRequest(request, response);
			this.clearAuthenticationAttributes(request);
			return;
		}

		this.clearAuthenticationAttributes(request);
	}
}