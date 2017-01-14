package com.sap.jnc.marketing.service.bonus.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.bonus.BonusDispatcherRequest;
import com.sap.jnc.marketing.dto.request.bonus.BonusDispatcherRequest.BonusDispatcherItemRequest;
import com.sap.jnc.marketing.dto.response.bonus.BonusValidityResponse;
import com.sap.jnc.marketing.dto.response.bonus.BonusValidityResponse.BonusValidityType;
import com.sap.jnc.marketing.dto.response.bonus.ProductCategoryResponse;
import com.sap.jnc.marketing.persistence.criteria.dealer.BonusConfigAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Amount;
import com.sap.jnc.marketing.persistence.model.AverageAmount;
import com.sap.jnc.marketing.persistence.model.BonusDispatchConfig;
import com.sap.jnc.marketing.persistence.model.BonusDispatchConfigItem;
import com.sap.jnc.marketing.persistence.model.ProductErpCategory;
import com.sap.jnc.marketing.persistence.model.ValidityPeriod;
import com.sap.jnc.marketing.persistence.model.VarianceAmount;
import com.sap.jnc.marketing.persistence.repository.BonusDispatchConfigItemRepository;
import com.sap.jnc.marketing.persistence.repository.BonusDispatchConfigRepository;
import com.sap.jnc.marketing.persistence.repository.ProductErpCategoryRepository;
import com.sap.jnc.marketing.service.bonus.BonusConfigureService;

@Service
@Transactional
public class BonusConfigureServiceImpl implements BonusConfigureService {

	@Autowired
	private ProductErpCategoryRepository productErpCategoryRepository;

	@Autowired
	private BonusDispatchConfigRepository bonusDispatchConfigRepository;

	@Autowired
	private BonusDispatchConfigItemRepository bonusDispatchConfigItemRepository;

