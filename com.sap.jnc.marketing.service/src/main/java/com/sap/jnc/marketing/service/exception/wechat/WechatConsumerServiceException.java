/**
 * 
 */
package com.sap.jnc.marketing.service.exception.wechat;

/**
 * @author I323560
 *
 */
public class WechatConsumerServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5547170908621786455L;

	public static final String WECHAT_CONSUMER_INFO_ERROR = "微信消费者信息未正确返回";

	public WechatConsumerServiceException() {
		super();
	}

	public WechatConsumerServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public WechatConsumerServiceException(String message) {
		super(message);
	}

}
