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

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;

public class DetailedMissingGermplasmList {

	private Component mainView;
	private Binder parBinder;
	private ArrayList<String> invalidGermplasmList;

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

	public ArrayList<String> getInvalidGermplasmList() {
		return invalidGermplasmList;
	}

	public void setInvalidGermplasmList(ArrayList<String> invalidGermplasmList) {
		this.invalidGermplasmList = invalidGermplasmList;
	}

	@Init
	public void Init(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx, @ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("list") ArrayList<String> invalidGermList) {

		mainView = view;
		// parBinder = (Binder) view.getParent().getAttribute("binder");

		invalidGermplasmList = invalidGermList;

	}
}
