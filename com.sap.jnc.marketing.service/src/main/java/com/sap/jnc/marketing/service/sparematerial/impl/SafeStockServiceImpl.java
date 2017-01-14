package com.sap.jnc.marketing.service.sparematerial.impl;

/**
 * @author Marco, Huang I323691
 */

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.SpareMaterialSafetyStock;
import com.sap.jnc.marketing.persistence.repository.SpareMaterialSafetyStockRepository;
import com.sap.jnc.marketing.service.sparematerial.SafeStockService;

@Service
@Transactional
public class SafeStockServiceImpl implements SafeStockService {
	
	private static final String BLANK_COLUMN_VALUE = "有列值为空";
	private static final String REPEAT_RECORD = "有重复数据";
	private static final String ILEGAL_OPERATION = "操作不合法";
	private static final String ILEGAL_SAFETY_STOCK_QUANTITY = "安全库存列包含非法字符,只应当包含纯数字，不包含小数点";
	private static final String POSITION_ID = "岗位编号";
	private static final String POSITION_NAME = "岗位名称";
	private static final String EMPLOYEE_NAME = "员工姓名";
	private static final String MATERIAL_ID = "物料编号";
	private static final String MATERIAL_NAME = "物料名称";
	private static final String SAFESTOCK_QUANTITY = "安全库存";
	private static final String INTRANSIT_SAFESTOCK_QUANTITY = "在途库存";
	private static final String CURRENT_SAFESTOCK_QUANTITY = "当前库存";
	private static final String REQUIRED_SAFESTOCK_QUANTITY = "需补库存";
	private static final String TOTAL_DELIVERED_QUANTITY = "累计发放数";
	private static final String TOTAL_PAID_QUANTITY = "累计兑付数";
	private static final String TOTAL_VERIFIED_QUANTITY = "累计核销数";
	private static final String TOTAL_DIFFER_QUANTITY = "累计差异数";
	private static final String SAFESTOCK_SHEET_NAME = "安全库存报表";
	private static final String SAFESTOCK_ERROR_EXPORT_SHEET_NAME = "安全库存出错信息报表";
	private static final String ROW_NUMBER = "行号";
	private static final String OPERATION = "操作";
	private static final String ERROR_INFORMATION = "错误信息";
	
	private static final int EMPTY_CSV_RECORD_COUNT = 1;
	private static final int CSV_RECORD_LIMIT_COUNT = 1000;
	private static final String EMPTY_CSV_RECORD_COUNT_INFO = "文件为空";
	private static final String CSV_RECORD_LIMIT_COUNT_INFO = "上传文件长度超过允许最大值1000条";
	private static final String POSITION_ID_BLANK = "Position ID为空，请检查主表数据!";
	
	/*
	 * 用于第一层校验 判断是否有重复记录
	 */
	public Map<String, String> recordMap = new HashMap<>();

	// 响应页面的存储信息
	public List<String> responseMsg = new ArrayList<>();

	// 错误特征符，用来判断库存报表是否有错误
	private boolean errorFound = false;

	// 导入总条目数
	private int totalImportItems;
	/*
	 * 用于导出功能 每次调用第二层业务逻辑校验this.businessCheckForSafeStock后 如果有报错信息，就将responseMsg塞入到该Map中 导出错误请求发出时，直接将错误信息从Map中取出
	 */
	public Map<String, List<String>> errorMsgForExport = new HashMap<>();

	/**
	 * 记录所有需要的信息 用于后面批量数据导入
	 **/
	public List<String> operationList = null;
	public List<String> positionIdList = null;
	public List<String> materialIdList = null;
	public List<String> safeStockQuList = null;

	@Autowired
	private SpareMaterialSafetyStockRepository spareMaterialSafetyStockRepository;

	/**
	 * 查询安全库存功能
	 **/
	@Override
	public String querySpareMaterial(String positionId, String materialId) {
		// TODO Auto-generated method stub
		List<SpareMaterialSafetyStock> safetyStockList = new ArrayList<SpareMaterialSafetyStock>();
		// 获取safetyStockList
		safetyStockList = this.spareMaterialSafetyStockRepository.querySpareMaterials(positionId, materialId);
		return this.createSafetyStockJSON(safetyStockList);
	}

