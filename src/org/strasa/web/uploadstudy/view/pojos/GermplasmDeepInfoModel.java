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

import java.util.ArrayList;
import java.util.List;

import org.strasa.middleware.manager.KeyCharacteristicManagerImpl;
import org.strasa.middleware.model.Germplasm;
import org.strasa.middleware.model.GermplasmCharacteristics;
import org.strasa.middleware.model.GermplasmType;
import org.strasa.middleware.model.KeyAbiotic;
import org.strasa.middleware.model.KeyBiotic;
import org.strasa.middleware.model.KeyGrainQuality;
import org.strasa.middleware.model.KeyMajorGenes;

import com.mysql.jdbc.StringUtils;

public class GermplasmDeepInfoModel extends Germplasm {

	public static String ABIOTIC = "Abiotic";
	public static String BIOTIC = "Biotic";
	public static String MAJORGENES = "Major Genes";
	public static String GRAINQUALITY = "Grain Quality";

	private List<String> lstBiotics = new ArrayList<String>();
	private List<String> lstAbiotic = new ArrayList<String>();
	private List<String> lstGrainQuality = new ArrayList<String>();
	private List<String> lstMajorGenes = new ArrayList<String>();
	
	private String styleBG = "background-color: #FFF";
	public int rowIndex;
	private GermplasmType selectedGermplasmType;
	private String category;
	public boolean recordExist = false;
	private boolean isKnown;
	private String newBreeder;
	private ArrayList<CharacteristicModel> invalidCharacteristic = new ArrayList<CharacteristicModel>();


	public GermplasmDeepInfoModel() {

	}
	
	public GermplasmDeepInfoModel(Germplasm data) {
		// TODO Auto-generated constructor stub

		if (!StringUtils.isNullOrEmpty(data.getBreeder()))
			setBreeder(data.getBreeder());
		if (!StringUtils.isNullOrEmpty(data.getFemaleparent()))
			setFemaleparent(data.getFemaleparent());
		if (!StringUtils.isNullOrEmpty(data.getGermplasmname()))
			setGermplasmname(data.getGermplasmname());
		if (data.getGid() != null)
			setGid(data.getGid());
		if (data.getGermplasmtypeid() != null)
			setGermplasmtypeid(data.getGermplasmtypeid());
		if (!StringUtils.isNullOrEmpty(data.getIrcross()))
			setIrcross(data.getIrcross());
		if (!StringUtils.isNullOrEmpty(data.getIrnumber()))
			setIrnumber(data.getIrnumber());
		if (!StringUtils.isNullOrEmpty(data.getMaleparent()))
			setMaleparent(data.getMaleparent());
		if (!StringUtils.isNullOrEmpty(data.getOthername()))
			setOthername(data.getOthername());
		if (!StringUtils.isNullOrEmpty(data.getParentage()))
			setParentage(data.getParentage());
		if (!StringUtils.isNullOrEmpty(data.getRemarks()))
			setRemarks(data.getRemarks());
		if (!StringUtils.isNullOrEmpty(data.getSelectionhistory()))
			setSelectionhistory(data.getSelectionhistory());
		if (!StringUtils.isNullOrEmpty(data.getSource()))
			setSource(data.getSource());
		this.setUserid(data.getUserid());
		this.setId(data.getId());
	}

	
	public List<String> getLstBiotics() {
		return lstBiotics;
	}

	public void setLstBiotics(List<String> lstBiotics) {
		this.lstBiotics = lstBiotics;
	}

	public List<String> getLstAbiotic() {
		return lstAbiotic;
	}

	public void setLstAbiotic(List<String> lstAbiotic) {
		this.lstAbiotic = lstAbiotic;
	}

	public List<String> getLstGrainQuality() {
		return lstGrainQuality;
	}

	public void setLstGrainQuality(List<String> lstGrainQuality) {
		this.lstGrainQuality = lstGrainQuality;
	}

	public List<String> getLstMajorGenes() {
		return lstMajorGenes;
	}

	public void setLstMajorGenes(List<String> lstMajorGenes) {
		this.lstMajorGenes = lstMajorGenes;
	}

	public ArrayList<CharacteristicModel> getInvalidCharacteristic() {
		return invalidCharacteristic;
	}

	public void setInvalidCharacteristic(ArrayList<CharacteristicModel> invalidCharacteristic) {
		this.invalidCharacteristic = invalidCharacteristic;
	}

	public String getNewBreeder() {
		return newBreeder;
	}

	public void setNewBreeder(String newBreeder) {
		this.newBreeder = newBreeder;
	}

