package com.sap.jnc.marketing.dto.respose.mainten;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;
/**
 * @author Maggie Liu
 */
public class MaintenDMSthirdResponse implements Serializable {
	private static final long serialVersionUID = -4584688742835365459L;

	private String id="";
	private String name="";

	public MaintenDMSthirdResponse(ProductDmsCategory productDmsCategory) {
		if(productDmsCategory==null){
			return;
		}
		if(productDmsCategory.getId()!=null){
			this.id = productDmsCategory.getId();
		}
		if(productDmsCategory.getName()!=null){
			this.name = productDmsCategory.getName();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
