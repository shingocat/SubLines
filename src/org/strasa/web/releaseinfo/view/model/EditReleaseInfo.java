package org.strasa.web.releaseinfo.view.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.strasa.middleware.manager.CountryManagerImpl;
import org.strasa.middleware.manager.EcotypeManagerImpl;
import org.strasa.middleware.manager.ReleaseInfoManagerImpl;
import org.strasa.middleware.manager.GermplasmManagerImpl;
import org.strasa.middleware.manager.LocationManagerImpl;
import org.strasa.middleware.manager.ProgramManagerImpl;
import org.strasa.middleware.manager.ProjectManagerImpl;
import org.strasa.middleware.manager.StudyLocationManagerImpl;
import org.strasa.middleware.manager.StudyManagerImpl;
import org.strasa.middleware.manager.StudySiteManagerImpl;
import org.strasa.middleware.model.Country;
import org.strasa.middleware.model.Ecotype;
import org.strasa.middleware.model.ReleaseInfo;
import org.strasa.middleware.model.Location;
import org.strasa.middleware.model.Program;
import org.strasa.middleware.model.Project;
import org.strasa.web.admin.view.model.EditAbioticKey;
import org.strasa.web.uploadstudy.view.model.AddLocation;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;


public class EditReleaseInfo {
	ReleaseInfoManagerImpl man;
	StudySiteManagerImpl studySiteMan;
	ProgramManagerImpl programMan;
	ProjectManagerImpl projectMan;
	
	List<RowStatus> rowList = new ArrayList<RowStatus>();
	private List<Program> programList= null;
	private List<Project> projectList= null;
	
	private ArrayList<String> cmbCountry = new ArrayList<String>(); 
	private HashMap<Integer,String> programKeyList = new HashMap<Integer,String>();
	private HashMap<Integer,String> projectKeyList = new HashMap<Integer,String>();
	
	public List<RowStatus> getRowList() {
		return rowList;
	}

	public void setRowList(List<RowStatus> rowList) {
		this.rowList = rowList;
	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view){
		man = new ReleaseInfoManagerImpl();
		studySiteMan = new StudySiteManagerImpl();
		programMan = new ProgramManagerImpl();
		projectMan = new ProjectManagerImpl();
		
		setProgramList(new ArrayList<Program>());
		for(Program p: programMan.getAllProgram()){
			if(!projectMan.getProjectByProgramId(p.getId()).isEmpty())programList.add(p);
		}
		setProjectList(projectMan.getAllProject());
		
		makeRowStatus(man.getAllReleaseInfo());

		List<Country> lCountries = new CountryManagerImpl().getAllCountry();
		for (Country data : lCountries) {
			cmbCountry.add(data.getIsoabbr());
		}
	}

	private void makeRowStatus(List<ReleaseInfo> list) {
		// TODO Auto-generated method stub
		projectKeyList.clear();
		programKeyList.clear();
		rowList.clear();
		for (ReleaseInfo p: list){
			Program prog = programMan.getProgramById(p.getProgramid());
			programKeyList.put(prog.getId(),prog.getName());

			Project proj = projectMan.getProjectById(p.getProjectid());
			projectKeyList.put(proj.getId(),proj.getName());
			
			RowStatus ps = new RowStatus(p,false, prog, proj);
			rowList.add(ps);
		}
	}

	@NotifyChange("projectList")
	@Command
	public void changeEditableStatus(@BindingParam("RowStatus") RowStatus ps) {
		setProjectList(projectMan.getProjectByProgramId(ps.getValue().getProgramid()));
		unCheckAllRowStatusExcept(ps);
		ps.setEditingStatus(!ps.getEditingStatus());
		refreshRowTemplate(ps);
	}
	
	private void unCheckAllRowStatusExcept(RowStatus ps) {
		// TODO Auto-generated method stub
		for(RowStatus rs: rowList){
			if(!rs.equals(ps))rs.setEditingStatus(false);
			refreshRowTemplate(rs);
		}
	}

	@Command
	public String getProgramName(@BindingParam("programId") Integer programId) {
		return programKeyList.get(programId);
	}

