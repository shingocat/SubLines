package org.strasa.web.analysis.sssl.view.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.analysis.rserve.manager.RServeManager;
import org.analysis.rserve.manager.SSSLRserveManager;
import org.apache.commons.io.input.ReaderInputStream;
import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.filesystem.manager.UserFileManager;
import org.strasa.middleware.manager.BrowseStudyManagerImpl;
import org.strasa.middleware.manager.StudyDataColumnManagerImpl;
import org.strasa.middleware.manager.StudyDataSetManagerImpl;
import org.strasa.middleware.manager.StudyManagerImpl;
import org.strasa.middleware.model.Study;
import org.strasa.middleware.model.StudyDataColumn;
import org.strasa.middleware.model.StudyDataSet;
import org.strasa.middleware.util.StringConstants;
import org.strasa.web.analysis.view.model.SSSLAnalysisModel;
import org.strasa.web.utilities.AnalysisUtils;
import org.strasa.web.utilities.FileUtilities;
import org.zkoss.bind.BindContext;
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
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.impl.InputElement;

import au.com.bytecode.opencsv.CSVReader;

public class Specifications {

	private Component component;
	private Grid gridManuallyOnGenotype;
	private Grid gridManuallyOnEnv;
	private Columns columnsManuallyOnGenotype;
	private Columns columnsManuallyOnEnv;
	private Rows rowsManuallyOnGenotype;
	private Rows rowsManuallyOnEnv;
	// select data component;
	private Div SSSLSpecificationsWindow;
	private Combobox studiesCombobox;
	private Combobox dataSetCombobox;
	private Label lblMsgUpload;
	private Button selectDataBtn;
	private Button uploadCSVBtn;
	private Button resetBtn;

	// data preview component;
	private Groupbox grpVariableData;
	private Div dataGridDiv;

	// Tab panel components;
	private Tabbox tabBox;
	private Tabs tabs;
	private Tab modelSpecificationsTab;
	private Tab otherOptionsTab;
	private Tab graphOptionsTab;
	private Tabpanels tabpanels;
	private Tabpanel modelSpecificationsTP;
	private Tabpanel otherOptionsTP;
	private Tabpanel graphOptionsTP;

	// modelSpecificationsTabpanel;
	private Combobox lstTypeOfEnvCBB;
	private Combobox lstTypeOfDesignCBB;
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

	// otherOptionTabpanel;
	private Include includeOtherOptions;
	private Checkbox descriptiveStatCB;
	private Checkbox varComponentCB;
	private Doublebox alphaLevelDB;
	private Div alphaLevelDBDiv;
	private Checkbox compareWithControlCB;
	private Combobox controlCBB;
	private Checkbox acrossEnvCB;
	private Div acrossEnvCBDiv;
	private Div specifiedContrastOnGenotypeDiv;
	private Button contrastFromFileOnGenotypeBtn;
	private Button contrastByManuallyOnGenotypeBtn;
	private Button contrastResetOnGenotypeBtn;
	private Div contrastGridOnGenotypeDiv;
	private Div specifiedContrastOnEnvDiv;
	private Button contrastFromFileOnEnvBtn;
	private Button contrastByManuallyOnEnvBtn;
	private Button contrastResetOnEnvBtn;
	private Div contrastGridOnEnvDiv;
	private Div genotypeByEnvDiv;
	private Checkbox finlayWilkinsonModelCB;
	private Checkbox shuklaModelCB;
	private Checkbox ammiModelCB;
	private Checkbox ggeModelCB;

	// graphOptionTabpanel;
	private Include includeGraphOptions;
	private Div singleEnvOnGraphDiv;
	private Checkbox boxplotSingleEnvCB;
	private Checkbox histogramSingleEnvCB;
	private Checkbox diagnosticplotSingleEnvCB;
	private Div acrossEnvOnGraphDiv;
	private Checkbox boxplotAcrossEnvCB;
	private Checkbox histogramAcrossEnvCB;
	private Checkbox diagnosticplotAcrossEnvCB;
	private Div multiplicativeModelOnGraphDiv;
	private Div ammiGraphicOptionsDiv;
	private Checkbox responsePlotCB;
	private Checkbox adaptationMapCB;
	private Checkbox ammiBiplotCB;
	private Checkbox ammiBiplotPC1VsPC2CB;
	private Checkbox ammiBiplotPC1VsPC3CB;
	private Checkbox ammiBiplotPC2VsPC3CB;
	private Div ggeGraphicOptionsDiv;
	private Checkbox ggeBiplotSymmetricViewCB;
	private Checkbox ggeBiplotEnvironmentViewCB;
	private Checkbox ggeBiplotGenotypeViewCB;

	// run button
	private Button runBtn;

	//basic arguments
	private SSSLRserveManager ssslRServeManager = new SSSLRserveManager();
	private StudyManagerImpl studyManagerImpl = new StudyManagerImpl();
	private StudyDataSetManagerImpl studyDataSetManagerImpl = new StudyDataSetManagerImpl();
	private BrowseStudyManagerImpl browseStudyManagerImpl = new BrowseStudyManagerImpl();
	private UserFileManager userFileManager = new UserFileManager();
	private Study selectedStudy;
	private List<Study> lstStudy;
	private StudyDataSet selectedStudyDataSet;
	private List<StudyDataSet> lstStudyDataSet;
	private String dataFileName;
	private boolean isVariableDataVisible;
	private List<String> lstTypeOfDesign;
	private List<String> lstTypeOfAnalysisEnv;
	private String designType;
	private String analysisEnvType;
	private SSSLAnalysisModel ssslAnalysisModel = new SSSLAnalysisModel();
	private String resultRServe;
	// column list from raw data
	private List<String> columnList = new ArrayList<String>(); 
	// real value from raw data
	private List<String[]> dataList = new ArrayList<String[]>(); 

	private File uploadedFile;
	private String fileName; // for the analysis raw data

	private List<String> lstVarInfo;
	private List<String> lstVarNames;
	private int fileFormat = 1;
	private String separator = "NULL";

	private ListModelList<String> numericModel;
	private ListModelList<String> responseModel;
	private ListModelList<String> factorModel;
	private ListModelList<String> genotypeLevelsModel;

	private List<String> respvars = new ArrayList<String>();
	private String[] environmentLevels = null;
	private String[] genotypeLevels = null;

	private String errorMessage = null;

	private Double alphalevel = null;
//	private Boolean isAcrossEnv = true;
	private String uploadedFileFolderPath = null;
	private HashMap<String, String> genoContrastFiles = new HashMap<String, String>();
	private HashMap<String, String> envContrastFiles = new HashMap<String, String>();
	private Boolean isSpecifyGenoContrast = false;
	private Boolean isSpecifyEnvContrsat = false;

	@Init
	public void init() {
		setAnalysisEnvType("Multi-Environment");
		setDesignType("Randomized Complete Block(RCB)");
		setAlphalevel(0.05);
	}

