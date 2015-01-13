package org.strasa.web.crossstudyquery.view.model;

import java.util.ArrayList;

public class AcrossStudyData {
	
	int studyId;
	String studyname;
	String gname;
	
	ArrayList<String>otherdata;
	public String getStudyname() {
		return studyname;
	}
	public void setStudyname(String studyname) {
		this.studyname = studyname;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public ArrayList<String> getOtherdata() {
		return otherdata;
	}
	public void setOtherdata(ArrayList<String> otherdata) {
		this.otherdata = otherdata;
	}
	public int getStudyId() {
		return studyId;
	}
	public void setStudyId(int studyId) {
		this.studyId = studyId;
	}

	
	
	

}
