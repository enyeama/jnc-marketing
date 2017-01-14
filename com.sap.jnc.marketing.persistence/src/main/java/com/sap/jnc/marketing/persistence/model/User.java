package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

/**
 * @author Diouf Du
 */
@Entity
@Table(name = "T_USER", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "UserSeq", sequenceName = "SEQ_USER")
public class User extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -3780267302524852783L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserSeq")
	private Long id;

	private LoginAccount loginAccount;

	private WechatAccount wechatAccount;

	@Column(name = "name")
	private String name;

	@ManyToMany(mappedBy = "users")
	protected List<Role> roles;

	@ManyToMany(mappedBy = "users")
	protected List<UserReference> userReferences;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LoginAccount getLoginAccount() {
		return this.loginAccount;
	}

	public void setLoginAccount(LoginAccount loginAccount) {
		this.loginAccount = loginAccount;
	}

	public WechatAccount getWechatAccount() {
		return this.wechatAccount;
	}

	public void setWechatAccount(WechatAccount wechatAccount) {
		this.wechatAccount = wechatAccount;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserReference> getUserReferences() {
		return this.userReferences;
	}

	public void setUserReferences(List<UserReference> userReferences) {
		this.userReferences = userReferences;
	}
}