	@AfterCompose
	public void afterCompose(
			@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(component, this, false);

		// wire all the component
		this.component = component;
		this.SSSLSpecificationsWindow = (Div) component
				.getFellow("SSSLSpecificationsWindow");
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
		this.tabBox = (Tabbox) component.getFellow("tabBox");
		this.tabs = (Tabs) component.getFellow("tabs");
		this.modelSpecificationsTab = (Tab) component
				.getFellow("modelSpecificationsTab");
		this.otherOptionsTab = (Tab) component.getFellow("otherOptionsTab");
		this.graphOptionsTab = (Tab) component.getFellow("graphOptionsTab");
		this.tabpanels = (Tabpanels) component.getFellow("tabpanels");
		this.modelSpecificationsTP = (Tabpanel) component
				.getFellow("modelSpecificationsTP");
		this.otherOptionsTP = (Tabpanel) component.getFellow("otherOptionsTP");
		this.graphOptionsTP = (Tabpanel) component.getFellow("graphOptionsTP");
		this.lstTypeOfEnvCBB = (Combobox) component
				.getFellow("lstTypeOfAnalysisEnvCBB");
		this.lstTypeOfDesignCBB = (Combobox) component
				.getFellow("lstTypeOfDesignCBB");
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
		this.alphaLevelDB = (Doublebox) this.includeOtherOptions
				.getFellow("alphaLevelDB");
		this.alphaLevelDBDiv = (Div) this.includeOtherOptions.getFellow("alphaLevelDBDiv");
		this.acrossEnvCB = (Checkbox) this.includeOtherOptions.getFellow("acrossEnvCB");
		this.acrossEnvCBDiv = (Div) this.includeOtherOptions.getFellow("acrossEnvCBDiv");
		this.compareWithControlCB = (Checkbox) this.includeOtherOptions
				.getFellow("compareWithControlCB");
		this.controlCBB = (Combobox) this.includeOtherOptions
				.getFellow("controlCBB");
		this.specifiedContrastOnGenotypeDiv = (Div) includeOtherOptions
				.getFellow("specifiedContrastOnGenotypeDiv");
		this.contrastFromFileOnGenotypeBtn = (Button) includeOtherOptions
				.getFellow("contrastFromFileOnGenotypeBtn");
		this.contrastByManuallyOnGenotypeBtn = (Button) includeOtherOptions
				.getFellow("contrastByManuallyOnGenotypeBtn");
		this.contrastResetOnGenotypeBtn = (Button) includeOtherOptions
				.getFellow("contrastResetOnGenotypeBtn");
		this.contrastGridOnGenotypeDiv = (Div) includeOtherOptions
				.getFellow("contrastGridOnGenotypeDiv");
		this.specifiedContrastOnEnvDiv = (Div) includeOtherOptions
				.getFellow("specifiedContrastOnEnvDiv");
		this.contrastFromFileOnEnvBtn = (Button) includeOtherOptions
				.getFellow("contrastFromFileOnEnvBtn");
		this.contrastByManuallyOnEnvBtn = (Button) includeOtherOptions
				.getFellow("contrastByManuallyOnEnvBtn");
		this.contrastResetOnEnvBtn = (Button) includeOtherOptions
				.getFellow("contrastResetOnEnvBtn");
		this.contrastGridOnEnvDiv = (Div) includeOtherOptions
				.getFellow("contrastGridOnEnvDiv");
		this.genotypeByEnvDiv = (Div) includeOtherOptions
				.getFellow("genotypeByEnvDiv");
		this.finlayWilkinsonModelCB = (Checkbox) includeOtherOptions
				.getFellow("finlayWilkinsonModelCB");
		this.shuklaModelCB = (Checkbox) includeOtherOptions
				.getFellow("shuklaModelCB");
		this.ammiModelCB = (Checkbox) includeOtherOptions
				.getFellow("ammiModelCB");
		this.ggeModelCB = (Checkbox) includeOtherOptions
				.getFellow("ggeModelCB");
		this.includeGraphOptions = (Include) component
				.getFellow("includeGraphOptions");
		this.singleEnvOnGraphDiv = (Div) includeGraphOptions
				.getFellow("singleEnvOnGraphDiv");
		this.boxplotSingleEnvCB = (Checkbox) includeGraphOptions
				.getFellow("boxplotSingleEnvCB");
		this.histogramSingleEnvCB = (Checkbox) includeGraphOptions
				.getFellow("histogramSingleEnvCB");
		this.diagnosticplotSingleEnvCB = (Checkbox) includeGraphOptions
				.getFellow("diagnosticplotSingleEnvCB");
		this.acrossEnvOnGraphDiv = (Div) includeGraphOptions
				.getFellow("acrossEnvOnGraphDiv");
		this.boxplotAcrossEnvCB = (Checkbox) includeGraphOptions
				.getFellow("boxplotAcrossEnvCB");
		this.histogramAcrossEnvCB = (Checkbox) includeGraphOptions
				.getFellow("histogramAcrossEnvCB");
		this.diagnosticplotAcrossEnvCB = (Checkbox) includeGraphOptions
				.getFellow("diagnosticplotAcrossEnvCB");
		this.multiplicativeModelOnGraphDiv = (Div) includeGraphOptions
				.getFellow("multiplicativeModelOnGraphDiv");
		this.ammiGraphicOptionsDiv = (Div) includeGraphOptions.getFellow("ammiGraphicOptionsDiv");
		this.responsePlotCB = (Checkbox) includeGraphOptions
				.getFellow("responsePlotCB");
		this.adaptationMapCB = (Checkbox) includeGraphOptions
				.getFellow("adaptationMapCB");
		this.ammiBiplotCB = (Checkbox) includeGraphOptions
				.getFellow("ammiBiplotCB");
		this.ammiBiplotPC1VsPC2CB = (Checkbox) includeGraphOptions
				.getFellow("ammiBiplotPC1VsPC2CB");
		this.ammiBiplotPC1VsPC3CB = (Checkbox) includeGraphOptions
				.getFellow("ammiBiplotPC1VsPC3CB");
		this.ammiBiplotPC2VsPC3CB = (Checkbox) includeGraphOptions
				.getFellow("ammiBiplotPC2VsPC3CB");
		this.ggeGraphicOptionsDiv = (Div) includeGraphOptions.getFellow("ggeGraphicOptionsDiv");
		this.ggeBiplotSymmetricViewCB = (Checkbox) includeGraphOptions
				.getFellow("ggeBiplotSymmetricViewCB");
		this.ggeBiplotEnvironmentViewCB = (Checkbox) includeGraphOptions
				.getFellow("ggeBiplotEnvironmentViewCB");
		this.ggeBiplotGenotypeViewCB = (Checkbox) includeGraphOptions
				.getFellow("ggeBiplotGenotypeViewCB");
		this.runBtn = (Button) component.getFellow("runBtn");


		if (columnList != null && !columnList.isEmpty()) {
			columnList.clear();
			dataList.clear();
		}

	}

	@NotifyChange("*")
	@Command("updateDataSetList")
	public void updateDataSetList() {
		dataSetCombobox.setSelectedItem(null);
		dataSetCombobox.setVisible(false);
		List<StudyDataSet> dataSet = studyDataSetManagerImpl
				.getDataSetsByStudyId(selectedStudy.getId());
		if (!dataSet.isEmpty()) {
			dataSetCombobox.setVisible(true);
			setLstStudyDataSet(dataSet);
			BindUtils.postNotifyChange(null, null, this, "*");
		} else {
			showMessage("Please choose a different study");
		}
	}

