package com.github.lhervier.domino.spring.servlet;

import java.util.List;

import lotus.domino.Database;
import lotus.domino.Session;

public interface NotesContext {

	/**
	 * Return the user session
	 * @return the user session
	 */
	public Session getUserSession();
	
	/**
	 * Return the user database
	 * @return the user database
	 */
	public Database getUserDatabase();
	
	/**
	 * Return the server session
	 * @return the server session
	 */
	public Session getServerSession();
	
	/**
	 * Return the server database
	 */
	public Database getServerDatabase();
	
	/**
	 * Return the user roles
	 * @return the user roles
	 */
	public List<String> getUserRoles();
}
