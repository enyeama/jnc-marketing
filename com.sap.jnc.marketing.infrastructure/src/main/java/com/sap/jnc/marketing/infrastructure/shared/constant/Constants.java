package com.sap.jnc.marketing.infrastructure.shared.constant;

public class Constants {
	/**
	 * 用户登录时参数的分割
	 */
	public static final String LOGINID_DEVICE_LOGINTYPE_SPLIT = "/";

	/**
	 * 时间格式化类型yyyy/MM/dd
	 */
	public static final String DATE_YYYY_MM_DD = "yyyy/MM/dd";

	/**
	 * 作成日期
	 */
	public static final String D_TField_CREATEAT = "createAt";

	public static final String DES_PASSWORD_KEY = "";

	public final static String WECHAT_AUTH_BASE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";

	public final static String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

	public final static String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";

	public final static String USER_INFO_WITH_BASE_SCOPE_URL = "https://api.weixin.qq.com/cgi-bin/user/info";

	public final static String WECHAT_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

	public final static String WECHAT_JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

	public final static String WECHAT_GET_MESSAGE_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/template/api_add_template";

	public final static String WECHAT_SEND_MESSAGE_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send";

	public final static String WECHAT_QRCODE_TICKET_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create";

	public final static String WECHAT_QRCODE_SHOW_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode";

	public final static String WECHAT_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info";

	public final static String WECHAT_MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create";

	public final static String WECHAT_MENU_DELETE = "https://api.weixin.qq.com/cgi-bin/menu/delete";

	public final static String WECHAT_MASS_SEND = "https://api.weixin.qq.com/cgi-bin/message/mass/send";

	// 查账户信息的http地址
	public static final String URI_GET_USER_INFO = "https://sms.yunpian.com/v1/user/get.json";

	// 智能匹配模版发送接口的http地址
	public static final String URI_SEND_SMS = "https://sms.yunpian.com/v1/sms/send.json";

	// 模板发送接口的http地址
	public static final String URI_TPL_SEND_SMS = "https://sms.yunpian.com/v1/sms/tpl_send.json";

	// 发送语音验证码接口的http地址
	public static final String URI_SEND_VOICE = "https://voice.yunpian.com/v1/voice/send.json";

	public static final String ATTENTION_URL = "http://mp.weixin.qq.com/s?__biz=MzI1NzAxMDIxNw==&mid=307983593&idx=1&sn=05f1f02ed0b56e08bd008145487d97cc#rd";

	public static final String LANG = "zh_CN";

	/**
	 * sms success code
	 */
	public static final String SMS_SUCCESS_CODE = "0";

	public static final String ENCODING = "UTF-8";

	public static final String RESPONSE_TYPE = "code";

	public static final String USER_INFO_SCOPE = "snsapi_userinfo";

	public static final String BASE_SCOPE = "snsapi_base";

	public static final String IMG_SHOP_PATH = "shop";

	/**
	 * 更新日期
	 */
	public static final String D_TFIELD_UPDATEAT = "updateAt";

	public static final class StoragePath {
		public static final String PRODUCT_IMG_BIG = "product/big";
		public static final String PRODUCT_IMG_SMALL = "product/small";
		public static final String SAUCE_IMG = "sauce";
		public static final String STAPLE_IMG = "staple";
		public static final String PRODUCT_TYPE_IMG = "product_type";
		public static final String DEFAULT_UPLOAD_IMG_PATH = "upload_img";
	}

	public static final String WELCOME_TEXT = "";

	public static final String WELCOME_TEXT_BACK = "";

	public static final String API_CALLBACK_URL = "api_callback_url";

	public static final String QR_CODE_KEY = "qr_code";

	public static final int COOKIE_EXPIRE_TIME = 1200;

	public static final int CACHE_EXPIRE_TIME = 1800;

	/**
	 * JMS queue for wechat promotion activity
	 */
	public static final String MESSAGE_QUEUE_WECHAT_PROMOTION_ACTIVITY = "MESSAGE_QUEUE_WECHAT_PROMOTION_ACTIVITY";

	public static final class GENDER {

		public static final String MALE = "男";

		public static final String FEMAL = "女";

		public static final String NONE = "未知";
	}

	public static final class SmallWineConstants {
		public static final String EVENT_KEY = "WechatPromotionActivity00001";
		// public static final String EVENT_KEY = "109";

		public static final String ATTENTION_MESSAGE_DESC = "点击领取酒店赠饮！";

		public static final String ATTENTION_MESSAGE_TITEL = "剑南春酒店赠饮";

		public static final String ATTENTION_MESSAGE_IMG_URL = "http://jnctest.javafan.cn/ui/img/promotion_wine.jpg";

		public static final String ATTENTION_MESSAGE_REDIRECT_URL = "http://jnctest.javafan.cn/qrCodeEntrance?api_callback_url=http://jnctest.javafan.cn/ui/promotion/restaurantpromotion.html";
	}

	//测试数据
	public static final Long SALE_ACCOUNT_ID = 13L;
	public static final Long CHANNEL_HOTEL_ID = 11L;
	public static final Long CHANNEL_SHOP_ID = 14L;
	public static final Long TERMINAL_ID = 10000000L;
}