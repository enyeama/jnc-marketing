package com.sap.jnc.marketing.service.migration.rule;

import java.util.LinkedList;
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
import com.sap.jnc.marketing.service.migration.validate.RuleValidator;

/**
 * @author I322359
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceConfigTest2.class, PersistenceConfig.class })
public class RuleValidatorTest extends CommonPrint {

	@Autowired
	private RuleValidator ruleValidator;

	@Test
	public void testValidate() {
		System.out.println("=============================================================================");
		ProductRow row = new ProductRow();
		row.setOperation("C");
		row.setId("1001000002");
		row.setName("38°水晶剑南春[500×1×6]");
		row.setProductGroup("1001");
		row.setProductType("ZAFR");

		List<ProductRow> rows = new LinkedList<ProductRow>();
		rows.add(row);

		List<ProductRow> errorRows = ruleValidator.validate(rows, ProductRow.class);
		Assert.assertEquals(errorRows.size(), 1);
		Assert.assertEquals(errorRows.get(0).getErrorMsg().contains("记录已存在"), true);
		printList(errorRows);
	}
	
}
