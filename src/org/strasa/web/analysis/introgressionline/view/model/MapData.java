package org.strasa.web.analysis.introgressionline.view.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.strasa.web.utilities.AnalysisUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

public class MapData {
	
	//component
	Div specDiv;
	Listbox variableLB;
	Image addMarImg;
	Textbox marTB;
	Image addChrImg;
	Textbox chrTB;
	Image addPosImg;
	Textbox posTB;
	Radiogroup unitsRG;
	Radio cmRadio;
	Radio bpRadio;
	Div dataDiv;
	
	//values
	String marker;
	String chr;
	String pos;
	String unit;
	ListModelList<String> variables;
	List<String> columnList = new ArrayList<String>();
	List<String[]> dataList = new ArrayList<String[]>();
	
	@NotifyChange("*")
	@AfterCompose
	public void afterCompose(
			@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(component, this, false);
		specDiv = (Div) component.getFellow("specDiv");
		dataDiv = (Div) component.getFellow("dataDiv");
		refreshDataDiv();
	}
	
	@NotifyChange("*")
	@Command("chooseVariable")
	public boolean chooseVariable(
			@BindingParam("varTextBox") Textbox varTextBox,
			@BindingParam("img") Image imgButton)
	{
		Set<String> set = variables.getSelection();
		if (varTextBox.getValue().isEmpty() && !set.isEmpty()) {
			for (String selectedItem : set) {
				varTextBox.setValue(selectedItem);
				variables.remove(selectedItem);
			}

			if (varTextBox.getId().equals("marTB")) {
			}

			if (varTextBox.getId().equals("chrTB")) {
			}

			imgButton.setSrc("/images/leftarrow_g.png");
			return true;
		} else if (!varTextBox.getValue().isEmpty()) {
			variables.add(varTextBox.getValue());
			varTextBox.setValue(null);
			if(varTextBox.getId().equals("marTB"))
			{
			}
			if(varTextBox.getId().equals("chrTB"))
			{
			}
		}
		imgButton.setSrc("/images/rightarrow_g.png");
		return false;
	}
	public void refreshDataDiv()
	{
		if(!dataDiv.getChildren().isEmpty())
			dataDiv.getChildren().get(0).detach();
		Include inc = new Include();
		inc.setSrc("/user/analysis/csvgrid.zul");
		inc.setParent(dataDiv);
	}
	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getChr() {
		return chr;
	}

	public void setChr(String chr) {
		this.chr = chr;
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