	/**
	 * 将safetyStockList变为json格式字符串 {"positionId":"xxxx",.....}
	 **/
	private String createSafetyStockJSON(List<SpareMaterialSafetyStock> safetyStockList) {
		// TODO Auto-generated method stub
		final StringBuffer sb = new StringBuffer();
		/***
		 * 计算不同种类库存数量 组装页面展示JSON格式数据
		 **/
		sb.append("[");
		for (int stockIndex = 0; stockIndex < safetyStockList.size(); stockIndex++) {
			// TODO robst
			// 岗位ID
			SpareMaterialSafetyStock safeStock = safetyStockList.get(stockIndex);
			PositionView pv = safeStock.getPosition();
			String positionId;
			try{
				positionId = String.valueOf(pv.getId());
			}catch(NullPointerException e){
				positionId = "";
				throw new RuntimeException(POSITION_ID_BLANK, e);
			}
			
			// 岗位名称
			final String positionName = pv.getName();
			// 员工姓名
			final String employeeName = pv.getEmployees().get(0).getName();
			// 物料 编号
			final String materialId = safeStock.getProduct().getId().toString();
			// 物料名称
			final String materialName = safeStock.getProduct().getName();
			// 安全库存
			final BigDecimal safetyStockQuantity = safeStock.getSafetyStockQuantity().getValue();
			// 在途库存
			final BigDecimal inTransitStockQuantity = safeStock.getInTransitStockQuantity().getValue();
			// 累计发放数
			final BigDecimal totalDeliveredQuantity = safeStock.getTotalDeliveredQuantity().getValue();
			// 累计核销差异数
			final BigDecimal totalDifferenceQuantity = safeStock.getTotalDifferenceQuantity().getValue();
			// 累计兑付数
			final BigDecimal totalPaidQuantity = safeStock.getTotalPaidQuantity().getValue();
			// 累计核销数
			final BigDecimal totalVerifiedQuantity = safeStock.getTotalVerifiedQuantity().getValue();
			// TODO计算当前库存 = 累计发放数+累计核销差异数 - 在途库存
			final BigDecimal currentStockQuantity = totalDeliveredQuantity.add(totalDifferenceQuantity).subtract(inTransitStockQuantity);
			// TODO计算需补库存=安全库存-在途库存-当前库存
			final BigDecimal requiredStockQuantity = safetyStockQuantity.subtract(inTransitStockQuantity).subtract(currentStockQuantity);

			sb.append("{\"positionId\":\"" + positionId + "\",");
			sb.append("\"positionName\":\"" + positionName + "\",");
			sb.append("\"employeeName\":\"" + employeeName + "\",");
			sb.append("\"materialId\":\"" + materialId + "\",");
			sb.append("\"materialName\":\"" + materialName + "\",");
			// 安全库存
			sb.append("\"safetyStockQuantity\":\"" + String.valueOf(safetyStockQuantity.intValue()) + "\",");
			// 在途库存
			sb.append("\"inTransitStockQuantity\":\"" + String.valueOf(inTransitStockQuantity.intValue()) + "\",");
			// 计算当前库存
			sb.append("\"currentStockQuantity\":\"" + String.valueOf(currentStockQuantity.intValue()) + "\",");
			// 计算需补库存
			sb.append("\"requiredStockQuantity\":\"" + String.valueOf(requiredStockQuantity.intValue()) + "\",");
			// 累计发放数
			sb.append("\"totalDeliveredQuantity\":\"" + String.valueOf(totalDeliveredQuantity.intValue()) + "\",");
			// 累计兑付数
			sb.append("\"totalPaidQuantity\":\"" + String.valueOf(totalPaidQuantity.intValue()) + "\",");
			// 累计核销数
			sb.append("\"totalVerifiedQuantity\":\"" + String.valueOf(totalVerifiedQuantity.intValue()) + "\",");
			// 累计核销差异数
			sb.append("\"totalDifferenceQuantity\":\"" + String.valueOf(totalDifferenceQuantity.intValue()) + "\"},");
		}

		// 去除逗号并返回
		return sb.toString().substring(0, sb.toString().length() - 1) + "]";
	}

