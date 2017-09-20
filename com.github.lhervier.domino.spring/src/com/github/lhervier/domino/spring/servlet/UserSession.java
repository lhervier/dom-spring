package com.github.lhervier.domino.spring.servlet;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

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

import org.springframework.stereotype.Component;

import com.ibm.domino.osgi.core.context.ContextInfo;

@Component
public class UserSession implements Session {

	private Session getUserSession() {
		return ContextInfo.getUserSession();
	}
	
	public boolean isAvailable() {
		return this.getUserSession() != null;
	}
	
	// ==========================================================

	public AdministrationProcess createAdministrationProcess(String arg0)
			throws NotesException {
		return this.getUserSession().createAdministrationProcess(arg0);
	}

	public ColorObject createColorObject() throws NotesException {
		return this.getUserSession().createColorObject();
	}

	public DateRange createDateRange() throws NotesException {
		return this.getUserSession().createDateRange();
	}

	public DateRange createDateRange(Date arg0, Date arg1)
			throws NotesException {
		return this.getUserSession().createDateRange(arg0, arg1);
	}

	public DateRange createDateRange(DateTime arg0, DateTime arg1)
			throws NotesException {
		return this.getUserSession().createDateRange(arg0, arg1);
	}

	public DateTime createDateTime(Calendar arg0) throws NotesException {
		return this.getUserSession().createDateTime(arg0);
	}

	public DateTime createDateTime(Date arg0) throws NotesException {
		return this.getUserSession().createDateTime(arg0);
	}

	public DateTime createDateTime(String arg0) throws NotesException {
		return this.getUserSession().createDateTime(arg0);
	}

	public DxlExporter createDxlExporter() throws NotesException {
		return this.getUserSession().createDxlExporter();
	}

	public DxlImporter createDxlImporter() throws NotesException {
		return this.getUserSession().createDxlImporter();
	}

	public Log createLog(String arg0) throws NotesException {
		return this.getUserSession().createLog(arg0);
	}

	public Name createName(String arg0) throws NotesException {
		return this.getUserSession().createName(arg0);
	}

	public Name createName(String arg0, String arg1) throws NotesException {
		return this.getUserSession().createName(arg0, arg1);
	}

	public Newsletter createNewsletter(DocumentCollection arg0)
			throws NotesException {
		return this.getUserSession().createNewsletter(arg0);
	}

	public Registration createRegistration() throws NotesException {
		return this.getUserSession().createRegistration();
	}

	public RichTextParagraphStyle createRichTextParagraphStyle()
			throws NotesException {
		return this.getUserSession().createRichTextParagraphStyle();
	}

	public RichTextStyle createRichTextStyle() throws NotesException {
		return this.getUserSession().createRichTextStyle();
	}

	public Stream createStream() throws NotesException {
		return this.getUserSession().createStream();
	}

	@SuppressWarnings("unchecked")
	public Vector evaluate(String arg0) throws NotesException {
		return this.getUserSession().evaluate(arg0);
	}

	@SuppressWarnings("unchecked")
	public Vector evaluate(String arg0, Document arg1) throws NotesException {
		return this.getUserSession().evaluate(arg0, arg1);
	}

	@SuppressWarnings("unchecked")
	public Vector freeResourceSearch(DateTime arg0, DateTime arg1, String arg2,
			int arg3, int arg4) throws NotesException {
		return this.getUserSession().freeResourceSearch(arg0, arg1, arg2, arg3, arg4);
	}

	@SuppressWarnings("unchecked")
	public Vector freeResourceSearch(DateTime arg0, DateTime arg1, String arg2,
			int arg3, int arg4, String arg5, int arg6, String arg7,
			String arg8, int arg9) throws NotesException {
		return this.getUserSession().freeResourceSearch(arg0, arg1, arg2, arg3, arg4,
				arg5, arg6, arg7, arg8, arg9);
	}

	@SuppressWarnings("unchecked")
	public Vector freeTimeSearch(DateRange arg0, int arg1, Object arg2,
			boolean arg3) throws NotesException {
		return this.getUserSession().freeTimeSearch(arg0, arg1, arg2, arg3);
	}

	@SuppressWarnings("unchecked")
	public Vector getAddressBooks() throws NotesException {
		return this.getUserSession().getAddressBooks();
	}

	public AgentContext getAgentContext() throws NotesException {
		return this.getUserSession().getAgentContext();
	}

	public NotesCalendar getCalendar(Database arg0) throws NotesException {
		return this.getUserSession().getCalendar(arg0);
	}

	public String getCommonUserName() throws NotesException {
		return this.getUserSession().getCommonUserName();
	}

	public Object getCredentials() throws NotesException {
		return this.getUserSession().getCredentials();
	}

	public Database getCurrentDatabase() throws NotesException {
		return this.getUserSession().getCurrentDatabase();
	}

	public Database getDatabase(String arg0, String arg1) throws NotesException {
		return this.getUserSession().getDatabase(arg0, arg1);
	}

	public Database getDatabase(String arg0, String arg1, boolean arg2)
			throws NotesException {
		return this.getUserSession().getDatabase(arg0, arg1, arg2);
	}

	public DbDirectory getDbDirectory(String arg0) throws NotesException {
		return this.getUserSession().getDbDirectory(arg0);
	}

	public Directory getDirectory() throws NotesException {
		return this.getUserSession().getDirectory();
	}

	public Directory getDirectory(String arg0) throws NotesException {
		return this.getUserSession().getDirectory(arg0);
	}

