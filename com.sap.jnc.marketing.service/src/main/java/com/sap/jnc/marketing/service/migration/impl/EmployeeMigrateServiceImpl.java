package com.sap.jnc.marketing.service.migration.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.sap.jnc.marketing.dto.response.migration.EmployeeErrorMessage;
import com.sap.jnc.marketing.dto.response.migration.EmployeeErrorMessageResponse;
import com.sap.jnc.marketing.persistence.criteria.migration.EmployeeAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Department;
import com.sap.jnc.marketing.persistence.model.DepartmentPositionAssignment;
import com.sap.jnc.marketing.persistence.model.DepartmentRelation;
import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.EmployeePositionAssignment;
import com.sap.jnc.marketing.persistence.model.Job;
import com.sap.jnc.marketing.persistence.model.JobPositionAssignment;
import com.sap.jnc.marketing.persistence.model.Position;
import com.sap.jnc.marketing.persistence.model.ProductSalesCategory;
import com.sap.jnc.marketing.persistence.model.Region;
import com.sap.jnc.marketing.persistence.model.RegionPositionAssignment;
import com.sap.jnc.marketing.persistence.model.ValidityPeriod;
import com.sap.jnc.marketing.persistence.repository.DepartmentPositionAssignmentRepository;
import com.sap.jnc.marketing.persistence.repository.DepartmentRelationRepository;
import com.sap.jnc.marketing.persistence.repository.DepartmentRepository;
import com.sap.jnc.marketing.persistence.repository.EmployeePositionAssignmentRepository;
import com.sap.jnc.marketing.persistence.repository.EmployeeRepository;
import com.sap.jnc.marketing.persistence.repository.JobPositionAssignmentRepository;
import com.sap.jnc.marketing.persistence.repository.JobRepository;
import com.sap.jnc.marketing.persistence.repository.PositionRepository;
import com.sap.jnc.marketing.persistence.repository.ProductSalesCategoryRepository;
import com.sap.jnc.marketing.persistence.repository.RegionPositionAssignmentRepository;
import com.sap.jnc.marketing.persistence.repository.RegionRepository;
import com.sap.jnc.marketing.service.migration.EmployeeMigrateService;

/**
 * 
 * @author Dan Tong
 *
 */
