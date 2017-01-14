package com.sap.jnc.marketing.service.banquet.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.banquet.BanquetTabSaveRequest;
import com.sap.jnc.marketing.dto.request.banquet.BanquetTabVerificationRequest;
import com.sap.jnc.marketing.dto.response.banquet.BanquetTabSaveResponse;
import com.sap.jnc.marketing.dto.response.banquet.BanquetTabVerificationHeaderResponse;
import com.sap.jnc.marketing.dto.response.banquet.BanquetTabVerificationResponse;
import com.sap.jnc.marketing.dto.shared.banquet.BanquetTabVerificationResult;
import com.sap.jnc.marketing.persistence.enumeration.BanquetVerificationInvalidReason;
import com.sap.jnc.marketing.persistence.enumeration.BanquetVerificationResult;
import com.sap.jnc.marketing.persistence.model.Banquet;
import com.sap.jnc.marketing.persistence.model.BanquetItem;
import com.sap.jnc.marketing.persistence.model.BanquetVerification;
import com.sap.jnc.marketing.persistence.model.BanquetVerifyApplication;
import com.sap.jnc.marketing.persistence.model.BasicProduct;
import com.sap.jnc.marketing.persistence.model.OldIndividualProduct;
import com.sap.jnc.marketing.persistence.repository.BanquetItemRepository;
import com.sap.jnc.marketing.persistence.repository.BanquetVerificationRepository;
import com.sap.jnc.marketing.persistence.repository.impl.OldIndividualProductRepositoryImpl;
import com.sap.jnc.marketing.service.banquet.BanquetTabVerificationService;
import com.sap.jnc.marketing.service.exception.FieldErrorBodyAudit;
import com.sap.jnc.marketing.service.exception.ResponseBodyDBAuditException;

@Service
@Transactional
public class BanquetTabVerificationServiceImpl implements BanquetTabVerificationService {
	private static final String VERIFICATION_NOT_FOUND = "回收流转单未找到";
	private static final String BANQUET_NOT_FOUND = "回收流转单所相应的宴会报备单不存在";
	private static final String VERIFY_APPLICATION_NOT_FOUND = "回收流转单所相应的核销申请单不存在";
	private static final String VERIFICATION_NUMBER_INVALID = "核销单号错误";
	private static final String INVALID_REQUEST = "请求参数无效";
	private static final String INVALID_LOGISTIC_CODE = "物流码未找到";
	private static final String NOT_REPORTED = "未报备，不属于已报备货品";
	private static final String WRONG_REPORTED_BANQUET = "非本报备单号，不属于本宴会";
	private static final String NOT_REBATED = "未兑付";

	@Autowired
	BanquetVerificationRepository verificationRepository;

	@Autowired
	BanquetItemRepository banquetItemRepository;

	@Autowired
	private OldIndividualProductRepositoryImpl oldProductRepository;

	@Override
	@Transactional(readOnly = true)
	public BanquetTabVerificationResult getBanquetTabVerificationHeader(String verificationNumber) {
		if (StringUtils.isEmpty(verificationNumber)) {
			throw this.getExceptionEntity(new RuntimeException(), "verificationNumber", verificationNumber,
					VERIFICATION_NUMBER_INVALID);
		}
		BanquetVerification verification = verificationRepository.findByVerificationNumber(verificationNumber);
		if (verification == null) {
			throw this.getExceptionEntity(new RuntimeException(), "verificationNumber", verificationNumber,
					VERIFICATION_NOT_FOUND);
		}
		Banquet banquet = verification.getBanquet();
		if (banquet == null) {
			throw this.getExceptionEntity(new RuntimeException(), "verificationNumber", verificationNumber,
					BANQUET_NOT_FOUND);
		}
		BanquetVerifyApplication verifyApplication = verification.getBanquetVerifyApplication();
		if (verifyApplication == null) {
			throw this.getExceptionEntity(new RuntimeException(), "verificationNumber", verificationNumber,
					VERIFY_APPLICATION_NOT_FOUND);
		}
		BanquetTabVerificationHeaderResponse headerResponse = new BanquetTabVerificationHeaderResponse();
		this.getVerificationInformation(verification, headerResponse);
		this.getBanquetInformation(banquet, headerResponse);
		this.getBanquetVerifyApplication(verifyApplication, headerResponse);
		List<BanquetItem> rebatedItems = banquetItemRepository.findRbatedByBanquetIdAndIsPaid(banquet.getId());
		if (!CollectionUtils.isEmpty(rebatedItems)) {
			headerResponse.setTotalRebate(rebatedItems.size());
		}
		return new BanquetTabVerificationResult(headerResponse, verification);
	}

