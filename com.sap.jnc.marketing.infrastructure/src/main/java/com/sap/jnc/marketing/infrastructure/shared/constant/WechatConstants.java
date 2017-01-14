package com.sap.jnc.marketing.infrastructure.shared.constant;

/**
 * Created by ying.dong on 3/18/16.
 */
public class WechatConstants {

	public static final String WECHAT_STATUS_SUCCESS = "SUCCESS";

	public final static String WECHAT_AUTH_BASE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
	// wechat pay
	public static final String WECHAT_PRE_PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// wechat payment query
	public static final String WECHAT_PAYMENT_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

	public final static String WECHAT_GIFT_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";

	public final static String WECHAT_TRANSFER_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

	public final static String WECHAT_BONUS_QUERY_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo";

	public final static String WECHAT_BONUS_QUERY_TYPE = "MCHT";

	public final static Integer WECHAT_ACTIVITY_TOTAL_DEFAULT = 1;

	public final static class WechatNameCheckOption {

		public final static String NO_CHECK = "NO_CHECK";

		public final static String FORCE_CHECK = "FORCE_CHECK";

		public final static String OPTION_CHECK = "OPTION_CHECK";
	}
}
