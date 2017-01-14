package com.sap.jnc.marketing.service.migration.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.request.migration.DealerImportRequest;
import com.sap.jnc.marketing.dto.response.migration.DealerMigrationImportErrorResponse;
import com.sap.jnc.marketing.dto.response.migration.DealerMigrationResponse;
import com.sap.jnc.marketing.persistence.enumeration.DealerStatus;
import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.repository.DealerMigrationRepository;
import com.sap.jnc.marketing.persistence.repository.PositionViewRepository;
import com.sap.jnc.marketing.service.migration.DealerImportService;

@Service
@Transactional
public class DealerImportServiceImpl implements DealerImportService {
	private static final int REQUIRED_LEAST_RECORD_COUNT = 2;
	private static final int LIMIT_RECORD_COUNT = 1002;

	private static final int DEALER_OPERATION_CELL_INDEX = 0;
	private static final int DEALER_ID_CELL_INDEX = 1;
	private static final int DEALER_NAME_CELL_INDEX = 2;
	private static final int DEALER_STATUS_CELL_INDEX = 3;
	private static final int PLATFORM_COMPANY_CELL_INDEX = 4;
	private static final int IS_CENTRAL_DEALER_ID = 5;
	private static final int CENTRAL_DEALER_ID = 6;
	private static final int CITY_MANAGER_POSITION = 7;

	private static String OPERATION_CREATE_VALUE = "C";
	private static String OPERATION_UPDATE_VALUE = "U";
	private static String OPERATION_DELETE_VALUE = "D";

	private static final int FIRST_ROW_NUMBER = 3;

	private static final String REQUIRED_IMPORT_FILE_FORMAT = "xls";
	private static final String NO_RECORD_IN_UPLOAD_FILE = "导入文件中没有记录";
	private static final String RECORD_COUNT_BEYOND_LIMIT = "导入文件记录数超过最大限度1000条!";
	private static final String OPERATION_CELL_EMPTY = "操作一栏值为空! 请填写C,U,D中的一种！";
	private static final String OPERATION_CELL_ILLEGAL = "操作值不合法！ 请填写C,U,D中的一种！";
	private static final String DEALER_ID_CELL_EMPTY = "经销商ID不能为空！";
	private static final String DEALER_ID_CELL_ILLEGAL = "经销商ID出现除字母和数字以外字符！";
	private static final String DEALER_NAME_CELL_EMPTY = "经销商名称不能为空！";
	private static final String DEALER_NAME_CELL_ILLEGAL = "经销商名称出现除字母和数字以外字符！";
	private static final String DUPLICATE_CREATE_DEALER_ID = "出现重复的经销商编码插入记录";
	private static final String DUPLICATE_DELETE_DEALER_ID = "出现重复的经销商编码删除记录";
	private static final String DEALER_ID_EXISTED = "该经销商ID已存在";
	private static final String DEALER_ID_NOT_EXIST = "该经销商ID不存在";
	private static final String POSITION_ID_NOT_EXIST = "该经销商负责人岗位ID不存在";
	private static final String PRIMARY_DEALER_ID_EMPTY = "非主经销商的主经销商ID不能为空";
	private static final String PRIMARY_DEALER_ID_INVALID = "主经销商不能再指定主经销商ID";
	private static final String DEALER_STATUS_INVALID = "经销商状态不合法";
	private static final String ISPLATFORM_CELL_INVALID = "平台公司标识不合法";
	private static final String ISPRIMARY_DEALER_INVALID = "主经销商标识不合法";
	private static final String RECORD_NOT_EXIST = "当前记录不存在";
	private static final String NOT_PRIMARY_DEALER = "输入的主经销商ID不是主经销商";

	@Autowired
	private DealerMigrationRepository dealerMigrationRepository;

	@Autowired
	private PositionViewRepository positionViewRepository;

