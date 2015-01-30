package org.strasa.web.analysis.introgressionline.view.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.filesystem.manager.UserFileManager;
import org.strasa.middleware.model.Study;
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
	private Button uploadRefGenoBtn;
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
	String errorMessage;

	//setting the initiate variables
	@Init
	public void init(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view)
	{
		//do nothing now
		setAnalysisMethod("Multi Marker Analysis");
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
		uploadRefGenoBtn= (Button) component.getFellow("uploadRefGenoBtn");
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

		this.buildUploadedFileFolderPath();
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
		String filepath = userFileManager.uploadFileForAnalysis(name, tempFile, 
				uploadedFileFolderPath, "GenotypicData");
		genoFile = new File(filepath);
		setGenoFileName(name);
		genoFileLb.setVisible(true);
		selectGenoFromDBBtn.setVisible(false);
		selectGenoFromFileBtn.setVisible(false);
		resetGenoBtn.setVisible(true);
		isSpecGenoFile = true;
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("FilePath", filepath);
		BindUtils.postGlobalCommand(null, null, "getGenoFile", args);
	}

	@NotifyChange("*")
	@Command("resetGeno")	
	public void resetGeno()
	{
		setGenoFileName(null);
		genoFileLb.setVisible(false);
		selectGenoFromDBBtn.setVisible(true);
		selectGenoFromFileBtn.setVisible(true);
		resetGenoBtn.setVisible(false);
		isSpecGenoFile = false;
		BindUtils.postGlobalCommand(null, null, "resetGenoFile", null);
	}

	@NotifyChange("*")
	@Command("selectPhenoFromDB")
	public void selectPhenoFromDB()
	{
	}

	@NotifyChange
	@Command("selectPhenoFromFile")
	public void selectPhenoFromFile()
	{


	}

	@NotifyChange
	@Command("resetPheno")
	public void resetPheno()
	{

	}

	@NotifyChange
	@Command("selectMapFromDB")
	public void selectMapFromDB()
	{

	}

	@NotifyChange
	@Command("selectMapFromFile")
	public void selectMapFromFile()
	{

	}

	@NotifyChange
	@Command("resetMap")
	public void resetMap()
	{

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
				csDiv.setVisible(true);
			model.setAnalysisType("MMA");
		}
	}

	@NotifyChange
	@Command("validateILAnalysisInputs")
	public void validateILAnalysisInputs()
	{	

		if(isSpecMapFile)
		{
			validateMapFile();
		}
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
		} else if(analysisMethod.equals("Single Marker Analysis"))
		{
			if(!validateSMA())
			{
				return;
			}
			model.setAnalysisType("SMA");
			resultFolderPath = AnalysisUtils.createOutputFolder(
					phenoFileName.replaceAll(" ", ""), "ilSingleMarker");
			model.setOutFileName(resultFolderPath + File.separator + "Single _Marker_Analysis_Outcomes.txt");
		} else if(analysisMethod.equals("Multi Marker Analysis"))
		{
			if(!validateMMA())
			{
				return;
			}
			model.setAnalysisType("MMA");
			resultFolderPath = AnalysisUtils.createOutputFolder(
					phenoFileName.replaceAll(" ", ""), "ilMultiMarker");
			model.setOutFileName(resultFolderPath + File.separator + "Multi_Marker_Analysis_Outcomes.txt");
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
		this.genoCol = genoCol;
		this.dpCode = dpCode;
		this.rpCode = rpCode;
		this.htCode = htCode;
		this.naCode = naCode;
		this.bcn = Integer.valueOf(bcn);
		this.fn = Integer.valueOf(fn);
	}

	private boolean validateChisq()
	{
		if(isSpecGenoFile)
		{
			if(isSpecGenoRefFile)
			{
				Map<String, Object> args = new HashMap<String, Object>();
				args.put("RefGenoFile", genoRefFileName);
				BindUtils.postGlobalCommand(null, null, "validateGenoTap", args);
			} else
			{
				BindUtils.postGlobalCommand(null, null, "validateGenoTap", null);
			}
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
		if(isSpecGenoFile)
		{
			if(isSpecPhenoFile)
			{
				BindUtils.postGlobalCommand(null, null, "validatePhenoFile", null);
			} else
			{
				showMessage("Please select phenotypic file!");
				return false;
			}
		} else
		{
			showMessage("Please select genotypic file!");
			return false;
		}
		return true;
	}

	private boolean validateMMA()
	{
		if(isSpecGenoFile)
		{
			if(isSpecPhenoFile)
			{
				BindUtils.postGlobalCommand(null, null, "validatePhenoFile", null);
			} else
			{
				showMessage("Please select phenotypic file!");
				return false;
			}
		} else
		{
			showMessage("Please select genotypic file!");
			return false;
		}
		return true;
	}

	private boolean validateMapFile()
	{
		BindUtils.postGlobalCommand(null, null, "validateMapFile", null);
		return false;
	}

	private void buildUploadedFileFolderPath()
	{
		String temp = UserFileManager.buildUserPath(SecurityUtil.getDbUser().getId(),0, "IL_Analysis");
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

	private void showMessage(String message)
	{
		Messagebox.show(message, "Warning", Messagebox.OK, Messagebox.INFORMATION);
	}
}
