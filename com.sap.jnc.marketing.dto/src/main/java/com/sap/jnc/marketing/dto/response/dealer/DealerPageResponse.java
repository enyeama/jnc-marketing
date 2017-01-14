package com.sap.jnc.marketing.dto.response.dealer;

import java.io.Serializable;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.dto.shared.contact.ContactNode;
import com.sap.jnc.marketing.persistence.enumeration.DealerStatus;
import com.sap.jnc.marketing.persistence.model.Dealer;

public class DealerPageResponse extends PageImpl<DealerPageResponse.Item> implements Serializable {

	private static final long serialVersionUID = 6824889108470865103L;

	public DealerPageResponse(Page<Dealer> pages, PageRequest pageRequest) {
		super(
			// Content
			pages.getContent().stream().map(dealer -> new Item(dealer)).collect(Collectors.toList()),
			// Page Request
			pageRequest,
			// Total
			pages.getTotalElements());
	}

	public static class Item implements Serializable {

		private static final long serialVersionUID = 6824889108470865103L;

		private Long id;

		private String externalId;

		private DealerStatus status;

		private String name;

		private Boolean isPlatformDealer;

		private String address;

		private ContactNode legalContact;

		private String cityManagerName;

		private String officeName;

		private String parentDealerName;

		public Item() {
		}

		public Item(Dealer dealer) {
			if (dealer == null) {
				return;
			}
			this.setId(dealer.getId());
			this.setAddress(dealer.getAddress());
			this.setExternalId(dealer.getExternalId());
			this.setIsPlatformDealer(dealer.getIsPlatformDealer());
			this.setLegalContact(new ContactNode(dealer.getLegalContact()));
			this.setName(dealer.getName());
			if (dealer.getOffice() != null) {
				this.setOfficeName(dealer.getOffice().getName());
			}
			if (dealer.getParent() != null) {
				this.setParentDealerName(dealer.getParent().getName());
			}
			this.setStatus(dealer.getStatus());
		}

		public Long getId() {
			return this.id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getExternalId() {
			return this.externalId;
		}

		public void setExternalId(String externalId) {
			this.externalId = externalId;
		}

		public DealerStatus getStatus() {
			return this.status;
		}

		public void setStatus(DealerStatus status) {
			this.status = status;
		}

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Boolean getIsPlatformDealer() {
			return this.isPlatformDealer;
		}

		public void setIsPlatformDealer(Boolean isPlatformDealer) {
			this.isPlatformDealer = isPlatformDealer;
		}

		public String getAddress() {
			return this.address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public ContactNode getLegalContact() {
			return this.legalContact;
		}

		public void setLegalContact(ContactNode legalContact) {
			this.legalContact = legalContact;
		}

		public String getCityManagerName() {
			return this.cityManagerName;
		}

		public void setCityManagerName(String cityManagerName) {
			this.cityManagerName = cityManagerName;
		}

		public String getOfficeName() {
			return this.officeName;
		}

		public void setOfficeName(String officeName) {
			this.officeName = officeName;
		}

		public String getParentDealerName() {
			return this.parentDealerName;
		}

		public void setParentDealerName(String parentDealerName) {
			this.parentDealerName = parentDealerName;
		}
	}
}
