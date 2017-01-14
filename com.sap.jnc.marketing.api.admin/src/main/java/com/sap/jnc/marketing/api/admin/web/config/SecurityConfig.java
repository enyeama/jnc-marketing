package com.sap.jnc.marketing.api.admin.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author I071053 Diouf Du
 */
@Configuration
@EnableWebSecurity
@ImportResource("classpath:META-INF/spring-security.xml")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

}