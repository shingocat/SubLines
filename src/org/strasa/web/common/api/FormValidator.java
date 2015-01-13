package org.strasa.web.common.api;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.validator.AbstractValidator;

public class FormValidator {

	public boolean isAllValid = true;
	
	public boolean isAllValidated(){
		return isAllValid;
	}
	
	
	public Validator getTextValidator(){
	    return new AbstractValidator() {
	        public void validate(ValidationContext ctx) {
	          
	        	if(ctx.getProperty().getValue().toString().isEmpty()) {
	        		addInvalidMessage(ctx, ctx.getProperty().getProperty() + " must not be empty.");
	        		System.out.println("INVALID " + ctx.getProperty().getProperty());
	        		isAllValid = false;
	        	}
	        	else{
	        		System.out.println("Valid");
	        		isAllValid = true;
	        	}
	        	
	        }
	    };
	}
	
	public static boolean hasBlankVariables(Object obj) throws IllegalArgumentException, IllegalAccessException {
	    for (Field field : obj.getClass().getDeclaredFields()) {
	        if (!field.isAccessible()) {
	            field.setAccessible(true);
	        }
	        // Danger!
	        if (field.get(obj) == null) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public boolean isAllValid() {
		return isAllValid;
	}


	public void setAllValid(boolean isAllValid) {
		this.isAllValid = isAllValid;
	}


	public static ArrayList<String> getBlankVariables(Object obj, String[] excemptions) throws IllegalArgumentException, IllegalAccessException {
	    ArrayList<String> returnVal = new ArrayList<String>();
		ArrayList<String> arrExps = new ArrayList<String>();
		arrExps.addAll(Arrays.asList(excemptions));
		for (Field field : obj.getClass().getDeclaredFields()) {
	        if (!field.isAccessible()) {
	            field.setAccessible(true);
	        }
	        // Danger!
	        if (field.get(obj) == null) {
	        	if(!arrExps.contains(field.getName())) {
	        		returnVal.add(field.getName());
	        		System.out.println(field.getName() + " INVALID");
	        	}
	        
	        }
	    }
	    return returnVal;
	}


}
