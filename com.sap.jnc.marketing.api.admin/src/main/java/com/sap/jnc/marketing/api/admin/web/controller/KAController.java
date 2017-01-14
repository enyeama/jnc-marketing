package com.sap.jnc.marketing.api.admin.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sap.jnc.marketing.dto.request.GeneralSearchRequest;
import com.sap.jnc.marketing.dto.response.bonus.page.PageBean;
import com.sap.jnc.marketing.dto.response.ka.KAImportResponse;
import com.sap.jnc.marketing.dto.response.ka.KAPageResponse;
import com.sap.jnc.marketing.dto.response.ka.KAResponse;
import com.sap.jnc.marketing.dto.shared.terminal.TerminalNode;
import com.sap.jnc.marketing.persistence.criteria.ka.KAAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.criteria.ka.KAExportCriteriaRequest;
import com.sap.jnc.marketing.persistence.enumeration.TerminalType;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.service.exception.CommonServiceException;
import com.sap.jnc.marketing.service.exception.migration.ka.UploadFileContentRequiredException;
import com.sap.jnc.marketing.service.exception.migration.ka.UploadFileInvalidFormatException;
import com.sap.jnc.marketing.service.ka.KAExportService;
import com.sap.jnc.marketing.service.ka.KAImportService;
import com.sap.jnc.marketing.service.ka.KAService;
import com.sap.jnc.marketing.service.terminal.TerminalService;

@RestController
public class KAController extends GeneralController {

	private final static String FILE_NAME = "KA终端数据";

	private static final String XLSX_FILE = ".xlsx";
	private static final String XLS_FILE = ".xlsx";
	private static final String REQUIRED_KA_IMPORT_FILE_FORMAT = "csv";
	private static final long FILE_SIZE_LIMIT = 10L * 1024 * 1024;

	@Autowired
	KAService kaService;

	@Autowired
	KAImportService kaImportService;

	@Autowired
	KAExportService kaExportService;

	@Autowired
	private TerminalService terminalService;

	@RequestMapping(value = { "/ka/import" }, method = RequestMethod.POST)
	public Collection<KAImportResponse> importKaTerminals(@RequestParam("csvFile") MultipartFile uploadCsvFile) {
		if (uploadCsvFile == null || uploadCsvFile.isEmpty() || (!uploadCsvFile.getOriginalFilename().contains(XLSX_FILE) && !uploadCsvFile
			.getOriginalFilename().contains(XLS_FILE)) || uploadCsvFile.getSize() > FILE_SIZE_LIMIT) {
			throw new UploadFileContentRequiredException();
		}
		try (InputStream fileStream = uploadCsvFile.getInputStream()) {
			return this.kaImportService.importKARecords(fileStream, uploadCsvFile.getOriginalFilename());
		}
		catch (NullPointerException ex) {
			throw new UploadFileContentRequiredException(ex);
		}
		catch (IOException ex) {
			throw new UploadFileInvalidFormatException(REQUIRED_KA_IMPORT_FILE_FORMAT, ex);
		}
	}

	@RequestMapping(value = { "/ka/export" }, method = RequestMethod.POST)
	public void exportKATerminals(@RequestBody KAExportCriteriaRequest exportCriteriaRequest) {
		Workbook wb = this.kaExportService.exportKARecords(exportCriteriaRequest);
		if (wb != null) {
			try {
				this.getHttpServletResponse().setContentType("application/vnd.ms-excel");
				String agent = this.getHttpServletRequest().getHeader("USER-AGENT").toLowerCase();
				String excelFileName = java.net.URLEncoder.encode(FILE_NAME, "UTF-8");
				if (agent.contains("firefox")) {
					this.getHttpServletResponse().setHeader("Content-disposition", "attachment;filename=" + new String(excelFileName.getBytes(),
						"ISO8859-1") + ".xlsx");
				}
				this.getHttpServletResponse().setHeader("Content-disposition", "attachment;filename=" + excelFileName + ".xlsx");

				OutputStream ouputStream = null;
				try {
					ouputStream = this.getHttpServletResponse().getOutputStream();
					wb.write(ouputStream);
					ouputStream.flush();
				}
				finally {
					if (ouputStream != null) {
						ouputStream.close();
					}
				}
			}
			catch (Exception e) {
				throw new CommonServiceException(e.getMessage());
			}
		}
	}

	@RequestMapping(value = { "/ka/{id}" }, method = RequestMethod.GET)
	public TerminalNode getById(@PathVariable Long id) {
		if (id == null || id.longValue() < 1) {
			return null;
		}
		Terminal terminal = this.terminalService.findById(id);
		if (terminal == null || terminal.getType() != TerminalType.KA) {
			return null;
		}
		return new TerminalNode(terminal);
	}

	@RequestMapping(value = { "/ka/{id}" }, method = RequestMethod.PUT)
	public TerminalNode update(@PathVariable Long id, @RequestBody TerminalNode terminalNode) {
		if (id == null || id.longValue() < 1) {
			return null;
		}
		return this.kaService.update(terminalNode);
	}

	@RequestMapping(value = { "/ka/{id}/disable" }, method = RequestMethod.POST)
	public void disableKA(@PathVariable Long id) {
		if (id == null || id.longValue() < 1) {
			return;
		}
		this.kaService.disableKA(id);
	}

	@RequestMapping(value = "/ka/pages", method = RequestMethod.POST)
	@ResponseBody
	public PageBean<KAResponse> advanceSearch(@RequestBody GeneralSearchRequest<KAAdvanceSearchKeywordNode> searchRequest) {
		final PageRequest pageRequest = searchRequest.getPageRequest();
		final Page<Terminal> pages = this.terminalService.advanceKASearch(searchRequest.getKeywords(), pageRequest);
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return null;
		}
		KAPageResponse kaPageResponse = new KAPageResponse(pages, pageRequest);

		List<KAResponse> items = kaPageResponse.getContent();
		int total = (int) kaPageResponse.getTotalElements();
		int number = pages.getNumber();
		int size = pages.getSize();

		PageBean<KAResponse> kaResponseBean = new PageBean<KAResponse>(number + 1, size, total, items);
		return kaResponseBean;
	}
}
