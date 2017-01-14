package com.sap.jnc.marketing.service.banquet;

import com.sap.jnc.marketing.dto.request.banquet.BanquetTabSaveRequest;
import com.sap.jnc.marketing.dto.request.banquet.BanquetTabVerificationRequest;
import com.sap.jnc.marketing.dto.response.banquet.BanquetTabSaveResponse;
import com.sap.jnc.marketing.dto.response.banquet.BanquetTabVerificationResponse;
import com.sap.jnc.marketing.dto.shared.banquet.BanquetTabVerificationResult;
import com.sap.jnc.marketing.persistence.model.BanquetVerification;

/**
 * 
 * @author I324442
 *
 */
public interface BanquetTabVerificationService {
	public BanquetTabVerificationResult getBanquetTabVerificationHeader(String verificationId);

	public BanquetTabVerificationResponse logisticCodeVerification(BanquetTabVerificationRequest verificationRequest,
			BanquetVerification banquetVerification);

	public BanquetTabVerificationResponse scanCodeVerification(BanquetTabVerificationRequest verificationRequest);

	public BanquetTabSaveResponse saveBanquetTabCode(BanquetTabSaveRequest saveRequest);
}
