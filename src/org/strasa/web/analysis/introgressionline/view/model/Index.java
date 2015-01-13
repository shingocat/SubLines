package org.strasa.web.analysis.introgressionline.view.model;

import org.analysis.rserve.manager.RServeManager;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;

public class Index {
	private Tab resultTab;
	private RServeManager rServeManager;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(component, this, false);
	}
	
	@GlobalCommand("launchIntrogressionLine")
	public void launchIntrogressionLine(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view)
	{
		Tabpanel specificationsPanel = (Tabpanel) component.getFellow("specificationsPanel");
		specificationsPanel.getChildren().get(0).detach();
		
		Include specificationPage = new Include();
		specificationPage.setParent(specificationsPanel);
		specificationPage.setSrc("/user/analysis/introgressionline/specifications.zul");
	}
}
