package org.strasa.web.admin.view.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.strasa.middleware.manager.EcotypeManagerImpl;
import org.strasa.middleware.manager.LocationManagerImpl;
import org.strasa.middleware.manager.StudyLocationManagerImpl;
import org.strasa.middleware.manager.StudyManagerImpl;
import org.strasa.middleware.manager.StudySiteManagerImpl;
import org.strasa.middleware.model.Ecotype;
import org.strasa.middleware.model.Location;
import org.strasa.web.uploadstudy.view.model.AddLocation;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;


public class EditEcotype {
	EcotypeManagerImpl man;
	StudySiteManagerImpl studySiteMan;
	List<RowStatus> rowList = new ArrayList<RowStatus>(); 

	public List<RowStatus> getRowList() {
		return rowList;
	}

	public void setRowList(List<RowStatus> rowList) {
		this.rowList = rowList;
	}

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view){
		man = new EcotypeManagerImpl();
		studySiteMan = new StudySiteManagerImpl();
		 makeRowStatus(man.getAllEcotypes());
	}

	private void makeRowStatus(List<Ecotype> list) {
		// TODO Auto-generated method stub
		
		rowList.clear();
		for (Ecotype p: list){
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
		man.updateEcotype(ps.getValue());
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
	
	@SuppressWarnings("unchecked")
	@NotifyChange("rowList")
	@Command("delete")
	public void delete(@BindingParam("id") final Integer Id){
		if(studySiteMan.getSiteByEcotypeId(Id).isEmpty()){
			Messagebox.show("Are you sure?",
					"Delete", Messagebox.OK | Messagebox.CANCEL,
					Messagebox.QUESTION, new EventListener() {
				public void onEvent(Event e) {
					if ("onOK".equals(e.getName())) {
						man.deleteById(Id);
						makeRowStatus(man.getAllEcotypes());
						BindUtils.postNotifyChange(null, null,
								EditEcotype.this, "rowList");
						Messagebox.show("Changes saved.");
					} else if ("onCancel".equals(e.getName())) {
					}
				}
			});
			
		} else  Messagebox.show("Cannot delete an ecotype that is in use.", "Error", Messagebox.OK, Messagebox.ERROR); 
	}

//	@NotifyChange("list")
	@Command("add")
	public void add(@ContextParam(ContextType.COMPONENT) Component component) {
		Window win = (Window) component.getFellow("editEcotypeWindow");
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("oldVar", null);

		Window popup = (Window) Executions.createComponents(
				AddEcotype.ZUL_PATH, win, params);

		popup.doModal();
//		makeRowStatus(man.getAllLocations());
	}

	@NotifyChange("rowList")
	@Command("refreshList")
	public void refreshList() {
		makeRowStatus(man.getAllEcotypes());
	}
	
	
	public class RowStatus {
	    private  Ecotype value;
	    private boolean editingStatus;
	     
	    public RowStatus(Ecotype p, boolean editingStatus) {
	        this.setValue(p);
	        this.editingStatus = editingStatus;
	    }
	     
	     
	    public boolean getEditingStatus() {
	        return editingStatus;
	    }
	     
	    public void setEditingStatus(boolean editingStatus) {
	        this.editingStatus = editingStatus;
	    }


		public Ecotype getValue() {
			return value;
		}


		public void setValue(Ecotype value) {
			this.value = value;
		}
	}
}