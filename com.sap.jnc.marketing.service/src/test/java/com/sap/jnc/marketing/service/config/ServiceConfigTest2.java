package com.sap.jnc.marketing.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import com.sap.jnc.marketing.infrastructure.config.RedisConfig;
import com.sap.jnc.marketing.service.migration.cache.CacheManager;
import com.sap.jnc.marketing.service.migration.impl.ProductServiceImpl;
import com.sap.jnc.marketing.service.migration.parser.ExcelParser;
import com.sap.jnc.marketing.service.migration.persistence.UploadPersistence;
import com.sap.jnc.marketing.service.migration.validate.RepeatValidator;
import com.sap.jnc.marketing.service.migration.validate.RuleValidator;
import com.sap.jnc.marketing.service.migration.validate.TreeValidator;
import com.sap.jnc.marketing.service.migration.validate.Validator;
import com.sap.jnc.marketing.service.migration.validate.rule.DependencyRule;
import com.sap.jnc.marketing.service.migration.validate.rule.EntityExistRule;
import com.sap.jnc.marketing.service.migration.validate.rule.MappingExistRule;
import com.sap.jnc.marketing.service.migration.validate.rule.NonBlankRule;
import com.sap.jnc.marketing.service.util.JNCApplicationContextUtil;

/**
 * @author Alex
 */
@Configuration
@ComponentScan(basePackages = ServiceConfig.PACKAGE_NAMESPACE, includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {
	ProductServiceImpl.class, JNCApplicationContextUtil.class, ExcelParser.class, UploadPersistence.class, NonBlankRule.class,
	DependencyRule.class, RuleValidator.class,EntityExistRule.class, UploadPersistence.class, RepeatValidator.class, TreeValidator.class, Validator.class
	,MappingExistRule.class
//	CacheManager.class,JNCApplicationContextUtil.class, ExcelParser.class, UploadPersistence.class, NonBlankRule.class, CacheDependencyRule.class, Validator.class, ProductServiceImpl.class
	}), useDefaultFilters = false)
@Import(RedisConfig.class)
public class ServiceConfigTest2 {

}