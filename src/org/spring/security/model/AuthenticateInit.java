package org.spring.security.model;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.spring.security.model.SecurityUtil;
import org.springframework.security.core.userdetails.User;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zul.Include;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class AuthenticateInit extends org.zkoss.zk.ui.util.GenericInitiator{

	@Init
	public void doInit(Page page, Map args) throws Exception {
		
		if(SecurityUtil.isAnyGranted("ROLE_ADMIN")){
			Execution exec = Executions.getCurrent();
			HttpServletResponse response = (HttpServletResponse)exec.getNativeResponse();
			response.sendRedirect(response.encodeRedirectURL("admin/")); //assume there is /login
//			exec.setVoided(true); //no
		}else if(SecurityUtil.isAnyGranted("ROLE_USER")){
			Execution exec = Executions.getCurrent();
			HttpServletResponse response = (HttpServletResponse)exec.getNativeResponse();
			response.sendRedirect(response.encodeRedirectURL("user/")); //assume there is /login
//			exec.setVoided(true); //no
		}else if(SecurityUtil.isAnyGranted("ROLE_GUEST")){
			Execution exec = Executions.getCurrent();
			HttpServletResponse response = (HttpServletResponse)exec.getNativeResponse();
			response.sendRedirect(response.encodeRedirectURL("newuser/")); //assume there is /login
		}
		
	}
	
	@Command("register")
	public void openRegisterForm() throws IOException{
		Execution exec = Executions.getCurrent();
		HttpServletResponse response = (HttpServletResponse)exec.getNativeResponse();
		response.sendRedirect(response.encodeRedirectURL("registration.zul")); //assume there is /login
		exec.setVoided(true); //no
	}
	


}
