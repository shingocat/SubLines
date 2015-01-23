package org.strasa.web.analysis.view.model;

import java.util.HashMap;
import java.util.List;

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
			@ExecutionArgParam("Arguments") HashMap<String, Object> args)
	{
		tabbox = (Tabbox) component.getFellow("tabbox");
		tabs = (Tabs) component.getFellow("tabs");
		tabpanels = (Tabpanels) component.getFellow("tabpanels");
		if(args.containsKey("EnvNames"))
		{
			List<String> tabnames = (List<String>) args.get("EnvNames");
			System.out.println("Tabnames " + tabnames);
			for(String s : tabnames)
			{
				//setting tab
				Tab tab = new Tab();
				tab.setLabel(s);
				tabs.appendChild(tab);
				//setting tabpanel
				Tabpanel tabpanel =new Tabpanel();
				tabpanel.setHeight("350px");
				Include inc = new Include();
				HashMap<String, Object> pasedArgs = new HashMap<String, Object>();
				pasedArgs.put("EnvName", s);
				pasedArgs.put("UploadedFileFolderPath", args.get("UploadedFileFolderPath"));
				pasedArgs.put("GenosOnEnv", ((HashMap<String, List<String>>)args.get("GenosOnEnv")).get(s));
				inc.setDynamicProperty("Arguments", pasedArgs);
				inc.setSrc("/user/analysis/contrast.zul");
				inc.setParent(tabpanel);
				tabpanels.appendChild(tabpanel);
			}
		}
	}
}
