package applications2;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkmax.zul.Navbar;
import org.zkoss.zkmax.zul.Navitem;
import org.zkoss.zul.A;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;

public class SidebarComposer extends SelectorComposer<Component>  {

	@Wire
	Hlayout main;
	@Wire
	Div sidebar;
	@Wire
	Div contentui;
	@Wire
	Navbar navbar;
	@Wire
	Navitem calitem;
	@Wire
	A toggler;
	
	@Wire
	Navitem releaseinfo;



	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	// Toggle sidebar to collapse or expand
	@Listen("onClick = #toggler")
	public void toggleSidebarCollapsed() {
		if (navbar.isCollapsed()) {
			sidebar.setSclass("sidebar");
			navbar.setCollapsed(false);
			calitem.setTooltip("calpp, position=end_center, delay=0");
			toggler.setIconSclass("z-icon-angle-double-left");
		} else {
			sidebar.setSclass("sidebar sidebar-min");
			navbar.setCollapsed(true);
			calitem.setTooltip(""); 
			toggler.setIconSclass("z-icon-angle-double-right");
		}
		// Force the hlayout contains sidebar to recalculate its size
		Clients.resize(sidebar.getRoot().query("#main"));
	}

	@Listen("onClick = #across")
	public void browseStudy(){
		Div d=(Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/crossstudyquery/index.zul", d, null);
		
	}
	
	@Listen("onClick = #germplasm")
	public void browseGermplasm(){
		Div d=(Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/browsegermplasm/index.zul", d, null);
		
	}
	
	@Listen("onClick = #releaseinfo")
	public void browseReleaseinfo(){
		Div d=(Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../newuser/releaseinfo/index.zul", d, null);
		
	}



}