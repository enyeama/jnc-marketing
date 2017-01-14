package com.sap.jnc.marketing.service.region;

import java.util.List;

import com.sap.jnc.marketing.dto.response.region.RegionNodeResponse;

/**
 * @author I300934 Ray Lv
 */
public interface RegionNodeService {

	public List<RegionNodeResponse> findAllRegionNodes();

}
