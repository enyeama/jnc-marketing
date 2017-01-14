package com.sap.jnc.marketing.infrastructure.shared;

/**
 * Created by dy on 16/6/27.
 */
public class GlobalCacheKeys {

    private static final String LATITUDE = "LATITUDE_";

    private static final String LONGITUDE = "LONGITUDE_";

    private static final String PRECISION = "PRECISION_";

    private static final String WECHAT_JSAPI_TICKET = "JSAPI_TICKET_JSON_KEY";

    private static final String CACHE_QR_CODE_KEY = "QR_CODE_KEY_";

    private static final String TRY_BONUS_COUNT = "TRY_BONUS_COUNT_";

    public static String getGlobalLatitudeKey(String openId) {
        return LATITUDE + openId;
    }

    public static String getGlobalLongitudeKey(String openId) {
        return LONGITUDE + openId;
    }

    public static String getGlobalPrecisionKey(String openId) {
        return PRECISION + openId;
    }

    public static String getWechatJsapiTicketKey() {
        return WECHAT_JSAPI_TICKET;
    }

    public static String getCacheQrCodeKey(String openId) {
        return CACHE_QR_CODE_KEY + openId;
    }

    public static String getTryBonusCount(String openId) {
        return TRY_BONUS_COUNT + openId;
    }
}
