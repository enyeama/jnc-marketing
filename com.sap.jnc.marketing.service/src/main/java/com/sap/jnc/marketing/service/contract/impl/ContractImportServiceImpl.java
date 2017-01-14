package com.sap.jnc.marketing.service.contract.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
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

import com.sap.jnc.marketing.dto.response.contract.ContractImportResponse;
import com.sap.jnc.marketing.dto.response.contract.ContractImportResponseBody;
import com.sap.jnc.marketing.dto.shared.contract.ContractRecord;
import com.sap.jnc.marketing.persistence.enumeration.ContractStatus;
import com.sap.jnc.marketing.persistence.model.Channel;
import com.sap.jnc.marketing.persistence.model.Contract;
import com.sap.jnc.marketing.persistence.model.ContractItem;
import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;
import com.sap.jnc.marketing.persistence.model.Region;
import com.sap.jnc.marketing.persistence.model.ValidityPeriod;
import com.sap.jnc.marketing.persistence.repository.ChannelRepository;
import com.sap.jnc.marketing.persistence.repository.ContractItemRepository;
import com.sap.jnc.marketing.persistence.repository.ContractRepository;
import com.sap.jnc.marketing.persistence.repository.DealerRepository;
import com.sap.jnc.marketing.persistence.repository.PositionViewRepository;
import com.sap.jnc.marketing.persistence.repository.ProductDmsCategoryRepository;
import com.sap.jnc.marketing.persistence.repository.RegionRepository;
import com.sap.jnc.marketing.service.contract.ContractImportDeleteService;
import com.sap.jnc.marketing.service.contract.ContractImportService;

@Service
@Transactional
public class ContractImportServiceImpl implements ContractImportService {
	private static final String XLSX_FILE = ".xlsx";
	private static final String REQUIRED_KA_IMPORT_FILE_FORMAT = "xls";
	private static final int ROW_SIZE_MAX = 1000;
	private static final String FILE_TWO_MANY_ROWS = "文件不能超过" + ROW_SIZE_MAX + "行";
	private static final String INVALID_OPERATION = "操作标识错误";
	private static final String INVALID_CONTRACT_ID = "合同ID不能为空";
	private static final String INVALID_CONTRACT_ID_EXISTED = "合同ID已存在";
	private static final String INVALID_CONTRACT_TEMPLATE = "合同模板不能为空";
	private static final String INVALID_STATUS = "合同状态错误";
	private static final String INVALID_DEALER_ID = "经销商ID不能为空";
	private static final String INVALID_DEALER_ID_EXISTED = "经销商ID不存在";
	private static final String INVALID_POSITION_ID = "城市经理岗位不能为空";
	private static final String INVALID_POSITION_ID_EXISTED = "城市经理岗位不存在";
	private static final String INVALID_FINACIAL_YEAR = "财年不能为空";
	private static final String INVALID_VALID_FROM = "合同开始日期错误";
	private static final String INVALID_VALID_TO = "合同截止日期错误";
	private static final String INVALID_DMS_CATEGORY = "物料三大类ID不能为空";
	private static final String INVALID_DMS_CATEGORY_EXISTED = "物料三大类ID不存在";
	private static final String INVALID_CHANNEL = "渠道不存在";

	private static final long INVALID_OPERATION_ERROR_CODE = 1L;
	private static final long INVALID_CONTRACT_ID_CODE = 2L;
	private static final long INVALID_CONTRACT_TEMPLATE_CODE = 3L;
	private static final long INVALID_STATUS_CODE = 4L;
	private static final long INVALID_DEALER_ID_CODE = 5L;
	private static final long INVALID_POSITION_ID_CODE = 6L;
	private static final long INVALID_FINACIAL_YEAR_CODE = 7L;
	private static final long INVALID_VALID_FROM_CODE = 8L;
	private static final long INVALID_VALID_TO_CODE = 9L;
	private static final long INVALID_DMS_CATEGORY_CODE = 10L;
	private static final long INVALID_CHANNEL_CODE = 11L;

	@Autowired
	private ContractRepository contractRepository;
	@Autowired
	private ContractItemRepository contractItemRepository;
	@Autowired
	private DealerRepository dealerRepository;
	@Autowired
	private PositionViewRepository positionViewRepository;
	@Autowired
	private ProductDmsCategoryRepository productDmsCategoryRepository;
	@Autowired
	private ChannelRepository channelRepository;
	@Autowired
	private RegionRepository regionRepository;
	@Autowired
	private ContractImportDeleteService contractImportDeleteService;

