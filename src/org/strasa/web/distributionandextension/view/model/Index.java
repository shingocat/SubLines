package org.strasa.web.distributionandextension.view.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.strasa.middleware.model.Program;
import org.strasa.middleware.model.Project;
import org.strasa.middleware.model.StudyType;
import org.strasa.web.utilities.FileUtilities;
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
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Window;

public class Index {	
	//public static ArrayList<Integer> activeStudyIds = new ArrayList<Integer>();
	private static HashMap<String,Tab> activeStudyIds;

	public static void removeFromTab(int studyId){
		activeStudyIds.remove(studyId);
	}

	@AfterCompose
	public void init(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view){
		activeStudyIds = new HashMap<String,Tab>();

		//add detail tab
		Tabpanels tabPanels = (Tabpanels) component.getFellow("tabPanels");
		Tabs tabs = (Tabs) component.getFellow("tabs");
		Tabbox tabBox = (Tabbox) component.getFellow("tabBox");

		Tabpanel newPanel = new Tabpanel();
		Tab newTab = new Tab();
		newTab.setLabel("Summary");
		newTab.setImage("/images/summary.png");

		//initialize view after view construction.
		Include studyInformationPage = new Include();
		studyInformationPage.setParent(newPanel);
		//		studyInformationPage.setDynamicProperty("detailTab", newTab2);
		studyInformationPage.setSrc("/user/distributionandextension/distributionandextensionbrowse.zul");
		tabPanels.appendChild(newPanel);
		tabs.appendChild(newTab);
		tabBox.setSelectedPanel(newPanel);

	}

	@GlobalCommand
	public void exportGrid(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view, @BindingParam("gridId")String gridId, @BindingParam("grid")Grid grid){
//		Grid grid = (Grid) component.getFellow(gridId);
		Columns columns = grid.getColumns();
		Rows rows = grid.getRows();

		FileUtilities.exportGridData(columns, rows, gridId);

	}
	

	@NotifyChange("*")
	@GlobalCommand("openExtensionDataDetail")
	public void openExtensionDataDetail(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view,@BindingParam("function") String function, @BindingParam("summaryModel")SummaryModel each, @BindingParam("germplasmName")String germplasmName){
		
		Tabpanels tabPanels = (Tabpanels) component.getFellow("tabPanels");
		Tabs tabs = (Tabs) component.getFellow("tabs");
		Tabbox tabBox = (Tabbox) component.getFellow("tabBox");
		
		Tabpanel newPanel2 = new Tabpanel();
		Tab newTab2 = new Tab();
		newTab2.setClosable(true);
		
		if(!germplasmName.isEmpty()) newTab2.setLabel(germplasmName);
		else newTab2.setLabel("Germplasm Detail");
		
		Include studyInformationPage2 = new Include();
		studyInformationPage2.setParent(newPanel2);
		studyInformationPage2.setSrc("/user/distributionandextension/distributionandextensiondetail.zul");
		studyInformationPage2.setDynamicProperty("function", function);
		studyInformationPage2.setDynamicProperty("summaryModel", each);
		studyInformationPage2.setDynamicProperty("germplasmName", germplasmName);
		
		tabPanels.appendChild(newPanel2);
		tabs.appendChild(newTab2);
		tabBox.setSelectedPanel(newPanel2);

	}
}
