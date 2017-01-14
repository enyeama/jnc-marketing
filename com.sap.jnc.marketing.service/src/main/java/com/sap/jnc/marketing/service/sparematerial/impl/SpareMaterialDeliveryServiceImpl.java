package com.sap.jnc.marketing.service.sparematerial.impl;

import java.math.BigDecimal;import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.criteria.sparematerial.SpareMaterialDeliverySearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.SpareMaterialDeliveryStatus;
import com.sap.jnc.marketing.persistence.model.Position;
import com.sap.jnc.marketing.persistence.model.SpareMaterialDelivery;
import com.sap.jnc.marketing.persistence.model.SpareMaterialSafetyStock;
import com.sap.jnc.marketing.persistence.repository.SpareMaterialDeliveryRepository;
import com.sap.jnc.marketing.persistence.repository.SpareMaterialSafetyStockRepository;
import com.sap.jnc.marketing.service.sparematerial.SpareMaterialDeliveryService;

/*
 * @author Kay, Du I326950
 */

@Service
@Transactional
public class SpareMaterialDeliveryServiceImpl implements SpareMaterialDeliveryService {
	@Autowired
	private SpareMaterialDeliveryRepository spareMaterialDeliveryRepository;
	@Autowired
	private SpareMaterialSafetyStockRepository spareMaterialSafetyStockRepository;

	@Override
	public SpareMaterialDelivery confirmReceipt(String positionId, Long id) {
		SpareMaterialDelivery spareMaterialDelivery = spareMaterialDeliveryRepository.findOne(id);
		String materialId = spareMaterialDelivery.getProduct().getId();
		BigDecimal deliveryQuantityValue = spareMaterialDelivery.getDeliveryQuantity().getValue();

		// Update safetyStock table
		SpareMaterialSafetyStock spareMaterialSafetyStock = spareMaterialSafetyStockRepository.queryByPositionExternalIdAndMaterialId(positionId,
			materialId);
		spareMaterialSafetyStock.getInTransitStockQuantity().setValue(
			spareMaterialSafetyStock.getInTransitStockQuantity().getValue().subtract(deliveryQuantityValue));
		spareMaterialSafetyStockRepository.saveAndFlush(spareMaterialSafetyStock);
		
		// Update delivery table
		spareMaterialDelivery.setDeliveryStatus(SpareMaterialDeliveryStatus.DELIVERED);
		spareMaterialDelivery.setAcknowledgementDate(Calendar.getInstance());
		return spareMaterialDeliveryRepository.saveAndFlush(spareMaterialDelivery);
	}

	@Override
	public SpareMaterialDelivery cancelDelivery(Long id) {
		SpareMaterialDelivery spareMaterialDelivery = spareMaterialDeliveryRepository.findOne(id);
		String materialId = spareMaterialDelivery.getProduct().getId();
		String positionId = spareMaterialDelivery.getPosition().getExternalId();
		BigDecimal deliveryQuantityValue = spareMaterialDelivery.getDeliveryQuantity().getValue();

		// Update safetyStock table
		SpareMaterialSafetyStock spareMaterialSafetyStock = spareMaterialSafetyStockRepository.queryByPositionExternalIdAndMaterialId(positionId,
			materialId);
		spareMaterialSafetyStock.getInTransitStockQuantity().setValue(
			spareMaterialSafetyStock.getInTransitStockQuantity().getValue().subtract(deliveryQuantityValue));
		spareMaterialSafetyStock.getTotalDeliveredQuantity().setValue(
			spareMaterialSafetyStock.getTotalDeliveredQuantity().getValue().subtract(deliveryQuantityValue));
		spareMaterialSafetyStockRepository.saveAndFlush(spareMaterialSafetyStock);
		
		// Update delivery table
		spareMaterialDelivery.setDeliveryStatus(SpareMaterialDeliveryStatus.RETURNED);
		return spareMaterialDeliveryRepository.saveAndFlush(spareMaterialDelivery);
	}

	@Override
	public Page<SpareMaterialDelivery> pagedQuery(SpareMaterialDeliverySearchKeywordNode spareMaterialDeliverySearchKeywordNode, PageRequest pageRequest) {
		return spareMaterialDeliveryRepository.pagedQuery(spareMaterialDeliverySearchKeywordNode, pageRequest);
	}

	@Override
	public List<Position> findPositionIds() {
		List<SpareMaterialDelivery> spareMaterialDeliveryList = spareMaterialDeliveryRepository.findPositionIds();
		List<Position> positionList = new ArrayList<>();
		
		for(int i = 0; i < spareMaterialDeliveryList.size(); i++) {
			Position position = spareMaterialDeliveryList.get(i).getPosition();
			
			positionList.add(position);
		}
		
		return positionList;
	}
}
