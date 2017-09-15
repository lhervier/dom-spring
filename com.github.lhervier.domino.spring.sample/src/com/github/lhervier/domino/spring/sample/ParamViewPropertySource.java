package com.github.lhervier.domino.spring.sample;

import lotus.domino.Database;
import lotus.domino.NotesException;

import com.github.lhervier.domino.spring.servlet.BaseParamViewPropertySource;

public class ParamViewPropertySource extends BaseParamViewPropertySource {

	public ParamViewPropertySource() {
		super("param-view-property-source", "Params");
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
