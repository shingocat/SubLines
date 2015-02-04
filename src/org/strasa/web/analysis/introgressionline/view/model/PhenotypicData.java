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

import org.analysis.rserve.manager.ILRserveManager;
import org.strasa.web.utilities.AnalysisUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import au.com.bytecode.opencsv.CSVReader;

public class PhenotypicData {

	//component
	Div specDiv;
	Combobox dataTypeCBB;
	Hbox envAnalsisHB;
	Combobox envAnalysisCBB;
	Hbox expDesignHB;
	//	Label expDesignLb;
	Combobox expDesignCBB;
	Textbox naCodeTB;
	Listbox numericLB;
	Image chooseResponseBtn;
	Image removeResponseBtn;
	Listbox responseLB;
	Image toNumericBtn;
	Image toFactorBtn;
	Listbox factorLB;
	Row genotypeRow;
	Image addGenotypeBtn;
	Textbox genotypeTB;
	Row envRow;
	Image addEnvBtn;
	Textbox envTB;
	Row blockRow;
	Image addBlockBtn;
	Textbox blockTB;
	Row replicateRow;
	Image addRepBtn;
	Textbox repTB;
	Row rowRow;
	Image addRowBtn;
	Textbox rowTB;
	Row columnRow;
	Image addColumnBtn;
	Textbox columnTB;
	Div dataDiv;
	Grid dataGrid;

	//values
	List<String> lstDataTypes = new ArrayList<String>();
	String dataType;
	List<String> lstExpDesigns = new ArrayList<String>();
	List<String> lstEnvAnalysisTypes = new ArrayList<String>();
	String expDesign;
	String envAnalysisType;
	String naCode;
	ListModelList<String> numericModel;
	ListModelList<String> responseModel;
	ListModelList<String> factorModel;
	String fileName;
	File dataFile;
	List<String> lstVarInfo;
	List<String> respvars = new ArrayList<String>();
	List<String> columnList = new ArrayList<String>();
	List<String[]> dataList = new ArrayList<String[]>();
	ILRserveManager manager = new ILRserveManager();
	int fileFormat = 1;
	String separator = "NULL";

	@NotifyChange("*")
	@Init
	public void init(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view)
	{
		setNaCode("NA");
		//		setDataType("MEAN");
		//		setExpDesign("Randomized Complete Block(RCB)");
	}

	@NotifyChange("*")
	@AfterCompose
	public void afterCompose(
			@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(component, this, false);
		this.specDiv = (Div) component.getFellow("specDiv");
		this.dataTypeCBB = (Combobox) component.getFellow("dataTypeCBB");
		this.envAnalsisHB = (Hbox) component.getFellow("envAnalysisHB");
		this.envAnalysisCBB = (Combobox) component.getFellow("envAnalysisCBB");
		this.expDesignHB = (Hbox) component.getFellow("expDesignHB");
		this.expDesignCBB = (Combobox) component.getFellow("expDesignCBB");
		this.naCodeTB = (Textbox) component.getFellow("naCodeTB");
		this.numericLB = (Listbox) component.getFellow("numericLB");
		this.chooseResponseBtn = (Image) component.getFellow("chooseResponseBtn");
		this.removeResponseBtn = (Image) component.getFellow("removeResponseBtn");
		this.responseLB = (Listbox) component.getFellow("responseLB");
		this.toNumericBtn = (Image) component.getFellow("toNumericBtn");
		this.toFactorBtn = (Image) component.getFellow("toFactorBtn");
		this.factorLB = (Listbox) component.getFellow("factorLB");
		this.genotypeRow = (Row) component.getFellow("genotypeRow");
		this.addGenotypeBtn = (Image) component.getFellow("addGenotypeBtn");
		this.genotypeTB = (Textbox) component.getFellow("genotypeTB");
		this.envRow = (Row) component.getFellow("envRow");
		this.addEnvBtn = (Image) component.getFellow("addEnvBtn");
		this.envTB = (Textbox) component.getFellow("envTB");
		this.blockRow = (Row) component.getFellow("blockRow");
		this.addBlockBtn = (Image) component.getFellow("addBlockBtn");
		this.blockTB = (Textbox) component.getFellow("blockTB");
		this.replicateRow = (Row) component.getFellow("replicateRow");
		this.addRepBtn = (Image) component.getFellow("addRepBtn");
		this.repTB = (Textbox) component.getFellow("repTB");
		this.rowRow = (Row) component.getFellow("rowRow");
		this.addRowBtn = (Image) component.getFellow("addRowBtn");
		this.rowTB = (Textbox) component.getFellow("rowTB");
		this.columnRow = (Row) component.getFellow("columnRow");
		this.addColumnBtn = (Image) component.getFellow("addColumnBtn");
		this.columnTB = (Textbox) component.getFellow("columnTB");
		this.dataDiv = (Div) component.getFellow("dataDiv");
		this.dataGrid = (Grid) component.getFellow("dataGrid");

		this.envRow.setVisible(false);
		this.blockRow.setVisible(false);
		this.replicateRow.setVisible(false);
		this.rowRow.setVisible(false);
		this.columnRow.setVisible(false);
	}

