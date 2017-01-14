package com.sap.jnc.marketing.service.banquet.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sap.jnc.marketing.dto.request.banquet.BanquetScanUpdateRequest;
import com.sap.jnc.marketing.dto.response.banquet.BanquetScanUpdateResponse;
import com.sap.jnc.marketing.dto.shared.banquet.BoxCasePair;
import com.sap.jnc.marketing.persistence.model.Banquet;
import com.sap.jnc.marketing.persistence.model.BanquetItem;
import com.sap.jnc.marketing.persistence.model.BasicProduct;
import com.sap.jnc.marketing.persistence.model.IndividualProduct;
import com.sap.jnc.marketing.persistence.model.OldIndividualProduct;
import com.sap.jnc.marketing.persistence.repository.impl.BanquetItemRepositoryImpl;
import com.sap.jnc.marketing.persistence.repository.impl.BanquetRepositoryImpl;
import com.sap.jnc.marketing.persistence.repository.impl.IndividualProductRepositoryImpl;
import com.sap.jnc.marketing.persistence.repository.impl.OldIndividualProductRepositoryImpl;
import com.sap.jnc.marketing.service.banquet.BanquetReportScanUpdateService;

/**
 * 
 * @author I324442 Sha Liu
 *
 */
@Service
@Transactional
public class BanquetReportScanUpdateServiceImpl implements BanquetReportScanUpdateService {
	private static final int NO_CONTENT = 0;
	@Autowired
	private BanquetItemRepositoryImpl itemRepository;
	@Autowired
	private IndividualProductRepositoryImpl productRepository;
	@Autowired
	private BanquetRepositoryImpl banquetRepository;
	@Autowired
	private OldIndividualProductRepositoryImpl legacyProductRepository;

	@Override
	@Transactional
	public BanquetScanUpdateResponse updateReportScanCode(BanquetScanUpdateRequest updateRequest) {
		if (updateRequest == null || CollectionUtils.isEmpty(updateRequest.getBoxCasePairs())) {
			return new BanquetScanUpdateResponse(HttpStatus.BAD_REQUEST, NO_CONTENT);
		}
		List<String> boxIdList = this.getBoxIdList(updateRequest);
		List<IndividualProduct> individualProducts = this.productRepository.findAllByBoxId(boxIdList);
		BanquetScanUpdateResponse updateBanquetItemResponse = this.updateBanquetItem(individualProducts, updateRequest);
		if(updateBanquetItemResponse.getStatus() != HttpStatus.CREATED){
			return updateBanquetItemResponse;
		}
		List<IndividualProduct> updatedProducts = this.productRepository.save(individualProducts);
		if (CollectionUtils.isEmpty(updatedProducts)) {
			return new BanquetScanUpdateResponse(HttpStatus.NOT_MODIFIED, NO_CONTENT);
		}
		return new BanquetScanUpdateResponse(HttpStatus.CREATED, updateBanquetItemResponse.getUpdatedCount());			
	}

	@Override
	@Transactional
	public BanquetScanUpdateResponse updateReportScanLegacyCode(BanquetScanUpdateRequest updateRequest) {
		if (updateRequest == null || CollectionUtils.isEmpty(updateRequest.getBoxCasePairs())) {
			return new BanquetScanUpdateResponse(HttpStatus.BAD_REQUEST, NO_CONTENT);
		}
		List<String> boxIdList = this.getBoxIdList(updateRequest);
		List<OldIndividualProduct> legacyProducts = this.legacyProductRepository.findAllByBoxId(boxIdList);
		BanquetScanUpdateResponse updateBanquetItemResponse = this.updateBanquetItem(legacyProducts, updateRequest);
		if (updateBanquetItemResponse.getStatus() != HttpStatus.CREATED) {
			return updateBanquetItemResponse;
		}
		List<OldIndividualProduct> updatedProducts = this.legacyProductRepository.save(legacyProducts);
		if (CollectionUtils.isEmpty(updatedProducts)) {
			return new BanquetScanUpdateResponse(HttpStatus.NOT_MODIFIED, NO_CONTENT);
		}
		return new BanquetScanUpdateResponse(HttpStatus.CREATED, updateBanquetItemResponse.getUpdatedCount());
	}
	
