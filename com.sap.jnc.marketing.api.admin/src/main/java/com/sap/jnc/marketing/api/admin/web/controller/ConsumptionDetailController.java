package com.sap.jnc.marketing.api.admin.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.dto.request.consumption.DFActionRequest;
import com.sap.jnc.marketing.dto.response.consumption.DFQueryResponse;
import com.sap.jnc.marketing.service.consumption.ConsumptionExportService;
import com.sap.jnc.marketing.service.consumption.ConsumptionService;
import com.sap.jnc.marketing.service.exception.CommonServiceException;

@RestController
public class ConsumptionDetailController extends GeneralController {

	@Autowired
	private ConsumptionService consumptionService;
	@Autowired
	private ConsumptionExportService consumptionExportService;

	@RequestMapping(path = "/consumptiondetail/allconsumption", method = { RequestMethod.GET })
	public List<DFQueryResponse> find(@RequestParam(required = false) String id, @RequestParam(required = false) String positionId) {
		return consumptionService.findAllDF(id, positionId);
	}

	@RequestMapping(path = "/consumptiondetail/allconsumption/{id}", method = { RequestMethod.POST })
	public DFQueryResponse find(@PathVariable String id, @RequestBody DFActionRequest dFActionRequest) {
		if (dFActionRequest.getVerifiedQuantity() != null || dFActionRequest.getManualAdjustmentQuantity() != null)
			return consumptionService.update(id, dFActionRequest);
		else
			throw new CommonServiceException("核销量不能为空");
	}

	@RequestMapping(value = "/consumptiondetail/exportation", method = RequestMethod.GET)
	public void exportDealer(String dealID, String dealerName, String status) throws IOException {
		List<DFQueryResponse> responseList = consumptionService.findAllDF("", "");

		HSSFWorkbook wb = consumptionExportService.export(responseList);
		this.getHttpServletResponse().setContentType("application/vnd.ms-excel");
		String agent = this.getHttpServletRequest().getHeader("USER-AGENT").toLowerCase();
		String excelFileName = java.net.URLEncoder.encode("兑付核销数据", "UTF-8");
		if (agent.contains("firefox")) {
			this.getHttpServletResponse().setHeader("Content-disposition", "attachment;filename=" + new String(excelFileName.getBytes(), "ISO8859-1")
				+ ".xls");
		}
		this.getHttpServletResponse().setHeader("Content-disposition", "attachment;filename=" + excelFileName + ".xls");

		OutputStream ouputStream = this.getHttpServletResponse().getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();

	}

	@ExceptionHandler(CommonServiceException.class)
	public ResponseEntity<String> rulesForCustomerNotFound(CommonServiceException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);

	}
}
