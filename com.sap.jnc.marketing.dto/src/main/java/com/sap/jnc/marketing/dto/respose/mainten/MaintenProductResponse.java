package com.sap.jnc.marketing.dto.respose.mainten;
/**
 * @author Maggie Liu
 */
import com.sap.jnc.marketing.persistence.model.Product;

public class MaintenProductResponse {
	private String id="";
	private String name="";
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
	
	public MaintenProductResponse(Product p){
		if(p==null){
			return;
		}
		if(p.getId()!=null){
			this.id= p.getId();
		}
		if(p.getName()!=null){
			this.name=p.getName();
		}
	}
}
