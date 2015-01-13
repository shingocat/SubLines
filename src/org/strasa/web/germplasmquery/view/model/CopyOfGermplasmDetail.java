package org.strasa.web.germplasmquery.view.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.strasa.middleware.manager.BrowseGermplasmManagerImpl;
import org.strasa.middleware.manager.GermplasmCharacteristicMananagerImpl;
import org.strasa.middleware.manager.GermplasmManagerImpl;
import org.strasa.middleware.manager.GermplasmTypeManagerImpl;
import org.strasa.middleware.manager.KeyCharacteristicManagerImpl;
import org.strasa.middleware.model.Germplasm;
import org.strasa.middleware.model.GermplasmCharacteristics;
import org.strasa.middleware.model.GermplasmType;
import org.strasa.middleware.model.KeyAbiotic;
import org.strasa.middleware.model.KeyBiotic;
import org.strasa.middleware.model.KeyGrainQuality;
import org.strasa.middleware.model.KeyMajorGenes;
import org.strasa.web.browsestudy.view.model.StudySearchResultModel;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;



public class CopyOfGermplasmDetail {

	private String nameSearch;
	private String searchKey;
	private List<GermplasmType> germplasmType;
	private List<String> keyCharacteristicOption;
	private int germplasmTypeId=0;
	private String keyCharactericticValue;
	private List<Germplasm> germplasmList;
	private Germplasm germplasm;
	private String abioticCharacteristics;
	private String bioticCharacteristics;
	private String grainQualityCharacteristics;
	private String majorGenesCharacteristics;
	private List<StudySearchResultModel> studyTested;
	private HashMap<String,Integer> germplasmTypeKey = new HashMap<String,Integer>();
	private ArrayList<String> keyCharValueList= new ArrayList<String>();
	private final static String ABIOTIC="Abiotic";
	private final static String BIOTIC="Biotic";
	private final static String GRAIN_QUALITY="Grain Quality";
	private final static String MAJOR_GENES="Major Genes";
	private String searchResultLabel="Search Result";
	private List<CharacteristicModel> keyAbioticList;
	private List<CharacteristicModel> keyBioticList;
	private List<CharacteristicModel> keyGrainQualityList;
	private List<CharacteristicModel> keyMajorGenesList;
	private List<String> listKeyCharFilter= new ArrayList<String>();

	private String parentSource;

	@Init
	public void init(@ExecutionArgParam("gname")String germplasmName, @ExecutionArgParam("parentSource")String source){
		setParentSource(source);
		System.out.println("parentSource "+source);
		System.out.println("gname"+ germplasmName);
		BrowseGermplasmManagerImpl mgr= new BrowseGermplasmManagerImpl();
		setGermplasmList( mgr.getGermplasmByNameLike(germplasmName));
		
		GermplasmManagerImpl gMgr = new GermplasmManagerImpl();		
		Germplasm g=gMgr.getGermplasmById(getGermplasmList().get(0).getId());
		
		setGermplasm(g);
		setAbioticCharacteristics(getGermplasmCharacteristics(ABIOTIC,germplasmName));
		setBioticCharacteristics(getGermplasmCharacteristics(BIOTIC,germplasmName));
		setGrainQualityCharacteristics(getGermplasmCharacteristics(GRAIN_QUALITY,germplasmName));
		setMajorGenesCharacteristics(getGermplasmCharacteristics(MAJOR_GENES,germplasmName));

		setStudyTested(getStudyTested(germplasmName));
	}
	
	public String getNameSearch() {
		return nameSearch;
	}
	public void setNameSearch(String nameSearch) {
		this.nameSearch = nameSearch;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}


	public List<GermplasmType> getGermplasmType() {
		return germplasmType;
	}
	public void setGermplasmType(List<GermplasmType> germplasmType) {
		this.germplasmType = germplasmType;
	}


	public int getGermplasmTypeId() {
		return germplasmTypeId;
	}
	public void setGermplasmTypeId(int germplasmTypeId) {
		this.germplasmTypeId = germplasmTypeId;
	}


