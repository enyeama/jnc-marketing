package com.sap.jnc.marketing.dto.request.bonus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BonusDispatcherRequest implements Serializable {

	private static final long serialVersionUID = 2683425680321919073L;

	/** 计划均化金额 */
	private String averageAmount;
	/** 均值误差金额 */
	private String varianceAmount;
	/** 均值生效时间 */
	private String validFrom;
	/** 均值失效时间 */
	private String validTo;
	/** ErpProductCategory ID */
	private Long productErpCategoryId;
	/** 基数 */
	private Integer calculatedBaseNumber;

	public static final String AMOUNTCURRENCY = "CNY";

	private List<BonusDispatcherItemRequest> bonusDispatcherItemRequests = new ArrayList<BonusDispatcherItemRequest>();

	public String getAverageAmount() {
		return averageAmount;
	}

	public void setAverageAmount(String averageAmount) {
		this.averageAmount = averageAmount;
	}

	public String getVarianceAmount() {
		return varianceAmount;
	}

	public void setVarianceAmount(String varianceAmount) {
		this.varianceAmount = varianceAmount;
	}

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public String getValidTo() {
		return validTo;
	}

	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}

	public Long getProductErpCategoryId() {
		return productErpCategoryId;
	}

	public void setProductErpCategoryId(Long productErpCategoryId) {
		this.productErpCategoryId = productErpCategoryId;
	}

	public Integer getCalculatedBaseNumber() {
		return calculatedBaseNumber;
	}

	public void setCalculatedBaseNumber(Integer calculatedBaseNumber) {
		this.calculatedBaseNumber = calculatedBaseNumber;
	}

	public List<BonusDispatcherItemRequest> getBonusDispatcherItemRequests() {
		return bonusDispatcherItemRequests;
	}

	public void setBonusDispatcherItemRequests(List<BonusDispatcherItemRequest> bonusDispatcherItemRequests) {
		this.bonusDispatcherItemRequests = bonusDispatcherItemRequests;
	}

	public static class BonusDispatcherItemRequest implements Serializable {

		private static final long serialVersionUID = -5004513784157388339L;
		private Integer id;
		/** 数量 */
		private String number;
		/** 比例 */
		private String percentage;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		public String getPercentage() {
			return percentage;
		}

		public void setPercentage(String percentage) {
			this.percentage = percentage;
		}

		@Override
		public String toString() {
			return "BonusDispatcherItemRequest [id=" + id + ", number=" + number + ", percentage=" + percentage + "]";
		}

	}

}
