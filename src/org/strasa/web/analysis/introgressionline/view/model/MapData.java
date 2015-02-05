package org.strasa.web.analysis.introgressionline.view.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.strasa.web.utilities.AnalysisUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import au.com.bytecode.opencsv.CSVReader;

public class MapData {
	
	//component
	Div specDiv;
	Listbox variableLB;
	Image addMarImg;
	Textbox marTB;
	Image addChrImg;
	Textbox chrTB;
	Image addPosImg;
	Textbox posTB;
	Radiogroup unitsRG;
	Radio cmRadio;
	Radio bpRadio;
	Div dataDiv;
	Grid dataGrid;
	
	//values
	String marker;
	String chr;
	String pos;
	String unit;
	ListModelList<String> variables;
	List<String> columnList = new ArrayList<String>();
	List<String[]> dataList = new ArrayList<String[]>();
	File dataFile;
	
	@NotifyChange("*")
	@AfterCompose
	public void afterCompose(
			@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(component, this, false);
		specDiv = (Div) component.getFellow("specDiv");
		variableLB = (Listbox) component.getFellow("variableLB");
		addMarImg = (Image) component.getFellow("addMarImg");
		marTB = (Textbox) component.getFellow("marTB");
		addChrImg = (Image) component.getFellow("addChrImg");
		chrTB = (Textbox) component.getFellow("chrTB");
		addPosImg = (Image) component.getFellow("addPosImg");
		posTB = (Textbox) component.getFellow("posTB");
		unitsRG = (Radiogroup) component.getFellow("unitsRG");
		dataDiv = (Div) component.getFellow("dataDiv");
		dataGrid = (Grid) component.getFellow("dataGrid");
//		refreshDataDiv();
	}
	
	@NotifyChange("*")
	@Command("chooseVariable")
	public boolean chooseVariable(
			@BindingParam("varTextBox") Textbox varTextBox,
			@BindingParam("img") Image imgButton)
	{
		Set<String> set = variables.getSelection();
		if (varTextBox.getValue().isEmpty() && !set.isEmpty()) {
			for (String selectedItem : set) {
				varTextBox.setValue(selectedItem);
				variables.remove(selectedItem);
			}
			imgButton.setSrc("/images/leftarrow_g.png");
			return true;
		} else if (!varTextBox.getValue().isEmpty()) {
			variables.add(varTextBox.getValue());
			varTextBox.setValue(null);
		}
		imgButton.setSrc("/images/rightarrow_g.png");
		return false;
	}
	
	@NotifyChange("*")
	@GlobalCommand("getMapFile")
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
		refreshGrid();
		refreshVariables();
	}
	
	@NotifyChange("*")
	@GlobalCommand("validateMapTap")
	public void validateMapTap(
			@BindingParam("MapFile") String file)
	{
		if(marTB.getValue().isEmpty() || marTB.getValue().length() == 0)
		{
			showMessage("The marker is not specify on map tab!");
			return;
		}
		if(chrTB.getValue().isEmpty() || chrTB.getValue().length() == 0)
		{
			showMessage("The chromosome is not specify on map tab!");
			return;
		}
		if(posTB.getValue().isEmpty() || posTB.getValue().length() == 0)
		{
			showMessage("The position is not specify on map tab!");
			return;
		}
		if(unitsRG.getSelectedItem() == null)
		{
			showMessage("The units is not specify on map tab!");
			return;
		}
		
		HashMap<String, Object> args = new HashMap<String, Object>();
		args.put("Marker", marTB.getValue());
		args.put("Chromosome", chrTB.getValue());
		args.put("Position", posTB.getValue());
		args.put("Unit", (String)unitsRG.getSelectedItem().getValue());
		BindUtils.postGlobalCommand(null, null, "getMapFileValidated", args);
	}
	
	public void refreshCSVData()
	{
		if(dataFile == null)
		{
			showMessage("The genotypic file is null!");
			return;
		}
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
		//	refreshGenoCBB(columnList);
		} catch(FileNotFoundException e)
		{
			e.printStackTrace();
		} catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void refreshGrid()
	{
		if(!dataGrid.getChildren().isEmpty())
		{
			dataGrid.getColumns().detach();
			dataGrid.getRows().detach();
		}
		Columns cols = new Columns();
		cols.setSizable(true);
		cols.setVflex("true");
		for(String s : columnList)
		{
			Column col = new Column();
			col.setHflex("1");
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
	
	public void refreshVariables()
	{
		if(variableLB.getModel() != null)
			variableLB.setModel(new ListModelList<String>());
		variables = new ListModelList<String>();
		for(String s : columnList)
		{
			variables.add(s);
		}
		variableLB.setModel(variables);
	}
	
	public void refreshDataDiv()
	{
		if(!dataDiv.getChildren().isEmpty())
			dataDiv.getChildren().get(0).detach();
		Include inc = new Include();
		inc.setSrc("/user/analysis/csvgrid.zul");
		inc.setParent(dataDiv);
	}
	
	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getChr() {
		return chr;
	}

	public void setChr(String chr) {
		this.chr = chr;
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
	
	
	
	private void showMessage(String message)
	{
		Messagebox.show(message, "Warnings", Messagebox.OK, Messagebox.INFORMATION);
	}
}