	/**
	 * 上传安全库存报表入口
	 * a. 第一部分数据校验: checkSafeStockCSV() 
	 *  1> CSV文件中是否有列值为空 
	 *  2> CSV文件中是否出现重复的C操作，即重复的物料编号，岗位编号，插入操作
	 * b. 第二部分实际业务操作: businessCheckForSafeStock() 
	 *  1> 在持久化中查询岗位编号，物料编号是否为空 
	 *  2> 全部数据通过校验，则执行真实数据库操作
	 *
	 * @throws IOException
	 **/
	@Override
	public String uploadSafeStock(String file, byte[] content) throws IOException {
		/*
		 * 读取CSV内容 成功:==>返回组装实体对象所需数据 失败:==>返回错误信息
		 */
		this.errorFound = false;
		this.operationList = new ArrayList<>();
		this.positionIdList = new ArrayList<>();
		this.materialIdList = new ArrayList<>();
		this.safeStockQuList = new ArrayList<>();
		this.recordMap = new HashMap<>();
		this.responseMsg = new ArrayList<>();
		this.errorMsgForExport = new HashMap<>();
		this.totalImportItems = 0;

		// 读取CSV内容
		final String safeStockCSVContent = new String(content);
		// 取得所有CSV记录
		final String[] csvRecords = safeStockCSVContent.split("\n");
		this.totalImportItems = csvRecords.length - 1;
		
		/*
		 * 判断是否为空，判断是否过大
		 */
		this.checkCSVEmpty(totalImportItems);
		if (this.errorFound) {
			this.errorMsgForExport.put("error", this.responseMsg);
			return this.createTotalResponse(this.responseMsg, this.errorFound);
		}
		this.checkCSVBeyondSize(totalImportItems);
		if (this.errorFound) {
			this.errorMsgForExport.put("error", this.responseMsg);
			return this.createTotalResponse(this.responseMsg, this.errorFound);
		}
		// 开启第一段前端校验
		this.checkSafeStockCSV(csvRecords);
		
		if (this.errorFound) {
			this.errorMsgForExport.put("error", this.responseMsg);
			return this.createTotalResponse(this.responseMsg, this.errorFound);
		}
		// 开启第二段业务校验
		this.businessCheckForSafeStock(this.operationList, this.positionIdList, this.materialIdList, this.safeStockQuList);
		if (this.errorFound) {
			this.errorMsgForExport.put("error", this.responseMsg);
			return this.createTotalResponse(this.responseMsg, this.errorFound);
		}
		return this.createTotalResponse(this.responseMsg, this.errorFound);
	}

	/**
	 * 第一类处理 进行报表CSV内容数据校验 若有以下错误将抛出错误信息： a. 有列值为空 b. 报表CSV中出现重复记录 若没有错误通知上层初步校验结果
	 **/
	public void checkSafeStockCSV(String[] csvRecords) {

		/*
		 * 判断是否有列为空 判断是否有重复记录
		 */
		final int excelLength = csvRecords.length;
		// TODO import data
		// TODO export count

		for (int i = 1; i < excelLength; i++) {
			final String[] singleLineRecord = csvRecords[i].split(",");
			// 判断是否有空列值
			final String operation = singleLineRecord[0].trim();
			final String positionId = singleLineRecord[1].trim();
			final String materialId = singleLineRecord[2].trim();
			final String safetyStockQuantity = singleLineRecord[3].trim();

			if (StringUtils.isEmpty(singleLineRecord[0].trim()) || StringUtils.isEmpty(singleLineRecord[1].trim()) || StringUtils.isEmpty(
				singleLineRecord[2].trim()) || StringUtils.isEmpty(singleLineRecord[3].trim())) {
				// 创建错误信息
				StringBuffer result = this.createPartString(operation, positionId, materialId, safetyStockQuantity, i);
				result.append("\"errorMsg\":" + "\"" + BLANK_COLUMN_VALUE + "\"");
				result.append("}");
				this.responseMsg.add(result.toString());
				this.errorFound = true;

			}
			else {
				/*
				 * 判断是否有重复记录 a. 初步校验成功一条即插入一条，同时附带入行号 b. 下一条检查时查询Map，判断是否存在重复记录 c. 如果所有数据都通过初步校验，那么导入最终操作的是Map中的数据
				 */
				this.checkOperationType(operation, positionId, materialId, safetyStockQuantity, i);
			}
		}
	}

