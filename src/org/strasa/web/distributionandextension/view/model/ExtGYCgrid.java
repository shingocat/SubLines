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

public class ExtGYCgrid {


	@SuppressWarnings("unused")
	private List<SummaryModel> areaSummaryGYC=new ArrayList<SummaryModel>();  //gce
	private SummaryFilter filter= new SummaryFilter();
	List<SummaryModel> currentModelGYC=  new ArrayList<SummaryModel>();
	static List<SummaryModel> allDataGYC=  new ArrayList<SummaryModel>();
	CategoryModel model;
	String type;
	private String[] category;



	@Init
	public void setData(){
		DistributionAndExtensionManagerImpl mgr= new DistributionAndExtensionManagerImpl();
		this.areaSummaryGYC=mgr.getAreaSummaryGermplasmByYearandCountryExtension();
		allDataGYC=mgr.getAreaSummaryGermplasmByYearandCountryExtension();
		currentModelGYC=mgr.getAreaSummaryGermplasmByYearandCountryExtension();
		type = "column";
		category=mgr.getCategoryByCountry();
		model = ChartData.getAreaSummaryGermplasmByYearCountry(allDataGYC,category);
	}

	public SummaryFilter getFilter() {
		return filter;
	}

	public void setFilter(SummaryFilter filter) {
		this.filter = filter;
	}

	public List<SummaryModel> getCurrentModelGCE() {
		return currentModelGYC;
	}

	public void setCurrentModelGYC(List<SummaryModel> currentModelGYC) {
		this.currentModelGYC = currentModelGYC;
	}

	public List<SummaryModel> getAreaSummaryGYC() {
		return new ListModelList<SummaryModel>(currentModelGYC);
	}

	public void setAreaSummaryGYC(
			List<SummaryModel> areaSummaryGYC) {
		this.areaSummaryGYC = areaSummaryGYC;
	}

	public static List<SummaryModel> getGYC(SummaryFilter filter){
		List<SummaryModel> someResult = new ArrayList<SummaryModel>();
		String programName = filter.getProgramName().toLowerCase();
		String yearExtension = filter.getYearExtention().toLowerCase();
		String countryExtension=filter.getCountryExtension().toLowerCase();
		String germplasmName=filter.getGermplasmName().toLowerCase();

		for (Iterator<SummaryModel> i = allDataGYC.iterator(); i.hasNext();) {
			SummaryModel tmp = i.next();
			if (tmp.getProgramName().toLowerCase().contains(programName) && 
					tmp.getCountryextension().toLowerCase().contains(countryExtension) && 
					tmp.getYearextension().toLowerCase().contains(yearExtension) && 
					tmp.getGermplasmname().toLowerCase().contains(germplasmName)) {
				someResult.add(tmp);
			}
		}
		return someResult;
	}	

	@Command
	@NotifyChange({"areaSummaryGYC","model"})
	public void changeFilter() {
		currentModelGYC = getGYC(filter);
		model = ChartData.getAreaSummaryGermplasmByYearCountry(currentModelGYC,category);
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