	@Override
	public DealerMigrationResponse uploadDealer(String name, byte[] content) {
		List<DealerMigrationImportErrorResponse> responseList = new ArrayList<>();
		DealerMigrationResponse responseBody = new DealerMigrationResponse();
		List<DealerImportRequest> requestList = this.parseImportFile(name, content, responseList, responseBody);

		responseBody.setTotalCount(requestList.size());

		Map<String, Dealer> dealerMap = new HashMap<>();
		Map<String, Dealer> primaryDealerMap = new HashMap<>();
		Map<String, PositionView> positionMap = new HashMap<>();

		Set<String> dealerIdSet = new HashSet<>();
		Set<String> primaryDealerIdSet = new HashSet<>();
		Set<String> positionIdSet = new HashSet<>();

		int errorCount = this.validateDealerImportRecords(requestList, responseList, dealerIdSet, primaryDealerIdSet, positionIdSet);
		if(errorCount > 0) {
			responseBody.setErrorCount(responseList.size());
			responseBody.setDealerMigrationImportResponse(responseList);
			return responseBody;
		}

		List<Dealer> dealerList = dealerMigrationRepository.findByExternalIds(dealerIdSet);
		List<Dealer> primaryDealerList = null;
		if(!primaryDealerIdSet.isEmpty()){
			primaryDealerList = dealerMigrationRepository.findPrimaryDealerByExternalIds(primaryDealerIdSet);
		}
		List<PositionView> positionList = null;
		if(!positionIdSet.isEmpty()){
			positionList = positionViewRepository.findPositionViewByexternalIds(positionIdSet);
		}

		for (Dealer dealer : dealerList) {
			dealerMap.put(dealer.getExternalId(), dealer);
		}
		if(primaryDealerList != null) {
			for (Dealer primaryDealer : primaryDealerList) {
				primaryDealerMap.put(primaryDealer.getExternalId(), primaryDealer);
			}
		}
		if(positionList != null) {
			for (PositionView position : positionList) {
				positionMap.put(position.getExternalId(), position);
			}
		}

		errorCount = this.validateDBData(requestList, responseList, dealerMap, primaryDealerMap, positionMap);
		if (errorCount > 0) {
			responseBody.setErrorCount(responseList.size());
			responseBody.setDealerMigrationImportResponse(responseList);
			return responseBody;
		}
		
		errorCount = this.resolveDBOperation(requestList, responseList, dealerMap, primaryDealerMap, positionMap);
		if (errorCount > 0) {
			responseBody.setErrorCount(responseList.size());
			responseBody.setDealerMigrationImportResponse(responseList);
			return responseBody;
		}

		return responseBody;
	}

	private int resolveDBOperation(List<DealerImportRequest> requestList, List<DealerMigrationImportErrorResponse> responseList,
		Map<String, Dealer> dealerMap, Map<String, Dealer> primaryDealerMap, Map<String, PositionView> positionMap) {
		Iterator<DealerImportRequest> it = requestList.iterator();
		int errorCount = 0;
		int i = 3;
		while (it.hasNext()) {
			i++;
			boolean hasError = false;
			DealerImportRequest dealerRecord = it.next();
			
			if (dealerRecord.getOperationType().equalsIgnoreCase("C")) {
				hasError = resolveInsertOperation(i, dealerRecord, responseList, dealerMap, primaryDealerMap, positionMap);
			} else if (dealerRecord.getOperationType().equalsIgnoreCase("U")) {
				hasError = resolveUpdateOperation(i, dealerRecord, responseList, dealerMap, primaryDealerMap, positionMap);
			} else {
				hasError = resolveDeleteOperation(i, dealerRecord, responseList, dealerMap, primaryDealerMap);
			}

			if (hasError) {
				errorCount++;
			}
		}
		return errorCount;
	}

	private boolean resolveInsertOperation(int i, DealerImportRequest dealerRecord, List<DealerMigrationImportErrorResponse> responseList,
		Map<String, Dealer> dealerMap, Map<String, Dealer> primaryDealerMap, Map<String, PositionView> positionMap) {
		PositionView cityManager = positionMap.get(dealerRecord.getCityManagerPositionId());
		boolean isPlatformDealer = false;
		DealerStatus status = DealerStatus.ACTIVE;
		Dealer dealer = new Dealer();
		Dealer parentDealer = new Dealer();

		if (dealerRecord.getDealerStatus().equalsIgnoreCase("X")) {
			status = DealerStatus.INACTIVE;
		}

		if (dealerRecord.getIsPlatform().equalsIgnoreCase("X")) {
			isPlatformDealer = true;
		}

		if (dealerRecord.getIsPrimaryDealer().equalsIgnoreCase("X")) {
			dealer.setIsParent(true);
			parentDealer = null;
		}
		else {
			Dealer existPrimaryDealer = primaryDealerMap.get(dealerRecord.getPrimaryDealerExternalId());
			if(existPrimaryDealer == null) {
				DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
				errorResponse.setRowNumber(String.valueOf(i));
				errorResponse.setErrorInfo(NOT_PRIMARY_DEALER);
				errorResponse.setOperation(dealerRecord.getOperationType());
				errorResponse.setExternalId(dealerRecord.getDealerExternalId());
				errorResponse.setName(dealerRecord.getDealerName());
				errorResponse.setStatus(dealerRecord.getDealerStatus());
				errorResponse.setIsPlatformCompany(dealerRecord.getIsPlatform());
				errorResponse.setIsCentralDealer(dealerRecord.getIsPrimaryDealer());
				errorResponse.setIsCentralDealer(dealerRecord.getPrimaryDealerExternalId());
				errorResponse.setCitiManagerPosition(dealerRecord.getCityManagerPositionId());
				responseList.add(errorResponse);
				return true;
			}
			else {
				dealer.setIsParent(false);
				parentDealer = existPrimaryDealer;
			}
		}

		dealer.setExternalId(dealerRecord.getDealerExternalId());
		dealer.setName(dealerRecord.getDealerName());
		dealer.setStatus(status);
		dealer.setIsPlatformDealer(isPlatformDealer);
		dealer.setParent(parentDealer);
		dealer.setCityManager(cityManager);

		dealerMigrationRepository.saveAndFlush(dealer);
		dealerMap.put(dealer.getExternalId(), dealer);
		if(dealer.getIsParent()) {
			primaryDealerMap.put(dealer.getExternalId(), dealer);
		}
		
		return false;
	}

