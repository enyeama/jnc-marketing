package com.sap.jnc.marketing.service.payment.impl;

import com.sap.jnc.marketing.dto.request.bonus.BonusRequest;
import com.sap.jnc.marketing.dto.request.bonus.BonusResultRequest;
import com.sap.jnc.marketing.dto.request.transfer.TransferRequest;
import com.sap.jnc.marketing.dto.response.bonus.BonusQueryResponse;
import com.sap.jnc.marketing.dto.response.bonus.BonusVerificationResponse;
import com.sap.jnc.marketing.dto.shared.wechat.*;
import com.sap.jnc.marketing.infrastructure.shared.ActivityConfig;
import com.sap.jnc.marketing.infrastructure.shared.GlobalCacheKeys;
import com.sap.jnc.marketing.infrastructure.shared.SystemConfig;
import com.sap.jnc.marketing.infrastructure.shared.constant.WechatConstants;
import com.sap.jnc.marketing.infrastructure.shared.utils.OrderNoGenerator;
import com.sap.jnc.marketing.infrastructure.shared.utils.wechat.RandomStringGenerator;
import com.sap.jnc.marketing.service.bonus.BonusRuleService;
import com.sap.jnc.marketing.service.exception.CommonBusinessException;
import com.sap.jnc.marketing.service.globalcache.GlobalCacheService;
import com.sap.jnc.marketing.service.payment.PaymentService;
import com.sap.jnc.marketing.service.wechat.WechatPaymentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by ying.dong on 16/3/24.
 */
