/**
 * 
 */
package com.sap.jnc.marketing.persistence.exception.bonus;

/**
 * Exception for BonusDispatchConfigRepository
 * 
 * @author I323560
 *
 */
public class BonusDispatchConfigRepositoryException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2629312554928914240L;

	public static final String GET_BONUS_CONFIG_ERROR = "获取红包配置错误： 应该获取一个记录，实际大于一条记录";

	public static final String DATETIME_PARSE_ERROR = "日期转换错误";

	public BonusDispatchConfigRepositoryException() {
		super();
	}

	public BonusDispatchConfigRepositoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public BonusDispatchConfigRepositoryException(String message) {
		super(message);
	}

}
