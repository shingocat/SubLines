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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Textbox;

public class CopyOfBrowseGermplasm
{
	
	private String nameSearch;
	private String searchKey;
	private List<GermplasmType> germplasmType;
	private List<String> keyCharacteristicOption;
	private int germplasmTypeId = 0;
	private String keyCharactericticValue;
	private List<Germplasm> germplasmList;
	private Germplasm germplasm;
	private String abioticCharacteristics;
	private String bioticCharacteristics;
	private String grainQualityCharacteristics;
	private String majorGenesCharacteristics;
	private List<StudySearchResultModel> studyTested;
	private HashMap<String, Integer> germplasmTypeKey = new HashMap<String, Integer>();
	private ArrayList<String> keyCharValueList = new ArrayList<String>();
	private final static String ABIOTIC = "Abiotic";
	private final static String BIOTIC = "Biotic";
	private final static String GRAIN_QUALITY = "Grain Quality";
	private final static String MAJOR_GENES = "Major Genes";
	private String searchResultLabel = "Search Result";
	private List<CharacteristicModel> keyAbioticList;
	private List<CharacteristicModel> keyBioticList;
	private List<CharacteristicModel> keyGrainQualityList;
	private List<CharacteristicModel> keyMajorGenesList;
	private List<String> listKeyCharFilter = new ArrayList<String>();
	private HashMap<Integer, Tab> activeGermplasmIds;
	
	private Tab germplasmSearchTab;
	
	private Integer segmentNumber; // adding by QIN MAO
	private String recurrentParent; // adding by QIN MAO
	private GermplasmType selectedGermplasmType; //adding by QIN MAO
	
	@Init
	public void setInitialData(
	        @ExecutionArgParam("germplasmSearchTab") Tab germplasmSearchTab)
	{
		activeGermplasmIds = new HashMap<Integer, Tab>();
		this.germplasmSearchTab = germplasmSearchTab;
		this.germplasmList = getGermplasmByName("");
		this.germplasm = getGermplasmDetailInformation(-1);
		this.germplasmType = getGermplasmTypeList();
		keyAbioticList = new ArrayList<CharacteristicModel>();
		keyBioticList = new ArrayList<CharacteristicModel>();
		keyGrainQualityList = new ArrayList<CharacteristicModel>();
		keyMajorGenesList = new ArrayList<CharacteristicModel>();
		populateKeyCharacteristics();
	}
	
	public String getNameSearch()
	{
		return nameSearch;
	}
	
	public void setNameSearch(String nameSearch)
	{
		this.nameSearch = nameSearch;
	}
	
	public String getSearchKey()
	{
		return searchKey;
	}
	
	public void setSearchKey(String searchKey)
	{
		this.searchKey = searchKey;
	}
	
	public List<GermplasmType> getGermplasmType()
	{
		return germplasmType;
	}
	
	public void setGermplasmType(List<GermplasmType> germplasmType)
	{
		this.germplasmType = germplasmType;
	}
	
	public int getGermplasmTypeId()
	{
		return germplasmTypeId;
	}
	
	public void setGermplasmTypeId(int germplasmTypeId)
	{
		this.germplasmTypeId = germplasmTypeId;
	}
	
	public List<String> getKeyCharacteristicOption()
	{
		return keyCharacteristicOption;
	}
	
	public void setKeyCharacteristicOption(List<String> keyCharacteristicOption)
	{
		this.keyCharacteristicOption = keyCharacteristicOption;
	}
	
	public String getKeyCharactericticValue()
	{
		return keyCharactericticValue;
	}
	
	public void setKeyCharactericticValue(String keyCharactericticValue)
	{
		this.keyCharactericticValue = keyCharactericticValue;
	}
	
	public List<Germplasm> getGermplasmList()
	{
		return germplasmList;
	}
	
	public void setGermplasmList(List<Germplasm> germplasmList)
	{
		this.germplasmList = germplasmList;
	}
	
	public Germplasm getGermplasm()
	{
		return germplasm;
	}
	
	public void setGermplasm(Germplasm germplasm)
	{
		this.germplasm = germplasm;
	}
	
	public String getAbioticCharacteristics()
	{
		return abioticCharacteristics;
	}
	
	public void setAbioticCharacteristics(String abioticCharacteristics)
	{
		this.abioticCharacteristics = abioticCharacteristics;
	}
	
