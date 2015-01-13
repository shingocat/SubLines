package org.strasa.web.browsestudy.view.model;

import java.util.ArrayList;
import java.util.List;

import org.strasa.middleware.manager.EcotypeManagerImpl;
import org.strasa.middleware.manager.PlantingTypeManagerImpl;
import org.strasa.middleware.manager.StudyAgronomyManagerImpl;
import org.strasa.middleware.manager.StudyDesignManagerImpl;
import org.strasa.middleware.manager.StudySiteManagerImpl;
import org.strasa.middleware.model.Ecotype;
import org.strasa.middleware.model.PlantingType;
import org.strasa.middleware.model.StudyAgronomy;
import org.strasa.middleware.model.StudyDesign;
import org.strasa.middleware.model.StudySite;
import org.strasa.web.common.api.ProcessTabViewModel;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

//import org.strasa.web.uploadstudy.view.model.StudySiteInfo.StudySiteInfoModel;

public class SiteInformation extends ProcessTabViewModel {
	private StudySiteManagerImpl studySiteMan;
	private StudyAgronomyManagerImpl studyAgroMan;
	private StudyDesignManagerImpl studyDesignMan;
	private EcotypeManagerImpl ecotypeMan;
	private PlantingTypeManagerImpl plantingtypeMan;

	private List<StudySiteInfoModel> sites;
	private List<StudyAgronomy> agroInfo;
	private List<StudyDesign> designInfo;
	private List<Ecotype> ecotypes;
	private List<PlantingType> plantingtypes;

	private StudySite selectedSite;
	private StudyAgronomy selectedAgroInfo = new StudyAgronomy();
	private StudyDesign selectedDesignInfo = new StudyDesign();
	private PlantingType selectedSitePlantingType; // .getPlantingTypeById(selectedAgroInfo.getPlantingtypeid());
	private Integer studyId;
	private boolean isRaw = false;
	protected boolean goToNextPage = true;
	private int selectedPlantingIndex = 0;

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

	public void setSites(List<StudySiteInfoModel> sites) {
		this.sites = sites;
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

	@NotifyChange("*")
	@Command("updateDesignInfo")
	public void updateDesignInfo(@BindingParam("studysiteid") Integer studysiteid) {
		System.out.println(studysiteid);
		selectedDesignInfo = studyDesignMan.getStudyDesign(studysiteid);
		selectedAgroInfo = studyAgroMan.getStudyAgronomy(studysiteid);
		selectedSitePlantingType = plantingtypeMan.getPlantingTypeById(studysiteid);
		//
		// siteInfo.selectedDesignInfo =
		// studyDesignMan.getStudyDesign(siteD.getId());
		// siteInfo.selectedAgroInfo =
		// studyAgroMan.getStudyAgronomy(siteD.getId());
		// siteInfo.selectedSitePlantingType =
		// plantingtypeMan.getPlantingTypeById(siteInfo.getSelectedAgroInfo().getPlantingtypeid());
		// selectedAgroInfo = agroInfo.get(studysiteid);
		// selectedDesignInfo = designInfo.get(studysiteid);
		// selectedSitePlantingType
		// =plantingtypeMan.getPlantingTypeById(studysiteid);
	}

	@Init
	public void init(@ExecutionArgParam("studyId") Integer studyId, @ExecutionArgParam("dataset") Integer dataset) {
		this.setStudyID(studyId);

		sites = new ArrayList<StudySiteInfoModel>();

		studySiteMan = new StudySiteManagerImpl(false);
		studyAgroMan = new StudyAgronomyManagerImpl();
		studyDesignMan = new StudyDesignManagerImpl();
		ecotypeMan = new EcotypeManagerImpl();
		plantingtypeMan = new PlantingTypeManagerImpl();

		ecotypes = ecotypeMan.getAllEcotypes();
		plantingtypes = plantingtypeMan.getAllPlantingTypes();
		designInfo = studyDesignMan.getAllStudyDesign();
		agroInfo = studyAgroMan.getAllStudyAgronomy();

		List<org.strasa.web.uploadstudy.view.pojos.StudySiteInfoModel> subsites = studySiteMan.getStudySiteByStudyId(studyID, dataset);
		if (!subsites.isEmpty()) {
			for (StudySite siteD : subsites) {
				System.out.println(Integer.toString(siteD.getId()));
				StudySiteInfoModel siteInfo = new StudySiteInfoModel(siteD);
				siteInfo.setSelectedDesignInfo(studyDesignMan.getStudyDesign(siteD.getId()));
				siteInfo.setSelectedAgroInfo(studyAgroMan.getStudyAgronomy(siteD.getId()));
				siteInfo.setSelectedSitePlantingType(plantingtypeMan.getPlantingTypeById(siteInfo.getSelectedAgroInfo().getPlantingtypeid()));
				sites.add(siteInfo);
			}
			selectedSite = sites.get(0);
			updateDesignInfo(0);
			selectedDesignInfo = studyDesignMan.getStudyDesign(subsites.get(0).getId());
			selectedAgroInfo = studyAgroMan.getStudyAgronomy(subsites.get(0).getId());
			selectedSitePlantingType = plantingtypeMan.getPlantingTypeById(selectedAgroInfo.getPlantingtypeid());
		}
	}

	public PlantingType getSelectedSitePlantingType() {
		return selectedSitePlantingType;
	}

	public void setSelectedSitePlantingType(PlantingType selectedSitePlantingType) {
		this.selectedSitePlantingType = selectedSitePlantingType;

		System.out.println(selectedSitePlantingType.getPlanting());
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public class StudySiteInfoModel extends StudySite {
		private Ecotype ecotype = new Ecotype();
		private StudyAgronomy selectedAgroInfo = new StudyAgronomy();
		private StudyDesign selectedDesignInfo = new StudyDesign();
		private PlantingType selectedSitePlantingType = new PlantingType();

		public StudySiteInfoModel(StudySite s) {
			this.setEcotypeid(s.getEcotypeid());
			try {
				this.setEcotype(getEcotypeById(s.getEcotypeid()));
			} catch (NullPointerException npe) {
				Ecotype e = new Ecotype();
				e.setEcotype("");
				this.setEcotype(e);
			}
			this.setSeason(s.getSeason());
			this.setSitelocation(s.getSitelocation());
			this.setSitename(s.getSitename());
			this.setSoilph(s.getSoilph());
			this.setSoiltype(s.getSoiltype());
			this.setSoiltype(s.getSoiltype());
			this.setStudyid(s.getStudyid());
			this.setYear(s.getYear());
			this.setId(s.getId());

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

		public PlantingType getSelectedSitePlantingType() {

			System.out.println(selectedSitePlantingType.getPlanting());
			return selectedSitePlantingType;
		}

		public void setSelectedSitePlantingType(PlantingType selectedSitePlantingType) {
			this.selectedSitePlantingType = selectedSitePlantingType;
		}

		public Ecotype getEcotype() {
			return ecotype;
		}

		public void setEcotype(Ecotype ecotype) {
			this.ecotype = ecotype;
		}

		public Ecotype getEcotypeById(int id) {
			return ecotypeMan.getEcotypeById(id);
		}

	}

}