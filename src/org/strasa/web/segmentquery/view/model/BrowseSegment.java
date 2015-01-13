/**
 * Builder: Mao Qin
 * Email: mqin@ymail.com
 * Date: 2014 - Jul 5, 2014
 * Project: StrasaWeb
 * Package: org.strasa.web.segmentquery.view.model
 * Name: BrowseSegment.java
 */
package org.strasa.web.segmentquery.view.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.strasa.middleware.manager.IntrogressionLineManagerImpl;
import org.strasa.middleware.manager.SegmentManagerImpl;
import org.strasa.middleware.model.IntrogressionLine;
import org.strasa.middleware.model.Segment;
import org.strasa.middleware.model.enumeration.Chromosome;
import org.strasa.middleware.util.ConditionAndValue;
import org.strasa.web.germplasmquery.view.model.BrowseGermplasm.CharacteristicModel;
import org.strasa.web.managegermplasm.view.pojos.SegmentExt;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
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
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Textbox;

public class BrowseSegment
{
	@Wire("#btnSearch")
	Button btnSearch;
	
	@Wire("#btnReset")
	Button btnReset;
	
	@Wire("#tbChromosome")
	Textbox tbChromosome;
	
//	@Wire("#cmbSearchChromosome")
//	Combobox cmbSearchChromosome;
	
	@Wire("#tbRecurrentParent")
	Textbox tbRecurrentParent;
	
	@Wire("#tbDonorParent")
	Textbox tbDonorParent;
	
	@Wire("#cmbOperationOnMarker")
	Combobox cmbOperationOnMarker;
	
	@Wire("#cmbOperationOnEstimatedLength")
	Combobox cmbOperationOnEstimatedLength;
	
	@Wire("#dbEstimatedLegnth")
	Doublebox dbEstimatedLegnth;
	
	@Wire("cmbOperationOnGeneticalStart")
	Combobox cmbOperationOnGeneticalStart;
	
	@Wire("#dbGeneticalStart")
	Double dbGeneticalStart;
	
	@Wire("cmbOperationOnGeneticalEnd")
	Combobox cmbOperationOnGeneticalEnd;
	
	@Wire("#dbGeneticalEnd")
	Double dbGeneticalEnd;
//	
//	@Wire("#cmbOperationPosition1")
//	Combobox cmbOpeartionPosition1;
//	
//	@Wire("#dbPosition1")
//	Doublebox dbPostion1;
//	
//	@Wire("#cmbOperationPosition2")
//	Combobox cmbOperationPosition2;
//	
//	@Wire("#dbPosition2")
//	Doublebox dbPosition2;
//	
//	@Wire("#cmbOperationPosition3")
//	Combobox cmbOperationPosition3;
//	
//	@Wire("#dbPosition3")
//	Doublebox dbPosition3;
//	
//	@Wire("#cmbOperationPosition4")
//	Combobox cmbOperationPosition4;
//	
//	@Wire("#dbPosition4")
//	Doublebox dbPosition4;
//	
	@Wire("#cmbOperationOnPhysicalStart")
	Combobox cmbOperationOnPhysicalStart;
	
	@Wire("#itPhysicalStart")
	Intbox itPysicalStart;
	
	@Wire("#cmbOperationOnPhysicalEnd")
	Combobox cmbOperationOnPhysicalEnd;
	
	@Wire("#itPhysicalEnd")
	Intbox itPhysicalEnd;
	
