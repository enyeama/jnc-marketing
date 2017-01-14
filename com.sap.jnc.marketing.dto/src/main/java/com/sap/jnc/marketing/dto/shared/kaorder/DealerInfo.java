package com.sap.jnc.marketing.dto.shared.kaorder;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.enumeration.DealerStatus;
import com.sap.jnc.marketing.persistence.model.Dealer;

/**
 * @author Joel.Cheng I310645
 *
 */
public class DealerInfo implements Serializable {

	private static final long serialVersionUID = -631435055553232244L;

	private Long id;

	private String externalId;

	private DealerStatus status;

	private String name;

	private Boolean isPlatformDealer;

	private String address;

	public DealerInfo() {
		// TODO Auto-generated constructor stub
	}

	public DealerInfo(Dealer dealer) {
		this.id = dealer.getId();
		this.externalId = dealer.getExternalId();
		this.status = dealer.getStatus();
		this.name = dealer.getName();
		this.isPlatformDealer = dealer.getIsPlatformDealer();
		this.address = dealer.getAddress();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public DealerStatus getStatus() {
		return status;
	}

	public void setStatus(DealerStatus status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsPlatformDealer() {
		return isPlatformDealer;
	}

	public void setIsPlatformDealer(Boolean isPlatformDealer) {
		this.isPlatformDealer = isPlatformDealer;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
