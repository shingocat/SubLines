package org.strasa.web.browsestudy.view.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.strasa.middleware.manager.CountryManagerImpl;
import org.strasa.middleware.manager.LocationManagerImpl;
import org.strasa.middleware.manager.StudyLocationManagerImpl;
import org.strasa.middleware.manager.StudyRawDataManagerImpl;
import org.strasa.middleware.model.Country;
import org.strasa.middleware.model.Location;
import org.strasa.middleware.model.StudyLocation;
import org.strasa.web.common.api.FormValidator;
import org.strasa.web.common.api.ProcessTabViewModel;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;

public class LocationInfo{
    
	private int studyId;
	private List<Location> lstLocations = new ArrayList<Location>();
	private StudyLocationManagerImpl studyLocationManager;
	private LocationManagerImpl locationManager;
	
	public List<Country> getCountryList(){
		return new CountryManagerImpl().getAllCountry();
	}

	public List<Location> getLstLocations() {
		return lstLocations;
	}


	public void setLstLocations(List<Location> lstLocations) {
		this.lstLocations = lstLocations;
	}

	@Init
	public void init(@ExecutionArgParam("studyId") Integer studyId){
		this.setStudyId(studyId);
		
		studyLocationManager = new StudyLocationManagerImpl(false);
		locationManager = new LocationManagerImpl();
		List<StudyLocation> studyLocations = new ArrayList<StudyLocation>();
		
		studyLocations=studyLocationManager.getStudyLocationsById(studyId);
		for(StudyLocation s : studyLocations){
			Location l = locationManager.getLocationById(s.getLocationid());
			lstLocations.add(l);
		}
	}

	public int getStudyId() {
		return studyId;
	}

	public void setStudyId(int studyId) {
		this.studyId = studyId;
	}
}