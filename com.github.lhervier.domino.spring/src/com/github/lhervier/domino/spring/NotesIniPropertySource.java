package com.github.lhervier.domino.spring;

import lotus.domino.NotesException;
import lotus.domino.NotesFactory;
import lotus.domino.NotesThread;
import lotus.domino.Session;

import org.springframework.core.env.PropertySource;

import com.ibm.domino.osgi.core.context.ContextInfo;

public class NotesIniPropertySource extends PropertySource<Object> {
	
	public NotesIniPropertySource() {
		super("notes-ini-property-source");
	}

	@Override
	public Object getProperty(String name) {
		Session session = ContextInfo.getUserSession();
		boolean term = false;
		try {
			if( session == null ) {
				NotesThread.sinitThread();
				session = NotesFactory.createSession();
				term = true;
			}
			String value = session.getEnvironmentString(name, true);
			if( value.length() == 0 )
				value = null;
			if( "EMPTY_STRING".equals(value) )
				return "";
			return value;
		} catch(NotesException e) {
			throw new RuntimeException(e);
		} finally {
			if( term ) {
				ContextInfo.termNotesContext();
				NotesThread.stermThread();
			}
		}
	}
}
