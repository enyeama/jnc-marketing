package com.sap.jnc.marketing.service.config.migration;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.sap.jnc.marketing.service.migration.validate.rule.Rule;

/**
 * 定义UploadRow中的相关字段的属性
 * 
 * @author I322359
 */
@SuppressWarnings("all")
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UploadField {

	/**
	 * UploadRow的属性
	 * 
	 * @return
	 */
	String prop();

	/**
	 * 文件的列表头
	 * 
	 * @return
	 */
	String header() default "";

	/**
	 * 对应UploadFile的Entity的字段
	 * 
	 * @return
	 */
	String field() default "";

	/**
	 * 当Row的属性是一个枚举类时, enumProp表示的是该枚举的一个属性，且必输
	 * 
	 * @return
	 */
	String enumProp() default "";

	/**
	 * 文件中对应的列
	 * 
	 * @return
	 */
	int column();

	/**
	 * 是否有依赖，当对象存在依赖关系时，必输且为true
	 * 
	 * @return
	 */
	boolean dependency() default false;

	/**
	 * 依赖键，当Row对A(prop1, prop2, ...)存在依赖关系时，该字段指向依赖对象A的一个属性(prop1/prop2/...)
	 * 一般是A的业务主键
	 * @return
	 */
	String dependencyKeys() default "";

	/**
	 * 依赖的Entity Class
	 * 
	 * @return
	 */
	Class<? extends Serializable> dependencyEntity() default Serializable.class;

	/**
	 * 单元格级别的校验规则类，须实现Rule接口
	 * 
	 * @return
	 */
	Class<? extends Rule>[] rules() default {};
}
