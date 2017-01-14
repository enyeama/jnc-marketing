package com.sap.jnc.marketing.dto.respose.mainten;
/**
 * @author Maggie Liu
 */
import com.sap.jnc.marketing.persistence.model.PositionView;

/**
 * @author Maggie Liu
 */
public class MaintenLeaderResponse {
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

	public MaintenLeaderResponse(PositionView p) {
		if (p == null) {
			return;
		}
		if(p.getExternalId()!=null){
			this.id = p.getExternalId();
		}
		if(p.getEmployee()!=null){
			this.name = p.getEmployee().getName();
		}
	}
}
