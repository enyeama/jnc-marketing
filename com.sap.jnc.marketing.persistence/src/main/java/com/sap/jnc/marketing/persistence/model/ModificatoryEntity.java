package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -5083840345440733970L;

	@Version
	@Column(name = "version", nullable = true)
	private Timestamp version;

	@CreatedDate
	@Column(name = "createOn", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createOn;

	@LastModifiedDate
	@Column(name = "updateOn", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar updateOn;

	@CreatedBy
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "createBy", nullable = true)
	private User createBy;

	@LastModifiedBy
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "updateBy", nullable = true)
	private User updateBy;

	public Timestamp getVersion() {
		return this.version;
	}

	public void setVersion(Timestamp version) {
		this.version = version;
	}

	public Calendar getCreateOn() {
		return this.createOn;
	}

	protected void setCreateOn(Calendar createOn) {
		this.createOn = createOn;
	}

	public Calendar getUpdateOn() {
		return this.updateOn;
	}

	protected void setUpdateOn(Calendar updateOn) {
		this.updateOn = updateOn;
	}

	public User getCreateBy() {
		return this.createBy;
	}

	protected void setCreateBy(User createBy) {
		this.createBy = createBy;
	}

	public User getUpdateBy() {
		return this.updateBy;
	}

	protected void setUpdateBy(User updateBy) {
		this.updateBy = updateBy;
	}
}