@Service
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	WechatPaymentService wechatPaymentService;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	GlobalCacheService globalCacheService;

	@Autowired
	BonusRuleService bonusRuleService;

	@Autowired
	ActivityConfig activityConfig;

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Override
	@Transactional
	public Boolean transfer(TransferRequest transferRequest) {
		// check verify code
//		verify(transferRequest.getUserOpenId(), transferRequest.getVerifyCode());
		WechatTransferRequest transferParams = new WechatTransferRequest();
		// TODO set amount
//		transferParams.setAmount(100);
		// transferParams.setAmount(transferRequest.getAmount());
		transferParams.setMchAppid(systemConfig.getAppid());
		transferParams.setMchid(systemConfig.getShopId());
		transferParams.setNonceStr(RandomStringGenerator.getRandomString());
		transferParams.setPartnerTradeNo(OrderNoGenerator.generate());
		transferParams.setOpenid(transferRequest.getUserOpenId());
		transferParams.setSpbillCreateIp(transferRequest.getClientIp());
		transferParams.setCheckName(WechatConstants.WechatNameCheckOption.NO_CHECK);
		if (StringUtils.isNotBlank(transferRequest.getRealUserName())) {
			transferParams.setReUserName(transferRequest.getRealUserName());
		}
		transferParams.setDesc(transferRequest.getDescription());
		WechatTransferResponse wechatTransferResponse = wechatPaymentService.transfer(transferParams);
		if (wechatTransferResponse.getReturnCode().equals(WechatConstants.WECHAT_STATUS_SUCCESS) && wechatTransferResponse.equals(
			WechatConstants.WECHAT_STATUS_SUCCESS)) {
			LOGGER.debug("send wechat gift success");
			// delete qrCode
			globalCacheService.delKey(transferParams.getOpenid());
			// TODO set verifyCode status (used)
			// TODO save verifyCode used record
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public Float grantBonus(BonusRequest bonusRequest) {
		//validate location first
		validateUserLocationPrivilegeGranted(bonusRequest.getOpenId());
		String qrCode = globalCacheService.get(GlobalCacheKeys.getCacheQrCodeKey(bonusRequest.getOpenId())).toString();
		LOGGER.debug("qrCode in redis: {}", qrCode);
		BonusVerificationResponse verificationResponse = bonusRuleService.getBonusVerification(qrCode, bonusRequest.getVerifyCode());
		LOGGER.debug("qrCode verification: {}", verificationResponse.toString());
		WechatBonusRequest wechatBonusRequest = new WechatBonusRequest();
		if (!verificationResponse.isVerified()) {
			Long tryCount = globalCacheService.increBy(GlobalCacheKeys.getTryBonusCount(bonusRequest.getOpenId()), 1L);
			if (tryCount > 5) {
				throw new CommonBusinessException("e.ex.mm.1002");
			}
			//TODO throw inviliad verify code exception
			LOGGER.error("verification code invalid!");
//			throw new RuntimeException("verification code invalid!");
			throw new CommonBusinessException(CommonBusinessException.ErrorCodeBuilder.addErrorCode("e.ex.mm.1001"));
		}
		String openId = bonusRequest.getOpenId();
		wechatBonusRequest.setTotalAmount(verificationResponse.getBonusAmount());
		wechatBonusRequest.setActName(activityConfig.getActivityName());
		wechatBonusRequest.setClientIp(bonusRequest.getClientIp());
		wechatBonusRequest.setReOpenid(openId);
		wechatBonusRequest.setRemark(activityConfig.getActivityRemark());
		wechatBonusRequest.setSendName(activityConfig.getActivityShopName());
		wechatBonusRequest.setWishing(activityConfig.getActivityWish());
		wechatBonusRequest.setTotalNum(WechatConstants.WECHAT_ACTIVITY_TOTAL_DEFAULT);
		wechatBonusRequest.setMchId(systemConfig.getShopId());
		wechatBonusRequest.setMchBillno(OrderNoGenerator.generate());
		wechatBonusRequest.setWxappid(systemConfig.getAppid());
		wechatBonusRequest.setNonceStr(RandomStringGenerator.getRandomString());
		WechatBonusResponse wechatBonusResponse = wechatPaymentService.grantBonus(wechatBonusRequest);
		if (!wechatBonusResponse.getReturnCode().equals(WechatConstants.WECHAT_STATUS_SUCCESS)
				|| wechatBonusResponse.equals(WechatConstants.WECHAT_STATUS_SUCCESS)) {
			//TODO throw json Exception error message
			throw new RuntimeException("error send wechat bonus");
		}
		LOGGER.debug("send wechat bonus success");
		//insert bonus record
		BonusResultRequest bonusResultRequest = getBonusResultRequest(wechatBonusResponse, qrCode);
		LOGGER.debug("insert bonus result: {}", bonusRequest.toString());
		try {
			bonusRuleService.setBonusResult(bonusResultRequest);
			globalCacheService.delKey(GlobalCacheKeys.getGlobalLatitudeKey(openId));
			globalCacheService.delKey(GlobalCacheKeys.getGlobalLongitudeKey(openId));
			globalCacheService.delKey(GlobalCacheKeys.getGlobalPrecisionKey(openId));
			globalCacheService.delKey(GlobalCacheKeys.getCacheQrCodeKey(openId));
		} catch (RuntimeException e) {
			LOGGER.error("error to save bonus", e);
			throw new CommonBusinessException(e.getMessage(), e);
		}
		return (Float.parseFloat(wechatBonusResponse.getTotalAmount()) / 100);
	}

	@Override
	public BonusQueryResponse query(String orderNo) {
		WechatBonusQueryRequest wechatBonusQueryRequest = new WechatBonusQueryRequest();
		wechatBonusQueryRequest.setAppid(systemConfig.getAppid());
		wechatBonusQueryRequest.setMchId(systemConfig.getShopId());
		wechatBonusQueryRequest.setBillType(WechatConstants.WECHAT_BONUS_QUERY_TYPE);
		wechatBonusQueryRequest.setNonceStr(RandomStringGenerator.getRandomString());
		wechatBonusQueryRequest.setMchBillno(orderNo);
		WechatBonusQueryResponse wechatBonusQueryResponse = wechatPaymentService.queryBonus(wechatBonusQueryRequest);
		if (!wechatBonusQueryResponse.getReturnCode().equals(WechatConstants.WECHAT_STATUS_SUCCESS)
				|| !wechatBonusQueryResponse.equals(WechatConstants.WECHAT_STATUS_SUCCESS)) {
			throw new RuntimeException("error to query wechat bonus with orderNo: " + orderNo);
		}
		return parseToQueryResponse(wechatBonusQueryResponse);
	}

	private void validateUserLocationPrivilegeGranted(String openId) {
		Double latitude = (Double) globalCacheService.get(GlobalCacheKeys.getGlobalLatitudeKey(openId));
		Double longitude = (Double) globalCacheService.get(GlobalCacheKeys.getGlobalLongitudeKey(openId));
		if (latitude == null || longitude == null) {
			//TODO throw exception
			LOGGER.error("wechat user not grant location privilege");
			throw new CommonBusinessException("e.ex.mm.1003");
		}
	}

	private BonusQueryResponse parseToQueryResponse(WechatBonusQueryResponse wechatBonusQueryResponse) {
		//https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=13_7&index=6
		BonusQueryResponse bonusQueryResponse = new BonusQueryResponse();
		bonusQueryResponse.setMchBillno(wechatBonusQueryResponse.getMchBillno());
		bonusQueryResponse.setMchId(wechatBonusQueryResponse.getMchId());
		bonusQueryResponse.setDetailId(wechatBonusQueryResponse.getDetailId());
		bonusQueryResponse.setOpenId(wechatBonusQueryResponse.getOpenid());
		bonusQueryResponse.setRcvTime(wechatBonusQueryResponse.getRcvTime());
		bonusQueryResponse.setStatus(wechatBonusQueryResponse.getStatus());
		return bonusQueryResponse;
	}

	private BonusResultRequest getBonusResultRequest(WechatBonusResponse wechatBonusResponse, String qrCode) {
		String openId = wechatBonusResponse.getReOpenid();
		Double latitude = (Double)globalCacheService.get(GlobalCacheKeys.getGlobalLatitudeKey(openId));
		Double longitude = (Double)globalCacheService.get(GlobalCacheKeys.getGlobalLongitudeKey(openId));
		BonusResultRequest bonusResultRequest = new BonusResultRequest();
		bonusResultRequest.setActivityName(activityConfig.getActivityName());
		bonusResultRequest.setConsumerOpenId(openId);
		bonusResultRequest.setLatitude(new BigDecimal(latitude));
		bonusResultRequest.setLongitude(new BigDecimal(longitude));
		bonusResultRequest.setMessage(activityConfig.getActivityWish());
		bonusResultRequest.setWechatBonusNumber(wechatBonusResponse.getMchBillno());
		bonusResultRequest.setWechatPaymentAccountId(wechatBonusResponse.getMchId());
		bonusResultRequest.setWechatSubscriptionAccountAppId(wechatBonusResponse.getWxappid());
		bonusResultRequest.setCapInnerCode(qrCode);
		return bonusResultRequest;
	}
}
