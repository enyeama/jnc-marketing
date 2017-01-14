package com.sap.jnc.marketing.api.wechat.web.controller.region;

import com.sap.jnc.marketing.dto.response.region.RegionResponse;
import com.sap.jnc.marketing.service.region.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by guokai on 16/7/4.
 */
@RestController
@RequestMapping("region")
public class RegionController {

	@Autowired
	RegionService regionService;

	@RequestMapping("list")
	public List<RegionResponse> regionList(String cityName, String provinceName) {
		return regionService.findByCityName(cityName, provinceName).stream().map(a -> new RegionResponse(a)).collect(Collectors.toList());
	}

	@RequestMapping("one")
	public RegionResponse findOne(String countyName, String cityName, String provinceName) {
		return new RegionResponse(regionService.findOneByAddress(countyName, cityName, provinceName));
	}
}
