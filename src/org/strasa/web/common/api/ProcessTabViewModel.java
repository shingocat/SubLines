package org.strasa.web.common.api;

import org.spring.security.model.SecurityUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.strasa.middleware.model.StudyDataSet;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;

@Service
@Scope("prototype")
public class ProcessTabViewModel {
	public boolean isRaw = true;
	public int studyID;
	private Component mainView;
	public boolean uploadToFolder = false;
	public StudyDataSet dataset = new StudyDataSet();
	public boolean isUpdateMode = false;
	public int userID = SecurityUtil.getDbUser().getId();
	public double processTabID;
	private boolean isUploadMode = false;
	public boolean isMergeMode = false;
	public boolean reloadNext = false;

	public boolean isUploadMode() {
		return isUploadMode;
	}

	public void setUploadMode(boolean isUploadMode) {
		this.isUploadMode = isUploadMode;
	}

	public boolean isRaw() {
		return isRaw;
	}

	public boolean isDataReUploaded = false;
	public Tab mainTab;
	public Tabpanel mainTabPanel;

	public boolean isDataReUploaded() {
		return isDataReUploaded;
	}

	public void setDataReUploaded(boolean isDataReUploaded) {
		this.isDataReUploaded = isDataReUploaded;
	}

	public int getUserID() {
		return userID;
	}

	@Override
	public String toString() {
		return "ProcessTabViewModel [isRaw=" + isRaw + ", studyID=" + studyID + ", mainView=" + mainView + ", uploadToFolder=" + uploadToFolder + ", dataset=" + dataset + ", isUpdateMode=" + isUpdateMode + ", userID=" + userID + ", isDataReUploaded=" + isDataReUploaded + "]";
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public void initValues(ProcessTabViewModel uploadModel) {
		if (uploadModel == null) {
			System.out.println("NULL");
			return;
		}
		this.isMergeMode = uploadModel.isMergeMode;
		this.processTabID = uploadModel.processTabID;
		isRaw = uploadModel.isRaw;
		uploadToFolder = uploadModel.uploadToFolder;
		setStudyID(uploadModel.getStudyID());
		setUpdateMode(uploadModel.isUpdateMode);
		this.userID = uploadModel.userID;
		this.isDataReUploaded = uploadModel.isDataReUploaded;
		this.setDataset(uploadModel.getDataset());
		this.mainTab = uploadModel.mainTab;
		this.isUploadMode = uploadModel.isUploadMode;
		if (this.isMergeMode)
			this.getDataset().setId(null);
	}

	public boolean isUpdateMode() {
		return isUpdateMode;
	}

	public void setUpdateMode(boolean isUpdateMode) {
		this.isUpdateMode = isUpdateMode;
	}

	public void setRaw(boolean isRaw) {
		this.isRaw = isRaw;
	}

	public boolean isUploadToFolder() {
		return uploadToFolder;
	}

	public void setUploadToFolder(boolean uploadToFolder) {
		this.uploadToFolder = uploadToFolder;
	}

	public StudyDataSet getDataset() {
		return dataset;
	}

	public void setDataset(StudyDataSet dataset) {
		this.dataset = dataset;
	}

	public void initSpecial() {

	}

	public Component getMainView() {
		return mainView;
	}

	public void setMainView(Component mainView) {
		this.mainView = mainView;
	}

	public boolean validateTab() {
		return false;
	}

	public int getStudyID() {
		return studyID;
	}

	public void setStudyID(int studyID) {
		this.studyID = studyID;
	}
}
