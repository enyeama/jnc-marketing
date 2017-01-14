package com.sap.jnc.marketing.service.wechat.impl;

import com.sap.jnc.marketing.dto.enumeration.wechat.WechatEventType;
import com.sap.jnc.marketing.dto.enumeration.wechat.WechatMessageType;
import com.sap.jnc.marketing.dto.response.wechat.JSSDKConfigResponse;
import com.sap.jnc.marketing.infrastructure.shared.ActivityConfig;
import com.sap.jnc.marketing.infrastructure.shared.GlobalCacheKeys;
import com.sap.jnc.marketing.infrastructure.shared.SystemConfig;
import com.sap.jnc.marketing.infrastructure.shared.constant.Constants;
import com.sap.jnc.marketing.infrastructure.shared.utils.wechat.HttpsRequest;
import com.sap.jnc.marketing.infrastructure.shared.utils.wechat.RandomStringGenerator;
import com.sap.jnc.marketing.infrastructure.shared.utils.wechat.Signature;
import com.sap.jnc.marketing.service.exception.wechat.WechatTemplateMessageException;
import com.sap.jnc.marketing.service.globalcache.GlobalCacheService;
import com.sap.jnc.marketing.service.wechat.WechatService;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.SimpleGetRequestExecutor;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.WxMpTemplateMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Service
public class WechatServiceImpl implements WechatService {

	private static Logger LOGGER = LoggerFactory.getLogger(WechatServiceImpl.class);

	private static final String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

	public static final String JSAPI_TICKET_JSON = "JSAPI_TICKET_JSON";

	/*
	 * @Value("${wechat.template.platform.received.id}") private String wechatTemplatePlatformReceivedId;
	 * @Value("${wechat.template.vip.card.activation}") private String wechatTemplateVipCardActivation;
	 * @Value("${wechat.template.paid.id}") private String wechatTemplatePaidId;
	 * @Value("${wechat.template.recharge.id}")
	 */
	private String wechatTemplateRechargeId;

	@Autowired
	HttpsRequest httpsRequest;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	ActivityConfig activityConfig;

	@Autowired
	private GlobalCacheService globalCacheService;

	private WxMpService wxMpService = new WxMpServiceImpl();
	private WxMpMessageRouter wxMpMessageRouter = new WxMpMessageRouter(wxMpService);
	private WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();

	@PostConstruct
	protected void init() {
		config.setAppId(systemConfig.getAppid()); // 设置微信公众号的appid
		config.setSecret(systemConfig.getSecret()); // 设置微信公众号的app corpSecret
		config.setToken(systemConfig.getAuth_token()); // 设置微信公众号的token
		config.setAesKey(systemConfig.getEncodingaeskey()); // 设置微信公众号的EncodingAESKey
		wxMpService.setWxMpConfigStorage(config);
	}

	@Override
	public WxMpService getWxMpService() {
		return wxMpService;
	}

	@Override
	public WxMpMessageRouter getWxMpMessageRouter() {
		return wxMpMessageRouter;
	}

	@Override
	public WxMpInMemoryConfigStorage getConfig() {
		return config;
	}

	@Override
	public String buildAccessCodeURL(String scope) {
		String requestURL = null;
		try {
			requestURL = new URIBuilder(Constants.WECHAT_AUTH_BASE_URL).addParameter("appid", systemConfig.getAppid()).addParameter("redirect_uri",
					systemConfig.getRedirectURI()).addParameter("response_type", Constants.RESPONSE_TYPE).addParameter("scope", scope).toString()
					+ "#wechat_redirect";
		}
		catch (Exception e) {
			LOGGER.error("Error occured while building request url", e);
		}
		return requestURL;
	}

	/**
	 * 用户授权机制所需token
	 *
	 * @param code
	 * @return
	 */
	@Override
	public WxMpOAuth2AccessToken retrieveAccessToken(String code) {
		try {
			return wxMpService.oauth2getAccessToken(code);
		}
		catch (WxErrorException e) {
			LOGGER.error("oauth error", e);
		}
		return null;
	}

