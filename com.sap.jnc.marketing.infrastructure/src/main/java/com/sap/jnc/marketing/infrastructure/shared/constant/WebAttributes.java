package com.sap.jnc.marketing.infrastructure.shared.constant;

public final class WebAttributes {

	/**
	 * Session attributes
	 */
	// temporary for storing the uncompleted wechat user info, should be cleared once completed
	public static final String TO_BE_COMPLETED_WECHATUSER = "TO_BE_COMPLETED_WECHATUSER_";

	public static final String CAPTCHA_KEY = "CAPTCHA_KEY";

	// 带场景二维码的二维码值
	public static final String QR_SCENE_ID = "QR_SCENE_ID_";

	public static final String WECHAT_ACCESS_TOKEN_JSON_OBJECT = "WECHAT_ACCESS_TOKEN_JSON_OBJECT_";

	// 用户位置
	public static final String WECHAT_LOCATION_INFO = "WECHAT_LOCATION_INFO_";

	// WECHAT OPENID
	public static final String WECHAT_OPEN_ID = "WECHAT_OPEN_ID";
	public static final String REQUEST_URL_WITH_NO_OPEN_ID = "REQUEST_URL";

	public static final String OAUTH_ATTEMPT_COUNT = "OAUTH_ATTEMPT_COUNT";

	/**
	 * Key of access token which stored in ServletContext with JSONObject
	 */
	public final static String CONTEXT_WECHAT_TOKEN = "CONTEXT_WECHAT_TOKEN";

	/**
	 * Key of jsapi token which stored in ServletContext with JSONObject
	 */
	public final static String CONTEXT_WECHAT_TICKET = "CONTEXT_WECHAT_TICKET";

	public final static String APP_GLOBAL_VARIABLE = "APP_GLOBAL_VARIABLE";

	public final static String QR_CODE_KEY = "QR_CODE_KEY";

}
