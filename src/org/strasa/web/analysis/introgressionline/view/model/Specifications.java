package org.strasa.web.analysis.introgressionline.view.model;

import java.util.ArrayList;
import java.util.List;

import org.strasa.middleware.model.Study;
import org.strasa.middleware.model.StudyDataSet;
import org.strasa.web.analysis.view.model.IntrogressionLineAnalysisModel;
import org.strasa.web.analysis.view.model.PyramidedLineAnalysisModel;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Textbox;

public class Specifications {
	// The component list
	private Component component;
	private Div introgressionLineSpecificationsWindow;
	private Label lblMsgUpload;
	private Combobox analysisMethodCBB;
	private Combobox studiesCombobox;
	private Combobox dataSetCombobox;
	private Button selectDataBtn;
	private Button uploadCSVBtn;
	private Button resetBtn;
	private Groupbox grpVariableData;
	private Div dataGridDiv;
	private Button runBtn;
//	for model specifications
	private Include includeVariableLst;
	private Listbox numericLB;
	private Image chooseResponseBtn;
	private Image removeResponseBtn;
	private Listbox responseLB;
	private Image toNumericBtn;
	private Image toFactorBtn;
	private Listbox factorLB;
	private Row genotypeRow;
	private Image addGenotypeBtn;
	private Textbox genotypeTB;
	private Row envRow;
	private Image addEnvBtn;
	private Textbox envTB;
	private Checkbox envFixedCB;
	private Checkbox envRandomCB;
	private Row blockRow;
	private Image addBlockBtn;
	private Textbox blockTB;
	private Row replicateRow;
	private Image addReplicateBtn;
	private Textbox replicateTB;
	private Row rowRow;
	private Image addRowBtn;
	private Textbox rowTB;
	private Row columnRow;
	private Image addColumnBtn;
	private Textbox columnTB;
//	for other options;
	private Include includeOtherOptions;
	private Checkbox descriptiveStatCB;
	private Checkbox varComponentCB;
	
//	for graph options;
	private Include includeGraphOptions;
	private Checkbox boxplotCB;
	private Checkbox histogramCB;
	private Checkbox heatmapCB;
	private Combobox fieldRowCBB;
	private Combobox fieldColumnCBB;
	private Checkbox diagnosticplotCB;
	
// The div options;
	private Div analysisMethodDiv;
	private Div analysisMethodMMADiv;
	private Div analysisMethodSMADiv;
	private Div analysisMethodChisqDiv;

	private List<Study> lstStudy;
	private Study selectedStudy;
	private List<StudyDataSet> lstStudyDataSet;
	private StudyDataSet selectedStudyDataSet;
	private boolean isFromExternalFile = false;
	private String dataFileName;
	private List<String> lstTypeOfDesign;
	private List<String> lstAnalysisMethod;
	private String analysisMethod;
	private String resultRServe;
	private IntrogressionLineAnalysisModel introgressionLineAnalysisModel;
	private List<String> lstVarNames; // not sure right now, it is used for head map plot
	
	//setting the initiate variables
	@Init
	public void init()
	{
		this.setAnalysisMethod("Multiple Marker Analysis");
	}

