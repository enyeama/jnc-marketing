package com.sap.jnc.marketing.service.migration.rule;

import java.util.ArrayList;
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
import com.sap.jnc.marketing.service.config.migration.product.ProductDmsCategoryRow;
import com.sap.jnc.marketing.service.migration.validate.TreeValidator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceConfigTest2.class, PersistenceConfig.class })
public class TreeValidatorTest extends CommonPrint {

	@Autowired
	private TreeValidator treeValidator;
	
	@Test
	public void testTree(){
		ProductDmsCategoryRow row = new ProductDmsCategoryRow();
		row.setOperation("D");
		row.setId("11472");
		row.setName("2016");
		
		List<ProductDmsCategoryRow> list = new ArrayList<ProductDmsCategoryRow>();
		list.add(row);
		List<ProductDmsCategoryRow> errorRows = treeValidator.validate(list, ProductDmsCategoryRow.class);
		Assert.assertNotNull(errorRows);
		printList(errorRows);
	}
}
