package com.sap.jnc.marketing.service.region;

import com.sap.jnc.marketing.persistence.model.Region;

import java.util.List;

/**
 * Created by guokai on 16/7/4.
 */
public interface RegionService {

	List<Region> findByCityName(String cityName, String provinceName);

	Region findOneByAddress(String countyName, String cityName, String provinceName);
}