	private Component component;
	
//	private List<String> lstChromosome;
	private String chromosome;
//	private String recurrentParent;
	private String donorParent;
//	private List<String> lstStartMarker;
//	private List<String> lstEndMarker;
	private Double estimatedLength;
//	private Double position1;
//	private Double position2;
//	private Double position3;
//	private Double position4;
	private Double geneticalStart;
	private Double geneticalEnd;
	private Integer physicalStart;
	private Integer physicalEnd;
//	private String selectedChromosome;
	private String selectedStartMarker;
	private String selectedEndMarker;
	private List<String> lstOperation;
	private List<String> lstOperationMarker;
	private String segmentMarker;
	private String selectedOperationOnMarker;
	private String selectedOperationOnEstimatedLength;
	private String selectedOperationOnGeneticalStart;
	private String selectedOperationOnGeneticalEnd;
//	private String selectedOperationOnPosition1;
//	private String selectedOperationOnPosition2;
//	private String selectedOperationOnPosition3;
//	private String selectedOperationOnPosition4;
	private String selectedOperationOnPhysicalStart;
	private String selectedOperationOnPhysicalEnd;
	private List<IntrogressionLine> lstIntrogressionLine;
	private String searchResultLabel = "Search Result";
	private String searchSegmentResultLabel = "Search Segment Result";
	private SegmentExt segmentExt;
	private HashMap<String, Object> params = new HashMap<String, Object>();
	private HashMap<Integer, Tab> activeGermplasmIds;
	private Tab segmentSearchTab;
	private List<Segment> lstSegment;
	private List<SegmentExt> lstSegmentExt;
	private List<ConditionAndValue> lstSegmentSummary;
	
	@Init
	public void init(@ExecutionArgParam("segmentSearchTab")Tab segmentSearchTab)
	{
//		setSelectedChromosome("ALL");
		setSelectedOperationOnMarker("Include");
		setSelectedOperationOnEstimatedLength("=");
		setSelectedOperationOnGeneticalStart("=");
		setSelectedOperationOnGeneticalEnd("=");
//		setSelectedOperationOnPosition1("=");
//		setSelectedOperationOnPosition2("=");
//		setSelectedOperationOnPosition3("=");
//		setSelectedOperationOnPosition4("=");
		setSelectedOperationOnPhysicalStart("=");
		setSelectedOperationOnPhysicalEnd("=");
		activeGermplasmIds = new HashMap<Integer, Tab>();
		this.segmentSearchTab = segmentSearchTab;
		SegmentManagerImpl sManagerImpl = new SegmentManagerImpl();
		lstSegmentSummary = sManagerImpl.getSegmentSummary();
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		this.component = view;
	}
	
