package com.github.lhervier.domino.spring;

import java.util.List;

import lotus.domino.NotesException;
import lotus.domino.NotesFactory;
import lotus.domino.NotesThread;
import lotus.domino.Session;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.github.lhervier.domino.spring.servlet.BaseNotesPropertySource;
import com.github.lhervier.domino.spring.util.DominoUtils;
import com.github.lhervier.domino.spring.util.OsgiUtils;

public class DominoApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext ac) {
		Session session = null;
		try {
			// Opens a new session
			NotesThread.sinitThread();
			session = NotesFactory.createSession();
			
			// Inject property sources from osgi extension
			List<BaseNotesPropertySource> propertySources = OsgiUtils.getExtensions("com.github.lhervier.domino.spring.propertysources", BaseNotesPropertySource.class);
			for( BaseNotesPropertySource propSrc : propertySources ) {
				propSrc.init(session);
				ac.getEnvironment().getPropertySources().addLast(propSrc);
			}
			
			// Inject the Notes.ini property source
			NotesIniPropertySource iniPropSrc = new NotesIniPropertySource();
			iniPropSrc.init(session);
			ac.getEnvironment().getPropertySources().addLast(iniPropSrc);
		} catch (NotesException e) {
			throw new RuntimeException(e);
		} finally {
			DominoUtils.recycleQuietly(session);
			NotesThread.stermThread();
		}
	}
}
