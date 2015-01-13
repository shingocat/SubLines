package org.strasa.web.crossstudyquery.view.model;

public class CrossStudyQueryFilterModel {


	String columnHeader;
	String variable;
	String operator;
	String valueString;
	double valueDouble;
	String dataType;
	String orderCriteria;
	String columnAs;
	int userid;
	int studyid;
	
	
	public String getVariable() {
		return variable;
	}
	public void setVariable(String variable) {
		this.variable = variable;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getValueString() {
		return valueString;
	}
	public void setValueString(String valueString) {
		this.valueString = valueString;
	}
	public double getValueDouble() {
		return valueDouble;
	}
	public void setValueDouble(double valueDouble) {
		this.valueDouble = valueDouble;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getOrderCriteria() {
		return orderCriteria;
	}
	public void setOrderCriteria(String orderCriteria) {
		this.orderCriteria = orderCriteria;
	}
	public String getColumnAs() {
		return columnAs;
	}
	public void setColumnAs(String columnAs) {
		this.columnAs = columnAs;
	}
	public String getColumnHeader() {
		return columnHeader;
	}
	public void setColumnHeader(String columnHeader) {
		this.columnHeader = columnHeader;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getStudyid() {
		return studyid;
	}
	public void setStudyid(int studyid) {
		this.studyid = studyid;
	}
	
	
	
	

	

	
	
	

}