	@AfterCompose
	public void afterCompose(
			@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(component, this, false);
		
		introgressionLineAnalysisModel = new IntrogressionLineAnalysisModel();
		
		// wire all the component
		this.component = component;
		this.introgressionLineSpecificationsWindow = (Div) component
				.getFellow("introgressionLineSpecificationsWindow");
		this.analysisMethodCBB = (Combobox) component.getFellow("analysisMethodCBB");
		this.analysisMethodDiv = (Div) component.getFellow("analysisMethodDiv");
		this.analysisMethodMMADiv = (Div) component.getFellow("analysisMethodMMADiv");
		this.analysisMethodSMADiv = (Div) component.getFellow("analysisMethodSMADiv");
		this.analysisMethodChisqDiv = (Div) component.getFellow("analysisMethodChisqDiv");
		this.studiesCombobox = (Combobox) component
				.getFellow("studiesCombobox");
		this.dataSetCombobox = (Combobox) component
				.getFellow("dataSetCombobox");
		this.lblMsgUpload = (Label) component.getFellow("lblMsgUpload");
		this.selectDataBtn = (Button) component.getFellow("selectDataBtn");
		this.uploadCSVBtn = (Button) component.getFellow("uploadCSVBtn");
		this.resetBtn = (Button) component.getFellow("resetBtn");
		this.grpVariableData = (Groupbox) component
				.getFellow("grpVariableData");
		this.dataGridDiv = (Div) component.getFellow("dataGridDiv");
		this.includeVariableLst = (Include) component
				.getFellow("includeVariableLst");
		this.numericLB = (Listbox) this.includeVariableLst
				.getFellow("numericLB");
		this.chooseResponseBtn = (Image) this.includeVariableLst
				.getFellow("chooseResponseBtn");
		this.removeResponseBtn = (Image) this.includeVariableLst
				.getFellow("removeResponseBtn");
		this.responseLB = (Listbox) this.includeVariableLst
				.getFellow("responseLB");
		this.toNumericBtn = (Image) this.includeVariableLst
				.getFellow("toNumericBtn");
		this.toFactorBtn = (Image) this.includeVariableLst
				.getFellow("toFactorBtn");
		this.factorLB = (Listbox) this.includeVariableLst.getFellow("factorLB");
		this.genotypeRow = (Row) this.includeVariableLst
				.getFellow("genotypeRow");
		this.addGenotypeBtn = (Image) this.includeVariableLst
				.getFellow("addGenotypeBtn");
		this.genotypeTB = (Textbox) this.includeVariableLst
				.getFellow("genotypeTB");
		this.envRow = (Row) this.includeVariableLst.getFellow("envRow");
		this.addEnvBtn = (Image) this.includeVariableLst.getFellow("addEnvBtn");
		this.envTB = (Textbox) this.includeVariableLst.getFellow("envTB");
		this.envFixedCB = (Checkbox) this.includeVariableLst
				.getFellow("envFixedCB");
		this.envRandomCB = (Checkbox) this.includeVariableLst
				.getFellow("envRandomCB");
		this.blockRow = (Row) this.includeVariableLst.getFellow("blockRow");
		this.addBlockBtn = (Image) this.includeVariableLst
				.getFellow("addBlockBtn");
		this.blockTB = (Textbox) this.includeVariableLst.getFellow("blockTB");
		this.replicateRow = (Row) this.includeVariableLst
				.getFellow("replicateRow");
		this.addReplicateBtn = (Image) this.includeVariableLst
				.getFellow("addReplicateBtn");
		this.replicateTB = (Textbox) this.includeVariableLst
				.getFellow("replicateTB");
		this.rowRow = (Row) this.includeVariableLst.getFellow("rowRow");
		this.addRowBtn = (Image) this.includeVariableLst.getFellow("addRowBtn");
		this.rowTB = (Textbox) this.includeVariableLst.getFellow("rowTB");
		this.columnRow = (Row) this.includeVariableLst.getFellow("columnRow");
		this.addColumnBtn = (Image) this.includeVariableLst
				.getFellow("addColumnBtn");
		this.columnTB = (Textbox) this.includeVariableLst.getFellow("columnTB");
		this.includeOtherOptions = (Include) component
				.getFellow("includeOtherOptions");
		this.descriptiveStatCB = (Checkbox) this.includeOtherOptions
				.getFellow("descriptiveStatCB");
		this.varComponentCB = (Checkbox) this.includeOtherOptions
				.getFellow("varComponentCB");
		this.includeGraphOptions = (Include) component
				.getFellow("includeGraphOptions");
		this.boxplotCB = (Checkbox) this.includeGraphOptions
				.getFellow("boxplotCB");
		this.histogramCB = (Checkbox) this.includeGraphOptions
				.getFellow("histogramCB");
		this.heatmapCB = (Checkbox) this.includeGraphOptions
				.getFellow("heatmapCB");
		this.fieldRowCBB = (Combobox) this.includeGraphOptions
				.getFellow("fieldRowCBB");
		this.fieldColumnCBB = (Combobox) this.includeGraphOptions
				.getFellow("fieldColumnCBB");
		this.diagnosticplotCB = (Checkbox) this.includeGraphOptions
				.getFellow("diagnosticplotCB");
		this.runBtn = (Button) component.getFellow("runBtn");
	}

