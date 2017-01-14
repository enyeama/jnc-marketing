/**
 * 
 */
package com.sap.jnc.marketing.service.bonus;

import com.sap.jnc.marketing.dto.request.bonus.WechatConsumerRequest;

/**
 * 消费者微信信息服务
 * 
 * @author I323560
 */
public interface WechatConsumerService {

	/**
	 * 保存消费者微信信息
	 * 
	 * @param wechatConsumer 消费者微信信息
	 */
	public void setWechatConsumer(WechatConsumerRequest wechatConsumer);

}
