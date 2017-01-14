package com.sap.jnc.marketing.persistence;

import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.config.PersistenceHanaTestConfig;

/**
 * Abstract superclass for other test classes to inherit from. This can be used to generated master data before each test methods.
 *
 * @author I071053 Diouf Du
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(classes = PersistenceHanaTestConfig.class))
@Transactional
@Rollback(true)
public abstract class PersistenceHanaDatabaseTest {
}
