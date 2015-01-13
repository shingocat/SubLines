package org.strasa.web.browsestudy.view.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.strasa.middleware.manager.StudyFileManagerImpl;
import org.strasa.middleware.model.StudyFile;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Filedownload;

public class GenotypicData {
	private StudyFileManagerImpl studyFileMan;

	private String dataType="gd"; // genotypic data
	
	private  List<StudyFile> genotypicFiles;
	
	public GenotypicData() {
		// TODO Auto-generated constructor stub
	}

	@Init
	public void init(@ExecutionArgParam("studyid") Integer studyId){
		studyFileMan = new StudyFileManagerImpl();
		System.out.println("StudyId"+Integer.toString(studyId));
		try {
			setGenotypicFiles(studyFileMan.getFileByStudyIdAndDataType(studyId, dataType));
		}catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<StudyFile> getGenotypicFiles() {
		return genotypicFiles;
	}

	public void setGenotypicFiles(List<StudyFile> genotypicFiles) {
		this.genotypicFiles = genotypicFiles;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