	public List<String> getKeyCharacteristicOption() {
		return keyCharacteristicOption;
	}
	public void setKeyCharacteristicOption(List<String> keyCharacteristicOption) {
		this.keyCharacteristicOption = keyCharacteristicOption;
	}

	public String getKeyCharactericticValue() {
		return keyCharactericticValue;
	}
	public void setKeyCharactericticValue(String keyCharactericticValue) {
		this.keyCharactericticValue = keyCharactericticValue;
	}


	public List<Germplasm> getGermplasmList() {
		return germplasmList;
	}
	public void setGermplasmList(List<Germplasm> germplasmList) {
		this.germplasmList = germplasmList;
	}

	public Germplasm getGermplasm() {
		return germplasm;
	}


	public void setGermplasm(Germplasm germplasm) {
		this.germplasm = germplasm;
	}


	public String getAbioticCharacteristics() {
		return abioticCharacteristics;
	}


	public void setAbioticCharacteristics(String abioticCharacteristics) {
		this.abioticCharacteristics = abioticCharacteristics;
	}


	public String getBioticCharacteristics() {
		return bioticCharacteristics;
	}


	public void setBioticCharacteristics(String bioticCharacteristics) {
		this.bioticCharacteristics = bioticCharacteristics;
	}


	public String getGrainQualityCharacteristics() {
		return grainQualityCharacteristics;
	}


	public void setGrainQualityCharacteristics(String grainQualityCharacteristics) {
		this.grainQualityCharacteristics = grainQualityCharacteristics;
	}


	public String getMajorGenesCharacteristics() {
		return majorGenesCharacteristics;
	}


	public void setMajorGenesCharacteristics(String majoyGenesCharacteristics) {
		this.majorGenesCharacteristics = majoyGenesCharacteristics;
	}




	public List<StudySearchResultModel> getStudyTested() {
		return studyTested;
	}


	public void setStudyTested(List<StudySearchResultModel> studyTested) {
		this.studyTested = studyTested;
	}




	public String getSearchResultLabel() {
		return searchResultLabel;
	}


	public void setSearchResultLabel(String searchResultLabel) {
		this.searchResultLabel = searchResultLabel;
	}






	public List<CharacteristicModel> getKeyAbioticList() {
		return keyAbioticList;
	}


	public void setKeyAbioticList(List<CharacteristicModel> keyAbioticList) {
		this.keyAbioticList = keyAbioticList;
	}


	public List<CharacteristicModel> getKeyBioticList() {
		return keyBioticList;
	}


	public void setKeyBioticList(List<CharacteristicModel> keyBioticList) {
		this.keyBioticList = keyBioticList;
	}


	public List<CharacteristicModel> getKeyGrainQualityList() {
		return keyGrainQualityList;
	}


	public void setKeyGrainQualityList(List<CharacteristicModel> keyGrainQualityList) {
		this.keyGrainQualityList = keyGrainQualityList;
	}


	public List<CharacteristicModel> getKeyMajorGenesList() {
		return keyMajorGenesList;
	}


	public void setKeyMajorGenesList(List<CharacteristicModel> keyMajorGenesList) {
		this.keyMajorGenesList = keyMajorGenesList;
	}


	private  List<Germplasm> getGermplasmByName(String name){
		BrowseGermplasmManagerImpl mgr= new BrowseGermplasmManagerImpl();
		if(name.contains("%") || name.contains("?") ){
			return (List<Germplasm>) mgr.getGermplasmByNameLike(name);
		}else{
			return (List<Germplasm>) mgr.getGermplasmByNameEqual(name);
		}
	}

	private List<Germplasm> getGermplasmByType(int typeid) {
		// TODO Auto-generated method stub
		BrowseGermplasmManagerImpl mgr= new BrowseGermplasmManagerImpl();
		return (List<Germplasm>) mgr.getGermplasmByType(typeid);
	}