	@Override
	public WxMpUser retrieveUserInfo(WxMpOAuth2AccessToken token, String openId) {
		try {
			return wxMpService.oauth2getUserInfo(token, openId);
		}
		catch (WxErrorException e) {
			e.printStackTrace();
			return null;
		}
	}

    /**
     * Use this method to retrieve user info without authorization page, note that the precondition is
     * user has subscribed this Public Account!
     * @param openId
     * @return
     * @throws WxErrorException
     */
    @Override
    public WxMpUser retrieveUserInfoSilently(String openId) throws WxErrorException {
        String url = Constants.USER_INFO_WITH_BASE_SCOPE_URL;//"https://api.weixin.qq.com/cgi-bin/user/info";
        String accessToken = wxMpService.getAccessToken();
        String responseContent = wxMpService.execute(new SimpleGetRequestExecutor(), url, "access_token" + accessToken + "openid=" + openId + "&lang=" + "zh_CN");
        return WxMpUser.fromJson(responseContent);
    }

	/**
	 * 获取access_token
	 *
	 * @return
	 */
	@Override
	public String getAccessToken() {
		try {
			return wxMpService.getAccessToken();
		}
		catch (WxErrorException e) {
			LOGGER.error("oauth error: {}", e);
		}
		return null;
	}

	@Override
	public String oauthLogin() {
		return wxMpService.oauth2buildAuthorizationUrl(systemConfig.getRedirectURI(), WxConsts.OAUTH2_SCOPE_USER_INFO, null);
	}

	// 处理event，及发送的消息
	@Override
	public void messageHandle(HttpServletRequest request, HttpServletResponse response, String timestamp, String nonce) {
		WxMpXmlMessage inMessage = null;// 传入message信息
		String result = null;// 返回（字符串）
		WxMpXmlOutMessage outMessage = null;// 返回（对象）
		String msgSignature = request.getParameter("msg_signature");// 加密签名

		LOGGER.debug("wechat message encryptType: {}", request.getParameter("encrypt_type"));

		// 获取加密类型
		String encryptType = StringUtils.isBlank(request.getParameter("encrypt_type")) ? "raw" : request.getParameter("encrypt_type");

		// 判断是否加密，加密类型
		Boolean isAes = ("raw".equals(encryptType) ? false : ("aes".equals(encryptType) ? true : null));// raw明文模式，aes表示aes加密模式
		LOGGER.debug("wechat message encryptType isAes: {}", isAes);
		try {
			if (isAes == null) {
				response.getWriter().println("不可识别的加密类型");
				return;
			}

			inMessage = (!isAes ? WxMpXmlMessage.fromXml(request.getInputStream())
					: WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), config, timestamp, nonce, msgSignature));

			LOGGER.debug("############ wechat message: {}", inMessage);

			// 处理message
			WechatMessageType msgType = WechatMessageType.valueOf(inMessage.getMsgType().toUpperCase());
			WechatEventType type = null;
			String eventType = inMessage.getEvent();
			if (StringUtils.isNotBlank(eventType))
				type = WechatEventType.valueOf(eventType.toUpperCase());
			String eventKey  = "";

