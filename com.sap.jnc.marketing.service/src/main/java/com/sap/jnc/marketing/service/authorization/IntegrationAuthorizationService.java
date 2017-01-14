package com.sap.jnc.marketing.service.authorization;

public interface IntegrationAuthorizationService {

	/**
	 * 大写开头的参数？
	 * 
	 * @param authorizationCode
	 * @return
	 */
	boolean authorize(String authorizationCode);

}
