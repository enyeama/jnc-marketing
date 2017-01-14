package com.sap.jnc.marketing.api.bonus.web.config;

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

	/*
	 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { System.out.println(
	 * "############start build security###########################################"); auth .inMemoryAuthentication()
	 * .withUser("user").password("password").roles("USER"); }
	 * @Override protected void configure(HttpSecurity http) throws Exception { System.out.println(
	 * "############start config security###########################################"); http .authorizeRequests() .anyRequest().authenticated() .and()
	 * .formLogin() .loginPage("/login") .permitAll(); }
	 * @Override public void configure(WebSecurity web) throws Exception { web .ignoring() .antMatchers("/resources
	 *//**
		 * "); }
		 */
}