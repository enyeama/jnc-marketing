package com.sap.jnc.marketing.api.wechat.web.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.dto.response.authentication.LoginUserDetailResponse;
import com.sap.jnc.marketing.persistence.enumeration.PrivilegeType;
import com.sap.jnc.marketing.persistence.model.User;
import com.sap.jnc.marketing.service.authentication.AuthenticationService;
import com.sap.jnc.marketing.service.security.AuthenticationTokenFilter;
import com.sap.jnc.marketing.service.security.portal.PortalUser;

/**
 * @author I071053 Diouf Du
 */
@RestController
public class AuthenticationController extends GeneralController {

	private static Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private AuthenticationService authenticationService;

	@RequestMapping(value = "/authentication/user/openid", method = RequestMethod.GET)
	public String getLoginUserOpenId() {
		String openId = null;
		final List<Cookie> cookies = Arrays.asList(super.getHttpServletRequest().getCookies());
		if (CollectionUtils.isNotEmpty(cookies)) {
			final Cookie userOpenIdCookie = IterableUtils.find(cookies, c -> StringUtils.equalsIgnoreCase(c.getName(),
				AuthenticationTokenFilter.USER_OPEN_ID));
			if (userOpenIdCookie != null) {
				openId = userOpenIdCookie.getValue();
				LOGGER.debug("AuthenticationController.getLoginUserOpenId from cookie: " + openId);
				return openId;
			}
			else {
				openId = super.getHttpSession().getAttribute(AuthenticationTokenFilter.USER_OPEN_ID).toString();
				LOGGER.debug("AuthenticationController.getLoginUserOpenId from session: " + openId);
				return openId;
			}
		}
		LOGGER.debug("Cookies are empty");
		super.getHttpServletResponse().setStatus(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}

	@RequestMapping(value = "/authentication/user", method = RequestMethod.GET)
	public LoginUserDetailResponse getLoginUserDetail(@AuthenticationPrincipal PortalUser portalUser) {
		final String username = this.getLoginUserOpenId();
		LOGGER.debug("AuthenticationController.getLoginUserDetail - openid: " + username);
		if (StringUtils.isNotBlank(username)) {
			return this.getLoginUserDetailByLoginKeyword(username);
		}
		else if ((portalUser != null) && StringUtils.isNotBlank(portalUser.getOpenId())) {
			return this.getLoginUserDetailByLoginKeyword(portalUser.getOpenId());
		}
		super.getHttpServletResponse().setStatus(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}

	/**
	 * Login via keywords.
	 *
	 * @param keyword
	 *            the login keyword
	 * @return the loaded user detail response
	 */
	private LoginUserDetailResponse getLoginUserDetailByLoginKeyword(final String keyword) {
		if (StringUtils.isBlank(keyword)) {
			super.getHttpServletResponse().setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		final User loginUser = this.authenticationService.findOneByLoginKeyword(keyword);
		if ((loginUser == null) || CollectionUtils.isEmpty(loginUser.getRoles())) {
			super.getHttpServletResponse().setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return new LoginUserDetailResponse(loginUser, PrivilegeType.ADMIN);
	}
}
