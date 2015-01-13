package org.strasa.web.browsestudy.view.model;

import java.util.ArrayList;
import java.util.List;

import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.manager.BrowseStudyManagerImpl;
import org.strasa.middleware.manager.CountryManagerImpl;
import org.strasa.middleware.manager.LocationManagerImpl;
import org.strasa.middleware.manager.ProgramManagerImpl;
import org.strasa.middleware.manager.ProjectManagerImpl;
import org.strasa.middleware.manager.StudyManagerImpl;
import org.strasa.middleware.manager.StudyTypeManagerImpl;
import org.strasa.middleware.model.Country;
import org.strasa.middleware.model.Program;
import org.strasa.middleware.model.Project;
import org.strasa.middleware.model.StudyType;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Window;

public class SearchResult {
	private BrowseStudyManagerImpl browseStudyManagerImpl;
	private StudyManagerImpl studyMan;

	private List<StudySearchResultModel> searchResult;

	private String searchResultLabel;
	private Tab resultTab;
	
	@Init
	public void init(@ExecutionArgParam("resultTab") Tab resultTab){
		this.resultTab = resultTab;
		
		browseStudyManagerImpl= new BrowseStudyManagerImpl();
		studyMan= new StudyManagerImpl();
		searchResult = new ArrayList<StudySearchResultModel>();
	}
	
	@NotifyChange("*")
	@GlobalCommand
	public void searchByStudyName(@BindingParam("studyName") String studyName){
		if(validateString(studyName)){
			StudySearchFilterModel searchFilter = new StudySearchFilterModel();
			searchFilter.setStudyname(studyName);
			searchResult = browseStudyManagerImpl.getStudySearchResult(searchFilter);
			resultTab.setSelected(true);
		}else{
			
		}
	}
	
	@NotifyChange("*")
	@GlobalCommand
	public void updateSearchFilterResult(@BindingParam("searchFilter")StudySearchFilterModel searchFilter){
//		if(validateSearch(searchFilter)){
			searchFilter.shared = checkIfEmpty(searchFilter.shared);
			searchFilter.studyname = checkIfEmpty(searchFilter.studyname);
			searchFilter.country = checkIfEmpty(searchFilter.country);
			searchFilter.endyear = checkIfEmpty(searchFilter.endyear);
			searchFilter.startyear =  checkIfEmpty(searchFilter.startyear);
			
			try{
			if(searchFilter.shared.equals("private")){ //specific user only
				searchFilter.setShared(null);
				searchFilter.userid = studyMan.getUserid();
			}
			else if(searchFilter.shared.equals("public")){
				searchFilter.shared = "1";
				searchFilter.userid = 0;
			}else if(searchFilter.shared.equals("both")){//both
				searchFilter.shared = "1";//both
				searchFilter.userid =  studyMan.getUserid();
			}
			}catch(NullPointerException npe){
				
			}
			
			searchResult = browseStudyManagerImpl.getStudySearchResult(searchFilter);
			setSearchResultLabel("Search Result:  "+ searchResult.size()+"  row(s) returned");
			System.out.println("Size:"+searchResult.size());
			resultTab.setSelected(true);
//		}else{
//			Messagebox.show("Please filter your search.", "Search Filter too broad", Messagebox.OK, Messagebox.EXCLAMATION);
//		}
	}

	private boolean validateSearch(StudySearchFilterModel searchFilter) {
		// TODO Auto-generated method stub
		if( searchFilter.getLocationid()!=0 || searchFilter.getProgramid()!=0 || searchFilter.getProjectid()!=0 || searchFilter.getStudytypeid()!=0) return true;
		if(validateString(searchFilter.getStudyname())) return true;
		if(validateString(searchFilter.getCountry())) return true;
		if(validateString(searchFilter.getEndyear())) return true;
		if(validateString(searchFilter.getShared())) return true;
		if(validateString(searchFilter.getStartyear())) return true;

		return false;
	}

	private boolean validateString(String str) {
		// TODO Auto-generated method stub
		try{
			if(!str.equals("")) return true;
		}catch(NullPointerException npe) {
		}
		return false;
	}

	@NotifyChange("*")
	@GlobalCommand
	public void updateSummaryResult(@ContextParam(ContextType.COMPONENT) Component component, @BindingParam("summaryFilter")StudySummaryModel summary){
		resultTab.setSelected(true);
		StudySearchFilterModel searchFilter = new StudySearchFilterModel();
		searchFilter.setProgramid(summary.getProgramId());
		searchFilter.setProjectid(summary.getProjectId());
		searchFilter.setShared("1");
		searchFilter.setUserid(SecurityUtil.getDbUser().getId());
		searchResult = browseStudyManagerImpl.getStudySearchResult(searchFilter);
		System.out.println("Size:"+searchResult.size());
		setSearchResultLabel("Search Result:  "+ searchResult.size()+"  row(s) returned");

	}
	@NotifyChange("*")
	@GlobalCommand
	public void updateSummaryResultByStudyType(@ContextParam(ContextType.COMPONENT) Component component, @BindingParam("summaryFilter")StudySummaryModel summary, @BindingParam("studyTypeId")Integer studyTypeId){
		resultTab.setSelected(true);
		StudySearchFilterModel searchFilter = new StudySearchFilterModel();
		searchFilter.setProgramid(summary.getProgramId());
		searchFilter.setProjectid(summary.getProjectId());
		searchFilter.setStudytypeid(studyTypeId);
		searchFilter.setShared("1");
		searchFilter.setUserid(SecurityUtil.getDbUser().getId());
		searchResult = browseStudyManagerImpl.getStudySearchResult(searchFilter);
		System.out.println("Result "+summary.toString());
		setSearchResultLabel("Search Result:  "+ searchResult.size()+"  row(s) returned");

	}
	private String checkIfEmpty(String string) {
		// TODO Auto-generated method stub
		try{
			if(string.isEmpty()) return null;
		}catch(NullPointerException npe){
			//			System.out.println("Caught Null Pointer Exception at SearchResult.java under user browse study");
		}

		return string;
	}

	public List<StudySearchResultModel> getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(List<StudySearchResultModel> searchResult) {
		this.searchResult = searchResult;
	}

	public BrowseStudyManagerImpl getBrowseStudyManagerImpl() {
		return browseStudyManagerImpl;
	}

	public void setBrowseStudyManagerImpl(BrowseStudyManagerImpl browseStudyManagerImpl) {
		this.browseStudyManagerImpl = browseStudyManagerImpl;
	}

	public String getSearchResultLabel() {
		return searchResultLabel;
	}

	public void setSearchResultLabel(String searchResultLabel) {
		this.searchResultLabel = searchResultLabel;
	}
}
