package com.sap.jnc.marketing.service.banquet;

import com.sap.jnc.marketing.dto.request.banquet.BanquetScanUpdateRequest;
import com.sap.jnc.marketing.dto.response.banquet.BanquetScanUpdateResponse;
/**
 * 
 * @author I324442 Sha Liu
 *
 */
public interface BanquetReportScanUpdateService {
	BanquetScanUpdateResponse updateReportScanCode(BanquetScanUpdateRequest updateRequest);
	
	BanquetScanUpdateResponse updateReportScanLegacyCode(BanquetScanUpdateRequest updateRequest);
}
