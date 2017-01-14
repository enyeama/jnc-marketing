package com.sap.jnc.marketing.api.integration.web.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sap.jnc.marketing.api.integration.web.exception.AuthorizationException;
import com.sap.jnc.marketing.service.authorization.IntegrationAuthorizationService;

import me.chanjar.weixin.common.util.StringUtils;

@Component
public class JfIntegrationAuthorizationInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private IntegrationAuthorizationService integrationAuthrizationService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		JfAccessRequired annotation = method.getAnnotation(JfAccessRequired.class);
		if (annotation != null) {
			boolean returnValue = true;
			String authCode = (String) request.getHeader("Authorization");
			if (StringUtils.isNotBlank(authCode)) {
				returnValue = integrationAuthrizationService.authorize(authCode);
			}
			else {
				returnValue = false;
			}
			if (returnValue == false) {
				throw new AuthorizationException("Authorization Error");
			}
			return returnValue;
		}
		return super.preHandle(request, response, handler);
	}

}