	@Command("submitSearchSegment")
	public void submitSearchSegment()
	{
		if(validateSearchInput() != null)
		{
			Messagebox.show(validateSearchInput(), "Error!", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		if(segmentExt == null)
			segmentExt = new SegmentExt();
		if(!params.isEmpty())
			params.clear();
		
		if(chromosome != null)
		{
			params.put("Chromosome", chromosome);
		}
//		if(selectedChromosome != null)
//		{
//			params.put("Chromosome", selectedChromosome);
//		}
//		if(recurrentParent != null)
//			params.put("RecurrentParent", recurrentParent);
		if(donorParent != null)
			params.put("DonorParent", donorParent);
		if(estimatedLength != null)
		{
			HashMap<String, Double> temp = new HashMap<String, Double>();
			if(selectedOperationOnEstimatedLength != null)
			{
				temp.put(selectedOperationOnEstimatedLength, estimatedLength);
				params.put("EstimatedLength", temp);
			} else
			{
				temp.put("=", estimatedLength);
				params.put("EstimatedLength", temp);
			}
		}
		if(segmentMarker != null)
		{
			HashMap<String, String> temp = new HashMap<String, String>();
			if(selectedOperationOnMarker != null)
			{
				temp.put(selectedOperationOnMarker, segmentMarker);
				params.put("SegmentMarker", temp);
			} else
			{
				temp.put("Include", segmentMarker);
				params.put("SegmentMarker", temp);
			}
		}
		
		if(geneticalStart != null)
		{
			HashMap<String, Double> temp = new HashMap<String, Double>();
			if(selectedOperationOnGeneticalStart != null)
			{
				temp.put(selectedOperationOnGeneticalStart, geneticalStart);
				params.put("GeneticalStart", temp);
			} else
			{
				temp.put("=", geneticalStart);
				params.put("GeneticalStart", temp);
			}
		}
		if(geneticalEnd != null)
		{
			HashMap<String, Double> temp = new HashMap<String, Double>();
			if(selectedOperationOnGeneticalEnd != null)
			{
				temp.put(selectedOperationOnGeneticalEnd, geneticalEnd);
				params.put("GeneticalEnd", temp);
			} else
			{
				temp.put("=", geneticalEnd);
				params.put("GeneticalEnd", temp);
			}
		}
//		if(position1 != null)
//		{
//			HashMap<String, Double> temp = new HashMap<String, Double>();
//			if(selectedOperationOnPosition1 != null)
//			{
//				temp.put(selectedOperationOnPosition1, position1);
//				params.put("Position1", temp);
//			} else
//			{
//				temp.put("=", position1);
//				params.put("Position1", temp);
//			}
//		}
//		if(position2 != null)
//		{
//			HashMap<String, Double> temp = new HashMap<String, Double>();
//			if(selectedOperationOnPosition2 != null)
//			{
//				temp.put(selectedOperationOnPosition2, position1);
//				params.put("Position2", temp);
//			} else
//			{
//				temp.put("=", position2);
//				params.put("Position2", temp);
//			}
//		}
//		if(position3 != null)
//		{
//			HashMap<String, Double> temp = new HashMap<String, Double>();
//			if(selectedOperationOnPosition3 != null)
//			{
//				temp.put(selectedOperationOnPosition3, position3);
//				params.put("Position3", temp);
//			} else
//			{
//				temp.put("=", position3);
//				params.put("Position3", temp);
//			}
//		}
//		if(position4 != null)
//		{
//			HashMap<String, Double> temp = new HashMap<String, Double>();
//			if(selectedOperationOnPosition4 != null)
//			{
//				temp.put(selectedOperationOnPosition4, position4);
//				params.put("Position4", temp);
//			} else
//			{
//				temp.put("=", position4);
//				params.put("Position4", temp);
//			}
//		}
		if(physicalStart != null)
		{
			HashMap<String, Integer> temp = new HashMap<String, Integer>();
			if(selectedOperationOnPhysicalStart != null)
			{
				temp.put(selectedOperationOnPhysicalStart, physicalStart);
				params.put("PhysicalStart", temp);
			} else
			{
				temp.put("=", physicalStart);
				params.put("PhysicalStart", temp);
			}
		}
		
		if(physicalEnd != null)
		{
			HashMap<String, Integer> temp = new HashMap<String, Integer>();
			if(selectedOperationOnPhysicalEnd != null)
			{
				temp.put(selectedOperationOnPhysicalEnd, physicalEnd);
				params.put("PhysicalEnd", temp);
			} else
			{
				temp.put("=", physicalEnd);
				params.put("PhysicalEnd", temp);
			}
		}
		
		IntrogressionLineManagerImpl ilManagerImpl = new IntrogressionLineManagerImpl();
		SegmentManagerImpl sManagerImpl = new SegmentManagerImpl();
		if(lstIntrogressionLine == null)
			lstIntrogressionLine = new ArrayList<IntrogressionLine>();
		if(lstSegment == null)
			lstSegment = new ArrayList<Segment>();
		lstIntrogressionLine.clear();
		lstSegment.clear();
		lstSegment = sManagerImpl.getSegmentByDynamicSQL(params);
		if(lstSegmentExt == null)
			lstSegmentExt = new ArrayList<SegmentExt>();
		lstSegmentExt.clear();
		for(Segment s : lstSegment)
		{
			SegmentExt se = new SegmentExt();
			se.setSegmentExtFromSegment(s);
			se.setHarborer(ilManagerImpl.getIntrogressionLineBySegment(s));
			lstSegmentExt.add(se);
		}
		lstIntrogressionLine = ilManagerImpl.getIntrogressionLineBySegmentParams(params);
//		component.getFellow("divSearchSegmentResult").setVisible(true);
		component.getFellow("divSearchIntrogressionLineResult").setVisible(false);
		setSearchSegmentResultLabel("Search Segment Result " + lstSegment.size() + " Row(s) Return!");
		setSearchResultLabel("Search Introgression Line Result " + lstIntrogressionLine.size() + " Row(s) Return!");
		btnReset.setVisible(true);
		BindUtils.postNotifyChange(null, null, this, "*");
	}
	
//	@NotifyChange({"chromosome","donorParent","segmentMarker","estimatedLength","geneticalStart","geneticalEnd","physicalStart","physicalEnd"})
	@Command("resetSearchSegment")
	public void resetSearchSegment()
	{
		setChromosome(null);
		setDonorParent(null);
		setSelectedOperationOnMarker("Include");
		setSegmentMarker(null);
		setSelectedOperationOnEstimatedLength("=");
		setEstimatedLength(null);
		setSelectedOperationOnGeneticalStart("=");
		setGeneticalStart(null);
		setSelectedOperationOnGeneticalEnd("=");
		setGeneticalEnd(null);
		setSelectedOperationOnPhysicalStart("=");
		setPhysicalStart(null);
		setSelectedOperationOnPhysicalEnd("=");
		setPhysicalEnd(null);
		lstIntrogressionLine.clear();
		lstSegment.clear();
//		component.getFellow("divSearchSegmentResult").setVisible(true);
		component.getFellow("divSearchIntrogressionLineResult").setVisible(false);
		setSearchSegmentResultLabel("Matching Segment Info!");
		setSearchResultLabel("Matching Introgression Line Info!");
		if(lstSegment != null && lstSegment.size() != 0)
			lstSegment.clear();
		if(lstSegmentExt != null && lstSegmentExt.size() != 0)
			lstSegmentExt.clear();
		btnReset.setVisible(false);
		BindUtils.postNotifyChange(null, null, this, "*");
	}
	
	@NotifyChange("*")
	@Command
	public void openIntrogressionLineDetailInSegment(@ContextParam(ContextType.COMPONENT)Component component,
			@ContextParam(ContextType.VIEW) Component view, @BindingParam("germplasmId")Integer id, @BindingParam("gname")String gname, @BindingParam("introgressionLine") IntrogressionLine introgressionLine)
	{
		Tabpanels tabPanels = (Tabpanels) component.getFellow("tabPanels");
		Tabs tabs = (Tabs) component.getFellow("tabs");
		Tabbox tabBox = (Tabbox) component.getFellow("tabBox");
		
		if(!activeGermplasmIds.containsKey(id))
		{
			final int gid = id;
			Tab newTab = new Tab();
			newTab.setLabel(gname);
			newTab.setClosable(true);
			newTab.addEventListener("onClose", new EventListener(){

				@Override
				public void onEvent(Event arg0) throws Exception {
					// TODO Auto-generated method stub
					activeGermplasmIds.remove(gid);
				}
				
			});
			Tabpanel newPanel = new Tabpanel();
			
			// initialize view after view construction
			Include studyInformationPage = new Include();
			studyInformationPage.setSrc("/user/browsesegment/introgressionlinedetail.zul");
			studyInformationPage.setParent(newPanel);
			studyInformationPage.setDynamicProperty("parentSource", "germplasm");
			studyInformationPage.setDynamicProperty("gname", gname);
			studyInformationPage.setDynamicProperty("introgressionLine", introgressionLine);
			
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
		segmentSearchTab.setSelected(true);
	}
	
	@NotifyChange("*")
	@GlobalCommand
	public void openIntrogressionLineDetail(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view,@BindingParam("germplasmId")Integer id,@BindingParam("gname")String gname, @BindingParam("introgressionLine") IntrogressionLine introgressionLine){

		Tabpanels tabPanels = (Tabpanels) component.getFellow("tabPanels");
		Tabs tabs = (Tabs) component.getFellow("tabs");
		Tabbox tabBox = (Tabbox) component.getFellow("tabBox");
		
		if(!activeGermplasmIds.containsKey(id)){
			final int gid=id;
			Tab newTab = new Tab();
			newTab.setLabel(gname);
			newTab.setClosable(true);
			newTab.addEventListener("onClose", new EventListener() {
				@Override
				public void onEvent(Event event) throws Exception {
					activeGermplasmIds.remove(gid);
				}
			});
			Tabpanel newPanel = new Tabpanel();

			//initialize view after view construction.
			Include studyInformationPage = new Include();
			studyInformationPage.setSrc("/user/browsesegment/introgressionlinedetail.zul");
			studyInformationPage.setParent(newPanel);
			studyInformationPage.setDynamicProperty("parentSource", "germplasm");
			studyInformationPage.setDynamicProperty("gname", gname);
			studyInformationPage.setDynamicProperty("introgressionLine", introgressionLine);

			tabPanels.appendChild(newPanel);
			tabs.appendChild(newTab);
			tabBox.setSelectedPanel(newPanel);

			newTab.setSelected(true);
			activeGermplasmIds.put(id, newTab);

		}
		else{
			Tab tab = activeGermplasmIds.get(id);
			tab.setSelected(true);
		}
		//		
		segmentSearchTab.setSelected(true);
		//		Include studyInformationPage = new Include();
		//		studyInformationPage.setSrc("/user/browsegermplasm/germplasmdetail.zul");
		//		studyInformationPage.setParent(studyDetailWindow);
		//		studyInformationPage.setDynamicProperty("gname", gname);

	}
	
	public String validateSearchInput()
	{
		String isExist = null;
		if((geneticalStart != null) && (geneticalEnd != null))
		{
			if(geneticalStart > geneticalEnd)
			{
				return isExist = "Genetical Position Input Invalid, Start Position should not be larger than End position!" ;
			}
		}
		if((physicalStart != null) && (physicalEnd != null))
		{
			if(physicalStart > physicalEnd)
			{
				return isExist = "Physical Position Input Invalid, Start Position should not be larger than End position!";
			}
		}
		return isExist;
	}
//	public List<String> getLstChromosome()
//	{
//		if (lstChromosome == null)
//			lstChromosome = new ArrayList<String>();
//		lstChromosome.clear();
//		for (Chromosome chr : Chromosome.values())
//		{
//			switch (chr)
//			{
//				case Chromosome_1:
//					lstChromosome.add("1");
//					break;
//				case Chromosome_2:
//					lstChromosome.add("2");
//					break;
//				case Chromosome_3:
//					lstChromosome.add("3");
//					break;
//				case Chromosome_4:
//					lstChromosome.add("4");	
//					break;
//				case Chromosome_5:
//					lstChromosome.add("5");
//					break;
//				case Chromosome_6:
//					lstChromosome.add("6");
//					break;
//				case Chromosome_7:
//					lstChromosome.add("7");	
//					break;
//				case Chromosome_8:
//					lstChromosome.add("8");
//					break;
//				case Chromosome_9:
//					lstChromosome.add("9");	
//					break;
//				case Chromosome_10:
//					lstChromosome.add("10");
//					break;
//				case Chromosome_11:
//					lstChromosome.add("11");
//					break;
//				case Chromosome_12:
//					lstChromosome.add("12");
//					break;
//				case Chromosome_ALL:
//					lstChromosome.add("ALL");
//					break;
//			}
//		}
//		return lstChromosome;
//	}
//	
//	public void setLstChromosome(List<String> lstChromosome)
//	{
//		this.lstChromosome = lstChromosome;
//	}
	
//	public String getRecurrentParent()
//	{
//		return recurrentParent;
//	}
//	
//	public void setRecurrentParent(String recurrentParent)
//	{
//		this.recurrentParent = recurrentParent;
//	}
	
	public String getDonorParent()
	{
		return donorParent;
	}
	
	public void setDonorParent(String donorParent)
	{
		this.donorParent = donorParent;
	}
	
//	public List<String> getLstStartMarker()
//	{
//		return lstStartMarker;
//	}
//	
//	public void setLstStartMarker(List<String> lstStartMarker)
//	{
//		this.lstStartMarker = lstStartMarker;
//	}
//	
//	public List<String> getLstEndMarker()
//	{
//		return lstEndMarker;
//	}
//	
//	public void setLstEndMarker(List<String> lstEndMarker)
//	{
//		this.lstEndMarker = lstEndMarker;
//	}
//	
	public Double getEstimatedLength()
	{
		return estimatedLength;
	}
	
	public void setEstimatedLength(Double estimatedLength)
	{
		this.estimatedLength = estimatedLength;
	}
	
//	public Double getPosition1()
//	{
//		return position1;
//	}
//	
//	public void setPosition1(Double position1)
//	{
//		this.position1 = position1;
//	}
//	
//	public Double getPosition2()
//	{
//		return position2;
//	}
//	
//	public void setPosition2(Double position2)
//	{
//		this.position2 = position2;
//	}
//	
//	public Double getPosition3()
//	{
//		return position3;
//	}
//	
//	public void setPosition3(Double position3)
//	{
//		this.position3 = position3;
//	}
//	
//	public Double getPosition4()
//	{
//		return position4;
//	}
//	
//	public void setPosition4(Double position4)
//	{
//		this.position4 = position4;
//	}
	
	public Integer getPhysicalStart()
	{
		return physicalStart;
	}
	
	public void setPhysicalStart(Integer physicalStart)
	{
		this.physicalStart = physicalStart;
	}
	
	public Integer getPhysicalEnd()
	{
		return physicalEnd;
	}
	
	public void setPhysicalEnd(Integer physicalEnd)
	{
		this.physicalEnd = physicalEnd;
	}
	
//	public String getSelectedChromosome()
//	{
//		return selectedChromosome;
//	}
//	
//	public void setSelectedChromosome(String selectedChromosome)
//	{
//		this.selectedChromosome = selectedChromosome;
//	}
	
	public String getSelectedStartMarker()
	{
		return selectedStartMarker;
	}
	
	public void setSelectedStartMarker(String selectedStartMarker)
	{
		this.selectedStartMarker = selectedStartMarker;
	}
	
	public String getSelectedEndMarker()
	{
		return selectedEndMarker;
	}
	
	public void setSelectedEndMarker(String selectedEndMarker)
	{
		this.selectedEndMarker = selectedEndMarker;
	}
	
	public List<String> getLstOperation()
	{
		if (lstOperation == null)
			lstOperation = new ArrayList<String>();
		lstOperation.clear();
		lstOperation.add(">");
		lstOperation.add(">=");
		lstOperation.add("=");
		lstOperation.add("<");
		lstOperation.add("<=");
		return lstOperation;
	}
	
	public void setLstOperation(List<String> lstOperation)
	{
		this.lstOperation = lstOperation;
	}
	
	public String getSelectedOperationOnEstimatedLength()
	{
		return selectedOperationOnEstimatedLength;
	}
	
	public void setSelectedOperationOnEstimatedLength(
	        String selectedOperationOnEstimatedLength)
	{
		this.selectedOperationOnEstimatedLength = selectedOperationOnEstimatedLength;
	}
	
//	public String getSelectedOperationOnPosition1()
//	{
//		return selectedOperationOnPosition1;
//	}
//	
//	public void setSelectedOperationOnPosition1(
//	        String selectedOperationOnPosition1)
//	{
//		this.selectedOperationOnPosition1 = selectedOperationOnPosition1;
//	}
//	
//	public String getSelectedOperationOnPosition2()
//	{
//		return selectedOperationOnPosition2;
//	}
//	
//	public void setSelectedOperationOnPosition2(
//	        String selectedOperationOnPosition2)
//	{
//		this.selectedOperationOnPosition2 = selectedOperationOnPosition2;
//	}
//	
//	public String getSelectedOperationOnPosition3()
//	{
//		return selectedOperationOnPosition3;
//	}
//	
//	public void setSelectedOperationOnPosition3(
//	        String selectedOperationOnPosition3)
//	{
//		this.selectedOperationOnPosition3 = selectedOperationOnPosition3;
//	}
//	
//	public String getSelectedOperationOnPosition4()
//	{
//		return selectedOperationOnPosition4;
//	}
//	
//	public void setSelectedOperationOnPosition4(
//	        String selectedOperationOnPosition4)
//	{
//		this.selectedOperationOnPosition4 = selectedOperationOnPosition4;
//	}
	
	public String getSelectedOperationOnPhysicalStart()
	{
		return selectedOperationOnPhysicalStart;
	}
	
	public void setSelectedOperationOnPhysicalStart(
	        String selectedOperationOnPhysicalStart)
	{
		this.selectedOperationOnPhysicalStart = selectedOperationOnPhysicalStart;
	}
	
	public String getSelectedOperationOnPhysicalEnd()
	{
		return selectedOperationOnPhysicalEnd;
	}
	
	public void setSelectedOperationOnPhysicalEnd(
	        String selectedOperationOnPhysicalEnd)
	{
		this.selectedOperationOnPhysicalEnd = selectedOperationOnPhysicalEnd;
	}
	
	public List<IntrogressionLine> getLstIntrogressionLine()
	{
		return lstIntrogressionLine;
	}
	
	public void setLstIntrogressionLine(
	        List<IntrogressionLine> lstIntrogressionLine)
	{
		this.lstIntrogressionLine = lstIntrogressionLine;
	}
	
	public List<String> getLstOperationMarker()
	{
		if (lstOperationMarker == null)
			lstOperationMarker = new ArrayList<String>();
		lstOperationMarker.clear();
		lstOperationMarker.add("Include");
		lstOperationMarker.add("Exclude");
		return lstOperationMarker;
	}
	
	public void setLstOperationMarker(List<String> lstOperationMarker)
	{
		this.lstOperationMarker = lstOperationMarker;
	}
	
	public String getSelectedOperationOnMarker()
	{
		return selectedOperationOnMarker;
	}
	
	public void setSelectedOperationOnMarker(String selectedOperationMarker)
	{
		this.selectedOperationOnMarker = selectedOperationMarker;
	}
	
	public String getSegmentMarker()
	{
		return segmentMarker;
	}
	
	public void setSegmentMarker(String segmentMarker)
	{
		this.segmentMarker = segmentMarker;
	}
	
	public String getSearchResultLabel()
	{
		return searchResultLabel;
	}
	
	public void setSearchResultLabel(String searchResultLabel)
	{
		this.searchResultLabel = searchResultLabel;
	}

	public Double getGeneticalStart()
	{
		return geneticalStart;
	}

	public void setGeneticalStart(Double geneticalStart)
	{
		this.geneticalStart = geneticalStart;
	}

	public Double getGeneticalEnd()
	{
		return geneticalEnd;
	}

	public void setGeneticalEnd(Double geneticalEnd)
	{
		this.geneticalEnd = geneticalEnd;
	}

	public String getSelectedOperationOnGeneticalStart()
	{
		return selectedOperationOnGeneticalStart;
	}

	public void setSelectedOperationOnGeneticalStart(
	        String selectedOperationOnGeneticalStart)
	{
		this.selectedOperationOnGeneticalStart = selectedOperationOnGeneticalStart;
	}

	public String getSelectedOperationOnGeneticalEnd()
	{
		return selectedOperationOnGeneticalEnd;
	}

	public void setSelectedOperationOnGeneticalEnd(
	        String selectedOperationOnGeneticalEnd)
	{
		this.selectedOperationOnGeneticalEnd = selectedOperationOnGeneticalEnd;
	}

	public SegmentExt getSegmentExt()
	{
		return segmentExt;
	}

	public void setSegmentExt(SegmentExt segmentExt)
	{
		this.segmentExt = segmentExt;
	}

	public HashMap<String, Object> getParams()
	{
		return params;
	}

	public void setParams(HashMap<String, Object> params)
	{
		this.params = params;
	}

	public String getChromosome()
	{
		return chromosome;
	}

	public void setChromosome(String chromosome)
	{
		this.chromosome = chromosome;
	}

	public List<Segment> getLstSegment()
	{
		return lstSegment;
	}

	public void setLstSegment(List<Segment> lstSegment)
	{
		this.lstSegment = lstSegment;
	}

	public String getSearchSegmentResultLabel()
	{
		return searchSegmentResultLabel;
	}

	public void setSearchSegmentResultLabel(String searchSegmentResultLabel)
	{
		this.searchSegmentResultLabel = searchSegmentResultLabel;
	}

	public List<ConditionAndValue> getLstSegmentSummary()
	{
		return lstSegmentSummary;
	}

	public void setLstSegmentSummary(List<ConditionAndValue> lstSegmentSummary)
	{
		this.lstSegmentSummary = lstSegmentSummary;
	}

	public List<SegmentExt> getLstSegmentExt() {
		return lstSegmentExt;
	}

	public void setLstSegmentExt(List<SegmentExt> lstSegmentExt) {
		this.lstSegmentExt = lstSegmentExt;
	}
	
	
}
