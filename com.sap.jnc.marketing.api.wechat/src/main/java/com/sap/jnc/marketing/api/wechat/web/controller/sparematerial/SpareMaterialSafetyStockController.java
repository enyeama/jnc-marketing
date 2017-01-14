package com.sap.jnc.marketing.api.wechat.web.controller.sparematerial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.dto.request.sparematerial.SpareMaterialDeliveryRequest;
import com.sap.jnc.marketing.service.sparematerial.SpareMaterialSafetyStockService;

@RestController
public class SpareMaterialSafetyStockController {
	@Autowired
	private SpareMaterialSafetyStockService spareMaterialSafetyStockService;
	
	@RequestMapping(value = "/safetystocks/{safetyStockId}", method = RequestMethod.PUT)
	public @ResponseBody String deliverById(@PathVariable("safetyStockId") Long safetyStockId, @RequestBody SpareMaterialDeliveryRequest spareMaterialDeliveryRequest) {
		return spareMaterialSafetyStockService.deliverById(safetyStockId, spareMaterialDeliveryRequest);
	}
}
