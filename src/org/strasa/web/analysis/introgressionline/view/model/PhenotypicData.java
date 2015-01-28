package org.strasa.web.analysis.introgressionline.view.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.strasa.web.utilities.AnalysisUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class PhenotypicData {
	
	//component
	Div specDiv;
	Combobox dataTypeCBB;
	Combobox expDesignCBB;
	Textbox naCodeTB;
	Listbox numericLB;
	Image chooseResponseBtn;
	Image removeResponseBtn;
	Listbox responseLB;
	Image toNumericBtn;
	Image toFactorBtn;
	Listbox factorLB;
	Image addGenotypeBtn;
	Textbox genotypeTB;
	Image addEnvBtn;
	Textbox envTB;
	Image addBlockBtn;
	Textbox blockTB;
	Image addRepBtn;
	Textbox repTB;
	Image addRowBtn;
	Textbox rowTB;
	Image addColumnBtn;
	Textbox columnTB;
	Div dataDiv;
	
	//values
	List<String> lstDataTypes = new ArrayList<String>();
	String dataType;
	List<String> lstExpDesigns = new ArrayList<String>();
	String expDesign;
	String naCode;
	ListModelList<String> numericModel;
	ListModelList<String> responseModel;
	ListModelList<String> factorModel;
	String fileName;
	List<String> lstVarInfo;
	List<String> respvars = new ArrayList<String>();
	List<String> columnList = new ArrayList<String>();
	List<String[]> dataList = new ArrayList<String[]>();
	
	@NotifyChange("*")
	@Init
	public void init(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view)
	{
		setDataType("MEAN");
		setExpDesign("Randomized Complete Block(RCB)");
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
		this.expDesignCBB = (Combobox) component.getFellow("expDesignCBB");
		this.naCodeTB = (Textbox) component.getFellow("naCodeTB");
		this.numericLB = (Listbox) component.getFellow("numericLB");
		this.chooseResponseBtn = (Image) component.getFellow("chooseResponseBtn");
		this.removeResponseBtn = (Image) component.getFellow("removeResponseBtn");
		this.responseLB = (Listbox) component.getFellow("responseLB");
		this.toNumericBtn = (Image) component.getFellow("toNumericBtn");
		this.toFactorBtn = (Image) component.getFellow("toFactorBtn");
		this.factorLB = (Listbox) component.getFellow("factorLB");
		this.addGenotypeBtn = (Image) component.getFellow("addGenotypeBtn");
		this.genotypeTB = (Textbox) component.getFellow("genotypeTB");
		this.addEnvBtn = (Image) component.getFellow("addEnvBtn");
		this.envTB = (Textbox) component.getFellow("envTB");
		this.addBlockBtn = (Image) component.getFellow("addBlockBtn");
		this.blockTB = (Textbox) component.getFellow("blockTB");
		this.addRepBtn = (Image) component.getFellow("addRepBtn");
		this.repTB = (Textbox) component.getFellow("repTB");
		this.addRowBtn = (Image) component.getFellow("addRowBtn");
		this.rowTB = (Textbox) component.getFellow("rowTB");
		this.addColumnBtn = (Image) component.getFellow("addColumnBtn");
		this.columnTB = (Textbox) component.getFellow("columnTB");
		this.dataDiv = (Div) component.getFellow("dataDiv");
		refreshDataDiv();
	}
	
	@NotifyChange("*")
	@Command("updateDataType")
	public void updateDataType(
			@BindingParam("selectedItem") String selectedItem)
	{
		
	}
	
	@NotifyChange("*")
	@Command("updateExpDesign")
	public void updateExpDesign(
			@BindingParam("selectedItem") String selectedItem)
	{
		
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
}
