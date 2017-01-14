package com.sap.jnc.marketing.service.sparematerial.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.sparematerial.SpareMaterialDeliveryRequest;
import com.sap.jnc.marketing.persistence.enumeration.GiftListStatus;
import com.sap.jnc.marketing.persistence.enumeration.SpareMaterialDeliveryStatus;
import com.sap.jnc.marketing.persistence.model.DeliveryQuantity;
import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.Position;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.SpareMaterialDelivery;
import com.sap.jnc.marketing.persistence.model.SpareMaterialSafetyStock;
import com.sap.jnc.marketing.persistence.repository.EmployeeRepository;
import com.sap.jnc.marketing.persistence.repository.PositionRepository;
import com.sap.jnc.marketing.persistence.repository.SpareMaterialDeliveryRepository;
import com.sap.jnc.marketing.persistence.repository.SpareMaterialSafetyStockRepository;
import com.sap.jnc.marketing.service.sparematerial.SpareMaterialSafetyStockService;

@Service
@Transactional
public class SpareMaterialSafetyStockServiceImpl implements SpareMaterialSafetyStockService {
	@Autowired
	private SpareMaterialSafetyStockRepository spareMaterialSafetyStockRepository;
	@Autowired
	private SpareMaterialDeliveryRepository spareMaterialDeliveryRepository;
	@Autowired
	private PositionRepository positionRepository;
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public String deliverById(Long safetyStockId, SpareMaterialDeliveryRequest spareMaterialDeliveryRequest) {
		// TODO Auto-generated method stub
		
		SpareMaterialSafetyStock spareMaterialSafetyStock = spareMaterialSafetyStockRepository.findOne(safetyStockId);
		Employee employee = employeeRepository.findOne(spareMaterialSafetyStock.getPosition().getEmployees().get(0).getId());
		Position position = positionRepository.findOne(spareMaterialSafetyStock.getPosition().getId());
		Product product = spareMaterialSafetyStock.getProduct();
		DeliveryQuantity deliveryQuantity = new DeliveryQuantity();
		SpareMaterialDelivery spareMaterialDelivery = new SpareMaterialDelivery();
		
		// Update SafetyStock table
		spareMaterialSafetyStock.getInTransitStockQuantity().setValue(spareMaterialSafetyStock.getInTransitStockQuantity().getValue().add(spareMaterialDeliveryRequest.getDeliveryQuantityValue()));
		spareMaterialSafetyStock.getTotalDeliveredQuantity().setValue(spareMaterialSafetyStock.getTotalDeliveredQuantity().getValue().add(spareMaterialDeliveryRequest.getDeliveryQuantityValue()));
		spareMaterialSafetyStockRepository.saveAndFlush(spareMaterialSafetyStock);
		
		// Add a new record in Delivery table
		deliveryQuantity.setUnit("瓶");
		deliveryQuantity.setValue(spareMaterialDeliveryRequest.getDeliveryQuantityValue());
		spareMaterialDelivery.setDeliveryStatus(SpareMaterialDeliveryStatus.IN_TRANSIT);
		spareMaterialDelivery.setGiftListStatus(GiftListStatus.NOT_EXPORTED);
		spareMaterialDelivery.setDeliveryQuantity(deliveryQuantity);
		spareMaterialDelivery.setDeliveryDate(Calendar.getInstance());
		spareMaterialDelivery.setComment(spareMaterialDeliveryRequest.getDeliveryComment());
		spareMaterialDelivery.setProduct(product);
		spareMaterialDelivery.setPosition(position);
		spareMaterialDelivery.setEmployee(employee);
		spareMaterialDeliveryRepository.saveAndFlush(spareMaterialDelivery);
		
		return null;
	}

}
