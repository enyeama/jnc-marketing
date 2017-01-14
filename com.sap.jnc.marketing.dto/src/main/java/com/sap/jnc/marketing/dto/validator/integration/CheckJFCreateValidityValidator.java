/**
 * This Class is used to validate the input parameter from restful API. It will check the class JfCreateRequest fields to ensure the correct operation
 * of creation in DB
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

import com.sap.jnc.marketing.dto.request.integration.JFMergeRequest;
import com.sap.jnc.marketing.dto.request.integration.JFMergeRequest.JfMergeRequestItem;
import com.sap.jnc.marketing.persistence.model.IndividualProduct;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.repository.IndividualProductRepository;
import com.sap.jnc.marketing.persistence.repository.ProductRepository;

public class CheckJFCreateValidityValidator implements ConstraintValidator<CheckValidity, JFMergeRequest> {
	@Autowired
	private IndividualProductRepository individualProductRepository;

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
	public boolean isValid(JFMergeRequest dto, ConstraintValidatorContext context) {
		boolean returnValue = true;
		List<JfMergeRequestItem> items = dto.getItems();
		Set<String> boxIdSet = new HashSet<>();
		Set<String> capInnerCodeSet = new HashSet<>();
		Set<String> capOuterCodeSet = new HashSet<>();
		Set<String> productIdSet = new HashSet<>();

		for (JfMergeRequestItem item : items) {
			String boxID = item.getBoxID();
			String bottleOID = item.getBottleOID();
			String bottleIID = getBottleIID(item.getBottleIID());
			String materialID = item.getMaterialID();
			if (StringUtils.isBlank(bottleIID) || StringUtils.isBlank(boxID) || StringUtils.isBlank(bottleOID) || StringUtils.isBlank(materialID)) {
				return false;
			}
			capInnerCodeSet.add(bottleIID);
			boxIdSet.add(item.getBoxID());
			capOuterCodeSet.add(item.getBottleOID());
			productIdSet.add(materialID);
		}
		Map<String, IndividualProduct> individualProductMapOfBox = new HashMap<>();
		Map<String, IndividualProduct> individualProductMapOfCapInnerCode = new HashMap<>();
		Map<String, IndividualProduct> individualProductMapOfCapOuterCode = new HashMap<>();
		Map<String, Product> productMap = new HashMap<>();

		try {
			List<IndividualProduct> individualProductListOfBox = individualProductRepository.findByBoxIds(boxIdSet);
			List<IndividualProduct> individualProductListOfCapInnerCode = individualProductRepository.findByCapInnerCodes(capInnerCodeSet);
			List<IndividualProduct> individualProductListOfCapOuterCode = individualProductRepository.findByCapOuterCodes(capOuterCodeSet);
			List<Product> productList = productRepository.findByMaterialIds(productIdSet);

			setAllListToMap(individualProductListOfBox, individualProductListOfCapInnerCode, individualProductListOfCapOuterCode, productList,
				individualProductMapOfBox, individualProductMapOfCapInnerCode, individualProductMapOfCapOuterCode, productMap);
		}
		catch (RuntimeException e) {
			return false;
		}
		for (int i = 0; i < items.size(); i++) {
			JfMergeRequestItem item = dto.getItems().get(i);

			String boxID = item.getBoxID();
			String bottleOID = item.getBottleOID();
			String bottleIID = getBottleIID(item.getBottleIID());
			String materialID = item.getMaterialID();

			// TODO try to combine all the check in one query. problem: cannot find which was already existed.

			if (individualProductMapOfCapInnerCode.containsKey(bottleIID)) {
				context.buildConstraintViolationWithTemplate("瓶内码已经存在").addPropertyNode("items[" + i + "].bottleIID").addConstraintViolation();
				returnValue = false;
			}

			if (individualProductMapOfBox.containsKey(boxID)) {
				context.buildConstraintViolationWithTemplate("盒码已经存在").addPropertyNode("items[" + i + "].boxID").addConstraintViolation();
				returnValue = false;
			}

			if (individualProductMapOfCapOuterCode.containsKey(bottleOID)) {
				context.buildConstraintViolationWithTemplate("瓶外码已经存在").addPropertyNode("items[" + i + "].bottleOID").addConstraintViolation();
				returnValue = false;
			}

			if (!productMap.containsKey(materialID)) {
				context.buildConstraintViolationWithTemplate("物料号不存在").addPropertyNode("items[" + i + "].materialID").addConstraintViolation();
				returnValue = false;
			}

		}
		// TODO check bonus existed

		return returnValue;

	}

	private void setAllListToMap(List<IndividualProduct> individualProductListOfBox, List<IndividualProduct> individualProductListOfCapInnerCode,
		List<IndividualProduct> individualProductListOfCapOuterCode, List<Product> productList,
		Map<String, IndividualProduct> individualProductMapOfBox, Map<String, IndividualProduct> individualProductMapOfCapInnerCode,
		Map<String, IndividualProduct> individualProductMapOfCapOuterCode, Map<String, Product> productMap) {

		for (IndividualProduct individualProduct : individualProductListOfBox) {
			individualProductMapOfBox.put(individualProduct.getBoxId(), individualProduct);
		}
		for (IndividualProduct individualProduct : individualProductListOfCapInnerCode) {
			individualProductMapOfCapInnerCode.put(individualProduct.getCapInnerCode(), individualProduct);
		}
		for (IndividualProduct individualProduct : individualProductListOfCapOuterCode) {
			individualProductMapOfCapOuterCode.put(individualProduct.getCapOuterCode(), individualProduct);
		}
		for (Product product : productList) {
			productMap.put(product.getId(), product);
		}

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
