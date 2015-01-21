package org.strasa.web.page.navigation.user;

import java.util.HashMap;

import org.spring.security.model.SecurityUtil;
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
import org.zkoss.zul.Include;

public class SidebarComposer extends SelectorComposer<Component>  {

	@Wire
	Hlayout main;
	@Wire
	Div sidebar;
	@Wire
	Div contentui;
	@Wire
	Navbar navbar;
//	@Wire
//	Navitem calitem;
	@Wire
	A toggler;
	
	@Wire
	Navitem releaseinfo;
	@Wire
	Navitem extension;
	
	@Wire
	Navitem browsegermplasm;
	@Wire
	Navitem browsestudy;
	@Wire
	Navitem browsesegment;
	
	@Wire
	Navitem uploadstudy;

	@Wire
	Navitem uploadgermplasm;
	
	@Wire
	Navitem uploadsubsitutionline; // adding by QIN MAO
	
	@Wire
	Navitem uploadextension;
	
	@Wire
	Navitem uploadrelease;
	
	@Wire
	Navitem singlesite;
	
	@Wire
	Navitem ssslAnalysis; // adding by QIN MAO
	
	@Wire
	Navitem ssslAnalysisOutcome; // adding by QIN MAO on JAN 19, 2015
	
	@Wire 
	Navitem pyramidedAnalysis; // adding by QIN MAO
	
	@Wire
	Navitem pyramidedLineAnalysisOutcome; // adding by QIN MAO on JAN 19, 2015
	
	@Wire
	Navitem introgressionLineAnalysis; // adding by QIN MAO
	
	@Wire
	Navitem introgressionLineAnalysisOutcome; // adding by QIN MAO on JAN 19, 2015
	
	@Wire
	Navitem genomicselection; // adding by QIN MAO

	@Wire
	Navitem linkagemapping;
	
	@Wire
	Navitem settings;
	
	@Wire
	Navitem createfieldbook;
	
	@Wire
	Navitem browseanalysisresult;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	// Toggle sidebar to collapse or expand
	@Listen("onClick = #toggler")
	public void toggleSidebarCollapsed() {
		if (navbar.isCollapsed()) {
			sidebar.setSclass("sidebar");
			navbar.setCollapsed(false);
//			calitem.setTooltip("calpp, position=end_center, delay=0");
			toggler.setIconSclass("z-icon-angle-double-left");
		} else {
			sidebar.setSclass("sidebar sidebar-min");
			navbar.setCollapsed(true);
//			calitem.setTooltip(""); 
			toggler.setIconSclass("z-icon-angle-double-right");
		}
		// Force the hlayout contains sidebar to recalculate its size
		Clients.resize(sidebar.getRoot().query("#main"));
	}
	
	
	
	@Listen("onClick = #browsestudy")
	public void browseStudy(){
		Div d=(Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/browsestudy/index.zul", d, null);
		
	}

	@Listen("onClick = #across")
	public void browseAcrossStudy(){
		Div d=(Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/crossstudyquery/index.zul", d, null);
		
	}
	
	@Listen("onClick = #browsegermplasm")
	public void browseGermplasm(){
		Div d=(Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/browsegermplasm/index.zul", d, null);
		
	}
//	adding by QIN MAO, is used to browse introgression line info by searching segment
	@Listen("onClick = #browsesegment")
	public void browseSegment()
	{
		Div d = (Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/browsesegment/index.zul", d, null);
	}
	
	@Listen("onClick = #releaseinfo")
	public void browseReleaseinfo(){
		Div d=(Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/releaseinfo/index.zul", d, null);
		
	}
	
	@Listen("onClick = #extension")
	public void browseExtension(){
		Div d=(Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/distributionandextension/index.zul", d, null);
		
	}
	
	@Listen("onClick = #uploadstudy")
	public void uploadstudy(){
		Div d=(Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/maintenance/edituploadedstudies.zul", d, null);
		
	}
	
	
	@Listen("onClick = #uploadgermplasm")
	public void uploadgermplasm(){
		Div d=(Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/managegermplasm/index.zul", d, null);
		
	}
	
//	adding by QIN MAO for uploading substitution lines data
	@Listen("onClick = #uploadsubstitutionline")
	public void uploadsubstitutionline()
	{
		Div d = (Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/managegermplasm/introgressionline/index.zul", d, null);
	}
	
	@Listen("onClick = #uploadextension")
	public void uploadextension(){
		Div d=(Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/distributionandextension/editdistributionandextension.zul", d, null);
		
	}
	
	
	@Listen("onClick = #uploadrelease")
	public void uploadrelease(){
		Div d=(Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/releaseinfo/editreleaseinfo.zul", d, null);
		
	}

	@Listen("onClick = #singlesite")
	public void singlesite(){
		Div d=(Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/analysis/singlesite/index.zul", d, null);
		
	}
	
	@Listen("onClick = #ssslAnalysis") // adding by QIN MAO
	public void ssslAnalysis()
	{
		Div d = (Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/analysis/sssl/index.zul", d, null);
	}
	
	@Listen("onClick = #ssslAnalysisOutcome")
	public void ssslAnalysisOutcome()
	{
		Div d = (Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("Type", "SSSL_Analysis");
		args.put("Username", SecurityUtil.getDbUser().getUsername());
		Executions.createComponents("../user/analysis/resultanalysistree.zul", d, args);
	}
	
	@Listen("onClick = #pyramidedLineAnalysis") // adding by QIN MAO
	public void pyramidedLineAnalysis()
	{
		Div d = (Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/analysis/pyramidedline/index.zul", d, null);
	}
	
	@Listen("onClick = #pyramidedLineAnalysisOutcome")
	public void pyramidedLineAnalysisOutcome()
	{
		Div d = (Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("Type", "PL_Analysis");
		args.put("Username", SecurityUtil.getDbUser().getUsername());
		Executions.createComponents("../user/analysis/resultanalysistree.zul", d, args);
	}
	
	@Listen("onClick = #introgressionLineAnalysis") // adding by QIN MAO 
	public void introgressionLineAnalysis()
	{
		Div d = (Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/analysis/introgressionline/index.zul", d, null);
	}
	
	@Listen("onClick = #introgressionLineAnalysisOutcome")
	public void introgressionLineAnalysisOutcome()
	{
		Div d = (Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("Type", "IL_Analysis");
		args.put("Username", SecurityUtil.getDbUser().getUsername());
		Executions.createComponents("../user/analysis/resultanalysistree.zul", d, args);
	}
	@Listen("onClick = #linkagemapping")
	public void linkagemapping(){
		Div d=(Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/analysis/linkagemapping/index.zul", d, null);
		
	}
	
	@Listen("onClick = #genomicselection")
	public void genomicselection()
	{
		Div d = (Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/analysis/genomicselection/index.zul", d, null);
	}
	
	@Listen("onClick = #settings")
	public void settings(){
		Div d=(Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/maintenance/index.zul", d, null);
		
	}
	
	
	@Listen("onClick = #createfieldbook")
	public void createfieldbook(){
		Div d=(Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/createfieldbook/index.zul", d, null);
		
	}
	
	@Listen("onClick = #browseanalysisresult")
	public void browseanalysisresult(){
		Div d=(Div) sidebar.getRoot().query("#contentui");
		d.getChildren().clear();
		Executions.createComponents("../user/analysis/resultanalysistree.zul", d, null);
		
	}

}