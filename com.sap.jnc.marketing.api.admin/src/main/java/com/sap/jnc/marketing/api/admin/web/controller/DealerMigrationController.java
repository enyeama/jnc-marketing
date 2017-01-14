package com.sap.jnc.marketing.api.admin.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sap.jnc.marketing.dto.GeneralSearchRequest;
import com.sap.jnc.marketing.dto.response.migration.DealerMigrationPageResponse;
import com.sap.jnc.marketing.dto.response.migration.DealerMigrationResponse;
import com.sap.jnc.marketing.persistence.criteria.migration.DealerMigrationAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.service.exception.CommonServiceException;
import com.sap.jnc.marketing.service.exception.migration.UploadFileInvalidFormatException;
import com.sap.jnc.marketing.service.migration.DealerImportService;
import com.sap.jnc.marketing.service.migration.DealerMigrationService;

/**
 * @author I323691 Marco Huang
 */
@RestController
public class DealerMigrationController extends GeneralController {
	protected static final String REQUIRED_UPLOAD_FILE_FORMAT = "Invalid file format, xls format is required.";
	protected static final String REQUIRED_EXCEL_FORMAT = "请使用.xlsx扩展名的Excel,版本为2007版本的Excel";
	private final static String FILE_NAME = "经销商基本信息";

	@Autowired
	private DealerMigrationService dealerMigrationService;

	@Autowired
	private DealerImportService dealerImportService;

	/**
	 * 查询经销商主数据
	 */
	@RequestMapping(value = "dealer/query", method = RequestMethod.POST)
	public DealerMigrationPageResponse queryDealer(@Valid @RequestBody GeneralSearchRequest<DealerMigrationAdvanceSearchKeywordNode> searchRequest) {
		final PageRequest pageRequest = searchRequest.getPageRequest();
		final Page<Dealer> pages = this.dealerMigrationService.queryDealer(searchRequest.getKeywords(), pageRequest);
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return null;
		}
		return new DealerMigrationPageResponse(pages, pageRequest);
	}

	/**
	 * 经销商数据导入功能
	 */
	@RequestMapping(value = "dealer/dealerimport", method = RequestMethod.POST, headers = "content-type=multipart/*")
	public DealerMigrationResponse uploadDealer(@RequestParam("file") MultipartFile file) {
		try {
			return dealerImportService.uploadDealer(file.getName(), file.getBytes());
		}
		catch (IOException e) {
			throw new UploadFileInvalidFormatException(REQUIRED_UPLOAD_FILE_FORMAT, e);
		}
	}

	// 经销商数据导出
	@RequestMapping(value = "dealers/export", method = RequestMethod.POST)
	public void exportDealer(@Valid @RequestBody GeneralSearchRequest<DealerMigrationAdvanceSearchKeywordNode> searchRequest) {
		DealerMigrationPageResponse dealerResponse = this.queryDealer(searchRequest);
		List<DealerMigrationPageResponse.Item> dealerList = dealerResponse.getContent();
		Workbook wb = dealerMigrationService.exportDealer(dealerList);

		if (wb != null) {
			try {
				this.getHttpServletResponse().setContentType("application/vnd.ms-excel");
				String agent = this.getHttpServletRequest().getHeader("USER-AGENT").toLowerCase();
				String excelFileName = java.net.URLEncoder.encode(FILE_NAME, "UTF-8");
				if (agent.contains("firefox")) {
					this.getHttpServletResponse().setHeader("Content-disposition",
						"attachment;filename=" + new String(excelFileName.getBytes(), "ISO8859-1") + ".xlsx");
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
}
