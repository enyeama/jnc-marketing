package com.sap.jnc.marketing.service.security;

import com.sap.jnc.marketing.infrastructure.shared.utils.wechat.RandomStringGenerator;
import com.sap.jnc.marketing.persistence.model.User;
import com.sap.jnc.marketing.persistence.model.WechatAccount;
import com.sap.jnc.marketing.service.globalcache.GlobalCacheService;
import com.sap.jnc.marketing.service.security.portal.PortalUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dy on 16/6/21.
 */
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationTokenFilter.class);

    public static final String HEADER_TOKEN_NAME = "token";

    public static final String USER_OPEN_ID = "user_open_id";

    public static final String CACHE_TOKEN_KEY = "TOKEN_CACHE_";

    private static final String WECHAT_TOKEN_PREFIX = "wx_";

    @Autowired
    private IdentityService identityService;

    @Autowired
    private GlobalCacheService globalCacheService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String mixedUserOpenId = httpServletRequest.getHeader(USER_OPEN_ID);
        if (StringUtils.isBlank(mixedUserOpenId)) {
            LOGGER.debug("cannot get userId from header");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String openId = recoveryUserOpenId(mixedUserOpenId);
        LOGGER.debug("AuthenticationFilter openId: {}", openId);

        String token = httpServletRequest.getHeader(HEADER_TOKEN_NAME);
        if (StringUtils.isBlank(token)) {
            LOGGER.debug("cannot get token from header");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        LOGGER.debug("AuthenticationFilter token: {}", token);
        String cachedToken = (String)globalCacheService.get(CACHE_TOKEN_KEY + openId);
        IdmUser idmUser;
        if (StringUtils.isNotBlank(cachedToken) && token.equals(cachedToken)) {
            if (token.startsWith(WECHAT_TOKEN_PREFIX)) {
                //consumer user
                User user = new User();
                WechatAccount wechatAccount = new WechatAccount();
                wechatAccount.setOpenId(openId);
                user.setWechatAccount(wechatAccount);
                idmUser = new PortalUser(user);
                token = recreateToken(true);
            } else {
                idmUser = identityService.loadUserByOpenId(openId);
                token = recreateToken(false);
            }
            identityService.setUserAuthenticationInfo(httpServletRequest, idmUser);
            globalCacheService.save(getTokenCacheKey(openId), token);
            httpServletResponse.setHeader(USER_OPEN_ID, mixedUserOpenId);
            httpServletResponse.setHeader(HEADER_TOKEN_NAME, token);
        } else {
            LOGGER.debug("cannot find token with key: {} in redis", getTokenCacheKey(openId));
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    public static String getTokenCacheKey(String openId) {
        if (StringUtils.isBlank(openId)) {
            throw new RuntimeException("openId must be not null");
        }
        return CACHE_TOKEN_KEY + openId;
    }

    public static String getMixedUserOpenId(String openId) {
        if (StringUtils.isBlank(openId)) {
            throw new RuntimeException("openId must be not null");
        }
        //TODO mix
        return openId;
    }

    public static String recoveryUserOpenId(String mixedUserOpenId) {
        //TODO recovery
        return mixedUserOpenId;
    }

    public static String recreateToken(Boolean isConsumer) {
        if (!isConsumer) {
            return RandomStringGenerator.getRandomString(32);
        }
        String token = RandomStringGenerator.getRandomString(29);
        return WECHAT_TOKEN_PREFIX + token;
    }
}