	public String getTemplateMode() {

		if (this.getId() == null) {
			return "newEntry";
		}

		if (isKnown)
			return "display";

		return "editable";
	}

	
	public void setGermplasmValue(Germplasm data) {
		if (!StringUtils.isNullOrEmpty(data.getBreeder()))
			setBreeder(data.getBreeder());
		if (!StringUtils.isNullOrEmpty(data.getFemaleparent()))
			setFemaleparent(data.getFemaleparent());
		if (!StringUtils.isNullOrEmpty(data.getGermplasmname()))
			setGermplasmname(data.getGermplasmname());
		if (data.getGid() != null)
			setGid(data.getGid());
		if (data.getGermplasmtypeid() != null)
			setGermplasmtypeid(data.getGermplasmtypeid());
		if (!StringUtils.isNullOrEmpty(data.getIrcross()))
			setIrcross(data.getIrcross());
		if (!StringUtils.isNullOrEmpty(data.getIrnumber()))
			setIrnumber(data.getIrnumber());
		if (!StringUtils.isNullOrEmpty(data.getMaleparent()))
			setMaleparent(data.getMaleparent());
		if (!StringUtils.isNullOrEmpty(data.getOthername()))
			setOthername(data.getOthername());
		if (!StringUtils.isNullOrEmpty(data.getParentage()))
			setParentage(data.getParentage());
		if (!StringUtils.isNullOrEmpty(data.getRemarks()))
			setRemarks(data.getRemarks());
		if (!StringUtils.isNullOrEmpty(data.getSelectionhistory()))
			setSelectionhistory(data.getSelectionhistory());
		if (!StringUtils.isNullOrEmpty(data.getSource()))
			setSource(data.getSource());
		this.setUserid(data.getUserid());
		this.setId(data.getId());
	}

	public boolean isEditMode() {
		return isKnown;
	}

	public boolean isKnown() {
		return isKnown;
	}

