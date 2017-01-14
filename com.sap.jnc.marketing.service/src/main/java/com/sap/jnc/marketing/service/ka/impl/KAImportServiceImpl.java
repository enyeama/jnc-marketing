/**
 * 
 */
package com.sap.jnc.marketing.service.ka.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.response.ka.KAImportResponse;
import com.sap.jnc.marketing.dto.shared.ka.KAImportRecord;
import com.sap.jnc.marketing.persistence.enumeration.BranchLevel;
import com.sap.jnc.marketing.persistence.enumeration.PromoterType;
import com.sap.jnc.marketing.persistence.enumeration.TerminalStatus;
import com.sap.jnc.marketing.persistence.enumeration.TerminalType;
import com.sap.jnc.marketing.persistence.model.Contact;
import com.sap.jnc.marketing.persistence.model.DepartmentView;
import com.sap.jnc.marketing.persistence.model.GPS;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.Region;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.repository.ContactRepository;
import com.sap.jnc.marketing.persistence.repository.DepartmentViewRepository;
import com.sap.jnc.marketing.persistence.repository.PositionViewRepository;
import com.sap.jnc.marketing.persistence.repository.RegionRepository;
import com.sap.jnc.marketing.persistence.repository.TerminalRepository;
import com.sap.jnc.marketing.service.exception.CommonServiceException;
import com.sap.jnc.marketing.service.exception.migration.ka.UploadFileInvalidFormatException;
import com.sap.jnc.marketing.service.ka.KAImportService;

/**
 * @author Quansheng Liu I075496
 */
@Service
@Transactional
public class KAImportServiceImpl implements KAImportService {

	private static final int CONTENT_ROW_INDEX = 4;
	private static final String XLSX_FILE = ".xlsx";
	private static final String REQUIRED_KA_IMPORT_FILE_FORMAT = "xls";
	private static final int ROW_SIZE_MAX = 1000;
	private static final String FILE_TWO_MANY_ROWS = "文件里包含的数据行数过多，可能引起性能问题，建议分成多个文件导入，每个文件行数少于1000行。";
	private static final String INVALID_KA_OFFICE_ID = "办事处编号不正确或者数据库里没有该办事处的信息。";
	private static final String INVALID_REGION_ID = "区县编号不正确或者区域表里没有该区县编号的信息。";
	private static final String INVALID_KA_ACCOUNT_MANAGER_POSITION_ID = "KA客户经理不正确或者在岗位表里没有该KA客户经理的信息。";
	private static final String INVALID_KA_SPECIALIST_POSITION_ID = "KA专员不正确或者在岗位表里没有该KA专员的信息。";
	private static final String INVALID_MAINTAINER_POSITION_ID = "维护人不正确或者在岗位表里没有该维护人的信息。";
	private static final String INVALID_NAME = "网点名称不能为空";
	private static final String INVALID_ID = "网点编号不正确或者数据库里没有该KA网点的信息。";
	private static final long INVALID_KA_OFFICE_ID_ERROR_CODE = 1L;
	private static final long INVALID_REGION_ID_ERROR_CODE = 2L;
	private static final long INVALID_KA_ACCOUNT_MANAGER_POSITION_ID_ERROR_CODE = 3L;
	private static final long INVALID_KA_SPECIALIST_POSITION_ID_ERROR_CODE = 4L;
	private static final long INVALID_MAINTAINER_POSITION_ID_ERROR_CODE = 5L;
	private static final long INVALID_NAME_ERROR_CODE = 6L;
	private static final long INVALID_ID_ERROR_CODE = 6L;

	@Autowired
	private TerminalRepository terminalRepository;

	@Autowired
	private DepartmentViewRepository departmentViewRepository;

