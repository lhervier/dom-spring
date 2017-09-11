package com.github.lhervier.domino.spring.servlet;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import freemarker.template.utility.XmlEscape;

@Configuration
@ComponentScan
@EnableWebMvc
public class SpringServletConfig extends WebMvcConfigurerAdapter {

	@Bean
	public FreeMarkerConfigurer freemarkerConfigurer() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setTemplateLoaderPath("/templates/");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("xml_escape", new XmlEscape());
		configurer.setFreemarkerVariables(map);
		return configurer;
	}

	@Bean
	public FreeMarkerViewResolver freeMarkerViewResolver() {
	    FreeMarkerViewResolver fvr = new FreeMarkerViewResolver();
	    fvr.setCache(true);
	    fvr.setPrefix("");
	    fvr.setSuffix(".ftl");
	    fvr.setRequestContextAttribute("rc");
	    return fvr;
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
