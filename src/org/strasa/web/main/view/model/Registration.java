package org.strasa.web.main.view.model;

import org.strasa.middleware.manager.UserManagerImpl;
import org.strasa.middleware.model.DbUser;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

public class Registration extends RegistrationModel {

	private String dateFormat;
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public String getForegroundColour() {
		return foregroundColour;
	}
	public void setForegroundColour(String foregroundColour) {
		this.foregroundColour = foregroundColour;
	}
	public String getBackgroundColour() {
		return backgroundColour;
	}
	public void setBackgroundColour(String backgroundColour) {
		this.backgroundColour = backgroundColour;
	}
	private String foregroundColour = "#000000", backgroundColour = "#FDC966";


	@Command
	@NotifyChange("captcha")
	public void regenerate() {
		this.regenerateCaptcha();
	}

	@NotifyChange("*")
	@Command
	public void submit() {

		UserManagerImpl userManger= new UserManagerImpl();
		userManger.addUser(this.getUser());
		Executions.sendRedirect("registrationmsg.zul");
		
		
	}

}
