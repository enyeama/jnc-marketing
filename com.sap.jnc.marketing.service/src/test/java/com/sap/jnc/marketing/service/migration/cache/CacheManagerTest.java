package com.sap.jnc.marketing.service.migration.cache;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.repository.impl.ProductTypeRepositoryImpl;
import com.sap.jnc.marketing.service.CommonPrint;
import com.sap.jnc.marketing.service.config.ServiceConfigTest2;
import com.sap.jnc.marketing.service.config.migration.UploadConf;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.config.migration.product.ProductDmsCategoryRow;
import com.sap.jnc.marketing.service.config.migration.product.ProductErpCategoryRow;
import com.sap.jnc.marketing.service.config.migration.product.ProductTypeRow;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceConfigTest2.class, PersistenceConfig.class })
public class CacheManagerTest extends CommonPrint {

	@Test
	public void loadData() {
		Set<String> keys = new HashSet<String>();
		keys.add("externalId");
		DataCache cache = new DataCache(ProductTypeRepositoryImpl.class, keys);
		cache.load();
		printList(cache.getList());
	}

	@Test
	public void loadUploadConf() {
		CacheManager cacheManager = CacheManager.getInstance();
		UploadConf conf = cacheManager.getUploadConf(ProductTypeRow.class);
		printObject(conf);
	}

	@Test
	public void loadErpCategoryCache() {
		CacheManager cacheManager = CacheManager.getInstance();
		cacheManager.getCacheByRowConf(ProductErpCategoryRow.class);
	}

	@Test
	public void revertRows() {
		CacheManager cacheManager = CacheManager.getInstance();
		UploadConf conf = cacheManager.getUploadConf(ProductDmsCategoryRow.class);
		Map<BusinessKey, ? extends UploadRow> map = cacheManager.revertRows(conf);
		Assert.assertNotNull(map);
		Assert.assertNotEquals(0, map.size());
		printList(map.values());
	}
}
