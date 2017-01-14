package com.sap.jnc.marketing.dto.request.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author C5231393 Xu Xiaolei
 */
public class AuditDetailRequest implements Serializable {

	private static final long serialVersionUID = 9058774058782885310L;

	private Long id;

	private List<AuditItemsRequest> itemList = new ArrayList<AuditItemsRequest>();

	private List<AuditPictureRequest> pictureList = new ArrayList<AuditPictureRequest>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<AuditItemsRequest> getItemList() {
		return itemList;
	}

	public void setItemList(List<AuditItemsRequest> itemList) {
		this.itemList = itemList;
	}

	public List<AuditPictureRequest> getPictureList() {
		return pictureList;
	}

	public void setPictureList(List<AuditPictureRequest> pictureList) {
		this.pictureList = pictureList;
	}

}