	private List<Germplasm> getGermplasmByKeyCharacteristics(ArrayList<String> keyCharList, String KeyChar ) {
		// TODO Auto-generated method stub
		BrowseGermplasmManagerImpl mgr= new BrowseGermplasmManagerImpl();
		return  mgr.getGermplasmKeyCharacteristicsAbiotic(keyCharList,KeyChar);
	}


	

	private List<Germplasm> getGermplasmByKeyCharacteristics() {
		final BrowseGermplasmManagerImpl browseStudyManagerImpl= new BrowseGermplasmManagerImpl(); 


		KeyCharacteristicQueryModel keyCriteria= new KeyCharacteristicQueryModel();
		List<String> keyValues=new ArrayList<String>();

		for(String s:listKeyCharFilter){
			keyValues.add(s);
		}

		int size=keyValues.size();

		keyCriteria.setCountKeyCriteria(keyValues.size());
		keyCriteria.setKeyValues(keyValues);

		List<Germplasm> toreturn= browseStudyManagerImpl.getGermplasmKeyCharacteristics(keyCriteria);
		System.out.println("Size:"+toreturn.size());

		return toreturn;
	}


	private ArrayList<String> getKeyCharacteristicsSelected() {

		ArrayList<String> toreturn=new ArrayList<String>();

		for(CharacteristicModel keyValue:keyAbioticList){
			if(keyValue.isValue()){
				toreturn.add(keyValue.name);
			}
		}
		for(CharacteristicModel keyValue:keyBioticList){
			if(keyValue.isValue()){
				toreturn.add(keyValue.name);
			}
		}
		for(CharacteristicModel keyValue:keyGrainQualityList){
			if(keyValue.isValue()){
				toreturn.add(keyValue.name);
			}
		}
		for(CharacteristicModel keyValue:keyMajorGenesList){
			if(keyValue.isValue()){
				toreturn.add(keyValue.name);
			}
		}

		return toreturn;

	}


	@Command("AddNewKeyCharacteristics")
	public void AddNewKeyCharacteristics(@BindingParam("keyCharValue") String keyCharValue) {
		//		Chosenbox keyValues= (Chosenbox) component.getFellow("cmbKeyCharValue");
		keyCharValueList.addAll(keyCharValueList);
		System.out.println(keyCharValue);

	}



	@NotifyChange("germplasmList,studyTested")
	@Command
	public void getKeyCharacteristicOptionsList(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view) {

		Combobox cmbKeyChar= (Combobox) component.getFellow("cmbKeyChar");

		if(cmbKeyChar.getValue().equals("Abiotic")){
			keyCharacteristicOption=getAbioticOptions();
		}else if(cmbKeyChar.getValue().contains("Biotic")){
			keyCharacteristicOption=getBioticOptions();
		}else if(cmbKeyChar.getValue().contains("Grain")){
			keyCharacteristicOption=getGrainQuality();
		}else{
			keyCharacteristicOption=getMajorGenes();
		}

		Chosenbox box = (Chosenbox) component.getFellow("cmbKeyCharValue");
		box.setSelectedIndex(-1);



	}

//	@NotifyChange("*")
	@Command
	public void DisplayGermplasmInfo(Integer id, String gname){

		setGermplasm(getGermplasmDetailInformation(id));
		setAbioticCharacteristics(getGermplasmCharacteristics(ABIOTIC,gname));
		setBioticCharacteristics(getGermplasmCharacteristics(BIOTIC,gname));
		setGrainQualityCharacteristics(getGermplasmCharacteristics(GRAIN_QUALITY,gname));
		setMajorGenesCharacteristics(getGermplasmCharacteristics(MAJOR_GENES,gname));

		setStudyTested(getStudyTested(gname));
	}



	@NotifyChange("*")
	@Command
	public void DisplayStudyDetail(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view,@BindingParam("studyid")Integer studyid,@BindingParam("studyname")String studyname){


		Window studyDetailWindow = (Window)Executions.getCurrent().createComponents(
				"/user/browsegermplasm/studydetails.zul", null, null);
		studyDetailWindow.doModal();
		studyDetailWindow.setTitle(studyname);

		Include studyInformationPage = new Include();
		studyInformationPage.setSrc("/user/browsestudy/studyinformation.zul");
		studyInformationPage.setDynamicProperty("parentSource", getParentSource());
		studyInformationPage.setParent(studyDetailWindow);
		studyInformationPage.setDynamicProperty("studyId", studyid);




	}