	@NotifyChange("*")
	@Command("displaySelectedDataSet")
	public void displaySelectedDataSet(
			@ContextParam(ContextType.BIND_CONTEXT) BindContext bindContext,
			@ContextParam(ContextType.VIEW) Component view) {
		// clear before status of columnList and dataList
		if(columnList != null && ! columnList.isEmpty())
		{
			columnList.clear();
			dataList.clear();
		}
		reloadCsvGrid();
		selectDataBtn.setVisible(false);
		studiesCombobox.setVisible(false);
		resetBtn.setVisible(true);
		uploadCSVBtn.setVisible(false);
		List<HashMap<String, String>> toreturn = browseStudyManagerImpl
				.getStudyData(selectedStudy.getId(),
						selectedStudyDataSet.getDatatype(),
						selectedStudyDataSet.getId());
		List<StudyDataColumn> columns = new StudyDataColumnManagerImpl()
		.getStudyDataColumnByStudyId(selectedStudy.getId(),
				selectedStudyDataSet.getDatatype(),
				selectedStudyDataSet.getId());
		for (StudyDataColumn d : columns) {
			columnList.add(d.getColumnheader());
		}
		for (HashMap<String, String> rec : toreturn) {
			ArrayList<String> newRow = new ArrayList<String>();
			for (StudyDataColumn d : columns) {
				String value = rec.get(d.getColumnheader());
				newRow.add(value);
			}
			dataList.add(newRow.toArray(new String[newRow.size()]));
		}
		//set fileName to study.name_dataset.tile
		fileName = selectedStudy.getName() + "_" + selectedStudyDataSet.getTitle().replaceAll(" ", "");
		// set BASE_FOLDER to /UPLOADS/UserId_DatasetId/Userid_DatasetId
		File BASE_FOLDER = new File(UserFileManager.buildUserPath(selectedStudy.getUserid(), selectedStudy.getId(), fileName));
		if (!BASE_FOLDER.exists())
			BASE_FOLDER.mkdirs();
		String createFile = BASE_FOLDER.getAbsolutePath() + File.separator + fileName + "(Dataset).csv";
		// write the columnList and dataList value to the uploadedFile
		uploadedFile = FileUtilities.createFileFromDatabase(columnList,dataList, createFile);
		if (uploadedFile == null)
			return;
		// get uploaded file folder path
		if(uploadedFileFolderPath != null)
		{	
			this.uploadedFileFolderPath = null;
			this.uploadedFileFolderPath = BASE_FOLDER.getAbsolutePath() + File.separator;
		} else
		{
			this.uploadedFileFolderPath = BASE_FOLDER.getAbsolutePath() + File.separator;
		}
		System.out.println("Uploaded File Folder Path : " + uploadedFileFolderPath);
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("filePath", uploadedFile.getAbsolutePath());
		BindUtils.postGlobalCommand(null, null,"setSSSLAnalysisModelListvariables", args);
		isVariableDataVisible = true;
	}

	@NotifyChange("*")
	@Command("selectFromDatabase")
	public void selectFromDatabase(
			@ContextParam(ContextType.BIND_CONTEXT) BindContext bindContext,
			@ContextParam(ContextType.VIEW) Component view) {
//		lstStudy = studyManagerImpl.getStudiesByUserID(SecurityUtil.getDbUser()
//				.getId());
		lstStudy = studyManagerImpl.getStudiesByUserIdAndStudyType(SecurityUtil.getDbUser().getId(), "SSSL");
		selectDataBtn.setVisible(false);
		studiesCombobox.setVisible(true);
		resetBtn.setVisible(true);
		uploadCSVBtn.setVisible(false);
	}

