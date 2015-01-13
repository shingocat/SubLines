package org.strasa.web.createfieldbook.view.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.strasa.middleware.manager.EcotypeManagerImpl;
import org.strasa.middleware.manager.LocationManagerImpl;
import org.strasa.middleware.manager.PlantingTypeManagerImpl;
import org.strasa.middleware.manager.SoilTypeManagerImpl;
import org.strasa.middleware.model.Ecotype;
import org.strasa.middleware.model.Location;
import org.strasa.middleware.model.PlantingType;
import org.strasa.middleware.model.StudyVariable;
import org.strasa.web.createfieldbook.view.pojos.SiteInformationModel;
import org.strasa.web.uploadstudy.view.model.AddLocation;
import org.strasa.web.uploadstudy.view.model.DataColumnChanged;
import org.strasa.web.utilities.FileUtilities;
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
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Window;

import com.mysql.jdbc.StringUtils;

public class FieldBookSite {

	Tab siteTab;

	/*
	 * DECLARATIONS
	 */

	List<Ecotype> ecotypes = new EcotypeManagerImpl().getAllEcotypes();
	List<String> soiltypes = new SoilTypeManagerImpl().getAllSoilType();
	List<PlantingType> plantingtypes = new PlantingTypeManagerImpl().getAllPlantingTypes();

	/*
	 * INITS
	 */
	@Init
	public void init(@ExecutionArgParam("SiteModel") SiteInformationModel siteModel, @ExecutionArgParam("SiteTab") Tab siteTab) {
		lstLocations = getAllLocations();
		this.site = siteModel;
		this.siteTab = siteTab;

		if (!StringUtils.isNullOrEmpty(site.getPlantingtype().getPlanting())) {
			if (site.getPlantingtype().getPlanting().equals("Transplanting"))
				labelDate = "Transplanting Date";
			else if (site.getPlantingtype().getId() == -1)
				labelDate = "Transplanting/Sowing Date";
			else
				labelDate = "Sowing Date";
		}

	}

