package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;

import com.sap.jnc.marketing.persistence.model.WechatPromotionActivity_;
import org.nustaq.serialization.annotations.Predict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.WechatPromotionActivity;
import com.sap.jnc.marketing.persistence.repository.WechatPromotionActivityRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class WechatPromotionActivityRepositoryImpl extends SimpleJpaRepository<WechatPromotionActivity, Long> implements WechatPromotionActivityRepository {

	@Autowired
	public WechatPromotionActivityRepositoryImpl(EntityManager em) {
		super(WechatPromotionActivity.class, em);
	}

	@Override
	public long countByDaysAndOpenId(int days, String openId) {
		return super.count((root, query, cb) -> {
			final List<Predicate> predicate = new ArrayList<>();
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, - days);
			calendar.set(calendar.HOUR_OF_DAY, 0);
			calendar.set(calendar.MINUTE, 0);
			calendar.set(calendar.SECOND, 0);
			calendar.set(calendar.MILLISECOND, 0);
			predicate.add(cb.equal(root.get(WechatPromotionActivity_.wechatOpenID), openId));
			predicate.add(cb.greaterThanOrEqualTo(root.get(WechatPromotionActivity_.receiveDate), calendar));
			return cb.and(predicate.toArray(new Predicate[predicate.size()]));
		});
	}
}
