package org.strasa.web.analysis.linkagemapping.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.input.ReaderInputStream;
import org.strasa.middleware.filesystem.manager.UserFileManager;
import org.strasa.middleware.manager.EcotypeManagerImpl;
import org.strasa.middleware.manager.ProgramManagerImpl;
import org.strasa.middleware.model.Ecotype;
import org.strasa.middleware.model.Program;
import org.strasa.web.utilities.FileUtilities;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.DependsOn;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;

import au.com.bytecode.opencsv.CSVReader;

public class Index {

	private List<String> phenotypeFormat, typeOfDesignList, dataFormatList, traitType, mapMethod, pModel, pMethod, lociMethod, scanType;
	private List<String> columnList = new ArrayList<String>();
	private List<String[]> dataList = new ArrayList<String[]>();
	private List<Student> students;
	private List<Program> programList; 
	private List<Ecotype> ecotypeList;
	
	BindContext ctx1, ctx2, ctx3;
	Component view3, view1, view2;
	InputStream in1, in2, in3;

	private Student jenina;
	private Ecotype chosenEcotype;

	private String chosenMapping, fileName1, fileName2, fileName3, dataFileName, fileName, comboboxMapping, coboboxmapping2, comboboxmapping3, comboboxmapping4;
	private String value1, value2, value3;

	private boolean mapping1, mapping2, mapping3, mapping4, isNewDataSet, isVariableDataVisible;
	public boolean isUpdateMode = false;
	public boolean isDataReUploaded = false, gridReUploaded = false;

	private int activePage = 0;
	private int pageSize=10;
	private int n;

	private Integer chosenDataFormat;
	private Integer selected;
	private String selectedTraitType;

	private File tempFile,  file1, file2, file3;
	private Div phenobox, genobox, mfgroupbox, datagroupbox, crossgroupbox, mapbox1, mapbox2, inputbox, divDatagrid;
	
	private Radio deleteRadioButton;
	private Radio imputeRadioButton;
	
	private Doublespinner dbDelete;
	private Doublespinner dbSignificance;
	private Doublespinner dbMatchGenotypes;
	private Doublespinner dbThreshold;
	private Doublespinner dbCutOff;
	private Doublespinner dbMainEffects;
	
	private Checkbox cbSetup1;
	private Checkbox cbSetup2;
	
	private Textbox tbMEffect;
	private Textbox tbAll;
	private Textbox tbMaxNumber;

	@AfterCompose
	public void init(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view){
	
		typeOfDesignList = getCrossType();
		jenina = new Student("jenina",1,"asdasd","BSCS","San Vicente", "Binan");
		students = new ArrayList<Student>();
		dataFormatList = dFormatList();
		traitType = tType();
		mapMethod = mMethod();		
		programList = getPList();
		ecotypeList = getEList();
		pModel = pMList();
		pMethod = pMethodList();
		lociMethod = lociMList();
		phenotypeFormat = phenotype();
		scanType = scanTypeList();
		phenobox = (Div) component.getFellow("phenobox");
		datagroupbox = (Div) component.getFellow("datagroupbox");
		crossgroupbox = (Div) component.getFellow("crossgroupbox");
		inputbox = (Div) component.getFellow("inputbox");
		divDatagrid = (Div) component.getFellow("datagrid");
		deleteRadioButton = (Radio) component.getFellow("delete");
		dbDelete= (Doublespinner) component.getFellow("db9");
		imputeRadioButton = (Radio) component.getFellow("impute");
		dbSignificance = (Doublespinner) component.getFellow("db8");
		dbMatchGenotypes = (Doublespinner) component.getFellow("db7");
		dbThreshold = (Doublespinner) component.getFellow("db6");
		dbCutOff = (Doublespinner) component.getFellow("db5");
		cbSetup1= (Checkbox) component.getFellow("setup1");
		cbSetup2 = (Checkbox) component.getFellow("setup2");
		dbMainEffects = (Doublespinner) component.getFellow("db16");
		tbMEffect = (Textbox) component.getFellow("maineffect");
		tbAll = (Textbox) component.getFellow("all");
		tbMaxNumber = (Textbox) component.getFellow("maxnumber");

		
		makeDisable();
	}
	
	private void makeDisable() {
		imputeRadioButton.setDisabled(true);
		dbDelete.setDisabled(true);
		deleteRadioButton.setDisabled(true);
		dbSignificance.setDisabled(true);
		dbMatchGenotypes.setDisabled(true);
		dbThreshold.setDisabled(true);
		dbCutOff.setDisabled(true);
		cbSetup1.setDisabled(true);
		cbSetup2.setDisabled(true);
		dbMainEffects.setDisabled(true);
		tbMEffect.setDisabled(true);
		tbAll.setDisabled(true);
		tbMaxNumber.setDisabled(true);
	}


	
	@Command
	public void save(){
		int i = 0;
		int j = programList.size();
		ProgramManagerImpl pm = new ProgramManagerImpl();
		while(i<j){
			pm.updateProgram(programList.get(i));
			i++;
		}
	}
	
