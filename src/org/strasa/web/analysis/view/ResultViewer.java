package org.strasa.web.analysis.view;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.analysis.rserve.manager.RServeManager;
import org.apache.commons.io.input.ReaderInputStream;
import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.util.StringConstants;
import org.strasa.web.analysis.result.view.model.FileComposer;
import org.strasa.web.analysis.result.view.model.FileModel;
import org.strasa.web.analysis.result.view.model.FileModelTreeNode;
import org.strasa.web.analysis.view.model.SingleSiteAnalysisModel;
import org.strasa.web.utilities.FileUtilities;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Treeitem;

import au.com.bytecode.opencsv.CSVReader;

public class ResultViewer {
	private RServeManager rServeManager;
	String textFileContent = null;
	private AMedia fileContent;
	private File tempFile;
	private static final String FILE_SEPARATOR = System
			.getProperty("file.separator");
	private static final String IMAGE_THUMBNAIL_HEIGHT = "150px";
	private static final String IMAGE_THUMBNAIL_WIDTH = "150px";
	private static final String TXT_THUMBNAIL_HEIGHT = "150px";
	private static final String TXT_THUMBNAIL_WIDTH = "150px";
	private static final String IMAGE_HEIGHT = "480px";
	private static final String IMAGE_WIDTH = "480px";
	private static String RESULT_ANALYSIS_PATH = FILE_SEPARATOR
			+ "resultanalysis" + FILE_SEPARATOR + SecurityUtil.getUserName()
			+ FILE_SEPARATOR + "Single-Site" + FILE_SEPARATOR;
	private String outputFolderPath;

	@AfterCompose
	public void afterCompose(
			@ContextParam(ContextType.COMPONENT) final Component component,
			@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("OutputFolderPath") String outputFolderPath) {
		StringBuilder sb = new StringBuilder();
		if(outputFolderPath == null)
			return;
		this.outputFolderPath = outputFolderPath;
		// outputTextViewer
		File outputFolder = new File(outputFolderPath);
		if (outputFolder.isDirectory()) {
			// System.out.println("folder: " + outputFolderPath);
			String[] files = outputFolder.list();
			for (String file : files) {
				final String path = (outputFolder + File.separator + file).replace(StringConstants.BSLASH, StringConstants.FSLASH);
				final String name = new File(path).getName();
				String [] tempPath = path.split(StringConstants.FSLASH);
				String temp = path.substring(path.indexOf("resultanalysis"));
				final String relativePath = StringConstants.FSLASH + StringConstants.FSLASH + 
						temp.toString().replace(StringConstants.BSLASH, StringConstants.FSLASH);
				if (file.endsWith(".txt")) {
					Div div = (Div) component.getFellow("txtResultDiv");
					final Groupbox gb = new Groupbox();
					gb.setTitle(file.replaceAll(".csv", "").replaceAll(".txt", ""));
					gb.setHeight(TXT_THUMBNAIL_HEIGHT);
					gb.setWidth(TXT_THUMBNAIL_WIDTH);
					gb.setClosable(false);
					gb.addEventListener(Events.ON_CLICK, 
							new EventListener<Event>(){
						@Override
						public void onEvent(Event event)
								throws Exception {
							viewTxtContent(name.replaceAll(".txt", ""), relativePath, component);
						}
					});
					Div inc = new Div();
					inc.setHflex("1");
					inc.setVflex("1");
					Image icon = new Image();
					icon.setSrc("/images/TXT128.png");
					icon.setParent(inc);
					inc.setParent(gb);

					div.appendChild(gb);
					Separator sep = new Separator();
					sep.setHeight("30px");
					div.appendChild(sep);
					sb.append("txt");
				} else if (file.endsWith(".png")) {
					Div div = (Div) component.getFellow("graphResultDiv");
					final Groupbox newGroupBox = new Groupbox();
					// newGroupBox.setStyle("overflow: auto");
					newGroupBox.setTitle(file.replaceAll(".csv", ""));
					newGroupBox.setHeight(IMAGE_THUMBNAIL_HEIGHT);
					newGroupBox.setWidth(IMAGE_THUMBNAIL_WIDTH);
					newGroupBox.setClosable(false);

					newGroupBox.addEventListener(Events.ON_CLICK,
							new EventListener<Event>() {
						@Override
						public void onEvent(Event event)
								throws Exception {
							zoomImage(name.replaceAll(".png", ""), relativePath, component);
						}
					});

					Include studyInformationPage = new Include();
					studyInformationPage.setDynamicProperty("Height",
							IMAGE_THUMBNAIL_HEIGHT);
					studyInformationPage.setDynamicProperty("Width",
							IMAGE_THUMBNAIL_WIDTH);
					studyInformationPage.setDynamicProperty("FilePath", relativePath);
					studyInformationPage.setSrc("/user/analysis/imgviewer.zul");
					studyInformationPage.setParent(newGroupBox);

					div.appendChild(newGroupBox);
					Separator sep = new Separator();
					sep.setHeight("30px");
					div.appendChild(sep);
					sb.append(".png");
				} else if (file.endsWith(".csv") && !file.contains("(Dataset)")) {
					Div div = (Div) component.getFellow("csvResultDiv");
					final Groupbox gb = new Groupbox();
					gb.setTitle(file.replaceAll(".csv", "").replaceAll(".txt", ""));
					gb.setHeight(TXT_THUMBNAIL_HEIGHT);
					gb.setWidth(TXT_THUMBNAIL_WIDTH);
					gb.setClosable(false);
					gb.addEventListener(Events.ON_CLICK, 
							new EventListener<Event>(){
						@Override
						public void onEvent(Event event)
								throws Exception {
							viewCsvContent(name.replaceAll(".csv", ""),relativePath, component);
						}
					});
					Div inc = new Div();
					inc.setHflex("1");
					inc.setVflex("1");
					Image icon = new Image();
					icon.setSrc("/images/CSV128.png");
					icon.setParent(inc);
					inc.setParent(gb);

					div.appendChild(gb);
					Separator sep = new Separator();
					sep.setHeight("30px");
					div.appendChild(sep);
					sb.append("csv");

				} else if (file.endsWith("(Dataset).csv")) {
					Tabpanel tabPanel = (Tabpanel) component
							.getFellow("dataSetTab");
					Groupbox newGroupBox = new Groupbox();
					newGroupBox.setTitle(file.replaceAll(".csv", ""));
					newGroupBox.setMold("3d");
					newGroupBox.setHeight("500px");
					Include inc = new Include();
					inc.setDynamicProperty("FilePath",relativePath);
					inc.setDynamicProperty("Name",name.replaceAll(".csv", ""));
					inc.setSrc("/user/analysis/csvviewer.zul");
					inc.setParent(newGroupBox);
					tabPanel.appendChild(newGroupBox);
					Separator sep = new Separator();
					sep.setHeight("20px");
					tabPanel.appendChild(sep);

				}
			}
		}
		String s = sb.toString();
		Tabpanel tabanel = null;
		Tab tab = null;
		if (!s.contains("txt")) {
			tabanel = (Tabpanel) component.getFellow("txtResultTab");
			tab = (Tab) component.getFellow("txtResult");
			detach(tabanel, tab);
		}
		if (!s.contains("csv")) {
			tabanel = (Tabpanel) component.getFellow("csvResultTab");
			tab = (Tab) component.getFellow("csvResult");
			detach(tabanel, tab);
		}
		if (!s.contains("png")) {
			tabanel = (Tabpanel) component.getFellow("graphResultTab");
			tab = (Tab) component.getFellow("graphResult");
			detach(tabanel, tab);
		}

		Tabbox tabBox = (Tabbox) component.getFellow("tabBox");
		tabBox.setSelectedIndex(0);

	}

