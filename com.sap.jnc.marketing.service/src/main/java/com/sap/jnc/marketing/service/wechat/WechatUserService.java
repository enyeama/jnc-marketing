package com.sap.jnc.marketing.service.wechat;

import com.sap.jnc.marketing.persistence.enumeration.UserStatus;
import com.sap.jnc.marketing.persistence.model.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Alex
 */
public interface WechatUserService {

	List<User> findByOpenId(String openId, Optional<UserStatus> active);

	List<User> findByPhoneNo(String phoneNo);

	List<User> findByUsername(String username);
}
