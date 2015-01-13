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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.strasa.middleware.manager.StudyVariableManagerImpl;
import org.strasa.middleware.model.StudyVariable;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zul.Row;

public class DataColumnChanged {
	public static String ZUL_PATH = "/user/uploadstudy/datacolumnchanged.zul";
	private Binder parBinder;
	private String oldVar;
	private Component mainView;
	private ArrayList<StudyVariable> selectedVar = new ArrayList<StudyVariable>();
	private String filter = new String();

	public String getFilter() {
		return filter;
	}

	@NotifyChange("*")
	public void setFilter(String filter) {
		this.filter = filter;
		StudyVariableManagerImpl studyVarMan = new StudyVariableManagerImpl();
		varList.clear();
		varList.addAll(studyVarMan.getVariables(filter));
		System.out.print(filter);
	}

	@Command
	public void updatefilter(@BindingParam("filterValue") String filterValue) {

		StudyVariableManagerImpl studyVarMan = new StudyVariableManagerImpl();
		varList.clear();
		varList.addAll(studyVarMan.getVariables(filterValue));
		System.out.print(filter);
	}

	private List<StudyVariable> varList = new ArrayList<StudyVariable>();
	private boolean multiselect;

	@Init
	public void Init(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx, @ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("oldVar") String oldVar, @ExecutionArgParam("multiselect") boolean multiselect,
			@ExecutionArgParam("filter") ArrayList<StudyVariable> lstFilters) {

		StudyVariableManagerImpl studyVarMan = new StudyVariableManagerImpl();
		if (lstFilters == null || lstFilters.isEmpty())
			varList = studyVarMan.getVariables();
		else
			varList = studyVarMan.getVariables(lstFilters);
		this.oldVar = oldVar;
		System.out.println(oldVar);
		mainView = view;
		parBinder = (Binder) view.getParent().getAttribute("binder");
		this.multiselect = multiselect;
	}

	public String getOldVar() {
		return oldVar;
	}

	public void setOldVar(String oldVar) {
		this.oldVar = oldVar;
	}

	public List<StudyVariable> getVarList() {
		return varList;
	}

	public void setVarList(List<StudyVariable> varList) {
		this.varList = varList;
	}

	@Command("selectVar")
	public void change(@ContextParam(ContextType.VIEW) Component view, @BindingParam("selVar") String selectedVar) {

		Binder bind = parBinder;
		if (bind == null)
			return;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("newValue", selectedVar);

		// this.parBinder.postCommand("change", params);
		bind.postCommand("change", params);

		view.detach();
	}

	public void onItemClicked(Event ev) throws Exception {
		Event origin;
		// get event target
		if (ev instanceof ForwardEvent) {
			origin = Events.getRealOrigin((ForwardEvent) ev);
		} else {
			origin = ev;
		}
		Component target = origin.getTarget();
		System.out.print(((Row) target).getValue());
	}

	/**
	 * @return the multiselect
	 */
	public boolean isMultiselect() {
		return multiselect;
	}

	/**
	 * @param multiselect
	 *            the multiselect to set
	 */
	public void setMultiselect(boolean multiselect) {
		this.multiselect = multiselect;
	}

	/**
	 * @return the selectedVar
	 */
	public ArrayList<StudyVariable> getSelectedVar() {
		return selectedVar;
	}

	@Command
	public void submitVariables() {

		Binder bind = parBinder;
		if (bind == null)
			return;

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("oldVar", oldVar);
		params.put("variable", selectedVar);
		// this.parBinder.postCommand("change", params);
		bind.postCommand("refreshVarList", params);

		mainView.detach();
	}

	/**
	 * @param selectedVar
	 *            the selectedVar to set
	 */

	public void setSelectedVar(ArrayList<StudyVariable> selectedVar) {
		this.selectedVar = selectedVar;

		if (!multiselect) {

			StudyVariable selVar = selectedVar.get(0);
			System.out.println(selVar.getDescription());

			Binder bind = parBinder;
			if (bind == null)
				return;

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("newValue", selVar.getVariablecode());
			params.put("oldVar", oldVar);
			params.put("variable", selVar);
			// this.parBinder.postCommand("change", params);
			bind.postCommand("refreshVarList", params);

			mainView.detach();
		}

	}

}
