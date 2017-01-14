package com.sap.jnc.marketing.dto.response.authentication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.sap.jnc.marketing.persistence.enumeration.PrivilegeType;
import com.sap.jnc.marketing.persistence.model.User;

/**
 * @author I071053 Diouf Du
 */
public class RoleAndPrivilegeResponse extends ArrayList<RoleDetailResponse> implements Serializable {

	private static final long serialVersionUID = 1328348876160160000L;

	public RoleAndPrivilegeResponse(User user) {
		this(user, null);
	}

	public RoleAndPrivilegeResponse(User user, PrivilegeType privilegeType) {
		super(user.getRoles().stream()
			// Parent privilege only
			// Create role dtos
			.map(role -> new RoleDetailResponse(role, privilegeType))
			// Remove role with empty privileges
			.filter(r -> CollectionUtils.isNotEmpty(r.getPrivileges()))
			// Create sub-roles
			.collect(Collectors.toList()));
	}

	public RoleAndPrivilegeResponse(List<RoleDetailResponse> roles) {
		super(roles);
	}
}