	@Command
	public void confirm(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view, @BindingParam("RowStatus") RowStatus ps) {
		Combobox programComboBox = (Combobox) component.getFellow("programComboBox");
		Combobox projectComboBox = (Combobox) component.getFellow("projectComboBox");
		
//		Program prog= programMan.getProgramById((Integer)programComboBox.getSelectedItem().getValue());
//		Project proj= projectMan.getProjectById((Integer)projectComboBox.getSelectedItem().getValue());
		
		ps.getValue().setProgramid((Integer)programComboBox.getSelectedItem().getValue());
		ps.getValue().setProjectid((Integer)projectComboBox.getSelectedItem().getValue());  
//		ps.setProject(proj);
		refreshRowTemplate(ps);
		man.updateReleaseInfo(ps.getValue());
		Messagebox.show("Changes saved.");
		changeEditableStatus(ps);
	}

	public void refreshRowTemplate(RowStatus ps) {
		/*
		 * This code is special and notifies ZK that the bean's value
		 * has changed as it is used in the template mechanism.
		 * This stops the entire Grid's data from being refreshed
		 */
		BindUtils.postNotifyChange(null, null, ps, "editingStatus");
	}

	@SuppressWarnings("unchecked")
	@NotifyChange("rowList")
	@Command("delete")
	public void delete(@BindingParam("id") final Integer Id){
		//		if(studySiteMan.getSiteByEcotypeId(Id).isEmpty()){
		Messagebox.show("Are you sure?",
				"Delete", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new EventListener() {
			public void onEvent(Event e) {
				if ("onOK".equals(e.getName())) {
					man.deleteById(Id);
					makeRowStatus(man.getAllReleaseInfo());
					BindUtils.postNotifyChange(null, null,
							EditReleaseInfo.this, "rowList");
					Messagebox.show("Changes saved.");
				} else if ("onCancel".equals(e.getName())) {
				}
			}
		});

		//		} else  Messagebox.show("Cannot delete an extension data that is in use.", "Error", Messagebox.OK, Messagebox.ERROR); 
	}

	//	@NotifyChange("list")
	@Command("add")
	public void add(@ContextParam(ContextType.COMPONENT) Component component) {
		Window win = (Window) component.getFellow("editReleaseInfo");
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("oldVar", null);

		Window popup = (Window) Executions.createComponents(
				AddReleaseInfo.ZUL_PATH, win, params);

		popup.doModal();
	}

	@NotifyChange("rowList")
	@GlobalCommand("refreshReleaseInfoList")
	public void refreshReleaseInfoList() {
		makeRowStatus(man.getAllReleaseInfo());
	}

	@NotifyChange("projectList")
	@Command
	public void updateLists(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view, @BindingParam("program") Comboitem program){
		Combobox projectComboBox = (Combobox) component.getFellow("projectComboBox");
		
		try{
		setProjectList(projectMan.getProjectByProgramId((Integer)program.getValue()));
		projectComboBox.setValue(projectList.get(0).getName());
		BindUtils.postNotifyChange(null, null,
				EditReleaseInfo.this, "projectList");
		}catch(RuntimeException re){
			setProjectList(projectMan.getAllProject());
		}
	}
	
	public ArrayList<String> getCmbCountry() {
		return cmbCountry;
	}

	public void setCmbCountry(ArrayList<String> cmbCountry) {
		this.cmbCountry = cmbCountry;
	}

	public List<Program> getProgramList() {
		return programList;
	}

	public void setProgramList(List<Program> programList) {
		this.programList = programList;
	}


	public List<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}


	public class RowStatus {
		private  Program program;
		private Project project;
		private  ReleaseInfo value;
		private boolean editingStatus;

		public RowStatus(ReleaseInfo p, boolean editingStatus, Program program, Project project) {
			this.setValue(p);
			this.editingStatus = editingStatus;
			this.setProgram(program);
			this.setProject(project);
		}


		public boolean getEditingStatus() {
			return editingStatus;
		}

		public void setEditingStatus(boolean editingStatus) {
			this.editingStatus = editingStatus;
		}


		public ReleaseInfo getValue() {
			return value;
		}


		public void setValue(ReleaseInfo p) {
			this.value = p;
		}


		public Program getProgram() {
			return program;
		}


		public void setProgram(Program program) {
			this.program = program;
		}


		public Project getProject() {
			return project;
		}


		public void setProject(Project project) {
			this.project = project;
		}

	}
}