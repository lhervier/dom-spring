package com.github.lhervier.domino.spring.servlet;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import lotus.domino.Database;
import lotus.domino.Document;
import lotus.domino.Item;
import lotus.domino.NotesException;
import lotus.domino.NotesFactory;
import lotus.domino.NotesThread;
import lotus.domino.Session;
import lotus.domino.View;

import com.github.lhervier.domino.spring.util.DominoUtils;
import com.github.lhervier.domino.spring.util.ValueHolder;
import com.ibm.domino.osgi.core.context.ContextInfo;

/**
 * Base class for property sources that will search
 * for values in the fields of the first document of a
 * parameter view.
 * @author Lionel HERVIER
 */
public abstract class BaseParamViewPropertySource extends BaseNotesPropertySource {

	/**
	 * Expiration
	 */
	private static final long EXPIRATION = 30 * 60 * 1000;			// 30 minutes
	
	/**
	 * The properties for each database
	 */
	private Map<String, Properties> propertiesByDb;
	
	/**
	 * The expiration for each db
	 */
	private Map<String, Long> expirationByDb;
	
	/**
	 * Constructor
	 */
	public BaseParamViewPropertySource(String name) {
		super(name);
		this.propertiesByDb = new HashMap<String, Properties>();
		this.expirationByDb = new HashMap<String, Long>();
	}
	
	/**
	 * Return the view name
	 * @return the view name
	 */
	protected abstract String getViewName();
	
	/**
	 * A prefix to add to the field names
	 */
	protected String getPrefix() {
		return "";
	}
	
	/**
	 * Check if this database is elligible for property extraction
	 * @param database the database (opened with server access)
	 * @return true if the property can be extracted. False otherwise.
	 * @throws NotesException
	 */
	public abstract boolean checkDb(Database database) throws NotesException;
	
	/**
	 * @see com.github.lhervier.domino.spring.servlet.BaseNotesPropertySource#init(lotus.domino.Session)
	 */
	@Override
	public void init(Session sessionAsServer) throws NotesException {
		// Can't do anything here as we dont have a database context...
	}
	
	/**
	 * Return the properties for a given database
	 */
	private Properties getProperties(String dbPath) {
		Long expiration = this.expirationByDb.get(dbPath);
		if( expiration == null )
			return null;
		if( expiration.longValue() < System.currentTimeMillis() )
			return null;
		return this.propertiesByDb.get(dbPath);
	}
	
	/**
	 * Set the properties for a given db
	 */
	private void setProperties(String dbPath, Properties props) {
		this.expirationByDb.put(dbPath, new Long(System.currentTimeMillis() + EXPIRATION));
		this.propertiesByDb.put(dbPath, props);
	}
	
	/**
	 * Extract the property value from the first document in the view
	 */
	@Override
	public synchronized Object getProperty(String name) {
		try {
			// We need a notes context
			if( ContextInfo.getUserDatabase() == null )
				return null;
			final String dbPath = ContextInfo.getUserDatabase().getFilePath();
			
			// Search in the cache
			Properties prop = this.getProperties(dbPath);
			if( prop != null )
				return prop.get(name.toUpperCase());
			
			// We need to run the extraction in a new thread to use the server session.
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					Properties props = new Properties();
					
					Session session = null;
					Database db = null;
					View v = null;
					Document doc = null;
					try {
						NotesThread.sinitThread();
						session = NotesFactory.createSession();
						db = DominoUtils.openDatabase(session, dbPath);
						v = db.getView(BaseParamViewPropertySource.this.getViewName());
						if( v == null )
							return;
						doc = v.getFirstDocument();
						if( doc == null )
							return;
						Vector<?> items = doc.getItems();
						for( Iterator<?> iterator = items.iterator(); iterator.hasNext(); ) {
							Item it = (Item) iterator.next();
							if( it.getType() != Item.TEXT && it.getType() != Item.NUMBERS )
								continue;
							Vector<?> values = it.getValues();
							if( values == null || values.size() == 0 )
								continue;
							String value;
							if( values.size() == 1 ) {
								value = values.get(0).toString();
							} else {
								value = "";
								for( int i=0; i<values.size(); i++ ) {
									value = value + values.get(i);
									if( i != values.size() - 1)
										value += ",";
								}
							}
							props.setProperty(BaseParamViewPropertySource.this.getPrefix().toUpperCase() + it.getName().toUpperCase(), value);
						}
					} catch(NotesException e) {
						throw new RuntimeException(e);
					} finally {
						DominoUtils.recycleQuietly(doc);
						DominoUtils.recycleQuietly(v);
						DominoUtils.recycleQuietly(db);
						DominoUtils.recycleQuietly(session);
						NotesThread.stermThread();
						
						BaseParamViewPropertySource.this.setProperties(dbPath, props);
					}
				}
			};
			Thread t = new Thread(runnable);
			final ValueHolder<Throwable> ex = new ValueHolder<Throwable>();
			t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread t, Throwable e) {
					ex.set(e);
				}
			});
			t.start();
			t.join();
			
			if( ex.get() != null )
				throw new RuntimeException(ex.get());
			
			return this.getProperty(name);
		} catch(NotesException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
