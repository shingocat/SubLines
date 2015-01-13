package org.strasa.web.main.view.model;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Window;



public class Login extends SelectorComposer<Window> {

	private static final long serialVersionUID = 5730426085235946339L;


	private String name;
	private String password;
	private String role;
	private String mesgLbl;
	
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	
	
	public String getMesgLbl() {
		return mesgLbl;
	}

	public void setMesgLbl(String mesgLbl) {
		this.mesgLbl = mesgLbl;
	}

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		if (LoginUserCredentialManager.getIntance().isAuthenticated()) {
			Executions.getCurrent().sendRedirect("registration.zul");
		}


	}

	@Command
	public void CheckLogin() {
		doLogin();
	}

	private void doLogin() {
		LoginUserCredentialManager mgmt = LoginUserCredentialManager.getIntance();
		mgmt.login(name, password);
		if (mgmt.isAuthenticated()) {
			Executions.getCurrent().sendRedirect("registration.zul");
		} else {
			mesgLbl="Your User Name or Password is invalid!";
		}
	}


}
