package org.strasa.web.analysis.sssl.view.model;

import java.io.File;
import java.util.HashMap;

import org.analysis.rserve.manager.RServeManager;
import org.analysis.rserve.manager.SSSLRserveManager;
import org.strasa.web.analysis.view.model.SSSLAnalysisModel;
import org.strasa.web.analysis.view.model.SingleSiteAnalysisModel;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

public class Index {
	private Tab resultTab;
	private SSSLRserveManager ssslRServeManager;
	private Tabpanel specificationsPanel;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(component, this, false);
		specificationsPanel = (Tabpanel) component.getFellow("specificationsPanel");
		//set the initiated specifications tabpanel
		if(specificationsPanel.getFirstChild() == null)
		{
			Include inc = new Include();
			inc.setSrc("/user/analysis/sssl/specifications.zul");
			inc.setParent(specificationsPanel);
		}
	}


	@NotifyChange("*")
	@GlobalCommand("launchSSSL")
	public void launchSSSL(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view) {
		if(specificationsPanel.getFirstChild() != null)
			specificationsPanel.getFirstChild().detach();
		
		Include specificationPage = new Include();
		specificationPage.setParent(specificationsPanel);
		specificationPage.setSrc("/user/analysis/sssl/specifications.zul");
	}

	@GlobalCommand("displaySSSLResult")
	@NotifyChange("*")
	public void displaySSSLResult(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view, @BindingParam("ssslModel") SSSLAnalysisModel ssslModel) {
		System.out.println("running displaySSSLResult method...");
		ssslRServeManager = new SSSLRserveManager();
		HashMap<String, String> outcomes = ssslRServeManager.doAnalysis(ssslModel);
		if(outcomes.get("Success").equalsIgnoreCase("TRUE"))
		{
			Messagebox.show(outcomes.get("Message"), "Note", Messagebox.OK, Messagebox.NONE);
		} else
		{
			Messagebox.show(outcomes.get("Message"), "Note", Messagebox.OK, Messagebox.NONE);
		}
		Tabpanels tabPanels = (Tabpanels) component.getFellow("tabPanels");
		Tabs tabs = (Tabs) component.getFellow("tabs");

		Tabpanel newPanel = new Tabpanel();
		Tab newTab = new Tab();
		newTab.setLabel(new File(ssslModel.getOutFileName()).getParentFile().getName());
		newTab.setClosable(true);
		
		//initialize view after view construction.
		Include studyInformationPage = new Include();
		studyInformationPage.setParent(newPanel);
		studyInformationPage.setDynamicProperty("outputFolderPath", ssslModel.getResultFolderPath().replaceAll("\\\\", "/"));
		studyInformationPage.setSrc("/user/analysis/resultviewer.zul");

		tabPanels.appendChild(newPanel);
		tabs.appendChild(newTab);
	}

	public Tab getResultTab() {
		return resultTab;
	}

	public void setResultTab(Tab resultTab) {
		this.resultTab = resultTab;
	}
}
