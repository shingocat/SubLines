package org.strasa.web.distributionandextension.view.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.strasa.extensiondata.chart.ChartData;
import org.strasa.middleware.manager.DistributionAndExtensionManagerImpl;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.ListModelList;

public class ExtGYgrid {


	@SuppressWarnings("unused")
	private List<SummaryModel> areaSummaryGY=new ArrayList<SummaryModel>();  //gce
	private SummaryFilter filter= new SummaryFilter();
	List<SummaryModel> currentModelGY=  new ArrayList<SummaryModel>();
	static List<SummaryModel> allDataGY=  new ArrayList<SummaryModel>();
	CategoryModel model;
	String type;
	private String[] category;

	@Init
	public void setData(){
		DistributionAndExtensionManagerImpl mgr= new DistributionAndExtensionManagerImpl();
//		this.areaSummaryGY=mgr.getAreaSummaryGermplasmByYear();
		allDataGY=mgr.getAreaSummaryGermplasmByYear();
		currentModelGY=mgr.getAreaSummaryGermplasmByYear();
		type = "column";
		category=mgr.getCategoryByYear();
		model = ChartData.getAreaSummaryGermplasmByYear(allDataGY,category);
		
	}

	public SummaryFilter getFilter() {
		return filter;
	}

	public void setFilter(SummaryFilter filter) {
		this.filter = filter;
	}

	public List<SummaryModel> getCurrentModelGY() {
		return currentModelGY;
	}

	public void setCurrentModelGY(List<SummaryModel> currentModelGY) {
		this.currentModelGY = currentModelGY;
	}

	public List<SummaryModel> getAreaSummaryGY() {
		return new ListModelList<SummaryModel>(currentModelGY);
	}

	public void setAreaSummaryGY(
			List<SummaryModel> areaSummaryGY) {
		this.areaSummaryGY = areaSummaryGY;
	}

	public static List<SummaryModel> getGY(SummaryFilter gce){
		List<SummaryModel> someResult = new ArrayList<SummaryModel>();
		String programName = gce.getProgramName().toLowerCase();
		String yearExtension = gce.getYearExtention().toLowerCase();
		String germplasmName=gce.getGermplasmName().toLowerCase();

		for (Iterator<SummaryModel> i = allDataGY.iterator(); i.hasNext();) {
			SummaryModel tmp = i.next();
			if (tmp.getProgramName().toLowerCase().contains(programName) && 
					tmp.getYearextension().toLowerCase().contains(yearExtension) && 
					tmp.getGermplasmname().toLowerCase().contains(germplasmName)) {
				someResult.add(tmp);
			}
		}
		return someResult;
	}	

	@Command
	@NotifyChange({"areaSummaryGY","model"})
	public void changeFilter() {
		currentModelGY = getGY(filter);
		model = ChartData.getAreaSummaryGermplasmByYear(currentModelGY,category);
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