@Service
public class EmployeeMigrateServiceImpl implements EmployeeMigrateService {
	public static final Logger logger = LoggerFactory.getLogger(EmployeeMigrateServiceImpl.class);
	private List<Region> createRegions = new ArrayList<>();
	private List<Region> modifyRegions = new ArrayList<>();
	private List<Region> deleteRegions = new ArrayList<>();
	private List<String> departmentExist = new ArrayList<>();
	private List<String> positionExist = new ArrayList<>();
	private List<Department> departmentToDB = new ArrayList<Department>();
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private PositionRepository positionRepository;
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private ProductSalesCategoryRepository productSalesCategoryRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private RegionRepository regionRepository;
	@Autowired
	private DepartmentRelationRepository departmentRelationRepository;
	@Autowired
	private DepartmentPositionAssignmentRepository departmentPositionAssignmentRepository;
	@Autowired
	private EmployeePositionAssignmentRepository employeePositionAssignmentRepository;
	@Autowired
	private JobPositionAssignmentRepository jobPositionAssignmentRepository;
	@Autowired
	private RegionPositionAssignmentRepository regionPositionAssignmentRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Page<Employee> advanceSearch(EmployeeAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return this.employeeRepository.advanceSearch(searchCriteria, pageRequest);
	}
	@Override
	public EmployeeErrorMessageResponse parseFile(String type, byte[] content) {
		EmployeeErrorMessageResponse eemr = new EmployeeErrorMessageResponse();
		List<EmployeeErrorMessage> results = new ArrayList<>();
		try {
			ByteArrayInputStream is = new ByteArrayInputStream(content);
			// 创建工作页
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			//按不同的sheet进行解析
			results = parseDifferentSheet(type, workbook);
		}catch (IOException e) {
		}
		eemr.setData(results);
		return eemr;
	}
	private List<EmployeeErrorMessage> parseDifferentSheet(String type, XSSFWorkbook workbook) {
		List<EmployeeErrorMessage> results = new ArrayList<>();
		List<Department> department = new ArrayList<>();
		List<Position> position = new ArrayList<>();
		List<Job> job = new ArrayList<>();
		List<ProductSalesCategory> productSalesCategory = new ArrayList<>();
		List<Employee> employee = new ArrayList<>();
		List<DepartmentRelation> departmentRelation = new ArrayList<>();
		List< DepartmentPositionAssignment>  departmentPositionAssignment = new ArrayList<>();
		List<EmployeePositionAssignment> employeePositionAssignment = new ArrayList<>();
		List<JobPositionAssignment> jobPositionAssignment = new ArrayList<>();
		List<RegionPositionAssignment> regionPositionAssignment = new ArrayList<>();
		List<Region> region = new ArrayList<>();
		//解析Excel
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			// 读取工作页
			XSSFSheet sheet = workbook.getSheetAt(i);
			switch (sheet.getSheetName()) {
			//组织结构
			case "组织结构(O)":
				department = parseDepartmentSheet(sheet);
				break;
			//岗位
			case "职位岗位(S)":
				position = parsePositionSheet(sheet);
				break;
			//职务
			case "职务JOB(C)":
				job = parseJobSheet(sheet);
				break;
			//人事子范围
			case "人事子范围":
				productSalesCategory = parsePSCSheet(sheet);
				break;
			//员工主数据
			case "员工主数据(P)":
				employee = parseEmployeeSheet(sheet);
				break;
			//区域主数据
			case "区域主数据":
				region = parseRegionSheet(sheet);
				break;
			//部门关系表 
			case "组织部门上下级关系":
				departmentRelation = parseDepartmentRelationSheet(sheet);
				break;
			//岗位与部门关系
			case "岗位和组织部门关联":
				departmentPositionAssignment = parsedepartmentPositionAssignmentSheet(sheet);
				break;
			//人员和岗位的关系 
			case "人员和岗位关联":
				employeePositionAssignment = parseEmployeePositionAssignmentSheet(sheet);
				break;
			//岗位和职务(JOB)的关系 
			case "岗位-职务对应关系":
				jobPositionAssignment = parseJobPositionAssignmentSheet(sheet);
				break;
			//岗位和区域的关系 
			case "岗位-区域":
				regionPositionAssignment = parseRegionPositionAssignmentSheet(sheet);
				break;
			}
		}
		//数据校验
		results = commonCheck(department,position,job,productSalesCategory,employee,region,
			departmentRelation,departmentPositionAssignment,employeePositionAssignment,jobPositionAssignment,regionPositionAssignment);
		if (CollectionUtils.isEmpty(results)) {
			results = depedenceCheck(type,department,position,job,productSalesCategory,employee,region,
				departmentRelation,departmentPositionAssignment,employeePositionAssignment,jobPositionAssignment,regionPositionAssignment);
			if (CollectionUtils.isEmpty(results)) {
//				//校验成功，插入数据库
				results = saveToDB(departmentToDB,position,job,productSalesCategory,employee,createRegions,modifyRegions,deleteRegions,
					departmentRelation,departmentPositionAssignment,employeePositionAssignment,jobPositionAssignment,regionPositionAssignment);
				return results;
			}else{
				return results;
			}
			
		}
		return results;
	}
	private List<Department> parseDepartmentSheet(XSSFSheet sheet) {
		List<Department> departments = new ArrayList<>();
		String value = null;
		//从第四行开始读取
		int firstRowNum = 3;
		int lastRowNum = sheet.getLastRowNum();
		for (int i = firstRowNum; i <= lastRowNum; i++) {
			Department department = new Department();
			ValidityPeriod validityPeriod = new ValidityPeriod();
			XSSFRow row = sheet.getRow(i);
			if(row==null){
				continue;
			}
			int lastCellNum = row.getLastCellNum();
			for (int j = 0; j < lastCellNum; j++) {
				XSSFCell cell = row.getCell(j);
				if (cell == null) {
					value = "";
				}
				else {
					value = getCellValue(cell);
				}
				Date date = null;
				Calendar calendar;
				switch (j) {
				case 0:
					//有效期从
					if (StringUtils.isEmpty(value)) {
						value = "19000101";
					}
					try {
						date = DateUtils.parseDateStrictly(value, "yyyyMMdd");
					}
					catch (ParseException e) {
						
					}
					calendar = Calendar.getInstance();
					calendar.setTime(date);
					validityPeriod.setValidFrom(calendar);
					break;
				case 1:
					//有效期到
					if (StringUtils.isEmpty(value)) {
						value = "19000101";
					}
					try {
						date = DateUtils.parseDateStrictly(value, "yyyyMMdd");
					}
					catch (ParseException e) {
					}
					calendar = Calendar.getInstance();
					calendar.setTime(date);
					validityPeriod.setValidTo(calendar);
					department.setValidityPeriod(validityPeriod);
					break;
				case 2:
					//组织单元编号
					department.setExternalId(value);
					break;
				case 3:
					//组织单元名称
					department.setName(value);
					break;
				}
			}
			if (StringUtils.isEmpty(department.getExternalId()) && StringUtils.isEmpty(department.getName())) {
				continue;
			}
			departments.add(department);
		}
		return departments;
	}
	private List<Position> parsePositionSheet(XSSFSheet sheet) {
		List<Position> positions = new ArrayList<>();
		String value = null;
		//从第四行开始读取
		int firstRowNum = 3;
		int lastRowNum = sheet.getLastRowNum();
		for (int i = firstRowNum; i <= lastRowNum; i++) {
			Position position = new Position();
			ValidityPeriod validityPeriod = new ValidityPeriod();
			XSSFRow row = sheet.getRow(i);
			if(row==null){
				continue;
			}
			int lastCellNum = row.getLastCellNum();
			for (int j = 0; j < lastCellNum; j++) {
				XSSFCell cell = row.getCell(j);
				if (cell == null) {
					value = "";
				}
				else {
					value = getCellValue(cell);
				}
				Date date = null;
				Calendar calendar;
				switch (j) {
				case 0:
					//有效期从
					if (StringUtils.isEmpty(value)) {
						value = "19000101";
					}
					try {
						date = DateUtils.parseDateStrictly(value, "yyyyMMdd");
					}
					catch (ParseException e) {
						
					}
					calendar = Calendar.getInstance();
					calendar.setTime(date);
					validityPeriod.setValidFrom(calendar);
					break;
				case 1:
					//有效期到
					if (StringUtils.isEmpty(value)) {
						value = "19000101";
					}
					try {
						date = DateUtils.parseDateStrictly(value, "yyyyMMdd");
					}
					catch (ParseException e) {
					}
					calendar = Calendar.getInstance();
					calendar.setTime(date);
					validityPeriod.setValidTo(calendar);
					position.setValidityPeriod(validityPeriod);
					break;
				case 2:
					//岗位编号
					position.setExternalId(value);
					break;
				case 3:
					//岗位名称
					position.setName(value);
					break;
				case 4:
					//是否是领导岗位
					if (value.equals("X")) {
						position.setIsHead(true);
					}else{
						position.setIsHead(false);
					}
					break;
				}
			}
			if (StringUtils.isEmpty(position.getExternalId()) && StringUtils.isEmpty(position.getName())) {
				continue;
			}
			positions.add(position);
		}
		return positions;
	}
	private List<Job> parseJobSheet(XSSFSheet sheet) {
		List<Job> jobs = new ArrayList<>();
		String value = null;
		//从第四行开始读取
		int firstRowNum = 3;
		int lastRowNum = sheet.getLastRowNum();
		for (int i = firstRowNum; i <= lastRowNum; i++) {
			Job job = new Job();
			ValidityPeriod validityPeriod = new ValidityPeriod();
			XSSFRow row = sheet.getRow(i);
			if(row==null){
				continue;
			}
			int lastCellNum = row.getLastCellNum();
			for (int j = 0; j < lastCellNum; j++) {
				XSSFCell cell = row.getCell(j);
				if (cell == null) {
					value = "";
				}
				else {
					value = getCellValue(cell);
				}
				Date date = null;
				Calendar calendar;
				switch (j) {
				case 0:
					//有效期从
					if (StringUtils.isEmpty(value)) {
						value = "19000101";
					}
					try {
						date = DateUtils.parseDateStrictly(value, "yyyyMMdd");
					}
					catch (ParseException e) {
						
					}
					calendar = Calendar.getInstance();
					calendar.setTime(date);
					validityPeriod.setValidFrom(calendar);
					break;
				case 1:
					//有效期到
					if (StringUtils.isEmpty(value)) {
						value = "19000101";
					}
					try {
						date = DateUtils.parseDateStrictly(value, "yyyyMMdd");
					}
					catch (ParseException e) {
					}
					calendar = Calendar.getInstance();
					calendar.setTime(date);
					validityPeriod.setValidTo(calendar);
					job.setValidityPeriod(validityPeriod);
					break;
				case 2:
					//岗位编号
					job.setExternalId(value);
					break;
				case 3:
					//岗位名称
					job.setName(value);;
					break;
				}
			}
			if (StringUtils.isEmpty(job.getExternalId()) && StringUtils.isEmpty(job.getName())) {
				continue;
			}
			jobs.add(job);
		}
		return jobs;
	}
	private List<ProductSalesCategory> parsePSCSheet(XSSFSheet sheet) {
		List<ProductSalesCategory> productSalesCategorys = new ArrayList<>();
		String value = null;
		//从第四行开始读取
		int firstRowNum = 3;
		int lastRowNum = sheet.getLastRowNum();
		for (int i = firstRowNum; i <= lastRowNum; i++) {
			ProductSalesCategory productSalesCategory = new ProductSalesCategory();
			XSSFRow row = sheet.getRow(i);
			if(row==null){
				continue;
			}
			int lastCellNum = row.getLastCellNum();
			for (int j = 0; j < lastCellNum; j++) {
				XSSFCell cell = row.getCell(j);
				if (cell == null) {
					value = "";
				}
				else {
					value = getCellValue(cell);
				}
				switch (j) {
				case 0:
					//人事子范围编码
					productSalesCategory.setExternalId(value);
					break;
				case 1:
					//人事子范围描述
					productSalesCategory.setName(value);
					break;
				}
			}
			if (StringUtils.isEmpty(productSalesCategory.getExternalId()) && StringUtils.isEmpty(productSalesCategory.getName())) {
				continue;
			}
			productSalesCategorys.add(productSalesCategory);
		}
		return productSalesCategorys;
	}
	private List<Employee> parseEmployeeSheet(XSSFSheet sheet) {
		List<Employee> employees = new ArrayList<>();
		String value = null;
		//从第四行开始读取
		int firstRowNum = 3;
		int lastRowNum = sheet.getLastRowNum();
		for (int i = firstRowNum; i <= lastRowNum; i++) {
			Employee employee = new Employee();
			ValidityPeriod validityPeriod = new ValidityPeriod();
			XSSFRow row = sheet.getRow(i);
			if(row==null){
				continue;
			}
			int lastCellNum = row.getLastCellNum();
			for (int j = 0; j < lastCellNum; j++) {
				XSSFCell cell = row.getCell(j);
				if (cell == null) {
					value = "";
				}
				else {
					value = getCellValue(cell);
				}
				Date date = null;
				Calendar calendar;
				switch (j) {
				case 0:
					//有效期从
					if (StringUtils.isEmpty(value)) {
						value = "19000101";
					}
					try {
						date = DateUtils.parseDateStrictly(value, "yyyyMMdd");
					}
					catch (ParseException e) {
						
					}
					calendar = Calendar.getInstance();
					calendar.setTime(date);
					validityPeriod.setValidFrom(calendar);
					break;
				case 1:
					//有效期到
					if (StringUtils.isEmpty(value)) {
						value = "19000101";
					}
					try {
						date = DateUtils.parseDateStrictly(value, "yyyyMMdd");
					}
					catch (ParseException e) {
					}
					calendar = Calendar.getInstance();
					calendar.setTime(date);
					validityPeriod.setValidTo(calendar);
					employee.setValidityPeriod(validityPeriod);
					break;
				case 2:
					//员工号
					employee.setExternalId(value);
					break;
				case 3:
					//员工姓名
					employee.setName(value);
					break;
				case 4:
					//电话号码
					employee.setPhone(value);
					break;
				case 5:
					//身份证号码
					employee.setIdCardNO(value);
					break;
				case 6:
					//所属岗位编号
//					employee.setposition(value);
					break;
				case 7:
					//所属部门编号
//					employee.setDepartment(value);;
					break;
				case 8:
					//人事子范围
					ProductSalesCategory psc = new ProductSalesCategory();
					psc.setExternalId(value);
					employee.setProductSalesCategory(psc);
					break;
				}
			}
			if (StringUtils.isEmpty(employee.getExternalId()) && StringUtils.isEmpty(employee.getName()) &&
				StringUtils.isEmpty(employee.getPhone()) && StringUtils.isEmpty(employee.getIdCardNO()) &&
				employee.getProductSalesCategory() == null) {
				continue;
			}
			employees.add(employee);
		}
		return employees;
	}
	private List<Region> parseRegionSheet(XSSFSheet sheet) {
		List<Region> regions = new ArrayList<>();	
		String value = null;
		//从第四行开始读取
		int firstRowNum = 3;
		int lastRowNum = sheet.getLastRowNum();
		for (int i = firstRowNum; i <= lastRowNum; i++) {
			Region region = new Region();
			ValidityPeriod validityPeriod = new ValidityPeriod();
			String type = "";
			XSSFRow row = sheet.getRow(i);
			if(row==null){
				continue;
			}
			int lastCellNum = row.getLastCellNum();
			for (int j = 0; j < lastCellNum; j++) {
				XSSFCell cell = row.getCell(j);
				if (cell == null) {
					value = "";
				}
				else {
					value = getCellValue(cell);
				}
				Date date = null;
				Calendar calendar;
				switch (j) {
				case 0:
					//省(直辖市)编号
					region.setProvinceId(value);
					break;
				case 1:
					//省(直辖市)名称
					region.setProvinceName(value);
					break;
				case 2:
					//市编号
					region.setCityId(value);
					break;
				case 3:
					//市名称
					region.setCityName(value);
					break;
				case 4:
					//区县编号
					region.setCountyId(value);
					break;
				case 5:
					//区县名称
					region.setCountyName(value);
					break;
				case 6:
					//区域级别
					if (StringUtils.isEmpty(value)) {
						region.setLevel(0);
					}else{
						region.setLevel(Integer.valueOf(value).intValue());
					}
					break;
				case 7:
					//操作标识
					switch (value.toUpperCase()) {
					case "C":
						region.setIsValid(true);
						createRegions.add(region);
						break;
					case "U":
						region.setIsValid(true);
						modifyRegions.add(region);
						break;
					case "D":
						region.setIsValid(false);
						deleteRegions.add(region);
						break;
					default:
						region.setIsValid(null);
						break;
					}
					if (StringUtils.isEmpty(region.getProvinceId()) && StringUtils.isEmpty(region.getProvinceName()) && 
						StringUtils.isEmpty(region.getCityId()) && StringUtils.isEmpty(region.getCityName()) &&
						StringUtils.isEmpty(region.getCountyId()) && StringUtils.isEmpty(region.getCountyName())) {
						continue;
					}
					regions.add(region);
				}
			}
		}
		return regions;
	}
	private List<DepartmentRelation> parseDepartmentRelationSheet(XSSFSheet sheet) {
		List<DepartmentRelation> departmentRelations = new ArrayList<>();
		String value = null;
		//从第四行开始读取
		int firstRowNum = 3;
		int lastRowNum = sheet.getLastRowNum();
		for (int i = firstRowNum; i <= lastRowNum; i++) {
			DepartmentRelation departmentRelation = new DepartmentRelation();
			ValidityPeriod validityPeriod = new ValidityPeriod();
			XSSFRow row = sheet.getRow(i);
			if(row==null){
				continue;
			}
			int lastCellNum = row.getLastCellNum();
			for (int j = 0; j < lastCellNum; j++) {
				XSSFCell cell = row.getCell(j);
				if (cell == null) {
					value = "";
				}
				else {
					value = getCellValue(cell);
				}
				Date date = null;
				Calendar calendar;
				switch (j) {
				case 0:
					//有效期从
					if (StringUtils.isEmpty(value)) {
						value = "19000101";
					}
					try {
						date = DateUtils.parseDateStrictly(value, "yyyyMMdd");
					}
					catch (ParseException e) {
						
					}
					calendar = Calendar.getInstance();
					calendar.setTime(date);
					validityPeriod.setValidFrom(calendar);
					break;
				case 1:
					//有效期到
					if (StringUtils.isEmpty(value)) {
						value = "19000101";
					}
					try {
						date = DateUtils.parseDateStrictly(value, "yyyyMMdd");
					}
					catch (ParseException e) {
					}
					calendar = Calendar.getInstance();
					validityPeriod.setValidTo(calendar);
					departmentRelation.setValidityPeriod(validityPeriod);
					break;
				case 2:
					//组织部门编号
					departmentRelation.setDepartmentId(value);
					break;
				case 3:
					//上级组织部门编号
					departmentRelation.setSuperiorDepartmentId(value);
					break;
				}
			}
			if (StringUtils.isEmpty(departmentRelation.getDepartmentId()) && StringUtils.isEmpty(departmentRelation.getSuperiorDepartmentId())) {
				continue;
			}
			departmentRelations.add(departmentRelation);
		}
		return departmentRelations;
	}
	private List<DepartmentPositionAssignment> parsedepartmentPositionAssignmentSheet(XSSFSheet sheet) {
		List<DepartmentPositionAssignment> departmentPositionAssignments = new ArrayList<>();
		String value = null;
		//从第四行开始读取
		int firstRowNum = 3;
		int lastRowNum = sheet.getLastRowNum();
		for (int i = firstRowNum; i <= lastRowNum; i++) {
			DepartmentPositionAssignment departmentPositionAssignment = new DepartmentPositionAssignment();
			ValidityPeriod validityPeriod = new ValidityPeriod();
			XSSFRow row = sheet.getRow(i);
			if(row==null){
				continue;
			}
			int lastCellNum = row.getLastCellNum();
			for (int j = 0; j < lastCellNum; j++) {
				XSSFCell cell = row.getCell(j);
				if (cell == null) {
					value = "";
				}
				else {
					value = getCellValue(cell);
				}
				Date date = null;
				Calendar calendar;
				switch (j) {
				case 0:
					//有效期从
					if (StringUtils.isEmpty(value)) {
						value = "19000101";
					}
					try {
						date = DateUtils.parseDateStrictly(value, "yyyyMMdd");
					}
					catch (ParseException e) {
						
					}
					calendar = Calendar.getInstance();
					calendar.setTime(date);
					validityPeriod.setValidFrom(calendar);
					break;
				case 1:
					//有效期到
					if (StringUtils.isEmpty(value)) {
						value = "19000101";
					}
					try {
						date = DateUtils.parseDateStrictly(value, "yyyyMMdd");
					}
					catch (ParseException e) {
					}
					calendar = Calendar.getInstance();
					calendar.setTime(date);
					validityPeriod.setValidTo(calendar);
					departmentPositionAssignment.setValidityPeriod(validityPeriod);
					break;
				case 2:
					//岗位编号
					departmentPositionAssignment.setPositionExternalId(value);
					break;
				case 3:
					//组织部门编号
					departmentPositionAssignment.setDepartmentExternalId(value);
					break;
				}
			}
			if (StringUtils.isEmpty(departmentPositionAssignment.getPositionExternalId()) && StringUtils.isEmpty(departmentPositionAssignment.getDepartmentExternalId())) {
				continue;
			}
			departmentPositionAssignments.add(departmentPositionAssignment);
		}
		return departmentPositionAssignments;
	}
	private List<EmployeePositionAssignment> parseEmployeePositionAssignmentSheet(XSSFSheet sheet) {
		List<EmployeePositionAssignment> employeePositionAssignments = new ArrayList<>();
		String value = null;
		//从第四行开始读取
		int firstRowNum = 3;
		int lastRowNum = sheet.getLastRowNum();
		for (int i = firstRowNum; i <= lastRowNum; i++) {
			EmployeePositionAssignment employeePositionAssignment = new EmployeePositionAssignment();
			ValidityPeriod validityPeriod = new ValidityPeriod();
			XSSFRow row = sheet.getRow(i);
			if(row==null){
				continue;
			}
			int lastCellNum = row.getLastCellNum();
			for (int j = 0; j < lastCellNum; j++) {
				XSSFCell cell = row.getCell(j);
				if (cell == null) {
					value = "";
				}
				else {
					value = getCellValue(cell);
				}
				Date date = null;
				Calendar calendar;
				switch (j) {
				case 0:
					//有效期从
					if (StringUtils.isEmpty(value)) {
						value = "19000101";
					}
					try {
						date = DateUtils.parseDateStrictly(value, "yyyyMMdd");
					}
					catch (ParseException e) {
						
					}
					calendar = Calendar.getInstance();
					calendar.setTime(date);
					validityPeriod.setValidFrom(calendar);
					break;
				case 1:
					//有效期到
					if (StringUtils.isEmpty(value)) {
						value = "19000101";
					}
					try {
						date = DateUtils.parseDateStrictly(value, "yyyyMMdd");
					}
					catch (ParseException e) {
					}
					calendar = Calendar.getInstance();
					calendar.setTime(date);
					validityPeriod.setValidTo(calendar);
					employeePositionAssignment.setValidityPeriod(validityPeriod);
					break;
				case 2:
					//员工号
					employeePositionAssignment.setEmployeeExternalId(value);
					break;
				case 3:
					//岗位编号
					employeePositionAssignment.setPositionExternalId(value);
					break;
				}
			}
			if (StringUtils.isEmpty(employeePositionAssignment.getEmployeeExternalId()) && StringUtils.isEmpty(employeePositionAssignment.getPositionExternalId())) {
				continue;
			}
			employeePositionAssignments.add(employeePositionAssignment);
		}
		return employeePositionAssignments;
	}
	private List<JobPositionAssignment> parseJobPositionAssignmentSheet(XSSFSheet sheet) {
		List<JobPositionAssignment> jobPositionAssignments = new ArrayList<>();
		String value = null;
		//从第四行开始读取
		int firstRowNum = 3;
		int lastRowNum = sheet.getLastRowNum();
		for (int i = firstRowNum; i <= lastRowNum; i++) {
			JobPositionAssignment jobPositionAssignment = new JobPositionAssignment();
			ValidityPeriod validityPeriod = new ValidityPeriod();
			XSSFRow row = sheet.getRow(i);
			if(row==null){
				continue;
			}
			int lastCellNum = row.getLastCellNum();
			for (int j = 0; j < lastCellNum; j++) {
				XSSFCell cell = row.getCell(j);
				if (cell == null) {
					value = "";
				}
				else {
					value = getCellValue(cell);
				}
				Date date = null;
				Calendar calendar;
				switch (j) {
				case 0:
					//有效期从
					if (StringUtils.isEmpty(value)) {
						value = "19000101";
					}
					try {
						date = DateUtils.parseDateStrictly(value, "yyyyMMdd");
					}
					catch (ParseException e) {
						
					}
					calendar = Calendar.getInstance();
					calendar.setTime(date);
					validityPeriod.setValidFrom(calendar);
					break;
				case 1:
					//有效期到
					if (StringUtils.isEmpty(value)) {
						value = "19000101";
					}
					try {
						date = DateUtils.parseDateStrictly(value, "yyyyMMdd");
					}
					catch (ParseException e) {
					}
					calendar = Calendar.getInstance();
					calendar.setTime(date);
					validityPeriod.setValidTo(calendar);
					jobPositionAssignment.setValidityPeriod(validityPeriod);
					break;
				case 2:
					//岗位编号
					jobPositionAssignment.setPositionExternalId(value);
					break;
				case 3:
					//职务编号
					jobPositionAssignment.setJobExternalId(value);;
					break;
				}
			}
			if (StringUtils.isEmpty(jobPositionAssignment.getPositionExternalId()) && StringUtils.isEmpty(jobPositionAssignment.getJobExternalId())) {
				continue;
			}
			jobPositionAssignments.add(jobPositionAssignment);
		}
		return jobPositionAssignments;
	}
	private List<RegionPositionAssignment> parseRegionPositionAssignmentSheet(XSSFSheet sheet) {
		List<RegionPositionAssignment> regionPositionAssignments = new ArrayList<>();
		String value = null;
		//从第四行开始读取
		int firstRowNum = 3;
		int lastRowNum = sheet.getLastRowNum();
		for (int i = firstRowNum; i <= lastRowNum; i++) {
			RegionPositionAssignment regionPositionAssignment = new RegionPositionAssignment();
			ValidityPeriod validityPeriod = new ValidityPeriod();
			XSSFRow row = sheet.getRow(i);
			if(row==null){
				continue;
			}
			int lastCellNum = row.getLastCellNum();
			for (int j = 0; j < lastCellNum; j++) {
				XSSFCell cell = row.getCell(j);
				if (cell == null) {
					value = "";
				}
				else {
					value = getCellValue(cell);
				}
				Date date = null;
				Calendar calendar;
				switch (j) {
				case 0:
					//有效期从
					if (StringUtils.isEmpty(value)) {
						value = "19000101";
					}
					try {
						date = DateUtils.parseDateStrictly(value, "yyyyMMdd");
					}
					catch (ParseException e) {
						
					}
					calendar = Calendar.getInstance();
					calendar.setTime(date);
					validityPeriod.setValidFrom(calendar);
					break;
				case 1:
					//有效期到
					if (StringUtils.isEmpty(value)) {
						value = "19000101";
					}
					try {
						date = DateUtils.parseDateStrictly(value, "yyyyMMdd");
					}
					catch (ParseException e) {
					}
					calendar = Calendar.getInstance();
					calendar.setTime(date);
					validityPeriod.setValidTo(calendar);
					regionPositionAssignment.setValidityPeriod(validityPeriod);
					break;
				case 2:
					//岗位编号
					regionPositionAssignment.setPositionExternalId(value);
					break;
				case 3:
					//区县编号
					regionPositionAssignment.setRegionId(value);;;
					break;
				}
			}
			if (StringUtils.isEmpty(regionPositionAssignment.getPositionExternalId()) && StringUtils.isEmpty(regionPositionAssignment.getRegionId())) {
				continue;
			}
			regionPositionAssignments.add(regionPositionAssignment);
		}
		return regionPositionAssignments;
	}
	private String getCellValue(XSSFCell cell) {
		int cellType = cell.getCellType();
		if (cellType == cell.CELL_TYPE_BLANK) {
			return "";
		}
		else {
			if (cellType == cell.CELL_TYPE_NUMERIC) {
//				return String.valueOf(cell.getNumericCellValue());
				DecimalFormat df = new DecimalFormat("#.#");
				return df.format(cell.getNumericCellValue()); 
			}
			if(cellType == cell.CELL_TYPE_STRING){
				return cell.getStringCellValue();
			}
		}
		return "";
	}
	//一般检查
	private List<EmployeeErrorMessage> commonCheck(List<Department> department, List<Position> position, List<Job> job,
		List<ProductSalesCategory> productSalesCategory, List<Employee> employee, List<Region> region, List<DepartmentRelation> departmentRelation,
		List<DepartmentPositionAssignment> departmentPositionAssignment, List<EmployeePositionAssignment> employeePositionAssignment,
		List<JobPositionAssignment> jobPositionAssignment, List<RegionPositionAssignment> regionPositionAssignment) {
		//检查消息
		List<EmployeeErrorMessage> commonErrorMessage = new ArrayList<>();
		//检查组织结构(O)
		commonErrorMessage.addAll(commonCheckDepartment(department));
		//检查职位岗位(S)
		commonErrorMessage.addAll(commonCheckPosition(position));
		//检查职务JOB(C)
		commonErrorMessage.addAll(commonCheckJob(job));
		//检查人事子范围
		commonErrorMessage.addAll(commonCheckProductSalesCategory(productSalesCategory ));
		//检查员工主数据
		commonErrorMessage.addAll(commonCheckEmployee(employee));
		//检查区域主数据
		commonErrorMessage.addAll(commonCheckRegion(region));
		//检查组织部门上下级关系
		commonErrorMessage.addAll(commonCheckDepartmentRelation(departmentRelation));
		//检查岗位与部门关系
		commonErrorMessage.addAll(commonCheckDepartmentPositionAssignment(departmentPositionAssignment));
		//检查人员和岗位关联
		commonErrorMessage.addAll(commonCheckEmployeePositionAssignment(employeePositionAssignment));
		//检查岗位-职务对应关系
		commonErrorMessage.addAll(commonCheckJobPositionAssignment(jobPositionAssignment));
		//检查岗位-区域
		commonErrorMessage.addAll(commonCheckRegionPositionAssignment(regionPositionAssignment));
		
		return commonErrorMessage;
	}
	//依赖检查
	private List<EmployeeErrorMessage> depedenceCheck(String type, List<Department> department, List<Position> position, List<Job> job,
		List<ProductSalesCategory> productSalesCategory, List<Employee> employee, List<Region> region,
		List<DepartmentRelation> departmentRelation, List<DepartmentPositionAssignment> departmentPositionAssignment, List<EmployeePositionAssignment> employeePositionAssignment,
		List<JobPositionAssignment> jobPositionAssignment, List<RegionPositionAssignment> regionPositionAssignment) {
		//检查消息
		List<EmployeeErrorMessage> dependenceErrorMessage = new ArrayList<>();
		//检查组织结构(O)
		dependenceErrorMessage.addAll(dependenceCheckDepartment(type,department));
		//检查职位岗位(S)
		dependenceErrorMessage.addAll(dependenceCheckPosition(position));
		//检查职务JOB(C)
		dependenceErrorMessage.addAll(dependenceCheckJob(job));
		//检查人事子范围
		dependenceErrorMessage.addAll(dependenceCheckProductSalesCategory(productSalesCategory));
		//检查员工主数据
		dependenceErrorMessage.addAll(dependenceCheckEmployee(employee, productSalesCategory));
		//检查区域主数据
		dependenceErrorMessage.addAll(dependenceCheckRegion(modifyRegions));
		dependenceErrorMessage.addAll(dependenceCheckRegion(deleteRegions));
		//检查组织部门上下级关系
		dependenceErrorMessage.addAll(dependenceCheckDepartmentRelation(departmentRelation, department));
		//检查岗位与部门关系
		dependenceErrorMessage.addAll(dependenceCheckDepartmentPositionAssignment(departmentPositionAssignment, department, position));
		//检查人员和岗位关联
		dependenceErrorMessage.addAll(dependenceCheckEmployeePositionAssignment(employeePositionAssignment, employee, position));
		//检查岗位-职务对应关系
		dependenceErrorMessage.addAll(dependenceCheckJobPositionAssignment(jobPositionAssignment, job, position));
		//检查岗位-区域
		dependenceErrorMessage.addAll(dependenceCheckRegionPositionAssignment(regionPositionAssignment, region, position));
		
		return dependenceErrorMessage;
	}

	//检查组织结构(O)
	private List<EmployeeErrorMessage> commonCheckDepartment(List<Department> department) {
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		List<String> DuplicateCheck = new ArrayList<>();
		String duplicate;
		for (int i = 0; i < department.size(); i++) {
			if (department.get(i).getValidityPeriod().getValidFrom().equals("19000101")) {
				eems.add(new EmployeeErrorMessage("组织结构(O)",i+4,"有效期从字段必填"));
			}
			if (department.get(i).getValidityPeriod().getValidTo().equals("19000101")) {
				eems.add(new EmployeeErrorMessage("组织结构(O)",i+4,"有效期到字段必填"));
			}
			if (StringUtils.isEmpty(department.get(i).getExternalId())) {
				eems.add(new EmployeeErrorMessage("组织结构(O)",i+4,"组织单元编号字段必填"));
			}else{
				//判断重复数据
				SimpleDateFormat from = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat to = new SimpleDateFormat("yyyyMMdd");
				String StringFrom = from.format(department.get(i).getValidityPeriod().getValidFrom().getTime());
				String StringTo = to.format(department.get(i).getValidityPeriod().getValidTo().getTime());
				duplicate = StringFrom + StringTo + department.get(i).getExternalId();
				if (DuplicateCheck.contains(duplicate)) {
					eems.add(new EmployeeErrorMessage("组织结构(O)",i+4,"数据重复"));
				}else{
					DuplicateCheck.add(duplicate);
				}
			}
			if (department.get(i).getValidityPeriod().getValidFrom().after(department.get(i).getValidityPeriod().getValidTo())) {
				eems.add(new EmployeeErrorMessage("组织结构(O)",i+4,"有效期从字段大于有效期到字段"));
			}
			if (StringUtils.isEmpty(department.get(i).getName())) {
				eems.add(new EmployeeErrorMessage("组织结构(O)",i+4,"组织单元名称字段必填"));
			}
		}
		return eems;
	}
	private List<EmployeeErrorMessage> dependenceCheckDepartment(String type, List<Department> department) {
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		List<Department> latestDepartments = new ArrayList<Department>();
		if (type.equals("false")) {
			for (int i = 0; i < department.size(); i++) {
				String exitFlag = "";
				for (int j = 0; j < latestDepartments.size(); j++) {
					exitFlag = "X";
					if (latestDepartments.get(j).getExternalId().equals(department.get(i).getExternalId())) {
						if (department.get(i).getValidityPeriod().getValidFrom().after(latestDepartments.get(j).getValidityPeriod().getValidFrom())) {
							departmentToDB.add(department.get(i));
							break;
						}else if (department.get(i).getValidityPeriod().getValidFrom().equals(latestDepartments.get(j).getValidityPeriod().getValidFrom())) {
							department.get(i).setId(latestDepartments.get(j).getId());
							departmentToDB.add(department.get(i));
							break;
						}else if (department.get(i).getValidityPeriod().getValidFrom().before(latestDepartments.get(j).getValidityPeriod().getValidFrom())) {
							eems.add(new EmployeeErrorMessage("组织结构(O)",i+4,"有效期从字段在当前组织结构ID的最后一条记录之前"));
						}
					}
				}
				if (exitFlag.equals("")) {
					Department departmentTemp = departmentRepository.findLatestOneByExternalIds(department.get(i).getExternalId());
					if (department.get(i).getValidityPeriod().getValidFrom().after(departmentTemp.getValidityPeriod().getValidFrom())) {
						departmentToDB.add(department.get(i));
						break;
					}else if (department.get(i).getValidityPeriod().getValidFrom().equals(departmentTemp.getValidityPeriod().getValidFrom())) {
						department.get(i).setId(departmentTemp.getId());
						departmentToDB.add(department.get(i));
						break;
					}else if (department.get(i).getValidityPeriod().getValidFrom().before(departmentTemp.getValidityPeriod().getValidFrom())) {
						eems.add(new EmployeeErrorMessage("组织结构(O)",i+4,"有效期从字段在当前组织结构ID的最后一条记录之前"));
					}
					latestDepartments.add(departmentTemp);
				}
				
			}
		}
		else{
			departmentToDB.addAll(department);
		}
		return eems;
	}
	//检查职位岗位(S)
	private List<EmployeeErrorMessage> commonCheckPosition(List<Position> position) {
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		List<String> DuplicateCheck = new ArrayList<>();
		String duplicate;
		for (int i = 0; i < position.size(); i++) {
			if (position.get(i).getValidityPeriod().getValidFrom().equals("19000101")) {
				eems.add(new EmployeeErrorMessage("职位岗位(S)",i+4,"有效期从字段必填"));
			}
			if (position.get(i).getValidityPeriod().getValidTo().equals("19000101")) {
				eems.add(new EmployeeErrorMessage("职位岗位(S)",i+4,"有效期到字段必填"));
			}
			if (StringUtils.isEmpty(position.get(i).getExternalId())) {
				eems.add(new EmployeeErrorMessage("职位岗位(S)",i+4,"岗位编号字段必填"));
			}else{
				//判断重复数据
				SimpleDateFormat from = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat to = new SimpleDateFormat("yyyyMMdd");
				String StringFrom = from.format(position.get(i).getValidityPeriod().getValidFrom().getTime());
				String StringTo = to.format(position.get(i).getValidityPeriod().getValidTo().getTime());
				duplicate = StringFrom + StringTo + position.get(i).getExternalId();
				if (DuplicateCheck.contains(duplicate)) {
					eems.add(new EmployeeErrorMessage("职位岗位(S)",i+4,"数据重复"));
				}else{
					DuplicateCheck.add(duplicate);
				}
			}
			if (position.get(i).getValidityPeriod().getValidFrom().after(position.get(i).getValidityPeriod().getValidTo())) {
				eems.add(new EmployeeErrorMessage("职位岗位(S)",i+4,"有效期从字段大于有效期到字段"));
			}
			if (StringUtils.isEmpty(position.get(i).getName())) {
				eems.add(new EmployeeErrorMessage("职位岗位(S)",i+4,"岗位名称字段必填"));
			}
		}
		return eems;
	}
	private List<EmployeeErrorMessage> dependenceCheckPosition(List<Position> position) {
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		return eems;
	}
	//检查职务JOB(C)
	private List<EmployeeErrorMessage> commonCheckJob(List<Job> job) {
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		List<String> DuplicateCheck = new ArrayList<>();
		String duplicate;
		for (int i = 0; i < job.size(); i++) {
			if (job.get(i).getValidityPeriod().getValidFrom().equals("19000101")) {
				eems.add(new EmployeeErrorMessage("职务JOB(C)",i+4,"开始日期字段必填"));
			}
			if (job.get(i).getValidityPeriod().getValidTo().equals("19000101")) {
				eems.add(new EmployeeErrorMessage("职务JOB(C)",i+4,"结束日期字段必填"));
			}
			if (StringUtils.isEmpty(job.get(i).getExternalId())) {
				eems.add(new EmployeeErrorMessage("职务JOB(C)",i+4,"职务编号字段必填"));
			}else{
				//判断重复数据
				SimpleDateFormat from = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat to = new SimpleDateFormat("yyyyMMdd");
				String StringFrom = from.format(job.get(i).getValidityPeriod().getValidFrom().getTime());
				String StringTo = to.format(job.get(i).getValidityPeriod().getValidTo().getTime());
				duplicate = StringFrom + StringTo + job.get(i).getExternalId();
				if (DuplicateCheck.contains(duplicate)) {
					eems.add(new EmployeeErrorMessage("职务JOB(C)",i+4,"数据重复"));
				}else{
					DuplicateCheck.add(duplicate);
				}
			}
			if (job.get(i).getValidityPeriod().getValidFrom().after(job.get(i).getValidityPeriod().getValidTo())) {
				eems.add(new EmployeeErrorMessage("职务JOB(C)",i+4,"开始日期字段大于结束日期字段"));
			}
			if (StringUtils.isEmpty(job.get(i).getName())) {
				eems.add(new EmployeeErrorMessage("职务JOB(C)",i+4,"职务名称字段必填"));
			}
		}
		return eems;
	}
	private List<EmployeeErrorMessage> dependenceCheckJob(List<Job> job) {
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		return eems;
	}
	//检查人事子范围
	private List<EmployeeErrorMessage> commonCheckProductSalesCategory(List<ProductSalesCategory> productSalesCategory) {
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		List<String> DuplicateCheck = new ArrayList<>();
		String duplicate;
		for (int i = 0; i < productSalesCategory.size(); i++) {
			if (StringUtils.isEmpty(productSalesCategory.get(i).getExternalId())) {
				eems.add(new EmployeeErrorMessage("人事子范围",i+4,"人事子范围编码字段必填"));
			}else{
				//判断重复数据
				duplicate = productSalesCategory.get(i).getExternalId();
				if (DuplicateCheck.contains(duplicate)) {
					eems.add(new EmployeeErrorMessage("人事子范围",i+4,"数据重复"));
				}else{
					DuplicateCheck.add(duplicate);
				}
			}
			if (StringUtils.isEmpty(productSalesCategory.get(i).getName())) {
				eems.add(new EmployeeErrorMessage("人事子范围",i+4,"人事子范围描述字段必填"));
			}
		}
		return eems;
	}
	private List<EmployeeErrorMessage> dependenceCheckProductSalesCategory(List<ProductSalesCategory> productSalesCategory) {
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		return eems;
	}
	//检查员工主数据
	private List<EmployeeErrorMessage> commonCheckEmployee(List<Employee> employee) {
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		List<String> DuplicateCheck = new ArrayList<>();
		String duplicate;
		for (int i = 0; i < employee.size(); i++) {
			if (employee.get(i).getValidityPeriod().getValidFrom().equals("19000101")) {
				eems.add(new EmployeeErrorMessage("员工主数据(P)",i+4,"开始日期字段必填"));
			}
			if (employee.get(i).getValidityPeriod().getValidTo().equals("19000101")) {
				eems.add(new EmployeeErrorMessage("员工主数据(P)",i+4,"结束日期字段必填"));
			}
			if (StringUtils.isEmpty(employee.get(i).getExternalId())) {
				eems.add(new EmployeeErrorMessage("员工主数据(P)",i+4,"员工号字段必填"));
			}else{
				//判断重复数据
				SimpleDateFormat from = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat to = new SimpleDateFormat("yyyyMMdd");
				String StringFrom = from.format(employee.get(i).getValidityPeriod().getValidFrom().getTime());
				String StringTo = to.format(employee.get(i).getValidityPeriod().getValidTo().getTime());
				duplicate = StringFrom + StringTo + employee.get(i).getExternalId();
				if (DuplicateCheck.contains(duplicate)) {
					eems.add(new EmployeeErrorMessage("员工主数据(P)",i+4,"数据重复"));
				}else{
					DuplicateCheck.add(duplicate);
				}
			}
			if (employee.get(i).getValidityPeriod().getValidFrom().after(employee.get(i).getValidityPeriod().getValidTo())) {
				eems.add(new EmployeeErrorMessage("职务JOB(C)",i+4,"开始日期字段大于结束日期字段"));
			}
			if (StringUtils.isEmpty(employee.get(i).getName())) {
				eems.add(new EmployeeErrorMessage("员工主数据(P)",i+4,"员工姓名字段必填"));
			}
			if (StringUtils.isEmpty(employee.get(i).getPhone())) {
				eems.add(new EmployeeErrorMessage("员工主数据(P)",i+4,"电话号码字段必填"));
			}
			if (StringUtils.isEmpty(employee.get(i).getIdCardNO())) {
				eems.add(new EmployeeErrorMessage("员工主数据(P)",i+4,"身份证号码字段必填"));
			}
			if (employee.get(i).getProductSalesCategory().getExternalId().equals(null)) {
				eems.add(new EmployeeErrorMessage("员工主数据(P)",i+4,"人事子范围字段必填"));
			}
		}
		return eems;
	}
	private List<EmployeeErrorMessage> dependenceCheckEmployee(List<Employee> employee, List<ProductSalesCategory> productSalesCategory) {
		//所属人事子范围必须在人事子范围表中存在
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		List<String> productSalesCategoryExist = new ArrayList<>();
		String existFlag;
		for (int i = 0; i < employee.size(); i++) {
			if (!productSalesCategoryExist.contains(employee.get(i).getProductSalesCategory().getExternalId())) {
				existFlag = "";
				for (int j = 0; j < productSalesCategory.size(); j++) {
					if (employee.get(i).getProductSalesCategory().getExternalId().equals(productSalesCategory.get(j).getExternalId())) {
						productSalesCategoryExist.add(productSalesCategory.get(j).getExternalId());
						existFlag = "X";
						break;
					}
				}
				if (StringUtils.isEmpty(existFlag)) {
					eems.add(new EmployeeErrorMessage("员工主数据(P)",i+4,"所属人事子范围不在人事子范围表中"));
				}
			}
		}
		return eems;
	}
	//检查区域主数据
	private List<EmployeeErrorMessage> commonCheckRegion(List<Region> regions) {
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		List<String> DuplicateCheck = new ArrayList<>();
		String duplicate;
		for (int i = 0; i < regions.size(); i++) {
			switch (regions.get(i).getLevel()) {
			case 1:
				if (StringUtils.isEmpty(regions.get(i).getProvinceId())||StringUtils.isEmpty(regions.get(i).getProvinceName())) {
					eems.add(new EmployeeErrorMessage("区域主数据",i+4,"省(直辖市)编号和省(直辖市)名称字段必填"));
				}else if (!(StringUtils.isEmpty(regions.get(i).getCityId())||StringUtils.isEmpty(regions.get(i).getCityName())
					 || StringUtils.isEmpty(regions.get(i).getCountyId())||StringUtils.isEmpty(regions.get(i).getCountyName()))) {
					eems.add(new EmployeeErrorMessage("区域主数据",i+4,"区域级别为1，请只填写省(直辖市)编号和省(直辖市)名称字段"));
				} 
					
				break;
			case 2:
				if (StringUtils.isEmpty(regions.get(i).getProvinceId())||StringUtils.isEmpty(regions.get(i).getProvinceName())
				|| StringUtils.isEmpty(regions.get(i).getCityId())||StringUtils.isEmpty(regions.get(i).getCityName())) {
					eems.add(new EmployeeErrorMessage("区域主数据",i+4,"省(直辖市)编号、省(直辖市)名称、市编号、市名称字段必填"));
				}else if (!(StringUtils.isEmpty(regions.get(i).getCountyId())||StringUtils.isEmpty(regions.get(i).getCountyName()))) {
					eems.add(new EmployeeErrorMessage("区域主数据",i+4,"区域级别为2，请只填写省(直辖市)编号、省(直辖市)名称、市编号、市名称字段"));
				}
				break;
			case 3:
				if (StringUtils.isEmpty(regions.get(i).getProvinceId())||StringUtils.isEmpty(regions.get(i).getProvinceName())
				 || StringUtils.isEmpty(regions.get(i).getCityId())||StringUtils.isEmpty(regions.get(i).getCityName())
				 || StringUtils.isEmpty(regions.get(i).getCountyId())||StringUtils.isEmpty(regions.get(i).getCountyName())) {
					eems.add(new EmployeeErrorMessage("区域主数据",i+4,"省(直辖市)编号、省(直辖市)名称、市编号、市名称、区县编号、区县名称字段必填"));
					}
				break;
			default :
				eems.add(new EmployeeErrorMessage("区域主数据",i+4,"区域级别字段必填或者输入不合法"));
				break;
			}
			if (regions.get(i).getIsValid().equals(null)) {
				eems.add(new EmployeeErrorMessage("区域主数据",i+4,"操作标识字段必填或者输入不合法"));
			}
			duplicate = regions.get(i).getProvinceId() + regions.get(i).getCityId() + regions.get(i).getCountyId();
			if (DuplicateCheck.contains(duplicate)) {
				eems.add(new EmployeeErrorMessage("区域主数据",i+4,"数据重复"));
			}else{
				DuplicateCheck.add(duplicate);
			}
		}
		return eems;
	}
	private List<EmployeeErrorMessage> dependenceCheckRegion(List<Region> region) {
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		for (int i = 0; i < region.size(); i++) {
//			region.get(i).setId(regionRepository.findAllWithCounty(region.get(i).getProvinceId(), region.get(i).getCityId(), region.get(i).getCountyId()).getId());
		}
		return eems;
	}
	//组织部门上下级关系
	private List<EmployeeErrorMessage> commonCheckDepartmentRelation(List<DepartmentRelation> departmentRelation) {
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		List<String> DuplicateCheck = new ArrayList<>();
		String duplicate;
		for (int i = 0; i < departmentRelation.size(); i++) {
			if (departmentRelation.get(i).getValidityPeriod().getValidFrom().equals("19000101")) {
				eems.add(new EmployeeErrorMessage("组织部门上下级关系",i+4,"开始日期字段必填"));
			}
			if (departmentRelation.get(i).getValidityPeriod().getValidTo().equals("19000101")) {
				eems.add(new EmployeeErrorMessage("组织部门上下级关系",i+4,"结束日期字段必填"));
			}
			if (StringUtils.isEmpty(departmentRelation.get(i).getDepartmentId())) {
				eems.add(new EmployeeErrorMessage("组织部门上下级关系",i+4,"组织部门编号字段必填"));
			}
			//判断重复数据
			SimpleDateFormat from = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat to = new SimpleDateFormat("yyyyMMdd");
			String StringFrom = from.format(departmentRelation.get(i).getValidityPeriod().getValidFrom().getTime());
			String StringTo = to.format(departmentRelation.get(i).getValidityPeriod().getValidTo().getTime());
			duplicate = StringFrom + StringTo + departmentRelation.get(i).getDepartmentId() + departmentRelation.get(i).getSuperiorDepartmentId();
			if (DuplicateCheck.contains(duplicate)) {
				eems.add(new EmployeeErrorMessage("组织部门上下级关系",i+4,"数据重复"));
			}else{
				DuplicateCheck.add(duplicate);
			}
			if (departmentRelation.get(i).getValidityPeriod().getValidFrom().after(departmentRelation.get(i).getValidityPeriod().getValidTo())) {
				eems.add(new EmployeeErrorMessage("组织部门上下级关系",i+4,"开始日期字段大于结束日期字段"));
			}
		}
		return eems;
	}
	private List<EmployeeErrorMessage> dependenceCheckDepartmentRelation(List<DepartmentRelation> departmentRelation, List<Department> department) {
		//当前部门ID必须在组织部门表中存在
		//上级部门ID若填写，必须在组织部门表中存在
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		String existFlag;
		for (int i = 0; i < departmentRelation.size(); i++) {
			if (!departmentExist.contains(departmentRelation.get(i).getDepartmentId())) {
				existFlag = "";
				for (int j = 0; j < department.size(); j++) {
					if (departmentRelation.get(i).getDepartmentId().equals(department.get(j).getExternalId())) {
						departmentExist.add(department.get(j).getExternalId());
						existFlag = "X";
						break;
					}
				}
				if (StringUtils.isEmpty(existFlag)) {
					eems.add(new EmployeeErrorMessage("员工主数据(P)",i+4,"所属人事子范围在人事子范围表中不存在"));
				}
			}
			if (!StringUtils.isEmpty(departmentRelation.get(i).getSuperiorDepartmentId())) {
				if (!departmentExist.contains(departmentRelation.get(i).getSuperiorDepartmentId())) {
					existFlag = "";
					for (int k = 0; k < department.size(); k++) {
						if (departmentRelation.get(i).getSuperiorDepartmentId().equals(department.get(k).getExternalId())) {
							departmentExist.add(department.get(k).getExternalId());
							existFlag = "X";
							break;
						}
					}
					if (StringUtils.isEmpty(existFlag)) {
						eems.add(new EmployeeErrorMessage("员工主数据(P)",i+4,"上级人事子范围在人事子范围表中不存在"));
					}
				}
			}
		}
		return eems;
	}
	//岗位和组织部门关联
	private List<EmployeeErrorMessage> commonCheckDepartmentPositionAssignment(
		List<DepartmentPositionAssignment> departmentPositionAssignment) {
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		List<String> DuplicateCheck = new ArrayList<>();
		String duplicate;
		for (int i = 0; i < departmentPositionAssignment.size(); i++) {
			if (departmentPositionAssignment.get(i).getValidityPeriod().getValidFrom().equals("19000101")) {
				eems.add(new EmployeeErrorMessage("岗位和组织部门关联",i+4,"开始日期字段必填"));
			}
			if (departmentPositionAssignment.get(i).getValidityPeriod().getValidTo().equals("19000101")) {
				eems.add(new EmployeeErrorMessage("岗位和组织部门关联",i+4,"结束日期字段必填"));
			}
			if (StringUtils.isEmpty(departmentPositionAssignment.get(i).getPositionExternalId())) {
				eems.add(new EmployeeErrorMessage("岗位和组织部门关联",i+4,"岗位编号字段必填"));
			}
			if (StringUtils.isEmpty(departmentPositionAssignment.get(i).getDepartmentExternalId())) {
				eems.add(new EmployeeErrorMessage("岗位和组织部门关联",i+4,"组织部门编号字段必填"));
			}
			//判断重复数据
			SimpleDateFormat from = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat to = new SimpleDateFormat("yyyyMMdd");
			String StringFrom = from.format(departmentPositionAssignment.get(i).getValidityPeriod().getValidFrom().getTime());
			String StringTo = to.format(departmentPositionAssignment.get(i).getValidityPeriod().getValidTo().getTime());
			duplicate = StringFrom + StringTo + departmentPositionAssignment.get(i).getDepartmentExternalId() + departmentPositionAssignment.get(i).getPositionExternalId();
			if (DuplicateCheck.contains(duplicate)) {
				eems.add(new EmployeeErrorMessage("岗位和组织部门关联",i+4,"数据重复"));
			}else{
				DuplicateCheck.add(duplicate);
			}
			if (departmentPositionAssignment.get(i).getValidityPeriod().getValidFrom().after(departmentPositionAssignment.get(i).getValidityPeriod().getValidTo())) {
				eems.add(new EmployeeErrorMessage("岗位和组织部门关联",i+4,"开始日期字段大于结束日期字段"));
			}
		}
		return eems;
	}
	private List<EmployeeErrorMessage> dependenceCheckDepartmentPositionAssignment(
		List<DepartmentPositionAssignment> departmentPositionAssignment, 
		List<Department> department, List<Position> position) {
		//部门ID必须存在于部门表中
		//岗位ID必须存在于岗位表中
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		String existFlag;
		for (int i = 0; i < departmentPositionAssignment.size(); i++) {
			if (!departmentExist.contains(departmentPositionAssignment.get(i).getDepartmentExternalId())) {
				existFlag = "";
				for (int j = 0; j < department.size(); j++) {
					if (departmentPositionAssignment.get(i).getDepartmentExternalId().equals(department.get(j).getExternalId())) {
						departmentExist.add(department.get(j).getExternalId());
						existFlag = "X";
						break;
					}
				}
				if (StringUtils.isEmpty(existFlag)) {
					eems.add(new EmployeeErrorMessage("岗位和组织部门关联",i+4,"组织部门编号在组织部门表中不存在"));
				}
			}
			if (!positionExist.contains(departmentPositionAssignment.get(i).getPositionExternalId())) {
				existFlag = "";
				for (int k = 0; k < position.size(); k++) {
					if (departmentPositionAssignment.get(i).getPositionExternalId().equals(position.get(k).getExternalId())) {
						positionExist.add(position.get(k).getExternalId());
						existFlag = "X";
						break;
					}
				}
				if (StringUtils.isEmpty(existFlag)) {
					eems.add(new EmployeeErrorMessage("岗位和组织部门关联",i+4,"岗位编号在岗位表中不存在"));
				}
			}
		}
		return eems;
	}
	//检查人员和岗位关联
	private List<EmployeeErrorMessage> commonCheckEmployeePositionAssignment(
		List<EmployeePositionAssignment> employeePositionAssignment) {
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		List<String> DuplicateCheck = new ArrayList<>();
		String duplicate;
		for (int i = 0; i < employeePositionAssignment.size(); i++) {
			if (employeePositionAssignment.get(i).getValidityPeriod().getValidFrom().equals("19000101")) {
				eems.add(new EmployeeErrorMessage("人员和岗位关联",i+4,"开始日期字段必填"));
			}
			if (employeePositionAssignment.get(i).getValidityPeriod().getValidTo().equals("19000101")) {
				eems.add(new EmployeeErrorMessage("人员和岗位关联",i+4,"结束日期字段必填"));
			}
			if (StringUtils.isEmpty(employeePositionAssignment.get(i).getEmployeeExternalId())) {
				eems.add(new EmployeeErrorMessage("人员和岗位关联",i+4,"员工号字段必填"));
			}
			if (StringUtils.isEmpty(employeePositionAssignment.get(i).getPositionExternalId())) {
				eems.add(new EmployeeErrorMessage("人员和岗位关联",i+4,"岗位编号字段必填"));
			}
			//判断重复数据
			SimpleDateFormat from = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat to = new SimpleDateFormat("yyyyMMdd");
			String StringFrom = from.format(employeePositionAssignment.get(i).getValidityPeriod().getValidFrom().getTime());
			String StringTo = to.format(employeePositionAssignment.get(i).getValidityPeriod().getValidTo().getTime());
			duplicate = StringFrom + StringTo + employeePositionAssignment.get(i).getEmployeeExternalId() + employeePositionAssignment.get(i).getPositionExternalId();
			if (DuplicateCheck.contains(duplicate)) {
				eems.add(new EmployeeErrorMessage("人员和岗位关联",i+4,"数据重复"));
			}else{
				DuplicateCheck.add(duplicate);
			}
			if (employeePositionAssignment.get(i).getValidityPeriod().getValidFrom().after(employeePositionAssignment.get(i).getValidityPeriod().getValidTo())) {
				eems.add(new EmployeeErrorMessage("人员和岗位关联",i+4,"开始日期字段大于结束日期字段"));
			}
		}
		return eems;
	}
	private List<EmployeeErrorMessage> dependenceCheckEmployeePositionAssignment(
		List<EmployeePositionAssignment> employeePositionAssignment,
		List<Employee> employee, List<Position> position) {
		//人员ID必须存在于人员表中
		//岗位ID必须存在于岗位表中
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		List<String> employeeExist = new ArrayList<>();
		String existFlag;
		for (int i = 0; i < employeePositionAssignment.size(); i++) {
			if (!employeeExist.contains(employeePositionAssignment.get(i).getEmployeeExternalId())) {
				existFlag = "";
				for (int j = 0; j < employee.size(); j++) {
					if (employeePositionAssignment.get(i).getEmployeeExternalId().equals(employee.get(j).getExternalId())) {
						employeeExist.add(employee.get(j).getExternalId());
						existFlag = "X";
						break;
					}
				}
				if (StringUtils.isEmpty(existFlag)) {
					eems.add(new EmployeeErrorMessage("人员和岗位关联",i+4,"员工号在员工主数据表中不存在"));
				}
			}
			if (!positionExist.contains(employeePositionAssignment.get(i).getPositionExternalId())) {
				existFlag = "";
				for (int k = 0; k < position.size(); k++) {
					if (employeePositionAssignment.get(i).getPositionExternalId().equals(position.get(k).getExternalId())) {
						positionExist.add(position.get(k).getExternalId());
						existFlag = "X";
						break;
					}
				}
				if (StringUtils.isEmpty(existFlag)) {
					eems.add(new EmployeeErrorMessage("人员和岗位关联",i+4,"岗位编号在岗位表中不存在"));
				}
			}
		}
		return eems;
	}
	//检查岗位-职务对应关系
	private List<EmployeeErrorMessage> commonCheckJobPositionAssignment(List<JobPositionAssignment> jobPositionAssignment) {
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		List<String> DuplicateCheck = new ArrayList<>();
		String duplicate;
		for (int i = 0; i < jobPositionAssignment.size(); i++) {
			if (jobPositionAssignment.get(i).getValidityPeriod().getValidFrom().equals("19000101")) {
				eems.add(new EmployeeErrorMessage("岗位-职务对应关系",i+4,"开始日期字段必填"));
			}
			if (jobPositionAssignment.get(i).getValidityPeriod().getValidTo().equals("19000101")) {
				eems.add(new EmployeeErrorMessage("岗位-职务对应关系",i+4,"结束日期字段必填"));
			}
			if (StringUtils.isEmpty(jobPositionAssignment.get(i).getPositionExternalId())) {
				eems.add(new EmployeeErrorMessage("岗位-职务对应关系",i+4,"岗位编号字段必填"));
			}
			if (StringUtils.isEmpty(jobPositionAssignment.get(i).getJobExternalId())) {
				eems.add(new EmployeeErrorMessage("岗位-职务对应关系",i+4,"职务编号字段必填"));
			}
			//判断重复数据
			SimpleDateFormat from = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat to = new SimpleDateFormat("yyyyMMdd");
			String StringFrom = from.format(jobPositionAssignment.get(i).getValidityPeriod().getValidFrom().getTime());
			String StringTo = to.format(jobPositionAssignment.get(i).getValidityPeriod().getValidTo().getTime());
			duplicate = StringFrom + StringTo + jobPositionAssignment.get(i).getPositionExternalId() + jobPositionAssignment.get(i).getJobExternalId();
			if (DuplicateCheck.contains(duplicate)) {
				eems.add(new EmployeeErrorMessage("岗位-职务对应关系",i+4,"数据重复"));
			}else{
				DuplicateCheck.add(duplicate);
			}
			if (jobPositionAssignment.get(i).getValidityPeriod().getValidFrom().after(jobPositionAssignment.get(i).getValidityPeriod().getValidTo())) {
				eems.add(new EmployeeErrorMessage("岗位-职务对应关系",i+4,"开始日期字段大于结束日期字段"));
			}
		}
		return eems;
	}
	private List<EmployeeErrorMessage> dependenceCheckJobPositionAssignment(List<JobPositionAssignment> jobPositionAssignment, 
		List<Job> job, List<Position> position) {
		//岗位ID必须存在于岗位表中
		//职务ID必须存在于职务表中
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		List<String> jobExist = new ArrayList<>();
		String existFlag;
		for (int i = 0; i < jobPositionAssignment.size(); i++) {
			if (!positionExist.contains(jobPositionAssignment.get(i).getPositionExternalId())) {
				existFlag = "";
				for (int k = 0; k < position.size(); k++) {
					if (jobPositionAssignment.get(i).getPositionExternalId().equals(position.get(k).getExternalId())) {
						positionExist.add(position.get(k).getExternalId());
						existFlag = "X";
						break;
					}
				}
				if (StringUtils.isEmpty(existFlag)) {
					eems.add(new EmployeeErrorMessage("岗位-职务对应关系",i+4,"岗位编号在岗位表中不存在"));
				}
			}
			if (!jobExist.contains(jobPositionAssignment.get(i).getJobExternalId())) {
				existFlag = "";
				for (int j = 0; j < job.size(); j++) {
					if (jobPositionAssignment.get(i).getJobExternalId().equals(job.get(j).getExternalId())) {
						jobExist.add(job.get(j).getExternalId());
						existFlag = "X";
						break;
					}
				}
				if (StringUtils.isEmpty(existFlag)) {
					eems.add(new EmployeeErrorMessage("岗位-职务对应关系",i+4,"职务编号在职务表中不存在"));
				}
			}
		}
		return eems;
	}
	//检查岗位-区域
	private List<EmployeeErrorMessage> commonCheckRegionPositionAssignment(List<RegionPositionAssignment> regionPositionAssignment) {
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		List<String> DuplicateCheck = new ArrayList<>();
		String duplicate;
		for (int i = 0; i < regionPositionAssignment.size(); i++) {
			if (regionPositionAssignment.get(i).getValidityPeriod().getValidFrom().equals("19000101")) {
				eems.add(new EmployeeErrorMessage("岗位-区域",i+4,"有效期从字段必填"));
			}
			if (regionPositionAssignment.get(i).getValidityPeriod().getValidTo().equals("19000101")) {
				eems.add(new EmployeeErrorMessage("岗位-区域",i+4,"有效期到字段必填"));
			}
			if (StringUtils.isEmpty(regionPositionAssignment.get(i).getPositionExternalId())) {
				eems.add(new EmployeeErrorMessage("岗位-区域",i+4,"岗位编号字段必填"));
			}
			if (StringUtils.isEmpty(regionPositionAssignment.get(i).getRegionId())) {
				eems.add(new EmployeeErrorMessage("岗位-区域",i+4,"区县编号字段必填"));
			}
			//判断重复数据
			SimpleDateFormat from = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat to = new SimpleDateFormat("yyyyMMdd");
			String StringFrom = from.format(regionPositionAssignment.get(i).getValidityPeriod().getValidFrom().getTime());
			String StringTo = to.format(regionPositionAssignment.get(i).getValidityPeriod().getValidTo().getTime());
			duplicate = StringFrom + StringTo + regionPositionAssignment.get(i).getPositionExternalId() + regionPositionAssignment.get(i).getRegionId();
			if (DuplicateCheck.contains(duplicate)) {
				eems.add(new EmployeeErrorMessage("岗位-区域",i+4,"数据重复"));
			}else{
				DuplicateCheck.add(duplicate);
			}
			if (regionPositionAssignment.get(i).getValidityPeriod().getValidFrom().after(regionPositionAssignment.get(i).getValidityPeriod().getValidTo())) {
				eems.add(new EmployeeErrorMessage("岗位-区域",i+4,"开始日期字段大于结束日期字段"));
			}
		}
		return eems;
	}
	private List<EmployeeErrorMessage> dependenceCheckRegionPositionAssignment(
		List<RegionPositionAssignment> regionPositionAssignment, List<Region> region, List<Position> position) {
		//岗位ID必须存在于岗位表中
		//区县ID必须存在去区域主数据表中，且层级为3
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		List<String> regionExist = new ArrayList<>();
		String existFlag;
		for (int i = 0; i < regionPositionAssignment.size(); i++) {
			if (!positionExist.contains(regionPositionAssignment.get(i).getPositionExternalId())) {
				existFlag = "";
				for (int k = 0; k < position.size(); k++) {
					if (regionPositionAssignment.get(i).getPositionExternalId().equals(position.get(k).getExternalId())) {
						positionExist.add(position.get(k).getExternalId());
						existFlag = "X";
						break;
					}
				}
				if (StringUtils.isEmpty(existFlag)) {
					eems.add(new EmployeeErrorMessage("岗位-区域",i+4,"岗位编号在岗位表中不存在"));
				}
			}
			if (!regionExist.contains(regionPositionAssignment.get(i).getRegionId())) {
				existFlag = "";
				for (int j = 0; j < region.size(); j++) {
					if (regionPositionAssignment.get(i).getRegionId().equals(region.get(j).getCountyId())) {
						if (!region.get(j).getLevel().equals(3)) {
							eems.add(new EmployeeErrorMessage("岗位-区域",i+4,"区县编号在区域主数据表中的区域级别不为3"));
						}
						regionExist.add(region.get(j).getCountyId());
						existFlag = "X";
						break;
					}
				}
				if (StringUtils.isEmpty(existFlag)) {
					eems.add(new EmployeeErrorMessage("岗位-区域",i+4,"区县编号在区域主数据表中不存在"));
				}
			}
		}
		return eems;
	}
	private List<EmployeeErrorMessage> saveToDB(List<Department> department, List<Position> position, List<Job> job, List<ProductSalesCategory> productSalesCategory,
		List<Employee> employee, List<Region> createRegions, List<Region> modifyRegions, List<Region> deleteRegions,
		List<DepartmentRelation> departmentRelation, List<DepartmentPositionAssignment> departmentPositionAssignment,
		List<EmployeePositionAssignment> employeePositionAssignment, List<JobPositionAssignment> jobPositionAssignment,
		List<RegionPositionAssignment> regionPositionAssignment) {
		List<EmployeeErrorMessage> eems = new ArrayList<>();
		//部门
		if (CollectionUtils.isNotEmpty(department)) {
			saveDepartment(department);
			eems.add(new EmployeeErrorMessage("组织结构(O)", 0 ,"数据导入完成"));
		}
		//岗位
		if (CollectionUtils.isNotEmpty(position)) {
			savePosition(position);
			eems.add(new EmployeeErrorMessage("职位岗位(S)", 0 ,"数据导入完成"));
		}
		//职务
		if (CollectionUtils.isNotEmpty(job)) {
			saveJob(job);
			eems.add(new EmployeeErrorMessage("职务JOB(C)", 0 ,"数据导入完成"));
		}
		//人事子范围
		if (CollectionUtils.isNotEmpty(productSalesCategory)) {
			saveProductSalesCategory(productSalesCategory);
			eems.add(new EmployeeErrorMessage("人事子范围", 0 ,"数据导入完成"));
		}
		//员工主数据
		if (CollectionUtils.isNotEmpty(employee)) {
			for (int i = 0; i < employee.size(); i++) {
				for (int j = 0; j < productSalesCategory.size(); j++) {
					if (employee.get(i).getProductSalesCategory().getExternalId().equals(productSalesCategory.get(j).getExternalId())) {
						employee.get(i).getProductSalesCategory().setId(productSalesCategory.get(j).getId());
						break;
					}
				}
			}
			saveEmployee(employee);
			eems.add(new EmployeeErrorMessage("员工主数据(P)", 0 ,"数据导入完成"));
		}
		//区域
		if (CollectionUtils.isNotEmpty(createRegions)) {
			saveRegion(createRegions);
			eems.add(new EmployeeErrorMessage("区域主数据", 0 ,"数据导入完成"));
		}
		if (CollectionUtils.isNotEmpty(modifyRegions)) {
			modifyRegion(modifyRegions);
		}
		if (CollectionUtils.isNotEmpty(deleteRegions)) {
			deleteRegion(deleteRegions);
		}
		//部门关系表 
		if (CollectionUtils.isNotEmpty(departmentRelation)) {
			saveDepartmentRelation(departmentRelation);
			eems.add(new EmployeeErrorMessage("组织部门上下级关系", 0 ,"数据导入完成"));
		}
		//岗位与部门关系表 
		if (CollectionUtils.isNotEmpty(departmentPositionAssignment)) {
			saveDepartmentPositionAssignment(departmentPositionAssignment);
			eems.add(new EmployeeErrorMessage("岗位和组织部门关联", 0 ,"数据导入完成"));
		}
		//人员和岗位关系表 
		if (CollectionUtils.isNotEmpty(employeePositionAssignment)) {
			saveEmployeePositionAssignment(employeePositionAssignment);
			eems.add(new EmployeeErrorMessage("人员和岗位关联", 0 ,"数据导入完成"));
		}
		//岗位和职务关系表 
		if (CollectionUtils.isNotEmpty(jobPositionAssignment)) {
			saveJobPositionAssignment(jobPositionAssignment);
			eems.add(new EmployeeErrorMessage("岗位-职务对应关系", 0 ,"数据导入完成"));
		}
		//岗位-区域
		if (CollectionUtils.isNotEmpty(regionPositionAssignment)) {
			saveRegionPositionAssignment(regionPositionAssignment);
			eems.add(new EmployeeErrorMessage("岗位-区域", 0 ,"数据导入完成"));
		}
		return eems;
	}
	private void saveDepartment(List<Department> department) {
		departmentRepository.save(department); 
	}
	private void savePosition(List<Position> position) {
		positionRepository.save(position);
	}
	private void saveJob(List<Job> job) {
		jobRepository.save(job);
	}
	private void saveProductSalesCategory(List<ProductSalesCategory> productSalesCategory) {
		productSalesCategoryRepository.save(productSalesCategory);
	}
	private void saveEmployee(List<Employee> employee) {
		employeeRepository.save(employee);
	}
	private void saveRegion(List<Region> region) {
		regionRepository.save(region);
	}
	private void modifyRegion(List<Region> region) {
		regionRepository.save(region);
	}
	private void deleteRegion(List<Region> region) {
		regionRepository.save(region);
	}
	private void saveDepartmentRelation(List<DepartmentRelation> departmentRelation) {
		departmentRelationRepository.save(departmentRelation);
	}
	private void saveDepartmentPositionAssignment(List<DepartmentPositionAssignment> departmentPositionAssignment) {
		departmentPositionAssignmentRepository.save(departmentPositionAssignment);
	}
	private void saveEmployeePositionAssignment(List<EmployeePositionAssignment> employeePositionAssignment) {
		employeePositionAssignmentRepository.save(employeePositionAssignment);
	}
	private void saveJobPositionAssignment(List<JobPositionAssignment> jobPositionAssignment) {
		jobPositionAssignmentRepository.save(jobPositionAssignment);
	}
	private void saveRegionPositionAssignment(List<RegionPositionAssignment> regionPositionAssignment) {
		regionPositionAssignmentRepository.save(regionPositionAssignment);
	}
}
//	@Override
//	@Transactional(readOnly = true)
//	public void export(List<Employee> list) {
//		CSVUtils.exportCsv(new File("D:/test/ljq.csv"), list);

