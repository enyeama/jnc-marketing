package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

@Entity
@Table(name = "T_ROLE", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "RoleSeq", sequenceName = "SEQ_ROLE")
public class Role implements Serializable {

	private static final long serialVersionUID = 6966000252535130644L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RoleSeq")
	private Long id;

	@Column(name = "name")
	protected String name;

	@Column(name = "description")
	protected String description;

	@ManyToMany
	@JoinTable(name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".M_" + "USER_ROLE",
		// Self
		joinColumns = @JoinColumn(name = "roleName"),
		// Inverse
		inverseJoinColumns = @JoinColumn(name = "userId"))
	protected List<User> users;

	@ManyToMany(mappedBy = "roles")
	protected List<Privilege> privileges;

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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Privilege> getPrivileges() {
		return this.privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
