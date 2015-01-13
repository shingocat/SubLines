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
package org.strasa.web.createfieldbook.view.pojos;

import java.io.File;
import java.util.ArrayList;

import org.strasa.middleware.model.Ecotype;
import org.strasa.middleware.model.Location;
import org.strasa.middleware.model.PlantingType;
import org.strasa.middleware.model.StudyAgronomy;
import org.strasa.middleware.model.StudyDesign;
import org.strasa.middleware.model.StudySite;
import org.strasa.middleware.model.StudyVariable;

import com.mysql.jdbc.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class SiteInformationModel.
 */
public class SiteInformationModel extends StudySite implements Cloneable {

	public SiteInformationModel() {

	}

	private boolean selected = true;

	public ArrayList<StudyVariable> lstStudyVariable = new ArrayList<StudyVariable>();

	/** The file layout. */
	private File fileLayout;

	/** The file genotype. */
	private File fileGenotype;

	/** The design. */
	private StudyDesign design = new StudyDesign();

	/** The agronomy. */
	private StudyAgronomy agronomy = new StudyAgronomy();

	/** The location. */
	private Location location = new Location();;

	/** The lst variable. */

	private ArrayList<StudyVariable> lstVariable;

	private Ecotype ecotype;
	private PlantingType plantingtype = new PlantingType();
	private boolean headerAutoMatch = true;
	private String headerLayout;
	private String headerGenotype;

	private String lblGenotypeFileName;

	private String lblLayoutFileName;

	public Ecotype getEcotype() {
		return ecotype;
	}

	public void setEcotype(Ecotype ecotype) {
		this.ecotype = ecotype;
		this.setEcotypeid(ecotype.getId());
	}

	/**
	 * Gets the design.
	 * 
	 * @return the design
	 */
	public StudyDesign getDesign() {
		return design;
	}

	/**
	 * Sets the design.
	 * 
	 * @param design
	 *            the new design
	 */
	public void setDesign(StudyDesign design) {
		this.design = design;
	}

	/**
	 * Gets the agronomy.
	 * 
	 * @return the agronomy
	 */
	public StudyAgronomy getAgronomy() {
		return agronomy;
	}

	/**
	 * Sets the agronomy.
	 * 
	 * @param agronomy
	 *            the new agronomy
	 */
	public void setAgronomy(StudyAgronomy agronomy) {
		this.agronomy = agronomy;
	}

	/**
	 * Gets the location.
	 * 
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Sets the location.
	 * 
	 * @param location
	 *            the new location
	 */
	public void setLocation(Location location) {
		this.location = location;
		this.setLocationid(location.getId());
	}

	/**
	 * Gets the file layout.
	 * 
	 * @return the file layout
	 */
	public File getFileLayout() {
		return fileLayout;
	}

	/**
	 * Sets the file layout.
	 * 
	 * @param fileLayout
	 *            the new file layout
	 */
	public void setFileLayout(File fileLayout) {
		this.fileLayout = fileLayout;
	}

	/**
	 * Gets the file genotype.
	 * 
	 * @return the file genotype
	 */
	public File getFileGenotype() {
		return fileGenotype;
	}

	/**
	 * Sets the file genotype.
	 * 
	 * @param fileGenotype
	 *            the new file genotype
	 */
	public void setFileGenotype(File fileGenotype) {
		this.fileGenotype = fileGenotype;
	}

	/**
	 * Gets the lst variable.
	 * 
	 * @return the lst variable
	 */
	public ArrayList<StudyVariable> getLstVariable() {
		return lstVariable;
	}

	/**
	 * Sets the lst variable.
	 * 
	 * @param lstVariable
	 *            the new lst variable
	 */
	public void setLstVariable(ArrayList<StudyVariable> lstVariable) {
		this.lstVariable = lstVariable;
	}

	public PlantingType getPlantingtype() {
		return plantingtype;
	}

	public void setPlantingtype(PlantingType plantingtype) {
		this.plantingtype = plantingtype;
	}

	public ArrayList<StudyVariable> getLstStudyVariable() {
		return lstStudyVariable;
	}

