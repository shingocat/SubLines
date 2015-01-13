package org.strasa.web.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.input.ReaderInputStream;
import org.strasa.middleware.filesystem.manager.UserFileManager;
import org.strasa.middleware.util.StringConstants;
import org.zkoss.bind.BindContext;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Toolbarbutton;

import au.com.bytecode.opencsv.CSVWriter;

public class FileUtilities {

//	define by QIN MAO
//	it is used to write a List<String[]> data to a specified file
	public static void saveDataToFile(List<String[]> data, String fileName, String filePath)
	{
		String file = filePath + StringConstants.SYSTEM_FILE_SEPARATOR + fileName;
		FileWriter filewriter;
		CSVWriter csvwriter = null;
		
		if(!file.endsWith(".csv"))
			file = file + ".csv";
		try {
			filewriter = new FileWriter(file);
			csvwriter = new CSVWriter(filewriter);
			csvwriter.writeAll(data);
			csvwriter.flush();
			csvwriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	


	public static void uploadFile(String filepath, InputStream file) {
		try {
			OutputStream out = new java.io.FileOutputStream(filepath);
			InputStream in = file;
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			in.close();
			out.flush();
			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static File getFileFromUpload(BindContext ctx, Component view) {

		UploadEvent event = (UploadEvent) ctx.getTriggerEvent();

		// System.out.println(event.getMedia().getStringData());

		String name = event.getMedia().getName();
		File tempFile = null;
		try {
			tempFile = File.createTempFile(name, ".tmp");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// if (!name.endsWith(".csv")) {
		// Messagebox.show("Error: File must be a text-based csv format",
		// "Upload Error", Messagebox.OK, Messagebox.ERROR);
		// return null;
		// }

		InputStream in = event.getMedia().isBinary() ? event.getMedia().getStreamData() : new ReaderInputStream(event.getMedia().getReaderData());
		FileUtilities.uploadFile(tempFile.getAbsolutePath(), in);
		return tempFile;
	}

	public static void exportData(List<String> columns, List<String[]> rows, String outputFileName) {
		List<String[]> grid = rows;

		StringBuffer sb = new StringBuffer();

		System.out.println("creating File...");
		int ctr = 0;
		for (String s : columns) {
			ctr++;
			sb.append(s);
			if (ctr != columns.size())
				sb.append(",");
		}
		sb.append("\n");

		for (String[] row : grid) {
			ctr = 0;
			for (String s : row) {
				ctr++;
				sb.append(s);
				if (ctr != row.length)
					sb.append(",");
			}
			sb.append("\n");
		}

		System.out.println("downloading File...");
		Filedownload.save(sb.toString().getBytes(), "text/plain", outputFileName + ".csv");
	}

	public static void exportGridData(Columns columns, Rows rows, String fileName) {
		// TODO Auto-generated method stub

		StringBuffer sb = new StringBuffer();

		System.out.println("creating File...");
		int ctr = 0;
		for (Component comp : columns.getChildren()) {
			Column c = (Column) comp;
			ctr++;
			sb.append(c.getLabel());
			if (ctr != columns.getChildren().size())
				sb.append(",");
		}
		sb.append("\n");

		for (Component row : rows.getChildren()) {
			ctr = 0;
			Row r = (Row) row;
			for (Component comp : r.getChildren()) {
				Label l;
				StringBuffer miniSb = new StringBuffer();
				try {
					l = (Label) comp;
					ctr++;
					sb.append(l.getValue());
				} catch (ClassCastException npe) {
					Div d = (Div) comp;
					miniSb.append("\"");
					for (Component toolbarButtons : comp.getChildren()) {
						Toolbarbutton tb = (Toolbarbutton) toolbarButtons;
						miniSb.append(tb.getLabel());
						if (!toolbarButtons.equals(comp.getLastChild()))
							miniSb.append(", ");
					}
					miniSb.append("\"");
					sb.append(miniSb.toString());
				}

				if (ctr != r.getChildren().size())
					sb.append(",");
			}
			sb.append("\n");
		}

		System.out.println("downloading File...");
		Filedownload.save(sb.toString().getBytes(), "text/plain", fileName + ".csv");
	}

	public static File createFileFromDatabase(List<String> columns,
			List<String[]> rows, String filePath) {
		// TODO Auto-generated method stub
		List<String[]> grid = rows;

		StringBuffer sb = new StringBuffer();

		System.out.println("creating File...");
		int ctr = 0;
		for (String s : columns) {
			ctr++;
			sb.append(s);
			if (ctr != columns.size())
				sb.append(",");
		}
		sb.append("\n");

		for (String[] row : grid) {
			ctr = 0;
			for (String s : row) {
				ctr++;
				sb.append(s);
				if (ctr != row.length)
					sb.append(",");
			}
			sb.append("\n");
		}

		
		FileWriter writer = null;
		try {
			writer = new FileWriter(filePath);
			writer.write(sb.toString());
			if (writer != null){
				writer.close();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return new File(filePath);
	}

}
