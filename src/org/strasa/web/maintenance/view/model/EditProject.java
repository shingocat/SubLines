package org.strasa.web.maintenance.view.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.strasa.middleware.manager.ProgramManagerImpl;
import org.strasa.middleware.manager.ProjectManagerImpl;
import org.strasa.middleware.manager.StudyManagerImpl;
import org.strasa.middleware.model.Program;
import org.strasa.middleware.model.Project;
import org.strasa.web.distributionandextension.view.model.EditDistributionAndExtension.RowStatus;
import org.strasa.web.uploadstudy.view.model.AddProject;
import org.zkoss.bind.BindUtils;
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


public class EditProject {
	ProjectManagerImpl projectMan;
	List<ProjectStatus> projectList = new ArrayList<ProjectStatus>(); 
	List<Program> programList = new ArrayList<Program>(); 

	public List<Program> getProgramList() {
		return programList;
	}
	public void setProgramList(List<Program> programList) {
		this.programList = programList;
	}

	private StudyManagerImpl studyMan;
	private ProgramManagerImpl programMan;

	public List<ProjectStatus> getProjectList() {
		return projectList;
	}
	public void setProjectList(List<ProjectStatus> projectList) {
		this.projectList = projectList;
	}
	@Init
	public void init(){

		studyMan = new StudyManagerImpl();
		programMan = new ProgramManagerImpl();
		projectMan = new ProjectManagerImpl();
		//		projectList = new ArrayList<Project>();

		makeProjectStatus(projectMan.getProjectByUserId());
		setProgramList(programMan.getAllProgram());
	}

	private void makeProjectStatus(List<Project> projectByUserId) {
		// TODO Auto-generated method stub

		projectList.clear();
		for (Project p: projectByUserId){
			System.out.println("programId "+ Integer.toString(p.getProgramid()));
			Program program = programMan.getProgramById(p.getProgramid());
			ProjectStatus ps = new ProjectStatus(p,program, false);
			projectList.add(ps);
		}
	}

	@Command
	public void changeEditableStatus(@BindingParam("ProjectStatus") ProjectStatus ps) {
		unCheckAllRowStatusExcept(ps);
		ps.setEditingStatus(!ps.getEditingStatus());
		refreshRowTemplate(ps);
	}

	private void unCheckAllRowStatusExcept(ProjectStatus ps) {
		// TODO Auto-generated method stub
		for(ProjectStatus rs: projectList){
			if(!rs.equals(ps))rs.setEditingStatus(false);
			refreshRowTemplate(rs);
		}
	}
	
	@Command
	public void confirm(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view, @BindingParam("ProjectStatus") ProjectStatus ps) {
		Combobox programComboBox = (Combobox) component.getFellow("programComboBox");
		
		
		ps.getProject().setProgramid((Integer)programComboBox.getSelectedItem().getValue());
		
		changeEditableStatus(ps);
		refreshRowTemplate(ps);
		projectMan.updateProject(ps.getProject());
		Messagebox.show("Changes saved.");
	}

	public void refreshRowTemplate(ProjectStatus ps) {
		/*
		 * This code is special and notifies ZK that the bean's value
		 * has changed as it is used in the template mechanism.
		 * This stops the entire Grid's data from being refreshed
		 */
		BindUtils.postNotifyChange(null, null, ps, "editingStatus");
	}

	@SuppressWarnings("unchecked")
	@NotifyChange("projectList")
	@Command("deleteProject")
	public void deleteStudy(@BindingParam("projectId") final Integer projectId){

		if(studyMan.getStudyByProgramId(projectId).isEmpty()){
			Messagebox.show("Are you sure?",
					"Delete", Messagebox.OK | Messagebox.CANCEL,
					Messagebox.QUESTION, new EventListener() {
				public void onEvent(Event e) {
					if ("onOK".equals(e.getName())) {
						projectMan.deleteProjectById(projectId);
						makeProjectStatus(projectMan.getProjectByUserId());
						BindUtils.postNotifyChange(null, null,
								EditProject.this, "rowList");
						Messagebox.show("Changes saved.");
					} else if ("onCancel".equals(e.getName())) {
					}
				}
			});
		}
		else  Messagebox.show("Cannot delete a project with studies.", "Error", Messagebox.OK, Messagebox.ERROR); 
		//		populateEditStudyList();
	}

	@NotifyChange("*")
	@Command("addProject")
	public void addProject(@ContextParam(ContextType.COMPONENT) Component component) {

		Window win = (Window) component.getFellow("editProjectWindow");

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("oldVar", null);
		params.put("programID",1); //yah better change this! Make Dynamic

		Window popup = (Window) Executions.getCurrent().createComponents(
				AddNewProject.ZUL_PATH, win, params);

		popup.doModal();
		makeProjectStatus(projectMan.getProjectByUserId());

	}

	@NotifyChange("projectList")
	@GlobalCommand("refreshProjectList")
	public void refreshProjectList() {
		makeProjectStatus(projectMan.getProjectByUserId());
	}

	public class ProjectStatus {
		private Project p;
		private Program prog;
		private boolean editingStatus;

		public ProjectStatus(Project p, Program program, boolean editingStatus) {
			this.p = p;
			this.editingStatus = editingStatus;
			this.prog = program;
		}

		public Project getProject() {
			return p;
		}

		public boolean getEditingStatus() {
			return editingStatus;
		}

		public void setEditingStatus(boolean editingStatus) {
			this.editingStatus = editingStatus;
		}

		public Program getProg() {
			return prog;
		}

		public void setProg(Program prog) {
			this.prog = prog;
		}
	}
}