	private static final String END_DATE = "9999-12-31";
	private static final String TEMPLATE_DATE = "yyyy-MM-dd";
	private static final SimpleDateFormat sdf = new SimpleDateFormat(TEMPLATE_DATE);

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategoryResponse> listProductCategories() {
		final List<ProductErpCategory> categoriesList = this.productErpCategoryRepository.findAllFourthCategory();
		final List<ProductCategoryResponse> categoryResponseList = new ArrayList<ProductCategoryResponse>();
		if (!CollectionUtils.isEmpty(categoriesList)) {
			for (final ProductErpCategory category : categoriesList) {
				final ProductCategoryResponse categoryResponse = new ProductCategoryResponse();
				categoryResponse.setId(category.getId());
				categoryResponse.setCategoryName(category.getCategoryName());
				categoryResponse.setCategoryId(category.getCategoryId());
				categoryResponseList.add(categoryResponse);
			}
		}
		return categoryResponseList;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<BonusDispatchConfig> advanceSearch(BonusConfigAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return this.bonusDispatchConfigRepository.advanceSearch(searchCriteria, pageRequest);
	}

	@Override
	@Transactional(readOnly = false)
	public BonusValidityResponse addBonusDispatcher(BonusDispatcherRequest request) {
		try {
			BonusValidityResponse validFromLegal = isValidFromLegal(request);
			Calendar currentCal = Calendar.getInstance();
			currentCal.add(Calendar.DATE, 1);
			Date currentDate = currentCal.getTime();
			String currentTime = sdf.format(currentDate);
			// ValidFrom验证成功进行逻辑处理
			switch (validFromLegal.getBonusValidityType()) {
			case OK_GT_CURRENT_TIME_AND_LAST_VALIDFROM:
				BonusDispatchConfig lastOne = bonusDispatchConfigRepository.findLastBonusByCategoryId(request.getProductErpCategoryId());
				// 表单时间等于系统时间
				if (currentTime.equals(request.getValidFrom().trim())) {
					Calendar cd = Calendar.getInstance();
					cd.setTime(currentDate);
					lastOne.getValidityPeriod().setValidTo(cd); // 设置当前系统时间为上次失效时间
				}
				else {// 表单时间大于系统时间+1
					Calendar cd = Calendar.getInstance();
					cd.setTime(sdf.parse(request.getValidFrom()));
					cd.add(Calendar.DATE, -1); // 设置当前时间前一天为上次失效时间
					lastOne.getValidityPeriod().setValidTo(cd);
				}

				saveBonusConfigure(request);

				break;
			case OK_GT_CURRENT_TIME_AND_NO_HISTORY:
				saveBonusConfigure(request);
				break;

			default:
				break;
			}
			return validFromLegal;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 保存新建红包配置逻辑
	 * 
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	private BonusDispatchConfig saveBonusConfigure(BonusDispatcherRequest request) throws Exception {
		final BonusDispatchConfig bonusDispatchConfig = new BonusDispatchConfig();

		// ValidityPeriod
		final ValidityPeriod validityPeriod = new ValidityPeriod();

		final Calendar validFrom = Calendar.getInstance();
		validFrom.setTime(sdf.parse(request.getValidFrom()));
		validityPeriod.setValidFrom(validFrom);

		final Calendar validTo = Calendar.getInstance();
		if (StringUtils.isBlank(request.getValidTo())) {
			validTo.setTime(sdf.parse(END_DATE));
		}
		else {
			validTo.setTime(sdf.parse(request.getValidTo()));
		}
		validityPeriod.setValidTo(validTo);

		bonusDispatchConfig.setValidityPeriod(validityPeriod);

		// AverageAmount
		final AverageAmount averageAmount = new AverageAmount();
		averageAmount.setCurrency(BonusDispatcherRequest.AMOUNTCURRENCY);
		averageAmount.setValue(new BigDecimal(request.getAverageAmount()).setScale(2, RoundingMode.HALF_UP));
		bonusDispatchConfig.setAverageAmount(averageAmount);

		// VarianceAmount
		final VarianceAmount varianceAmount = new VarianceAmount();
		varianceAmount.setCurrency(BonusDispatcherRequest.AMOUNTCURRENCY);
		varianceAmount.setValue(new BigDecimal(request.getVarianceAmount()).setScale(2, RoundingMode.HALF_UP));
		bonusDispatchConfig.setVarianceAmount(varianceAmount);
		request.getVarianceAmount();

		// CNY基数
		bonusDispatchConfig.setCalculatedBaseNumber(request.getCalculatedBaseNumber());

		if(request.getProductErpCategoryId() == null) {
			throw new Exception("产品类型ID不合法！");
		}
		// ProductErpCategory
		final ProductErpCategory productErpCategory = this.productErpCategoryRepository.findOne(request.getProductErpCategoryId());
		if (productErpCategory != null) {
			bonusDispatchConfig.setErpCategory(productErpCategory);
		}
		else {
			throw new Exception("产品类型不存在！");
		}

		// Item列表
		final List<BonusDispatcherRequest.BonusDispatcherItemRequest> items = request.getBonusDispatcherItemRequests();
		final List<BonusDispatchConfigItem> pItems = new ArrayList<BonusDispatchConfigItem>();
		for (final BonusDispatcherItemRequest item : items) {
			final BonusDispatchConfigItem bonusDispatchConfigItem = new BonusDispatchConfigItem();

			// Amount
			final Amount amount = new Amount();
			amount.setCurrency(BonusDispatcherRequest.AMOUNTCURRENCY);
			amount.setValue(new BigDecimal(item.getNumber()));
			bonusDispatchConfigItem.setAmount(amount);

			// Percentage
			bonusDispatchConfigItem.setPercentage(new BigDecimal(item.getPercentage() + ""));

			this.bonusDispatchConfigItemRepository.save(bonusDispatchConfigItem);
			bonusDispatchConfigItem.setConfig(bonusDispatchConfig);
			pItems.add(bonusDispatchConfigItem);

		}

		bonusDispatchConfig.setItems(pItems);
		BonusDispatchConfig newlyBonusDispatchConfig = this.bonusDispatchConfigRepository.save(bonusDispatchConfig);
		return newlyBonusDispatchConfig;
	}

	@Override
	public BonusValidityResponse isValidFromLegal(BonusDispatcherRequest request) {
		try {
			BonusValidityResponse validityResponse = new BonusValidityResponse();
			if (StringUtils.isBlank(request.getValidFrom())) {
				validityResponse.setBonusValidityType(BonusValidityType.ERROR_EMPTY_VALIDFROM);
				return validityResponse;
			}
			// 表单填写生效时间
			Calendar newlyCalendar = Calendar.getInstance();
			newlyCalendar.setTime(sdf.parse(request.getValidFrom()));
			// 系统当前时间Day
			Calendar currentCal = Calendar.getInstance();

			// 比较表单生效时间与系统当前时间Day+1
			if (newlyCalendar.compareTo(currentCal) > 0) {
				Long erpCategoryId = request.getProductErpCategoryId();
				BonusDispatchConfig lastOne = bonusDispatchConfigRepository.findLastBonusByCategoryId(erpCategoryId);
				// 本产品类型是否存在最近一次新增红包配置的生效时间
				if (lastOne != null && lastOne.getValidityPeriod() != null && lastOne.getValidityPeriod().getValidFrom() != null) {
					// 比较表单生效时间和本产品类型最近一次新增红包配置的生效时间
					if (newlyCalendar.compareTo(lastOne.getValidityPeriod().getValidFrom()) > 0) {
						validityResponse.setBonusValidityType(BonusValidityType.OK_GT_CURRENT_TIME_AND_LAST_VALIDFROM);
					}
					else {
						validityResponse.setBonusValidityType(BonusValidityType.FAILURE_LT_LAST_VALIDFROM);
					}
				}
				else { // 历史生效时间不存在
					validityResponse.setBonusValidityType(BonusValidityType.OK_GT_CURRENT_TIME_AND_NO_HISTORY);
				}
			}
			else if (newlyCalendar.compareTo(currentCal) < 0) {
				validityResponse.setBonusValidityType(BonusValidityType.FAILURE_LT_CURRENT_TIME);
			}
			return validityResponse;

		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
