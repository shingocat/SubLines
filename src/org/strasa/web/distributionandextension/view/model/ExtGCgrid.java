package org.strasa.web.distributionandextension.view.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.strasa.extensiondata.chart.ChartData;
import org.strasa.middleware.manager.DistributionAndExtensionManagerImpl;
import org.strasa.web.utilities.FileUtilities;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Rows;

public class ExtGCgrid {


	@SuppressWarnings("unused")
	private List<SummaryModel> areaSummaryGC=new ArrayList<SummaryModel>();  //gce
	private SummaryFilter filter= new SummaryFilter();
	List<SummaryModel> currentModelGCE=  new ArrayList<SummaryModel>();
	static List<SummaryModel> allDataGC=  new ArrayList<SummaryModel>();
	CategoryModel model;
	String type;
	private String[] category;


	@Init
	public void setData(){
		DistributionAndExtensionManagerImpl mgr= new DistributionAndExtensionManagerImpl();
		this.areaSummaryGC=mgr.getAreaSummaryGermplasmByCountryExtension();
		allDataGC=mgr.getAreaSummaryGermplasmByCountryExtension();
		currentModelGCE=mgr.getAreaSummaryGermplasmByCountryExtension();
		type = "column";
		category=mgr.getCategoryByCountry();
		model = ChartData.getAreaSummaryGermplasmByCountry(allDataGC,category);
	}

	public SummaryFilter getFilter() {
		return filter;
	}

	public void setFilter(SummaryFilter filter) {
		this.filter = filter;
	}

	public List<SummaryModel> getCurrentModelGCE() {
		return currentModelGCE;
	}

	public void setCurrentModelGCE(List<SummaryModel> currentModelGCE) {
		this.currentModelGCE = currentModelGCE;
	}

	public List<SummaryModel> getAreaSummaryGC() {
		return new ListModelList<SummaryModel>(currentModelGCE);
	}

	public void setAreaSummaryGC(
			List<SummaryModel> areaSummaryGC) {
		this.areaSummaryGC = areaSummaryGC;
	}

	public static List<SummaryModel> getGCE(SummaryFilter filter){
		List<SummaryModel> someResult = new ArrayList<SummaryModel>();
		String programName = filter.getProgramName().toLowerCase();
		String countryExtension=filter.getCountryExtension().toLowerCase();
		String germplasmName=filter.getGermplasmName().toLowerCase();

		for (Iterator<SummaryModel> i = allDataGC.iterator(); i.hasNext();) {
			SummaryModel tmp = i.next();
			if (tmp.getProgramName().toLowerCase().contains(programName) && 
					tmp.getCountryextension().toLowerCase().contains(countryExtension) && 
					tmp.getGermplasmname().toLowerCase().contains(germplasmName)) {
				someResult.add(tmp);
			}
		}
		return someResult;
	}	

	@Command
	@NotifyChange({"areaSummaryGC","model"})
	public void changeFilter() {
		currentModelGCE = getGCE(filter);
		model = ChartData.getAreaSummaryGermplasmByCountry(currentModelGCE,category);
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
