package com.sap.jnc.marketing.dto.request.integration;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.jnc.marketing.dto.validator.integration.CheckOutboundValidity;

@CheckOutboundValidity
public class JNCOuntboundInfo implements Serializable {

	private static final long serialVersionUID = 1790871177762790052L;

	@Valid
	private List<JNCOuntboundInfoItem> items;

	public List<JNCOuntboundInfoItem> getItems() {
		return items;
	}

	public void setItems(List<JNCOuntboundInfoItem> items) {
		this.items = items;
	}

	public static class JNCOuntboundInfoItem implements Serializable {

		private static final long serialVersionUID = 4231907996277501501L;

		@JsonProperty(value = "dealerID")
		@Min(value = 0)
		@NotNull
		private String dealerID;// 经销商号

		@JsonProperty(value = "erpOrderID")
		@Length(min = 1, max = 10)
		@NotBlank
		private String erpOrderID;// ERP订单号

		@JsonProperty(value = "dmsOrderID")
		@Digits(integer = 7, fraction = 0)
		@NotNull
		private String dmsOrderID;// DMS订单号

		@JsonProperty(value = "orderItemID")
		@Min(value = 0)
		@NotBlank
		private String orderItemID;// 关联订单行项目编号

		@JsonProperty(value = "caseID")
		@Length(min = 1, max = 24)
		@NotBlank
		private String caseID;// 箱码

		/*
		 * @JsonProperty(value = "quantity") private Integer quantity;// 订单行项目数量
		 */

		@JsonProperty(value = "materialID")
		@Length(min = 1, max = 18)
		@NotBlank
		private String materialID;// 物料号

		@JsonProperty(value = "cud")
		@NotBlank
		@Pattern(regexp = "C|U|D")
		private String cud;

		public String getCud() {
			return cud;
		}

		public void setCud(String cud) {
			this.cud = cud;
		}

		public String getDealerID() {
			return dealerID;
		}

		public void setDealerID(String dealerID) {
			this.dealerID = dealerID;
		}

		public String getErpOrderID() {
			return erpOrderID;
		}

		public void setErpOrderID(String erpOrderID) {
			this.erpOrderID = erpOrderID;
		}

		public String getDmsOrderID() {
			return dmsOrderID;
		}

		public void setDmsOrderID(String dmsOrderID) {
			this.dmsOrderID = dmsOrderID;
		}

		public String getOrderItemID() {
			return orderItemID;
		}

		public void setOrderItemID(String orderItemID) {
			this.orderItemID = orderItemID;
		}

		public String getCaseID() {
			return caseID;
		}

		public void setCaseID(String caseID) {
			this.caseID = caseID;
		}

		public String getMaterialID() {
			return materialID;
		}

		public void setMaterialID(String materialID) {
			this.materialID = materialID;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

	}
}
