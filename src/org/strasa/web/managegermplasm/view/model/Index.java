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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.input.ReaderInputStream;
import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.manager.GermplasmCharacteristicMananagerImpl;
import org.strasa.middleware.manager.GermplasmManagerImpl;
import org.strasa.middleware.manager.GermplasmTypeManagerImpl;
import org.strasa.middleware.manager.KeyCharacteristicManagerImpl;
import org.strasa.middleware.manager.StudyGermplasmManagerImpl;
import org.strasa.middleware.model.Germplasm;
import org.strasa.middleware.model.GermplasmType;
import org.strasa.middleware.model.KeyAbiotic;
import org.strasa.middleware.model.KeyBiotic;
import org.strasa.middleware.model.KeyGrainQuality;
import org.strasa.middleware.model.KeyMajorGenes;
import org.strasa.web.common.api.Encryptions;
import org.strasa.web.managegermplasm.view.model.ValidateGermplasmCharacteristics.CharacterEntity;
import org.strasa.web.managegermplasm.view.pojos.GermplasmComparator;
import org.strasa.web.managegermplasm.view.pojos.GermplasmGroupingModel;
import org.strasa.web.uploadstudy.view.pojos.CharacteristicModel;
import org.strasa.web.uploadstudy.view.pojos.GermplasmDeepInfoModel;
import org.strasa.web.uploadstudy.view.pojos.GermplasmExt;
import org.strasa.web.uploadstudy.view.pojos.GermplasmFilter;
import org.strasa.web.utilities.FileUtilities;
import org.strasa.web.utilities.ListBoxValidationUtility;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

import com.mysql.jdbc.StringUtils;

public class Index {

	@WireVariable
	ConnectionFactory connectionFactory;

	@Wire("#tblKnownGerm")
	Listbox tblKnownGerm;

	@Wire("#tblStudyGerm")
	Listbox tblStudyGerm;

	@Wire("#gbUnknownGermplasm")
	Groupbox gbUnknownGermplasm;
	@Wire("#gbKnownGermplasm")
	Groupbox gbKnownGermplasm;
	@Wire("#divUploadOption")
	Div divUploadOption;
	Component mainView;

	private GermplasmFilter studyGermplasmFilter = new GermplasmFilter();
	private GermplasmFilter knownGermplasmFilter = new GermplasmFilter();

	public GermplasmFilter getKnownGermplasmFilter() {
		return knownGermplasmFilter;
	}

	public void setKnownGermplasmFilter(GermplasmFilter knownGermplasmFilter) {
		this.knownGermplasmFilter = knownGermplasmFilter;
	}

	public GermplasmFilter getStudyGermplasmFilter() {
		return studyGermplasmFilter;
	}

	public void setStudyGermplasmFilter(GermplasmFilter studyGermplasmFilter) {
		this.studyGermplasmFilter = studyGermplasmFilter;
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

		mainView = view;
		// wire event listener
		// Selectors.wireEventListeners(view, this);
		gbUnknownGermplasm.setVisible(false);
		divUploadOption.setVisible(false);
	}

	HashMap<String, GermplasmDeepInfoModel> lstStudyGermplasm = new HashMap<String, GermplasmDeepInfoModel>();

	private GermplasmDeepInfoModel selectedGermplasm = new GermplasmDeepInfoModel();

	public GermplasmDeepInfoModel getSelectedGermplasm() {

		return selectedGermplasm;
	}

	public void setSelectedGermplasm(GermplasmDeepInfoModel selectedGermplasm) {
		this.selectedGermplasm = selectedGermplasm;
	}

	public ArrayList<GermplasmDeepInfoModel> arrGermplasmDeepInfo = new ArrayList<GermplasmDeepInfoModel>();

	private List<GermplasmType> lstGermplasmType = new ArrayList<GermplasmType>();
	private HashMap<String, GermplasmDeepInfoModel> lstKnownGermplasm = new HashMap<String, GermplasmDeepInfoModel>();

	private boolean showGroup = true;

