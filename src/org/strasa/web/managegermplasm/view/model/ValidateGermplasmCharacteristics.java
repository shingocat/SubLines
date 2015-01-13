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
package org.strasa.web.managegermplasm.view.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.strasa.middleware.manager.KeyCharacteristicManagerImpl;
import org.strasa.middleware.model.KeyAbiotic;
import org.strasa.middleware.model.KeyBiotic;
import org.strasa.middleware.model.KeyGrainQuality;
import org.strasa.middleware.model.KeyMajorGenes;
import org.strasa.web.uploadstudy.view.pojos.CharacteristicModel;
import org.strasa.web.uploadstudy.view.pojos.GermplasmDeepInfoModel;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;

public class ValidateGermplasmCharacteristics {

	public static final String ZUL_PATH = "/user/managegermplasm/validategermplasmcharacter.zul";

	ArrayList<GermplasmDeepInfoModel> lstGermplasm = new ArrayList<GermplasmDeepInfoModel>();
	private List<KeyBiotic> lstBiotics;
	private List<KeyAbiotic> lstAbiotics;
	private List<KeyGrainQuality> lstGrainQualities;
	private List<KeyMajorGenes> lstAllMajorGenes;
	private HashMap<String, CharacterEntity> lstHash = new HashMap<String, CharacterEntity>();

	private Component mainView;

	private Binder bindCtx;

	@Init
	public void init(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx, @ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("germplasmList") ArrayList<GermplasmDeepInfoModel> lst) {

		mainView = view;
		bindCtx = (Binder) view.getParent().getAttribute("binder");
		;
		lstGermplasm = lst;
		KeyCharacteristicManagerImpl keyMan = new KeyCharacteristicManagerImpl();
		lstBiotics = keyMan.getAllBiotic();
		lstAbiotics = keyMan.getAllAbiotic();
		lstGrainQualities = keyMan.getAllGrainQuality();
		lstAllMajorGenes = keyMan.getAllMajorGenes();

		for (GermplasmDeepInfoModel germData : lst) {
			for (CharacteristicModel chr : germData.getInvalidCharacteristic()) {
				lstHash.put(chr.getName(), new CharacterEntity(chr.getName(), chr.getAttribute()));
			}
		}

	}

	public ArrayList<CharacterEntity> getBiotic() {
		ArrayList<CharacterEntity> returnVal = new ArrayList<ValidateGermplasmCharacteristics.CharacterEntity>();

		for (CharacterEntity ch : lstHash.values()) {
			if (ch.getAttribute().equals(GermplasmDeepInfoModel.BIOTIC)) {
				returnVal.add(ch);
			}
		}
		return returnVal;

	}

	public List<KeyBiotic> getLstBiotics() {
		return lstBiotics;
	}

	public void setLstBiotics(List<KeyBiotic> lstBiotics) {
		this.lstBiotics = lstBiotics;
	}

	public List<KeyAbiotic> getLstAbiotics() {
		return lstAbiotics;
	}

	public void setLstAbiotics(List<KeyAbiotic> lstAbiotics) {
		this.lstAbiotics = lstAbiotics;
	}

	public List<KeyGrainQuality> getLstGrainQualities() {
		return lstGrainQualities;
	}

	public void setLstGrainQualities(List<KeyGrainQuality> lstGrainQualities) {
		this.lstGrainQualities = lstGrainQualities;
	}

	public List<KeyMajorGenes> getLstAllMajorGenes() {
		return lstAllMajorGenes;
	}

	public void setLstAllMajorGenes(List<KeyMajorGenes> lstAllMajorGenes) {
		this.lstAllMajorGenes = lstAllMajorGenes;
	}

	public HashMap<String, CharacterEntity> getLstHash() {
		return lstHash;
	}

	public void setLstHash(HashMap<String, CharacterEntity> lstHash) {
		this.lstHash = lstHash;
	}

	public ArrayList<CharacterEntity> getAbiotic() {
		ArrayList<CharacterEntity> returnVal = new ArrayList<ValidateGermplasmCharacteristics.CharacterEntity>();

		for (CharacterEntity ch : lstHash.values()) {
			if (ch.getAttribute().equals(GermplasmDeepInfoModel.ABIOTIC)) {
				returnVal.add(ch);
			}
		}
		return returnVal;

	}

	public ArrayList<CharacterEntity> getGrainQuality() {
		ArrayList<CharacterEntity> returnVal = new ArrayList<ValidateGermplasmCharacteristics.CharacterEntity>();

		for (CharacterEntity ch : lstHash.values()) {
			if (ch.getAttribute().equals(GermplasmDeepInfoModel.GRAINQUALITY)) {
				returnVal.add(ch);
			}
		}
		return returnVal;

	}

	public ArrayList<CharacterEntity> getMajorGenes() {
		ArrayList<CharacterEntity> returnVal = new ArrayList<ValidateGermplasmCharacteristics.CharacterEntity>();

		for (CharacterEntity ch : lstHash.values()) {
			if (ch.getAttribute().equals(GermplasmDeepInfoModel.MAJORGENES)) {
				returnVal.add(ch);
			}
		}
		return returnVal;

	}

	@Command
	public void submit() {

		boolean isComplete = true;

		for (CharacterEntity ch : lstHash.values()) {

		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("newValues", lstHash);
		// this.parBinder.postCommand("change", params);
		bindCtx.postCommand("refreshGermplasmCharacteristics", params);
		mainView.detach();
	}

	public ArrayList<GermplasmDeepInfoModel> getLstGermplasm() {
		return lstGermplasm;
	}

	public void setLstGermplasm(ArrayList<GermplasmDeepInfoModel> lstGermplasm) {
		this.lstGermplasm = lstGermplasm;
	}

	public class CharacterEntity {

		private String oldValue;
		String newValue;
		private String attribute;

		public String getAttribute() {
			return attribute;
		}

		public void setAttribute(String attribute) {
			this.attribute = attribute;
		}

		public String getOldValue() {
			return oldValue;
		}

		public void setOldValue(String oldValue) {
			this.oldValue = oldValue;
		}

		public String getNewValue() {
			return newValue;
		}

		public void setNewValue(String newValue) {
			this.newValue = newValue;
		}

		public CharacterEntity(String arg0, String arg1, String arg2) {

			oldValue = arg0;
			newValue = arg1;
			attribute = arg2;
			// TODO Auto-generated constructor stub
		}

		public CharacterEntity(String arg0, String arg2) {

			oldValue = arg0;

			attribute = arg2;
			// TODO Auto-generated constructor stub
		}

	}
}
