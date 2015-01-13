/*
 * Data Management and Analysis (DMAS) - International Rice Research Institute 2013-2015
 * 
 *   DMAS is an opensource Data management and statistical analysis mainly for STRASA Project: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *  DMAS is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with DMAS.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * 
 * 
 */
package org.strasa.middleware.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReadStatus;
import net.sf.jxls.reader.XLSReader;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.strasa.middleware.model.Germplasm;
import org.strasa.middleware.model.Study;
import org.strasa.middleware.model.StudyDataSet;
import org.strasa.middleware.model.StudyGermplasm;
import org.strasa.middleware.model.StudyVariable;
import org.strasa.web.common.api.ExcelHelper;
import org.strasa.web.createfieldbook.view.model.CreateFieldBookException;
import org.strasa.web.createfieldbook.view.pojos.CreateFieldBookThread;
import org.strasa.web.createfieldbook.view.pojos.CreateFieldbookTemplateParser;
import org.strasa.web.createfieldbook.view.pojos.ExtSiteInformationModel;
import org.strasa.web.createfieldbook.view.pojos.SiteInformationModel;
import org.strasa.web.uploadstudy.view.pojos.StudySiteInfoModel;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateFieldBookManagerImpl.
 */
public class CreateFieldBookManagerImpl extends ExcelHelper {

	/** The observation sheet. */
	Sheet observationSheet;

	/** The germplasm man. */
	GermplasmManagerImpl germplasmMan = new GermplasmManagerImpl();

	/** The study. */
	Study study;

	/** The output folder. */
	File outputFolder;

	/** The output file. */
	File outputFile;

	CreateFieldBookThread mainThread;

	/** The lst site info. */
	private ArrayList<SiteInformationModel> lstSiteInfo = new ArrayList<SiteInformationModel>();

	/**
	 * Instantiates a new CreateFieldBookManageImpl.
	 * 
	 * @param lstSiteInfo
	 *            the List<SiteInformationModel> that contains all the
	 *            SiteInformation
	 * @param study
	 *            the study
	 * @param outputFolder
	 *            the output folder
	 */
	public CreateFieldBookManagerImpl(ArrayList<SiteInformationModel> lstSiteInfo, Study study, File outputFolder, CreateFieldBookThread thread) {

		this.lstSiteInfo = lstSiteInfo;
		this.study = study;
		this.outputFolder = outputFolder;
		this.mainThread = thread;

	}

	/**
	 * Instantiates a new creates the field book manager impl.
	 */
	public CreateFieldBookManagerImpl() {

	}