	public GermplasmGroupingModel getGermplasmModel() {

		ArrayList<GermplasmDeepInfoModel> allData = new ArrayList<GermplasmDeepInfoModel>();
		allData.addAll(lstKnownGermplasm.values());
		allData.addAll(lstStudyGermplasm.values());

		return new GermplasmGroupingModel(allData, new GermplasmComparator(), this.showGroup);
	}

	public List<GermplasmDeepInfoModel> getLstKnownGermplasm() {

		ArrayList<GermplasmDeepInfoModel> lstGerm = new ArrayList<GermplasmDeepInfoModel>(lstKnownGermplasm.values());
		ArrayList<GermplasmDeepInfoModel> returnVal = new ArrayList<GermplasmDeepInfoModel>();
		for (GermplasmDeepInfoModel data : lstGerm) {
			if (knownGermplasmFilter.equals(data))
				returnVal.add(data);
		}

		return returnVal;
	}

	public void setLstKnownGermplasm(HashMap<String, GermplasmDeepInfoModel> lstKnownGermplasm) {
		this.lstKnownGermplasm = lstKnownGermplasm;
	}

	private List<String> lstBiotics;

	public List<String> getLstBiotics() {
		return lstBiotics;
	}

	public void setLstBiotics(List<String> lstBiotics) {
		this.lstBiotics = lstBiotics;
	}

	public List<String> getLstAbiotics() {
		return lstAbiotics;
	}

	public void setLstAbiotics(List<String> lstAbiotics) {
		this.lstAbiotics = lstAbiotics;
	}

	public List<String> getLstGrainQualities() {
		return lstGrainQualities;
	}

	public void setLstGrainQualities(List<String> lstGrainQualities) {
		this.lstGrainQualities = lstGrainQualities;
	}

	public List<String> getLstMajorGenes() {
		return lstMajorGenes;
	}

	public void setLstMajorGenes(List<String> lstMajorGenes) {
		this.lstMajorGenes = lstMajorGenes;
	}

	private List<String> lstAbiotics;

	private List<String> lstGrainQualities;

	private List<String> lstMajorGenes;

	private Integer userID = SecurityUtil.getDbUser().getId();

	public List<GermplasmType> getLstGermplasmType() {
		return lstGermplasmType;
	}

	public void setLstGermplasmType(List<GermplasmType> lstGermplasmType) {
		this.lstGermplasmType = lstGermplasmType;
	}

	public ArrayList<GermplasmDeepInfoModel> getLstStudyGermplasm() {

		ArrayList<GermplasmDeepInfoModel> lstGerm = new ArrayList<GermplasmDeepInfoModel>(lstStudyGermplasm.values());
		ArrayList<GermplasmDeepInfoModel> returnVal = new ArrayList<GermplasmDeepInfoModel>();
		for (GermplasmDeepInfoModel data : lstGerm) {
			if (studyGermplasmFilter.equals(data))
				returnVal.add(data);
		}

		return returnVal;

	}

	public void setLstStudyGermplasm(HashMap<String, GermplasmDeepInfoModel> lstStudyGermplasm) {
		this.lstStudyGermplasm = lstStudyGermplasm;
	}

	@NotifyChange("lstStudyGermplasm")
	@Command
	public void changeFilterStudy() {

	}

	@NotifyChange("lstKnownGermplasm")
	@Command
	public void changeFilterKnown() {

	}

	@Command
	public List<GermplasmDeepInfoModel> getGermplasmByName(@BindingParam("Gname") String gname) {

		List<GermplasmDeepInfoModel> returnVal = new ArrayList<GermplasmDeepInfoModel>();
		for (GermplasmDeepInfoModel data : arrGermplasmDeepInfo) {
			if (data.getGermplasmname().equals(gname)) {
				returnVal.add(data);

			}
		}
		return returnVal;

	}

	public GermplasmType getGermplasmTypeById(Integer id) {
		// System.out.println("ID:" + id);
		for (GermplasmType gtype : lstGermplasmType) {
			if (gtype.getId() == id)
				return gtype;
		}
		return null;
	}

