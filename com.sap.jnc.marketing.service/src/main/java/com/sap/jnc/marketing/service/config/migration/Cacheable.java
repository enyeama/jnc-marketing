package com.sap.jnc.marketing.service.config.migration;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

@SuppressWarnings("all")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {

	/**
	 * 需要缓存的Entity
	 * 
	 * @return
	 */
	Class<? extends Serializable> entity();

	/**
	 * Entity对应的JPA Repository
	 * 
	 * @return
	 */
	Class repository();

	/**
	 * 缓存中该JPA Entity指定的业务主键
	 * 
	 * @return
	 */
	String[] keys();
}
