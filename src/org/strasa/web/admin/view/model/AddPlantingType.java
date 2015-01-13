package org.strasa.web.admin.view.model;

import java.util.HashMap;
import java.util.Map;

import org.strasa.middleware.manager.EcotypeManagerImpl;
import org.strasa.middleware.manager.PlantingTypeManagerImpl;
import org.strasa.middleware.manager.ProgramManagerImpl;
import org.strasa.middleware.model.Ecotype;
import org.strasa.middleware.model.PlantingType;
import org.strasa.middleware.model.Program;
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

public class AddPlantingType {
	public static String ZUL_PATH = "/admin/settings/addplantingtype.zul";
	private PlantingType model = new PlantingType();
	public PlantingType getModel() {
		return model;
	}

	public void setModel(PlantingType model) {
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
		PlantingTypeManagerImpl man = new PlantingTypeManagerImpl();
		if(man.getAllPlantingTypes().contains(model.getPlanting())){
			Messagebox.show("Planting type already exist! Choose a different name.", "OK", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		try {
			if(model.getPlanting().isEmpty()){

				Messagebox.show("Please enter a name", "OK", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO IMPORTANT!!! Must change this to real UserID
		man.addPlantingType(model);
		
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
