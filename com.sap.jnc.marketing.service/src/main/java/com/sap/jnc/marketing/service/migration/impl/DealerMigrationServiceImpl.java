package com.sap.jnc.marketing.service.migration.impl;

import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sap.jnc.marketing.dto.response.migration.DealerMigrationPageResponse.Item;
import com.sap.jnc.marketing.persistence.criteria.migration.DealerMigrationAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.DealerStatus;
import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.repository.DealerMigrationRepository;
import com.sap.jnc.marketing.persistence.repository.PositionViewRepository;
import com.sap.jnc.marketing.service.migration.DealerMigrationService;

/**
 * @author I323691 Marco Huang
 */
@Service
@Transactional
public class DealerMigrationServiceImpl implements DealerMigrationService {
	@Autowired
	private DealerMigrationRepository dealerMigrationRepository;

	@Autowired
	private PositionViewRepository positionViewRepository;
	
	private final static String DEALER_EXTERNAL_ID = "经销商编码";
	private final static String DEALER_NAME = "经销商名称";
	private final static String DEALER_STATUS = "经销商状态";
	private final static String PLATFORM_COMPANY = "平台公司";
	private final static String IS_PRIMARY_DEALER = "主经销商标识";
	private final static String PRIMARY_DEALER_ID = "主经销商ID";
	private final static String CITY_MANAGER_POSITION_ID = "经销商岗位 负责人ID";
	
	private final static String FILE_NAME = "经销商基本信息";
	private final static String OPTION_IS = "是";
	private final static String OPTION_NOT = "否";
	private final static String STATUS_ACTIVE = "可用";
	private final static String STATUS_INACTIVE = "停用";

	/**
	 * 主经销商页面分页查询
	 */
	@Override
	public Page<Dealer> queryDealer(DealerMigrationAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return this.dealerMigrationRepository.queryDealer(searchCriteria, pageRequest);
	}

	@Override
	public Workbook exportDealer(List<Item> dealerList) {
		String[] excelHeader = {DEALER_EXTERNAL_ID, DEALER_NAME, DEALER_STATUS, PLATFORM_COMPANY, IS_PRIMARY_DEALER, PRIMARY_DEALER_ID, CITY_MANAGER_POSITION_ID};
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(FILE_NAME);
		XSSFRow row = sheet.createRow(0);
		XSSFCellStyle style = wb.createCellStyle();
		
		// Align Center
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// Vertical Center
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// width of each column(pixel)
		for(int i = 0; i < excelHeader.length; i++) {
			sheet.setColumnWidth(i, 20 * 256);
		}
		
		// Header
		for(int i = 0; i < excelHeader.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(excelHeader[i]);
			cell.setCellStyle(style);
		}
		
		// Create cells
		for(int rowIndex = 0; rowIndex < dealerList.size(); rowIndex++) {
			row = sheet.createRow(rowIndex + 1);
			Item dealerItem = dealerList.get(rowIndex);
			String dealerStatus = "";
			String isPlatform = "";
			String isPrimaryDealer = "";
			
			if(dealerItem.getStatus().equals(DealerStatus.ACTIVE)) {
				dealerStatus = STATUS_ACTIVE;
			} else if (dealerItem.getStatus().equals(DealerStatus.INACTIVE)) {
				dealerStatus = STATUS_INACTIVE;
			}
			
			if(dealerItem.getIsPlatformDealer()) {
				isPlatform = OPTION_IS;
			} else {
				isPlatform = OPTION_NOT;
			}
			
			if(dealerItem.getIsPrimaryDealer()) {
				isPrimaryDealer = OPTION_IS;
			} else {
				isPrimaryDealer = OPTION_NOT;
			}
			
			row.createCell(0).setCellValue(dealerItem.getExternalId());
			row.createCell(1).setCellValue(dealerItem.getName());
			row.createCell(2).setCellValue(dealerStatus);
			row.createCell(3).setCellValue(isPlatform);
			row.createCell(4).setCellValue(isPrimaryDealer);
			row.createCell(5).setCellValue(dealerItem.getPrimaryDealderId());
			row.createCell(6).setCellValue(dealerItem.getCityManagerPositionId());
		}
		
		return wb;
	}
}
