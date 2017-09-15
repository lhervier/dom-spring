package com.github.lhervier.domino.spring;

import com.github.lhervier.domino.spring.servlet.BaseNotesPropertySource;

import lotus.domino.NotesException;
import lotus.domino.Session;

public class NotesIniPropertySource extends BaseNotesPropertySource {
	
	public NotesIniPropertySource() {
		super("notes-ini-property-source");
	}

	@Override
	public Object getProperty(Session session, String name) throws NotesException {
		String value = session.getEnvironmentString(name, true);
		if( value.length() == 0 )
			value = null;
		if( "EMPTY_STRING".equals(value) )
			return "";
		return value;
	}
}
