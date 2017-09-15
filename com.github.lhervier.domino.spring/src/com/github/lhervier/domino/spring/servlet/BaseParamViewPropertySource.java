package com.github.lhervier.domino.spring.servlet;

import lotus.domino.Database;
import lotus.domino.Document;
import lotus.domino.NotesException;
import lotus.domino.Session;
import lotus.domino.View;

import com.github.lhervier.domino.spring.util.DominoUtils;

/**
 * Base class for property sources that will search
 * for values in the fields of the first document of a
 * parameter view.
 * @author Lionel HERVIER
 */
public abstract class BaseParamViewPropertySource extends BaseNotesPropertySource {

	/**
	 * The view Name
	 */
	private String viewName;
	
	/**
	 * Constructor
	 */
	public BaseParamViewPropertySource(String name, String viewName) {
		super(name);
		this.viewName = viewName;
	}
	
	/**
	 * Check if this database is elligible for property extraction
	 * @param database the database (opened with server access)
	 * @return true if the property can be extracted. False otherwise.
	 * @throws NotesException
	 */
	public abstract boolean checkDb(Database database) throws NotesException;
	
	/**
	 * Extract the property value from the first document in the view
	 */
	@Override
	public Object getProperty(Session session, String name) throws NotesException {
		Database db = session.getCurrentDatabase();
		if( db == null )
			return null;
		if( !this.checkDb(db) )
			return null;
		
		NotesContext ctx = new NotesContext();
		View v = null;
		Document firstDoc = null;
		try {
			ctx.init();
			
			v = ctx.getServerDatabase().getView(this.viewName);
			if( v == null )
				return null;
			firstDoc = v.getFirstDocument();
			if( firstDoc == null )
				return null;
			String value = firstDoc.getItemValueString(name);
			if( value.length() == 0 )
				return null;
			if( "EMPTY_STRING".equals(value) )
				return "";
			return value;
		} finally {
			DominoUtils.recycleQuietly(firstDoc);
			DominoUtils.recycleQuietly(v);
			ctx.cleanUp();
		}
	}

	
}
