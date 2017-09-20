package com.github.lhervier.domino.spring.servlet;

import java.util.Vector;

import lotus.domino.ACL;
import lotus.domino.Agent;
import lotus.domino.Database;
import lotus.domino.DateTime;
import lotus.domino.Document;
import lotus.domino.DocumentCollection;
import lotus.domino.Form;
import lotus.domino.NoteCollection;
import lotus.domino.NotesException;
import lotus.domino.Outline;
import lotus.domino.Replication;
import lotus.domino.Session;
import lotus.domino.View;

import org.springframework.stereotype.Component;

import com.ibm.domino.osgi.core.context.ContextInfo;

@Component
public class UserDatabase implements Database {

	private Database getUserDatabase() {
		return ContextInfo.getUserDatabase();
	}
	
	public boolean isAvailable() {
		return this.getUserDatabase() != null;
	}
	
	// ===========================================================
	
	public Document FTDomainSearch(String arg0, int arg1, int arg2, int arg3,
			int arg4, int arg5, String arg6) throws NotesException {
		return getUserDatabase()
				.FTDomainSearch(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	}

	public DocumentCollection FTSearch(String arg0) throws NotesException {
		return getUserDatabase().FTSearch(arg0);
	}

	public DocumentCollection FTSearch(String arg0, int arg1)
			throws NotesException {
		return getUserDatabase().FTSearch(arg0, arg1);
	}

	public DocumentCollection FTSearch(String arg0, int arg1, int arg2, int arg3)
			throws NotesException {
		return getUserDatabase().FTSearch(arg0, arg1, arg2, arg3);
	}

	public DocumentCollection FTSearchRange(String arg0, int arg1, int arg2,
			int arg3, int arg4) throws NotesException {
		return getUserDatabase().FTSearchRange(arg0, arg1, arg2, arg3, arg4);
	}

	public int compact() throws NotesException {
		return getUserDatabase().compact();
	}

	public int compactWithOptions(String arg0) throws NotesException {
		return getUserDatabase().compactWithOptions(arg0);
	}

	public int compactWithOptions(int arg0) throws NotesException {
		return getUserDatabase().compactWithOptions(arg0);
	}

	public int compactWithOptions(int arg0, String arg1) throws NotesException {
		return getUserDatabase().compactWithOptions(arg0, arg1);
	}

	public Database createCopy(String arg0, String arg1) throws NotesException {
		return getUserDatabase().createCopy(arg0, arg1);
	}

	public Database createCopy(String arg0, String arg1, int arg2)
			throws NotesException {
		return getUserDatabase().createCopy(arg0, arg1, arg2);
	}

	public Document createDocument() throws NotesException {
		return getUserDatabase().createDocument();
	}

	public DocumentCollection createDocumentCollection() throws NotesException {
		return getUserDatabase().createDocumentCollection();
	}

	public void createFTIndex(int arg0, boolean arg1) throws NotesException {
		getUserDatabase().createFTIndex(arg0, arg1);
	}

	public Database createFromTemplate(String arg0, String arg1, boolean arg2)
			throws NotesException {
		return getUserDatabase().createFromTemplate(arg0, arg1, arg2);
	}

	public Database createFromTemplate(String arg0, String arg1, boolean arg2,
			int arg3) throws NotesException {
		return getUserDatabase().createFromTemplate(arg0, arg1, arg2, arg3);
	}

	public NoteCollection createNoteCollection(boolean arg0)
			throws NotesException {
		return getUserDatabase().createNoteCollection(arg0);
	}

	public Outline createOutline(String arg0) throws NotesException {
		return getUserDatabase().createOutline(arg0);
	}

	public Outline createOutline(String arg0, boolean arg1)
			throws NotesException {
		return getUserDatabase().createOutline(arg0, arg1);
	}

	public View createQueryView(String arg0, String arg1) throws NotesException {
		return getUserDatabase().createQueryView(arg0, arg1);
	}

	public View createQueryView(String arg0, String arg1, View arg2)
			throws NotesException {
		return getUserDatabase().createQueryView(arg0, arg1, arg2);
	}

	public View createQueryView(String arg0, String arg1, View arg2,
			boolean arg3) throws NotesException {
		return getUserDatabase().createQueryView(arg0, arg1, arg2, arg3);
	}

	public Database createReplica(String arg0, String arg1)
			throws NotesException {
		return getUserDatabase().createReplica(arg0, arg1);
	}

	public View createView() throws NotesException {
		return getUserDatabase().createView();
	}

	public View createView(String arg0) throws NotesException {
		return getUserDatabase().createView(arg0);
	}

	public View createView(String arg0, String arg1) throws NotesException {
		return getUserDatabase().createView(arg0, arg1);
	}

	public View createView(String arg0, String arg1, View arg2)
			throws NotesException {
		return getUserDatabase().createView(arg0, arg1, arg2);
	}

	public View createView(String arg0, String arg1, View arg2, boolean arg3)
			throws NotesException {
		return getUserDatabase().createView(arg0, arg1, arg2, arg3);
	}

	public void enableFolder(String arg0) throws NotesException {
		getUserDatabase().enableFolder(arg0);
	}

	public void fixup() throws NotesException {
		getUserDatabase().fixup();
	}

	public void fixup(int arg0) throws NotesException {
		getUserDatabase().fixup(arg0);
	}

	public ACL getACL() throws NotesException {
		return getUserDatabase().getACL();
	}

	@SuppressWarnings("unchecked")
	public Vector getACLActivityLog() throws NotesException {
		return getUserDatabase().getACLActivityLog();
	}

	public Agent getAgent(String arg0) throws NotesException {
		return getUserDatabase().getAgent(arg0);
	}

	@SuppressWarnings("unchecked")
	public Vector getAgents() throws NotesException {
		return getUserDatabase().getAgents();
	}

	public DocumentCollection getAllDocuments() throws NotesException {
		return getUserDatabase().getAllDocuments();
	}

	public DocumentCollection getAllReadDocuments() throws NotesException {
		return getUserDatabase().getAllReadDocuments();
	}

	public DocumentCollection getAllReadDocuments(String arg0)
			throws NotesException {
		return getUserDatabase().getAllReadDocuments(arg0);
	}

	public DocumentCollection getAllUnreadDocuments() throws NotesException {
		return getUserDatabase().getAllUnreadDocuments();
	}

	public DocumentCollection getAllUnreadDocuments(String arg0)
			throws NotesException {
		return getUserDatabase().getAllUnreadDocuments(arg0);
	}

	public String getCategories() throws NotesException {
		return getUserDatabase().getCategories();
	}

	public DateTime getCreated() throws NotesException {
		return getUserDatabase().getCreated();
	}

	public int getCurrentAccessLevel() throws NotesException {
		return getUserDatabase().getCurrentAccessLevel();
	}

	public String getDB2Schema() throws NotesException {
		return getUserDatabase().getDB2Schema();
	}

	public String getDesignTemplateName() throws NotesException {
		return getUserDatabase().getDesignTemplateName();
	}

	public Document getDocumentByID(String arg0) throws NotesException {
		return getUserDatabase().getDocumentByID(arg0);
	}

	public Document getDocumentByUNID(String arg0) throws NotesException {
		return getUserDatabase().getDocumentByUNID(arg0);
	}

	public Document getDocumentByURL(String arg0, boolean arg1)
			throws NotesException {
		return getUserDatabase().getDocumentByURL(arg0, arg1);
	}

	public Document getDocumentByURL(String arg0, boolean arg1, boolean arg2,
			boolean arg3, String arg4, String arg5, String arg6, String arg7,
			String arg8, boolean arg9) throws NotesException {
		return getUserDatabase().getDocumentByURL(arg0, arg1, arg2, arg3, arg4, arg5,
				arg6, arg7, arg8, arg9);
	}

	public int getFTIndexFrequency() throws NotesException {
		return getUserDatabase().getFTIndexFrequency();
	}

	public int getFileFormat() throws NotesException {
		return getUserDatabase().getFileFormat();
	}

	public String getFileName() throws NotesException {
		return getUserDatabase().getFileName();
	}

	public String getFilePath() throws NotesException {
		return getUserDatabase().getFilePath();
	}

	public boolean getFolderReferencesEnabled() throws NotesException {
		return getUserDatabase().getFolderReferencesEnabled();
	}

	public Form getForm(String arg0) throws NotesException {
		return getUserDatabase().getForm(arg0);
	}

	@SuppressWarnings("unchecked")
	public Vector getForms() throws NotesException {
		return getUserDatabase().getForms();
	}

	public String getHttpURL() throws NotesException {
		return getUserDatabase().getHttpURL();
	}

	public DateTime getLastFTIndexed() throws NotesException {
		return getUserDatabase().getLastFTIndexed();
	}

	public DateTime getLastFixup() throws NotesException {
		return getUserDatabase().getLastFixup();
	}

	public DateTime getLastModified() throws NotesException {
		return getUserDatabase().getLastModified();
	}

	public double getLimitRevisions() throws NotesException {
		return getUserDatabase().getLimitRevisions();
	}

	public double getLimitUpdatedBy() throws NotesException {
		return getUserDatabase().getLimitUpdatedBy();
	}

	public boolean getListInDbCatalog() throws NotesException {
		return getUserDatabase().getListInDbCatalog();
	}

	@SuppressWarnings("unchecked")
	public Vector getManagers() throws NotesException {
		return getUserDatabase().getManagers();
	}

	public long getMaxSize() throws NotesException {
		return getUserDatabase().getMaxSize();
	}

	public DocumentCollection getModifiedDocuments() throws NotesException {
		return getUserDatabase().getModifiedDocuments();
	}

	public DocumentCollection getModifiedDocuments(DateTime arg0)
			throws NotesException {
		return getUserDatabase().getModifiedDocuments(arg0);
	}

	public DocumentCollection getModifiedDocuments(DateTime arg0, int arg1)
			throws NotesException {
		return getUserDatabase().getModifiedDocuments(arg0, arg1);
	}

	public String getNotesURL() throws NotesException {
		return getUserDatabase().getNotesURL();
	}

	public boolean getOption(int arg0) throws NotesException {
		return getUserDatabase().getOption(arg0);
	}

	public Outline getOutline(String arg0) throws NotesException {
		return getUserDatabase().getOutline(arg0);
	}

	public Session getParent() throws NotesException {
		return getUserDatabase().getParent();
	}

	public double getPercentUsed() throws NotesException {
		return getUserDatabase().getPercentUsed();
	}

	public DocumentCollection getProfileDocCollection(String arg0)
			throws NotesException {
		return getUserDatabase().getProfileDocCollection(arg0);
	}

	public Document getProfileDocument(String arg0, String arg1)
			throws NotesException {
		return getUserDatabase().getProfileDocument(arg0, arg1);
	}

	public String getReplicaID() throws NotesException {
		return getUserDatabase().getReplicaID();
	}

	public Replication getReplicationInfo() throws NotesException {
		return getUserDatabase().getReplicationInfo();
	}

	public String getServer() throws NotesException {
		return getUserDatabase().getServer();
	}

	public double getSize() throws NotesException {
		return getUserDatabase().getSize();
	}

	public int getSizeQuota() throws NotesException {
		return getUserDatabase().getSizeQuota();
	}

	public long getSizeWarning() throws NotesException {
		return getUserDatabase().getSizeWarning();
	}

	public String getTemplateName() throws NotesException {
		return getUserDatabase().getTemplateName();
	}

	public String getTitle() throws NotesException {
		return getUserDatabase().getTitle();
	}

	public int getType() throws NotesException {
		return getUserDatabase().getType();
	}

	public String getURL() throws NotesException {
		return getUserDatabase().getURL();
	}

	public String getURLHeaderInfo(String arg0, String arg1, String arg2,
			String arg3, String arg4, String arg5) throws NotesException {
		return getUserDatabase().getURLHeaderInfo(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	public int getUndeleteExpireTime() throws NotesException {
		return getUserDatabase().getUndeleteExpireTime();
	}

	public View getView(String arg0) throws NotesException {
		return getUserDatabase().getView(arg0);
	}

	@SuppressWarnings("unchecked")
	public Vector getViews() throws NotesException {
		return getUserDatabase().getViews();
	}

	public void grantAccess(String arg0, int arg1) throws NotesException {
		getUserDatabase().grantAccess(arg0, arg1);
	}

	public boolean isAllowOpenSoftDeleted() throws NotesException {
		return getUserDatabase().isAllowOpenSoftDeleted();
	}

	public boolean isClusterReplication() throws NotesException {
		return getUserDatabase().isClusterReplication();
	}

	public boolean isConfigurationDirectory() throws NotesException {
		return getUserDatabase().isConfigurationDirectory();
	}

	public boolean isCurrentAccessPublicReader() throws NotesException {
		return getUserDatabase().isCurrentAccessPublicReader();
	}

	public boolean isCurrentAccessPublicWriter() throws NotesException {
		return getUserDatabase().isCurrentAccessPublicWriter();
	}

	public boolean isDB2() throws NotesException {
		return getUserDatabase().isDB2();
	}

	public boolean isDelayUpdates() throws NotesException {
		return getUserDatabase().isDelayUpdates();
	}

	public boolean isDesignLockingEnabled() throws NotesException {
		return getUserDatabase().isDesignLockingEnabled();
	}

	public boolean isDirectoryCatalog() throws NotesException {
		return getUserDatabase().isDirectoryCatalog();
	}

	public boolean isDocumentLockingEnabled() throws NotesException {
		return getUserDatabase().isDocumentLockingEnabled();
	}

	public boolean isFTIndexed() throws NotesException {
		return getUserDatabase().isFTIndexed();
	}

	public boolean isInMultiDbIndexing() throws NotesException {
		return getUserDatabase().isInMultiDbIndexing();
	}

	public boolean isInService() throws NotesException {
		return getUserDatabase().isInService();
	}

	public boolean isLink() throws NotesException {
		return getUserDatabase().isLink();
	}

	public boolean isMultiDbSearch() throws NotesException {
		return getUserDatabase().isMultiDbSearch();
	}

	public boolean isOpen() throws NotesException {
		return getUserDatabase().isOpen();
	}

	public boolean isPendingDelete() throws NotesException {
		return getUserDatabase().isPendingDelete();
	}

	public boolean isPrivateAddressBook() throws NotesException {
		return getUserDatabase().isPrivateAddressBook();
	}

	public boolean isPublicAddressBook() throws NotesException {
		return getUserDatabase().isPublicAddressBook();
	}

	public void markForDelete() throws NotesException {
		getUserDatabase().markForDelete();
	}

	public boolean open() throws NotesException {
		return getUserDatabase().open();
	}

	public boolean openByReplicaID(String arg0, String arg1)
			throws NotesException {
		return getUserDatabase().openByReplicaID(arg0, arg1);
	}

	public boolean openIfModified(String arg0, String arg1, DateTime arg2)
			throws NotesException {
		return getUserDatabase().openIfModified(arg0, arg1, arg2);
	}

	public boolean openWithFailover(String arg0, String arg1)
			throws NotesException {
		return getUserDatabase().openWithFailover(arg0, arg1);
	}

	public int queryAccess(String arg0) throws NotesException {
		return getUserDatabase().queryAccess(arg0);
	}

	public int queryAccessPrivileges(String arg0) throws NotesException {
		return getUserDatabase().queryAccessPrivileges(arg0);
	}

	@SuppressWarnings("unchecked")
	public Vector queryAccessRoles(String arg0) throws NotesException {
		return getUserDatabase().queryAccessRoles(arg0);
	}

	public void recycle() throws NotesException {
		getUserDatabase().recycle();
	}

	@SuppressWarnings("unchecked")
	public void recycle(Vector arg0) throws NotesException {
		getUserDatabase().recycle(arg0);
	}

	public void remove() throws NotesException {
		getUserDatabase().remove();
	}

	public void removeFTIndex() throws NotesException {
		getUserDatabase().removeFTIndex();
	}

	public boolean replicate(String arg0) throws NotesException {
		return getUserDatabase().replicate(arg0);
	}

	public void revokeAccess(String arg0) throws NotesException {
		getUserDatabase().revokeAccess(arg0);
	}

	public DocumentCollection search(String arg0) throws NotesException {
		return getUserDatabase().search(arg0);
	}

	public DocumentCollection search(String arg0, DateTime arg1)
			throws NotesException {
		return getUserDatabase().search(arg0, arg1);
	}

	public DocumentCollection search(String arg0, DateTime arg1, int arg2)
			throws NotesException {
		return getUserDatabase().search(arg0, arg1, arg2);
	}

	public void setAllowOpenSoftDeleted(boolean arg0) throws NotesException {
		getUserDatabase().setAllowOpenSoftDeleted(arg0);
	}

	public void setCategories(String arg0) throws NotesException {
		getUserDatabase().setCategories(arg0);
	}

	public void setDelayUpdates(boolean arg0) throws NotesException {
		getUserDatabase().setDelayUpdates(arg0);
	}

	public void setDesignLockingEnabled(boolean arg0) throws NotesException {
		getUserDatabase().setDesignLockingEnabled(arg0);
	}

	public void setDocumentLockingEnabled(boolean arg0) throws NotesException {
		getUserDatabase().setDocumentLockingEnabled(arg0);
	}

	public void setFTIndexFrequency(int arg0) throws NotesException {
		getUserDatabase().setFTIndexFrequency(arg0);
	}

	public void setFolderReferencesEnabled(boolean arg0) throws NotesException {
		getUserDatabase().setFolderReferencesEnabled(arg0);
	}

	public void setInMultiDbIndexing(boolean arg0) throws NotesException {
		getUserDatabase().setInMultiDbIndexing(arg0);
	}

	public void setInService(boolean arg0) throws NotesException {
		getUserDatabase().setInService(arg0);
	}

	public void setLimitRevisions(double arg0) throws NotesException {
		getUserDatabase().setLimitRevisions(arg0);
	}

	public void setLimitUpdatedBy(double arg0) throws NotesException {
		getUserDatabase().setLimitUpdatedBy(arg0);
	}

	public void setListInDbCatalog(boolean arg0) throws NotesException {
		getUserDatabase().setListInDbCatalog(arg0);
	}

	public void setOption(int arg0, boolean arg1) throws NotesException {
		getUserDatabase().setOption(arg0, arg1);
	}

	public void setSizeQuota(int arg0) throws NotesException {
		getUserDatabase().setSizeQuota(arg0);
	}

	public void setSizeWarning(int arg0) throws NotesException {
		getUserDatabase().setSizeWarning(arg0);
	}

	public void setTitle(String arg0) throws NotesException {
		getUserDatabase().setTitle(arg0);
	}

	public void setUndeleteExpireTime(int arg0) throws NotesException {
		getUserDatabase().setUndeleteExpireTime(arg0);
	}

	public void sign() throws NotesException {
		getUserDatabase().sign();
	}

	public void sign(int arg0) throws NotesException {
		getUserDatabase().sign(arg0);
	}

	public void sign(int arg0, boolean arg1) throws NotesException {
		getUserDatabase().sign(arg0, arg1);
	}

	public void sign(int arg0, boolean arg1, String arg2) throws NotesException {
		getUserDatabase().sign(arg0, arg1, arg2);
	}

	public void sign(int arg0, boolean arg1, String arg2, boolean arg3)
			throws NotesException {
		getUserDatabase().sign(arg0, arg1, arg2, arg3);
	}

	public void updateFTIndex(boolean arg0) throws NotesException {
		getUserDatabase().updateFTIndex(arg0);
	}
}
