package com.sap.jnc.marketing.persistence.config.auditor;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sap.jnc.marketing.persistence.model.User;

/**
 * Provide the auditor from Spring Security Context for Spring Data
 *
 * @author I071053 Diouf Du
 */
@Component("modificatoryAuditorAware")
public class ModificatoryAuditorAware implements AuditorAware<User> {

	@Override
	public User getCurrentAuditor() {
		final SecurityContext securityContext = SecurityContextHolder.getContext();
		if ((securityContext == null) || (securityContext.getAuthentication() == null) || (securityContext.getAuthentication()
			.getPrincipal() == null)) {
			return null;
		}
		final Object userDetail = securityContext.getAuthentication().getPrincipal();
		if (userDetail instanceof ModificatoryAuditorUser) {
			return ((ModificatoryAuditorUser) userDetail).getUser();
		}
		else {
			return null;
		}
	}
}