//		String[] jxsExcelHeader = { "经销商ID", "状态", "经销商名称", "平台公司", "是否为主经销商", "主经销商ID", "办事处", "城市经理岗位" };
//		int[] excelHeaderWidth = { 300, 280, 260, 260, 260, 260, 260, 360 };
//		HSSFWorkbook wb = new HSSFWorkbook();
//		HSSFSheet sheet = wb.createSheet("经销商报表");
//		HSSFRow row = sheet.createRow((int) 0);
//
//		HSSFCellStyle style = wb.createCellStyle();
//		// 设置水平居中
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		// 设置垂直居中
//		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		// 设置列宽度（像素）
//		for (int i = 0; i < jxsExcelHeader.length; i++) {
//			sheet.setColumnWidth(i, 20 * 256);
//		}
//
//		// 创建Excel头部
//		for (int i = 0; i < jxsExcelHeader.length; i++) {
//			HSSFCell cell = row.createCell(i);
//			cell.setCellValue(jxsExcelHeader[i]);
//			cell.setCellStyle(style);
//			// 自动调整列宽度
//			// sheet.autoSizeColumn(i);
//		}
//
//		// 遍历数据，创建单元格
//		for (int rowIndex = 0; rowIndex < list.size(); rowIndex++) {
//			row = sheet.createRow(rowIndex + 1);
//			Dealer d = list.get(rowIndex);
//			row.createCell(0).setCellValue(String.valueOf(d.getId()));
//			row.createCell(1).setCellValue(String.valueOf(d.getId()));
//			row.createCell(2).setCellValue(d.getName());
//			row.createCell(3).setCellValue(String.valueOf(d.getId()));
//			row.createCell(4).setCellValue(String.valueOf(d.getId()));
//			row.createCell(5).setCellValue(String.valueOf(d.getId()));
//			row.createCell(6).setCellValue(String.valueOf(d.getId()));
//			row.createCell(7).setCellValue(String.valueOf(d.getId()));
//
//		}
//		return wb;
//	}
//}
