/*
 * @author James Jiang
 */
package com.sap.jnc.marketing.api.admin.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sap.jnc.marketing.api.admin.web.exception.contract.ContractException;
import com.sap.jnc.marketing.dto.GeneralSearchRequest;
import com.sap.jnc.marketing.dto.GeneralSearchRequest.Paging;
import com.sap.jnc.marketing.dto.response.contract.ContractImportResponseBody;
import com.sap.jnc.marketing.dto.response.contract.DealerContractResponse;
import com.sap.jnc.marketing.persistence.criteria.contract.ContractAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.ContractItem;
import com.sap.jnc.marketing.service.contract.ContractExportService;
import com.sap.jnc.marketing.service.contract.ContractImportService;
import com.sap.jnc.marketing.service.contract.ContractService;

@RestController
public class ContractController extends GeneralController {
	private static final String XLSX_FILE = ".xlsx";
	private static final String XLS_FILE = ".xlsx";
	private static final String REQUIRED_KA_IMPORT_FILE_FORMAT = "csv";
	private static final long FILE_SIZE_LIMIT = 10L * 1024 * 1024;

	@Autowired
	private ContractService contractService;
	@Autowired
	private ContractImportService contractImportService;
	@Autowired
	private ContractExportService contractExportService;

	@RequestMapping(value = "/dealercontracts/pages", method = RequestMethod.POST)
	public DealerContractResponse advanceSearch(@RequestBody GeneralSearchRequest<ContractAdvanceSearchKeywordNode> searchRequest) {
		final PageRequest pageRequest = searchRequest.getPageRequest();
		final Page<ContractItem> pages = this.contractService.advanceSearch(searchRequest.getKeywords(), pageRequest);
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return null;
		}
		return new DealerContractResponse(pages, pageRequest);
	}

	@RequestMapping(value = { "/contract/import" }, method = RequestMethod.POST)
	public ContractImportResponseBody importKaTerminals(@RequestParam("flie") MultipartFile uploadCsvFile) {
		if (uploadCsvFile == null || uploadCsvFile.isEmpty() || (!uploadCsvFile.getOriginalFilename().contains(XLSX_FILE) && !uploadCsvFile
			.getOriginalFilename().contains(XLS_FILE))) {
			throw new RuntimeException("文件格式不正确");
		}
		if (uploadCsvFile.getSize() > FILE_SIZE_LIMIT) {
			throw new RuntimeException("文件太大");
		}

		try (InputStream fileStream = uploadCsvFile.getInputStream()) {
			return this.contractImportService.importContractRecords(fileStream, uploadCsvFile.getOriginalFilename());
		}
		catch (NullPointerException ex) {
			throw new RuntimeException("运行时错误");
		}
		catch (IOException ex) {
			throw new RuntimeException("运行时错误");
		}

	}

	@RequestMapping(value = "/contract/export", method = RequestMethod.GET)
	public void exportDealer() throws IOException {
		GeneralSearchRequest<ContractAdvanceSearchKeywordNode> searchRequest = new GeneralSearchRequest<ContractAdvanceSearchKeywordNode>();
		Paging paging = new Paging();
		paging.setSize(Integer.MAX_VALUE - 1);
		searchRequest.setPaging(paging);
		ContractAdvanceSearchKeywordNode keywords = new ContractAdvanceSearchKeywordNode();
		searchRequest.setKeywords(keywords);

		final PageRequest pageRequest = searchRequest.getPageRequest();
		final Page<ContractItem> pages = this.contractService.advanceSearch(searchRequest.getKeywords(), pageRequest);
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return;
		}
		DealerContractResponse response = new DealerContractResponse(pages, pageRequest);
		List<DealerContractResponse.Item> contentList = response.getContent();

		HSSFWorkbook wb = contractExportService.export(contentList);

		this.getHttpServletResponse().setContentType("application/vnd.ms-excel");
		String agent = this.getHttpServletRequest().getHeader("USER-AGENT").toLowerCase();
		String excelFileName = java.net.URLEncoder.encode("合同数据", "UTF-8");
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

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ContractException> dbConnectionErrorHandler(RuntimeException e) {
		return new ResponseEntity<ContractException>(new ContractException(e.getMessage()), HttpStatus.BAD_REQUEST);

	}
}
