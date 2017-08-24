package com.github.lhervier.domino.spring;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class DominoApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext ac) {
		ac.getEnvironment().getPropertySources().addLast(new NotesIniPropertySource());
	}
}
