package com.sap.jnc.marketing.dto.response.region;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.sap.jnc.marketing.persistence.enumeration.RegionLevel;
import com.sap.jnc.marketing.persistence.model.RegionNode;

/**
 * @author I300934 Ray Lv
 */
public class RegionNodeResponse {

	private Long id;

	private String code;

	private String name;

	private RegionLevel level;

	private Boolean isValid;

	// private RegionNodeResponse parent;

	private List<RegionNodeResponse> children;

	public RegionNodeResponse(RegionNode tree) {
		if (tree == null) {
			return;
		}

		if (tree.getId() != null) {
			this.setId(tree.getId());
		}
		if (StringUtils.isNotBlank(tree.getCode())) {
			this.setCode(tree.getCode());
		}
		if (tree.getIsValid() != null) {
			this.setIsValid(tree.getIsValid());
		}
		if (tree.getLevel() != null) {
			this.setLevel(tree.getLevel());
		}

		if (StringUtils.isNotBlank(tree.getName())) {
			this.setName(tree.getName());
		}
		// if (tree.getParent() != null) {
		// this.setParent(new RegionTreeResponse(tree.getParent()));
		// }
		if (tree.getChildren() != null && tree.getChildren().size() > 0) {
			List<RegionNodeResponse> childrenResponses = new ArrayList<RegionNodeResponse>();
			for (RegionNode child : tree.getChildren()) {
				RegionNodeResponse childrenResponse = new RegionNodeResponse(child);
				// childrenResponse.setParent(this);
				childrenResponses.add(childrenResponse);
			}
			this.setChildren(childrenResponses);
		}
	}

	public Boolean hasChildRegion() {
		return this.getChildren() != null && !this.children.isEmpty();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RegionLevel getLevel() {
		return level;
	}

	public void setLevel(RegionLevel level) {
		this.level = level;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public List<RegionNodeResponse> getChildren() {
		return children;
	}

	public void setChildren(List<RegionNodeResponse> children) {
		this.children = children;
	}
}
