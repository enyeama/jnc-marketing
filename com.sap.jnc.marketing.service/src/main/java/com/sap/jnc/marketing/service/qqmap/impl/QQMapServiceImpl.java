package com.sap.jnc.marketing.service.qqmap.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.sap.jnc.marketing.infrastructure.shared.SystemConfig;
import com.sap.jnc.marketing.service.qqmap.QQMapService;

/**
 * 腾讯地图Service
 * 
 * @author I323560
 */
@Service
@Transactional
public class QQMapServiceImpl implements QQMapService {

	private static Logger LOGGER = LoggerFactory.getLogger(QQMapServiceImpl.class);

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	@Transactional(readOnly = true)
	public String getLocation(String lat, String lng) {
		String GEOCODER_URL = systemConfig.getTencentApi() + "?key=" + systemConfig.getTencentKey()
				+ "&location={lat},{lng}";
		String locationStr = restTemplate.getForObject(GEOCODER_URL, String.class, lat, lng);

		LOGGER.debug("location for GPS(lat:" + lat + ", lng" + lng + "):\n" + locationStr);

		return locationStr;
	}

}
