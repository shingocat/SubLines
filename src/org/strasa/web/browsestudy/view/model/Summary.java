package org.strasa.web.browsestudy.view.model;

import java.util.ArrayList;
import java.util.List;

import org.strasa.middleware.manager.BrowseStudyManagerImpl;
import org.strasa.middleware.manager.CountryManagerImpl;
import org.strasa.middleware.manager.LocationManagerImpl;
import org.strasa.middleware.manager.ProgramManagerImpl;
import org.strasa.middleware.manager.ProjectManagerImpl;
import org.strasa.middleware.manager.StudyManagerImpl;
import org.strasa.middleware.manager.StudyTypeManagerImpl;
import org.strasa.middleware.model.Country;
import org.strasa.middleware.model.Program;
import org.strasa.middleware.model.Project;
import org.strasa.middleware.model.StudyType;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Tab;

public class Summary {
	private ProgramManagerImpl programMan;
	private ProjectManagerImpl projectMan;
	private StudyTypeManagerImpl studyTypeMan;
	private BrowseStudyManagerImpl studyQueryManagerImpl;
	
	private List<StudySummaryModel> summary;
	private List<Program> programList;
	private List<Project> projectList;
	private List<StudyType> studyTypeList;
	
	@Init
	public void init(){
		 programMan = new ProgramManagerImpl();
		 projectMan = new ProjectManagerImpl();

			 studyTypeMan = new StudyTypeManagerImpl();
			 studyQueryManagerImpl= new BrowseStudyManagerImpl();
			
			 summary= studyQueryManagerImpl.getStudySummary();
		 programList = programMan.getAllProgram();
			 projectList = projectMan.getAllProject();
			 studyTypeList = studyTypeMan.getAllStudyType();
	}
	
	
	@NotifyChange("*")
	@Command("updateSearchFilter")
	public void updateSearchFilter(){
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

	public List<StudyType> getStudyTypeList() {
		return studyTypeList;
	}

	public void setStudyTypeList(List<StudyType> studyTypeList) {
		this.studyTypeList = studyTypeList;
	}

	public List<StudySummaryModel> getSummary() {
		return summary;
	}

	public void setSummary(List<StudySummaryModel> summary) {
		this.summary = summary;
	}

}
