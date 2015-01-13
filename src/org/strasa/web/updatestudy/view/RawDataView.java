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
package org.strasa.web.updatestudy.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.input.ReaderInputStream;
import org.strasa.middleware.manager.ProgramManagerImpl;
import org.strasa.middleware.manager.ProjectManagerImpl;
import org.strasa.middleware.manager.StudyDataColumnManagerImpl;
import org.strasa.middleware.manager.StudyDataSetManagerImpl;
import org.strasa.middleware.manager.StudyGermplasmManagerImpl;
import org.strasa.middleware.manager.StudyLocationManagerImpl;
import org.strasa.middleware.manager.StudyManager;
import org.strasa.middleware.manager.StudyManagerImpl;
import org.strasa.middleware.manager.StudyRawDataManagerImpl;
import org.strasa.middleware.manager.StudySiteManagerImpl;
import org.strasa.middleware.manager.StudyTypeManagerImpl;
import org.strasa.middleware.manager.StudyVariableManagerImpl;
import org.strasa.middleware.model.Program;
import org.strasa.middleware.model.Project;
import org.strasa.middleware.model.Study;
import org.strasa.middleware.model.StudyDataSet;
import org.strasa.middleware.model.StudyType;
import org.strasa.web.common.api.Encryptions;
import org.strasa.web.common.api.ProcessTabViewModel;
import org.strasa.web.uploadstudy.view.model.AddProgram;
import org.strasa.web.uploadstudy.view.model.AddProject;
import org.strasa.web.uploadstudy.view.model.DataColumnChanged;
import org.strasa.web.uploadstudy.view.model.DataColumnValidation;
import org.strasa.web.uploadstudy.view.pojos.UploadCSVDataVariableModel;
import org.strasa.web.utilities.FileUtilities;
import org.zkoss.bind.BindContext;
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
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import au.com.bytecode.opencsv.CSVReader;

public class RawDataView extends ProcessTabViewModel {

	// Wired Components

	@Wire("#gbUploadData")
	Groupbox gbUploadData;

	@Wire("#divRawData")
	Div divRawData;

	@Wire("#datagrid")
	Div divDatagrid;
	private List<String> columnList = new ArrayList<String>();
	public String dataFileName;
	private List<String[]> dataList = new ArrayList<String[]>();
	private ArrayList<Program> programList = new ArrayList<Program>();
	private ArrayList<Project> projectList = new ArrayList<Project>();
	private ArrayList<String> studyTypeList = new ArrayList<String>();

	private ArrayList<String> dataTypeList = new ArrayList<String>();
	private ArrayList<GenotypeFileModel> genotypeFileList = new ArrayList<RawDataView.GenotypeFileModel>();
	private Program txtProgram = new Program();
	private Project txtProject = new Project();
	private boolean isDataUploaded = false;
	private boolean processComplete = false;

	public boolean isDataUploaded() {
		return isDataUploaded;
	}

	public void setDataUploaded(boolean isDataUploaded) {
		this.isDataUploaded = isDataUploaded;
	}

	public Program getTxtProgram() {
		return txtProgram;
	}

	@NotifyChange({ "projectList", "txtProject" })
	public void setTxtProgram(Program txtProgram) {
		refreshProjectList(txtProgram);
		this.txtProgram = txtProgram;
		txtProject = null;
	}

	public void setTxtProject(Project txtProject) {
		this.txtProject = txtProject;
	}

	private String txtStudyName = new String();
	private String txtStudyType = new String();
	private int startYear = Calendar.getInstance().get(Calendar.YEAR);
	private int endYear = Calendar.getInstance().get(Calendar.YEAR);
	private int pageSize = 10;
	private int activePage = 0;
	private File tempFile;
	private String uploadTo = "database";
	private String studyType = "rawdata";
	private boolean isNewDataSet = true;

	public ArrayList<GenotypeFileModel> getGenotypeFileList() {
		return genotypeFileList;
	}

	public void setGenotypeFileList(ArrayList<GenotypeFileModel> genotypeFileList) {
		this.genotypeFileList = genotypeFileList;
	}

	public int getTotalSize() {
		return dataList.size();
	}

