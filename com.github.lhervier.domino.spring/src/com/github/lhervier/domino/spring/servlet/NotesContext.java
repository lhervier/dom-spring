package com.github.lhervier.domino.spring.servlet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lotus.domino.Database;
import lotus.domino.NotesException;
import lotus.domino.Session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.github.lhervier.domino.spring.util.DominoUtils;
import com.ibm.domino.napi.NException;
import com.ibm.domino.napi.c.NotesUtil;
import com.ibm.domino.napi.c.Os;
import com.ibm.domino.napi.c.xsp.XSPNative;
import com.ibm.domino.osgi.core.context.ContextInfo;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class NotesContext {

	/**
	 * The server session
	 */
	private Session serverSession;
	
	/**
	 * The usernamelist pointer
	 */
	private Long userNameList;
	
	/**
	 * The server database
	 */
	private Database serverDatabase;
	
	/**
	 * The user roles
	 */
	private List<String> userRoles;
	
	/**
	 * Initialisation
	 */
	@PostConstruct
	public void init() {
		try {
			String server = ContextInfo.getUserSession().getServerName();
			this.userNameList = NotesUtil.createUserNameList(server);
			Session serverSession = XSPNative.createXPageSession(server, this.userNameList, false, false);
			this.serverSession = serverSession;
			
			Database db = ContextInfo.getUserDatabase();
			if( db != null ) {
				Database serverDatabase = DominoUtils.openDatabase(serverSession, db.getFilePath());
				this.serverDatabase = serverDatabase;
			} else 
				this.serverDatabase = null;
			
			this.userRoles = new ArrayList<String>();
			if( db != null ) {
				Vector<?> roles = db.queryAccessRoles(ContextInfo.getUserSession().getUserName());
				for( Iterator<?> it = roles.iterator(); it.hasNext(); )
					this.userRoles.add((String) it.next());
			}
		} catch (NotesException e) {
			throw new RuntimeException(e);
		} catch (NException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Shutdown the provider for the current thread
	 */
	@PreDestroy
	public void cleanUp() {
		if( this.serverSession != null ) {
			DominoUtils.recycleQuietly(serverSession);
			this.serverSession = null;
		}
		
		if( this.serverDatabase != null ) {
			DominoUtils.recycleQuietly(serverDatabase);
			this.serverDatabase = null;
		}
		
		if( this.userNameList != null ) {
			try {
				Os.OSMemFree(this.userNameList);
			} catch (NException e) {
				e.printStackTrace(System.err);
				throw new RuntimeException(e);
			}
			this.userNameList = null;
		}
	}
	
	/**
	 * Return the user session
	 */
	public Session getUserSession () {
		return ContextInfo.getUserSession();
	}
	
	/**
	 * Return the server session
	 */
	public Session getServerSession() {
		return this.serverSession;
	}
	
	/**
	 * Return the current database with the user rights
	 */
	public Database getUserDatabase() {
		return ContextInfo.getUserDatabase();
	}
	
	/**
	 * Return the current database opened with the server session
	 */
	public Database getServerDatabase() {
		return this.serverDatabase;
	}
	
	/**
	 * Return the current user roles
	 */
	public List<String> getUserRoles() {
		return this.userRoles;
	}
}
