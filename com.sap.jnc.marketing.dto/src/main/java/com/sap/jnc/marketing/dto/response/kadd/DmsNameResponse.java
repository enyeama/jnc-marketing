package com.sap.jnc.marketing.dto.response.kadd;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;

/**
 * @author Maggie Liu
 */
public class DmsNameResponse implements Serializable {

	private static final long serialVersionUID = -2550150262811003598L;
	private String dmsName="";
	private String dmsId="";
	
	public DmsNameResponse(ProductDmsCategory productDmsCategory){
		if(productDmsCategory==null){
			return;
		}
		if(productDmsCategory.getName()!=null){
			this.dmsName=productDmsCategory.getName();
		}
		if(productDmsCategory.getId()!=null){
			this.dmsId=productDmsCategory.getId();
		}
	}

	public String getDmsName() {
		return dmsName;
	}

	public void setDmsName(String dmsName) {
		this.dmsName = dmsName;
	}

	public String getDmsId() {
		return dmsId;
	}

	public void setDmsId(String dmsId) {
		this.dmsId = dmsId;
	}
	
}
