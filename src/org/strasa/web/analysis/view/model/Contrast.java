package org.strasa.web.analysis.view.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.strasa.web.utilities.FileUtilities;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
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
	Div dataGridDiv;
	
	//values
	public List<String[]> dataList;
	public List<String> columnList;
	String filename = null;
	File file = null;
	@Init
	public void init(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view)
	{
		dataList = new ArrayList<String[]>();
		columnList = new ArrayList<String>();
	}
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("filepath") String filepath
			)
	{
		System.out.println("filepath : " + filepath);
		fileNameLB = (Label) component.getFellow("fileNameLB");
		uploadBtn = (Button) component.getFellow("uploadBtn");
		resetBtn = (Button) component.getFellow("resetBtn");
//		dataGrid = (Grid) component.getFellow("dataGrid");
		dataGridDiv = (Div) component.getFellow("dataGridDiv");
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
		setFilename(name);
		// read all the content of upload to a new temp file
		file = FileUtilities.getFileFromUpload(bind, view);
		refreshDataGridDiv();
		refreshCSVData();
		this.fileNameLB.setVisible(true);
		this.uploadBtn.setVisible(false);
		this.resetBtn.setVisible(true);
	}

	@NotifyChange("*")
	@Command("reset")
	public void reset()
	{
		System.out.println("reset press");
		setFilename(null);
		columnList.clear();
		dataList.clear();
		this.fileNameLB.setVisible(false);
		this.uploadBtn.setVisible(true);
		this.resetBtn.setVisible(false);
		if(!dataGridDiv.getChildren().isEmpty())
			dataGridDiv.getFirstChild().detach();
	}

	@NotifyChange("*")
	@Command("refreshCSVData")
	public void refreshCSVData()
	{
		CSVReader reader;
		try{
			reader = new CSVReader(new FileReader(this.file.getAbsolutePath()));
			List<String[]> rawData = reader.readAll();
			columnList.clear();
			dataList.clear();
			columnList = new ArrayList<String>(Arrays.asList(rawData.get(0)));
			rawData.remove(0);
			dataList = new ArrayList<String[]>(rawData);
			refreshDataGridDiv();
		} catch(FileNotFoundException e)
		{
			e.printStackTrace();
		} catch(IOException e)
		{
			e.printStackTrace();
		}
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
