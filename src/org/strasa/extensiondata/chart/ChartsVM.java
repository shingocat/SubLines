package org.strasa.extensiondata.chart;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.CategoryModel;

public class ChartsVM {
	
	CategoryModel model;
    String type;
     
    @Init
    public void init() {
        // prepare chart data
        type = "bar";
        model = ChartData.getModelByCountry();
    }
 
    public CategoryModel getModel() {
        return model;
    }
     
    public String getType(){
        return type;
    }
     
    @GlobalCommand("configChanged") 
    @NotifyChange("type")
    public void onConfigChanged(
            @BindingParam("type")String type){
        this.type = type;
    }

}
