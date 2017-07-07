package com.github.lhervier.domino.spring.servlet;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan
@EnableWebMvc
public class SpringServletConfig {
	public SpringServletConfig() {
		System.out.println("servlet config");
	}
}
