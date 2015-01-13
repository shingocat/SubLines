/**
 * Builder: Mao Qin
 * Email: mqin@ymail.com
 * Date: 2014 - Jun 25, 2014
 * Project: StrasaWeb
 * Package: org.strasa.web.managegermplasm.view.model.introgressionline
 * Name: Index.java
 */
package org.strasa.web.managegermplasm.view.model.introgressionline;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.input.ReaderInputStream;
import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.manager.GermplasmCharacteristicMananagerImpl;
import org.strasa.middleware.manager.GermplasmManagerImpl;
import org.strasa.middleware.manager.GermplasmTypeManagerImpl;
import org.strasa.middleware.manager.IntrogressionLineManagerImpl;
import org.strasa.middleware.manager.KeyCharacteristicManagerImpl;
import org.strasa.middleware.manager.SegmentManagerImpl;
import org.strasa.middleware.manager.StudyGermplasmManagerImpl;
import org.strasa.middleware.model.Germplasm;
import org.strasa.middleware.model.GermplasmType;
import org.strasa.middleware.model.IntrogressionLine;
import org.strasa.middleware.model.KeyAbiotic;
import org.strasa.middleware.model.KeyBiotic;
import org.strasa.middleware.model.KeyGrainQuality;
import org.strasa.middleware.model.KeyMajorGenes;
import org.strasa.middleware.model.Segment;
import org.strasa.middleware.model.enumeration.Chromosome;
import org.strasa.middleware.model.enumeration.Homogenous;
import org.strasa.web.common.api.Encryptions;
import org.strasa.web.managegermplasm.view.model.ValidateGermplasmCharacteristics;
import org.strasa.web.managegermplasm.view.model.Index.Runtimer;
import org.strasa.web.managegermplasm.view.model.ValidateGermplasmCharacteristics.CharacterEntity;
import org.strasa.web.managegermplasm.view.pojos.GermplasmComparator;
import org.strasa.web.managegermplasm.view.pojos.GermplasmGroupingModel;
import org.strasa.web.managegermplasm.view.pojos.SegmentExt;
import org.strasa.web.uploadstudy.view.pojos.CharacteristicModel;
import org.strasa.web.uploadstudy.view.pojos.GermplasmDeepInfoModel;
import org.strasa.web.uploadstudy.view.pojos.GermplasmExt;
import org.strasa.web.uploadstudy.view.pojos.GermplasmFilter;
import org.strasa.web.uploadstudy.view.pojos.IntrogressionLineExt;
import org.strasa.web.uploadstudy.view.pojos.IntrogressionLineFilter;
import org.strasa.web.utilities.FileUtilities;
import org.strasa.web.utilities.ListBoxValidationUtility;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameMappingStrategy;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import au.com.bytecode.opencsv.bean.MappingStrategy;

import com.mysql.jdbc.StringUtils;

public class Index
{
	@WireVariable
	ConnectionFactory connectionFactory;
	
	@Wire("#tblKnownIntrogressionLine")
	Listbox tblKnownIntrogressionLine;
	
	@Wire("#tblStudyIntrogressionLine")
	Listbox tblStudyIntrogressionLine;
	
	@Wire("#gbUnknownIntrogressionLine")
	Groupbox gbUnknownIntrogressionLine;
	
	@Wire("#gbKnownIntrogressionLine")
	Groupbox gbKnownIntrogressionLine;
	
	@Wire("#divUploadOption")
	Div divUploadOption;
	
	@Wire("#gnameForFilterKnownIntrogressionLine")
	Textbox gnameForFilterKnownIntrogressionLine;
	
	@Wire("#segmentNumberForFilterKnownIntrogressionLine")
	Intbox segmentNumberForFilterKnownIntrogressionLine;
	
	@Wire("#tblSegmentInfo")
	Listbox tblSegmentInfo;
	
	@Wire("#uploadGenotypeData")
	Button uploadGenotypeData;
	
	Component mainView;
	
	private Integer userID = SecurityUtil.getDbUser().getId();
	
	private HashMap<String, IntrogressionLine> lstStudyIntrogressionLine = new HashMap<String, IntrogressionLine>();
	private HashMap<String, IntrogressionLine> lstKnownIntrogressionLine = new HashMap<String, IntrogressionLine>();
	private IntrogressionLine selectedIntrogressionLine = new IntrogressionLine();
	private SegmentExt selectedSegment = new SegmentExt();
	
	private List<String> lstAbiotics;
	private List<String> lstBiotics;
	private List<String> lstGrainQualities;
	private List<String> lstMajorGenes;
	private List<GermplasmType> lstGermplasmType;
	private List<SegmentExt> lstSegments;
	private List<Homogenous> lstHomogenous;
	private List<Chromosome> lstChromosome;
	
	private IntrogressionLineFilter studyIntrogressionLineFilter = new IntrogressionLineFilter();
	private IntrogressionLineFilter knownIntrogressionLineFilter = new IntrogressionLineFilter();
	
	private String totalKnownIntrogressionLine;
	private String totalUnknownIntrogressionLine;
	
	private List<IntrogressionLine> lstInvalidateIntrogressionLine = new ArrayList<IntrogressionLine>();
	
	@Init
	public void init()
	{
		Runtimer timer = new Runtimer();
		timer.start();
		
		GermplasmTypeManagerImpl germMan = new GermplasmTypeManagerImpl();
		lstGermplasmType = germMan.getAllGermplasmType();
		
		KeyCharacteristicManagerImpl keyMan = new KeyCharacteristicManagerImpl();
		lstAbiotics = keyMan.getAllAbioticAsString();
		lstBiotics = keyMan.getAllBioticAsString();
		lstGrainQualities = keyMan.getAllGrainQualityAsString();
		lstMajorGenes = keyMan.getAllMajorGenesAsString();
		
		IntrogressionLineManagerImpl ilManagerImpl = new IntrogressionLineManagerImpl();
		List<IntrogressionLine> lstIntrogressionLine = ilManagerImpl
		        .getIntrogressionLineByUserId(this.getUserID());
		this.setKnownIntrogressionLineFromList(lstIntrogressionLine);
		
		timer.end();
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		
		mainView = view;
		// wire event listener
		// Selectors.wireEventListeners(view, this);
		gbUnknownIntrogressionLine.setVisible(false);
		divUploadOption.setVisible(false);
	}
	
