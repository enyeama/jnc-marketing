package com.sap.jnc.marketing.api.admin.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.dto.response.region.RegionNodeResponse;
import com.sap.jnc.marketing.service.region.RegionNodeService;

/**
 * 层级结构的区域数据
 * 
 * @author I300934 Ray Lv
 */
@RestController
public class RegionNodeController extends GeneralController {

	private static Logger LOGGER = LoggerFactory.getLogger(RegionNodeController.class);
	
	@Autowired
	private RegionNodeService regionNodeService;

	@RequestMapping(value = "/regionnodes", method = RequestMethod.GET)
	public List<RegionNodeResponse> getRegionNodes() {
		return regionNodeService.findAllRegionNodes();
	}
}
