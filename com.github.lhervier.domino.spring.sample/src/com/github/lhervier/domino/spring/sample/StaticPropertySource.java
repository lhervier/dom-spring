package com.github.lhervier.domino.spring.sample;

import lotus.domino.NotesException;
import lotus.domino.Session;

import com.github.lhervier.domino.spring.servlet.BaseNotesPropertySource;

public class StaticPropertySource extends BaseNotesPropertySource {

	public StaticPropertySource() {
		super("static-property-source");
	}

	@Override
	public void init(Session sessionAsServer) throws NotesException {
		// Nothing to do...
	}

	@Override
	public Object getProperty(String name) {
		if( "spring.sample.test-property".equals(name) )
			return "property-value-from-osgi";
		return null;
	}

}