	@NotifyChange("*")
	@Command("uploadCSV")
	public void uploadCSV(
			@ContextParam(ContextType.BIND_CONTEXT) BindContext bindContext,
			@ContextParam(ContextType.VIEW) Component view) {
		UploadEvent event = (UploadEvent) bindContext.getTriggerEvent();
		// the from external file name
		fileName = event.getMedia().getName();
		if (!fileName.endsWith(".csv")) {
			showMessage("Error: File must be a text-based csv format!");
			return;
		}
		File tempFile = null;
		try {
			tempFile = File.createTempFile(fileName, ".tmp");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		InputStream in = event.getMedia().isBinary() ? event.getMedia()
				.getStreamData() : new ReaderInputStream(event.getMedia()
						.getReaderData());
				FileUtilities.uploadFile(tempFile.getAbsolutePath(), in);
				if (tempFile == null)
					return;
				BindUtils.postNotifyChange(null, null, this, "*");
				String filePath = userFileManager.uploadFileForAnalysis(fileName, tempFile);
				uploadedFile = new File(filePath);
				// set uploadedFileFolderPath
				if(uploadedFileFolderPath != null)
				{
					uploadedFileFolderPath = null;
					uploadedFileFolderPath = uploadedFile.getParent();
				} else
				{
					uploadedFileFolderPath = uploadedFile.getParent();
				}
				isVariableDataVisible = true;
				dataFileName = fileName;
				Map<String, Object> args = new HashMap<String, Object>();
				args.put("filePath", filePath);
				BindUtils.postGlobalCommand(null, null,"setSSSLAnalysisModelListvariables", args);
				refreshCsv(uploadedFile);
				resetBtn.setVisible(true);
				uploadCSVBtn.setVisible(false);
				selectDataBtn.setVisible(false);
	}

//
//	@NotifyChange("*")
//	@Command("acrossEnvCBCheck")
//	public void acrossEnvCBCheck()
//	{
//		isAcrossEnv  = this.acrossEnvCB.isChecked();
//	}

	@NotifyChange("*")
	@Command("uploadContrastFromFileOnGenotype")
	public void uploadContrastFromFileOnGenotype(
			@ContextParam(ContextType.BIND_CONTEXT) BindContext bindContext,
			@ContextParam(ContextType.VIEW) Component view) {
		//		the fileName is the file for analysis
		if(fileName == null){
			showMessage("Please selected or uploaded raw data at frist!");
			return;
		}
		if (genotypeTB.getValue().length() == 0) {
			showMessage("Error: Please fill up genotype factor on Model Specifications Tab!");
			return;
		}
		if (!this.contrastGridOnGenotypeDiv.getChildren().isEmpty())
			contrastGridOnGenotypeDiv.getFirstChild().detach();

		// basing on whether user choose across env
		Include inc = new Include();
		inc.setParent(this.contrastGridOnGenotypeDiv);
		HashMap<String, Object> args = new HashMap<String, Object>();
		args.put("Factor", "Genotype");
		List<String> lstLevelNames = new ArrayList<String>();
		if(acrossEnvCB.isChecked())
		{	
			lstLevelNames.add("AcrossEnv");
			args.put("LevelNames", lstLevelNames);
			HashMap<String, List<String>> hmGenosOnEnv = new HashMap<String, List<String>>();
			hmGenosOnEnv.put("AcrossEnv", Arrays.asList(genotypeLevels));
			args.put("Levels", hmGenosOnEnv);
		} else
		{
			if(!envTB.getValue().isEmpty())
			{
				args.put("LevelNames", Arrays.asList(environmentLevels));
				args.put("Levels", ssslRServeManager.getLevelsOnOtherLevels(columnList, dataList, genotypeTB.getValue(), envTB.getValue()));

			} else
			{
				lstLevelNames.add("AcrossEnv");
				args.put("EnvNames", lstLevelNames);
				HashMap<String, List<String>> hmGenosOnEnv = new HashMap<String, List<String>>();
				hmGenosOnEnv.put("AcrossEnv", Arrays.asList(genotypeLevels));
				args.put("Levels", hmGenosOnEnv);
			}
		}
		args.put("Type", "File");
		args.put("UploadedFileFolderPath", uploadedFileFolderPath);
		inc.setDynamicProperty("Arguments", args);
		inc.setSrc("/user/analysis/contrasttabbox.zul");
		this.acrossEnvCBDiv.setVisible(false);
		this.contrastFromFileOnGenotypeBtn.setVisible(false);
		this.contrastByManuallyOnGenotypeBtn.setVisible(false);
		this.contrastResetOnGenotypeBtn.setVisible(true);
		this.isSpecifyGenoContrast = true;
	}

	@NotifyChange("*")
	@Command("uploadContrastFromFileOnEnv")
	public void uploadContrastFromFileOnEnv(
			@ContextParam(ContextType.BIND_CONTEXT) BindContext bindContext,
			@ContextParam(ContextType.VIEW) Component view) {
		if(fileName == null){
			showMessage("Please selected or uploaded raw data at frist!");
			return;
		}
		if (envTB.getValue().isEmpty()) {
			showMessage("Please fill up environment factor on Model Specifications Tab!");
			return;
		}
		if (!this.contrastGridOnEnvDiv.getChildren().isEmpty())
			contrastGridOnEnvDiv.getFirstChild().detach();
		// basing on whether user choose across env
		Include inc = new Include();
		inc.setParent(this.contrastGridOnEnvDiv);
		HashMap<String, Object> args = new HashMap<String, Object>();
		args.put("Factor", "Environment");
		List<String> lstLevelNames = new ArrayList<String>();
		lstLevelNames.add("Environment");
		args.put("LevelNames", lstLevelNames);
		HashMap<String, List<String>> hmGenosOnEnv = new HashMap<String, List<String>>();
		hmGenosOnEnv.put("Environment", Arrays.asList(environmentLevels));
		args.put("Levels", hmGenosOnEnv);
		args.put("Type", "File");
		args.put("UploadedFileFolderPath", uploadedFileFolderPath);
		inc.setDynamicProperty("Arguments", args);
		inc.setSrc("/user/analysis/contrasttabbox.zul");
		this.contrastFromFileOnEnvBtn.setVisible(false);
		this.contrastByManuallyOnEnvBtn.setVisible(false);
		this.contrastResetOnEnvBtn.setVisible(true);
		this.isSpecifyEnvContrsat = true;
	}

	@NotifyChange("*")
	@Command("updateVariableList")
	public void updateVariableList(
			@BindingParam("selectedIndex") Integer selectedIndex) {
		if (designType.equals(lstTypeOfDesign.get(0))) {
			blockRow.setVisible(true);
			replicateRow.setVisible(false);
			rowRow.setVisible(false);
			columnRow.setVisible(false);
			this.ssslAnalysisModel.setDesign(0);
		} else if (designType.equals(lstTypeOfDesign.get(1))) {
			blockRow.setVisible(true);
			replicateRow.setVisible(false);
			rowRow.setVisible(false);
			columnRow.setVisible(false);
			this.ssslAnalysisModel.setDesign(1);
		} else if (designType.equals(lstTypeOfDesign.get(2))) {
			blockRow.setVisible(false);
			replicateRow.setVisible(false);
			rowRow.setVisible(true);
			columnRow.setVisible(true);
			this.ssslAnalysisModel.setDesign(2);
		} else if (designType.equals(lstTypeOfDesign.get(3))) {
			blockRow.setVisible(true);
			replicateRow.setVisible(true);
			rowRow.setVisible(false);
			columnRow.setVisible(false);
			this.ssslAnalysisModel.setDesign(3);
		} else if (designType.equals(lstTypeOfDesign.get(4))) {
			blockRow.setVisible(true);
			replicateRow.setVisible(false);
			rowRow.setVisible(true);
			columnRow.setVisible(true);
			this.ssslAnalysisModel.setDesign(4);
		} else if (designType.equals(lstTypeOfDesign.get(5))) {
			blockRow.setVisible(true);
			replicateRow.setVisible(true);
			rowRow.setVisible(false);
			columnRow.setVisible(false);
			this.ssslAnalysisModel.setDesign(5);
		} else if (designType.equals(lstTypeOfDesign.get(6))) {
			blockRow.setVisible(false);
			replicateRow.setVisible(true);
			rowRow.setVisible(true);
			columnRow.setVisible(true);
			this.ssslAnalysisModel.setDesign(6);
		}
	}

	@NotifyChange("*")
	@Command("updateEnvVariableList")
	public void updateEnvVariableList(
			@BindingParam("selectedIndex") Integer selectedIndex) {
		this.ssslAnalysisModel.setAnalysisEnvType(analysisEnvType);
		if (analysisEnvType.equals("Multi-Environment")) {
			this.specifiedContrastOnEnvDiv.setVisible(true);
			this.genotypeByEnvDiv.setVisible(true);
			this.singleEnvOnGraphDiv.setVisible(false);
			this.acrossEnvOnGraphDiv.setVisible(true);
			this.multiplicativeModelOnGraphDiv.setVisible(true);
			if(!acrossEnvCB.isChecked())
			{
				this.acrossEnvCB.setChecked(true);
			}
			if(acrossEnvCBDiv.isVisible())
				this.acrossEnvCBDiv.setVisible(false);
			if(this.alphaLevelDBDiv.isVisible())
				this.alphaLevelDBDiv.setVisible(false);
		} else if (analysisEnvType.equals("Single Environment")) {
			this.specifiedContrastOnEnvDiv.setVisible(false);
			this.genotypeByEnvDiv.setVisible(false);
			this.singleEnvOnGraphDiv.setVisible(true);
			this.acrossEnvOnGraphDiv.setVisible(false);
			this.multiplicativeModelOnGraphDiv.setVisible(false);
			if(!this.acrossEnvCBDiv.isVisible())
				this.acrossEnvCBDiv.setVisible(true);
			if(!acrossEnvCB.isChecked())
			{
				this.acrossEnvCB.setChecked(true);
			}
			if(!this.alphaLevelDBDiv.isVisible())
				this.alphaLevelDBDiv.setVisible(true);
		}
	}

	@Command
	public void validateSSSLAnalysisInputs() {
		if (validateSSSLAanalysisModel()) {
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("ssslModel", this.ssslAnalysisModel);
			BindUtils.postGlobalCommand(null, null, "displaySSSLResult", args);
		} else {
			showMessage(errorMessage);
		}
	}
	// this function is used to get upload contrast files from contrast.zul or contrastmanually.zul
	@NotifyChange("*")
	@GlobalCommand("getUploadedContrastFiles")
	public void getUploadedContrastFiles(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW)Component view,
			@BindingParam("Name") String name,
			@BindingParam("FilePath") String filePath,
			@BindingParam("Factor") String factor)
	{
		if(factor == "Genotype")
		{
			if(name != null)
			{
				if(genoContrastFiles.containsKey(name))
				{
					if(filePath != null)
						genoContrastFiles.put(name, filePath);
					else
						genoContrastFiles.remove(name);
				} else
				{
					if(filePath != null)
						genoContrastFiles.put(name, filePath);
				}
			}
		} else
		{
			if(name != null)
			{
				if(envContrastFiles.containsKey(name))
				{
					if(filePath != null)
						envContrastFiles.put(name, filePath);
					else
						envContrastFiles.remove(name);
				} else
				{
					if(filePath != null)
						envContrastFiles.put(name, filePath);
				}
			} 
		}
	}


