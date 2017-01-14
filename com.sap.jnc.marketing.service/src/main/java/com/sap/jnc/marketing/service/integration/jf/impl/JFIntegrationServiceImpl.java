/*
 * This implementation is used to process the restful api call from JF production
 * @author James Jiang
 */
package com.sap.jnc.marketing.service.integration.jf.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.integration.JFDeleteRequest;
import com.sap.jnc.marketing.dto.request.integration.JFDeleteRequest.JFDelRequestItem;
import com.sap.jnc.marketing.dto.request.integration.JFMergeRequest;
import com.sap.jnc.marketing.dto.request.integration.JFMergeRequest.JfMergeRequestItem;
import com.sap.jnc.marketing.dto.request.integration.JFUpdateRequest;
import com.sap.jnc.marketing.dto.request.integration.JFUpdateRequest.JFUpdateDelRequestItem;
import com.sap.jnc.marketing.dto.response.FieldErrorResponse;
import com.sap.jnc.marketing.persistence.enumeration.IndividualProductLogisticIntegrityStatus;
import com.sap.jnc.marketing.persistence.enumeration.IndividualProductRegionValidityStatus;
import com.sap.jnc.marketing.persistence.enumeration.OwnerType;
import com.sap.jnc.marketing.persistence.model.IndividualProduct;
import com.sap.jnc.marketing.persistence.model.IndividualProductBonus;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.ProductErpCategory;
import com.sap.jnc.marketing.persistence.repository.IndividualProductBonusRepository;
import com.sap.jnc.marketing.persistence.repository.IndividualProductRepository;
import com.sap.jnc.marketing.persistence.repository.ProductRepository;
import com.sap.jnc.marketing.service.exception.migration.ResponseBodyDBException;
import com.sap.jnc.marketing.service.integration.jf.JFIntegrationService;

@Service
@Transactional
public class JFIntegrationServiceImpl implements JFIntegrationService {

	@Autowired
	private IndividualProductRepository individualProductRepository;

	@Autowired
	private IndividualProductBonusRepository individualProductBonusRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	@Transactional
	public JFMergeRequest create(JFMergeRequest dto) {
		final HashMap<String, Product> productMap = new HashMap<String, Product>();
		Set<IndividualProduct> individualProductSet = new HashSet<>();
		Set<IndividualProductBonus> individualProductBonusSet = new HashSet<>();
		Set<String> materialIdSet = new HashSet<>();

		List<JfMergeRequestItem> items = dto.getItems();

		for (JfMergeRequestItem item : items) {
			materialIdSet.add(item.getMaterialID());
		}
		searchAllMaterials(materialIdSet, productMap);

		for (int i = 0; i < items.size(); i++) {
			final JfMergeRequestItem item = dto.getItems().get(i);
			final IndividualProduct individualProduct = new IndividualProduct();
			final String bottleIID = this.getBottleIID(item.getBottleIID());

			final String materialID = item.getMaterialID();
			final Product product = this.getProduct(productMap, materialID, item, i);

			final String month = this.getYear(bottleIID);
			final String year = this.getMonth(bottleIID);

			individualProduct.setCapInnerCode(bottleIID);
			individualProduct.setBoxId(item.getBoxID());
			individualProduct.setCaseId(item.getCaseID());
			individualProduct.setCapOuterCode(item.getBottleOID());
			individualProduct.setProduct(product);
			individualProduct.setProducationPatchCode(item.getBatchPP());
			individualProduct.setProductionDate(Calendar.getInstance());
			individualProduct.setLogisticIntegrityStatus(IndividualProductLogisticIntegrityStatus.NA);
			individualProduct.setRegionValidityStatus(IndividualProductRegionValidityStatus.NA);
			individualProduct.setOwnerType(OwnerType.JNC);

			individualProduct.setYear(year);
			individualProduct.setMonth(month);

			if (StringUtils.isNotBlank(item.getBottleVID())) {
				final IndividualProductBonus bonus = new IndividualProductBonus();
				bonus.setCapInnerCode(bottleIID);
				bonus.setVerificationCode(item.getBottleVID());
				bonus.setYear(year);
				bonus.setMonth(month);
				bonus.setIndividualProduct(individualProduct);
				for (ProductErpCategory category : product.getProductErpCategories()) {
					if (ProductErpCategory.PRODUCT_LEVEL_4_ID.equals(category.getLevelNumber())) {
						bonus.setProductErpCategory(category);
					}
				}

				individualProductBonusSet.add(bonus);
				individualProduct.setBonus(bonus);
			}
			individualProductSet.add(individualProduct);

		}
		try {
			individualProductRepository.save(individualProductSet);
			individualProductBonusRepository.save(individualProductBonusSet);
		}
		catch (final RuntimeException e) {
			throw this.getExceptionEntity(e, "items[" + 0 + "].bottleIID", items.get(0).getBottleIID(), "数据库操作出错", 0);

		}
		return dto;

	}