	/**
	 * Generate field book.
	 * 
	 * @return the file
	 * @throws CreateFieldBookException
	 *             the create field book exception
	 * @throws Exception
	 *             the exception
	 */
	public void generateFieldBook() throws CreateFieldBookException, Exception {
		// Create excel
		mainThread.onStart();
		try {

			mainThread.updateUI("Creating Workbook...");
			workbook = createExcel(study.getName() + ".xls");

			Sheet descriptionSheet = workbook.createSheet("Description");

			mainThread.updateUI("Generating Metadata information...");
			generateMetaInfo(descriptionSheet);

			mainThread.updateUI("Generating Condition information...");
			generateCondition(descriptionSheet);

			mainThread.updateUI("Generating Factor information...");
			generateFactor(descriptionSheet, lstSiteInfo);
			mainThread.updateUI("Generating Variate information...");
			generateVariate(descriptionSheet, lstSiteInfo);
			setColumnSize(descriptionSheet, 7000);
			observationSheet = workbook.createSheet("Observation");

			mainThread.updateUI("Generating Observation data sheet...");

			for (SiteInformationModel siteInfo : lstSiteInfo) {
				generateSheetFromSite(siteInfo, observationSheet);
			}
			populateVariateHeader(observationSheet, lstSiteInfo);

			setColumnAutoResize(observationSheet);

			Sheet siteInformationSheet = workbook.createSheet("Site Information");

			mainThread.updateUI("Generating Site information...");

			generateSiteInformationSheet(siteInformationSheet, lstSiteInfo);
			setColumnAutoResize(siteInformationSheet);

			Sheet systemInformationSheet = workbook.createSheet("SYSTEM INFORMATION");
			generateSystemInfoSheet(systemInformationSheet);
			workbook.setSheetHidden(3, Workbook.SHEET_STATE_VERY_HIDDEN);

			outputFile = new File(outputFolder.getAbsolutePath() + "/" + study.getName() + ".xls");
			FileOutputStream fos = new FileOutputStream(outputFile);
			mainThread.updateUI("Writing Fieldbook Template to excel...");
			workbook.write(fos);
			fos.close();

			mainThread.onFinish(outputFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CreateFieldBookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Creates the excel.
	 * 
	 * @param sheet
	 *            the sheet
	 * @return the workbook
	 */

	/*
	 * 
	 * META DATA
	 */

	public void generateMetaInfo(Sheet sheet) {
		int rowNum = 0;

		writeMetaInfo(sheet, "STUDY", study.getName(), rowNum++);
		writeMetaInfo(sheet, "TITLE", "", rowNum++);
		writeMetaInfo(sheet, "PMKEY", "", rowNum++);
		writeMetaInfo(sheet, "OBJECTIVE", "", rowNum++);

		writeMetaInfo(sheet, "START DATE", study.getStartyear(), rowNum++);
		writeMetaInfo(sheet, "END DATE", study.getEndyear(), rowNum++);

	}

	/**
	 * Generate system info sheet.
	 * 
	 * @param sheet
	 *            the sheet
	 */
	public void generateSystemInfoSheet(Sheet sheet) {
		writeRowFromList(Arrays.asList(String.valueOf(study.getId())), sheet, 0, formatCell(IndexedColors.WHITE.getIndex(), IndexedColors.WHITE.getIndex(), (short) 1, false));
	}

	/**
	 * Generate variate.
	 * 
	 * @param sheet
	 *            the sheet
	 * @param lstSiteInfo
	 *            the lst site info
	 * @throws CreateFieldBookException
	 *             the create field book exception
	 * @throws Exception
	 *             the exception
	 */
	public void generateVariate(Sheet sheet, List<SiteInformationModel> lstSiteInfo) throws CreateFieldBookException, Exception {

		HashSet<String> lstSet = new HashSet<String>();
		for (SiteInformationModel siteInfo : lstSiteInfo) {
			for (StudyVariable var : siteInfo.lstStudyVariable) {
				lstSet.add(var.getVariablecode());
			}
		}

		List<StudyVariable> lstVar = new StudyVariableManagerImpl().getVariateVariable(Arrays.asList(lstSet.toArray(new String[lstSet.size()])));

		int col = sheet.getLastRowNum() + 2;
		writeRowFromList(new ArrayList<String>(Arrays.asList("VARIATE", "DESCRIPTION", "PROPERTY", "SCALE", "METHOD", "DATATYPE", "       ")), sheet, col++, formatCell(IndexedColors.VIOLET.getIndex(), IndexedColors.WHITE.getIndex(), (short) 200, true));
		for (StudyVariable variable : lstVar) {
			writeRowFromList(new ArrayList<String>(Arrays.asList(variable.getVariablecode(), variable.getDescription(), variable.getProperty(), variable.getScale(), variable.getMethod(), variable.getDatatype(), "    ")), sheet, col++, null);

		}
	}

	/**
	 * Generate factor.
	 * 
	 * @param sheet
	 *            the sheet
	 * @param lstSiteInfo
	 *            the lst site info
	 * @throws CreateFieldBookException
	 *             the create field book exception
	 * @throws Exception
	 *             the exception
	 */
	public void generateFactor(Sheet sheet, List<SiteInformationModel> lstSiteInfo) throws CreateFieldBookException, Exception {

		HashSet<String> lstSet = new HashSet<String>();
		lstSet.add("SITE");
		lstSet.add("LOCATION");
		lstSet.add("YEAR");
		lstSet.add("SEASON");

		for (SiteInformationModel siteInfo : lstSiteInfo) {
			Sheet shGenotype = getExcelSheet(siteInfo.getFileGenotype(), 0);
			Sheet shLayout = getExcelSheet(siteInfo.getFileLayout(), 0);
			lstSet.addAll(readParticularRowInExcelSheet(shGenotype, 0));
			lstSet.addAll(readParticularRowInExcelSheet(shLayout, 0));
		}

		List<StudyVariable> lstVar = new StudyVariableManagerImpl().getFactorVariable(Arrays.asList(lstSet.toArray(new String[lstSet.size()])));

		int col = sheet.getLastRowNum() + 2;
		writeRowFromList(new ArrayList<String>(Arrays.asList("FACTOR", "DESCRIPTION", "PROPERTY", "SCALE", "METHOD", "DATATYPE", "       ")), sheet, col++, formatCell(IndexedColors.GREEN.getIndex(), IndexedColors.WHITE.getIndex(), (short) 200, true));
		for (StudyVariable variable : lstVar) {
			writeRowFromList(new ArrayList<String>(Arrays.asList(variable.getVariablecode(), variable.getDescription(), variable.getProperty(), variable.getScale(), variable.getMethod(), variable.getDatatype(), "    ")), sheet, col++, null);

		}
	}

	/*
	 * CONDITION
	 */

	/**
	 * Generate condition.
	 * 
	 * @param sheet
	 *            the sheet
	 */
	public void generateCondition(Sheet sheet) {

		int col = sheet.getLastRowNum() + 2;
		writeRowFromList(new ArrayList<String>(Arrays.asList("CONDITION", "DESCRIPTION", "PROPERTY", "SCALE", "METHOD", "DATATYPE", "VALUE")), sheet, col++, formatCell(IndexedColors.GREEN.getIndex(), IndexedColors.WHITE.getIndex(), (short) 200, true));
		HashMap<String, StudyVariable> lstVars = new StudyVariableManagerImpl().getConditionVariable(new ArrayList<String>(Arrays.asList("StudyDescription", "StudyProgram", "StudyProject")));

		StudyVariable variable = lstVars.get("StudyDescription");

		writeRowFromList(new ArrayList<String>(Arrays.asList(variable.getVariablecode(), variable.getDescription(), variable.getProperty(), variable.getScale(), variable.getMethod(), variable.getDatatype(), study.getDescription())), sheet, col++, null);

		variable = lstVars.get("StudyProgram");
		writeRowFromList(
				new ArrayList<String>(Arrays.asList(variable.getVariablecode(), variable.getDescription(), variable.getProperty(), variable.getScale(), variable.getMethod(), variable.getDatatype(), new ProgramManagerImpl().getProgramById(study.getProgramid()).getName())), sheet,
				col++, null);
		variable = lstVars.get("StudyProject");
		writeRowFromList(
				new ArrayList<String>(Arrays.asList(variable.getVariablecode(), variable.getDescription(), variable.getProperty(), variable.getScale(), variable.getMethod(), variable.getDatatype(), new ProjectManagerImpl().getProjectById(study.getProjectid()).getName())), sheet,
				col++, null);

	}

	/**
	 * Generate site information sheet.
	 * 
	 * @param sheet
	 *            the sheet
	 * @param lstSiteInfo
	 *            the lst site info
	 */
	public void generateSiteInformationSheet(Sheet sheet, List<SiteInformationModel> lstSiteInfo) {

		ArrayList<ArrayList<String>> lstColumnFields = new ArrayList<ArrayList<String>>();

		lstColumnFields.add(new ArrayList<String>(Arrays.asList("Site Name", "Location ID", "Site Location", "Year", "Season", "Eco System", "Soil Type", "Soil pH", "Planting Type", "Transplanting/Sowing Date", "Harvest Date", "Fertilization", "Density", "Plot Size",
				"Design Structure", "Treatment Stucture", "Design Factor 1", "Design Factor 2", "Design Factor 3", "Design Factor 4")));

		for (SiteInformationModel s : lstSiteInfo) {
			ArrayList<String> ar = new ArrayList<String>();
			ar.add(s.getSitename());
			ar.add(String.valueOf(s.getLocation().getId()));
			ar.add(s.getLocation().getLocationname());
			ar.add(s.getYear());
			ar.add(s.getSeason());
			ar.add(new EcotypeManagerImpl().getEcotypeById(s.getEcotypeid()).getEcotype());
			ar.add(s.getSoiltype());
			ar.add(s.getSoilph());

			ar.add(s.getPlantingtype().getPlanting());
			ar.add(new SimpleDateFormat("MM/dd/yyyy").format(s.getAgronomy().getSowingdate()));
			ar.add(new SimpleDateFormat("MM/dd/yyyy").format(s.getAgronomy().getHarvestdate()));
			ar.add(s.getAgronomy().getFertilization());
			ar.add(s.getAgronomy().getDensity());

			ar.add(s.getDesign().getPlotsize());
			ar.add(s.getDesign().getDesignstructure());
			ar.add(s.getDesign().getTreatmentstructure());
			ar.add(s.getDesign().getDesignfactor1());
			ar.add(s.getDesign().getDesignfactor2());
			ar.add(s.getDesign().getDesignfactor3());
			ar.add(s.getDesign().getDesignfactor4());
			lstColumnFields.add(ar);

		}

		writeRowFromList(lstColumnFields.get(0), sheet, 0, formatCell(IndexedColors.GREEN.getIndex(), IndexedColors.WHITE.getIndex(), (short) 200, true));

		for (int i = 1; i < lstColumnFields.size(); i++) {

			writeRowFromList(lstColumnFields.get(i), sheet, i, null);

		}

		/*
		 * ArrayList<String> lstHeader = new ArrayList<String>(); for
		 * (ArrayList<String> col : lstColumnFields) {
		 * lstHeader.add(col.get(0).toUpperCase()); } int rowCol = 0;
		 * writeRowFromList(lstHeader, sheet, rowCol++,
		 * formatCell(IndexedColors.GREEN.getIndex(),
		 * IndexedColors.WHITE.getIndex(), (short) 200, true));
		 * 
		 * for (int i = 1; i < lstColumnFields.get(0).size(); i++) {
		 * ArrayList<String> newcol = new ArrayList<String>(); for
		 * (ArrayList<String> col : lstColumnFields) { newcol.add(col.get(i)); }
		 * writeRowFromList(newcol, sheet, rowCol++, null);
		 * 
		 * sheet.getRow(i).getCell(0).setCellStyle(formatCell(IndexedColors.GREEN
		 * .getIndex(), IndexedColors.WHITE.getIndex(), (short) 200, true)); }
		 */

	}

	/**
	 * Populate sheet from site.
	 * 
	 * @param siteInfo
	 *            the site info
	 * @param sheet
	 *            the sheet
	 * @throws CreateFieldBookException
	 *             the create field book exception
	 * @throws Exception
	 *             the exception
	 */
	public void generateSheetFromSite(SiteInformationModel siteInfo, Sheet sheet) throws CreateFieldBookException, Exception {
		Sheet shGenotype = getExcelSheet(siteInfo.getFileGenotype(), 0);
		Sheet shLayout = getExcelSheet(siteInfo.getFileLayout(), 0);
		Integer colGenotype;
		Integer colLayout;
		String autoHeaderMatch = null;

		if (siteInfo.isHeaderAutoMatch()) {
			autoHeaderMatch = getFirstCommonString(readParticularRowInExcelSheet(shGenotype, 0), readParticularRowInExcelSheet(shLayout, 0));
			colGenotype = getHeaderColumnNumber(autoHeaderMatch, shGenotype);
			colLayout = getHeaderColumnNumber(autoHeaderMatch, shLayout);

		} else {
			colGenotype = getHeaderColumnNumber(siteInfo.getHeaderGenotype(), shGenotype);
			colLayout = getHeaderColumnNumber(siteInfo.getHeaderLayout(), shGenotype);
		}

		// Write the header column for Observation Header if its blank or has
		// not been created yet
		if (sheet.getRow(0) == null) {
			ArrayList<String> lstGenotype = readParticularRowInExcelSheet(shGenotype, 0);
			ArrayList<String> lstLayout = readParticularRowInExcelSheet(shLayout, 0);
			if (autoHeaderMatch != null) {
				lstGenotype.remove(autoHeaderMatch);
				lstLayout.remove(autoHeaderMatch);
			} else {
				lstGenotype.remove(colGenotype);
				lstLayout.remove(colLayout);
			}
			lstGenotype.add("SITE");
			lstGenotype.add("LOCATION");
			lstGenotype.add("YEAR");
			lstGenotype.add("SEASON");
			lstGenotype.addAll(lstLayout);
			writeRowFromList(lstGenotype, sheet, 0, formatCell(IndexedColors.GREEN.getIndex(), HSSFColor.WHITE.index, (short) 210, true));
		}

		// Generate the observation sheet;
		HashMap<String, ArrayList<String>> hmGenotype = parseExcelSheetToHMap(shGenotype, colGenotype, 1, true);

		ArrayList<ArrayList<String>> hmLayout = readExcelSheet(shLayout, 1);
		// printMap(hmGenotype);
		for (ArrayList<String> lstLayout : hmLayout) {

			String key = lstLayout.get(colLayout).trim();
			lstLayout.remove((int) colLayout);
			if (!hmGenotype.containsKey(key.trim()))
				throw new CreateFieldBookException("Key '" + key + "' not found in genotype sheet");

			ArrayList<String> lstNewRow = new ArrayList<String>(hmGenotype.get(key));

			lstNewRow.add(siteInfo.getSitename());
			lstNewRow.add(siteInfo.getLocation().getLocationname());
			lstNewRow.add(siteInfo.getYear());
			lstNewRow.add(siteInfo.getSeason());

			lstNewRow.addAll(lstLayout);
			writeRowFromList(lstNewRow, sheet, sheet.getLastRowNum() + 1, null);
		}

	}

	/**
	 * Validate germplasm from genotype.
	 * 
	 * @param genotype
	 *            the genotype
	 * @throws Exception
	 *             the exception
	 */
	public void validateGermplasmFromGenotype(File genotype) throws Exception {
		Sheet shGenotype = getExcelSheet(genotype, 0);

		int colGenotype = getHeaderColumnNumber("GName", shGenotype);
		ArrayList<String> lstGenotype = readRowsByColumn(shGenotype, 1, colGenotype).get(0);
		GermplasmManagerImpl germMan = new GermplasmManagerImpl();
		HashMap<String, Germplasm> lstGerm = germMan.getGermplasmBatchAsMap(lstGenotype);
		ArrayList<String> lstErrors = new ArrayList<String>();
		for (String key : lstGerm.keySet()) {
			if (!lstGenotype.contains(key))
				lstErrors.add(key);
		}
		if (!lstErrors.isEmpty())
			throw new CreateFieldBookException("Unknown germplasm detected", lstErrors);

	}

	/**
	 * Populate variate header.
	 * 
	 * @param sheet
	 *            the sheet
	 * @param lstSiteInfo
	 *            the lst site info
	 */
	public void populateVariateHeader(Sheet sheet, List<SiteInformationModel> lstSiteInfo) {
		ArrayList<String> variateHeader = new ArrayList<String>();
		HashSet<String> noDup = new HashSet<String>();
		for (SiteInformationModel siteInfo : lstSiteInfo) {
			for (StudyVariable variate : siteInfo.lstStudyVariable) {
				if (noDup.add(variate.getVariablecode()))
					variateHeader.add(variate.getVariablecode());

			}
		}
		appendRowFromList(variateHeader, sheet, 0, formatCell(IndexedColors.BLUE.getIndex(), HSSFColor.WHITE.index, (short) 210, true));

	}

	/**
	 * Gets the study from template.
	 * 
	 * @param template
	 *            the template
	 * @return the study from template
	 * @throws CreateFieldBookException
	 *             the create field book exception
	 */
	public Study getStudyFromTemplate(File template) throws CreateFieldBookException {
		Sheet systemInfoSheet = getExcelSheet(template, 3);
		String strStudyId = systemInfoSheet.getRow(0).getCell(0).getStringCellValue();
		if (strStudyId == null)
			throw new CreateFieldBookException("Invalid Fieldbook template.");
		return new StudyManagerImpl().getStudyById(Integer.valueOf(strStudyId));
	}

	/**
	 * Populate study from a fieldbook template file.
	 * 
	 * @param template
	 *            - File of the template
	 * @param userID
	 *            the user id - the current UserID
	 * @param isRaw
	 *            - if the data is raw;
	 * @param datasetname
	 *            the datasetname - name of the dataset
	 * @throws CreateFieldBookException
	 *             the create field book exception - use a messagebox to promp
	 *             the error
	 * @throws Exception
	 *             the exception
	 */
	public void populateStudyFromTemplate(File template, Integer userID, boolean isRaw, String datasetname) throws CreateFieldBookException, Exception {
		Sheet observationSheet = getExcelSheet(template, 1);
		Sheet siteInfoSheet = getExcelSheet(template, 2);
		Sheet systemInfoSheet = getExcelSheet(template, 3);

		validateGermplasm(observationSheet);
		validateSite(observationSheet, siteInfoSheet);

		Integer studyId = Integer.valueOf(systemInfoSheet.getRow(0).getCell(0).getStringCellValue());
		Study study = new StudyManagerImpl().getStudyById(studyId);
		StudyDataSet dataSet = new StudyDataSet();
		dataSet.setDatatype((isRaw) ? "rd" : "dd");
		dataSet.setStudyid(study.getId());
		dataSet.setTitle(datasetname);
		System.out.println("Processing Observation...");
		populateObservation(study, observationSheet, dataSet, userID, isRaw);

		System.out.println("Processing Site...");
		populateSiteInformationSheet(template, siteInfoSheet, study, dataSet.getId());
		System.out.println("Processing Germplasm...");
		populateGermplasm(studyId, dataSet.getId(), isRaw);
	}

	/**
	 * Validate germplasm.
	 * 
	 * @param sheet
	 *            the sheet
	 * @throws Exception
	 *             the exception
	 */
	public void validateGermplasm(Sheet sheet) throws Exception {

		Integer colNum = getHeaderColumnNumber("Gname", sheet);

		ArrayList<String> lstGermplasm = readRowsByColumn(sheet, 1, colNum).get(0);

		HashSet<String> uniqueGerm = new HashSet<String>();
		uniqueGerm.addAll(lstGermplasm);
		ArrayList<String> lstUnknownGerm = new ArrayList<String>();
		lstUnknownGerm.addAll(uniqueGerm);
		lstUnknownGerm.removeAll(new GermplasmManagerImpl().getGermplasmBatchAsString(new ArrayList<String>(uniqueGerm)));

		// System.out.println("GEMR: " + lstGermplasm.get(0));
		if (!lstUnknownGerm.isEmpty()) {
			System.out.println("Error: Unknown Germplasm detected {" + StringUtils.join(lstUnknownGerm.toArray(new String[lstUnknownGerm.size()]), ", "));

			throw new CreateFieldBookException("Error: Unknown Germplasm detected {" + StringUtils.join(lstUnknownGerm.toArray(new String[lstUnknownGerm.size()]), ", ") + "}");
		}
	}

	/**
	 * Validate site.
	 * 
	 * @param shObservation
	 *            the sh observation
	 * @param shSiteInfo
	 *            the sh site info
	 * @throws Exception
	 *             the exception
	 */
	public void validateSite(Sheet shObservation, Sheet shSiteInfo) throws Exception {

		Integer colSite = getHeaderColumnNumber("Site", shObservation);
		HashSet<String> uniqueSite = new HashSet<String>();
		uniqueSite.addAll(readRowsByColumn(shObservation, 1, colSite).get(0));
		System.out.println(readRowsByColumn(shObservation, 1, colSite).get(0).get(0));
		ArrayList<String> lstUnknownSite = readRowsByColumn(shSiteInfo, 1, 0).get(0);
		if (lstUnknownSite.size() > uniqueSite.size()) {

			lstUnknownSite.removeAll(uniqueSite);
			throw new CreateFieldBookException("Invalid list of sites detected. Could not find {" + StringUtils.join(lstUnknownSite.toArray(new String[lstUnknownSite.size()]), ",") + "} in Observation sheet.");
		}
		if (uniqueSite.size() > lstUnknownSite.size()) {
			uniqueSite.removeAll(lstUnknownSite);
			throw new CreateFieldBookException("Invalid list of sites detected. Could not find {" + StringUtils.join(uniqueSite.toArray(new String[uniqueSite.size()]), ",") + "} in Site Information sheet.");

		}
		lstUnknownSite.removeAll(uniqueSite);
		if (!lstUnknownSite.isEmpty()) {
			throw new CreateFieldBookException("Invalid list of sites detected. Could not find {" + StringUtils.join(lstUnknownSite.toArray(new String[lstUnknownSite.size()]), ",") + "} in Site Information sheet.");

		}

	}

	/**
	 * Populate observation.
	 * 
	 * @param study
	 *            the study
	 * @param observation
	 *            the observation
	 * @param dataSet
	 *            the data set
	 * @param userID
	 *            the user id
	 * @param isRaw
	 *            the is raw
	 * @throws CreateFieldBookException
	 *             the create field book exception
	 * @throws Exception
	 *             the exception
	 */
	public void populateObservation(Study study, Sheet observation, StudyDataSet dataSet, Integer userID, boolean isRaw) throws CreateFieldBookException, Exception {
		ArrayList<String[]> lstData = readExcelSheetAsArray(observation, 0);
		String[] header = lstData.get(0);
		lstData.remove((int) 0);

		new StudyDataSetManagerImpl().addDataSet(dataSet);
		new StudyRawDataManagerImpl().addStudyRawData(study, header, lstData, dataSet.getId(), isRaw, userID);

	}

	/**
	 * Populate site information sheet.
	 * 
	 * @param excelFile
	 *            the excel file
	 * @param informationSheet
	 *            the information sheet
	 * @param study
	 *            the study
	 * @param datasetID
	 *            the dataset id
	 * @throws Exception
	 *             the exception
	 */
	public void populateSiteInformationSheet(File excelFile, Sheet informationSheet, Study study, Integer datasetID) throws Exception {
		ArrayList<StudySiteInfoModel> lstSites = new ArrayList<StudySiteInfoModel>();

		LocationManagerImpl locMan = new LocationManagerImpl();
		EcotypeManagerImpl ecoMan = new EcotypeManagerImpl();
		PlantingTypeManagerImpl plantMan = new PlantingTypeManagerImpl();

		InputStream inputXML = Resources.getResourceAsStream("CreateFieldBookConfig.xml");
		XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML);
		InputStream inputXLS = new FileInputStream(excelFile.getAbsoluteFile());

		CreateFieldbookTemplateParser templateParser = new CreateFieldbookTemplateParser();

		Map beans = new HashMap();
		beans.put("fbparser", templateParser);
		XLSReadStatus readStatus = mainReader.read(inputXLS, beans);
		templateParser.getSites().remove(0);
		for (ExtSiteInformationModel site : templateParser.getSites()) {

			site.setStudyid(study.getId());
			site.setDataset(datasetID);

			site.setSelectedLocation(locMan.getLocationById(Integer.valueOf(site.getStr_locationid())));
			site.setEcotypeid(ecoMan.getEcotypeByName(site.getEcoSystem()).getId());
			site.setSelectedSitePlantingType(plantMan.getPlantingTypeByName(site.getSelectedSitePlantingType().getPlanting()));
			site.getSelectedAgroInfo().setSowingdate(new SimpleDateFormat("MM/dd/yyyy").parse(site.getStr_sowingdate()));
			site.getSelectedAgroInfo().setHarvestdate(new SimpleDateFormat("MM/dd/yyyy").parse(site.getStr_harvestdate()));

			lstSites.add(site);
		}

		new StudySiteManagerImpl().addSiteBatch(lstSites);
	}

	/**
	 * Populate germplasm.
	 * 
	 * @param studyid
	 *            the studyid
	 * @param dataset
	 *            the dataset
	 * @param isRaw
	 *            the is raw
	 */
	public void populateGermplasm(Integer studyid, Integer dataset, boolean isRaw) {
		StudyRawDataManagerImpl rawMan = new StudyRawDataManagerImpl(isRaw);
		List<Germplasm> lst = rawMan.getStudyGermplasmInfo(studyid, dataset);
		StudyGermplasmManagerImpl studyGermMan = new StudyGermplasmManagerImpl();
		ArrayList<StudyGermplasm> lstStudyGermplasms = new ArrayList<StudyGermplasm>();
		GermplasmManagerImpl germMan = new GermplasmManagerImpl();
		for (Germplasm germ : lst) {
			if (germMan.isGermplasmExisting(germ.getGermplasmname())) {

				StudyGermplasm studyGerm = new StudyGermplasm();
				studyGerm.setDataset(dataset);
				studyGerm.setStudyid(studyid);
				studyGerm.setGref(germMan.getGermplasmByName(germ.getGermplasmname()).getId());

				lstStudyGermplasms.add(studyGerm);
			}
		}
		studyGermMan.addStudyGermplasm(lstStudyGermplasms);
	}

}
