/**
 * This Class is used to validate the input parameter from restful API. It will check the class JfDeleteRequest fields to ensure the correct operation
 * of deletion in DB
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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sap.jnc.marketing.dto.request.integration.JFDeleteRequest;
import com.sap.jnc.marketing.dto.request.integration.JFDeleteRequest.JFDelRequestItem;
import com.sap.jnc.marketing.persistence.enumeration.MovementType;
import com.sap.jnc.marketing.persistence.model.IndividualProduct;
import com.sap.jnc.marketing.persistence.model.Logistic;
import com.sap.jnc.marketing.persistence.repository.IndividualProductRepository;
import com.sap.jnc.marketing.persistence.repository.LogisticRepository;

public class CheckJFDeleteValidityValidator implements ConstraintValidator<CheckValidity, JFDeleteRequest> {
	@Autowired
	private IndividualProductRepository individualProductRepository;
	@Autowired
	private LogisticRepository logisticRepository;

	@Override
	public void initialize(CheckValidity constraintAnnotation) {
	}

	/*
	 * this Validator will check the DB consistency before the real operation in DB
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(JFDeleteRequest dto, ConstraintValidatorContext context) {
		boolean returnValue = true;
		List<JFDelRequestItem> items = dto.getItems();
		Set<String> capInnerCodeSet = new HashSet<>();
		Map<String, IndividualProduct> individualProductMap = new HashMap<>();
		Map<String, Logistic> logisticMap = new HashMap<>();
		for (int i = 0; i < items.size(); i++) {
			JFDelRequestItem item = items.get(0);
			String bottleIID = getBottleIID(item.getBottleIID());
			if (StringUtils.isBlank(bottleIID)) {
				return false;
			}
			capInnerCodeSet.add(bottleIID);
		}

		List<IndividualProduct> individualProductList = individualProductRepository.findByCapInnerCodes(capInnerCodeSet);
		List<Logistic> logisticList = logisticRepository.findByCapInnerCodesAndMovementType(capInnerCodeSet, MovementType.JNC_OUT);
		for (IndividualProduct individualProduct : individualProductList) {
			individualProductMap.put(individualProduct.getCapInnerCode(), individualProduct);
		}
		for (Logistic logistic : logisticList) {
			logisticMap.put(logistic.getIndividualProduct().getCapInnerCode(), logistic);
		}

		for (int i = 0; i < items.size(); i++) {
			JFDelRequestItem item = items.get(0);
			String bottleIID = getBottleIID(item.getBottleIID());
			if (!individualProductMap.containsKey(bottleIID)) {
				context.buildConstraintViolationWithTemplate("瓶内码不存在").addPropertyNode("items[" + i + "].bottleIID").addConstraintViolation();
				returnValue = false;
			}
			if (logisticMap.containsKey(bottleIID)) {
				context.buildConstraintViolationWithTemplate("剑南春已出货").addPropertyNode("items[" + i + "].bottleIID").addConstraintViolation();
				returnValue = false;
			}
		}
		return returnValue;
	}

	private String getBottleIID(String bottleIIDPara) {
		if (bottleIIDPara != null && bottleIIDPara.length() > 18) {
			String bottleIID = bottleIIDPara.substring(bottleIIDPara.length() - 18);
			return bottleIID;
		}
		else {
			return null;
		}
	}
}