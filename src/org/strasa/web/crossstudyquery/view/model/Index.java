package org.strasa.web.crossstudyquery.view.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.strasa.middleware.model.Program;
import org.strasa.middleware.model.Project;
import org.strasa.middleware.model.StudyType;
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
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Window;

public class Index {	
//public static ArrayList<Integer> activeStudyIds = new ArrayList<Integer>();
private static HashMap<String,Tab> activeStudyIds;

private Tab resultTab;

	public static void removeFromTab(int studyId){
		activeStudyIds.remove(studyId);
	}

	 @AfterCompose
	public void init(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view){
		activeStudyIds = new HashMap<String,Tab>();
		
		Tabpanels tabPanels = (Tabpanels) component.getFellow("tabPanels");
		Tabs tabs = (Tabs) component.getFellow("tabs");
		Tabbox tabBox = (Tabbox) component.getFellow("tabBox");
		
		Tabpanel newPanel = new Tabpanel();
		Tab newTab = new Tab();
		newTab.setImage("/images/Search16a.png");
		newTab.setLabel("Query");
		
		//initialize view after view construction.
		Include studyInformationPage = new Include();
		studyInformationPage.setParent(newPanel);
		studyInformationPage.setDynamicProperty("parentSource", "crossstudy");
		studyInformationPage.setDynamicProperty("crossstudySearchTab", newTab);
		studyInformationPage.setSrc("/user/crossstudyquery/crossstudyquery.zul");
		tabPanels.appendChild(newPanel);
		tabs.appendChild(newTab);
		tabBox.setSelectedPanel(newPanel);
		
		tabBox.setSelectedIndex(0);
	}
	
	 @NotifyChange
		@GlobalCommand
		public void openGermplasmDetailInAcrossStudy(@ContextParam(ContextType.COMPONENT) Component component,
				@ContextParam(ContextType.VIEW) Component view, @BindingParam("gname")  String gname){
			
			Tabpanels tabPanels = (Tabpanels) component.getFellow("tabPanels");
			Tabs tabs = (Tabs) component.getFellow("tabs");
			Tabbox tabBox = (Tabbox) component.getFellow("tabBox");
			
			if(!activeStudyIds.containsKey(gname)){
			final String id=gname;
			Tab newTab = new Tab();
			newTab.setImage("/images/Germplasm16.png");
			newTab.setLabel(gname);
			newTab.setClosable(true);
			newTab.addEventListener("onClose", new EventListener() {
				@Override
				public void onEvent(Event event) throws Exception {
					activeStudyIds.remove(id);
				}
			});
			Tabpanel newPanel = new Tabpanel();
			
			//initialize view after view construction.
			Include studyInformationPage = new Include();
			studyInformationPage.setSrc("/user/browsegermplasm/germplasmdetail.zul");
			studyInformationPage.setDynamicProperty("parentSource", "crossstudy");
			studyInformationPage.setDynamicProperty("gname", gname);
			studyInformationPage.setParent(newPanel);
			
			tabPanels.appendChild(newPanel);
			tabs.appendChild(newTab);
			tabBox.setSelectedPanel(newPanel);
			
			newTab.setSelected(true);
			activeStudyIds.put(gname, newTab);
			
			}
			else{
			Tab tab = activeStudyIds.get(gname);
			tab.setSelected(true);
			}
		}
	 
	 @NotifyChange
		@GlobalCommand
		public void openStudyDetailInAcrossStudy(@ContextParam(ContextType.COMPONENT) Component component,
				@ContextParam(ContextType.VIEW) Component view,@BindingParam("studyid")  Integer studyId, @BindingParam("studyName")String studyName){
			
			Tabpanels tabPanels = (Tabpanels) component.getFellow("tabPanels");
			Tabs tabs = (Tabs) component.getFellow("tabs");
			Tabbox tabBox = (Tabbox) component.getFellow("tabBox");
			
			if(!activeStudyIds.containsKey(Integer.toString(studyId))){
			final int id=studyId;
			Tab newTab = new Tab();
			newTab.setImage("/images/study16.png");
			newTab.setLabel(studyName);
			newTab.setClosable(true);
			newTab.addEventListener("onClose", new EventListener() {
				@Override
				public void onEvent(Event event) throws Exception {
					activeStudyIds.remove(Integer.toString(id));
				}
			});
			Tabpanel newPanel = new Tabpanel();
			
			//initialize view after view construction.
			Include studyInformationPage = new Include();
			studyInformationPage.setParent(newPanel);
			studyInformationPage.setSrc("/user/browsestudy/studyinformation.zul");
			studyInformationPage.setDynamicProperty("parentSource", "crossstudy");
			studyInformationPage.setDynamicProperty("studyId", studyId);
			tabPanels.appendChild(newPanel);
		
			int index=tabs.getChildren().size();
			tabs.appendChild(newTab);
			tabBox.setSelectedIndex(index);
			
			newTab.setSelected(true);
			activeStudyIds.put(Integer.toString(studyId), newTab);
			
			}
			else{
			Tab tab = activeStudyIds.get(Integer.toString(studyId));
			tab.setSelected(true);
			}
		}

}