	public Study getStudy() {
		return study;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	private String txtYear = "";

	public int getPageSize() {
		return pageSize;
	}

	@NotifyChange("*")
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@NotifyChange("*")
	public int getActivePage() {

		return activePage;
	}

	@NotifyChange("*")
	public void setActivePage(int activePage) {
		System.out.println("pageSize");
		reloadCsvGrid();
		this.activePage = activePage;
	}

	public boolean isVariableDataVisible = true;

	private Study study;

	public List<UploadCSVDataVariableModel> varData = new ArrayList<UploadCSVDataVariableModel>();

	private StudyDataSet newdataset;

	public ArrayList<Program> getProgramList() {
		return programList;
	}

	public void setProgramList(ArrayList<Program> programList) {
		this.programList = programList;
	}

	public String getUploadTo() {
		return uploadTo;
	}

	public void setUploadTo(String uploadTo) {
		this.uploadTo = uploadTo;
	}

	public String getStudyType() {
		return studyType;
	}

	public void setStudyType(String studyType) {
		this.studyType = studyType;
	}

	public ArrayList<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(ArrayList<Project> projectList) {
		this.projectList = projectList;
	}

	public ArrayList<String> getDataTypeList() {
		return dataTypeList;
	}

	public void setDataTypeList(ArrayList<String> dataTypeList) {
		this.dataTypeList = dataTypeList;
	}

	public ArrayList<String> getStudyTypeList() {

		studyTypeList.clear();

		for (StudyType studyType : new StudyTypeManagerImpl().getAllStudyType()) {
			studyTypeList.add(studyType.getStudytype());
		}
		;
		return studyTypeList;
	}

	public void setStudyTypeList(ArrayList<String> studyTypeList) {
		this.studyTypeList = studyTypeList;
	}

	public Project getTxtProject() {
		return txtProject;
	}

	public String getTxtStudyName() {
		return txtStudyName;
	}

	public void setTxtStudyName(String txtStudyName) {
		this.txtStudyName = txtStudyName;
	}

	public String getTxtStudyType() {
		return txtStudyType;
	}

	public void setTxtStudyType(String txtStudyType) {
		this.txtStudyType = txtStudyType;
	}

	public String getTxtYear() {
		return txtYear;
	}

	public void setTxtYear(String txtYear) {
		this.txtYear = txtYear;
	}

	public List<String> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}

	public List<String[]> getDataList() {
		if (true)
			return dataList;
		ArrayList<String[]> pageData = new ArrayList<String[]>();
		for (int i = activePage * pageSize; i < activePage * pageSize + pageSize; i++) {
			pageData.add(dataList.get(i));
			System.out.println(Arrays.toString(dataList.get(i)));
		}

		return pageData;
	}

	public ArrayList<ArrayList<String>> getCsvData() {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		if (dataList.isEmpty())
			return result;
		for (int i = activePage * pageSize; i < activePage * pageSize + pageSize && i < dataList.size(); i++) {
			ArrayList<String> row = new ArrayList<String>();
			row.addAll(Arrays.asList(dataList.get(i)));
			result.add(row);
			row.add(0, "  ");
			System.out.println(Arrays.toString(dataList.get(i)) + "ROW: " + row.get(0));
		}
		return result;
	}

	public void setDataList(List<String[]> dataList) {
		this.dataList = dataList;
	}

	public String getDataFileName() {
		return dataFileName;
	}

	public boolean isVariableDataVisible() {
		return isVariableDataVisible;
	}

	public void setVariableDataVisible(boolean isVariableDataVisible) {
		this.isVariableDataVisible = isVariableDataVisible;
	}

	public List<UploadCSVDataVariableModel> getVarData() {
		return varData;
	}

	@Command
	@NotifyChange("variableData")
	public void changeVar(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx, @ContextParam(ContextType.VIEW) Component view, @BindingParam("oldVar") String oldVar) {

		Map<String, Object> params = new HashMap<String, Object>();
		System.out.println(oldVar);
		params.put("oldVar", oldVar);
		params.put("parent", view);

		Window popup = (Window) Executions.createComponents(DataColumnChanged.ZUL_PATH, view, params);

		popup.doModal();
	}