	@Override
	@Transactional(readOnly = true)
	public BanquetTabVerificationResponse logisticCodeVerification(BanquetTabVerificationRequest verificationRequest,
			BanquetVerification banquetVerification) {
		if (verificationRequest == null || StringUtils.isEmpty(verificationRequest.getCode())
				|| StringUtils.isBlank(verificationRequest.getBanquetVerificationNumber())) {
			throw this.getExceptionEntity(new RuntimeException(), "logicticCode", verificationRequest.getCode(),
					INVALID_REQUEST);
		}
		String verifyNumber = verificationRequest.getBanquetVerificationNumber();
		OldIndividualProduct legacyProduct = this.oldProductRepository.findByBoxId(verificationRequest.getCode());
		if (banquetVerification == null) {
			banquetVerification = verificationRepository.findByVerificationNumber(verifyNumber);
		}
		BanquetTabVerificationResponse verificationResponse = this.productVerification(legacyProduct,verificationRequest, banquetVerification);
		if(verificationResponse != null){
			return verificationResponse;
		}
		return null;
	}

	@Override
	public BanquetTabVerificationResponse scanCodeVerification(BanquetTabVerificationRequest verificationRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BanquetTabSaveResponse saveBanquetTabCode(BanquetTabSaveRequest saveRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	private BanquetTabVerificationHeaderResponse getVerificationInformation(BanquetVerification verification,
			BanquetTabVerificationHeaderResponse headerResponse) {
		headerResponse.setCapQuantityOfFirstVerification(verification.getCapQuantityOfFirstVerification());
		headerResponse.setCommentOfFirstVerification(verification.getCommentOfFirstVerification());
		headerResponse
				.setExteriorRingQuantityOfFirstVerification(verification.getExteriorRingQuantityOfFirstVerification());
		headerResponse
				.setInteriorRingQuantityOfFirstVerification(verification.getInteriorRingQuantityOfFirstVerification());
		headerResponse.setTimeOfFirstVerification(verification.getTimeOfFirstVerification());
		headerResponse.setVerificationId(verification.getId());
		headerResponse.setVerifierOfFirstVerification(verification.getVerifierOfFirstVerification().getName());
		return headerResponse;

	}

	private BanquetTabVerificationHeaderResponse getBanquetInformation(Banquet banquet,
			BanquetTabVerificationHeaderResponse headerResponse) {
		headerResponse.setBanquetId(banquet.getId());
		headerResponse.setBanquetType(banquet.getType().getName());
		headerResponse.setChannelName(banquet.getMaterialChannel().getName());
		headerResponse.setDealerName(banquet.getDealer().getName());
		headerResponse.setProductName(banquet.getProduct().getName());
		headerResponse.setScanType(banquet.getScanType());
		return headerResponse;
	}

	private BanquetTabVerificationHeaderResponse getBanquetVerifyApplication(BanquetVerifyApplication verifyApplication,
			BanquetTabVerificationHeaderResponse headerResponse) {
		headerResponse.setBottleQuantity(verifyApplication.getBottleQuantity().getValue());
		headerResponse.setBoxQuantity(verifyApplication.getBoxQuantity());
		return headerResponse;
	}

	private BanquetTabVerificationResponse productVerification(BasicProduct product,
			BanquetTabVerificationRequest verifyRequest, BanquetVerification banquetVerification) {
		String code = verifyRequest.getCode();
		BanquetTabVerificationResponse veriResponse = null;
		final Calendar verifyDate = Calendar.getInstance();
		if (product == null) {
			return new BanquetTabVerificationResponse(code, BanquetVerificationResult.NOT_FOUND,
					BanquetVerificationInvalidReason.VERIFICATION_NOT_FOUND, verifyDate);
		} else if (product.getBanquetItem() == null) {
			return new BanquetTabVerificationResponse(code, BanquetVerificationResult.NOT_REPORTED,
					BanquetVerificationInvalidReason.NOT_REPORTED, verifyDate);
		} else if (!(product.getBanquetItem().getBanquet().getId() == banquetVerification.getBanquet().getId())) {
			return new BanquetTabVerificationResponse(code, BanquetVerificationResult.INVALID_REPORT,
					BanquetVerificationInvalidReason.INVALID_REPORT, verifyDate);
		} else if (!product.getBanquetItem().getIsPaid()) {
			return new BanquetTabVerificationResponse(code, BanquetVerificationResult.NOT_REBATED,
					BanquetVerificationInvalidReason.NOT_REBATED, verifyDate);
		}
		return veriResponse;
	}

	private ResponseBodyDBAuditException getExceptionEntity(RuntimeException e, String field, String rejectValue,
			String message) {
		final FieldErrorBodyAudit errors = new FieldErrorBodyAudit();
		final List<FieldErrorBodyAudit.FieldErrorBodyItems> itemsBody = new ArrayList<FieldErrorBodyAudit.FieldErrorBodyItems>(
				1);
		final FieldErrorBodyAudit.FieldErrorBodyItems itemBody = errors.new FieldErrorBodyItems(field, rejectValue,
				message);
		itemsBody.add(itemBody);
		errors.setError(itemsBody);
		return new ResponseBodyDBAuditException(e, errors);
	}

}