	@Override
	public ContractImportResponseBody importContractRecords(InputStream inputStream, String fileName) {
		List<ContractRecord> requestList = this.resolveContractImportExcelFile(inputStream, fileName);
		ContractImportResponseBody responseBody = new ContractImportResponseBody();
		List<ContractImportResponse> responseList = new ArrayList<>();
		if (CollectionUtils.isEmpty(requestList)) {
			responseBody.setContractImportResponse(responseList);
			return responseBody;
		}

		responseBody.setTotalCount(requestList.size());

		Map<String, Contract> contractMap = new HashMap<>();
		Map<String, Channel> channelMap = new HashMap<>();
		Map<String, Dealer> dealerMap = new HashMap<>();
		Map<String, PositionView> cityManagerMap = new HashMap<>();
		Map<String, ProductDmsCategory> categoryMap = new HashMap<>();
		Map<String, Region> regionMap = new HashMap<>();

		Set<String> contractIdSet = new HashSet<>();
		Set<String> channelIdSet = new HashSet<>();
		Set<String> dealerIdSet = new HashSet<>();
		Set<String> positionIdSet = new HashSet<>();
		Set<String> categoryIdSet = new HashSet<>();
		Set<String> regionIdSet = new HashSet<>();

		String operation = validateOpertions(requestList, responseList);
		if (responseList.size() > 0) {
			responseBody.setErrorCount(responseList.size());
			responseBody.setContractImportResponse(responseList);
			return responseBody;
		}
		if (operation.equalsIgnoreCase("D")) {
			contractImportDeleteService.ContractDelete(requestList, responseList);
			if (responseList.size() > 0) {
				responseBody.setErrorCount(responseList.size());
				responseBody.setContractImportResponse(responseList);
			}
			return responseBody;
		}
		else if (operation.equalsIgnoreCase("U")) {
			contractImportDeleteService.ContractDelete(requestList, responseList);
			if (responseList.size() > 0) {
				responseBody.setErrorCount(responseList.size());
				responseBody.setContractImportResponse(responseList);
				return responseBody;
			}

		}

		long errorCount = validateContractImportRecords(requestList, responseList, contractIdSet, channelIdSet, dealerIdSet, positionIdSet,
			categoryIdSet, regionIdSet);
		if (responseList.size() > 0) {
			responseBody.setErrorCount(errorCount);
			responseBody.setContractImportResponse(responseList);
			return responseBody;
		}

		List<Contract> contractList = contractRepository.findContractByExternalIds(contractIdSet);
		List<Dealer> dealerList = dealerRepository.findDealerByExternalIds(dealerIdSet);
		List<PositionView> positionList = positionViewRepository.findPositionViewByexternalIds(positionIdSet);
		List<ProductDmsCategory> categoryList = productDmsCategoryRepository.findDMSCategoryByIds(categoryIdSet);
		List<Channel> channelList = channelRepository.findByIds(channelIdSet);
		List<Region> regionList = regionRepository.findByCountyIds(regionIdSet);

		for (Contract contract : contractList) {
			contractMap.put(contract.getExternalId(), contract);
		}
		for (Dealer dealer : dealerList) {
			dealerMap.put(dealer.getExternalId(), dealer);
		}
		for (PositionView position : positionList) {
			cityManagerMap.put(position.getExternalId(), position);
		}
		for (ProductDmsCategory category : categoryList) {
			categoryMap.put(category.getId(), category);
		}
		for (Channel channel : channelList) {
			channelMap.put(channel.getExternalId(), channel);
		}
		for (Region region : regionList) {
			regionMap.put(region.getCountyId(), region);
		}

		errorCount = validateDBMasterData(requestList, responseList, contractMap, channelMap, dealerMap, cityManagerMap, categoryMap, regionMap);

		if (responseList.size() > 0) {
			responseBody.setErrorCount(errorCount);
			responseBody.setContractImportResponse(responseList);
			return responseBody;
		}

		insertIntoDatabase(requestList, contractMap, channelMap, dealerMap, cityManagerMap, categoryMap, regionMap, contractIdSet);
		responseBody.setContractImportResponse(responseList);
		return responseBody;
	}

