package com.sap.jnc.marketing.service.config.migration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于定义映射
 * 
 * @author I322359
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {

	/**
	 * 映射中的主表
	 * 
	 * @return
	 */
	MappingElement major();

	/**
	 * 映射中的从表
	 * 
	 * @return
	 */
	MappingElement slave();
}
