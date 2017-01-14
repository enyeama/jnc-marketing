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

@CheckValidity(groups = { JFCreateUpdateGroup.class })
public class JFMergeRequest implements Serializable {

	private static final long serialVersionUID = 7662983987848262547L;

	@Valid
	private List<JfMergeRequestItem> items;

	public List<JfMergeRequestItem> getItems() {
		return items;
	}

	public void setItems(List<JfMergeRequestItem> items) {
		this.items = items;
	}

	public static class JfMergeRequestItem implements Serializable {
		private static final long serialVersionUID = -8887650958433621809L;

		@JsonProperty(value = "boxID")
		@NotBlank(groups = { JFCreateUpdateGroup.class })
		@Length(min = 1, max = 18, groups = { JFCreateUpdateGroup.class })
		private String boxID;// 盒码

		@JsonProperty(value = "caseID")
		@NotBlank(groups = { JFCreateUpdateGroup.class })
		@Length(min = 24, max = 26, groups = { JFCreateUpdateGroup.class })
		private String caseID;// 箱码

		@JsonProperty(value = "bottleOID")
		@NotBlank(groups = { JFCreateUpdateGroup.class })
		@Length(min = 1, max = 18, groups = { JFCreateUpdateGroup.class })
		private String bottleOID;// 瓶外码

		@JsonProperty(value = "bottleIID")
		@NotBlank(groups = { JFCreateUpdateGroup.class, JFDeleteGroup.class })
		@Length(min = 19, max = 47, groups = { JFCreateUpdateGroup.class, JFDeleteGroup.class })
		private String bottleIID;// 瓶内码

		@JsonProperty(value = "bottleVID")
		@NotBlank(groups = { JFCreateUpdateGroup.class })
		@Length(min = 1, max = 4, groups = { JFCreateUpdateGroup.class })
		private String bottleVID;// 验证码

		@JsonProperty(value = "batchPP")
		@NotBlank(groups = { JFCreateUpdateGroup.class })
		private String batchPP;// 生产批次号

		@JsonProperty(value = "materialID")
		@Length(min = 1, max = 18, groups = { JFCreateUpdateGroup.class })
		@NotBlank(groups = { JFCreateUpdateGroup.class })
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
