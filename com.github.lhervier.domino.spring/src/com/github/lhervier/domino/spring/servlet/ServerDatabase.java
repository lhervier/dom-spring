package com.github.lhervier.domino.spring.servlet;

import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.github.lhervier.domino.spring.util.DominoUtils;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServerDatabase implements Database {

	/**
	 * The server session
	 */
	@Autowired
	private ServerSession serverSession;
	
	/**
	 * The user database
	 */
	@Autowired
	private UserDatabase userDatabase;
	
	/**
	 * The server database
	 */
	private Database serverDatabase;

	/**
	 * Bean Initialisation
	 */
	@PostConstruct
	public void init() {
		try {
			if( this.userDatabase.isAvailable() ) {
				this.serverDatabase = DominoUtils.openDatabase(serverSession, this.userDatabase.getFilePath());
			} else 
				this.serverDatabase = null;
		} catch (NotesException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Cleaups
	 */
	@PreDestroy
	public void cleanUp() {
		if( this.serverDatabase != null ) {
			DominoUtils.recycleQuietly(serverDatabase);
			this.serverDatabase = null;
		}
	}
	
	/**
	 * Check if database is available
	 */
	public boolean isAvailable() {
		return this.serverDatabase != null;
	}
	
	// ===================================================================
	
	public Document FTDomainSearch(String arg0, int arg1, int arg2, int arg3,
			int arg4, int arg5, String arg6) throws NotesException {
		return serverDatabase.FTDomainSearch(arg0, arg1, arg2, arg3, arg4,
				arg5, arg6);
	}

	public DocumentCollection FTSearch(String arg0) throws NotesException {
		return serverDatabase.FTSearch(arg0);
	}

	public DocumentCollection FTSearch(String arg0, int arg1)
			throws NotesException {
		return serverDatabase.FTSearch(arg0, arg1);
	}

	public DocumentCollection FTSearch(String arg0, int arg1, int arg2, int arg3)
			throws NotesException {
		return serverDatabase.FTSearch(arg0, arg1, arg2, arg3);
	}

	public DocumentCollection FTSearchRange(String arg0, int arg1, int arg2,
			int arg3, int arg4) throws NotesException {
		return serverDatabase.FTSearchRange(arg0, arg1, arg2, arg3, arg4);
	}

	public int compact() throws NotesException {
		return serverDatabase.compact();
	}

	public int compactWithOptions(String arg0) throws NotesException {
		return serverDatabase.compactWithOptions(arg0);
	}

	public int compactWithOptions(int arg0) throws NotesException {
		return serverDatabase.compactWithOptions(arg0);
	}

	public int compactWithOptions(int arg0, String arg1) throws NotesException {
		return serverDatabase.compactWithOptions(arg0, arg1);
	}

	public Database createCopy(String arg0, String arg1) throws NotesException {
		return serverDatabase.createCopy(arg0, arg1);
	}

	public Database createCopy(String arg0, String arg1, int arg2)
			throws NotesException {
		return serverDatabase.createCopy(arg0, arg1, arg2);
	}

	public Document createDocument() throws NotesException {
		return serverDatabase.createDocument();
	}

	public DocumentCollection createDocumentCollection() throws NotesException {
		return serverDatabase.createDocumentCollection();
	}

	public void createFTIndex(int arg0, boolean arg1) throws NotesException {
		serverDatabase.createFTIndex(arg0, arg1);
	}

	public Database createFromTemplate(String arg0, String arg1, boolean arg2)
			throws NotesException {
		return serverDatabase.createFromTemplate(arg0, arg1, arg2);
	}

	public Database createFromTemplate(String arg0, String arg1, boolean arg2,
			int arg3) throws NotesException {
		return serverDatabase.createFromTemplate(arg0, arg1, arg2, arg3);
	}

	public NoteCollection createNoteCollection(boolean arg0)
			throws NotesException {
		return serverDatabase.createNoteCollection(arg0);
	}

	public Outline createOutline(String arg0) throws NotesException {
		return serverDatabase.createOutline(arg0);
	}

	public Outline createOutline(String arg0, boolean arg1)
			throws NotesException {
		return serverDatabase.createOutline(arg0, arg1);
	}

	public View createQueryView(String arg0, String arg1) throws NotesException {
		return serverDatabase.createQueryView(arg0, arg1);
	}

	public View createQueryView(String arg0, String arg1, View arg2)
			throws NotesException {
		return serverDatabase.createQueryView(arg0, arg1, arg2);
	}

	public View createQueryView(String arg0, String arg1, View arg2,
			boolean arg3) throws NotesException {
		return serverDatabase.createQueryView(arg0, arg1, arg2, arg3);
	}

	public Database createReplica(String arg0, String arg1)
			throws NotesException {
		return serverDatabase.createReplica(arg0, arg1);
	}

	public View createView() throws NotesException {
		return serverDatabase.createView();
	}

	public View createView(String arg0) throws NotesException {
		return serverDatabase.createView(arg0);
	}

	public View createView(String arg0, String arg1) throws NotesException {
		return serverDatabase.createView(arg0, arg1);
	}

	public View createView(String arg0, String arg1, View arg2)
			throws NotesException {
		return serverDatabase.createView(arg0, arg1, arg2);
	}

	public View createView(String arg0, String arg1, View arg2, boolean arg3)
			throws NotesException {
		return serverDatabase.createView(arg0, arg1, arg2, arg3);
	}

	public void enableFolder(String arg0) throws NotesException {
		serverDatabase.enableFolder(arg0);
	}

	public void fixup() throws NotesException {
		serverDatabase.fixup();
	}

	public void fixup(int arg0) throws NotesException {
		serverDatabase.fixup(arg0);
	}

	public ACL getACL() throws NotesException {
		return serverDatabase.getACL();
	}

	@SuppressWarnings("unchecked")
	public Vector getACLActivityLog() throws NotesException {
		return serverDatabase.getACLActivityLog();
	}

	public Agent getAgent(String arg0) throws NotesException {
		return serverDatabase.getAgent(arg0);
	}

	@SuppressWarnings("unchecked")
	public Vector getAgents() throws NotesException {
		return serverDatabase.getAgents();
	}

	public DocumentCollection getAllDocuments() throws NotesException {
		return serverDatabase.getAllDocuments();
	}

	public DocumentCollection getAllReadDocuments() throws NotesException {
		return serverDatabase.getAllReadDocuments();
	}

	public DocumentCollection getAllReadDocuments(String arg0)
			throws NotesException {
		return serverDatabase.getAllReadDocuments(arg0);
	}

	public DocumentCollection getAllUnreadDocuments() throws NotesException {
		return serverDatabase.getAllUnreadDocuments();
	}

	public DocumentCollection getAllUnreadDocuments(String arg0)
			throws NotesException {
		return serverDatabase.getAllUnreadDocuments(arg0);
	}

	public String getCategories() throws NotesException {
		return serverDatabase.getCategories();
	}

	public DateTime getCreated() throws NotesException {
		return serverDatabase.getCreated();
	}

	public int getCurrentAccessLevel() throws NotesException {
		return serverDatabase.getCurrentAccessLevel();
	}

	public String getDB2Schema() throws NotesException {
		return serverDatabase.getDB2Schema();
	}

	public String getDesignTemplateName() throws NotesException {
		return serverDatabase.getDesignTemplateName();
	}

	public Document getDocumentByID(String arg0) throws NotesException {
		return serverDatabase.getDocumentByID(arg0);
	}

	public Document getDocumentByUNID(String arg0) throws NotesException {
		return serverDatabase.getDocumentByUNID(arg0);
	}

	public Document getDocumentByURL(String arg0, boolean arg1)
			throws NotesException {
		return serverDatabase.getDocumentByURL(arg0, arg1);
	}

	public Document getDocumentByURL(String arg0, boolean arg1, boolean arg2,
			boolean arg3, String arg4, String arg5, String arg6, String arg7,
			String arg8, boolean arg9) throws NotesException {
		return serverDatabase.getDocumentByURL(arg0, arg1, arg2, arg3, arg4,
				arg5, arg6, arg7, arg8, arg9);
	}

	public int getFTIndexFrequency() throws NotesException {
		return serverDatabase.getFTIndexFrequency();
	}

	public int getFileFormat() throws NotesException {
		return serverDatabase.getFileFormat();
	}

	public String getFileName() throws NotesException {
		return serverDatabase.getFileName();
	}

	public String getFilePath() throws NotesException {
		return serverDatabase.getFilePath();
	}

	public boolean getFolderReferencesEnabled() throws NotesException {
		return serverDatabase.getFolderReferencesEnabled();
	}

	public Form getForm(String arg0) throws NotesException {
		return serverDatabase.getForm(arg0);
	}

	@SuppressWarnings("unchecked")
	public Vector getForms() throws NotesException {
		return serverDatabase.getForms();
	}

	public String getHttpURL() throws NotesException {
		return serverDatabase.getHttpURL();
	}

	public DateTime getLastFTIndexed() throws NotesException {
		return serverDatabase.getLastFTIndexed();
	}

	public DateTime getLastFixup() throws NotesException {
		return serverDatabase.getLastFixup();
	}

	public DateTime getLastModified() throws NotesException {
		return serverDatabase.getLastModified();
	}

	public double getLimitRevisions() throws NotesException {
		return serverDatabase.getLimitRevisions();
	}

	public double getLimitUpdatedBy() throws NotesException {
		return serverDatabase.getLimitUpdatedBy();
	}

	public boolean getListInDbCatalog() throws NotesException {
		return serverDatabase.getListInDbCatalog();
	}

	@SuppressWarnings("unchecked")
	public Vector getManagers() throws NotesException {
		return serverDatabase.getManagers();
	}

	public long getMaxSize() throws NotesException {
		return serverDatabase.getMaxSize();
	}

	public DocumentCollection getModifiedDocuments() throws NotesException {
		return serverDatabase.getModifiedDocuments();
	}

	public DocumentCollection getModifiedDocuments(DateTime arg0)
			throws NotesException {
		return serverDatabase.getModifiedDocuments(arg0);
	}

	public DocumentCollection getModifiedDocuments(DateTime arg0, int arg1)
			throws NotesException {
		return serverDatabase.getModifiedDocuments(arg0, arg1);
	}

	public String getNotesURL() throws NotesException {
		return serverDatabase.getNotesURL();
	}

	public boolean getOption(int arg0) throws NotesException {
		return serverDatabase.getOption(arg0);
	}

	public Outline getOutline(String arg0) throws NotesException {
		return serverDatabase.getOutline(arg0);
	}

	public Session getParent() throws NotesException {
		return serverDatabase.getParent();
	}

	public double getPercentUsed() throws NotesException {
		return serverDatabase.getPercentUsed();
	}

	public DocumentCollection getProfileDocCollection(String arg0)
			throws NotesException {
		return serverDatabase.getProfileDocCollection(arg0);
	}

	public Document getProfileDocument(String arg0, String arg1)
			throws NotesException {
		return serverDatabase.getProfileDocument(arg0, arg1);
	}

	public String getReplicaID() throws NotesException {
		return serverDatabase.getReplicaID();
	}

	public Replication getReplicationInfo() throws NotesException {
		return serverDatabase.getReplicationInfo();
	}

	public String getServer() throws NotesException {
		return serverDatabase.getServer();
	}

	public double getSize() throws NotesException {
		return serverDatabase.getSize();
	}

	public int getSizeQuota() throws NotesException {
		return serverDatabase.getSizeQuota();
	}

	public long getSizeWarning() throws NotesException {
		return serverDatabase.getSizeWarning();
	}

	public String getTemplateName() throws NotesException {
		return serverDatabase.getTemplateName();
	}

	public String getTitle() throws NotesException {
		return serverDatabase.getTitle();
	}

	public int getType() throws NotesException {
		return serverDatabase.getType();
	}

	public String getURL() throws NotesException {
		return serverDatabase.getURL();
	}

	public String getURLHeaderInfo(String arg0, String arg1, String arg2,
			String arg3, String arg4, String arg5) throws NotesException {
		return serverDatabase.getURLHeaderInfo(arg0, arg1, arg2, arg3, arg4,
				arg5);
	}

	public int getUndeleteExpireTime() throws NotesException {
		return serverDatabase.getUndeleteExpireTime();
	}

	public View getView(String arg0) throws NotesException {
		return serverDatabase.getView(arg0);
	}

	@SuppressWarnings("unchecked")
	public Vector getViews() throws NotesException {
		return serverDatabase.getViews();
	}

	public void grantAccess(String arg0, int arg1) throws NotesException {
		serverDatabase.grantAccess(arg0, arg1);
	}

	public boolean isAllowOpenSoftDeleted() throws NotesException {
		return serverDatabase.isAllowOpenSoftDeleted();
	}

	public boolean isClusterReplication() throws NotesException {
		return serverDatabase.isClusterReplication();
	}

	public boolean isConfigurationDirectory() throws NotesException {
		return serverDatabase.isConfigurationDirectory();
	}

	public boolean isCurrentAccessPublicReader() throws NotesException {
		return serverDatabase.isCurrentAccessPublicReader();
	}

	public boolean isCurrentAccessPublicWriter() throws NotesException {
		return serverDatabase.isCurrentAccessPublicWriter();
	}

	public boolean isDB2() throws NotesException {
		return serverDatabase.isDB2();
	}

	public boolean isDelayUpdates() throws NotesException {
		return serverDatabase.isDelayUpdates();
	}

	public boolean isDesignLockingEnabled() throws NotesException {
		return serverDatabase.isDesignLockingEnabled();
	}

	public boolean isDirectoryCatalog() throws NotesException {
		return serverDatabase.isDirectoryCatalog();
	}

	public boolean isDocumentLockingEnabled() throws NotesException {
		return serverDatabase.isDocumentLockingEnabled();
	}

	public boolean isFTIndexed() throws NotesException {
		return serverDatabase.isFTIndexed();
	}

	public boolean isInMultiDbIndexing() throws NotesException {
		return serverDatabase.isInMultiDbIndexing();
	}

	public boolean isInService() throws NotesException {
		return serverDatabase.isInService();
	}

	public boolean isLink() throws NotesException {
		return serverDatabase.isLink();
	}

	public boolean isMultiDbSearch() throws NotesException {
		return serverDatabase.isMultiDbSearch();
	}

	public boolean isOpen() throws NotesException {
		return serverDatabase.isOpen();
	}

	public boolean isPendingDelete() throws NotesException {
		return serverDatabase.isPendingDelete();
	}

	public boolean isPrivateAddressBook() throws NotesException {
		return serverDatabase.isPrivateAddressBook();
	}

	public boolean isPublicAddressBook() throws NotesException {
		return serverDatabase.isPublicAddressBook();
	}

	public void markForDelete() throws NotesException {
		serverDatabase.markForDelete();
	}

	public boolean open() throws NotesException {
		return serverDatabase.open();
	}

	public boolean openByReplicaID(String arg0, String arg1)
			throws NotesException {
		return serverDatabase.openByReplicaID(arg0, arg1);
	}

	public boolean openIfModified(String arg0, String arg1, DateTime arg2)
			throws NotesException {
		return serverDatabase.openIfModified(arg0, arg1, arg2);
	}

	public boolean openWithFailover(String arg0, String arg1)
			throws NotesException {
		return serverDatabase.openWithFailover(arg0, arg1);
	}

	public int queryAccess(String arg0) throws NotesException {
		return serverDatabase.queryAccess(arg0);
	}

	public int queryAccessPrivileges(String arg0) throws NotesException {
		return serverDatabase.queryAccessPrivileges(arg0);
	}

	@SuppressWarnings("unchecked")
	public Vector queryAccessRoles(String arg0) throws NotesException {
		return serverDatabase.queryAccessRoles(arg0);
	}

	public void recycle() throws NotesException {
		serverDatabase.recycle();
	}

	@SuppressWarnings("unchecked")
	public void recycle(Vector arg0) throws NotesException {
		serverDatabase.recycle(arg0);
	}

	public void remove() throws NotesException {
		serverDatabase.remove();
	}

	public void removeFTIndex() throws NotesException {
		serverDatabase.removeFTIndex();
	}

	public boolean replicate(String arg0) throws NotesException {
		return serverDatabase.replicate(arg0);
	}

	public void revokeAccess(String arg0) throws NotesException {
		serverDatabase.revokeAccess(arg0);
	}

	public DocumentCollection search(String arg0) throws NotesException {
		return serverDatabase.search(arg0);
	}

	public DocumentCollection search(String arg0, DateTime arg1)
			throws NotesException {
		return serverDatabase.search(arg0, arg1);
	}

	public DocumentCollection search(String arg0, DateTime arg1, int arg2)
			throws NotesException {
		return serverDatabase.search(arg0, arg1, arg2);
	}

	public void setAllowOpenSoftDeleted(boolean arg0) throws NotesException {
		serverDatabase.setAllowOpenSoftDeleted(arg0);
	}

	public void setCategories(String arg0) throws NotesException {
		serverDatabase.setCategories(arg0);
	}

	public void setDelayUpdates(boolean arg0) throws NotesException {
		serverDatabase.setDelayUpdates(arg0);
	}

	public void setDesignLockingEnabled(boolean arg0) throws NotesException {
		serverDatabase.setDesignLockingEnabled(arg0);
	}

	public void setDocumentLockingEnabled(boolean arg0) throws NotesException {
		serverDatabase.setDocumentLockingEnabled(arg0);
	}

	public void setFTIndexFrequency(int arg0) throws NotesException {
		serverDatabase.setFTIndexFrequency(arg0);
	}

	public void setFolderReferencesEnabled(boolean arg0) throws NotesException {
		serverDatabase.setFolderReferencesEnabled(arg0);
	}

	public void setInMultiDbIndexing(boolean arg0) throws NotesException {
		serverDatabase.setInMultiDbIndexing(arg0);
	}

	public void setInService(boolean arg0) throws NotesException {
		serverDatabase.setInService(arg0);
	}

	public void setLimitRevisions(double arg0) throws NotesException {
		serverDatabase.setLimitRevisions(arg0);
	}

	public void setLimitUpdatedBy(double arg0) throws NotesException {
		serverDatabase.setLimitUpdatedBy(arg0);
	}

	public void setListInDbCatalog(boolean arg0) throws NotesException {
		serverDatabase.setListInDbCatalog(arg0);
	}

	public void setOption(int arg0, boolean arg1) throws NotesException {
		serverDatabase.setOption(arg0, arg1);
	}

	public void setSizeQuota(int arg0) throws NotesException {
		serverDatabase.setSizeQuota(arg0);
	}

	public void setSizeWarning(int arg0) throws NotesException {
		serverDatabase.setSizeWarning(arg0);
	}

	public void setTitle(String arg0) throws NotesException {
		serverDatabase.setTitle(arg0);
	}

	public void setUndeleteExpireTime(int arg0) throws NotesException {
		serverDatabase.setUndeleteExpireTime(arg0);
	}

	public void sign() throws NotesException {
		serverDatabase.sign();
	}

	public void sign(int arg0) throws NotesException {
		serverDatabase.sign(arg0);
	}

	public void sign(int arg0, boolean arg1) throws NotesException {
		serverDatabase.sign(arg0, arg1);
	}

	public void sign(int arg0, boolean arg1, String arg2) throws NotesException {
		serverDatabase.sign(arg0, arg1, arg2);
	}

	public void sign(int arg0, boolean arg1, String arg2, boolean arg3)
			throws NotesException {
		serverDatabase.sign(arg0, arg1, arg2, arg3);
	}

	public void updateFTIndex(boolean arg0) throws NotesException {
		serverDatabase.updateFTIndex(arg0);
	}
}
