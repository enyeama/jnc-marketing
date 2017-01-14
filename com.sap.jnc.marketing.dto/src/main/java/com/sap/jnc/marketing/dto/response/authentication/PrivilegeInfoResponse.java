package com.sap.jnc.marketing.dto.response.authentication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.sap.jnc.marketing.persistence.enumeration.PrivilegeType;
import com.sap.jnc.marketing.persistence.model.Privilege;

/**
 * @author I071053 Diouf Du
 */
public class PrivilegeInfoResponse implements Serializable {

	private static final long serialVersionUID = 7839620450641949496L;

	private static final List<PrivilegeInfoResponse> EMPTY_PRIVILEGES = new ArrayList<>(0);

	private Long id;

	protected String name;

	protected String description;

	protected PrivilegeType privilegeType;

	private List<PrivilegeInfoResponse> children;

	public PrivilegeInfoResponse() {
	}

	public PrivilegeInfoResponse(Privilege privilege) {
		this(privilege, null);
	}

	public PrivilegeInfoResponse(Privilege privilege, PrivilegeType privilegeType) {
		if (privilege == null) {
			return;
		}
		this.setId(privilege.getId());
		this.setName(privilege.getName());
		this.setDescription(privilege.getDescription());
		this.setPrivilegeType(privilege.getType());
		this.setChildren(CollectionUtils.isEmpty(privilege.getChildren()) ? EMPTY_PRIVILEGES :
			// Transforming privileges
			privilege.getChildren().stream()
			// Specific privilege type only
			.filter(child -> (privilegeType == null) || (Objects.equals(child.getType(), privilegeType)))
			// Create sub-privilege
			.map(child -> new PrivilegeInfoResponse(child))
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

	public PrivilegeType getPrivilegeType() {
		return this.privilegeType;
	}

	public void setPrivilegeType(PrivilegeType privilegeType) {
		this.privilegeType = privilegeType;
	}

	public List<PrivilegeInfoResponse> getChildren() {
		return this.children;
	}

	public void setChildren(List<PrivilegeInfoResponse> children) {
		this.children = children;
	}
}
