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
		if(args.containsKey("LevelNames"))
		{
			List<String> tabnames = (List<String>) args.get("LevelNames");
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
				pasedArgs.put("LevelName", s);
				pasedArgs.put("UploadedFileFolderPath", args.get("UploadedFileFolderPath"));
				pasedArgs.put("Levels", ((HashMap<String, List<String>>)args.get("Levels")).get(s));
				pasedArgs.put("Factor", args.get("Factor"));
				pasedArgs.put("Type", args.get("Type"));
				pasedArgs.put("DefaultContrastFile", args.get("DefaultContrastFile"));
				inc.setDynamicProperty("Arguments", pasedArgs);
				if(args.get("Type") ==  "File")
					inc.setSrc("/user/analysis/contrast.zul");
				else if(args.get("Type") == "Manual")
					inc.setSrc("/user/analysis/contrastmanually.zul");
				else if(args.get("Type") == "Bi-Genes" || args.get("Type") == "Tri-Genes"
						|| args.get("Type") == "Quadra-Genes")
					inc.setSrc("/user/analysis/contrastdefault.zul");

				inc.setParent(tabpanel);
				tabpanels.appendChild(tabpanel);
			}
		}
	}
}
