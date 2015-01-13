package org.strasa.web.createfieldbook.view.model;

import java.util.ArrayList;

public class CreateFieldBookException extends Exception {

	public ArrayList<String> lstErrors;

	public CreateFieldBookException(String msg) {
		super(msg);
	}

	public CreateFieldBookException(String msg, ArrayList<String> lstError) {
		super(msg);
		this.lstErrors = lstError;
	}
}
