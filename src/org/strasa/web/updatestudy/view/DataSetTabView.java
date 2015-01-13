/*
 * Data Management and Analysis (DMAS) - International Rice Research Institute 2013-2015
 * 
 *   DMAS is an opensource Data management and statistical analysis mainly for STRASA Project: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *  DMAS is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with DMAS.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * 
 * 
 */
package org.strasa.web.updatestudy.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.strasa.middleware.manager.StudyDataSetManagerImpl;
import org.strasa.middleware.manager.StudyManagerImpl;
import org.strasa.web.common.api.ProcessTabViewModel;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;

public class DataSetTabView {

	@Wire("#tab1")
	Tab tab1;
	@Wire("#tab2")
	Tab tab2;
	@Wire("#tab3")
	Tab tab3;
	@Wire("#tab4")
	Tab tab4;
	@Wire("#tab5")
	Tab tab5;

	@Wire("#tabpanel1")
	Tabpanel tabpanel1;
	@Wire("#tabpanel2")
	Tabpanel tabpanel2;
	@Wire("#tabpanel3")
	Tabpanel tabpanel3;
	@Wire("#tabpanel4")
	Tabpanel tabpanel4;
	@Wire("#tabpanel5")
	Tabpanel tabpanel5;

	@Wire("#btnSaveDataset")
	Button btnSaveDataset;
	@Wire("#btnDeleteDataset")
	Button btnDeleteDataset;

	ArrayList<Tabpanel> arrTabPanels = new ArrayList<Tabpanel>();

	private int selectedIndex = 0;
	private boolean[] tabDisabled = { false, true, true, true, true };

	private boolean isRaw;
	private ProcessTabViewModel uploadModel;

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public boolean[] getTabDisabled() {
		return tabDisabled;
	}

	public void setTabDisabled(boolean[] tabDisabled) {
		this.tabDisabled = tabDisabled;
	}

	public ProcessTabViewModel getUploadModel() {
		return uploadModel;
	}

	public void setUploadModel(ProcessTabViewModel uploadModel) {
		this.uploadModel = uploadModel;
	}

	@Init
	public void init(@ExecutionArgParam("uploadModel") ProcessTabViewModel procModel) {
		// editing mode

		this.uploadModel = procModel;
		System.out.println(uploadModel.toString());
		System.out.println();
		System.out.println();
		System.out.println();

		if (procModel.isUpdateMode) {
			tabDisabled[0] = false;
			tabDisabled[1] = false;
			tabDisabled[2] = false;
			tabDisabled[3] = false;
			tabDisabled[4] = false;

		}

		uploadModel.processTabID = Math.random();

	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);

		// wire event listener
		// Selectors.wireEventListeners(view, this);

		arrTabPanels.add(tabpanel1);

		arrTabPanels.add(tabpanel2);

		arrTabPanels.add(tabpanel3);

		arrTabPanels.add(tabpanel4);

		arrTabPanels.add(tabpanel5);
		// Events.sendEvent("onSelect",tab1,tab1);

		if (!this.uploadModel.isUpdateMode) {
			btnDeleteDataset.setVisible(false);
			btnSaveDataset.setVisible(false);
		}

