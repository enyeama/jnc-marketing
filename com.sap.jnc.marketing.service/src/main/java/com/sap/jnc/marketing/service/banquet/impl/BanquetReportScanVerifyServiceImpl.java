package com.sap.jnc.marketing.service.banquet.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sap.jnc.marketing.dto.request.banquet.BanquetScanVerifyRequest;
import com.sap.jnc.marketing.dto.response.banquet.BanquetScanVerifyResponse;
import com.sap.jnc.marketing.dto.shared.banquet.BoxCasePair;
import com.sap.jnc.marketing.persistence.model.IndividualProduct;
import com.sap.jnc.marketing.persistence.model.BasicProduct;
import com.sap.jnc.marketing.persistence.model.OldIndividualProduct;
import com.sap.jnc.marketing.persistence.repository.impl.IndividualProductRepositoryImpl;
import com.sap.jnc.marketing.persistence.repository.impl.OldIndividualProductRepositoryImpl;
import com.sap.jnc.marketing.service.banquet.BanquetReportScanVerifyService;
/**
 * 
 * @author I324442 Sha Liu
 *
 */
@Service
@Transactional
public class BanquetReportScanVerifyServiceImpl implements BanquetReportScanVerifyService {
	@Autowired
	private IndividualProductRepositoryImpl individualProductRepository;
	@Autowired
	private OldIndividualProductRepositoryImpl oldProductRepository;

	@Override
	@Transactional(readOnly = true)
	public BanquetScanVerifyResponse verifyReportScanCaseId(BanquetScanVerifyRequest verifyRequest) {
		if (verifyRequest == null || StringUtils.isBlank(verifyRequest.getScanCode())) {
			return new BanquetScanVerifyResponse(HttpStatus.BAD_REQUEST, Collections.emptyList());
		}
		List<IndividualProduct> individualProducts = this.individualProductRepository
				.findAllByCaseId(verifyRequest.getScanCode());
		if (CollectionUtils.isEmpty(individualProducts)) {
			return new BanquetScanVerifyResponse(HttpStatus.NOT_FOUND, Collections.emptyList());
		}
		List<BoxCasePair> pairs = new ArrayList<>();
		BanquetScanVerifyResponse veriResponse = null;
		for (IndividualProduct individualProduct : individualProducts) {
			veriResponse = this.productVerification(individualProduct, verifyRequest);
			if (veriResponse != null) {
				return veriResponse;
			}
			pairs.add(new BoxCasePair(individualProduct.getBoxId(), individualProduct.getCaseId()));
		}
		return new BanquetScanVerifyResponse(HttpStatus.ACCEPTED, pairs);
	}

	@Override
	@Transactional(readOnly = true)
	public BanquetScanVerifyResponse verifyReportScanBoxId(BanquetScanVerifyRequest verifyRequest) {
		if (verifyRequest == null || StringUtils.isBlank(verifyRequest.getScanCode())) {
			return new BanquetScanVerifyResponse(HttpStatus.BAD_REQUEST, Collections.emptyList());
		}
		IndividualProduct individualProduct = this.individualProductRepository.findByBoxId(verifyRequest.getScanCode());
		BanquetScanVerifyResponse veriResponse = this.productVerification(individualProduct, verifyRequest);
		if (veriResponse != null) {
			return veriResponse;
		}
		BoxCasePair pair = new BoxCasePair(individualProduct.getBoxId(), individualProduct.getCaseId());
		List<BoxCasePair> pairs = new ArrayList<>();
		pairs.add(pair);
		return new BanquetScanVerifyResponse(HttpStatus.ACCEPTED, pairs);
	}
	
	@Override
	public BanquetScanVerifyResponse verifyReportLegacyBarCode(BanquetScanVerifyRequest verifyRequest) {
		if (verifyRequest == null || StringUtils.isBlank(verifyRequest.getScanCode())) {
			return new BanquetScanVerifyResponse(HttpStatus.BAD_REQUEST, Collections.emptyList());
		}
		List<OldIndividualProduct> legacyProducts = this.oldProductRepository
				.findAllByCaseId(verifyRequest.getScanCode());
		if (CollectionUtils.isEmpty(legacyProducts)) {
			return new BanquetScanVerifyResponse(HttpStatus.NOT_FOUND, Collections.emptyList());
		}
		List<BoxCasePair> pairs = new ArrayList<>();
		BanquetScanVerifyResponse verifyResponse = null;
		for (OldIndividualProduct legacyProduct : legacyProducts) {
			verifyResponse = this.productVerification(legacyProduct, verifyRequest);
			if (verifyResponse != null) {
				return verifyResponse;
			}
			pairs.add(new BoxCasePair(legacyProduct.getBoxId(), legacyProduct.getCaseId()));
		}
		return new BanquetScanVerifyResponse(HttpStatus.ACCEPTED, pairs);
	}
	
	@Override
	public BanquetScanVerifyResponse verifyReportLegacyPlainCode(BanquetScanVerifyRequest verifyRequest) {
		if (verifyRequest == null || StringUtils.isBlank(verifyRequest.getScanCode())) {
			return new BanquetScanVerifyResponse(HttpStatus.BAD_REQUEST, Collections.emptyList());
		}
		OldIndividualProduct legacyProduct = this.oldProductRepository.findByBoxId(verifyRequest.getScanCode());
		BanquetScanVerifyResponse veriResponse = this.productVerification(legacyProduct, verifyRequest);
		if (veriResponse != null) {
			return veriResponse;
		}
		BoxCasePair pair = new BoxCasePair(legacyProduct.getBoxId(), legacyProduct.getCaseId());
		List<BoxCasePair> pairs = new ArrayList<>();
		pairs.add(pair);
		return new BanquetScanVerifyResponse(HttpStatus.ACCEPTED, pairs);
	}

	private BanquetScanVerifyResponse productVerification(BasicProduct product,
			BanquetScanVerifyRequest verifyRequest) {
		BanquetScanVerifyResponse veriResponse = null;
		if (product == null) {
			veriResponse = new BanquetScanVerifyResponse(HttpStatus.NOT_FOUND, Collections.emptyList());
		} else if (product.getBanquetItem() != null) {
			veriResponse = new BanquetScanVerifyResponse(HttpStatus.CONFLICT, Collections.emptyList());
		} else if (!product.getProduct().getId().equals(verifyRequest.getMaterialId())) {
			veriResponse = new BanquetScanVerifyResponse(HttpStatus.FORBIDDEN, Collections.emptyList());
		}
		return veriResponse;
	}

}
