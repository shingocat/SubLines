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
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.manager.GermplasmCharacteristicMananagerImpl;
import org.strasa.middleware.manager.GermplasmManagerImpl;
import org.strasa.middleware.manager.GermplasmTypeManagerImpl;
import org.strasa.middleware.manager.KeyCharacteristicManagerImpl;
import org.strasa.middleware.manager.StudyGermplasmManagerImpl;
import org.strasa.middleware.manager.StudyRawDataManagerImpl;
import org.strasa.middleware.model.Germplasm;
import org.strasa.middleware.model.GermplasmType;
import org.strasa.middleware.model.KeyMajorGenes;
import org.strasa.web.common.api.Encryptions;
import org.strasa.web.common.api.ProcessTabViewModel;
import org.strasa.web.managegermplasm.view.pojos.GermplasmComparator;
import org.strasa.web.managegermplasm.view.pojos.GermplasmGroupingModel;
import org.strasa.web.uploadstudy.view.pojos.GermplasmDeepInfoModel;
import org.strasa.web.uploadstudy.view.pojos.GermplasmExt;
import org.strasa.web.utilities.FileUtilities;
import org.strasa.web.utilities.ListBoxValidationUtility;
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
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Listbox;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

import com.mysql.jdbc.StringUtils;

public class StudyGermplasmInfo extends ProcessTabViewModel {

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

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

