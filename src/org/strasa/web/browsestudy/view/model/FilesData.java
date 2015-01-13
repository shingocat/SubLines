package org.strasa.web.browsestudy.view.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.strasa.middleware.manager.StudyFileManagerImpl;
import org.strasa.middleware.model.StudyFile;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

public class FilesData {
	private StudyFileManagerImpl studyFileMan;

	private  List<StudyFile> files = new  ArrayList<StudyFile>() ;

	private String dataType="fd"; // genotypic data

	public FilesData() {
		// TODO Auto-generated constructor stub
	}


	public List<StudyFile> getFiles() {
		return files;
	}

	public void setFiles(List<StudyFile> files) {
		this.files = files;
	}

	@Init
	public void init(@ExecutionArgParam("studyid") Integer studyId){

		studyFileMan = new StudyFileManagerImpl();
		List<StudyFile> newFiles = studyFileMan.getFileByStudyIdAndDataType(studyId, "dd");
		add(files,newFiles);
		newFiles = studyFileMan.getFileByStudyIdAndDataType(studyId, "rd");
		add(files,newFiles);
//		newFiles = studyFileMan.getFileByStudyIdAndDataType(studyId, "fd");
//		add(files,newFiles);
	}

	private void add(List<StudyFile> files2, List<StudyFile> newFiles) {
		// TODO Auto-generated method stub
		for(StudyFile sf : newFiles){
			files2.add(sf);
		}
	}


	@GlobalCommand
	public void exportData(@BindingParam("columns")List<String> columns, @BindingParam("rows")List<String[]> rows, @BindingParam("studyName") String studyName, @BindingParam("dataType") String dataType){
//		List<String[]> grid = new ArrayList<String[]>();
//		grid.addAll(rows);
//		grid.add(0,columns.toArray(new String[columns.size()]));
		
		List<String[]> grid = rows;
		grid.add(0,columns.toArray(new String[columns.size()]));
		
		StringBuffer sb = new StringBuffer();

		System.out.println("creating File...");
		for (String[] row : grid) {
			int ctr=0;
			for (String s : row) {
				ctr++;
				sb.append(s);
				if(ctr!=row.length) sb.append(",");
			}
			sb.append("\n");
		}
		System.out.println("downloading File...");
		if(dataType.equals("rd"))    Filedownload.save(sb.toString().getBytes(), "text/plain", studyName+"_rawData.csv");
		else  Filedownload.save(sb.toString().getBytes(), "text/plain", studyName+"_derivedData.csv");
	}

	public String extractFileName(File file, String dataType) {
		String extractedFileName;

		//		if(dataType.equals("dd") || dataType.equals("rd")) extractedFileName=(file.getName().split(".csv")[0])+".csv";
		//		else
		if(dataType.equals("gd")) extractedFileName=(file.getName().split(".txt")[0])+".txt";
		else  extractedFileName=(file.getName().split(".csv")[0])+".csv";
		return extractedFileName;
	}

	@GlobalCommand("downloadFile")
	public void downloadFile(@BindingParam("filePath")String filePath,@BindingParam("dataType") String dataType){
		FileInputStream inputStream;
		try {
			File file = new File(filePath);
			String repSrcPath = Sessions.getCurrent().getWebApp().getRealPath(extractFileName(file,dataType));
			if (file.exists()) {
				inputStream = new FileInputStream(file);
				Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(file),extractFileName(file,dataType));
				System.out.println("Should download from"+repSrcPath);
			}
			else{
				System.out.println("Cannot find file:"+repSrcPath);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getDataType() {
		return dataType;
	}


	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
