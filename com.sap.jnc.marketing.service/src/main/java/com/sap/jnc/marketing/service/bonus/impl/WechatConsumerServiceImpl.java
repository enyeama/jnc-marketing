package com.sap.jnc.marketing.service.bonus.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.bonus.WechatConsumerRequest;
import com.sap.jnc.marketing.persistence.model.Consumer;
import com.sap.jnc.marketing.persistence.repository.ConsumerRepository;
import com.sap.jnc.marketing.service.bonus.WechatConsumerService;
import com.sap.jnc.marketing.service.exception.wechat.WechatConsumerServiceException;

/**
 * 消费者微信信息服务
 * 
 * @author I323560
 */
@Service
@Transactional
public class WechatConsumerServiceImpl implements WechatConsumerService {

	@Autowired
	private ConsumerRepository consumerRepository;

	@Override
	@Transactional
	public void setWechatConsumer(WechatConsumerRequest wechatConsumerRequest) {
		if (null == wechatConsumerRequest || StringUtils.isBlank(wechatConsumerRequest.getOpenId())) {
			throw new WechatConsumerServiceException(WechatConsumerServiceException.WECHAT_CONSUMER_INFO_ERROR);
		}

		Consumer consumer = consumerRepository.findOne(wechatConsumerRequest.getOpenId());
		if (null == consumer) {
			consumer = new Consumer();
			consumer.setId(wechatConsumerRequest.getOpenId());
		}

		consumer.setFollowStatus(wechatConsumerRequest.getFollowStatus());
		consumer.setFollowTime(wechatConsumerRequest.getFollowTime());
		consumer.setNickName(wechatConsumerRequest.getNickName());
		consumer.setGender(wechatConsumerRequest.getGender());
		consumer.setCountry(wechatConsumerRequest.getCountry());
		consumer.setProvince(wechatConsumerRequest.getProvince());
		consumer.setCity(wechatConsumerRequest.getCity());
		consumer.setLanguage(wechatConsumerRequest.getLanguage());

		consumerRepository.saveAndFlush(consumer);
	}

}