	/**
	 * 判断文件长度是否过大
	 */
	public void checkCSVBeyondSize(int length){
		if(length > CSV_RECORD_LIMIT_COUNT){
			this.errorFound = true;
			final StringBuffer result = new StringBuffer();
			result.append("{");
			result.append("\"rowNumber\":" +"\"0"+ "\"" + ",");
			result.append("\"operation\":" +"\"0"+ "\"" +",");
			result.append("\"positionId\":" +"\"0"+ "\"" +",");
			result.append("\"materialId\":" +"\"0"+ "\"" + ",");
			result.append("\"safetyStockQuantity\":" +"\"0"+ "\"" + ",");
			result.append("\"errorMsg\":" + "\"" + CSV_RECORD_LIMIT_COUNT_INFO + "\"");
			result.append("}");
			this.responseMsg.add(result.toString());
		}
	}
	
	/**
	 * 判断文件是否为空
	 */
	public void checkCSVEmpty(int length){
		if(length < EMPTY_CSV_RECORD_COUNT){
			this.errorFound = true;
			final StringBuffer result = new StringBuffer();
			result.append("{");
			result.append("\"rowNumber\":" +"\"0"+ "\"" + ",");
			result.append("\"operation\":" +"\"0"+ "\"" +",");
			result.append("\"positionId\":" +"\"0"+ "\"" +",");
			result.append("\"materialId\":" +"\"0"+ "\"" + ",");
			result.append("\"safetyStockQuantity\":" +"\"0"+ "\"" + ",");
			result.append("\"errorMsg\":" + "\"" + EMPTY_CSV_RECORD_COUNT_INFO + "\"");
			result.append("}");
			this.responseMsg.add(result.toString());
		}
	}
	
	/**
	 * 判断operationType是否为C, c, U, u, D, d中的一个 如果不是，报错
	 **/
	public void checkOperationType(String operation, String positionId, String materialId, String safetyStockQuantity, int rowIndex) {
		final String info = operation + positionId + materialId;
		/*
		 * 判断safetyStockQuantity是否为纯数字字符串
		 */
		if(!this.isNumber(safetyStockQuantity)){
			this.errorFound = true;
			StringBuffer result = this.createPartString(operation, positionId, materialId, safetyStockQuantity, rowIndex);
			result.append("\"errorMsg\":" + "\"" + ILEGAL_SAFETY_STOCK_QUANTITY + "\"");
			result.append("}");
			this.responseMsg.add(result.toString());
		}
		
		switch (operation) {
		case "C":
			if (this.dataExistsInMap(info)) {
				// 数据已存在，出现重复数据
				this.errorFound = true;
				StringBuffer result = this.createPartString(operation, positionId, materialId, safetyStockQuantity, rowIndex);
				result.append("\"errorMsg\":" + "\"" + REPEAT_RECORD + "\"");
				result.append("}");
				this.responseMsg.add(result.toString());
			}
			this.operationList.add(operation);
			this.positionIdList.add(positionId);
			this.materialIdList.add(materialId);
			this.safeStockQuList.add(safetyStockQuantity);
			this.recordMap.put(info, "exist");
			break;
		case "c":
			if (this.dataExistsInMap(info)) {
				// 数据已存在，出现重复数据
				this.errorFound = true;
				StringBuffer result = this.createPartString(operation, positionId, materialId, safetyStockQuantity, rowIndex);
				result.append("\"errorMsg\":" + "\"" + REPEAT_RECORD + "\"");
				result.append("}");
				this.responseMsg.add(result.toString());
			}
			this.operationList.add(operation);
			this.positionIdList.add(positionId);
			this.materialIdList.add(materialId);
			this.safeStockQuList.add(safetyStockQuantity);
			this.recordMap.put(info, "exist");
			break;
		case "U":
			// 理论上来说，通过初步数据校验的，都可以进行到第二阶段验证业务数据
			this.operationList.add(operation);
			this.positionIdList.add(positionId);
			this.materialIdList.add(materialId);
			this.safeStockQuList.add(safetyStockQuantity);
			break;
		case "u":
			// 理论上来说，通过初步数据校验的，都可以进行到第二阶段验证业务数据
			this.operationList.add(operation);
			this.positionIdList.add(positionId);
			this.materialIdList.add(materialId);
			this.safeStockQuList.add(safetyStockQuantity);
			break;
		case "D":
			// 理论上来说，通过初步数据校验的，都可以进行到第二阶段验证业务数据
			this.operationList.add(operation);
			this.positionIdList.add(positionId);
			this.materialIdList.add(materialId);
			this.safeStockQuList.add(safetyStockQuantity);
			break;
		case "d":
			// 理论上来说，通过初步数据校验的，都可以进行到第二阶段验证业务数据
			this.operationList.add(operation);
			this.positionIdList.add(positionId);
			this.materialIdList.add(materialId);
			this.safeStockQuList.add(safetyStockQuantity);
			break;
		default:
			this.errorFound = true;
			StringBuffer result = this.createPartString(operation, positionId, materialId, safetyStockQuantity, rowIndex);
			result.append("\"errorMsg\":" + "\"" + ILEGAL_OPERATION + "\"");
			result.append("}");
			this.responseMsg.add(result.toString());
		}
	}

