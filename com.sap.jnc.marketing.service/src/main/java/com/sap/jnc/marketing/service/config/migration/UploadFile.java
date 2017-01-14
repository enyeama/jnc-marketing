package com.sap.jnc.marketing.service.config.migration;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.sap.jnc.marketing.service.migration.validate.rule.Rule;

@SuppressWarnings("all")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UploadFile {

	public enum FileType {
		EXCEL, CSV, TXT
	};

	/**
	 * Row对应的entity的JPA Repository, 用于CRUD, 非映射时必输
	 * 
	 * @return
	 */
	Class repository() default SimpleJpaRepository.class;

	/**
	 * Row对应的entity, 非映射时必输
	 * 
	 * @return
	 */
	Class<? extends Serializable> entity() default Serializable.class;

	/**
	 * Row的业务主键
	 * 
	 * @return
	 */
	String[] keys() default {};

	/**
	 * Row的上级主键，当存在上下级关系时，必输
	 * 
	 * @return
	 */
	String[] parentKeys() default {};

	/**
	 * 导入文件类型
	 * 
	 * @return
	 */
	FileType fileType() default FileType.EXCEL;

	/**
	 * Sheet Name, 如果是Excel是，需要sheet name
	 * 
	 * @return
	 */
	String sheetName();

	/**
	 * 表头行
	 * 
	 * @return
	 */
	int headerRowNum() default 1;

	/**
	 * 数据的起始行
	 * 
	 * @return
	 */
	int firstDataRow() default 3;

	/**
	 * 行级别的校验规则， 需要实现 Rule接口
	 * 
	 * @return
	 */
	Class<? extends Rule>[] rules() default {};
}
