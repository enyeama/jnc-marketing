package com.sap.jnc.marketing.persistence.config.auditor;

import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.sap.jnc.marketing.persistence.model.User;

/**
 * Store more detailed information for DB Model User, which provide additional fields within session
 *
 * @author I071053 Diouf Du
 */
public class ModificatoryAuditorUser extends org.springframework.security.core.userdetails.User {

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = 9149197102423819305L;

	private final User user;

	/**
	 * Construct the <code>User</code> with the details required by {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}.
	 *
	 * @param user
	 *            the user presented to the DB model
	 * @param authorities
	 *            the authorities that should be granted to the caller if they presented the correct username and password and the user is enabled.
	 *            Not null.
	 */
	public ModificatoryAuditorUser(User user) {
		super(
			// User name
			user.getLoginAccount().getUserName(),
			// Password
			user.getLoginAccount().getPassword(),
			// Authorities
			ListUtils.union(
				// Roles
				user.getRoles().stream().map(role ->
				// Generate authorities
				new SimpleGrantedAuthority("ROLE_" + role.getName())).collect(Collectors.toList()),
				// Privileges
				user.getRoles().stream().map(r -> r.getPrivileges()).reduce((l, r) -> {
					return ListUtils.union(l, r);
				}).get().stream().map(privilege ->
				// Generate authorities
				new SimpleGrantedAuthority("PRIVILEGE_" + privilege.getName())).collect(Collectors.toList())));
		this.user = user;
	}

	public Long getUserId() {
		return this.user.getId();
	}

	public User getUser() {
		return this.user;
	}
}
