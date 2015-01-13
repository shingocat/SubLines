/**
 *
 * 	Builder: 	Mao Qin
 *	Email:		mqin@ymail.com
 *	Date:		2014 - Jul 7, 2014
 *	Project: 	StrasaWeb
 *  Package: 	org.strasa.web.segmentquery.view.model
 *	Name:	 IntrogressionLineDetail.java
 *	
 *
 */
package org.strasa.web.segmentquery.view.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.strasa.middleware.manager.BrowseGermplasmManagerImpl;
import org.strasa.middleware.manager.GermplasmCharacteristicMananagerImpl;
import org.strasa.middleware.manager.GermplasmManagerImpl;
import org.strasa.middleware.model.Germplasm;
import org.strasa.middleware.model.GermplasmCharacteristics;
import org.strasa.middleware.model.GermplasmType;
import org.strasa.middleware.model.IntrogressionLine;
import org.strasa.middleware.model.Segment;
import org.strasa.web.browsestudy.view.model.StudySearchResultModel;
import org.strasa.web.germplasmquery.view.model.GermplasmDetail.CharacteristicModel;
import org.strasa.web.managegermplasm.view.pojos.SegmentExt;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;

public class IntrogressionLineDetail
{
	private IntrogressionLine introgressionLine;
	private String nameSearch;
	private String searchKey;
	private List<GermplasmType> germplasmType;
	private List<String> keyCharacteristicOption;
	private int germplasmTypeId=0;
	private String keyCharactericticValue;
	private List<Germplasm> germplasmList;
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
	private Segment selectedSegment;

	private String parentSource;
	
	@Init
	public void init(@ExecutionArgParam("gname")String germplasmName, @ExecutionArgParam("parentSource")String source, @ExecutionArgParam("introgressionLine") IntrogressionLine introgressionLine){
		setParentSource(source);
		System.out.println("parentSource "+source);
		System.out.println("gname"+ germplasmName);
		System.out.println("introgression line " + introgressionLine.toString());
		
		BrowseGermplasmManagerImpl mgr= new BrowseGermplasmManagerImpl();
		setGermplasmList( mgr.getGermplasmByNameLike(germplasmName));
		
		GermplasmManagerImpl gMgr = new GermplasmManagerImpl();		
		Germplasm g=gMgr.getGermplasmById(getGermplasmList().get(0).getId());
		
		setIntrogressionLine(introgressionLine);
		setAbioticCharacteristics(getGermplasmCharacteristics(ABIOTIC,germplasmName));
		setBioticCharacteristics(getGermplasmCharacteristics(BIOTIC,germplasmName));
		setGrainQualityCharacteristics(getGermplasmCharacteristics(GRAIN_QUALITY,germplasmName));
		setMajorGenesCharacteristics(getGermplasmCharacteristics(MAJOR_GENES,germplasmName));

		setStudyTested(getStudyTested(germplasmName));
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
	
	private List<StudySearchResultModel> getStudyTested(String gname) {
		BrowseGermplasmManagerImpl browseStudyManagerImpl= new BrowseGermplasmManagerImpl(); 
		return browseStudyManagerImpl.getStudyWithGemrplasmTested(gname);

	}
	
	
	public IntrogressionLine getIntrogressionLine()
	{
		return introgressionLine;
	}

	public void setIntrogressionLine(IntrogressionLine introgressionLine)
	{
		this.introgressionLine = introgressionLine;
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

	public List<String> getKeyCharacteristicOption()
	{
		return keyCharacteristicOption;
	}

	public void setKeyCharacteristicOption(List<String> keyCharacteristicOption)
	{
		this.keyCharacteristicOption = keyCharacteristicOption;
	}

	public int getGermplasmTypeId()
	{
		return germplasmTypeId;
	}

	public void setGermplasmTypeId(int germplasmTypeId)
	{
		this.germplasmTypeId = germplasmTypeId;
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

	public void setGrainQualityCharacteristics(String grainQualityCharacteristics)
	{
		this.grainQualityCharacteristics = grainQualityCharacteristics;
	}

	public String getMajorGenesCharacteristics()
	{
		return majorGenesCharacteristics;
	}

	public void setMajorGenesCharacteristics(String majorGenesCharacteristics)
	{
		this.majorGenesCharacteristics = majorGenesCharacteristics;
	}

	public List<StudySearchResultModel> getStudyTested()
	{
		return studyTested;
	}

	public void setStudyTested(List<StudySearchResultModel> studyTested)
	{
		this.studyTested = studyTested;
	}

	public HashMap<String, Integer> getGermplasmTypeKey()
	{
		return germplasmTypeKey;
	}

	public void setGermplasmTypeKey(HashMap<String, Integer> germplasmTypeKey)
	{
		this.germplasmTypeKey = germplasmTypeKey;
	}

	public ArrayList<String> getKeyCharValueList()
	{
		return keyCharValueList;
	}

	public void setKeyCharValueList(ArrayList<String> keyCharValueList)
	{
		this.keyCharValueList = keyCharValueList;
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

	public void setKeyGrainQualityList(List<CharacteristicModel> keyGrainQualityList)
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

	public List<String> getListKeyCharFilter()
	{
		return listKeyCharFilter;
	}

	public void setListKeyCharFilter(List<String> listKeyCharFilter)
	{
		this.listKeyCharFilter = listKeyCharFilter;
	}

	public String getParentSource()
	{
		return parentSource;
	}

	public void setParentSource(String parentSource)
	{
		this.parentSource = parentSource;
	}

	public static String getAbiotic()
	{
		return ABIOTIC;
	}

	public static String getBiotic()
	{
		return BIOTIC;
	}

	public static String getGrainQuality()
	{
		return GRAIN_QUALITY;
	}

	public static String getMajorGenes()
	{
		return MAJOR_GENES;
	}
	
	public Segment getSelectedSegment()
	{
		return selectedSegment;
	}

	public void setSelectedSegment(Segment selectedSegment)
	{
		this.selectedSegment = selectedSegment;
	}
	
	
}