			LOGGER.info("#############  envent key : {}", eventKey);
			LOGGER.debug("wechat message type,  event type: {}, {}", msgType, type);
			if (msgType == WechatMessageType.EVENT) {
				switch (type) {
					case SCAN:// 扫码
						eventKey = String.valueOf(inMessage.getEventKey());
						if(Constants.SmallWineConstants.EVENT_KEY.equals(eventKey)) {
							outMessage = generateSmallWineMsg(inMessage);
						}
						break;
					case LOCATION:// 位置
						outMessage = null;// TODO
						saveLocationToRedis(inMessage);
						break;
					case SUBSCRIBE:// 关注
						eventKey = inMessage.getEventKey().replace("qrscene_", "");
						if(Constants.SmallWineConstants.EVENT_KEY.equals(eventKey)) {
							outMessage = generateSmallWineMsg(inMessage);
						} else {
							String qrCode = (String) globalCacheService.get(GlobalCacheKeys.getCacheQrCodeKey(inMessage.getFromUserName()));
							LOGGER.debug("qrCode in redis: {}", qrCode);
							if (StringUtils.isNotBlank(qrCode))
							{
								WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
								item.setDescription(activityConfig.getActivityAttentionMsgDesc());
								item.setPicUrl(activityConfig.getActivityAttentionMsgImgUrl());
								item.setTitle(activityConfig.getActivityAttentionMsgTitle());
								item.setUrl(activityConfig.getActivityAttentionMsgRedirectUrl());
								outMessage = WxMpXmlOutMessage.NEWS()
										.fromUser(inMessage.getToUserName())
										.toUser(inMessage.getFromUserName())
										.addArticle(item)
										.build();
							}
						}
						break;
					case UNSUBSCRIBE:// 取消关注
						outMessage = null;// TODO
						break;
					case VIEW:// 菜单跳转
						outMessage = null;// TODO
						break;
					default:
						outMessage = null;// TODO
						break;
				}
			}
			else {
				switch (msgType) {
					case TEXT:
						if (Constants.WELCOME_TEXT.equals(inMessage.getContent())) {
							outMessage = WxMpXmlOutMessage.TEXT().toUser(inMessage.getToUserName()).fromUser(inMessage.getFromUserName()).content(
									Constants.WELCOME_TEXT_BACK).build();
						}
						break;
					default:
						outMessage = null;// TODO
						break;
				}
				outMessage = wxMpMessageRouter.route(inMessage);
			}

