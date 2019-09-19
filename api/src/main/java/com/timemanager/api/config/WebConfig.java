package com.timemanager.api.config;

import com.timemanager.core.common.WebInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
/**
 * Config an interceptors for received request from front.
 */
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    WebInterceptor webInterceptor;
    
    @Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(webInterceptor);
    }

}