	public void printArrList() {
		// System.out.println("_____________________________________________________________");

		for (GermplasmDeepInfoModel data : arrGermplasmDeepInfo) {
			// System.out.println(data.toString());
		}

		// System.out.println("_____________________________________________________________");

	}

	public String getTotalUnknownGermplasm() {
		// return "List of germplasm not existing in the database : "+
		// lstStudyGermplasm.size() + " row(s) returned";
		return "Count:" + lstStudyGermplasm.size() + " rows";
	}

	public String getTotalKnownGermplasm() {
		// return
		// "List of germplasm already exist in the database : "+lstKnownGermplasm.size()
		// + " row(s) returned";
		return "Count: " + lstKnownGermplasm.size() + " rows";
	}

	public void resetSize() {
		Clients.resize(mainView.getFellow("tableLayout"));
		Clients.resize(mainView.getFellow("gbUnknownGermplasm"));
		Clients.resize(mainView.getFellow("gbKnownGermplasm"));
		Clients.resize(mainView.getFellow("tblStudyGerm"));
		Clients.resize(mainView.getFellow("tableLayout"));

		Clients.resize(mainView.getFellow("tableLayout"));
	}

	@Command
	public void selectGermplasm(@BindingParam("germplasm") GermplasmDeepInfoModel data) {

		if (selectedGermplasm != null)
			if (data.getGermplasmname().equals(selectedGermplasm.getGermplasmname())) {
				return;
			}

		// selectedGermplasm.setStyleBG("background-color: #FFF");
		// data.setStyleBG("background-color: #BEC7F7 ");
		selectedGermplasm = data;
		BindUtils.postNotifyChange(null, null, Index.this, "selectedGermplasm");
		if (lstStudyGermplasm.containsKey(data.getGermplasmname())) {

			tblStudyGerm.getItemAtIndex(data.rowIndex).setSelected(true);
			Clients.scrollIntoView(tblStudyGerm.getItemAtIndex(data.rowIndex));
		} else {

			tblKnownGerm.getItemAtIndex(data.rowIndex).setSelected(true);
			Clients.scrollIntoView(tblKnownGerm.getItemAtIndex(data.rowIndex));
		}
	}

	@Command
	public void saveGermplasm(@BindingParam("germplasm") GermplasmDeepInfoModel data, @BindingParam("listitem") Integer item) {

		if (lstKnownGermplasm.containsKey(data.getGermplasmname())) {
			new ListBoxValidationUtility(tblKnownGerm, new ArrayList<Integer>(Arrays.asList(0, 1, 3, 4))).validateRow(item);
			;
		} else {
			new ListBoxValidationUtility(tblStudyGerm, new ArrayList<Integer>(Arrays.asList(0, 1, 3, 4))).validateRow(item);

		}
		if (validateGermplasm(data)) {
			new GermplasmManagerImpl().modifyGermplasm(data);
			cancelEdit(data);
		} else {

		}

	}

	@Command
	public void cancelEdit(@BindingParam("germplasm") GermplasmDeepInfoModel data) {

		lstKnownGermplasm.get(data.getGermplasmname()).setKnown(true);
		selectedGermplasm = lstKnownGermplasm.get(data.getGermplasmname());

		Germplasm subGermData = new GermplasmManagerImpl().getGermplasmById(data.getId());

		data.clearCharactersticValue();
		data.setGermplasmValue(subGermData);
		data.setCharacteristicValues(new GermplasmCharacteristicMananagerImpl().getGermplasmByGermplasmName(subGermData.getGermplasmname()));
		data.setSelectedGermplasmType(getGermplasmTypeById(data.getGermplasmtypeid()));
		data.setKnown(true);

		BindUtils.postNotifyChange(null, null, data, "known");
		BindUtils.postNotifyChange(null, null, selectedGermplasm, "*");

	}

