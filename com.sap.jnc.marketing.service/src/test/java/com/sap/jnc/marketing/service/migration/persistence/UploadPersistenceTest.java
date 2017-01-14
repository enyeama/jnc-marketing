package com.sap.jnc.marketing.service.migration.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.service.CommonPrint;
import com.sap.jnc.marketing.service.config.ServiceConfigTest2;
import com.sap.jnc.marketing.service.config.migration.product.ProductRow;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceConfigTest2.class, PersistenceConfig.class })
public class UploadPersistenceTest extends CommonPrint {

	@Autowired
	private UploadPersistence uploadPersistence;

	@Test
	public void testPersistCreation() {
		ProductRow row = new ProductRow();
		row.setId("999990001");
		row.setProductType("ZAFR");
		row.setName("Test persist creation");
		row.setOperation("C");

		List rows = new ArrayList();
		rows.add(row);

		Map<String, List> map = uploadPersistence.persist(rows, ProductRow.class);
		Assert.assertEquals(map.get("C").size(), 1);
		Product product = (Product) map.get("C").get(0);
		Assert.assertNotNull(product.getProductType());
	}

	@Test
	public void testPersistModification() {
		ProductRow row = new ProductRow();
		row.setId("1001000002");
		row.setProductType("ZFFR");
		row.setName("Test persist creation");
		row.setOperation("U");

		List rows = new ArrayList();
		rows.add(row);

		Map<String, List> map = uploadPersistence.persist(rows, ProductRow.class);
		Assert.assertEquals(map.get("U").size(), 1);
		Product product = (Product) map.get("U").get(0);
		Assert.assertEquals(product.getName(), "Test persist creation");
		
		Assert.assertNotNull(product.getProductType());
		Assert.assertEquals(product.getProductType().getExternalId(), "ZFFR");
		
	}

	@Test
	public void testPersistDelete() {
		ProductRow row = new ProductRow();
		row.setId("1001000003");
		row.setOperation("D");

		List rows = new ArrayList();
		rows.add(row);

		Map<String, List> map = uploadPersistence.persist(rows, ProductRow.class);
		Assert.assertEquals(map.get("D").size(), 1);
		
		Product product = (Product) map.get("D").get(0);
		Assert.assertEquals(product.getName(), "38°水晶剑南春[250×1×12]");
	}
}
