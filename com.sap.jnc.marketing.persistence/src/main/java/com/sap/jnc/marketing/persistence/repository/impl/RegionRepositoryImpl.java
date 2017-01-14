package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.PositionView_;
import com.sap.jnc.marketing.persistence.model.Region;
import com.sap.jnc.marketing.persistence.model.Region_;
import com.sap.jnc.marketing.persistence.repository.RegionRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RegionRepositoryImpl extends SimpleJpaRepository<Region, Long> implements RegionRepository {

	@Autowired
	public RegionRepositoryImpl(EntityManager em) {
		super(Region.class, em);
	}

	@Override
	@Transactional(readOnly = true)
	public Region findOneByPositionId(Long id) {
		final Region result = super.findOne((root, query, cb) -> {
			query.distinct(true);
			root.fetch(Region_.positions, JoinType.LEFT);
			return cb.equal(root.get(Region_.id), id);
		});
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public String findProvinceTempIdByRegionId(Long id) {
		final Region result = super.findOne((root, query, cb) -> {
			return cb.equal(root.get(Region_.id), id);
		});
		return result.getProvinceId();
	}

	@Override
	@Transactional(readOnly = true)
	public Long findOneByProvinceId(String provinceId) {
		final Region result = super.findOne((root, query, cb) -> {
			return cb.and(cb.equal(root.get(Region_.level), 1), cb.equal(root.get(Region_.provinceId), provinceId));

		});
		return result.getId();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> findByIds(Collection<Long> ids) {
		return super.findAll((root, query, cb) -> {
			return root.get(Region_.id).in(ids);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> findByCountyIds(Collection<String> countyIds) {
		return super.findAll((root, query, cb) -> {
			return root.get(Region_.countyId).in(countyIds);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> findAllByPositionId(Long id) {
		return super.findAll((root, query, cb) -> {
			return cb.equal(root.join(Region_.positions).get(PositionView_.id), id);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> findAllWithProvince() {
		List<Region> result = super.findAll((root, query, cb) -> {
			return cb.and(cb.equal(root.get(Region_.level), 1), cb.equal(root.get(Region_.isValid), 1));
		});
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> findAllByProvinceId(String provinceId) {
		List<Region> result = super.findAll((root, query, cb) -> {
			return cb.and(cb.equal(root.get(Region_.level), 2), cb.equal(root.get(Region_.isValid), 1), cb.equal(root.get(Region_.provinceId),
				provinceId));
		});
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> findAllByCityId(String provinceId, String cityId) {
		List<Region> result = super.findAll((root, query, cb) -> {
			return cb.and(cb.equal(root.get(Region_.level), 3), cb.equal(root.get(Region_.isValid), 1), cb.equal(root.get(Region_.provinceId),
				provinceId), cb.equal(root.get(Region_.cityId), cityId));
		});
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> findAllWithProvinceAndCityAndCounty(String provinceId) {
		List<Region> result = super.findAll((root, query, cb) -> {
			return cb.and(cb.equal(root.get(Region_.isValid), 1), cb.equal(root.get(Region_.provinceId), provinceId));
		});
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> findAllWithCityAndCounty(String provinceId, String cityId) {
		List<Region> result = super.findAll((root, query, cb) -> {
			return cb.and(cb.equal(root.get(Region_.isValid), 1), cb.equal(root.get(Region_.provinceId), provinceId), cb.equal(root.get(
				Region_.cityId), cityId));
		});
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Region findAllWithCounty(String provinceId, String cityId, String countyId) {
		Region result = super.findOne((root, query, cb) -> {
			return cb.and(cb.equal(root.get(Region_.isValid), 1), cb.equal(root.get(Region_.provinceId), provinceId), cb.equal(root.get(
				Region_.cityId), cityId), cb.equal(root.get(Region_.countyId), countyId));
		});
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> findByCityName(String cityName, String provinceName) {
		List<Region> result = super.findAll((root, query, cb) -> {
			return cb.and(cb.equal(root.get(Region_.cityName), cityName), cb.equal(root.get(Region_.provinceName), provinceName), cb.isNotNull(root
				.get(Region_.countyId)));
		});
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Region findByAddress(String countyName, String cityName, String provinceName) {
		Region result = super.findOne((root, query, cb) -> {
			return cb.and(cb.equal(root.get(Region_.countyName), countyName), cb.equal(root.get(Region_.cityName), cityName), cb.equal(root.get(
				Region_.provinceName), provinceName));
		});
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Region findOneByCountyId(String countyId) {
		Region result = super.findOne((root, query, cb) -> {
			return cb.equal(root.get(Region_.countyId), countyId);
		});
		return result;
	}

}
