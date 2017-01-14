package com.sap.jnc.marketing.dto.shared;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.IndividualProduct;

/**
 * @author Joel.Cheng I310645
 *
 */
public class IndividualProductInfo implements Serializable {

	private static final long serialVersionUID = 3060080646385139681L;

	private String capInnerCode;

	private String capOuterCode;

	private String boxId;

	private String caseId;

	public IndividualProductInfo() {
		// TODO Auto-generated constructor stub
	}

	public IndividualProductInfo(IndividualProduct individualProduct) {
		this.capInnerCode = individualProduct.getCapInnerCode();
		this.capOuterCode = individualProduct.getCapOuterCode();
		this.boxId = individualProduct.getBoxId();
		this.caseId = individualProduct.getCaseId();
	}

	public String getCapInnerCode() {
		return capInnerCode;
	}

	public void setCapInnerCode(String capInnerCode) {
		this.capInnerCode = capInnerCode;
	}

	public String getCapOuterCode() {
		return capOuterCode;
	}

	public void setCapOuterCode(String capOuterCode) {
		this.capOuterCode = capOuterCode;
	}

	public String getBoxId() {
		return boxId;
	}

	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

}