	@Command("getData")
	@NotifyChange("*")
	public void getData(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view){
		
		Checkbox checkifchecked = (Checkbox) component.getFellow("datacheck1");
		boolean datacheck1 = checkifchecked.isChecked();
		Double db9Value = dbDelete.getValue();
		boolean deleteRadioButtonSelected = deleteRadioButton.isSelected();
		boolean imputeRadioButtonSelected = imputeRadioButton.isSelected();
		checkifchecked = (Checkbox) component.getFellow("datacheck2");
		boolean datacheck2 = checkifchecked.isChecked();
		Double db8Value = dbSignificance.getValue();
		checkifchecked = (Checkbox) component.getFellow("datacheck3");
		boolean datacheck3 = checkifchecked.isChecked();
		Double db7Value = dbMatchGenotypes.getValue();
		checkifchecked = (Checkbox) component.getFellow("datacheck4");
		boolean datacheck4 = checkifchecked.isChecked();
		Double db6Value = dbThreshold.getValue();
		checkifchecked = (Checkbox) component.getFellow("datacheck5");
		boolean datacheck5 = checkifchecked.isChecked();
		Double db5Value = dbCutOff.getValue();
		
		if(datacheck1!=true && datacheck2!=true && datacheck4!=true && datacheck3!=true && datacheck5!=true){
			Messagebox.show("Please choose one out of all the options.");
			System.out.println("No checkbox chosen");
			return;
		}
		
		if(datacheck1)
			System.out.println("Manage Missing data: Checked");
		if(db9Value!=null&&dbDelete.isDisabled()!=true)
			System.out.println("Chosen value for Delete: "+ db9Value);
		
		if(deleteRadioButtonSelected!=true && imputeRadioButtonSelected!=true){
			Messagebox.show("Please choose between delete and impute.");
			System.out.println("No Radiobox chosen");		
			return;
		}
	
		if(deleteRadioButtonSelected && deleteRadioButton.isDisabled()!=true)
			System.out.println("Delete: Selected");
		if(imputeRadioButtonSelected && imputeRadioButton.isDisabled()!=true)
			System.out.println("Impute: Selected");
		
		if(datacheck2)
			System.out.println("Test for segregation distortion: Checked");
		if(db8Value!=null && dbSignificance.isDisabled()!=true)
			System.out.println("Chosen value for Level of Significance: "+ db8Value);
		if(datacheck3)
			System.out.println("Compare Genotypes: Checked");
		if(db7Value!=null&& dbMatchGenotypes.isDisabled()!=true)
			System.out.println("Chosen value for Cut-off proportion of matching genotypes: "+ db7Value);
		if(datacheck4)
			System.out.println("Check marker order: Checked");
		if(db6Value!=null&& dbThreshold.isDisabled()!=true)
			System.out.println("Chosen value for Threshold " + db6Value);
		if(datacheck5)
			System.out.println("Identify likely genotyping errors: Checked");
		if(db5Value!=null&&dbCutOff.isDisabled()!=true)
			System.out.println("Chosen value for Cut-off: " + db5Value);

	}
		