	public String getBioticCharacteristics()
	{
		return bioticCharacteristics;
	}
	
	public void setBioticCharacteristics(String bioticCharacteristics)
	{
		this.bioticCharacteristics = bioticCharacteristics;
	}
	
	public String getGrainQualityCharacteristics()
	{
		return grainQualityCharacteristics;
	}
	
	public void setGrainQualityCharacteristics(
	        String grainQualityCharacteristics)
	{
		this.grainQualityCharacteristics = grainQualityCharacteristics;
	}
	
	public String getMajorGenesCharacteristics()
	{
		return majorGenesCharacteristics;
	}
	
	public void setMajorGenesCharacteristics(String majoyGenesCharacteristics)
	{
		this.majorGenesCharacteristics = majoyGenesCharacteristics;
	}
	
	public List<StudySearchResultModel> getStudyTested()
	{
		return studyTested;
	}
	
	public void setStudyTested(List<StudySearchResultModel> studyTested)
	{
		this.studyTested = studyTested;
	}
	
	public String getSearchResultLabel()
	{
		return searchResultLabel;
	}
	
	public void setSearchResultLabel(String searchResultLabel)
	{
		this.searchResultLabel = searchResultLabel;
	}
	
	public List<CharacteristicModel> getKeyAbioticList()
	{
		return keyAbioticList;
	}
	
	public void setKeyAbioticList(List<CharacteristicModel> keyAbioticList)
	{
		this.keyAbioticList = keyAbioticList;
	}
	
	public List<CharacteristicModel> getKeyBioticList()
	{
		return keyBioticList;
	}
	
	public void setKeyBioticList(List<CharacteristicModel> keyBioticList)
	{
		this.keyBioticList = keyBioticList;
	}
	
	public List<CharacteristicModel> getKeyGrainQualityList()
	{
		return keyGrainQualityList;
	}
	
	public void setKeyGrainQualityList(
	        List<CharacteristicModel> keyGrainQualityList)
	{
		this.keyGrainQualityList = keyGrainQualityList;
	}
	
	public List<CharacteristicModel> getKeyMajorGenesList()
	{
		return keyMajorGenesList;
	}
	
	public void setKeyMajorGenesList(List<CharacteristicModel> keyMajorGenesList)
	{
		this.keyMajorGenesList = keyMajorGenesList;
	}
	
	@NotifyChange("keyAbioticList,keyBioticList,keyGrainQualityList,keyMajorGenesList")
	@Command
	public void SetSearchUI(
	        @ContextParam(ContextType.COMPONENT) Component component,
	        @ContextParam(ContextType.VIEW) Component view)
	{
		
		Combobox cmbSearchKey = (Combobox) component.getFellow("cmbSearchKey");
		if (cmbSearchKey.getValue().equals("Name"))
		{
			component.getFellow("txtNameSearch").setVisible(true);
			component.getFellow("cmbKeyChar").setVisible(false);
			component.getFellow("cmbKeyCharValue").setVisible(false);
			component.getFellow("cmbGermplasmType").setVisible(false);
			component.getFellow("keyCharacteristicsOptions").setVisible(false);
			component.getFellow("resetKeyCharId").setVisible(false);
			
		} else if (cmbSearchKey.getValue().contains("Key"))
		{
			component.getFellow("txtNameSearch").setVisible(false);
			component.getFellow("cmbKeyChar").setVisible(false);
			component.getFellow("cmbKeyCharValue").setVisible(false);
			component.getFellow("cmbGermplasmType").setVisible(false);
			component.getFellow("keyCharacteristicsOptions").setVisible(true);
			component.getFellow("resetKeyCharId").setVisible(true);
			
		} else
		{
			component.getFellow("txtNameSearch").setVisible(false);
			component.getFellow("cmbKeyChar").setVisible(false);
			component.getFellow("cmbKeyCharValue").setVisible(false);
			component.getFellow("cmbGermplasmType").setVisible(true);
			component.getFellow("keyCharacteristicsOptions").setVisible(false);
			component.getFellow("resetKeyCharId").setVisible(false);
			if (selectedGermplasmType != null && selectedGermplasmType.getGermplasmtype().equals("IL"))
				component.getFellow("searchIntrogresionLineOption").setVisible(
				        true);
		}
	}
	
