package com.sap.jnc.marketing.dto.request.integration;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.jnc.marketing.dto.validator.integration.CheckValidity;
import com.sap.jnc.marketing.dto.validator.integration.JFCreateUpdateGroup;
import com.sap.jnc.marketing.dto.validator.integration.JFDeleteGroup;

@CheckValidity(groups = { JFDeleteGroup.class })
public class JFDeleteRequest implements Serializable {

	private static final long serialVersionUID = 2946884414469012332L;

	@Valid
	private List<JFDelRequestItem> items;

	public List<JFDelRequestItem> getItems() {
		return items;
	}

	public void setItems(List<JFDelRequestItem> items) {
		this.items = items;
	}

	public static class JFDelRequestItem implements Serializable {

		private static final long serialVersionUID = -4274488914590305557L;

		@JsonProperty(value = "boxID")
		@Length(min = 1, max = 18, groups = { JFCreateUpdateGroup.class })
		private String boxID;// 盒码

		@JsonProperty(value = "caseID")
		@Length(min = 24, max = 24, groups = { JFCreateUpdateGroup.class })
		private String caseID;// 箱码

		@JsonProperty(value = "bottleOID")
		@Length(min = 1, max = 18, groups = { JFCreateUpdateGroup.class })
		private String bottleOID;// 瓶外码

		@JsonProperty(value = "bottleIID")
		@NotBlank
		@Length(min = 19, max = 47, groups = { JFCreateUpdateGroup.class, JFDeleteGroup.class })
		private String bottleIID;// 瓶内码

		@JsonProperty(value = "bottleVID")
		@Length(min = 1, max = 18, groups = { JFCreateUpdateGroup.class })
		private String bottleVID;// 验证码

		@JsonProperty(value = "batchPP")
		private String batchPP;// 生产批次号

		@JsonProperty(value = "materialID")
		@Length(min = 1, max = 18, groups = { JFCreateUpdateGroup.class })
		private String materialID;// 物料号

		public String getBottleVID() {
			return bottleVID;
		}

		public void setBottleVID(String bottleVID) {
			this.bottleVID = bottleVID;
		}

		public String getBoxID() {
			return boxID;
		}

		public void setBoxID(String boxID) {
			this.boxID = boxID;
		}

		public String getCaseID() {
			return caseID;
		}

		public void setCaseID(String caseID) {
			this.caseID = caseID;
		}

		public String getBottleOID() {
			return bottleOID;
		}

		public void setBottleOID(String bottleOID) {
			this.bottleOID = bottleOID;
		}

		public String getBottleIID() {
			return bottleIID;
		}

		public void setBottleIID(String bottleIID) {
			this.bottleIID = bottleIID;
		}

		public String getBatchPP() {
			return batchPP;
		}

		public void setBatchPP(String batchPP) {
			this.batchPP = batchPP;
		}

		public String getMaterialID() {
			return materialID;
		}

		public void setMaterialID(String materialID) {
			this.materialID = materialID;
		}
	}
}