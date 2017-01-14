package com.sap.jnc.marketing.service.payment.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.payment.PaymentAccountConfigRequest;
import com.sap.jnc.marketing.dto.response.payment.PaymentAccountValidityResponse;
import com.sap.jnc.marketing.dto.response.payment.PaymentAccountValidityResponse.PaymentAccountValidityType;
import com.sap.jnc.marketing.persistence.criteria.payment.PaymentAccountConfigAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.PaymentAccountConfig;
import com.sap.jnc.marketing.persistence.model.ValidityPeriod;
import com.sap.jnc.marketing.persistence.repository.PaymentAccountConfigRepository;
import com.sap.jnc.marketing.service.payment.PaymentAccountConfigService;

@Service
@Transactional
public class PaymentAccountConfigServiceImpl implements PaymentAccountConfigService {

	@Autowired
	private PaymentAccountConfigRepository paymentAccountConfigRepository;

	private static final String END_DATE = "9999-12-31";
	private static final String DEFAULT_PATTERN = "yyyy-MM-dd";
	private static final SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);

	@Override
	@Transactional(readOnly = true)
	public Page<PaymentAccountConfig> advanceSearch(PaymentAccountConfigAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return paymentAccountConfigRepository.advanceSearch(searchCriteria, pageRequest);
	}

	@Override
	public PaymentAccountValidityResponse isValidFromLegal(PaymentAccountConfigRequest request) {
		try {
			PaymentAccountValidityResponse validityResponse = new PaymentAccountValidityResponse();
			if (StringUtils.isBlank(request.getValidFrom())) {
				validityResponse.setPaymentAccountValidityType(PaymentAccountValidityType.ERROR_EMPTY_VALIDFROM);
				return validityResponse;
			}
			// 表单填写生效时间
			Calendar newlyCalendar = Calendar.getInstance();
			newlyCalendar.setTime(sdf.parse(request.getValidFrom()));
			// 系统当前时间Day
			Calendar currentCal = Calendar.getInstance();

			// 比较表单生效时间与系统当前时间Day
			if (newlyCalendar.compareTo(currentCal) > 0) {
				Long defaultAccountId = request.getDefaultAccountId();
				String defaultAccountOpenId = request.getDefaultAccountOpenId();
				PaymentAccountConfig lastOne = paymentAccountConfigRepository.findLastPaymentConfigByPaymentAccountIdAndPaymentAccountOpenId(
					defaultAccountId, defaultAccountOpenId);
				// 本账号是否存在最近一次新增支付账户配置的生效时间
				if (lastOne != null && lastOne.getValidityPeriod() != null && lastOne.getValidityPeriod().getValidFrom() != null) {
					// 比较表单生效时间和最近一次新增支付账户配置的生效时间
					if (newlyCalendar.compareTo(lastOne.getValidityPeriod().getValidFrom()) > 0) {
						validityResponse.setPaymentAccountValidityType(PaymentAccountValidityType.OK_GT_CURRENT_TIME_AND_LAST_VALIDFROM);
					}
					else {
						validityResponse.setPaymentAccountValidityType(PaymentAccountValidityType.FAILURE_LT_LAST_VALIDFROM);
					}
				}
				else { // 历史生效时间不存在
					validityResponse.setPaymentAccountValidityType(PaymentAccountValidityType.OK_GT_CURRENT_TIME_AND_NO_HISTORY);
				}
			}
			else if (newlyCalendar.compareTo(currentCal) < 0) {
				validityResponse.setPaymentAccountValidityType(PaymentAccountValidityType.FAILURE_LT_CURRENT_TIME);
			}
			return validityResponse;

		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public PaymentAccountValidityResponse addPaymentAccountConfig(PaymentAccountConfigRequest request) {
		try {
			PaymentAccountValidityResponse validFromLegal = isValidFromLegal(request);
			Calendar currentCal = Calendar.getInstance();
			currentCal.add(Calendar.DATE, 1);
			Date currentDate = currentCal.getTime();
			String currentTime = sdf.format(currentDate);
			// ValidFrom验证成功进行逻辑处理
			switch (validFromLegal.getPaymentAccountValidityType()) {

			case OK_GT_CURRENT_TIME_AND_LAST_VALIDFROM:
				Long defaultAccountId = request.getDefaultAccountId();
				String defaultAccountOpenId = request.getDefaultAccountOpenId();
				PaymentAccountConfig lastOne = paymentAccountConfigRepository.findLastPaymentConfigByPaymentAccountIdAndPaymentAccountOpenId(//
					defaultAccountId, defaultAccountOpenId);
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

				savePaymentAccountConfig(request);

				break;
			case OK_GT_CURRENT_TIME_AND_NO_HISTORY:
				savePaymentAccountConfig(request);
				break;

			default:
				break;
			}
			return validFromLegal;

		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private PaymentAccountConfig savePaymentAccountConfig(PaymentAccountConfigRequest request) throws ParseException {
		PaymentAccountConfig config = new PaymentAccountConfig();
		config.setDefaultAccountId(request.getDefaultAccountId());
		config.setDefaultAccountOpenId(request.getDefaultAccountOpenId());

		// ValidityPeriod
		ValidityPeriod validityPeriod = new ValidityPeriod();

		Calendar validFrom = Calendar.getInstance();
		validFrom.setTime(sdf.parse(request.getValidFrom()));
		validityPeriod.setValidFrom(validFrom);

		Calendar validTo = Calendar.getInstance();

		validTo.setTime(sdf.parse(END_DATE));

		validityPeriod.setValidTo(validTo);

		config.setValidityPeriod(validityPeriod);

		PaymentAccountConfig paymentAccountCfg = paymentAccountConfigRepository.saveAndFlush(config);
		return paymentAccountCfg;
	}

}
