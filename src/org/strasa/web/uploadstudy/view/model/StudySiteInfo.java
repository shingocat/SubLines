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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.strasa.middleware.manager.EcotypeManagerImpl;
import org.strasa.middleware.manager.LocationManagerImpl;
import org.strasa.middleware.manager.PlantingTypeManagerImpl;
import org.strasa.middleware.manager.SoilTypeManagerImpl;
import org.strasa.middleware.manager.StudyAgronomyManagerImpl;
import org.strasa.middleware.manager.StudyDesignManagerImpl;
import org.strasa.middleware.manager.StudyManagerImpl;
import org.strasa.middleware.manager.StudyRawDataManagerImpl;
import org.strasa.middleware.manager.StudySiteManagerImpl;
import org.strasa.middleware.model.Ecotype;
import org.strasa.middleware.model.Location;
import org.strasa.middleware.model.PlantingType;
import org.strasa.middleware.model.StudyAgronomy;
import org.strasa.middleware.model.StudyDesign;
import org.strasa.middleware.model.StudySite;
import org.strasa.web.common.api.ProcessTabViewModel;
import org.strasa.web.uploadstudy.view.pojos.StudySiteInfoModel;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.mysql.jdbc.StringUtils;

public class StudySiteInfo extends ProcessTabViewModel {
	private StudySiteManagerImpl studySiteMan;
	private StudyAgronomyManagerImpl studyAgroMan;
	private StudyDesignManagerImpl studyDesignMan;
	private EcotypeManagerImpl ecotypeMan;
	private PlantingTypeManagerImpl plantingtypeMan;

	private ListModelList<StudySiteInfoModel> sites;
	private List<StudyAgronomy> agroInfo;
	private List<StudyDesign> designInfo;
	private List<Ecotype> ecotypes;
	private List<PlantingType> plantingtypes;
	private boolean noSite = false;
	private StudySite selectedSite;
	private StudyAgronomy selectedAgroInfo = new StudyAgronomy();;
	private StudyDesign selectedDesignInfo = new StudyDesign();;
	private PlantingType selectedSitePlantingType = new PlantingType(); // .getPlantingTypeById(selectedAgroInfo.getPlantingtypeid());
	private int selectedID = 0;
	private boolean pageLoaded = false;
	protected boolean goToNextPage = true;
	private int selectedPlantingIndex = 0;
	private boolean applyToAll = false;
	private String labelDate;
	private boolean hasBeenProcessed = false;
	private ListModelList<Location> lstLocations = new ListModelList<Location>();
	private ListModelList<StudySiteInfoModel> previousSites;
	private ArrayList<String> soilTypes = new ArrayList<String>();

	public ArrayList<String> getSoilTypes() {
		return soilTypes;
	}

	public void setSoilTypes(ArrayList<String> soilTypes) {
		this.soilTypes = soilTypes;
	}

	public ListModelList<StudySiteInfoModel> getPreviousSites() {
		return previousSites;
	}

	public void setPreviousSites(ListModelList<StudySiteInfoModel> previousSites) {
		this.previousSites = previousSites;
	}

	@Wire("#mainGrid")
	Grid mainGrid;
	@Wire("#cmbTreatmentStructure")
	Combobox cmbTreatmentStructure;
	private int x;

	@Command
	public void newLocationModel(@BindingParam("locationModel") Location newValue) {

		lstLocations.add(newValue);

		for (int i = 0; i < sites.getSize(); i++) {

			if (sites.get(i).getSitelocation().equals(newValue.getLocationname())) {
				sites.get(i).setSelectedLocation(newValue);
				BindUtils.postNotifyChange(null, null, this.sites.get(i), "*");
			}

		}

	}

	@Command
	public void setSiteRow(@BindingParam("model") StudySiteInfoModel model) {
		sites.set(selectedID, model);
		BindUtils.postNotifyChange(null, null, this.sites.get(selectedID), "*");
		updateDesignInfo(selectedID);
		BindUtils.postNotifyChange(null, null, this.selectedAgroInfo, "*");
		BindUtils.postNotifyChange(null, null, this.selectedDesignInfo, "*");
		BindUtils.postNotifyChange(null, null, this.selectedSitePlantingType, "*");
	}

	@Command("addLocation")
	public void addLocation(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx, @ContextParam(ContextType.VIEW) Component view, @BindingParam("loc") StudySiteInfoModel model) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parent", view);
		params.put("locname", model.selectedLocation.getLocationname());

