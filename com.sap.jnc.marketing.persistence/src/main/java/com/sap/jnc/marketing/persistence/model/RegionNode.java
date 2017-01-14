package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.enumeration.RegionLevel;

/**
 * @author I300934 Ray Lv
 */
@Entity
@Table(name = "V_REGION_NODE", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
public class RegionNode implements Serializable {

	private static final long serialVersionUID = -2194507618256589656L;

	@Id
	@Column(name = "Id")
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "level")
	@Enumerated(EnumType.STRING)
	private RegionLevel level;

	@Column(name = "isValid")
	private Boolean isValid;
	
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn(name = "parent_id")
	private RegionNode parent;
	
	@OneToMany(mappedBy = "parent")
	private List<RegionNode> children;

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

	public RegionNode getParent() {
		return parent;
	}

	public void setParent(RegionNode parent) {
		this.parent = parent;
	}

	public List<RegionNode> getChildren() {
		return children;
	}

	public void setChildren(List<RegionNode> children) {
		this.children = children;
	}
}
