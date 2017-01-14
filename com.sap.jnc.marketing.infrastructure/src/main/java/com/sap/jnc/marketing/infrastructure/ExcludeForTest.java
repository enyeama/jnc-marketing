package com.sap.jnc.marketing.infrastructure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for Spring components that rely on outside factors that cannot be recreated for unit tests. When an implementing class has this
 * annotation, it is excluded for unit tests. Thus, an alternative (mocked) implementation has to be provided.
 * 
 * @author I071053 Diouf Du
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcludeForTest {
}