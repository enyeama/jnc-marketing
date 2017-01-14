package com.sap.jnc.marketing.service.jms;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.sap.jnc.marketing.infrastructure.shared.constant.Constants;
import com.sap.jnc.marketing.persistence.model.WechatPromotionActivity;

/**
 * @author Alex
 */
@Service
public class WechatPromotionActivityActivityQueueMessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(WechatPromotionActivityActivityQueueMessageListener.class);

	@SuppressWarnings("rawtypes")
	@JmsListener(destination = Constants.MESSAGE_QUEUE_WECHAT_PROMOTION_ACTIVITY, containerFactory = "jmsListenerContainerFactory")
	public void onMessage(Message message) {
		if (message instanceof ObjectMessage) {
			final ObjectMessage objMessage = (ObjectMessage) message;
			try {
				final WechatPromotionActivity activity = (WechatPromotionActivity) objMessage.getObject();
				LOGGER.debug("received message {} from {}", activity, Constants.MESSAGE_QUEUE_WECHAT_PROMOTION_ACTIVITY);
			}
			catch (final JMSException e) {
				LOGGER.error("error occurred", e);
			}

		}
	}
}
