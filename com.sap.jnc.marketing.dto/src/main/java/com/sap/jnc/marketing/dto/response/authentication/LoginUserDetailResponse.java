package com.sap.jnc.marketing.dto.response.authentication;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.sap.jnc.marketing.persistence.enumeration.Gender;
import com.sap.jnc.marketing.persistence.enumeration.PrivilegeType;
import com.sap.jnc.marketing.persistence.model.User;
import com.sap.jnc.marketing.persistence.model.UserReferenceToEmployee;

/**
 * @author I071053 Diouf Du
 */
public class LoginUserDetailResponse implements Serializable {

	private static final long serialVersionUID = 1544760466245286587L;

	private String name;

	private String loginName;

	private String mobile;

	private String group;

	private Gender gender;

	private String country;

	private String province;

	private String city;

	private Calendar subscribeAt;

	private Calendar unsubscribeAt;

	private List<EmployeeReferenceResponse> employees;

	private RoleAndPrivilegeResponse roles;

	public LoginUserDetailResponse() {
	}

	public LoginUserDetailResponse(User user) {
		this(user, null);
	}

	public LoginUserDetailResponse(User user, PrivilegeType privilegeType) {
		if (user == null) {
			return;
		}
		this.setName(user.getName());
		if (user.getLoginAccount() != null) {
			this.setLoginName(user.getLoginAccount().getUserName());
			this.setMobile(user.getLoginAccount().getMobile());
		}
		if (user.getWechatAccount() != null) {
			this.setGroup(user.getWechatAccount().getGroup());
			this.setGender(user.getWechatAccount().getGender());
			this.setCountry(user.getWechatAccount().getCountry());
			this.setProvince(user.getWechatAccount().getProvince());
			this.setCity(user.getWechatAccount().getCity());
			this.setSubscribeAt(user.getWechatAccount().getSubscribeAt());
			this.setUnsubscribeAt(user.getWechatAccount().getUnsubscribeAt());
		}

		if (CollectionUtils.isNotEmpty(user.getUserReferences())) {
			this.setEmployees(user.getUserReferences().stream()
				// Check type
				.filter(ur -> (ur != null) && (ur instanceof UserReferenceToEmployee))
				// Convert to specific type
				.map(ur -> ((UserReferenceToEmployee) ur).getEmployee())
				// Check not null
				.filter(ev -> (ev != null))
				// Convert to dto
				.map(ev -> new EmployeeReferenceResponse(ev))
				// Collect
				.collect(Collectors.toList()));
		}
		this.roles = new RoleAndPrivilegeResponse(user, privilegeType);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGroup() {
		return this.group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Gender getGender() {
		return this.gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Calendar getSubscribeAt() {
		return this.subscribeAt;
	}

	public void setSubscribeAt(Calendar subscribeAt) {
		this.subscribeAt = subscribeAt;
	}

	public Calendar getUnsubscribeAt() {
		return this.unsubscribeAt;
	}

	public void setUnsubscribeAt(Calendar unsubscribeAt) {
		this.unsubscribeAt = unsubscribeAt;
	}

	public List<EmployeeReferenceResponse> getEmployees() {
		return this.employees;
	}

	public void setEmployees(List<EmployeeReferenceResponse> employees) {
		this.employees = employees;
	}

	public RoleAndPrivilegeResponse getRoles() {
		return this.roles;
	}

	public void setRoles(RoleAndPrivilegeResponse roles) {
		this.roles = roles;
	}
}
