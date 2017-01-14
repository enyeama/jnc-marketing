package com.sap.jnc.marketing.service.security;

import com.sap.jnc.marketing.infrastructure.shared.SystemConfig;
import com.sap.jnc.marketing.infrastructure.shared.constant.WebAttributes;
import com.sap.jnc.marketing.infrastructure.shared.enumeration.DeployMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Alex
 */
public class OpenIDFilter extends OncePerRequestFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenIDFilter.class);
	@Value("${app.base_url}")
	private String baseUrl;
	@Autowired
	private SystemConfig systemConfig;

	public OpenIDFilter() {
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,
		IOException {
		HttpSession session = request.getSession();
		String openId = (String) session.getAttribute(WebAttributes.WECHAT_OPEN_ID);
		if (systemConfig.getDeployMode() != DeployMode.LOCAL && openId == null) {
			/*
			 * String requestURL = request.getScheme() + "://" + // "http" + ":// request.getServerName() + // "hostname" ":" + // ":"
			 * request.getServerPort() + // "port" request.getRequestURI() + // "/XXX" "?" + // "?" request.getQueryString();
			 */
			String requestURL = baseUrl + request.getRequestURI() + // "/XXX"
				"?" + // "?"
				request.getQueryString();
			if (requestURL.contains("/wechat_oauth_callback")) {
				filterChain.doFilter(request, response);
			}
			else {
				LOGGER.debug("requestURI {} has no openId associated", requestURL);
				session.setAttribute(WebAttributes.REQUEST_URL_WITH_NO_OPEN_ID, requestURL);
				RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
				redirectStrategy.sendRedirect(request, response, "/get_openid");
			}
		}
		else {
			filterChain.doFilter(request, response);
		}
	}

}
