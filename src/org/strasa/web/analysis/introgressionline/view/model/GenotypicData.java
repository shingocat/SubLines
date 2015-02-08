package org.strasa.web.analysis.introgressionline.view.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.strasa.middleware.util.Runtimer;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import au.com.bytecode.opencsv.CSVReader;

public class GenotypicData {

	//component
	Div specDiv;
	Combobox genoCBB;
	Textbox dpCodeTB;
	Textbox rpCodeTB;
	Textbox htCodeTB;
	Textbox naCodeTB;
	Intbox bcnIB;
	Intbox fnIB;
	Div dataDiv;
	Grid dataGrid;
	//values
	List<String> columnList = new ArrayList<String>();
	List<String[]> dataList = new ArrayList<String[]>();
	List<String> displayColumnList = new ArrayList<String>();
	List<String[]> displayDataList = new ArrayList<String[]>();
	String genoCol;
	String dpCode;
	String rpCode;
	String htCode;
	String naCode;
	Integer bcn;
	Integer fn;
	File dataFile;
	Component component;
	int displayColSize = 50;
	int displayRowSize = 20;


	@Init
	public void init(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view)
	{
		setGenoCol(null);
		setDpCode("B");
		setRpCode("A");
		setHtCode("H");
		setNaCode("NA");
		setBcn(3);
		setFn(2);
	}

	@AfterCompose
	public void afterComposer(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(component, this, false);
		this.specDiv = (Div) component.getFellow("specDiv");
		this.genoCBB = (Combobox) component.getFellow("genoCBB");
		this.dpCodeTB = (Textbox) component.getFellow("dpCodeTB");
		this.rpCodeTB = (Textbox) component.getFellow("rpCodeTB");
		this.htCodeTB = (Textbox) component.getFellow("htCodeTB");
		this.naCodeTB = (Textbox) component.getFellow("naCodeTB");;
		this.bcnIB = (Intbox) component.getFellow("bcnIB");
		this.fnIB = (Intbox) component.getFellow("fnIB");
		this.dataDiv = (Div) component.getFellow("dataDiv");
		this.dataGrid = (Grid) component.getFellow("dataGrid");
		this.component = component;
		//	refreshDataDiv();
	}

	@NotifyChange("*")
	@Command("updateGeno")
	public void updateGeno(
			@BindingParam("selectedItem")String geno)
	{
		System.out.println("Geno column " + geno);
		HashMap<String, Object> args = new HashMap<String, Object>();
		args.put("Geno",geno);
		args.put("DpCode", dpCode);
		args.put("RpCode", rpCode);
		args.put("HtCode", htCode);
		args.put("NaCode", naCode);
		args.put("BCn", bcn);
		args.put("Fn", fn);
		BindUtils.postGlobalCommand(null, null, "getGenoFileValidated", args);

	}

	@NotifyChange("*")
	@GlobalCommand("resetGenoFile")
	public void resetGenoFile()
	{
		if(!columnList.isEmpty())
			columnList.clear();
		if(!dataList.isEmpty())
			dataList.clear();
		refreshGenoCBB(null);
		refreshGrid();
		//		refreshDataDiv();
	}

	@NotifyChange("*")
	@GlobalCommand("getGenoFile")
	public void getGenoFile(
			@BindingParam("FilePath") String filePath)
	{
		if(!filePath.endsWith(".csv"))
		{
			showMessage("The file should be text-based csv format!");
			return;
		}
		dataFile = new File(filePath);
		refreshCSVData();
		//refreshGrid();
	}

	@NotifyChange("*")
	@GlobalCommand("validateGenoTap")
	public void validateGenoTap(
			@BindingParam("RefGenoFile") String refGenoFile)
	{
		if(genoCBB.getValue().isEmpty() || genoCol.length() == 0)
		{
			showMessage("The Geno is not specify on genotypic tab!");
			return;
		}
		if(dpCode.length() == 0)
		{
			showMessage("The dp.code could not be empty!");
			return;
		}
		if(rpCode.length() == 0)
		{
			showMessage("The rp.code could not be empty!");
			return;
		}
		if(htCode.length() == 0)
		{
			showMessage("The ht.code could not be empty!");
			return;
		}
		if(naCode.length() == 0)
		{
			showMessage("The na.code could not be empty!");
			return;
		}
		if(String.valueOf(bcn).length() == 0)
		{
			showMessage("The BCn could not be empty!");
			return;
		}
		if(String.valueOf(fn).length() == 0)
		{
			showMessage("The Fn could not be empty!");
			return;
		}

		HashMap<String, Object> args = new HashMap<String, Object>();
		args.put("Geno", genoCol);
		args.put("DpCode", dpCode);
		args.put("RpCode", rpCode);
		args.put("HtCode", htCode);
		args.put("NaCode", naCode);
		args.put("BCn", bcn);
		args.put("Fn", fn);
		BindUtils.postGlobalCommand(null, null, "getGenoFileValidated", args);
	}

