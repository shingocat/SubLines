package org.strasa.web.releaseinfo.view.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.strasa.extensiondata.chart.ChartData;
import org.strasa.extensiondata.chart.LineChartEngine;
import org.strasa.middleware.manager.DistributionAndExtensionManagerImpl;
import org.strasa.middleware.manager.ReleaseInfoManagerImpl;
import org.strasa.web.distributionandextension.view.model.SummaryFilter;
import org.strasa.web.releaseinfo.view.model.ReleaseInfoSummaryModel;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.ListModelList;

public class RelVYgrid {


	@SuppressWarnings("unused")
	private List<ReleaseInfoSummaryModel> noVY=new ArrayList<ReleaseInfoSummaryModel>();  //vy
	private SummaryFilter filter= new SummaryFilter();
	List<ReleaseInfoSummaryModel> currentModelVY=  new ArrayList<ReleaseInfoSummaryModel>();
	static List<ReleaseInfoSummaryModel> allDataVY=  new ArrayList<ReleaseInfoSummaryModel>();
	String type;
	private String[] category;
	CategoryModel model;

	@Init
	public void setData(){
		ReleaseInfoManagerImpl mgr= new ReleaseInfoManagerImpl();
		//		this.areaSummaryVY=mgr.getAreaSummaryGermplasmByYear();
		category=mgr.getProgramList();
		allDataVY=mgr.getNoOfVarietyReleaseByYear();
		currentModelVY=mgr.getNoOfVarietyReleaseByYear();
		
		type = "column";
		
		model = ChartData.getReleaseInfoByVarietyYear(allDataVY,category);
	}

	public SummaryFilter getFilter() {
		return filter;
	}

	public void setFilter(SummaryFilter filter) {
		this.filter = filter;
	}

	public List<ReleaseInfoSummaryModel> getCurrentModelVY() {
		return currentModelVY;
	}

	public void setCurrentModelVY(List<ReleaseInfoSummaryModel> currentModelVY) {
		this.currentModelVY = currentModelVY;
	}

	public List<ReleaseInfoSummaryModel> getNoVY() {
		return new ListModelList<ReleaseInfoSummaryModel>(currentModelVY);
	}

	public void setNoVY(
			List<ReleaseInfoSummaryModel> noVY) {
		this.noVY = noVY;
	}

	public static List<ReleaseInfoSummaryModel> getVY(SummaryFilter filter){
		List<ReleaseInfoSummaryModel> someResult = new ArrayList<ReleaseInfoSummaryModel>();
		String programName = filter.getProgramName().toLowerCase();
		String yearRelease = filter.getYearRelease().toLowerCase();

		for (Iterator<ReleaseInfoSummaryModel> i = allDataVY.iterator(); i.hasNext();) {
			ReleaseInfoSummaryModel tmp = i.next();
			if (tmp.getProgramName().toLowerCase().contains(programName) && 
					tmp.getYearrelease().toLowerCase().contains(yearRelease)) {
				someResult.add(tmp);
			}
		}
		return someResult;
	}	

	@Command
	@NotifyChange({"noVY","model"})
	public void changeFilter() {
		currentModelVY = getVY(filter);
		model = ChartData.getReleaseInfoByVarietyYear(currentModelVY,category);
	}
	
	public CategoryModel getModel() {
		return model;
	}

	public String getType(){
		return type;
	}

	@GlobalCommand("configChanged") 
	@NotifyChange("type")
	public void onConfigChanged(
			@BindingParam("type")String type){
		this.type = type;
	}

}