	@NotifyChange("*")
	@Command("updateDataSetList")
	public void updateDataSetList() {

	}

	@NotifyChange("*")
	@Command("displaySelectedDataSet")
	public void displaySelectedDataSet() {

	}

	@NotifyChange("*")
	@Command("selectFromDatabase")
	public void selectFromDatabase() {

	}

	@NotifyChange("*")
	@Command("uploadCSV")
	public void uploadCSV() {

	}
	
	@NotifyChange("*")
	@Command("updateAnalysisMethodDiv")
	public void updateAnalysisMethodDiv(@BindingParam("selectedItem")String value)
	{
		System.out.println("Analysis Method:" + value);
		if(value.equals(lstAnalysisMethod.get(0)))
		{
			if(this.analysisMethodMMADiv.isVisible())
				analysisMethodMMADiv.setVisible(false);
			if(this.analysisMethodSMADiv.isVisible())
				this.analysisMethodSMADiv.setVisible(false);
			if(!this.analysisMethodChisqDiv.isVisible())
				this.analysisMethodChisqDiv.setVisible(true);
		} else if(value.equals(lstAnalysisMethod.get(1)))
		{
			if(this.analysisMethodMMADiv.isVisible())
				analysisMethodMMADiv.setVisible(false);
			if(!this.analysisMethodSMADiv.isVisible())
				this.analysisMethodSMADiv.setVisible(true);
			if(this.analysisMethodChisqDiv.isVisible())
				this.analysisMethodChisqDiv.setVisible(false);
		} else if(value.equals(lstAnalysisMethod.get(2)))
		{
			if(!this.analysisMethodMMADiv.isVisible())
				analysisMethodMMADiv.setVisible(true);
			if(this.analysisMethodSMADiv.isVisible())
				this.analysisMethodSMADiv.setVisible(false);
			if(this.analysisMethodChisqDiv.isVisible())
				this.analysisMethodChisqDiv.setVisible(false);
		}
	}
	@NotifyChange("*")
	@Command("updateVariableList")
	public void updateVariableList(@BindingParam("selectedIndex")Integer selectedIndex)
	{
		String temp = lstTypeOfDesign.get(selectedIndex);
		if (temp.equals(lstTypeOfDesign.get(0))) {
			blockRow.setVisible(true);
			replicateRow.setVisible(false);
			rowRow.setVisible(false);
			columnRow.setVisible(false);
		} else if (temp.equals(lstTypeOfDesign.get(1))) {
			blockRow.setVisible(true);
			replicateRow.setVisible(false);
			rowRow.setVisible(false);
			columnRow.setVisible(false);
		} else if (temp.equals(lstTypeOfDesign.get(2))) {
			blockRow.setVisible(false);
			replicateRow.setVisible(false);
			rowRow.setVisible(true);
			columnRow.setVisible(true);
		} else if (temp.equals(lstTypeOfDesign.get(3))) {
			blockRow.setVisible(true);
			replicateRow.setVisible(true);
			rowRow.setVisible(false);
			columnRow.setVisible(false);
		} else if (temp.equals(lstTypeOfDesign.get(4))) {
			blockRow.setVisible(true);
			replicateRow.setVisible(false);
			rowRow.setVisible(true);
			columnRow.setVisible(true);
		} else if (temp.equals(lstTypeOfDesign.get(5))) {
			blockRow.setVisible(true);
			replicateRow.setVisible(true);
			rowRow.setVisible(false);
			columnRow.setVisible(false);
		} else if (temp.equals(lstTypeOfDesign.get(6))) {
			blockRow.setVisible(false);
			replicateRow.setVisible(true);
			rowRow.setVisible(true);
			columnRow.setVisible(true);
		}
	}
	
	@NotifyChange("*")
	@Command("validateIntrogressionLineAnalysisInputs")
	public void validateIntrogressionLineAnalysisInputs()
	{
		
	}

	@NotifyChange("*")
	@Command("addResponse")
	public void addResponse()
	{
		
	}
	
