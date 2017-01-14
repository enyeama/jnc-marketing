package com.sap.jnc.marketing.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.User;

@NoRepositoryBean
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByUserName(String userName);

	List<User> findAllByUserName(String username);

	User findOneByLoginKeyword(String username);

	User findOneByOpenId(String openId);

	User findOneByEmployeeProfile(String name, String idCardNo, String mobile, String externalEmployeeId);

}
