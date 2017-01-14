package com.sap.jnc.marketing.api.admin.web.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.jnc.marketing.api.admin.web.security.handler.DefaultExceptionHandler;
import com.sap.jnc.marketing.api.admin.web.security.handler.ExceptionContainer;
import com.sap.jnc.marketing.api.admin.web.security.handler.ExceptionHandler;

@Component
public class GlobalExceptionHandlerResolver extends DefaultHandlerExceptionResolver {

	private static final String DEFAULT_CONTENT_TYPE = "application/json";

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandlerResolver.class);

	@Autowired
	protected DefaultExceptionHandler defaultExceptionHandler;

	@Autowired
	protected List<ExceptionHandler<?>> exceptionHandlers;

	@Autowired
	protected ObjectMapper mapper;

	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		response.setHeader(HttpHeaders.CONTENT_TYPE, DEFAULT_CONTENT_TYPE);
		response.setStatus(this.getStatusCode(ex));

		final StringWriter exMsgWriter =  new StringWriter(512);
		ex.printStackTrace(new PrintWriter(exMsgWriter));
		LOGGER.error(exMsgWriter.toString());
		try {
			this.mapper.writeValue(response.getWriter(), new ExceptionContainer(this.getExceptionHandler(ex).getBody(ex)));
		}
		catch (final IOException ex0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		return new ModelAndView();
	}

	protected int getStatusCode(Exception ex) {
		return this.getExceptionHandler(ex).getStatusCode();
	}

	@SuppressWarnings("rawtypes")
	protected ExceptionHandler getExceptionHandler(Exception ex) {
		// Check for exception hierarchy type match
		Class<?> exceptionType = ex.getClass();
		while (exceptionType != null) {
			for (final ExceptionHandler<?> exceptionHandler : this.exceptionHandlers) {
				if (exceptionType.equals(exceptionHandler.getExceptionType())) {
					return exceptionHandler;
				}
			}
			exceptionType = exceptionType.getSuperclass();
		}
		// Check for expection hierarchy for exception itself
		for (final ExceptionHandler<?> exceptionHandler : this.exceptionHandlers) {
			if (exceptionHandler.getExceptionType().isInstance(ex)) {
				return exceptionHandler;
			}
		}
		// Check for one-level exception interfaces type match
		for (final ExceptionHandler<?> exceptionHandler : this.exceptionHandlers) {
			if (Arrays.asList(ex.getClass().getInterfaces()).contains(exceptionHandler.getExceptionType())) {
				return exceptionHandler;
			}
		}
		return this.defaultExceptionHandler;
	}

	/**
	 * Setter for field {@link #exceptionHandlers}, which is expected to be alphabetically sorted in tests.
	 *
	 * @param exceptionHandlers
	 *            Spring's autowired exception handlers
	 */
	@Autowired
	public void setExceptionHandler(List<ExceptionHandler<?>> exceptionHandlers) {
		final List<ExceptionHandler<?>> handlers = new ArrayList<>(exceptionHandlers);
		Collections.sort(handlers, (o1, o2) -> o1.getClass().getName().compareTo(o2.getClass().getName()));
		exceptionHandlers = handlers;
	}
}
