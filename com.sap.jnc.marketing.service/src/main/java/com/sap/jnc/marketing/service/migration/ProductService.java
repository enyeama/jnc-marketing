package com.sap.jnc.marketing.service.migration;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;

import com.sap.jnc.marketing.dto.response.product.ProductResponse;
import com.sap.jnc.marketing.persistence.criteria.product.GeneralSearchNode;
import com.sap.jnc.marketing.persistence.model.Channel;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory;
import com.sap.jnc.marketing.persistence.model.ProductErpCategory;
import com.sap.jnc.marketing.persistence.model.ProductGroup;
import com.sap.jnc.marketing.persistence.model.ProductType;
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

public interface ProductService {

	Page<ProductResponse> advanceSearch(GeneralSearchNode searchNode);

	public Map<String, List<? extends UploadRow>> imports(InputStream is) throws Exception;

	public List<ProductRow> importProduct(InputStream is, XSSFWorkbook workbook);

	public List<ProductTypeRow> importProductType(InputStream is, XSSFWorkbook workbook);

	public List<ProductGroupRow> importProductGroup(InputStream is, XSSFWorkbook workbook);

	public List<ProductDmsCategoryRow> importProductDmsCategory(InputStream is, XSSFWorkbook workbook);

	public List<ProductErpCategoryRow> importProductErpCategory(InputStream is, XSSFWorkbook workbook);

	public List<ChannelRow> importChannel(InputStream is, XSSFWorkbook workbook);

	public List<ProductChannelMappingRow> importProductChannelMapping(InputStream is, XSSFWorkbook workbook);

	public List<ProductErpCategoryMappingRow> importProductErpCategoryMapping(InputStream is, XSSFWorkbook workbook);

	public List<ProductDmsCategoryMappingRow> importProductDmsCategoryMapping(InputStream is, XSSFWorkbook workbook);

	public List<ProductType> getProductTypes();

	public List<ProductGroup> getProductGroups();

	public List<Channel> getChannels();

	public List<ProductDmsCategory> getProductDmsCategories();

	public List<ProductErpCategory> getProductErpCategories();
}
