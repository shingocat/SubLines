package org.strasa.web.browsestudy.view.model;


public class StudyDataColumnModel{
	
	int order;
	int count;
	int studyid;
	int dataset;
	String columnheader;
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getStudyid() {
		return studyid;
	}
	public void setStudyid(int studyid) {
		this.studyid = studyid;
	}
	public String getColumnheader() {
		return columnheader;
	}
	public void setColumnheader(String columnheader) {
		this.columnheader = columnheader;
	}
	
	
	public int getDataset() {
		return dataset;
	}
	public void setDataset(int dataset) {
		this.dataset = dataset;
	}
	@Override
	public String toString() {
		return "StudyDataColumnModel [order=" + order + ", count=" + count
				+ ", studyid=" + studyid + ", columnheader=" + columnheader
				+ "]";
	}
	
	
	

	
}