	private boolean resolveUpdateOperation(int i, DealerImportRequest dealerRecord, List<DealerMigrationImportErrorResponse> responseList,
		Map<String, Dealer> dealerMap, Map<String, Dealer> primaryDealerMap, Map<String, PositionView> positionMap) {
		PositionView cityManager = positionMap.get(dealerRecord.getCityManagerPositionId());
		boolean isPlatformDealer = false;
		DealerStatus status = DealerStatus.ACTIVE;
		Dealer dealer = dealerMap.get(dealerRecord.getDealerExternalId());
		Dealer parentDealer = null;

		if (dealerRecord.getDealerStatus().equalsIgnoreCase("X")) {
			status = DealerStatus.INACTIVE;
		}

		if (dealerRecord.getIsPlatform().equalsIgnoreCase("X")) {
			isPlatformDealer = true;
		}

		if (dealerRecord.getIsPrimaryDealer().equalsIgnoreCase("X")) {
			dealer.setIsParent(true);
			parentDealer = null;
		}
		else {
			Dealer existPrimaryDealer = primaryDealerMap.get(dealerRecord.getPrimaryDealerExternalId());
			if(existPrimaryDealer == null) {
				DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
				errorResponse.setRowNumber(String.valueOf(i));
				errorResponse.setErrorInfo(NOT_PRIMARY_DEALER);
				errorResponse.setOperation(dealerRecord.getOperationType());
				errorResponse.setExternalId(dealerRecord.getDealerExternalId());
				errorResponse.setName(dealerRecord.getDealerName());
				errorResponse.setStatus(dealerRecord.getDealerStatus());
				errorResponse.setIsPlatformCompany(dealerRecord.getIsPlatform());
				errorResponse.setIsCentralDealer(dealerRecord.getIsPrimaryDealer());
				errorResponse.setIsCentralDealer(dealerRecord.getPrimaryDealerExternalId());
				errorResponse.setCitiManagerPosition(dealerRecord.getCityManagerPositionId());
				responseList.add(errorResponse);
				return true;
			}
			else {
				dealer.setIsParent(false);
				parentDealer = existPrimaryDealer;
			}
		}

		dealer.setExternalId(dealerRecord.getDealerExternalId());
		dealer.setName(dealerRecord.getDealerName());
		dealer.setStatus(status);
		dealer.setIsPlatformDealer(isPlatformDealer);
		dealer.setParent(parentDealer);
		dealer.setCityManager(cityManager);

		dealerMigrationRepository.saveAndFlush(dealer);
		if(dealer.getIsParent()) {
			primaryDealerMap.put(dealer.getExternalId(), dealer);
		}
		
		return false;
	}

	private boolean resolveDeleteOperation(int i, DealerImportRequest dealerRecord, List<DealerMigrationImportErrorResponse> responseList,
		Map<String, Dealer> dealerMap, Map<String, Dealer> primaryDealerMap) {
		Dealer dealer = dealerMap.get(dealerRecord.getDealerExternalId());
		if (dealer == null) {
			DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
			errorResponse.setRowNumber(String.valueOf(i));
			errorResponse.setErrorInfo(RECORD_NOT_EXIST);
			errorResponse.setOperation(dealerRecord.getOperationType());
			errorResponse.setExternalId(dealerRecord.getDealerExternalId());
			errorResponse.setName(dealerRecord.getDealerName());
			errorResponse.setStatus(dealerRecord.getDealerStatus());
			errorResponse.setIsPlatformCompany(dealerRecord.getIsPlatform());
			errorResponse.setIsCentralDealer(dealerRecord.getIsPrimaryDealer());
			errorResponse.setIsCentralDealer(dealerRecord.getPrimaryDealerExternalId());
			errorResponse.setCitiManagerPosition(dealerRecord.getCityManagerPositionId());
			responseList.add(errorResponse);
			return true;
		}
		dealerMigrationRepository.delete(dealer);
		dealerMap.remove(dealer.getExternalId(), dealer);
		if(dealer.getIsParent()) {
			primaryDealerMap.remove(dealer.getExternalId(), dealer);
		}
		return false;
	}

	private int validateDBData(List<DealerImportRequest> requestList, List<DealerMigrationImportErrorResponse> responseList,
		Map<String, Dealer> dealerMap, Map<String, Dealer> primaryDealerMap, Map<String, PositionView> positionMap) {
		Iterator<DealerImportRequest> it = requestList.iterator();
		int errorCount = 0;
		int i = 3;
		while (it.hasNext()) {
			i++;
			boolean hasError = false;
			DealerImportRequest dealerRecord = it.next();
			
			if (dealerRecord.getOperationType().equalsIgnoreCase("C")) {
				hasError = prepareInsertOperation(i, dealerRecord, responseList, dealerMap, primaryDealerMap, positionMap);
			} else if (dealerRecord.getOperationType().equalsIgnoreCase("U")) {
				hasError = prepareUpdateOperation(i, dealerRecord, responseList, dealerMap, primaryDealerMap, positionMap);
			} else {
				hasError = prepareDeleteOperation(i, dealerRecord, responseList, dealerMap);
			}

			if (hasError) {
				errorCount++;
			}
		}
		return errorCount;
	}

