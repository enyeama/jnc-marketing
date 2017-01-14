package com.sap.jnc.marketing.dto.request.banquet;

import java.io.Serializable;
import java.util.List;

import com.sap.jnc.marketing.persistence.enumeration.BanquetStatus;

/**
 * @author I332242 Zhu Qiang
 */
public class BanquetApproveRequest implements Serializable {

	private static final long serialVersionUID = -306738779294935733L;
	
	private List<Long> banquetIds;
	
	private BanquetStatus status;
	
	private String approveComment;
	
	public String getApproveComment() {
		return approveComment;
	}

	public void setApproveComment(String approveComment) {
		this.approveComment = approveComment;
	}

	public BanquetStatus getStatus() {
		return status;
	}

	public void setStatus(BanquetStatus status) {
		this.status = status;
	}

	public List<Long> getBanquetIds() {
		return banquetIds;
	}

	public void setBanquetIds(List<Long> banquetIds) {
		this.banquetIds = banquetIds;
	}
}
