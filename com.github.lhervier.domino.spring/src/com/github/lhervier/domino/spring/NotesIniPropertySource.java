package com.github.lhervier.domino.spring;

import java.util.Properties;

import com.github.lhervier.domino.spring.servlet.BaseNotesPropertySource;

import lotus.domino.NotesException;
import lotus.domino.Session;

public class NotesIniPropertySource extends BaseNotesPropertySource {
	
	private Properties props;
	
	public NotesIniPropertySource() {
		super("notes-ini-property-source");
	}

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
	
	@Override
	public Object getProperty(String name) {
		return this.props.get(name.toUpperCase());
	}
}
