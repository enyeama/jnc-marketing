package com.sap.jnc.marketing.service.authentication;

import com.sap.jnc.marketing.dto.request.authentication.UserProfileChangeRequest;
import com.sap.jnc.marketing.persistence.model.User;

/**
 * @author I071053 Diouf Du
 */
public interface AuthenticationService {

	/**
	 * 根据当前登录用户的登录关键字来查找登录信息.
	 *
	 * @param loginKeyword
	 *            登录关键字，可以是用户 username, 微信openId，或phone
	 * @return 找到的用户
	 */
	User findOneByLoginKeyword(String loginKeyword);

	/**
	 * 修改当前登录用户的信息.
	 *
	 * @param request
	 *            请求修改的内容
	 */
	void changeProfile(UserProfileChangeRequest request);

}
