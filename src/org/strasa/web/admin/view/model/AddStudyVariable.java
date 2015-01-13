package org.strasa.web.admin.view.model;

import java.util.HashMap;
import java.util.Map;

import org.strasa.middleware.manager.EcotypeManagerImpl;
import org.strasa.middleware.manager.ProgramManagerImpl;
import org.strasa.middleware.manager.StudyTypeManagerImpl;
import org.strasa.middleware.model.Ecotype;
import org.strasa.middleware.model.Program;
import org.strasa.middleware.model.StudyType;
import org.strasa.web.common.api.FormValidator;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Messagebox;

public class AddStudyVariable {
	public static String ZUL_PATH = "/admin/settings/addstudytype.zul";
	private StudyType model = new StudyType();
	public StudyType getModel() {
		return model;
	}

	public void setModel(StudyType model) {
		this.model = model;
	}
	private Component mainView;
	private Binder parBinder;

	@Init
	public void Init(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx,@ContextParam(ContextType.VIEW) Component view ,@ExecutionArgParam("oldVar")  String oldVar) {

	        mainView = view;
	        parBinder =  (Binder) view.getParent().getAttribute("binder");
	        
	}
	
	@Command("add")
	public void add(){
		StudyTypeManagerImpl man = new StudyTypeManagerImpl();
		if(man.getAllStudyType().contains(model.getStudytype())){
			Messagebox.show("StudyType already exist! Choose a different name.", "OK", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		try {
			if(model.getStudytype().isEmpty()){

				Messagebox.show("Please enter a name", "OK", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO IMPORTANT!!! Must change this to real UserID
		man.addStudyType(model);
		
		//TODO Validate!!
		Messagebox.show("Successfully added to database!", "OK", Messagebox.OK, Messagebox.INFORMATION);
//		System.out.println("SavePath: "+CsvPath);
		
		
		Binder bind = parBinder;
		if (bind == null)
			return;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("selected",model);

		// this.parBinder.postCommand("change", params);
		bind.postCommand("refreshList", params);
		mainView.detach();
	}
	@Command
	public void cancel(){
		mainView.detach();
	}
	
}