	protected void zoomImage(String name, String filePath, Component component) {
		Div div = (Div) component.getFellow("zoomDiv");
		if (div.getChildren().size() > 0)
			div.getChildren().get(0).detach();
		Include inc = new Include();
		inc.setDynamicProperty("Height",FileComposer.IMAGE_HEIGHT);
		inc.setDynamicProperty("Width",FileComposer.IMAGE_WIDTH);
		inc.setDynamicProperty("Name", name);
		inc.setDynamicProperty("FilePath", filePath);
		inc.setSrc("/user/analysis/imgviewer.zul");
		inc.setParent(div);
		div.appendChild(inc);
	}

	protected void viewTxtContent(String name, String filePath, Component component)
	{
		Div div = (Div) component.getFellow("viewTxtContentDiv");
		if(!div.getChildren().isEmpty())
			div.getChildren().get(0).detach();
		Include inc = new Include();
		inc.setDynamicProperty("Name", name);
		inc.setDynamicProperty("FilePath", filePath);
		inc.setSrc("/user/analysis/txtviewer.zul");
		inc.setParent(div);
	}

	protected void viewCsvContent(String name, String filePath,  Component component)
	{
		Div div = (Div) component.getFellow("viewCsvContentDiv");
		if(!div.getChildren().isEmpty())
			div.getChildren().get(0).detach();
		Include inc = new Include();
		inc.setDynamicProperty("FilePath", filePath);
		inc.setDynamicProperty("Name", name);
		inc.setSrc("/user/analysis/csvviewer.zul");
		inc.setParent(div);
	}

	@NotifyChange("*")
	@Command("save")
	public void save()
	{
		if(outputFolderPath == null)
		{
			showMessage("The output folder is null!");
			return;
		}
		File outputFolder = new File(outputFolderPath);
		if(outputFolder.exists()){
			if(outputFolder.isDirectory())
			{
				//checking whether there is an zip file 
				File [] files = outputFolder.listFiles();
				boolean isZipExist = false;
				File zipFile = null;
				for(File file : files)
				{
					if(file.getName().endsWith(".zip"))
					{ 
						isZipExist = true;
						zipFile = file;
						break;
					}
				}
				if(isZipExist)
				{
					try {
						Filedownload.save(zipFile, ".zip");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				} else
				{
					FileUtilities.buildZip(outputFolderPath, outputFolderPath, "Outcomes");
					zipFile = new File(outputFolderPath + File.separator + "Outcomes.zip");
					try {
						Filedownload.save(zipFile, ".zip");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			} else
			{
				showMessage("The output folder is not a folder!");
				return;
			}
		}else
		{
			showMessage("Could not find the output folder!");
			return;
		}
	}

	private void detach(Tabpanel tabanel, Tab tab) {
		// TODO Auto-generated method stub
		tabanel.detach();
		tab.detach();
	}

	private void showMessage(String message)
	{
		Messagebox.show(message, "Warning", Messagebox.OK, Messagebox.INFORMATION);
	}
}
