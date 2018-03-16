package com.github.lhervier.domino.spring.servlet;

import lotus.domino.NotesException;
import lotus.domino.Session;

import org.springframework.core.env.PropertySource;

public abstract class BaseNotesPropertySource extends PropertySource<Object> {
	
	/**
	 * Constructor
	 * @param name the property source name
	 */
	public BaseNotesPropertySource(String name) {
		super(name);
	}

	/**
	 * Initialisation
	 * @param sessionAsServer a session opened as the server
	 * @throws NotesException 
	 */
	public abstract void init(Session sessionAsServer) throws NotesException;
	
}
