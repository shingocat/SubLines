package org.strasa.web.releaseinfo.view.model;

import org.strasa.middleware.model.ExtensionData;
import org.strasa.middleware.model.ReleaseInfo;

public class ReleaseInfoListModel extends ReleaseInfo {
	
	String programName;
	String projectName;
	
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
