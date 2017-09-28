package com.github.lhervier.domino.spring.sample;

import lotus.domino.Database;
import lotus.domino.NotesException;

import com.github.lhervier.domino.spring.servlet.BaseParamViewPropertySource;

public class ParamViewPropertySource extends BaseParamViewPropertySource {

	/**
	 * Empty constructor
	 */
	public ParamViewPropertySource() {
		super("sample-param-view-property-source");
	}

	/**
	 * @see com.github.lhervier.domino.spring.servlet.BaseParamViewPropertySource#getViewName()
	 */
	@Override
	protected String getViewName() {
		return "Params";
	}

	/**
	 * @see com.github.lhervier.domino.spring.servlet.BaseParamViewPropertySource#getPrefix()
	 */
	@Override
	protected String getPrefix() {
		return "spring.sample.local.";
	}

	/**
	 * We will extract properties from every database that 
	 * contains a "Params" view.
	 */
	@Override
	public boolean checkDb(Database database) throws NotesException {
		return true;
	}

}
