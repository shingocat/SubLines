package org.strasa.web.analysis.pyramidedline.view.model;

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

import org.analysis.rserve.manager.PyramidedLineRserveManager;
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
import org.strasa.web.analysis.view.model.PyramidedLineAnalysisModel;
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
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
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

import au.com.bytecode.opencsv.CSVReader;

public class Specifications {

	// The component list
	private Component component;
	private Div pyramidedLineSpecificationsWindow;
	private Label lblMsgUpload;
	private Combobox studiesCombobox;
	private Combobox dataSetCombobox;
	private Button selectDataBtn;
	private Button uploadCSVBtn;
	private Button resetBtn;
	private Groupbox grpVariableData;
	private Div dataGridDiv;
	private Combobox lstAnalysisTypeCBB;
	private Combobox lstTypeOfDesignCBB;
	private Button runBtn;
	// for model specifications
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
	private Checkbox envIsRandomCB;
	// private Checkbox envRandomCB;
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
	// for other options;
	private Include includeOtherOptions;
	private Checkbox descriptiveStatCB;
	private Checkbox varComponentCB;
	private Doublebox alphaLevelDB;
	private Div specifiedContrastOnGenotypeDiv;
	private Label fileNameOfContrastOnGenotypeLb;
	private Button defaultGenesContrastBtn;
	private Button contrastFromFileOnGenotypeBtn;
	private Button contrastByManuallyOnGenotypeBtn;
	private Button contrastResetOnGenotypeBtn;
	private Div contrastByManuallyOnGenotypeDiv;
	private Doublespinner numberOfContrastOnGenotypeDS;
	private Div contrastGridOnGenotypeDiv;
	private Div genotypeByEnvDiv;
	private Checkbox finlayWilkinsonModelCB;
	private Checkbox shuklaModelCB;
	private Checkbox ammiModelCB;
	private Checkbox ggeModelCB;

	// for graph options;
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
	private Checkbox adaptationMapCB;
	private Checkbox ammiBiplotCB;
	private Checkbox ammiBiplotPC1VsPC2CB;
	private Checkbox ammiBiplotPC1VsPC3CB;
	private Checkbox ammiBiplotPC2VsPC3CB;
	private Div ggeGraphicOptionsDiv;
	private Checkbox ggeBiplotSymmetricViewCB;
	private Checkbox ggeBiplotEnvironmentViewCB;
	private Checkbox ggeBiplotGenotypeViewCB;

	// logic options
	private PyramidedLineRserveManager manager;
	private StudyDataSetManagerImpl studyDataSetManagerImpl;
	private StudyManagerImpl studyManagerImpl;
	private BrowseStudyManagerImpl browseStudyManagerImpl;
	private UserFileManager userFileManager;
	private List<Study> lstStudy;
	private Study selectedStudy;
	private List<StudyDataSet> lstStudyDataSet;
	private StudyDataSet selectedStudyDataSet;
	private boolean isFromExternalFile = false;
	private String dataFileName;
	private List<String> lstTypeOfDesign;
	private String typeOfDesign;
	private List<String> lstAnalysisType;
	private String analysisType;
	private String resultRServe;
	private PyramidedLineAnalysisModel model;
	private double alphalevel;
	private String contrastFileNameOnGenotype;
	private List<String> columnList = new ArrayList<String>(); // column list of raw data;
	private List<String[]> dataList = new ArrayList<String[]>(); // raw data from selected data;
	private List<String> contrastColumnListOnGen = new ArrayList<String>(); // column list of genotype contrast data;
	private List<String[]> contrastDataListOnGen = new ArrayList<String[]>(); //raw contrast genotype data
	private int activePage = 0;
	private int pageSize = 10;
	private File tempFile;
	private boolean gridReUploaded;
	private String fileName;
	private File uploadedFile;
	private boolean isVariableDataVisisble;
	private List<String> lstVarInfo;
	private List<String> lstVarNames;
	private int fileFormat = 1;
	private String separator = "NULL";
	private ListModelList<String> numericModel;
	private ListModelList<String> responseModel;
	private ListModelList<String> factorModel;
	private boolean isVariableDataVisible;
	private boolean isUpadteMode;
	private boolean isNewDataSet;
	private List<String> respvars = new ArrayList<String>();
	private String[] genotypeLevels;
	private ListModelList<String> geneotypeLevelsModel;
	private File uploadedContrastFileOnGenotype;
	private Grid gridManuallyOnGenotype;
	private Columns columnsManuallyOnGenotype;
	private Rows rowsManuallyOnGenotype;
	private String numberOfGenes ="Bi-Genes";
	
	
	@Init
	public void init() {
		setAnalysisType("Multi-Environment");
		setTypeOfDesign("Randomized Complete Block(RCB)");
		setAlphalevel(0.05);
	}

