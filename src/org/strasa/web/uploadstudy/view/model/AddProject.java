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

import java.util.HashMap;
import java.util.Map;

import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.manager.ProjectManagerImpl;
import org.strasa.middleware.model.Project;
import org.strasa.web.common.api.FormValidator;
import org.strasa.web.utilities.ValidationUtilities;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Messagebox;

public class AddProject {
	public static String ZUL_PATH = "/user/uploadstudy/addproject.zul";
	private Component mainView;
	private Binder parBinder;
	private FormValidator formValidator;
	private Project projectModel = new Project();
	public int programID;
	private Integer userID = SecurityUtil.getDbUser().getId();

	public Project getProjectModel() {
		return projectModel;
	}

	public void setProjectModel(Project projectModel) {
		this.projectModel = projectModel;
	}

	public FormValidator getFormValidator() {
		return formValidator;
	}

	public void setFormValidator(FormValidator formValidator) {
		this.formValidator = formValidator;
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

	@Init
	public void Init(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx, @ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("oldVar") String oldVar, @ExecutionArgParam("programID") int pID) {

		mainView = view;
		parBinder = (Binder) view.getParent().getAttribute("binder");
		programID = pID;
	}

	// @Init
	// public void Init(@ContextParam(ContextType.BIND_CONTEXT) BindContext
	// ctx,@ContextParam(ContextType.VIEW) Component view
	// ,@ExecutionArgParam("oldVar") String oldVar,
	// @ExecutionArgParam("programID") int pID ) {
	//
	// mainView = view;
	// parBinder = ctx.getBinder();
	// programID = pID;
	// }

	@Command("add")
	public void add() {

		ProjectManagerImpl projectMan = new ProjectManagerImpl();

		try {
			ValidationUtilities.check(mainView);
		} catch (WrongValueException e) {

		}

		try {
			if (!formValidator.getBlankVariables(projectModel, new String[] { "id", "userid", "programid" }).isEmpty()) {
				Messagebox.show("All fields are requied.", "Validation Error", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO IMPORTANT!!! Must change this to real UserID
		projectModel.setUserid(userID);
		projectModel.setProgramid(programID);
		projectMan.addProject(projectModel);

		// TODO Validate!!
		Messagebox.show("Program successfully added to database!", "Add", Messagebox.OK, Messagebox.INFORMATION);
		// System.out.println("SavePath: "+CsvPath);

		Binder bind = parBinder;
		if (bind == null)
			return;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("selected", projectModel);

		bind.postCommand("changeProjectList", params);
		mainView.detach();
	}

	@Command
	public void cancel() {
		mainView.detach();
	}

}
