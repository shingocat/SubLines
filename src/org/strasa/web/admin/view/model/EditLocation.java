package org.strasa.web.admin.view.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.strasa.middleware.manager.LocationManagerImpl;
import org.strasa.middleware.manager.StudyLocationManagerImpl;
import org.strasa.middleware.manager.StudyManagerImpl;
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


public class EditLocation {
	LocationManagerImpl man;
	StudyLocationManagerImpl studyLocMan;
	List<RowStatus> locationList = new ArrayList<RowStatus>(); 

	public List<RowStatus> getList() {
		return locationList;
	}
	public void setList(List<RowStatus> List) {
		this.locationList = List;
	}
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view){
		man = new LocationManagerImpl();
		studyLocMan = new StudyLocationManagerImpl();
		 makeRowStatus(man.getAllLocations());
	}

	private void makeRowStatus(List<Location> ByUserId) {
		// TODO Auto-generated method stub
		
		locationList.clear();
		for (Location p: ByUserId){
			RowStatus ps = new RowStatus(p,false);
			locationList.add(ps);
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
		man.updateLocation(ps.getLocation());
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
	@NotifyChange("list")
	@Command("delete")
	public void delete(@BindingParam("id") final Integer Id){
		if(studyLocMan.getStudyLocationsByLocId(Id).isEmpty()){
			Messagebox.show("Are you sure?",
					"Delete", Messagebox.OK | Messagebox.CANCEL,
					Messagebox.QUESTION, new EventListener() {
				public void onEvent(Event e) {
					if ("onOK".equals(e.getName())) {
						man.deleteById(Id);
						makeRowStatus(man.getAllLocations());
						BindUtils.postNotifyChange(null, null,
								EditLocation.this, "rowList");
						Messagebox.show("Changes saved.");
					} else if ("onCancel".equals(e.getName())) {
					}
				}
			});
		} else  Messagebox.show("Cannot delete a location with studies.", "Error", Messagebox.OK, Messagebox.ERROR); 
	}

//	@NotifyChange("list")
	@Command("add")
	public void add(@ContextParam(ContextType.COMPONENT) Component component) {
		Window win = (Window) component.getFellow("editLocationWindow");
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("oldVar", null);

		Window popup = (Window) Executions.createComponents(
				AddLocation.ZUL_PATH, win, params);

		popup.doModal();
//		makeRowStatus(man.getAllLocations());
	}

	@NotifyChange("list")
	@Command("newLocationModel")
	public void refreshList() {
		makeRowStatus(man.getAllLocations());
	}
	
	
	public class RowStatus {
	    private  Location loc;
	    private boolean editingStatus;
	     
	    public RowStatus(Location loc, boolean editingStatus) {
	        this.loc = loc;
	        this.editingStatus = editingStatus;
	    }
	     
	    public Location getLocation() {
	        return loc;
	    }
	     
	    public boolean getEditingStatus() {
	        return editingStatus;
	    }
	     
	    public void setEditingStatus(boolean editingStatus) {
	        this.editingStatus = editingStatus;
	    }
	}
}