package com.sap.jnc.marketing.service.migration.rule;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.service.CommonPrint;
import com.sap.jnc.marketing.service.config.ServiceConfigTest2;
import com.sap.jnc.marketing.service.config.migration.UploadConf;
import com.sap.jnc.marketing.service.config.migration.product.ProductChannelMappingRow;
import com.sap.jnc.marketing.service.config.migration.product.ProductRow;
import com.sap.jnc.marketing.service.migration.cache.CacheManager;
import com.sap.jnc.marketing.service.migration.validate.rule.DependencyRule;
import com.sap.jnc.marketing.service.migration.validate.rule.MappingExistRule;
import com.sap.jnc.marketing.service.migration.validate.rule.NonBlankRule;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceConfigTest2.class, PersistenceConfig.class })
public class RuleTest extends CommonPrint {

	@Autowired
	private NonBlankRule nonBlankRule;

	@Autowired
	private DependencyRule dependencyRule;
	
	@Autowired
	private MappingExistRule mappingExistRule;

	@Test
	public void testNonBlankRule() {
		CacheManager cacheManager = CacheManager.getInstance();
		UploadConf conf = cacheManager.getUploadConf(ProductRow.class);
		ProductRow row = new ProductRow();
		row.setId("1001000001");
		row.setName("38°简方瓶剑南春[500×1×12]");
		row.setProductGroup("1001");
		row.setProductType("ZFAR");
		String msg = nonBlankRule.check(row, conf.getFieldsAnnotation().get("name"), conf);
		Assert.assertNull(msg);
		printObject(msg);

		row.setName("");
		msg = nonBlankRule.check(row, conf.getFieldsAnnotation().get("name"), conf);
		Assert.assertNotNull(msg);
		printObject(msg);

		row.setName("  ");
		msg = nonBlankRule.check(row, conf.getFieldsAnnotation().get("name"), conf);
		Assert.assertNotNull(msg);
		printObject(msg);

		row.setName("Type");
		msg = nonBlankRule.check(row, conf.getFieldsAnnotation().get("name"), conf);
		Assert.assertNull(msg);
		printObject(msg);
	}

	@Test
	public void testDependencyRule() {
		CacheManager cacheManager = CacheManager.getInstance();
		UploadConf conf = cacheManager.getUploadConf(ProductRow.class);
		ProductRow row = new ProductRow();
		row.setId("1001000001");
		row.setName("38°简方瓶剑南春[500×1×12]");
		row.setProductGroup("1001");
		row.setProductType("ZFAR");

		String msg = dependencyRule.check(row, conf.getFieldsAnnotation().get("productType"), conf);
		Assert.assertNotNull(msg);
		printObject(msg);

		row.setProductType("ZAFR");
		msg = dependencyRule.check(row, conf.getFieldsAnnotation().get("productType"), conf);
		Assert.assertNull(msg);
		printObject(msg);

		row.setProductGroup("");
		msg = dependencyRule.check(row, conf.getFieldsAnnotation().get("productGroup"), conf);
		Assert.assertNotNull(msg);
		printObject(msg);

		row.setProductGroup("");
		msg = dependencyRule.check(row, conf.getFieldsAnnotation().get("productGroup"), conf);
		Assert.assertNotNull(msg);
		printObject(msg);
	}

	@Test
	public void testMappingRule() {
		CacheManager cacheManager = CacheManager.getInstance();
		UploadConf conf = cacheManager.getUploadConf(ProductChannelMappingRow.class);
		ProductChannelMappingRow row = new ProductChannelMappingRow();
		row.setChannelId("11");
		row.setProductId("1001000005");
		
		String msg = mappingExistRule.check(row, null, conf);
		Assert.assertNotNull(msg);
		printObject(msg);
	}
}
