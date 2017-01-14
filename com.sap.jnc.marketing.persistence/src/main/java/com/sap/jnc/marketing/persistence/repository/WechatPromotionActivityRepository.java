package com.sap.jnc.marketing.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.WechatPromotionActivity;

@NoRepositoryBean
public interface WechatPromotionActivityRepository extends JpaRepository<WechatPromotionActivity, Long> {
    long countByDaysAndOpenId(int days, String openId);
}