	private List<StudySearchResultModel> getStudyTested(String gname) {
		BrowseGermplasmManagerImpl browseStudyManagerImpl= new BrowseGermplasmManagerImpl(); 
		return browseStudyManagerImpl.getStudyWithGemrplasmTested(gname);

	}


	private String getGermplasmCharacteristics(String keyChar, String gname) {
		String toreturn = "";
		GermplasmCharacteristicMananagerImpl mgr= new GermplasmCharacteristicMananagerImpl();
		List<GermplasmCharacteristics>  germplasmCharateristics= mgr.getGermplasmCharacteristicByKeyandGname(keyChar, gname);

		if(germplasmCharateristics.size() > 0){
			for(GermplasmCharacteristics key:germplasmCharateristics){
				toreturn+=key.getKeyvalue()+" ,";
			}
			return toreturn.substring(0,toreturn.length()-1);
		}

		return toreturn;

	}


	private List<String> getGrainQuality() {
		List<String> toreturn= new ArrayList<String>();

		GermplasmTypeManagerImpl mgr= new GermplasmTypeManagerImpl();
		List<KeyGrainQuality> keyGrainQualityList = mgr.getKeyGrainQualityOption();
		for(KeyGrainQuality k:keyGrainQualityList ){
			toreturn.add(k.getValue());
		}

		return toreturn;
	}

	private List<String> getMajorGenes() {
		List<String> toreturn= new ArrayList<String>();
		GermplasmTypeManagerImpl mgr= new GermplasmTypeManagerImpl();
		List<KeyMajorGenes> keyMajorGenesList = mgr.getKeyMajorGenesOption();
		for(KeyMajorGenes k:keyMajorGenesList ){
			toreturn.add(k.getValue());
		}

		return toreturn;
	}


	private List<String> getBioticOptions() {
		List<String> toreturn= new ArrayList<String>();
		GermplasmTypeManagerImpl mgr= new GermplasmTypeManagerImpl();
		List<KeyBiotic> keyBioticList = mgr.getKeyBioticOption();
		for(KeyBiotic k:keyBioticList ){
			toreturn.add(k.getValue());
		}

		return toreturn;
	}
	private List<String> getAbioticOptions() {
		List<String> toreturn= new ArrayList<String>();
		GermplasmTypeManagerImpl mgr= new GermplasmTypeManagerImpl();
		List<KeyAbiotic> keyAbioticList = mgr.getKeyAbioticOption();
		for(KeyAbiotic k:keyAbioticList ){
			toreturn.add(k.getValue());
		}

		return toreturn;
	}

	private Germplasm getGermplasmDetailInformation(int id){
		GermplasmManagerImpl mgr = new GermplasmManagerImpl();
		Germplasm g=mgr.getGermplasmById(id);
		return g;
	}

	private List<GermplasmType> getGermplasmTypeList() {
		GermplasmTypeManagerImpl mgr= new GermplasmTypeManagerImpl();
		germplasmType=mgr.getAllGermplasmType();
		for(GermplasmType type:germplasmType){
			germplasmTypeKey.put(type.getGermplasmtype(), type.getId());
		}
		return germplasmType;
	}

	public String getParentSource() {
		return parentSource;
	}

	public void setParentSource(String parentSource) {
		this.parentSource = parentSource;
	}

	public class CharacteristicModel {
		private String name;
		private boolean value;
		private int primaryid;

		public int getPrimaryid() {
			return primaryid;
		}

		public void setPrimaryid(int primaryid) {
			this.primaryid = primaryid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isValue() {
			return value;
		}

		public void setValue(boolean value) {
			this.value = value;

		}

	}


}
