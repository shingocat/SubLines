package org.strasa.web.utilities;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Grid;
import org.zkoss.zul.impl.InputElement;

public class GridValidationUtility {

	public Grid mainCom;
	private ArrayList<Integer> lstColumns;

	public GridValidationUtility(Grid mainCom, ArrayList<Integer> lstCol) {
		this.mainCom = mainCom;
		this.lstColumns = lstCol;
	}

	public void validateAll() {
		for (Component rows : mainCom.getRows().getChildren()) {

			validateRow(rows);

		}

	}

	public void validateRow(Component row) {
		for (Integer col : lstColumns) {

			check(row.getChildren().get(col));
		}

	}

	public void check(Component component) throws WrongValueException {
		checkIsValid(component);

		List<Component> children = component.getChildren();
		for (Component each : children) {
			check(each);
		}
	}

	public void checkIsValid(Component component) throws WrongValueException {
		if (component instanceof InputElement) {
			((InputElement) component).setConstraint("no empty");
			try {
				((InputElement) component).getText();
			} finally {
				((InputElement) component).setConstraint((String) null);
			}

		}
	}
}
