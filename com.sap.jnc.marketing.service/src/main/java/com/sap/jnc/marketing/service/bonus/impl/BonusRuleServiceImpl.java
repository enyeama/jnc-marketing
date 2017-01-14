package com.sap.jnc.marketing.service.bonus.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sap.jnc.marketing.dto.request.bonus.BonusResultRequest;
import com.sap.jnc.marketing.dto.response.bonus.BonusVerificationResponse;
import com.sap.jnc.marketing.dto.shared.bonus.BonusRule;
import com.sap.jnc.marketing.dto.shared.bonus.BonusRuleItem;
import com.sap.jnc.marketing.infrastructure.config.QQMapConstants;
import com.sap.jnc.marketing.persistence.model.BonusDispatchConfig;
import com.sap.jnc.marketing.persistence.model.IndividualProductBonus;
import com.sap.jnc.marketing.persistence.repository.BonusDispatchConfigRepository;
import com.sap.jnc.marketing.persistence.repository.IndividualProductBonusRepository;
import com.sap.jnc.marketing.service.bonus.BonusRuleService;
import com.sap.jnc.marketing.service.exception.bonus.BonusRuleServiceException;
import com.sap.jnc.marketing.service.globalcache.GlobalCacheService;
import com.sap.jnc.marketing.service.qqmap.QQMapService;

/**
 * 红包规则Service
 * 
 * @author I323560
 */
@Service
@Transactional
public class BonusRuleServiceImpl implements BonusRuleService {

	private static Logger LOGGER = LoggerFactory.getLogger(BonusRuleServiceImpl.class);

	private static final String BONUS_RULE = "BONUS_RULE";
	private static final String BONUS_LIST_APPLIED = "BONUS_LIST_APPLIED";
	private static final String BONUS_LIST_BUFFER = "BONUS_LIST_BUFFER";

	@Autowired
	private GlobalCacheService globalCacheService;

	@Autowired
	private BonusDispatchConfigRepository bonusDispatchConfigRepository;

	@Autowired
	private IndividualProductBonusRepository individualProductBonusRepository;

	@Autowired
	private QQMapService qqMapService;

	@Override
	@Transactional(readOnly = true)
	public BonusVerificationResponse getBonusVerification(String capInnerCode, String verificationCode) {
		LOGGER.debug("capInnerCode:" + capInnerCode + "----verificationCode" + verificationCode);
		BonusVerificationResponse bonusVerification = new BonusVerificationResponse();
		Integer bonusAmount = 0;
		boolean verified = individualProductBonusRepository.verifyCode(capInnerCode, verificationCode);
		if (verified) {
			bonusAmount = getBonusAmount(capInnerCode);
		}
		bonusVerification.setVerified(verified);
		bonusVerification.setBonusAmount(bonusAmount);

		LOGGER.debug(bonusVerification.toString());
		return bonusVerification;
	}

	@Override
	@Transactional
	public void setBonusResult(BonusResultRequest bonusResultRequest) {
		LOGGER.debug(bonusResultRequest.toString());
		if (null == bonusResultRequest || StringUtils.isBlank(bonusResultRequest.getCapInnerCode())) {
			throw new BonusRuleServiceException(BonusRuleServiceException.WECHAT_RETURN_ERROR);
		}

		BigDecimal latitude = bonusResultRequest.getLatitude();
		BigDecimal longitude = bonusResultRequest.getLongitude();
		if (null == latitude || null == longitude) {
			throw new BonusRuleServiceException(BonusRuleServiceException.GPS_RETURN_ERROR);
		}

		String latitudeStr = new Double(latitude.doubleValue()).toString();
		String longitudeStr = new Double(longitude.doubleValue()).toString();
		String locationStr = qqMapService.getLocation(latitudeStr, longitudeStr);
		JSONObject jsonObj = JSON.parseObject(locationStr);
		JSONObject result = (JSONObject) jsonObj.get(QQMapConstants.RESULT);
		JSONObject formattedAddresses = (JSONObject) result.get(QQMapConstants.FORMATTED_ADDRESSES);
		String recommend = formattedAddresses.getString(QQMapConstants.FORMATTED_ADDRESSES_RECOMMEND);
		JSONObject addressComponent = (JSONObject) result.get(QQMapConstants.ADDRESS_COMPONENT);
		String province = addressComponent.getString(QQMapConstants.ADDRESS_COMPONENT_PROVINCE);
		String city = addressComponent.getString(QQMapConstants.ADDRESS_COMPONENT_CITY);
		String district = addressComponent.getString(QQMapConstants.ADDRESS_COMPONENT_DISTRICT);

		IndividualProductBonus individualProductBonus = individualProductBonusRepository
				.findOne(bonusResultRequest.getCapInnerCode());
		individualProductBonus.setActivityName(bonusResultRequest.getActivityName());
		individualProductBonus
				.setWechatSubscriptionAccountAppId(bonusResultRequest.getWechatSubscriptionAccountAppId());
		individualProductBonus.setWechatPaymentAccountId(bonusResultRequest.getWechatPaymentAccountId());
		individualProductBonus.setConsumerOpenId(bonusResultRequest.getConsumerOpenId());
		individualProductBonus.setWechatBonusNumber(bonusResultRequest.getWechatBonusNumber());
		individualProductBonus.setMessage(bonusResultRequest.getMessage());
		individualProductBonus.setConsumerAddressProvince(province);
		individualProductBonus.setConsumerAddressCity(city);
		individualProductBonus.setConsumerAddress(recommend);
		individualProductBonus.setConsumerAddressCounty(district);
		individualProductBonus.setConsumerAddressLatitude(latitudeStr);
		individualProductBonus.setConsumerAddressLongtitude(longitudeStr);
		individualProductBonus.setIsConsumed(true);

		individualProductBonusRepository.saveAndFlush(individualProductBonus);
	}

