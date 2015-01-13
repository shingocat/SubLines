package org.strasa.web.admin.view.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.strasa.middleware.manager.EcotypeManagerImpl;
import org.strasa.middleware.manager.GermplasmCharacteristicMananagerImpl;
import org.strasa.middleware.manager.KeyCharacteristicManagerImpl;
import org.strasa.middleware.manager.LocationManagerImpl;
import org.strasa.middleware.manager.StudyGermplasmCharacteristicsManagerImpl;
import org.strasa.middleware.manager.StudyGermplasmManagerImpl;
import org.strasa.middleware.manager.StudyLocationManagerImpl;
import org.strasa.middleware.manager.StudyManagerImpl;
import org.strasa.middleware.manager.StudyRawDataManagerImpl;
import org.strasa.middleware.manager.StudySiteManagerImpl;
import org.strasa.middleware.model.Ecotype;
import org.strasa.middleware.model.KeyBiotic;
import org.strasa.middleware.model.Location;
import org.strasa.web.uploadstudy.view.model.AddLocation;
import org.strasa.web.uploadstudy.view.model.UploadData;
import org.strasa.web.utilities.FileUtilities;
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


public class EditBioticKey {
	KeyCharacteristicManagerImpl man;
	GermplasmCharacteristicMananagerImpl germplasmCharMan;
	List<RowStatus> rowList = new ArrayList<RowStatus>(); 

	public List<RowStatus> getRowList() {
		return rowList;
	}

	public void setRowList(List<RowStatus> rowList) {
		this.rowList = rowList;
	}

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view){
		man = new KeyCharacteristicManagerImpl();
		germplasmCharMan = new GermplasmCharacteristicMananagerImpl();
		makeRowStatus(man.getAllBiotic());
	}

	private void makeRowStatus(List<KeyBiotic> list) {
		// TODO Auto-generated method stub

		rowList.clear();
		for (KeyBiotic p: list){
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
		man.updateBiotic(ps.getValue());
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
	public void delete(@BindingParam("id") final Integer Id, @BindingParam("keyName") String keyName){
		if(!germplasmCharMan.isCharacteristicValueExisting("Biotic", keyName)){
			Messagebox.show("Are you sure you want to delete "+keyName+"?",
					"Delete", Messagebox.OK | Messagebox.CANCEL,
					Messagebox.QUESTION, new EventListener() {
				public void onEvent(Event e) {
					if ("onOK".equals(e.getName())) {
						man.deleteBioticById(Id);
						makeRowStatus(man.getAllBiotic());
						BindUtils.postNotifyChange(null, null,
								EditBioticKey.this, "rowList");
						Messagebox.show("Changes saved.");
					} else if ("onCancel".equals(e.getName())) {
					}
				}
			});

		} else  Messagebox.show("Cannot delete a biotic key that is in use.", "Error", Messagebox.OK, Messagebox.ERROR); 
	}

	//	@NotifyChange("list")
	@Command("add")
	public void add(@ContextParam(ContextType.COMPONENT) Component component) {
		Window win = (Window) component.getFellow("editBioticKeyWindow");
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("oldVar", null);

		Window popup = (Window) Executions.createComponents(
				AddBioticKey.ZUL_PATH, win, params);

		popup.doModal();
		//		makeRowStatus(man.getAllLocations());
	}

	@NotifyChange("rowList")
	@Command("refreshList")
	public void refreshList() {
		makeRowStatus(man.getAllBiotic());
	}


	public class RowStatus {
		private  KeyBiotic value;
		private boolean editingStatus;

		public RowStatus(KeyBiotic p, boolean editingStatus) {
			this.setValue(p);
			this.editingStatus = editingStatus;
		}


		public boolean getEditingStatus() {
			return editingStatus;
		}

		public void setEditingStatus(boolean editingStatus) {
			this.editingStatus = editingStatus;
		}


		public KeyBiotic getValue() {
			return value;
		}


		public void setValue(KeyBiotic p) {
			this.value = p;
		}
	}
}