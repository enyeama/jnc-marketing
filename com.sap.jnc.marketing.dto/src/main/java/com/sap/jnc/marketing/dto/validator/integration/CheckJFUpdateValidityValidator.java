/**
 * This Class is used to validate the input parameter from restful API. It will check the class JfDeleteRequest fields to ensure the correct operation
 * of update in DB
 * 
 * @author James Jiang
 */
package com.sap.jnc.marketing.dto.validator.integration;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sap.jnc.marketing.dto.request.integration.JFUpdateRequest;
import com.sap.jnc.marketing.dto.request.integration.JFUpdateRequest.JFUpdateDelRequestItem;
import com.sap.jnc.marketing.persistence.enumeration.MovementType;
import com.sap.jnc.marketing.persistence.model.IndividualProduct;
import com.sap.jnc.marketing.persistence.model.Logistic;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.repository.IndividualProductRepository;
import com.sap.jnc.marketing.persistence.repository.LogisticRepository;
import com.sap.jnc.marketing.persistence.repository.ProductRepository;

public class CheckJFUpdateValidityValidator implements ConstraintValidator<CheckValidity, JFUpdateRequest> {
	@Autowired
	private IndividualProductRepository individualProductRepository;

	@Autowired
	private LogisticRepository logisticRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public void initialize(CheckValidity constraintAnnotation) {
	}

	/*
	 * this Validator will check the DB consistency before the real operation in DB
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(JFUpdateRequest dto, ConstraintValidatorContext context) {
		boolean returnValue = true;
		for (int i = 0; i < dto.getItems().size(); i++) {
			JFUpdateDelRequestItem item = dto.getItems().get(i);

			String bottleIID = getBottleIID(item.getBottleIID());
			if (StringUtils.isBlank(bottleIID))
				return false;
			String materialID = item.getMaterialID();
			String boxID = item.getBoxID();
			String bottleOID = item.getBottleOID();

			if (StringUtils.isBlank(bottleIID) || StringUtils.isBlank(boxID) || StringUtils.isBlank(bottleOID) || StringUtils.isBlank(materialID))
				return false;

			IndividualProduct individualProductFind = individualProductRepository.findOne(bottleIID);
			if (individualProductFind == null) {
				context.buildConstraintViolationWithTemplate("瓶内码不存在").addPropertyNode("items[" + i + "].bottleIID").addConstraintViolation();
				returnValue = false;
				return false;
			}

			List<IndividualProduct> individualProductsOfBoxID = individualProductRepository.findByBoxID(boxID);
			if (individualProductFind != null && individualProductsOfBoxID != null && individualProductsOfBoxID.size() > 0) {
				if (!individualProductsOfBoxID.get(0).getCapInnerCode().equals(individualProductFind.getCapInnerCode())) {
					context.buildConstraintViolationWithTemplate("盒码已经存在").addPropertyNode("items[" + i + "].boxID").addConstraintViolation();
					returnValue = false;
				}
			}

			List<IndividualProduct> individualProductsOfBottleOID = individualProductRepository.findByBottleOID(bottleOID);
			if (individualProductFind != null && individualProductsOfBottleOID != null && individualProductsOfBottleOID.size() > 0) {
				if (!individualProductsOfBottleOID.get(0).getCapInnerCode().equals(individualProductFind.getCapInnerCode())) {
					context.buildConstraintViolationWithTemplate("瓶外码已经存在").addPropertyNode("items[" + i + "].bottleOID").addConstraintViolation();
					returnValue = false;
				}
			}

			Product product = productRepository.findOne(materialID);
			if (product == null) {
				context.buildConstraintViolationWithTemplate("物料号不存在").addPropertyNode("items[" + i + "].materialID").addConstraintViolation();
				returnValue = false;
			}

			// check logistic outbound already
			if (checkAlreadyOutbound(bottleIID)) {
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

	private boolean checkAlreadyOutbound(String bottleIIDPara) {
		List<Logistic> logistics = logisticRepository.findByCapInnerCodeAndMovementType(bottleIIDPara, MovementType.JNC_OUT);
		if (logistics != null && logistics.size() > 0) {
			return true;
		}
		return false;
	}
}