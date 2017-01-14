package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.enumeration.PrivilegeType;

@Entity
@Table(name = "T_PRIVILEGE", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "PrivilegeSeq", sequenceName = "SEQ_PRIVILEGE")
public class Privilege implements Serializable {

	private static final long serialVersionUID = -6476576158733707385L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PrivilegeSeq")
	private Long id;

	@Column(name = "name")
	protected String name;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	protected PrivilegeType type;

	@Column(name = "description")
	protected String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId")
	private Privilege parent;

	@OneToMany(mappedBy = "parent")
	private List<Privilege> children;

	@ManyToMany
	@JoinTable(name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".M_" + "ROLE_PRIVILEGE",
	// Self
	joinColumns = @JoinColumn(name = "privilegeId"),
	// Inverse
	inverseJoinColumns = @JoinColumn(name = "roleId"))
	protected List<Role> roles;

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object another) {
		return EqualsBuilder.reflectionEquals(this, another);
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

	public PrivilegeType getType() {
		return this.type;
	}

	public void setType(PrivilegeType type) {
		this.type = type;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Privilege getParent() {
		return this.parent;
	}

	public void setParent(Privilege parent) {
		this.parent = parent;
	}

	public List<Privilege> getChildren() {
		return this.children;
	}

	public void setChildren(List<Privilege> children) {
		this.children = children;
	}
}