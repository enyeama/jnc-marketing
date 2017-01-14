package com.sap.jnc.marketing.service.util;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.sap.jnc.marketing.persistence.model.Product;

@Component
public class JNCApplicationContextUtil implements ApplicationContextAware {

	public static ApplicationContext CONTEXT;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		JNCApplicationContextUtil.setContext(context);
	}

	public static void setContext(ApplicationContext context) {
		JNCApplicationContextUtil.CONTEXT = context;
	}

	public static <T> T getBean(Class<T> clazz) {
		Map<String, T> map = JNCApplicationContextUtil.getBeanOfType(clazz);
		if(map.size() == 0) throw new RuntimeException("没有 "+clazz.getName()+" 的Bean实例");
		return map.values().iterator().next();
	}
	
	public static <T> T getBean(String name, Class<T> clazz){
		return JNCApplicationContextUtil.CONTEXT.getBean(name, clazz); 
	}
	
	public static <T> Map<String, T> getBeanOfType(Class<T> clazz){
		return JNCApplicationContextUtil.CONTEXT.getBeansOfType(clazz);
	}

	public static void main(String[] args) {
		System.out.println(Product.class.getSimpleName());
	}
}
