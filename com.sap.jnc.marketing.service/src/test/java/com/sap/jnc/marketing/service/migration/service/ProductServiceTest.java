package com.sap.jnc.marketing.service.migration.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.service.CommonPrint;
import com.sap.jnc.marketing.service.config.ServiceConfigTest2;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.migration.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceConfigTest2.class, PersistenceConfig.class })
public class ProductServiceTest extends CommonPrint {

	@Autowired
	private ProductService productService;

	@Test
	public void testImports() throws Exception {
		long start = System.currentTimeMillis();

		InputStream is = new FileInputStream(new File("C:/Users/i322359/Documents/SAP/Projects/JNC/Development/testdata/MIT主数据模板-测试数据0705-03.xlsx"));
		productService.imports(is);
		long end = System.currentTimeMillis();
		System.out.println("总时间(s):" + (end - start) / 1000);
	}

	@Test
	public void importProductType() throws Exception {
		InputStream is = new FileInputStream(new File("C:/Users/i322359/Documents/SAP/Projects/JNC/Development/testdata/MIT主数据模板-测试数据0705-03.xlsx"));
		List<? extends UploadRow> list = productService.importProductType(is, null);
		printList(list);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void importProductGroup() throws Exception {
		InputStream is = new FileInputStream(new File("C:/Users/i322359/Documents/SAP/Projects/JNC/Development/testdata/MIT主数据模板-测试数据0705-03.xlsx"));
		List<? extends UploadRow> list = productService.importProductGroup(is, null);
		printList(list);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void importChannel() throws Exception {
		InputStream is = new FileInputStream(new File("C:/Users/i322359/Documents/SAP/Projects/JNC/Development/testdata/MIT主数据模板-测试数据0705-03.xlsx"));
		List<? extends UploadRow> list = productService.importChannel(is, null);
		printList(list);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void importProductErpCategory() throws Exception {
		InputStream is = new FileInputStream(new File("C:/Users/i322359/Documents/SAP/Projects/JNC/Development/testdata/MIT主数据模板-测试数据0705-03.xlsx"));
		List<? extends UploadRow> list = productService.importProductErpCategory(is, null);
		printList(list);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void importProductDmsCategory() throws Exception {
		InputStream is = new FileInputStream(new File("C:/Users/i322359/Documents/SAP/Projects/JNC/Development/testdata/MIT主数据模板-测试数据0705-03.xlsx"));
		List<? extends UploadRow> list = productService.importProductDmsCategory(is, null);
		printList(list);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void importProduct() throws Exception {
		InputStream is = new FileInputStream(new File("C:/Users/i322359/Documents/SAP/Projects/JNC/Development/testdata/MIT主数据模板-测试数据0705-03.xlsx"));
		List<? extends UploadRow> list = productService.importProduct(is, null);
		printList(list);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void importProductDmsCategoryMapping() throws Exception {
		InputStream is = new FileInputStream(new File("C:/Users/i322359/Documents/SAP/Projects/JNC/Development/testdata/MIT主数据模板-测试数据0705-03.xlsx"));
		List<? extends UploadRow> list = productService.importProductDmsCategoryMapping(is, null);
		printList(list);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void importProductErpCategoryMapping() throws Exception {
		InputStream is = new FileInputStream(new File("C:/Users/i322359/Documents/SAP/Projects/JNC/Development/testdata/MIT主数据模板-测试数据0705-03.xlsx"));
		List<? extends UploadRow> list = productService.importProductErpCategoryMapping(is, null);
		printList(list);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void importProductChannelMapping() throws Exception {
		InputStream is = new FileInputStream(new File("C:/Users/i322359/Documents/SAP/Projects/JNC/Development/testdata/MIT主数据模板-测试数据0705-03.xlsx"));
		List<? extends UploadRow> list = productService.importProductChannelMapping(is, null);
		printList(list);
		Assert.assertEquals(0, list.size());
	}
}
