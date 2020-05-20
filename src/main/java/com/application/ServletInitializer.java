package com.application;

import com.application.authen.AuthorizationFilter;
import com.application.authen.LoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	@Autowired
	LoginFilter loginFilter;

	@Autowired
	AuthorizationFilter authorizationFilter;

	@Bean
	FilterRegistrationBean<LoginFilter> loginFilter() {
		FilterRegistrationBean<LoginFilter> frb = new FilterRegistrationBean<LoginFilter>();
		frb.setFilter(loginFilter);
		frb.setName("Login");
		frb.setUrlPatterns(Arrays.asList("/login"));
		return frb;
	}
	@Bean
	FilterRegistrationBean<AuthorizationFilter> authorizationFilter() {
		FilterRegistrationBean<AuthorizationFilter> frb = new FilterRegistrationBean<AuthorizationFilter>();
		frb.setFilter(authorizationFilter);
		frb.setName("auth");
		frb.setUrlPatterns(Arrays.asList("/*"));
		return frb;
	}
}
