package org.strasa.web.browsestudy.view.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.strasa.middleware.manager.BrowseGermplasmManagerImpl;
import org.strasa.middleware.manager.GermplasmCharacteristicMananagerImpl;
import org.strasa.middleware.manager.GermplasmManagerImpl;
import org.strasa.middleware.manager.GermplasmTypeManagerImpl;
import org.strasa.middleware.manager.StudyGermplasmManagerImpl;
import org.strasa.middleware.model.Germplasm;
import org.strasa.middleware.model.GermplasmCharacteristics;
import org.strasa.middleware.model.GermplasmType;
import org.strasa.middleware.model.KeyAbiotic;
import org.strasa.middleware.model.KeyBiotic;
import org.strasa.middleware.model.KeyGrainQuality;
import org.strasa.middleware.model.KeyMajorGenes;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;

public class GermplasmInfo {

	private String nameSearch;
	private String searchKey;
	private List<GermplasmType> germplasmType;
	private List<String> keyCharacteristicOption;
	private int germplasmTypeId;
	private String keyCharactericticValue;
	private List<GermplasmInfoModel> germplasmList = new ArrayList<GermplasmInfoModel>();
	private Germplasm germplasm;
	private String abioticCharacteristics;
	private String bioticCharacteristics;
	private String grainQualityCharacteristics;
	private String majorGenesCharacteristics;
	private List<StudySearchResultModel> studyTested;
	private HashMap<String, Integer> germplasmTypeKey = new HashMap<String, Integer>();
	private String searchResultLabel;

	private String parentSource;

