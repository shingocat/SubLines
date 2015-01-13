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
package org.strasa.web.uploadstudy.view.model;

import java.util.ArrayList;

import org.strasa.web.common.api.ProcessTabViewModel;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
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
import org.zkoss.zul.Include;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Timer;

import com.mysql.jdbc.StringUtils;

public class Index {

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

	ArrayList<Tabpanel> arrTabPanels = new ArrayList<Tabpanel>();
	private UploadData uploadData;
	private int selectedIndex = 1;
	private boolean[] tabDisabled = { false, true, true, true, true };
	private boolean siteReloaded = false;
	private ProcessTabViewModel uploadModel;

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public UploadData getUploadData() {
		return uploadData;
	}

	public void setUploadData(UploadData uploadData) {
		this.uploadData = uploadData;
	}

	public boolean[] getTabDisabled() {
		return tabDisabled;
	}

	public void setTabDisabled(boolean[] tabDisabled) {
		this.tabDisabled = tabDisabled;
	}

	@Init
	public void init() {
		// editing mode
		uploadModel = new ProcessTabViewModel();

		if (!StringUtils.isNullOrEmpty(Executions.getCurrent().getParameter("studyid"))) {
			uploadModel.setStudyID(Integer.parseInt(Executions.getCurrent().getParameter("studyid")));
			uploadModel.setUpdateMode(true);
			tabDisabled[0] = false;
			tabDisabled[1] = false;
			tabDisabled[2] = false;
			tabDisabled[3] = false;
			tabDisabled[4] = false;
		}
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) final Component view) {

		Selectors.wireComponents(view, this, false);

		// wire event listener
		// Selectors.wireEventListeners(view, this);

		// arrTabPanels.add(tabpanel1);

		arrTabPanels.add(tabpanel2);

		arrTabPanels.add(tabpanel3);

		arrTabPanels.add(tabpanel4);

		arrTabPanels.add(tabpanel5);
		// selectedIndex = 0;

		Timer timer = new Timer(10);
		timer.setRepeats(false);
		timer.setPage(view.getPage());
		timer.addEventListener("onTimer", new EventListener() {
			public void onEvent(Event event) throws Exception {
				Events.sendEvent("onSelect", view.getFellow("tab6"), view.getFellow("tab6"));
				System.out.println("SELECT");
				selectedIndex = 0;

			}
		});

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
		System.out.println((panel != null) + " " + !panel.getChildren().isEmpty() + " " + siteReloaded);
		if (panel != null && panel.getChildren().isEmpty()) {

			Include include = new Include();
			include.setDynamicProperty("uploadModel", uploadModel);
			include.setSrc(zulFileName);
			// include.setMode("defer");
			/*
			 * Map arg = new HashMap(); arg.put("uploadModel", uploadModel);
			 * 
			 * Executions.createComponents(zulFileName, panel, arg);
			 */

			include.setParent(panel);

		}
		if (panel != null && !panel.getChildren().isEmpty() && siteReloaded) {

			BindUtils.postGlobalCommand(null, null, "refreshLocationList", null);

			System.out.println("reloading...");
		}
	}

	@NotifyChange("*")
	@GlobalCommand("nextTab")
	public void nextTab(@BindingParam("model") ProcessTabViewModel uploadData) {

		Clients.showBusy("Processing... Please wait.");
		boolean valid = uploadData.validateTab();
		Clients.clearBusy();
		System.out.println("ReloadPage: " + uploadData.reloadNext);
		siteReloaded = uploadData.reloadNext;
		if (!valid) {
			return;
		}
		if (selectedIndex == 0) {

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

		Events.sendEvent("onSelect", tabs[selectedIndex + 1], tabs[selectedIndex + 1]);
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
