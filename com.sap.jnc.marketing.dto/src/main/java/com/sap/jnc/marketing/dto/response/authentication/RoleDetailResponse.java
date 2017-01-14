package com.sap.jnc.marketing.dto.response.authentication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.sap.jnc.marketing.persistence.enumeration.PrivilegeType;
import com.sap.jnc.marketing.persistence.model.Role;

/**
 * @author I071053 Diouf Du
 */
public class RoleDetailResponse implements Serializable {

	private static final long serialVersionUID = 1328348876160160000L;

	private static final List<PrivilegeInfoResponse> EMPTY_PRIVILEGES = new ArrayList<>(0);

	private Long id;

	protected String name;

	protected String description;

	private List<PrivilegeInfoResponse> privileges;

	public RoleDetailResponse() {
	}

	public RoleDetailResponse(Role role) {
		this(role, null);
	}

	public RoleDetailResponse(Role role, PrivilegeType privilegeType) {
		if (role == null) {
			return;
		}
		this.setId(role.getId());
		this.setName(role.getName());
		this.setDescription(role.getDescription());
		this.setPrivileges(CollectionUtils.isEmpty(role.getPrivileges()) ? EMPTY_PRIVILEGES :
			// Transforming privileges
			role.getPrivileges().stream()
			// Parent privilege only
			.filter(p -> (Objects.equals(p.getType(), PrivilegeType.MODULE)))
			// Create sub-privilege
			.map(privilege -> new PrivilegeInfoResponse(privilege, privilegeType))
			// Remove role with empty privileges
			.filter(p -> CollectionUtils.isNotEmpty(p.getChildren()))
			// Collect
			.collect(Collectors.toList()));
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<PrivilegeInfoResponse> getPrivileges() {
		return this.privileges;
	}

	public void setPrivileges(List<PrivilegeInfoResponse> privileges) {
		this.privileges = privileges;
	}
}
