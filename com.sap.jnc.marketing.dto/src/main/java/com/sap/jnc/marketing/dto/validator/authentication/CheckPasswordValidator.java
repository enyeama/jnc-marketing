package com.sap.jnc.marketing.dto.validator.authentication;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sap.jnc.marketing.dto.request.authentication.UserProfileChangeRequest;
import com.sap.jnc.marketing.persistence.model.User;
import com.sap.jnc.marketing.persistence.repository.UserRepository;

/**
 * @author I071053 Diouf Du
 */
public class CheckPasswordValidator implements ConstraintValidator<CheckPassword, UserProfileChangeRequest> {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void initialize(CheckPassword constraintAnnotation) {
	}

	@Override
	public boolean isValid(UserProfileChangeRequest request, ConstraintValidatorContext context) {
		if (request == null) {
			return true;
		}
		final String userName = request.getUserName().trim();
		if (StringUtils.isBlank(userName)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("用户未登录").addConstraintViolation();
			return false;
		}
		final User user = this.userRepository.findOneByLoginKeyword(userName);
		if ((user == null) || (user.getLoginAccount() == null)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("用户不存在").addConstraintViolation();
			return false;
		}
		// 旧密码不符合
		if (!StringUtils.equals(user.getLoginAccount().getPassword(), request.getOldPassword())) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("旧密码不正确").addConstraintViolation();
			return false;
		}
		// 两次密码不一致
		final String newPassword = request.getNewPassword().trim();
		final String confirmPassword = request.getConfirmPassword().trim();
		if (!StringUtils.equals(newPassword, confirmPassword)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("输入的新密码和确认新密码不一致").addConstraintViolation();
			return false;
		}
		return true;
	}
}