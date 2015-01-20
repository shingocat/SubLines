package org.strasa.web.analysis.result.view.model;

import java.io.IOException;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Image;

public class ImageViewer {
	
	String imageName;
	Image image;
	@AfterCompose
	public void afterComposer(@ContextParam(ContextType.COMPONENT) final Component component,
			@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("filepath") String filepath,
			@ExecutionArgParam("height") String height,
			@ExecutionArgParam("width") String width,
			@ExecutionArgParam("name") String name
			)
	{
		if(filepath == null)
			return;
		System.out.println("filepath : " + filepath);
		System.out.println("height : " + height);
		System.out.println("width : " + width);
		System.out.println("name : " + name);
		//wire image component;
		image = (Image) component.getFellow("image");
		AImage content = null;
		try {
			content = new AImage(Executions.getCurrent().getDesktop().getWebApp().getResource(filepath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		image.setContent(content);
		image.setHeight(height);
		image.setWidth(width);
		image.setVisible(true);
		
		this.setImageName(name);
	}
	
	public String getImageName()
	{
		return imageName;
	}
	public void setImageName(String imageName)
	{
		this.imageName = imageName;
	}
}
