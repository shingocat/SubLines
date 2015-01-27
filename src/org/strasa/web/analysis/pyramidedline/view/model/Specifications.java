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
	private Combobox lstGeneNumberCBB;
	private Combobox lstAnalysisEnvTypeCBB;
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
	private Div specifiedContrastOnGenoDiv;
	private Checkbox acrossEnvCB;
	private Div acrossEnvCBDiv;
	private Button defaultGenesContrastBtn;
	private Button contrastFromFileOnGenoBtn;
	private Button contrastByManuallyOnGenoBtn;
	private Button contrastResetOnGenoBtn;
	private Div contrastGridOnGenoDiv;
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
	private PyramidedLineRserveManager manager = new PyramidedLineRserveManager();;
	private StudyDataSetManagerImpl studyDataSetManagerImpl = new StudyDataSetManagerImpl();;
	private StudyManagerImpl studyManagerImpl = new StudyManagerImpl();
	private BrowseStudyManagerImpl browseStudyManagerImpl = new BrowseStudyManagerImpl();
	private UserFileManager userFileManager = new UserFileManager();;
	private PyramidedLineAnalysisModel model = new PyramidedLineAnalysisModel();
	private List<Study> lstStudy;
	private Study selectedStudy;
	private List<StudyDataSet> lstStudyDataSet;
	private StudyDataSet selectedStudyDataSet;
	private boolean isExternalFile = false;
	private String dataFileName;
	private List<String> lstTypeOfDesign;
	private String typeOfDesign;
	private List<String> lstAnalysisEnvType;
	private String analysisEnvType;
	private List<String> lstGeneNumber;
	private String geneNumber ="Bi-Genes";
	private String resultRServe;
	private List<String> columnList = new ArrayList<String>(); // column list of raw data;
	private List<String[]> dataList = new ArrayList<String[]>(); // raw data from selected data;
	private String fileName;
	private File uploadedFile;
	private String uploadedFileFolderPath;
	private List<String> lstVarInfo;
	private List<String> lstVarNames;
	private int fileFormat = 1;
	private String separator = "NULL";
	private ListModelList<String> numericModel;
	private ListModelList<String> responseModel;
	private ListModelList<String> factorModel;
	private List<String> respvars = new ArrayList<String>();
	private String[] genoLevels;
	private String[] envLevels;
	private ListModelList<String> genoLevelsModel;
	private boolean isSpecifyGenoContrast = false;
	private boolean isSpecifyEnvContrast = false;
	private HashMap<String, String> genoContrastFiles = new HashMap<String, String>();
	private HashMap<String, String> envContrastFiles = new HashMap<String, String>();
	private String errorMessage = null;

	@Init
	public void init(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view) {
		setAnalysisEnvType("Multi-Environment");
		setTypeOfDesign("Randomized Complete Block(RCB)");
		setGeneNumber("Bi-Genes");
	}

	@AfterCompose
	public void afterCompose(
			@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(component, this, false);

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
		this.lstGeneNumberCBB = (Combobox) component.getFellow("lstGeneNumberCBB");
		this.lstAnalysisEnvTypeCBB = (Combobox) component
				.getFellow("lstAnalysisEnvTypeCBB");
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
		// other options component
		this.includeOtherOptions = (Include) component
				.getFellow("includeOtherOptions");
		this.descriptiveStatCB = (Checkbox) this.includeOtherOptions
				.getFellow("descriptiveStatCB");
		this.varComponentCB = (Checkbox) this.includeOtherOptions
				.getFellow("varComponentCB");
		this.specifiedContrastOnGenoDiv = (Div) this.includeOtherOptions
				.getFellow("specifiedContrastOnGenoDiv");
		this.acrossEnvCB = (Checkbox) this.includeOtherOptions.getFellow("acrossEnvCB");
		this.acrossEnvCBDiv = (Div) this.includeOtherOptions.getFellow("acrossEnvCBDiv");
		this.defaultGenesContrastBtn = (Button) this.includeOtherOptions.getFellow("defaultGenesContrastBtn");
		this.contrastFromFileOnGenoBtn = (Button) this.includeOtherOptions
				.getFellow("contrastFromFileOnGenoBtn");
		this.contrastByManuallyOnGenoBtn = (Button) this.includeOtherOptions
				.getFellow("contrastByManuallyOnGenoBtn");
		this.contrastResetOnGenoBtn = (Button) this.includeOtherOptions
				.getFellow("contrastResetOnGenoBtn");
		this.contrastGridOnGenoDiv = (Div) this.includeOtherOptions
				.getFellow("contrastGridOnGenoDiv");
		this.specifiedContrastOnEnvDiv = (Div) this.includeOtherOptions
				.getFellow("specifiedContrastOnEnvDiv");
		this.contrastFromFileOnEnvBtn = (Button) this.includeOtherOptions
				.getFellow("contrastFromFileOnEnvBtn");
		this.contrastByManuallyOnEnvBtn = (Button) this.includeOtherOptions
				.getFellow("contrastByManuallyOnEnvBtn");
		this.contrastResetOnEnvBtn = (Button) this.includeOtherOptions
				.getFellow("contrastResetOnEnvBtn");
		this.contrastGridOnEnvDiv = (Div) this.includeOtherOptions
				.getFellow("contrastGridOnEnvDiv");
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
		List<StudyDataSet> dataSet = studyDataSetManagerImpl.getDataSetsByStudyId(selectedStudy.getId());
		if(!dataSet.isEmpty())
		{
			dataSetCombobox.setVisible(true);
			setLstStudyDataSet(dataSet);
			BindUtils.postNotifyChange(null, null, this, "*");
		} else
		{
			showMessage("Please choose a different study! Study has no data!");
		}
	}

	@NotifyChange("*")
	@Command("displaySelectedDataSet")
	public void displaySelectedDataSet(@ContextParam(ContextType.BIND_CONTEXT)BindContext bindContext,
			@ContextParam(ContextType.VIEW) Component view) {
		// clear before status of columnList and dataList
		if(columnList != null && ! columnList.isEmpty())
		{
			columnList.clear();
			dataList.clear();
		}
		reloadCsvGrid();
		this.selectDataBtn.setVisible(false);
		this.studiesCombobox.setVisible(false);
		this.resetBtn.setVisible(true);
		this.uploadCSVBtn.setVisible(false);

		List<HashMap<String, String>> toreturn = browseStudyManagerImpl
				.getStudyData(selectedStudy.getId(),
						selectedStudyDataSet.getDatatype(),
						selectedStudyDataSet.getId());
		List<StudyDataColumn> columns = new StudyDataColumnManagerImpl()
		.getStudyDataColumnByStudyId(selectedStudy.getId(),
				selectedStudyDataSet.getDatatype(),
				selectedStudyDataSet.getId());
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
		File BASE_FOLDER = new File(UserFileManager.buildUserPath(selectedStudy.getUserid(), selectedStudy.getId(), fileName));
		if (!BASE_FOLDER.exists())
			BASE_FOLDER.mkdirs();
		String createFile = BASE_FOLDER.getAbsolutePath() + File.separator + fileName + "(Dataset).csv";
		uploadedFile = FileUtilities.createFileFromDatabase(columnList,dataList, createFile);
		if(uploadedFile == null)
			return;
		if(uploadedFileFolderPath != null)
		{	
			this.uploadedFileFolderPath = null;
			this.uploadedFileFolderPath = BASE_FOLDER.getAbsolutePath() + File.separator;
		} else
		{
			this.uploadedFileFolderPath = BASE_FOLDER.getAbsolutePath() + File.separator;
		}
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("FilePath", uploadedFile.getAbsolutePath());
		BindUtils.postGlobalCommand(null, null, "setPyramidedLineAnalysisModelListVariables", args);
		isExternalFile = false;
	}

	@NotifyChange("*")
	@GlobalCommand("setPyramidedLineAnalysisModelListVariables")
	public void setPyramidedLineAnalysisModelListVariables(@BindingParam("FilePath") String filePath)
	{
		model.setDataFileName(filePath.replace(StringConstants.BSLASH, StringConstants.FSLASH));
		lstVarInfo = manager.getVariableInfo(
				filePath.replace("//", "/"), fileFormat, separator);
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
	@Command("selectFromDatabase")
	public void selectFromDatabase(@ContextParam(ContextType.BIND_CONTEXT)BindContext bindContext, @ContextParam(ContextType.VIEW) Component view) {
		lstStudy = studyManagerImpl.getStudiesByUserID(SecurityUtil.getDbUser().getId());
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
			showMessage("Error: File must be a text-based csv format!");
			return;
		}
		File tempFile = null;
		if(tempFile == null)
		{
			try{
				tempFile = File.createTempFile(fileName, ".tmp");
			} catch(IOException e)
			{
				e.printStackTrace();
			}
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
				isExternalFile = true;
				dataFileName = fileName;
				Map<String, Object> args = new HashMap<String, Object>();
				args.put("FilePath", filePath);
				BindUtils.postGlobalCommand(null, null,"setPyramidedLineAnalysisModelListVariables", args);
				refreshCsv(uploadedFile);
				resetBtn.setVisible(true);
				uploadCSVBtn.setVisible(false);
				selectDataBtn.setVisible(false);
	}

	@NotifyChange("*")
	@Command("updateAnalysisEnvType")
	public void updateAnalysisEnvType(
			@BindingParam("selectedIndex") Integer selectedType) {
		System.out.println("Analysis Type index " + selectedType);
		switch (selectedType) {
		case 0: {
			this.model
			.setAnalysisEnvType("Multi-Environment");
			if(!acrossEnvCB.isChecked())
			{
				this.acrossEnvCB.setChecked(true);
			}
			this.acrossEnvCBDiv.setVisible(false);
			this.specifiedContrastOnEnvDiv.setVisible(true);
			this.genotypeByEnvDiv.setVisible(true);
			this.singleEnvOnGraphDiv.setVisible(false);
			this.acrossEnvOnGraphDiv.setVisible(true);
			this.multiplicativeModelOnGraphDiv.setVisible(true);
			break;
		}
		case 1: {
			this.model
			.setAnalysisEnvType("Single Environment");
			if(!acrossEnvCB.isChecked())
			{
				this.acrossEnvCB.setChecked(true);
			}
			this.acrossEnvCBDiv.setVisible(true);
			this.specifiedContrastOnEnvDiv.setVisible(false);
			this.genotypeByEnvDiv.setVisible(false);
			this.singleEnvOnGraphDiv.setVisible(true);
			this.acrossEnvOnGraphDiv.setVisible(false);
			this.multiplicativeModelOnGraphDiv.setVisible(false);
			break;
		}
		default: {
			this.model
			.setAnalysisEnvType("Multi-Environment");
			if(!acrossEnvCB.isChecked())
			{
				this.acrossEnvCB.setChecked(true);
			}
			this.acrossEnvCBDiv.setVisible(false);
			this.specifiedContrastOnEnvDiv.setVisible(true);
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
	@Command("updateGeneNumber")
	public void updateGeneNumber(@BindingParam("selectedValue") String selectedValue)
	{
		System.out.println("Number of Genes selected " + selectedValue);
	}

	@NotifyChange("*")
	@Command("validatePyramidedLineAnalysisInputs")
	public void validatePyramidedLineAnalysisInputs() {
		if (validatePLAanalysisModel()) {
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("Model", this.model);
			BindUtils.postGlobalCommand(null, null, "displaySSSLResult", args);
		} else {
			showMessage(errorMessage);
		}
	}
	
	public boolean validatePLAanalysisModel()
	{
		
		return true;
	}


	@NotifyChange("*")
	@Command("addResponse")
	public void addResponse() {
		Set<String> set = numericModel.getSelection();
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
				showMessage("Can't move variable. \n" + selectedItem +" is not Numeric.");
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
				genoLevels = manager.getLevels(columnList, dataList, genotypeTB.getValue());
				if(!validateGenoLevels())
				{
					factorModel.add(varTextBox.getValue());
					varTextBox.setValue(null);
					return false;
				}
				genoLevelsModel = AnalysisUtils.toListModelList(genoLevels);
			}

			if(varTextBox.getId().equals("envTB"))
			{
				this.envLevels = manager.getLevels(columnList, 
						dataList, envTB.getValue());
			}

			imgButton.setSrc("/images/leftarrow_g.png");
			return true;
		} else if(!varTextBox.getValue().isEmpty())
		{
			factorModel.add(varTextBox.getValue());
			varTextBox.setValue(null);
			if(varTextBox.getId().equals("genotypeTB"))
			{
				genoLevels = null;
				genoLevelsModel.clear();
			}
			if(varTextBox.getId().equals("envTB"))
			{
				envLevels = null;
			}
		}
		imgButton.setSrc("/images/rightarrow_g.png");
		return false;
	}

	@NotifyChange("*")
	@Command("uploadContrastFromFileOnGeno")
	public void uploadContrastFromFileOnGeno(
			@ContextParam(ContextType.BIND_CONTEXT)BindContext bindContext,
			@ContextParam(ContextType.VIEW) Component view)
	{
		//		the fileName is the file for analysis
		if(fileName == null){
			showMessage("Please selected or uploaded raw data at frist!");
			return;
		}
		if (genotypeTB.getValue().length() == 0) {
			showMessage("Error: Please fill up genotype factor on Model Specifications Tab!");
			return;
		}
		if (!this.contrastGridOnGenoDiv.getChildren().isEmpty())
			contrastGridOnGenoDiv.getFirstChild().detach();

		// basing on whether user choose across env
		Include inc = new Include();
		inc.setParent(this.contrastGridOnGenoDiv);
		HashMap<String, Object> args = new HashMap<String, Object>();
		args.put("Factor", "Genotype");
		List<String> lstLevelNames = new ArrayList<String>();
		if(acrossEnvCB.isChecked())
		{	
			lstLevelNames.add("AcrossEnv");
			args.put("LevelNames", lstLevelNames);
			HashMap<String, List<String>> hmGenosOnEnv = new HashMap<String, List<String>>();
			hmGenosOnEnv.put("AcrossEnv", Arrays.asList(genoLevels));
			args.put("Levels", hmGenosOnEnv);
		} else
		{
			if(!envTB.getValue().isEmpty())
			{
				args.put("LevelNames", Arrays.asList(envLevels));
				args.put("Levels", manager.getLevelsOnOtherLevels(columnList, dataList, genotypeTB.getValue(), envTB.getValue()));

			} else
			{
				lstLevelNames.add("AcrossEnv");
				args.put("EnvNames", lstLevelNames);
				HashMap<String, List<String>> hmGenosOnEnv = new HashMap<String, List<String>>();
				hmGenosOnEnv.put("AcrossEnv", Arrays.asList(genoLevels));
				args.put("Levels", hmGenosOnEnv);
			}
		}
		args.put("Type", "File");
		args.put("UploadedFileFolderPath", uploadedFileFolderPath);
		inc.setDynamicProperty("Arguments", args);
		inc.setSrc("/user/analysis/contrasttabbox.zul");
		this.acrossEnvCBDiv.setVisible(false);
		this.defaultGenesContrastBtn.setVisible(false);
		this.contrastFromFileOnGenoBtn.setVisible(false);
		this.contrastByManuallyOnGenoBtn.setVisible(false);
		this.contrastResetOnGenoBtn.setVisible(true);
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
		hmGenosOnEnv.put("Environment", Arrays.asList(envLevels));
		args.put("Levels", hmGenosOnEnv);
		args.put("Type", "File");
		args.put("UploadedFileFolderPath", uploadedFileFolderPath);
		inc.setDynamicProperty("Arguments", args);
		inc.setSrc("/user/analysis/contrasttabbox.zul");
		this.contrastFromFileOnEnvBtn.setVisible(false);
		this.contrastByManuallyOnEnvBtn.setVisible(false);
		this.contrastResetOnEnvBtn.setVisible(true);
		this.isSpecifyEnvContrast = true;
	}
	@NotifyChange("*")
	@Command("manuallyInputContrastOnGeno")
	public void manuallyInputContrastOnGeno() {
		if(fileName == null){
			showMessage("Please selected or uploaded raw data at frist!");
			return;
		}
		if (genotypeTB.getValue().length() == 0) {
			showMessage("Please fill up genotype factor on Model Specifications Tab!");
			return;
		}
		if (!this.contrastGridOnGenoDiv.getChildren().isEmpty())
			contrastGridOnGenoDiv.getFirstChild().detach();
		// basing on whether user choose across env
		Include inc = new Include();
		inc.setParent(this.contrastGridOnGenoDiv);
		HashMap<String, Object> args = new HashMap<String, Object>();
		List<String> lstLevelNames = new ArrayList<String>();
		if(acrossEnvCB.isChecked())
		{	
			lstLevelNames.add("AcrossEnv");
			args.put("LevelNames", lstLevelNames);
			HashMap<String, List<String>> hmGenosOnEnv = new HashMap<String, List<String>>();
			hmGenosOnEnv.put("AcrossEnv", Arrays.asList(genoLevels));
			args.put("Levels", hmGenosOnEnv);
		} else
		{
			if(!envTB.getValue().isEmpty())
			{
				args.put("LevelNames", Arrays.asList(envLevels));
				args.put("Levels", manager.getLevelsOnOtherLevels(columnList, dataList, genotypeTB.getValue(), envTB.getValue()));

			} else
			{
				lstLevelNames.add("AcrossEnv");
				args.put("LevelNames", lstLevelNames);
				HashMap<String, List<String>> hmGenosOnEnv = new HashMap<String, List<String>>();
				hmGenosOnEnv.put("AcrossEnv", Arrays.asList(genoLevels));
				args.put("Levels", hmGenosOnEnv);
			}
		}
		args.put("Factor", "Genotype");
		args.put("Type", "Manual");
		args.put("UploadedFileFolderPath", uploadedFileFolderPath);
		inc.setDynamicProperty("Arguments", args);
		inc.setSrc("/user/analysis/contrasttabbox.zul");
		this.acrossEnvCBDiv.setVisible(false);
		this.defaultGenesContrastBtn.setVisible(false);
		this.contrastFromFileOnGenoBtn.setVisible(false);
		this.contrastByManuallyOnGenoBtn.setVisible(false);
		this.contrastResetOnGenoBtn.setVisible(true);
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
		String [] envs = manager.getLevels(columnList, dataList, envTB.getValue());
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
		this.isSpecifyEnvContrast = true;
	}
	@NotifyChange("*")
	@Command("defaultContrastOnGeno")
	public void defaultContrastOnGeno()
	{
		if(fileName == null){
			showMessage("Please selected or uploaded raw data at frist!");
			return;
		}
		if (genotypeTB.getValue().length() == 0) {
			showMessage("Error: Please fill up genotype factor on Model Specifications Tab!");
			return;
		}
		if (!this.contrastGridOnGenoDiv.getChildren().isEmpty())
			contrastGridOnGenoDiv.getFirstChild().detach();
		//create the default contrast under the uploaded file folder path;
		int genes = 2;
		if(geneNumber.equals("Tri-Genes"))
			genes = 3;
		else if(geneNumber.equals("Quadra-Genes"))
			genes = 4;
		String defaultContrastFile = new PyramidedLineRserveManager()
		.getDefaultContrast(genes, uploadedFileFolderPath);
		if(defaultContrastFile == null)
		{
			showMessage("Could not retrieve default contrast!");
			return;
		}
		// basing on whether user choose across env
		Include inc = new Include();
		inc.setParent(this.contrastGridOnGenoDiv);
		HashMap<String, Object> args = new HashMap<String, Object>();
		args.put("Factor", "Genotype");
		List<String> lstLevelNames = new ArrayList<String>();
		if(acrossEnvCB.isChecked())
		{	
			lstLevelNames.add("AcrossEnv");
			args.put("LevelNames", lstLevelNames);
			HashMap<String, List<String>> hmGenosOnEnv = new HashMap<String, List<String>>();
			hmGenosOnEnv.put("AcrossEnv", Arrays.asList(genoLevels));
			args.put("Levels", hmGenosOnEnv);
		} else
		{
			if(!envTB.getValue().isEmpty())
			{
				args.put("LevelNames", Arrays.asList(envLevels));
				args.put("Levels", manager.getLevelsOnOtherLevels(columnList, dataList, genotypeTB.getValue(), envTB.getValue()));

			} else
			{
				lstLevelNames.add("AcrossEnv");
				args.put("EnvNames", lstLevelNames);
				HashMap<String, List<String>> hmGenosOnEnv = new HashMap<String, List<String>>();
				hmGenosOnEnv.put("AcrossEnv", Arrays.asList(genoLevels));
				args.put("Levels", hmGenosOnEnv);
			}
		}
		args.put("Type", geneNumber);
		args.put("DefaultContrastFile", defaultContrastFile);
		args.put("UploadedFileFolderPath", uploadedFileFolderPath);
		inc.setDynamicProperty("Arguments", args);
		inc.setSrc("/user/analysis/contrasttabbox.zul");
		this.acrossEnvCBDiv.setVisible(false);
		this.defaultGenesContrastBtn.setVisible(false);
		this.contrastFromFileOnGenoBtn.setVisible(false);
		this.contrastByManuallyOnGenoBtn.setVisible(false);
		this.contrastResetOnGenoBtn.setVisible(true);
	}

	@NotifyChange("*")
	@Command("resetContrastOnGeno")
	public void resetContrastOnGeno() {
		genoContrastFiles.clear();
		model.setGenotypeContrastFile(null);
		if (!this.contrastGridOnGenoDiv.getChildren().isEmpty())
			this.contrastGridOnGenoDiv.getFirstChild().detach();
		this.defaultGenesContrastBtn.setVisible(true);
		this.contrastFromFileOnGenoBtn.setVisible(true);
		this.contrastByManuallyOnGenoBtn.setVisible(true);
		this.contrastResetOnGenoBtn.setVisible(false);
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
		model.setGenotypeContrastFile(null);
		if (!this.contrastGridOnEnvDiv.getChildren().isEmpty())
			this.contrastGridOnEnvDiv.getFirstChild().detach();
		this.contrastFromFileOnEnvBtn.setVisible(true);
		this.contrastByManuallyOnEnvBtn.setVisible(true);
		this.contrastResetOnEnvBtn.setVisible(false);
		this.isSpecifyEnvContrast = false;
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
	public void refreshCsv(File dataFile)
	{
		CSVReader  reader;
		reloadCsvGrid();
		try{
			reader = new CSVReader(new FileReader(dataFile.getAbsolutePath()));
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
	@Command("refreshContrastCSVOnGeno")
	public void refreshContrastCSVOnGeno() {
		reloadContrastCSVGridOnGeno();
	}

	public boolean validateGenoLevels()
	{
		if(genoLevels.length == 0)
			return false;
		if(geneNumber.equals("Bi-Genes"))
		{
			List<String> biGeneLevels = new ArrayList<String>(
					Arrays.asList(
							"aabb", "aaBb", "aaBB", "Aabb", "AaBb",
							"AaBB", "AAbb", "AABb", "AABB"
							));
			for(String s : genoLevels)
			{
				if(!biGeneLevels.contains(s))
				{
					showMessage("The genotype factor should be coded as AABB, AABb,"
							+ " ..., aabb on " + geneNumber + " design!");
					return false;
				}
			}
		} else if(geneNumber.equals("Tri-Genes"))
		{
			List<String> triGeneLevels = new ArrayList<String>(
					Arrays.asList(
							"aabbcc", "aabbCc", "aabbCC", "aaBbcc",
							"aaBbCc", "aaBbCC", "aaBBcc", "aaBBCc",
							"aaBBCC", "Aabbcc", "AabbCc", "AabbCC",
							"AaBbcc", "AaBbCc", "AaBbCC", "AaBBcc",
							"AaBBCc", "AaBBCC", "AAbbcc", "AAbbCc",
							"AAbbCC", "AABbcc", "AABbCc", "AABbCC",
							"AABBcc", "AABBCc", "AABBCC"
							));
			for(String s : genoLevels)
			{
				if(!triGeneLevels.contains(s))
				{
					showMessage("The genotype factor should be coded as AABBCC, AABBCc,"
							+ " ..., aabbcc on " + geneNumber + " design!");
					return false;
				}
			}
		} else if(geneNumber.equals("Quadra-Genes"))
		{
			List<String> quadraGeneLevels = new ArrayList<String>(
					Arrays.asList(
							"aabbccdd", "aabbccDd", "aabbccDD",
							"aabbCcdd", "aabbCcDd", "aabbCcDD",
							"aabbCCdd", "aabbCCDd", "aabbCCDD",
							"aaBbccdd", "aaBbccDd", "aaBbccDD",
							"aaBbCcdd", "aaBbCcDd", "aaBbCcDD",
							"aaBbCCdd", "aaBbCCDd", "aaBbCCDD",
							"aaBBccdd", "aaBBccDd", "aaBBccDD",
							"aaBBCcdd", "aaBBCcDd", "aaBBCcDD",
							"aaBBCCdd", "aaBBCCDd", "aaBBCCDD",
							"Aabbccdd", "AabbccDd", "AabbccDD",
							"AabbCcdd", "AabbCcDd", "AabbCcDD",
							"AabbCCdd", "AabbCCDd", "AabbCCDD",
							"AaBbccdd", "AaBbccDd", "AaBbccDD",
							"AaBbCcdd", "AaBbCcDd", "AaBbCcDD",
							"AaBbCCdd", "AaBbCCDd", "AaBbCCDD",
							"AaBBccdd", "AaBBccDd", "AaBBccDD",
							"AaBBCcdd", "AaBBCcDd", "AaBBCcDD",
							"AaBBCCdd", "AaBBCCDd", "AaBBCCDD",
							"AAbbccdd", "AAbbccDd", "AAbbccDD",
							"AAbbCcdd", "AAbbCcDd", "AAbbCcDD",
							"AAbbCCdd", "AAbbCCDd", "AAbbCCDD",
							"AABbccdd", "AABbccDd", "AABbccDD",
							"AABbCcdd", "AABbCcDd", "AABbCcDD",
							"AABbCCdd", "AABbCCDd", "AABbCCDD",
							"AABBccdd", "AABBccDd", "AABBccDD",
							"AABBCcdd", "AABBCcDd", "AABBCcDD",
							"AABBCCdd", "AABBCCDd", "AABBCCDD"
							));
			for(String s : genoLevels)
			{
				if(!quadraGeneLevels.contains(s))
				{
					showMessage("The genotype factor should be coded as AABBCCDD, AABBCCDd,"
							+ " ..., aabbccdd on " + geneNumber + " design!");
					return false;
				}
			}		
		}
		return true;
	}

	public void reloadCsvGrid()
	{
		if(!dataGridDiv.getChildren().isEmpty())
			dataGridDiv.getFirstChild().detach();
		Include incCSVData = new Include();
		incCSVData.setSrc("/user/updatestudy/csvdata.zul");
		incCSVData.setParent(dataGridDiv);
	}

	public void reloadContrastCSVGridOnGeno() {
		if (!contrastGridOnGenoDiv.getChildren().isEmpty())
			contrastGridOnGenoDiv.getFirstChild().detach();
		Include incContrastCSVDataOnGenotype = new Include();
		incContrastCSVDataOnGenotype
		.setSrc("/user/analysis/pyramidedline/contrastongenotype.zul");
		incContrastCSVDataOnGenotype.setParent(contrastGridOnGenoDiv);
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

	public boolean getIsExternalFile() {
		return isExternalFile;
	}

	public void setExternalFile(boolean isExternalFile) {
		this.isExternalFile = isExternalFile;
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

	public List<String> getLstAnalysisEnvType() {
		if (lstAnalysisEnvType == null)
			lstAnalysisEnvType = new ArrayList<String>();
		lstAnalysisEnvType.clear();
		lstAnalysisEnvType.add("Multi-Environment");
		lstAnalysisEnvType.add("Single Environment");
		return lstAnalysisEnvType;
	}

	public List<String> getLstGeneNumber()
	{
		if(lstGeneNumber == null)
			lstGeneNumber = new ArrayList<String>();
		lstGeneNumber.clear();
		lstGeneNumber.add("Bi-Genes");
		lstGeneNumber.add("Tri-Genes");
		lstGeneNumber.add("Quadra-Genes");
		return lstGeneNumber;
	}

	public void setLstAnalysisType(List<String> lstAnalysisType) {
		this.lstAnalysisEnvType = lstAnalysisType;
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

	public String getTypeOfDesign() {
		return typeOfDesign;
	}

	public void setTypeOfDesign(String typeOfDesign) {
		this.typeOfDesign = typeOfDesign;
	}

	public String getAnalysisEnvType() {
		return analysisEnvType;
	}

	public void setAnalysisEnvType(String analysisEnvType) {
		this.analysisEnvType = analysisEnvType;
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

	public String getGeneNumber() {
		return geneNumber;
	}

	public void setGeneNumber(String geneNumber) {
		this.geneNumber = geneNumber;
	}

	public void showMessage(String message)
	{
		Messagebox.show(message, "Warning", Messagebox.OK, Messagebox.INFORMATION);
	}

}