	private void populateKeyCharacteristics()
	{
		KeyCharacteristicManagerImpl keyMan = new KeyCharacteristicManagerImpl();
		List<KeyBiotic> keyBiotic = keyMan.getAllBiotic();
		List<KeyAbiotic> keyAbiotic = keyMan.getAllAbiotic();
		List<KeyGrainQuality> keyGrainQuality = keyMan.getAllGrainQuality();
		List<KeyMajorGenes> keyMajorGenes = keyMan.getAllMajorGenes();
		
		keyAbioticList.clear();
		keyBioticList.clear();
		keyGrainQualityList.clear();
		keyMajorGenesList.clear();
		
		for (KeyBiotic m : keyBiotic)
		{
			CharacteristicModel charKey = new CharacteristicModel();
			charKey.setName(m.getValue());
			charKey.setPrimaryid(m.getId());
			charKey.setValue(false);
			keyBioticList.add(charKey);
		}
		
		for (KeyAbiotic m : keyAbiotic)
		{
			CharacteristicModel charKey = new CharacteristicModel();
			charKey.setName(m.getValue());
			charKey.setPrimaryid(m.getId());
			charKey.setValue(false);
			keyAbioticList.add(charKey);
		}
		
		for (KeyGrainQuality m : keyGrainQuality)
		{
			CharacteristicModel charKey = new CharacteristicModel();
			charKey.setName(m.getValue());
			charKey.setPrimaryid(m.getId());
			charKey.setValue(false);
			keyGrainQualityList.add(charKey);
		}
		
		for (KeyMajorGenes m : keyMajorGenes)
		{
			CharacteristicModel charKey = new CharacteristicModel();
			charKey.setName(m.getValue());
			charKey.setPrimaryid(m.getId());
			charKey.setValue(false);
			keyMajorGenesList.add(charKey);
		}
		
	}
	
	private List<Germplasm> getGermplasmByName(String name)
	{
		
		BrowseGermplasmManagerImpl mgr = new BrowseGermplasmManagerImpl();
		if (name.contains("%") || name.contains("?"))
		{
			return (List<Germplasm>) mgr.getGermplasmByNameLike(name);
		} else
		{
			return (List<Germplasm>) mgr.getGermplasmByNameEqual(name);
		}
	}
	
	private List<Germplasm> getGermplasmByType(int typeid)
	{
		// TODO Auto-generated method stub
		BrowseGermplasmManagerImpl mgr = new BrowseGermplasmManagerImpl();
		return (List<Germplasm>) mgr.getGermplasmByType(typeid);
	}
	
	private List<Germplasm> getGermplasmByKeyCharacteristics(
	        ArrayList<String> keyCharList, String KeyChar)
	{
		// TODO Auto-generated method stub
		BrowseGermplasmManagerImpl mgr = new BrowseGermplasmManagerImpl();
		return mgr.getGermplasmKeyCharacteristicsAbiotic(keyCharList, KeyChar);
	}
	
