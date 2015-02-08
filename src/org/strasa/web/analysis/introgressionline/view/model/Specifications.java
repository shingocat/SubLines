package org.strasa.web.analysis.introgressionline.view.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.filesystem.manager.UserFileManager;
import org.strasa.middleware.manager.BrowseStudyManagerImpl;
import org.strasa.middleware.manager.StudyDataColumnManagerImpl;
import org.strasa.middleware.manager.StudyDataSetManagerImpl;
import org.strasa.middleware.manager.StudyManagerImpl;
import org.strasa.middleware.model.Study;
import org.strasa.middleware.model.StudyDataColumn;
import org.strasa.middleware.model.StudyDataSet;
import org.strasa.web.analysis.view.model.ILAnalysisModel;
import org.strasa.web.analysis.view.model.IntrogressionLineAnalysisModel;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
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
	private Div ilSpecWindow;
	//files specification div
	private Div filesSpecDiv;
	private Label genoFileLb;
	private Button selectGenoFromDBBtn;
	private Button selectGenoFromFileBtn;
	private Button resetGenoBtn;
	private Label phenoFileLb;
	private Button selectPhenoFromDBBtn;
	private Combobox studiesCBB;
	private Combobox dataSetCBB;
	private Button selectPhenoFromFileBtn;
	private Button resetPhenoBtn;
	private Label mapFileLb;
	private Button selectMapFromDBBtn;
	private Button selectMapFromFileBtn;
	private Button resetMapBtn;
	//method specification div
	private Div methodSpecDiv;
	private Combobox analysisMethodCBB;
	private Div analysisMethodDiv;
	private Div mmaDiv;
	private Radiogroup regMethodRG;
	private Radio lassoRadio;
	private Radio ridgeRadio;
	private Radio elesticRadio;
	private Radiogroup pvalueRG;
	private Radio medianRadio;
	private Radio fdrRadio;
	private Radio holmRadio;
	private Radio QARadio;
	private Decimalbox alphaDB;
	private Intbox bootstrapIB;
	private Intbox nfoldsIB;
	private Checkbox includeHtOnMMCB;
	private Decimalbox stepDB;
	private Intbox maxTryIB;
	private Div smaDiv;
	private Checkbox includeHtOnSMCB;
	private Intbox digitsIB;
	private Radiogroup testRG;
	private Radio ftestRadio;
	private Radio ctestRadio;
	private Div csDiv;
	private Checkbox includeHtOnCSCB;
	private Checkbox simPvalueCB;
	private Intbox bIB;
	private Label refGenoLb;
	private Button uploadRefGenoBtn;
	private Button resetRefGenoBtn;
	private Tabbox dataTB;
	private Tabs dataTabs;
	private Tab genoDataTab;
	private Tab phenoDataTab;
	private Tab mapDataTab;
	private Tabpanels dataTabpanels;
	private Tabpanel genoDataTP;
	private Tabpanel phenoDataTP;
	private Tabpanel mapDataTP;
	private Button runBtn;
	private Button resetBtn;

	String genoFileName;
	File genoFile;
	String genoRefFileName;
	File genoRefFile;
	String phenoFileName;
	File phenoFile;
	String mapFileName;
	File mapFile;
	List<String> lstAnalysisMethod;
	String analysisMethod;
	Double alpha;
	Integer bootstrap;
	Integer nfolds;
	Double step;
	Integer maxTry;
	Integer digits;
	Integer b;
	String resultRServe;
	UserFileManager userFileManager = new UserFileManager();
	String uploadedFileFolderPath;
	boolean isSpecGenoFile;
	boolean isSpecGenoRefFile;
	boolean isSpecPhenoFile;
	boolean isSpecMapFile;
	ILAnalysisModel model = new ILAnalysisModel();
	String genoCol;
	String dpCode;
	String rpCode;
	String htCode;
	String naCode;
	Integer bcn;
	Integer fn;
	String genoRefCol;
	String dpRefCode;
	String rpRefCode;
	String htRefCode;
	String naRefCode;
	Integer bcnRef;
	Integer fnRef;
	String dataTypeOnPD;
	String envAnalysisTypeOnPD;
	String naCodeOnPD;
	String genotypeOnPD;
	String environmentOnPD;
	String blockOnPD;
	String replicateOnPD;
	String rowOnPD;
	String columnOnPD;
	String expDesignOnPD;
	String markerOnMD;
	String chromosomeOnMD;
	String positionOnMD;
	String unitOnMD;
	List<String> respVars;
	String errorMessage;
	List<Study> lstStudy;
	Study selectedStudy; 
	List<StudyDataSet> lstDataSet;
	StudyDataSet selectedDataSet;
	StudyManagerImpl studyManagerImpl;
	StudyDataSetManagerImpl studyDataSetManagerImpl;
	BrowseStudyManagerImpl browseStudyManagerImpl;

	//setting the initiate variables
	@Init
	public void init(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view)
	{
		//do nothing now
		setAnalysisMethod("Chisq");
		setAlpha(0.05);
		setBootstrap(10);
		setNfolds(3);
		setStep(0.1);
		setMaxTry(10);
	}

	@AfterCompose
	public void afterCompose(
			@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(component, this, false);
		// wire all the component
		this.component = component;
		this.ilSpecWindow = (Div) component
				.getFellow("ilSpecWindow");
		this.filesSpecDiv = (Div) component.getFellow("filesSpecDiv");
		this.genoFileLb = (Label) component.getFellow("genoFileLb");
		this.selectGenoFromDBBtn = (Button) component.getFellow("selectGenoFromDBBtn");
		this.selectGenoFromFileBtn = (Button) component.getFellow("selectGenoFromFileBtn");
		this.resetGenoBtn = (Button) component.getFellow("resetGenoBtn");
		this.phenoFileLb = (Label) component.getFellow("phenoFileLb");
		this.selectPhenoFromDBBtn = (Button) component.getFellow("selectPhenoFromDBBtn");
		this.studiesCBB = (Combobox) component.getFellow("studiesCBB");
		this.dataSetCBB = (Combobox) component.getFellow("dataSetCBB");
		this.selectPhenoFromFileBtn = (Button) component.getFellow("selectPhenoFromFileBtn");
		this.resetPhenoBtn = (Button) component.getFellow("resetPhenoBtn");
		this.mapFileLb = (Label) component.getFellow("mapFileLb");
		this.selectMapFromDBBtn = (Button) component.getFellow("selectMapFromDBBtn");
		this.selectMapFromFileBtn = (Button) component.getFellow("selectMapFromFileBtn");
		this.resetMapBtn = (Button) component.getFellow("resetMapBtn");
		this.methodSpecDiv = (Div) component.getFellow("methodSpecDiv");
		this.analysisMethodCBB = (Combobox) component.getFellow("analysisMethodCBB");
		this.analysisMethodDiv = (Div) component.getFellow("analysisMethodDiv");
		this.mmaDiv = (Div) component.getFellow("mmaDiv");
		this.regMethodRG = (Radiogroup) component.getFellow("regMethodRG");
		lassoRadio = (Radio) component.getFellow("lassoRadio");
		ridgeRadio = (Radio) component.getFellow("ridgeRadio");
		elesticRadio = (Radio) component.getFellow("elesticRadio");
		pvalueRG = (Radiogroup) component.getFellow("pvalueRG");
		medianRadio = (Radio) component.getFellow("medianRadio");
		fdrRadio = (Radio) component.getFellow("fdrRadio");
		holmRadio= (Radio) component.getFellow("holmRadio");
		QARadio= (Radio) component.getFellow("QARadio");
		alphaDB= (Decimalbox) component.getFellow("alphaDB");
		bootstrapIB= (Intbox) component.getFellow("bootstrapIB");
		nfoldsIB= (Intbox) component.getFellow("nfoldsIB");
		includeHtOnMMCB= (Checkbox) component.getFellow("includeHtOnMMCB");
		stepDB= (Decimalbox) component.getFellow("stepDB");
		maxTryIB= (Intbox) component.getFellow("maxTryIB");
		smaDiv= (Div) component.getFellow("smaDiv");
		includeHtOnSMCB= (Checkbox) component.getFellow("includeHtOnSMCB");
		digitsIB= (Intbox) component.getFellow("digitsIB");
		testRG= (Radiogroup) component.getFellow("testRG");
		ftestRadio= (Radio) component.getFellow("ftestRadio");
		ctestRadio= (Radio) component.getFellow("ctestRadio");
		csDiv= (Div) component.getFellow("csDiv");
		includeHtOnCSCB= (Checkbox) component.getFellow("includeHtOnCSCB");
		simPvalueCB= (Checkbox) component.getFellow("simPvalueCB");
		bIB= (Intbox) component.getFellow("bIB");
		refGenoLb = (Label) component.getFellow("refGenoLb");
		uploadRefGenoBtn= (Button) component.getFellow("uploadRefGenoBtn");
		resetRefGenoBtn = (Button) component.getFellow("resetRefGenoBtn");
		dataTB= (Tabbox) component.getFellow("dataTB");
		dataTabs= (Tabs) component.getFellow("dataTabs");
		genoDataTab= (Tab) component.getFellow("genoDataTab");
		phenoDataTab= (Tab) component.getFellow("phenoDataTab");
		mapDataTab= (Tab) component.getFellow("mapDataTab");
		dataTabpanels= (Tabpanels) component.getFellow("dataTabpanels");
		genoDataTP= (Tabpanel) component.getFellow("genoDataTP");
		phenoDataTP= (Tabpanel) component.getFellow("phenoDataTP");
		mapDataTP= (Tabpanel) component.getFellow("mapDataTP");
		runBtn = (Button) component.getFellow("runBtn");
		resetBtn = (Button) component.getFellow("resetBtn");
	}

	@NotifyChange
	@Command("selectGenoFromDB")
	public void selectGenoFromDB()
	{

	}

	@NotifyChange("*")
	@Command("selectGenoFromFile")
	public void selectGenoFromFile(@ContextParam(ContextType.BIND_CONTEXT) BindContext bind,
			@ContextParam(ContextType.VIEW)Component view)
	{
		UploadEvent event = (UploadEvent) bind.getTriggerEvent();
		String name = event.getMedia().getName();
		if(!name.endsWith(".csv"))
		{
			showMessage("File must be a text-based csv format!");
			return;
		}
		// read all the content of upload to a new temp file
		File tempFile = FileUtilities.getFileFromUpload(bind, view);
		if(tempFile == null)
			return;
		BindUtils.postNotifyChange(null, null, this, "*");
		userFileManager = new UserFileManager();
		if(uploadedFileFolderPath == null || uploadedFileFolderPath.isEmpty())
			this.buildUploadedFileFolderPath(0);
		String filepath = userFileManager.uploadFileForAnalysis(name, tempFile, 
				uploadedFileFolderPath, "GenotypicData");
		genoFile = new File(filepath);
		setGenoFileName(name);
		genoFileLb.setVisible(true);
		selectGenoFromDBBtn.setVisible(false);
		selectGenoFromFileBtn.setVisible(false);
		resetGenoBtn.setVisible(true);
		isSpecGenoFile = true;
		dataTB.setSelectedTab(genoDataTab);
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("FilePath", filepath);
		BindUtils.postGlobalCommand(null, null, "getGenoFile", args);
	}

	@NotifyChange("*")
	@Command("uploadRefGenoFile")
	public void uploadRefGenoFile(@ContextParam(ContextType.BIND_CONTEXT) BindContext bind,
			@ContextParam(ContextType.VIEW)Component view)
	{
		UploadEvent event = (UploadEvent) bind.getTriggerEvent();
		String name = event.getMedia().getName();
		if(!name.endsWith(".csv"))
		{
			showMessage("File must be a text-based csv format!");
			return;
		}
		// read all the content of upload to a new temp file
		File tempFile = FileUtilities.getFileFromUpload(bind, view);
		if(tempFile == null)
			return;
		BindUtils.postNotifyChange(null, null, this, "*");
		userFileManager = new UserFileManager();
		String filepath = userFileManager.uploadFileForAnalysis(name, tempFile, 
				uploadedFileFolderPath, "RefGenoData");
		genoRefFile = new File(filepath);
		this.genoRefFileName = name;
		isSpecGenoRefFile = true;
		// initiated ref geno tab 
		Tab tab = new Tab();
		tab.setId("refGenoTab");
		tab.setLabel("Reference Genotypic Data");
		this.dataTabs.appendChild(tab);
		Tabpanel tabpanel = new Tabpanel();
		tabpanel.setId("refGenoTP");
		Include inc = new Include();
		inc.setSrc("/user/analysis/introgressionline/refgenotypicdata.zul");
		inc.setDynamicProperty("FilePath", filepath);
		inc.setParent(tabpanel);
		dataTabpanels.appendChild(tabpanel);
		dataTB.setSelectedTab(tab);
		this.refGenoLb.setVisible(true);
		this.uploadRefGenoBtn.setVisible(false);
		this.resetRefGenoBtn.setVisible(true);
	}

	@NotifyChange("*")
	@Command("resetRefGenoFile")
	public void resetRefGenoFile()
	{
		this.genoRefFile = null;
		this.genoRefFileName = "";
		this.refGenoLb.setVisible(false);
		this.uploadRefGenoBtn.setVisible(true);
		this.resetRefGenoBtn.setVisible(false);
		if(dataTabs.getFellow("refGenoTab") != null)
			this.dataTabs.getFellow("refGenoTab").detach();
		if(dataTabpanels.getFellow("refGenoTP") != null)
			this.dataTabpanels.getFellow("refGenoTP").detach();
		dataTB.setSelectedTab(phenoDataTab);
	}
	
	@NotifyChange("*")
	@Command("resetGeno")	
	public void resetGeno()
	{
		setGenoFileName(null);
		genoFileLb.setVisible(false);
		selectGenoFromDBBtn.setVisible(false);
		selectGenoFromFileBtn.setVisible(true);
		resetGenoBtn.setVisible(false);
		isSpecGenoFile = false;
//		BindUtils.postGlobalCommand(null, null, "resetGenoFile", null);
		dataTB.setSelectedTab(phenoDataTab);
		resetGenoTabpanel();
	}
	
	public void resetGenoTabpanel()
	{
		if(!genoDataTP.getChildren().isEmpty())
			genoDataTP.getChildren().get(0).detach();
		Include inc = new Include();
		inc.setSrc("/user/analysis/introgressionline/genotypicdata.zul");
		inc.setParent(genoDataTP);
	}

	@NotifyChange("*")
	@Command("selectPhenoFromDB")
	public void selectPhenoFromDB(
			@ContextParam(ContextType.BIND_CONTEXT) BindContext bindContext,
			@ContextParam(ContextType.VIEW) Component view) 
	{
		if(isSpecPhenoFile)
			resetPheno();
		studyManagerImpl = new StudyManagerImpl();
		lstStudy = studyManagerImpl.getStudiesByUserIdAndStudyType(SecurityUtil.getDbUser()
				.getId(), "IL");
		selectPhenoFromDBBtn.setVisible(false);
		selectPhenoFromFileBtn.setVisible(false);
		studiesCBB.setVisible(true);
		resetPhenoBtn.setVisible(true);
	}
	
	@NotifyChange("*")
	@Command("updateDataSetList")
	public void updateDataSetList()
	{
		dataSetCBB.setSelectedItem(null);
		dataSetCBB.setVisible(false);
		studyDataSetManagerImpl = new StudyDataSetManagerImpl();
		List<StudyDataSet> dataSet = studyDataSetManagerImpl
				.getDataSetsByStudyId(selectedStudy.getId());
		if (!dataSet.isEmpty()) {
			dataSetCBB.setVisible(true);
			setLstDataSet(dataSet);
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
		selectPhenoFromDBBtn.setVisible(false);
		selectPhenoFromFileBtn.setVisible(false);
		phenoFileLb.setVisible(false);
		studiesCBB.setVisible(false);
		resetPhenoBtn.setVisible(true);
		if(browseStudyManagerImpl == null)
			browseStudyManagerImpl = new BrowseStudyManagerImpl();
		List<HashMap<String, String>> toreturn = browseStudyManagerImpl
				.getStudyData(selectedStudy.getId(),
						selectedDataSet.getDatatype(),
						selectedDataSet.getId());
		List<String> columnList = new ArrayList<String>();
		List<String[]> dataList = new ArrayList<String[]>();
		List<StudyDataColumn> columns = new StudyDataColumnManagerImpl()
		.getStudyDataColumnByStudyId(selectedStudy.getId(),
				selectedDataSet.getDatatype(),
				selectedDataSet.getId());
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
		phenoFileName = selectedStudy.getName() + "_" + selectedDataSet.getTitle().replaceAll(" ", "");
		// set BASE_FOLDER to /UPLOADS/UserId_DatasetId/Userid_DatasetId
		buildUploadedFileFolderPath(selectedDataSet.getId());
		String createFile = uploadedFileFolderPath + File.separator + phenoFileName + "(Dataset).csv";
		// write the columnList and dataList value to the uploadedFile
		phenoFile = FileUtilities.createFileFromDatabase(columnList,dataList, createFile);
		if (phenoFile == null)
			return;
		isSpecPhenoFile = true;
		System.out.println("Uploaded File Folder Path : " + uploadedFileFolderPath);
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("FilePath", phenoFile.getAbsolutePath());
		BindUtils.postGlobalCommand(null, null,"getPhenoFile", args);
	}
	
	@NotifyChange("*")
	@Command("selectPhenoFromFile")
	public void selectPhenoFromFile(@ContextParam(ContextType.BIND_CONTEXT) BindContext bind,
			@ContextParam(ContextType.VIEW)Component view)
	{
		UploadEvent event = (UploadEvent) bind.getTriggerEvent();
		String name = event.getMedia().getName();
		if(!name.endsWith(".csv"))
		{
			showMessage("File must be a text-based csv format!");
			return;
		}
		// read all the content of upload to a new temp file
		File tempFile = FileUtilities.getFileFromUpload(bind, view);
		if(tempFile == null)
			return;
		BindUtils.postNotifyChange(null, null, this, "*");
		userFileManager = new UserFileManager();
		String filepath = userFileManager.uploadFileForAnalysis(name, tempFile);
		phenoFile = new File(filepath);
		setPhenoFileName(phenoFile.getName());
		phenoFileLb.setVisible(true);
		selectPhenoFromDBBtn.setVisible(false);
		selectPhenoFromFileBtn.setVisible(false);
		resetPhenoBtn.setVisible(true);
		isSpecPhenoFile = true;
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("FilePath", filepath);
		BindUtils.postGlobalCommand(null, null, "getPhenoFile", args);
	}

	@NotifyChange
	@Command("resetPheno")
	public void resetPheno()
	{
		phenoFile = null;
		setPhenoFileName(null);
		phenoFileLb.setVisible(false);
		studiesCBB.setVisible(false);
		dataSetCBB.setVisible(false);
		selectPhenoFromDBBtn.setVisible(true);
		selectPhenoFromFileBtn.setVisible(true);
		resetPhenoBtn.setVisible(false);
		isSpecPhenoFile = false;
		resetPhenoTabpanel();
//		BindUtils.postGlobalCommand(null, null, "resetPhenoFile", null);
	}

	public void resetPhenoTabpanel()
	{
		if(!phenoDataTP.getChildren().isEmpty())
			phenoDataTP.getChildren().get(0).detach();
		Include inc = new Include();
		inc.setSrc("/user/analysis/introgressionline/phenotypicdata.zul");
		inc.setParent(phenoDataTP);
	}
	
	@NotifyChange
	@Command("selectMapFromDB")
	public void selectMapFromDB()
	{

	}

	@NotifyChange
	@Command("selectMapFromFile")
	public void selectMapFromFile(@ContextParam(ContextType.BIND_CONTEXT) BindContext bind,
			@ContextParam(ContextType.VIEW)Component view)
	{
		UploadEvent event = (UploadEvent) bind.getTriggerEvent();
		String name = event.getMedia().getName();
		if(!name.endsWith(".csv"))
		{
			showMessage("File must be a text-based csv format!");
			return;
		}
		// read all the content of upload to a new temp file
		File tempFile = FileUtilities.getFileFromUpload(bind, view);
		if(tempFile == null)
			return;
		BindUtils.postNotifyChange(null, null, this, "*");
		userFileManager = new UserFileManager();
		String filepath = userFileManager.uploadFileForAnalysis(name, tempFile, 
				uploadedFileFolderPath, "MapData");
		mapFile = new File(filepath);
		setMapFileName(mapFile.getName());
		mapFileLb.setVisible(true);
		selectMapFromDBBtn.setVisible(false);
		selectMapFromFileBtn.setVisible(false);
		resetMapBtn.setVisible(true);
		isSpecMapFile = true;
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("FilePath", filepath);
		BindUtils.postGlobalCommand(null, null, "getMapFile", args);
		dataTB.setSelectedTab(mapDataTab);
	}

	@NotifyChange
	@Command("resetMap")
	public void resetMap()
	{
		mapFile = null;
		setMapFileName(null);
		mapFileLb.setVisible(false);
		selectMapFromDBBtn.setVisible(false);
		selectMapFromFileBtn.setVisible(true);
		resetMapBtn.setVisible(false);
		isSpecMapFile = false;
		resetMapTabpanel();
	}
	
	public void resetMapTabpanel()
	{
		if(!mapDataTP.getChildren().isEmpty())
			mapDataTP.getChildren().get(0).detach();
		Include inc = new Include();
		inc.setSrc("/user/analysis/introgressionline/mapdata.zul");
		inc.setParent(mapDataTP);
	}

	@NotifyChange
	@Command("updateAnalysisMethodDiv")
	public void updateAnalysisMethodDiv(
			@BindingParam("selectedItem") String method)
	{
		if(method.equals("Chisq"))
		{
			if(mmaDiv.isVisible())
				mmaDiv.setVisible(false);
			if(smaDiv.isVisible())
				smaDiv.setVisible(false);
			if(!csDiv.isVisible())
				csDiv.setVisible(true);
			model.setAnalysisType("Chisq");
		} else if(method.equals("Single Marker Analysis"))
		{
			if(mmaDiv.isVisible())
				mmaDiv.setVisible(false);
			if(!smaDiv.isVisible())
				smaDiv.setVisible(true);
			if(csDiv.isVisible())
				csDiv.setVisible(false);
			model.setAnalysisType("SMA");
		} else if(method.equals("Multi Marker Analysis"))
		{
			if(!mmaDiv.isVisible())
				mmaDiv.setVisible(true);
			if(smaDiv.isVisible())
				smaDiv.setVisible(false);
			if(csDiv.isVisible())
				csDiv.setVisible(false);
			model.setAnalysisType("MMA");
		}
	}

	@NotifyChange
	@Command("validateILAnalysisInputs")
	public void validateILAnalysisInputs()
	{	
		String resultFolderPath = null;
		if(analysisMethod.equals("Chisq"))
		{
			if(!validateChisq())
				return;
			model.setIsIncludeHT(includeHtOnCSCB.isChecked());
			model.setSimPvalueOnCS(simPvalueCB.isChecked());
			model.setBootStrapTimesOnCS(bIB.getValue());
			resultFolderPath = AnalysisUtils.createOutputFolder(
					genoFileName.replaceAll(" ", ""), "ilChisq");
			System.out.println("resultfolderpath" + resultFolderPath);
			model.setOutFileName(resultFolderPath + File.separator + "Chisq_Analysis_Outcomes.txt");
			model.setGenoFile(userFileManager.copyUploadedFileToOutputFolder(resultFolderPath, genoFileName, genoFile).getName());
			if(isSpecGenoRefFile)
				model.setRefGenoFile(userFileManager.copyUploadedFileToOutputFolder(resultFolderPath, genoRefFileName, genoRefFile).getName());
		} else if(analysisMethod.equals("Single Marker Analysis"))
		{
			if(!validateSMA())
				return;
			resultFolderPath = AnalysisUtils.createOutputFolder(
					phenoFileName.replaceAll(" ", ""), "ilSingleMarker");
			model.setIsIncludeHT(includeHtOnSMCB.isChecked());
			model.setDigitsOnSM(digitsIB.getValue());
			if(testRG.getSelectedItem()	!= null)
				model.setTestOnSM(testRG.getSelectedItem().getLabel());
			model.setAnalysisType("SMA");
			model.setGenoFile(userFileManager.copyUploadedFileToOutputFolder(resultFolderPath, genoFileName, genoFile).getName());
			model.setPhenoFile(userFileManager.copyUploadedFileToOutputFolder(resultFolderPath, phenoFileName, phenoFile).getName());
			model.setOutFileName(resultFolderPath + File.separator + "Single _Marker_Analysis_Outcomes.txt");
		} else if(analysisMethod.equals("Multi Marker Analysis"))
		{
			if(!validateMMA())
				return;
			model.setAnalysisType("MMA");
			resultFolderPath = AnalysisUtils.createOutputFolder(
					phenoFileName.replaceAll(" ", ""), "ilMultiMarker");
			if(regMethodRG.getSelectedItem() == null)
			{
				showMessage("The regression method of multi-marker could not be null!");
				return;
			}
			model.setRegMethodOnMM((String)regMethodRG.getSelectedItem().getValue());
			if(pvalueRG.getSelectedItem() == null)
			{
				showMessage("The pvalue method of multi-marker could not be null!");
				return;
			}
			model.setPvalMethodOnMM((String)pvalueRG.getSelectedItem().getValue());
			
			if(alpha != null)
				model.setAlphaOnMM(alpha);
			if(bootstrap != null)
				model.setBootstrapOnMM(bootstrap);
			if(nfolds != null)
				model.setNfoldsOnMM(nfolds);
			model.setIsIncludeHT(includeHtOnMMCB.isChecked());
			if(step != null)
				model.setStepOnMM(step);
			if(maxTry != null)
				model.setMaxTryOnMM(maxTry);
			model.setGenoFile(userFileManager.copyUploadedFileToOutputFolder(resultFolderPath, genoFileName, genoFile).getName());
			model.setPhenoFile(userFileManager.copyUploadedFileToOutputFolder(resultFolderPath, phenoFileName, phenoFile).getName());
			model.setOutFileName(resultFolderPath + File.separator + "Multi_Marker_Analysis_Outcomes.txt");
		}
		if(isSpecMapFile)
		{
			if(!validateMapFile())
				return;
			model.setMapFile(userFileManager.copyUploadedFileToOutputFolder(resultFolderPath, mapFileName, mapFile).getName());
		}
		model.setResultFolderPath(resultFolderPath);
		HashMap<String, Object> args = new HashMap<String,Object>();
		args.put("Model", model);
		BindUtils.postGlobalCommand(null, null, "displayILResult", args);
	}

	@NotifyChange("*")
	@GlobalCommand("getGenoFileValidated")
	public void getGenoFileValidated(
			@BindingParam("Geno") String genoCol,
			@BindingParam("DpCode") String dpCode,
			@BindingParam("RpCode") String rpCode,
			@BindingParam("HtCode") String htCode,
			@BindingParam("NaCode") String naCode,
			@BindingParam("BCn") String bcn,
			@BindingParam("Fn") String fn
			)
	{
		System.out.println("Genotypic File Validated!");
		System.out.println("Geno Col " + genoCol);
		System.out.println("DpCode " + dpCode);
		System.out.println("RpCode " + rpCode);
		System.out.println("HtCode " + htCode);
		System.out.println("NaCode " + naCode);
		System.out.println("BCn " + bcn);
		System.out.println("Fn " + fn);
		if(genoCol != null && !genoCol.isEmpty())
			this.genoCol = genoCol;
		if(dpCode != null && !dpCode.isEmpty())
			this.dpCode = dpCode;
		if(rpCode != null && !rpCode.isEmpty())
			this.rpCode = rpCode;
		if(htCode != null && !htCode.isEmpty())
			this.htCode = htCode;
		if(naCode != null && !naCode.isEmpty())
			this.naCode = naCode;
		if(bcn != null)
			this.bcn = Integer.valueOf(bcn);
		if(fn != null)
			this.fn = Integer.valueOf(fn);
	}

	@NotifyChange("*")
	@GlobalCommand("getRefGenoFileValidated")
	public void getRefGenoFileValidated(
			@BindingParam("Geno") String genoRefCol,
			@BindingParam("DpCode") String dpRefCode,
			@BindingParam("RpCode") String rpRefCode,
			@BindingParam("HtCode") String htRefCode,
			@BindingParam("NaCode") String naRefCode,
			@BindingParam("BCn") String bcnRef,
			@BindingParam("Fn") String fnRef
			)
	{
		System.out.println("Genotypic Reference File Validated!");
		System.out.println("Geno Col " + genoRefCol);
		System.out.println("DpCode " + dpRefCode);
		System.out.println("RpCode " + rpRefCode);
		System.out.println("HtCode " + htRefCode);
		System.out.println("NaCode " + naRefCode);
		System.out.println("BCn " + bcnRef);
		System.out.println("Fn " + fnRef);
		if(genoRefCol != null && !genoRefCol.isEmpty())
			this.genoRefCol = genoRefCol;
		if(dpRefCode != null && !dpRefCode.isEmpty())
			this.dpRefCode = dpRefCode;
		if(rpRefCode != null && !rpRefCode.isEmpty())
			this.rpRefCode = rpRefCode;
		if(htRefCode != null && !htRefCode.isEmpty())
			this.htRefCode = htRefCode;
		if(naRefCode != null && !naRefCode.isEmpty())
			this.naRefCode = naRefCode;
		if(bcnRef != null)
			this.bcnRef = Integer.valueOf(bcnRef);
		if(fnRef != null)
			this.fnRef = Integer.valueOf(fnRef);
	}

	@NotifyChange("*")
	@GlobalCommand("getPhenoFileValidated")
	public void getPhenoFileValidated(
			@BindingParam("DataType") String dataType,
			@BindingParam("ExpDesign") String expDesign,
			@BindingParam("EnvAnalysisType") String  envAnalysisType,
			@BindingParam("Genotype") String genotype,
			@BindingParam("Environment") String environment,
			@BindingParam("NaCode") String naCode,
			@BindingParam("Block") String block,
			@BindingParam("Replicate") String replicate,
			@BindingParam("Row") String row,
			@BindingParam("Column") String column,
			@BindingParam("RespVars") List<String> respVars)
	{
		System.out.println("Method: getPhenoFileValidated");
		System.out.println("DataType " + dataType);
		System.out.println("ExpDesign " + expDesign);
		System.out.println("EnvAnalysisType " + envAnalysisType);
		System.out.println("Genotype " + genotype);
		System.out.println("Environment " + environment);
		System.out.println("NaCode " + naCode);
		System.out.println("Block " + block);
		System.out.println("Replicate " + replicate);
		System.out.println("Row " + row);
		System.out.println("Column " + column);
		System.out.println("RespVars " + respVars);
		System.out.println("-----------------------------");
		if(dataType != null && !dataType.isEmpty())
			dataTypeOnPD = dataType;
		if(expDesign != null && !expDesign.isEmpty())
			expDesignOnPD = expDesign;
		if(envAnalysisType != null && !envAnalysisType.isEmpty())
			envAnalysisTypeOnPD = envAnalysisType;
		if(genotype != null && !genotype.isEmpty())
			genotypeOnPD = genotype;
		if(environment != null && !environment.isEmpty())
			environmentOnPD = environment;
		if(naCode != null && !naCode.isEmpty())
			naCodeOnPD = naCode;
		if(block != null && !block.isEmpty())
			blockOnPD = block;
		if(replicate != null && !replicate.isEmpty())
			replicateOnPD = replicate;
		if(row != null && !row.isEmpty())
			rowOnPD = row;
		if(column != null && !column.isEmpty())
			columnOnPD = column;
		if(respVars != null && !respVars.isEmpty())
			this.respVars = respVars;
	}
	
	@NotifyChange("*")
	@GlobalCommand("getMapFileValidated")
	public void getMapFileValidated(
			@BindingParam("Marker") String marker,
			@BindingParam("Chromosome") String chromosome,
			@BindingParam("Position") String  position,
			@BindingParam("Unit") String unit)
	{
		System.out.println("Method: getMapFileValidated");
		System.out.println("Marker " + marker);
		System.out.println("Chromosome " + chromosome);
		System.out.println("Position " + position);
		System.out.println("Unit " + unit);
		System.out.println("-----------------------------");
		if(marker != null && !marker.isEmpty())
			markerOnMD = marker;
		if(chromosome != null && !chromosome.isEmpty())
			chromosomeOnMD = chromosome;
		if(position != null && !position.isEmpty())
			positionOnMD = position;
		if(unit != null && !unit.isEmpty())
			unitOnMD = unit;
	}

	private boolean validateChisq()
	{
		if(isSpecGenoFile)
		{
			if(isSpecGenoRefFile)
			{
				Map<String, Object> args = new HashMap<String, Object>();
				args.put("RefGenoFile", genoRefFileName);
				BindUtils.postGlobalCommand(null, null, "validateRefGenoTap", args);
				if(genoRefFile == null)
				{
					showMessage("Please select reference genotypic file!");
				} else
				{
					model.setRefGenoFile(genoRefFile.getAbsolutePath());
				}
				if(genoRefCol == null || genoRefCol.isEmpty())
				{
					showMessage("The geno coolumn on Reference Genotypic Tab could not be empty!");
					return false;
				} else
				{
					model.setGenoColumnOnRGD(genoRefCol);
				}
				if(dpRefCode == null || dpRefCode.isEmpty())
				{
					showMessage("The dp.code on Reference Genotypic Tab could not be empty!");
					return false;
				} else
				{
					model.setDpCodeOnRGD(dpRefCode);
				}
				if(rpRefCode == null || rpRefCode.isEmpty())
				{
					showMessage("The rp.code on Reference Genotypic Tab could not be empty!");
					return false;
				} else
				{
					model.setRpCodeOnRGD(rpRefCode);
				}
				if(htRefCode == null || htRefCode.isEmpty())
				{
					showMessage("The ht.code on Reference Genotypic Tab could not be empty!");
					return false;
				} else
				{
					model.setHtCodeOnRGD(htRefCode);
				}
				if(naRefCode == null || naRefCode.isEmpty())
				{
					showMessage("The na.code on Reference Genotypic Tab could not be empty!");
					return false;
				} else
				{
					model.setNaCodeOnRGD(naRefCode);
				}
				if(bcnRef == null || String.valueOf(bcnRef).isEmpty())
				{
					showMessage("The BCn on Reference Genoyptic Tab could not be empty!");
					return false;
				} else
				{
					model.setBcnOnRGD(bcnRef);
				}
				if(fnRef == null || String.valueOf(fnRef).isEmpty())
				{
					showMessage("The Fn on Reference Genotypic Tab could not be empty!");
					return false;
				} else
				{
					model.setFnOnRGD(fnRef);
				}

			} 

			BindUtils.postGlobalCommand(null, null, "validateGenoTap", null);

			if(genoFile == null)
			{
				showMessage("Please select genotypic file!");
				return false;
			} else
			{
				model.setGenoFile(genoFile.getAbsolutePath());
			}
			if(!analysisMethod.equals("Chisq"))
			{
				showMessage("The analysis method should be chisq!");
				return false;
			} else
			{
				model.setAnalysisType("Chisq");
			}
			if(genoCol == null || genoCol.isEmpty())
			{
				showMessage("The Geno on Genotypic Tab could not be empty!");
				return false;
			} else
			{
				model.setGenoColumnOnGD(genoCol);
			} 
			if(dpCode == null || dpCode.isEmpty())
			{
				showMessage("The do.code on Genotypic Tab could not be empty!");
				return false;
			} else
			{
				model.setDpCodeOnGD(dpCode);
			}
			if(rpCode == null || rpCode.isEmpty())
			{
				showMessage("The rp.code on Genotypic Tab could not be empty!");
				return false;
			} else
			{
				model.setRpCodeOnGD(rpCode);
			}
			if(htCode == null || htCode.isEmpty())
			{
				showMessage("The ht.code on Genotypic Tab could not be empty!");
				return false;
			} else
			{
				model.setHtCodeOnGD(htCode);
			}
			if(naCode == null || naCode.isEmpty())
			{
				showMessage("The na.code on Genotypic Tab could not be empty!");
				return false;
			} else
			{
				model.setNaCodeOnGD(naCode);
			}
			if(bcn == null || String.valueOf(bcn).isEmpty())
			{
				showMessage("The BCn on Genoyptic Tab could not be empty!");
				return false;
			} else
			{
				model.setBcnOnGD(bcn);
			}
			if(fn == null || String.valueOf(fn).isEmpty())
			{
				showMessage("The Fn on Genotypic Tab could not be empty!");
				return false;
			} else
			{
				model.setFnOnGD(fn);
			}

		} else
		{
			showMessage("Plase select genotypic file!");
			return false;
		}
		return true;
	}

	private boolean validateSMA()
	{
		if(isSpecPhenoFile)
		{
			if(!isSpecGenoFile)
			{
				showMessage("Please select Genotypic file!");
				return false;
			}
			BindUtils.postGlobalCommand(null, null, "validatePhenoFile", null);
			BindUtils.postGlobalCommand(null, null, "validateGenoFile", null);

			if(dataTypeOnPD == null || dataTypeOnPD.isEmpty())
			{
				showMessage("The data type in phenotypic tab could not be null!");
				return false;
			} else
			{
				model.setDataTypeOnPD(dataTypeOnPD);
			}
			if(genotypeOnPD == null || genotypeOnPD.isEmpty())
			{
				showMessage("The genotype in phenotypic tab could not be null!");
				return false;
			} else
			{
				model.setGenoFactOnPD(genotypeOnPD);
			}
			if(naCodeOnPD == null || naCodeOnPD.isEmpty())
			{
				showMessage("The na.code in phenotypic tab could not be null!");
				return false;
			} else
			{
				model.setNaCodeOnPD(naCodeOnPD);
			}
			if(respVars == null || respVars.isEmpty())
			{
				showMessage("The response variable(s) in phenotypic tab could not be null!");
				return false;
			} else
			{
				model.setRespsOnPD(respVars);
			}

			if(dataTypeOnPD.equals("RAW"))
			{
				if(envAnalysisTypeOnPD == null || envAnalysisTypeOnPD.isEmpty())
				{
					showMessage("The analysis type in phenotypic tab could not be null!");
					return false;
				} else
				{
					model.setEnvAnalysisType(envAnalysisTypeOnPD);
				}
				if(envAnalysisTypeOnPD.equals("Multi-Environment Analysis"))
				{
					if(environmentOnPD == null || environmentOnPD.isEmpty())
					{
						showMessage("The environment factor in phenotypic tab could not be null when analysis type is multi-environment!");
					} else
					{
						model.setEnvFactOnPD(environmentOnPD);
					}
				} else
				{
					if(environmentOnPD != null  && !environmentOnPD.isEmpty())
						model.setEnvFactOnPD(environmentOnPD);
				}
				
				if(expDesignOnPD == null || expDesignOnPD.isEmpty())
				{
					showMessage("The experimental design in phenotypic tab could not be null!");
					return false;
				} else
				{
					model.setExptlDesignOnPD(expDesignOnPD);
				}
				if(expDesignOnPD.equals("Randomized Complete Block(RCB)") 
						|| expDesignOnPD.equals("Augmented RCB"))
				{
					//0 or 1
					if(blockOnPD == null || blockOnPD.isEmpty())
					{
						showMessage("The block in phenotyic tab could not be null!");
						return false;
					} else
					{
						model.setBlockFactOnPD(blockOnPD);
					}
				} else if(expDesignOnPD.equals("Augmented Latin Square"))
				{
					//2
					if(rowOnPD == null || rowOnPD.isEmpty())
					{
						showMessage("The row in phenotypic tab could not be null!");
						return false;
					} else{
						model.setRowFactOnPD(rowOnPD);
					}
					if(columnOnPD == null || columnOnPD.isEmpty())
					{
						showMessage("The column in phenotypic tab could not be null!");
						return false;
					} else
					{
						model.setColFactOnPD(columnOnPD);
					}
				} else if(expDesignOnPD.equals("Alpha-Lattice") 
						|| expDesignOnPD.equals("Latinized Alpha-Lattice"))
				{
					//3 or 5
					if(blockOnPD == null || blockOnPD.isEmpty())
					{
						showMessage("The block in phenotypic tab could not be null!");
						return false;
					} else
					{
						model.setBlockFactOnPD(blockOnPD);
					}
					if(replicateOnPD == null || replicateOnPD.isEmpty())
					{
						showMessage("The replicate in phenotypic tab could not be null!");
						return false;
					} else
					{
						model.setRepFactOnPD(replicateOnPD);
					}
				} else if(expDesignOnPD.equals("Row-Column"))
				{
					//4
					if(blockOnPD == null || blockOnPD.isEmpty())
					{
						showMessage("The block in phenotypic tab could not be null!");
						return false;
					} else
					{
						model.setBlockFactOnPD(blockOnPD);
					}
					if(rowOnPD == null || rowOnPD.isEmpty())
					{
						showMessage("The row in phenotypic tab could not be null!");
						return false;
					} else{
						model.setRowFactOnPD(rowOnPD);
					}
					if(columnOnPD == null || columnOnPD.isEmpty())
					{
						showMessage("The column in phenotypic tab could not be null!");
						return false;
					} else
					{
						model.setColFactOnPD(columnOnPD);
					}
				} else if(expDesignOnPD.equals("Latinized Row-Column"))
				{
					//6
					if(replicateOnPD == null || replicateOnPD.isEmpty())
					{
						showMessage("The replicate in phenotypic tab could not be null!");
						return false;
					} else
					{
						model.setRepFactOnPD(replicateOnPD);
					}
					if(rowOnPD == null || rowOnPD.isEmpty())
					{
						showMessage("The row in phenotypic tab could not be null!");
						return false;
					} else{
						model.setRowFactOnPD(rowOnPD);
					}
					if(columnOnPD == null || columnOnPD.isEmpty())
					{
						showMessage("The column in phenotypic tab could not be null!");
						return false;
					} else
					{
						model.setColFactOnPD(columnOnPD);
					}
				}

			}

			// validate genotypic data;
			if(genoFile == null)
			{
				showMessage("Please select genotypic file!");
				return false;
			} else
			{
				model.setGenoFile(genoFile.getAbsolutePath());
			}

			if(genoCol == null || genoCol.isEmpty())
			{
				showMessage("The Geno on Genotypic Tab could not be empty!");
				return false;
			} else
			{
				model.setGenoColumnOnGD(genoCol);
			} 
			if(dpCode == null || dpCode.isEmpty())
			{
				showMessage("The do.code on Genotypic Tab could not be empty!");
				return false;
			} else
			{
				model.setDpCodeOnGD(dpCode);
			}
			if(rpCode == null || rpCode.isEmpty())
			{
				showMessage("The rp.code on Genotypic Tab could not be empty!");
				return false;
			} else
			{
				model.setRpCodeOnGD(rpCode);
			}
			if(htCode == null || htCode.isEmpty())
			{
				showMessage("The ht.code on Genotypic Tab could not be empty!");
				return false;
			} else
			{
				model.setHtCodeOnGD(htCode);
			}
			if(naCode == null || naCode.isEmpty())
			{
				showMessage("The na.code on Genotypic Tab could not be empty!");
				return false;
			} else
			{
				model.setNaCodeOnGD(naCode);
			}
			if(bcn == null || String.valueOf(bcn).isEmpty())
			{
				showMessage("The BCn on Genoyptic Tab could not be empty!");
				return false;
			} else
			{
				model.setBcnOnGD(bcn);
			}
			if(fn == null || String.valueOf(fn).isEmpty())
			{
				showMessage("The Fn on Genotypic Tab could not be empty!");
				return false;
			} else
			{
				model.setFnOnGD(fn);
			}

		} else
		{
			showMessage("Please select Phenotypic file!");
			return false;
		}
		return true;
	}

	private boolean validateMMA()
	{
		return validateSMA();
	}

	private boolean validateMapFile()
	{
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("MapFile", mapFileName);
		BindUtils.postGlobalCommand(null, null, "validateMapTap", args);
		if(markerOnMD == null || markerOnMD.isEmpty())
		{
			showMessage("The marker is not specified on map tab!");
			return false;
		} else
		{
			model.setMarColOnMD(markerOnMD);
		}
		if(chromosomeOnMD == null || chromosomeOnMD.isEmpty())
		{
			showMessage("The chromosome is not specified on map tab!");
			return false;
		} else
		{
			model.setChrColOnMD(chromosomeOnMD);
		}
		if(positionOnMD == null || positionOnMD.isEmpty())
		{
			showMessage("The position is not specified on map tab!");
			return false;
		} else
		{
			model.setPosColOnMD(positionOnMD);
		}
		if(unitOnMD == null || unitOnMD.isEmpty())
		{
			showMessage("The unit is not specified on map tab!");
			return false;
		} else
		{
			model.setUnitOnMD(unitOnMD);
		}
		return true;
	}

	private void buildUploadedFileFolderPath(int dataSetId)
	{
		String temp = null;
		if(dataSetId == 0)
			temp = UserFileManager.buildUserPath(SecurityUtil.getDbUser().getId(),0, "IL_Analysis");
		else
			temp = UserFileManager.buildUserPath(SecurityUtil.getDbUser().getId(),dataSetId, "IL_Analysis");

		File BASE_FOLDER = new File(temp);
		if (!BASE_FOLDER.exists())
			BASE_FOLDER.mkdirs();
		uploadedFileFolderPath = BASE_FOLDER.getAbsolutePath();
	}

	public String getGenoFileName() {
		return genoFileName;
	}

	public void setGenoFileName(String genoFileName) {
		this.genoFileName = genoFileName;
	}

	public String getPhenoFileName() {
		return phenoFileName;
	}

	public void setPhenoFileName(String phenoFileName) {
		this.phenoFileName = phenoFileName;
	}

	public String getMapFileName() {
		return mapFileName;
	}

	public void setMapFileName(String mapFileName) {
		this.mapFileName = mapFileName;
	}
	
	public List<String> getLstAnalysisMethod() {
		if(lstAnalysisMethod == null)
			lstAnalysisMethod = new ArrayList<String>();
		lstAnalysisMethod.clear();
		lstAnalysisMethod.add("Chisq");
		lstAnalysisMethod.add("Single Marker Analysis");
		lstAnalysisMethod.add("Multi Marker Analysis");
		return lstAnalysisMethod;
	}

	public void setLstAnalysisMethod(List<String> lstAnalysisMethod) {
		this.lstAnalysisMethod = lstAnalysisMethod;
	}

	public String getAnalysisMethod() {
		return analysisMethod;
	}

	public void setAnalysisMethod(String analysisMethod) {
		this.analysisMethod = analysisMethod;
	}

	public Double getAlpha() {
		return alpha;
	}

	public void setAlpha(Double alpha) {
		this.alpha = alpha;
	}

	public Integer getBootstrap() {
		return bootstrap;
	}

	public void setBootstrap(Integer bootstrap) {
		this.bootstrap = bootstrap;
	}

	public Integer getNfolds() {
		return nfolds;
	}

	public void setNfolds(Integer nfolds) {
		this.nfolds = nfolds;
	}

	public Double getStep() {
		return step;
	}

	public void setStep(Double step) {
		this.step = step;
	}

	public Integer getMaxTry() {
		return maxTry;
	}

	public void setMaxTry(Integer maxTry) {
		this.maxTry = maxTry;
	}

	public Integer getDigits() {
		return digits;
	}

	public void setDigits(Integer digits) {
		this.digits = digits;
	}

	public Integer getB() {
		return b;
	}

	public void setB(Integer b) {
		this.b = b;
	}

	public String getResultRServe() {
		return resultRServe;
	}

	public void setResultRServe(String resultRServe) {
		this.resultRServe = resultRServe;
	}

	public String getGenoRefFileName() {
		return genoRefFileName;
	}

	public void setGenoRefFileName(String genoRefFileName) {
		this.genoRefFileName = genoRefFileName;
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

	public List<StudyDataSet> getLstDataSet() {
		return lstDataSet;
	}

	public void setLstDataSet(List<StudyDataSet> lstDataSet) {
		this.lstDataSet = lstDataSet;
	}

	public StudyDataSet getSelectedDataSet() {
		return selectedDataSet;
	}

	public void setSelectedDataSet(StudyDataSet selectedDataSet) {
		this.selectedDataSet = selectedDataSet;
	}

	private void showMessage(String message)
	{
		Messagebox.show(message, "Warning", Messagebox.OK, Messagebox.INFORMATION);
	}
}
