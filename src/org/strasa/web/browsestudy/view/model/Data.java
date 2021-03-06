package org.strasa.web.browsestudy.view.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.strasa.middleware.manager.BrowseStudyManagerImpl;
import org.strasa.middleware.manager.StudyDataColumnManagerImpl;
import org.strasa.middleware.manager.StudyManagerImpl;
import org.strasa.middleware.model.StudyDataColumn;
import org.strasa.web.utilities.FileUtilities;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;

public class Data {

	private String filePath;
	private String studyName;

	private List<String> columnList;
	private List<String[]> dataList;

	private BrowseStudyManagerImpl browseStudyManagerImpl;
	private StudyManagerImpl studyMan;
	private String dataType;
	private Div divDatagrid;
	
	public Data()
	{
		//TODO Auto-generated constructor stub
	}

	public List<String> getColumnList()
	{
		return columnList;
	}
	public List<String[]> getRowData()
	{
		return null;
	}
	
	public List<String[]> getDataList()
	{
		return dataList;
	}
	
	public Integer getDataListSize()
	{
		return dataList.size();
	}
	

	@Init
	public void init(@ExecutionArgParam("dataType") String dataType, @ExecutionArgParam("studyId") Integer studyId, @ExecutionArgParam("dataset") Integer dataset) {
		setDataType(dataType);

		studyMan = new StudyManagerImpl();
		browseStudyManagerImpl = new BrowseStudyManagerImpl();
		columnList = new ArrayList<String>();
		dataList = new ArrayList<String[]>();

		// change this value as parameter

		// System.out.println("StudyId:"+ Integer.toString(studyId));
		// System.out.println("and dataset:" +Integer.toString(dataset));
		List<HashMap<String, String>> toreturn = browseStudyManagerImpl.getStudyData(studyId, dataType, dataset);
//		System.out.println("Size:" + toreturn.size());
		List<StudyDataColumn> columns = new StudyDataColumnManagerImpl().getStudyDataColumnByStudyId(studyId, dataType, dataset); // rd as raw data, dd as derived data
																																
		for (StudyDataColumn d : columns) {
			columnList.add(d.getColumnheader());
//			System.out.print(d.getColumnheader() + "\t");
		}
//		System.out.println("\n ");
		for (HashMap<String, String> rec : toreturn) {
			ArrayList<String> newRow = new ArrayList<String>();
			for (StudyDataColumn d : columns) {
				String value = rec.get(d.getColumnheader());
				newRow.add(value);
//				System.out.print(value + "\t");
			}
//			System.out.println("\n ");
			dataList.add(newRow.toArray(new String[newRow.size()]));
		}

		setStudyName(studyMan.getStudyById(studyId).getName());
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		divDatagrid = (Div) view.getFellow("datagrid");
		includeDataGrid();
	}

	public void includeDataGrid() {
		if (!divDatagrid.getChildren().isEmpty())
			divDatagrid.getFirstChild().detach();
		Include incCSVData = new Include();
		incCSVData.setSrc("/user/browsestudy/datagrid.zul");
		incCSVData.setParent(divDatagrid);
	}

	@Command
	public void exportRowData(@BindingParam("columns") List<String> columns, @BindingParam("rows") List<String[]> rows, @BindingParam("studyName") String studyName, @BindingParam("dataType") String dataType) {
		// List<String[]> grid = new ArrayList<String[]>();
		// grid.addAll(rows);
		// grid.add(0,columns.toArray(new String[columns.size()]));

		if (dataType.endsWith("rd"))
			FileUtilities.exportData(columns, rows, studyName + "_rawData.csv");
		else
			FileUtilities.exportData(columns, rows, studyName + "_derivedData.csv");
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}
}
