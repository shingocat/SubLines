package org.strasa.web.browsestudy.view.model;

import java.util.HashMap;
import java.util.Map;

import org.strasa.middleware.manager.ProgramManagerImpl;
import org.strasa.middleware.manager.ProjectManagerImpl;
import org.strasa.middleware.manager.StudyDataSetManagerImpl;
import org.strasa.middleware.manager.StudyManagerImpl;
import org.strasa.middleware.manager.StudyTypeManagerImpl;
import org.strasa.middleware.model.Program;
import org.strasa.middleware.model.Project;
import org.strasa.middleware.model.Study;
import org.strasa.middleware.model.StudyType;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Tabpanel;


public class StudyInformation {
	StudyManagerImpl studyMan = new StudyManagerImpl();
	ProgramManagerImpl proramMan = new ProgramManagerImpl();
	ProjectManagerImpl projectMan = new ProjectManagerImpl();
	StudyTypeManagerImpl studyTypeMan= new StudyTypeManagerImpl();
	StudyDataSetManagerImpl studyDatasetMan = new StudyDataSetManagerImpl();
	private Study selectedStudy;
	private Program selectedProgram = new Program();
	private Project selectedProject = new Project();
	private StudyType selectedStudyType = new StudyType();
	private boolean isRaw = false;

	private Integer studyId;
	private Integer dataset;
	private String parentSource;

	@Command("showzulfile")
	public void showzulfile(@BindingParam("zulFileName") String zulFileName,
			@BindingParam("target") Tabpanel panel) {
		if (panel != null && panel.getChildren().isEmpty()) {
			 Map arg = new HashMap();
		        arg.put("studyid", studyId);
		        arg.put("dataset", dataset);
		        arg.put("parentSource", parentSource);
			Executions.createComponents(zulFileName, panel, arg);
		}
	}
	
	@Init
	public void init(@ExecutionArgParam("studyId") Integer studyId,@ExecutionArgParam("parentSource") String source){
		setDataset(studyDatasetMan.getDataSetsByStudyId(studyId).size());
		setStudyId(studyId);
		setParentSource(source);
		
		selectedStudy = new Study();
		System.out.println("studyId"+ Integer.toString(studyId));
		selectedStudy = studyMan.getStudyById(studyId);
		try{
			selectedProgram = proramMan.getProgramById(selectedStudy.getProgramid());
		}catch(IndexOutOfBoundsException o){
			selectedProgram = new Program();
		}
		try{
			selectedProject = projectMan.getProjectById(selectedStudy.getProjectid());
		}catch(IndexOutOfBoundsException o){
			selectedProject = new Project();
		}
		try{
			selectedStudyType = studyTypeMan.getStudyTypeById(selectedStudy.getStudytypeid());
		}catch(IndexOutOfBoundsException o){
			selectedStudyType = new StudyType();
		}

	}

	public Study getSelectedStudy() {
		return selectedStudy;
	}

	public Program getSelectedProgram() {
		return selectedProgram;
	}

	public Project getSelectedProject() {
		return selectedProject;
	}

	public StudyType getSelectedStudyType() {
		return selectedStudyType;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public Integer getDataset() {
		return dataset;
	}

	public void setDataset(Integer dataset) {
		this.dataset = dataset;
	}

	public String getParentSource() {
		return parentSource;
	}

	public void setParentSource(String parentSource) {
		this.parentSource = parentSource;
	}


}