	@NotifyChange("*")
	@Command("showGenotypicDetail")
	public void showGenotypicDetail()
	{
		if(dataFile == null)
		{
			showMessage("Do not select genotypic file!");
			return;
		}
		try {
			byte[] buffer = new byte[(int) dataFile.length()];
			FileInputStream fs = new FileInputStream(dataFile);
			fs.read(buffer);
			fs.close();
			ByteArrayInputStream is = new ByteArrayInputStream(buffer);
			AMedia fileContent = new AMedia("report", "text", "text/plain", is);
			HashMap<String, AMedia> args = new HashMap<String, AMedia>();
			args.put("DataFile", fileContent);
			Window window = (Window) Executions.createComponents("/user/analysis/introgressionline/genotypicdetail.zul",
					null, args);
			window.doModal();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@NotifyChange("*")
	public void refreshCSVData()
	{
		if(dataFile == null)
		{
			showMessage("The genotypic file is null!");
			return;
		}
		Runtimer timer = new Runtimer();
		timer.start();
		CSVReader reader = null;
		try{
			reader = new CSVReader(new FileReader(dataFile.getAbsolutePath()));
			List<String[]> rawData = reader.readAll();
			columnList.clear();
			dataList.clear();
			columnList = new ArrayList<String>(Arrays.asList(rawData.get(0)));
			if(columnList.get(columnList.size() - 1).isEmpty() || 
					columnList.get(columnList.size() - 1).length() == 0)
				columnList.remove(columnList.size() - 1);
			rawData.remove(0);
			dataList = new ArrayList<String[]>(rawData);
			//only display the first 50 column of genotypic data
			if(displayColumnList == null)
				displayColumnList = new ArrayList<String>();
			displayColumnList.clear();
			if(displayDataList == null)
				displayDataList = new ArrayList<String[]>();
				displayDataList.clear();
				if(columnList.size() >= displayColSize)
				{
					displayColumnList.addAll(columnList.subList(0, displayColSize));
					if(dataList.size() >= displayRowSize)
					{
						for(int i = 0; i < displayRowSize; i++)
						{
							String [] temp = dataList.get(i);
							displayDataList.add(Arrays.copyOfRange(temp, 0, displayColSize));
						}
					}else{
						for(int i = 0; i < dataList.size(); i++)
						{
							String [] temp = dataList.get(i);
							displayDataList.add(Arrays.copyOfRange(temp, 0, displayColSize));
						}
					}
				}else
				{
					displayColumnList.addAll(columnList);
					if(dataList.size() >= displayRowSize)
						displayDataList.addAll(dataList.subList(0, displayRowSize));
					else
						displayDataList.addAll(dataList);
				}


				//	refreshGenoCBB(columnList);
		} catch(FileNotFoundException e)
		{
			e.printStackTrace();
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		timer.end();
	}

	@NotifyChange("*")
	public void refreshGenoCBB(List<String> data)
	{
		if(!genoCBB.getChildren().isEmpty())
			genoCBB.getChildren().clear();
		ListModelList<String> lml = null;
		if(data == null)
			data = new ArrayList<String>();
		lml = new ListModelList<String>(data);
		genoCBB.setModel(lml);
	}

	public void refreshGrid()
	{
		if(dataGrid == null)
			dataGrid = (Grid) component.getFellow("dataGrid");
		if(!dataGrid.getChildren().isEmpty())
		{
			if(dataGrid.getColumns() != null)
				dataGrid.getColumns().detach();
			if(dataGrid.getRows() != null)
				dataGrid.getRows().detach();
		}
		Columns cols = new Columns();
		cols.setSizable(true);
		//cols.setHflex("true");
		for(String s : columnList)
		{
			Column col = new Column();
			col.setHflex("min");
			col.setLabel(s);
			col.setParent(cols);
		}
		Rows rows = new Rows();
		for(String [] ss : dataList)
		{
			Row row = new Row();
			for(String s : ss)
			{
				Label lb = new Label();
				lb.setValue(s);
				lb.setParent(row);
			}
			rows.appendChild(row);
		}
		dataGrid.appendChild(cols);
		dataGrid.appendChild(rows);
	}

	public String getGenoCol() {
		return genoCol;
	}

	public void setGenoCol(String genoCol) {
		this.genoCol = genoCol;
	}

	public String getDpCode() {
		return dpCode;
	}

	public void setDpCode(String dpCode) {
		this.dpCode = dpCode;
	}

	public String getRpCode() {
		return rpCode;
	}

	public void setRpCode(String rpCode) {
		this.rpCode = rpCode;
	}

	public String getHtCode() {
		return htCode;
	}

	public void setHtCode(String htCode) {
		this.htCode = htCode;
	}

	public String getNaCode() {
		return naCode;
	}

	public void setNaCode(String naCode) {
		this.naCode = naCode;
	}

	public Integer getBcn() {
		return bcn;
	}

	public void setBcn(Integer bcn) {
		this.bcn = bcn;
	}

	public Integer getFn() {
		return fn;
	}

	public void setFn(Integer fn) {
		this.fn = fn;
	}

	public List<String> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}

	public List<String[]> getDataList() {
		return dataList;
	}

	public void setDataList(List<String[]> dataList) {
		this.dataList = dataList;
	}

	public List<String> getDisplayColumnList() {
		return displayColumnList;
	}

	public void setDisplayColumnList(List<String> displayColumnList) {
		this.displayColumnList = displayColumnList;
	}

	public List<String[]> getDisplayDataList() {
		return displayDataList;
	}

	public void setDisplayDataList(List<String[]> displayDataList) {
		this.displayDataList = displayDataList;
	}

	public File getDataFile() {
		return dataFile;
	}

	public void setDataFile(File dataFile) {
		this.dataFile = dataFile;
	}

	private void showMessage(String message)
	{
		Messagebox.show(message, "Warning", Messagebox.OK, Messagebox.INFORMATION);
	}

}