	@Override
	@Transactional
	public JFUpdateRequest update(JFUpdateRequest dto) {
		List<JFUpdateDelRequestItem> items = dto.getItems();
		Set<String> capInnerCodeSet = new HashSet<>();
		Map<String, IndividualProduct> individualProductMap = new HashMap<>();
		Set<IndividualProduct> individualProductSet = new HashSet<>();
		Set<IndividualProductBonus> IndividualProductBonusSet = new HashSet<>();
		for (JFUpdateDelRequestItem item : items) {
			capInnerCodeSet.add(getBottleIID(item.getBottleIID()));
		}
		List<IndividualProduct> individualProductList = individualProductRepository.findByCapInnerCodes(capInnerCodeSet);

		for (IndividualProduct individualProduct : individualProductList) {
			individualProductMap.put(individualProduct.getCapInnerCode(), individualProduct);
		}
		for (int i = 0; i < items.size(); i++) {
			final JFUpdateDelRequestItem item = items.get(i);
			final String bottleIID = this.getBottleIID(item.getBottleIID());
			IndividualProduct individualProductFind = individualProductMap.get(bottleIID);
			final String month = this.getMonth(bottleIID);
			final String year = this.getYear(bottleIID);

			individualProductFind.setCapInnerCode(bottleIID);
			individualProductFind.setBoxId(item.getBoxID());
			individualProductFind.setCaseId(item.getCaseID());
			individualProductFind.setCapOuterCode(item.getBottleOID());
			individualProductFind.setProducationPatchCode(item.getBatchPP());

			individualProductFind.setYear(year);
			individualProductFind.setMonth(month);
			if ((item.getBottleVID() != null) && !"".equals(item.getBottleVID())) {
				final IndividualProductBonus bonus = individualProductFind.getBonus();
				if (bonus != null) {
					bonus.setVerificationCode(item.getBottleVID());
					bonus.setYear(year);
					bonus.setMonth(month);
					bonus.setIndividualProduct(individualProductFind);
					IndividualProductBonusSet.add(bonus);
					individualProductFind.setBonus(bonus);
				}
			}
			individualProductSet.add(individualProductFind);
		}
		try {
			this.individualProductBonusRepository.save(IndividualProductBonusSet);
			this.individualProductRepository.save(individualProductSet);
		}
		catch (final RuntimeException e) {
			throw this.getExceptionEntity(e, "items[" + 0 + "].bottleIID", items.get(0).getBottleIID(), "数据库操作出错", 0);
		}

		return dto;
	}

	@Override
	@Transactional
	public JFDeleteRequest delete(JFDeleteRequest dto) {
		List<JFDelRequestItem> items = dto.getItems();
		Set<String> capInnerCodeSet = new HashSet<>();
		for (int i = 0; i < items.size(); i++) {
			JFDelRequestItem item = items.get(i);
			capInnerCodeSet.add(getBottleIID(item.getBottleIID()));
		}

		List<IndividualProduct> individualProductList = individualProductRepository.findByCapInnerCodes(capInnerCodeSet);
		List<IndividualProductBonus> individualProductBonusList = new ArrayList<>();
		Map<String, IndividualProduct> IndividualProductMap = new HashMap<>();
		for (IndividualProduct individualProduct : individualProductList) {
			IndividualProductMap.put(individualProduct.getCapInnerCode(), individualProduct);
			if (individualProduct.getBonus() != null) {
				individualProductBonusList.add(individualProduct.getBonus());
			}
		}

		for (int i = 0; i < dto.getItems().size(); i++) {
			JFDelRequestItem item = dto.getItems().get(i);
			if (!IndividualProductMap.containsKey(getBottleIID(item.getBottleIID()))) {
				throw getExceptionEntity(new RuntimeException(), "items[" + i + "].bottleIID", item.getBottleIID(), "数据库操作出错", i);
			}
		}
		try {
			individualProductRepository.delete(individualProductList);
			individualProductBonusRepository.delete(individualProductBonusList);
		}
		catch (RuntimeException e) {
			throw this.getExceptionEntity(e, "items[" + 0 + "].bottleIID", items.get(0).getBottleIID(), "数据库操作出错", 0);
		}

		return dto;
	}

	// avoid mutiple search for the same material
	private Product getProduct(HashMap<String, Product> materialhash, String materialID, JfMergeRequestItem item, int i) {
		if (materialhash.containsKey(materialID)) {
			return materialhash.get(materialID);
		}
		else {
			throw this.getExceptionEntity(new RuntimeException(), "items[" + i + "].materialID", item.getMaterialID(), "物料号不存在", i);
		}
	}

	private void searchAllMaterials(Set<String> materialIdSet, HashMap<String, Product> materialhash) {
		List<Product> productList = this.productRepository.findByMaterialIds(materialIdSet);
		for (Product product : productList) {
			if (!materialhash.containsKey(product.getId())) {
				materialhash.put(product.getId(), product);
			}
		}
	}

	private String getBottleIID(String bottleIIDPara) {
		final String bottleIID = bottleIIDPara.substring(bottleIIDPara.length() - 18);
		return bottleIID;
	}

	private String getYear(String bottleIIDPara) {
		return bottleIIDPara.substring(1, 3);
	}

	private String getMonth(String bottleIIDPara) {
		String month = "";
		final char monthChr = bottleIIDPara.charAt(3);
		switch (monthChr) {
		case '0':
			month = "10";
			break;
		case 'A':
			month = "11";
			break;
		case 'B':
			month = "12";
			break;
		}
		if (month.length() == 0) {
			month = "0" + monthChr;
		}
		return month;
	}

	private ResponseBodyDBException getExceptionEntity(RuntimeException e, String field, String rejectValue, String message, int itemId) {
		final FieldErrorResponse errors = new FieldErrorResponse();
		final List<FieldErrorResponse.FieldErrorBodyItems> itemsBody = new ArrayList<FieldErrorResponse.FieldErrorBodyItems>(1);
		final FieldErrorResponse.FieldErrorBodyItems itemBody = errors.new FieldErrorBodyItems(field, rejectValue, message, itemId);
		itemsBody.add(itemBody);
		errors.setError(itemsBody);
		return new ResponseBodyDBException(e, errors);
	}
}