	@NotifyChange("*")
	@Command("updateDataType")
	public void updateDataType(
			@BindingParam("selectedItem") String selectedItem)
	{
		System.out.println("Data type : " + dataType);
		if(dataType.equals(lstDataTypes.get(1)))
		{
			this.envAnalsisHB.setVisible(false);
			this.expDesignHB.setVisible(false);
			this.envRow.setVisible(false);
			this.blockRow.setVisible(false);
			this.replicateRow.setVisible(false);
			this.rowRow.setVisible(false);
			this.columnRow.setVisible(false);
		} else if(dataType.equals(lstDataTypes.get(0)))
		{
			this.envAnalsisHB.setVisible(true);
			this.expDesignHB.setVisible(true);
			this.envRow.setVisible(true);
			//			this.updateExpDesign(expDesign);
		}
		HashMap<String, Object> args = new HashMap<String, Object>();
		args.put("DataType", dataType);
		BindUtils.postGlobalCommand(null, null, "getPhenoFileValidated", args);
	}

	@NotifyChange("*")
	@Command("updateEnvAnalysisType")
	public void updateEnvAnalysisType(
			@BindingParam("selectedItem") String selectedItem)
	{
		System.out.println("Env Analysis Type is " + selectedItem);
		HashMap<String, Object> args = new HashMap<String, Object>();
		args.put("EnvAnalysisType", envAnalysisType);
		BindUtils.postGlobalCommand(null, null, "getPhenoFileValidated", args);
	}

	@NotifyChange("*")
	@Command("updateExpDesign")
	public void updateExpDesign(
			@BindingParam("selectedItem") String selectedItem)
	{
		System.out.println("Experiment design is " + expDesign);
		if(expDesign.equals(lstExpDesigns.get(0)))
		{
			blockRow.setVisible(true);
			replicateRow.setVisible(false);
			rowRow.setVisible(false);
			columnRow.setVisible(false);
		} else if(expDesign.equals(lstExpDesigns.get(1)))
		{
			blockRow.setVisible(true);
			replicateRow.setVisible(false);
			rowRow.setVisible(false);
			columnRow.setVisible(false);
		} else if(expDesign.equals(lstExpDesigns.get(2)))
		{
			blockRow.setVisible(false);
			replicateRow.setVisible(false);
			rowRow.setVisible(true);
			columnRow.setVisible(true);
		} else if(expDesign.equals(lstExpDesigns.get(3)))
		{
			blockRow.setVisible(true);
			replicateRow.setVisible(true);
			rowRow.setVisible(false);
			columnRow.setVisible(false);
		} else if(expDesign.equals(lstExpDesigns.get(4)))
		{
			blockRow.setVisible(true);
			replicateRow.setVisible(false);
			rowRow.setVisible(true);
			columnRow.setVisible(true);
		} else if(expDesign.equals(lstExpDesigns.get(5)))
		{
			blockRow.setVisible(true);
			replicateRow.setVisible(true);
			rowRow.setVisible(false);
			columnRow.setVisible(false);
		} else if(expDesign.equals(lstExpDesigns.get(6)))
		{
			blockRow.setVisible(false);
			replicateRow.setVisible(true);
			rowRow.setVisible(true);
			columnRow.setVisible(true);
		}
		HashMap<String, Object> args = new HashMap<String, Object>();
		args.put("ExpDesign", expDesign);
		BindUtils.postGlobalCommand(null, null, "getPhenoFileValidated", args);
	}

	@NotifyChange("*")
	@Command("addResponse")
	public void addResponse()
	{
		chooseResponseVariable();
	}

	@NotifyChange("*")
	@Command("removeResponse")
	public void removeResponse()
	{
		unchooseResponseVariable();
	}

	@NotifyChange("*")
	@Command("toNumeric")
	public void toNumeric()
	{
		Set<String> set = this.factorModel.getSelection();
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			fileName = fileName.replace("\\", "//");
		} else {
			fileName = fileName.replace("\\", "/");
		}

