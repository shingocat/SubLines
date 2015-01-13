package org.strasa.web.maintenance.view.model;

import java.util.List;

import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.manager.ProgramManagerImpl;
import org.strasa.middleware.manager.ProjectManagerImpl;
import org.strasa.middleware.model.Program;
import org.strasa.middleware.model.Project;
import org.strasa.web.common.api.FormValidator;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Messagebox;

public class AddNewProject {
	public static String ZUL_PATH = "/user/maintenance/addnewproject.zul";
	private Component mainView;
	private Binder parBinder;
	private FormValidator formValidator;
	private Project projectModel = new Project();
	public int programID;
	private Integer userID = SecurityUtil.getDbUser().getId();
	private List<Program> programList = null;
	private Program program = new Program();
	private ProgramManagerImpl programMan;

	@Init
	public void Init(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx, @ContextParam(ContextType.VIEW) Component view) {

		setMainView(view);
		parBinder = ctx.getBinder();

		programMan = new ProgramManagerImpl();
		setProgramList(programMan.getAllProgram());
	}

	@NotifyChange("*")
	@Command("add")
	public void add() {
		ProjectManagerImpl projectMan = new ProjectManagerImpl();
		if (projectMan.isProjectExist(projectModel.getName(), userID, programID)) {
			Messagebox.show("Project name already exist! Choose a different name.", "Validation Error", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		try {
			if (!formValidator.getBlankVariables(projectModel, new String[] { "id", "userid", "programid" }).isEmpty()) {
				Messagebox.show("All fields are requied.", "Validation Error", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO IMPORTANT!!! Must change this to real UserID
		projectModel.setUserid(userID);
		projectModel.setProgramid(programID);
		projectMan.addProject(projectModel);

		// TODO Validate!!
		Messagebox.show("Program successfully added to database!", "Add", Messagebox.OK, Messagebox.INFORMATION);

		BindUtils.postGlobalCommand(null, null, "refreshProjectList", null);
		setProjectModel(new Project());
		// bind.postCommand("changeProjectList", params);
	}

	@Command
	public void cancel() {
		mainView.detach();
	}

	@NotifyChange("projectList")
	@Command
	public void updateLists(@ContextParam(ContextType.COMPONENT) Component component, @ContextParam(ContextType.VIEW) Component view, @BindingParam("program") Comboitem comboProgram) {
		programID = comboProgram.getValue();
		program.setId(programID);
	}

	public List<Program> getProgramList() {
		return programList;
	}

	public void setProgramList(List<Program> programList) {
		this.programList = programList;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Project getProjectModel() {
		return projectModel;
	}

	public void setProjectModel(Project projectModel) {
		this.projectModel = projectModel;
	}

	public FormValidator getFormValidator() {
		return formValidator;
	}

	public void setFormValidator(FormValidator formValidator) {
		this.formValidator = formValidator;
	}

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

	// System.out.println("SavePath: "+CsvPath);
}
