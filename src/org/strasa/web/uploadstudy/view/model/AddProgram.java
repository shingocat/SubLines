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
import org.strasa.middleware.manager.ProgramManagerImpl;
import org.strasa.middleware.model.Program;
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
import org.zkoss.zul.Messagebox;

public class AddProgram {
	public static String ZUL_PATH = "/user/uploadstudy/addprogram.zul";
	private Program programModel = new Program();
	private Component mainView;
	private Binder parBinder;
	private int userID = SecurityUtil.getDbUser().getId();

	public Program getProgramModel() {
		return programModel;
	}

	public void setProgramModel(Program programModel) {
		this.programModel = programModel;
	}

	@Init
	public void Init(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx, @ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("oldVar") String oldVar) {

		mainView = view;
		parBinder = (Binder) view.getParent().getAttribute("binder");

	}

	@Command("add")
	public void add() {
		ValidationUtilities.check(mainView);
		ProgramManagerImpl programMan = new ProgramManagerImpl();

		if (programMan.isProgramExist(programModel.getName(), userID)) {
			Messagebox.show("Program already exist! Choose a different name.", "OK", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		try {
			if (new FormValidator().getBlankVariables(programModel, new String[] { "userid", "id" }).isEmpty() == false) {

				Messagebox.show("All fields are required", "OK", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO IMPORTANT!!! Must change this to real UserID
		programModel.setUserid(userID);
		programMan.addProgram(programModel);

		// TODO Validate!!
		Messagebox.show("Program successfully added to database!", "OK", Messagebox.OK, Messagebox.INFORMATION);
		// System.out.println("SavePath: "+CsvPath);

		Binder bind = parBinder;
		if (bind == null)
			return;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("selected", programModel);

		// this.parBinder.postCommand("change", params);
		bind.postCommand("refreshProgramList", params);
		mainView.detach();
	}

	@Command
	public void cancel() {
		mainView.detach();
	}

}
