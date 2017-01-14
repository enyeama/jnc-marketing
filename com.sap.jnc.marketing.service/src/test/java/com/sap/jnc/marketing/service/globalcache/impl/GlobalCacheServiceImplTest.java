package com.sap.jnc.marketing.service.globalcache.impl;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.service.config.ServiceConfigTest;
import com.sap.jnc.marketing.service.config.ServiceConfigTest2;
import com.sap.jnc.marketing.service.globalcache.GlobalCacheService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.Serializable;

/**
 * @author Alex
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceConfigTest.class })
@Ignore
public class GlobalCacheServiceImplTest {
	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private GlobalCacheService globalCacheService;
	private String keyFoo = "foo";
	private String keyBar = "bar";

	@Before
	public void setUp() {
		globalCacheService.delKey(keyFoo);
		globalCacheService.delKey(keyBar);
		Assert.assertFalse(redisTemplate.hasKey(keyFoo));
		Assert.assertFalse(redisTemplate.hasKey(keyBar));
	}

	@Test
	public void testSave() throws Exception {
		A a = new B();
		System.out.println(a);
		globalCacheService.save(keyFoo, a, 10);
		Object foo = globalCacheService.get(keyFoo);
		System.out.println(foo);
	}

	@Test
	public void testSaveString() {
		String test = "test123中文";
		globalCacheService.save(keyBar,test);
		Object bar = globalCacheService.get(keyBar);
		Assert.assertEquals(bar,test);
		System.out.println(bar);
	}

	@Test
	public void testExpire() throws Exception {
		String test = "test123范德萨fdsfds";
		globalCacheService.save(keyFoo, test, 1);
		Object foo = globalCacheService.get(keyFoo);
		Assert.assertEquals(foo,test);
		Thread.sleep(2000);
		foo = globalCacheService.get(keyFoo);
		Assert.assertNotEquals(foo,test);
	}

	public static class A implements Serializable {

		long longProperty = System.currentTimeMillis();
		String stringProperty = "foo";

		public String getStringProperty() {
			return stringProperty;
		}

		public void setStringProperty(String stringProperty) {
			this.stringProperty = stringProperty;
		}

		@Override
		public String toString() {
			return "A [stringProperty=" + stringProperty + "]";
		}
	}

	public static class B extends A {

		int intProperty = 42;

		public int getIntProperty() {
			return intProperty;
		}

		public void setIntProperty(int intProperty) {
			this.intProperty = intProperty;
		}
	}
}