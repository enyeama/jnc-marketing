/**
 * This Interface is used to validate the input parameter from restful API.
 * The validator is CheckOutboundValidityValidator which will check the class JNCOuntboundInfo for create and update
 * @author      James Jiang
 */
package com.sap.jnc.marketing.dto.validator.integration;
import javax.validation.Constraint;  
import javax.validation.Payload;  
import javax.validation.constraints.NotNull;  
import java.lang.annotation.Documented;  
import java.lang.annotation.Retention;  
import java.lang.annotation.Target;  
import static java.lang.annotation.ElementType.*;  
import static java.lang.annotation.RetentionPolicy.*;  

@Target({ TYPE, ANNOTATION_TYPE})  
@Retention(RUNTIME)  
@Constraint(validatedBy = CheckOutboundValidityValidator.class)  
@Documented  
public @interface CheckOutboundValidity {
	//默认错误消息  
    String message() default "";  
  
    //分组  
    Class<?>[] groups() default { };  
  
    //负载  
    Class<? extends Payload>[] payload() default { };  
  
    //指定多个时使用  
    @Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })  
    @Retention(RUNTIME)  
    @Documented  
    @interface List {  
    	CheckValidity[] value();  
    }  
}
