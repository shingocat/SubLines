package org.strasa.web.crossstudyquery.view.model;

import java.util.HashMap;
import java.util.Map;

import org.strasa.middleware.manager.StudyManager;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Toolbarbutton;

public class  AcrossStudyRowRenderer implements RowRenderer<AcrossStudyData>{

	
	private Tab crossstudySearchTab;
//	private Binder parBinder;
	
	@NotifyChange("newDataRow")
	@Init
	public void setInitialData(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx,@ContextParam(ContextType.VIEW) Component view,@ExecutionArgParam("crossstudySearchTab")Tab crossstudySearchTab){
		this.crossstudySearchTab = crossstudySearchTab;
//        parBinder =  (Binder) view.getParent().getAttribute("binder");
	}
	
	private int getStudyIdByStudyName(String studyname) {
		int toreturn=0;
		StudyManager mgr = new StudyManager();
		toreturn=mgr.getStudyByStudyName(studyname);
		
		return toreturn;
	}
	
	@Override
	public void render(Row row, final AcrossStudyData data, int index) throws Exception {

//		row.appendChild(new Label(String.valueOf(data.getStudyId())));
		
		Toolbarbutton studyNameLink=  new Toolbarbutton();
		studyNameLink.setLabel(data.getStudyname());
		studyNameLink.setClass("grid-link");
		studyNameLink.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				// TODO Auto-generated method stub

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("studyid",getStudyIdByStudyName(data.getStudyname()));
				params.put("studyName",data.getStudyname());
				
				BindUtils.postGlobalCommand(null, null, "openStudyDetailInAcrossStudy", params);
			}

		});

		row.appendChild(studyNameLink);

		Toolbarbutton gNameLink=  new Toolbarbutton();
		gNameLink.setLabel(data.getGname());
		gNameLink.setClass("grid-link");
		row.appendChild(gNameLink);
		
		gNameLink.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				// TODO Auto-generated method stub

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("gname",data.getGname());
				
				BindUtils.postGlobalCommand(null, null, "openGermplasmDetailInAcrossStudy", params);
			}

		});

		for(String s: data.getOtherdata()){
			row.appendChild(new Label(s));

		}

	}
}	