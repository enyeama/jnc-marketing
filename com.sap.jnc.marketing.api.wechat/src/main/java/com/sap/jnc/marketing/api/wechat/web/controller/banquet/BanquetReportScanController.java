package com.sap.jnc.marketing.api.wechat.web.controller.banquet;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sap.jnc.marketing.api.wechat.web.controller.GeneralController;
import com.sap.jnc.marketing.dto.request.banquet.BanquetScanUpdateRequest;
import com.sap.jnc.marketing.dto.request.banquet.BanquetScanVerifyRequest;
import com.sap.jnc.marketing.dto.response.banquet.BanquetScanUpdateResponse;
import com.sap.jnc.marketing.dto.response.banquet.BanquetScanVerifyResponse;
import com.sap.jnc.marketing.service.banquet.BanquetReportScanUpdateService;
import com.sap.jnc.marketing.service.banquet.BanquetReportScanVerifyService;

/**
 * 
 * @author I324442 Sha Liu
 *
 */

@RestController
public class BanquetReportScanController extends GeneralController {
	public static final int BOX_SCAN_CODE_LENGTH = 18;
	public static final int CASE_SCAN_CODE_LENGTH = 24;
	public static final int LEGACY_CODE_LENGTH = 24;
	
	@Autowired
	private BanquetReportScanVerifyService reportScanVerifyService;
	@Autowired
	private BanquetReportScanUpdateService reportScanUpdateService;

	@RequestMapping(value = "/liquors/codes/{scanCode}/materials/{materialId}", method = { RequestMethod.GET } )
	public BanquetScanVerifyResponse verifyScanCode(@PathVariable String scanCode, @PathVariable String materialId) {
		int scanCodeLength = scanCode.length();
		BanquetScanVerifyRequest veriRequest = new BanquetScanVerifyRequest(scanCode, materialId);
		if (scanCodeLength == BOX_SCAN_CODE_LENGTH) {
			return this.reportScanVerifyService.verifyReportScanBoxId(veriRequest);
		} else if (scanCodeLength == CASE_SCAN_CODE_LENGTH) {
			return this.reportScanVerifyService.verifyReportScanCaseId(veriRequest);
		} else
			return new BanquetScanVerifyResponse(HttpStatus.NOT_ACCEPTABLE, Collections.emptyList());
	}

	@RequestMapping(value = "/liquors", method = RequestMethod.POST)
	public BanquetScanUpdateResponse updateScanCode(@RequestBody BanquetScanUpdateRequest updateRequest) {
		updateRequest.setLegecyProduct(false);
		return this.reportScanUpdateService.updateReportScanCode(updateRequest);
	}
	
	@RequestMapping(value="/liquors/legacy/barcodes/{barCode}/materials/{materialId}", method= { RequestMethod.GET })
	public BanquetScanVerifyResponse verifyBarCode(@PathVariable String barCode, @PathVariable String materialId ){
		int barCodeLength = barCode.length();
		BanquetScanVerifyRequest veriRequest = new BanquetScanVerifyRequest(barCode,materialId);
		if(barCodeLength == LEGACY_CODE_LENGTH){
		   	return this.reportScanVerifyService.verifyReportLegacyBarCode(veriRequest);
		}else
			return new BanquetScanVerifyResponse(HttpStatus.NOT_ACCEPTABLE, Collections.emptyList());
	}
	
	@RequestMapping(value="/liquors/legacy/plaincodes/{plainCode}/materials/{materialId}", method= {RequestMethod.GET})
	public BanquetScanVerifyResponse verifyPlainCode(@PathVariable String plainCode, @PathVariable String materialId){
		int plainCodeLength = plainCode.length();
		BanquetScanVerifyRequest veriRequest = new BanquetScanVerifyRequest(plainCode,materialId);
		if(plainCodeLength == LEGACY_CODE_LENGTH){
			return this.reportScanVerifyService.verifyReportLegacyPlainCode(veriRequest);
		}else
			return new BanquetScanVerifyResponse(HttpStatus.NOT_ACCEPTABLE,Collections.emptyList());
	}
	
	@RequestMapping(value="/liquors/legacy", method= { RequestMethod.POST})
	public BanquetScanUpdateResponse updateLegacyCode(@RequestBody BanquetScanUpdateRequest updateRequest){
		updateRequest.setLegecyProduct(true);
		return this.reportScanUpdateService.updateReportScanLegacyCode(updateRequest);
	}
}


