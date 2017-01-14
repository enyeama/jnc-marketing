/**
 * 
 */
package com.sap.jnc.marketing.dto.response.dealer;

import java.util.Calendar;

/**
 * @author Quansheng Liu I075496
 */
public class DealerLogisticStasticsResponse {
	long leadId;
	String leadName;
	int dealerOutCount;
	int leadInCount;
	Calendar createOn;

	public DealerLogisticStasticsResponse() {
		this.dealerOutCount = 0;
		this.leadInCount = 0;
	}

	public long getLeadId() {
		return leadId;
	}

	public void setLeadId(long leadId) {
		this.leadId = leadId;
	}

	public String getLeadName() {
		return leadName;
	}

	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}

	public int getDealerOutCount() {
		return dealerOutCount;
	}

	public void setDealerOutCount(int dealerOutCount) {
		this.dealerOutCount = dealerOutCount;
	}

	public int getLeadInCount() {
		return leadInCount;
	}

	public void setLeadInCount(int leadInCount) {
		this.leadInCount = leadInCount;
	}

	public Calendar getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Calendar createOn) {
		this.createOn = createOn;
	}

}
