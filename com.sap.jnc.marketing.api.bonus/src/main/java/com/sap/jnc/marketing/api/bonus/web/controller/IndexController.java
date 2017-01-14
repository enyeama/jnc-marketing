package com.sap.jnc.marketing.api.bonus.web.controller;

import com.sap.jnc.marketing.infrastructure.shared.ActivityConfig;
import com.sap.jnc.marketing.infrastructure.shared.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dy on 16/6/28.
 */
@Controller
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    ActivityConfig activityConfig;

    @RequestMapping("/*")
    public String index(HttpServletRequest request) {
        String uri = request.getRequestURI();
        LOGGER.debug("IndexController uri:" + uri);
        String qrCode = uri.substring(1, uri.length());
        if (StringUtils.isBlank(qrCode)) {
            throw new RuntimeException("user may be not from qr_code");
        }
        String oauthUrl = "/oauth_login?" + Constants.QR_CODE_KEY + "=" + qrCode + "&" + Constants.API_CALLBACK_URL
                + "=" + activityConfig.getActivityAttentionMsgRedirectUrl();
        LOGGER.debug("User from qr_code is redirect to: {}", oauthUrl);
        return "redirect:" + oauthUrl;
    }

}