	@Autowired
	private PositionViewRepository positionViewRepository;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Override
	public Collection<KAImportResponse> importKARecords(InputStream inputStream, String fileName) {
		List<KAImportRecord> requestList = this.resolveKAImportExcelFile(inputStream, fileName);
		if (CollectionUtils.isEmpty(requestList)) {
			return Collections.emptyList();
		}
		List<KAImportResponse> responseList = new ArrayList<>();

		Map<String, DepartmentView> departmentViewMap = new HashMap<>();
		Map<String, PositionView> positionViewMap = new HashMap<>();
		Map<String, Region> regionMap = new HashMap<>();
		Map<String, Terminal> terminalMap = new HashMap<>();
		Set<String> positionViewIdSet = new HashSet<>();
		Set<String> countyIdSet = new HashSet<>();
		Set<String> terminalIdSet = new HashSet<>();
		validateKAImportRecords(requestList, responseList, departmentViewMap, positionViewIdSet, countyIdSet, terminalIdSet);
		if (!CollectionUtils.isEmpty(responseList)) {
			return responseList;
		}
		List<PositionView> positionViewList = Collections.emptyList();
		if (CollectionUtils.isEmpty(positionViewList)) {
			positionViewList = this.positionViewRepository.findByExternalIds(positionViewIdSet);
		}
		List<Region> regionList = Collections.emptyList();
		if (CollectionUtils.isEmpty(regionList)) {
			regionList = this.regionRepository.findByCountyIds(countyIdSet);
		}
		List<Terminal> terminalList = Collections.emptyList();
		if (!CollectionUtils.isEmpty(terminalIdSet)) {
			terminalList = this.terminalRepository.findByExternalIds(terminalIdSet);
		}
		for (PositionView positionView : positionViewList) {
			positionViewMap.put(positionView.getExternalId(), positionView);
		}
		for (Region region : regionList) {
			regionMap.put(region.getCountyId(), region);
		}
		if (!CollectionUtils.isEmpty(terminalList)) {
			for (Terminal terminal : terminalList) {
				terminalMap.put(terminal.getExternalId(), terminal);
			}
		}

		createKARecords(requestList, responseList, departmentViewMap, positionViewMap, regionMap, terminalMap);

		return responseList;
	}

	private void validateKAImportRecords(List<KAImportRecord> requestList, List<KAImportResponse> responseList,
		Map<String, DepartmentView> departmentViewMap, Set<String> positionViewIdSet, Set<String> countyIdSet, Set<String> terminalIdSet) {
		long i = 0;
		Iterator<KAImportRecord> it = requestList.iterator();
		while (it.hasNext()) {
			KAImportRecord request = it.next();
			i++;
			boolean isValid = true;
			String externalId = request.getExternalId();
			if (!StringUtils.isEmpty(externalId)) {
				terminalIdSet.add(externalId);
			}
			if (StringUtils.isEmpty(request.getBranchName())) {
				KAImportResponse response = new KAImportResponse(i, request.getBranchName(), INVALID_NAME_ERROR_CODE, INVALID_NAME);
				responseList.add(response);
				isValid = false;
			}
			String kaOfficeName = request.getKaOfficeDescription();
			DepartmentView departmentView = null;
			if (!StringUtils.isEmpty(kaOfficeName)) {
				departmentView = this.departmentViewRepository.findByDepartmentName(kaOfficeName);
			}
			if (departmentView == null) {
				KAImportResponse response = new KAImportResponse(i, request.getBranchName(), INVALID_KA_OFFICE_ID_ERROR_CODE, INVALID_KA_OFFICE_ID);
				responseList.add(response);
				isValid = false;
			}
			else {
				departmentViewMap.put(kaOfficeName, departmentView);
			}
			String accountManagerPositionExternalId = request.getAccountManagerPositionExternalId();
			if (StringUtils.isEmpty(accountManagerPositionExternalId)) {
				KAImportResponse response = new KAImportResponse(i, request.getBranchName(), INVALID_KA_ACCOUNT_MANAGER_POSITION_ID_ERROR_CODE,
					INVALID_KA_ACCOUNT_MANAGER_POSITION_ID);
				responseList.add(response);
				isValid = false;
			}
			else {
				positionViewIdSet.add(accountManagerPositionExternalId);
			}
			String specialistPositionExternalId = request.getSpecialistPositionExternalId();
			if (StringUtils.isEmpty(specialistPositionExternalId)) {
				KAImportResponse response = new KAImportResponse(i, request.getBranchName(), INVALID_KA_SPECIALIST_POSITION_ID_ERROR_CODE,
					INVALID_KA_SPECIALIST_POSITION_ID);
				responseList.add(response);
				isValid = false;
			}
			else {
				positionViewIdSet.add(specialistPositionExternalId);
			}
			String maintainerPositionExternalId = request.getMaintainerPositionExternalId();
			if (StringUtils.isEmpty(maintainerPositionExternalId)) {
				KAImportResponse response = new KAImportResponse(i, request.getBranchName(), INVALID_MAINTAINER_POSITION_ID_ERROR_CODE,
					INVALID_MAINTAINER_POSITION_ID);
				responseList.add(response);
				isValid = false;
			}
			else {
				positionViewIdSet.add(maintainerPositionExternalId);
			}
			String countyId = request.getCountyId();
			if (StringUtils.isEmpty(countyId)) {
				KAImportResponse response = new KAImportResponse(i, request.getBranchName(), INVALID_REGION_ID_ERROR_CODE, INVALID_REGION_ID);
				responseList.add(response);
				isValid = false;
			}
			else {
				countyIdSet.add(countyId);
			}
			if (!isValid) { // Delete invalid record.
				it.remove();
			}
		}
	}

