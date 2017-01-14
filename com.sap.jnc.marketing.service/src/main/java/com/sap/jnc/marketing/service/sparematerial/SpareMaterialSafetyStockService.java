package com.sap.jnc.marketing.service.sparematerial;

import com.sap.jnc.marketing.dto.request.sparematerial.SpareMaterialDeliveryRequest;

public interface SpareMaterialSafetyStockService {

	String deliverById(Long safetyStockId, SpareMaterialDeliveryRequest spareMaterialDeliveryRequest);

}
