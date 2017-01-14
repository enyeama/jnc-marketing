package com.sap.jnc.marketing.api.admin.web.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sap.jnc.marketing.dto.request.banquet.BanquetTabVerificationRequest;
import com.sap.jnc.marketing.dto.response.banquet.BanquetTabVerificationHeaderResponse;
import com.sap.jnc.marketing.dto.response.banquet.BanquetTabVerificationResponse;
import com.sap.jnc.marketing.dto.shared.banquet.BanquetTabVerificationResult;
import com.sap.jnc.marketing.persistence.model.BanquetVerification;
import com.sap.jnc.marketing.service.banquet.BanquetTabVerificationService;
import com.sap.jnc.marketing.service.exception.FieldErrorBodyAudit;
import com.sap.jnc.marketing.service.exception.ResponseBodyDBAuditException;
import com.sap.jnc.marketing.service.exception.migration.ResponseBodyDBException;
import me.chanjar.weixin.common.util.StringUtils;

/**
 * 
 * @author I324442 Sha Liu
 *
 */
@RestController
public class BanquetTabVerificationController extends GeneralController{
	@Autowired
	BanquetTabVerificationService verificationService;
	
	@Autowired
	private HttpSession httpSession;
	
	@RequestMapping(value="/tabs/verifications/{verificationId}")
	public BanquetTabVerificationHeaderResponse tabVerificationHeaderRequestHandler(@PathVariable String verificationNumber){
		if(StringUtils.isBlank(verificationNumber)){
			return null;
		}
		BanquetTabVerificationResult result = verificationService.getBanquetTabVerificationHeader(verificationNumber);
		BanquetVerification banquetVerification = result.getBanquetVerification();
		if(banquetVerification != null){
			httpSession.setAttribute(banquetVerification.getVerificationNumber(), banquetVerification);
		}
		return result.getHeaderResponse();
		
	}

	@RequestMapping(value="/tabs/verifications/{verificationNumber}/plaincodes/{plainCode}")
	public BanquetTabVerificationResponse tabLogisticCodeVerification(@PathVariable String verificationNumber, @PathVariable String plainCode){
		BanquetTabVerificationRequest verifyRequest = new BanquetTabVerificationRequest(verificationNumber,plainCode);
		BanquetVerification banquetVerification = (BanquetVerification)httpSession.getAttribute(verificationNumber);
		verificationService.logisticCodeVerification(verifyRequest,banquetVerification);
		return new BanquetTabVerificationResponse();
	}
	
	@RequestMapping(value="/tabs/verifications/{verificationId}/scancodes/{scanCode}")
	public BanquetTabVerificationResponse tabScanCodeVerification(@PathVariable long verificationId, @PathVariable String scanCode){
		return new BanquetTabVerificationResponse();
	}
	
	@RequestMapping(value="/tabs/tabcodes", method={RequestMethod.POST})
	public void tabCodeSave(){
		
	}
	
	@RequestMapping(value="/tabs/verifications", method={RequestMethod.POST})
	public void tabCodeSubmit(){
		
	}
	
	@ExceptionHandler(ResponseBodyDBException.class)
	public ResponseEntity<FieldErrorBodyAudit> dbConnectionErrorHandler(ResponseBodyDBAuditException e){
		return new ResponseEntity<FieldErrorBodyAudit>(e.getFieldErorrBody(),HttpStatus.NOT_FOUND);
	}
}