	private void createKARecords(List<KAImportRecord> requestList, List<KAImportResponse> responseList, Map<String, DepartmentView> departmentViewMap,
		Map<String, PositionView> positionViewMap, Map<String, Region> regionMap, Map<String, Terminal> terminalMap) {
		Iterator<KAImportRecord> it = requestList.iterator();
		long i = 0;
		while (it.hasNext()) {
			KAImportRecord request = it.next();
			i++;
			boolean isValid = true;
			String externalId = request.getExternalId();
			if (!StringUtils.isEmpty(externalId) && (!terminalMap.containsKey(externalId) || terminalMap.get(externalId)
				.getStatus() != TerminalStatus.ACTIVE || terminalMap.get(externalId).getType() != TerminalType.KA)) {
				KAImportResponse response = new KAImportResponse(i, request.getBranchName(), INVALID_ID_ERROR_CODE, INVALID_ID);
				responseList.add(response);
				isValid = false;
			}
			String accountManagerPositionExternalId = request.getAccountManagerPositionExternalId();
			if (StringUtils.isEmpty(accountManagerPositionExternalId) || !positionViewMap.containsKey(accountManagerPositionExternalId)) {
				KAImportResponse response = new KAImportResponse(i, request.getBranchName(), INVALID_KA_ACCOUNT_MANAGER_POSITION_ID_ERROR_CODE,
					INVALID_KA_ACCOUNT_MANAGER_POSITION_ID);
				responseList.add(response);
				isValid = false;
			}
			String specialistPositionExternalId = request.getSpecialistPositionExternalId();
			if (StringUtils.isEmpty(specialistPositionExternalId) || !positionViewMap.containsKey(specialistPositionExternalId)) {
				KAImportResponse response = new KAImportResponse(i, request.getBranchName(), INVALID_KA_SPECIALIST_POSITION_ID_ERROR_CODE,
					INVALID_KA_SPECIALIST_POSITION_ID);
				responseList.add(response);
				isValid = false;
			}
			String maintainerPositionExternalId = request.getMaintainerPositionExternalId();
			if (StringUtils.isEmpty(maintainerPositionExternalId) || !positionViewMap.containsKey(maintainerPositionExternalId)) {
				KAImportResponse response = new KAImportResponse(i, request.getBranchName(), INVALID_MAINTAINER_POSITION_ID_ERROR_CODE,
					INVALID_MAINTAINER_POSITION_ID);
				responseList.add(response);
				isValid = false;
			}
			String countyId = request.getCountyId();
			if (StringUtils.isEmpty(countyId) || !regionMap.containsKey(countyId)) {
				KAImportResponse response = new KAImportResponse(i, request.getBranchName(), INVALID_REGION_ID_ERROR_CODE, INVALID_REGION_ID);
				responseList.add(response);
				isValid = false;
			}
			if (!isValid) {
				it.remove();
			}
		}
		if (!CollectionUtils.isEmpty(responseList)) {
			return;
		}
		for (KAImportRecord request : requestList) {
			createOrUpdateKARecord(request, departmentViewMap, positionViewMap, regionMap, terminalMap);
		}
	}

