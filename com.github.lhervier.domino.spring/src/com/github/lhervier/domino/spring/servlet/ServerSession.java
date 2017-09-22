package com.github.lhervier.domino.spring.servlet;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lotus.domino.AdministrationProcess;
import lotus.domino.AgentContext;
import lotus.domino.Base;
import lotus.domino.ColorObject;
import lotus.domino.Database;
import lotus.domino.DateRange;
import lotus.domino.DateTime;
import lotus.domino.DbDirectory;
import lotus.domino.Directory;
import lotus.domino.Document;
import lotus.domino.DocumentCollection;
import lotus.domino.DxlExporter;
import lotus.domino.DxlImporter;
import lotus.domino.IDVault;
import lotus.domino.International;
import lotus.domino.Log;
import lotus.domino.Name;
import lotus.domino.Newsletter;
import lotus.domino.NotesCalendar;
import lotus.domino.NotesException;
import lotus.domino.PropertyBroker;
import lotus.domino.Registration;
import lotus.domino.RichTextParagraphStyle;
import lotus.domino.RichTextStyle;
import lotus.domino.Session;
import lotus.domino.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.github.lhervier.domino.spring.util.DominoUtils;
import com.ibm.domino.napi.NException;
import com.ibm.domino.napi.c.NotesUtil;
import com.ibm.domino.napi.c.Os;
import com.ibm.domino.napi.c.xsp.XSPNative;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServerSession implements Session {

	/**
	 * The user session
	 */
	@Autowired
	private UserSession userSession;
	
	/**
	 * The server session
	 */
	private Session serverSession;
	
	/**
	 * The usernamelist
	 */
	private Long userNameList;
	
	public boolean isAvailable() {
		return this.userSession.isAvailable();
	}
	
	/**
	 * Bean initialization
	 */
	@PostConstruct
	public void init() {
		try {
			String server = this.userSession.getServerName();
			this.userNameList = NotesUtil.createUserNameList(server);
			Session serverSession = XSPNative.createXPageSession(server, this.userNameList, false, false);
			this.serverSession = serverSession;
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
	}
	
	// =================================================================
	
	public AdministrationProcess createAdministrationProcess(String arg0)
			throws NotesException {
		return serverSession.createAdministrationProcess(arg0);
	}

	public ColorObject createColorObject() throws NotesException {
		return serverSession.createColorObject();
	}

	public DateRange createDateRange() throws NotesException {
		return serverSession.createDateRange();
	}

	public DateRange createDateRange(Date arg0, Date arg1)
			throws NotesException {
		return serverSession.createDateRange(arg0, arg1);
	}

	public DateRange createDateRange(DateTime arg0, DateTime arg1)
			throws NotesException {
		return serverSession.createDateRange(arg0, arg1);
	}

	public DateTime createDateTime(Calendar arg0) throws NotesException {
		return serverSession.createDateTime(arg0);
	}

	public DateTime createDateTime(Date arg0) throws NotesException {
		return serverSession.createDateTime(arg0);
	}

	public DateTime createDateTime(String arg0) throws NotesException {
		return serverSession.createDateTime(arg0);
	}

	public DxlExporter createDxlExporter() throws NotesException {
		return serverSession.createDxlExporter();
	}

	public DxlImporter createDxlImporter() throws NotesException {
		return serverSession.createDxlImporter();
	}

	public Log createLog(String arg0) throws NotesException {
		return serverSession.createLog(arg0);
	}

	public Name createName(String arg0) throws NotesException {
		return serverSession.createName(arg0);
	}

	public Name createName(String arg0, String arg1) throws NotesException {
		return serverSession.createName(arg0, arg1);
	}

	public Newsletter createNewsletter(DocumentCollection arg0)
			throws NotesException {
		return serverSession.createNewsletter(arg0);
	}

	public Registration createRegistration() throws NotesException {
		return serverSession.createRegistration();
	}

	public RichTextParagraphStyle createRichTextParagraphStyle()
			throws NotesException {
		return serverSession.createRichTextParagraphStyle();
	}

	public RichTextStyle createRichTextStyle() throws NotesException {
		return serverSession.createRichTextStyle();
	}

	public Stream createStream() throws NotesException {
		return serverSession.createStream();
	}

	@SuppressWarnings("unchecked")
	public Vector evaluate(String arg0) throws NotesException {
		return serverSession.evaluate(arg0);
	}

	@SuppressWarnings("unchecked")
	public Vector evaluate(String arg0, Document arg1) throws NotesException {
		return serverSession.evaluate(arg0, arg1);
	}

	@SuppressWarnings("unchecked")
	public Vector freeResourceSearch(DateTime arg0, DateTime arg1, String arg2,
			int arg3, int arg4) throws NotesException {
		return serverSession.freeResourceSearch(arg0, arg1, arg2, arg3, arg4);
	}

	@SuppressWarnings("unchecked")
	public Vector freeResourceSearch(DateTime arg0, DateTime arg1, String arg2,
			int arg3, int arg4, String arg5, int arg6, String arg7,
			String arg8, int arg9) throws NotesException {
		return serverSession.freeResourceSearch(arg0, arg1, arg2, arg3, arg4, arg5,
				arg6, arg7, arg8, arg9);
	}

	@SuppressWarnings("unchecked")
	public Vector freeTimeSearch(DateRange arg0, int arg1, Object arg2,
			boolean arg3) throws NotesException {
		return serverSession.freeTimeSearch(arg0, arg1, arg2, arg3);
	}

	@SuppressWarnings("unchecked")
	public Vector getAddressBooks() throws NotesException {
		return serverSession.getAddressBooks();
	}

	public AgentContext getAgentContext() throws NotesException {
		return serverSession.getAgentContext();
	}

	public NotesCalendar getCalendar(Database arg0) throws NotesException {
		return serverSession.getCalendar(arg0);
	}

	public String getCommonUserName() throws NotesException {
		return serverSession.getCommonUserName();
	}

	public Object getCredentials() throws NotesException {
		return serverSession.getCredentials();
	}

	public Database getCurrentDatabase() throws NotesException {
		return serverSession.getCurrentDatabase();
	}

	public Database getDatabase(String arg0, String arg1) throws NotesException {
		return serverSession.getDatabase(arg0, arg1);
	}

	public Database getDatabase(String arg0, String arg1, boolean arg2)
			throws NotesException {
		return serverSession.getDatabase(arg0, arg1, arg2);
	}

	public DbDirectory getDbDirectory(String arg0) throws NotesException {
		return serverSession.getDbDirectory(arg0);
	}

	public Directory getDirectory() throws NotesException {
		return serverSession.getDirectory();
	}

	public Directory getDirectory(String arg0) throws NotesException {
		return serverSession.getDirectory(arg0);
	}

	public String getEffectiveUserName() throws NotesException {
		return serverSession.getEffectiveUserName();
	}

	public String getEnvironmentString(String arg0) throws NotesException {
		return serverSession.getEnvironmentString(arg0);
	}

	public String getEnvironmentString(String arg0, boolean arg1)
			throws NotesException {
		return serverSession.getEnvironmentString(arg0, arg1);
	}

	public Object getEnvironmentValue(String arg0) throws NotesException {
		return serverSession.getEnvironmentValue(arg0);
	}

	public Object getEnvironmentValue(String arg0, boolean arg1)
			throws NotesException {
		return serverSession.getEnvironmentValue(arg0, arg1);
	}

	public String getHttpURL() throws NotesException {
		return serverSession.getHttpURL();
	}

	public International getInternational() throws NotesException {
		return serverSession.getInternational();
	}

	public String getNotesVersion() throws NotesException {
		return serverSession.getNotesVersion();
	}

	public String getOrgDirectoryPath() throws NotesException {
		return serverSession.getOrgDirectoryPath();
	}

	public String getPlatform() throws NotesException {
		return serverSession.getPlatform();
	}

	public PropertyBroker getPropertyBroker() throws NotesException {
		return serverSession.getPropertyBroker();
	}

	public String getServerName() throws NotesException {
		return serverSession.getServerName();
	}

	public String getSessionToken() throws NotesException {
		return serverSession.getSessionToken();
	}

	public String getSessionToken(String arg0) throws NotesException {
		return serverSession.getSessionToken(arg0);
	}

	public String getURL() throws NotesException {
		return serverSession.getURL();
	}

	public Database getURLDatabase() throws NotesException {
		return serverSession.getURLDatabase();
	}

	@SuppressWarnings("unchecked")
	public Vector getUserGroupNameList() throws NotesException {
		return serverSession.getUserGroupNameList();
	}

	public String getUserName() throws NotesException {
		return serverSession.getUserName();
	}

	@SuppressWarnings("unchecked")
	public Vector getUserNameList() throws NotesException {
		return serverSession.getUserNameList();
	}

	public Name getUserNameObject() throws NotesException {
		return serverSession.getUserNameObject();
	}

	public Document getUserPolicySettings(String arg0, String arg1, int arg2)
			throws NotesException {
		return serverSession.getUserPolicySettings(arg0, arg1, arg2);
	}

	public Document getUserPolicySettings(String arg0, String arg1, int arg2,
			String arg3) throws NotesException {
		return serverSession.getUserPolicySettings(arg0, arg1, arg2, arg3);
	}

	public String hashPassword(String arg0) throws NotesException {
		return serverSession.hashPassword(arg0);
	}

	public boolean isConvertMIME() throws NotesException {
		return serverSession.isConvertMIME();
	}

	public boolean isConvertMime() throws NotesException {
		return serverSession.isConvertMime();
	}

	public boolean isOnServer() throws NotesException {
		return serverSession.isOnServer();
	}

	public boolean isRestricted() throws NotesException {
		return serverSession.isRestricted();
	}

	public boolean isTrackMillisecInJavaDates() throws NotesException {
		return serverSession.isTrackMillisecInJavaDates();
	}

	public boolean isTrustedSession() throws NotesException {
		return serverSession.isTrustedSession();
	}

	public boolean isValid() {
		return serverSession.isValid();
	}

	public void recycle() throws NotesException {
		serverSession.recycle();
	}

	@SuppressWarnings("unchecked")
	public void recycle(Vector arg0) throws NotesException {
		serverSession.recycle(arg0);
	}

	public boolean resetUserPassword(String arg0, String arg1, String arg2)
			throws NotesException {
		return serverSession.resetUserPassword(arg0, arg1, arg2);
	}

	public boolean resetUserPassword(String arg0, String arg1, String arg2,
			int arg3) throws NotesException {
		return serverSession.resetUserPassword(arg0, arg1, arg2, arg3);
	}

	public Base resolve(String arg0) throws NotesException {
		return serverSession.resolve(arg0);
	}

	public String sendConsoleCommand(String arg0, String arg1)
			throws NotesException {
		return serverSession.sendConsoleCommand(arg0, arg1);
	}

	public void setAllowLoopBack(boolean arg0) throws NotesException {
		serverSession.setAllowLoopBack(arg0);
	}

	public void setConvertMIME(boolean arg0) throws NotesException {
		serverSession.setConvertMIME(arg0);
	}

	public void setConvertMime(boolean arg0) throws NotesException {
		serverSession.setConvertMime(arg0);
	}

	public void setEnvironmentVar(String arg0, Object arg1)
			throws NotesException {
		serverSession.setEnvironmentVar(arg0, arg1);
	}

	public void setEnvironmentVar(String arg0, Object arg1, boolean arg2)
			throws NotesException {
		serverSession.setEnvironmentVar(arg0, arg1, arg2);
	}

	public void setTrackMillisecInJavaDates(boolean arg0) throws NotesException {
		serverSession.setTrackMillisecInJavaDates(arg0);
	}

	public boolean verifyPassword(String arg0, String arg1)
			throws NotesException {
		return serverSession.verifyPassword(arg0, arg1);
	}

	public boolean changePassword(String arg0, String arg1, String arg2)
			throws NotesException {
		return serverSession.changePassword(arg0, arg1, arg2);
	}

	public IDVault getIDVault() throws NotesException {
		return serverSession.getIDVault();
	}

	public IDVault getIDVault(String arg0) throws NotesException {
		return serverSession.getIDVault(arg0);
	}
}