	@SuppressWarnings("unchecked")
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) final Component view) {
		Selectors.wireComponents(view, this, false);
		Timer timer = new Timer(10);
		timer.setRepeats(false);
		timer.setPage(view.getPage());
		timer.addEventListener("onTimer", new EventListener() {
			public void onEvent(Event event) throws Exception {

				Combobox cmbTreatmentStructure = (Combobox) view.getFellow("cmbTreatmentStructure");
				if (!StringUtils.isNullOrEmpty(site.getDesign().getTreatmentstructure())) {
					if (site.getDesign().getTreatmentstructure().equals("One Factor"))
						cmbTreatmentStructure.setSelectedIndex(0);
					if (site.getDesign().getTreatmentstructure().equals("Two Factor Factorial"))
						cmbTreatmentStructure.setSelectedIndex(1);
					if (site.getDesign().getTreatmentstructure().equals("Three Factor Factorial"))
						cmbTreatmentStructure.setSelectedIndex(2);
				}
				if (cmbTreatmentStructure.getSelectedIndex() == 0) {
					((Textbox) view.getFellow("txtDRow2")).setValue("");
					((Textbox) view.getFellow("txtDRow3")).setValue("");
					((Textbox) view.getFellow("txtDRow4")).setValue("");

					view.getFellow("dRow2").setVisible(false);
					view.getFellow("dRow3").setVisible(false);
					view.getFellow("dRow4").setVisible(false);

				} else if (cmbTreatmentStructure.getSelectedIndex() == 1) {
					((Textbox) view.getFellow("txtDRow3")).setValue("");
					((Textbox) view.getFellow("txtDRow4")).setValue("");
					view.getFellow("dRow2").setVisible(true);
					view.getFellow("dRow3").setVisible(false);
					view.getFellow("dRow4").setVisible(false);

				} else if (cmbTreatmentStructure.getSelectedIndex() == 2) {
					view.getFellow("dRow2").setVisible(true);
					view.getFellow("dRow3").setVisible(true);
					view.getFellow("dRow4").setVisible(true);

				}

			}
		});

	}

	SiteInformationModel site;
	@Wire("#bbox_location")
	Bandbox bboxLocation;
	List<Location> lstLocations;
	private String labelDate = "Transplating/Sowing";
	private String lblLayoutFileName;
	private String lblGenotypeFileName;

	/*
	 * COMMANDS
	 */

	@Command
	public void addTrait(@ContextParam(ContextType.VIEW) Component view) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("parent", view);
		params.put("multiselect", true);
		params.put("filter", site.lstStudyVariable);
		Window popup = (Window) Executions.createComponents(DataColumnChanged.ZUL_PATH, view, params);
		popup.doModal();
	}

	@NotifyChange("site")
	@Command("refreshVarList")
	public void refreshList(@BindingParam("variable") ArrayList<StudyVariable> newVariable) {

		site.lstStudyVariable.addAll(newVariable);
	}

	@NotifyChange("site")
	@Command
	public void removeTrait(@BindingParam("trait") StudyVariable var) {
		site.lstStudyVariable.remove(var);
	}

	@Command
	public void updateTab() {
		siteTab.setLabel(site.getSitename());
		BindUtils.postGlobalCommand(null, null, "refreshSiteList", null);
	}

	@NotifyChange("filteredLocations")
	@Command
	public void doLocationSearch() {
		toggleBandBox(true);
		System.out.println("WORK");
		// BindUtils.postNotifyChange(null, null, FieldBookSite.this,
		// "filteredLocations");
		site.getLocation().setId(null);
	}

	@NotifyChange("labelDate")
	@Command
	public void updateLabelDate() {
		if (site.getPlantingtype().getPlanting().equals("Transplanting"))
			labelDate = "Transplanting Date";
		else if (site.getPlantingtype().getId() == -1)
			labelDate = "Transplanting/Sowing Date";
		else
			labelDate = "Sowing Date";
	}

	@Command
	public void autoFillBandbox() {

		if (!bboxLocation.isOpen()) {
			if (getFilteredLocations().isEmpty() && getFilteredLocations().getSize() != lstLocations.size()) {
				site.setLocation(getFilteredLocations().get(0));

			}
		} else {
			// updateDesignInfo(lstId);
		}
	}

	@Command
	public void openBandbox() {
		bboxLocation.open();
	}

	@Command
	public void setLocationRow(@BindingParam("location") Location location) {

		site.setLocation(location);
		BindUtils.postNotifyChange(null, null, this.site, "*");
		toggleBandBox(false);
	}

	@Command
	public void removeGenotype() {
		if (StringUtils.isNullOrEmpty(site.getLblGenotypeFileName()))
			return;
		site.setLblGenotypeFileName("");
		site.getFileGenotype().delete();
		site.setFileGenotype(null);
		BindUtils.postNotifyChange(null, null, site, "lblGenotypeFileName");

	}

	@Command
	public void removeLayout() {

		if (StringUtils.isNullOrEmpty(site.getLblLayoutFileName()))
			return;
		site.setLblLayoutFileName("");
		site.getFileLayout().delete();
		site.setFileLayout(null);
		BindUtils.postNotifyChange(null, null, site, "lblLayoutFileName");

	}

	@Command("uploadGenotype")
	public void uploadGenotype(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx, @ContextParam(ContextType.VIEW) Component view) {

		File tempFile = FileUtilities.getFileFromUpload(ctx, view);
		if (tempFile == null)
			return;

		site.setFileGenotype(tempFile);
		UploadEvent event = (UploadEvent) ctx.getTriggerEvent();
		site.setLblGenotypeFileName(event.getMedia().getName());
		BindUtils.postNotifyChange(null, null, site, "lblGenotypeFileName");
	}

	@Command("uploadLayout")
	public void uploadLayout(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx, @ContextParam(ContextType.VIEW) Component view) {

		File tempFile = FileUtilities.getFileFromUpload(ctx, view);
		if (tempFile == null)
			return;

		site.setFileLayout(tempFile);
		UploadEvent event = (UploadEvent) ctx.getTriggerEvent();
		site.setLblLayoutFileName(event.getMedia().getName());
		BindUtils.postNotifyChange(null, null, site, "lblLayoutFileName");
	}

	@Command("addLocation")
	public void addLocation(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx, @ContextParam(ContextType.VIEW) Component view) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parent", view);
		params.put("locname", site.getLocation().getLocationname());

		Window popup = (Window) Executions.createComponents(AddLocation.ZUL_PATH, view, params);

		popup.doModal();

	}

	@NotifyChange("site")
	@Command("newLocationModel")
	public void refreshLocation(@BindingParam("locationModel") Location newValue) {
		site.setLocation(newValue);

	}

	/*
	 * COMMON METHODS
	 */
	public void toggleBandBox(boolean isOpen) {

		if (isOpen) {
			bboxLocation.open();
			bboxLocation.setFocus(true);
		} else {
			bboxLocation.close();
			bboxLocation.setFocus(false);
		}

	}

	public ListModelList<Location> getFilteredLocations() {

		ListModelList<Location> returnVal = new ListModelList<Location>();
		if (StringUtils.isNullOrEmpty(site.getLocation().getLocationname())) {

			return getAllLocations();

		}
		String tempSelected = null;

		ListModelList<Location> lstLocations = getAllLocations();
		for (Location loc : lstLocations) {
			if (loc.getLocationname().toLowerCase().startsWith(site.getLocation().getLocationname().toLowerCase())) {
				returnVal.add(loc);
				if (tempSelected == null)
					tempSelected = loc.getLocationname();
			}
		}

		return returnVal;

	}

	public ListModelList<Location> getAllLocations() {
		ListModelList<Location> lstLocations = new ListModelList<Location>();
		lstLocations.addAll(new LocationManagerImpl().getAllLocations());
		return lstLocations;
	}

	/*
	 * GETTERS AND SETTERS
	 */

	public List<Ecotype> getEcotypes() {
		return ecotypes;
	}

	public void setEcotypes(List<Ecotype> ecotypes) {
		this.ecotypes = ecotypes;
	}

	public List<String> getSoiltypes() {
		return soiltypes;
	}

	public void setSoiltypes(List<String> soiltypes) {
		this.soiltypes = soiltypes;
	}

	public List<Location> getLstLocations() {
		return lstLocations;
	}

	public void setLstLocations(List<Location> lstLocations) {
		this.lstLocations = lstLocations;
	}

	public SiteInformationModel getSite() {
		return site;
	}

	public void setSite(SiteInformationModel site) {
		this.site = site;
	}

	public List<PlantingType> getPlantingtypes() {
		return plantingtypes;
	}

	public void setPlantingtypes(List<PlantingType> plantingtypes) {
		this.plantingtypes = plantingtypes;
	}

	public String getLabelDate() {
		return labelDate;
	}

	public void setLabelDate(String labelDate) {
		this.labelDate = labelDate;
	}

	public String getLblLayoutFileName() {
		return lblLayoutFileName;
	}

	public void setLblLayoutFileName(String lblLayoutFileName) {
		this.lblLayoutFileName = lblLayoutFileName;
	}

	public String getLblGenotypeFileName() {
		return lblGenotypeFileName;
	}

	public void setLblGenotypeFileName(String lblGenotypeFileName) {
		this.lblGenotypeFileName = lblGenotypeFileName;
	}

}
