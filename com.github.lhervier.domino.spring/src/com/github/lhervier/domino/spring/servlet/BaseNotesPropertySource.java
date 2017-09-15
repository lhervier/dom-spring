package com.github.lhervier.domino.spring.servlet;

import lotus.domino.NotesException;
import lotus.domino.NotesFactory;
import lotus.domino.NotesThread;
import lotus.domino.Session;

import org.springframework.core.env.PropertySource;

import com.ibm.domino.osgi.core.context.ContextInfo;

public abstract class BaseNotesPropertySource extends PropertySource<Object> {
	
	/**
	 * Constructor
	 * @param name the property source name
	 */
	public BaseNotesPropertySource(String name) {
		super(name);
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
			return this.getProperty(session, name);
		} catch(NotesException e) {
			throw new RuntimeException(e);
		} finally {
			if( term ) {
				ContextInfo.termNotesContext();
				NotesThread.stermThread();
			}
		}
	}
	
	/**
	 * Return the value of a property
	 * @param session a notes session.
	 * @param name the property name
	 * @return the property value
	 */
	public abstract Object getProperty(Session session, String name) throws NotesException;
}
