package org.strasa.web.admin.view.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.strasa.middleware.manager.EcotypeManagerImpl;
import org.strasa.middleware.manager.ProgramManagerImpl;
import org.strasa.middleware.manager.ProjectManagerImpl;
import org.strasa.middleware.manager.StudyManagerImpl;
import org.strasa.middleware.manager.StudySiteManagerImpl;
import org.strasa.middleware.manager.StudyTypeManagerImpl;
import org.strasa.middleware.model.Ecotype;
import org.strasa.middleware.model.Program;
import org.strasa.middleware.model.Project;
import org.strasa.middleware.model.Study;
import org.strasa.middleware.model.StudyType;
import org.strasa.web.admin.view.model.EditEcotype.RowStatus;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;


public class AdminSettings {


	StudyTypeManagerImpl man;
	StudyManagerImpl studyMan;
	List<RowStatus> rowList = new ArrayList<RowStatus>(); 
	
	@Command("showzulfile")
	public void showzulfile(@BindingParam("zulFileName") String zulFileName,
			@BindingParam("target") Tabpanel panel) {
		if (panel != null && panel.getChildren().isEmpty()) {
			 Map arg = new HashMap();
			Executions.createComponents(zulFileName, panel, arg);
			
		}
	}

	public List<RowStatus> getRowList() {
		return rowList;
	}

	public void setRowList(List<RowStatus> rowList) {
		this.rowList = rowList;
	}

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view){
		man = new StudyTypeManagerImpl();
		studyMan = new StudyManagerImpl();
		 makeRowStatus(man.getAllStudyType());
	}

	private void makeRowStatus(List<StudyType> list) {
		// TODO Auto-generated method stub
		
		rowList.clear();
		for (StudyType p: list){
			RowStatus ps = new RowStatus(p,false);
			rowList.add(ps);
		}
	}
	
	@Command
	public void changeEditableStatus(@BindingParam("RowStatus") RowStatus ps) {
		ps.setEditingStatus(!ps.getEditingStatus());
		refreshRowTemplate(ps);
	}

	@Command
	public void confirm(@BindingParam("RowStatus") RowStatus ps) {
		changeEditableStatus(ps);
		refreshRowTemplate(ps);
		man.update(ps.getValue());
		Messagebox.show("Changes saved.");
	}

	public void refreshRowTemplate(RowStatus ps) {
		/*
		 * This code is special and notifies ZK that the bean's value
		 * has changed as it is used in the template mechanism.
		 * This stops the entire Grid's data from being refreshed
		 */
		BindUtils.postNotifyChange(null, null, ps, "editingStatus");
	}
	
	@NotifyChange("rowList")
	@Command("delete")
	public void delete(@BindingParam("id") Integer Id){
		if(studyMan.getByStudyTypeId(Id).isEmpty()){
			man.deleteById(Id);
			makeRowStatus(man.getAllStudyType());
			Messagebox.show("Changes saved.");
		} else  Messagebox.show("Cannot delete a study type that is in use.", "Error", Messagebox.OK, Messagebox.ERROR); 
	}

//	@NotifyChange("list")
	@Command("add")
	public void add(@ContextParam(ContextType.COMPONENT) Component component) {
		Div win = (Div) component.getFellow("adminSettingsWindow");
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("oldVar", null);

		Window popup = (Window) Executions.createComponents(
				AddStudyType.ZUL_PATH, win, params);

		popup.doModal();
//		makeRowStatus(man.getAllLocations());
	}

	@NotifyChange("rowList")
	@Command("refreshList")
	public void refreshList() {
		makeRowStatus(man.getAllStudyType());
	}
	
	
	public class RowStatus {
	    private  StudyType value;
	    private boolean editingStatus;
	     
	    public RowStatus(StudyType p, boolean editingStatus) {
	        this.setValue(p);
	        this.editingStatus = editingStatus;
	    }
	     
	     
	    public boolean getEditingStatus() {
	        return editingStatus;
	    }
	     
	    public void setEditingStatus(boolean editingStatus) {
	        this.editingStatus = editingStatus;
	    }


		public StudyType getValue() {
			return value;
		}


		public void setValue(StudyType value) {
			this.value = value;
		}
	}
}