package org.strasa.web.admin.view.model;

import java.io.File;
import java.util.List;

import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.manager.UserManagerImpl;
import org.strasa.middleware.model.DbUser;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Messagebox;

public class UserManagement {


	List<DbUser> users;

	public List<DbUser> getUser() {
		return users = this.getAllRegisteredUser();
	}


	public List<DbUser> getAllRegisteredUser(){

		UserManagerImpl userManagerImpl= new UserManagerImpl();
		List<DbUser> users=userManagerImpl.getAllRegisteredUser();
		return users;

	}

	@NotifyChange("*")
	@Command("onChecked")
	public void addUser(@BindingParam("user") DbUser user){
		UserManagerImpl userManagerImp =  new UserManagerImpl();
		userManagerImp.updateUser(user);
		if(user.getStatus()==true){
			createUserFolder(user);
		}
	}

	private void createUserFolder(DbUser user) {
		try{
			String path = Sessions.getCurrent().getWebApp().getServletContext().getRealPath("/")+"resultanalysis"+"/"+user.getUsername();
			File f = new File (path);
			f.mkdir();
		}catch(Exception e){

		}

	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@NotifyChange("*")
	@Command("deleteUser")
	public void deleteUser(@BindingParam("user") final DbUser user){

		Messagebox.show("Are you sure to delete this user?", "Confirm Dialog", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
			public void onEvent(Event evt) throws InterruptedException {
				if (evt.getName().equals("onOK")) {
					UserManagerImpl userManagerImp =  new UserManagerImpl();
					userManagerImp.deleteUser(user);
				} 
			}
		});
	}

}
