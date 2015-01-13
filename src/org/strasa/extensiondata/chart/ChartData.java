package org.strasa.extensiondata.chart;

import java.util.List;

import org.strasa.middleware.manager.DistributionAndExtensionManagerImpl;
import org.strasa.middleware.manager.ReleaseInfoManagerImpl;
import org.strasa.middleware.model.ExtensionData;
import org.strasa.web.distributionandextension.view.model.DistributionAndExtensionSummaryModel;
import org.strasa.web.distributionandextension.view.model.SummaryModel;
import org.strasa.web.releaseinfo.view.model.ReleaseInfoSummaryModel;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.SimpleCategoryModel;

public class ChartData {

	private static List<ReleaseInfoSummaryModel> allDataVY;

	public static CategoryModel getModelByCountry() {
		CategoryModel model = new SimpleCategoryModel();
		String[] category = { "Country"};

		DistributionAndExtensionManagerImpl mgr= new DistributionAndExtensionManagerImpl();
		List<DistributionAndExtensionSummaryModel> list=null;//mgr.getCountOfGermplasmByCountrRealease();

		for(DistributionAndExtensionSummaryModel d:list){
			model.setValue(category[0], d.getData(),d.getTotalCount());
		}

		return model;
	}

	public static CategoryModel getModelByYear() {
		CategoryModel model = new SimpleCategoryModel();
		String[] category = { "Year"};

		DistributionAndExtensionManagerImpl mgr= new DistributionAndExtensionManagerImpl();
		List<DistributionAndExtensionSummaryModel> list=null;//mgr.getCountOfGermplasmByYear();

		for(DistributionAndExtensionSummaryModel d:list){
			model.setValue(category[0], d.getData(),d.getTotalCount());
		}

		return model;
	}

	public static CategoryModel getReleaseInfoByVarietyYear(List<ReleaseInfoSummaryModel> data,String[] category) {

		CategoryModel model = new SimpleCategoryModel();

		for(int i=0; i < category.length;i++){
			for(ReleaseInfoSummaryModel d:data){
				if(d.getProgramName().contains(category[i])){
					model.setValue(category[i], d.getYearrelease(),d.getCountVariety());
				}
			}
		}

		return model;

	}

	public static CategoryModel getReleaseInfoByVarietyCountry(
			List<ReleaseInfoSummaryModel> data, String[] category) {
		// TODO Auto-generated method stub
		CategoryModel model = new SimpleCategoryModel();

		for(int i=0; i < category.length;i++){
			for(ReleaseInfoSummaryModel d:data){
				if(d.getProgramName().contains(category[i])){
					model.setValue(category[i], d.getCountryrelease(),d.getCountVariety());
				}
			}
		}

		return model;

	}

	public static CategoryModel getReleaseInfoByVarietyCountryYear(
			List<ReleaseInfoSummaryModel> data, String[] category) {
		CategoryModel model = new SimpleCategoryModel();

		for(int i=0; i < category.length;i++){
//			System.out.println("Category " +category[i]);
			for(ReleaseInfoSummaryModel d:data){
				String progNameTemp=d.getProgramName()+"-"+d.getCountryrelease();
				if(progNameTemp.equals(category[i])){
					model.setValue(category[i], d.getYearrelease(),d.getCountVariety());
				}
			}
		}
		return model;
	}

	public static CategoryModel getAreaSummaryGermplasmByCountry(
			List<SummaryModel> data, String[] category) {
		CategoryModel model = new SimpleCategoryModel();

		for(int i=0; i < category.length;i++){
//			System.out.println("Category " +category[i]);
			for(SummaryModel d:data){
				String progNameTemp=d.getProgramName()+"-"+d.getCountryextension();
				if(progNameTemp.equals(category[i])){
					model.setValue(category[i], d.getCountryextension(),d.getSumPlantingArea());
				}
			}
		}
		return model;
	}
	
	public static CategoryModel getAreaSummaryGermplasmByYear(
			List<SummaryModel> data, String[] category) {
		CategoryModel model = new SimpleCategoryModel();

		for(int i=0; i < category.length;i++){
			for(SummaryModel d:data){
				String progNameTemp=d.getProgramName();
				if(progNameTemp.equals(category[i])){
					model.setValue(category[i], d.getYearextension(),d.getSumPlantingArea());
					System.out.println("Alex" +category[i] +"  "+ d.getYearextension()+ " "+d.getSumPlantingArea());
				}
			}
		}
		return model;
	}
	
	public static CategoryModel getAreaSummaryGermplasmByYearCountry(
			List<SummaryModel> data, String[] category) {
		CategoryModel model = new SimpleCategoryModel();

		for(int i=0; i < category.length;i++){
//			System.out.println("Category " +category[i]);
			for(SummaryModel d:data){
				String progNameTemp=d.getProgramName()+"-"+d.getCountryextension();
				if(progNameTemp.equals(category[i])){
					model.setValue(category[i], d.getYearextension(),d.getSumPlantingArea());
				}
			}
		}
		return model;
	}
}