	/**
	 * 根据列表responseMsg创建错误Message
	 **/
	private String createResponse(List<String> responseMsg) {
		final StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int index = 0; index < responseMsg.size(); index++) {
			sb.append(responseMsg.get(index));
			sb.append(",");
		}
		return sb.substring(0, sb.length() - 1) + "]";
	}

	/**
	 * 判断字符串是否包含非数字字符
	 */
	private boolean isNumber(String str){
		Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
	
	}
	
	/**
	 * 创建返回页面所需要的字符串
	 */
	private StringBuffer createPartString(String operation, String positionId, String materialId, String safetyStockQuantity, int rowIndex){
		StringBuffer result = new StringBuffer();
		result.append("{");
		result.append("\"rowNumber\":" + "\"" + String.valueOf(rowIndex + 1) + "\",");
		result.append("\"operation\":" + "\"" + operation + "\",");
		result.append("\"positionId\":" + "\"" + positionId + "\",");
		result.append("\"materialId\":" + "\"" + materialId + "\",");
		result.append("\"safetyStockQuantity\":" + "\"" + safetyStockQuantity + "\",");
		return result;
	}
	
	/**
	 * 组装ErrorMsg和错误状态信息
	 **/
	public String createTotalResponse(List<String> responseMsg, boolean errorFlag) {
		if (errorFlag) {
			final String detailErrorMsg = this.createResponse(responseMsg);
			final StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("\"detailErrorMsg" + "\":");
			sb.append(detailErrorMsg);
			sb.append(",");
			sb.append("\"errorFlag" + "\":");
			sb.append(String.valueOf(this.errorFound));
			// 增加导入总共失败数
			sb.append(",");
			sb.append("\"totalItems" + "\":");
			sb.append(String.valueOf(this.totalImportItems));
			// 增加验证失败条目数
			sb.append(",");
			sb.append("\"checkFailureItems" + "\":");
			sb.append(String.valueOf(responseMsg.size()));
			sb.append("}");
			return sb.toString();
		}
		final StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"errorFlag" + "\":");
		sb.append(String.valueOf(false));
		// 增加总共导入成功条目数
		sb.append(",");
		sb.append("\"totalItems" + "\":");
		sb.append(String.valueOf(this.totalImportItems));
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 判断字符串是否已存入Map
	 **/
	public boolean dataExistsInMap(String data) {
		/*
		 * Set <Map.Entry<String, String>> set=recordMap.entrySet(); Iterator <Map.Entry<String, String>> iterator = set.iterator();
		 * while(iterator.hasNext()){ Map.Entry<String, String> me=iterator.next(); System.out.println(me.getKey()+"  "+me.getValue()); }
		 */
		if (this.recordMap.get(data) == null) {
			return false;
		}
		else {
			return this.recordMap.get(data).equals("exist");
		}
	}

	/**
	 * 第二阶段业务逻辑校验 a. operationList, positionIdList, materialIdList, safeStockQuanList 容量判断是否一致 b. 调用DTO进行实际的业务逻辑校验 1>
	 * 岗位编号(positionId),物料编号(materialId)是否存在,对于插入已存在记录，立即失败 2> 具体业务逻辑校验以及计算 3> 更新库存表
	 **/
	private void businessCheckForSafeStock(List<String> operationList, List<String> positionIdList, List<String> materialIdList,
		List<String> safeStockQuList) {
		final boolean oListEqualpList = operationList.size() == positionIdList.size();
		final boolean pListEqualmList = positionIdList.size() == materialIdList.size();
		final boolean mListEqualsList = materialIdList.size() == safeStockQuList.size();
		if (!(oListEqualpList && pListEqualmList && mListEqualsList)) {
			this.errorFound = true;
		}
		List<String> dbOperationMsg = new ArrayList<String>();
		dbOperationMsg = this.spareMaterialSafetyStockRepository.uploadSafeStockDataIntoDB(operationList, positionIdList, materialIdList,
			safeStockQuList).get("errorFound");
		// 如果dbOperationMsg不为空，证明第二段业务操作时有错误发生
		if (!CollectionUtils.isEmpty(dbOperationMsg)) {
			this.errorFound = true;
			this.responseMsg = dbOperationMsg;
			return;
		}
		this.errorFound = false;
		this.responseMsg = dbOperationMsg;
	}

	/**
	 * 安全库存导出功能
	 *
	 * @return
	 **/
	@Override
	public HSSFWorkbook exportSafeStock(String positionId, String materialId) {
		List<SpareMaterialSafetyStock> safetyStockList = new ArrayList<SpareMaterialSafetyStock>();
		// 获取safetyStockList
		safetyStockList = this.spareMaterialSafetyStockRepository.querySpareMaterials(positionId, materialId);
		final HSSFWorkbook wb = this.export(safetyStockList);
		return wb;
	}

	/**
	 * 导出动作
	 **/
	public HSSFWorkbook export(List<SpareMaterialSafetyStock> list) {
		// private final static String NUMBER = ""
		final String[] jxsExcelHeader = { POSITION_ID, POSITION_NAME, EMPLOYEE_NAME, MATERIAL_ID, MATERIAL_NAME, SAFESTOCK_QUANTITY, INTRANSIT_SAFESTOCK_QUANTITY, CURRENT_SAFESTOCK_QUANTITY, REQUIRED_SAFESTOCK_QUANTITY, TOTAL_DELIVERED_QUANTITY, TOTAL_PAID_QUANTITY, TOTAL_VERIFIED_QUANTITY,
			TOTAL_DIFFER_QUANTITY };
		final HSSFWorkbook wb = new HSSFWorkbook();
		final HSSFSheet sheet = wb.createSheet(SAFESTOCK_SHEET_NAME);
		HSSFRow row = sheet.createRow(0);

		final HSSFCellStyle style = wb.createCellStyle();
		// 设置水平居中
		style.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置垂直居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// 设置列宽度（像素）
		for (int i = 0; i < jxsExcelHeader.length; i++) {
			sheet.setColumnWidth(i, 20 * 256);
		}

		// 创建Excel头部
		for (int i = 0; i < jxsExcelHeader.length; i++) {
			final HSSFCell cell = row.createCell(i);
			cell.setCellValue(jxsExcelHeader[i]);
			cell.setCellStyle(style);
			// 自动调整列宽度
			// sheet.autoSizeColumn(i);
		}

		// 遍历数据，创建单元格
		for (int rowIndex = 0; rowIndex < list.size(); rowIndex++) {
			row = sheet.createRow(rowIndex + 1);
			final SpareMaterialSafetyStock safeStock = list.get(rowIndex);
			PositionView pv = safeStock.getPosition();
			// TODO check
			// 岗位编号
			row.createCell(0).setCellValue(String.valueOf(pv.getExternalId()));
			// 岗位名称
			row.createCell(1).setCellValue(pv.getName());
			// 员工姓名
			row.createCell(2).setCellValue(pv.getEmployees().get(0).getName());
			// 物料编号
			row.createCell(3).setCellValue(String.valueOf(safeStock.getProduct().getId()));
			// 物料名称
			row.createCell(4).setCellValue(String.valueOf(safeStock.getProduct().getName()));
			// 安全库存
			final BigDecimal safeStockQuantity = safeStock.getSafetyStockQuantity().getValue();
			row.createCell(5).setCellValue(String.valueOf(safeStockQuantity.intValue()));
			// 在途库存
			final BigDecimal inTransitStockQuantity = safeStock.getInTransitStockQuantity().getValue();
			row.createCell(6).setCellValue(String.valueOf(inTransitStockQuantity.intValue()));

			// 累计发放数
			final BigDecimal totalDeliveredQuantity = safeStock.getTotalDeliveredQuantity().getValue();
			// 累计兑付数
			final BigDecimal totalPaidQuantity = safeStock.getTotalPaidQuantity().getValue();
			// 累计核销数
			final BigDecimal totalVerifiedQuantity = safeStock.getTotalVerifiedQuantity().getValue();
			// 累计核销差异数
			final BigDecimal totalDifferenceQuantity = safeStock.getTotalDifferenceQuantity().getValue();
			// 计算当前库存 = 累计发放数 - 累计兑付数 + 累计核销差异数 - 在途库存
			// TODO null check
			final BigDecimal currentSafeStockQuantity = totalDeliveredQuantity.subtract(totalPaidQuantity).add(totalDifferenceQuantity).subtract(
				inTransitStockQuantity);
			row.createCell(7).setCellValue(String.valueOf(currentSafeStockQuantity.intValue()));

			// 需补库存 = 安全库存 - 在途库存 + 当前库存
			final BigDecimal requiredStockQuantity = safeStockQuantity.subtract(inTransitStockQuantity).add(currentSafeStockQuantity);
			row.createCell(8).setCellValue(String.valueOf(requiredStockQuantity.intValue()));
			// 累计发放数
			row.createCell(9).setCellValue(String.valueOf(totalDeliveredQuantity.intValue()));
			// 累计兑付数
			row.createCell(10).setCellValue(String.valueOf(totalPaidQuantity.intValue()));
			// 累计核销数
			row.createCell(11).setCellValue(String.valueOf(totalVerifiedQuantity.intValue()));
			// 累计核销差异数
			row.createCell(12).setCellValue(String.valueOf(totalDifferenceQuantity.intValue()));
		}
		return wb;
	}

	/**
	 * 导出安全库存导入时候的出错信息
	 **/
	public HSSFWorkbook exportErrorSafeStockForImport() {
		final List<String> errorResponse = this.errorMsgForExport.get("error");
		// 获取safetyStockList
		final HSSFWorkbook wb = this.exportErrorSafeStock(errorResponse);
		return wb;
	}

	/**
	 * 导出安全库存出错信息
	 **/
	public HSSFWorkbook exportErrorSafeStock(List<String> errorInfo) {
		// TODO private static final String =
		final String[] jxsExcelHeader = { ROW_NUMBER, OPERATION, POSITION_ID, MATERIAL_ID, SAFESTOCK_QUANTITY, ERROR_INFORMATION };
		final HSSFWorkbook wb = new HSSFWorkbook();
		final HSSFSheet sheet = wb.createSheet(SAFESTOCK_ERROR_EXPORT_SHEET_NAME);
		HSSFRow row = sheet.createRow(0);

		final HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// 设置列宽度（像素）
		for (int i = 0; i < jxsExcelHeader.length; i++) {
			sheet.setColumnWidth(i, 20 * 256);
		}

		// 创建Excel头部
		for (int i = 0; i < jxsExcelHeader.length; i++) {
			final HSSFCell cell = row.createCell(i);
			cell.setCellValue(jxsExcelHeader[i]);
			cell.setCellStyle(style);
		}

		// 遍历数据，创建单元格
		for (int rowIndex = 0; rowIndex < errorInfo.size(); rowIndex++) {
			row = sheet.createRow(rowIndex + 1);
			final String error = errorInfo.get(rowIndex);
			final String info[] = error.split(":");
			// 行号
			row.createCell(0).setCellValue(info[1].split("\"")[1]);
			// 操作
			row.createCell(1).setCellValue(info[2].split("\"")[1]);
			// 岗位编号
			row.createCell(2).setCellValue(info[3].split("\"")[1]);
			// 物料编号
			row.createCell(3).setCellValue(info[4].split("\"")[1]);
			// 安全库存
			row.createCell(4).setCellValue(info[5].split("\"")[1]);
			// 错误信息
			row.createCell(5).setCellValue(info[6].split("\"")[1]);
		}
		return wb;
	}

}
