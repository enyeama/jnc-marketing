package com.sap.jnc.marketing.service.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.BeanUtils;

/**
 * @author I322359
 */
public class JNCBeanUtils {

	public static Object getProperty(Object obj, String prop) {
		try {
			if(obj == null) {
				return null;
			}
			PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(obj.getClass(), prop);
			if(descriptor==null){
				return null;
			}
			Method getter = descriptor.getReadMethod();
			if(getter == null){
				return null;
			}
			return getter.invoke(obj);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void setProperty(Object obj, String prop, Object value) {
		try {
			PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(obj.getClass(), prop);
			if(descriptor == null){
				return;
			}
			Method setter = descriptor.getWriteMethod();
			if(setter == null){
				return;
			}
			Class<?> type = setter.getParameterTypes()[0];

			if (type.isInstance(value)) {
				setter.invoke(obj, value);
			}
			else {
				setter.invoke(obj, ConvertUtils.convert(value, type));
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
