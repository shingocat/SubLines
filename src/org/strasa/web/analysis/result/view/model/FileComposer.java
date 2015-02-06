package org.strasa.web.analysis.result.view.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.input.ReaderInputStream;
import org.spring.security.model.SecurityUtil;
import org.strasa.web.analysis.view.model.SingleSiteAnalysisModel;
import org.strasa.web.utilities.AnalysisUtils;
import org.strasa.web.utilities.FileUtilities;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import au.com.bytecode.opencsv.CSVReader;

public class FileComposer extends SelectorComposer<Component> {
	private static final long serialVersionUID = 3814570327995355261L;

	@Wire
	private Window demoWindow;
	@Wire
	private Tree tree;
	@Wire
	private Tabpanels treeTabPanels;
	@Wire
	private Tabs treeTabs;
	@Wire
	private Div viewPanel;

	private static final String FILE_SEPARATOR = System
			.getProperty("file.separator");
	public static final String IMAGE_HEIGHT = "800px";
	public static final String IMAGE_WIDTH = "800px";

	private static String RESULT_ANALYSIS_PATH = FILE_SEPARATOR
			+ "resultanalysis" + FILE_SEPARATOR + SecurityUtil.getUserName()
			+ FILE_SEPARATOR;

	private AdvancedFileTreeModel fileTreeModel;
	AMedia fileContent;
	private String path;


	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		final Execution execution = Executions.getCurrent();
		String type = (String) execution.getArg().get("Type");
		String username = (String) execution.getArg().get("Username");
		if(type.equalsIgnoreCase("SSSL_Analysis"))
		{
			path = FileComposer.RESULT_ANALYSIS_PATH + "SSSL_Analysis" + FILE_SEPARATOR;
		} else if(type.equalsIgnoreCase("PL_Analysis"))
		{
			path = FileComposer.RESULT_ANALYSIS_PATH + "PL_Analysis" + FILE_SEPARATOR;
		} else if(type.equalsIgnoreCase("IL_Analysis"))
		{
			path = FileComposer.RESULT_ANALYSIS_PATH + "IL_Analysis" + FILE_SEPARATOR;
		}
		fileTreeModel = new AdvancedFileTreeModel(new FileList(type).getRoot());
		//fileTreeModel = new AdvancedFileTreeModel(new FileList().getRoot());
		tree.setItemRenderer(new FileTreeRenderer());
		tree.setModel(fileTreeModel);
	}

	/**
	 * The structure of tree
	 * 
	 * <pre>
	 * &lt;treeitem>
	 *   &lt;treerow>
	 *     &lt;treecell>...&lt;/treecell>
	 *   &lt;/treerow>
	 *   &lt;treechildren>
	 *     &lt;treeitem>...&lt;/treeitem>
	 *   &lt;/treechildren>
	 * &lt;/treeitem>
	 * </pre>
	 */
	private final class FileTreeRenderer implements
			TreeitemRenderer<FileModelTreeNode> {
		@Override
		public void render(final Treeitem treeItem, FileModelTreeNode treeNode,
				int index) throws Exception {
			FileModelTreeNode ctn = treeNode;
			FileModel filename = (FileModel) ctn.getData();
			Treerow dataRow = new Treerow();
			dataRow.setParent(treeItem);
			dataRow.addEventListener(Events.ON_DOUBLE_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							System.out.println("Folder double click");
							FileModelTreeNode clickedNodeValue = (FileModelTreeNode) ((Treeitem)
									event.getTarget().getParent()).getValue();
							FileModel fm = clickedNodeValue.getData();
							FileModel upperFM = clickedNodeValue.getParent().getData();
							if(upperFM != null && isFolder(upperFM))
							{
								if(upperFM.getFoldername().equals("Single_Environment"))
								{
									if(isFolder(fm))
									{
										System.out.println("Browse result of single environment on " + fm.getFoldername());
										addResultViewer(fm.getFoldername(), path + "Single_Environment" +
												FILE_SEPARATOR + clickedNodeValue.getData().getFoldername() + FILE_SEPARATOR);
									}
								} else if(upperFM.getFoldername().equals("Multi_Environment"))
								{
									if(isFolder(fm))
									{
										System.out.println("Browse result of multi environment on " + fm.getFoldername());
										addResultViewer(fm.getFoldername(), path + "Multi_Environment" +
												FILE_SEPARATOR + clickedNodeValue.getData().getFoldername()+ FILE_SEPARATOR);
									}
								} else if(upperFM.getFoldername().equals("Chisq"))
								{
									if(isFolder(fm))
									{
										System.out.println("Browse result of chisq on " + fm.getFoldername());
										addResultViewer(fm.getFoldername(), path + "Chisq" +
												FILE_SEPARATOR + clickedNodeValue.getData().getFoldername() + FILE_SEPARATOR);
									}
								} else if(upperFM.getFoldername().equals("Single_Marker"))
								{
									if(isFolder(fm))
									{
										System.out.println("Browse result of Single marker on " + fm.getFoldername());
										addResultViewer(fm.getFoldername(), path + "Single_Marker" +
												FILE_SEPARATOR + clickedNodeValue.getData().getFoldername() + FILE_SEPARATOR);
									}
								} else if(upperFM.getFoldername().equals("Multi_Marker"))
								{
									if(isFolder(fm))
									{
										System.out.println("Browse result of Multi marker on " + fm.getFoldername());
										addResultViewer(fm.getFoldername(), path + "Multi_Marker" + 
												FILE_SEPARATOR + clickedNodeValue.getData().getFoldername() + FILE_SEPARATOR);
									}
								} else if(upperFM.getFoldername().equals("Single-Site"))
								{
									if(isFolder(fm))
									{
										System.out.println("Browse result of single site on " + fm.getFoldername());
										addResultViewer(fm.getFoldername(), RESULT_ANALYSIS_PATH + "Single-Site" + 
												FILE_SEPARATOR + clickedNodeValue.getData().getFoldername() + FILE_SEPARATOR);
									}
								} else if(upperFM.getFoldername().equals("Multi-Site"))
								{
									if(isFolder(fm))
									{
										System.out.println("Browse result of multi site on  " + fm.getFoldername());
										addResultViewer(fm.getFoldername(), RESULT_ANALYSIS_PATH + "Multi-Site" + 
												FILE_SEPARATOR + clickedNodeValue.getData().getFoldername() + FILE_SEPARATOR);
									}
								}
							}
						}
					});
			treeItem.setValue(ctn);
			treeItem.setOpen(ctn.isOpen());

			if (!isFolder(filename)) { // Contact Row
				if(filename.getFilename().equals(".RData"))
					return;
				if(filename.getFilename().endsWith(".zip"))
					return;
				Hlayout hl = new Hlayout();
				hl.appendChild(new Image("/images/" + filename.getFileicon()));
				hl.appendChild(new Label(filename.getFilename()));
				hl.setSclass("h-inline-block");
				Treecell treeCell = new Treecell();
				treeCell.appendChild(hl);
				dataRow.setDraggable("true");
				dataRow.appendChild(treeCell);
				dataRow.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {
							private File tempFile;
							@Override
							public void onEvent(Event event) throws Exception {
								FileModelTreeNode clickedNodeValue = (FileModelTreeNode) ((Treeitem) event
										.getTarget().getParent()).getValue();
								FileModel f = (FileModel) clickedNodeValue.getParent().getData();
								String rootAnalysisFolder = clickedNodeValue.getParent().getParent().getData()
										.getFoldername();
								String fileFolderName = f.getFoldername();
								String filenamePath = path + rootAnalysisFolder + FILE_SEPARATOR
										+ fileFolderName + FILE_SEPARATOR + clickedNodeValue.getData().getFilename();
								String webFolderPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
								File fileToCreate = new File(webFolderPath + filenamePath);
								Window w = new Window(((FileModel) clickedNodeValue.getData()).getFilename(), "normal", true);
								w.setPosition("parent");
								w.setParent(demoWindow);
								w.setMaximizable(true);
								w.setMinheight(500);
								w.setMinwidth(500);
								w.setPosition("center");

								String dType = filenamePath;
								dType = dType.substring(dType.length() - 3, dType.length());
								
								HashMap<String, String> dataArgs = new HashMap<String, String>();
								dataArgs.put("name", clickedNodeValue.getData().getFilename());

								if (clickedNodeValue.getData().getFilename().contains(".png")) {
									addImageViewer(clickedNodeValue.getData().getFilename(), FILE_SEPARATOR + filenamePath);
								} else if (clickedNodeValue.getData().getFilename().contains(".pdf")) {
									byte[] buffer = new byte[(int) fileToCreate.length()];
									FileInputStream fs = new FileInputStream(fileToCreate);
									fs.read(buffer);
									fs.close();
									ByteArrayInputStream is = new ByteArrayInputStream(buffer);
									fileContent = new AMedia("report", "pdf","application/pdf", is);
									// HashMap<String, AMedia> dataArgsPdf = new
									// HashMap<String, AMedia>();
									// dataArgsPdf.put("pdfFile", fileContent);
									//
									// Executions.createComponents("analysis/pdfviewer.zul",
									// w, dataArgsPdf);
									// w.doOverlapped();
									addPdfViewer(clickedNodeValue.getData().getFilename(), fileContent);
								} else if (clickedNodeValue.getData().getFilename().contains(".txt")) {
									addTxtViewer(clickedNodeValue.getData().getFilename(), filenamePath);
								} else if (clickedNodeValue.getData().getFilename().contains(".csv")) {
									addCsvViewer(clickedNodeValue.getData().getFilename(), filenamePath);
								}else {
									/*
									 * HashMap<String, String> dataArgsTxt2 =
									 * new HashMap<String, String>();
									 * dataArgsTxt2.put("txtFile", "Test");
									 * Executions
									 * .createComponents("testprint.zul", w,
									 * dataArgsTxt2); w.doOverlapped();
									 */
								}
							}
						});
			} else { // Category Row
				dataRow.appendChild(new Treecell(filename.getFoldername()));
			}
			// Both category row and contact row can be item dropped
			dataRow.setDroppable("true");
			dataRow.addEventListener(Events.ON_DROP,
					new EventListener<Event>() {
						@SuppressWarnings("unchecked")
						@Override
						public void onEvent(Event event) throws Exception {
							// The dragged target is a TreeRow belongs to an
							// Treechildren of TreeItem.
							Treeitem draggedItem = (Treeitem) ((DropEvent) event)
									.getDragged().getParent();
							FileModelTreeNode draggedValue = (FileModelTreeNode) draggedItem
									.getValue();
							Treeitem parentItem = treeItem.getParentItem();
							fileTreeModel.remove(draggedValue);
							if (isFolder((FileModel) ((FileModelTreeNode) treeItem
									.getValue()).getData())) {
								fileTreeModel.add(
										(FileModelTreeNode) treeItem.getValue(),
										new DefaultTreeNode[] { draggedValue });
							} else {
								int index = parentItem.getTreechildren()
										.getChildren().indexOf(treeItem);
								if (parentItem.getValue() instanceof FileModelTreeNode) {
									fileTreeModel.insert(
											(FileModelTreeNode) parentItem
													.getValue(),
											index,
											index,
											new DefaultTreeNode[] { draggedValue });
								}

							}
						}
					});

		}

		private boolean isFolder(FileModel filename) {
			return filename.getFilename() == null;
		}
	}

	// @GlobalCommand("addImageViewer")
	@NotifyChange("*")
	public void addImageViewer(String name, String filenamePath) {

		if (viewPanel.getChildren().size() > 0)
			viewPanel.getChildren().get(0).detach();
		Include inc = new Include();
		filenamePath = FILE_SEPARATOR + filenamePath.replaceAll("\\\\", "//");
		inc.setDynamicProperty("FilePath",filenamePath);
		inc.setDynamicProperty("Height", IMAGE_HEIGHT);
		inc.setDynamicProperty("Width", IMAGE_WIDTH);
		inc.setDynamicProperty("Name", name);
		inc.setSrc("/user/analysis/imgviewer.zul");
		inc.setParent(viewPanel);
		viewPanel.appendChild(inc);
	}

	protected void addPdfViewer(String name, AMedia fileContent) {

		if (viewPanel.getChildren().size() > 0)
			viewPanel.getChildren().get(0).detach();
		Include studyInformationPage = new Include();
		studyInformationPage.setDynamicProperty("pdfFile", fileContent);
		studyInformationPage.setSrc("/user/analysis/pdfviewer.zul");
		studyInformationPage.setParent(viewPanel);
		viewPanel.appendChild(studyInformationPage);
	}

	@NotifyChange("*")
	public void addTxtViewer(String name, String filePath) {
		// outputTextViewer
		if (viewPanel.getChildren().size() > 0)
			viewPanel.getChildren().get(0).detach();
		Include inc = new Include();
		inc.setDynamicProperty("Name", name);
		inc.setDynamicProperty("FilePath", filePath);
		inc.setSrc("/user/analysis/txtviewer.zul");
		inc.setParent(viewPanel);
		viewPanel.appendChild(inc);
	}

	private void addCsvViewer(String name, String filePath) {
		if (viewPanel.getChildren().size() > 0)
			viewPanel.getChildren().get(0).detach();
		Include inc = new Include();
		inc.setDynamicProperty("Name", name.replaceAll(".csv", ""));
		inc.setDynamicProperty("FilePath", filePath);
		inc.setSrc("/user/analysis/csvviewer.zul");
		inc.setParent(viewPanel);
		viewPanel.appendChild(inc);
	}

	@NotifyChange("*")
	public void addResultViewer(String name, String filenamePath) {
		String getoutputFolderPath = AnalysisUtils
				.getoutputFolderPath(filenamePath);
		Tabpanel tabPanel = new Tabpanel();
		Tab newTab = new Tab();
		newTab.setLabel(name);
		newTab.setClosable(true);

		Include studyInformationPage = new Include();
		studyInformationPage.setDynamicProperty("OutputFolderPath",
				getoutputFolderPath);
		studyInformationPage.setSrc("/user/analysis/resultviewer.zul");
		studyInformationPage.setParent(tabPanel);

		treeTabPanels.appendChild(tabPanel);
		treeTabs.appendChild(newTab);
	}
}