		for (String selectedItem : set) {
			if (AnalysisUtils.isColumnNumeric(lstVarInfo, selectedItem)) {
				numericModel.add(selectedItem);
				factorModel.remove(selectedItem);
			} else {
				showMessage("Can't move variable. \n" + selectedItem
						+ " is not Numeric.");
			}
		}
	}

	@NotifyChange("*")
	@Command("toFactor")
	public void toFactor()
	{
		Set<String> set = this.numericModel.getSelection();
		for (String selectedItem : set) {
			this.factorModel.add(selectedItem);
			this.numericModel.remove(selectedItem);
		}
	}

	@NotifyChange("*")
	@Command("chooseVariable")
	public boolean chooseVariable(
			@BindingParam("varTextBox") Textbox varTB,
			@BindingParam("imgButton") Image imgBtn)
	{
		Set<String> set = factorModel.getSelection();
		if (varTB.getValue().isEmpty() && !set.isEmpty()) {
			for (String selectedItem : set) {
				varTB.setValue(selectedItem);
				factorModel.remove(selectedItem);
			}

			imgBtn.setSrc("/images/leftarrow_g.png");
			return true;
		} else if (!varTB.getValue().isEmpty()) {
			factorModel.add(varTB.getValue());
			varTB.setValue(null);
		}
		imgBtn.setSrc("/images/rightarrow_g.png");
		return false;
	}

	@NotifyChange("*")
	@GlobalCommand("validatePhenoFile")
	public void validatePhenoFile()
	{
		HashMap<String, Object> args = new HashMap<String, Object>();
		if(respvars.isEmpty())
		{
			showMessage("The response variable(s) could not be null!");
			return;
		} else
		{
			args.put("RespVars", respvars);
		}
		if(genotypeTB.getValue().isEmpty())
		{
			showMessage("The genotype of phenotypic tab could not be null!");
			return;
		} else
		{
			args.put("Genotype", genotypeTB.getValue());
		}
		if(getNaCode().isEmpty())
		{
			showMessage("The na.code of phenotypic tab could not be null!");
			return;
		} else
		{
			args.put("NaCode", getNaCode());
		}
		args.put("DataType", dataType);
		if(dataType.equals("RAW"))
		{
			if(envAnalysisType == null || envAnalysisType.isEmpty())
			{
				showMessage("The analysis type of phenotypic tab could not be null when data type is RAW!");
				return;
			} else
			{
				args.put("EnvAnalysisType", envAnalysisType);
			}
			if(expDesign == null || expDesign.isEmpty())
			{
				showMessage("The experimental design of phenotypic tab could not be null when data type is RAW!");
				return;
			} else
			{
				args.put("ExpDesign", expDesign);
			}
			if(envAnalysisType.equals("Multi-Environment Analysis"))
			{
				if(envTB.getValue().isEmpty())
				{
					showMessage("The environment factor of phenotypic tab could not be null when analysis type is multi-environment analysis!");
					return;
				} else
				{
					args.put("Environment", envTB.getValue());
				}
			} else
			{
				if(!envTB.getValue().isEmpty())
					args.put("Environment", envTB.getValue());
			}
			if(expDesign.equals(lstExpDesigns.get(0)))
			{
				if(blockTB.getValue().isEmpty())
				{
					showMessage("The block factor of phenotypic tab could not be null when experimental design is " 
							+ lstExpDesigns.get(0));
					return;
				} else
				{
					args.put("Block", blockTB.getValue());
				}
			} else if(expDesign.equals(lstExpDesigns.get(1)))
			{
				if(blockTB.getValue().isEmpty())
				{
					showMessage("The block factor of phenotypic tab could not be null when experimental design is " 
							+ lstExpDesigns.get(1));
				} else
				{
					args.put("Block", blockTB.getValue());
				}
			} else if(expDesign.equals(lstExpDesigns.get(2)))
			{
				if(rowTB.getValue().isEmpty())
				{
					showMessage("The row factor of phenotypic tab could not be null when experimental design is " 
							+ lstExpDesigns.get(2));
				} else
				{
					args.put("Row", rowTB.getValue());
				}
				if(columnTB.getValue().isEmpty())
				{
					showMessage("The column factor of phenotypic tab could not be null when experimental design is " 
							+ lstExpDesigns.get(2));
				} else
				{
					args.put("Column", columnTB.getValue());
				}
			} else if(expDesign.equals(lstExpDesigns.get(3)))
			{
				if(blockTB.getValue().isEmpty())
				{
					showMessage("The block factor of phenotypic tab could not be null when experimental design is " 
							+ lstExpDesigns.get(3));
				} else
				{
					args.put("Block", blockTB.getValue());
				}
				if(repTB.getValue().isEmpty())
				{
					showMessage("The rep factor of phenotypic tab could not be null when experimental design is " 
							+ lstExpDesigns.get(3));
				} else
				{
					args.put("Replicate", repTB.getValue());
				}
			} else if(expDesign.equals(lstExpDesigns.get(4)))
			{
				if(blockTB.getValue().isEmpty())
				{
					showMessage("The block factor of phenotypic tab could not be null when experimental design is " 
							+ lstExpDesigns.get(4));
				} else
				{
					args.put("Block", blockTB.getValue());
				}
				if(rowTB.getValue().isEmpty())
				{
					showMessage("The row factor of phenotypic tab could not be null when experimental design is " 
							+ lstExpDesigns.get(4));
				} else
				{
					args.put("Row", rowTB.getValue());
				}
				if(columnTB.getValue().isEmpty())
				{
					showMessage("The column factor of phenotypic tab could not be null when experimental design is " 
							+ lstExpDesigns.get(4));
				} else
				{
					args.put("Column", columnTB.getValue());
				}
			} else if(expDesign.equals(lstExpDesigns.get(5)))
			{
				if(blockTB.getValue().isEmpty())
				{
					showMessage("The block factor of phenotypic tab could not be null when experimental design is " 
							+ lstExpDesigns.get(5));
				} else
				{
					args.put("Block", blockTB.getValue());
				}
				if(repTB.getValue().isEmpty())
				{
					showMessage("The rep factor of phenotypic tab could not be null when experimental design is " 
							+ lstExpDesigns.get(5));
				} else
				{
					args.put("Replicate", repTB.getValue());
				}
			} else if(expDesign.equals(lstExpDesigns.get(6)))
			{
				if(blockTB.getValue().isEmpty())
				{
					showMessage("The block factor of phenotypic tab could not be null when experimental design is " 
							+ lstExpDesigns.get(6));
				} else
				{
					args.put("Block", blockTB.getValue());
				}
				if(rowTB.getValue().isEmpty())
				{
					showMessage("The row factor of phenotypic tab could not be null when experimental design is " 
							+ lstExpDesigns.get(6));
				} else
				{
					args.put("Row", rowTB.getValue());
				}
				if(columnTB.getValue().isEmpty())
				{
					showMessage("The column factor of phenotypic tab could not be null when experimental design is " 
							+ lstExpDesigns.get(6));
				} else
				{
					args.put("Column", columnTB.getValue());
				}
			}
		}
		BindUtils.postGlobalCommand(null, null, "getPhenoFileValidated", args);
	}

	@NotifyChange("*")
	@GlobalCommand("getPhenoFile")
	public void getPhenoFile(
			@BindingParam("FilePath") String filePath)
	{
		lstVarInfo = manager.getVariableInfo(filePath.replace("//", "/"), fileFormat, separator);
		if(lstVarInfo == null || lstVarInfo.size() == 0)
		{
			showMessage("There are problems on getting variables information!");
			return;
		}
		numericModel = AnalysisUtils.getNumericAsListModel(lstVarInfo);
		factorModel = AnalysisUtils.getFactorsAsListModel(lstVarInfo);
		responseModel = new ListModelList<String>();
		dataFile = new File(filePath);
		fileName = dataFile.getName();
		this.numericLB.setModel(numericModel);
		this.factorLB.setModel(factorModel);
		this.responseLB.setModel(responseModel);

		refreshCSVData();
		refreshGrid();
	}

	@NotifyChange("*")
	@GlobalCommand("resetPhenoFile")
	public void resetPhenoFile()
	{
		lstVarInfo.clear();;
		numericModel.clear();
		factorModel.clear();
		responseModel.clear();
		columnList.clear();
		dataList.clear();
		numericLB.getChildren().clear();
		factorLB.getChildren().clear();
		responseLB.getChildren().clear();
		if(!genotypeTB.getValue().isEmpty())
			genotypeTB.setValue(null);
		if(!envTB.getValue().isEmpty())
			envTB.setValue(null);
		if(!blockTB.getValue().isEmpty())
			blockTB.setValue(null);
		if(!repTB.getValue().isEmpty())
			repTB.setValue(null);
		if(!rowTB.getValue().isEmpty())
			rowTB.setValue(null);
		if(!columnTB.getValue().isEmpty())
			columnTB.setValue("");
		setNaCode("NA");
		if(dataGrid.getColumns() != null)
			dataGrid.getColumns().detach();
		if(dataGrid.getRows() != null)
			dataGrid.getRows().detach();
	}

	private void refreshDataDiv()
	{
		if(!dataDiv.getChildren().isEmpty())
			dataDiv.getChildren().get(0).detach();
		Include inc = new Include();
		inc.setSrc("/user/analysis/csvgrid.zul");
		inc.setParent(dataDiv);
	}

	private void chooseResponseVariable() {
		Set<String> set = numericModel.getSelection();
		for (String selectedItem : set) {
			respvars.add(selectedItem);
			responseModel.add(selectedItem);
			numericModel.remove(selectedItem);
		}
	}

	private void unchooseResponseVariable() {
		Set<String> set = responseModel.getSelection();
		System.out.println("removeResponse");
		for (String selectedItem : set) {
			respvars.remove(selectedItem);
			numericModel.add(selectedItem);
			responseModel.remove(selectedItem);
		}
	}

	@NotifyChange("*")
	public void refreshCSVData()
	{
		if(dataFile == null)
		{
			showMessage("The phenotypic file is null!");
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

	public void showMessage(String message)
	{
		Messagebox.show(message, "Warnings", Messagebox.OK, Messagebox.INFORMATION);
	}

	public List<String> getLstDataTypes() {
		if(lstDataTypes == null)
			lstDataTypes = new ArrayList<String>();
		lstDataTypes.clear();
		lstDataTypes.add("RAW");
		lstDataTypes.add("MEAN");
		return lstDataTypes;
	}

	public void setLstDataTypes(List<String> lstDataTypes) {
		this.lstDataTypes = lstDataTypes;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public List<String> getLstExpDesigns() {
		if (lstExpDesigns == null)
			lstExpDesigns = new ArrayList<String>();
		lstExpDesigns.clear();
		lstExpDesigns.add("Randomized Complete Block(RCB)");
		lstExpDesigns.add("Augmented RCB");
		lstExpDesigns.add("Augmented Latin Square");
		lstExpDesigns.add("Alpha-Lattice");
		lstExpDesigns.add("Row-Column");
		lstExpDesigns.add("Latinized Alpha-Lattice");
		lstExpDesigns.add("Latinized Row-Column");
		return lstExpDesigns;
	}

	public void setLstExpDesigns(List<String> lstExpDesigns) {
		this.lstExpDesigns = lstExpDesigns;
	}

	public String getExpDesign() {
		return expDesign;
	}

	public void setExpDesign(String expDesign) {
		this.expDesign = expDesign;
	}

	public String getNaCode() {
		return naCode;
	}

	public void setNaCode(String naCode) {
		this.naCode = naCode;
	}

	public ListModelList<String> getNumericModel() {
		return numericModel;
	}

	public void setNumericModel(ListModelList<String> numericModel) {
		this.numericModel = numericModel;
	}

	public ListModelList<String> getResponseModel() {
		return responseModel;
	}

	public void setResponseModel(ListModelList<String> responseModel) {
		this.responseModel = responseModel;
	}

	public ListModelList<String> getFactorModel() {
		return factorModel;
	}

	public void setFactorModel(ListModelList<String> factorModel) {
		this.factorModel = factorModel;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<String> getLstVarInfo() {
		return lstVarInfo;
	}

	public void setLstVarInfo(List<String> lstVarInfo) {
		this.lstVarInfo = lstVarInfo;
	}


	public List<String> getRespvars() {
		return respvars;
	}


	public void setRespvars(List<String> respvars) {
		this.respvars = respvars;
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

	public List<String> getLstEnvAnalysisTypes() {
		if(lstEnvAnalysisTypes == null)
			lstEnvAnalysisTypes = new ArrayList<String>();
		lstEnvAnalysisTypes.clear();
		lstEnvAnalysisTypes.add("Single-Environment Analysis");
		lstEnvAnalysisTypes.add("Multi-Environment Analysis");
		return lstEnvAnalysisTypes;
	}

	public void setLstEnvAnalysisTypes(List<String> lstEnvAnalysisTypes) {
		this.lstEnvAnalysisTypes = lstEnvAnalysisTypes;
	}

	public String getEnvAnalysisType() {
		return envAnalysisType;
	}

	public void setEnvAnalysisType(String envAnalysisType) {
		this.envAnalysisType = envAnalysisType;
	}

}
