package org.strasa.web.releaseinfo.view.model;

import java.util.ArrayList;
import java.util.List;

import org.strasa.middleware.manager.DistributionAndExtensionManagerImpl;
import org.strasa.middleware.manager.ProgramManagerImpl;
import org.strasa.middleware.manager.ProjectManagerImpl;
import org.strasa.middleware.manager.ReleaseInfoManagerImpl;
import org.strasa.web.releaseinfo.view.model.ReleaseInfoSummaryModel;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Toolbarbutton;

public class ReleaseInfoBrowse {
	private ReleaseInfoManagerImpl mgr;
	
	private List<String> stringy;
	private List<ReleaseInfoSummaryModel> summaryByCountry;
	private List<ReleaseInfoSummaryModel> summaryByYear;
	private List<ReleaseInfoListModel> extensionDataList;
	private List<ReleaseInfoSummaryModel> summaryArea;
	
	private List<ReleaseInfoSummaryModel> areaSummaryGermplasmByYearandCountryExtension;
	private List<ReleaseInfoSummaryModel> areaSummaryGermplasmByYear;
	private List<ReleaseInfoSummaryModel> areaSummaryGermplasmByCountryExtension;
	private List<ReleaseInfoSummaryModel> noOfVarietyReleaseByCountryAndYear;
	private List<ReleaseInfoSummaryModel> noOfVarietyReleaseByCountryRelease;
	private List<ReleaseInfoSummaryModel> noOfVarietyReleaseByYear;
	
	
	
	@Init
	public void setData(){
//		this.summaryByCountry=mgr.getCountOfGermplasmByCountrRealease();
//		this.summaryByYear=mgr.getCountOfGermplasmByYear();
//		this.extensionDataList=mgr.getExtensionDataList();
		
		mgr= new ReleaseInfoManagerImpl();
		this.noOfVarietyReleaseByCountryAndYear=mgr.getNoOfVarietyReleaseByCountryAndYear();
		this.noOfVarietyReleaseByCountryRelease=mgr.getNoOfVarietyReleaseByCountryRelease();
		this.noOfVarietyReleaseByYear=mgr.getNoOfVarietyReleaseByYear();
		
//		for(ReleaseInfoSummaryModel sm : noOfVarietyReleaseByYear){
//			System.out.println("next SM");
//			for(String s: sm.germplasmNames){
//				System.out.println(s);
//			}
//		}
	}
	
	public List<ReleaseInfoSummaryModel> getAreaSummaryGermplasmByYearandCountryExtension() {
		return areaSummaryGermplasmByYearandCountryExtension;
	}





	public void setAreaSummaryGermplasmByYearandCountryExtension(
			List<ReleaseInfoSummaryModel> areaSummaryGermplasmByYearandCountryExtension) {
		this.areaSummaryGermplasmByYearandCountryExtension = areaSummaryGermplasmByYearandCountryExtension;
	}





	public List<ReleaseInfoSummaryModel> getAreaSummaryGermplasmByYear() {
		return areaSummaryGermplasmByYear;
	}





	public void setAreaSummaryGermplasmByYear(
			List<ReleaseInfoSummaryModel> areaSummaryGermplasmByYear) {
		this.areaSummaryGermplasmByYear = areaSummaryGermplasmByYear;
	}





	public List<ReleaseInfoSummaryModel> getAreaSummaryGermplasmByCountryExtension() {
		return areaSummaryGermplasmByCountryExtension;
	}





	public void setAreaSummaryGermplasmByCountryExtension(
			List<ReleaseInfoSummaryModel> areaSummaryGermplasmByCountryExtension) {
		this.areaSummaryGermplasmByCountryExtension = areaSummaryGermplasmByCountryExtension;
	}





	public List<ReleaseInfoSummaryModel> getNoOfVarietyReleaseByCountryAndYear() {
		return noOfVarietyReleaseByCountryAndYear;
	}





	public void setNoOfVarietyReleaseByCountryAndYear(
			List<ReleaseInfoSummaryModel> noOfVarietyReleaseByCountryAndYear) {
		this.noOfVarietyReleaseByCountryAndYear = noOfVarietyReleaseByCountryAndYear;
	}





	public List<ReleaseInfoSummaryModel> getNoOfVarietyReleaseByCountryRelease() {
		return noOfVarietyReleaseByCountryRelease;
	}





	public void setNoOfVarietyReleaseByCountryRelease(
			List<ReleaseInfoSummaryModel> noOfVarietyReleaseByCountryRelease) {
		this.noOfVarietyReleaseByCountryRelease = noOfVarietyReleaseByCountryRelease;
	}





	public List<ReleaseInfoSummaryModel> getNoOfVarietyReleaseByYear() {
		return noOfVarietyReleaseByYear;
	}





	public void setNoOfVarietyReleaseByYear(
			List<ReleaseInfoSummaryModel> noOfVarietyReleaseByYear) {
		this.noOfVarietyReleaseByYear = noOfVarietyReleaseByYear;
	}





	public List<ReleaseInfoSummaryModel> getSummaryArea() {
		return summaryArea;
	}



	public void setSummaryArea(List<ReleaseInfoSummaryModel> summaryArea) {
		this.summaryArea = summaryArea;
	}



	public List<ReleaseInfoSummaryModel> getSummaryByCountry() {
		return summaryByCountry;
	}
	public void setSummaryByCountry(List<ReleaseInfoSummaryModel> summaryByCountry) {
		this.summaryByCountry = summaryByCountry;
	}
	public List<ReleaseInfoSummaryModel> getSummaryByYear() {
		return summaryByYear;
	}
	public void setSummaryByYear(List<ReleaseInfoSummaryModel> summaryByYear) {
		this.summaryByYear = summaryByYear;
	}



	public List<ReleaseInfoListModel> getExtensionDataList() {
		return extensionDataList;
	}



	public void setExtensionDataList(List<ReleaseInfoListModel> extensionDataList) {
		this.extensionDataList = extensionDataList;
	}





	public List<String> getStringy() {
		return stringy;
	}

	public void setStringy(List<String> stringy) {
		this.stringy = stringy;
	}

////	@NotifyChange("*")
//	@Command
//	public void addListCellItems(@ContextParam(ContextType.COMPONENT) Component component,
//			@ContextParam(ContextType.VIEW) Component view,@BindingParam("function") String function,@BindingParam("count") Integer count,@BindingParam("cell") Listcell listcell){
//		if(function.equals("varietyByYear")){
//			Toolbarbutton tb = new Toolbarbutton();
//			tb.setLabel("");
//			listcell.appendChild(tb);
////			for(int i=0;i<count;i++){
////				Toolbarbutton toolbarButton =new Toolbarbutton();
////				toolbarButton.setParent(listcell);
////				toolbarButton.setLabel("toolbarbutton"+Integer.toString(i));
////			}
//		}
//	}
	
	@NotifyChange("*")
	@Command
	public void show(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view,@BindingParam("function") String function,@BindingParam("ReleaseInfoSummaryModel") ReleaseInfoSummaryModel releaseInfoSummaryModel){
		if(function.equals("varietyByYear")){
//			mgr.getVarietyNamesOfVarietyReleaseByYear(ReleaseInfoSummaryModel);
			releaseInfoSummaryModel.getGermplasmname();
			System.out.println("");
		}
	}
}
