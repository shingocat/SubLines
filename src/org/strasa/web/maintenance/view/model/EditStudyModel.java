package org.strasa.web.maintenance.view.model;

import org.strasa.middleware.model.Program;
import org.strasa.middleware.model.Project;
import org.strasa.middleware.model.Study;

public class EditStudyModel {

	int index;
	boolean shared;
	
	public boolean isPrivacy() {
		return shared;
	}

	public void setPrivacy(boolean privacy) {
		this.shared = privacy;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	Study study;
	Project project;
	Program program;
	
	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}
	
	public EditStudyModel() {
		this.study = new Study();
		this.project = new Project();
		this.program = new Program();
	}
	
}