	public void setKnown(boolean isKnown) {
		this.isKnown = isKnown;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public GermplasmType getSelectedGermplasmType() {
		return selectedGermplasmType;
	}

	public void setSelectedGermplasmType(GermplasmType selectedGermplasmType) {
		this.selectedGermplasmType = selectedGermplasmType;
		this.setGermplasmtypeid(selectedGermplasmType.getId());
	}

	public String getStyleBG() {
		return styleBG;
	}

	public void setStyleBG(String styleBG) {
		this.styleBG = styleBG;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public void setBiotic(List<KeyBiotic> list) {

		for (KeyBiotic key : list) {

			lstBiotics.add(key.getValue());

		}
	}

	public void setAbiotic(List<KeyAbiotic> list) {

		for (KeyAbiotic key : list) {

			lstAbiotic.add(key.getValue());

		}
	}

	public void setMajorGenes(List<KeyMajorGenes> list) {

		for (KeyMajorGenes key : list) {

			lstMajorGenes.add(key.getValue());

		}
	}

	public void setGrainQuality(List<KeyGrainQuality> list) {

		for (KeyGrainQuality key : list) {

			lstGrainQuality.add(key.getValue());

		}
	}

	public void setCharacteristicValues(List<GermplasmCharacteristics> lstChars) {

		for (GermplasmCharacteristics germChar : lstChars) {
			if (germChar.getAttribute().equals(ABIOTIC)) {
				lstAbiotic.add(germChar.getKeyvalue());
			}
			if (germChar.getAttribute().equals(BIOTIC)) {
				lstBiotics.add(germChar.getKeyvalue());
			}
			if (germChar.getAttribute().equals(MAJORGENES)) {
				lstMajorGenes.add(germChar.getKeyvalue());
			}
			if (germChar.getAttribute().equals(GRAINQUALITY)) {
				lstGrainQuality.add(germChar.getKeyvalue());
			}
		}
	}

	public void clearCharactersticValue() {
		lstAbiotic.clear();
		lstBiotics.clear();
		lstGrainQuality.clear();
		lstMajorGenes.clear();
	}

	public String validate() {
		// if(true) return true;
		styleBG = "background-color: #ff6666";
		if (StringUtils.isNullOrEmpty(getGermplasmname())) {
			// styleBG = "background-color: #ff6666";
			return "Error: GName cannot be empty";
		}
		if (StringUtils.isNullOrEmpty(getBreeder())) {
			// styleBG = "background-color: #ff6666";
			return "Error: Breeder must not be empty in GName: " + getGermplasmname();
		}

		if (StringUtils.isNullOrEmpty(getFemaleparent())) {
			// styleBG = "background-color: #ff6666";
			return "Error: Female Parent must not be empty in GName: " + getGermplasmname();
		}

		;
		if (StringUtils.isNullOrEmpty(getMaleparent())) {
			// styleBG = "background-color: #ff6666";
			return "Error: Male Parent must not be empty in GName: " + getGermplasmname();
		}

		if (getGermplasmtypeid() == null) {
			// styleBG = "background-color: #ff6666";
			return "Error: Germplasm Type must not be empty in GName: " + getGermplasmname();
		}
		styleBG = "background-color: #FFF";
		return null;
	}

	public List<GermplasmCharacteristics> getCharacteristicValues() {
		// TODO Auto-generated method stub
		ArrayList<GermplasmCharacteristics> returnVal = new ArrayList<GermplasmCharacteristics>();

		for (String charData : lstAbiotic) {

			GermplasmCharacteristics newData = new GermplasmCharacteristics();
			newData.setAttribute(ABIOTIC);
			newData.setGermplasmname(this.getGermplasmname());
			newData.setKeyvalue(charData);
			returnVal.add(newData);

			System.out.println(charData.toString());
		}
		for (String charData : lstBiotics) {

			GermplasmCharacteristics newData = new GermplasmCharacteristics();
			newData.setAttribute(BIOTIC);
			newData.setGermplasmname(this.getGermplasmname());
			newData.setKeyvalue(charData);
			returnVal.add(newData);

			System.out.println(charData.toString());
		}
		for (String charData : lstMajorGenes) {

			GermplasmCharacteristics newData = new GermplasmCharacteristics();
			newData.setAttribute(MAJORGENES);
			newData.setGermplasmname(this.getGermplasmname());
			newData.setKeyvalue(charData);
			returnVal.add(newData);

			System.out.println(charData.toString());
		}
		for (String charData : lstGrainQuality) {

			GermplasmCharacteristics newData = new GermplasmCharacteristics();
			newData.setAttribute(GRAINQUALITY);
			newData.setGermplasmname(this.getGermplasmname());
			newData.setKeyvalue(charData);
			returnVal.add(newData);

			System.out.println(charData.toString());
		}

		return returnVal;
	}

	public void setValueFromeGermplasmEx(GermplasmExt data, List<GermplasmType> lstGermplasmType) {

		if (!StringUtils.isNullOrEmpty(data.getBreeder()))
			setBreeder(data.getBreeder());
		if (!StringUtils.isNullOrEmpty(data.getFemaleparent()))
			setFemaleparent(data.getFemaleparent());

		if (data.getGid() != null)
			setGid(data.getGid());

		if (!StringUtils.isNullOrEmpty(data.getIrcross()))
			setIrcross(data.getIrcross());
		if (!StringUtils.isNullOrEmpty(data.getIrnumber()))
			setIrnumber(data.getIrnumber());
		if (!StringUtils.isNullOrEmpty(data.getMaleparent()))
			setMaleparent(data.getMaleparent());
		if (!StringUtils.isNullOrEmpty(data.getOther_name()))
			setOthername(data.getOther_name());
		if (!StringUtils.isNullOrEmpty(data.getParentage()))
			setParentage(data.getParentage());
		if (!StringUtils.isNullOrEmpty(data.getRemarks()))
			setRemarks(data.getRemarks());
		if (!StringUtils.isNullOrEmpty(data.getSelectionhistory()))
			setSelectionhistory(data.getSelectionhistory());
		if (!StringUtils.isNullOrEmpty(data.getSource()))
			setSource(data.getSource());

		for (GermplasmType gtype : lstGermplasmType) {
			if (gtype.getGermplasmtype().equals(data.getGermplasmtype())) {
				this.setSelectedGermplasmType(gtype);
			}
		}

		// System.out.println(this.toString());

	}

	public boolean setGermplasmExCharacteristic(GermplasmExt data) {

		// TODO: NOT A GOOD APPROACH, REVISED IF HAVE THE TIME
		KeyCharacteristicManagerImpl keyMan = new KeyCharacteristicManagerImpl();
		List<String> Abiotics = keyMan.getAllAbioticAsString();
		List<String> Biotics = keyMan.getAllBioticAsString();
		List<String> GrainQualities = keyMan.getAllGrainQualityAsString();
		List<String> MajorGenes = keyMan.getAllMajorGenesAsString();
		lstBiotics.clear();
		lstAbiotic.clear();
		lstGrainQuality.clear();
		lstMajorGenes.clear();
		// Biotic
		ArrayList<CharacteristicModel> returnVal = new ArrayList<CharacteristicModel>();
		if (!StringUtils.isEmptyOrWhitespaceOnly(data.getBiotic()))
			for (String key : data.getBiotic().split(",")) {
				if (Biotics.contains(key)) {
					// System.out.println("Key : " + key);
					lstBiotics.add(key);
				} else
					returnVal.add(new CharacteristicModel(key, BIOTIC));
			}

		if (!StringUtils.isEmptyOrWhitespaceOnly(data.getAbiotic()))
			for (String key : data.getAbiotic().split(",")) {
				if (Abiotics.contains(key)) {
					lstAbiotic.add(key);
				} else
					returnVal.add(new CharacteristicModel(key, ABIOTIC));

			}
		if (!StringUtils.isEmptyOrWhitespaceOnly(data.getGrainQuality()))
			for (String key : data.getGrainQuality().split(",")) {
				if (GrainQualities.contains(key)) {
					lstGrainQuality.add(key);
				} else
					returnVal.add(new CharacteristicModel(key, GRAINQUALITY));

			}
		if (!StringUtils.isEmptyOrWhitespaceOnly(data.getMajorGenes()))
			for (String key : data.getMajorGenes().split(",")) {
				if (MajorGenes.contains(key)) {
					lstMajorGenes.add(key);
				} else
					returnVal.add(new CharacteristicModel(key, MAJORGENES));

			}

		invalidCharacteristic = returnVal;
		return returnVal.isEmpty();

	}

	public boolean equalsGermplasmNoBreeder(Germplasm obj) {

		Germplasm other = obj;

		if (getFemaleparent() == null) {
			if (other.getFemaleparent() != null)
				return false;
		} else if (!getFemaleparent().equals(other.getFemaleparent()))
			return false;
		if (getGermplasmname() == null) {
			if (other.getGermplasmname() != null)
				return false;
		} else if (!getGermplasmname().equals(other.getGermplasmname()))
			return false;
		if (getGermplasmtypeid() == null) {
			if (other.getGermplasmtypeid() != null)
				return false;
		} else if (!getGermplasmtypeid().equals(other.getGermplasmtypeid()))
			return false;
		if (getGid() == null) {
			if (other.getGid() != null)
				return false;
		} else if (!getGid().equals(other.getGid()))
			return false;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		if (getIrcross() == null) {
			if (other.getIrcross() != null)
				return false;
		} else if (!getIrcross().equals(other.getIrcross()))
			return false;
		if (getIrnumber() == null) {
			if (other.getIrnumber() != null)
				return false;
		} else if (!getIrnumber().equals(other.getIrnumber()))
			return false;
		if (getMaleparent() == null) {
			if (other.getMaleparent() != null)
				return false;
		} else if (!getMaleparent().equals(other.getMaleparent()))
			return false;
		if (getOthername() == null) {
			if (other.getOthername() != null)
				return false;
		} else if (!getOthername().equals(other.getOthername()))
			return false;
		if (getParentage() == null) {
			if (other.getParentage() != null)
				return false;
		} else if (!getParentage().equals(other.getParentage()))
			return false;
		if (getRemarks() == null) {
			if (other.getRemarks() != null)
				return false;
		} else if (!getRemarks().equals(other.getRemarks()))
			return false;
		if (getSelectionhistory() == null) {
			if (other.getSelectionhistory() != null)
				return false;
		} else if (!getSelectionhistory().equals(other.getSelectionhistory()))
			return false;
		if (getSource() == null) {
			if (other.getSource() != null)
				return false;
		} else if (!getSource().equals(other.getSource()))
			return false;
		if (getUserid() == null) {
			if (other.getUserid() != null)
				return false;
		} else if (!getUserid().equals(other.getUserid()))
			return false;
		return true;
	}

	public void reprocessInvalidCharacters() {
		// TODO: NOT A GOOD APPROACH, REVISED IF HAVE THE TIME
		KeyCharacteristicManagerImpl keyMan = new KeyCharacteristicManagerImpl();
		List<String> Abiotics = keyMan.getAllAbioticAsString();
		List<String> Biotics = keyMan.getAllBioticAsString();
		List<String> GrainQualities = keyMan.getAllGrainQualityAsString();
		List<String> MajorGenes = keyMan.getAllMajorGenesAsString();
		for (CharacteristicModel ch : invalidCharacteristic) {
			if (ch.getAttribute().equals(ABIOTIC)) {
				if (Abiotics.contains(ch.getName()))
					lstAbiotic.add(ch.getName());
			}
			if (ch.getAttribute().equals(BIOTIC)) {
				if (Biotics.contains(ch.getName()))
					lstBiotics.add(ch.getName());
			}
			if (ch.getAttribute().equals(MAJORGENES)) {
				if (MajorGenes.contains(ch.getName()))
					lstMajorGenes.add(ch.getName());
			}
			if (ch.getAttribute().equals(GRAINQUALITY)) {
				if (GrainQualities.contains(ch.getName()))
					lstGrainQuality.add(ch.getName());
			}
		}

	}
}