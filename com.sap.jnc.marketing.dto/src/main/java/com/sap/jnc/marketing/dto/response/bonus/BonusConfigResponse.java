package com.sap.jnc.marketing.dto.response.bonus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.model.Amount;
import com.sap.jnc.marketing.persistence.model.BonusDispatchConfig;
import com.sap.jnc.marketing.persistence.model.BonusDispatchConfigItem;

/**
 * 红包支付配置response DTO
 * 
 * @author I327119
 */
public class BonusConfigResponse extends PageImpl<BonusConfigResponse.Item> implements Serializable {

	private static final long serialVersionUID = -174683857120963831L;

	public BonusConfigResponse(Page<BonusDispatchConfig> pages, PageRequest pageRequest) {
		super(pages.getContent().stream().map(bdc -> new Item(bdc)).collect(Collectors.toList()), pageRequest,
				pages.getTotalElements());
	}

	public static class Item implements Serializable {

		private static final long serialVersionUID = -326230611997851204L;

		public Item() {
		}

		public Item(BonusDispatchConfig bdc) {
			if (bdc == null) {
				return;
			}

			this.setId(bdc.getId());
			this.setAverageAmount(
					bdc.getAverageAmount().getValue().toString() + " " + bdc.getAverageAmount().getCurrency());
			this.setVarianceAmount(
					bdc.getVarianceAmount().getValue().toString() + " " + bdc.getVarianceAmount().getCurrency());

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			this.setValidFrom(sdf.format(bdc.getValidityPeriod().getValidFrom().getTime()));
			this.setValidTo(sdf.format(bdc.getValidityPeriod().getValidTo().getTime()));

			if (bdc.getErpCategory() != null) {
				ProductErpRespose productErpRespose = new ProductErpRespose();
				productErpRespose.setId(bdc.getErpCategory().getId());
				productErpRespose.setCategoryName(bdc.getErpCategory().getCategoryName());
				productErpRespose.setLevelName(bdc.getErpCategory().getLevelName());
				productErpRespose.setLevelNumber(bdc.getErpCategory().getLevelNumber());
				this.setErpRespose(productErpRespose);
			}

			if (bdc.getItems() != null && bdc.getItems().size() > 0) {
				List<BonusConfigItemResponse> bonusConfigItems = new ArrayList<BonusConfigItemResponse>();
				Iterator<BonusDispatchConfigItem> bonusDispatchConfigItems = bdc.getItems().iterator();
				while (bonusDispatchConfigItems.hasNext()) {
					BonusDispatchConfigItem bonusDispatchConfigItem = bonusDispatchConfigItems.next();
					BonusConfigItemResponse bonusConfigItemResponse = new BonusConfigItemResponse(
							bonusDispatchConfigItem.getPercentage(), bonusDispatchConfigItem.getAmount());
					bonusConfigItems.add(bonusConfigItemResponse);
				}
				this.setBonusConfigItems(bonusConfigItems);

			}
		}

		/**
		 * 红包配置Id
		 */
		private Long id;

		/**
		 * 计划均化金额
		 */
		private String averageAmount;

		/**
		 * 均值误差金额
		 */
		private String varianceAmount;

		/**
		 * 均值生效时间
		 */
		private String validFrom;

		/**
		 * 均值失效时间
		 */
		private String validTo;

		/**
		 * 产品大类
		 */
		private ProductErpRespose erpRespose;

		/**
		 * Bonus Configuration Items
		 */
		private List<BonusConfigItemResponse> bonusConfigItems;

		public List<BonusConfigItemResponse> getBonusConfigItems() {
			return this.bonusConfigItems;
		}

		public void setBonusConfigItems(List<BonusConfigItemResponse> bonusConfigItems) {
			this.bonusConfigItems = bonusConfigItems;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

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

		public ProductErpRespose getErpRespose() {
			return erpRespose;
		}

		public void setErpRespose(ProductErpRespose erpRespose) {
			this.erpRespose = erpRespose;
		}

		class BonusConfigItemResponse implements Serializable {

			private static final long serialVersionUID = -7627373742314340826L;

			private BigDecimal percentage;
			private String amount;

			public BonusConfigItemResponse() {
			}

			public BonusConfigItemResponse(BigDecimal percentage, Amount amount) {
				super();
				this.percentage = percentage;
				if (amount != null) {
					this.amount = amount.getValue() + " " + amount.getCurrency();
				}
			}

			public void setPercentage(BigDecimal percentage) {
				this.percentage = percentage;
			}

			public BigDecimal getPercentage() {
				return this.percentage;
			}

			public void setAmount(Amount amount) {
				if (amount != null) {
					this.amount = amount.getValue() + " " + amount.getCurrency();
				}
			}

			public String getAmount() {
				return this.amount;
			}
		}

		class ProductErpRespose implements Serializable {

			private static final long serialVersionUID = 4073905992038213428L;

			/**
			 * "物料四级分类"的分类Id
			 */
			private Long id;

			/**
			 * "物料四级分类"的分类级别
			 */
			private String levelNumber;

			/**
			 * "物料四级分类"的分类名称
			 */
			private String levelName;

			/**
			 * "物料四级分类"的分类值名称
			 */
			private String categoryName;

			public ProductErpRespose() {
			}

			public ProductErpRespose(Long id, String categoryName) {
				super();
				this.id = id;
				this.categoryName = categoryName;
			}

			public ProductErpRespose(String levelNumber, String levelName, Long id, String categoryName) {
				super();
				this.levelNumber = levelNumber;
				this.levelName = levelName;
				this.id = id;
				this.categoryName = categoryName;
			}

			public Long getId() {
				return id;
			}

			public void setId(Long id) {
				this.id = id;
			}

			public String getLevelNumber() {
				return levelNumber;
			}

			public void setLevelNumber(String levelNumber) {
				this.levelNumber = levelNumber;
			}

			public String getLevelName() {
				return levelName;
			}

			public void setLevelName(String levelName) {
				this.levelName = levelName;
			}

			public String getCategoryName() {
				return categoryName;
			}

			public void setCategoryName(String categoryName) {
				this.categoryName = categoryName;
			}

		}

	}
}