	@NotifyChange("*")
	@Command("uploadCSV")
	public void uploadCSV(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx, @ContextParam(ContextType.VIEW) Component view) {

		UploadEvent event = (UploadEvent) ctx.getTriggerEvent();

		// System.out.println(event.getMedia().getStringData());

		String name = event.getMedia().getName();
		if (tempFile == null)
			try {
				tempFile = File.createTempFile(name, ".tmp");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		if (!name.endsWith(".csv")) {
			Messagebox.show("Error: File must be a text-based csv format", "Upload Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		InputStream in = event.getMedia().isBinary() ? event.getMedia().getStreamData() : new ReaderInputStream(event.getMedia().getReaderData());
		FileUtilities.uploadFile(tempFile.getAbsolutePath(), in);
		BindUtils.postNotifyChange(null, null, this, "*");

		ArrayList<String> invalidHeader = new ArrayList<String>();
		boolean isHeaderValid = true;
		try {
			StudyVariableManagerImpl studyVarMan = new StudyVariableManagerImpl();
			CSVReader reader = new CSVReader(new FileReader(tempFile.getAbsolutePath()));
			String[] header = reader.readNext();
			for (String column : header) {
				if (!studyVarMan.hasVariable(column)) {
					invalidHeader.add(column);
					isHeaderValid = false;
				}
			}
			List<String[]> dataPrep = reader.readAll();
			dataPrep.remove(0);
			if (!new StudyManagerImpl().validateCSVDataForGermplasmComparision(this.studyID, dataPrep, 0, this.isRaw)) {
				Map<String, Object> arguments = new HashMap<String, Object>();
				arguments.put("list", new StudyManagerImpl().getUnknownGermplasmFromCSVData(this.studyID, dataPrep, 0, this.isRaw));

				String template = "/user/updatestudy/validategermplasmbox.zul";
				Window window = (Window) Executions.createComponents(template, null, arguments);
				window.doModal();
			}
			;
			System.out.println(invalidHeader.size());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		isVariableDataVisible = true;
		dataFileName = name;
		if (!isHeaderValid)
			openCSVHeaderValidator(tempFile.getAbsolutePath(), false);
		else {
			refreshCsv();
			if (this.isUpdateMode)
				isNewDataSet = true;

		}

	}

	public void uploadFile(String path, String name, String data) {

		String filePath = path + name;

		try {
			PrintWriter out = new PrintWriter(filePath);
			out.println(data);
			out.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Command("addProgram")
	public void addProgram() {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("oldVar", null);
		params.put("parent", getMainView());

		Window popup = (Window) Executions.createComponents(AddProgram.ZUL_PATH, getMainView(), params);

		popup.doModal();
	}

	@Command("showzulfile")
	public void showzulfile(@BindingParam("zulFileName") String zulFileName, @BindingParam("target") Tabpanel panel) {
		if (panel != null && panel.getChildren().isEmpty()) {
			Map arg = new HashMap();
			arg.put("studyid", this.studyID);
			Executions.createComponents(zulFileName, panel, arg);

		}
	}

	@Command("addProject")
	public void addProject() {

		if (txtProgram == null) {
			Messagebox.show("Error: Please specify/select a program first.", "Upload Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("oldVar", null);
		params.put("parent", getMainView());
		params.put("programID", txtProgram.getId());

		Window popup = (Window) Executions.createComponents(AddProject.ZUL_PATH, getMainView(), params);

		popup.doModal();
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

		if (this.isUpdateMode) {
			Map arg = new HashMap();
			arg.put("studyId", this.studyID);
			arg.put("dataset", this.dataset.getId());
			arg.put("dataType", (this.isRaw) ? "rd" : "dd");
			Executions.createComponents("/user/browsestudy/data.zul", divRawData, arg);

		}
		isVariableDataVisible = false;
	}

	@GlobalCommand
	public void testGlobalCom(@BindingParam("newVal") double newVal) {
		System.out.println("globalCom: " + newVal);
	}

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("uploadModel") ProcessTabViewModel uploadModel) {

		this.initValues(uploadModel);

		setMainView(view);
		System.out.println("SID: " + this.studyID);
		refreshProgramList(null);
		refreshProjectList(null);
		System.out.println("LOADED");
		if (this.isUpdateMode) {
			StudyManagerImpl studyMan = new StudyManagerImpl();
			study = studyMan.getStudyById(uploadModel.getStudyID());
			this.txtStudyName = study.getName();
			this.txtStudyType = new StudyTypeManagerImpl().getStudyTypeById(study.getStudytypeid()).getStudytype();
			this.txtProgram = new ProgramManagerImpl().getProgramById(study.getProgramid());
			this.txtProject = new ProjectManagerImpl().getProjectById(study.getProjectid());
			this.startYear = Integer.parseInt(study.getStartyear());
			this.endYear = Integer.parseInt(study.getEndyear());
			isNewDataSet = false;

		} else {
			this.isDataReUploaded = true;
		}
	}

	public void openCSVHeaderValidator(String CSVPath, boolean showAll) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("CSVPath", CSVPath);
		params.put("parent", getMainView());
		params.put("showAll", showAll);
		params.put("tabModel", this);
		Window popup = (Window) Executions.createComponents(DataColumnValidation.ZUL_PATH, getMainView(), params);

		popup.doModal();
	}

	@Command
	public void updateColumnHeader() {
		openCSVHeaderValidator("", true);
	}

	@NotifyChange("*")
	@Command("refreshVarList")
	public void refreshList(@BindingParam("newValue") String newValue, @BindingParam("oldVar") String oldVar) {
		for (int i = 0; i < varData.size(); i++) {

			if (varData.get(i).getCurrentVariable().equals(oldVar)) {
				System.out.println("   ss");
				varData.get(i).setNewVariable(newValue);
			}

		}

	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command("removeUpload")
	public void removeUpload() {
		reloadCsvGrid();
		if (!isDataUploaded) {
			isVariableDataVisible = false;
			dataFileName = "";
			// isNewDataset = true;
			varData.clear();
			dataList.clear();
			columnList.clear();
			isDataUploaded = false;

			return;
		}
		Messagebox.show("Are you sure you want to delete the previous uploaded data? WARNING! This cannot be undone.", "Delete all data?", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {
			public void onEvent(Event e) {
				if ("onOK".equals(e.getName())) {
					isVariableDataVisible = false;
					dataFileName = "";
					isNewDataSet = true;
					varData.clear();
					columnList.clear();
					dataList.clear();
					isDataUploaded = false;
					processComplete = false;
					BindUtils.postGlobalCommand(null, null, "disableTabs", null);
					BindUtils.postNotifyChange(null, null, RawDataView.this, "*");
					new StudyManagerImpl().deleteStudyById(RawDataView.this.studyID, RawDataView.this.getDataset().getId());

				} else if ("onCancel".equals(e.getName())) {

				}

				/*
				 * Event Name Mapping list Messagebox.YES = "onYes";
				 * Messagebox.NO = "onNo"; Messagebox.RETRY = "onRetry";
				 * Messagebox.ABORT = "onAbort"; Messagebox.IGNORE = "onIgnore";
				 * Messagebox.CANCEL = "onCancel"; Messagebox.OK = "onOK";
				 */
			}
		});

	}

	@SuppressWarnings("unchecked")
	@NotifyChange("*")
	@Command("removeRawData")
	public void removeRawData() {
		Messagebox.show("Are you sure you want to delete the previous uploaded data? You need to fill up the meta information again. WARNING! This cannot be undone.", "Delete all data?", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {
			public void onEvent(Event e) {
				if ("onOK".equals(e.getName())) {
					isVariableDataVisible = false;
					dataFileName = "";
					isNewDataSet = true;
					varData.clear();
					isDataUploaded = false;
					BindUtils.postGlobalCommand(null, null, "disableTabs", null);
					BindUtils.postNotifyChange(null, null, RawDataView.this, "*");
					new StudyRawDataManagerImpl(studyType.equalsIgnoreCase("rawdata")).deleteByStudyId(study.getId());
					new StudyDataColumnManagerImpl().removeStudyDataColumnByStudyId(RawDataView.this.studyID, "rd", dataset.getId());

					RawDataView.this.isDataReUploaded = true;

				} else if ("onCancel".equals(e.getName())) {

				}

				/*
				 * Event Name Mapping list Messagebox.YES = "onYes";
				 * Messagebox.NO = "onNo"; Messagebox.RETRY = "onRetry";
				 * Messagebox.ABORT = "onAbort"; Messagebox.IGNORE = "onIgnore";
				 * Messagebox.CANCEL = "onCancel"; Messagebox.OK = "onOK";
				 */
			}
		});

	}

	public void deleteAll() {
		isVariableDataVisible = false;
		dataFileName = "";
		isNewDataSet = true;
		varData.clear();
		isDataUploaded = false;
		BindUtils.postGlobalCommand(null, null, "disableTabs", null);
		new StudyRawDataManagerImpl(studyType.equalsIgnoreCase("rawdata")).deleteByStudyId(study.getId());
		;
		new StudySiteManagerImpl(studyType.equalsIgnoreCase("rawdata")).removeSiteByStudyId(study.getId(), null);
		new StudyLocationManagerImpl(studyType.equalsIgnoreCase("rawdata")).removeLocationByStudyId(study.getId());
		new StudyGermplasmManagerImpl().removeGermplasmByStudyId(study.getId());

	}

	public void reloadCsvGrid() {

		System.out.println("RELOAD CSV CALLED");
		if (!divDatagrid.getChildren().isEmpty())
			divDatagrid.getFirstChild().detach();
		if (this.isUpdateMode) {
			Include incCSVData = new Include();
			incCSVData.setSrc("/user/browsestudy/data.zul");
			incCSVData.setParent(divDatagrid);
			incCSVData.setDynamicProperty("studyId", this.studyID);
			incCSVData.setDynamicProperty("dataset", this.dataset.getId());
			incCSVData.setDynamicProperty("dataType", (this.isRaw) ? "rd" : "dd");

		}

		else {
			Include incCSVData = new Include();
			incCSVData.setSrc("/user/updatestudy/csvdata.zul");
			incCSVData.setParent(divDatagrid);
		}

		gbUploadData.invalidate();
	}

	@NotifyChange("*")
	@Command("refreshCsv")
	public void refreshCsv() {

		activePage = 0;
		CSVReader reader;

		reloadCsvGrid();
		if (this.isUpdateMode)
			return;
		try {
			reader = new CSVReader(new FileReader(tempFile.getAbsolutePath()));
			List<String[]> rawData = reader.readAll();
			columnList.clear();
			dataList.clear();
			columnList = new ArrayList<String>(Arrays.asList(rawData.get(0)));
			rawData.remove(0);
			dataList = new ArrayList<String[]>(rawData);
			System.out.println(Arrays.toString(dataList.get(0)));
			if (!this.isDataReUploaded)
				gbUploadData.invalidate();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@NotifyChange("*")
	@Command("refreshProgramList")
	public void refreshProgramList(@BindingParam("selected") Program selected) {

		ProgramManagerImpl programMan = new ProgramManagerImpl();
		programList.clear();
		programList.addAll(programMan.getAllProgram());
		System.out.print(selected);
		txtProgram = selected;

	}

	@NotifyChange({ "txtProject", "projectList" })
	@Command("changeProjectList")
	public void changeProjectList(@BindingParam("selected") Project selected) {
		refreshProjectList(txtProgram);
		txtProject = selected;

	}

	@NotifyChange("projectList")
	@Command("refreshProjectList")
	public void refreshProjectList(@BindingParam("selected") Program selected) {

		txtProject = null;
		if (selected == null) {
			projectList.clear();
			return;

		}

		ProjectManagerImpl programMan = new ProjectManagerImpl();
		projectList.clear();
		projectList.addAll(programMan.getProjectList(selected));

	}

	@Override
	public boolean validateTab() {
		if (processComplete)
			return true;
		if (this.isUpdateMode)
			return true;
		Runtimer timer = new Runtimer();
		timer.start();
		boolean isRawData = this.isRaw;
		System.out.println("StudyType: " + studyType + " + " + isRawData);
		StudyManager studyMan = new StudyManager();
		HashSet noDupSet = new HashSet();
		noDupSet.addAll(columnList);
		if (noDupSet.size() != columnList.size()) {
			Messagebox.show("Error: Column duplication detected. Columns should be unique", "Upload Error", Messagebox.OK, Messagebox.ERROR);

			// TODO: must have message DIalog
			return false;
		}

		if (tempFile == null || !isVariableDataVisible) {
			if (!isUpdateMode()) {
				Messagebox.show("Error: You must upload a data first", "Upload Error", Messagebox.OK, Messagebox.ERROR);

				return false;
			}
		}

		StudyRawDataManagerImpl studyRawData = new StudyRawDataManagerImpl(isRawData);

		study = studyMan.getStudyByStudyId(this.getStudyID());
		if (study == null) {
			study = new Study();
			study.setShared(false);
		}

		if (uploadTo.equals("database")) {

			if (isNewDataSet) {
				System.out.println("DATA UPLOADING! ");
				if (newdataset == null) {
					newdataset = new StudyDataSet();
					newdataset.setStudyid(this.studyID);
					newdataset.setDatatype((this.isRaw) ? "rd" : "dd");
					newdataset.setTitle(this.dataset.getTitle());

					this.mainTab.setLabel(this.dataset.getTitle());
					new StudyDataSetManagerImpl().addDataSet(newdataset);
				}
				studyRawData.addStudyRawData(study, columnList.toArray(new String[columnList.size()]), dataList, newdataset.getId(), isRawData, this.userID);
				this.dataset = newdataset;
			}

			studyRawData.addStudy(study);

//			new StudyDataColumnManagerImpl().addStudyDataColumn(study.getId(), columnList.toArray(new String[columnList.size()]), isRawData, this.dataset.getId());

			isDataUploaded = true;
			BindUtils.postNotifyChange(null, null, this, "*");

		}
		refreshCsv();
		processComplete = true;
		return true;

	}

	@NotifyChange("genotypeFileList")
	@Command("uploadGenotypeData")
	public void uploadGenotypeData(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx, @ContextParam(ContextType.VIEW) Component view) {

		UploadEvent event = (UploadEvent) ctx.getTriggerEvent();

		// System.out.println(event.getMedia().getStringData());

		String name = event.getMedia().getName();
		if (!name.endsWith(".txt")) {
			Messagebox.show("Error: File must be a text-based  format", "Upload Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		try {
			String filename = name + Encryptions.encryptStringToNumber(name, new Date().getTime());
			File tempGenoFile = File.createTempFile(filename, ".tmp");
			InputStream in = event.getMedia().isBinary() ? event.getMedia().getStreamData() : new ReaderInputStream(event.getMedia().getReaderData());
			FileUtilities.uploadFile(tempGenoFile.getAbsolutePath(), in);

			GenotypeFileModel newGenotypeFile = new GenotypeFileModel(name, tempGenoFile);
			genotypeFileList.add(newGenotypeFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Command
	public void modifyDataHeader() {
		openCSVHeaderValidator(tempFile.getAbsolutePath(), true);
	}

	@NotifyChange("genotypeFileList")
	@Command("removeGenotypeFile")
	public void removeGenotypeFile(@BindingParam("index") int index) {
		System.out.println("Deleted file index: " + index);
		genotypeFileList.get(index).tempFile.delete();
		genotypeFileList.remove(index);
	}

	public class Runtimer {
		long startTime = System.nanoTime();

		public long start() {
			startTime = System.nanoTime();
			return startTime;
		}

		public double end() {
			long endTime = System.nanoTime();
			return (endTime - startTime) / 1000000000.0;
		}

	}

	public class GenotypeFileModel {

		private String name;
		private File tempFile;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public File getFilepath() {
			return tempFile;
		}

		public void setFilepath(File filepath) {
			this.tempFile = filepath;
		}

		public GenotypeFileModel(String name, File path) {
			this.name = name;
			this.tempFile = path;
		}

	}
}
