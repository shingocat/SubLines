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
package org.strasa.web.updatestudy.view;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.input.ReaderInputStream;
import org.strasa.middleware.filesystem.manager.UserFileManager;
import org.strasa.middleware.manager.StudyFileManagerImpl;
import org.strasa.middleware.model.StudyFile;
import org.strasa.web.common.api.Encryptions;
import org.strasa.web.common.api.ProcessTabViewModel;
import org.strasa.web.uploadstudy.view.pojos.GenotypeFileModel;
import org.strasa.web.utilities.FileUtilities;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Messagebox;

public class GenotypicData {
	private StudyFileManagerImpl studyFileMan;

	private String dataType="gd"; // genotypic data
	Integer studyid;
	private  List<StudyFile> genotypicFiles;
	
	public GenotypicData() {
		// TODO Auto-generated constructor stub
	}

	@Init
	public void init(@ExecutionArgParam("uploadModel") ProcessTabViewModel model){
		studyFileMan = new StudyFileManagerImpl();
		this.studyid = model.studyID;
		System.out.println("StudyId"+Integer.toString( model.studyID));
		try {
			setGenotypicFiles(studyFileMan.getFileByStudyIdAndDataType( model.studyID, dataType));
		}catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@NotifyChange("*")
	@Command("uploadGenotypeData")
	public void uploadGenotypeData(
			@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx,
			@ContextParam(ContextType.VIEW) Component view) {

		UploadEvent event = (UploadEvent) ctx.getTriggerEvent();

		// System.out.println(event.getMedia().getStringData());

		String name = event.getMedia().getName();
		if (!name.endsWith(".txt")) {
			Messagebox.show("Error: File must be a text-based  format",
					"Upload Error", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		try {
			String filename = name
					+ Encryptions.encryptStringToNumber(name,
							new Date().getTime());
			File tempGenoFile = File.createTempFile(filename, ".tmp");
			InputStream in = event.getMedia().isBinary() ? event.getMedia()
					.getStreamData() : new ReaderInputStream(event.getMedia()
					.getReaderData());
			FileUtilities.uploadFile(tempGenoFile.getAbsolutePath(), in);

			GenotypeFileModel newGenotypeFile = new GenotypeFileModel(name,
					tempGenoFile);
			UserFileManager fileMan = new UserFileManager();
			fileMan.createNewFileFromUpload(1, studyid, newGenotypeFile.getName(),
					newGenotypeFile.getFilepath(), "gd");
			setGenotypicFiles(studyFileMan.getFileByStudyIdAndDataType(studyid, dataType));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	@NotifyChange("*")
	@Command
	public void removeGenotypeFile(@BindingParam("model") StudyFile model){
		
		new StudyFileManagerImpl().deleteRecord(model);
		setGenotypicFiles(studyFileMan.getFileByStudyIdAndDataType(studyid, dataType));
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
