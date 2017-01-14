package com.sap.jnc.marketing.service.wechat;

import com.sap.jnc.marketing.dto.response.wechat.JSSDKConfigResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * @author Alex
 */
public interface WechatService {

	WxMpService getWxMpService();

	WxMpMessageRouter getWxMpMessageRouter();

	WxMpInMemoryConfigStorage getConfig();

	String buildAccessCodeURL(String scope);

	WxMpOAuth2AccessToken retrieveAccessToken(String code);

	WxMpUser retrieveUserInfo(WxMpOAuth2AccessToken token, String openId);

    WxMpUser retrieveUserInfoSilently(String openId) throws WxErrorException ;

    String getAccessToken();

	String oauthLogin();

	void messageHandle(HttpServletRequest request, HttpServletResponse response, String timestamp, String nonce) throws IOException;

	void sendTemplateMessageForRecharge(String toUserOpenId, BigDecimal price, Date rechargeTime);

	void createMenu();

	JSSDKConfigResponse jsWxConfig(String url);

}
