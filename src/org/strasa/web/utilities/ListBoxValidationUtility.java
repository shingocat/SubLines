package org.strasa.web.utilities;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.impl.InputElement;

public class ListBoxValidationUtility {

	public Listbox mainCom;
	private ArrayList<Integer> lstColumns;

	public ListBoxValidationUtility(Listbox mainCom, ArrayList<Integer> lstCol) {
		this.mainCom = mainCom;
		this.lstColumns = lstCol;
	}

	public void validateAll() {
		for (Listitem rows : mainCom.getItems()) {
			
				validateRow(rows);

		}

	}

	public void validateRow(Listitem row) {
		System.out.println("TBL: " + mainCom.getListhead().getChildren().size() + "  l: " + row.getChildren().size());
		for (Integer col : lstColumns) {
				check(row.getChildren().get(col));
		}

	}

	public void validateRow(Integer rownum) {

		validateRow(mainCom.getItems().get(rownum));
	}

	public void check(Component component) throws WrongValueException {
		// System.out.println(component.getWidgetClass());
		if (component instanceof Label)
			System.out.println("val: " + ((Label) component).getValue());
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
				System.out.println("Data: " + ((InputElement) component).getText());
			} finally {
				((InputElement) component).setConstraint((String) null);
			}

		}
	}
}