	@Transactional
	private BanquetScanUpdateResponse updateBanquetItem(List<? extends BasicProduct> products, BanquetScanUpdateRequest updateRequest){
		BanquetScanUpdateResponse verifyResponse = this.productVerification(products, updateRequest);
		if (verifyResponse != null) {
			return verifyResponse;
		}
		Banquet banquet = this.banquetRepository.findOne(updateRequest.getBanquetId());
		if (banquet == null) {
			return new BanquetScanUpdateResponse(HttpStatus.PRECONDITION_FAILED, NO_CONTENT);
		}
		List<BanquetItem> items = this.constructBanquetItem(products, banquet,updateRequest);
		List<BanquetItem> updatedItems = this.itemRepository.save(items);
		if (CollectionUtils.isEmpty(updatedItems)) {
			return new BanquetScanUpdateResponse(HttpStatus.NOT_MODIFIED, NO_CONTENT);
		}
		return new BanquetScanUpdateResponse(HttpStatus.CREATED,updatedItems.size());
	}

	private BanquetScanUpdateResponse productVerification(List<? extends BasicProduct> products,
			BanquetScanUpdateRequest updateRequest) {
		BanquetScanUpdateResponse updateResponse = null;
		if (CollectionUtils.isEmpty(products)) {
			return new BanquetScanUpdateResponse(HttpStatus.NOT_FOUND, NO_CONTENT);
		} else if (products.size() != updateRequest.getBoxCasePairs().size()) {
			return new BanquetScanUpdateResponse(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, NO_CONTENT);
		}
		for (BasicProduct product : products) {
			if (!product.getProduct().getId().equals(updateRequest.getMaterialId())) {
				updateResponse = new BanquetScanUpdateResponse(HttpStatus.FORBIDDEN, NO_CONTENT);
				break;
			} else if (product.getBanquetItem() != null) {
				updateResponse = new BanquetScanUpdateResponse(HttpStatus.CONFLICT, NO_CONTENT);
				break;
			}
		}
		return updateResponse;
	}

	private List<BanquetItem> constructBanquetItem(List<? extends BasicProduct> basicProducts, Banquet banquet,
			BanquetScanUpdateRequest updateRequest) {
		if (CollectionUtils.isEmpty(basicProducts)) {
			return Collections.emptyList();
		}
		List<BanquetItem> items = new ArrayList<>();
		for (BasicProduct basicProduct : basicProducts) {
			BanquetItem item = new BanquetItem();
			item.setBanquet(banquet);
			item.setBoxId(basicProduct.getBoxId());
			item.setCaseId(basicProduct.getCaseId());
			item.setIsPaid(false);
			if(!updateRequest.isLegecyProduct()){
				IndividualProduct individualProduct = (IndividualProduct)basicProduct;
				item.setCapInnerCode(individualProduct.getCapInnerCode());
				item.setCapOuterCode(individualProduct.getCapOuterCode());
				item.setProducationBatchCode(individualProduct.getProducationPatchCode());
				item.setIndividualProduct(individualProduct);
	            individualProduct.setBanquetItem(item);
				items.add(item);
				continue;
			}
			item.setCapInnerCode(basicProduct.getBoxId());
			item.setCapOuterCode(basicProduct.getCaseId());
			OldIndividualProduct oldProduct = (OldIndividualProduct)basicProduct;
			item.setOldIndividualProduct(oldProduct);
			basicProduct.setBanquetItem(item);
			items.add(item);
		}
		return items;
}

	private List<String> getBoxIdList(BanquetScanUpdateRequest updateRequest) {
		List<String> boxIdList = new ArrayList<>();
		List<BoxCasePair> pairs = updateRequest.getBoxCasePairs();
		for (BoxCasePair pair : pairs) {
			boxIdList.add(pair.getBoxId());
		}
		return boxIdList;
	}
}
