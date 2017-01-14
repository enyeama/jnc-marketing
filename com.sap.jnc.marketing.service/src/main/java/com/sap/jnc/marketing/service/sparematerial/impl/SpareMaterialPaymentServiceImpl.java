package com.sap.jnc.marketing.service.sparematerial.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sap.jnc.marketing.dto.request.sparematerial.SpareMaterialPaymentWechatRequest;
import com.sap.jnc.marketing.persistence.criteria.sparematerial.SpareMaterialPaymentSearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.SpareMaterialActivityType;
import com.sap.jnc.marketing.persistence.enumeration.SpareMaterialVerificationStatus;
import com.sap.jnc.marketing.persistence.model.DifferenceQuantity;
import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.PaidQuantity;
import com.sap.jnc.marketing.persistence.model.Position;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.SpareMaterialPayment;
import com.sap.jnc.marketing.persistence.model.SpareMaterialSafetyStock;
import com.sap.jnc.marketing.persistence.model.VerifiedQuantity;
import com.sap.jnc.marketing.persistence.repository.EmployeeRepository;
import com.sap.jnc.marketing.persistence.repository.PositionRepository;
import com.sap.jnc.marketing.persistence.repository.PositionViewRepository;
import com.sap.jnc.marketing.persistence.repository.ProductRepository;
import com.sap.jnc.marketing.persistence.repository.SpareMaterialPaymentRepository;
import com.sap.jnc.marketing.persistence.repository.SpareMaterialSafetyStockRepository;
import com.sap.jnc.marketing.service.sparematerial.SpareMaterialPaymentService;

/*
 * @author Kay, Du I326950
 */

@Service
@Transactional
public class SpareMaterialPaymentServiceImpl implements SpareMaterialPaymentService {
	@Autowired
	private SpareMaterialPaymentRepository spareMaterialPaymentRepository;
	@Autowired
	private SpareMaterialSafetyStockRepository spareMaterialSafetyStockRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private PositionViewRepository positionViewRepository;
	@Autowired
	private PositionRepository positionRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	
	private final static String QUANTITY_UNIT = "瓶";
	
	@Override
	@Transactional(readOnly = true)
	public List<Product> findSpareMaterialsByPositionId(String positionId) {
		List<SpareMaterialSafetyStock> spareMaterialSafetyStockList = spareMaterialSafetyStockRepository.findAllMaterialsByPositionExternalId(positionId);
		List<Product> spareMaterialsList = new ArrayList<>();
		
		for(int i = 0; i < spareMaterialSafetyStockList.size(); i++) {
			Product spareMaterial = spareMaterialSafetyStockList.get(i).getProduct();
			
			spareMaterialsList.add(spareMaterial);
		}
		
		return spareMaterialsList;
	}

	@Override
	public SpareMaterialPayment updateConsumptionQuantityByPositionIdAndMaterialId(String positionId, String materialId, SpareMaterialPaymentWechatRequest spareMaterialPaymentRequest) {
		SpareMaterialSafetyStock spareMaterialSafetyStock = spareMaterialSafetyStockRepository.queryByPositionExternalIdAndMaterialId(positionId, materialId);
		Calendar currentTime = Calendar.getInstance();
		SpareMaterialVerificationStatus verificationStatus = SpareMaterialVerificationStatus.NOT_VERIFIED;
		
		VerifiedQuantity verifiedQuantity = new VerifiedQuantity();
		verifiedQuantity.setUnit(QUANTITY_UNIT);
		verifiedQuantity.setValue(BigDecimal.ZERO);
		
		DifferenceQuantity differenceQuantity = new DifferenceQuantity();
		differenceQuantity.setUnit(QUANTITY_UNIT);
		differenceQuantity.setValue(BigDecimal.ZERO);
		
		PaidQuantity paidQuantity = new PaidQuantity();
		paidQuantity.setUnit(QUANTITY_UNIT);
		paidQuantity.setValue(spareMaterialPaymentRequest.getPaidQuantityValue());
		
		Product spareMaterial = productRepository.findOne(spareMaterialPaymentRequest.getMaterialId());
		PositionView positionView = positionViewRepository.findOne(positionId);
		Position position = positionRepository.findOne(positionView.getId());
		Employee employee = employeeRepository.findOne(positionView.getEmployees().get(0).getId());
		
		// Update safetyStock table
		spareMaterialSafetyStock.getTotalPaidQuantity().setValue(spareMaterialSafetyStock.getTotalPaidQuantity().getValue().add(spareMaterialPaymentRequest.getPaidQuantityValue()));
		spareMaterialSafetyStockRepository.saveAndFlush(spareMaterialSafetyStock);
		
		// Update payment table
		SpareMaterialPayment spareMaterialPayment = new SpareMaterialPayment();
		spareMaterialPayment.setPaymentDate(currentTime);
		spareMaterialPayment.setVerificationStatus(verificationStatus);
		spareMaterialPayment.setVerifiedQuantity(verifiedQuantity);
		spareMaterialPayment.setDifferenceQuantity(differenceQuantity);
		spareMaterialPayment.setPaidQuantity(paidQuantity);
		spareMaterialPayment.setManualAdjustmentQuantity(BigDecimal.ZERO);
		spareMaterialPayment.setPaymentComment(spareMaterialPaymentRequest.getPaymentComment());
		spareMaterialPayment.setProduct(spareMaterial);
		spareMaterialPayment.setEmployee(employee);
		spareMaterialPayment.setPosition(position);
		spareMaterialPayment.setActivityType(SpareMaterialActivityType.UNKNOWN);
		return spareMaterialPaymentRepository.saveAndFlush(spareMaterialPayment);
	}

	@Override
	public Page<SpareMaterialPayment> pagedQuery(SpareMaterialPaymentSearchKeywordNode keywords, PageRequest pageRequest) {
		return spareMaterialPaymentRepository.pagedQuery(keywords, pageRequest);
	}

}
