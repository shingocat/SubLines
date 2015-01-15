package org.strasa.web.maintenance.view.model;

import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.manager.ProgramManagerImpl;
import org.strasa.middleware.model.Program;
import org.strasa.web.common.api.FormValidator;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class AddNewProgram {
	public static String ZUL_PATH = "/user/uploadstudy/addprogram.zul";
	private Program programModel = new Program();
	private Component mainView;
	
	Textbox nameTB;
	Textbox objectiveTB;
	Textbox coordinatorTB;
	Textbox leadingInstituteTB;
	Textbox collaboratorTB;

	public Component getMainView() {
		return mainView;
	}

	public void setMainView(Component mainView) {
		this.mainView = mainView;
	}

	private Binder parBinder;
	private int userID = SecurityUtil.getDbUser().getId();

	public Program getProgramModel() {
		return programModel;
	}

	public void setProgramModel(Program programModel) {
		this.programModel = programModel;
	}

	@Init
	public void Init(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx, @ContextParam(ContextType.VIEW) Component view) {
		mainView = view;
		parBinder = ctx.getBinder();
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.COMPONENT) Component comp,
			@ContextParam(ContextType.VIEW) Component view)
	{
		nameTB = (Textbox) comp.getFellow("nameTB");
		objectiveTB = (Textbox) comp.getFellow("objectiveTB");
		coordinatorTB= (Textbox) comp.getFellow("coordinatorTB");
		leadingInstituteTB = (Textbox) comp.getFellow("leadingInstituteTB");
		collaboratorTB = (Textbox) comp.getFellow("collaboratorTB");
	}

	@NotifyChange("*")
	@Command("add")
	public void add() {
		if(programModel != null)
		{
			if(programModel.getName() == null || programModel.getName().length() == 0)
			{
				Messagebox.show("Program name not allow empty", "Warnings", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			if(programModel.getObjective() == null || programModel.getObjective().length() == 0)
			{
				Messagebox.show("Program object not allow empty", "Warnings", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			if(programModel.getCoordinator() == null || programModel.getCoordinator().length() == 0)
			{
				Messagebox.show("Program coordinator not allow empty", "Warnings", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			if(programModel.getLeadinginstitute() == null || programModel.getLeadinginstitute().length() == 0)
			{
				Messagebox.show("Program leading institue not allow empty", "Warnings", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			if(programModel.getCollaborator() == null || programModel.getCollaborator().length() == 0)
			{
				Messagebox.show("Program collaborator not allow empty", "Warnings", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			ProgramManagerImpl programMan = new ProgramManagerImpl();
			if (programMan.isProgramExist(programModel.getName(), getUserID())) {
				Messagebox.show("Program already exist! Choose a different name.", "Program Added", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			try {
				if (new FormValidator().getBlankVariables(programModel, new String[] { "userid", "id" }).isEmpty() == false) {

					Messagebox.show("All fields are required", "Progam Added", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TODO IMPORTANT!!! Must change this to real UserID
			programModel.setUserid(getUserID());
			programMan.addProgram(programModel);

			// TODO Validate!!
			Messagebox.show("Program added successfully", "Program Added", Messagebox.OK, Messagebox.INFORMATION);
			BindUtils.postGlobalCommand(null, null, "refreshProgramList", null);
			setProgramModel(new Program());
		}
	
	}
	
	@Command
	public void reset()
	{
		if(programModel != null)
		{
			programModel.setName(null);
			programModel.setObjective(null);
			programModel.setCoordinator(null);
			programModel.setLeadinginstitute(null);
			programModel.setCollaborator(null);
			programModel.setCollaborator(null);
		}
		this.nameTB.setRawValue(null);
		this.objectiveTB.setRawValue(null);
		this.collaboratorTB.setRawValue(null);
		this.leadingInstituteTB.setRawValue(null);
		this.collaboratorTB.setRawValue(null);
	}

	@Command
	public void cancel() {
		mainView.detach();
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
}
