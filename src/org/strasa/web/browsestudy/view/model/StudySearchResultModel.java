package org.strasa.web.browsestudy.view.model;


public class StudySearchResultModel{
	
	int id;
	int programid;
	int projectid;
	String studyname;
	int studytypeid;
	String programname;
	String projectname;
	String studytypename;
	String startyear;
	String endyear;
	int locationid;
	String locationname;
	String country;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProgramid() {
		return programid;
	}
	public void setProgramid(int programid) {
		this.programid = programid;
	}
	public int getProjectid() {
		return projectid;
	}
	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}
	public String getStudyname() {
		return studyname;
	}
	public void setStudyname(String studyname) {
		this.studyname = studyname;
	}
	public int getStudytypeid() {
		return studytypeid;
	}
	public void setStudytypeid(int studytypeid) {
		this.studytypeid = studytypeid;
	}
	public String getProgramname() {
		return programname;
	}
	public void setProgramname(String programname) {
		this.programname = programname;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getStudytypename() {
		return studytypename;
	}
	public void setStudytypename(String studytypename) {
		this.studytypename = studytypename;
	}
	public String getStartyear() {
		return startyear;
	}
	public void setStartyear(String startyear) {
		this.startyear = startyear;
	}
	public String getEndyear() {
		return endyear;
	}
	public void setEndyear(String endyear) {
		this.endyear = endyear;
	}
	public int getLocationid() {
		return locationid;
	}
	public void setLocationid(int locationid) {
		this.locationid = locationid;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
	public String getLocationname() {
		return locationname;
	}
	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}
	@Override
	public String toString() {
		return "StudySearchResultModel [id=" + id + ", programid=" + programid
				+ ", projectid=" + projectid + ", studyname=" + studyname
				+ ", studytypeid=" + studytypeid + ", programname="
				+ programname + ", projectname=" + projectname
				+ ", studytypename=" + studytypename + ", startyear="
				+ startyear + ", endyear=" + endyear + ", locationid="
				+ locationid + ", country=" + country + "]";
	}

	

	
	
	
}
