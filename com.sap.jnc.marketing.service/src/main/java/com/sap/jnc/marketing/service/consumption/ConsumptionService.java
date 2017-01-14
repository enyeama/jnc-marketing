package com.sap.jnc.marketing.service.consumption;

import java.util.List;

import com.sap.jnc.marketing.dto.request.consumption.DFActionRequest;
import com.sap.jnc.marketing.dto.response.consumption.DFQueryResponse;

public interface ConsumptionService {

	/**
	 * 什么叫DF？请用全名
	 * 
	 * @param id
	 * @param positionId
	 * @return
	 */
	public List<DFQueryResponse> findAllDF(String id, String positionId);

	public DFQueryResponse update(String id, DFActionRequest dfActionRequest);

}
