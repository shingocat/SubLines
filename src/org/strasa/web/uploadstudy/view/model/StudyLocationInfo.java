/*
 * Data Management and Analysis (DMAS) - International Rice Research Institute 2013-2015
 * 
 *   DMAS is an opensource Data management and statistical analysis mainly for STRASA Project: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *  DMAS is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with DMAS.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * 
 * 
 */
package org.strasa.web.uploadstudy.view.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.strasa.middleware.manager.CountryManagerImpl;
import org.strasa.middleware.manager.StudyLocationManagerImpl;
import org.strasa.middleware.model.Country;
import org.strasa.middleware.model.Location;
import org.strasa.web.common.api.FormValidator;
import org.strasa.web.common.api.ProcessTabViewModel;
import org.strasa.web.utilities.GridValidationUtility;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;

import com.mysql.jdbc.StringUtils;

public class StudyLocationInfo extends ProcessTabViewModel {

	@Wire("#step4")
	Grid mainGrid;

	private FormValidator formValidator = new FormValidator();
	private boolean noLocation;
	private List<Location> lstUnknownLocations = new ArrayList<Location>();
	private List<Location> lstLocations = new ArrayList<Location>();
	private ArrayList<String> cmbCountry = new ArrayList<String>();

	private StudyLocationManagerImpl studyLocationManager;

	public ArrayList<String> getCmbCountry() {
		return cmbCountry;
	}

	public boolean editable(int userid) {
		System.out.println("functon called: " + this.userID);
		return userid == this.userID;

	}

	public void setCmbCountry(ArrayList<String> cmbCountry) {
		this.cmbCountry = cmbCountry;
	}

	public List<Country> getCountryList() {
		return new CountryManagerImpl().getAllCountry();
	}

	private double sampleID;

	@GlobalCommand
	@NotifyChange("sampleID")
	public void testGlobalCom(@BindingParam("studyID") double newVal) {
		sampleID = newVal;
	}

	public List<Location> getLstUnknownLocations() {
		return lstUnknownLocations;
	}

	public void setLstUnknownLocations(List<Location> lstUnknownLocations) {
		this.lstUnknownLocations = lstUnknownLocations;
	}

	public List<Location> getLstLocations() {
		return lstLocations;
	}

	public void setLstLocations(List<Location> lstLocations) {
		this.lstLocations = lstLocations;
	}

	public boolean validateTab() {
		ArrayList<Integer> lstCol = new ArrayList<Integer>(Arrays.asList(0, 4, 5, 6));
		new GridValidationUtility(mainGrid, lstCol).validateAll();
		;
		for (Location loc : lstLocations) {
			if (StringUtils.isNullOrEmpty(loc.getLocationname())) {
				Messagebox.show("Location must not be empty", "OK", Messagebox.OK, Messagebox.EXCLAMATION);
				return false;
			}
			if (loc.getId() == null) {
				if (StringUtils.isNullOrEmpty(loc.getAltitude())) {
					Messagebox.show("Altitude in " + loc.getLocationname() + " must not be empty", "OK", Messagebox.OK, Messagebox.EXCLAMATION);
					return false;
				}
				if (StringUtils.isNullOrEmpty(loc.getLatitude())) {
					Messagebox.show("Latitude in " + loc.getLocationname() + " must not be empty", "OK", Messagebox.OK, Messagebox.EXCLAMATION);
					return false;
				}
				if (StringUtils.isNullOrEmpty(loc.getWeatherstation())) {
					Messagebox.show("Weather Station in " + loc.getLocationname() + " must not be empty", "OK", Messagebox.OK, Messagebox.EXCLAMATION);
					return false;
				}

			}
		}

		studyLocationManager.updateStudyLocation(lstLocations, studyID);
		return true;
	}

	public FormValidator getFormValidator() {
		return formValidator;
	}

	public void setFormValidator(FormValidator formValidator) {
		this.formValidator = formValidator;
	}

	@Init
	public void init(@ExecutionArgParam("uploadModel") ProcessTabViewModel uploadModel) {

		this.initValues(uploadModel);
		System.out.println("_______________________________________________________________");
		System.out.println("Staring debugging :" + StudyLocationInfo.class.getName());
		System.out.println("_______________________________________________________________");
		System.out.println("Raw: " + isRaw);
		System.out.println("StudyID: " + studyID);

		studyLocationManager = new StudyLocationManagerImpl(isRaw);
		List<Country> lCountries = getCountryList();
		for (Country data : lCountries) {
			cmbCountry.add(data.getIsoabbr());
		}

		lstLocations.addAll(studyLocationManager.getLocationsFromStudySite(studyID, this.dataset.getId()));

	}

	@GlobalCommand
	public void refreshLocationList() {
		lstLocations.clear();
		lstLocations.addAll(studyLocationManager.getLocationsFromStudySite(studyID, this.dataset.getId()));
		BindUtils.postNotifyChange(null, null, StudyLocationInfo.this, "*");
		System.out.println("Location list Refreshed");
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);
	}

	public double getSampleID() {
		return sampleID;
	}

	public void setSampleID(double sampleID) {
		this.sampleID = sampleID;
	}

	@Command("saveLocationInfo")
	public void saveLocationInfo() {
		// selectedSite=
		// studyLocationManager.updateStudyLocation(locations);

		Messagebox.show("Changes saved.");
	}
}