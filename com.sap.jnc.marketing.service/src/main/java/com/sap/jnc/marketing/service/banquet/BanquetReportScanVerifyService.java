package com.sap.jnc.marketing.service.banquet;

import com.sap.jnc.marketing.dto.request.banquet.BanquetScanVerifyRequest;
import com.sap.jnc.marketing.dto.response.banquet.BanquetScanVerifyResponse;

/**
 * 
 * @author I324442 Sha Liu
 *
 */
public interface BanquetReportScanVerifyService {
	BanquetScanVerifyResponse verifyReportScanCaseId(BanquetScanVerifyRequest veriRequest);

	BanquetScanVerifyResponse verifyReportScanBoxId(BanquetScanVerifyRequest veriRequest);
	
	BanquetScanVerifyResponse verifyReportLegacyBarCode(BanquetScanVerifyRequest veriRequest);
	
	BanquetScanVerifyResponse verifyReportLegacyPlainCode(BanquetScanVerifyRequest veriRequest);
}