	@Command("getData2")
	@NotifyChange("*")
	public void getData2(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view){	
		
		if(selectedTraitType!=null){
			System.out.println("Chosen Method to find Loci: " + selectedTraitType);
		}
		else{			
			Messagebox.show("Please choose a trait type");
			System.out.println("No trait type chosen");		
			return;
		}
		
		Doublespinner db;
		Radio checkIfSelected;
		Checkbox checkifchecked = (Checkbox) component.getFellow("trait1");
		boolean trait1 = checkifchecked.isChecked();	
		checkifchecked = (Checkbox) component.getFellow("trait2");
		boolean trait2 = checkifchecked.isChecked();
		checkifchecked = (Checkbox) component.getFellow("trait3");
		boolean trait3 = checkifchecked.isChecked();
		checkifchecked = (Checkbox) component.getFellow("trait4");
		boolean trait4 = checkifchecked.isChecked();
		
		
		if(trait1!=true && trait2!=true && trait3!=true && trait4!=true){
			Messagebox.show("Please choose at least one trait");
			System.out.println("No trait chosen");	
		}
		
		if(trait1)
			System.out.println("Yield: Checked");
		if(trait2)
			System.out.println("Pitht: Checked");
		if(trait3)
			System.out.println("Awit: Checked");
		if(trait4)
			System.out.println("Awwd: Checked");
		
		
		if(chosenMapping==null){
			Messagebox.show("Please choose mapping method");
			System.out.println("No mapping method chosen");
		}
		
		else if(chosenMapping=="IM"){
			db = (Doublespinner) component.getFellow("db12");
			Double db12Value = db.getValue();
			
			if(db12Value!=null)
				System.out.println("Chosen value for Cut-off: " + db12Value);
			if(comboboxMapping != null)
				System.out.println("Chosen value for Phenotype Model: " + comboboxMapping);
			else{
				Messagebox.show("Please choose value for Phenotype Model");
				System.out.println("No value for phenotype Model chosen");
			}
			if(coboboxmapping2 !=null)
				System.out.println("Chosen value for Method: " + coboboxmapping2);
			else{
				Messagebox.show("Please choose value for Method");
				System.out.println("No value for Method chosen");
			}
		}
		else if(chosenMapping=="CIM"){

			checkifchecked = (Checkbox) component.getFellow("hpresent");
			boolean hpresent = checkifchecked.isChecked();
			
			db = (Doublespinner) component.getFellow("db1");
			Double db1Value = db.getValue();
	
			if(hpresent)
				System.out.println("Heterozygotes Present: Checked");
			if(db1Value!=null)
				System.out.println("Chosen value for Step: " + db1Value);
			
			db = (Doublespinner) component.getFellow("db2");
			Double db2Value = db.getValue();
			if(db2Value!=null)
				System.out.println("Chosen value for Window Size: " + db2Value);			
			
			db = (Doublespinner) component.getFellow("db3");
			Double db3Value = db.getValue();
			if(db3Value!=null)
				System.out.println("Chosen value for Minimum Distance: " + db3Value);
			
			checkIfSelected = (Radio) component.getFellow("Liji");
			boolean Liji = checkIfSelected.isSelected();
		
			checkIfSelected = (Radio) component.getFellow("numerical");
			boolean numerical = checkIfSelected.isSelected();
		
			if(numerical!=true && Liji!=true){
				Messagebox.show("Please choose value for Threshold for p-value");
				System.out.println("No value for Threshold for p-value chosen");	
			}
			
			if(Liji)
				System.out.println("Li and Ji: Selected");
			if(numerical)
				System.out.println("Numerical: Selected");
			
			db = (Doublespinner) component.getFellow("db4");
			Double db4Value = db.getValue();
			if(db4Value!=null)
				System.out.println("Chosen value for Numerical: " + db4Value);
		}
		else if(chosenMapping=="MQM"){
	
			db = (Doublespinner) component.getFellow("db13");
			Double db13Value = db.getValue();
			if(db13Value!=null)
				System.out.println("Chosen value for Step Size: " + db13Value);
			
			db = (Doublespinner) component.getFellow("db14");
			Double db14Value = db.getValue();
			if(db14Value!=null)
				System.out.println("Chosen value for Window Size: " + db14Value);
			
			checkIfSelected = (Radio) component.getFellow("additive");
			boolean additive = checkIfSelected.isSelected();

			checkIfSelected = (Radio) component.getFellow("dominance");
			boolean dominance = checkIfSelected.isSelected();
			
			if(additive!=true && dominance!=true){
				Messagebox.show("Please choose value for Model");
				System.out.println("No value for Model chosen");				
			}
			
			if(additive)
				System.out.println("Additive: Selected");
			if(dominance)
				System.out.println("Dominance: Selected");

			checkIfSelected = (Radio) component.getFellow("ML");
			boolean ML = checkIfSelected.isSelected();
			
			checkIfSelected = (Radio) component.getFellow("REML");
			boolean REML = checkIfSelected.isSelected();
			
			if(ML!=true && REML!=true){
				Messagebox.show("Please choose value for Algorithm");
				System.out.println("No value for Algorithm chosen");				
			}
			
			if(ML)
				System.out.println("ML: Selected");
			if(REML)
				System.out.println("REML: Selected");

		}
		else if(chosenMapping=="Bayesian Mapping"){
			
			db = (Doublespinner) component.getFellow("db15");
			Double db15Value = db.getValue();
			if(db15Value!=null)
				System.out.println("Chosen value for Window Step: " + db15Value);
			
			if(comboboxmapping3!=null){
				System.out.println("Chosen Method to find Loci: " + comboboxmapping3);
			}
			else{
				Messagebox.show("Please choose value for Method to find Loci");
				System.out.println("No value for Method to find Loci chosen");				
			}
			
			if(comboboxmapping4!=null)
				System.out.println("Chosen Type of Scan: " + comboboxmapping4);
			else{
				Messagebox.show("Please choose value for Type of scan");
				System.out.println("No value for Type of scan chosen");				
			}
			}
			
			checkifchecked = (Checkbox) component.getFellow("setup");
			boolean setup = checkifchecked.isChecked();
			if(setup)
				System.out.println("Set-up Interacting QTL Model: Selected");
			
			boolean setup1= cbSetup1.isChecked();
			if(setup1 && cbSetup1.isDisabled()!=true)
				System.out.println("Include Epistasis: Selected");
			
			boolean setup2 = cbSetup2.isChecked();
			if(setup2 && cbSetup2!=null)
				System.out.println("Use Dependent Prior: Selected");
			
			Double db16Value = dbMainEffects.getValue();
			if(db16Value!=null && dbMainEffects.isDisabled()!=true)
				System.out.println("No. of main effects in model: " + db16Value);
			
			String maineffect = tbMEffect.getValue();
			if(maineffect!=null && tbMEffect.isDisabled()!=true){
				System.out.println("Main effect value: " + maineffect);
			}

			String all = tbAll.getValue();
			if(all!=null && tbAll.isDisabled()!=true){
				System.out.println("All value: " + all);
			}
			
			String maxnumber = tbMaxNumber.getValue();
			if(maxnumber!=null && tbMaxNumber.isDisabled()!=true){
				System.out.println("Max Number Value: " + maxnumber);
			}

	}
	
	@Command("missingDataCheck")
	@NotifyChange("*")
	public void missingDataCheck(){
		
		if(imputeRadioButton.isDisabled()==true)
			imputeRadioButton.setDisabled(false);
		else 
			imputeRadioButton.setDisabled(true);

		if(dbDelete.isDisabled()==true)
			dbDelete.setDisabled(false);
		else
			dbDelete.setDisabled(true);
		
		if(deleteRadioButton.isDisabled()==true)
			deleteRadioButton.setDisabled(false);
		else
			deleteRadioButton.setDisabled(true);	
	}
	
