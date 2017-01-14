package com.sap.jnc.marketing.dto.response.contract;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.enumeration.ContractStatus;
import com.sap.jnc.marketing.persistence.model.ContractItem;
import com.sap.jnc.marketing.persistence.model.Region;
import com.sap.jnc.marketing.persistence.model.ValidityPeriod;

public class DealerContractResponse extends PageImpl<DealerContractResponse.Item> implements Serializable {

	private static final long serialVersionUID = 6682099100988402630L;

	public DealerContractResponse(Page<ContractItem> pages, PageRequest pageRequest) {
		super(
			// Content
			pages.getContent().stream().map(contractItem -> new Item(contractItem)).collect(Collectors.toList()),
			// Page Request
			pageRequest,
			// Total
			pages.getTotalElements());
	}

	public static class Item implements Serializable {

		private static final long serialVersionUID = 6824889108470865103L;

		private Long id;

		private String externalId;

		private String status;

		private String name;

		private String dealerId;

		private String positionId;

		private String finacialYear;

		private String productDmsCategoryId;

		private String channelId;

		private String regionId;

		private Calendar validFrom;

		private Calendar validTo;

		private String contractTemplate;

		public Item(ContractItem contractItem) {

			if (contractItem.getContract() != null) {
				this.setId(contractItem.getContract().getId());
				String externalId = contractItem.getContract().getExternalId();
				if (StringUtils.isNotBlank(externalId)) {
					this.setExternalId(externalId);
				}

				if (contractItem.getContract().getStatus() != null) {
					if (contractItem.getContract().getStatus() == ContractStatus.INACTIVE)
						this.setStatus("停用");
					else
						this.setStatus("可用");
				}

				if (contractItem.getContract().getDealer() != null) {
					if (StringUtils.isNotBlank(contractItem.getContract().getDealer().getExternalId())) {
						this.setDealerId(contractItem.getContract().getDealer().getExternalId());
					}
				}

				if (contractItem.getContract().getCityManager() != null) {
					if (StringUtils.isNotBlank(contractItem.getContract().getCityManager().getExternalId())) {
						this.setPositionId(contractItem.getContract().getCityManager().getExternalId());
					}
				}

				if (StringUtils.isNotBlank(contractItem.getContract().getFinancialYear())) {
					this.setFinacialYear(contractItem.getContract().getFinancialYear());
				}
				ValidityPeriod validperiod = contractItem.getContract().getValidityPeriod();
				if (validperiod != null && validperiod.getValidFrom() != null && validperiod.getValidTo() != null) {
					this.setValidFrom(validperiod.getValidFrom());
					this.setValidTo(validperiod.getValidTo());
				}

				if (StringUtils.isNotBlank(contractItem.getContract().getTemplateName())) {
					this.setContractTemplate(contractItem.getContract().getTemplateName());
				}

			}
			if (contractItem.getDmsCategory() != null) {
				this.setProductDmsCategoryId(contractItem.getDmsCategory().getId());
			}

			if (contractItem.getChannel() != null) {
				this.setChannelId(contractItem.getChannel().getExternalId());
			}

			List<Region> regions = contractItem.getRegions();
			if (CollectionUtils.isNotEmpty(regions)) {
				String regionStr = "";
				for (Region region : regions) {
					if (StringUtils.isNotBlank(region.getCountyId()))
						regionStr += region.getCountyId() + ";";
				}
				if (regionStr.length() > 1)
					regionStr = regionStr.substring(0, regionStr.length() - 1);
				this.setRegionId(regionStr);
			}
		}

		public Item() {

		}

		public Calendar getValidFrom() {
			return validFrom;
		}

		public void setValidFrom(Calendar validFrom) {
			this.validFrom = validFrom;
		}

		public Calendar getValidTo() {
			return validTo;
		}

		public void setValidTo(Calendar validTo) {
			this.validTo = validTo;
		}

		public String getRegionId() {
			return regionId;
		}

		public void setRegionId(String regionId) {
			this.regionId = regionId;
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

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDealerId() {
			return dealerId;
		}

		public void setDealerId(String dealerId) {
			this.dealerId = dealerId;
		}

		public String getPositionId() {
			return positionId;
		}

		public void setPositionId(String positionId) {
			this.positionId = positionId;
		}

		public String getContractTemplate() {
			return contractTemplate;
		}

		public void setContractTemplate(String contractTemplate) {
			this.contractTemplate = contractTemplate;
		}

		public String getFinacialYear() {
			return finacialYear;
		}

		public void setFinacialYear(String finacialYear) {
			this.finacialYear = finacialYear;
		}

		public String getProductDmsCategoryId() {
			return productDmsCategoryId;
		}

		public void setProductDmsCategoryId(String productDmsCategoryId) {
			this.productDmsCategoryId = productDmsCategoryId;
		}

		public String getChannelId() {
			return channelId;
		}

		public void setChannelId(String channelId) {
			this.channelId = channelId;
		}

	}
}