	public void setLstStudyVariable(ArrayList<StudyVariable> lstStudyVariable) {
		this.lstStudyVariable = lstStudyVariable;
	}

	public boolean isHeaderAutoMatch() {
		return headerAutoMatch;
	}

	public void setHeaderAutoMatch(boolean headerAutoMatch) {
		this.headerAutoMatch = headerAutoMatch;
	}

	public String getHeaderLayout() {
		return headerLayout;
	}

	public void setHeaderLayout(String headerLayout) {
		this.headerLayout = headerLayout;
	}

	public String getHeaderGenotype() {
		return headerGenotype;
	}

	public void setHeaderGenotype(String headerGenotype) {
		this.headerGenotype = headerGenotype;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public SiteInformationModel(SiteInformationModel site) {

		this.selected = site.selected;
		this.lstStudyVariable = site.lstStudyVariable;
		this.fileLayout = site.fileLayout;
		this.fileGenotype = site.fileGenotype;
		this.design = site.design;
		this.agronomy = site.agronomy;
		this.location = site.location;
		this.lstVariable = site.lstVariable;
		this.ecotype = site.ecotype;
		this.plantingtype = site.plantingtype;
		this.headerAutoMatch = site.headerAutoMatch;
		this.headerLayout = site.headerLayout;
		this.headerGenotype = site.headerGenotype;
		this.setSitename(site.getSitename());
		// this.setSitelocation(site.getSiteLocation());

	}

	/**
	 * @return the lblGenotypeFileName
	 */
	public String getLblGenotypeFileName() {
		return lblGenotypeFileName;
	}

	/**
	 * @param lblGenotypeFileName
	 *            the lblGenotypeFileName to set
	 */
	public void setLblGenotypeFileName(String lblGenotypeFileName) {
		this.lblGenotypeFileName = lblGenotypeFileName;
	}

	/**
	 * @return the lblLayoutFileName
	 */
	public String getLblLayoutFileName() {
		return lblLayoutFileName;
	}

	/**
	 * @param lblLayoutFileName
	 *            the lblLayoutFileName to set
	 */
	public void setLblLayoutFileName(String lblLayoutFileName) {
		this.lblLayoutFileName = lblLayoutFileName;
	}

	public String validateAll() {
		if (StringUtils.isNullOrEmpty(this.getSitename())) {
			return "Error: Site Name  must not be empty! ";
		}
		if (StringUtils.isNullOrEmpty(this.location.getLocationname())) {
			return "Error: Location in " + this.getSitename() + " must not be empty! ";
		}
		if (this.location.getId() == null) {
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

		if (plantingtype.getId() == -1) {
			return "Error: Planting Type in " + this.getSitename() + " must not be empty! ";
		}
		if (agronomy.getHarvestdate() == null) {
			return "Error: Harvest Date Type in " + this.getSitename() + " must not be empty! ";
		}
		if (agronomy.getSowingdate() == null) {
			return "Error: Sowing Date Type in " + this.getSitename() + " must not be empty! ";
		}

		if (StringUtils.isNullOrEmpty(design.getTreatmentstructure())) {

			return "Treatment Structure in " + this.getSitename() + "must not be empty!";
		}
		if (StringUtils.isNullOrEmpty(design.getDesignstructure())) {
			return "Design Structure in " + this.getSitename() + "must not be empty!";
		}
		if (StringUtils.isNullOrEmpty(design.getPlotsize())) {
			return "Plot size in " + this.getSitename() + "must not be empty!";

		}
		if (this.agronomy.getSowingdate().compareTo(this.agronomy.getHarvestdate()) > 0) {

			return "Error: Havest date must be greater than Transplanting/Sowing date in " + this.getSitename();
		}

		if (getFileGenotype() == null || !getFileGenotype().exists()) {

			return "Must have a genotype file uploaded in " + this.getSitename();

		}
		if (getFileLayout() == null || !getFileLayout().exists()) {

			return "Must have a layout file uploaded in " + this.getSitename();

		}

		if (lstStudyVariable.isEmpty()) {
			return "Must have atleast one variate selected in " + this.getSitename();
		}

		return null;

	}

}
