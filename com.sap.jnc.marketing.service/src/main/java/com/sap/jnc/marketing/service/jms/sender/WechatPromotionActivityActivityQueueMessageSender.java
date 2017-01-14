package com.sap.jnc.marketing.service.jms.sender;

import com.sap.jnc.marketing.infrastructure.shared.constant.Constants;
import com.sap.jnc.marketing.persistence.model.WechatPromotionActivity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.annotation.PostConstruct;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

/**
 * @author Alex
 */
// @Service
public class WechatPromotionActivityActivityQueueMessageSender {
	private static final Logger LOGGER = LoggerFactory.getLogger(WechatPromotionActivityActivityQueueMessageSender.class);
	@Autowired
	private JmsTemplate jmsTemplate;

	@PostConstruct
	void init() {
		jmsTemplate.execute(session -> {
			Queue queue = session.createQueue(Constants.MESSAGE_QUEUE_WECHAT_PROMOTION_ACTIVITY);
			LOGGER.info("queue {} created", queue.getQueueName());
			return null;
		});
	}

	public void send(WechatPromotionActivity activity) {
		jmsTemplate.convertAndSend(Constants.MESSAGE_QUEUE_WECHAT_PROMOTION_ACTIVITY, (MessageCreator) session -> {
			ObjectMessage objMessage = session.createObjectMessage(activity);
			LOGGER.debug("object {} was sent to {}", activity, Constants.MESSAGE_QUEUE_WECHAT_PROMOTION_ACTIVITY);
			return objMessage;
		});
	}

}