	/**
	 * 通过瓶盖内码获取应得红包金额
	 * 
	 * @param capInnerCode
	 *            瓶盖内码
	 * @return 红包金额
	 */
	private Integer getBonusAmount(String capInnerCode) {
		Integer bonusAmount = 0;
		// 1. 通过capInnerCode获取产品"物料四级分类"的erpCategoryId
		Long erpCategoryId = individualProductBonusRepository.findProductErpCategoryIdByCapInnerCode(capInnerCode);
		LOGGER.debug("erpCategoryId:" + erpCategoryId);
		if (null == erpCategoryId) {
			throw new BonusRuleServiceException(BonusRuleServiceException.INVALID_PRODUCT_ERP_CATEGORY_ID);
		}
		// 2. 通过"物料四级分类"的categoryId从Redis中获取已存在的红包规则
		BonusRule bonusRule = getBonusRuleFromRedis(erpCategoryId);
		// Redis中红包规则对应的红包配置的Id
		Long configId = null == bonusRule ? 0L : bonusRule.getConfigId();
		// 比较该红包规则的获取日期与当前日期，如果和当前日期一致，则使用该红包规则，
		// 否则，取数据库的红包配置，转换为红包规则，并保存在Redis中
		// 当前日期，设置时分秒毫秒为0
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.MILLISECOND, 0);

		// 3. 如果Redis中没有已保存的对应物料分类的规则，或红包规则为之前的日期
		if (null == bonusRule || 0 > bonusRule.getRuleDate().compareTo(currentDate)) {
			// 3.1 取数据库的红包配置，转换为红包规则
			// 如果适用新配置变化，则红包规则内configId已经更新
			bonusRule = getBonusRuleFromDB(erpCategoryId);
			// 3.2 将新红包规则保存在Redis中
			setBonusRuleToRedis(erpCategoryId, bonusRule);
		}

		// 4. 通过红包规则内configId来判断是否为新的红包配置
		if (!configId.equals(bonusRule.getConfigId())) {
			// 4.1 是新的红包配置，通过新的红包规则生成新的红包列表
			List<Integer> bonusList = generateBonusList(bonusRule);
			// 4.2 替换当前使用中的红包列表
			setAppliedBonusList(erpCategoryId, bonusList);
			// 4.3 替换缓冲红包列表
			setBufferBonusList(erpCategoryId, bonusList);
		}

		// 5. 获取当前使用中的红包列表及大小
		List<Integer> appliedBonusList = getAppliedBonusList(erpCategoryId);
		if (CollectionUtils.isEmpty(appliedBonusList)) {
			// 5.1 如果当前使用中的红包列表为空，使用缓冲红包列表替换当前使用中的红包列表
			List<Integer> bufferBonusList = getBufferBonusList(erpCategoryId);
			if (CollectionUtils.isEmpty(bufferBonusList)) {
				bufferBonusList = generateBonusList(bonusRule);
				setBufferBonusList(erpCategoryId, bufferBonusList);
			}
			setAppliedBonusList(erpCategoryId, bufferBonusList);
		}

		// 6. 获取红包列表中第一个红包，并从红包列表中删除该红包。
		appliedBonusList = getAppliedBonusList(erpCategoryId);
		LOGGER.debug("appliedBonusList size:" + appliedBonusList.size());
		bonusAmount = appliedBonusList.get(0);
		appliedBonusList.remove(0);
		LOGGER.debug("saved appliedBonusList size:" + appliedBonusList.size());
		// 7. 将更新后的红包列表保存到Redis
		setAppliedBonusList(erpCategoryId, appliedBonusList);