	@AfterCompose
	public void afterCompose(
			@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(component, this, false);

		model = new PyramidedLineAnalysisModel();
		studyManagerImpl = new StudyManagerImpl();
		studyDataSetManagerImpl =new StudyDataSetManagerImpl();
		userFileManager = new UserFileManager();
		manager = new PyramidedLineRserveManager();
		
		lstStudy = studyManagerImpl.getStudiesByUserID(SecurityUtil.getDbUser().getId());
		
		// wire all the component
		this.component = component;
		this.pyramidedLineSpecificationsWindow = (Div) component
				.getFellow("pyramidedLineSpecificationsWindow");
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
		// model specification component
		this.lstAnalysisTypeCBB = (Combobox) component
				.getFellow("lstAnalysisTypeCBB");
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
		 this.envIsRandomCB = (Checkbox) this.includeVariableLst
		 .getFellow("envIsRandomCB");
		// this.envRandomCB = (Checkbox) this.includeVariableLst
		// .getFellow("envRandomCB");
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
		// other options component
		this.includeOtherOptions = (Include) component
				.getFellow("includeOtherOptions");
		this.descriptiveStatCB = (Checkbox) this.includeOtherOptions
				.getFellow("descriptiveStatCB");
		this.varComponentCB = (Checkbox) this.includeOtherOptions
				.getFellow("varComponentCB");
		this.alphaLevelDB = (Doublebox) this.includeOtherOptions
				.getFellow("alphaLevelDB");
		this.specifiedContrastOnGenotypeDiv = (Div) this.includeOtherOptions
				.getFellow("specifiedContrastOnGenotypeDiv");
		this.fileNameOfContrastOnGenotypeLb = (Label) this.includeOtherOptions
				.getFellow("fileNameOfContrastOnGenotypeLb");
		this.defaultGenesContrastBtn = (Button) this.includeOtherOptions.getFellow("defaultGenesContrastBtn");
		this.contrastFromFileOnGenotypeBtn = (Button) this.includeOtherOptions
				.getFellow("contrastFromFileOnGenotypeBtn");
		this.contrastByManuallyOnGenotypeBtn = (Button) this.includeOtherOptions
				.getFellow("contrastByManuallyOnGenotypeBtn");
		this.contrastResetOnGenotypeBtn = (Button) this.includeOtherOptions
				.getFellow("contrastResetOnGenotypeBtn");
		this.contrastByManuallyOnGenotypeDiv = (Div) this.includeOtherOptions
				.getFellow("contrastByManuallyOnGenotypeDiv");
		this.numberOfContrastOnGenotypeDS = (Doublespinner) this.includeOtherOptions
				.getFellow("numberOfContrastOnGenotypeDS");
		this.contrastGridOnGenotypeDiv = (Div) this.includeOtherOptions
				.getFellow("contrastGridOnGenotypeDiv");
		this.genotypeByEnvDiv = (Div) this.includeOtherOptions
				.getFellow("genotypeByEnvDiv");
		this.finlayWilkinsonModelCB = (Checkbox) this.includeOtherOptions
				.getFellow("finlayWilkinsonModelCB");
		this.shuklaModelCB = (Checkbox) this.includeOtherOptions
				.getFellow("shuklaModelCB");
		this.ammiModelCB = (Checkbox) this.includeOtherOptions
				.getFellow("ammiModelCB");
		this.ggeModelCB = (Checkbox) this.includeOtherOptions
				.getFellow("ggeModelCB");
		// graphic component
		this.includeGraphOptions = (Include) component
				.getFellow("includeGraphOptions");
		this.singleEnvOnGraphDiv = (Div) this.includeGraphOptions
				.getFellow("singleEnvOnGraphDiv");
		this.boxplotSingleEnvCB = (Checkbox) this.includeGraphOptions
				.getFellow("boxplotSingleEnvCB");
		this.histogramSingleEnvCB = (Checkbox) this.includeGraphOptions
				.getFellow("histogramSingleEnvCB");
		this.diagnosticplotSingleEnvCB = (Checkbox) this.includeGraphOptions
				.getFellow("diagnosticplotSingleEnvCB");
		this.acrossEnvOnGraphDiv = (Div) this.includeGraphOptions
				.getFellow("acrossEnvOnGraphDiv");
		this.boxplotAcrossEnvCB = (Checkbox) this.includeGraphOptions
				.getFellow("boxplotAcrossEnvCB");
		this.histogramAcrossEnvCB = (Checkbox) this.includeGraphOptions
				.getFellow("histogramAcrossEnvCB");
		this.diagnosticplotAcrossEnvCB = (Checkbox) this.includeGraphOptions
				.getFellow("diagnosticplotAcrossEnvCB");
		this.multiplicativeModelOnGraphDiv = (Div) this.includeGraphOptions
				.getFellow("multiplicativeModelOnGraphDiv");
		this.ammiGraphicOptionsDiv = (Div) this.includeGraphOptions
				.getFellow("ammiGraphicOptionsDiv");
		this.adaptationMapCB = (Checkbox) this.includeGraphOptions
				.getFellow("adaptationMapCB");
		this.ammiBiplotCB = (Checkbox) this.includeGraphOptions
				.getFellow("ammiBiplotCB");
		this.ammiBiplotPC1VsPC2CB = (Checkbox) this.includeGraphOptions
				.getFellow("ammiBiplotPC1VsPC2CB");
		this.ammiBiplotPC1VsPC3CB = (Checkbox) this.includeGraphOptions
				.getFellow("ammiBiplotPC1VsPC3CB");
		this.ammiBiplotPC2VsPC3CB = (Checkbox) this.includeGraphOptions
				.getFellow("ammiBiplotPC2VsPC3CB");
		this.ggeGraphicOptionsDiv = (Div) this.includeGraphOptions
				.getFellow("ggeGraphicOptionsDiv");
		this.ggeBiplotSymmetricViewCB = (Checkbox) this.includeGraphOptions
				.getFellow("ggeBiplotSymmetricViewCB");
		this.ggeBiplotEnvironmentViewCB = (Checkbox) this.includeGraphOptions
				.getFellow("ggeBiplotEnvironmentViewCB");
		this.ggeBiplotGenotypeViewCB = (Checkbox) this.includeGraphOptions
				.getFellow("ggeBiplotGenotypeViewCB");

	}

