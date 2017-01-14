package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

@Entity
@Table(name = "T_EXHIBITION_PICTURE", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "ExhibitionPictureSeq", sequenceName = "SEQ_EXHIBITIONPICTURE")
public class ExhibitionPicture extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 8541558564928289763L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ExhibitionPictureSeq")
	private Long id;

	@Column(name = "url")
	private String url;

	@Column(name = "address")
	private String address;

	@Column(name = "comment")
	private String comment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exhibitionId")
	private Exhibition exhibition;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creatorId")
	private Employee creator;

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

	public Employee getCreator() {
		return this.creator;
	}

	public void setCreator(Employee creator) {
		this.creator = creator;
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

	public Exhibition getExhibition() {
		return this.exhibition;
	}

	public void setExhibition(Exhibition exhibition) {
		this.exhibition = exhibition;
	}

}
