package org.strasa.web.analysis.view.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.analysis.rserve.manager.PyramidedLineRserveManager;
import org.strasa.middleware.filesystem.manager.UserFileManager;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Button;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Rows;

import au.com.bytecode.opencsv.CSVReader;

public class ContrastDefault {
	Div dataGridDiv;
	
	public List<String[]> dataList = new ArrayList<String[]>();
	public List<String> columnList = new ArrayList<String>();
	String type = null;
	String fileName = null;
	File file = null;
	String factor = null;
	String levelName = null;
	String uploadedFileFolderPath = null;
	List<String> levels = null;
	UserFileManager userFileManager = null;
	PyramidedLineRserveManager manager = null;
	
	@SuppressWarnings("unchecked")
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("Arguments") HashMap<String, Object> args
			)
	{
		Selectors.wireComponents(component, this, false);
		if(args.containsKey("LevelName"))
			levelName = (String) args.get("LevelName");
		if(args.containsKey("UploadedFileFolderPath"))
			uploadedFileFolderPath = (String) args.get("UploadedFileFolderPath");
		if(args.containsKey("Levels"))
			levels = (List<String>) args.get("Levels");
		if(args.containsKey("Factor"))
			factor = (String) args.get("Factor");
		if(args.containsKey("Type"))
			type = (String) args.get("Type");
		if(args.containsKey("DefaultContrastFile"))
			fileName = (String) args.get("DefaultContrastFile");
		dataGridDiv = (Div) component.getFellow("dataGridDiv");
		buildGrid();
	}
	
	private void getDefaultContrast()
	{
		if(manager == null)
			manager = new PyramidedLineRserveManager();
		int geneNumber = 2;
		if(type.equals("Tri-Genes"))
			geneNumber = 3;
		else if(type.equals("Quadra-Genes"))
			geneNumber = 4;
		fileName = manager.getDefaultContrast(geneNumber, uploadedFileFolderPath);
	}
	
	private void buildGrid()
	{
		if(fileName == null || fileName.isEmpty())
		{
			showMessage("Could not retrieve default contrast!");
			return;
		} else
		{
			refreshGrid();
			file = new File(fileName);
			refreshCsvData();
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("Name", levelName);
			args.put("FilePath", file.getAbsolutePath());
			args.put("Factor", factor);
			BindUtils.postGlobalCommand(null, null, "getUploadedContrastFiles", args);
		}
	}
	
	private void refreshGrid()
	{
		if(!dataGridDiv.getChildren().isEmpty())
			dataGridDiv.getChildren().get(0).detach();
		Include inc = new Include();
		inc.setSrc("/user/analysis/contrastgrid.zul");
		inc.setParent(dataGridDiv);
	}
	
	private boolean refreshCsvData()
	{
		CSVReader reader;
		try{
			reader = new CSVReader(new FileReader(file.getAbsolutePath()));
			List<String[]> rawData = reader.readAll();
			columnList.clear();
			dataList.clear();
			columnList = new ArrayList<String>(Arrays.asList(rawData.get(0)));
			if(columnList.get(columnList.size() - 1).isEmpty() || 
					columnList.get(columnList.size() - 1).length() == 0)
				columnList.remove(columnList.size() - 1);
			// set the frist column header to label
			if(columnList.get(0).isEmpty())
				columnList.set(0, "Label");
				
			if(!validateColumnList())
			{
				showMessage("The header of uploaded contrast file do not match " + factor + " levels!");
				return false;
			}
			rawData.remove(0);
			dataList = new ArrayList<String[]>(rawData);
			BindUtils.postNotifyChange(null, null, this, "*");
		} catch(FileNotFoundException e)
		{
			e.printStackTrace();
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean validateColumnList()
	{
		if(columnList.size() != levels.size() + 1)
			return false;
		for(String s : levels)
		{
			if(!columnList.contains(s))
				return false;
		}
		return true;
	}
	
	
	public List<String[]> getDataList() {
		return dataList;
	}

	public void setDataList(List<String[]> dataList) {
		this.dataList = dataList;
	}

	public List<String> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}

	private void showMessage(String message)
	{
		Messagebox.show(message, "Warnings", Messagebox.OK, Messagebox.INFORMATION);
	}
}