	@Command("isCompareWithControlCBChecked")
	@NotifyChange("*")
	public void isCompareWithControlCBChecked() {
		if (genotypeTB.getValue().isEmpty()) {
			showMessage("Error: Please fill up genotype factor on Model Specifications Tab!");
			compareWithControlCB.setChecked(false);
			return;
		}
		if (compareWithControlCB.isChecked()) {
			controlCBB.setVisible(true);
			controlCBB.setDisabled(false);
		} else {
			controlCBB.setDisabled(true);
		}
	}

	@NotifyChange("*")
	@Command("manuallyInputContrastOnGenotype")
	public void manuallyInputContrastOnGenotype() {
		if(fileName == null){
			showMessage("Please selected or uploaded raw data at frist!");
			return;
		}
		if (genotypeTB.getValue().length() == 0) {
			showMessage("Please fill up genotype factor on Model Specifications Tab!");
			return;
		}
		if (!this.contrastGridOnGenotypeDiv.getChildren().isEmpty())
			contrastGridOnGenotypeDiv.getFirstChild().detach();
		// basing on whether user choose across env
		Include inc = new Include();
		inc.setParent(this.contrastGridOnGenotypeDiv);
		HashMap<String, Object> args = new HashMap<String, Object>();
		List<String> lstLevelNames = new ArrayList<String>();
		if(acrossEnvCB.isChecked())
		{	
			lstLevelNames.add("AcrossEnv");
			args.put("LevelNames", lstLevelNames);
			HashMap<String, List<String>> hmGenosOnEnv = new HashMap<String, List<String>>();
			hmGenosOnEnv.put("AcrossEnv", Arrays.asList(genotypeLevels));
			args.put("Levels", hmGenosOnEnv);
		} else
		{
			if(!envTB.getValue().isEmpty())
			{
				args.put("LevelNames", Arrays.asList(environmentLevels));
				args.put("Levels", ssslRServeManager.getLevelsOnOtherLevels(columnList, dataList, genotypeTB.getValue(), envTB.getValue()));

			} else
			{
				lstLevelNames.add("AcrossEnv");
				args.put("LevelNames", lstLevelNames);
				HashMap<String, List<String>> hmGenosOnEnv = new HashMap<String, List<String>>();
				hmGenosOnEnv.put("AcrossEnv", Arrays.asList(genotypeLevels));
				args.put("Levels", hmGenosOnEnv);
			}
		}
		args.put("Factor", "Genotype");
		args.put("Type", "Manual");
		args.put("UploadedFileFolderPath", uploadedFileFolderPath);
		inc.setDynamicProperty("Arguments", args);
		inc.setSrc("/user/analysis/contrasttabbox.zul");
		acrossEnvCBDiv.setVisible(false);
		contrastFromFileOnGenotypeBtn.setVisible(false);
		contrastByManuallyOnGenotypeBtn.setVisible(false);
		contrastResetOnGenotypeBtn.setVisible(true);
		this.isSpecifyGenoContrast=true;
	}

	@NotifyChange("*")
	@Command("manuallyInputContrastOnEnv")
	public void manuallyInputContrastOnEnv() {
		if(fileName == null){
			showMessage("Please selected or uploaded raw data at frist!");
			return;
		}
		if (envTB.getValue().length() == 0) {
			showMessage("Please fill up environment factor on Model Specifications Tab");
			return;
		}
		if (!this.contrastGridOnEnvDiv.getChildren().isEmpty())
			this.contrastGridOnEnvDiv.getFirstChild().detach();
		
		// basing on whether user choose across env
		Include inc = new Include();
		inc.setParent(this.contrastGridOnEnvDiv);
		HashMap<String, Object> args = new HashMap<String, Object>();
		List<String> lstLevelNames = new ArrayList<String>();
		lstLevelNames.add("Environment");
		args.put("LevelNames", lstLevelNames);
		String [] envs = ssslRServeManager.getLevels(columnList, dataList, envTB.getValue());
		HashMap<String, List<String>> hmGenosOnEnv = new HashMap<String, List<String>>();
		hmGenosOnEnv.put("Environment", Arrays.asList(envs));
		args.put("Levels", hmGenosOnEnv);
		args.put("Type", "Manual");
		args.put("Factor", "Environment");
		args.put("UploadedFileFolderPath", uploadedFileFolderPath);
		inc.setDynamicProperty("Arguments", args);
		inc.setSrc("/user/analysis/contrasttabbox.zul");
		contrastFromFileOnEnvBtn.setVisible(false);
		contrastByManuallyOnEnvBtn.setVisible(false);
		contrastResetOnEnvBtn.setVisible(true);
		this.isSpecifyEnvContrsat = true;
	}


	@NotifyChange("*")
	@Command("resetContrastOnGenotype")
	public void resetContrastOnGenotype() {
		genoContrastFiles.clear();
		ssslAnalysisModel.setGenotypeContrastFile(null);
		if (!this.contrastGridOnGenotypeDiv.getChildren().isEmpty())
			this.contrastGridOnGenotypeDiv.getFirstChild().detach();
		this.contrastFromFileOnGenotypeBtn.setVisible(true);
		this.contrastByManuallyOnGenotypeBtn.setVisible(true);
		this.contrastResetOnGenotypeBtn.setVisible(false);
		this.isSpecifyGenoContrast = false;
		if(!acrossEnvCB.isChecked())
		{
			this.acrossEnvCB.setChecked(true);
		}
		if(analysisEnvType == "Single Environment")
		{
			this.acrossEnvCBDiv.setVisible(true);
		}else
		{
			this.acrossEnvCBDiv.setVisible(false);
		}
	}

	@NotifyChange("*")
	@Command("resetContrastOnEnv")
	public void resetContrastOnEnv() {
		envContrastFiles.clear();
		ssslAnalysisModel.setGenotypeContrastFile(null);
		if (!this.contrastGridOnEnvDiv.getChildren().isEmpty())
			this.contrastGridOnEnvDiv.getFirstChild().detach();
		this.contrastFromFileOnEnvBtn.setVisible(true);
		this.contrastByManuallyOnEnvBtn.setVisible(true);
		this.contrastResetOnEnvBtn.setVisible(false);
		this.isSpecifyEnvContrsat = false;
	}

