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
package org.strasa.web.uploadstudy.view.pojos;

import org.strasa.middleware.manager.LocationManagerImpl;
import org.strasa.middleware.model.Ecotype;
import org.strasa.middleware.model.Location;
import org.strasa.middleware.model.PlantingType;
import org.strasa.middleware.model.StudyAgronomy;
import org.strasa.middleware.model.StudyDesign;
import org.strasa.middleware.model.StudySite;
import org.zkoss.bind.Binder;
import org.zkoss.zul.ListModelList;

import com.mysql.jdbc.StringUtils;

public class StudySiteInfoModel extends StudySite {
	public StudyAgronomy selectedAgroInfo = new StudyAgronomy();
	public StudyDesign selectedDesignInfo = new StudyDesign();
	public PlantingType selectedSitePlantingType = new PlantingType();
	public int selectedPlantingIndex;
	public Ecotype selectedEcotype;
	public boolean isYearAuto = false;
	public Location selectedLocation = new Location();
	public Binder binder;
	public boolean isUpdateMode;
	private String ecoSystem;

	public boolean isUpdateMode() {
		return isUpdateMode;
	}

	public boolean isSiteEditable() {
		if (isUpdateMode)
			return isUpdateMode;
		return (this.getSitename().isEmpty());
	}

	public boolean isYearEditable() {
		if (isUpdateMode)
			return isUpdateMode;
		return (isYearAuto);
	}

	public boolean isSeasonEditable() {
		if (isUpdateMode)
			return isUpdateMode;
		return (this.getSeason().isEmpty());
	}

	public void setUpdateMode(boolean isUpdateMode) {
		this.isUpdateMode = isUpdateMode;
	}

	public Location getSelectedLocation() {
		return selectedLocation;
	}

	public ListModelList<Location> getAllLocations() {
		ListModelList<Location> lstLocations = new ListModelList<Location>();
		lstLocations.addAll(new LocationManagerImpl().getAllLocations());
		return lstLocations;
	}

	public void setSelectedLocation(Location selectedLocation) {
		this.selectedLocation = selectedLocation;
		this.setSitelocation(selectedLocation.getLocationname());
		this.setLocationid(selectedLocation.getId());
	}

	public boolean isYearAuto() {

		if (isUpdateMode)
			return isUpdateMode;
		return (isYearAuto);
	}

	public void updateFilteredLocations() {

	}

	public ListModelList<Location> getFilteredLocations() {

		ListModelList<Location> returnVal = new ListModelList<Location>();
		if (StringUtils.isNullOrEmpty(selectedLocation.getLocationname())) {

			return getAllLocations();

		}
		String tempSelected = null;

		ListModelList<Location> lstLocations = getAllLocations();
		for (Location loc : lstLocations) {
			if (loc.getLocationname().toLowerCase().startsWith(selectedLocation.getLocationname().toLowerCase())) {
				returnVal.add(loc);
				if (tempSelected == null)
					tempSelected = loc.getLocationname();
			}
		}

		return returnVal;

	}

	public void setYearAuto(boolean isYearAuto) {
		this.isYearAuto = isYearAuto;
	}

	public Ecotype getSelectedEcotype() {
		return selectedEcotype;
	}

	public void setSelectedEcotype(Ecotype selectedEcotype) {
		this.selectedEcotype = selectedEcotype;
		this.setEcotypeid(selectedEcotype.getId());
	}