		// wire event listener
		// Selectors.wireEventListeners(view, this);
		if (lstStudyGermplasm.isEmpty()) {
			gbUnknownGermplasm.setVisible(false);
			view.getFellow("uploadGenotypeData").setVisible(false);
		}
		if (lstKnownGermplasm.isEmpty()) {
			gbKnownGermplasm.setVisible(false);
		}
	}

	HashMap<String, GermplasmDeepInfoModel> lstStudyGermplasm = new HashMap<String, GermplasmDeepInfoModel>();

	private GermplasmDeepInfoModel selectedGermplasm;

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

		return new ArrayList<GermplasmDeepInfoModel>(lstKnownGermplasm.values());
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

	private List<String> lstAbiotics;

	public List<String> getLstAbiotics() {
		return lstAbiotics;
	}

	public void setLstAbiotics(List<String> lstAbiotics) {
		this.lstAbiotics = lstAbiotics;
	}

	private List<String> lstGrainQualities;

	public List<String> getLstGrainQualities() {
		return lstGrainQualities;
	}

	public void setLstGrainQualities(List<String> lstGrainQualities) {
		this.lstGrainQualities = lstGrainQualities;
	}

	private List<KeyMajorGenes> lstAllMajorGenes;

	public List<KeyMajorGenes> getLstAllMajorGenes() {
		return lstAllMajorGenes;
	}

	public void setLstAllMajorGenes(List<KeyMajorGenes> lstAllMajorGenes) {
		this.lstAllMajorGenes = lstAllMajorGenes;
	}

	private List<String> lstMajorGenes;

	public List<String> getLstMajorGenes() {
		return lstMajorGenes;
	}

	public void setLstMajorGenes(List<String> lstMajorGenes) {
		this.lstMajorGenes = lstMajorGenes;
	}

	public List<GermplasmType> getLstGermplasmType() {
		return lstGermplasmType;
	}

	public void setLstGermplasmType(List<GermplasmType> lstGermplasmType) {
		this.lstGermplasmType = lstGermplasmType;
	}

	public ArrayList<GermplasmDeepInfoModel> getLstStudyGermplasm() {
		return new ArrayList<GermplasmDeepInfoModel>(lstStudyGermplasm.values());
	}

	public void setLstStudyGermplasm(HashMap<String, GermplasmDeepInfoModel> lstStudyGermplasm) {
		this.lstStudyGermplasm = lstStudyGermplasm;
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
		System.out.println("_____________________________________________________________");

		for (GermplasmDeepInfoModel data : arrGermplasmDeepInfo) {
			System.out.println(data.toString());
		}

		System.out.println("_____________________________________________________________");

	}

	public String getTotalUnknownGermplasm() {
		return "List of new germplasm (" + lstStudyGermplasm.size() + " records)";
	}

	public String getTotalKnownGermplasm() {
		return "List of uploaded germplasm ("+lstKnownGermplasm.size() + " records )";
	}

	@Command
	public void selectGermplasm(@BindingParam("germplasm") GermplasmDeepInfoModel data) {
		if (selectedGermplasm != null) {
			if (data.getGermplasmname().equals(selectedGermplasm.getGermplasmname())) {
				return;
			}
		}

		selectedGermplasm = data;
		BindUtils.postNotifyChange(null, null, "*", "selectedGermplasm");
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
	public void init(@ExecutionArgParam("uploadModel") ProcessTabViewModel uploadModel) {

		initValues(uploadModel);
		// @Init
		// public void init() {
		// int studyID = 121; //small
		// int studyID = 132; // large
		// boolean isRaw = true;

		Runtimer timer = new Runtimer();
		timer.start();
		KeyCharacteristicManagerImpl keyMan = new KeyCharacteristicManagerImpl();

		lstAbiotics = keyMan.getAllAbioticAsString();
		lstBiotics = keyMan.getAllBioticAsString();
		lstGrainQualities = keyMan.getAllGrainQualityAsString();
		lstMajorGenes = keyMan.getAllMajorGenesAsString();

		GermplasmTypeManagerImpl germMan = new GermplasmTypeManagerImpl();

		lstGermplasmType = germMan.getAllGermplasmType();
		GermplasmCharacteristicMananagerImpl germCharMan = new GermplasmCharacteristicMananagerImpl();
		StudyRawDataManagerImpl rawMan = new StudyRawDataManagerImpl(isRaw);
		List<Germplasm> lst = rawMan.getStudyGermplasmInfo(studyID, dataset.getId());

		StudyGermplasmManagerImpl studyGermMan = new StudyGermplasmManagerImpl();

		GermplasmManagerImpl germplasmMan = new GermplasmManagerImpl();
		for (Germplasm germData : lst) {

			if (germplasmMan.isGermplasmExisting(germData.getGermplasmname())) {

				List<Germplasm> germplasmList = germplasmMan.getGermplasmListByName(germData.getGermplasmname());
				for (Germplasm subGermData : germplasmList) {
					GermplasmDeepInfoModel newData = new GermplasmDeepInfoModel(subGermData);
					newData.setCharacteristicValues(germCharMan.getGermplasmByGermplasmName(germData.getGermplasmname()));
					newData.setSelectedGermplasmType(getGermplasmTypeById(newData.getGermplasmtypeid()));
					newData.setKnown(true);
					newData.setRowIndex(lstKnownGermplasm.size());

					// IF Record does not exist, add
					if (!lstKnownGermplasm.containsKey(newData.getGermplasmname())) {
						lstKnownGermplasm.put(newData.getGermplasmname(), newData);

					}
					// IF Record is equal to the UserID
					if (newData.getUserid() == this.getUserID()) {

						// If record does not exist, add
						if (!lstKnownGermplasm.containsKey(newData.getGermplasmname())) {
							lstKnownGermplasm.put(newData.getGermplasmname(), newData);
						} else {
							// IF Record exist and if the previous record does
							// not exist in the studygermplasm table
							if (!lstKnownGermplasm.get(newData.getGermplasmname()).recordExist) {
								lstKnownGermplasm.put(newData.getGermplasmname(), newData);
							}
						}

					}
					// IF Record exist in the studgermplasm table
					if (studyGermMan.isGermplasmRecordExist(subGermData.getId(), this.studyID, this.dataset.getId())) {
						newData.recordExist = true;
						lstKnownGermplasm.put(newData.getGermplasmname(), newData);
					}

					arrGermplasmDeepInfo.add(newData);
				}
			} else {
				GermplasmDeepInfoModel newData = new GermplasmDeepInfoModel(germData);
				newData.setUserid(this.userID);

				newData.setKnown(false);
				newData.setRowIndex(lstStudyGermplasm.size());

				lstStudyGermplasm.put(newData.getGermplasmname(), newData);

			}

		}

		ArrayList<GermplasmDeepInfoModel> allData = new ArrayList<GermplasmDeepInfoModel>();
		allData.addAll(lstKnownGermplasm.values());
		allData.addAll(lstStudyGermplasm.values());

		selectedGermplasm = allData.get(0);
		timer.end();
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

	@NotifyChange({ "lstStudyGermplasm", "lstKnownGermplasm" })
	@Command("uploadGenotypeData")
	public void uploadGenotypeData(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx, @ContextParam(ContextType.VIEW) Component view) {

		UploadEvent event = (UploadEvent) ctx.getTriggerEvent();

		// System.out.println(event.getMedia().getStringData());

		String name = event.getMedia().getName();
		if (!name.endsWith(".csv")) {
			Messagebox.show("Error: File must be a text-based CSV  format", "Upload Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		try {
			String filename = name + Encryptions.encryptStringToNumber(name, new Date().getTime());
			File tempGenoFile = File.createTempFile(filename, ".tmp");
			InputStream in = event.getMedia().isBinary() ? event.getMedia().getStreamData() : new ReaderInputStream(event.getMedia().getReaderData());
			FileUtilities.uploadFile(tempGenoFile.getAbsolutePath(), in);
			List<GermplasmExt> lstGermplasm = CSVToBean(tempGenoFile);
			for (GermplasmExt germData : lstGermplasm) {
				if (!StringUtils.isNullOrEmpty(germData.getGermplasmname())) {
					if (lstStudyGermplasm.containsKey(germData.getGermplasmname())) {
						lstStudyGermplasm.get(germData.getGermplasmname()).setGermplasmtypeid(getGermplasmTypeById(germData.getGermplasmtype()));
						lstStudyGermplasm.get(germData.getGermplasmname()).setValueFromeGermplasmEx(germData, lstGermplasmType);
						lstStudyGermplasm.get(germData.getGermplasmname()).setGermplasmExCharacteristic(germData);

					}
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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
		columnMapping.put("OTHERNAME", "othername");
		columnMapping.put("BREEDER", "breeder");
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
			header[i] = header[i].toUpperCase();
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
			String validate = data.validate();
			// ((Row)
			// tblKnownGerm.getRows().getChildren().get(studyGerm)).setStyle("background-color: #FFF");
			if (validate != null) {
				Messagebox.show(validate, "OK", Messagebox.OK, Messagebox.EXCLAMATION);

				// ((Row)
				// tblKnownGerm.getRows().getChildren().get(studyGerm)).setStyle("background-color: #ff6666");
				// ((Row)
				// tblKnownGerm.getRows().getChildren().get(studyGerm)).setFocus(true);
				return false;
			}
			studyGerm++;

		}
		return true;

	}

	public boolean validateStudyGermplasm() {
		int studyGerm = 0;
		for (GermplasmDeepInfoModel data : lstStudyGermplasm.values()) {
			// ((Row)
			// tblStudyGerm.getRows().getChildren().get(studyGerm)).setStyle("background-color: #FFF");
			String validate = data.validate();
			if (validate != null) {
				Messagebox.show(validate, "OK", Messagebox.OK, Messagebox.EXCLAMATION);

				// ((Row)
				// tblStudyGerm.getRows().getChildren().get(studyGerm)).setStyle("background-color: #ff6666");
				// ((Row)
				// tblStudyGerm.getRows().getChildren().get(studyGerm)).setFocus(true);
				return false;
			}
			studyGerm++;

		}
		return true;

	}

	@Command
	public void updateCharacteristicInfo(@BindingParam("model") GermplasmDeepInfoModel model) {
		BindUtils.postNotifyChange(null, null, this.getLstStudyGermplasm().get(model.getRowIndex()), "*");
		this.tblStudyGerm.invalidate();
	}

	public boolean validateGermplasm(GermplasmDeepInfoModel data) {
		String validate = data.validate();
		if (!data.getStyleBG().equals("background-color: #FFF")) {
			data.setStyleBG("background-color: #FFF");
			BindUtils.postNotifyChange(null, null, data, "styleBG");
		}
		if (validate != null) {
			Messagebox.show(validate, "OK", Messagebox.OK, Messagebox.EXCLAMATION);

			data.setStyleBG("background-color: #ff6666");
			BindUtils.postNotifyChange(null, null, data, "styleBG");
			return false;
		}
		return true;
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

	@Override
	public boolean validateTab() {

		// Validation
		if (!validateKnownGermplasm()) {

			new ListBoxValidationUtility(tblKnownGerm, new ArrayList<Integer>(Arrays.asList(0, 1, 3, 4))).validateAll();
			;
			return false;
		}
		if (!validateStudyGermplasm()) {
			new ListBoxValidationUtility(tblStudyGerm, new ArrayList<Integer>(Arrays.asList(0, 1, 3, 4))).validateAll();
			;

			return false;
		}

		StudyGermplasmManagerImpl studyGermplasmMan = new StudyGermplasmManagerImpl();
		GermplasmManagerImpl germplasmManagerImpl = new GermplasmManagerImpl();
		// List<StudyGermplasm> lstStudyGerm =
		// convertDeepInfoToModel(lstStudyGermplasm.values());
		GermplasmCharacteristicMananagerImpl germCharMan = new GermplasmCharacteristicMananagerImpl();
		List<GermplasmDeepInfoModel> lstStudyGermpl = new ArrayList<GermplasmDeepInfoModel>();
		lstStudyGermpl.addAll(lstKnownGermplasm.values());
		lstStudyGermpl.addAll(lstStudyGermplasm.values());
		germplasmManagerImpl.addGermplasmList(lstStudyGermplasm.values());

		studyGermplasmMan.addStudyGermplasmBatch(lstStudyGermpl, this.studyID, this.dataset.getId(), this.userID);
		germCharMan.addCharacteristicBatch(lstStudyGermplasm.values());

		return true;

	}

	// @Init
	// public void init() {
	// this.studyID = 1;
	// StudyGermplasmManagerImpl germplasmMan = new StudyGermplasmManagerImpl();
	// setLstStudyGermplasm(germplasmMan.getStudyGermplasmByStudyId(5));
	// }

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
			System.out.println("DURATION : " + (endTime - startTime) / 1000000000.0);
			return (endTime - startTime) / 1000000000.0;
		}

	}

}
