package com.sap.jnc.marketing.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import com.sap.jnc.marketing.infrastructure.config.RedisConfig;
import com.sap.jnc.marketing.service.payment.impl.PaymentServiceImpl;
import com.sap.jnc.marketing.service.wechat.impl.WechatPaymentServiceImpl;
import com.sap.jnc.marketing.service.wechat.impl.WechatServiceImpl;

/**
 * @author Alex
 */
@Configuration
@ComponentScan(basePackages = ServiceConfig.PACKAGE_NAMESPACE, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {
	WechatPaymentServiceImpl.class, WechatServiceImpl.class, PaymentServiceImpl.class }), useDefaultFilters = false)
@Import(RedisConfig.class)
public class ServiceConfigTest {

}