	@Command("aggregationCheck")
	@NotifyChange("*")
	public void aggregationCheck(){
		
		if(dbSignificance.isDisabled()==true)
			dbSignificance.setDisabled(false);
		else
			dbSignificance.setDisabled(true);

	}
	
	@Command("genotypeCheck")
	@NotifyChange("*")
	public void genotypeCheck(){
		
		if(dbMatchGenotypes.isDisabled()==true)
			dbMatchGenotypes.setDisabled(false);
		else
			dbMatchGenotypes.setDisabled(true);

	}
	
	@Command("markerCheck")
	@NotifyChange("*")
	public void markerCheck(){
		
		if(dbThreshold.isDisabled()==true)
			dbThreshold.setDisabled(false);
		else
			dbThreshold.setDisabled(true);

	}
	
	@Command("errorCheck")
	@NotifyChange("*")
	public void errorCheck(){
		
		if(dbCutOff.isDisabled()==true)
			dbCutOff.setDisabled(false);
		else
			dbCutOff.setDisabled(true);

	}
	
	@Command("QTLCheck")
	@NotifyChange("*")
	public void QTLCheck(){
		
		if(cbSetup1.isDisabled()==true)
			cbSetup1.setDisabled(false);
		else
			cbSetup1.setDisabled(true);
		
		if(cbSetup2.isDisabled()==true)
			cbSetup2.setDisabled(false);
		else
			cbSetup2.setDisabled(true);
		
		if(dbMainEffects.isDisabled()==true)
			dbMainEffects.setDisabled(false);
		else
			dbMainEffects.setDisabled(true);
		
		if(tbMEffect.isDisabled()==true)
			tbMEffect.setDisabled(false);
		
		else
			tbMEffect.setDisabled(true);
		
		if(tbAll.isDisabled()==true)
			tbAll.setDisabled(false);
		else
			tbAll.setDisabled(true);
		
		if(tbMaxNumber.isDisabled()==true)
			tbMaxNumber.setDisabled(false);
		else
			tbMaxNumber.setDisabled(true);
	}
	
	@Command("visibility")
	@NotifyChange("*")
	public void visibility(@BindingParam("selected") Integer selected){
	
		if(selected == 0){
			createPhenoBox();
			detachDiv(datagroupbox);
			detachDiv(crossgroupbox);
			detachDiv(inputbox);
			this.selected=0;
			makeNull();
		}

		else if(selected == 1){
			detachDiv(phenobox);
			createDataGroupBox();
			detachDiv(crossgroupbox);
			detachDiv(inputbox);
			this.selected=1;
			makeNull();
		}
		
		else if(selected == 2){
			detachDiv(phenobox);
			detachDiv(datagroupbox);
			createCrossGroupBox();
			detachDiv(inputbox);
			this.selected=2;
			makeNull();
		}

		
		else if(selected == 3){
			detachDiv(phenobox);
			detachDiv(datagroupbox);
			detachDiv(crossgroupbox);
			createInputBox();
			this.selected=3;
			makeNull();
		}
		
		else{
			detachDiv(phenobox);
			detachDiv(datagroupbox);
			detachDiv(crossgroupbox);
			detachDiv(inputbox);
			this.selected=4;
			makeNull();
		}
	}
	
	private void makeNull(){
		value1 = null;
		value2 = null;
		value3 = null;
		file1 = null;
		file2 = null;
		file3 = null;
		tempFile = null;
		fileName1 = null;
		fileName2 = null;
		fileName3 = null;
		
	}
	
	public void createPhenoBox(){
		
		if (!phenobox.getChildren().isEmpty())
			phenobox.getFirstChild().detach();
		
		Include phenoboxData = new Include();
		phenoboxData.setSrc("default.zul");
		phenoboxData.setParent(phenobox);
	
	}
		
	private void createDataGroupBox(){
		if (!datagroupbox.getChildren().isEmpty())
			datagroupbox.getFirstChild().detach();
		
		Include dataGroupData = new Include();
		dataGroupData.setSrc("mapmaker.zul");
		dataGroupData.setParent(datagroupbox);
		
	}
	
	private void createCrossGroupBox(){
		if (!crossgroupbox.getChildren().isEmpty())
			crossgroupbox.getFirstChild().detach();
		
		Include crossGroupData = new Include();
		crossGroupData.setSrc("QTL.zul");
		crossGroupData.setParent(crossgroupbox);
	}
		
	private void createInputBox(){
		if (!inputbox.getChildren().isEmpty())
			inputbox.getFirstChild().detach();
		
		Include inputGroupData = new Include();
		inputGroupData.setSrc("inputbox.zul");
		inputGroupData.setParent(inputbox);
	}
	
	public void detachDiv(Div div){
		if (!div.getChildren().isEmpty()){
			div.getFirstChild().detach();
		}
		
	}
	
	@Command
	@DependsOn("chosenMapping")
	@NotifyChange({"mapping1", "mapping2", "mapping3", "mapping4"})
	public void mappingVisibility(){
		if(chosenMapping=="IM"){
			mapping1 = true; mapping2=false;
			mapping3=false; mapping4=false;
		}
		else if(chosenMapping=="CIM"){
			mapping1 = false; mapping2=true;
			mapping3=false; mapping4=false;	
		}
		else if(chosenMapping=="MQM"){
			mapping1 = false; mapping2=false;
			mapping3=true; mapping4=false;
		}
		else if(chosenMapping=="Bayesian Mapping"){
			mapping1 = false; mapping2=false;
			mapping3=false; mapping4=true;
		}
	}
		
