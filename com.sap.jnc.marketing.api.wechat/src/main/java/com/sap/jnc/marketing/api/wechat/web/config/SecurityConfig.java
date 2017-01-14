package com.sap.jnc.marketing.api.wechat.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Alex
 */
@EnableWebSecurity
@Configuration
@ImportResource("classpath:META-INF/spring-security.xml")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

}