	private boolean prepareInsertOperation(int i, DealerImportRequest dealerRecord, List<DealerMigrationImportErrorResponse> responseList,
		Map<String, Dealer> dealerMap, Map<String, Dealer> primaryDealerMap, Map<String, PositionView> positionMap) {
		boolean errorFound = false;
		if (dealerMap.containsKey(dealerRecord.getDealerExternalId())) {
			DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
			errorResponse.setRowNumber(String.valueOf(i));
			errorResponse.setErrorInfo(DEALER_ID_EXISTED);
			errorResponse.setOperation(dealerRecord.getOperationType());
			errorResponse.setExternalId(dealerRecord.getDealerExternalId());
			errorResponse.setName(dealerRecord.getDealerName());
			errorResponse.setStatus(dealerRecord.getDealerStatus());
			errorResponse.setIsPlatformCompany(dealerRecord.getIsPlatform());
			errorResponse.setIsCentralDealer(dealerRecord.getIsPrimaryDealer());
			errorResponse.setIsCentralDealer(dealerRecord.getPrimaryDealerExternalId());
			errorResponse.setCitiManagerPosition(dealerRecord.getCityManagerPositionId());
			responseList.add(errorResponse);
			errorFound = true;
		} 
		
		if (!StringUtils.isBlank(dealerRecord.getCityManagerPositionId()) && !positionMap.containsKey(dealerRecord.getCityManagerPositionId())) {
			DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
			errorResponse.setRowNumber(String.valueOf(i));
			errorResponse.setErrorInfo(POSITION_ID_NOT_EXIST);
			errorResponse.setOperation(dealerRecord.getOperationType());
			errorResponse.setExternalId(dealerRecord.getDealerExternalId());
			errorResponse.setName(dealerRecord.getDealerName());
			errorResponse.setStatus(dealerRecord.getDealerStatus());
			errorResponse.setIsPlatformCompany(dealerRecord.getIsPlatform());
			errorResponse.setIsCentralDealer(dealerRecord.getIsPrimaryDealer());
			errorResponse.setIsCentralDealer(dealerRecord.getPrimaryDealerExternalId());
			errorResponse.setCitiManagerPosition(dealerRecord.getCityManagerPositionId());
			responseList.add(errorResponse);
			errorFound = true;
		}
		
		return errorFound;
	}

	private boolean prepareUpdateOperation(int i, DealerImportRequest dealerRecord, List<DealerMigrationImportErrorResponse> responseList,
		Map<String, Dealer> dealerMap, Map<String, Dealer> primaryDealerMap, Map<String, PositionView> positionMap) {
		boolean errorFound = false;
		if (!dealerMap.containsKey(dealerRecord.getDealerExternalId())) {
			DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
			errorResponse.setRowNumber(String.valueOf(i));
			errorResponse.setErrorInfo(DEALER_ID_NOT_EXIST);
			errorResponse.setOperation(dealerRecord.getOperationType());
			errorResponse.setExternalId(dealerRecord.getDealerExternalId());
			errorResponse.setName(dealerRecord.getDealerName());
			errorResponse.setStatus(dealerRecord.getDealerStatus());
			errorResponse.setIsPlatformCompany(dealerRecord.getIsPlatform());
			errorResponse.setIsCentralDealer(dealerRecord.getIsPrimaryDealer());
			errorResponse.setIsCentralDealer(dealerRecord.getPrimaryDealerExternalId());
			errorResponse.setCitiManagerPosition(dealerRecord.getCityManagerPositionId());
			responseList.add(errorResponse);
			errorFound = true;
		} 
		
		if (!StringUtils.isBlank(dealerRecord.getCityManagerPositionId()) && !positionMap.containsKey(dealerRecord.getCityManagerPositionId())) {
			DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
			errorResponse.setRowNumber(String.valueOf(i));
			errorResponse.setErrorInfo(POSITION_ID_NOT_EXIST);
			errorResponse.setOperation(dealerRecord.getOperationType());
			errorResponse.setExternalId(dealerRecord.getDealerExternalId());
			errorResponse.setName(dealerRecord.getDealerName());
			errorResponse.setStatus(dealerRecord.getDealerStatus());
			errorResponse.setIsPlatformCompany(dealerRecord.getIsPlatform());
			errorResponse.setIsCentralDealer(dealerRecord.getIsPrimaryDealer());
			errorResponse.setIsCentralDealer(dealerRecord.getPrimaryDealerExternalId());
			errorResponse.setCitiManagerPosition(dealerRecord.getCityManagerPositionId());
			responseList.add(errorResponse);
			errorFound = true;
		}
		
		return errorFound;
	}

