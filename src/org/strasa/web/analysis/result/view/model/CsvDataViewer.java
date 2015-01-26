package org.strasa.web.analysis.result.view.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;

import au.com.bytecode.opencsv.CSVReader;

public class CsvDataViewer {

	Div divDatagrid;

	private String filePath;
	private File csvFile;
	private List<String> columnList = new ArrayList<String>();
	private List<String[]> dataList = new ArrayList<String[]>();
	private String name;


	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("Name") String name,
			@ExecutionArgParam("FilePath") String filePath
			) {
		System.out.println("csv name " + name);
		System.out.println("csv filepath " + filePath);
		if(filePath == null)
			return;
		this.name = name;
		this.filePath = filePath;
		divDatagrid = (Div) view.getFellow("datagrid");
		csvFile = new File(Executions.getCurrent().getDesktop().getWebApp().getRealPath(filePath));
		initDataGrid();
		refreshCSVData();
	}

	public void initDataGrid() {
		if (!divDatagrid.getChildren().isEmpty())
			divDatagrid.getFirstChild().detach();
		Include incCSVData = new Include();
		incCSVData.setSrc("/user/analysis/csvgrid.zul");
		incCSVData.setParent(divDatagrid);
	}

	@Command
	public void exportRowData(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx,
		@ContextParam(ContextType.VIEW) Component view) {
		FileUtilities.exportData(getColumnList(),getDataList(),name);
	}

	@NotifyChange("*")
	@Command("refreshCSVData")
	public boolean refreshCSVData()
	{
		CSVReader reader;
		try{
			reader = new CSVReader(new FileReader(csvFile.getAbsolutePath()));
			List<String[]> rawData = reader.readAll();
			columnList.clear();
			dataList.clear();
			columnList = new ArrayList<String>(Arrays.asList(rawData.get(0)));
			if(columnList.get(columnList.size() - 1).isEmpty() || 
					columnList.get(columnList.size() - 1).length() == 0)
				columnList.remove(columnList.size() - 1);
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