	private void createOrUpdateKARecord(KAImportRecord request, Map<String, DepartmentView> departmentViewMap,
		Map<String, PositionView> positionViewMap, Map<String, Region> regionMap, Map<String, Terminal> terminalMap) {
		String externalId = request.getExternalId();
		Terminal terminal = null;
		if (StringUtils.isEmpty(externalId)) {
			terminal = new Terminal();
		}
		else {
			terminal = terminalMap.get(externalId);
		}
		terminal.setType(TerminalType.KA);
		terminal.setStatus(TerminalStatus.ACTIVE);
		terminal.setAddress(request.getAddress());
		terminal.setBranchName(request.getBranchName());
		terminal.setBranchLevel(BranchLevel.labelOf(request.getBranchLevelStr()));
		terminal.setKAOffice(departmentViewMap.get(request.getKaOfficeDescription()));
		terminal.setStoreNumber(request.getStoreNumber());
		terminal.setKASystemName(request.getKaSystemName());
		terminal.setKAProducts(request.getKaProducts());
		terminal.setKAAcountManager(positionViewMap.get(request.getAccountManagerPositionExternalId()));
		terminal.setKASpecialist(positionViewMap.get(request.getSpecialistPositionExternalId()));
		PositionView maintinerPositionView = positionViewMap.get(request.getMaintainerPositionExternalId());
		terminal.setMaintainer(maintinerPositionView);
		Region region = regionMap.get(request.getCountyId());
		terminal.setRegion(region);
		terminal.setCountyId(request.getCountyId());
		GPS gps = new GPS();
		gps.setProvince(request.getProvince());
		gps.setCity(request.getCity());
		terminal.setGps(gps);
		terminal.setRegion(regionMap.get(request.getCountyId()));
		terminal.setBusinessStatus(request.getBusinessStatus());
		terminal.setKASystemPropertyNumber(request.getSystemPropertyNumber());
		terminal.setKASystemPropertyName(request.getSystemPropertyName());
		Contact keyUserContact = prepareContact(terminal.getKeyUserContact(), request.getKeyUserContactName(), request.getKeyUserContactPhone(),
			request.getKeyUserContactWechat());
		if (keyUserContact != null) {
			this.contactRepository.saveAndFlush(keyUserContact);
		}
		terminal.setKeyUserContact(keyUserContact);
		Contact purchaseContact = prepareContact(terminal.getPurchaserContact(), request.getPurchaseContactName(), request.getPurchaseContactPhone(),
			request.getPurchaseContactWechat());
		if (purchaseContact != null) {
			this.contactRepository.saveAndFlush(purchaseContact);
		}
		terminal.setKeyUserContact(purchaseContact);
		Contact kaPurchaseContact = prepareContact(terminal.getKaPurchaserContact(), request.getKaPurchaseContactName(), request
			.getKaPurchaseContactPhone(), request.getKaPurchaseContactWechat());
		if (kaPurchaseContact != null) {
			this.contactRepository.saveAndFlush(kaPurchaseContact);
		}
		terminal.setKaPurchaserContact(kaPurchaseContact);
		Contact promoterContact = prepareContact(terminal.getPromoterContact(), request.getPromoterContactName(), request.getPromoterContactPhone(),
			request.getPromoterContactWechat());
		if (promoterContact != null) {
			this.contactRepository.saveAndFlush(promoterContact);
		}
		terminal.setPromoterContact(promoterContact);
		terminal.setPromoterType(PromoterType.labelOf(request.getPromoterTypeStr()));
		this.terminalRepository.saveAndFlush(terminal);
		postCreateKARecord(terminal, maintinerPositionView);
	}

