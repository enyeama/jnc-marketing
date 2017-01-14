package com.sap.jnc.marketing.api.admin.web.security;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sap.jnc.marketing.persistence.config.auditor.ModificatoryAuditorUser;
import com.sap.jnc.marketing.persistence.model.Role;
import com.sap.jnc.marketing.persistence.model.User;
import com.sap.jnc.marketing.service.authentication.AuthenticationService;

@Service
public class AdminUserDetailsService implements UserDetailsService {

	@Autowired
	private AuthenticationService authenticationService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final User user = this.authenticationService.findOneByLoginKeyword(username);
		if ((user == null) || (user.getLoginAccount() == null)) {
			throw new UsernameNotFoundException("Can't find specific user by name");
		}
		final List<Role> roles = user.getRoles();
		if (CollectionUtils.isEmpty(roles)) {
			throw new UsernameNotFoundException("Specific user doesn't have any privileges");
		}
		// Return
		return new ModificatoryAuditorUser(user);
	}
}
