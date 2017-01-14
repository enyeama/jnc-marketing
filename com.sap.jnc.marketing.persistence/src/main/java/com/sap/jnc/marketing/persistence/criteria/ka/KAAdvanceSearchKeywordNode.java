/**
 * 
 */
package com.sap.jnc.marketing.persistence.criteria.ka;

import com.sap.jnc.marketing.persistence.criteria.SearchKeywordNode;

/**
 * @author Quansheng Liu I075496
 */
public class KAAdvanceSearchKeywordNode implements SearchKeywordNode {

	private static final long serialVersionUID = -6521056151258865923L;

	private Long id;
	private String branchName;
	private String kaSystemNumber;
	private Long kaOfficeId;
	private String storeNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getKaSystemNumber() {
		return kaSystemNumber;
	}

	public void setKaSystemNumber(String kaSystemNumber) {
		this.kaSystemNumber = kaSystemNumber;
	}

	public Long getKaOfficeId() {
		return kaOfficeId;
	}

	public void setKaOfficeId(Long kaOfficeId) {
		this.kaOfficeId = kaOfficeId;
	}

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

}
