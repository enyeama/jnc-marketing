package com.sap.jnc.marketing.api.admin.web.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.dto.request.authentication.UserProfileChangeRequest;
import com.sap.jnc.marketing.dto.response.ObjectErrorResponse;
import com.sap.jnc.marketing.dto.response.authentication.LoginUserDetailResponse;
import com.sap.jnc.marketing.persistence.enumeration.PrivilegeType;
import com.sap.jnc.marketing.persistence.model.User;
import com.sap.jnc.marketing.service.authentication.AuthenticationService;
import com.sap.jnc.marketing.service.exception.validation.RequestBodyFieldValidationException;

/**
 * @author I071053 Diouf Du
 */
@RestController
public class AuthenticationController extends GeneralController {

	@Autowired
	private AuthenticationService authenticationService;

	@RequestMapping(value = "/authentication/user", method = RequestMethod.GET)
	public LoginUserDetailResponse getLoginUserDetail() {
		final String userName = super.getHttpServletRequest().getRemoteUser();
		if (StringUtils.isBlank(userName)) {
			super.getHttpServletResponse().setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		final User loginUser = this.authenticationService.findOneByLoginKeyword(userName);
		if ((loginUser == null) || CollectionUtils.isEmpty(loginUser.getRoles())) {
			super.getHttpServletResponse().setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		return new LoginUserDetailResponse(loginUser, PrivilegeType.ADMIN);
	}

	@RequestMapping(value = "/authentication/user", method = RequestMethod.PUT)
	public void changeProfile(@Valid @RequestBody UserProfileChangeRequest request, BindingResult bindingResult) {
		//如果有信息错误，返回。
		if (bindingResult.hasErrors()) {
			throw new RequestBodyFieldValidationException(bindingResult);
		}
		//尝试修改不是当前登录用户的信息
		if(!StringUtils.equals(request.getUserName(), super.getHttpServletRequest().getRemoteUser()) ){
			super.getHttpServletResponse().setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		this.authenticationService.changeProfile(request);
	}

	@ExceptionHandler(RequestBodyFieldValidationException.class)
	public ObjectErrorResponse handleRequestBodyFieldValidationException(RequestBodyFieldValidationException ex) {
		super.getHttpServletResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return ex.getObjectErrorResponse();
	}
}