	@NotifyChange("*")
	@Command("chooseFile1value1")
	public void chooseFileValue1(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx,
			@ContextParam(ContextType.VIEW) Component view) 
			throws Exception{
		
		UploadEvent event = (UploadEvent) ctx.getTriggerEvent();
		this.ctx1=ctx;
		this.view1=view;
		
		tempFile = new File(event.getMedia().getName());
		setFileName1(event.getMedia().getName());
		System.out.println(tempFile.getAbsolutePath());	
		
		value1 = tempFile.getAbsolutePath();
		if (file1 == null)
			try {
				file1 = File.createTempFile(fileName1, ".tmp");
				System.out.println(file1.getAbsolutePath());
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		if (!fileName1.endsWith(".csv")) {
			Messagebox.show("Error: File must be a text-based csv format",
				"Upload Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}
			
		in1 = event.getMedia().isBinary() ? event.getMedia().getStreamData() : new ReaderInputStream(event.getMedia().getReaderData());
	}
	
	
	@NotifyChange("*")
	@Command("chooseFile1value2")
	public void chooseFileValue2(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx,
			@ContextParam(ContextType.VIEW) Component view) 
			throws Exception{
		
		UploadEvent event = (UploadEvent) ctx.getTriggerEvent();
		this.ctx2=ctx;
		this.view2=view;		
		tempFile = new File(event.getMedia().getName());
		setFileName2(event.getMedia().getName());
		System.out.println(tempFile.getAbsolutePath());	
		
		value2 = tempFile.getAbsolutePath();
		if (file2 == null)
			try {
				file2 = File.createTempFile(fileName2, ".tmp");
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		if (!fileName2.endsWith(".csv")) {
			Messagebox.show("Error: File must be a text-based csv format",
				"Upload Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}
			
		in2 = event.getMedia().isBinary() ? event.getMedia().getStreamData() : new ReaderInputStream(event.getMedia().getReaderData());
	}
	
	@NotifyChange("*")
	@Command("chooseFile1value3")
	public void chooseFileValue3(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx,
			@ContextParam(ContextType.VIEW) Component view) 
			throws Exception{
		
		UploadEvent event = (UploadEvent) ctx.getTriggerEvent();
		
		this.ctx3=ctx;
		this.view3=view;
		
		tempFile = new File(event.getMedia().getName());
		setFileName3(event.getMedia().getName());
		System.out.println(tempFile.getAbsolutePath());	
		
		value3 = tempFile.getAbsolutePath();
		if (file3 == null)
			try {
				file3 = File.createTempFile(fileName3, ".tmp");
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		if (!fileName3.endsWith(".csv")) {
			Messagebox.show("Error: File must be a text-based csv format",
				"Upload Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}
			
		in3 = event.getMedia().isBinary() ? event.getMedia().getStreamData() : new ReaderInputStream(event.getMedia().getReaderData());
	}

	
	@Command
	@DependsOn("selected")
	public void uploadFiles(){
		if(selected == 0){
			uploadFile1();
			uploadFile2();
			uploadFile3();
			makeNull();
		}
		else if(selected == 1 || selected == 2){
			uploadFile1();
			uploadFile2();
			makeNull();
		}
		else if(selected == 3){
			uploadFile1();
			makeNull();
		}
		else{
			System.out.println("N/A");
		}
		
	}
	
	
	private void uploadFile1(){
		
		System.out.println(file1.getAbsolutePath());
		FileUtilities.uploadFile(file1.getAbsolutePath(), in1);
		BindUtils.postNotifyChange(null, null, this, "*");

		File uploadedFile = FileUtilities.getFileFromUpload(ctx1, view1);

		UserFileManager userFileManager = new UserFileManager();
		String filePath = userFileManager.uploadFileForAnalysis(fileName1, uploadedFile);
		
		if (file1 == null)
			return;
		
		isVariableDataVisible = true;
		setDataFileName(fileName1);

		isVariableDataVisible = true;
		dataFileName = fileName1;
		refreshCsv();
		if (this.isUpdateMode) setNewDataSet(true);
				
	}
	
	private void uploadFile2(){
		
		System.out.println(file2.getAbsolutePath());
		FileUtilities.uploadFile(file2.getAbsolutePath(), in2);
		BindUtils.postNotifyChange(null, null, this, "*");

		File uploadedFile = FileUtilities.getFileFromUpload(ctx2, view2);

		UserFileManager userFileManager = new UserFileManager();
		String filePath = userFileManager.uploadFileForAnalysis(fileName2, uploadedFile);		
	}
	
	private void uploadFile3(){
		
		System.out.println(file3.getAbsolutePath());
		FileUtilities.uploadFile(file3.getAbsolutePath(), in3);
		BindUtils.postNotifyChange(null, null, this, "*");

		File uploadedFile = FileUtilities.getFileFromUpload(ctx3, view3);

		UserFileManager userFileManager = new UserFileManager();
		String filePath = userFileManager.uploadFileForAnalysis(fileName3, uploadedFile);
	}
	
	@Command("refreshCsv")
	public void refreshCsv() {
		setActivePage(0);
		CSVReader reader;
		reloadCsvGrid();

		try {
			reader = new CSVReader(new FileReader(file1.getAbsolutePath()));
			List<String[]> rawData = reader.readAll();
			columnList.clear();
			dataList.clear();
			columnList = new ArrayList<String>(Arrays.asList(rawData.get(0)));
			rawData.remove(0);
			dataList = new ArrayList<String[]>(rawData);
			System.out.println(Arrays.toString(dataList.get(0)));
			if (!this.isDataReUploaded)
				System.out.println("gbUploadData.invalidate()");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<ArrayList<String>> getCsvData() {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		if (dataList.isEmpty())
			return result;
		for (int i = activePage * pageSize; i < activePage * pageSize + pageSize && i < dataList.size(); i++) {
			ArrayList<String> row = new ArrayList<String>();
			row.addAll(Arrays.asList(dataList.get(i)));
			result.add(row);
			row.add(0, "  ");
			System.out.println(Arrays.toString(dataList.get(i)) + "ROW: " + row.get(0));
		}
		return result;
	}
	
	public void reloadCsvGrid() {
		if (gridReUploaded) {
			gridReUploaded = false;
			return;
		}
		if (!divDatagrid.getChildren().isEmpty())
			divDatagrid.getFirstChild().detach();
		Include incCSVData = new Include();
		incCSVData.setSrc("/user/updatestudy/csvdata.zul");
		incCSVData.setParent(divDatagrid);
		gridReUploaded = true;
	}

	
	public static List<String> mMethod(){
		return Arrays.asList(new String[]{"IM", "CIM", "MQM", "Bayesian Mapping", "Two-d Mapping", "QTLRel", "MAGIC"});
	}
	
	public static List<String> getCrossType() {
		return Arrays.asList(new String[]{"F2", "BC", "RIL(SIB)", "RIL(SELF)", "[Other]", "BC", "DH", "F", "RIL(self)", "Ril(sib)", "[Other]"});
	}
	
	public static List<String> scanTypeList(){
		return Arrays.asList(new String[]{"2logBF", "cell mean", "count", "deviance", "detection", "estimate", "heritability", "log10", "log10 (posterior)", "LPD", "LR", "nqtl", "posterior", "variance"});
	}
	
	public static List<String> dFormatList(){
		return Arrays.asList(new String[]{"default", "Map Maker", "QTL Cartographer", "Map Manager"});
	}
	
	public static List<String> phenotype(){
		return Arrays.asList(new String[]{"Space-separated (.txt) file", "Comma-separated (.txt) file", "Tab-separated (.txt) file", "Semi-colon-separated (.txt) file"});
	}
	
	
	public static List<String> lociMList(){
		return Arrays.asList(new String[]{"Mean", "Mode", "Scan"});
	}
	
	public static List<String> pMList(){
		return Arrays.asList(new String[]{"Normal", "Binary", "Two-Part", "Non-parametric"});
	}
	
	public static List<String> pMethodList(){
		return Arrays.asList(new String[]{"Maximum Likehood via EM", "Haley-Knott Regression", "Extended Haley-Knott Method", "Imputation"});
	}
	
	public static List<String> genotype(){
		return Arrays.asList(new String[]{"Comma-separated (.csv) file", "Tab Delimited text (.txt) file"});
	}
	
	public static List<String> tType(){
		return Arrays.asList(new String[]{"Continuous", "Binary", "Ordinal"});
	}
	
	public static List<Program> getPList(){
		ProgramManagerImpl pm = new ProgramManagerImpl();
		return pm.getAllProgram();
	}
	
	public static List<Ecotype> getEList(){
		EcotypeManagerImpl em = new EcotypeManagerImpl();
		return em.getAllEcotypes();
	}
	
	public List<Student> getStudents() {
		Student jenina = new Student("jenina",1,"2011-46686","BSCS","San Vicente", "Binan");
		Student khem = new Student("khem",2,"2011-00000","BSCS","College","Los Banos");
		students.add(jenina);
		students.add(khem);
		return students;
	}
	
	public List<String[]> getDataList() {
		System.out.println("DaALIST GEt");
		reloadCsvGrid();
		if (true)
			return dataList;
			
		ArrayList<String[]> pageData = new ArrayList<String[]>();
		for (int i = activePage * pageSize; i < activePage * pageSize + pageSize; i++) {
			pageData.add(dataList.get(i));
			System.out.println(Arrays.toString(dataList.get(i)));
		}

		return pageData;
	}
	
	public List<String> getTypeOfDesignList() {
		return typeOfDesignList;
	}
		
	public boolean checkVisibility(int num){
		return true;
	}

	public void setTypeOfDesignList(List<String> typeOfDesignList) {
		this.typeOfDesignList = typeOfDesignList;
	}
	
	public List<String> getDataFormatList() {
		return dataFormatList;
	}

	public void setDataFormatList(List<String> dataFormatList) {
		this.dataFormatList = dataFormatList;
	}

	public List<String> getTraitType() {
		return traitType;
	}

	public void setTraitType(List<String> traitType) {
		this.traitType = traitType;
	}
	

	public List<String> getMapMethod() {
		return mapMethod;
	}

	public void setMapMethod(List<String> mapMethod) {
		this.mapMethod = mapMethod;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	} 

	public Student getJenina() {
		return jenina;
	}

	public void setJenina(Student jenina) {
		this.jenina = jenina;
	}

	public List<Program> getProgramList() {
		return programList;
	}

	public void setProgramList(List<Program> programList) {
		this.programList = programList;
	}
	
	public List<Ecotype> getEcotypeList() {
		return ecotypeList;
	}

	public void setEcotypeList(List<Ecotype> ecotypeList) {
		this.ecotypeList = ecotypeList;
	}

	public Ecotype getChosenEcotype() {
		return chosenEcotype;
	}

	public void setChosenEcotype(Ecotype chosenEcotype) {
		this.chosenEcotype = chosenEcotype;
	}


	public List<String> getpModel() {
		return pModel;
	}

	public void setpModel(List<String> pModel) {
		this.pModel = pModel;
	}

	public List<String> getpMethod() {
		return pMethod;
	}

	public void setpMethod(List<String> pMethod) {
		this.pMethod = pMethod;
	}

	public List<String> getLociMethod() {
		return lociMethod;
	}

	public void setLociMethod(List<String> lociMethod) {
		this.lociMethod = lociMethod;
	}

	public List<String> getScanType() {
		return scanType;
	}
	

	public void setScanType(List<String> scanType) {
		this.scanType = scanType;
	}

	public String getChosenMapping() {
		return chosenMapping;
	}

	public void setChosenMapping(String chosenMapping) {
		this.chosenMapping = chosenMapping;
	}

	public boolean isMapping1() {
		return mapping1;
	}

	public void setMapping1(boolean mapping1) {
		this.mapping1 = mapping1;
	}

	public boolean isMapping2() {
		return mapping2;
	}

	public void setMapping2(boolean mapping2) {
		this.mapping2 = mapping2;
	}

	public boolean isMapping3() {
		return mapping3;
	}

	public void setMapping3(boolean mapping3) {
		this.mapping3 = mapping3;
	}

	public boolean isMapping4() {
		return mapping4;
	}

	public void setMapping4(boolean mapping4) {
		this.mapping4 = mapping4;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isVariableDataVisible() {
		return isVariableDataVisible;
	}

	public void setVariableDataVisible(boolean isVariableDataVisible) {
		this.isVariableDataVisible = isVariableDataVisible;
	}

	public String getDataFileName() {
		return dataFileName;
	}

	public void setDataFileName(String dataFileName) {
		this.dataFileName = dataFileName;
	}

	@NotifyChange("*")
	public int getActivePage() {

		return activePage;
	}

	@NotifyChange("*")
	public void setActivePage(int activePage) {
		System.out.println("pageSize");
		reloadCsvGrid();
		this.activePage = activePage;
	}


	public boolean isNewDataSet() {
		return isNewDataSet;
	}

	public void setNewDataSet(boolean isNewDataSet) {
		this.isNewDataSet = isNewDataSet;
	}

	public Integer getChosenDataFormat() {
		return chosenDataFormat;
	}

	public void setChosenDataFormat(Integer chosenDataFormat) {
		this.chosenDataFormat = chosenDataFormat;
	}

	public Div getPhenobox() {
		return phenobox;
	}

	public void setPhenobox(Div phenobox) {
		this.phenobox = phenobox;
	}
	
	public Div getGenobox() {
		return genobox;
	}

	public void setGenobox(Div genobox) {
		this.genobox = genobox;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}


	public Div getMfgroupbox() {
		return mfgroupbox;
	}

	public void setMfgroupbox(Div mfgroupbox) {
		this.mfgroupbox = mfgroupbox;
	}

	public Div getDatagroupbox() {
		return datagroupbox;
	}

	public void setDatagroupbox(Div datagroupbox) {
		this.datagroupbox = datagroupbox;
	}

	public Div getCrossgroupbox() {
		return crossgroupbox;
	}

	public void setCrossgroupbox(Div crossgroupbox) {
		this.crossgroupbox = crossgroupbox;
	}
	
	public List<String> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}
	
	public int getTotalSize() {
		return dataList.size();
	}

	public Div getMapbox1() {
		return mapbox1;
	}

	public void setMapbox1(Div mapbox1) {
		this.mapbox1 = mapbox1;
	}

	public Div getMapbox2() {
		return mapbox2;
	}

	public void setMapbox2(Div mapbox2) {
		this.mapbox2 = mapbox2;
	}

	public Div getInputbox() {
		return inputbox;
	}

	public void setInputbox(Div inputbox) {
		this.inputbox = inputbox;
	}

	public List<String> getPhenotypeFormat() {
		return phenotypeFormat;
	}

	public void setPhenotypeFormat(List<String> phenotypeFormat) {
		this.phenotypeFormat = phenotypeFormat;
	}

	public Integer getSelected() {
		return selected;
	}

	public void setSelected(Integer selected) {
		this.selected = selected;
	}


	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}


	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}


	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}


	public File getFile1() {
		return file1;
	}

	public void setFile1(File file1) {
		this.file1 = file1;
	}


	public File getFile2() {
		return file2;
	}

	public void setFile2(File file2) {
		this.file2 = file2;
	}


	public File getFile3() {
		return file3;
	}

	public void setFile3(File file3) {
		this.file3 = file3;
	}


	public String getFileName1() {
		return fileName1;
	}

	public void setFileName1(String fileName1) {
		this.fileName1 = fileName1;
	}


	public String getFileName2() {
		return fileName2;
	}

	public void setFileName2(String fileName2) {
		this.fileName2 = fileName2;
	}


	public String getFileName3() {
		return fileName3;
	}

	public void setFileName3(String fileName3) {
		this.fileName3 = fileName3;
	}


	public int getPageSize() {
		return pageSize;
	}
	
	@NotifyChange("*")
	public void setPageSize(int pageSize) {
		if(pageSize>=0){
			pageSize=10;
		}
		this.pageSize = pageSize;
	}



	public String getSelectedTraitType() {
		return selectedTraitType;
	}

	public void setSelectedTraitType(String selectedTraitType) {
		this.selectedTraitType = selectedTraitType;
	}



	public Radio getDeleteRadioButton() {
		return deleteRadioButton;
	}

	public void setDeleteRadioButton(Radio deleteRadioButton) {
		this.deleteRadioButton = deleteRadioButton;
	}

	public String getComboboxMapping() {
		return comboboxMapping;
	}

	public void setComboboxMapping(String comboboxMapping) {
		this.comboboxMapping = comboboxMapping;
	}



	public String getCoboboxmapping2() {
		return coboboxmapping2;
	}

	public void setCoboboxmapping2(String coboboxmapping2) {
		this.coboboxmapping2 = coboboxmapping2;
	}



	public String getComboboxmapping3() {
		return comboboxmapping3;
	}

	public void setComboboxmapping3(String comboboxmapping3) {
		this.comboboxmapping3 = comboboxmapping3;
	}



	public String getComboboxmapping4() {
		return comboboxmapping4;
	}

	public void setComboboxmapping4(String comboboxmapping4) {
		this.comboboxmapping4 = comboboxmapping4;
	}


	public Doublespinner getDbDelete() {
		return dbDelete;
	}

	public void setDbDelete(Doublespinner dbDelete) {
		this.dbDelete = dbDelete;
	}


	public Radio getImputeRadioButton() {
		return imputeRadioButton;
	}

	public void setImputeRadioButton(Radio imputeRadioButton) {
		this.imputeRadioButton = imputeRadioButton;
	}


	public Doublespinner getDbSignificance() {
		return dbSignificance;
	}

	public void setDbSignificance(Doublespinner dbSignificance) {
		this.dbSignificance = dbSignificance;
	}


	public Doublespinner getDbMatchGenotypes() {
		return dbMatchGenotypes;
	}

	public void setDbMatchGenotypes(Doublespinner dbMatchGenotypes) {
		this.dbMatchGenotypes = dbMatchGenotypes;
	}


	public Doublespinner getDbThreshold() {
		return dbThreshold;
	}

	public void setDbThreshold(Doublespinner dbThreshold) {
		this.dbThreshold = dbThreshold;
	}


	public Doublespinner getDbCutOff() {
		return dbCutOff;
	}

	public void setDbCutOff(Doublespinner dbCutOff) {
		this.dbCutOff = dbCutOff;
	}


	public Checkbox getCbSetup1() {
		return cbSetup1;
	}

	public void setCbSetup1(Checkbox cbSetup1) {
		this.cbSetup1 = cbSetup1;
	}


	public Checkbox getCbSetup2() {
		return cbSetup2;
	}

	public void setCbSetup2(Checkbox cbSetup2) {
		this.cbSetup2 = cbSetup2;
	}


	public Doublespinner getDbMainEffects() {
		return dbMainEffects;
	}

	public void setDbMainEffects(Doublespinner dbMainEffects) {
		this.dbMainEffects = dbMainEffects;
	}


	public Textbox getTbMEffect() {
		return tbMEffect;
	}

	public void setTbMEffect(Textbox tbMEffect) {
		this.tbMEffect = tbMEffect;
	}


	public Textbox getTbAll() {
		return tbAll;
	}

	public void setTbAll(Textbox tbAll) {
		this.tbAll = tbAll;
	}


	public Textbox getTbMaxNumber() {
		return tbMaxNumber;
	}

	public void setTbMaxNumber(Textbox tbMaxNumber) {
		this.tbMaxNumber = tbMaxNumber;
	}


	public class Student{

		private String name;
		private String studentno;
		private String course;
		private Integer num;
		private Address add = new Address();
		
		
		public Student(String name, Integer num, String studentno, String course, String by, String cy){
			this.name = name;
			this.num = num;
			this.studentno = studentno;
			setStudentno(studentno);
			this.course = course;
			add.setBarangay(by);
			add.setCity(cy);
		}
		
		public Integer getNum() {
			return num;
		}
		public void setNum(Integer num) {
			this.num = num;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getStudentno() {
			return studentno;
		}
		public void setStudentno(String studentno) {
			this.studentno = studentno;
		}
		public String getCourse() {
			return course;
		}
		public void setCourse(String course) {
			this.course = course;
		}
		public Address getAdd() {
			return add;
		}
		public void setAdd(Address add) {
			this.add = add;
		}
	}
	
	public class Address{
				
		private String barangay;
		private String city;
		
		public String getCompleteAddress(){
			return barangay+","+city;	
		}
		
		public String getBarangay() {
			return barangay;
		}
		public void setBarangay(String barangay) {
			this.barangay = barangay;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}

	}
	
}
