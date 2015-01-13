package org.strasa.web.analysis.pyramidedline.view.model;

import java.io.File;

import org.analysis.rserve.manager.PyramidedLineRserveManager;
import org.analysis.rserve.manager.RServeManager;
import org.strasa.middleware.util.StringConstants;
import org.strasa.web.analysis.view.model.PyramidedLineAnalysisModel;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

public class Index {
	
	private Tab resultTab;
	private RServeManager rServeManager;
	private PyramidedLineRserveManager manager;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(component, this, false);
	}
	
	@NotifyChange("*")
	@GlobalCommand("launchPyramidedLine")
	public void launchPyramidedLine(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view)
	{
		Tabpanel specificationsPanel = (Tabpanel) component.getFellow("specificationsPanel");
		specificationsPanel.getChildren().get(0).detach();
		
		Include specificationPage = new Include();
		specificationPage.setParent(specificationsPanel);
		specificationPage.setSrc("/user/analysis/pyramidedline/specifications.zul");
	}
	
	@GlobalCommand("displayPyramidedLineResult")
	@NotifyChange("*")
	public void displayPyramidedLineResult(@ContextParam(ContextType.COMPONENT) Component component, 
			@ContextParam(ContextType.VIEW)Component view, @BindingParam("model") PyramidedLineAnalysisModel model)
	{
		System.out.println("Running display pyramided line result method...");
		manager = new PyramidedLineRserveManager();
		manager.doAnalysis(model);
		
		Tabpanels tabpanels = (Tabpanels) component.getFellow("tabPanels");
		Tabs tabs = (Tabs) component.getFellow("tabs");
		
		Tabpanel newpanel = new Tabpanel();
		Tab newtab = new Tab();
		
		newtab.setLabel(new File(model.getOutFileName()).getParentFile().getName());
		newtab.setClosable(true);
		
//		Initialize view after view construction
		Include studyInformationPage = new Include();
		studyInformationPage.setParent(newpanel);
		studyInformationPage.setDynamicProperty("outputFolderPath", model.getResultFolderPath().replace(StringConstants.BSLASH,StringConstants.FSLASH));
		studyInformationPage.setSrc("/user/analysis/resultviewer.zul");
		
		tabpanels.appendChild(newpanel);
		tabs.appendChild(newtab);
	
	}
	
	public Tab getResultTab()
	{
		return resultTab;
	}
	
	public void setResultTab(Tab resultTab)
	{
		this.resultTab = resultTab;
	}
}
