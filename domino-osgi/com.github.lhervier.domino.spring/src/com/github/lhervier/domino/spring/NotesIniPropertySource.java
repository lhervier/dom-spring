package com.github.lhervier.domino.spring;

import java.util.Properties;

import lotus.domino.NotesException;
import lotus.domino.Session;

import com.github.lhervier.domino.spring.servlet.BaseNotesPropertySource;

public class NotesIniPropertySource extends BaseNotesPropertySource {
	
	/**
	 * Notes.ini values
	 */
	private Properties props;
	
	/**
	 * Constructor
	 */
	public NotesIniPropertySource() {
		super("notes-ini-property-source");
	}

	/**
	 * @see com.github.lhervier.domino.spring.servlet.BaseNotesPropertySource#init(lotus.domino.Session)
	 */
	@Override
	public void init(Session session) throws NotesException {
		this.props = new Properties();
		String configs = session.sendConsoleCommand("", "show config *");
		configs = configs.replaceAll("\r\n", "\n");
		String[] lines = configs.split("\n");
		for( String line : lines ) {
			if( line.startsWith("#") )
				continue;
			int pos = line.indexOf('=');
			if( pos == -1 )
				continue;
			String key = line.substring(0, pos);
			String value = line.substring(pos + 1);
			this.props.setProperty(key.toUpperCase(), value);
		}
	}
	
	/**
	 * @see org.springframework.core.env.PropertySource#getProperty(java.lang.String)
	 */
	@Override
	public Object getProperty(String name) {
		return this.props.get(name.toUpperCase());
	}
}
