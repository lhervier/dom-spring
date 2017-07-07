package com.github.lhervier.domino.spring;

import org.eclipse.core.runtime.Plugin;

public class Activator extends Plugin {
	public static final String PLUGIN_ID = Activator.class.getPackage().getName();
	
	/**
	 * The instance
	 */
	private static Activator instance;
	public static Activator getDefault() {
		return instance;
	}
	
	/**
	 * Constructor
	 */
	public Activator() {
		instance = this;
	}
}