	@NotifyChange("*")
	@Command("removeResponse")
	public void removeResponse()
	{
		
	}
	
	@NotifyChange("*")
	@Command("toNumeric")
	public void toNumeric()
	{
		
	}
	
	@NotifyChange("*")
	@Command("toFactor")
	public void toFactor()
	{
		
	}
	
	@NotifyChange("*")
	@Command("chooseVariable")
	public void chooseVariable(@BindingParam("varTextBox")Textbox varTextBox, @BindingParam("imgButton") Image imgButton)
	{
		
	}
	
	@NotifyChange("*")
	@Command("updateEnvOptions")
	public void updateEnvOptions(@BindingParam("selected")Boolean selected, @BindingParam("label") String label)
	{
		
	}
	
	public List<Study> getLstStudy() {
		return lstStudy;
	}

	public void setLstStudy(List<Study> lstStudy) {
		this.lstStudy = lstStudy;
	}

	public Study getSelectedStudy() {
		return selectedStudy;
	}

	public void setSelectedStudy(Study selectedStudy) {
		this.selectedStudy = selectedStudy;
	}

	public List<StudyDataSet> getLstStudyDataSet() {
		return lstStudyDataSet;
	}

	public void setLstStudyDataSet(List<StudyDataSet> lstStudyDataSet) {
		this.lstStudyDataSet = lstStudyDataSet;
	}

	public StudyDataSet getSelectedStudyDataSet() {
		return selectedStudyDataSet;
	}

	public void setSelectedStudyDataSet(StudyDataSet selectedStudyDataSet) {
		this.selectedStudyDataSet = selectedStudyDataSet;
	}

	public boolean isFromExternalFile() {
		return isFromExternalFile;
	}

	public void setFromExternalFile(boolean isFromExternalFile) {
		this.isFromExternalFile = isFromExternalFile;
	}

	public String getDataFileName() {
		return dataFileName;
	}

	public void setDataFileName(String dataFileName) {
		this.dataFileName = dataFileName;
	}

	public List<String> getLstTypeOfDesign() {
		if (lstTypeOfDesign == null)
			lstTypeOfDesign = new ArrayList<String>();
		lstTypeOfDesign.clear();
		lstTypeOfDesign.add("Randomized Complete Block(RCB)");
		lstTypeOfDesign.add("Augmented RCB");
		lstTypeOfDesign.add("Augmented Latin Square");
		lstTypeOfDesign.add("Alpha-Lattice");
		lstTypeOfDesign.add("Row-Column");
		lstTypeOfDesign.add("Latinized Alpha-Lattice");
		lstTypeOfDesign.add("Latinized Row-Column");
		return lstTypeOfDesign;
	}
	public List<String> getLstAnalysisMethod()
	{
		if(lstAnalysisMethod == null)
			lstAnalysisMethod = new ArrayList<String>();
		lstAnalysisMethod.clear();
		lstAnalysisMethod.add("Chisq Test");
		lstAnalysisMethod.add("Single Marker Analysis");
		lstAnalysisMethod.add("Multiple Marker Analysis");
		return lstAnalysisMethod;
	}

	public void setLstTypeOfDesign(List<String> lstTypeOfDesign) {
		this.lstTypeOfDesign = lstTypeOfDesign;
	}

	public String getResultRServe() {
		return resultRServe;
	}

	public void setResultRServe(String resultRServe) {
		this.resultRServe = resultRServe;
	}

	public IntrogressionLineAnalysisModel getIntrogressionLineAnalysisModel() {
		return introgressionLineAnalysisModel;
	}

	public void setIntrogressionLineAnalysisModel(
			IntrogressionLineAnalysisModel introgressionLineAnalysisModel) {
		this.introgressionLineAnalysisModel = introgressionLineAnalysisModel;
	}
	
	public List<String> getLstVarNames() {
		return lstVarNames;
	}

	public void setLstVarNames(List<String> lstVarNames) {
		this.lstVarNames = lstVarNames;
	}

	public String getAnalysisMethod() {
		return analysisMethod;
	}

	public void setAnalysisMethod(String analysisMethod) {
		this.analysisMethod = analysisMethod;
	}
	
	
}