		tab5.setVisible(false);
		if (this.uploadModel.isMergeMode) {
			view.getFellow("divDataset").setVisible(false);
		}
		tabpanel1.getFirstChild().detach();
		Include inc = new Include();
		inc.setSrc("/user/updatestudy/modifydata.zul");
		inc.setParent(tabpanel1);
		inc.setDynamicProperty("uploadModel", uploadModel);
	}

	@SuppressWarnings("unchecked")
	@Command
	public void deleteDataset() {

		Messagebox.show("Are you sure you want to delete this dataset? WARNING! This cannot be undone.", "Delete dataset?", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {
			public void onEvent(Event e) {
				if ("onOK".equals(e.getName())) {
					new StudyManagerImpl().deleteStudyById(DataSetTabView.this.uploadModel.studyID, DataSetTabView.this.uploadModel.getDataset().getId());

					DataSetTabView.this.uploadModel.mainTab.detach();
					DataSetTabView.this.uploadModel.mainTabPanel.detach();

					Map<String, Object> args = new HashMap<String, Object>();

					args.put("isRaw", DataSetTabView.this.uploadModel.isRaw);

					BindUtils.postGlobalCommand(null, null, "checkMerge", args);
				} else if ("onCancel".equals(e.getName())) {

				}

				/*
				 * Event Name Mapping list Messagebox.YES = "onYes";
				 * Messagebox.NO = "onNo"; Messagebox.RETRY = "onRetry";
				 * Messagebox.ABORT = "onAbort"; Messagebox.IGNORE = "onIgnore";
				 * Messagebox.CANCEL = "onCancel"; Messagebox.OK = "onOK";
				 */
			}
		});

	}

	@Command
	public void saveDataset() {

		Messagebox.show("Information has been saved!", "Message", Messagebox.OK, null);
		new StudyDataSetManagerImpl().updateDataSet(this.uploadModel.dataset);
		this.uploadModel.mainTab.setLabel(this.uploadModel.dataset.getTitle());
	}

	@NotifyChange("*")
	@GlobalCommand("disableTabs")
	public void disableTabs() {
		tabDisabled[0] = false;
		tabDisabled[1] = true;
		tabDisabled[2] = true;
		tabDisabled[3] = true;
		tabDisabled[4] = true;
		System.out.println("Disabled Tabs Called");
	}

	@Command("showzulfile")
	public void showzulfile(@BindingParam("zulFileName") String zulFileName, @BindingParam("target") Tabpanel panel) {
		System.out.println(zulFileName);
		if (panel != null && panel.getChildren().isEmpty()) {
			Map arg = new HashMap();
			arg.put("uploadModel", uploadModel);

			Executions.createComponents(zulFileName, panel, arg);

		}
	}

	@NotifyChange("*")
	@GlobalCommand("nextTab")
	public void nextTab(@BindingParam("model") ProcessTabViewModel uploadData) {
		if (uploadModel == null)
			uploadModel = uploadData;
		if (uploadData.processTabID != uploadModel.processTabID) {
			System.out.println("uploadModel triggered " + uploadData.processTabID + " != " + uploadModel.processTabID);

			return;
		}

		Clients.showBusy("Processing... Please wait.");
		boolean valid = uploadData.validateTab();
		Clients.clearBusy();

		if (!valid) {
			return;
		}
		if (selectedIndex == 0) {
			isRaw = uploadData.isRaw;

			uploadModel = uploadData;
			System.out.println("IsRaw: " + uploadData.isDataReUploaded + " ");

		}
		if (uploadData.uploadToFolder) {
			selectedIndex = 3;
		}

		if (selectedIndex + 1 < arrTabPanels.size()) {

			arrTabPanels.get(selectedIndex + 1).getChildren().clear();
		}

		// System.out.println("Sample: " + uploadData.getTxtProject());

		// selectedIndex++;

		tabDisabled[selectedIndex + 1] = false;
		if (uploadData.uploadToFolder) {
			selectedIndex = 3;
		}
		Tab[] tabs = { tab1, tab2, tab3, tab4, tab5 };

		if (selectedIndex + 1 == 4 && !this.uploadModel.isUpdateMode) {
			Messagebox.show("Successfully added a new dataset.", "Information", Messagebox.OK, Messagebox.INFORMATION);
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("dataset", this.uploadModel.dataset);
			args.put("isUpdateMode", true);
			args.put("isRaw", this.uploadModel.isRaw);
			DataSetTabView.this.uploadModel.mainTab.close();
			;
			// DataSetTabView.this.uploadModel.mainTabPanel.detach();
			BindUtils.postGlobalCommand(null, null, "removeDataSet", args);
			return;
		}

		if (!this.uploadModel.isUpdateMode) {
			Events.sendEvent("onSelect", tabs[selectedIndex + 1], tabs[selectedIndex + 1]);
		} else {
			Messagebox.show("Changes has been saved", "Information", Messagebox.OK, Messagebox.INFORMATION);

			return;
		}

		System.out.println("INDEX: " + selectedIndex);
		if (uploadData.uploadToFolder) {
			tabDisabled[4] = false;
			System.out.println("FINISH LOADED: ");
			Events.sendEvent("onSelect", tab5, tab5);

			Events.sendEvent("onSelect", tab5, tab5);

			Events.sendEvent("onSelect", tab5, tab5);
			tab5.setDisabled(false);
			tab5.setSelected(true);
			Events.sendEvent(new Event("onSelect", tab5, null));
			return;
		}
		selectedIndex++;

	}

	@NotifyChange("*")
	public void changeTab() {
		System.out.println("Called to change tab");
		selectedIndex++;
	}
}