		Window popup = (Window) Executions.createComponents(AddLocation.ZUL_PATH, view, params);

		popup.doModal();

	}

	public Location getLocationById(int id) {
		for (Location loc : lstLocations) {
			if (loc.getId() == id)
				return loc;
		}
		return null;
	}

	public boolean isApplyToAll() {
		return applyToAll;
	}

	@NotifyChange("*")
	public void setApplyToAll(boolean applyToAll) {
		this.applyToAll = applyToAll;
		if (!applyToAll)
			updateDesignInfo(selectedID);
	}

	public int getSelectedPlantingIndex() {
		return selectedPlantingIndex;
	}

	public void setSelectedPlantingIndex(int selectedPlantingIndex) {
		this.selectedPlantingIndex = selectedPlantingIndex;
	}

	public List<PlantingType> getPlantingtypes() {

		return plantingtypes;
	}

	public void setPlantingtypes(List<PlantingType> plantingtypes) {
		this.plantingtypes = plantingtypes;
	}

	public PlantingType getSelectedSitePlantingType() {
		return selectedSitePlantingType;
	}

	@NotifyChange("labelDate")
	public void setSelectedSitePlantingType(PlantingType selectedSitePlantingType) {
		this.selectedSitePlantingType = selectedSitePlantingType;
		this.selectedAgroInfo.setPlantingtypeid(selectedSitePlantingType.getId());
		this.sites.get(selectedID).selectedSitePlantingType = selectedSitePlantingType;

		if (selectedSitePlantingType.getPlanting().equals("Transplanting"))
			labelDate = "Transplanting Date";
		else if (selectedSitePlantingType.getId() == -1)
			labelDate = "Transplanting/Sowing Date";
		else
			labelDate = "Sowing Date";
		System.out.print("OTU");
	}

	public StudyAgronomy getSelectedAgroInfo() {
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

	public List<StudyAgronomy> getAgroInfo() {
		return agroInfo;
	}

	public void setAgroInfo(List<StudyAgronomy> agroInfo) {
		this.agroInfo = agroInfo;
	}

	public List<StudyDesign> getDesignInfo() {
		return designInfo;
	}

	public void setDesignInfo(List<StudyDesign> designInfo) {
		this.designInfo = designInfo;
	}

	public List<Ecotype> getEcotypes() {
		return ecotypes;
	}

	public void setEcotypes(List<Ecotype> ecotypes) {
		this.ecotypes = ecotypes;
	}

	public List<StudySiteInfoModel> getSites() {
		return sites;
	}

	public void setSites(ListModelList<StudySiteInfoModel> sites) {
		this.sites = sites;
	}

	public void toggleBandBox(boolean isOpen, int row, int col) {

		if (mainGrid == null)
			System.out.println("Grid is null!!");
		Bandbox bandbox = (Bandbox) mainGrid.getRows().getChildren().get(row).getChildren().get(col).getFirstChild();
		if (isOpen) {
			bandbox.open();
			bandbox.setFocus(true);
		} else {
			bandbox.close();
			bandbox.setFocus(false);
		}

	}

	public Bandbox getBandBox(int row, int col) {
		return (Bandbox) mainGrid.getRows().getChildren().get(row).getChildren().get(col).getFirstChild();
	}

	public Button getAddButton(int row, int col) {
		return (Button) mainGrid.getRows().getChildren().get(row).getChildren().get(col).getLastChild();
	}

	private StudyDesign getDesignInfoBySiteID(Integer id) {
		// TODO Auto-generated method stub
		for (StudyDesign d : designInfo) {
			if (d.getStudysiteid() == id) {
				System.out.println("Selected Design info id: " + d.getStudysiteid());
				return d;
			}
		}
		return null;
	}

	private StudyAgronomy getAgroInfoBySiteID(Integer id) {
		// TODO Auto-generated method stub
		for (StudyAgronomy a : agroInfo) {
			if (a.getStudysiteid() == id) {
				System.out.println("Selected Agronomy info id: " + a.getStudysiteid());
				return a;
			}
		}
		return null;
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) final Component view) {
		Selectors.wireComponents(view, this, false);
		// BindUtils.postNotifyChange(null, null, this, "*");

		/*
		 * Timer timer = new Timer(0); timer.setRepeats(false);
		 * timer.setPage(view.getPage()); timer.addEventListener("onTimer", new
		 * EventListener() { public void onEvent(Event event) throws Exception {
		 * 
		 * (Combobox)view.getFellow("dRow2").setEnabled(false);
		 * view.getFellow("dRow3").setVisible(false);
		 * view.getFellow("dRow4").setVisible(false); } });
		 */

		Events.echoEvent("onChange", view.getFellow("cmbTreatmentStructure"), null);
		pageLoaded = true;
	}

	public String getLabelDate() {
		return labelDate;
	}

	public void setLabelDate(String labelDate) {
		this.labelDate = labelDate;
	}

	@Command("updateDesignInfo")
	public void updateDesignInfo(@BindingParam("id") Integer id) {

		// if(selectedID == id && selectedID != 0) return;
		selectedID = id;

		if (applyToAll)
			return;
		sites.get(selectedID).selectedPlantingIndex = selectedPlantingIndex;
		selectedAgroInfo = sites.get(id).getSelectedAgroInfo();
		selectedDesignInfo = sites.get(id).getSelectedDesignInfo();
		selectedSitePlantingType = sites.get(id).getSelectedSitePlantingType();

		// setSelectedAgroInfo(getAgroInfoBySiteID(id));
		// setSelectedDesignInfo(getDesignInfoBySiteID(id));
		// selectedSitePlantingType = plantingtypes.get(selectedAgroInfo
		// .getPlantingtypeid() - 1); //
		// .getPlantingTypeById(selectedAgroInfo.getPlantingtypeid());
		System.out.println("selected row id: " + Integer.toString(id));

		if (selectedSitePlantingType.getPlanting().equals("Transplanting"))
			labelDate = "Transplanting Date";
		else if (selectedSitePlantingType.getId() == -1)
			labelDate = "Transplanting/Sowing Date";
		else
			labelDate = "Sowing Date";

		BindUtils.postNotifyChange(null, null, StudySiteInfo.this, "selectedAgroInfo");
		BindUtils.postNotifyChange(null, null, StudySiteInfo.this, "selectedDesignInfo");
		BindUtils.postNotifyChange(null, null, StudySiteInfo.this, "selectedSitePlantingType");
		BindUtils.postNotifyChange(null, null, StudySiteInfo.this, "labelDate");

		if (pageLoaded) {
			Events.echoEvent("onChange", cmbTreatmentStructure, null);
			System.out.println("onChange called");
			cmbTreatmentOnChange(cmbTreatmentStructure);
		}

	}

	@Command("updateSelectedSitePlantingType")
	public void updateSelectedSitePlantingType() {
		selectedAgroInfo.setPlantingtypeid(selectedSitePlantingType.getId());
	}

	@Command("saveSiteAgroDesignInfo")
	public void saveSiteAgroDesignInfo() {

		List<StudySite> lstSites = new ArrayList<StudySite>();
		List<StudyAgronomy> lstAgro = new ArrayList<StudyAgronomy>();
		List<StudyDesign> designInfo = new ArrayList<StudyDesign>();
		for (StudySiteInfoModel data : sites) {
			StudySite siteData = data;
			lstAgro.add(data.getSelectedAgroInfo());
			designInfo.add(data.selectedDesignInfo);
		}
		studySiteMan.updateStudySite(lstSites);
		studyAgroMan.updateStudyAgronomy(agroInfo);
		studyDesignMan.updateStudyDesign(designInfo);

		Messagebox.show("Changes saved.");
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean validateTab() {
		x = -1;
		for (StudySiteInfoModel site : sites) {
			x++;
			if (!applyToAll) {
				if (!StringUtils.isNullOrEmpty(site.validateAll())) {

					if (site.validateAll().startsWith("Error: Location in ")) {
						Messagebox.show(site.validateAll() + " Do you want to add a new location?", "Upload Error", Messagebox.OK | Messagebox.CANCEL, Messagebox.EXCLAMATION, new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									Events.sendEvent("onClick", getAddButton(x, 1), getAddButton(x, 1));
								}
							}
						});
					} else
						Messagebox.show(site.validateAll(), "Upload Error", Messagebox.OK, Messagebox.EXCLAMATION);

					return false;
				}
			} else {
				if (!StringUtils.isNullOrEmpty(site.validateColumnOnly())) {

					if (site.validateColumnOnly().startsWith("Error: Location in ")) {
						Messagebox.show(site.validateColumnOnly() + " Do you want to add a new location?", "Upload Error", Messagebox.OK | Messagebox.CANCEL, Messagebox.EXCLAMATION, new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event evt) throws InterruptedException {
								if (evt.getName().equals("onOK")) {
									Events.sendEvent("onClick", getAddButton(x, 1), getAddButton(x, 1));
								}
							}
						});
					} else
						Messagebox.show(site.validateColumnOnly(), "Upload Error", Messagebox.OK, Messagebox.EXCLAMATION);

					return false;
				}
				if (selectedSitePlantingType.getId() == -1) {
					Messagebox.show("Planting type must not be empty!", "Upload Error", Messagebox.OK, Messagebox.EXCLAMATION);
				}
				if (selectedAgroInfo.getHarvestdate() == null) {
					Messagebox.show("Harvest date must not be empty!", "Upload Error", Messagebox.OK, Messagebox.EXCLAMATION);
					return false;
				}
				if (selectedAgroInfo.getSowingdate() == null) {
					Messagebox.show("Sowing date type must not be empty!", "Upload Error", Messagebox.OK, Messagebox.EXCLAMATION);
					return false;
				}
				if (StringUtils.isNullOrEmpty(selectedDesignInfo.getTreatmentstructure())) {
					Messagebox.show("Treatment Structure must not be empty!", "Upload Error", Messagebox.OK, Messagebox.EXCLAMATION);
					return false;
				}
				if (StringUtils.isNullOrEmpty(selectedDesignInfo.getDesignstructure())) {
					Messagebox.show("Design Structure must not be empty!", "Upload Error", Messagebox.OK, Messagebox.EXCLAMATION);
					return false;
				}
				if (StringUtils.isNullOrEmpty(selectedDesignInfo.getPlotsize())) {
					Messagebox.show("Plot size must not be empty!", "Upload Error", Messagebox.OK, Messagebox.EXCLAMATION);
					return false;
				}

				if (selectedAgroInfo.getSowingdate().compareTo(selectedAgroInfo.getHarvestdate()) > 0) {
					Messagebox.show("Havest date must be greater than Transplanting/Sowing date!", "Upload Error", Messagebox.OK, Messagebox.EXCLAMATION);
					return false;
				}

			}

		}

		System.out.println("LOOP : " + sites.size());

		List<StudySite> lstSites = new ArrayList<StudySite>();
		List<StudyAgronomy> lstAgro = new ArrayList<StudyAgronomy>();
		List<StudyDesign> designInfo = new ArrayList<StudyDesign>();
		StudySiteManagerImpl siteMan = new StudySiteManagerImpl(isRaw);
		boolean renewData = false;
		if (this.isDataReUploaded || hasBeenProcessed) {
			siteMan.removeSiteByStudyId(this.getStudyID(), this.getDataset().getId());
			renewData = true;
		}

		if (applyToAll) {

			selectedAgroInfo = sites.get(selectedID).selectedAgroInfo;
			selectedDesignInfo = sites.get(selectedID).selectedDesignInfo;
		}
		for (StudySiteInfoModel data : sites) {
			data.setStudyid(this.getStudyID());
			data.setDataset(this.dataset.getId());
			if (applyToAll)
				data.selectedAgroInfo.setPlantingtypeid(selectedSitePlantingType.getId());
			else
				data.selectedAgroInfo.setPlantingtypeid(data.selectedSitePlantingType.getId());

			if (data.getId() == null || renewData) {

				System.out.println("ADD MODE");
				StudySite siteData = data;
				siteMan.addStudySite(siteData);

				if (applyToAll) {

					selectedAgroInfo.setStudysiteid(data.getId());
					selectedDesignInfo.setStudysiteid(data.getId());

					studyAgroMan.addStudyAgronomy(selectedAgroInfo);
					studyDesignMan.addStudyDesign(selectedDesignInfo);
				} else {
					data.selectedAgroInfo.setStudysiteid(data.getId());
					data.selectedDesignInfo.setStudysiteid(data.getId());
					studyAgroMan.addStudyAgronomy(data.selectedAgroInfo);

					studyDesignMan.addStudyDesign(data.selectedDesignInfo);
				}
			} else {

				siteMan.updateStudySite(data);
				System.out.println("EDIT MODE");
				if (applyToAll) {
					selectedAgroInfo.setStudysiteid(data.getId());
					selectedDesignInfo.setStudysiteid(data.getId());
					studyAgroMan.updateStudyAgronomy(selectedAgroInfo);
					studyDesignMan.updateStudyDesign(selectedDesignInfo);

				} else {
					studyAgroMan.updateStudyAgronomy(data.selectedAgroInfo);
					studyDesignMan.updateStudyDesign(data.selectedDesignInfo);

				}

			}

		}

		// hasBeenProcessed = false;
		this.reloadNext = true;
		return goToNextPage;
	}

	@Command
	public void setLocationRow(@BindingParam("loc") Location loc) {

		sites.get(selectedID).setSelectedLocation(loc);
		BindUtils.postNotifyChange(null, null, this.sites.get(selectedID), "*");
		toggleBandBox(false, selectedID, 1);
	}

	@Command
	public void doLocationSearch(@BindingParam("id") int lstId) {
		toggleBandBox(true, lstId, 1);

		BindUtils.postNotifyChange(null, null, this.sites.get(lstId), "*");
		sites.get(lstId).selectedLocation.setId(null);
	}

	@Command
	public void doSiteSearch(@BindingParam("id") int lstId) {
		toggleBandBox(true, lstId, 0);

		BindUtils.postNotifyChange(null, null, this.sites.get(lstId), "*");
	}

	@Command
	public void openBandbox(@BindingParam("id") int lstId) {
		getBandBox(lstId, 1).open();
		updateDesignInfo(lstId);
	}

	@Command
	public void openSiteBandbox(@BindingParam("id") int lstId) {
		getBandBox(lstId, 0).open();
		updateDesignInfo(lstId);
	}

	@Command
	public void autoFillBandbox(@BindingParam("id") int lstId) {
		Bandbox bbox = getBandBox(lstId, 1);
		if (!bbox.isOpen()) {
			if (!sites.get(lstId).getFilteredLocations().isEmpty() && sites.get(lstId).getFilteredLocations().getSize() != lstLocations.getSize()) {
				sites.get(lstId).selectedLocation = sites.get(lstId).getFilteredLocations().get(0);

			}
		} else {
			updateDesignInfo(lstId);
		}
	}

	Location getLocationById(Integer locationID) {
		return new LocationManagerImpl().getLocationById(locationID);
	}

	Location getLocationByName(String locationName) {
		return new LocationManagerImpl().getLocationByLocationName(locationName);
	}

	@Init
	public void init(@ExecutionArgParam("uploadModel") ProcessTabViewModel uploadModel) {

		Clients.showBusy("Loading data please wait...");

		this.initValues(uploadModel);
		studySiteMan = new StudySiteManagerImpl(isRaw);
		studyAgroMan = new StudyAgronomyManagerImpl();
		studyDesignMan = new StudyDesignManagerImpl();
		ecotypeMan = new EcotypeManagerImpl();
		plantingtypeMan = new PlantingTypeManagerImpl();
		sites = new ListModelList<StudySiteInfoModel>();
		previousSites = new ListModelList<StudySiteInfoModel>();

		PlantingType blankPlantingType = new PlantingType();
		blankPlantingType.setId(-1);
		blankPlantingType.setPlanting("");
		selectedSitePlantingType = blankPlantingType;

		ecotypes = ecotypeMan.getAllEcotypes();

		plantingtypes = plantingtypeMan.getAllPlantingTypes();
		plantingtypes.add(0, blankPlantingType);

		lstLocations.addAll(new LocationManagerImpl().getAllLocations());

		StudyRawDataManagerImpl studyRawMan = new StudyRawDataManagerImpl(isRaw);

		String studyStartYear = new StudyManagerImpl().getStudyById(this.getStudyID()).getStartyear();

		if (studySiteMan.isSiteRecordExist(this.getStudyID()) && !this.isDataReUploaded) {
			sites.addAll(studySiteMan.getStudySiteByStudyId(this.getStudyID(), this.dataset.getId()));
			System.out.println("Site exist");

			for (StudySiteInfoModel site : sites) {
				for (Ecotype eco : ecotypes) {
					if (eco.getId() == site.getEcotypeid())
						site.selectedEcotype = eco;
				}
				for (PlantingType plant : plantingtypes) {
					if (plant.getId() == site.selectedAgroInfo.getPlantingtypeid())
						site.selectedSitePlantingType = plant;
				}

				site.isUpdateMode = true;
			}

		} else {
			List<StudySite> lstSiteRaw = studyRawMan.getStudySiteInfo(this.getStudyID(), dataset.getId());

			System.out.println("SITE COUNT: " + lstSiteRaw.size());
			for (StudySite siteData : lstSiteRaw) {

				StudySiteInfoModel siteInfo = new StudySiteInfoModel(siteData);

				if (StringUtils.isNullOrEmpty(siteInfo.getYear())) {

					siteInfo.setYear(studyStartYear);
					siteInfo.isYearAuto = true;
				}
				if (!StringUtils.isNullOrEmpty(siteInfo.getSitelocation())) {
					Location newLoc = new Location();
					Location exLoc = getLocationByName(siteInfo.getSitelocation());

					newLoc.setLocationname(siteInfo.getSitelocation());
					if (exLoc == null)
						siteInfo.setSelectedLocation(newLoc);
					else
						siteInfo.setSelectedLocation(exLoc);
				}

				siteInfo.selectedDesignInfo = new StudyDesign();
				siteInfo.selectedSitePlantingType = blankPlantingType;
				siteInfo.selectedAgroInfo = new StudyAgronomy();
				System.out.println("NO PLANTING TYPE");
				siteInfo.selectedAgroInfo.setStudysiteid(this.getStudyID());
				siteInfo.selectedDesignInfo.setStudyid(this.studyID);
				sites.add(siteInfo);
				System.out.println("SITE COUNT: " + sites.size());

			}
			if (this.isDataReUploaded) {
				previousSites.addAll(studySiteMan.getStudySiteByStudyId(this.getStudyID(), null));
				System.out.println("Previous Sites");

				for (StudySiteInfoModel site : previousSites) {
					for (Ecotype eco : ecotypes) {
						if (eco.getId() == site.getEcotypeid())
							site.selectedEcotype = eco;
					}
					for (PlantingType plant : plantingtypes) {
						if (plant.getId() == site.selectedAgroInfo.getPlantingtypeid())
							site.selectedSitePlantingType = plant;
					}

					site.isUpdateMode = true;
				}
			}

		}

		boolean hasSite = studySiteMan.hasSiteHeader(this.studyID, this.dataset.getId());

		if (!hasSite) {
			sites.removeRange(1, sites.size() - 1);

		}

		System.out.println("SITE FINAL COUNT: " + sites.size());
		selectedSite = sites.get(0);

		updateDesignInfo(0);

		soilTypes = new SoilTypeManagerImpl().getAllSoilType();
		Clients.clearBusy();
	}

	@Command
	@NotifyChange("*")
	public void applyToAll() {
		for (int i = 0; i < sites.size(); i++) {
			sites.get(i).selectedAgroInfo = selectedAgroInfo;
			sites.get(i).selectedDesignInfo = selectedDesignInfo;
			sites.get(i).selectedPlantingIndex = selectedPlantingIndex;
			sites.get(i).selectedSitePlantingType = selectedSitePlantingType;

		}
	}

	@Command
	public void cmbTreatmentOnChange(@ContextParam(ContextType.VIEW) Component view) {
		System.out.println("Called");
		Combobox cmbTreatmentStructure = (Combobox) view.getFellow("cmbTreatmentStructure");
		Textbox txtDRow2 = (Textbox) view.getFellow("txtDRow2");
		Textbox txtDRow3 = (Textbox) view.getFellow("txtDRow3");
		Textbox txtDRow4 = (Textbox) view.getFellow("txtDRow4");
		if (StringUtils.isNullOrEmpty(selectedDesignInfo.getTreatmentstructure()))
			return;
		if (selectedDesignInfo.getTreatmentstructure().equals("One Factor")) {
			txtDRow2.setValue("");
			txtDRow3.setValue("");
			txtDRow4.setValue("");

			txtDRow2.setDisabled(true);
			txtDRow3.setDisabled(true);
			txtDRow4.setDisabled(true);

		} else if (selectedDesignInfo.getTreatmentstructure().equals("Two Factor Factorial")) {
			txtDRow3.setValue("");
			txtDRow4.setValue("");
			txtDRow2.setDisabled(false);
			txtDRow3.setDisabled(true);
			txtDRow4.setDisabled(true);

		} else if (selectedDesignInfo.getTreatmentstructure().equals("Three Factor Factorial")) {
			txtDRow2.setDisabled(false);
			txtDRow3.setDisabled(false);
			txtDRow4.setDisabled(false);

		} else {
			System.out.println("TEST NONE" + selectedDesignInfo.getTreatmentstructure());
		}
	}

}