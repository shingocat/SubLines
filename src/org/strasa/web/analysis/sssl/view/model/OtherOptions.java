package org.strasa.web.analysis.sssl.view.model;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

public class OtherOptions {
	
	@Wire
	private Checkbox descriptiveStatCB;
	@Wire
	private Checkbox varComponentCB;
	@Wire
	private Checkbox compareWithControlCB;
	@Wire
	private Combobox controlCBB;
	@Wire
	private Checkbox specifiedContrastCB;
	@Wire
	private Div specifiedContrastDiv;
	@Wire
	private Label fileNameOfContrastLb;
	@Wire
	private Button contrastFromFileBtn;
	@Wire
	private Button contrastByManuallyBtn;
	@Wire
	private Button contrastResetBtn;
	@Wire
	private Div contrastByManuallyDiv;
	@Wire
	private Textbox contrastVariableTB;
	@Wire
	private Doublespinner numberOfContrastDS;
	@Wire
	private Div contrastGridDiv;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.COMPONENT) Component component, @ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(component, this, false);
	}
	
	@Command
	@NotifyChange("*")
	public void uploadContrastFromFile()
	{
		System.out.println("upload contrast on other options vm");
		fileNameOfContrastLb.setVisible(false);
		contrastFromFileBtn.setVisible(false);
		contrastByManuallyBtn.setVisible(false);
		contrastResetBtn.setVisible(true);
		contrastByManuallyDiv.setVisible(false);
	}
	
	@Command
	@NotifyChange("*")
	public void manuallyInputContrast()
	{
		System.out.println("input contrast on other options vm");
		fileNameOfContrastLb.setVisible(false);
		contrastFromFileBtn.setVisible(false);
		contrastByManuallyBtn.setVisible(false);
		contrastResetBtn.setVisible(true);
		contrastByManuallyDiv.setVisible(true);
	}
	
	@Command
	@NotifyChange("*")
	public void resetContrast()
	{
		System.out.println("reset contrast on other options vm");
		fileNameOfContrastLb.setVisible(false);
		contrastFromFileBtn.setVisible(true);
		contrastByManuallyBtn.setVisible(true);
		contrastByManuallyDiv.setVisible(false);
		contrastResetBtn.setVisible(false);
	}
	
	@Command
	@NotifyChange("*")
	public void isSpecifiedContrastCBChecked() {
		if (specifiedContrastCB.isChecked()) {
			specifiedContrastDiv.setVisible(true);
		} else
		{
			specifiedContrastDiv.setVisible(false);
		}
	}

	@Command
	@NotifyChange("*")
	public void isCompareWithControlCBChecked() {
		if (compareWithControlCB.isChecked()) {
			controlCBB.setVisible(true);
			controlCBB.setDisabled(false);
		} else {
			controlCBB.setDisabled(true);
		}
	}

}