	@NotifyChange("*")
	@Command
	public void SearchGermplasm(
	        @ContextParam(ContextType.COMPONENT) Component component,
	        @ContextParam(ContextType.VIEW) Component view)
	{
		Combobox cmbSearchKey = (Combobox) component.getFellow("cmbSearchKey");
		
		if (cmbSearchKey.getValue().equals("Name"))
		{
			Textbox txt = (Textbox) component.getFellow("txtNameSearch");
			if (txt.getValue().length() > 0)
			{
				this.germplasmList = getGermplasmByName(txt.getValue());
				if (germplasmList.size() == 1)
				{
					openGermplasmDetailInGermplasm(component, view,
					        germplasmList.get(0).getId(), germplasmList.get(0)
					                .getGermplasmname());
				}
			} else
			{
				Messagebox.show("Please enter germplasm name to search",
				        "Warning", null, null, null, null);
			}
		} else if (cmbSearchKey.getValue().contains("Key"))
		{
			Chosenbox keyValues = (Chosenbox) component
			        .getFellow("cmbKeyCharValue");
			listKeyCharFilter.clear();
			this.listKeyCharFilter = getKeyCharacteristicsSelected();
			if (this.listKeyCharFilter.size() > 0)
			{
				this.germplasmList = getGermplasmByKeyCharacteristics();
			} else
			{
				Messagebox.show("Please select germplasm key characteristics",
				        "Warning", null, null, null, null);
			}
			
		} else if (cmbSearchKey.getValue().equals("Type"))
		{
			Combobox cmbGermplasmType = (Combobox) component
			        .getFellow("cmbGermplasmType");
			if (cmbGermplasmType.getValue().toString().length() > 0)
			{
				int germplasmTypeId = germplasmTypeKey.get(cmbGermplasmType
				        .getValue());
				this.germplasmList = getGermplasmByType(germplasmTypeId);
			} else
			{
				Messagebox.show("Please select type ", "Warning", null, null,
				        null, null);
			}
		}
		
		searchResultLabel = "Search Result:  " + this.germplasmList.size()
		        + "  row(s) returned";
		if (this.germplasmList.size() == 1)
		{
			DisplayGermplasmInfo(component, view, this.germplasmList.get(0)
			        .getId(), this.germplasmList.get(0).getGermplasmname());
		} else
		{
			DisplayGermplasmInfo(component, view, -1, "");
			//			Groupbox groupBoxInfo= (Groupbox) component.getFellow("GermplasmDetailId");
			//			groupBoxInfo.setVisible(false);
			//
			//			Groupbox groupBoxStudyTested= (Groupbox) component.getFellow("StudyTestedId");
			//			groupBoxStudyTested.setVisible(false);
		}
		
	}
	
	private List<Germplasm> getGermplasmByKeyCharacteristics()
	{
		final BrowseGermplasmManagerImpl browseStudyManagerImpl = new BrowseGermplasmManagerImpl();
		List<Germplasm> toreturn = new ArrayList<Germplasm>();
		try
		{
			
			KeyCharacteristicQueryModel keyCriteria = new KeyCharacteristicQueryModel();
			List<String> keyValues = new ArrayList<String>();
			
			for (String s : listKeyCharFilter)
			{
				keyValues.add(s);
			}
			
			int size = keyValues.size();
			
			keyCriteria.setCountKeyCriteria(keyValues.size());
			keyCriteria.setKeyValues(keyValues);
			
			return toreturn = browseStudyManagerImpl
			        .getGermplasmKeyCharacteristics(keyCriteria);
			//		System.out.println("Size:"+toreturn.size());
		} catch (Exception e)
		{
			
		}
		return toreturn;
	}
	
	private ArrayList<String> getKeyCharacteristicsSelected()
	{
		
		ArrayList<String> toreturn = new ArrayList<String>();
		
		for (CharacteristicModel keyValue : keyAbioticList)
		{
			if (keyValue.isValue())
			{
				toreturn.add(keyValue.name);
			}
		}
		for (CharacteristicModel keyValue : keyBioticList)
		{
			if (keyValue.isValue())
			{
				toreturn.add(keyValue.name);
			}
		}
		for (CharacteristicModel keyValue : keyGrainQualityList)
		{
			if (keyValue.isValue())
			{
				toreturn.add(keyValue.name);
			}
		}
		for (CharacteristicModel keyValue : keyMajorGenesList)
		{
			if (keyValue.isValue())
			{
				toreturn.add(keyValue.name);
			}
		}
		
		for (String s : toreturn)
		{
			System.out.println(s);
		}
		return toreturn;
		
	}
	
	@Command("AddNewKeyCharacteristics")
	public void AddNewKeyCharacteristics(
	        @BindingParam("keyCharValue") String keyCharValue)
	{
		//		Chosenbox keyValues= (Chosenbox) component.getFellow("cmbKeyCharValue");
		keyCharValueList.addAll(keyCharValueList);
		System.out.println(keyCharValue);
		
	}
	
	@NotifyChange("germplasmList,studyTested")
	@Command
	public void getKeyCharacteristicOptionsList(
	        @ContextParam(ContextType.COMPONENT) Component component,
	        @ContextParam(ContextType.VIEW) Component view)
	{
		
		Combobox cmbKeyChar = (Combobox) component.getFellow("cmbKeyChar");
		
		if (cmbKeyChar.getValue().equals("Abiotic"))
		{
			keyCharacteristicOption = getAbioticOptions();
		} else if (cmbKeyChar.getValue().contains("Biotic"))
		{
			keyCharacteristicOption = getBioticOptions();
		} else if (cmbKeyChar.getValue().contains("Grain"))
		{
			keyCharacteristicOption = getGrainQuality();
		} else
		{
			keyCharacteristicOption = getMajorGenes();
		}
		
		Chosenbox box = (Chosenbox) component.getFellow("cmbKeyCharValue");
		box.setSelectedIndex(-1);
	}
	