	@Init
	public void init() {

		Runtimer timer = new Runtimer();
		timer.start();
		KeyCharacteristicManagerImpl keyMan = new KeyCharacteristicManagerImpl();

		GermplasmTypeManagerImpl germMan = new GermplasmTypeManagerImpl();

		lstGermplasmType = germMan.getAllGermplasmType();
		GermplasmCharacteristicMananagerImpl germCharMan = new GermplasmCharacteristicMananagerImpl();

		lstAbiotics = keyMan.getAllAbioticAsString();
		lstBiotics = keyMan.getAllBioticAsString();
		lstGrainQualities = keyMan.getAllGrainQualityAsString();
		lstMajorGenes = keyMan.getAllMajorGenesAsString();
		GermplasmManagerImpl germplasmMan = new GermplasmManagerImpl();
		List<Germplasm> germplasmList = germplasmMan.getGermplasmListByUserID(this.userID);
		for (Germplasm subGermData : germplasmList) {
			GermplasmDeepInfoModel newData = new GermplasmDeepInfoModel(subGermData);
			// newData.setBiotic(lstKeyBiotics);
			// newData.setAbiotic(lstKeyAbioitc);
			// newData.setMajorGenes(lstKeyMajorGenes);
			// newData.setGrainQuality(lstKeyGrainQuality);

			newData.setCharacteristicValues(germCharMan.getGermplasmByGermplasmName(subGermData.getGermplasmname()));
			newData.setSelectedGermplasmType(getGermplasmTypeById(newData.getGermplasmtypeid()));
			newData.setKnown(true);
			newData.setRowIndex(lstKnownGermplasm.size());

			lstKnownGermplasm.put(newData.getGermplasmname(), newData);
			if (selectedGermplasm == null) {
				selectedGermplasm = newData;

			}

		}

		timer.end();
	}

	public GermplasmDeepInfoModel getGermplasmDeepInfoModelById(Integer id) {

		for (GermplasmDeepInfoModel model : arrGermplasmDeepInfo) {
			if (model.getId() == id)
				return model;
		}
		return null;
	}

	@NotifyChange("lstKnownGermplasm")
	@Command
	public void changeGermplasmInfo(@BindingParam("index") GermplasmDeepInfoModel selected) {

		lstKnownGermplasm.put(selected.getGermplasmname(), selected);
		// printArrList();
		tblKnownGerm.invalidate();

	}