			// 处理返回信息
			if (outMessage != null) {
				LOGGER.debug("wechat out message: {}", outMessage.toString());
				result = (!isAes ? outMessage.toXml() : outMessage.toEncryptedXml(config));
			}
			// 返回
			if (StringUtils.isNoneBlank(result)) {
				response.getOutputStream().write(result.getBytes("UTF-8"));
			}
		} catch (Exception e) {
			LOGGER.error("##########exception occured: {}", e);
			throw new RuntimeException(e);
		}

	}

	/**
	 * 充值成功push通知
	 */
	@Override
	public void sendTemplateMessageForRecharge(String toUserOpenId, BigDecimal price, Date rechargeTime) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
		templateMessage.setToUser(toUserOpenId);
		templateMessage.setTemplateId(wechatTemplateRechargeId);
		templateMessage.getDatas().add(new WxMpTemplateData("first", "尊敬的用户，您已成功充值！", "#173177"));
		templateMessage.getDatas().add(new WxMpTemplateData("accountType", "充值时间", "#173177"));
		templateMessage.getDatas().add(new WxMpTemplateData("account", simpleDateFormat.format(rechargeTime), "#173177"));
		templateMessage.getDatas().add(new WxMpTemplateData("amount", price.toString(), "#173177"));
		templateMessage.getDatas().add(new WxMpTemplateData("result", "成功", "#173177"));
		try {
			wxMpService.templateSend(templateMessage);
		}
		catch (WxErrorException e) {
			LOGGER.error("send wechat template message error, {}", e.getMessage());
			throw new WechatTemplateMessageException(e);
		}
	}

	@Override
	public void createMenu() {

		WxMenu menu = new WxMenu();
		WxMenu.WxMenuButton button1 = new WxMenu.WxMenuButton();

		button1.setType(WxConsts.BUTTON_VIEW);
		button1.setName("");
		button1.setUrl("");
		menu.getButtons().add(button1);

		try {
			wxMpService.menuCreate(menu);
		}
		catch (WxErrorException e) {
			e.printStackTrace();
			// TODO
		}
	}

	@Override
	public JSSDKConfigResponse jsWxConfig(String url) {
		JSSDKConfigResponse config = new JSSDKConfigResponse();
		config.setAppId(systemConfig.getAppid());
		JSSDKConfigResponse jssdkConfig = new JSSDKConfigResponse();
		String noncestr = RandomStringGenerator.getRandomString();
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		String jsapi_ticket = null; // 获取jsapi_ticket
		try {
			jsapi_ticket = getJsapiTicket(wxMpService.getAccessToken());
		}
		catch (Exception e) {
			e.printStackTrace();
			// TODO
			return null;
		}
		String sign = sign(noncestr, jsapi_ticket, timestamp, url);
		LOGGER.info("Signature: " + sign);
		jssdkConfig.setAppId(systemConfig.getAppid());
		jssdkConfig.setNonceStr(noncestr);
		jssdkConfig.setTimestamp(timestamp);
		jssdkConfig.setSignature(sign);
		return jssdkConfig;
	}

	public String getJsapiTicket(String accessToken) throws Exception {
		LOGGER.info("Access Token: " + accessToken);
		JSONObject jsonObject;
		String ticket = (String) globalCacheService.get(GlobalCacheKeys.getWechatJsapiTicketKey());
		LOGGER.info("Ticket from redis: " + ticket);
		if (StringUtils.isNotBlank(ticket)) {
			return ticket;
		}
		LOGGER.debug("can not find wechat jsapi_ticket in redis or the jsapi_ticket is expired, a new request will be send.");
		URIBuilder uriBuilder = new URIBuilder(JSAPI_TICKET_URL).addParameter("access_token", accessToken).addParameter("type", "jsapi");
		String result = httpsRequest.sendSimpleGet(uriBuilder.build());
		LOGGER.debug("response of jsapi_ticket api: " + result);
		jsonObject = new JSONObject(result);
		if (jsonObject.getInt("errcode") == 0) {
			jsonObject.put("createAt", System.currentTimeMillis());
			ticket = jsonObject.getString("ticket");
			LOGGER.info("Ticket from Tecent: " + ticket);
			globalCacheService.save(GlobalCacheKeys.getWechatJsapiTicketKey(), ticket, 60);
			return ticket;
		}
		else {
			throw new RuntimeException("error to get wechat jsapi ticket");
		}
	}


	/**
	 * js sdk sign
	 *
	 * @param noncestr
	 * @param jsapi_ticket
	 * @param timestamp
	 * @param url
	 * @return
	 */
	private String sign(String noncestr, String jsapi_ticket, String timestamp, String url) {
		try {
			return Signature.getJSSDKSign(noncestr, jsapi_ticket, timestamp, url);
		}
		catch (Exception e) {
			e.printStackTrace();
			// TODO
			return null;
		}
	}

	private WxMpXmlOutMessage generateSmallWineMsg(WxMpXmlMessage inMessage) {
		WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
		item.setDescription(Constants.SmallWineConstants.ATTENTION_MESSAGE_DESC);
		item.setPicUrl(Constants.SmallWineConstants.ATTENTION_MESSAGE_IMG_URL);
		item.setTitle(Constants.SmallWineConstants.ATTENTION_MESSAGE_TITEL);
		item.setUrl(Constants.SmallWineConstants.ATTENTION_MESSAGE_REDIRECT_URL);
		return WxMpXmlOutMessage.NEWS()
				.fromUser(inMessage.getToUserName())
				.toUser(inMessage.getFromUserName())
				.addArticle(item)
				.build();
	}

	private void saveLocationToRedis(WxMpXmlMessage inMessage) {
		Double latitude = inMessage.getLatitude();
		Double longitude = inMessage.getLongitude();
		Double precision = inMessage.getPrecision();
		String openId = inMessage.getFromUserName();
		LOGGER.debug("save user location to redis: ", inMessage.toString());
		globalCacheService.save(GlobalCacheKeys.getGlobalLatitudeKey(openId), latitude);
		globalCacheService.save(GlobalCacheKeys.getGlobalLongitudeKey(openId), longitude);
		globalCacheService.save(GlobalCacheKeys.getGlobalPrecisionKey(openId), precision);
	}
}