	@NotifyChange("*")
	@Command
	public void ResetKeyChar(
	        @ContextParam(ContextType.COMPONENT) Component component,
	        @ContextParam(ContextType.VIEW) Component view)
	{
		
		Listbox list = (Listbox) component.getFellow("listboxAbioticId");
		populateKeyCharacteristics();
		list.invalidate();
		germplasmList.clear();
		searchResultLabel = "";
		
	}
	
	/* @NotifyChange("*") */
	@Command
	public void DisplayGermplasmInfo(
	        @ContextParam(ContextType.COMPONENT) Component component,
	        @ContextParam(ContextType.VIEW) Component view,
	        @BindingParam("germplasmId") Integer id,
	        @BindingParam("gname") String gname)
	{
		//		System.out.println(id + " "+gname);
		//		Groupbox groupBoxInfo= (Groupbox) component.getFellow("GermplasmDetailId");
		//		groupBoxInfo.setVisible(true);
		//
		//		Groupbox groupBoxStudyTested= (Groupbox) component.getFellow("StudyTestedId");
		//		groupBoxStudyTested.setVisible(true);
		
		germplasm = getGermplasmDetailInformation(id);
		abioticCharacteristics = getGermplasmCharacteristics(ABIOTIC, gname);
		bioticCharacteristics = getGermplasmCharacteristics(BIOTIC, gname);
		grainQualityCharacteristics = getGermplasmCharacteristics(
		        GRAIN_QUALITY, gname);
		majorGenesCharacteristics = getGermplasmCharacteristics(MAJOR_GENES,
		        gname);
		
		studyTested = getStudyTested(gname);
	}
	
	@NotifyChange("*")
	@GlobalCommand
	public void openGermplasmDetailInGermplasm(
	        @ContextParam(ContextType.COMPONENT) Component component,
	        @ContextParam(ContextType.VIEW) Component view,
	        @BindingParam("germplasmId") Integer id,
	        @BindingParam("gname") String gname)
	{
		
		Tabpanels tabPanels = (Tabpanels) component.getFellow("tabPanels");
		Tabs tabs = (Tabs) component.getFellow("tabs");
		Tabbox tabBox = (Tabbox) component.getFellow("tabBox");
		
		if (!activeGermplasmIds.containsKey(id))
		{
			final int gid = id;
			Tab newTab = new Tab();
			newTab.setLabel(gname);
			newTab.setClosable(true);
			newTab.addEventListener("onClose", new EventListener()
			{
				@Override
				public void onEvent(Event event) throws Exception
				{
					activeGermplasmIds.remove(gid);
				}
			});
			Tabpanel newPanel = new Tabpanel();
			
			//initialize view after view construction.
			Include studyInformationPage = new Include();
			studyInformationPage
			        .setSrc("/user/browsegermplasm/germplasmdetail.zul");
			studyInformationPage.setParent(newPanel);
			studyInformationPage
			        .setDynamicProperty("parentSource", "germplasm");
			studyInformationPage.setDynamicProperty("gname", gname);
			
			tabPanels.appendChild(newPanel);
			tabs.appendChild(newTab);
			tabBox.setSelectedPanel(newPanel);
			
			newTab.setSelected(true);
			activeGermplasmIds.put(id, newTab);
			
		} else
		{
			Tab tab = activeGermplasmIds.get(id);
			tab.setSelected(true);
		}
		//		
		germplasmSearchTab.setSelected(true);
		//		Include studyInformationPage = new Include();
		//		studyInformationPage.setSrc("/user/browsegermplasm/germplasmdetail.zul");
		//		studyInformationPage.setParent(studyDetailWindow);
		//		studyInformationPage.setDynamicProperty("gname", gname);
		
	}
	
	private List<StudySearchResultModel> getStudyTested(String gname)
	{
		BrowseGermplasmManagerImpl browseStudyManagerImpl = new BrowseGermplasmManagerImpl();
		return browseStudyManagerImpl.getStudyWithGemrplasmTested(gname);
		
	}
	
