package com.github.lhervier.domino.spring.sample.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class SampleService {

	@PostConstruct
	public void init() {
		System.out.println("service constructed");
	}
	
	public String getMessage() {
		return "Hello World !";
	}
}
