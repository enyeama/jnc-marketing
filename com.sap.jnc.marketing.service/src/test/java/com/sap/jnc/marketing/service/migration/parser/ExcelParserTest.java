package com.sap.jnc.marketing.service.migration.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.service.CommonPrint;
import com.sap.jnc.marketing.service.config.ServiceConfigTest2;
import com.sap.jnc.marketing.service.config.migration.product.ProductDmsCategoryRow;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceConfigTest2.class, PersistenceConfig.class })
public class ExcelParserTest extends CommonPrint {

	@Autowired
	private ExcelParser excelParser;

	@Test
	public void parse() throws Exception {
		InputStream is = new FileInputStream(new File("C:/Users/i322359/Documents/SAP/Projects/JNC/Development/testdata/物料主数据导入功能.xls"));
		XSSFWorkbook workbook = excelParser.getWorkbook(is);
//		List<ProductTypeRow> rows = excelParser.parse(workbook, ProductTypeRow.class);
//		Assert.assertNotNull(rows);
//		Assert.assertEquals(rows.size(), 4);
//		printList(rows);
//
//		List<ProductRow> rows1 = excelParser.parse(workbook, ProductRow.class);
//		Assert.assertNotNull(rows1);
//		Assert.assertEquals(rows1.size(), 2445);
//		printList(rows1);
//
//		List<ProductGroupRow> rows2 = excelParser.parse(workbook, ProductGroupRow.class);
//		Assert.assertNotNull(rows2);
//		Assert.assertEquals(rows2.size(), 87);
//		printList(rows1);

		List<ProductDmsCategoryRow> rows3 = excelParser.parse(workbook, ProductDmsCategoryRow.class);
		Assert.assertNotNull(rows3);
		Assert.assertEquals(rows3.size(), 96);
		printList(rows3);
		
		excelParser.close(is, workbook);
	}
	
}
