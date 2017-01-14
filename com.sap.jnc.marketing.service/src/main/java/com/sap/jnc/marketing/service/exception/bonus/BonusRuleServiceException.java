/**
 * 
 */
package com.sap.jnc.marketing.service.exception.bonus;

/**
 * @author I323560
 *
 */
public class BonusRuleServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6551684316746889512L;

	public static final String WECHAT_RETURN_ERROR = "微信返回错误";

	public static final String GPS_RETURN_ERROR = "GPS信息返回错误";

	public static final String NO_BONUS_CONFIG = "无对应的红包配置";

	public static final String GET_BONUS_LIST_ERROR = "获取红包列表异常";

	public static final String INVALID_PRODUCT_ERP_CATEGORY_ID = "红包返利表未保存有效物料分类id";

	public BonusRuleServiceException() {
		super();
	}

	public BonusRuleServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public BonusRuleServiceException(String message) {
		super(message);
	}

}
