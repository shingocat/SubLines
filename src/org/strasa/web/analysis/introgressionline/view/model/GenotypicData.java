package org.strasa.web.analysis.introgressionline.view.model;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

public class GenotypicData {
	
	//component
	Div specDiv;
	Combobox genoCBB;
	Textbox dpCodeTB;
	Textbox rpCodeTB;
	Textbox htCodeTB;
	Textbox naCodeTB;
	Intbox bcnIB;
	Intbox fnIB;
	Div dataDiv;
	//values
	List<String> lstColumns = new ArrayList<String>();
	List<String> columnList = new ArrayList<String>();
	List<String[]> dataList = new ArrayList<String[]>();
	String genoCol;
	String dpCode;
	String rpCode;
	String htCode;
	String naCode;
	Integer bcn;
	Integer fn;

	
	@Init
	public void init(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view)
	{
		setGenoCol(null);
		setDpCode("B");
		setRpCode("A");
		setHtCode("H");
		setNaCode("NA");
		setBcn(3);
		setFn(2);
	}
	
	@AfterCompose
	public void afterComposer(@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(component, this, false);
		this.specDiv = (Div) component.getFellow("specDiv");
		this.genoCBB = (Combobox) component.getFellow("genoCBB");
		this.dpCodeTB = (Textbox) component.getFellow("dpCodeTB");
		this.rpCodeTB = (Textbox) component.getFellow("rpCodeTB");
		this.htCodeTB = (Textbox) component.getFellow("htCodeTB");
		this.naCodeTB = (Textbox) component.getFellow("naCodeTB");;
		this.bcnIB = (Intbox) component.getFellow("bcnIB");
		this.fnIB = (Intbox) component.getFellow("fnIB");
		this.dataDiv = (Div) component.getFellow("dataDiv");
		refreshDataDiv();
	}
	
	@NotifyChange("*")
	@Command("updateGeno")
	public void updateGeno()
	{
		
	}
	
	@NotifyChange("*")
	@GlobalCommand("getGenoFile")
	public void getGenoFile()
	{
		
	}
	
	public void refreshDataDiv()
	{
		if(!dataDiv.getChildren().isEmpty())
			dataDiv.getChildren().get(0).detach();
		Include inc = new Include();
		inc.setSrc("/user/analysis/csvgrid.zul");
		inc.setParent(dataDiv);
	}

	public List<String> getLstColumns() {
		return lstColumns;
	}

	public void setLstColumns(List<String> lstColumns) {
		this.lstColumns = lstColumns;
	}

	public String getGenoCol() {
		return genoCol;
	}

	public void setGenoCol(String genoCol) {
		this.genoCol = genoCol;
	}

	public String getDpCode() {
		return dpCode;
	}

	public void setDpCode(String dpCode) {
		this.dpCode = dpCode;
	}

	public String getRpCode() {
		return rpCode;
	}

	public void setRpCode(String rpCode) {
		this.rpCode = rpCode;
	}

	public String getHtCode() {
		return htCode;
	}

	public void setHtCode(String htCode) {
		this.htCode = htCode;
	}

	public String getNaCode() {
		return naCode;
	}

	public void setNaCode(String naCode) {
		this.naCode = naCode;
	}

	public Integer getBcn() {
		return bcn;
	}

	public void setBcn(Integer bcn) {
		this.bcn = bcn;
	}

	public Integer getFn() {
		return fn;
	}

	public void setFn(Integer fn) {
		this.fn = fn;
	}

	public List<String> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}

	public List<String[]> getDataList() {
		return dataList;
	}

	public void setDataList(List<String[]> dataList) {
		this.dataList = dataList;
	}
	
}