	@NotifyChange({ "lstStudyGermplasm", "lstKnownGermplasm", "totalUnknownGermplasm", "totalKnownGermplasm" })
	@Command("uploadGenotypeData")
	public void uploadGenotypeData(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx, @ContextParam(ContextType.VIEW) Component view) {

		UploadEvent event = (UploadEvent) ctx.getTriggerEvent();

		// //System.out.println(event.getMedia().getStringData());

		String name = event.getMedia().getName();
		if (!name.endsWith(".csv")) {
			Messagebox.show("Error: File must be a text-based CSV  format", "Upload Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		((Label) view.getFellow("lblFileName")).setValue(name);
		try {
			String filename = name + Encryptions.encryptStringToNumber(name, new Date().getTime());
			File tempGenoFile = File.createTempFile(filename, ".tmp");
			InputStream in = event.getMedia().isBinary() ? event.getMedia().getStreamData() : new ReaderInputStream(event.getMedia().getReaderData());
			FileUtilities.uploadFile(tempGenoFile.getAbsolutePath(), in);
			List<GermplasmExt> lstGermplasm = CSVToBean(tempGenoFile);

			KeyCharacteristicManagerImpl keyMan = new KeyCharacteristicManagerImpl();
			List<KeyBiotic> lstKeyBiotics = keyMan.getAllBiotic();
			List<KeyAbiotic> lstKeyAbioitc = keyMan.getAllAbiotic();
			List<KeyMajorGenes> lstKeyMajorGenes = keyMan.getAllMajorGenes();
			List<KeyGrainQuality> lstKeyGrainQuality = keyMan.getAllGrainQuality();
			ArrayList<GermplasmDeepInfoModel> tempKnown = new ArrayList<GermplasmDeepInfoModel>();
			ArrayList<GermplasmDeepInfoModel> lstInvalidCharacteristics = new ArrayList<GermplasmDeepInfoModel>();

			for (GermplasmExt germData : lstGermplasm) {
				if (!StringUtils.isNullOrEmpty(germData.getGermplasmname())) {
					// System.out.println(germData.toString());
					if (lstKnownGermplasm.containsKey(germData.getGermplasmname())) {

						tempKnown.add(lstKnownGermplasm.get(germData.getGermplasmname()));
						if (lstKnownGermplasm.get(germData.getGermplasmname()).setGermplasmExCharacteristic(germData) == false) {
							lstInvalidCharacteristics.add(lstKnownGermplasm.get(germData.getGermplasmname()));
						}
					} else {

						GermplasmDeepInfoModel newData = new GermplasmDeepInfoModel();

						newData.setGermplasmtypeid(getGermplasmTypeById(germData.getGermplasmtype()));
						newData.setGermplasmname(germData.getGermplasmname());
						newData.setValueFromeGermplasmEx(germData, lstGermplasmType);
						newData.setBiotic(lstKeyBiotics);
						newData.setAbiotic(lstKeyAbioitc);
						newData.setMajorGenes(lstKeyMajorGenes);
						newData.setUserid(this.userID);
						newData.setGrainQuality(lstKeyGrainQuality);
						newData.setKnown(false);
						if (!newData.setGermplasmExCharacteristic(germData)) {
							lstInvalidCharacteristics.add(newData);
						}
						lstStudyGermplasm.put(newData.getGermplasmname(), newData);

					}
				}
			}

			lstKnownGermplasm.clear();
			for (GermplasmDeepInfoModel germData : tempKnown) {
				lstKnownGermplasm.put(germData.getGermplasmname(), germData);

			}

			gbUnknownGermplasm.setVisible(true);
			gbKnownGermplasm.setVisible(true);
			view.getFellow("divUploadOption").setVisible(true);
			resetSize();
			if (!lstInvalidCharacteristics.isEmpty()) {
				Map<String, Object> params = new HashMap<String, Object>();

				params.put("germplasmList", lstInvalidCharacteristics);
				params.put("parent", view);
				Window popup = (Window) Executions.createComponents(ValidateGermplasmCharacteristics.ZUL_PATH, view, params);

				popup.doModal();
			}

			// gbKnownGermplasm.invalidate();
			// gbUnknownGermplasm.invalidate();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		}

	}

	@Command
	public void refreshGermplasmCharacteristics(@BindingParam("newValues") HashMap<String, CharacterEntity> lstHash) {
		for (GermplasmDeepInfoModel germData : lstKnownGermplasm.values()) {
			for (CharacteristicModel chr : germData.getInvalidCharacteristic()) {

				if (lstHash.containsKey(chr.getName())) {
					chr.setName(lstHash.get(chr.getName()).newValue);
					// System.out.println("-");
				}

			}
			germData.reprocessInvalidCharacters();
		}

		for (GermplasmDeepInfoModel germData : lstStudyGermplasm.values()) {
			for (CharacteristicModel chr : germData.getInvalidCharacteristic()) {

				if (lstHash.containsKey(chr.getName())) {
					chr.setName(lstHash.get(chr.getName()).newValue);
					// System.out.println("-");
				}

			}
			germData.reprocessInvalidCharacters();
		}
		for (GermplasmDeepInfoModel data : lstKnownGermplasm.values()) {
			BindUtils.postNotifyChange(null, null, lstKnownGermplasm.get(data.getGermplasmname()), "keyBiotic");
			BindUtils.postNotifyChange(null, null, lstKnownGermplasm.get(data.getGermplasmname()), "keyAbiotic");
			BindUtils.postNotifyChange(null, null, lstKnownGermplasm.get(data.getGermplasmname()), "keyGrainQuality");
			BindUtils.postNotifyChange(null, null, lstKnownGermplasm.get(data.getGermplasmname()), "keyMajorGenes");
		}
		for (GermplasmDeepInfoModel data : lstStudyGermplasm.values()) {
			BindUtils.postNotifyChange(null, null, lstStudyGermplasm.get(data.getGermplasmname()), "keyBiotic");
			BindUtils.postNotifyChange(null, null, lstStudyGermplasm.get(data.getGermplasmname()), "keyAbiotic");
			BindUtils.postNotifyChange(null, null, lstStudyGermplasm.get(data.getGermplasmname()), "keyGrainQuality");
			BindUtils.postNotifyChange(null, null, lstStudyGermplasm.get(data.getGermplasmname()), "keyMajorGenes");
		}
	}

	@NotifyChange({ "lstStudyGermplasm" })
	@Command
	public void resetUnknownGermplasm() {
		lstStudyGermplasm.clear();
		gbUnknownGermplasm.setVisible(false);
		gbKnownGermplasm.setVisible(true);
		divUploadOption.setVisible(false);
		init();
		BindUtils.postNotifyChange(null, null, this, "*");

	}

	public Integer getGermplasmTypeById(String key) {
		for (GermplasmType gtype : lstGermplasmType) {
			if (gtype.getGermplasmtype().equals(key))
				return gtype.getId();
		}
		return null;
	}

	public List<GermplasmExt> CSVToBean(File file) throws IOException {
		CsvToBean<GermplasmExt> bean = new CsvToBean<GermplasmExt>();

		Map<String, String> columnMapping = new HashMap<String, String>();
		columnMapping.put("GID", "gid");
		columnMapping.put("GNAME", "germplasmname");
		columnMapping.put("BREEDER", "breeder");
		columnMapping.put("OTHERNAME", "other_name");
		columnMapping.put("IR NUMBER", "irnumber");
		columnMapping.put("IR CROSS", "ircross");
		columnMapping.put("GERMPLASMTYPE", "germplasmtype");
		columnMapping.put("PARENTAGE", "parentage");
		columnMapping.put("FEMALE PARENT", "femaleparent");
		columnMapping.put("MALE PARENT", "maleparent");
		columnMapping.put("SELECTION HISTORY", "selectionhistory");
		columnMapping.put("SOURCE", "source");
		columnMapping.put("BIOTIC", "biotic");
		columnMapping.put("ABIOTIC", "abiotic");
		columnMapping.put("MAJOR GENES", "majorGenes");
		columnMapping.put("GRAIN QUALITY", "grainQuality");
		System.out.println(file.getAbsolutePath());
		HeaderColumnNameTranslateMappingStrategy<GermplasmExt> strategy = new HeaderColumnNameTranslateMappingStrategy<GermplasmExt>();
		strategy.setType(GermplasmExt.class);
		strategy.setColumnMapping(columnMapping);

		CSVReader reader = new CSVReader(new FileReader(file));

		List<String[]> lstWriter = reader.readAll();
		String[] header = lstWriter.get(0);
		for (int i = 0; i < header.length; i++) {
			header[i] = header[i].toUpperCase().trim();
		}
		lstWriter.set(0, header);
		CSVWriter writer = new CSVWriter(new FileWriter(file.getAbsolutePath()));

		writer.writeAll(lstWriter);
		writer.close();
		reader = new CSVReader(new FileReader(file));

		return bean.parse(strategy, reader);
	}

	public boolean validateKnownGermplasm() {
		int studyGerm = 0;
		for (GermplasmDeepInfoModel data : lstKnownGermplasm.values()) {

			if (!validateGermplasm(data))
				return false;

		}
		return true;

	}

	public boolean validateGermplasm(GermplasmDeepInfoModel data) {
		String validate = data.validate();

		if (!data.getStyleBG().equals("background-color: #FFF")) {
			data.setStyleBG("background-color: #FFF");
			BindUtils.postNotifyChange(null, null, data, "styleBG");
		}
		if (validate != null) {
			Messagebox.show(validate, "OK", Messagebox.OK, Messagebox.EXCLAMATION);
			selectGermplasm(data);
			return false;
		}
		return true;
	}

	public boolean validateStudyGermplasm() {
		int studyGerm = 0;
		for (GermplasmDeepInfoModel data : lstStudyGermplasm.values()) {

			if (!validateGermplasm(data)) {
				selectGermplasm(data);
				return false;
			}

		}
		return true;

	}

	@Command
	public void updateCharacteristicInfo(@BindingParam("model") GermplasmDeepInfoModel model) {
		BindUtils.postNotifyChange(null, null, this.getLstStudyGermplasm().get(model.getRowIndex()), "*");
		this.tblStudyGerm.invalidate();
	}

	@NotifyChange("selectedGermplasm")
	@Command
	public void modifyGermplasm(@BindingParam("gname") String gname) {
		// System.out.println("GNAME: " + gname);
		// System.out.println("SIZE: " + lstKnownGermplasm.size());

		lstKnownGermplasm.get(gname).setKnown(false);
		selectedGermplasm = lstKnownGermplasm.get(gname);

		BindUtils.postNotifyChange(null, null, lstKnownGermplasm.get(gname), "known");

	}

	@SuppressWarnings("unchecked")
	@Command("removeGermplasm")
	public void removeGermplasm(@BindingParam("gname") GermplasmDeepInfoModel data) {
		final GermplasmDeepInfoModel gname = data;
		Messagebox.show("Are you sure you want to delete? WARNING: Cannot be undone", "Confirm Dialog", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
			public void onEvent(Event evt) throws InterruptedException {
				if (evt.getName().equals("onOK")) {
					if (!new StudyGermplasmManagerImpl().isGermplasmGrefExist(gname)) {
						lstKnownGermplasm.remove(gname.getGermplasmname());
						BindUtils.postNotifyChange(null, null, Index.this, "lstKnownGermplasm");

						BindUtils.postNotifyChange(null, null, Index.this, "totalKnownGermplasm");
						new GermplasmManagerImpl().deleteGermplasmById(gname.getId());

					} else {
						Messagebox.show("Cannot delete germplasm. Studies using this germplasm found.", "Conflict Error", Messagebox.OK, Messagebox.EXCLAMATION);
					}
				} else {

				}
			}
		});
	}

	@Command
	public void validateList() {
		Runtimer timer = new Runtimer();
		timer.start();

		if (!validateStudyGermplasm()) {

			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("model", this);
		BindUtils.postGlobalCommand(null, null, "nextTab", params);
	}

	public void selectRow(GermplasmDeepInfoModel model) {
		if (model.isKnown())
			tblKnownGerm.setSelectedIndex(model.rowIndex);
		else
			tblStudyGerm.setSelectedIndex(model.rowIndex);
	}

	@Command
	public void saveData() {

		// Validation
		if (!validateStudyGermplasm()) {
			return;
		}

		StudyGermplasmManagerImpl studyGermplasmMan = new StudyGermplasmManagerImpl();
		GermplasmManagerImpl germplasmManagerImpl = new GermplasmManagerImpl();
		// List<StudyGermplasm> lstStudyGerm =
		// convertDeepInfoToModel(lstStudyGermplasm.values());
		GermplasmCharacteristicMananagerImpl germCharMan = new GermplasmCharacteristicMananagerImpl();
		List<GermplasmDeepInfoModel> lstStudyGermpl = new ArrayList<GermplasmDeepInfoModel>();
		lstStudyGermpl.addAll(lstStudyGermplasm.values());
		germplasmManagerImpl.addGermplasmListNoRepeat(lstStudyGermplasm.values(), this.userID);

		germCharMan.addCharacteristicBatch(lstStudyGermplasm.values());

		resetUnknownGermplasm();
		// init();
		// BindUtils.postNotifyChange(null, null, this, "*");?
		Messagebox.show("Germplasms has been added to the database!", "Information", Messagebox.OK, Messagebox.INFORMATION);

	}

	public String getGermplasmType(int id) {

		for (GermplasmType type : lstGermplasmType) {
			if (type.getId() == id)
				return type.getGermplasmtype();
		}
		return "";

	}

	public class Runtimer {
		long startTime = System.nanoTime();

		public long start() {
			startTime = System.nanoTime();
			return startTime;
		}

		public double end() {
			long endTime = System.nanoTime();
			// System.out.println("DURATION : " + (endTime - startTime) /
			// 1000000000.0);
			return (endTime - startTime) / 1000000000.0;
		}

	}

}