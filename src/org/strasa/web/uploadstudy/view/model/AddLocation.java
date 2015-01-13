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

import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.manager.CountryManagerImpl;
import org.strasa.middleware.manager.LocationManagerImpl;
import org.strasa.middleware.model.Country;
import org.strasa.middleware.model.Location;
import org.strasa.web.common.api.FormValidator;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Messagebox;

public class AddLocation {
	public static String ZUL_PATH = "/user/uploadstudy/addlocation.zul";
	private Component mainView;
	private Binder parBinder;
	private FormValidator formValidator;
	private Location locationModel = new Location();
	public int selectedID;
	private Integer userID = SecurityUtil.getDbUser().getId();
	private ArrayList<String> cmbCountry = new ArrayList<String>();

	public ArrayList<String> getCmbCountry() {
		return cmbCountry;
	}

	public void setCmbCountry(ArrayList<String> cmbCountry) {
		this.cmbCountry = cmbCountry;
	}

	public Location getLocationModel() {
		return locationModel;
	}

	public void setLocationModel(Location locationModel) {
		this.locationModel = locationModel;
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
	public void Init(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx, @ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("locname") String newName) {

		mainView = view;
		parBinder = (Binder) view.getParent().getAttribute("binder");

		List<Country> lCountries = new CountryManagerImpl().getAllCountry();
		for (Country data : lCountries) {
			cmbCountry.add(data.getIsoabbr());
		}
		locationModel.setLocationname(newName);
	}

	@Command("add")
	public void add() {

		LocationManagerImpl locationMan = new LocationManagerImpl();

		try {
			if (!formValidator.getBlankVariables(locationModel, new String[] { "id", "userid" }).isEmpty()) {
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

		locationModel.setUserid(userID);
		locationMan.addLocation(locationModel);

		// TODO Validate!!
		Messagebox.show("Location successfully added to database!", "Add", Messagebox.OK, Messagebox.INFORMATION);
		// System.out.println("SavePath: "+CsvPath);

		Binder bind = parBinder;
		if (bind == null)
			return;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("locationModel", locationModel);

		// this.parBinder.postCommand("change", params);
		bind.postCommand("newLocationModel", params);
		mainView.detach();
	}

	@Command
	public void cancel() {
		mainView.detach();
	}

}
