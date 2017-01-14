package com.sap.jnc.marketing.api.admin.web.controller;

import com.sap.jnc.marketing.api.admin.web.security.AdminUserDetailsService;
import com.sap.jnc.marketing.infrastructure.shared.constant.WebAttributes;
import com.sap.jnc.marketing.persistence.model.User;
import com.sap.jnc.marketing.service.security.IdmUser;
import com.sap.jnc.marketing.service.wechat.WechatService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * @author Alex
 */
@Controller
public class LoginController {

    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private WechatService wechatService;

    @Autowired
    private AdminUserDetailsService adminUserDetailsService;

    @RequestMapping(value = "/wechat_oauth_callback", method = RequestMethod.GET)
    public String wechatCallback(HttpServletRequest request, HttpSession session, HttpServletResponse response, @RequestParam String code,
                                 @RequestParam(required = false) String state, @AuthenticationPrincipal IdmUser idmUser) throws IOException, WxErrorException {

        LOGGER.info(
                "Entering wechat authentication callback, try to retrieve open ID with code {}",
                code);
        WxMpOAuth2AccessToken oAuth2AccessToken = wechatService.getWxMpService().oauth2getAccessToken(code); //note this token is used to get openID, not userinfo

        LOGGER.info("Get open ID respone {}", oAuth2AccessToken.toString());
        if (oAuth2AccessToken == null || oAuth2AccessToken.getOpenId() == null) {
            LOGGER.warn("open ID not returned, trying to restart oauth login");
        }

        String openId = oAuth2AccessToken.getOpenId();
        LOGGER.info("User Open ID: {} acquired", openId);
        session.setAttribute(WebAttributes.WECHAT_OPEN_ID, openId);

        LOGGER.info("Trying to load user from local via openId");
        List<User> users = null; //= adminUserDetailsService.loadUserByOpenId(openId);

        if (!users.isEmpty()) {
            User user = users.get(0);//in theory, only one user should be found.
            LOGGER.info("User loaded successful via openId {}", user.getWechatAccount());

            try {
                //TODO process spring security login
            } catch (Exception e) {
                LOGGER.error("unexpected login failure", e);
            }

        } else {
            LOGGER.info("User is accessing with guest role, try to retrieve user info via Wechat API");
            try {
                WxMpUser wechatUser = wechatService.retrieveUserInfoSilently(openId); //Get user info using global Wechat Token, no authorization needed if user has subscribed.
                LOGGER.info("User info: {}", wechatUser.toString());
            } catch (Exception e) {
                LOGGER.error("Error ocurred while retrieving user info", e);
            }
        }

        return null;
    }
}
