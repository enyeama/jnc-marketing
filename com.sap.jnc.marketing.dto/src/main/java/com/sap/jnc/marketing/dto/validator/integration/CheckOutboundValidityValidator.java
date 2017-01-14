/**
 * This Class is used to validate the input parameter from restful API. It will check the class JNCOuntboundInfo for create and update via DB query
 * 
 * @author James Jiang
 */
package com.sap.jnc.marketing.dto.validator.integration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sap.jnc.marketing.dto.request.integration.JNCOuntboundInfo;
import com.sap.jnc.marketing.dto.request.integration.JNCOuntboundInfo.JNCOuntboundInfoItem;
import com.sap.jnc.marketing.persistence.enumeration.MovementType;
import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.model.IndividualProduct;
import com.sap.jnc.marketing.persistence.model.Logistic;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.repository.DealerRepository;
import com.sap.jnc.marketing.persistence.repository.IndividualProductRepository;
import com.sap.jnc.marketing.persistence.repository.LogisticRepository;
import com.sap.jnc.marketing.persistence.repository.ProductRepository;

public class CheckOutboundValidityValidator implements ConstraintValidator<CheckOutboundValidity, JNCOuntboundInfo> {

	@Autowired
	private IndividualProductRepository individualProductRepository;

	@Autowired
	private DealerRepository dealerRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private LogisticRepository logisticRepository;

	@Override
	public void initialize(CheckOutboundValidity constraintAnnotation) {

	}

	@Override
	public boolean isValid(JNCOuntboundInfo value, ConstraintValidatorContext context) {
		String operation = validateOperation(value.getItems(), context);
		if (operation == null) {
			return false;
		}

		Set<String> caseIdSet = new HashSet<>();
		Set<String> materialIdSet = new HashSet<>();
		Set<String> dealerIdSet = new HashSet<>();
		Map<String, IndividualProduct> individualProductMap = new HashMap<>();
		Map<String, Product> productMap = new HashMap<>();
		Map<String, Dealer> dealerMap = new HashMap<>();
		Map<String, Logistic> logisticMap = new HashMap<>();
		Map<String, Logistic> logisticDealerInMap = new HashMap<>();
		for (int i = 0; i < value.getItems().size(); i++) {
			JNCOuntboundInfo.JNCOuntboundInfoItem item = value.getItems().get(i);
			String caseId = item.getCaseID();
			String erpOrderId = item.getErpOrderID();
			String dmsOrderId = item.getDmsOrderID();
			String orderItemId = item.getOrderItemID();
			String materialId = item.getMaterialID();
			String dealerId = item.getDealerID();

			if (StringUtils.isBlank(caseId)) {
				return false;
			}
			if ("C".equalsIgnoreCase(operation) || "U".equalsIgnoreCase(operation)) {
				if (StringUtils.isBlank(erpOrderId) || StringUtils.isBlank(dmsOrderId) || StringUtils.isBlank(orderItemId) || StringUtils.isBlank(
					materialId) || StringUtils.isBlank(dealerId)) {
					return false;
				}
			}
			caseIdSet.add(caseId);
			materialIdSet.add(materialId);
			dealerIdSet.add(dealerId);
		}

		List<Logistic> logisticList = logisticRepository.findByCaseIdsAndMovementType(caseIdSet, MovementType.JNC_OUT);
		for (Logistic logistic : logisticList) {
			logisticMap.put(logistic.getIndividualProduct().getCaseId(), logistic);
		}
		if ("C".equalsIgnoreCase(operation)) {
			List<IndividualProduct> individualProductList = individualProductRepository.findByCaseIds(caseIdSet);
			for (IndividualProduct individualProduct : individualProductList) {
				individualProductMap.put(individualProduct.getCaseId(), individualProduct);
			}
		}

		if ("U".equalsIgnoreCase(operation) || "C".equalsIgnoreCase(operation)) {
			List<Product> productList = productRepository.findByMaterialIds(materialIdSet);
			for (Product product : productList) {
				productMap.put(product.getId(), product);
			}

			List<Dealer> dealerList = dealerRepository.findDealerByExternalIds(dealerIdSet);
			for (Dealer dealer : dealerList) {
				dealerMap.put(dealer.getExternalId(), dealer);
			}
		}
		if ("D".equalsIgnoreCase(operation)) {
			List<Logistic> logisticDealerInList = logisticRepository.findByCaseIdsAndMovementType(caseIdSet, MovementType.DEALER_IN);
			for (Logistic logistic : logisticDealerInList) {
				logisticDealerInMap.put(logistic.getIndividualProduct().getCaseId(), logistic);
			}
		}

		boolean hasError = false;
		for (int i = 0; i < value.getItems().size(); i++) {
			JNCOuntboundInfo.JNCOuntboundInfoItem item = value.getItems().get(i);
			String caseId = item.getCaseID();
			String materialId = item.getMaterialID();
			String dealerId = item.getDealerID();

			if ("C".equalsIgnoreCase(operation)) {
				if (CollectionUtils.isNotEmpty(logisticList)) {
					context.buildConstraintViolationWithTemplate("箱码已存在库存").addPropertyNode("items[" + i + "].caseID").addConstraintViolation();
					hasError = true;
				}
				if (!individualProductMap.containsKey(caseId)) {
					context.buildConstraintViolationWithTemplate("箱码不存在").addPropertyNode("items[" + i + "].caseID").addConstraintViolation();
					hasError = true;
				}
			}

			if ("C".equalsIgnoreCase(operation) || "U".equalsIgnoreCase(operation)) {
				// TODO should check material consistency with individual product?
				if (!productMap.containsKey(materialId)) {
					context.buildConstraintViolationWithTemplate("物料号不存在").addPropertyNode("items[" + i + "].materialID").addConstraintViolation();
					hasError = true;
				}
				if (!dealerMap.containsKey(dealerId)) {
					context.buildConstraintViolationWithTemplate("经销商不存在").addPropertyNode("items[" + i + "].dealerID").addConstraintViolation();
					hasError = true;
				}
			}

			if ("D".equalsIgnoreCase(operation) || "U".equalsIgnoreCase(operation)) {
				if (!logisticList.contains(caseId)) {
					context.buildConstraintViolationWithTemplate("箱码不存在于库存").addPropertyNode("items[" + i + "].caseID").addConstraintViolation();
					hasError = true;
				}
				if (logisticDealerInMap.containsKey(caseId)) {
					context.buildConstraintViolationWithTemplate("经销商已收货").addPropertyNode("items[" + i + "].caseID").addConstraintViolation();
					hasError = true;
				}
			}

		}

		return !hasError;
	}

	private String validateOperation(List<JNCOuntboundInfoItem> items, ConstraintValidatorContext context) {
		String operation = null;
		for (int i = 0; i < items.size(); i++) {
			JNCOuntboundInfoItem item = items.get(i);
			String op = item.getCud();
			if (!"C".equalsIgnoreCase(op) && !"U".equalsIgnoreCase(op) && !"D".equalsIgnoreCase(op)) {
				context.buildConstraintViolationWithTemplate("cud不正确").addPropertyNode("items[" + i + "].cud").addConstraintViolation();
				return null;
			}
			if (operation == null) {
				operation = op;
			}
			else if (!operation.equalsIgnoreCase(op)) {
				context.buildConstraintViolationWithTemplate("cud不唯一").addPropertyNode("items[" + i + "].cud").addConstraintViolation();
				return null;
			}
		}
		return operation;
	}

}