		LOGGER.debug("capInnerCode:" + capInnerCode + "---bonusAmount:" + bonusAmount);
		return bonusAmount;
	}

	/**
	 * 通过产品类型获取数据库中红包配置并转换问红包规则
	 * 
	 * @param erpCategoryId
	 *            产品类型id
	 * @return 红包规则
	 */
	private BonusRule getBonusRuleFromDB(Long erpCategoryId) {
		BonusDispatchConfig bonusDispatchConfig = bonusDispatchConfigRepository
				.findCurrentValidConfigByErpCategoryId(erpCategoryId);
		BonusRule bonusRule = null;
		if (null != bonusDispatchConfig) {
			bonusRule = new BonusRule(bonusDispatchConfig);
		} else {
			throw new BonusRuleServiceException(BonusRuleServiceException.NO_BONUS_CONFIG);
		}

		return bonusRule;
	}

	/**
	 * 获取Redis中的红包规则
	 * 
	 * @param erpCategoryId
	 *            产品类型
	 * @return 红包规则
	 */
	private BonusRule getBonusRuleFromRedis(Long erpCategoryId) {
		String bonusRuleStr = (String) globalCacheService.get(BONUS_RULE + "_" + erpCategoryId);
		BonusRule bonusRule = JSON.parseObject(bonusRuleStr, BonusRule.class);
		return bonusRule;
	}

	/**
	 * 将红包规则设置到Redis中
	 * 
	 * @param erpCategoryId
	 *            产品类型
	 * @param bonusRule
	 *            红包规则
	 */
	private void setBonusRuleToRedis(Long erpCategoryId, BonusRule bonusRule) {
		String bonusRuleStr = JSON.toJSONString(bonusRule);
		globalCacheService.save(BONUS_RULE + "_" + erpCategoryId, bonusRuleStr);
	}

	/**
	 * 获取Redis中的当前红包列表
	 * 
	 * @param erpCategoryId
	 *            产品类型
	 * @return Redis中当前红包列表
	 */
	private List<Integer> getAppliedBonusList(Long erpCategoryId) {
		String bonusListStr = (String) globalCacheService.get(BONUS_LIST_APPLIED + "_" + erpCategoryId);
		return JSON.parseArray(bonusListStr, Integer.class);
	}

	/**
	 * 将当前应用红包列表设置到Redis中
	 * 
	 *
	 * @param erpCategoryId
	 *            产品类型
	 * @param bonusList
	 *            红包列表
	 */
	private void setAppliedBonusList(Long erpCategoryId, List<Integer> bonusList) {
		String bonusListStr = JSON.toJSONString(bonusList);
		globalCacheService.save(BONUS_LIST_APPLIED + "_" + erpCategoryId, bonusListStr);
	}

	/**
	 * 获取Redis中的缓冲红包列表
	 * 
	 * @param erpCategoryId
	 *            产品类型
	 * @return Redis中缓冲红包列表
	 */
	private List<Integer> getBufferBonusList(Long erpCategoryId) {
		String bonusListStr = (String) globalCacheService.get(BONUS_LIST_BUFFER + "_" + erpCategoryId);
		return JSON.parseArray(bonusListStr, Integer.class);
	}

	/**
	 * 将缓冲红包列表设置到Redis中
	 * 
	 * @param erpCategoryId
	 *            产品类型
	 * @param bonusList
	 *            红包列表
	 */
	private void setBufferBonusList(Long erpCategoryId, List<Integer> bonusList) {
		String bonusListStr = JSON.toJSONString(bonusList);
		globalCacheService.save(BONUS_LIST_BUFFER + "_" + erpCategoryId, bonusListStr);
	}

	/**
	 * 通过BonusRule生成红包列表
	 * 
	 * @param bonusRule
	 *            红包规则
	 * @return 红包列表
	 */
	private List<Integer> generateBonusList(BonusRule bonusRule) {
		List<Integer> bonusList = null;

		if (null != bonusRule && null != bonusRule.getBonusRuleItemList()) {
			bonusList = new ArrayList<Integer>();
			List<BonusRuleItem> bonusRuleItemList = bonusRule.getBonusRuleItemList();
			for (BonusRuleItem bonusRuleItem : bonusRuleItemList) {
				Integer amount = bonusRuleItem.getBonusAmount();
				for (int i = bonusRuleItem.getQuantity(); i-- > 0;) {
					bonusList.add(amount);
				}
			}
		}

		if (CollectionUtils.isEmpty(bonusList)) {
			throw new BonusRuleServiceException(BonusRuleServiceException.GET_BONUS_LIST_ERROR);
		}

		return bonusList;
	}

}
