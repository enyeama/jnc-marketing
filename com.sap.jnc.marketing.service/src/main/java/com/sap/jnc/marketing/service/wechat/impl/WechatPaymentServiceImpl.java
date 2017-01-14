package com.sap.jnc.marketing.service.wechat.impl;

import com.sap.jnc.marketing.dto.shared.wechat.*;
import com.sap.jnc.marketing.infrastructure.shared.SystemConfig;
import com.sap.jnc.marketing.infrastructure.shared.constant.WechatConstants;
import com.sap.jnc.marketing.infrastructure.shared.utils.wechat.HttpsRequest;
import com.sap.jnc.marketing.infrastructure.shared.utils.wechat.Signature;
import com.sap.jnc.marketing.infrastructure.shared.utils.wechat.XMLParser;
import com.sap.jnc.marketing.service.wechat.WechatPaymentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ying.dong
 */
@Service
public class WechatPaymentServiceImpl implements WechatPaymentService {

	private static Logger LOGGER = LoggerFactory.getLogger(WechatPaymentServiceImpl.class);

	@Autowired
	HttpsRequest httpsRequest;

	@Autowired
	SystemConfig systemConfig;

	@Override
	public WechatTransferResponse transfer(WechatTransferRequest transferRequest) {
		LOGGER.info("transfer params: {}", transferRequest.toString());
		try {
			String sign = Signature.getSign(transferRequest, systemConfig.getShopKey());
			transferRequest.setSign(sign);
			String result = httpsRequest.sendPost(WechatConstants.WECHAT_TRANSFER_URL, transferRequest);
			LOGGER.info("wehcat transfer result: {}", result);
			WechatTransferResponse transferResult = XMLParser.getObjectFromXML(result, WechatTransferResponse.class);
			LOGGER.info("wechat transfer result object: {}", transferResult.toString());
			if (transferResult.getResultCode() != null) {
				return transferResult;
			}
			else {
				throw new RuntimeException("wechat transfer error");
			}
		}
		catch (Exception e) {
			LOGGER.error("transfer error: {}", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public WechatBonusResponse grantBonus(WechatBonusRequest bonusRequest) {
		LOGGER.info("wechat bonus request: {}", bonusRequest.toString());
		try {
			String sign = Signature.getSign(bonusRequest, systemConfig.getShopKey());
			bonusRequest.setSign(sign);
			String result = httpsRequest.sendPost(WechatConstants.WECHAT_GIFT_URL, bonusRequest);
			LOGGER.info("grant wechat bonus result: {}", result);
			WechatBonusResponse bonusResponse = XMLParser.getObjectFromXML(result, WechatBonusResponse.class);
			LOGGER.info("bonusResponse: {}", bonusResponse);
			if (bonusResponse.getResultCode() != null) {
				return bonusResponse;
			} else {
				throw new RuntimeException("error to grant wechat bonus");
			}
		} catch (Exception e) {
			LOGGER.error("error to grant wechat bonus: {}", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public WechatBonusQueryResponse queryBonus(WechatBonusQueryRequest wechatBonusQueryRequest) {
		LOGGER.info("wechat query bonus request: {}", wechatBonusQueryRequest.toString());
		try {
			String sign = Signature.getSign(wechatBonusQueryRequest, systemConfig.getShopKey());
			wechatBonusQueryRequest.setSign(sign);
			String result = httpsRequest.sendPost(WechatConstants.WECHAT_BONUS_QUERY_URL, wechatBonusQueryRequest);
			LOGGER.info("query wechat bonus result: {}", result);
			WechatBonusQueryResponse wechatBonusQueryResponse = XMLParser.getObjectFromXML(result, WechatBonusQueryResponse.class);
			LOGGER.info("WechatBonusQueryResponse: {}", wechatBonusQueryResponse);
			if (wechatBonusQueryResponse.getResultCode() != null) {
				return wechatBonusQueryResponse;
			} else {
				throw new RuntimeException("error to query wechat bonus");
			}
		} catch (Exception e) {
			LOGGER.error("error to query wechat bonus: {}", e);
			throw new RuntimeException(e);
		}
	}
}