	public String getEffectiveUserName() throws NotesException {
		return this.getUserSession().getEffectiveUserName();
	}

	public String getEnvironmentString(String arg0) throws NotesException {
		return this.getUserSession().getEnvironmentString(arg0);
	}

	public String getEnvironmentString(String arg0, boolean arg1)
			throws NotesException {
		return this.getUserSession().getEnvironmentString(arg0, arg1);
	}

	public Object getEnvironmentValue(String arg0) throws NotesException {
		return this.getUserSession().getEnvironmentValue(arg0);
	}

	public Object getEnvironmentValue(String arg0, boolean arg1)
			throws NotesException {
		return this.getUserSession().getEnvironmentValue(arg0, arg1);
	}

	public String getHttpURL() throws NotesException {
		return this.getUserSession().getHttpURL();
	}

	public International getInternational() throws NotesException {
		return this.getUserSession().getInternational();
	}

	public String getNotesVersion() throws NotesException {
		return this.getUserSession().getNotesVersion();
	}

	public String getOrgDirectoryPath() throws NotesException {
		return this.getUserSession().getOrgDirectoryPath();
	}

	public String getPlatform() throws NotesException {
		return this.getUserSession().getPlatform();
	}

	public PropertyBroker getPropertyBroker() throws NotesException {
		return this.getUserSession().getPropertyBroker();
	}

	public String getServerName() throws NotesException {
		return this.getUserSession().getServerName();
	}

	public String getSessionToken() throws NotesException {
		return this.getUserSession().getSessionToken();
	}

	public String getSessionToken(String arg0) throws NotesException {
		return this.getUserSession().getSessionToken(arg0);
	}

	public String getURL() throws NotesException {
		return this.getUserSession().getURL();
	}

	public Database getURLDatabase() throws NotesException {
		return this.getUserSession().getURLDatabase();
	}

	@SuppressWarnings("unchecked")
	public Vector getUserGroupNameList() throws NotesException {
		return this.getUserSession().getUserGroupNameList();
	}

	public String getUserName() throws NotesException {
		return this.getUserSession().getUserName();
	}

	@SuppressWarnings("unchecked")
	public Vector getUserNameList() throws NotesException {
		return this.getUserSession().getUserNameList();
	}

	public Name getUserNameObject() throws NotesException {
		return this.getUserSession().getUserNameObject();
	}

	public Document getUserPolicySettings(String arg0, String arg1, int arg2)
			throws NotesException {
		return this.getUserSession().getUserPolicySettings(arg0, arg1, arg2);
	}

	public Document getUserPolicySettings(String arg0, String arg1, int arg2,
			String arg3) throws NotesException {
		return this.getUserSession().getUserPolicySettings(arg0, arg1, arg2, arg3);
	}

	public String hashPassword(String arg0) throws NotesException {
		return this.getUserSession().hashPassword(arg0);
	}

	public boolean isConvertMIME() throws NotesException {
		return this.getUserSession().isConvertMIME();
	}

	public boolean isConvertMime() throws NotesException {
		return this.getUserSession().isConvertMime();
	}

	public boolean isOnServer() throws NotesException {
		return this.getUserSession().isOnServer();
	}

	public boolean isRestricted() throws NotesException {
		return this.getUserSession().isRestricted();
	}

	public boolean isTrackMillisecInJavaDates() throws NotesException {
		return this.getUserSession().isTrackMillisecInJavaDates();
	}

	public boolean isTrustedSession() throws NotesException {
		return this.getUserSession().isTrustedSession();
	}

	public boolean isValid() {
		return this.getUserSession().isValid();
	}

	public void recycle() throws NotesException {
		this.getUserSession().recycle();
	}

	@SuppressWarnings("unchecked")
	public void recycle(Vector arg0) throws NotesException {
		this.getUserSession().recycle(arg0);
	}

	public boolean resetUserPassword(String arg0, String arg1, String arg2)
			throws NotesException {
		return this.getUserSession().resetUserPassword(arg0, arg1, arg2);
	}

	public boolean resetUserPassword(String arg0, String arg1, String arg2,
			int arg3) throws NotesException {
		return this.getUserSession().resetUserPassword(arg0, arg1, arg2, arg3);
	}

	public Base resolve(String arg0) throws NotesException {
		return this.getUserSession().resolve(arg0);
	}

	public String sendConsoleCommand(String arg0, String arg1)
			throws NotesException {
		return this.getUserSession().sendConsoleCommand(arg0, arg1);
	}

	public void setAllowLoopBack(boolean arg0) throws NotesException {
		this.getUserSession().setAllowLoopBack(arg0);
	}

	public void setConvertMIME(boolean arg0) throws NotesException {
		this.getUserSession().setConvertMIME(arg0);
	}

	public void setConvertMime(boolean arg0) throws NotesException {
		this.getUserSession().setConvertMime(arg0);
	}

	public void setEnvironmentVar(String arg0, Object arg1)
			throws NotesException {
		this.getUserSession().setEnvironmentVar(arg0, arg1);
	}

	public void setEnvironmentVar(String arg0, Object arg1, boolean arg2)
			throws NotesException {
		this.getUserSession().setEnvironmentVar(arg0, arg1, arg2);
	}

	public void setTrackMillisecInJavaDates(boolean arg0) throws NotesException {
		this.getUserSession().setTrackMillisecInJavaDates(arg0);
	}

	public boolean verifyPassword(String arg0, String arg1)
			throws NotesException {
		return this.getUserSession().verifyPassword(arg0, arg1);
	}
}