	private boolean prepareDeleteOperation(int i, DealerImportRequest dealerRecord, List<DealerMigrationImportErrorResponse> responseList,
		Map<String, Dealer> dealerMap) {
		boolean errorFound = false;
		if (!dealerMap.containsKey(dealerRecord.getDealerExternalId())) {
			DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
			errorResponse.setRowNumber(String.valueOf(i));
			errorResponse.setErrorInfo(DEALER_ID_NOT_EXIST);
			errorResponse.setOperation(dealerRecord.getOperationType());
			errorResponse.setExternalId(dealerRecord.getDealerExternalId());
			errorResponse.setName(dealerRecord.getDealerName());
			errorResponse.setStatus(dealerRecord.getDealerStatus());
			errorResponse.setIsPlatformCompany(dealerRecord.getIsPlatform());
			errorResponse.setIsCentralDealer(dealerRecord.getIsPrimaryDealer());
			errorResponse.setIsCentralDealer(dealerRecord.getPrimaryDealerExternalId());
			errorResponse.setCitiManagerPosition(dealerRecord.getCityManagerPositionId());
			responseList.add(errorResponse);
			errorFound = true;
		}
		
		return errorFound;
	}

	private int validateDealerImportRecords(List<DealerImportRequest> requestList, List<DealerMigrationImportErrorResponse> responseList,
		Set<String> dealerIdSet, Set<String> primaryDealerIdSet, Set<String> positionIdSet) {
		int i = 3;
		int errorCount = 0;
		Iterator<DealerImportRequest> it = requestList.iterator();
		while (it.hasNext()) {
			i++;
			DealerImportRequest dealerRecord = it.next();
			boolean isRemoved = false;		
			String operationType = dealerRecord.getOperationType();
			boolean CFlag = OPERATION_CREATE_VALUE.equals(operationType.toUpperCase());
			boolean UFlag = OPERATION_UPDATE_VALUE.equals(operationType.toUpperCase());
			boolean DFlag = OPERATION_DELETE_VALUE.equals(operationType.toUpperCase());
			
			// 检测操作符
			if (StringUtils.isBlank(operationType)) {
				DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
				errorResponse.setRowNumber(String.valueOf(i));
				errorResponse.setErrorInfo(OPERATION_CELL_EMPTY);
				errorResponse.setOperation(operationType);
				errorResponse.setExternalId(dealerRecord.getDealerExternalId());
				errorResponse.setName(dealerRecord.getDealerName());
				errorResponse.setStatus(dealerRecord.getDealerStatus());
				errorResponse.setIsPlatformCompany(dealerRecord.getIsPlatform());
				errorResponse.setIsCentralDealer(dealerRecord.getIsPrimaryDealer());
				errorResponse.setIsCentralDealer(dealerRecord.getPrimaryDealerExternalId());
				errorResponse.setCitiManagerPosition(dealerRecord.getCityManagerPositionId());
				responseList.add(errorResponse);
				it.remove();
				isRemoved = true;
				continue;
			}
			else if (!(CFlag || UFlag || DFlag)) {
				DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
				errorResponse.setRowNumber(String.valueOf(i));
				errorResponse.setErrorInfo(OPERATION_CELL_ILLEGAL);
				errorResponse.setOperation(operationType);
				errorResponse.setExternalId(dealerRecord.getDealerExternalId());
				errorResponse.setName(dealerRecord.getDealerName());
				errorResponse.setStatus(dealerRecord.getDealerStatus());
				errorResponse.setIsPlatformCompany(dealerRecord.getIsPlatform());
				errorResponse.setIsCentralDealer(dealerRecord.getIsPrimaryDealer());
				errorResponse.setIsCentralDealer(dealerRecord.getPrimaryDealerExternalId());
				errorResponse.setCitiManagerPosition(dealerRecord.getCityManagerPositionId());
				responseList.add(errorResponse);
				it.remove();
				isRemoved = true;
				continue;
			}
			else {
				if (CFlag || DFlag) {
					if (!dealerIdSet.contains(dealerRecord.getDealerExternalId())) {
						dealerIdSet.add(dealerRecord.getDealerExternalId());
					}
					else {
						DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
						errorResponse.setRowNumber(String.valueOf(i));
						if(CFlag){
							errorResponse.setErrorInfo(DUPLICATE_CREATE_DEALER_ID);
						} else {
							errorResponse.setErrorInfo(DUPLICATE_DELETE_DEALER_ID);
						}
						errorResponse.setOperation(operationType);
						errorResponse.setExternalId(dealerRecord.getDealerExternalId());
						errorResponse.setName(dealerRecord.getDealerName());
						errorResponse.setStatus(dealerRecord.getDealerStatus());
						errorResponse.setIsPlatformCompany(dealerRecord.getIsPlatform());
						errorResponse.setIsCentralDealer(dealerRecord.getIsPrimaryDealer());
						errorResponse.setIsCentralDealer(dealerRecord.getPrimaryDealerExternalId());
						errorResponse.setCitiManagerPosition(dealerRecord.getCityManagerPositionId());
						responseList.add(errorResponse);
						it.remove();
						isRemoved = true;
						continue;
					}

				}
			}

			// 检查经销商ID是否为空
			if (StringUtils.isBlank(dealerRecord.getDealerExternalId())) {
				DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
				errorResponse.setRowNumber(String.valueOf(i));
				errorResponse.setErrorInfo(DEALER_ID_CELL_EMPTY);
				errorResponse.setOperation(dealerRecord.getOperationType());
				errorResponse.setExternalId(dealerRecord.getDealerExternalId());
				errorResponse.setName(dealerRecord.getDealerName());
				errorResponse.setStatus(dealerRecord.getDealerStatus());
				errorResponse.setIsPlatformCompany(dealerRecord.getIsPlatform());
				errorResponse.setIsCentralDealer(dealerRecord.getIsPrimaryDealer());
				errorResponse.setIsCentralDealer(dealerRecord.getPrimaryDealerExternalId());
				errorResponse.setCitiManagerPosition(dealerRecord.getCityManagerPositionId());
				responseList.add(errorResponse);
				isRemoved = true;
			}
			// 检查经销商ID是否包含非法字符
			else {
				if (!dealerRecord.getDealerExternalId().matches("[a-zA-Z0-9_\u4e00-\u9fa5]*")) {
					DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
					errorResponse.setRowNumber(String.valueOf(i));
					errorResponse.setErrorInfo(DEALER_ID_CELL_ILLEGAL);
					errorResponse.setOperation(dealerRecord.getOperationType());
					errorResponse.setExternalId(dealerRecord.getDealerExternalId());
					errorResponse.setName(dealerRecord.getDealerName());
					errorResponse.setStatus(dealerRecord.getDealerStatus());
					errorResponse.setIsPlatformCompany(dealerRecord.getIsPlatform());
					errorResponse.setIsCentralDealer(dealerRecord.getIsPrimaryDealer());
					errorResponse.setIsCentralDealer(dealerRecord.getPrimaryDealerExternalId());
					errorResponse.setCitiManagerPosition(dealerRecord.getCityManagerPositionId());
					responseList.add(errorResponse);
					isRemoved = true;
				}
				else {
					dealerIdSet.add(dealerRecord.getDealerExternalId());
				}
			}
			
			if(!dealerRecord.getOperationType().equalsIgnoreCase("D")) {
				// 经销商名称是否为空
				if (StringUtils.isBlank(dealerRecord.getDealerName())) {
					DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
					errorResponse.setRowNumber(String.valueOf(i));
					errorResponse.setErrorInfo(DEALER_NAME_CELL_EMPTY);
					errorResponse.setOperation(dealerRecord.getOperationType());
					errorResponse.setExternalId(dealerRecord.getDealerExternalId());
					errorResponse.setName(dealerRecord.getDealerName());
					errorResponse.setStatus(dealerRecord.getDealerStatus());
					errorResponse.setIsPlatformCompany(dealerRecord.getIsPlatform());
					errorResponse.setIsCentralDealer(dealerRecord.getIsPrimaryDealer());
					errorResponse.setIsCentralDealer(dealerRecord.getPrimaryDealerExternalId());
					errorResponse.setCitiManagerPosition(dealerRecord.getCityManagerPositionId());
					responseList.add(errorResponse);
					isRemoved = true;
				}
				// 经销商名称是否包含非法字符
				else {
					if (!dealerRecord.getDealerName().matches("[a-zA-Z0-9_\u4e00-\u9fa5]*")) {
						DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
						errorResponse.setRowNumber(String.valueOf(i));
						errorResponse.setErrorInfo(DEALER_NAME_CELL_ILLEGAL);
						errorResponse.setOperation(dealerRecord.getOperationType());
						errorResponse.setExternalId(dealerRecord.getDealerExternalId());
						errorResponse.setName(dealerRecord.getDealerName());
						errorResponse.setStatus(dealerRecord.getDealerStatus());
						errorResponse.setIsPlatformCompany(dealerRecord.getIsPlatform());
						errorResponse.setIsCentralDealer(dealerRecord.getIsPrimaryDealer());
						errorResponse.setIsCentralDealer(dealerRecord.getPrimaryDealerExternalId());
						errorResponse.setCitiManagerPosition(dealerRecord.getCityManagerPositionId());
						responseList.add(errorResponse);
						isRemoved = true;
					}
				}

				// 检查经销商状态是否为空或者X
				if (!StringUtils.isBlank(dealerRecord.getDealerStatus()) && !dealerRecord.getDealerStatus().equalsIgnoreCase("X")) {
					DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
					errorResponse.setRowNumber(String.valueOf(i));
					errorResponse.setErrorInfo(DEALER_STATUS_INVALID);
					errorResponse.setOperation(dealerRecord.getOperationType());
					errorResponse.setExternalId(dealerRecord.getDealerExternalId());
					errorResponse.setName(dealerRecord.getDealerName());
					errorResponse.setStatus(dealerRecord.getDealerStatus());
					errorResponse.setIsPlatformCompany(dealerRecord.getIsPlatform());
					errorResponse.setIsCentralDealer(dealerRecord.getIsPrimaryDealer());
					errorResponse.setIsCentralDealer(dealerRecord.getPrimaryDealerExternalId());
					errorResponse.setCitiManagerPosition(dealerRecord.getCityManagerPositionId());
					responseList.add(errorResponse);
					isRemoved = true;
				}

				// 检查平台公司是否为空或者X
				if (!StringUtils.isBlank(dealerRecord.getIsPlatform()) && !dealerRecord.getIsPlatform().equalsIgnoreCase("X")) {
					DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
					errorResponse.setRowNumber(String.valueOf(i));
					errorResponse.setErrorInfo(ISPLATFORM_CELL_INVALID);
					errorResponse.setOperation(dealerRecord.getOperationType());
					errorResponse.setExternalId(dealerRecord.getDealerExternalId());
					errorResponse.setName(dealerRecord.getDealerName());
					errorResponse.setStatus(dealerRecord.getDealerStatus());
					errorResponse.setIsPlatformCompany(dealerRecord.getIsPlatform());
					errorResponse.setIsCentralDealer(dealerRecord.getIsPrimaryDealer());
					errorResponse.setIsCentralDealer(dealerRecord.getPrimaryDealerExternalId());
					errorResponse.setCitiManagerPosition(dealerRecord.getCityManagerPositionId());
					responseList.add(errorResponse);
					isRemoved = true;
				}

				// 检查主经销商标识是否为空或者'X'：如果为空，则主经销商ID必须有值；如果为‘X’，则主经销商ID必须为空
				// 不为空且不为'X'
				if (!StringUtils.isBlank(dealerRecord.getIsPrimaryDealer()) && !dealerRecord.getIsPrimaryDealer().equalsIgnoreCase("X")) {
					DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
					errorResponse.setRowNumber(String.valueOf(i));
					errorResponse.setErrorInfo(ISPRIMARY_DEALER_INVALID);
					errorResponse.setOperation(dealerRecord.getOperationType());
					errorResponse.setExternalId(dealerRecord.getDealerExternalId());
					errorResponse.setName(dealerRecord.getDealerName());
					errorResponse.setStatus(dealerRecord.getDealerStatus());
					errorResponse.setIsPlatformCompany(dealerRecord.getIsPlatform());
					errorResponse.setIsCentralDealer(dealerRecord.getIsPrimaryDealer());
					errorResponse.setIsCentralDealer(dealerRecord.getPrimaryDealerExternalId());
					errorResponse.setCitiManagerPosition(dealerRecord.getCityManagerPositionId());
					responseList.add(errorResponse);
					isRemoved = true;
				}
				// 为空但没有指定主经销商ID
				else if (StringUtils.isBlank(dealerRecord.getIsPrimaryDealer()) && StringUtils.isBlank(dealerRecord.getPrimaryDealerExternalId())) {
					DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
					errorResponse.setRowNumber(String.valueOf(i));
					errorResponse.setErrorInfo(PRIMARY_DEALER_ID_EMPTY);
					errorResponse.setOperation(dealerRecord.getOperationType());
					errorResponse.setExternalId(dealerRecord.getDealerExternalId());
					errorResponse.setName(dealerRecord.getDealerName());
					errorResponse.setStatus(dealerRecord.getDealerStatus());
					errorResponse.setIsPlatformCompany(dealerRecord.getIsPlatform());
					errorResponse.setIsCentralDealer(dealerRecord.getIsPrimaryDealer());
					errorResponse.setIsCentralDealer(dealerRecord.getPrimaryDealerExternalId());
					errorResponse.setCitiManagerPosition(dealerRecord.getCityManagerPositionId());
					responseList.add(errorResponse);
					isRemoved = true;
				}
				// 不为空且指定了主经销商ID
				else if (!StringUtils.isBlank(dealerRecord.getIsPrimaryDealer()) && !StringUtils.isBlank(dealerRecord.getPrimaryDealerExternalId())) {
					DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
					errorResponse.setRowNumber(String.valueOf(i));
					errorResponse.setErrorInfo(PRIMARY_DEALER_ID_INVALID);
					errorResponse.setOperation(dealerRecord.getOperationType());
					errorResponse.setExternalId(dealerRecord.getDealerExternalId());
					errorResponse.setName(dealerRecord.getDealerName());
					errorResponse.setStatus(dealerRecord.getDealerStatus());
					errorResponse.setIsPlatformCompany(dealerRecord.getIsPlatform());
					errorResponse.setIsCentralDealer(dealerRecord.getIsPrimaryDealer());
					errorResponse.setIsCentralDealer(dealerRecord.getPrimaryDealerExternalId());
					errorResponse.setCitiManagerPosition(dealerRecord.getCityManagerPositionId());
					responseList.add(errorResponse);
					isRemoved = true;
				}
				else if(StringUtils.isBlank(dealerRecord.getIsPrimaryDealer()) && !StringUtils.isBlank(dealerRecord.getPrimaryDealerExternalId())) {
					primaryDealerIdSet.add(dealerRecord.getPrimaryDealerExternalId());
				}

				// 如果岗位编号不为空
				if (!StringUtils.isBlank(dealerRecord.getCityManagerPositionId())) {
					positionIdSet.add(dealerRecord.getCityManagerPositionId());
				}
			}

			if (isRemoved == true) {
				it.remove();
				errorCount++;
			}
		}
		return errorCount;
	}

