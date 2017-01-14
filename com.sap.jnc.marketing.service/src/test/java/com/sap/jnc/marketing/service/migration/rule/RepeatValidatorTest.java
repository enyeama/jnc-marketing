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
import com.sap.jnc.marketing.service.config.migration.product.ProductRow;
import com.sap.jnc.marketing.service.migration.validate.RepeatValidator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceConfigTest2.class, PersistenceConfig.class })
public class RepeatValidatorTest extends CommonPrint{

	@Autowired
	private RepeatValidator repeatValidator;

	@Test
	public void testValidate(){
		List<ProductRow> rows = new ArrayList<ProductRow>();
		ProductRow row1 = new ProductRow();
		row1.setOperation("C");
		row1.setId("1001000002");
		row1.setName("38°水晶剑南春[500×1×6]");
		row1.setProductGroup("1001");
		row1.setProductType("ZFAR");
		row1.setRowIndex(1);
		rows.add(row1);
		
		ProductRow row2 = new ProductRow();
		row2.setOperation("C");
		row2.setId("1001000002");
		row2.setName("38°水晶剑南春[500×1×6]");
		row2.setProductGroup("1001");
		row2.setProductType("ZFAR");
		row2.setRowIndex(2);
		rows.add(row2);
		
		repeatValidator.validate(rows, ProductRow.class);
		
		printList(rows);
		
		Assert.assertNotEquals(0, rows.get(0).getErrorMsg().size());
		Assert.assertEquals("1,2行重复", rows.get(0).getErrorMsg().get(0));
		
		Assert.assertNotEquals(0, rows.get(1).getErrorMsg().size());
		Assert.assertEquals("1,2行重复", rows.get(1).getErrorMsg().get(0));
	}
}