	@NotifyChange("*")
	@Command("updateDataSetList")
	public void updateDataSetList() {
		dataSetCombobox.setSelectedItem(null);
		dataSetCombobox.setVisible(false);
		
		if(!columnList.isEmpty())
		{
			columnList.clear();
			dataList.clear();
			refreshCsv();
		}
		
		List<StudyDataSet> dataSet = studyDataSetManagerImpl.getDataSetsByStudyId(selectedStudy.getId());
		
		if(!dataSet.isEmpty())
		{
			dataSetCombobox.setVisible(true);
			setLstStudyDataSet(dataSet);
			BindUtils.postNotifyChange(null, null, this, "*");
		} else
		{
			Messagebox.show("Please choose a different study!", "Study has no data", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@NotifyChange("*")
	@Command("displaySelectedDataSet")
	public void displaySelectedDataSet(@ContextParam(ContextType.BIND_CONTEXT)BindContext bindContext,
			@ContextParam(ContextType.VIEW) Component view) {
		this.selectDataBtn.setVisible(false);
		this.studiesCombobox.setVisible(false);
		this.resetBtn.setVisible(true);
		this.uploadCSVBtn.setVisible(false);
		
		browseStudyManagerImpl = new BrowseStudyManagerImpl();
		List<HashMap<String, String>> toreturn = this.browseStudyManagerImpl.getStudyData(selectedStudy.getId(), selectedStudyDataSet.getDatatype(), selectedStudyDataSet.getId());
		
		List<StudyDataColumn> columns = new StudyDataColumnManagerImpl().getStudyDataColumnByStudyId(selectedStudy.getId(), selectedStudyDataSet.getDatatype(), selectedStudyDataSet.getId());
		
		for(StudyDataColumn d: columns)
		{
			columnList.add(d.getColumnheader());
		}
		
		for(HashMap<String, String> rec : toreturn)
		{
			ArrayList<String> newRow = new ArrayList<String>();
			for(StudyDataColumn d: columns)
			{
				String value = rec.get(d.getColumnheader());
				newRow.add(value);
			}
			dataList.add(newRow.toArray(new String[newRow.size()]));
		}
		
		fileName = selectedStudy.getName() + "_" + selectedStudyDataSet.getTitle().replace(" ", "");
		File BASE_FOLDER = new File(UserFileManager.buildUserPath(selectedStudy.getUserid(), selectedStudy.getId()));
		if(!BASE_FOLDER.exists())
			BASE_FOLDER.mkdirs();
		String createPath = BASE_FOLDER.getAbsolutePath() + StringConstants.SYSTEM_FILE_SEPARATOR + fileName + "dataset.csv";
		System.out.println("Create Path on Display Database's dataset " + createPath);
		uploadedFile = FileUtilities.createFileFromDatabase(columnList, dataList, createPath);
		
		if(uploadedFile == null)
			return;
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("filePath", uploadedFile.getAbsolutePath());
		BindUtils.postGlobalCommand(null, null, "setPyramidedLineAnalysisModelListVariables", args);
		
		isVariableDataVisisble = true;
		
		activePage = 0;
		reloadCsvGrid();
	}
	
	@NotifyChange("*")
	@GlobalCommand("setPyramidedLineAnalysisModelListVariables")
	public void setPyramidedLineAnalysisModelListVariables(@BindingParam("filePath") String filePath)
	{
		model.setDataFileName(filePath.replace(StringConstants.BSLASH, StringConstants.FSLASH));
		lstVarInfo = manager.getVariableInfo(filePath.replace(StringConstants.BSLASH, StringConstants.FSLASH),fileFormat, separator);
		setLstVarNames(AnalysisUtils.getVarNames(lstVarInfo));
		numericModel = AnalysisUtils.getNumericAsListModel(lstVarInfo);
		factorModel = AnalysisUtils.getFactorsAsListModel(lstVarInfo);
		responseModel = new ListModelList<String>();
		
		this.numericLB.setModel(numericModel);
		this.factorLB.setModel(factorModel);
		this.responseLB.setModel(responseModel);
	}

	@NotifyChange("*")
	@Command("selectFromDatabase")
	public void selectFromDatabase(@ContextParam(ContextType.BIND_CONTEXT)BindContext bindContext, @ContextParam(ContextType.VIEW) Component view) {
		this.selectDataBtn.setVisible(false);
		this.studiesCombobox.setVisible(true);
		this.resetBtn.setVisible(true);
		this.uploadCSVBtn.setVisible(false);
	}

	@NotifyChange("*")
	@Command("uploadCSV")
	public void uploadCSV(@ContextParam(ContextType.BIND_CONTEXT)BindContext bindContext,
			@ContextParam(ContextType.VIEW)Component view) {
		UploadEvent event = (UploadEvent) bindContext.getTriggerEvent();
		
		fileName = event.getMedia().getName();
		
		if(!fileName.endsWith(".csv"))
		{
			Messagebox.show("Error: File must be a text-based csv format", "Upload Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		if(tempFile == null)
		{
			try{
				tempFile = File.createTempFile(fileName, ".tmp");
			} catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		InputStream in = event.getMedia().isBinary() ? event.getMedia().getStreamData() : new ReaderInputStream(event.getMedia().getReaderData());
		FileUtilities.uploadFile(tempFile.getAbsolutePath(), in);
		if(tempFile == null)
			return;
		BindUtils.postNotifyChange(null, null, this, "*");
		
		uploadedFile = tempFile;
		String filepath = userFileManager.uploadFileForAnalysis(fileName, uploadedFile);
		
		isVariableDataVisible = true;
		dataFileName = fileName;
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("filePath", filepath);
		BindUtils.postGlobalCommand(null, null, "setPyramidedLineAnalysisModelListVariables", args);
		
		refreshCsv();
		if(this.isUpadteMode)
			isNewDataSet = true;
		
		resetBtn.setVisible(true);
		uploadCSVBtn.setVisible(false);
		selectDataBtn.setVisible(false);
	}

	@NotifyChange("*")
	@Command("updateAnalysisTypeOptions")
	public void updateAnalysisTypeOptions(
			@BindingParam("selectedIndex") Integer selectedType) {
		System.out.println("Analysis Type index " + selectedType);
		switch (selectedType) {
		case 0: {
			this.model
					.setAnalysisType("Multi-Environment");
			this.genotypeByEnvDiv.setVisible(true);
			this.singleEnvOnGraphDiv.setVisible(false);
			this.acrossEnvOnGraphDiv.setVisible(true);
			this.multiplicativeModelOnGraphDiv.setVisible(true);
			break;
		}
		case 1: {
			this.model
					.setAnalysisType("Single Environment");
			this.genotypeByEnvDiv.setVisible(false);
			this.singleEnvOnGraphDiv.setVisible(true);
			this.acrossEnvOnGraphDiv.setVisible(false);
			this.multiplicativeModelOnGraphDiv.setVisible(false);
			break;
		}
		default: {
			this.model
					.setAnalysisType("Multi-Environment");
			this.genotypeByEnvDiv.setVisible(true);
			this.singleEnvOnGraphDiv.setVisible(false);
			this.acrossEnvOnGraphDiv.setVisible(true);
			this.multiplicativeModelOnGraphDiv.setVisible(true);
			break;
		}
		}
	}

	@NotifyChange("*")
	@Command("updateVariableList")
	public void updateVariableList(
			@BindingParam("selectedIndex") Integer selectedIndex) {
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
	@Command("updateNumberOfGenesOpt")
	public void updateNumberOfGenesOpt(@BindingParam("selectedValue") String selectedValue)
	{
		System.out.println("Number of Genes selected " + selectedValue);
		this.numberOfGenes = selectedValue;
	}

	@NotifyChange("*")
	@Command("validatePyramidedLineAnalysisInputs")
	public void validatePyramidedLineAnalysisInputs() {

	}

	@NotifyChange("*")
	@Command("addResponse")
	public void addResponse() {
		Set<String> set = numericModel.getSelection();
		System.out.println("addResponse");
		for(String selectedItem : set)
		{
			respvars.add(selectedItem);
			responseModel.add(selectedItem);
			numericModel.remove(selectedItem);
		}
	}

	@NotifyChange("*")
	@Command("removeResponse")
	public void removeResponse() {
		Set<String> set = responseModel.getSelection();
		System.out.println("removeResponse");
		for(String selectedItem : set)
		{
			respvars.remove(selectedItem);
			numericModel.add(selectedItem);
			responseModel.remove(selectedItem);
		}
	}

	@NotifyChange({"factorModel", "numericModel"})
	@Command("toNumeric")
	public void toNumeric() {
		Set<String> set = this.factorModel.getSelection();
		if(System.getProperty("os.name").toLowerCase().contains("windows"))
		{
			fileName = fileName.replace("\\", "//");
		} else {
			fileName = fileName.replace("\\", "/");
		}
		
		for (String selectedItem : set)
		{
			if(AnalysisUtils.isColumnNumeric(lstVarInfo, selectedItem))
			{
				numericModel.add(selectedItem);
				factorModel.remove(selectedItem);
			} else
			{
				Messagebox.show("Can't move variable. \n" + selectedItem +" is not Numeric.");
			}
		}
	}

	@NotifyChange({"factorModel","numericModel"})
	@Command("toFactor")
	public void toFactor() {
		Set<String> set = this.numericModel.getSelection();
		for(String selectedItem :set)
		{
			this.factorModel.add(selectedItem);
			this.numericModel.remove(selectedItem);
		}
	}

	@NotifyChange("*")
	@Command("chooseVariable")
	public boolean chooseVariable(@BindingParam("varTextBox") Textbox varTextBox,
			@BindingParam("imgButton") Image imgButton) {
		Set<String> set = factorModel.getSelection();
		if(varTextBox.getValue().isEmpty() && !set.isEmpty())
		{
			for(String selectedItem : set)
			{
				varTextBox.setValue(selectedItem);
				factorModel.remove(selectedItem);
			}
			
			if(varTextBox.getId().equals("genotypeTB"))
			{
				genotypeLevels = manager.getLevels(columnList, dataList, genotypeTB.getValue());
				geneotypeLevelsModel = AnalysisUtils.toListModelList(genotypeLevels);
			}
			
			imgButton.setSrc("/images/leftarrow_g.png");
			return true;
		} else if(!varTextBox.getValue().isEmpty())
		{
			factorModel.add(varTextBox.getValue());
			varTextBox.setValue(null);
		}
		imgButton.setSrc("/images/rightarrow_g.png");
		return false;
	}
	
	@NotifyChange("*")
	@Command("isEnvFactorRandomChecked")
	public void isEnvFactorRandomChecked(@BindingParam("isChecked") boolean isChecked)
	{
		System.out.println("Env Factor isRandom is " + isChecked);
	}

	@NotifyChange("*")
	@Command("uploadContrastFromFileOnGenotype")
	public void uploadContrastFromFileOnGenotype(@ContextParam(ContextType.BIND_CONTEXT)BindContext bindContext,
			@ContextParam(ContextType.VIEW) Component view) {
		if(genotypeTB.getValue().length() == 0)
		{
			Messagebox.show("Error: Please fill up genotype factor on Model Specifications Tab!", "Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		UploadEvent event = (UploadEvent) bindContext.getTriggerEvent();
		this.contrastFileNameOnGenotype = event.getMedia().getName();
		if(!this.contrastFileNameOnGenotype.endsWith(".csv")){
			Messagebox.show("Error: File must be a text-based csv format!", "Upload Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		this.uploadedContrastFileOnGenotype = FileUtilities.getFileFromUpload(bindContext, view);
		this.model.setGenotypeContrastFile(this.uploadedContrastFileOnGenotype.getAbsolutePath());
		refreshContrastCSVOnGenotype();
		this.fileNameOfContrastOnGenotypeLb.setVisible(true);
		this.defaultGenesContrastBtn.setVisible(false);
		this.contrastFromFileOnGenotypeBtn.setVisible(false);
		this.contrastByManuallyOnGenotypeBtn.setVisible(false);
		this.contrastByManuallyOnGenotypeDiv.setVisible(false);
		this.contrastResetOnGenotypeBtn.setVisible(true);
	}

	@NotifyChange("*")
	@Command("manuallyInputContrastOnGenotype")
	public void manuallyInputContrastOnGenotype() {
		if (genotypeTB.getValue().length() == 0) {
			Messagebox
					.show("Error: Please fill up genotype factor on Model Specifications Tab!",
							"Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (!this.contrastGridOnGenotypeDiv.getChildren().isEmpty()) {
			contrastGridOnGenotypeDiv.getFirstChild().detach();
		}

		gridManuallyOnGenotype = new Grid();
		gridManuallyOnGenotype.setId("contrastGridByManuallyOnGenotype");
		gridManuallyOnGenotype.setStyle("overflow:auto;");
		gridManuallyOnGenotype.setHeight("300px");
		gridManuallyOnGenotype.setVisible(true);
		gridManuallyOnGenotype.setSizedByContent(true);
		gridManuallyOnGenotype.setEmptyMessage("No data loaded...");

		columnsManuallyOnGenotype = new Columns();
		columnsManuallyOnGenotype.setParent(gridManuallyOnGenotype);
		columnsManuallyOnGenotype.setVisible(true);

		Column column = new Column();
		column.setLabel("Label");
		column.setHflex("1");
		column.setParent(columnsManuallyOnGenotype);

		for (int i = 0; i < this.genotypeLevels.length; i++) {
			Column genLabel = new Column();
			genLabel.setHflex("1");
			genLabel.setLabel(genotypeLevels[i]);
			genLabel.setParent(columnsManuallyOnGenotype);
		}

		rowsManuallyOnGenotype = new Rows();
		rowsManuallyOnGenotype.setParent(gridManuallyOnGenotype);
		rowsManuallyOnGenotype.setVisible(true);

		contrastGridOnGenotypeDiv.appendChild(gridManuallyOnGenotype);

		fileNameOfContrastOnGenotypeLb.setVisible(false);
		this.defaultGenesContrastBtn.setVisible(false);
		contrastFromFileOnGenotypeBtn.setVisible(false);
		contrastByManuallyOnGenotypeBtn.setVisible(false);
		contrastResetOnGenotypeBtn.setVisible(true);
		contrastByManuallyOnGenotypeDiv.setVisible(true);
	}
	
	@NotifyChange("*")
	@Command("defaultContrastOnGenotype")
	public void defaultContrastOnGenotype()
	{
		System.out.println("Selected number of genes is " + this.numberOfGenes);
		fileNameOfContrastOnGenotypeLb.setVisible(false);
		this.defaultGenesContrastBtn.setVisible(false);
		contrastFromFileOnGenotypeBtn.setVisible(false);
		contrastByManuallyOnGenotypeBtn.setVisible(false);
		contrastResetOnGenotypeBtn.setVisible(true);
		contrastByManuallyOnGenotypeDiv.setVisible(false);
	}
	
	@NotifyChange("*")
	@Command("defaultTwoGenesContrastOnGenotype")
	public void defaultTwoGenesContrastOnGenotype()
	{
		if (genotypeTB.getValue().length() == 0) {
			Messagebox
					.show("Error: Please fill up genotype factor on Model Specifications Tab!",
							"Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (!this.contrastGridOnGenotypeDiv.getChildren().isEmpty()) {
			contrastGridOnGenotypeDiv.getFirstChild().detach();
		}

		gridManuallyOnGenotype = new Grid();
		gridManuallyOnGenotype.setId("defaultTwoGenesContrastOnGenotype");
		gridManuallyOnGenotype.setStyle("overflow:auto;");
		gridManuallyOnGenotype.setHeight("300px");
		gridManuallyOnGenotype.setVisible(true);
		gridManuallyOnGenotype.setSizedByContent(true);
		gridManuallyOnGenotype.setEmptyMessage("No data loaded...");

		columnsManuallyOnGenotype = new Columns();
		columnsManuallyOnGenotype.setParent(gridManuallyOnGenotype);
		columnsManuallyOnGenotype.setVisible(true);

		Column column = new Column();
		column.setLabel("Label");
		column.setHflex("1");
		column.setParent(columnsManuallyOnGenotype);

		for (int i = 0; i < this.genotypeLevels.length; i++) {
			Column genLabel = new Column();
			genLabel.setHflex("1");
			genLabel.setLabel(genotypeLevels[i]);
			genLabel.setParent(columnsManuallyOnGenotype);
		}

		rowsManuallyOnGenotype = new Rows();
		rowsManuallyOnGenotype.setParent(gridManuallyOnGenotype);
		rowsManuallyOnGenotype.setVisible(true);

		contrastGridOnGenotypeDiv.appendChild(gridManuallyOnGenotype);

		fileNameOfContrastOnGenotypeLb.setVisible(false);
		contrastFromFileOnGenotypeBtn.setVisible(false);
		contrastByManuallyOnGenotypeBtn.setVisible(false);
		contrastResetOnGenotypeBtn.setVisible(true);
		contrastByManuallyOnGenotypeDiv.setVisible(true);
	}
	
	
	@NotifyChange("*")
	@Command("updateContrastGridManuallyOnGenotype")
	public void updateContrastGridManuallyOnGenotype(
			@BindingParam("inputValue") Integer value) {
		int numRows = rowsManuallyOnGenotype.getChildren().size();
		System.out.println("number of rows on genotype manually is " + numRows);
		if (value > numRows) {
			Row row = new Row();
			Textbox label = new Textbox();
			label.setHflex("1");
			label.setValue("C" + value);
			label.setVisible(true);
			label.setConstraint("no empty");
			label.setParent(row);

			for (int i = 0; i < this.genotypeLevels.length; i++) {
				Doublebox intbox = new Doublebox();
				intbox.setHflex("1");
				intbox.setVisible(true);
				intbox.setConstraint("no empty");
				intbox.setParent(row);
			}

			row.setParent(rowsManuallyOnGenotype);
		} else if (value < numRows && numRows > 0) {
			rowsManuallyOnGenotype.removeChild(rowsManuallyOnGenotype
					.getLastChild());
		}
	}



	@NotifyChange("*")
	@Command("resetContrastOnGenotype")
	public void resetContrastOnGenotype() {
		contrastDataListOnGen.clear();
		contrastColumnListOnGen.clear();
		this.numberOfContrastOnGenotypeDS.setValue(0.0);
		this.contrastFileNameOnGenotype = "";
		this.uploadedContrastFileOnGenotype = null;
		model.setGenotypeContrastFile(null);

		if (!this.contrastGridOnGenotypeDiv.getChildren().isEmpty())
			this.contrastGridOnGenotypeDiv.getFirstChild().detach();

		this.fileNameOfContrastOnGenotypeLb.setVisible(false);
		this.defaultGenesContrastBtn.setVisible(true);
		this.contrastFromFileOnGenotypeBtn.setVisible(true);
		this.contrastByManuallyOnGenotypeBtn.setVisible(true);
		this.contrastByManuallyOnGenotypeDiv.setVisible(false);
		this.contrastResetOnGenotypeBtn.setVisible(false);
	}

	@NotifyChange("*")
	@Command("updateContrastContrastGridManuallyOnGenotype")
	public void updateContrastContrastGridManuallyOnGenotype(
			@BindingParam("inputValue") Integer value) {

	}

	@NotifyChange("*")
	@Command("updateAMMIGraphicOptions")
	public void updateAMMIGraphicOptions(
			@BindingParam("status") boolean isChecked) {
		if(isChecked)
		{
			this.ammiGraphicOptionsDiv.setVisible(true);
		} else
		{
			this.ammiGraphicOptionsDiv.setVisible(false);
		}
	}

	@NotifyChange("*")
	@Command("updateGGEGraphicOptions")
	public void updateGGEGraphicOptions(
			@BindingParam("status") boolean isChecked) {
		if(isChecked)
		{
			this.ggeGraphicOptionsDiv.setVisible(true);
		} else
		{
			this.ggeGraphicOptionsDiv.setVisible(false);
		}
	}
	
	@NotifyChange("*")
	@Command("refreshCsv")
	public void refreshCsv()
	{
		activePage  = 0;
		CSVReader  reader;
		reloadCsvGrid();
		
		try{
			reader = new CSVReader(new FileReader(tempFile.getAbsolutePath()));
			List<String[]> rawData = reader.readAll();
			columnList.clear();
			dataList.clear();
			columnList = new ArrayList<String>(Arrays.asList(rawData.get(0)));
			rawData.remove(0);
			dataList = new ArrayList<String[]>(rawData);
		} catch(FileNotFoundException e)
		{
			e.printStackTrace();
		} catch(IOException e)
		{
			e.printStackTrace();
		} catch(NullPointerException e)
		{
			e.printStackTrace();
		}
	}
	
	@NotifyChange("*")
	@Command("refreshContrastCSVOnGenotype")
	public void refreshContrastCSVOnGenotype() {
		CSVReader reader;
		try {
			reader = new CSVReader(new FileReader(
					this.uploadedContrastFileOnGenotype.getAbsolutePath()));
			List<String[]> rawData = reader.readAll();
			contrastColumnListOnGen.clear();
			contrastDataListOnGen.clear();
			contrastColumnListOnGen = new ArrayList<String>(
					Arrays.asList(rawData.get(0)));
			rawData.remove(0);
			contrastDataListOnGen = new ArrayList<String[]>(rawData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reloadContrastCSVGridOnGenotype();
	}
	
	public void reloadCsvGrid()
	{
		if(gridReUploaded)
		{
			gridReUploaded = false;
			return;
		}
		if(!dataGridDiv.getChildren().isEmpty())
			dataGridDiv.getFirstChild().detach();
		Include incCSVData = new Include();
		incCSVData.setSrc("/user/updatestudy/csvdata.zul");
		incCSVData.setParent(dataGridDiv);
		gridReUploaded = true;
	}
	
	public void reloadContrastCSVGridOnGenotype() {
		if (!contrastGridOnGenotypeDiv.getChildren().isEmpty())
			contrastGridOnGenotypeDiv.getFirstChild().detach();
		Include incContrastCSVDataOnGenotype = new Include();
		incContrastCSVDataOnGenotype
				.setSrc("/user/analysis/pyramidedline/contrastongenotype.zul");
		incContrastCSVDataOnGenotype.setParent(contrastGridOnGenotypeDiv);
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

	public void setLstTypeOfDesign(List<String> lstTypeOfDesign) {
		this.lstTypeOfDesign = lstTypeOfDesign;
	}

	public List<String> getLstAnalysisType() {
		if (lstAnalysisType == null)
			lstAnalysisType = new ArrayList<String>();
		lstAnalysisType.clear();
		lstAnalysisType.add("Multi-Environment");
		lstAnalysisType.add("Single Environment");
		return lstAnalysisType;
	}

	public void setLstAnalysisType(List<String> lstAnalysisType) {
		this.lstAnalysisType = lstAnalysisType;
	}

	public String getResultRServe() {
		return resultRServe;
	}

	public void setResultRServe(String resultRServe) {
		this.resultRServe = resultRServe;
	}

	public PyramidedLineAnalysisModel getPyramidedLineAnalysisModel() {
		return model;
	}

	public void setPyramidedLineAnalysisModel(
			PyramidedLineAnalysisModel pyramidedLineAnalysisModel) {
		this.model = pyramidedLineAnalysisModel;
	}

	public List<String> getLstVarNames() {
		return lstVarNames;
	}

	public void setLstVarNames(List<String> lstVarNames) {
		this.lstVarNames = lstVarNames;
	}

	public double getAlphalevel() {
		return alphalevel;
	}

	public void setAlphalevel(double alphalevel) {
		this.alphalevel = alphalevel;
	}

	public String getContrastFileNameOnGenotype() {
		return contrastFileNameOnGenotype;
	}

	public void setContrastFileNameOnGenotype(String contrastFileNameOnGenotype) {
		this.contrastFileNameOnGenotype = contrastFileNameOnGenotype;
	}

	public String getTypeOfDesign() {
		return typeOfDesign;
	}

	public void setTypeOfDesign(String typeOfDesign) {
		this.typeOfDesign = typeOfDesign;
	}

	public String getAnalysisType() {
		return analysisType;
	}

	public void setAnalysisType(String analysisType) {
		this.analysisType = analysisType;
	}

	public List<String> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}

	public List<String[]> getDataList() {
		System.out.println("DataList Get:");
		reloadCsvGrid();
		return dataList;
	}

	public void setDataList(List<String[]> dataList) {
		this.dataList = dataList;
	}

	public List<String> getContrastColumnListOnGen() {
		return contrastColumnListOnGen;
	}

	public void setContrastColumnListOnGen(List<String> contrastColumnListOnGen) {
		this.contrastColumnListOnGen = contrastColumnListOnGen;
	}

	public List<String[]> getContrastDataListOnGen() {
		return contrastDataListOnGen;
	}

	public void setContrastDataListOnGen(List<String[]> contrastDataListOnGen) {
		this.contrastDataListOnGen = contrastDataListOnGen;
	}
	
	public ArrayList<ArrayList<String>> getCsvData()
	{
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		if(dataList.isEmpty())
			return result;
		for(int i = activePage * pageSize; i < activePage * pageSize + pageSize && i < dataList.size(); i++)
		{
			ArrayList<String> row = new ArrayList<String>();
			row.addAll(Arrays.asList(dataList.get(i)));
			result.add(row);
			row.add(0,"  ");
			System.out.println("On row " + i + ", data is " + Arrays.toString(dataList.get(i)) + "Row: " + row.get(0));
			
		}
		return result;
	}

	public int getPageSize() {
		return pageSize;
	}
	
	@NotifyChange("*")
	public void setPageSize(int pageSize) {
		System.out.println("page size " + pageSize);
		this.pageSize = pageSize;
	}
	
	@NotifyChange("*")
	public int getActivePage() {
		return activePage;
	}

	@NotifyChange("*")
	public void setActivePage(int activePage) {
		System.out.println("activage page " + activePage);
		reloadCsvGrid();
		this.activePage = activePage;
	}

	public int getTotalSize()
	{
		System.out.println("dataList size is " + dataList.size());
		return dataList.size();
	}

	public String getNumberOfGenes() {
		return numberOfGenes;
	}

	public void setNumberOfGenes(String numberOfGenes) {
		this.numberOfGenes = numberOfGenes;
	}
	
	
	
}
