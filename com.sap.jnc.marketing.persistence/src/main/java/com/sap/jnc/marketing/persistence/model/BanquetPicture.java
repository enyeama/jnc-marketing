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
@Table(name = "T_BANQUET_PICTURE", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "BanquetPictureSeq", sequenceName = "SEQ_BANQUET_PICTURE")
public class BanquetPicture extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -7547698619476931427L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BanquetPictureSeq")
	private Long id;

	@Column(name = "url")
	private String url;

	@Column(name = "address")
	private String address;

	@Column(name = "comment")
	private String comment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banquetId")
	private Banquet banquet;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processor")
	private Employee processor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Banquet getBanquet() {
		return banquet;
	}

	public void setBanquet(Banquet banquet) {
		this.banquet = banquet;
	}

	public Employee getProcessor() {
		return processor;
	}

	public void setProcessor(Employee processor) {
		this.processor = processor;
	}
}
