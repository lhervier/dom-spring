package com.github.lhervier.domino.spring.servlet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.annotation.PostConstruct;

import lotus.domino.NotesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserRoles extends ArrayList<String> {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 6899949232743616165L;

	/**
	 * The user session
	 */
	@Autowired
	private UserSession userSession;
	
	/**
	 * The user database
	 */
	@Autowired
	private UserDatabase userDatabase;
	
	/**
	 * Initialisation
	 * @throws NotesException 
	 */
	@PostConstruct
	public void init() throws NotesException {
		if( this.userDatabase.isAvailable() ) {
			Vector<?> roles = this.userDatabase.queryAccessRoles(userSession.getEffectiveUserName());
			for( Iterator<?> it = roles.iterator(); it.hasNext(); )
				this.add((String) it.next());
		}
	}
}
