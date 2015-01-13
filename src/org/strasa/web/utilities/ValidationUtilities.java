package org.strasa.web.utilities;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.impl.InputElement;

public class ValidationUtilities {
	public static void check(Component component) throws WrongValueException {
		checkIsValid(component);

		List<Component> children = component.getChildren();
		for (Component each : children) {
			check(each);
		}
	}

	public static void checkIsValid(Component component) throws WrongValueException {
		if (component instanceof InputElement) {
			if (!((InputElement) component).isValid()) {
				// Force show errorMessage
				((InputElement) component).getText();
			}
		}
	}
}
