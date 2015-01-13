/**
 * Builder: Mao Qin
 * Email: mqin@ymail.com
 * Date: 2014 - Jul 5, 2014
 * Project: StrasaWeb
 * Package: org.strasa.web.segmentquery.view.model
 * Name: Index.java
 */
package org.strasa.web.segmentquery.view.model;

import java.util.HashMap;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

public class Index
{
	private static HashMap<String, Tab> activeStudyIds;
	
	private Tab resultTab;
	
	public static void removeFromTab(int studyId)
	{
		activeStudyIds.remove(studyId);
	}
	
	@AfterCompose
	public void init(@ContextParam(ContextType.COMPONENT) Component component,
	        @ContextParam(ContextType.VIEW) Component view)
	{
		activeStudyIds = new HashMap<String, Tab>();
		
		Tabpanels tabPanels = (Tabpanels) component.getFellow("tabPanels");
		Tabs tabs = (Tabs) component.getFellow("tabs");
		Tabbox tabBox = (Tabbox) component.getFellow("tabBox");
		
		Tabpanel newPanel = new Tabpanel();
		Tab newTab = new Tab();
		newTab.setImage("/images/Search16a.png");
		newTab.setLabel("Search");
		
		//initialize view after view construction.
		Include studyInformationPage = new Include();
		studyInformationPage.setParent(newPanel);
		studyInformationPage.setDynamicProperty("segmentSearchTab", newTab);
		studyInformationPage
		        .setSrc("/user/browsesegment/browsesegment.zul");
		tabPanels.appendChild(newPanel);
		tabs.appendChild(newTab);
		tabBox.setSelectedPanel(newPanel);
		
		tabBox.setSelectedIndex(0);
	}
	
	@NotifyChange
	@GlobalCommand
	public void openStudyDetailInGermplasm(
	        @ContextParam(ContextType.COMPONENT) Component component,
	        @ContextParam(ContextType.VIEW) Component view,
	        @BindingParam("studyid") Integer studyId,
	        @BindingParam("studyName") String studyName)
	{
		
		Tabpanels tabPanels = (Tabpanels) component.getFellow("tabPanels");
		Tabs tabs = (Tabs) component.getFellow("tabs");
		Tabbox tabBox = (Tabbox) component.getFellow("tabBox");
		
		if (!activeStudyIds.containsKey(Integer.toString(studyId)))
		{
			final int id = studyId;
			Tab newTab = new Tab();
			newTab.setImage("/images/study16.png");
			newTab.setLabel(studyName);
			newTab.setClosable(true);
			newTab.addEventListener("onClose", new EventListener()
			{
				@Override
				public void onEvent(Event event) throws Exception
				{
					activeStudyIds.remove(Integer.toString(id));
				}
			});
			Tabpanel newPanel = new Tabpanel();
			
			//initialize view after view construction.
			Include studyInformationPage = new Include();
			studyInformationPage.setParent(newPanel);
			studyInformationPage
			        .setSrc("/user/browsestudy/studyinformation.zul");
			studyInformationPage
			        .setDynamicProperty("parentSource", "segment");
			studyInformationPage.setDynamicProperty("studyId", studyId);
			tabPanels.appendChild(newPanel);
			
			int index = tabs.getChildren().size();
			tabs.appendChild(newTab);
			tabBox.setSelectedIndex(index);
			
			newTab.setSelected(true);
			activeStudyIds.put(Integer.toString(studyId), newTab);
			
		} else
		{
			Tab tab = activeStudyIds.get(Integer.toString(studyId));
			tab.setSelected(true);
		}
	}
}
