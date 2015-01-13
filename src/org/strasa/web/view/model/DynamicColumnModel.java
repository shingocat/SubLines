package org.strasa.web.view.model;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Init;

public class DynamicColumnModel {

	private List<String> dataList = new ArrayList<String>();
	private List<String> columnList = new ArrayList<String>();
	
	@Init
	public void init(){
		dataList.add("Data 1");
		dataList.add("Data 2");
		dataList.add("Data 3");
		dataList.add("Data 4");
		dataList.add("Data 5");
		dataList.add("Data 6");
		dataList.add("Data 7");
		
		columnList.add("Dynamic Col A");
		columnList.add("Dynamic Col B");
		columnList.add("Dynamic Col C");
		columnList.add("Dynamic Col D");
	}
	
	public List<String> getColumnList() {
		return columnList;
	}
	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}
	
	public List<String> getDataList() {
		return dataList;
	}
	public void setDataList(List<String> dataList) {
		this.dataList = dataList;
	}
}