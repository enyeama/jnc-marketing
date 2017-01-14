package com.sap.jnc.marketing.persistence.criteria.banquet;


import java.io.Serializable;
import java.util.Calendar;

import com.sap.jnc.marketing.persistence.criteria.SearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.BanquetStatus;

/**
 * @author I332242 Zhu Qiang
 */
public class BanquetSearchKeywordNode implements Serializable, SearchKeywordNode {

	private static final long serialVersionUID = -5845990496381151311L;
	
	private Long id;
	
	private BanquetStatus status;

	private Calendar banquetTimeFrom;
	
	private Calendar banquetTimeTo;
	
	private Long creatorId;
	
	private String officeName;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getBanquetTimeFrom() {
		return banquetTimeFrom;
	}

	public void setBanquetTimeFrom(Calendar banquetTimeFrom) {
		this.banquetTimeFrom = banquetTimeFrom;
	}

	public Calendar getBanquetTimeTo() {
		return banquetTimeTo;
	}

	public void setBanquetTimeTo(Calendar banquetTimeTo) {
		this.banquetTimeTo = banquetTimeTo;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public BanquetStatus getStatus() {
		return status;
	}

	public void setStatus(BanquetStatus status) {
		this.status = status;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
}
