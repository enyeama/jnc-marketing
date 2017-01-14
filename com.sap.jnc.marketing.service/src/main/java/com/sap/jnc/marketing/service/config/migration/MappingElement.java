package com.sap.jnc.marketing.service.config.migration;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MappingElement {

	/**
	 * 映射的Entity
	 * 
	 * @return
	 */
	Class<? extends Serializable> entity();

	/**
	 * 实体对应的Repository
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Class repository();

	/**
	 * Entity中参与映射的属性
	 * 
	 * @return
	 */
	String prop();
}
