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
	
	Image image;
	String name;
	String filePath;
	String height;
	String width;
	
	@AfterCompose
	public void afterComposer(@ContextParam(ContextType.COMPONENT) final Component component,
			@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("FilePath") String filePath,
			@ExecutionArgParam("Height") String height,
			@ExecutionArgParam("Width") String width,
			@ExecutionArgParam("Name") String name
			)
	{
		if(filePath == null)
			return;
		//wire image component;
		image = (Image) component.getFellow("image");
		this.filePath = filePath;
		this.height = height;
		this.width = width;
		this.name = name;
		
		AImage content = null;
		try {
			content = new AImage(Executions.getCurrent().getDesktop().getWebApp().getResource(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		image.setContent(content);
		image.setHeight(height);
		image.setWidth(width);
		image.setVisible(true);
		
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}
	
}
