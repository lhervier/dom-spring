package com.github.lhervier.domino.spring.util;

import lotus.domino.Base;
import lotus.domino.Database;
import lotus.domino.NotesException;
import lotus.domino.Session;

/**
 * Méthodes pratiques pour Domino
 * @author Lionel HERVIER
 */
public class DominoUtils {

	/**
	 * Pour recycler un object Domino
	 * @param o l'objet à recycler
	 */
	public static final void recycleQuietly(Base o) {
		if( o == null )
			return;
		try {
			o.recycle();
		} catch(NotesException e) {
			e.printStackTrace(System.err);
		}
	}
	
	/**
	 * Ouvre une database
	 * @param session la session pour ouvrir la base
	 * @param filePath le chemin vers la base
	 * @return la database ou null si elle n'existe pas
	 * @throws NotesException si on n'a pas les droits
	 */
	public static final Database openDatabase(Session session, String name) throws NotesException {
		Database db;
		try {
			db = session.getDatabase(null, name, false);		// createOnFail = false
		} catch(NotesException e) {
			return null;
		}
		if( db == null )
			return null;
		if( !db.isOpen() )
			if( !db.open() ) 
				return null;
		return db;
	}
}
