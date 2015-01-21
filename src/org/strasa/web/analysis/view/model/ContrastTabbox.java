package org.strasa.web.analysis.view.model;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

public class ContrastTabbox {
	Tabbox tabbox;
	Tabs tabs;
	Tabpanels tabpanels;
	
	@AfterCompose
	public void afterComposer(@ContextParam(ContextType.COMPONENT) final Component component,
			@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("TabNames") String[] tabnames)
	{
		tabbox = (Tabbox) component.getFellow("tabbox");
		tabs = (Tabs) component.getFellow("tabs");
		tabpanels = (Tabpanels) component.getFellow("tabpanels");
		for(String s : tabnames)
		{
			System.out.println("tab name " + s);
			//setting tab
			Tab tab = new Tab();
			tab.setLabel(s);
			tabs.appendChild(tab);
			//setting tabpanel
			Tabpanel tabpanel =new Tabpanel();
			tabpanel.setHeight("350px");
			Include inc = new Include();
			inc.setSrc("/user/analysis/contrast.zul");
			inc.setParent(tabpanel);
			tabpanels.appendChild(tabpanel);
		}
	}
}
