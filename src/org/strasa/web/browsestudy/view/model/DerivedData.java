package org.strasa.web.browsestudy.view.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.strasa.middleware.manager.BrowseStudyManagerImpl;
import org.strasa.middleware.manager.StudyDataColumnManagerImpl;
import org.strasa.middleware.manager.StudyDataSetManagerImpl;
import org.strasa.middleware.manager.StudyFileManagerImpl;
import org.strasa.middleware.manager.StudyManagerImpl;
import org.strasa.middleware.model.StudyDataColumn;
import org.strasa.middleware.model.StudyDataSet;
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
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

public class DerivedData {
	StudyDataSetManagerImpl dataSetMan;

	public DerivedData() {
		// TODO Auto-generated constructor stub
	}

	@AfterCompose
	public void init(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("studyid") Integer studyId, @ExecutionArgParam("dataset") Integer dataset){

		dataSetMan = new StudyDataSetManagerImpl();

		Tabbox tabBox = (Tabbox) component.getFellow("dataTabBox");
		Tabpanels tabPanels = (Tabpanels) component.getFellow("dataPanels");
		Tabs tabs = (Tabs) component.getFellow("dataTabs");

		List<StudyDataSet> datasets = dataSetMan.getDataSetsByStudyId(studyId);

		for(StudyDataSet data : datasets){
			StudyDataColumnManagerImpl mgr= new StudyDataColumnManagerImpl();
			if(!mgr.getStudyDataColumnByStudyId(studyId, "dd", data.getId()).isEmpty()){
				Tabpanel newPanel = new Tabpanel();
				Tab newTab = new Tab();
				newTab.setLabel(data.getTitle());

				//initialize view after view construction.
				Include studyInformationPage = new Include();
				studyInformationPage.setParent(newPanel);
				studyInformationPage.setDynamicProperty("dataType", "dd");
				studyInformationPage.setDynamicProperty("studyId", studyId);
				studyInformationPage.setDynamicProperty("dataset", data.getId());
				studyInformationPage.setSrc("/user/browsestudy/datasettab.zul");
				tabPanels.appendChild(newPanel);
				tabs.appendChild(newTab);
				//		tabBox.setSelectedPanel(newPanel);
				tabBox.setSelectedIndex(newTab.getIndex());
			}
		}



	}

}