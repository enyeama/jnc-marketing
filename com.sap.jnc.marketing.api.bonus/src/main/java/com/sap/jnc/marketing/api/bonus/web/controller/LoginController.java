package com.sap.jnc.marketing.api.bonus.web.controller;

import com.sap.jnc.marketing.dto.request.bonus.WechatConsumerRequest;
import com.sap.jnc.marketing.infrastructure.shared.GlobalCacheKeys;
import com.sap.jnc.marketing.infrastructure.shared.SystemConfig;
import com.sap.jnc.marketing.infrastructure.shared.constant.Constants;
import com.sap.jnc.marketing.infrastructure.shared.utils.UrlParametersUtils;
import com.sap.jnc.marketing.persistence.enumeration.Gender;
import com.sap.jnc.marketing.service.bonus.WechatConsumerService;
import com.sap.jnc.marketing.service.globalcache.GlobalCacheService;
import com.sap.jnc.marketing.service.security.AuthenticationTokenFilter;
import com.sap.jnc.marketing.service.security.IdentityService;
import com.sap.jnc.marketing.service.wechat.WechatService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by dy on 16/6/28.
 */
@Controller
public class LoginController {

    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private WechatService wechatService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private GlobalCacheService globalCacheService;

    @Autowired
    private WechatConsumerService wechatConsumerService;

    @Autowired
    private SystemConfig systemConfig;


    /**
     * 用户授权方式获取用户信息
     *
     * @return
     */
    @RequestMapping("/oauth_login")
    public String login(HttpServletRequest request, HttpSession session) {
        String queryString = request.getQueryString();
        String callBackUrl = UrlParametersUtils.findParamterByName(queryString, Constants.API_CALLBACK_URL);
        if (StringUtils.isNotBlank(callBackUrl)) {
            session.setAttribute(Constants.API_CALLBACK_URL, callBackUrl);
            LOGGER.debug("User is redirect to " + callBackUrl + " after oauth complete");
        }
        String qrCode = UrlParametersUtils.findParamterByName(queryString, Constants.QR_CODE_KEY);
        if (StringUtils.isNotBlank(qrCode)) {
            session.setAttribute(Constants.QR_CODE_KEY, qrCode);
            LOGGER.debug("User scan qrCode: {}", qrCode);
        }
        // https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
        String accessCodeURL = wechatService.buildAccessCodeURL(Constants.USER_INFO_SCOPE);
        LOGGER.info("User is redirecting to " + accessCodeURL);
        return "redirect:" + accessCodeURL;
    }

    @RequestMapping(value = "/wechat_oauth_callback", method = RequestMethod.GET)
    public void wechatCallback(HttpServletResponse response,
                               HttpSession session,
                               @RequestParam String code,
                               @RequestParam(required = false) String state) throws IOException, WxErrorException {
        LOGGER.debug("wechat_oauth_callback start");

        WxMpOAuth2AccessToken tokenInfo = wechatService.retrieveAccessToken(code);
        LOGGER.debug("tokenInfo: {}", tokenInfo);
        String openId = tokenInfo.getOpenId();

        String token = AuthenticationTokenFilter.recreateToken(true);
        globalCacheService.save(AuthenticationTokenFilter.getTokenCacheKey(openId), token);
        Cookie openIdCookie = new Cookie(AuthenticationTokenFilter.USER_OPEN_ID, AuthenticationTokenFilter.getMixedUserOpenId(openId));
        Cookie tokenCookie = new Cookie(AuthenticationTokenFilter.HEADER_TOKEN_NAME, token);
        openIdCookie.setPath("/");
        tokenCookie.setPath("/");
        openIdCookie.setMaxAge(Constants.COOKIE_EXPIRE_TIME);
        tokenCookie.setMaxAge(Constants.COOKIE_EXPIRE_TIME);
        response.addCookie(openIdCookie);
        response.addCookie(tokenCookie);

        String qrCode = (String) session.getAttribute(Constants.QR_CODE_KEY);
        if (StringUtils.isNotBlank(qrCode)) {
            globalCacheService.save(GlobalCacheKeys.getCacheQrCodeKey(openId), qrCode);
            globalCacheService.save(GlobalCacheKeys.getTryBonusCount(openId), 0);
            LOGGER.debug("save qrCode to redis, key: {}", GlobalCacheKeys.getCacheQrCodeKey(openId));
        }

        WxMpUser user = wechatService.getWxMpService().userInfo(openId, Constants.LANG);
        if (user == null || StringUtils.isBlank(user.getNickname())) {
            LOGGER.debug("user haven't attention, to attention url now.....");
            response.sendRedirect(systemConfig.getAttentionUrl());
            return;
        }

        WxMpUser wxUser = wechatService.retrieveUserInfo(tokenInfo, openId);
        LOGGER.debug("wxUser: {}", wxUser.toString());
        //TODO register
        //insert consumer
        WechatConsumerRequest wechatConsumerRequest = parseWechatUserToConsumer(wxUser);
        wechatConsumerService.setWechatConsumer(wechatConsumerRequest);

        String redirect = (String) session.getAttribute(Constants.API_CALLBACK_URL);
        LOGGER.debug("wechat oauth complete, forward: {}", redirect);
        response.sendRedirect(redirect);
    }

    @RequestMapping("/backdoor")
    public String backdoor() {
        return "entrance/login"; //TODO: disable login page in production
    }

    private WechatConsumerRequest parseWechatUserToConsumer(WxMpUser wxMpUser) {
        WechatConsumerRequest wechatConsumerRequest = new WechatConsumerRequest();
        wechatConsumerRequest.setCity(wxMpUser.getCity());
        wechatConsumerRequest.setCountry(wxMpUser.getCountry());
        wechatConsumerRequest.setFollowTime(Calendar.getInstance());
        wechatConsumerRequest.setLanguage(wxMpUser.getLanguage());
        wechatConsumerRequest.setNickName(wxMpUser.getNickname());
        wechatConsumerRequest.setOpenId(wxMpUser.getOpenId());
        wechatConsumerRequest.setProvince(wxMpUser.getProvince());
        if (wxMpUser.getSex().equals(Constants.GENDER.MALE)) {
            wechatConsumerRequest.setGender(Gender.MALE);
        } else if (wxMpUser.getSex().equals(Constants.GENDER.FEMAL)) {
            wechatConsumerRequest.setGender(Gender.FEMALE);
        } else {
            wechatConsumerRequest.setGender(Gender.UNKNOWN);
        }
        return wechatConsumerRequest;
    }
}
