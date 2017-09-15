package com.github.lhervier.domino.spring;

import java.util.List;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.github.lhervier.domino.spring.servlet.BaseNotesPropertySource;
import com.github.lhervier.domino.spring.util.OsgiUtils;

public class DominoApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext ac) {
		// Inject property sources from osgi extension
		List<BaseNotesPropertySource> propertySources = OsgiUtils.getExtensions("com.github.lhervier.domino.spring.propertysources", BaseNotesPropertySource.class);
		for( BaseNotesPropertySource propSrc : propertySources )
			ac.getEnvironment().getPropertySources().addLast(propSrc);
		ac.getEnvironment().getPropertySources().addLast(new NotesIniPropertySource());
	}
}
