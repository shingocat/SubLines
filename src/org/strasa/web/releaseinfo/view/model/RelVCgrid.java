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

public class RelVCgrid {


	@SuppressWarnings("unused")
	private List<ReleaseInfoSummaryModel> noVC=new ArrayList<ReleaseInfoSummaryModel>();  //vc
	private SummaryFilter filter= new SummaryFilter();
	List<ReleaseInfoSummaryModel> currentModelVC=  new ArrayList<ReleaseInfoSummaryModel>();
	static List<ReleaseInfoSummaryModel> allDataVC=  new ArrayList<ReleaseInfoSummaryModel>();
	CategoryModel model;
	String type;
	private String[] category;


	@Init
	public void setData(){
		ReleaseInfoManagerImpl mgr= new ReleaseInfoManagerImpl();
		allDataVC=mgr.getNoOfVarietyReleaseByCountryRelease();
		currentModelVC=mgr.getNoOfVarietyReleaseByCountryRelease();
		type = "column";
		category=mgr.getProgramList();
		model = ChartData.getReleaseInfoByVarietyCountry(currentModelVC,category);
	}

	public SummaryFilter getFilter() {
		return filter;
	}

	public void setFilter(SummaryFilter filter) {
		this.filter = filter;
	}

	public List<ReleaseInfoSummaryModel> getCurrentModelVC() {
		return currentModelVC;
	}

	public void setCurrentModelVC(List<ReleaseInfoSummaryModel> currentModelVC) {
		this.currentModelVC = currentModelVC;
	}

	public List<ReleaseInfoSummaryModel> getNoVC() {
		return new ListModelList<ReleaseInfoSummaryModel>(currentModelVC);
	}

	public void setNoVC(
			List<ReleaseInfoSummaryModel> noVC) {
		this.noVC = noVC;
	}

	public static List<ReleaseInfoSummaryModel> getVC(SummaryFilter vc){
		List<ReleaseInfoSummaryModel> someResult = new ArrayList<ReleaseInfoSummaryModel>();
		String programName = vc.getProgramName().toLowerCase();
		String counrtyRelease=vc.getCounrtyRelease().toLowerCase();

		for (Iterator<ReleaseInfoSummaryModel> i = allDataVC.iterator(); i.hasNext();) {
			ReleaseInfoSummaryModel tmp = i.next();
			if (tmp.getProgramName().toLowerCase().contains(programName) && 
					tmp.getCountryrelease().toLowerCase().contains(counrtyRelease)) {
				someResult.add(tmp);
			}
		}
		return someResult;
	}	

	@Command
	@NotifyChange({"noVC","model"})
	public void changeFilter() {
		currentModelVC = getVC(filter);
		model = ChartData.getReleaseInfoByVarietyCountry(currentModelVC,category);
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