	public String validateAll() {
		if (StringUtils.isNullOrEmpty(this.getSitename())) {
			return "Error: Site Name  must not be empty! ";
		}
		if (StringUtils.isNullOrEmpty(this.selectedLocation.getLocationname())) {
			return "Error: Location in " + this.getSitename() + " must not be empty! ";
		}
		if (this.selectedLocation.getId() == null) {
			return "Error: Location in " + this.getSitename() + " does not exist in the database. Please add your location first or select any existing location.";
		}
		if (StringUtils.isNullOrEmpty(this.getYear())) {
			return "Error: Year in " + this.getSitename() + " must not be empty! ";
		}
		if (StringUtils.isNullOrEmpty(this.getSeason())) {
			return "Error: Season in " + this.getSitename() + " must not be empty! ";
		}
		System.out.println("EcotypeID: " + this.getEcotypeid());
		if (this.getEcotypeid() == null) {
			return "Error: Eco System in " + this.getSitename() + " must not be empty! ";
		}

		if (selectedSitePlantingType.getId() == -1) {
			return "Error: Planting Type in " + this.getSitename() + " must not be empty! ";
		}
		if (selectedAgroInfo.getHarvestdate() == null) {
			return "Error: Harvest Date Type in " + this.getSitename() + " must not be empty! ";
		}
		if (selectedAgroInfo.getSowingdate() == null) {
			return "Error: Sowing Date Type in " + this.getSitename() + " must not be empty! ";
		}

		if (StringUtils.isNullOrEmpty(selectedDesignInfo.getTreatmentstructure())) {

			return "Treatment Structure in " + this.getSitename() + "must not be empty!";
		}
		if (StringUtils.isNullOrEmpty(selectedDesignInfo.getDesignstructure())) {
			return "Design Structure in " + this.getSitename() + "must not be empty!";
		}
		if (StringUtils.isNullOrEmpty(selectedDesignInfo.getPlotsize())) {
			return "Plot size in " + this.getSitename() + "must not be empty!";

		}
		if (this.selectedAgroInfo.getSowingdate().compareTo(this.selectedAgroInfo.getHarvestdate()) > 0) {

			return "Error: Havest date must be greater than Transplanting/Sowing date in " + this.getSitename();
		}
		return null;

	}

	public String validateColumnOnly() {
		if (StringUtils.isNullOrEmpty(this.getSitename())) {
			return "Error: Site Name  must not be empty! ";
		}
		if (StringUtils.isNullOrEmpty(this.getSitelocation())) {
			return "Error: Location in " + this.getSitename() + " must not be empty! ";
		}
		if (StringUtils.isNullOrEmpty(this.getYear())) {
			return "Error: Year in " + this.getSitename() + " must not be empty! ";
		}
		if (StringUtils.isNullOrEmpty(this.getSeason())) {
			return "Error: Season in " + this.getSitename() + " must not be empty! ";
		}
		if (this.selectedLocation == null) {
			return "Error: Location in " + this.getSitename() + " does not exist in the database. Please add your location first or select any existing location.";
		}
		if (this.selectedLocation == null || this.selectedLocation.getId() == null) {
			return "Error: Location in " + this.getSitename() + " does not exist in the database. Please add your location first or select any existing location.";
		}

		return null;

	}

	public StudySiteInfoModel(StudySite s) {
		this.setEcotypeid(s.getEcotypeid());
		if (StringUtils.isNullOrEmpty(s.getSeason())) {
			System.out.println("Season is now null!");
			s.setSeason("");
		}
		this.setId(s.getId());
		this.setSeason(s.getSeason());
		this.setSitelocation(s.getSitelocation());
		this.setSitename(s.getSitename());
		this.setSoilph(s.getSoilph());
		this.setSoiltype(s.getSoiltype());
		this.setSoiltype(s.getSoiltype());
		this.setStudyid(s.getStudyid());
		this.setYear(s.getYear());
		this.setLocationid(s.getLocationid());
		if (s.getLocationid() != null)
			this.setSelectedLocation(new LocationManagerImpl().getLocationById(s.getLocationid()));

	}

	public StudySiteInfoModel() {

	}

	public StudyAgronomy getSelectedAgroInfo() {
		if (selectedAgroInfo == null)
			return new StudyAgronomy();
		return selectedAgroInfo;
	}

	public void setSelectedAgroInfo(StudyAgronomy selectedAgroInfo) {

		this.selectedAgroInfo = selectedAgroInfo;
	}

	public StudyDesign getSelectedDesignInfo() {

		return selectedDesignInfo;
	}

	public void setSelectedDesignInfo(StudyDesign selectedDesignInfo) {
		this.selectedDesignInfo = selectedDesignInfo;
	}

	public PlantingType getSelectedSitePlantingType() {

		return selectedSitePlantingType;
	}

	public void setSelectedSitePlantingType(PlantingType selectedSitePlantingType) {
		this.selectedSitePlantingType = selectedSitePlantingType;
		this.selectedAgroInfo.setPlantingtypeid(selectedSitePlantingType.getId());
	}

	public String getEcoSystem() {
		return ecoSystem;
	}

	public void setEcoSystem(String ecoSystem) {
		this.ecoSystem = ecoSystem;
	}

}