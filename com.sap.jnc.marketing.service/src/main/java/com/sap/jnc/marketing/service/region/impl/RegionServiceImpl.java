package com.sap.jnc.marketing.service.region.impl;

import com.sap.jnc.marketing.persistence.model.Region;
import com.sap.jnc.marketing.persistence.repository.RegionRepository;
import com.sap.jnc.marketing.service.region.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by guokai on 16/7/4.
 */
@Service
@Transactional(readOnly = false)
public class RegionServiceImpl implements RegionService {

	@Autowired
	RegionRepository regionRepository;

	@Override
	public List<Region> findByCityName(String cityName, String provinceName) {
		return regionRepository.findByCityName(cityName, provinceName);
	}

	@Override
	public Region findOneByAddress(String countyName, String cityName, String provinceName) {
		return regionRepository.findByAddress(countyName, cityName, provinceName);
	}
}