	@NotifyChange("*")
	@Command("refreshCsv")
	public void refreshCsv(File dataFile) {
		CSVReader reader;
		reloadCsvGrid();
		try {
			reader = new CSVReader(new FileReader(dataFile.getAbsolutePath()));
			List<String[]> rawData = reader.readAll();
			columnList.clear();
			dataList.clear();
			columnList = new ArrayList<String>(Arrays.asList(rawData.get(0)));
			rawData.remove(0);
			dataList = new ArrayList<String[]>(rawData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	@GlobalCommand("setSSSLAnalysisModelListvariables")
	@NotifyChange("*")
	public void setSSSLAnalysisModelListvariables(
			@BindingParam("filePath") String filepath) {
		ssslAnalysisModel.setDataFileName(filepath.replace("\\", "/"));
		lstVarInfo = ssslRServeManager.getVariableInfo(
				filepath.replace("//", "/"), fileFormat, separator);
		if(lstVarInfo == null || lstVarInfo.isEmpty())
		{
			showMessage("There are problems on getting variables information.");
			return;
		}
		setLstVarNames(AnalysisUtils.getVarNames(lstVarInfo));
		numericModel = AnalysisUtils.getNumericAsListModel(lstVarInfo);
		factorModel = AnalysisUtils.getFactorsAsListModel(lstVarInfo);
		responseModel = new ListModelList<String>();

		this.numericLB.setModel(numericModel);
		this.factorLB.setModel(factorModel);
		this.responseLB.setModel(responseModel);
	}


	@NotifyChange("*")
	@Command("addResponse")
	public void addResponse() {
		chooseResponseVariable();
	}

	@NotifyChange("*")
	@Command("removeResponse")
	public void removeResponse() {
		unchooseResponseVariable();
	}

	@NotifyChange({ "factorModel", "numericModel" })
	@Command("toNumeric")
	public void toNumeric() {
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

	@NotifyChange({ "factorModel", "numericModel" })
	@Command("toFactor")
	public void toFactor() {
		Set<String> set = this.numericModel.getSelection();
		for (String selectedItem : set) {
			this.factorModel.add(selectedItem);
			this.numericModel.remove(selectedItem);
		}
	}

	@NotifyChange("*")
	@Command("chooseVariable")
	public boolean chooseVariable(
			@BindingParam("varTextBox") Textbox varTextBox,
			@BindingParam("imgButton") Image imgButton) {
		Set<String> set = factorModel.getSelection();
		if (varTextBox.getValue().isEmpty() && !set.isEmpty()) {
			for (String selectedItem : set) {
				varTextBox.setValue(selectedItem);
				factorModel.remove(selectedItem);
			}

			if (varTextBox.getId().equals("genotypeTB")) {
				genotypeLevels = ssslRServeManager.getLevels(columnList,
						dataList, genotypeTB.getValue());
				genotypeLevelsModel = AnalysisUtils
						.toListModelList(genotypeLevels);
				this.controlCBB.setModel(genotypeLevelsModel);
			}

			if (varTextBox.getId().equals("envTB")) {
				this.environmentLevels = ssslRServeManager.getLevels(
						columnList, dataList, envTB.getValue());
			}

			imgButton.setSrc("/images/leftarrow_g.png");
			return true;
		} else if (!varTextBox.getValue().isEmpty()) {
			factorModel.add(varTextBox.getValue());
			varTextBox.setValue(null);
			if(varTextBox.getId().equals("genotypeTB"))
			{
				genotypeLevels = null;
				genotypeLevelsModel.clear();
				compareWithControlCB.setChecked(false);
				controlCBB.setDisabled(true);
			}
			if(varTextBox.getId().equals("envTB"))
			{
				environmentLevels = null;
			}
		}
		imgButton.setSrc("/images/rightarrow_g.png");
		return false;
	}

	@Command("updateAMMIGraphicOptions")
	@NotifyChange("*")
	public void updateAMMIGraphicOptions(@BindingParam("status") boolean isChecked)
	{
		if(isChecked)
		{
			this.ammiGraphicOptionsDiv.setVisible(true);
		} else
		{
			this.ammiGraphicOptionsDiv.setVisible(false);
		}
	}

	@Command("updateGGEGraphicOptions")
	@NotifyChange("*")
	public void updateGGEGraphicOptions(@BindingParam("status") boolean isChecked)
	{
		if(isChecked)
			this.ggeGraphicOptionsDiv.setVisible(true);
		else
			this.ggeGraphicOptionsDiv.setVisible(false);
	}

	private boolean validateSSSLAanalysisModel() {
		if (fileName == null) {
			errorMessage = "No Raw Data Selected!";
			return false;
		}
		if (!validateSpecifyGenotypeContrast())
			return false;
		if(!validateSpecifyEnvContrast())
			return false;

		String folderPath = null;
		if(analysisEnvType.equals("Single Environment"))
			folderPath = AnalysisUtils.createOutputFolder(
					fileName.replaceAll(" ", ""), "ssslAnalysisSEA");
		else if(analysisEnvType.equals("Multi-Environment"))
			folderPath = AnalysisUtils.createOutputFolder(
					fileName.replaceAll(" ", ""), "ssslAnalysisMEA");
		ssslAnalysisModel.setResultFolderPath(folderPath);

		userFileManager.moveUploadedFileToOutputFolder(folderPath,
				fileName.replaceAll(" ", ""), uploadedFile);

		ssslAnalysisModel.setOutFileName(ssslAnalysisModel
				.getResultFolderPath() + "SSSL_Analysis_Output.txt");
		
		// set Vars
		if (!this.lstTypeOfEnvCBB.getValue().isEmpty()) {
			ssslAnalysisModel.setAnalysisEnvType(analysisEnvType);
		} else {
			errorMessage = "Analysis Environment Type cannot be empty!";
			return false;
		}
		if (!this.responseLB.getItems().isEmpty()) {
			ssslAnalysisModel.setResponseVars(this.responseModel
					.toArray(new String[responseModel.size()]));
		} else {
			errorMessage = "Response Variable cannot be empty!";
			return false;
		}
		if (!this.genotypeTB.getValue().isEmpty()) {
			ssslAnalysisModel.setGenotypeFactor(this.genotypeTB.getValue());
			ssslAnalysisModel.setGenotypeFactorLevels(genotypeLevels);
		} else {
			errorMessage = "Genotype cannot be empty!";
			return false;
		}

		if (!this.envTB.getValue().isEmpty()) {
			ssslAnalysisModel.setEnvFactor(envTB.getValue());
			ssslAnalysisModel.setEnvFactorLevels(environmentLevels);
			if(analysisEnvType == "Multi-Environment" && environmentLevels.length <= 1)
			{
				errorMessage = "It could not conduct multi-environment analysis on only one level of environment factor!";
				return false;
			}
		} else if (this.lstTypeOfEnvCBB.getValue().equals("Multi-Environment")) {
			errorMessage = "Environment cannot be empty!";
			return false;
		}

		if (!this.blockTB.getValue().isEmpty()) {
			ssslAnalysisModel.setBlockFactor(this.blockTB.getValue());
			ssslAnalysisModel.setBlockFactorLevels(ssslRServeManager.getLevels(
					columnList, dataList, ssslAnalysisModel.getBlockFactor()));
		} else if (blockRow.isVisible()) {
			errorMessage = "Block cannot be empty!";
			return false;
		}

		if (!this.replicateTB.getValue().isEmpty()) {
			ssslAnalysisModel.setReplicateFactor(this.replicateTB.getValue());
			ssslAnalysisModel.setReplicateFactorLevels(ssslRServeManager
					.getLevels(columnList, dataList,
							ssslAnalysisModel.getReplicateFactor()));
		} else if (replicateRow.isVisible()) {
			errorMessage = "Replicate cannot be empty!";
			return false;
		}

		if (!this.columnTB.getValue().isEmpty()) {
			ssslAnalysisModel.setColumnFactor(this.columnTB.getValue());
			ssslAnalysisModel.setColumnFactorLevels(ssslRServeManager
					.getLevels(columnList, dataList,
							ssslAnalysisModel.getColumnFactor()));
		} else if (columnRow.isVisible()) {
			errorMessage = "Column cannot be empty!";
			return false;
		}

		if (!this.rowTB.getValue().isEmpty()) {
			ssslAnalysisModel.setRowFactor(this.rowTB.getValue());
			ssslAnalysisModel.setRowFactorLevels(ssslRServeManager.getLevels(
					columnList, dataList, ssslAnalysisModel.getRowFactor()));
		} else if (rowRow.isVisible()) {
			errorMessage = "Column cannot be empty!";
			return false;
		}

		// set other options
		if (this.descriptiveStatCB.isChecked()) {
			ssslAnalysisModel.setDescriptiveStat(true);
		} else {
			ssslAnalysisModel.setDescriptiveStat(false);
		}
		if (this.varComponentCB.isChecked()) {
			ssslAnalysisModel.setVarComponent(true);
		} else {
			ssslAnalysisModel.setVarComponent(false);
		}
		if(this.alphalevel > 0)
		{
			ssslAnalysisModel.setSignificantAlpha(this.alphalevel);
		} else
		{
			errorMessage = "Alpha Level is not correct!";
			return false;
		}
		if (this.compareWithControlCB.isChecked()
				&& this.controlCBB.getValue().length() != 0) {
			ssslAnalysisModel.setCompareWithRecurrent(true);
			ssslAnalysisModel.setRecurrentParent(this.controlCBB.getValue());
		} else if (this.compareWithControlCB.isChecked()
				&& this.controlCBB.getValue().length() == 0) {
			errorMessage = "Recurrent Parent cannot be empty!";
			return false;
		}
		// Here for define the contrast file for genotype and environment
		if(isSpecifyGenoContrast)
		{
			ssslAnalysisModel.setSpecifiedGenoContrast(true);
			if(acrossEnvCB.isChecked())
			{
				ssslAnalysisModel.setAcrossEnv(true);
			} else
			{
				ssslAnalysisModel.setAcrossEnv(false);
			}
			ssslAnalysisModel.setGenotypeContrastFile(genoContrastFiles);
			for(String s : genoContrastFiles.values())
			{
				userFileManager.moveContrastFileToOutputFolder(folderPath, s);
			}
		} else
		{
			ssslAnalysisModel.setSpecifiedGenoContrast(false);
			ssslAnalysisModel.setGenotypeContrastFile(null);
		}
		if(isSpecifyEnvContrsat)
		{
			ssslAnalysisModel.setSpecifiedEnvContrast(true);
			ssslAnalysisModel.setEnvContrastFile(envContrastFiles);
			for(String s : envContrastFiles.values())
			{
				userFileManager.moveContrastFileToOutputFolder(folderPath, s);
			}
		} else
		{
			ssslAnalysisModel.setSpecifiedEnvContrast(false);
			ssslAnalysisModel.setEnvContrastFile(null);
		}

		if (this.genotypeByEnvDiv.isVisible()
				&& this.finlayWilkinsonModelCB.isChecked()) {
			ssslAnalysisModel.setFinlayWikinson(true);
		} else {
			ssslAnalysisModel.setFinlayWikinson(false);
		}
		if (this.genotypeByEnvDiv.isVisible() && this.shuklaModelCB.isChecked()) {
			ssslAnalysisModel.setShukla(true);
		} else {
			ssslAnalysisModel.setShukla(false);
		}
		if (this.genotypeByEnvDiv.isVisible() && this.ammiModelCB.isChecked()) {
			ssslAnalysisModel.setAMMI(true);
		} else {
			ssslAnalysisModel.setAMMI(false);
		}
		if (this.genotypeByEnvDiv.isVisible() && this.ggeModelCB.isChecked()) {
			ssslAnalysisModel.setGGE(true);
		} else {
			ssslAnalysisModel.setGGE(false);
		}
		// for graphic options
		if (this.boxplotSingleEnvCB.isChecked()) {
			ssslAnalysisModel.setBoxplotOnSingleEnv(true);
		} else {
			ssslAnalysisModel.setBoxplotOnSingleEnv(false);
		}
		if (this.histogramSingleEnvCB.isChecked()) {
			ssslAnalysisModel.setHistogramOnSingleEnv(true);
		} else {
			ssslAnalysisModel.setHistogramOnSingleEnv(false);
		}
		if (this.diagnosticplotSingleEnvCB.isChecked()) {
			ssslAnalysisModel.setDiagnosticPlotOnSingleEnv(true);
		} else {
			ssslAnalysisModel.setDiagnosticPlotOnSingleEnv(false);
		}
		if (this.acrossEnvOnGraphDiv.isVisible()
				&& this.boxplotAcrossEnvCB.isChecked()) {
			ssslAnalysisModel.setBoxplotOnAcrossEnv(true);
		} else {
			ssslAnalysisModel.setBoxplotOnAcrossEnv(false);
		}
		if (this.acrossEnvOnGraphDiv.isVisible()
				&& this.histogramAcrossEnvCB.isChecked()) {
			ssslAnalysisModel.setHistogramOnAcrossEnv(true);
		} else {
			ssslAnalysisModel.setHistogramOnAcrossEnv(false);
		}
		if (this.acrossEnvOnGraphDiv.isVisible()
				&& this.diagnosticplotAcrossEnvCB.isChecked()) {
			ssslAnalysisModel.setDiagnosticPlotOnAcrossEnv(true);
		} else {
			ssslAnalysisModel.setDiagnosticPlotOnAcrossEnv(false);
		}
		if (this.multiplicativeModelOnGraphDiv.isVisible()
				&& this.responsePlotCB.isChecked()) {
			ssslAnalysisModel.setResponsePlot(true);
		} else {
			ssslAnalysisModel.setResponsePlot(false);
		}
		if (this.multiplicativeModelOnGraphDiv.isVisible()
				&& this.adaptationMapCB.isChecked()) {
			ssslAnalysisModel.setAdaptationMap(true);
		} else {
			ssslAnalysisModel.setAdaptationMap(false);
		}
		if (this.multiplicativeModelOnGraphDiv.isVisible()
				&& this.ammiBiplotCB.isChecked()) {
			ssslAnalysisModel.setAMMI1Biplot(true);
		} else {
			ssslAnalysisModel.setAMMI1Biplot(false);
		}
		if (this.multiplicativeModelOnGraphDiv.isVisible()
				&& this.ammiBiplotPC1VsPC2CB.isChecked()) {
			ssslAnalysisModel.setAMMIBiplotPC1VsPC2(true);
		} else {
			ssslAnalysisModel.setAMMIBiplotPC1VsPC2(false);
		}
		if (this.multiplicativeModelOnGraphDiv.isVisible()
				&& this.ammiBiplotPC1VsPC3CB.isChecked()) {
			ssslAnalysisModel.setAMMIBiplotPC1VsPC3(true);
		} else {
			ssslAnalysisModel.setAMMIBiplotPC1VsPC3(false);
		}
		if (this.multiplicativeModelOnGraphDiv.isVisible()
				&& this.ammiBiplotPC2VsPC3CB.isChecked()) {
			ssslAnalysisModel.setAMMIBiplotPC2VsPC3(true);
		} else {
			ssslAnalysisModel.setAMMIBiplotPC2VsPC3(false);
		}
		if (this.multiplicativeModelOnGraphDiv.isVisible()
				&& this.ggeBiplotSymmetricViewCB.isChecked()) {
			ssslAnalysisModel.setGGEBiplotSymmetricView(true);
		} else {
			ssslAnalysisModel.setGGEBiplotSymmetricView(false);
		}
		if (this.multiplicativeModelOnGraphDiv.isVisible()
				&& this.ggeBiplotEnvironmentViewCB.isChecked()) {
			ssslAnalysisModel.setGGEBiplotEnvironmentView(true);
		} else {
			ssslAnalysisModel.setGGEBiplotEnvironmentView(false);
		}
		if (this.multiplicativeModelOnGraphDiv.isVisible()
				&& this.ggeBiplotGenotypeViewCB.isChecked()) {
			ssslAnalysisModel.setGGEBiplotGenotypeView(true);
		} else {
			ssslAnalysisModel.setGGEBiplotGenotypeView(false);
		}
		return true;
	}
	
	private boolean validateSpecifyEnvContrast()
	{
		if(isSpecifyEnvContrsat)
		{
			if(analysisEnvType == "Multi-Environment")
			{
				if(envTB.getValue().isEmpty())
				{
					errorMessage = "The environement factor could not be null!";
					return false;
				}
			}
		}
		return true;
	}

	private boolean validateSpecifyGenotypeContrast()
	{
		if(isSpecifyGenoContrast)
		{
			if(envTB.getValue().isEmpty())
			{
				if(!genoContrastFiles.containsKey("AcrossEnv"))
				{
					errorMessage = "Do not specify genotype contrast from file(s)! Please upload contrast file(s)!";
					return false;
				}
			} else
			{
				if(acrossEnvCB.isChecked())
				{
					if(!genoContrastFiles.containsKey("AcrossEnv"))
					{
						errorMessage = "Do not specify genotype contrast from file(s)! Please upload contrast file(s)!";
						return false;
					}
				} else
				{
					for(String s : environmentLevels)
					{
						if(!genoContrastFiles.containsKey(s))
						{
							errorMessage = "Do not specify genotype contrast from file(s) on " + s + 
									" ! Please upload contrast file(s)!";
							return false;
						}
					}
				}
			}
		}
		return true;
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

	public void reloadCsvGrid() {
		if (!dataGridDiv.getChildren().isEmpty())
			dataGridDiv.getFirstChild().detach();
		Include incCSVData = new Include();
		incCSVData.setSrc("/user/updatestudy/csvdata.zul");
		incCSVData.setParent(dataGridDiv);
	}

	public Study getSelectedStudy() {
		return selectedStudy;
	}

	public void setSelectedStudy(Study selectedStudy) {
		this.selectedStudy = selectedStudy;
	}

	public List<Study> getLstStudy() {
		return lstStudy;
	}

	public void setLstStudy(List<Study> lstStudy) {
		this.lstStudy = lstStudy;
	}

	public StudyDataSet getSelectedStudyDataSet() {
		return selectedStudyDataSet;
	}

	public void setSelectedStudyDataSet(StudyDataSet selectedStudyDataSet) {
		this.selectedStudyDataSet = selectedStudyDataSet;
	}

	public List<StudyDataSet> getLstStudyDataSet() {
		return lstStudyDataSet;
	}

	public void setLstStudyDataSet(List<StudyDataSet> lstStudyDataSet) {
		this.lstStudyDataSet = lstStudyDataSet;
	}

	public String getDataFileName() {
		return dataFileName;
	}

	public void setDataFileName(String dataFileName) {
		this.dataFileName = dataFileName;
	}

	public boolean isVariableDataVisible() {
		return isVariableDataVisible;
	}

	public void setVariableDataVisible(boolean isVariableDataVisible) {
		this.isVariableDataVisible = isVariableDataVisible;
	}

	// This Type of Design for study should be retrieve from database
	// It should be refactor
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

	public List<String> getLstTypeOfAnalysisEnv() {
		if (lstTypeOfAnalysisEnv == null)
			lstTypeOfAnalysisEnv = new ArrayList<String>();
		lstTypeOfAnalysisEnv.clear();
		lstTypeOfAnalysisEnv.add("Multi-Environment");
		lstTypeOfAnalysisEnv.add("Single Environment");
		return lstTypeOfAnalysisEnv;
	}

	public void setLstTypeOfDesign(List<String> lstTypeOfDesign) {
		this.lstTypeOfDesign = lstTypeOfDesign;
	}

	public SSSLAnalysisModel getSsslAnalysisModel() {
		return ssslAnalysisModel;
	}

	public void setSsslAnalysisModel(SSSLAnalysisModel ssslAnalysisModel) {
		this.ssslAnalysisModel = ssslAnalysisModel;
	}

	public List<String> getLstVarNames() {
		return lstVarNames;
	}

	public void setLstVarNames(List<String> lstVarNames) {
		this.lstVarNames = lstVarNames;
	}

	public String getResultRServe() {
		return resultRServe;
	}

	public void setResultRServe(String resultRServe) {
		this.resultRServe = resultRServe;
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

	public File getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(File uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDesignType() {
		return designType;
	}

	public void setDesignType(String designType) {
		this.designType = designType;
	}

	public String getAnalysisEnvType() {
		return analysisEnvType;
	}

	public void setAnalysisEnvType(String analysisEnvType) {
		this.analysisEnvType = analysisEnvType;
	}

	public Double getAlphalevel() {
		return alphalevel;
	}

	public void setAlphalevel(Double alphalevel) {
		this.alphalevel = alphalevel;
	}
	
	private void showMessage(String message)
	{
		Messagebox.show(message, "Warnings", Messagebox.OK, Messagebox.INFORMATION);
	}

}