	private void postCreateKARecord(Terminal terminal, PositionView maintinerPositionView) {
		if (StringUtils.isEmpty(terminal.getExternalId())) {
			terminal.setExternalId(terminal.getCountyId() + terminal.getId());
		}
		List<PositionView> salesmanList = terminal.getSalesmen();
		if (!CollectionUtils.isEmpty(salesmanList)) {
			Iterator<PositionView> pit = salesmanList.iterator();
			boolean find = false;
			while (pit.hasNext()) {
				PositionView positionView = pit.next();
				if (maintinerPositionView != null && positionView.getId() == maintinerPositionView.getId()) {
					find = true;
					continue;
				}
				List<Terminal> terminals = positionView.getTerminals();
				Iterator<Terminal> tit = terminals.iterator();
				while (tit.hasNext()) {
					Terminal oTerminal = tit.next();
					if (oTerminal.getId() == terminal.getId()) {
						tit.remove();
					}
				}
				this.positionViewRepository.saveAndFlush(positionView);
				pit.remove();
			}
			if (!find && maintinerPositionView != null) {
				List<PositionView> positionViewList = new ArrayList<>();
				if (CollectionUtils.isEmpty(maintinerPositionView.getTerminals())) {
					maintinerPositionView.setTerminals(new ArrayList<>());
				}
				maintinerPositionView.getTerminals().add(terminal);
				this.positionViewRepository.saveAndFlush(maintinerPositionView);
				positionViewList.add(maintinerPositionView);
				terminal.setSalesmen(positionViewList);
			}
		}
		else if (maintinerPositionView != null) {
			List<PositionView> positionViewList = new ArrayList<>();
			if (CollectionUtils.isEmpty(maintinerPositionView.getTerminals())) {
				maintinerPositionView.setTerminals(new ArrayList<>());
			}
			maintinerPositionView.getTerminals().add(terminal);

			this.positionViewRepository.saveAndFlush(maintinerPositionView);
			positionViewList.add(maintinerPositionView);
			terminal.setSalesmen(positionViewList);
		}
		this.terminalRepository.saveAndFlush(terminal);
	}

	private Contact prepareContact(Contact contact, String name, String phone, String wechat) {
		if (contact == null && StringUtils.isEmpty(name) && StringUtils.isEmpty(phone) && StringUtils.isEmpty(wechat)) {
			return null;
		}
		Contact newContact = contact;
		if (newContact == null) {
			newContact = new Contact();
		}
		newContact.setName(name);
		newContact.setPhone(phone);
		newContact.setWechat(wechat);
		return newContact;
	}

