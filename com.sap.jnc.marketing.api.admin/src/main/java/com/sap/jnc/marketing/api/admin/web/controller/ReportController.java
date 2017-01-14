package com.sap.jnc.marketing.api.admin.web.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.thoughtworks.xstream.core.util.Base64Encoder;

import me.chanjar.weixin.common.util.StringUtils;

/**
 * @author I071053 Diouf Du
 */
@RestController
public class ReportController extends GeneralController {

	private static final String XS_JS_SERVICE_URL = "http://120.76.31.239:8000/cdp/mmp/xsjs/src/download/getFile.xsjs";

	private static final String USER_NAME = "JDBCUSER2";

	private static final String PASSWORD = "Welcome2";

	private static final Base64Encoder base64Encoder = new Base64Encoder();

	private static final String AUTHORIZATION_VALUE = "Basic " + base64Encoder.encode((USER_NAME + ":" + PASSWORD).getBytes());

	private final RestTemplate restTemplate = new RestTemplate();

	@RequestMapping(value = "/xsjs/report", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getReportFromXsJsService() {
		final HttpHeaders requestHeaders = new HttpHeaders();
		// Add the authorization header
		requestHeaders.set(HttpHeaders.AUTHORIZATION, AUTHORIZATION_VALUE);
		final HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		// Add the Object message converter
		this.restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
		// Make the HTTP GET request, marshaling the response to a Object
		final String queryString = super.getHttpServletRequest().getQueryString();
		final ResponseEntity<byte[]> response = this.restTemplate.exchange(
			// URL
			StringUtils.isBlank(queryString) ? XS_JS_SERVICE_URL : (XS_JS_SERVICE_URL + "?" + queryString),
			// Method & Request & Response Type
			HttpMethod.GET, requestEntity, byte[].class);
		return response;
	}
}
