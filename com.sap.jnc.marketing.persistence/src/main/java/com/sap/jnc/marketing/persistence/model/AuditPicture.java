package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.enumeration.AuditPictureType;

@Entity
@Table(name = "T_AUDIT_PICTURE", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "AuditPictureSeq", sequenceName = "SEQ_AUDITPICTURE")
public class AuditPicture extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 2075683535383792449L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AuditPictureSeq")
	private Long id;

	@Column(name = "url")
	private String url;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private AuditPictureType type;

	@Column(name = "address")
	private String address;

	@Column(name = "comment")
	private String comment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auditId")
	private Audit audit;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processor")
	private Employee processor;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Audit getAudit() {
		return this.audit;
	}

	public void setAudit(Audit audit) {
		this.audit = audit;
	}

	public Employee getProcessor() {
		return this.processor;
	}

	public void setProcessor(Employee processor) {
		this.processor = processor;
	}

	public AuditPictureType getType() {
		return type;
	}

	public void setType(AuditPictureType type) {
		this.type = type;
	}
}
