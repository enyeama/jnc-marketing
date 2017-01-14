package com.sap.jnc.marketing.api.admin.web.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class ApiOriginHandlerInterceptor extends HandlerInterceptorAdapter {

	private static final String DEFAULT_CHARACTER_ENCODING = "UTF-8";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
		response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		response.setCharacterEncoding(DEFAULT_CHARACTER_ENCODING);
		return super.preHandle(request, response, handler);
	}
}