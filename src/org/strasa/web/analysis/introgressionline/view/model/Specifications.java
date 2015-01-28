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
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
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
	private Intbox boostrapIB;
	private Intbox nfoldsIB;
	private Checkbox inCludeHtOnMMCB;
	private Decimalbox stepDB;
	private Intbox maxTryIB;
	private Div smaDiv;
	private Checkbox inCludeHtOnSMCB;
	private Intbox digitsIB;
	private Radiogroup testRG;
	private Radio ftestRadio;
	private Radio ctestRadio;
	private Div csDiv;
	private Checkbox includeHtOnCSCB;
	private Checkbox simPvalueCB;
	private Intbox bIB;
	private Button uploadRefGenoMatrix;
	private Tabbox dataTB;
	private Tabs dataTabs;
	private Tab genoDataTab;
	private Tab phenoDataTab;
	private Tab mapDataTab;
	private Tabpanels dataTabpanels;
	private Tabpanel genoDataTP;
	private Tabpanel phenoDataTP;
	private Tabpanel mapDataTP;

	String genoFileName;
	String phenoFileName;
	String mapFileName;
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
	//setting the initiate variables
	@Init
	public void init(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view)
	{
		//do nothing now
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
		this.analysisMethodCBB = (Combobox) component.getFellow("analysisMethodCBB");
		this.analysisMethodDiv = (Div) component.getFellow("analysisMethodDiv");
	}
	
	@NotifyChange
	@Command("selectGenoFromDB")
	public void selectGenoFromDB()
	{
		
	}
	
	@NotifyChange
	@Command("selectGenoFromFile")
	public void selectGenoFromFile()
	{
		
	}
	
	@NotifyChange
	@Command("resetGeno")
	public void resetGeno()
	{
		
	}
	
	@NotifyChange
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
	public void updateAnalysisMethodDiv()
	{
		
	}
	
	@NotifyChange
	@Command("validateILAnalysisInputs")
	public void validateILAnalysisInputs()
	{
		
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
}