	private void insertIntoDatabase(List<ContractRecord> requestList, Map<String, Contract> contractMap, Map<String, Channel> channelMap,
		Map<String, Dealer> dealerMap, Map<String, PositionView> cityManagerMap, Map<String, ProductDmsCategory> categoryMap,
		Map<String, Region> regionMap, Set<String> contractIdSet) {
		for (String ContractId : contractIdSet) {
			Contract contract = new Contract();
			contract.setExternalId(ContractId);
			contractMap.put(ContractId, contract);
		}
		for (ContractRecord record : requestList) {
			// Set Contract
			Contract contract = contractMap.get(record.getContratId());

			Dealer dealer = dealerMap.get(record.getDealerId());
			contract.setDealer(dealer);

			PositionView position = cityManagerMap.get(record.getPositionId());
			contract.setCityManager(position);

			contract.setTemplateName(record.getContractTemplate());
			contract.setStatus(record.getStatusEnum());

			contract.setFinancialYear(record.getFinacialYear());
			ValidityPeriod validityPeriod = new ValidityPeriod();
			validityPeriod.setValidFrom(record.getValidFrom());
			validityPeriod.setValidTo(record.getValidTo());
			contract.setValidityPeriod(validityPeriod);

			ContractItem contractItem = new ContractItem();
			contractItem.setChannel(channelMap.get(record.getChannelId()));

			String regionArray = record.getRegion();
			String[] regionsStr = regionArray.split(";");
			for (String regionStr : regionsStr) {
				Region rg = regionMap.get(regionStr);
				if (rg != null) {
					List<Region> rgList = contractItem.getRegions();
					if (CollectionUtils.isEmpty(rgList)) {
						rgList = new ArrayList<Region>();
					}
					rgList.add(rg);
					contractItem.setRegions(rgList);
				}
			}

			ProductDmsCategory productDmsCategory = categoryMap.get(record.getDmsCategoryId());
			contractItem.setDmsCategory(productDmsCategory);

			// Set relationship between Contract and ContractItem
			contractItem.setContract(contract);
			List<ContractItem> items = null;
			if (CollectionUtils.isEmpty(contract.getItems())) {
				items = new ArrayList<>();
			}
			else {
				items = contract.getItems();
			}
			items.add(contractItem);
			contract.setItems(items);
			contractItemRepository.save(contractItem);
			contractMap.put(record.getContratId(), contract);
		}

		for (String contractId : contractMap.keySet()) {
			Contract contract = contractMap.get(contractId);
			contractRepository.save(contract);
		}
	}

	private long validateDBMasterData(List<ContractRecord> requestList, List<ContractImportResponse> responseList, Map<String, Contract> contractMap,
		Map<String, Channel> channelMap, Map<String, Dealer> dealerMap, Map<String, PositionView> cityManagerMap,
		Map<String, ProductDmsCategory> categoryMap, Map<String, Region> regionMap) {
		Iterator<ContractRecord> it = requestList.iterator();
		long errorCount = 0;
		long i = 3;
		while (it.hasNext()) {
			boolean hasError = false;
			ContractRecord request = it.next();
			i++;
			if (!contractMap.isEmpty() && contractMap.containsKey(request.getContratId())) {
				ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, request
					.getContratId(), INVALID_CONTRACT_ID_CODE, INVALID_CONTRACT_ID_EXISTED);
				responseList.add(response);
				hasError = true;
			}

			if (!dealerMap.containsKey(request.getDealerId())) {
				ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, request
					.getDealerId(), INVALID_DEALER_ID_CODE, INVALID_DEALER_ID_EXISTED);
				responseList.add(response);
				hasError = true;
			}

