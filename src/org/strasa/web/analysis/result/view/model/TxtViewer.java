package org.strasa.web.analysis.result.view.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Include;

public class TxtViewer {
	Iframe printIFrame;
	
	String name;
	String filePath;
	
	@AfterCompose
	public void afterComposer(@ContextParam(ContextType.COMPONENT) final Component component,
			@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("FilePath") String filePath,
			@ExecutionArgParam("Name") String name
			)
	{
		System.out.println("Txt File Path "+filePath);
		if(filePath == null)
			return;
		this.name = name;
		this.filePath = filePath;
		
		printIFrame = (Iframe) component.getFellow("printIFrame");
		File fileToCreate = new File(Executions.getCurrent().getDesktop().getWebApp().getRealPath(filePath));
		byte[] buffer = new byte[(int) fileToCreate.length()];
		FileInputStream fs;
		try {
			fs = new FileInputStream(fileToCreate);
			fs.read(buffer);
			fs.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ByteArrayInputStream is = new ByteArrayInputStream(buffer);
		AMedia fileContent = new AMedia("report", "text",
				"text/plain", is);
		printIFrame.setContent(fileContent);
	}
}
