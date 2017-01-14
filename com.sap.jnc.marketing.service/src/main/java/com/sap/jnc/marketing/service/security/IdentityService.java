package com.sap.jnc.marketing.service.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sap.jnc.marketing.persistence.config.auditor.ModificatoryAuditorUser;

/**
 * Created by divestar on 11/11/15.
 */
public interface IdentityService extends UserDetailsService {

	void setUserAuthenticationInfo(HttpServletRequest request, IdmUser user);

	IdmUser loadUserByOpenId(String openId);

	ModificatoryAuditorUser loadModificatoryAuditorUserByOpenId(String openId);
}