	@NotifyChange(
	{ "lstStudyIntrogressionLine", "lstKnownIntrogressionLine",
	        "totalKnownIntrogressionLine", "totalUnknownIntrogressionLine" })
	@Command("uploadIntrogressionLine")
	public void uploadIntrogressionLine(
	        @ContextParam(ContextType.BIND_CONTEXT) BindContext ctx,
	        @ContextParam(ContextType.VIEW) Component view)
	{
		UploadEvent event = (UploadEvent) ctx.getTriggerEvent();
		String name = event.getMedia().getName();
		if (!name.endsWith(".csv"))
		{
			Messagebox.show("Error: File must be a text-based CSV  format",
			        "Upload Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		((Label) view.getFellow("lblFileName")).setValue(name);
		try
		{
			String filename = name
			        + Encryptions.encryptStringToNumber(name,
			                new Date().getTime());
			File tempGenoFile = File.createTempFile(filename, ".tmp");
			InputStream in = event.getMedia().isBinary() ? event.getMedia()
			        .getStreamData() : new ReaderInputStream(event.getMedia()
			        .getReaderData());
			FileUtilities.uploadFile(tempGenoFile.getAbsolutePath(), in);
			List<IntrogressionLineExt> lstIntrogressionLineExt = CSVToBean(tempGenoFile);
			
			if (this.validateBeanBasicInfo(lstIntrogressionLineExt))
			{
				Messagebox
				        .show("Error: Basic info (including germplasm name, total segment number, chromosome, "
				                + "recurrent parent and donor parent) of introgression line should not be empty! please checking the file first!",
				                "File Content Error", Messagebox.OK,
				                Messagebox.ERROR);
				return;
			}
			
			if (this.validateBeanWithSameGnameWithoutSameSegmentNumber(lstIntrogressionLineExt))
			{
				Messagebox
				        .show("Error: There are records with same name of introgression line but with different segment number, please checking the file first!",
				                "File Content Error", Messagebox.OK,
				                Messagebox.ERROR);
				return;
			}
			
			if (this.validateBeanWithDuplicateRecord(lstIntrogressionLineExt))
			{
				Messagebox
				        .show("Error: There are duplicated records, please checking the file first!",
				                "File Content Error", Messagebox.OK,
				                Messagebox.ERROR);
				return;
			}
			
			if (this.validateBeanWithDuplicateRecordOnOneSegmentNumber(lstIntrogressionLineExt))
			{
				Messagebox
				        .show("Error: There are the same name of introgression line with only one segment which has different segment info, please checking the file first!",
				                "File Content Error", Messagebox.OK,
				                Messagebox.ERROR);
				return;
			}
			
			if (this.validateBeanWithRecordNotSameWithSegmentNumber(lstIntrogressionLineExt))
			{
				Messagebox
				        .show("Error: The number of records is not same as the defined segment number, please checking the file first!",
				                "File Content Error", Messagebox.OK,
				                Messagebox.ERROR);
				return;
			}
			
			List<IntrogressionLine> lstIL = this
			        .beanToIntrogressionLine(lstIntrogressionLineExt);
			// JAN 12, 2015, checking whether return null;
			if(lstIL == null)
			{
				Messagebox
		        .show("Error: There are format or data promlems in the file, please checking the file first!",
		                "File Content Error", Messagebox.OK,
		                Messagebox.ERROR);
				return;
			}
			
			ArrayList<IntrogressionLine> tempKnown = new ArrayList<IntrogressionLine>();
			ArrayList<IntrogressionLine> lstInvalidIntrogressionLine = new ArrayList<IntrogressionLine>();
			
			for (IntrogressionLine il : lstIL)
			{
				
				if (lstKnownIntrogressionLine
				        .containsKey(il.getGermplasmname()))
				{
					IntrogressionLine temp = lstKnownIntrogressionLine.get(il.getGermplasmname());
//					if(il.getId() == null) il.setId(temp.getId());
					if(il.getFemaleparent() == null ) il.setFemaleparent(temp.getFemaleparent());
					if(il.getMaleparent() == null) il.setMaleparent(temp.getMaleparent());
					if(il.getBreeder() == null )il.setBreeder(temp.getBreeder());
					if(il.getGermplasmtypeid() == null) il.setGermplasmtypeid(temp.getGermplasmtypeid());
					
					if (lstKnownIntrogressionLine.get(il.getGermplasmname())
					        .getSegmentNumber() == null || lstKnownIntrogressionLine.get(il.getGermplasmname())
							        .getSegmentNumber() == 0)
					{
						il.setRowIndex(lstStudyIntrogressionLine.size());
						il.setKnown(false);
						lstStudyIntrogressionLine
						        .put(il.getGermplasmname(), il);
					} else
					{
						il.setId(temp.getId());
						il.setRowIndex(tempKnown.size());
						il.setKnown(false);
						tempKnown.add(il);
					}
				} else
				{
					il.setRowIndex(lstStudyIntrogressionLine.size());
					il.setKnown(false);
					lstStudyIntrogressionLine.put(il.getGermplasmname(), il);
				}
				
			}
			
			lstKnownIntrogressionLine.clear();
			for (IntrogressionLine il : tempKnown)
			{
				lstKnownIntrogressionLine.put(il.getGermplasmname(), il);
			}
			
			
			gbUnknownIntrogressionLine.setVisible(true);
			gbKnownIntrogressionLine.setVisible(true);
			view.getFellow("divUploadOption").setVisible(true);
			resetSize();
			if (!lstInvalidIntrogressionLine.isEmpty())
			{
				Map<String, Object> params = new HashMap<String, Object>();
				
				params.put("ilList", lstInvalidIntrogressionLine);
				params.put("parent", view);
				Window popup = (Window) Executions.createComponents(
				        "Undifine yet", view, params);
				popup.doModal();
			}
			
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
		}
		
		if(tblSegmentInfo.getItems().size() > 0)
			tblSegmentInfo.getItems().clear();
		if(selectedSegment != null)
			selectedSegment = null;
		if(selectedIntrogressionLine != null)
			selectedIntrogressionLine = null;
		BindUtils.postNotifyChange(null, null, Index.this, "lstStudyIntrogressionLine");
		BindUtils.postNotifyChange(null, null, Index.this, "lstKnownIntrogressionLine");
		BindUtils.postNotifyChange(null, null, Index.this, "selectedIntrogressionLine");
		BindUtils.postNotifyChange(null, null, Index.this, "selectedSegment");
		uploadGenotypeData.setVisible(false);
	}
	
	@NotifyChange({"lstStudyIntrogressionLine","selectedIntrogressionLine", "selectedSegment"})
	@Command("resetUnknownIntrogressionLine")
	public void resetUnknownIntrogressionLine()
	{
		lstStudyIntrogressionLine.clear();
		gbUnknownIntrogressionLine.setVisible(false);
		gbKnownIntrogressionLine.setVisible(true);
		divUploadOption.setVisible(false);
		uploadGenotypeData.setVisible(true);
		if(selectedIntrogressionLine != null)
			selectedIntrogressionLine = null;
		if(selectedSegment != null)
			selectedSegment = null;
		if(tblSegmentInfo.getItems().size() >0 )
			tblSegmentInfo.getItems().clear();

		init();
		BindUtils.postNotifyChange(null, null, Index.this, "lstStudyIntrogressionLine");
		BindUtils.postNotifyChange(null, null, Index.this, "selectedSegment");
		BindUtils.postNotifyChange(null, null, Index.this, "selectedIntrogressionLine");
		BindUtils.postNotifyChange(null, null, this, "*");
	}
	
	@Command("selectSegment")
	public void selectSegment(@BindingParam("segment") SegmentExt data)
	{
		if (selectedSegment != null)
		{
			if (data.getSegmentId() != null
			        && (data.getSegmentId().equals(selectedSegment
			                .getSegmentId())))
				return;
		}
		selectedSegment = data;
		BindUtils.postNotifyChange(null, null, Index.this, "selectedSegment");
	}
	
	@Command("selectIntrogressionLine")
	public void selectIntrogressionLine(
	        @BindingParam("introgressionLine") IntrogressionLine data)
	{
		if (selectedIntrogressionLine != null)
		{
			if (data.getGermplasmname().equals(
			        selectedIntrogressionLine.getGermplasmname()))
			{
				return;
			}
		}
		selectedIntrogressionLine = data;
		BindUtils.postNotifyChange(null, null, Index.this,
		        "selectedIntrogressionLine");
		if (lstStudyIntrogressionLine.containsKey(data.getGermplasmname()))
		{
			tblStudyIntrogressionLine.getItemAtIndex(data.rowIndex)
			        .setSelected(true);
			Clients.scrollIntoView(tblStudyIntrogressionLine
			        .getItemAtIndex(data.rowIndex));
		} else
		{
			tblKnownIntrogressionLine.getItemAtIndex(data.rowIndex)
			        .setSelected(true);
			Clients.scrollIntoView(tblKnownIntrogressionLine
			        .getItemAtIndex(data.rowIndex));
		}
	}
	
	@Command("saveData")
	public void saveData()
	{
		//validation
		if (validateStudyIntrogressionLine())
		{
			StudyGermplasmManagerImpl sgManagerImpl = new StudyGermplasmManagerImpl();
			IntrogressionLineManagerImpl ilManagerImpl = new IntrogressionLineManagerImpl();
			List<IntrogressionLine> lstStudyIL = new ArrayList<IntrogressionLine>();
			lstStudyIL.addAll(lstStudyIntrogressionLine.values());
			ilManagerImpl.addIntrogressionLineListNoRepeat(lstStudyIL,
			        this.getUserID());
			resetUnknownIntrogressionLine();
			Messagebox.show("Germplasms has been added to the database!",
			        "Information", Messagebox.OK, Messagebox.INFORMATION);
		} else
		{
//			do nothing right now
		}
		
	}
	
	@Command("removeUnknownIntrogressionLine")
	public void removeUnknownIntrogressionLine()
	{
		
	}
	
	@NotifyChange("lstStudyIntrogressionLine")
	@Command("changeFilterStudy")
	public void changeFilterStudy()
	{
		
	}
	
	@NotifyChange(
	{ "lstKnownIntrogressionLine", "totalKnownIntrogressionLine" })
	@Command("changeFilterKnown")
	public void changeFilterKnown()
	{
		IntrogressionLineManagerImpl ilManagerImpl = new IntrogressionLineManagerImpl();
		List<IntrogressionLine> lstIntrogressionLine = ilManagerImpl
		        .getFilterIntrogressionLine(knownIntrogressionLineFilter);
		lstKnownIntrogressionLine.clear();
		if (lstIntrogressionLine == null)
		{
			Messagebox
			        .show("Your quering introgression line is not exist! Return all introgression line!",
			                "Waring", Messagebox.OK, Messagebox.EXCLAMATION);
			List<IntrogressionLine> temp = ilManagerImpl
			        .getIntrogressionLineByUserId(this.getUserID());
			this.setKnownIntrogressionLineFromList(temp);
			this.setTotalKnownIntrogressionLine("Count: "
			        + lstKnownIntrogressionLine.size() + " rows.");
			return;
		}
		this.setKnownIntrogressionLineFromList(lstIntrogressionLine);
		this.setTotalKnownIntrogressionLine("Count: "
		        + lstKnownIntrogressionLine.size() + " rows.");
		
	}
	
	@NotifyChange("selectedIntrogressionLine")
	@Command("modifyIntrogressionLine")
	public void modifyIntrogressionLine(
	        @BindingParam("introgressionLine") IntrogressionLine il)
	{
		lstKnownIntrogressionLine.get(il.getGermplasmname()).setKnown(false);
		selectedIntrogressionLine = lstKnownIntrogressionLine.get(il
		        .getGermplasmname());
		BindUtils.postNotifyChange(null, null, Index.this, "lstKnownIntrogressionLine");
		BindUtils.postNotifyChange(null, null, Index.this, "selectedIntrogressionLine");
	}
	
	@NotifyChange(
	{ "selectedIntrogressionLine", "lstKnownIntrogressionLine" })
	@Command("cancelEdit")
	public void cancelEdit(
	        @BindingParam("introgressionLine") IntrogressionLine il)
	{
		IntrogressionLine introgressionLine = new IntrogressionLineManagerImpl()
		        .getIntrogressionLineById(il.getId());
		introgressionLine.setKnown(true);
		
		lstKnownIntrogressionLine.put(il.getGermplasmname(), introgressionLine);
		selectedIntrogressionLine = lstKnownIntrogressionLine.get(il
		        .getGermplasmname());
//		il = introgressionLine;
//		BindUtils.postNotifyChange(null, null, il, "known");
//		BindUtils.postNotifyChange(null, null, selectedIntrogressionLine, "*");
		
	}
	
	@NotifyChange(
	{ "selectedIntrogressionLine", "lstKnownIntrogressionLine" })
	@Command("saveIntrogressionLine")
	public void saveIntrogressionLine(
	        @BindingParam("introgressionLine") IntrogressionLine il,
	        @BindingParam("listitem") Integer item)
	{
		if (lstKnownIntrogressionLine.containsKey(il.getGermplasmname()))
		{
			new ListBoxValidationUtility(tblSegmentInfo,
			        new ArrayList<Integer>(Arrays.asList(0, 1, 2)))
			        .validateAll();
		} else
		{
			new ListBoxValidationUtility(tblSegmentInfo,
			        new ArrayList<Integer>(Arrays.asList(0, 1, 2)))
			        .validateAll();
		}
		if (validateIntrogressionLine(il))
		{
			new IntrogressionLineManagerImpl().modifyIntrogressionLine(il);
			cancelEdit(il);
		} else
		{
			Messagebox
            .show("Cannot modify segment. The changed value was not validation.",
                    "Validation Error",
                    Messagebox.OK,
                    Messagebox.EXCLAMATION);
		}
	}
	
	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	@NotifyChange(
	{ "lstKnownIntrogressionLine", "totalKnownIntrogressionLine",
	        "selectedIntrogressionLine" })
	@Command("removeIntrogressionLine")
	public void removeIntrogressionLine(
	        @BindingParam("introgressionLine") IntrogressionLine il)
	{
		final IntrogressionLine temp = il;
		final int index = tblKnownIntrogressionLine.getSelectedIndex();
		Messagebox
		        .show("Are you sure that you want to delete? WARNING: Canot be undone",
		                " Confirm Dialog", Messagebox.OK | Messagebox.CANCEL,
		                Messagebox.QUESTION,
		                new org.zkoss.zk.ui.event.EventListener()
		                {
			                
			                @Override
			                public void onEvent(Event evt) throws Exception
			                {
				                if (evt.getName().equals("onOK"))
				                {
					                if (!new StudyGermplasmManagerImpl()
					                        .isGermplasmGrefExist(temp))
					                {
						                lstKnownIntrogressionLine.remove(temp
						                        .getGermplasmname());
						                setKnownIntrogressionLineFromList(new ArrayList<IntrogressionLine>(
						                        lstKnownIntrogressionLine
						                                .values()));
						                setSelectedIntrogressionLine(null);
						                setTotalKnownIntrogressionLine("Count: "
						                        + lstKnownIntrogressionLine
						                                .size() + " rows.");
						                BindUtils.postNotifyChange(null, null,
						                        Index.this,
						                        "lstKnownIntrogressionLine");
						                BindUtils.postNotifyChange(null, null,
						                        Index.this,
						                        "totalKnownIntrogressionLine");
						                BindUtils.postNotifyChange(null, null,
						                        Index.this,
						                        "selectedIntrogressionLine");
						                
						                tblKnownIntrogressionLine
						                        .setSelectedIndex(index);
						                new IntrogressionLineManagerImpl()
						                        .deleteIntrogressionLineById(temp
						                                .getId());
					                } else
					                {
						                Messagebox
						                        .show("Cannot delete germplasm. Studies using this introgression line found.",
						                                "Conflict Error",
						                                Messagebox.OK,
						                                Messagebox.EXCLAMATION);
					                }
				                } else
				                {
					                
				                }
			                }
			                
		                });
		
	}
	
	public void resetSize()
	{
		Clients.resize(mainView.getFellow("tableLayout"));
		Clients.resize(mainView.getFellow("gbUnknownIntrogressionLine"));
		Clients.resize(mainView.getFellow("gbKnownIntrogressionLine"));
		Clients.resize(mainView.getFellow("tblStudyIntrogressionLine"));
		Clients.resize(mainView.getFellow("tblKnownIntrogressionLine"));
	}
	
	public Boolean validateStudyIntrogressionLine()
	{
		int studyIL = 0;
		for (IntrogressionLine il : lstStudyIntrogressionLine.values())
		{
			System.out.println("Validate study introgression Line "
			        + il.toString());
			if (!validateIntrogressionLine(il))
			{
				selectIntrogressionLine(il);
				return false;
			}
		}
		return true;
	}
	
	public Boolean validateIntrogressionLine(IntrogressionLine il)
	{
		String validate = il.validate();
		if (!il.getStyleBG().equals("background-color: #FFF"))
		{
			il.setStyleBG("background-color: #FFF");
			BindUtils.postNotifyChange(null, null, il, "styleBG");
		}
		if (validate != null)
		{
			if (validate
			        .equals("Error: Basic info (Breeder, Female Parent, Male Parent, Germplasm type) of Introgression Line must not be empty in "
			                + il.getGermplasmname()))
			{
				Messagebox
				        .show(validate
				                + ". You should go back to import the basic info of germplasm firstly!",
				                "OK", Messagebox.OK, Messagebox.EXCLAMATION);
				selectIntrogressionLine(il);
				
				return false;
			} else
			{
				Messagebox.show(validate, "OK", Messagebox.OK,
				        Messagebox.EXCLAMATION);
				selectIntrogressionLine(il);
				
				return false;
			}
		}
		return true;
	}
	
	public void setKnownIntrogressionLineFromList(
	        List<IntrogressionLine> lstIntrogressionLine)
	{
		GermplasmCharacteristicMananagerImpl germCharMan = new GermplasmCharacteristicMananagerImpl();
		if(lstIntrogressionLine == null || lstIntrogressionLine.size() == 0)
			return;
		for (int i = 0; i < lstIntrogressionLine.size(); i++)
		{
			lstIntrogressionLine.get(i).setCharacteristicValues(
			        germCharMan
			                .getGermplasmByGermplasmName(lstIntrogressionLine
			                        .get(i).getGermplasmname()));
			lstIntrogressionLine.get(i).setSelectedGermplasmType(
			        getGermplasmTypeById(lstIntrogressionLine.get(i)
			                .getGermplasmtypeid()));
			lstIntrogressionLine.get(i).setKnown(true);
			lstIntrogressionLine.get(i)
			        .setRowIndex(lstIntrogressionLine.size());
			lstKnownIntrogressionLine.put(lstIntrogressionLine.get(i)
			        .getGermplasmname(), lstIntrogressionLine.get(i));
			if (selectedIntrogressionLine == null)
			{
				selectedIntrogressionLine = lstIntrogressionLine.get(0);
			}
		}
	}
	
	public List<IntrogressionLineExt> CSVToBean(File file) throws IOException
	{
		CsvToBean<IntrogressionLineExt> bean = new CsvToBean<IntrogressionLineExt>();
		
		Map<String, String> columnMapping = new HashMap<String, String>();
		columnMapping.put("GNAME", "germplasmname");
		columnMapping.put("TOTAL SEGMENT NUMBER", "totalSegmentNumber");
		columnMapping.put("ISHOMOGENOUS", "isHomogenous");
		columnMapping.put("CHROMOSOME", "chromosome");
		columnMapping.put("RECURRENT PARENT", "recurrentParent");
		columnMapping.put("DONOR PARENT", "donorParent");
		columnMapping.put("GENETIC POSITION 1", "geneticPosition1");
		columnMapping.put("GENETIC POSITION 2", "geneticPosition2");
		columnMapping.put("GENETIC POSITION 3", "geneticPosition3");
		columnMapping.put("GENETIC POSITION 4", "geneticPosition4");
		columnMapping.put("PHYSICAL START", "physicalStart");
		columnMapping.put("PHYSICAL END", "physicalEnd");
		columnMapping.put("GENETIC ESTIMATED LENGTH", "geneticEstimatedLength");
		columnMapping.put("GENETIC MINIMUM LENGTH", "geneticMinimumLength");
		columnMapping.put("GENETIC MAXIMUM LENGTH", "geneticMaximumLength");
		columnMapping.put("SEGMENT DESCRIPTION", "segmentDescription");
		System.out.println(file.getAbsolutePath());
		HeaderColumnNameTranslateMappingStrategy<IntrogressionLineExt> strategy = new HeaderColumnNameTranslateMappingStrategy<IntrogressionLineExt>();
		strategy.setType(IntrogressionLineExt.class);
		strategy.setColumnMapping(columnMapping);
		CSVReader reader = new CSVReader(new FileReader(file));
		
		List<String[]> lstWriter = reader.readAll();
		String[] header = lstWriter.get(0);
		for (int i = 0; i < header.length; i++)
		{
			header[i] = header[i].toUpperCase().trim();
		}
		lstWriter.set(0, header);
		CSVWriter writer = new CSVWriter(new FileWriter(file.getAbsolutePath()));
		
		writer.writeAll(lstWriter);
		writer.close();
		reader = new CSVReader(new FileReader(file));
		
		return bean.parse(strategy, reader);
	}
	
	public boolean validateBeanWithSameGnameWithoutSameSegmentNumber(
	        List<IntrogressionLineExt> lstILExt)
	{
		boolean isExist = false;
		Outer: for (IntrogressionLineExt ilExtOuter : lstILExt)
		{
			for (IntrogressionLineExt ilExtInner : lstILExt)
			{
				if (ilExtOuter.getGermplasmname().equals(
				        ilExtInner.getGermplasmname())
				        && !(ilExtOuter.getTotalSegmentNumber()
				                .equals(ilExtInner.getTotalSegmentNumber())))
				{
					isExist = true;
					break Outer;
				}
			}
		}
		
		return isExist;
	}
	
	public boolean validateBeanWithDuplicateRecord(
	        List<IntrogressionLineExt> lstILExt)
	{
		boolean isExist = false;
		int recordNumber = lstILExt.size();
		Outer: for (int i = 0; i < recordNumber; i++)
		{
			if ((i + 1) >= recordNumber)
				break;
			for (int j = i + 1; j < recordNumber; j++)
			{
				
				if (lstILExt.get(i).equals(lstILExt.get(j)))
				{
					isExist = true;
					break Outer;
				}
			}
		}
		
		return isExist;
	}
	
//	This method check if the segment number is only 1, and then the gname should not be same;
	public boolean validateBeanWithDuplicateRecordOnOneSegmentNumber(
	        List<IntrogressionLineExt> lstILExt)
	{
		boolean isExist = false;
		int recordNumber = lstILExt.size();
		Outer: for (int i = 0; i < recordNumber; i++)
		{
			if (Integer.valueOf(lstILExt.get(i).getTotalSegmentNumber()) != 1)
				continue;
			for (int j = i + 1; j < recordNumber; j++)
			{
				if (Integer.valueOf(lstILExt.get(j).getTotalSegmentNumber()) != 1)
					continue;
				if (lstILExt.get(i).getGermplasmname()
				        .equals(lstILExt.get(j).getGermplasmname()))
				{
					isExist = true;
					break Outer;
				}
			}
		}
		return isExist;
	}
	
	public boolean validateBeanWithRecordNotSameWithSegmentNumber(
	        List<IntrogressionLineExt> lstILExt)
	{
		boolean isExist = false;
		List<IntrogressionLineExt> lstTempILExt = new ArrayList<IntrogressionLineExt>(
		        lstILExt);
		while (!lstTempILExt.isEmpty())
		{
			if (Integer.valueOf(lstTempILExt.get(0).getTotalSegmentNumber()) == 1)
			{
				lstTempILExt.remove(0);
				continue;
			} else
			{
				ArrayList<IntrogressionLineExt> temp = new ArrayList<IntrogressionLineExt>();
				int i = 0;
				temp.add(lstTempILExt.get(i));
				for (int j = i + 1; j < lstTempILExt.size(); j++)
				{
					if (Integer.valueOf(lstTempILExt.get(j)
					        .getTotalSegmentNumber()) == 1)
						continue;
					
					if (lstTempILExt.get(i).getGermplasmname()
					        .equals(lstTempILExt.get(j).getGermplasmname()))
					{
						temp.add(lstTempILExt.get(j));
					}
				}
				if (Integer
				        .valueOf(lstTempILExt.get(i).getTotalSegmentNumber()) != temp
				        .size())
				{
					isExist = true;
					break;
				}
				lstTempILExt.removeAll(temp);
				
			}
		}
//		Outer: for (int i = 0; i < lstILExt.size(); i++)
//		{
//			if (Integer.valueOf(lstILExt.get(i).getTotalSegmentNumber()) == 1)
//			{
//				lstILExt.remove(i);
//				continue Outer;
//			}
//			
//			ArrayList<IntrogressionLineExt> temp = new ArrayList<IntrogressionLineExt>();
//			temp.add(lstILExt.get(i));
//			lstILExt.remove(i);
//			int segmentNumber = Integer.valueOf(lstILExt.get(i).getTotalSegmentNumber());
//			Inner: for (int j = i + 1; j < lstILExt.size(); j++)
//			{
//				if (Integer.valueOf(lstILExt.get(j).getTotalSegmentNumber()) == 1)
//				{
//					lstILExt.remove(j);
//					continue Inner;
//				}
//				if (lstILExt.get(i).getGermplasmname()
//				        .equals(lstILExt.get(j).getGermplasmname()))
//				{
//					temp.add(lstILExt.get(j));
//					lstILExt.remove(j);
//				}
//			}
//			if (segmentNumber != temp
//			        .size())
//			{
//				isExist = true;
//				break;
//			}
//			temp.clear();
//		}
		return isExist;
	}
	
//	This method is used to validate the basic info on input file should not be null;
//	The checking basic field including
//	germplasmname
//	totalsegmentnumber
//	chromosome of segment
//	recurrentparent of segment
//	donorparent of segment 
	public boolean validateBeanBasicInfo(List<IntrogressionLineExt> lstILExt)
	{
		boolean isExist = false;
		if(lstILExt == null || lstILExt.size() == 0)
			return false;
		for (int i = 0; i < lstILExt.size(); i++)
		{
			IntrogressionLineExt ilExt = lstILExt.get(i);
			if(ilExt == null)
				continue;
			if (ilExt.getGermplasmname().trim().equals("")
			        || ilExt.getTotalSegmentNumber().trim().equals("")
			        || ilExt.getChromosome().trim().equals("")
			        || ilExt.getRecurrentParent().trim().equals("")
			        || ilExt.getDonorParent().trim().equals(""))
			{
				isExist = true;
				break;
			}
		}
		return isExist;
	}
	
	public List<IntrogressionLine> beanToIntrogressionLine(
	        List<IntrogressionLineExt> lstILExt)
	{
		if (lstILExt == null || lstILExt.isEmpty())
			return null;
		ArrayList<IntrogressionLineExt> temp = new ArrayList<IntrogressionLineExt>(
		        lstILExt); //it is used to store bean with same gname and segment number;
		ArrayList<IntrogressionLine> lstIL = new ArrayList<IntrogressionLine>();
		while (!temp.isEmpty())
		{
			if (Integer.valueOf(temp.get(0).getTotalSegmentNumber()) == 1)
			{
				IntrogressionLineExt ilExt = temp.get(0);
				IntrogressionLine il = new IntrogressionLine();
				if(ilExt.getGermplasmname() == null || ilExt.getGermplasmname().length() == 0 || ilExt.getGermplasmname().equalsIgnoreCase("UNKNOWN"))
					return null;
				else
					il.setGermplasmname(ilExt.getGermplasmname());
				il.setSegmentNumber(1);
				
				ArrayList<SegmentExt> lstSegment = new ArrayList<SegmentExt>();
				SegmentExt segment = new SegmentExt();
				//modifty by QIN MAO on Jan 9, 2015, if string value is null or unknown and then set this value to NULL
				//if the numeric value is null or unknown and then set this value to -1
				//if is the logical value is null or unknown and then set this to true;
				//default is to set ture
				if(ilExt.getIsHomogenous() == null || ilExt.getIsHomogenous().length() == 0 || ilExt.getIsHomogenous().equalsIgnoreCase("UNKNOWN"))
					segment.setIsHomogenous(Homogenous.YES.toString());
				else
					segment.setIsHomogenous(ilExt.getIsHomogenous().equals("YES") ? Homogenous.YES
				        .toString() : Homogenous.NO.toString());
				segment.setOwner(ilExt.getGermplasmname());
				if(ilExt.getChromosome() == null || ilExt.getChromosome().length() == 0 || ilExt.getChromosome().equalsIgnoreCase("UNKNOWN"))
					segment.setChromosome(-1);
				else
					segment.setChromosome(Integer.valueOf(ilExt.getChromosome()));
				if(ilExt.getRecurrentParent() == null || ilExt.getRecurrentParent().length() == 0 || ilExt.getRecurrentParent().equalsIgnoreCase("UNKNOWN"))
					segment.setRecurrentParent("NULL");
				else
					segment.setRecurrentParent(ilExt.getRecurrentParent());
				if(ilExt.getDonorParent() == null || ilExt.getDonorParent().length() == 0 || ilExt.getDonorParent().equalsIgnoreCase("UNKNOWN"))
					segment.setDonorParent("NULL");
				else
					segment.setDonorParent(ilExt.getDonorParent());
				if(ilExt.getGeneticPosition1() == null || ilExt.getGeneticPosition1().length() == 0 || ilExt.getGeneticPosition1().equalsIgnoreCase("UNKNOWN"))
					segment.setPosition1(BigDecimal.valueOf(-1));
				else
					segment.setPosition1(BigDecimal.valueOf(Double.valueOf(ilExt
				        .getGeneticPosition1())));
				if(ilExt.getGeneticPosition2() == null || ilExt.getGeneticPosition2().length() == 0 || ilExt.getGeneticPosition2().equalsIgnoreCase("UNKNOWN"))
					segment.setPosition2(BigDecimal.valueOf(-1));
				else
					segment.setPosition2(BigDecimal.valueOf(Double.valueOf(ilExt
				        .getGeneticPosition2())));
				if(ilExt.getGeneticPosition3() == null || ilExt.getGeneticPosition3().length() == 0 || ilExt.getGeneticPosition3().equalsIgnoreCase("UNKNOWN"))
					segment.setPosition3(BigDecimal.valueOf(-1));
				else
					segment.setPosition3(BigDecimal.valueOf(Double.valueOf(ilExt
				        .getGeneticPosition3())));
				if(ilExt.getGeneticPosition4() == null || ilExt.getGeneticPosition4().length() == 0 || ilExt.getGeneticPosition4().equalsIgnoreCase("UNKNOWN"))
					segment.setPosition4(BigDecimal.valueOf(-1));
				else
					segment.setPosition4(BigDecimal.valueOf(Double.valueOf(ilExt
				        .getGeneticPosition4())));
				if(ilExt.getPhysicalStart() == null || ilExt.getPhysicalStart().length() == 0 || ilExt.getPhysicalStart().equalsIgnoreCase("UNKNOWN"))
					segment.setPhysicalStart(Integer.valueOf(-1));
				else
					segment.setPhysicalStart(Integer.valueOf(ilExt
				        .getPhysicalStart()));
				if(ilExt.getPhysicalEnd() == null || ilExt.getPhysicalEnd().length() == 0 || ilExt.getPhysicalEnd().equalsIgnoreCase("UNKNOWN"))
					segment.setPhysicalEnd(Integer.valueOf(-1));
				else
					segment.setPhysicalEnd(Integer.valueOf(ilExt.getPhysicalEnd()));
				if(ilExt.getGeneticEstimatedLength() == null || ilExt.getGeneticEstimatedLength().length() == 0 || ilExt.getGeneticEstimatedLength().equalsIgnoreCase("UNKNOWN"))
					segment.setEstimatedLength(BigDecimal.valueOf(-1));
				else
					segment.setEstimatedLength(BigDecimal.valueOf(Double
				        .valueOf(ilExt.getGeneticEstimatedLength())));
				if(ilExt.getGeneticMinimumLength() == null || ilExt.getGeneticMinimumLength().length() == 0 || ilExt.getGeneticMinimumLength().equalsIgnoreCase("UNKNOWN"))
					segment.setMinimumLength(BigDecimal.valueOf(-1));
				else
					segment.setMinimumLength(BigDecimal.valueOf(Double
				        .valueOf(ilExt.getGeneticMinimumLength())));
				if(ilExt.getGeneticMaximumLength() == null || ilExt.getGeneticMaximumLength().length() == 0 || ilExt.getGeneticMaximumLength().equalsIgnoreCase("UNKNOWN"))
					segment.setMaximumLength(BigDecimal.valueOf(-1));
				else
					segment.setMaximumLength(BigDecimal.valueOf(Double
				        .valueOf(ilExt.getGeneticMaximumLength())));
				if(ilExt.getSegmentDescription() == null || ilExt.getSegmentDescription().length() == 0 || ilExt.getSegmentDescription().equalsIgnoreCase("UNKNOWN"))
					segment.setDescription("NULL");
				else
					segment.setDescription(ilExt.getSegmentDescription());
				lstSegment.add(segment);
				il.setSegments(lstSegment);
				lstIL.add(il);
				temp.remove(0);
				System.out.println("After.delete the " + il.getGermplasmname()
				        + " the size of temp is " + temp.size());
				continue;
			} else
			{
				// modify by qin mao on JAN 12, 2015 to check 
				// if string value is null or UNKNOWN, set it to be NULL
				// if numeric value is null or UNKNOWN, set it to be -1
				IntrogressionLineExt ilExt = temp.get(0);
				IntrogressionLine il = new IntrogressionLine();
				if(ilExt.getGermplasmname() == null || ilExt.getGermplasmname().length() == 0 || ilExt.getGermplasmname().equalsIgnoreCase("UNKNOWN"))
					return null;
				else
					il.setGermplasmname(ilExt.getGermplasmname());
				if(ilExt.getTotalSegmentNumber() == null || ilExt.getTotalSegmentNumber().length() == 0 || ilExt.getTotalSegmentNumber().equalsIgnoreCase("UNKNOWN"))
					return null;
				else
					il.setSegmentNumber(Integer.valueOf(ilExt
				        .getTotalSegmentNumber()));
				ArrayList<IntrogressionLineExt> tempILExt = new ArrayList<IntrogressionLineExt>();
				ArrayList<SegmentExt> lstSegment = new ArrayList<SegmentExt>();
				ArrayList<Integer> lstDeleteRecord = new ArrayList<Integer>();
				tempILExt.add(ilExt);
				lstDeleteRecord.add(0);
				
				for (int i = 1; i < temp.size(); i++)
				{
					if (Integer.valueOf(temp.get(i).getTotalSegmentNumber()) == 1)
					{
						continue;
					}
					if (temp.get(i).getGermplasmname()
					        .equals(ilExt.getGermplasmname())
					        && temp.get(i).getTotalSegmentNumber()
					                .equals(ilExt.getTotalSegmentNumber()))
					{
						tempILExt.add(temp.get(i));
						lstDeleteRecord.add(i);
					}
				}
				
				for (IntrogressionLineExt data : tempILExt)
				{
					SegmentExt segment = new SegmentExt();
					if(data.getIsHomogenous() == null || data.getIsHomogenous().length() == 0 || data.getIsHomogenous().equalsIgnoreCase("UNKNOWN"))
						segment.setIsHomogenous(Homogenous.YES.toString());
					else
						segment.setIsHomogenous(data.getIsHomogenous()
					        .equals("YES") ? Homogenous.YES.toString()
					        : Homogenous.NO.toString());
					if(data.getGermplasmname() == null || data.getGermplasmname().length() == 0 || data.getGermplasmname().equalsIgnoreCase("UNKNOWN"))
						return null;
					else
						segment.setOwner(data.getGermplasmname());
					if(data.getChromosome() == null || data.getChromosome().length() == 0 || data.getChromosome().equalsIgnoreCase("UNKNOWN"))
						segment.setChromosome(-1);
					else 
						segment.setChromosome(Integer.valueOf(data.getChromosome()));
					if(data.getRecurrentParent() == null || data.getRecurrentParent().length() == 0 || data.getRecurrentParent().equalsIgnoreCase("UNKNOWN"))
						segment.setRecurrentParent("NULL");
					else
						segment.setRecurrentParent(data.getRecurrentParent());
					if(data.getDonorParent() == null || data.getDonorParent().length() == 0 || data.getDonorParent().equalsIgnoreCase("UNKNOWN"))
						segment.setDonorParent("NULL");
					else
						segment.setDonorParent(data.getDonorParent());
					if(data.getGeneticPosition1() == null || data.getGeneticPosition1().length() == 0 || data.getGeneticPosition1().equalsIgnoreCase("UNKNOWN"))
						segment.setPosition1(BigDecimal.valueOf(-1));
					else
						segment.setPosition1(BigDecimal.valueOf(Double.valueOf(data.getGeneticPosition1())));
					if(data.getGeneticPosition2() == null || data.getGeneticPosition2().length() == 0 || data.getGeneticPosition2().equalsIgnoreCase("UNKNOWN"))
						segment.setPosition2(BigDecimal.valueOf(-1));
					else
						segment.setPosition2(BigDecimal.valueOf(Double.valueOf(data
					        .getGeneticPosition2())));
					if(data.getGeneticPosition3() == null || data.getGeneticPosition3().length() == 0 || data.getGeneticPosition3().equalsIgnoreCase("UNKNOWN"))
						segment.setPosition3(BigDecimal.valueOf(-1));
					else
						segment.setPosition3(BigDecimal.valueOf(Double.valueOf(data
					        .getGeneticPosition3())));
					if(data.getGeneticPosition4() == null || data.getGeneticPosition4().length() == 0 || data.getGeneticPosition4().equalsIgnoreCase("UNKNOWN"))
						segment.setPosition4(BigDecimal.valueOf(-1));
					else
						segment.setPosition4(BigDecimal.valueOf(Double.valueOf(data
					        .getGeneticPosition4())));
					if(data.getPhysicalStart() == null || data.getPhysicalStart().length() == 0 || data.getPhysicalStart().equalsIgnoreCase("UNKNOWN"))
						segment.setPhysicalStart(Integer.valueOf(-1));
					else
						segment.setPhysicalStart(Integer.valueOf(data
					        .getPhysicalStart()));
					if(data.getPhysicalEnd() == null || data.getPhysicalEnd().length() == 0 || data.getPhysicalEnd().equalsIgnoreCase("UNKNOWN"))
						segment.setPhysicalEnd(Integer.valueOf(-1));
					else
						segment.setPhysicalEnd(Integer.valueOf(data
					        .getPhysicalEnd()));
					if(data.getGeneticEstimatedLength() == null || data.getGeneticEstimatedLength().length() == 0 || data.getGeneticEstimatedLength().equalsIgnoreCase("UNKNOWN"))
						segment.setEstimatedLength(BigDecimal.valueOf(-1));
					else
						segment.setEstimatedLength(BigDecimal.valueOf(Double
					        .valueOf(data.getGeneticEstimatedLength())));
					if(data.getGeneticMinimumLength() == null || data.getGeneticMinimumLength().length() == 0 || data.getGeneticMinimumLength().equalsIgnoreCase("UNKNOWN"))
						segment.setMinimumLength(BigDecimal.valueOf(-1));
					else
						segment.setMinimumLength(BigDecimal.valueOf(Double
					        .valueOf(data.getGeneticMinimumLength())));
					if(data.getGeneticMaximumLength() == null || data.getGeneticMaximumLength().length() == 0 || data.getGeneticMaximumLength().equalsIgnoreCase("UNKNOWN"))
						segment.setMaximumLength(BigDecimal.valueOf(-1));
					else
						segment.setMaximumLength(BigDecimal.valueOf(Double
					        .valueOf(data.getGeneticMaximumLength())));
					if(data.getSegmentDescription() == null || data.getSegmentDescription().length() == 0 || data.getSegmentDescription().equalsIgnoreCase("UNKNOWN"))
						segment.setDescription("NULL");
					else
						segment.setDescription(data.getSegmentDescription());
						//segment.setDescription(ilExt.getSegmentDescription());
					lstSegment.add(segment);
				}
				il.setSegments(lstSegment);
				lstIL.add(il);
				Iterator<IntrogressionLineExt> itr = temp.iterator();
				while (itr.hasNext())
				{
					IntrogressionLineExt data = itr.next();
					if (il.getGermplasmname().equals(data.getGermplasmname())
					        && il.getSegmentNumber().toString()
					                .equals(data.getTotalSegmentNumber()))
						itr.remove();
				}
				
				System.out.println("After.delete the " + il.getGermplasmname()
				        + " the size of temp is " + temp.size());
			}
		}
		
		return lstIL;
	}
	
	public ArrayList<IntrogressionLine> getLstStudyIntrogressionLine()
	{
		ArrayList<IntrogressionLine> lsIL = new ArrayList<IntrogressionLine>(
		        lstStudyIntrogressionLine.values());
		ArrayList<IntrogressionLine> returnValue = new ArrayList<IntrogressionLine>();
		for (IntrogressionLine data : lsIL)
		{
			if (studyIntrogressionLineFilter.equals(data))
			{
				returnValue.add(data);
			}
		}
		if(returnValue == null)
			return null;
		return returnValue;
	}
	
	public void setLstStudyIntrogressionLine(
	        HashMap<String, IntrogressionLine> lstStudyIntrogressionLine)
	{
		this.lstStudyIntrogressionLine = lstStudyIntrogressionLine;
	}
	
	public ArrayList<IntrogressionLine> getLstKnownIntrogressionLine()
	{
		ArrayList<IntrogressionLine> lstIL = new ArrayList<IntrogressionLine>(
		        lstKnownIntrogressionLine.values());
//		ArrayList<IntrogressionLine> returnValue = new ArrayList<IntrogressionLine>();
//		for(IntrogressionLine data : lstIL)
//		{
//			if(knownIntrogressionLineFilter.equals(data)){
//				returnValue.add(data);
//			}
//		}
//
//		return returnValue;
		return lstIL;
	}
	
	public void setLstKnownIntrogressionLine(
	        HashMap<String, IntrogressionLine> lstKnownIntrogressionLine)
	{
		this.lstKnownIntrogressionLine = lstKnownIntrogressionLine;
	}
	
	public IntrogressionLine getSelectedIntrogressionLine()
	{
		return selectedIntrogressionLine;
	}
	
	public void setSelectedIntrogressionLine(
	        IntrogressionLine selectedIntrogressionLine)
	{
		this.selectedIntrogressionLine = selectedIntrogressionLine;
	}
	
	public Integer getUserID()
	{
		return userID;
	}
	
	public void setUserID(Integer userID)
	{
		this.userID = userID;
	}
	
	public List<String> getLstAbiotics()
	{
		return lstAbiotics;
	}
	
	public void setLstAbiotics(List<String> lstAbiotics)
	{
		this.lstAbiotics = lstAbiotics;
	}
	
	public List<String> getLstBiotics()
	{
		return lstBiotics;
	}
	
	public void setLstBiotics(List<String> lstBiotics)
	{
		this.lstBiotics = lstBiotics;
	}
	
	public List<String> getLstGrainQualities()
	{
		return lstGrainQualities;
	}
	
	public void setLstGrainQualities(List<String> lstGrainQualities)
	{
		this.lstGrainQualities = lstGrainQualities;
	}
	
	public List<String> getLstMajorGenes()
	{
		return lstMajorGenes;
	}
	
	public void setLstMajorGenes(List<String> lstMajorGenes)
	{
		this.lstMajorGenes = lstMajorGenes;
	}
	
	public List<GermplasmType> getLstGermplasmType()
	{
		return lstGermplasmType;
	}
	
	public void setLstGermplasmType(List<GermplasmType> lstGermplasmType)
	{
		this.lstGermplasmType = lstGermplasmType;
	}
	
	public List<SegmentExt> getLstSegments()
	{
		return lstSegments;
	}
	
	public void setLstSegments(List<SegmentExt> lstSegments)
	{
		this.lstSegments = lstSegments;
	}
	
	public GermplasmType getGermplasmTypeById(Integer id)
	{
		// System.out.println("ID:" + id);
		for (GermplasmType gtype : lstGermplasmType)
		{
			if (gtype.getId() == id)
				return gtype;
		}
		return null;
	}
	
	public void setTotalKnownIntrogressionLine(
	        String totalKnownIntrogressionLine)
	{
		this.totalKnownIntrogressionLine = totalKnownIntrogressionLine;
	}
	
	public void setTotalUnknownIntrogressionLine(
	        String totalUnknownIntrogressionLine)
	{
		this.totalUnknownIntrogressionLine = totalUnknownIntrogressionLine;
	}
	
	public String getTotalKnownIntrogressionLine()
	{
		return "Count: " + lstKnownIntrogressionLine.size() + " rows.";
	}
	
	public String getTotalUnknownIntrogressionLine()
	{
		return "Count: " + lstStudyIntrogressionLine.size() + " rows.";
	}
	
	public IntrogressionLineFilter getStudyIntrogressionLineFilter()
	{
		return studyIntrogressionLineFilter;
	}
	
	public void setStudyIntrogressionLineFilter(
	        IntrogressionLineFilter studyIntrogressionLineFilter)
	{
		this.studyIntrogressionLineFilter = studyIntrogressionLineFilter;
	}
	
	public IntrogressionLineFilter getKnownIntrogressionLineFilter()
	{
		return knownIntrogressionLineFilter;
	}
	
	public void setKnownIntrogressionLineFilter(
	        IntrogressionLineFilter knownIntrogressionLineFilter)
	{
		this.knownIntrogressionLineFilter = knownIntrogressionLineFilter;
	}
	
	public SegmentExt getSelectedSegment()
	{
		return selectedSegment;
	}
	
	public void setSelectedSegment(SegmentExt selectedSegment)
	{
		this.selectedSegment = selectedSegment;
	}
	
	public ArrayList<String> getLstHomogenous()
	{
		Homogenous[] lstHomo = Homogenous.values();
		ArrayList<String> returnValue = new ArrayList<String>();
		for (Homogenous h : lstHomo)
		{
			switch (h)
			{
				case YES:
					returnValue.add(h.toString());
					break;
				case NO:
					returnValue.add(h.toString());
					break;
			}
		}
		return returnValue;
	}
	
	public void setLstHomogenous(List<Homogenous> lstHomogenous)
	{
		this.lstHomogenous = lstHomogenous;
	}
	
	public ArrayList<String> getLstChromosome()
	{
		Chromosome[] lstChr = Chromosome.values();
		ArrayList<String> returnValue = new ArrayList<String>();
		for (Chromosome chr : lstChr)
		{
			switch (chr)
			{
				case Chromosome_1:
					returnValue.add(chr.toString());
					break;
				case Chromosome_2:
					returnValue.add(chr.toString());
					break;
				case Chromosome_3:
					returnValue.add(chr.toString());
					break;
				case Chromosome_4:
					returnValue.add(chr.toString());
					break;
				case Chromosome_5:
					returnValue.add(chr.toString());
					break;
				case Chromosome_6:
					returnValue.add(chr.toString());
					break;
				case Chromosome_7:
					returnValue.add(chr.toString());
					break;
				case Chromosome_8:
					returnValue.add(chr.toString());
					break;
				case Chromosome_9:
					returnValue.add(chr.toString());
					break;
				case Chromosome_10:
					returnValue.add(chr.toString());
					break;
				case Chromosome_11:
					returnValue.add(chr.toString());
					break;
				case Chromosome_12:
					returnValue.add(chr.toString());
					break;
			}
		}
		return returnValue;
	}
	
	public void setLstChromosome(List<Chromosome> lstChromosome)
	{
		this.lstChromosome = lstChromosome;
	}
	
	public class Runtimer
	{
		long startTime = System.nanoTime();
		
		public long start()
		{
			startTime = System.nanoTime();
			return startTime;
		}
		
		public double end()
		{
			long endTime = System.nanoTime();
			// System.out.println("DURATION : " + (endTime - startTime) /
			// 1000000000.0);
			return (endTime - startTime) / 1000000000.0;
		}
	}
}
