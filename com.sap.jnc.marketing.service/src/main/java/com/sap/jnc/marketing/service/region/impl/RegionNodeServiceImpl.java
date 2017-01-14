package com.sap.jnc.marketing.service.region.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.response.region.RegionNodeResponse;
import com.sap.jnc.marketing.persistence.enumeration.RegionLevel;
import com.sap.jnc.marketing.persistence.model.RegionNode;
import com.sap.jnc.marketing.persistence.repository.RegionNodeRepository;
import com.sap.jnc.marketing.service.region.RegionNodeService;

/**
 * @author I300934 Ray Lv
 */
@Service
@Transactional(readOnly = false)
public class RegionNodeServiceImpl implements RegionNodeService {

	@Autowired
	RegionNodeRepository regionNodeRepository;

	@Override
	public List<RegionNodeResponse> findAllRegionNodes() {

		List<RegionNodeResponse> regionNodeResponses = new ArrayList<RegionNodeResponse>();

		for (RegionNode regionNode : regionNodeRepository.findAllByLevel(RegionLevel.PROVINCE)) {
			regionNodeResponses.add(new RegionNodeResponse(regionNode));
		}
		return regionNodeResponses;
	}
}
