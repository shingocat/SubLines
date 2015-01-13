package org.strasa.web.test.view.model;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;

public class MultiUpload {

	@Wire
	private Button uploadBtn1;
	@Wire
	private Button uploadBtn2;
	@Wire
	private Label label1;
	@Wire
	private Label label2;

	@AfterCompose
	public void afterCompose(
			@ContextParam(ContextType.COMPONENT) Component component,
			@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(component, this, false);
	}

	@Command
	public void upload1() {
		System.out.println("upload 1 start");
	}

	@Command
	public void upload2() {
		System.out.println("upload 2 start");

	}
}