	private List<KAImportRecord> resolveKAImportExcelFile(InputStream inputStream, String fileName) {
		Workbook wb = null;
		try {
			if (fileName.contains(XLSX_FILE)) {
				wb = new XSSFWorkbook(inputStream);
			}
			else {
				wb = new HSSFWorkbook(inputStream);
			}
		}
		catch (IOException ex) {
			throw new UploadFileInvalidFormatException(REQUIRED_KA_IMPORT_FILE_FORMAT, ex);
		}
		Sheet sheet = wb.getSheetAt(0); // 获得第一个表单
		Iterator<Row> rows = sheet.rowIterator(); // 获得第一个表单的迭代器
		int i = 0;
		if (sheet.getLastRowNum() > ROW_SIZE_MAX) {
			throw new CommonServiceException(FILE_TWO_MANY_ROWS);
		}
		List<KAImportRecord> records = new LinkedList<KAImportRecord>();
		while (rows.hasNext()) {
			i++;
			Row row = rows.next(); // 获得行数据
			if (i < CONTENT_ROW_INDEX) {
				continue;
			}
			int cellNum = row.getLastCellNum();
			KAImportRecord record = new KAImportRecord();
			String externalId = getCellStringValue(row.getCell(0));
			record.setExternalId(externalId);
			String kaOfficeDescription = getCellStringValue(row.getCell(1));
			record.setKaOfficeDescription(kaOfficeDescription);
			String branchName = getCellStringValue(row.getCell(2));
			record.setBranchName(branchName);
			String address = getCellStringValue(row.getCell(3));
			record.setAddress(address);
			String storeNumber = getCellStringValue(row.getCell(4));
			record.setStoreNumber(storeNumber);
			String kaSystemName = getCellStringValue(row.getCell(5));
			record.setKaSystemName(kaSystemName);
			String kaProducts = getCellStringValue(row.getCell(6));
			record.setKaProducts(kaProducts);
			String accountManagerPositionExternalId = getCellStringValue(row.getCell(7));
			record.setAccountManagerPositionExternalId(accountManagerPositionExternalId);
			String accountManagerEmployeeName = getCellStringValue(row.getCell(8));
			record.setAccountManagerEmployeeName(accountManagerEmployeeName);
			String specialistPositionExternalId = getCellStringValue(row.getCell(9));
			record.setSpecialistPositionExternalId(specialistPositionExternalId);
			String specialistEmployeeName = getCellStringValue(row.getCell(10));
			record.setSpecialistEmployeeName(specialistEmployeeName);
			String maintainerPositionExternalId = getCellStringValue(row.getCell(11));
			record.setMaintainerPositionExternalId(maintainerPositionExternalId);
			String maintainerEmployeeName = getCellStringValue(row.getCell(12));
			record.setMaintainerEmployeeName(maintainerEmployeeName);
			String province = getCellStringValue(row.getCell(13));
			record.setProvince(province);
			String city = getCellStringValue(row.getCell(14));
			record.setCity(city);
			String county = getCellStringValue(row.getCell(15));
			record.setCounty(county);
			String countyId = getCellStringValue(row.getCell(16));
			record.setCountyId(countyId);
			String businessStatus = getCellStringValue(row.getCell(17));
			record.setBusinessStatus(businessStatus);
			String branchLevelStr = getCellStringValue(row.getCell(18));
			record.setBranchLevelStr(branchLevelStr);
			String systemPropertyNumber = getCellStringValue(row.getCell(19));
			record.setSystemPropertyNumber(systemPropertyNumber);
			String systemPropertyName = getCellStringValue(row.getCell(20));
			record.setSystemPropertyName(systemPropertyName);
			String keyUserContactName = getCellStringValue(row.getCell(21));
			record.setKeyUserContactName(keyUserContactName);
			String keyUserContactPhone = getCellStringValue(row.getCell(22));
			record.setKeyUserContactPhone(keyUserContactPhone);
			String keyUserContactWechat = getCellStringValue(row.getCell(23));
			record.setKeyUserContactWechat(keyUserContactWechat);
			String purchaseContactName = getCellStringValue(row.getCell(24));
			record.setPurchaseContactName(purchaseContactName);
			String purchaseContactPhone = getCellStringValue(row.getCell(25));
			record.setPurchaseContactPhone(purchaseContactPhone);
			String purchaseContactWechat = getCellStringValue(row.getCell(26));
			record.setPurchaseContactWechat(purchaseContactWechat);
			String kaPurchaseContactName = getCellStringValue(row.getCell(27));
			record.setKaPurchaseContactName(kaPurchaseContactName);
			String kaPurchaseContactPhone = getCellStringValue(row.getCell(28));
			record.setKaPurchaseContactPhone(kaPurchaseContactPhone);
			String kaPurchaseContactWechat = getCellStringValue(row.getCell(29));
			record.setKaPurchaseContactWechat(kaPurchaseContactWechat);
			String promoterContactName = getCellStringValue(row.getCell(30));
			record.setPromoterContactName(promoterContactName);
			String promoterTypeStr = getCellStringValue(row.getCell(31));
			record.setPromoterTypeStr(promoterTypeStr);
			String promoterContactPhone = getCellStringValue(row.getCell(32));
			record.setPromoterContactPhone(promoterContactPhone);
			String promoterContactWechat = getCellStringValue(row.getCell(33));
			record.setPromoterContactWechat(promoterContactWechat);
			records.add(record);
		}
		return records;
	}

	private String getCellStringValue(Cell cell) {
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:// 字符串类型
			cellValue = cell.getStringCellValue().trim();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC: // 数值类型
			double numericValue = cell.getNumericCellValue();
			long longValue = (long) numericValue;
			cellValue = String.valueOf(longValue);
			break;
		case HSSFCell.CELL_TYPE_FORMULA: // 公式
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			cellValue = "";
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			break;
		default:
			break;
		}
		return cellValue;
	}
}
