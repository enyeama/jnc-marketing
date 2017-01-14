package com.sap.jnc.marketing.dto.request.authentication;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.sap.jnc.marketing.dto.validator.authentication.CheckPassword;

/**
 * @author I071053 Diouf Du
 */
@CheckPassword
public class UserProfileChangeRequest implements Serializable {

	private static final long serialVersionUID = -3711432276258718391L;

	private String userName;

	@NotBlank
	@Length(min = 16, max = 64)
	private String oldPassword;

	@NotBlank
	@Length(min = 16, max = 64)
	private String newPassword;

	@NotBlank
	@Length(min = 16, max = 64)
	private String confirmPassword;

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOldPassword() {
		return this.oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return this.newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
