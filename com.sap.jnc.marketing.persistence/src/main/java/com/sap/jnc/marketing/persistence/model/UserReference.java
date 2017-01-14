package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.enumeration.ExternalUserRoleType;
import com.sap.jnc.marketing.persistence.enumeration.UserReferenceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "referenceType")
@Table(name = "T_USER_REFERENCE", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "UserReferenceSeq", sequenceName = "SEQ_USER_REFERENCE")
public class UserReference implements Serializable {

	private static final long serialVersionUID = -8374732825177518077L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserReferenceSeq")
	private Long id;

	@Column(name = "roleType")
	@Enumerated(EnumType.STRING)
	private ExternalUserRoleType roleType;

	@Column(name = "referenceType", insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private UserReferenceType referenceType;

	@Column(name = "referenceId", insertable = false, updatable = false)
	private Long referenceId;

	@Column(name = "referenceExternalId", insertable = false, updatable = false)
	private String referenceExternalId;

	@ManyToMany
	@JoinTable(
	// Join Table
	name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".M_" + "USER_REFERENCE",
	// Columns - Self
	joinColumns = @JoinColumn(name = "userReferenceId") ,
	// Columns - Inverse
	inverseJoinColumns = @JoinColumn(name = "userId") )
	private List<User> users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(UserReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public String getReferenceExternalId() {
		return referenceExternalId;
	}

	public void setReferenceExternalId(String referenceExternalId) {
		this.referenceExternalId = referenceExternalId;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public ExternalUserRoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(ExternalUserRoleType roleType) {
		this.roleType = roleType;
	}
}
