package com.sap.jnc.marketing.persistence.criteria.banquet;


import java.io.Serializable;
import java.util.Calendar;

import com.sap.jnc.marketing.persistence.criteria.SearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.BanquetVerifyApplicationStatus;

/**
 * @author I332242 Zhu Qiang
 */
public class BanquetVerifyApplicationSearchKeywordNode implements Serializable, SearchKeywordNode {

	private static final long serialVersionUID = -2594184638900832463L;

	private Long banquetId;
	
	private String expressNO;
	
	private Calendar expressTimeFrom;
	
	private Calendar expressTimeTo;
	
	private String dealerName;
	
	private String recycledNo;
	
	private BanquetVerifyApplicationStatus status;
	
	public Long getBanquetId() {
		return banquetId;
	}

	public void setBanquetId(Long banquetId) {
		this.banquetId = banquetId;
	}

	public String getExpressNO() {
		return expressNO;
	}

	public void setExpressNO(String expressNO) {
		this.expressNO = expressNO;
	}

	public Calendar getExpressTimeFrom() {
		return expressTimeFrom;
	}

	public void setExpressTimeFrom(Calendar expressTimeFrom) {
		this.expressTimeFrom = expressTimeFrom;
	}

	public Calendar getExpressTimeTo() {
		return expressTimeTo;
	}

	public void setExpressTimeTo(Calendar expressTimeTo) {
		this.expressTimeTo = expressTimeTo;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public BanquetVerifyApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(BanquetVerifyApplicationStatus status) {
		this.status = status;
	}

	public String getRecycledNo() {
		return recycledNo;
	}

	public void setRecycledNo(String recycledNo) {
		this.recycledNo = recycledNo;
	}
}
