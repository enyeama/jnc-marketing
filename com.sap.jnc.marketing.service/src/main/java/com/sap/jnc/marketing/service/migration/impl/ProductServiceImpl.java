package com.sap.jnc.marketing.service.migration.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.sap.jnc.marketing.dto.response.product.ProductResponse;
import com.sap.jnc.marketing.persistence.criteria.product.GeneralSearchNode;
import com.sap.jnc.marketing.persistence.model.Channel;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;
import com.sap.jnc.marketing.persistence.model.ProductErpCategory;
import com.sap.jnc.marketing.persistence.model.ProductGroup;
import com.sap.jnc.marketing.persistence.model.ProductType;
import com.sap.jnc.marketing.persistence.repository.ChannelRepository;
import com.sap.jnc.marketing.persistence.repository.ProductDmsCategoryRepository;
import com.sap.jnc.marketing.persistence.repository.ProductErpCategoryRepository;
import com.sap.jnc.marketing.persistence.repository.ProductGroupRepository;
import com.sap.jnc.marketing.persistence.repository.ProductRepository;
import com.sap.jnc.marketing.persistence.repository.ProductTypeRepository;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.config.migration.product.ChannelRow;
import com.sap.jnc.marketing.service.config.migration.product.ProductChannelMappingRow;
import com.sap.jnc.marketing.service.config.migration.product.ProductDmsCategoryMappingRow;
import com.sap.jnc.marketing.service.config.migration.product.ProductDmsCategoryRow;
import com.sap.jnc.marketing.service.config.migration.product.ProductErpCategoryMappingRow;
import com.sap.jnc.marketing.service.config.migration.product.ProductErpCategoryRow;
import com.sap.jnc.marketing.service.config.migration.product.ProductGroupRow;
import com.sap.jnc.marketing.service.config.migration.product.ProductRow;
import com.sap.jnc.marketing.service.config.migration.product.ProductTypeRow;
import com.sap.jnc.marketing.service.exception.migration.ValidationRecordException;
import com.sap.jnc.marketing.service.migration.ProductService;
import com.sap.jnc.marketing.service.migration.cache.CacheManager;
import com.sap.jnc.marketing.service.migration.parser.ExcelParser;
import com.sap.jnc.marketing.service.migration.persistence.UploadPersistence;
import com.sap.jnc.marketing.service.migration.validate.Validator;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	private static Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductTypeRepository ProductTypeRepository;
	@Autowired
	private ProductGroupRepository productGroupRepository;
	@Autowired
	private ChannelRepository channelRepository;
	@Autowired
	private ProductDmsCategoryRepository productDmsCategoryRepository;
	@Autowired
	private ProductErpCategoryRepository productErpCategoryRepository;

	@Autowired
	private ExcelParser excelParser;

	@Autowired
	private Validator validator;

	@Autowired
	private UploadPersistence persistence;

	@Override
	@Transactional(readOnly = true)
	public Page<ProductResponse> advanceSearch(GeneralSearchNode searchNode) {
		LOGGER.info("查询物料主数据，查询条件：" + JSONObject.toJSONString(searchNode));
		Page<Product> page = this.productRepository.advanceSearch(searchNode);
		return page.map(product -> new ProductResponse(product));
	}

	@Override
	@SuppressWarnings("all")
	@Transactional(rollbackFor={ValidationRecordException.class, Throwable.class})
	public Map<String, List<? extends UploadRow>> imports(InputStream is) {
		XSSFWorkbook workbook = null;
		try {
			long start = System.currentTimeMillis();
			LOGGER.info("导入开始");
			workbook = excelParser.getWorkbook(is);
			Map<String, List<? extends UploadRow>> map = new HashMap<String, List<? extends UploadRow>>();
			map.put("productGroup", importProductGroup(is, workbook));
			map.put("productType", importProductType(is, workbook));
			map.put("productDmsCategory", importProductDmsCategory(is, workbook));
			map.put("productErpCategory", importProductErpCategory(is, workbook));
			map.put("channel", importChannel(is, workbook));
			map.put("product", importProduct(is, workbook));
			map.put("productChannelMapping", importProductChannelMapping(is, workbook));
			map.put("productErpCategoryMapping", importProductErpCategoryMapping(is, workbook));
			map.put("productDmsCategoryMapping", importProductDmsCategoryMapping(is, workbook));
			long end = System.currentTimeMillis();
			LOGGER.info("导入完成, 共花费"+((end-start)/1000)+"s");
			for(List<? extends UploadRow> list : map.values()){
				if(list != null && list.size()>0){
					throw new ValidationRecordException("物料主数据导入检验有不符合规则的数据", map);
				}
			}
			return map;
		}
		catch(ValidationRecordException e){
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			CacheManager.clear();
			excelParser.close(is, workbook);
		}
	}

	@Override
	@Transactional(rollbackFor={ValidationRecordException.class, Throwable.class})
	public List<ProductRow> importProduct(InputStream is, XSSFWorkbook workbook) {
		boolean flag = (workbook == null) ? false : true;
		try {
			if (!flag) {
				workbook = excelParser.getWorkbook(is);
			}
			List<ProductRow> rows = excelParser.parse(workbook, ProductRow.class);
			List<ProductRow> errorRows = validator.validate(rows, ProductRow.class);
			if (errorRows.size() == 0) {
				persistence.persist(rows, ProductRow.class);
			}
			if(!flag && errorRows != null && errorRows.size() > 0){
				throw new ValidationRecordException("物料主数据导入检验有不符合规则的数据", "product", errorRows);
			}
			return errorRows;
		}
		catch(ValidationRecordException e){
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (!flag) {
				CacheManager.clear();
				excelParser.close(is, workbook);
			}
		}
	}

	@Override
	@Transactional(rollbackFor={ValidationRecordException.class, Throwable.class})
	public List<ProductTypeRow> importProductType(InputStream is, XSSFWorkbook workbook) {
		boolean flag = (workbook == null) ? false : true;
		try {
			if (!flag) {
				workbook = excelParser.getWorkbook(is);
			}
			List<ProductTypeRow> rows = excelParser.parse(workbook, ProductTypeRow.class);
			List<ProductTypeRow> errorRows = validator.validate(rows, ProductTypeRow.class);
			if (errorRows.size() == 0) {
				persistence.persist(rows, ProductTypeRow.class);
			}
			if(!flag && errorRows != null && errorRows.size() > 0){
				throw new ValidationRecordException("物料主数据导入检验有不符合规则的数据", "productType", errorRows);
			}
			return errorRows;
		}
		catch(ValidationRecordException e){
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (!flag) {
				CacheManager.clear();
				excelParser.close(is, workbook);
			}
		}
	}

	@Override
	@Transactional(rollbackFor={ValidationRecordException.class, Throwable.class})
	public List<ProductGroupRow> importProductGroup(InputStream is, XSSFWorkbook workbook) {
		boolean flag = (workbook == null) ? false : true;
		try {
			if (!flag) {
				workbook = excelParser.getWorkbook(is);
			}
			List<ProductGroupRow> rows = excelParser.parse(workbook, ProductGroupRow.class);
			List<ProductGroupRow> errorRows = validator.validate(rows, ProductGroupRow.class);
			if (errorRows.size() == 0) {
				persistence.persist(rows, ProductGroupRow.class);
			}
			if(!flag && errorRows != null && errorRows.size() > 0){
				throw new ValidationRecordException("物料主数据导入检验有不符合规则的数据", "productGroup", errorRows);
			}
			return errorRows;
		}
		catch(ValidationRecordException e){
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (!flag) {
				CacheManager.clear();
				excelParser.close(is, workbook);
			}
		}
	}

	@Override
	@Transactional(rollbackFor={ValidationRecordException.class, Throwable.class})
	public List<ProductDmsCategoryRow> importProductDmsCategory(InputStream is, XSSFWorkbook workbook) {
		boolean flag = (workbook == null) ? false : true;
		try {
			if (!flag) {
				workbook = excelParser.getWorkbook(is);
			}
			List<ProductDmsCategoryRow> rows = excelParser.parse(workbook, ProductDmsCategoryRow.class);
			List<ProductDmsCategoryRow> errorRows = validator.validate(rows, ProductDmsCategoryRow.class);
			if (errorRows.size() == 0) {
				persistence.persist(rows, ProductDmsCategoryRow.class);
			}
			if(!flag && errorRows != null && errorRows.size() > 0){
				throw new ValidationRecordException("物料主数据导入检验有不符合规则的数据", "productDmsCategory", errorRows);
			}
			return errorRows;
		}
		catch(ValidationRecordException e){
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (!flag) {
				CacheManager.clear();
				excelParser.close(is, workbook);
			}
		}
	}

	@Override
	@Transactional(rollbackFor={ValidationRecordException.class, Throwable.class})
	public List<ProductErpCategoryRow> importProductErpCategory(InputStream is, XSSFWorkbook workbook) {
		boolean flag = (workbook == null) ? false : true;
		try {
			if (!flag) {
				workbook = excelParser.getWorkbook(is);
			}
			List<ProductErpCategoryRow> rows = excelParser.parse(workbook, ProductErpCategoryRow.class);
			List<ProductErpCategoryRow> errorRows = validator.validate(rows, ProductErpCategoryRow.class);
			if (errorRows.size() == 0) {
				persistence.persist(rows, ProductErpCategoryRow.class);
			}
			if(!flag && errorRows != null && errorRows.size() > 0){
				throw new ValidationRecordException("物料主数据导入检验有不符合规则的数据", "productErpCategory", errorRows);
			}
			return errorRows;
		}
		catch(ValidationRecordException e){
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (!flag) {
				CacheManager.clear();
				excelParser.close(is, workbook);
			}
		}
	}

	@Override
	@Transactional(rollbackFor={ValidationRecordException.class, Throwable.class})
	public List<ChannelRow> importChannel(InputStream is, XSSFWorkbook workbook) {
		boolean flag = (workbook == null) ? false : true;
		try {
			if (!flag) {
				workbook = excelParser.getWorkbook(is);
			}
			List<ChannelRow> rows = excelParser.parse(workbook, ChannelRow.class);
			List<ChannelRow> errorRows = validator.validate(rows, ChannelRow.class);
			if (errorRows.size() == 0) {
				persistence.persist(rows, ChannelRow.class);
			}
			if(!flag && errorRows != null && errorRows.size() > 0){
				throw new ValidationRecordException("物料主数据导入检验有不符合规则的数据", "channel", errorRows);
			}
			return errorRows;
		}
		catch(ValidationRecordException e){
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (!flag) {
				CacheManager.clear();
				excelParser.close(is, workbook);
			}
		}
	}

	@Override
	@Transactional(rollbackFor={ValidationRecordException.class, Throwable.class})
	public List<ProductChannelMappingRow> importProductChannelMapping(InputStream is, XSSFWorkbook workbook) {
		boolean flag = (workbook == null) ? false : true;
		try {
			if (!flag) {
				workbook = excelParser.getWorkbook(is);
			}
			List<ProductChannelMappingRow> rows = excelParser.parse(workbook, ProductChannelMappingRow.class);
			List<ProductChannelMappingRow> errorRows = validator.validate(rows, ProductChannelMappingRow.class);
			if (errorRows.size() == 0) {
				persistence.persist(rows, ProductChannelMappingRow.class);
			}
			if(!flag && errorRows != null && errorRows.size() > 0){
				throw new ValidationRecordException("物料主数据导入检验有不符合规则的数据", "productChannelMapping", errorRows);
			}
			return errorRows;
		}
		catch(ValidationRecordException e){
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (!flag) {
				CacheManager.clear();
				excelParser.close(is, workbook);
			}
		}
	}

	@Override
	@Transactional(rollbackFor={ValidationRecordException.class, Throwable.class})
	public List<ProductErpCategoryMappingRow> importProductErpCategoryMapping(InputStream is, XSSFWorkbook workbook) {
		boolean flag = (workbook == null) ? false : true;
		try {
			if (!flag) {
				workbook = excelParser.getWorkbook(is);
			}
			List<ProductErpCategoryMappingRow> rows = excelParser.parse(workbook, ProductErpCategoryMappingRow.class);
			List<ProductErpCategoryMappingRow> errorRows = validator.validate(rows, ProductErpCategoryMappingRow.class);
			if (errorRows.size() == 0) {
				persistence.persist(rows, ProductErpCategoryMappingRow.class);
			}
			if(!flag && errorRows != null && errorRows.size() > 0){
				throw new ValidationRecordException("物料主数据导入检验有不符合规则的数据", "productErpCategoryMapping", errorRows);
			}
			return errorRows;
		}
		catch(ValidationRecordException e){
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (!flag) {
				CacheManager.clear();
				excelParser.close(is, workbook);
			}
		}
	}

	@Override
	@Transactional(rollbackFor={ValidationRecordException.class, Throwable.class})
	public List<ProductDmsCategoryMappingRow> importProductDmsCategoryMapping(InputStream is, XSSFWorkbook workbook) {
		boolean flag = (workbook == null) ? false : true;
		try {
			if (!flag) {
				workbook = excelParser.getWorkbook(is);
			}
			List<ProductDmsCategoryMappingRow> rows = excelParser.parse(workbook, ProductDmsCategoryMappingRow.class);
			List<ProductDmsCategoryMappingRow> errorRows = validator.validate(rows, ProductDmsCategoryMappingRow.class);
			if (errorRows.size() == 0) {
				persistence.persist(rows, ProductDmsCategoryMappingRow.class);
			}
			if(!flag && errorRows != null && errorRows.size() > 0){
				throw new ValidationRecordException("物料主数据导入检验有不符合规则的数据", "productDmsCategoryMapping", errorRows);
			}
			return errorRows;
		}
		catch(ValidationRecordException e){
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (!flag) {
				CacheManager.clear();
				excelParser.close(is, workbook);
			}
		}
	}

	@Override
	public List<ProductType> getProductTypes() {
		return ProductTypeRepository.findAll();
	}

	@Override
	public List<ProductGroup> getProductGroups() {
		return productGroupRepository.findAll();
	}

	@Override
	public List<Channel> getChannels() {
		return channelRepository.findAll();
	}

	@Override
	public List<ProductDmsCategory> getProductDmsCategories() {
		return productDmsCategoryRepository.findAll();
	}

	@Override
	public List<ProductErpCategory> getProductErpCategories() {
		return productErpCategoryRepository.findAll();
	}


}
