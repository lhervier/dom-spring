package com.github.lhervier.domino.spring.servlet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import lotus.domino.Database;
import lotus.domino.NotesException;
import lotus.domino.Session;

import com.github.lhervier.domino.spring.util.DominoUtils;
import com.ibm.domino.napi.NException;
import com.ibm.domino.napi.c.NotesUtil;
import com.ibm.domino.napi.c.Os;
import com.ibm.domino.napi.c.xsp.XSPNative;
import com.ibm.domino.osgi.core.context.ContextInfo;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class NotesContextImpl implements NotesContext {

	/**
	 * The server session
	 */
	private Session serverSession;
	
	/**
	 * The usernamelist
	 */
	private Long userNameList;
	
	/**
	 * The server database
	 */
	private Database serverDatabase;
	
	/**
	 * The user roles
	 */
	private List<String> roles;
	
	/**
	 * Bean initialization
	 */
	@PostConstruct
	public void init() {
		try {
			String server = ContextInfo.getUserSession().getServerName();
			this.userNameList = NotesUtil.createUserNameList(server);
			Session serverSession = XSPNative.createXPageSession(server, this.userNameList, false, false);
			this.serverSession = serverSession;
			if( ContextInfo.getUserDatabase() != null ) {
				this.serverDatabase = DominoUtils.openDatabase(serverSession, ContextInfo.getUserDatabase().getFilePath());
			} else 
				this.serverDatabase = null;
			
			this.roles = new ArrayList<String>();
			if( ContextInfo.getUserDatabase() != null ) {
				Vector<?> roles = ContextInfo.getUserDatabase().queryAccessRoles(ContextInfo.getUserSession().getEffectiveUserName());
				for( Iterator<?> it = roles.iterator(); it.hasNext(); )
					this.roles.add((String) it.next());
			}
		} catch (NotesException e) {
			throw new RuntimeException(e);
		} catch (NException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Bean cleaup
	 */
	@PreDestroy
	public void cleanUp() {
		DominoUtils.recycleQuietly(serverSession);
		this.serverSession = null;
		try {
			Os.OSMemFree(this.userNameList);
		} catch (NException e) {
			throw new RuntimeException(e);
		}
		this.userNameList = null;
		if( this.serverDatabase != null ) {
			DominoUtils.recycleQuietly(serverDatabase);
			this.serverDatabase = null;
		}
	}
	
	/**
	 * Return the user session
	 * @return the user session
	 */
	public Session getUserSession() {
		return ContextInfo.getUserSession();
	}
	
	/**
	 * Return the user database
	 * @return the user database
	 */
	public Database getUserDatabase() {
		return ContextInfo.getUserDatabase();
	}
	
	/**
	 * Return the server session
	 * @return the server session
	 */
	public Session getServerSession() {
		return this.serverSession;
	}
	
	/**
	 * Return the server database
	 */
	public Database getServerDatabase() {
		return this.serverDatabase;
	}
	
	/**
	 * Return the user roles
	 * @return the user roles
	 */
	public List<String> getUserRoles() {
		return this.roles;
	}
}
