/**
 * 
 */
package com.sap.jnc.marketing.dto.shared.bonus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.sap.jnc.marketing.infrastructure.shared.utils.DecimalUtils;
import com.sap.jnc.marketing.persistence.model.BonusDispatchConfig;
import com.sap.jnc.marketing.persistence.model.BonusDispatchConfigItem;

/**
 * 红包规则 一条红包规则对应一个产品类型，包含红包配置Id，从数据库获取红包规则的日期，红包规则条目
 * 
 * @author I323560
 */
public class BonusRule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3767293236392476701L;

	public BonusRule() {
		super();
	}

	public BonusRule(BonusDispatchConfig bonusDispatchConfig) {
		this.configId = bonusDispatchConfig.getId();
		this.ruleDate = Calendar.getInstance();
		this.ruleDate.set(Calendar.HOUR_OF_DAY, 0);
		this.ruleDate.set(Calendar.MINUTE, 0);
		this.ruleDate.set(Calendar.SECOND, 0);
		this.ruleDate.set(Calendar.MILLISECOND, 0);

		this.bonusRuleItemList = new ArrayList<BonusRuleItem>();
		Integer baseNumber = bonusDispatchConfig.getCalculatedBaseNumber();
		List<BonusDispatchConfigItem> items = bonusDispatchConfig.getItems();
		for (BonusDispatchConfigItem bonusDispatchConfigItem : items) {
			BonusRuleItem bonusRuleItem = new BonusRuleItem();
			BigDecimal baseNumberDecimal = new BigDecimal(baseNumber);
			// 通过calculatedBaseNumber计算该范围内某一档次红包数量
			BigDecimal percentage = DecimalUtils.Divide(bonusDispatchConfigItem.getPercentage(), new BigDecimal(100));
			int quantity = DecimalUtils.Multiply(percentage, baseNumberDecimal).intValue();
			bonusRuleItem.setQuantity(quantity);
			// 以人民币“分”为单位，前台配置页面单位为人民币“元”
			bonusRuleItem.setBonusAmount(DecimalUtils.Multiply(bonusDispatchConfigItem.getAmount().getValue(), new BigDecimal(100)).intValue());
			this.bonusRuleItemList.add(bonusRuleItem);
		}
	}

	/**
	 * 红包配置Id
	 */
	private Long configId;

	/**
	 * 获取红包规则时的日期
	 */
	private Calendar ruleDate;

	/**
	 * Value: 对应产品类型的红包规则条目
	 */
	private List<BonusRuleItem> bonusRuleItemList;

	public Long getConfigId() {
		return configId;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	public Calendar getRuleDate() {
		return ruleDate;
	}

	public void setRuleDate(Calendar ruleDate) {
		this.ruleDate = ruleDate;
	}

	public List<BonusRuleItem> getBonusRuleItemList() {
		return bonusRuleItemList;
	}

	public void setBonusRuleItemList(List<BonusRuleItem> bonusRuleItemList) {
		this.bonusRuleItemList = bonusRuleItemList;
	}

}
