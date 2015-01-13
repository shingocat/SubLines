package org.strasa.web.admin.view.model;

import java.util.HashMap;
import java.util.Map;

import org.strasa.middleware.manager.EcotypeManagerImpl;
import org.strasa.middleware.manager.KeyCharacteristicManagerImpl;
import org.strasa.middleware.manager.ProgramManagerImpl;
import org.strasa.middleware.model.KeyBiotic;
import org.strasa.middleware.model.KeyBiotic;
import org.strasa.middleware.model.KeyGrainQuality;
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

public class AddGrainQuality {
	public static String ZUL_PATH = "/admin/settings/addgrainquality.zul";
	private KeyGrainQuality model = new KeyGrainQuality();
	public KeyGrainQuality getModel() {
		return model;
	}

	public void setModel(KeyGrainQuality model) {
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
		KeyCharacteristicManagerImpl man = new KeyCharacteristicManagerImpl();
		if(man.getAllGrainQualityAsString().contains(model.getValue())){
			Messagebox.show("Grain Quality key already exists! Choose a different name.", "OK", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		try {
			if(model.getValue().isEmpty()){

				Messagebox.show("Please enter a name", "OK", Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO IMPORTANT!!! Must change this to real UserID
		man.addGrainQualityKey(model);
		
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