			if (!cityManagerMap.containsKey(request.getPositionId())) {
				ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, request
					.getPositionId(), INVALID_POSITION_ID_CODE, INVALID_POSITION_ID_EXISTED);
				responseList.add(response);
				hasError = true;
			}

			if (!categoryMap.containsKey(request.getDmsCategoryId())) {
				ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, request
					.getDmsCategoryId(), INVALID_DMS_CATEGORY_CODE, INVALID_DMS_CATEGORY_EXISTED);
				responseList.add(response);
				hasError = true;
			}

			if (!channelMap.containsKey(request.getChannelId())) {
				ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, request
					.getDmsCategoryId(), INVALID_CHANNEL_CODE, INVALID_CHANNEL);
				responseList.add(response);
				hasError = true;
			}

			if (hasError) {
				errorCount++;
			}
		}
		return errorCount;
	}

	private long validateContractImportRecords(List<ContractRecord> requestList, List<ContractImportResponse> responseList, Set<String> contractIdSet,
		Set<String> channelIdSet, Set<String> dealerIdSet, Set<String> positionIdSet, Set<String> categoryIdSet, Set<String> regionIdSet) {
		long i = 3;
		long errorCount = 0;
		Iterator<ContractRecord> it = requestList.iterator();
		while (it.hasNext()) {
			ContractRecord request = it.next();
			i++;
			boolean isRemove = false;
			if (StringUtils.isBlank(request.getContratId())) {
				ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, request
					.getContratId(), INVALID_CONTRACT_ID_CODE, INVALID_CONTRACT_ID);
				responseList.add(response);
				isRemove = true;
			}
			else {
				contractIdSet.add(request.getContratId());
			}

			if (StringUtils.isBlank(request.getContractTemplate())) {
				ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, request
					.getContractTemplate(), INVALID_CONTRACT_TEMPLATE_CODE, INVALID_CONTRACT_TEMPLATE);
				responseList.add(response);
				isRemove = true;
			}

			if (StringUtils.isNotBlank(request.getStatus()) && !request.getStatus().equalsIgnoreCase("X")) {
				ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, request
					.getStatus(), INVALID_STATUS_CODE, INVALID_STATUS);
				responseList.add(response);
				isRemove = true;
			}
			else {
				if (StringUtils.isBlank(request.getStatus())) {
					request.setStatusEnum(ContractStatus.ACTIVE);
				}
				else {
					request.setStatusEnum(ContractStatus.INACTIVE);
				}
			}

			if (StringUtils.isBlank(request.getDealerId())) {
				ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, request
					.getDealerId(), INVALID_DEALER_ID_CODE, INVALID_DEALER_ID);
				responseList.add(response);
				isRemove = true;
			}
			else {
				dealerIdSet.add(request.getDealerId());
			}

			if (StringUtils.isBlank(request.getPositionId())) {
				ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, request
					.getPositionId(), INVALID_POSITION_ID_CODE, INVALID_POSITION_ID);
				responseList.add(response);
				isRemove = true;
			}
			else {
				positionIdSet.add(request.getPositionId());
			}

			if (StringUtils.isBlank(request.getFinacialYear())) {
				ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, request
					.getFinacialYear(), INVALID_FINACIAL_YEAR_CODE, INVALID_FINACIAL_YEAR);
				responseList.add(response);
				isRemove = true;
			}

			DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
			if (StringUtils.isBlank(request.getValidFromStr())) {
				ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, null,
					INVALID_VALID_FROM_CODE, INVALID_VALID_FROM);
				responseList.add(response);
				isRemove = true;
			}
			else {
				try {
					Date date = dateFormatter.parse(request.getValidFromStr());
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					request.setValidFrom(calendar);
				}
				catch (ParseException e) {
					ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, request
						.getValidFromStr(), INVALID_VALID_FROM_CODE, INVALID_VALID_FROM);
					responseList.add(response);
					isRemove = true;
				}
			}

			if (StringUtils.isBlank(request.getValidToStr())) {
				ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, null,
					INVALID_VALID_TO_CODE, INVALID_VALID_TO);
				responseList.add(response);
				isRemove = true;
			}
			else {
				try {
					Date date = dateFormatter.parse(request.getValidToStr());
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					request.setValidTo(calendar);
				}
				catch (ParseException e) {
					ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, request
						.getValidToStr(), INVALID_VALID_TO_CODE, INVALID_VALID_TO);
					responseList.add(response);
					isRemove = true;
				}
			}

			if (StringUtils.isBlank(request.getDmsCategoryId())) {
				ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, request
					.getDmsCategoryId(), INVALID_DMS_CATEGORY_CODE, INVALID_DMS_CATEGORY);
				responseList.add(response);
				isRemove = true;
			}
			else {
				categoryIdSet.add(request.getDmsCategoryId());
			}

			if (StringUtils.isBlank(request.getChannelId())) {
				ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, request
					.getChannelId(), INVALID_CHANNEL_CODE, INVALID_CHANNEL);
				responseList.add(response);
				isRemove = true;
			}
			else {
				channelIdSet.add(request.getChannelId());
			}

			// Region can be empty
			if (StringUtils.isNotBlank(request.getRegion())) {
				String[] regions = request.getRegion().split(";");
				for (String region : regions) {
					regionIdSet.add(region);
				}
			}

			if (isRemove == true) {
				it.remove();
				errorCount++;
			}
		}
		return errorCount;
	}

	private List<ContractRecord> resolveContractImportExcelFile(InputStream inputStream, String fileName) {
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
			throw new RuntimeException(REQUIRED_KA_IMPORT_FILE_FORMAT, ex);
		}
		Sheet sheet = wb.getSheetAt(0); // 获得第一个表单
		Iterator<Row> rows = sheet.rowIterator(); // 获得第一个表单的迭代器
		int i = 0;
		if (sheet.getLastRowNum() > ROW_SIZE_MAX) {
			throw new RuntimeException(FILE_TWO_MANY_ROWS);// TODO change own exception
		}
		List<ContractRecord> records = new LinkedList<ContractRecord>();
		while (rows.hasNext()) {
			i++;
			Row row = rows.next(); // 获得行数据
			if (i < 4) {
				continue;
			}
			int cellNum = row.getLastCellNum();
			ContractRecord record = new ContractRecord();
			String operationFlag = getCellStringValue(row.getCell(0));// 操作标识
			record.setOperationFlag(operationFlag);
			String contractId = getCellStringValue(row.getCell(1));// 合同ID
			record.setContratId(contractId);
			String contractTemplate = getCellStringValue(row.getCell(2));// 合同模板
			record.setContractTemplate(contractTemplate);

			String status = getCellStringValue(row.getCell(3));// 合同状态
			record.setStatus(status);

			String dealerId = getCellStringValue(row.getCell(4));// 经销商ID
			record.setDealerId(dealerId);

			String positionId = getCellStringValue(row.getCell(5));// 城市经理岗位
			record.setPositionId(positionId);

			String finacialYear = getCellStringValue(row.getCell(6));// 财年
			record.setFinacialYear(finacialYear);

			String validFrom = getCellStringValue(row.getCell(7));// 合同开始日期
			record.setValidFromStr(validFrom);

			String validTo = getCellStringValue(row.getCell(8));// 合同截止日期
			record.setValidToStr(validTo);

			String dmsCategoryId = getCellStringValue(row.getCell(9));// 物料三大类
			record.setDmsCategoryId(dmsCategoryId);

			String channelId = getCellStringValue(row.getCell(10));// 渠道
			record.setChannelId(channelId);

			String regions = getCellStringValue(row.getCell(11));// 区域
			record.setRegion(regions);
			records.add(record);
		}

		return records;
	}

	private String validateOpertions(List<ContractRecord> requestList, List<ContractImportResponse> responseList) {
		String operation = null;
		Iterator<ContractRecord> it = requestList.iterator();
		int i = 3;
		while (it.hasNext()) {
			ContractRecord request = it.next();
			i++;
			String operationFlag = request.getOperationFlag();
			if (StringUtils.isBlank(operationFlag)) {
				ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, request
					.getOperationFlag(), INVALID_OPERATION_ERROR_CODE, INVALID_OPERATION);
				responseList.add(response);
				it.remove();
				continue;
			}
			else if (!"C".equalsIgnoreCase(operationFlag) && !"U".equalsIgnoreCase(operationFlag) && !"D".equalsIgnoreCase(operationFlag)) {
				ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, request
					.getOperationFlag(), INVALID_OPERATION_ERROR_CODE, INVALID_OPERATION);
				responseList.add(response);
				it.remove();
				continue;
			}
			else {
				if (operation == null)
					operation = operationFlag;
				if (!operationFlag.equalsIgnoreCase(operation)) {
					ContractImportResponse response = new ContractImportResponse(request.getOperationFlag(), request.getContratId(), i, request
						.getOperationFlag(), INVALID_OPERATION_ERROR_CODE, INVALID_OPERATION);
					responseList.add(response);
					it.remove();
					continue;
				}
			}
		}
		return operation;
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
