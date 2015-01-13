package org.strasa.web.utilities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.input.ReaderInputStream;
import org.spring.security.model.SecurityUtil;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

public class AnalysisUtils {

	private static final String FILE_SEPARATOR = System
			.getProperty("file.separator");

	public static ListModelList<String> getNumericAsListModel(
			List<String> lstVarInfo) {
		// TODO Auto-generated method stub
		ListModelList<String> modelList = new ListModelList<String>();
		for (String s : lstVarInfo) {
			if (s.contains(":Numeric"))
				modelList.add(s.split(":")[0]);
		}
		return modelList;
	}

	public static ListModelList<String> getFactorsAsListModel(
			List<String> lstVarInfo) {
		// TODO Auto-generated method stub
		ListModelList<String> modelList = new ListModelList<String>();
		for (String s : lstVarInfo) {
			if (s.contains(":Factor"))
				modelList.add(s.split(":")[0]);
		}
		return modelList;
	}

	public static boolean isColumnNumeric(List<String> lstVarInfo,
			String selectedItem) {
		// TODO Auto-generated method stub
		for (String s : lstVarInfo) {
			if (s.split(":")[0].equals(selectedItem) && s.contains("Numeric"))
				return true;
		}
		return false;
	}

	public static String getOutputFolderName(String fileName) {
		// TODO Auto-generated method stub
		return new File(fileName).getName() + "(" + getTimeStamp() + ")";
	}

	public static String getTimeStamp() {
		// TODO Auto-generated method stub
		Calendar now = Calendar.getInstance();
		return Long.toString(now.getTimeInMillis());
	}

	// Modify By QIN MAO
	public static String createOutputFolder(String fileName, String analysisType) {
		// TODO Auto-generated method stub
		String dataFileName = fileName.replaceAll(".csv", "");
		dataFileName = dataFileName.replaceAll(".tmp", "");
		String userFolderPath = null;
		
		if (analysisType.equals("ssa")) {
			userFolderPath = Sessions.getCurrent().getWebApp()
					.getRealPath("resultanalysis")
					+ FILE_SEPARATOR
					+ SecurityUtil.getDbUser().getUsername()
					+ FILE_SEPARATOR + "Single_Site";
		} else if (analysisType.equals("ssslAnalysis")) {
			userFolderPath = Sessions.getCurrent().getWebApp().getRealPath("resultanalysis") 
					+ FILE_SEPARATOR + SecurityUtil.getDbUser().getUsername()
					+ FILE_SEPARATOR + "SSSL_Analysis";
		}

		String outputStudyPath = userFolderPath + FILE_SEPARATOR
				+ getOutputFolderName(dataFileName) + FILE_SEPARATOR;

		if (createFolder(userFolderPath)) {
			createFolder(outputStudyPath);
		}
		return outputStudyPath;
	}

	public static boolean createFolder(String folderPath) {
		File outputFolder = new File(folderPath);

		if (outputFolder.exists())
			return true;
		return outputFolder.mkdir();
	}

	public static ArrayList<String> getVarNames(List<String> lstVarInfo) {
		// TODO Auto-generated method stub
		ArrayList<String> modelList = new ArrayList<String>();
		for (String s : lstVarInfo) {
			modelList.add(s.split(":")[0]);
		}
		return modelList;
	}

	public static String getoutputFolderPath(String filenamePath) {
		// TODO Auto-generated method stub
		System.out.println("path:" + filenamePath);
		return Sessions.getCurrent().getWebApp().getRealPath("") + filenamePath;
	}

	public static ListModelList<String> toListModelList(String[] stringArray) {
		// TODO Auto-generated method stub
		ListModelList<String> modelList = new ListModelList<String>();
		for (String s : stringArray) {
			System.out.println(s);
			modelList.add(s);
		}
		return modelList;
	}

	public static String[] getItemsFromListAsStringAyrray(Listbox controlsLb) {
		// TODO Auto-generated method stub
		ArrayList<String> stringList = new ArrayList<String>();
		for (Listitem li : controlsLb.getItems()) {
			stringList.add(li.getLabel());
		}

		return stringList.toArray(new String[stringList.size()]);
	}
}
