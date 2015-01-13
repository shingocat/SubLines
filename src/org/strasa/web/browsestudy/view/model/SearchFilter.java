package org.strasa.web.browsestudy.view.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.strasa.middleware.manager.BrowseStudyManagerImpl;
import org.strasa.middleware.manager.CountryManagerImpl;
import org.strasa.middleware.manager.LocationManagerImpl;
import org.strasa.middleware.manager.ProgramManagerImpl;
import org.strasa.middleware.manager.ProjectManagerImpl;
import org.strasa.middleware.manager.StudyDataColumnManagerImpl;
import org.strasa.middleware.manager.StudyManagerImpl;
import org.strasa.middleware.manager.StudyTypeManagerImpl;
import org.strasa.middleware.model.Country;
import org.strasa.middleware.model.GermplasmType;
import org.strasa.middleware.model.Location;
import org.strasa.middleware.model.Program;
import org.strasa.middleware.model.Project;
import org.strasa.middleware.model.StudyDataColumn;
import org.strasa.middleware.model.StudyType;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;

public class SearchFilter {
	private ProgramManagerImpl programMan;
	private ProjectManagerImpl projectMan;
	private StudyTypeManagerImpl studyTypeMan;
	private CountryManagerImpl countryMan;
	private LocationManagerImpl locationMan;

	private StudySearchFilterModel searchFilter = null;
	private List<Program> programList= null;
	private List<Project> projectList= null;
	private List<StudyType> studyTypeList= null;
	private List<Country> countryList= null;
	private List<Location> locationList= null;

	private boolean validation = false;
	private HashMap<String,Integer> programListKey = new HashMap<String,Integer>();
	private HashMap<String,Integer> projectListKey = new HashMap<String,Integer>();
	private HashMap<String,Integer> locationListKey = new HashMap<String,Integer>();

	public StudySearchFilterModel getSearchFilter() {
		return searchFilter;
	}


	public void setSearchFilter(StudySearchFilterModel searchFilter) {
		this.searchFilter = searchFilter;
	}

	public List<Program> getProgramList() {
		programList.add(0, new Program());
		return programList;
	}

	public void setProgramList(List<Program> programList) {
		this.programList = programList;
	}

	public List<Project> getProjectList() {
		projectList.add(0, new Project());
		return projectList;
	}

	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}

	public List<StudyType> getStudyTypeList() {
		studyTypeList.add(0, new StudyType());
		return studyTypeList;
	}

	public void setStudyTypeList(List<StudyType> studyTypeList) {
		this.studyTypeList = studyTypeList;
	}

	public List<Country> getCountryList() {
		countryList.add(0, new Country());
		return countryList;
	}

	public void setCountryList(List<Country> countryList) {
		this.countryList = countryList;
	}

	public List<Location> getLocationList() {
		locationList.add(0, new Location());
		return locationList;
	}

	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}

	@Init
	public void init(){
		programMan = new ProgramManagerImpl();
		projectMan = new ProjectManagerImpl();
		studyTypeMan = new StudyTypeManagerImpl();
		countryMan = new CountryManagerImpl();
		locationMan = new LocationManagerImpl();
		searchFilter = new StudySearchFilterModel();
		
		programList = programMan.getAllProgram();
		for(Program program:programList){
			programListKey.put(program.getName(), program.getId());
		}
		
		projectList = projectMan.getAllProject();
		for(Project proj:projectList){
			projectListKey.put(proj.getName(), proj.getId());
		}
		
		studyTypeList = studyTypeMan.getAllStudyType();
		countryList = countryMan.getAllCountry();
		locationList = locationMan.getAllLocations();
		for(Location l: locationList){
			locationListKey.put(l.getLocationname(), l.getId());
		}
	}

	@NotifyChange("searchFilter")
	@Command
	public void reset(){
		searchFilter = new StudySearchFilterModel();
		searchFilter.shared="";
	}
	
	@NotifyChange("projectList")
	@Command
	public void updateLists(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view, @BindingParam("programName") String programName){
		
		Combobox projectComboBox = (Combobox) component.getFellow("projectComboBox");
		System.out.println("programName: "+ programName);
		try{
		int progId = programListKey.get(programName);
		setProjectList(projectMan.getProjectByProgramId(progId));
		projectComboBox.setSelectedIndex(0);
		searchFilter.setProjectid(0);
		searchFilter.setProgramid(progId);
		System.out.println("programId: "+ Integer.toString(progId));
		}catch(RuntimeException re){
			System.out.println("Nothings been chosen");
			setProjectList(projectMan.getAllProject());
		}
		
	}
	
	
	@NotifyChange("locationList")
	@Command
	public void updateLocationList(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view, @BindingParam("countryName") String countryName){
		
		Combobox locationCombobox = (Combobox) component.getFellow("locationCombobox");
		System.out.println("countryName: "+ countryName);
		locationListKey.clear();
		try{
		setLocationList(locationMan.getLocationByCountryName(countryName));
		for(Location l: locationList){
			locationListKey.put(l.getLocationname(), l.getId());
		}
		}catch(RuntimeException re){
			setLocationList(new ArrayList<Location>());
			System.out.println("Nothings been chosen");
		}
		
		locationCombobox.setSelectedIndex(0);
		searchFilter.setLocationid(0);
	}
		
	@Command
	public void updateProjectId(@BindingParam("projectName") String projectName){
		try{
		int projId = projectListKey.get(projectName);
			searchFilter.setProjectid(projId);
		}catch(RuntimeException re){
			searchFilter.setProjectid(0);
		}
	}
	
	@Command
	public void updateLocationId(@BindingParam("locationName") String locationName){
		try{
		int locId = locationListKey.get(locationName);
			searchFilter.setLocationid(locId);
		}catch(RuntimeException re){
			searchFilter.setProjectid(0);
		}
	}
	public boolean isValidation() {
		return validation;
	}


	public void setValidation(boolean validation) {
		this.validation = validation;
	}

}