	private List<DealerImportRequest> parseImportFile(String name, byte[] content, List<DealerMigrationImportErrorResponse> responseList,
		DealerMigrationResponse responseBody) {
		ByteArrayInputStream is = new ByteArrayInputStream(content);
		// 创建工作页
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(is);
		}
		catch (IOException e) {
			// 异常的声明
			throw new RuntimeException(REQUIRED_IMPORT_FILE_FORMAT, e);
		}
		// 读取第一个Sheet
		XSSFSheet sheet = workbook.getSheetAt(0);
		int theLastRowNumber = sheet.getLastRowNum();

		// 检查是否为空
		checkFileEmpty(theLastRowNumber, responseList, responseBody);
		if(responseList.size() > 0) {
			responseBody.setErrorCount(responseList.size());
			responseBody.setDealerMigrationImportResponse(responseList);
		}

		// 检查记录是否超过1000条
		checkFileBeyondSize(theLastRowNumber, responseList, responseBody);
		if(responseList.size() > 0) {
			responseBody.setErrorCount(responseList.size());
			responseBody.setDealerMigrationImportResponse(responseList);
		}

		List<DealerImportRequest> dealerRecords = new LinkedList<DealerImportRequest>();

		for (int rowIndex = FIRST_ROW_NUMBER; rowIndex <= theLastRowNumber; rowIndex++) {
			XSSFRow row = sheet.getRow(rowIndex);
			// 如果row为空，那么继续不认为是错误
			if (row == null) {
				continue;
			}
			DealerImportRequest dealerRecord = new DealerImportRequest();
			String operationCellValue = this.setCellValue(row.getCell(DEALER_OPERATION_CELL_INDEX));
			String dealerIdCellValue = this.setCellValue(row.getCell(DEALER_ID_CELL_INDEX));
			String dealerNameCellValue = this.setCellValue(row.getCell(DEALER_NAME_CELL_INDEX));
			String dealerStatusCellValue = this.setCellValue(row.getCell(DEALER_STATUS_CELL_INDEX));
			String isPlatformCompanyCellValue = this.setCellValue(row.getCell(PLATFORM_COMPANY_CELL_INDEX));
			String isPrimaryDealerCellValue = this.setCellValue(row.getCell(IS_CENTRAL_DEALER_ID));
			String primaryDealerIdCellValue = this.setCellValue(row.getCell(CENTRAL_DEALER_ID));
			String cityManagerPositionIdCellValue = this.setCellValue(row.getCell(CITY_MANAGER_POSITION));

			dealerRecord.setOperationType(operationCellValue);
			dealerRecord.setDealerExternalId(dealerIdCellValue);
			dealerRecord.setDealerName(dealerNameCellValue);
			dealerRecord.setDealerStatus(dealerStatusCellValue);
			dealerRecord.setIsPlatform(isPlatformCompanyCellValue);
			dealerRecord.setIsPrimaryDealer(isPrimaryDealerCellValue);
			dealerRecord.setPrimaryDealerExternalId(primaryDealerIdCellValue);
			dealerRecord.setCityManagerPositionId(cityManagerPositionIdCellValue);

			dealerRecords.add(dealerRecord);
		}

