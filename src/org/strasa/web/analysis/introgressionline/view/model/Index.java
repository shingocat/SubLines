package org.strasa.web.analysis.introgressionline.view.model;

import java.io.File;
import java.util.HashMap;

import org.analysis.rserve.manager.ILRserveManager;
import org.analysis.rserve.manager.PyramidedLineRserveManager;
import org.analysis.rserve.manager.RServeManager;
import org.strasa.middleware.util.StringConstants;
import org.strasa.web.analysis.view.model.ILAnalysisModel;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

public class Index {
	private Tab resultTab;
	private Tabpanel specificationsPanel;
	private ILRserveManager manager;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(component, this, false);
		specificationsPanel = (Tabpanel) component.getFellow("specificationsPanel");
		if(specificationsPanel.getChildren().isEmpty())
		{	
			Include inc = new Include();
			inc.setParent(specificationsPanel);
			inc.setSrc("/user/analysis/introgressionline/specifications.zul");
		}
	}
	
	@GlobalCommand("launchIntrogressionLine")
	public void launchIntrogressionLine(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view)
	{
		if(!specificationsPanel.getChildren().isEmpty())
			specificationsPanel.getChildren().get(0).detach();
		Include inc = new Include();
		inc.setParent(specificationsPanel);
		inc.setSrc("/user/analysis/introgressionline/specifications.zul");
	}
	
	@GlobalCommand("displayILResult")
	@NotifyChange("*")
	public void displayILResult(
			@ContextParam(ContextType.COMPONENT) Component component, 
			@ContextParam(ContextType.VIEW)Component view, 
			@BindingParam("Model") ILAnalysisModel model)
	{
		manager = new ILRserveManager();
		HashMap<String, String> outcomes = manager.doAnalysis(model);
		if(outcomes.get("Success").equalsIgnoreCase("TRUE"))
		{
			Messagebox.show(outcomes.get("Message"), "Note", Messagebox.OK, Messagebox.NONE);
		} else
		{
			Messagebox.show(outcomes.get("Message"), "Note", Messagebox.OK, Messagebox.NONE);
		}
		
		Tabpanels tabpanels = (Tabpanels) component.getFellow("tabPanels");
		Tabs tabs = (Tabs) component.getFellow("tabs");
		
		Tabpanel newpanel = new Tabpanel();
		Tab newtab = new Tab();
	
		newtab.setLabel(new File(model.getOutFileName()).getParentFile().getName());
		newtab.setClosable(true);
		
//		Initialize view after view construction
		Include inc = new Include();
		inc.setParent(newpanel);
		inc.setDynamicProperty("OutputFolderPath", 
				model.getResultFolderPath().replace(StringConstants.BSLASH,StringConstants.FSLASH));
		inc.setSrc("/user/analysis/resultviewer.zul");
		
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
