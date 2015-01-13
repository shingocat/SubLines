/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
 */
package org.strasa.web.page.navigation.user;

import org.spring.security.model.SecurityUtil;
import org.strasa.web.page.util.navigation.SidebarPage;
import org.strasa.web.page.util.navigation.SidebarPageConfig;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;

public class SidebarAjaxbasedController extends SelectorComposer<Component>{

	private static final long serialVersionUID = 1L;
	@Wire
	Grid fnListUpload;
	@Wire
	Grid fnListBrowse;
	@Wire
	Grid fnAnalysis;
	@Wire
	Grid fnMaintenance;
	
	@Wire
	Grid fnDecisionMaking;

	//wire service
	SidebarPageConfig pageConfig = new SidebarPageConfigAjaxBasedImpl();

	@Override
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);

		//to initial view after view constructed.
		Rows rowsUpload = fnListUpload.getRows();
		Rows rowsAnalysis = fnAnalysis.getRows();
		Rows rowsDecisionMaking = fnDecisionMaking.getRows();
		Rows rowsMaintenance= fnMaintenance.getRows();
		Rows rowsBrowse= fnListBrowse.getRows();
		
		if(SecurityUtil.isAnyGranted("ROLE_GUEST")){
			Tab tabUpload = (Tab) comp.getFellow("uploadId");
			Tabpanel tabPanelUpload = (Tabpanel) comp.getFellow("tabPanelUploadId");
			Tab tabSetting = (Tab) comp.getFellow("settingsId");
			Tabpanel tabPanelSettings = (Tabpanel) comp.getFellow("tabPanelSettingId");
			
			tabUpload.detach();
			tabPanelUpload.detach();
			tabSetting.detach();
			tabPanelSettings.detach();
			
			Tab tabBrowse = (Tab) comp.getFellow("browseId");
			tabBrowse.setSelected(true);
		}
	
		for(SidebarPage page:pageConfig.getPages()){
			Row row;
			
			if(page.getName().contains("upload") && !SecurityUtil.isAnyGranted("ROLE_GUEST")){
				 row= constructSidebarRow("upload",page.getLabel(),page.getLabel(),page.getIconUri(),page.getUri());
				rowsUpload.appendChild(row);
			}else if (page.getName().contains("browse")){
				row = constructSidebarRow("browse",page.getName(),page.getLabel(),page.getIconUri(),page.getUri());
				rowsBrowse.appendChild(row);
			}else if (page.getName().contains("analysis")){
				row = constructSidebarRow("analysis",page.getName(),page.getLabel(),page.getIconUri(),page.getUri());
				rowsAnalysis.appendChild(row);
			}else if (page.getName().contains("decision")){
				row = constructSidebarRow("decision",page.getName(),page.getLabel(),page.getIconUri(),page.getUri());
				rowsDecisionMaking.appendChild(row);
			}else if (page.getName().contains("maintenance") && !SecurityUtil.isAnyGranted("ROLE_GUEST")){
				row = constructSidebarRow("maintenance",page.getName(),page.getLabel(),page.getIconUri(),page.getUri());
				rowsMaintenance.appendChild(row);
			}
		}		
	}

	private Row constructSidebarRow(final String menuCategory,final String name,String label, String imageSrc, final String locationUri) {

		//construct component and hierarchy
		Row row = new Row();
		Image image = new Image(imageSrc);
		Label lab = new Label(label);

		row.appendChild(image);
		row.appendChild(lab);

		//set style attribute
		row.setSclass("sidebar-fn");

		//new and register listener for events
		EventListener<Event> onActionListener = new SerializableEventListener<Event>(){
			private static final long serialVersionUID = 1L;

			public void onEvent(Event event) throws Exception {
				//redirect current url to new location
				if(locationUri.startsWith("http")){
					//open a new browser tab
					Executions.getCurrent().sendRedirect(locationUri);
				}else{
					//use iterable to find the first include only
					Include include = null;
					if(menuCategory.equals("upload")){
						include= (Include)Selectors.iterable(fnListUpload.getPage(), "#mainInclude").iterator().next();
					}else if(menuCategory.equals("browse")){
						include= (Include)Selectors.iterable(fnListBrowse.getPage(), "#mainInclude").iterator().next();
					}else if(menuCategory.equals("analysis")){
						include= (Include)Selectors.iterable(fnAnalysis.getPage(), "#mainInclude").iterator().next();
					}else if(menuCategory.equals("decision")){
						include= (Include)Selectors.iterable(fnDecisionMaking.getPage(), "#mainInclude").iterator().next();
					}else if(menuCategory.equals("maintenance")){
						include= (Include)Selectors.iterable(fnMaintenance.getPage(), "#mainInclude").iterator().next();
					}

					include.setSrc(locationUri);

					//advance bookmark control, 
					//bookmark with a prefix
					if(name!=null){
						getPage().getDesktop().setBookmark(name);
					}
				}
			}
		};		
		row.addEventListener(Events.ON_CLICK, onActionListener);

		return row;
	}

}
