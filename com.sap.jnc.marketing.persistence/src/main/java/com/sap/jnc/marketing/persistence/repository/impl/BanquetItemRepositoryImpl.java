package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.BanquetItem;
import com.sap.jnc.marketing.persistence.model.BanquetItem_;
import com.sap.jnc.marketing.persistence.model.Banquet_;
import com.sap.jnc.marketing.persistence.repository.BanquetItemRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class BanquetItemRepositoryImpl extends SimpleJpaRepository<BanquetItem, String>
		implements BanquetItemRepository {

	@Autowired
	public BanquetItemRepositoryImpl(EntityManager em) {
		super(BanquetItem.class, em);
	}

	@Override
	public List<BanquetItem> findReportedByBanquetIdAndCapInnerCode(long banquetId, String capInnerCode) {
		
		List<BanquetItem> results = this.findAll((root, query, cb) -> {
			root.fetch(BanquetItem_.individualProduct, JoinType.LEFT);
			return cb.and(cb.equal(root.join(BanquetItem_.banquet, JoinType.LEFT).get(Banquet_.id), banquetId),
					cb.equal(root.get(BanquetItem_.capInnerCode), capInnerCode));
		});

		return results;
	}
	
	@Override
	public List<BanquetItem> findReportedByBanquetIdAndBoxId(long banquetId, String boxId) {
		
		List<BanquetItem> results = this.findAll((root, query, cb) -> {
			root.fetch(BanquetItem_.individualProduct, JoinType.LEFT);
			return cb.and(cb.equal(root.join(BanquetItem_.banquet, JoinType.LEFT).get(Banquet_.id), banquetId),
					cb.equal(root.get(BanquetItem_.boxId), boxId));
		});

		return results;
	}
	
	@Override
	public List<BanquetItem> findReportedByBanquetId(long banquetId) {
		
		List<BanquetItem> results = this.findAll((root, query, cb) -> {
			return cb.equal(root.join(BanquetItem_.banquet, JoinType.LEFT).get(Banquet_.id), banquetId);
		});

		return results;
	}
	
	@Override
	public List<BanquetItem> findRbatedByBanquetIdAndIsPaid(long banquetId) {
		List<BanquetItem> results = this.findAll((root,query,cb) -> {
			return cb.and(cb.equal(root.join(BanquetItem_.banquet, JoinType.LEFT).get(Banquet_.id),banquetId),
					cb.equal(root.get(BanquetItem_.isPaid), Boolean.TRUE));
		});
		return results;
	}
	
	@Override
	public BanquetItem findbyId(long id) {
		
		BanquetItem result = this.findOne((root, query, cb) -> {
			root.fetch(BanquetItem_.individualProduct, JoinType.LEFT);
			return cb.equal(root.get(BanquetItem_.id), id);
		});

		return result;
	}
}
