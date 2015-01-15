package org.strasa.web.admin.view.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.strasa.middleware.manager.EcotypeManagerImpl;
import org.strasa.middleware.manager.ProgramManagerImpl;
import org.strasa.middleware.manager.StudyTypeManagerImpl;
import org.strasa.middleware.manager.StudyVariableManagerImpl;
import org.strasa.middleware.model.Ecotype;
import org.strasa.middleware.model.Program;
import org.strasa.middleware.model.StudyType;
import org.strasa.middleware.model.StudyVariable;
import org.strasa.web.common.api.FormValidator;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;

public class AddStudyVariable {
	public static String ZUL_PATH = "/admin/settings/addstudyvariable.zul";
	private StudyVariable model = new StudyVariable();
	private String variableCode;
	private String description;
	private String property;
	private String method;
	private String scale;
	private String applyToFilter;
	private String defaultColumn;
	private String dataType;
	private String category;

	@Wire
	Radiogroup applyToFilterRG;
	@Wire
	Radiogroup defaultColumnRG;
	@Wire
	Radiogroup dataTypeRG;
	@Wire
	Radiogroup categoryRG;

	public StudyVariable getModel() {
		return model;
	}

	public void setModel(StudyVariable model) {
		this.model = model;
	}
	private Component mainView;
	private Binder parBinder;

	@Init
	public void Init(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx,
			@ContextParam(ContextType.VIEW) Component view ,
			@ExecutionArgParam("oldVar")  String oldVar) {
		mainView = view;
		parBinder =  (Binder) view.getParent().getAttribute("binder");

	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.COMPONENT) Component com,
			@ContextParam(ContextType.COMPONENT) Component view
			)
	{
		//wire all the radiogroup

		applyToFilterRG = (Radiogroup) com.getFellow("applyToFilterRG");
		defaultColumnRG = (Radiogroup) com.getFellow("defaultColumnRG");
		dataTypeRG = (Radiogroup) com.getFellow("dataTypeRG");
		categoryRG = (Radiogroup) com.getFellow("categoryRG");
	}

	@Command("add")
	public void add(){
		//assign value to applyToFilter, defaultColumn, dataType, categoryRG
		if(applyToFilterRG != null)
			this.setApplyToFilter(this.applyToFilterRG.getSelectedItem().getValue().toString());
		if(defaultColumnRG != null)
			this.setDefaultColumn(this.defaultColumnRG.getSelectedItem().getValue().toString());
		if(dataTypeRG != null)
			this.setDataType(this.dataTypeRG.getSelectedItem().getValue().toString());
		if(categoryRG != null)
			this.setCategory(this.categoryRG.getSelectedItem().getValue().toString());
		
		// checking the requried fields not allow to empty
		if(this.getVariableCode() == null || this.getVariableCode().length() == 0)
		{
			Messagebox.show("The variable code not allow empty!.", "OK", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if(this.getDescription() == null || this.getDescription().length() == 0)
		{
			Messagebox.show("The description not allow empty!.", "OK", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if(this.getProperty() == null || this.getProperty().length() == 0)
		{
			Messagebox.show("The property not allow empty!.", "OK", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if(this.getMethod() == null || this.getMethod().length() == 0)
		{
			Messagebox.show("The method not allow empty!.", "OK", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if(this.getScale() == null || this.getScale().length() == 0)
		{
			Messagebox.show("The scalecode not allow empty!.", "OK", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		//checking  whether the variable code exist!
		StudyVariableManagerImpl svManImpl = new StudyVariableManagerImpl();
		StudyVariable sv= svManImpl.getStudyVariableByVariableCode(variableCode);
		if(sv != null)
		{
			Messagebox.show("The code of Study Variable already exist! Choose a different Code.", "OK", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		model.setVariablecode(variableCode);
		model.setDescription(description);
		model.setProperty(property);
		model.setMethod(method);
		model.setScale(scale);
		model.setApplytofilter(applyToFilter);
		model.setDefaultcolumn(defaultColumn);
		model.setDatatype(dataType);
		model.setCategory(category);
		
		System.out.println("Model" + model.toString());
		
		if(svManImpl.addStudyVariable(model))
			Messagebox.show("Successfully added to database!", "OK", Messagebox.OK, Messagebox.INFORMATION);
		else
			Messagebox.show("Unsuccessfully added to database!", "OK", Messagebox.OK, Messagebox.INFORMATION);
		Binder bind = parBinder;
		if(bind == null)
			return;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("selected", model);
		bind.postCommand("refreshList", params);
		
		//		StudyTypeManagerImpl man = new StudyTypeManagerImpl();
		//		if(man.getAllStudyType().contains(model.getStudytype())){
		//			Messagebox.show("StudyType already exist! Choose a different name.", "OK", Messagebox.OK, Messagebox.EXCLAMATION);
		//			return;
		//		}
		//		try {
		//			if(model.getStudytype().isEmpty()){
		//
		//				Messagebox.show("Please enter a name", "OK", Messagebox.OK, Messagebox.EXCLAMATION);
		//				return;
		//			}
		//		} catch (Exception e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//		//TODO IMPORTANT!!! Must change this to real UserID
		//		man.addStudyType(model);
		//		
		//		//TODO Validate!!
		//		Messagebox.show("Successfully added to database!", "OK", Messagebox.OK, Messagebox.INFORMATION);
		////		System.out.println("SavePath: "+CsvPath);
		//		
		//		
		//		Binder bind = parBinder;
		//		if (bind == null)
		//			return;
		//		
		//		Map<String, Object> params = new HashMap<String, Object>();
		//		params.put("selected",model);
		//
		//		// this.parBinder.postCommand("change", params);
		//		bind.postCommand("refreshList", params);
		mainView.detach();
	}
	@Command
	public void cancel(){
		mainView.detach();
	}


	public String getVariableCode() {
		return variableCode;
	}

	public void setVariableCode(String variableCode) {
		this.variableCode = variableCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getApplyToFilter() {
		return applyToFilter;
	}

	public void setApplyToFilter(String applyToFilter) {
		this.applyToFilter = applyToFilter;
	}

	public String getDefaultColumn() {
		return defaultColumn;
	}

	public void setDefaultColumn(String defaultColumn) {
		this.defaultColumn = defaultColumn;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Component getMainView() {
		return mainView;
	}

	public void setMainView(Component mainView) {
		this.mainView = mainView;
	}

	public Binder getParBinder() {
		return parBinder;
	}

	public void setParBinder(Binder parBinder) {
		this.parBinder = parBinder;
	}


}