	private String getGermplasmCharacteristics(String keyChar, String gname)
	{
		String toreturn = "";
		GermplasmCharacteristicMananagerImpl mgr = new GermplasmCharacteristicMananagerImpl();
		List<GermplasmCharacteristics> germplasmCharateristics = mgr
		        .getGermplasmCharacteristicByKeyandGname(keyChar, gname);
		
		if (germplasmCharateristics.size() > 0)
		{
			for (GermplasmCharacteristics key : germplasmCharateristics)
			{
				toreturn += key.getKeyvalue() + " ,";
			}
			return toreturn.substring(0, toreturn.length() - 1);
		}
		
		return toreturn;
		
	}
	
	private List<String> getGrainQuality()
	{
		List<String> toreturn = new ArrayList<String>();
		
		GermplasmTypeManagerImpl mgr = new GermplasmTypeManagerImpl();
		List<KeyGrainQuality> keyGrainQualityList = mgr
		        .getKeyGrainQualityOption();
		for (KeyGrainQuality k : keyGrainQualityList)
		{
			toreturn.add(k.getValue());
		}
		
		return toreturn;
	}
	
	private List<String> getMajorGenes()
	{
		List<String> toreturn = new ArrayList<String>();
		GermplasmTypeManagerImpl mgr = new GermplasmTypeManagerImpl();
		List<KeyMajorGenes> keyMajorGenesList = mgr.getKeyMajorGenesOption();
		for (KeyMajorGenes k : keyMajorGenesList)
		{
			toreturn.add(k.getValue());
		}
		
		return toreturn;
	}
	
	private List<String> getBioticOptions()
	{
		List<String> toreturn = new ArrayList<String>();
		GermplasmTypeManagerImpl mgr = new GermplasmTypeManagerImpl();
		List<KeyBiotic> keyBioticList = mgr.getKeyBioticOption();
		for (KeyBiotic k : keyBioticList)
		{
			toreturn.add(k.getValue());
		}
		
		return toreturn;
	}
	
	private List<String> getAbioticOptions()
	{
		List<String> toreturn = new ArrayList<String>();
		GermplasmTypeManagerImpl mgr = new GermplasmTypeManagerImpl();
		List<KeyAbiotic> keyAbioticList = mgr.getKeyAbioticOption();
		for (KeyAbiotic k : keyAbioticList)
		{
			toreturn.add(k.getValue());
		}
		
		return toreturn;
	}
	
	private Germplasm getGermplasmDetailInformation(int id)
	{
		GermplasmManagerImpl mgr = new GermplasmManagerImpl();
		Germplasm g = mgr.getGermplasmById(id);
		return g;
	}
	
	private List<GermplasmType> getGermplasmTypeList()
	{
		GermplasmTypeManagerImpl mgr = new GermplasmTypeManagerImpl();
		germplasmType = mgr.getAllGermplasmType();
		for (GermplasmType type : germplasmType)
		{
			germplasmTypeKey.put(type.getGermplasmtype(), type.getId());
		}
		return germplasmType;
	}
	
	public class CharacteristicModel
	{
		private String name;
		private boolean value;
		private int primaryid;
		
		public int getPrimaryid()
		{
			return primaryid;
		}
		
		public void setPrimaryid(int primaryid)
		{
			this.primaryid = primaryid;
		}
		
		public String getName()
		{
			return name;
		}
		
		public void setName(String name)
		{
			this.name = name;
		}
		
		public boolean isValue()
		{
			return value;
		}
		
		public void setValue(boolean value)
		{
			this.value = value;
			
		}
		
	}
	
	public Integer getSegmentNumber()
	{
		return segmentNumber;
	}
	
	public void setSegmentNumber(Integer segmentNumber)
	{
		this.segmentNumber = segmentNumber;
	}
	
	public String getRecurrentParent()
	{
		return recurrentParent;
	}
	
	public void setRecurrentParent(String recurrentParent)
	{
		this.recurrentParent = recurrentParent;
	}
	
	public GermplasmType getSelectedGermplasmType()
	{
		return selectedGermplasmType;
	}
	
	public void setSelectedGermplasmType(GermplasmType selectedGermplasmType)
	{
		this.selectedGermplasmType = selectedGermplasmType;
	}
	
}