	@Init
	public void init(@ExecutionArgParam("studyid") Integer studyId, @ExecutionArgParam("parentSource") String source) {
		setParentSource(source);
		System.out.println("studyid " + studyId);
		for (Germplasm sg : getGermplasmById(studyId)) {
			germplasmList.add(new GermplasmInfoModel(sg));
		}

		setSearchResultLabel("Record Count:  " + germplasmList.size() + "  row(s)");
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

	public List<GermplasmInfoModel> getGermplasmList() {
		return germplasmList;
	}

	public void setGermplasmList(List<GermplasmInfoModel> germplasmList) {
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

	@Command
	public void SetSearchUI(@ContextParam(ContextType.COMPONENT) Component component, @ContextParam(ContextType.VIEW) Component view) {

		Combobox cmbSearchKey = (Combobox) component.getFellow("cmbSearchKey");
		if (cmbSearchKey.getValue().equals("Name")) {
			component.getFellow("txtNameSearch").setVisible(true);
			component.getFellow("cmbKeyChar").setVisible(false);
			component.getFellow("cmbKeyCharValue").setVisible(false);
			component.getFellow("cmbGermplasmType").setVisible(false);
		} else if (cmbSearchKey.getValue().contains("Key")) {
			component.getFellow("txtNameSearch").setVisible(false);
			component.getFellow("cmbKeyChar").setVisible(true);
			component.getFellow("cmbKeyCharValue").setVisible(true);
			component.getFellow("cmbGermplasmType").setVisible(false);
		} else {
			component.getFellow("txtNameSearch").setVisible(false);
			component.getFellow("cmbKeyChar").setVisible(false);
			component.getFellow("cmbKeyCharValue").setVisible(false);
			component.getFellow("cmbGermplasmType").setVisible(true);
		}
	}

	private List<Germplasm> getGermplasmByName(String name) {

		GermplasmManagerImpl mgr = new GermplasmManagerImpl();
		return (List<Germplasm>) mgr.getGermplasmListByName(name);

	}

	private List<Germplasm> getGermplasmById(Integer studyId) {

		StudyGermplasmManagerImpl mgr = new StudyGermplasmManagerImpl();
		List<Germplasm> list;
		try {
			list = mgr.getGermplasmFromStudy(studyId);
		} catch (NullPointerException npe) {
			list = new ArrayList<Germplasm>();
		}
		return list;

	}

	private List<Germplasm> getGermplasmByType(int id) {
		// TODO Auto-generated method stub
		BrowseGermplasmManagerImpl mgr = new BrowseGermplasmManagerImpl();
		return (List<Germplasm>) mgr.getGermplasmListByType(id);
	}

	@NotifyChange("*")
	@Command
	public void getKeyCharacteristicOptionsList(@ContextParam(ContextType.COMPONENT) Component component, @ContextParam(ContextType.VIEW) Component view) {

		Combobox cmbKeyChar = (Combobox) component.getFellow("cmbKeyChar");

		if (cmbKeyChar.getValue().equals("Abiotic")) {
			keyCharacteristicOption = getAbioticOptions();
		} else if (cmbKeyChar.getValue().contains("Biotic")) {
			keyCharacteristicOption = getBioticOptions();
		} else if (cmbKeyChar.getValue().contains("Grain")) {
			keyCharacteristicOption = getGrainQuality();
		} else {
			keyCharacteristicOption = getMajorGenes();
		}

		Chosenbox box = (Chosenbox) component.getFellow("cmbKeyCharValue");
		box.setSelectedIndex(-1);

	}

	@NotifyChange("*")
	@Command
	public void DisplayGermplasmInfo(@ContextParam(ContextType.COMPONENT) Component component, @ContextParam(ContextType.VIEW) Component view, @BindingParam("germplasmId") Integer id, @BindingParam("gname") String gname) {
		// System.out.println(id + " "+gname);
		Groupbox groupBoxInfo = (Groupbox) component.getFellow("GermplasmDetailId");
		groupBoxInfo.setVisible(true);

		Groupbox groupBoxStudyTested = (Groupbox) component.getFellow("StudyTestedId");
		groupBoxStudyTested.setVisible(true);

		germplasm = getGermplasmDetailInformation(id);
		abioticCharacteristics = getGermplasmCharacteristics("Abiotic", gname);
		bioticCharacteristics = getGermplasmCharacteristics("Biotic", gname);

		studyTested = getStudyTested(gname);
	}

	private List<StudySearchResultModel> getStudyTested(String gname) {
		BrowseGermplasmManagerImpl browseGermplasmManagerImpl = new BrowseGermplasmManagerImpl();
		return browseGermplasmManagerImpl.getStudyWithGemrplasmTested(gname);

	}

	private String getGermplasmCharacteristics(String keyChar, String gname) {
		String toreturn = "";
		GermplasmCharacteristicMananagerImpl mgr = new GermplasmCharacteristicMananagerImpl();
		List<GermplasmCharacteristics> germplasmCharateristics = mgr.getGermplasmCharacteristicByKeyandGname(keyChar, gname);

		if (germplasmCharateristics.size() > 0) {
			for (GermplasmCharacteristics key : germplasmCharateristics) {
				toreturn += key.getKeyvalue() + " ,";
			}
			return toreturn.substring(0, toreturn.length() - 1);
		}

		return toreturn;

	}

	private List<String> getGrainQuality() {
		List<String> toreturn = new ArrayList<String>();

		GermplasmTypeManagerImpl mgr = new GermplasmTypeManagerImpl();
		List<KeyGrainQuality> keyGrainQualityList = mgr.getKeyGrainQualityOption();
		for (KeyGrainQuality k : keyGrainQualityList) {
			toreturn.add(k.getValue());
		}

		return toreturn;
	}

	private List<String> getMajorGenes() {
		List<String> toreturn = new ArrayList<String>();
		GermplasmTypeManagerImpl mgr = new GermplasmTypeManagerImpl();
		List<KeyMajorGenes> keyMajorGenesList = mgr.getKeyMajorGenesOption();
		for (KeyMajorGenes k : keyMajorGenesList) {
			toreturn.add(k.getValue());
		}

		return toreturn;
	}

	private List<String> getBioticOptions() {
		List<String> toreturn = new ArrayList<String>();
		GermplasmTypeManagerImpl mgr = new GermplasmTypeManagerImpl();
		List<KeyBiotic> keyBioticList = mgr.getKeyBioticOption();
		for (KeyBiotic k : keyBioticList) {
			toreturn.add(k.getValue());
		}

		return toreturn;
	}

	private List<String> getAbioticOptions() {
		List<String> toreturn = new ArrayList<String>();
		GermplasmTypeManagerImpl mgr = new GermplasmTypeManagerImpl();
		List<KeyAbiotic> keyAbioticList = mgr.getKeyAbioticOption();
		for (KeyAbiotic k : keyAbioticList) {
			toreturn.add(k.getValue());
		}

		return toreturn;
	}

	private Germplasm getGermplasmDetailInformation(int id) {
		GermplasmManagerImpl mgr = new GermplasmManagerImpl();
		Germplasm g = mgr.getGermplasmById(id);
		return g;
	}

	private List<GermplasmType> getGermplasmTypeList() {
		GermplasmTypeManagerImpl mgr = new GermplasmTypeManagerImpl();
		germplasmType = mgr.getAllGermplasmType();
		for (GermplasmType type : germplasmType) {
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

	public class GermplasmInfoModel extends Germplasm {
		private GermplasmType type;
		private String characteristics;

		private StringBuilder sb = new StringBuilder();

		public GermplasmInfoModel(Germplasm sg) {
			this.setUserid(sg.getUserid());
			this.setGid(sg.getGid());
			this.setGermplasmname(sg.getGermplasmname());
			this.setOthername(sg.getOthername());
			this.setBreeder(sg.getBreeder());
			this.setIrnumber(sg.getIrnumber());
			this.setIrcross(sg.getIrcross());
			this.setParentage(sg.getParentage());
			this.setFemaleparent(sg.getFemaleparent());
			this.setMaleparent(sg.getMaleparent());
			this.setSelectionhistory(sg.getSelectionhistory());
			this.setSource(sg.getSource());
			setType(getGermplasmTypeList().get(sg.getGermplasmtypeid()));
			sb.append(" ");
			// sb.append(getGermplasmCharacteristics("Biotic",
			// getGermplasmname()));
			// sb.append(getGermplasmCharacteristics("Abiotic",
			// getGermplasmname()));
			// sb.append(getGermplasmCharacteristics("Grain",
			// getGermplasmname()));

			setCharacteristics(sb.toString());
		}

		public GermplasmType getType() {
			return type;
		}

		public void setType(GermplasmType type) {
			this.type = type;
		}

		public String getCharacteristics() {
			return characteristics;
		}

		public void setCharacteristics(String characteristics) {
			this.characteristics = characteristics;
		}
	}
}
