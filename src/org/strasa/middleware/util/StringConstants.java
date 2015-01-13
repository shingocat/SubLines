/**
 *
 * 	Builder: 	Mao Qin
 *	Email:		mqin@ymail.com
 *	Date:		2014 - Jun 29, 2014
 *	Project: 	StrasaWeb
 *  Package: 	org.strasa.middleware.util
 *	Name:	 StringConstants.java
 *	
 *
 */
package org.strasa.middleware.util;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

// This value in this class is used to define Key for HashMap
// 
public class StringConstants
{
	
//	For system value string
	public static final String BSLASH = "\\";
	public static final String FSLASH = "/";
	public static final String SYSTEM_FILE_SEPARATOR = System.getProperty("file.separator");
	public static final String SYSTEM_NAME = System.getProperty("os.name");
	public static final String ANALYSIS_OUTPUTFOLDER_PATH = (Sessions.getCurrent().getWebApp()
			.getRealPath("resultanalysis")
			+ System.getProperty("file.separator")).replace(BSLASH, FSLASH);
	public static final String DATA_PATH = System.getProperty("user.dir")
			+ System.getProperty("file.separator") + "sample_datasets"
			+ System.getProperty("file.separator");
	public static final String ROOT_PATH = Executions.getCurrent().getDesktop()
			.getWebApp().getRealPath("/WebContent");
	
//	all string value below is for segment table
	public static final String CHROMOSOME = "CHROMOSOME";
	public static final String RECURRENT_PARENT = "RECURRENT_PARENT";
	public static final String DONOR_PARENT = "DONOR_PARENT";
	public static final String GENETIC_POSITION_1 = "GENETIC_POSITION_1";
	public static final String GENETIC_POSITION_2 = "GENETIC_POSITION_2";
	public static final String GENETIC_POSITION_3 = "GENETIC_POSITION_3";
	public static final String GENETIC_POSITION_4 = "GENETIC_POSITION_4";
	public static final String GENETIC_ESTIMATED_LENGHT = "GENETIC_ESTIMATED_LENGTH";
	public static final String GENETIC_MINIMUM_LENGHT = "GENETIC_MINIMUM_LENGHT";
	public static final String GENETIC_MAXIMUM_LENGHT = "GENETIC_MAXIMUM_LENGHT";
	public static final String PHYSICAL_START = "PHYSICAL_START";
	public static final String PHYSICAL_END = "PHYSICAL_END";
	public static final String SEGMENT_ID = "SEGMENT_ID";
	public static final String SEGMENT_DESCRIPTION = "SEGMENT_DESCRIPTION";
	
	
}

