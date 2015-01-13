package org.strasa.web.releaseinfo.view.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.strasa.extensiondata.chart.ChartData;
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

public class RelVCYgrid {


	@SuppressWarnings("unused")
	private List<ReleaseInfoSummaryModel> noVCY=new ArrayList<ReleaseInfoSummaryModel>();  //gce
	private SummaryFilter filter= new SummaryFilter();
	List<ReleaseInfoSummaryModel> currentModelVCY=  new ArrayList<ReleaseInfoSummaryModel>();
	static List<ReleaseInfoSummaryModel> allDataVCY=  new ArrayList<ReleaseInfoSummaryModel>();
	String type;
	private String[] category;
	CategoryModel model;

	@Init
	public void setData(){
		ReleaseInfoManagerImpl mgr= new ReleaseInfoManagerImpl();
//		this.areaSummaryVCY=mgr.getAreaSummaryGermplasmByYear();
		allDataVCY=mgr.getNoOfVarietyReleaseByCountryAndYear();
		currentModelVCY=mgr.getNoOfVarietyReleaseByCountryAndYear();
		type = "column";
		category=mgr.getProgramListWithCountry();
		model = ChartData.getReleaseInfoByVarietyCountryYear(allDataVCY,category);
		
	}

	public SummaryFilter getFilter() {
		return filter;
	}

	public void setFilter(SummaryFilter filter) {
		this.filter = filter;
	}

	public List<ReleaseInfoSummaryModel> getCurrentModelVCY() {
		return currentModelVCY;
	}

	public void setCurrentModelVCY(List<ReleaseInfoSummaryModel> currentModelVCY) {
		this.currentModelVCY = currentModelVCY;
	}

	public List<ReleaseInfoSummaryModel> getNoVCY() {
		return new ListModelList<ReleaseInfoSummaryModel>(currentModelVCY);
	}

	public void setNoVCY(
			List<ReleaseInfoSummaryModel> noVCY) {
		this.noVCY = noVCY;
	}

	public static List<ReleaseInfoSummaryModel> getVCY(SummaryFilter vcy){
		List<ReleaseInfoSummaryModel> someResult = new ArrayList<ReleaseInfoSummaryModel>();
		String programName = vcy.getProgramName().toLowerCase();
		String yearRelease = vcy.getYearRelease().toLowerCase();
		String counrtyRelease=vcy.getCounrtyRelease().toLowerCase();

		for (Iterator<ReleaseInfoSummaryModel> i = allDataVCY.iterator(); i.hasNext();) {
			ReleaseInfoSummaryModel tmp = i.next();
			if (tmp.getProgramName().toLowerCase().contains(programName) && 
					tmp.getYearrelease().toLowerCase().contains(yearRelease) && 
					tmp.getCountryrelease().toLowerCase().contains(counrtyRelease)) {
				someResult.add(tmp);
			}
		}
		return someResult;
	}	

	@Command
	@NotifyChange({"noVCY","model"})
	public void changeFilter() {
		currentModelVCY = getVCY(filter);
		model = ChartData.getReleaseInfoByVarietyCountryYear(currentModelVCY,category);
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
