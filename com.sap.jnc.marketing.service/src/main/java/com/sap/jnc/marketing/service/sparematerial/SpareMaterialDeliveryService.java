package com.sap.jnc.marketing.service.sparematerial;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.criteria.sparematerial.SpareMaterialDeliverySearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Position;
import com.sap.jnc.marketing.persistence.model.SpareMaterialDelivery;

public interface SpareMaterialDeliveryService {
	public SpareMaterialDelivery confirmReceipt(String positionId, Long id);

	public SpareMaterialDelivery cancelDelivery(Long id);

	public Page<SpareMaterialDelivery> pagedQuery(SpareMaterialDeliverySearchKeywordNode spareMaterialDeliverySearchKeywordNode, PageRequest pageRequest);

	public List<Position> findPositionIds();
}
