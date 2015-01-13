package org.strasa.web.common.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper {

	public Workbook workbook;

	public ExcelHelper() {

		try {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Workbook createExcel(String excelFileName) throws Exception {

		Workbook workbook;
		if (excelFileName.endsWith("xlsx")) {
			workbook = new XSSFWorkbook();
		} else if (excelFileName.endsWith("xls")) {
			workbook = new HSSFWorkbook();
		} else {
			throw new Exception("invalid file name, should be xls or xlsx");
		}

		return workbook;
	}

	public void writeMetaInfo(Sheet sheet, String label, String value, int rowNum) {

		Row row = sheet.createRow(rowNum);
		Cell cellLabel = row.createCell(0);
		cellLabel.setCellStyle(formatCell(IndexedColors.RED.getIndex(), IndexedColors.WHITE.getIndex(), (short) 200, true));
		cellLabel.setCellValue(label);

		Cell cellValue = row.createCell(1);
		cellValue.setCellValue(value);

	}

	public CellStyle formatCell(Short bgcolor, Short fontcolor, Short fontsize, boolean isBold) {

		CellStyle cellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();

		if (bgcolor != null) {
			cellStyle.setFillForegroundColor(bgcolor);
			cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}
		if (fontcolor != null) {
			font.setColor(fontcolor);

		}
		if (fontsize != null) {
			font.setFontHeight(fontsize);
		}

		if (isBold)
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		return cellStyle;

	}

	/**
	 * Write row from list.
	 * 
	 * @param lstRow
	 *            the lst row
	 * @param sheet
	 *            the sheet
	 * @param rowNum
	 *            the row num
	 */
	public void writeRowFromList(List<String> lstRow, Sheet sheet, int rowNum, CellStyle style) {
		Row row = sheet.createRow(rowNum);
		for (int i = 0; i < lstRow.size(); i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(lstRow.get(i));
			if (style != null) {
				cell.setCellStyle(style);
			}

		}

	}

	public void setColumnAutoResize(Sheet sheet) {
		for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells(); i++) {
			sheet.autoSizeColumn(i);
		}
	}

	public void setColumnSize(Sheet sheet, int size) {
		for (int i = 0; i < 20; i++) {
			sheet.setColumnWidth(i, size);
		}
	}

	/**
	 * Append row from list.
	 * 
	 * @param lstRow
	 *            the lst row
	 * @param sheet
	 *            the sheet
	 * @param rowNum
	 *            the row num
	 */
	public void appendRowFromList(List<String> lstRow, Sheet sheet, int rowNum, CellStyle cellStyle) {
		Row row = sheet.getRow(rowNum);

		int totalRows = row.getPhysicalNumberOfCells();

		for (int i = 0; i < lstRow.size(); i++) {
			Cell cell = row.createCell(i + totalRows);
			cell.setCellValue(lstRow.get(i));

			if (cellStyle != null)
				cell.setCellStyle(cellStyle);
		}

	}

	/**
	 * Gets the first common string.
	 * 
	 * @param lst1
	 *            the lst1
	 * @param lst2
	 *            the lst2
	 * @return the first common string
	 */
	public String getFirstCommonString(ArrayList<String> lst1, ArrayList<String> lst2) {
		lst1.retainAll(lst2);
		return lst1.get(0);
	}

	/**
	 * Read excel sheet.
	 * 
	 * @param sheet
	 *            the sheet
	 * @param startToRow
	 *            the start to row
	 * @return the array list
	 * @throws Exception
	 *             the exception
	 */
	public ArrayList<ArrayList<String>> readExcelSheet(Sheet sheet, int startToRow) throws Exception {

		ArrayList<ArrayList<String>> returnVal = new ArrayList<ArrayList<String>>();

		int totalRows = sheet.getLastRowNum();
		int totalColumns = sheet.getRow(0).getPhysicalNumberOfCells();
		for (int i = startToRow; i <= totalRows; i++) {
			ArrayList<String> row = new ArrayList<String>();
			for (int j = 0; j < totalColumns; j++) {
				row.add(getCellValueToString(sheet.getRow(i).getCell(j)));
			}
			returnVal.add(row);
		}

		return returnVal;
	}

	public ArrayList<String[]> readExcelSheetAsArray(Sheet sheet, int startToRow) throws Exception {

		ArrayList<String[]> returnVal = new ArrayList<String[]>();

		int totalRows = sheet.getLastRowNum();
		int totalColumns = sheet.getRow(0).getPhysicalNumberOfCells();
		for (int i = startToRow; i <= totalRows; i++) {
			String[] row = new String[totalColumns];
			for (int j = 0; j < totalColumns; j++) {
				if (sheet.getRow(i).getCell(j) == null) {
//					System.out.println("CELL NULL At row: " + i + " col:" + j);
				}
				row[j] = getCellValueToString(sheet.getRow(i).getCell(j));
			}
			returnVal.add(row);
		}

		return returnVal;
	}

	/**
	 * Gets the header column number.
	 * 
	 * @param header
	 *            the header
	 * @param sheet
	 *            the sheet
	 * @return the header column number
	 * @throws Exception
	 *             the exception
	 */
	public int getHeaderColumnNumber(String header, Sheet sheet) throws Exception {
		int totalColumns = sheet.getRow(0).getPhysicalNumberOfCells();

		for (int i = 0; i < totalColumns; i++) {
			if (sheet.getRow(0).getCell(i).getStringCellValue().toUpperCase().equals(header.toUpperCase()))
				return i;

		}

		throw new Exception("Error: Header not found!");

	}

	/**
	 * Parses the excel sheet to h map.
	 * 
	 * @param sheet
	 *            the sheet
	 * @param index
	 *            the index
	 * @param startToRow
	 *            the start to row
	 * @param removeIndexColumn
	 *            the remove index column
	 * @return the hash map
	 * @throws Exception
	 *             the exception
	 */
	public HashMap<String, ArrayList<String>> parseExcelSheetToHMap(Sheet sheet, int index, int startToRow, boolean removeIndexColumn) throws Exception {

		HashMap<String, ArrayList<String>> returnVal = new HashMap<String, ArrayList<String>>();
		ArrayList<ArrayList<String>> lstSheet = readExcelSheet(sheet, startToRow);

		for (ArrayList<String> arr : lstSheet) {
			returnVal.put(arr.get(index).trim(), arr);
			if (removeIndexColumn) {
				returnVal.get(arr.get(index)).remove(index);
			}
		}

		return returnVal;

	}

	/**
	 * Read particular row in excel sheet.
	 * 
	 * @param sheet
	 *            the sheet
	 * @param particularRow
	 *            the particular row
	 * @return the array list
	 * @throws Exception
	 *             the exception
	 */
	public ArrayList<String> readParticularRowInExcelSheet(Sheet sheet, int particularRow) throws Exception {

		ArrayList<String> returnVal = new ArrayList<String>();

		Iterator<Cell> cellIterator = sheet.getRow(0).cellIterator();
		while (cellIterator.hasNext()) {

			returnVal.add(getCellValueToString(cellIterator.next()));

		}

		return returnVal;
	}

	/**
	 * Gets the cell value to string.
	 * 
	 * @param cell
	 *            the cell
	 * @return the cell value to string
	 * @throws Exception
	 *             the exception
	 */
	public String getCellValueToString(Cell cell) throws Exception {
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());

		case Cell.CELL_TYPE_NUMERIC:
			return String.valueOf((int) cell.getNumericCellValue());

		case Cell.CELL_TYPE_STRING:
			return String.valueOf(cell.getStringCellValue());
		case Cell.CELL_TYPE_BLANK:
			return "";

		}

		throw new Exception("Unknown cell format");
	}

	public ArrayList<ArrayList<String>> readRowsByColumn(Sheet sheet, Integer start, Integer... cols) throws Exception {

		HashMap<Integer, ArrayList<String>> returnVal = new HashMap<Integer, ArrayList<String>>();
		Iterator<Row> rowIterator = sheet.iterator();
		for (Integer col : cols) {
			ArrayList<String> colVal = new ArrayList<String>();
			returnVal.put(col, colVal);
		}

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getRowNum() >= start) {
				for (Integer col : cols) {
					returnVal.get(col).add(getCellValueToString(row.getCell(col)));
				}
			}
		}
		return new ArrayList<ArrayList<String>>(returnVal.values());
	}

	public ArrayList<ArrayList<String>> readRowsByColumn(Sheet sheet, Integer start, Integer rangeStart, Integer rangeEnd) throws Exception {

		HashMap<Integer, ArrayList<String>> returnVal = new HashMap<Integer, ArrayList<String>>();
		Iterator<Row> rowIterator = sheet.iterator();
		for (int col = rangeStart; col < rangeEnd; col++) {
			ArrayList<String> colVal = new ArrayList<String>();
			returnVal.put(col, colVal);
		}

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getRowNum() >= start) {
				for (int col = rangeStart; col < rangeEnd; col++) {
					returnVal.get(col).add(getCellValueToString(row.getCell(col)));
				}
			}
		}
		return new ArrayList<ArrayList<String>>(returnVal.values());
	}

	/**
	 * Gets the excel sheet.
	 * 
	 * @param file
	 *            the file
	 * @param sheetNum
	 *            the sheet num
	 * @return the excel sheet
	 */
	public Sheet getExcelSheet(File file, int sheetNum) {
		try {
			FileInputStream excelFile = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(excelFile);
			return workbook.getSheetAt(sheetNum);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
