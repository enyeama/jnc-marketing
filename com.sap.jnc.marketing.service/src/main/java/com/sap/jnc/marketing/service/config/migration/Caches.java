package com.sap.jnc.marketing.service.config.migration;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SuppressWarnings("all")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Caches {

	Cacheable[] list() default {};
}