		return dealerRecords;
	}

	private DealerMigrationResponse checkFileBeyondSize(int theLastRowNumber, List<DealerMigrationImportErrorResponse> responseList,
		DealerMigrationResponse responseBody) {
		// 上传文件记录数
		int totalImportItems = theLastRowNumber - REQUIRED_LEAST_RECORD_COUNT;
		if (totalImportItems > LIMIT_RECORD_COUNT) {
			DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
			errorResponse.setRowNumber(String.valueOf(theLastRowNumber));
			errorResponse.setErrorInfo(RECORD_COUNT_BEYOND_LIMIT);
			responseList.add(errorResponse);
			responseBody.setErrorCount(1);
			responseBody.setTotalCount(0);
			responseBody.setDealerMigrationImportResponse(responseList);
		}
		return responseBody;
	}

	private DealerMigrationResponse checkFileEmpty(int theLastRowNumber, List<DealerMigrationImportErrorResponse> responseList,
		DealerMigrationResponse responseBody) {
		if (theLastRowNumber <= REQUIRED_LEAST_RECORD_COUNT) {
			DealerMigrationImportErrorResponse errorResponse = new DealerMigrationImportErrorResponse();
			errorResponse.setRowNumber("0");
			errorResponse.setErrorInfo(NO_RECORD_IN_UPLOAD_FILE);
			responseList.add(errorResponse);
			responseBody.setErrorCount(1);
			responseBody.setTotalCount(0);
			responseBody.setDealerMigrationImportResponse(responseList);
		}
		return responseBody;
	}

	public String setCellValue(XSSFCell cell) {
		if (cell == null) {
			return "";
		}
		else {
			cell.setCellType(Cell.CELL_TYPE_STRING);
			return cell.getStringCellValue().trim();
		}
	}
}
