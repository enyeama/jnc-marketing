package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.IndividualProductBonus;
import com.sap.jnc.marketing.persistence.model.IndividualProductBonus_;
import com.sap.jnc.marketing.persistence.repository.IndividualProductBonusRepository;

/**
 * 产品实体红包Repository
 * 
 * @author I323560
 */
@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class IndividualProductBonusRepositoryImpl extends SimpleJpaRepository<IndividualProductBonus, String>
		implements IndividualProductBonusRepository {

	private EntityManager em;

	@Autowired
	public IndividualProductBonusRepositoryImpl(EntityManager em) {
		super(IndividualProductBonus.class, em);
		this.em = em;
	}

	@Override
	public boolean verifyCode(String capInnerCode, String verificationCode) {
		CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		CriteriaQuery<IndividualProductBonus> criteriaQuery = criteriaBuilder.createQuery(IndividualProductBonus.class);
		Root<IndividualProductBonus> individualProductBonusRoot = criteriaQuery.from(IndividualProductBonus.class);

		Predicate capInnerCodePredicate = criteriaBuilder
				.equal(individualProductBonusRoot.get(IndividualProductBonus_.capInnerCode), capInnerCode);
		final Predicate verificationCodePredicate = criteriaBuilder
				.equal(individualProductBonusRoot.get(IndividualProductBonus_.verificationCode), verificationCode);
		Predicate consumedPredicate = criteriaBuilder
				.equal(individualProductBonusRoot.get(IndividualProductBonus_.isConsumed), false);
		criteriaQuery.where(capInnerCodePredicate, verificationCodePredicate, consumedPredicate);
		List<IndividualProductBonus> individualProductBonusList = em
				.createQuery(criteriaQuery.select(individualProductBonusRoot)).getResultList();
		boolean verified = false;
		if (CollectionUtils.isNotEmpty(individualProductBonusList)) {
			verified = true;
		}

		return verified;
	}

	@Override
	public Long findProductErpCategoryIdByCapInnerCode(String capInnerCode) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<IndividualProductBonus> criteriaQuery = criteriaBuilder.createQuery(IndividualProductBonus.class);
		Root<IndividualProductBonus> individualProductBonusRoot = criteriaQuery.from(IndividualProductBonus.class);
		individualProductBonusRoot.fetch(IndividualProductBonus_.productErpCategory, JoinType.LEFT);

		Predicate capInnerCodePredicate = criteriaBuilder
				.equal(individualProductBonusRoot.get(IndividualProductBonus_.capInnerCode), capInnerCode);
		criteriaQuery.where(capInnerCodePredicate);
		IndividualProductBonus individualProductBonus = em.createQuery(criteriaQuery.select(individualProductBonusRoot))
				.getSingleResult();
		Long productErpCategoryId = null;
		if (null != individualProductBonus && null != individualProductBonus.getProductErpCategory()) {
			productErpCategoryId = individualProductBonus.getProductErpCategory().getId();
		}

		return productErpCategoryId;
	}

}
