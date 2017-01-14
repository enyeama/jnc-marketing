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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.dto.request.register.EmployeeRegisterRequest;
import com.sap.jnc.marketing.dto.request.register.RegisterRequest;
import com.sap.jnc.marketing.infrastructure.shared.SystemConfig;
import com.sap.jnc.marketing.infrastructure.shared.constant.ApiResult;
import com.sap.jnc.marketing.infrastructure.shared.constant.MessageId;
import com.sap.jnc.marketing.infrastructure.shared.utils.sms.SmsEntity;
import com.sap.jnc.marketing.service.globalcache.GlobalCacheService;
import com.sap.jnc.marketing.service.security.AuthenticationTokenFilter;
import com.sap.jnc.marketing.service.terminal.TerminalService;
import com.sap.jnc.marketing.service.wechat.employee.WechatEmployeeRegisterService;

/**
 * Created by guokai on 16/6/28.
 */
@RestController
public class RegisterController extends GeneralController {

	private static Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

	@Autowired
	TerminalService terminalService;

	@Autowired
	WechatEmployeeRegisterService wechatEmployeeRegisterService;

	@Autowired
	GlobalCacheService globalCacheService;

	@Autowired
	SystemConfig config;

	@RequestMapping("register/terminal")
	public ApiResult registerTerminal(RegisterRequest request) {
		SmsEntity entity = new SmsEntity(request.getType(), request.getPhoneNumber(), request.getCode());
		SmsEntity info = (SmsEntity) globalCacheService.get(entity.getEntityKey());
		if (info != null && info.getCode().equals(request.getCode())) {
			terminalService.registerUser(request);
			return ApiResult.SUCCESS;
		} else {
			return new ApiResult(MessageId.NORMAL_PHONE_ALREADY_EXISTED, "not found");
		}
	}

	@RequestMapping(value = "register/employee", method = RequestMethod.POST)
	public ApiResult registerEmployee(EmployeeRegisterRequest employeeRegisterRequest) {
		employeeRegisterRequest.setOpenId(getLoginUserOpenId());
		LOGGER.debug(employeeRegisterRequest.toString());
		SmsEntity entity = new SmsEntity(0, employeeRegisterRequest.getEmployeePhone(), employeeRegisterRequest.getVerificationCode());
		SmsEntity info = (SmsEntity) globalCacheService.get(entity.getEntityKey());
		LOGGER.debug(info.toString());
		if (info != null && info.getCode().equals(employeeRegisterRequest.getVerificationCode())) {
			wechatEmployeeRegisterService.registerEmployee(employeeRegisterRequest);
			return ApiResult.SUCCESS;
		} else {
			return new ApiResult(MessageId.NORMAL_PHONE_ALREADY_EXISTED, "not found");
		}
	}

	private String getLoginUserOpenId() {
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
}
