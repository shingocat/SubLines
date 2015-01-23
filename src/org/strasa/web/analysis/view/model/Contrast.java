package org.strasa.web.analysis.view.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.input.ReaderInputStream;
import org.strasa.middleware.filesystem.manager.UserFileManager;
import org.strasa.web.utilities.FileUtilities;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import au.com.bytecode.opencsv.CSVReader;

public class Contrast {
	//component
	Label fileNameLB;
	Button uploadBtn;
	Button resetBtn;
	Grid dataGrid;
	Columns columns;
	Rows rows;
	Div dataGridDiv;
	
	public List<String[]> dataList = new ArrayList<String[]>();
	public List<String> columnList = new ArrayList<String>();
	String filename = null;
	File file = null;
	File tempFile = null;
	String envname = null;
	String uploadedFileFolderPath = null;
	List<String> genosOnEnv = null;
	UserFileManager userFileManager = null;
	
	@SuppressWarnings("unchecked")
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("Arguments") HashMap<String, Object> args
			)
	{
		Selectors.wireComponents(component, this, false);
		if(args.containsKey("EnvName"))
			envname = (String) args.get("EnvName");
		if(args.containsKey("UploadedFileFolderPath"))
			uploadedFileFolderPath = (String) args.get("UploadedFileFolderPath");
		if(args.containsKey("GenosOnEnv"))
			genosOnEnv = (List<String>) args.get("GenosOnEnv");
		fileNameLB = (Label) component.getFellow("fileNameLB");
		uploadBtn = (Button) component.getFellow("uploadBtn");
		resetBtn = (Button) component.getFellow("resetBtn");
		dataGridDiv = (Div) component.getFellow("dataGridDiv");
		dataGrid = (Grid) component.getFellow("dataGrid");
//		rows = (Rows) component.getFellow("rows");
//		columns = (Columns) component.getFellow("columns");
	}

	@NotifyChange("*")
	@Command("upload")
	public void upload(@ContextParam(ContextType.BIND_CONTEXT) BindContext bind,
			@ContextParam(ContextType.VIEW)Component view)
	{
		UploadEvent event = (UploadEvent) bind.getTriggerEvent();
		String name = event.getMedia().getName();
		if(!name.endsWith(".csv"))
		{
			Messagebox.show("Error: File must be a text-based csv format", "Upload Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		// read all the content of upload to a new temp file
//		tempFile = FileUtilities.getFileFromUpload(bind, view);
		if(tempFile == null)
		{
			try{
				tempFile = File.createTempFile(name, ".tmp");
			} catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		InputStream in = event.getMedia().isBinary() ? event.getMedia().getStreamData() : 
			new ReaderInputStream(event.getMedia().getReaderData());
		FileUtilities.uploadFile(tempFile.getAbsolutePath(), in);
		if(tempFile == null)
			return;
//		refreshDataGridDiv();
		if(!refreshCSVData())
			return;
		buildGrid();
		BindUtils.postNotifyChange(null, null, this, "*");
//		refreshDataGridDiv();

//		userFileManager = new UserFileManager();
//		String filepath = userFileManager.uploadContrastFileForAnalysis(name, tempFile, uploadedFileFolderPath, envname);
//		file = new File(filepath);
		setFilename(name);
		fileNameLB.setVisible(true);
		uploadBtn.setVisible(false);
		resetBtn.setVisible(true);
	}

	@NotifyChange("*")
	@Command("reset")
	public void reset()
	{
		setFilename(null);
		if(columnList != null && !columnList.isEmpty())
			columnList.clear();
		if(dataList != null && !dataList.isEmpty())
			dataList.clear();
		if(dataGrid.getColumns() != null)
			dataGrid.getColumns().detach();
		if(dataGrid.getRows() != null)
			dataGrid.getRows().detach();
		this.fileNameLB.setVisible(false);
		this.uploadBtn.setVisible(true);
		this.resetBtn.setVisible(false);
//		if(!dataGridDiv.getChildren().isEmpty())
//			dataGridDiv.getFirstChild().detach();
	}

	@NotifyChange("*")
	@Command("refreshCSVData")
	public boolean refreshCSVData()
	{
		CSVReader reader;
		try{
			reader = new CSVReader(new FileReader(tempFile.getAbsolutePath()));
			List<String[]> rawData = reader.readAll();
			columnList.clear();
			dataList.clear();
			columnList = new ArrayList<String>(Arrays.asList(rawData.get(0)));
			if(!validateColumnList())
			{
				Messagebox.show("The upload contrast genotype name do not match the raw data!",
						"Error", Messagebox.OK, Messagebox.ERROR);
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
	
	public void buildGrid()
	{
		if(!columnList.isEmpty())
		{
			columns = new Columns();
			for(String s : columnList)
			{
				Column col = new Column();
				col.setLabel(s);
				col.setHflex("1");
				columns.appendChild(col);
			}
		}
		if(!dataList.isEmpty())
		{
			rows = new Rows();
			for(String [] ss : dataList)
			{
				Row row = new Row();
				for(String s : ss)
				{
					Label label = new Label();
					label.setValue(s);
					row.appendChild(label);
				}
				rows.appendChild(row);
			}
		}
		dataGrid.appendChild(columns);
		dataGrid.appendChild(rows);
		dataGrid.setSizedByContent(true);
	}
	//hard coding for grid displaing contrast
	public void refreshDataGridDiv()
	{
		if(!dataGridDiv.getChildren().isEmpty())
			dataGridDiv.getFirstChild().detach();
		Include inc = new Include();
		inc.setSrc("/user/analysis/contrastgrid.zul");
		dataGridDiv.appendChild(inc);
	}
	
	public boolean validateColumnList()
	{
		if(columnList.size() != genosOnEnv.size() + 1)
			return false;
		for(String s : genosOnEnv)
		{
			if(!columnList.contains(s))
				return false;
		}
		return true;
	}

	public void setFilename(String filename)
	{
		this.filename = filename;
	}

	public String getFilename()
	{
		return filename;
	}

	public void setDataList(List<String[]> dataList)
	{
		this.dataList = dataList;
	}

	public List<String[]> getDataList()
	{
		return this.dataList;
	}

	public void setColumnList(List<String>  columnList)
	{
		this.columnList = columnList;
	}
	public List<String> getColumnList()
	{
		return this.columnList;
	}
}
