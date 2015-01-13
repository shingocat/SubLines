package org.strasa.web.maintenance.view.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.manager.CreateFieldBookManagerImpl;
import org.strasa.middleware.manager.ProgramManagerImpl;
import org.strasa.middleware.manager.ProjectManagerImpl;
import org.strasa.middleware.manager.StudyDerivedDataManagerImpl;
import org.strasa.middleware.manager.StudyManagerImpl;
import org.strasa.middleware.manager.StudyRawDataManagerImpl;
import org.strasa.middleware.model.Study;
import org.strasa.web.createfieldbook.view.model.CreateFieldBookException;
import org.strasa.web.utilities.FileUtilities;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Tabpanel;

public class EditUploadedStudies {

	@Wire("#divMain")
	public Div divMain;

	@Wire("#divUpdateStudy")
	public Div divUpdateStudy;

	@Wire("#btnBackId")
	Button btnBack;
	@Wire("#btnUploadId")
	Button btnUploadNewStudy;

	StudyManagerImpl studyMan;
	StudyRawDataManagerImpl studyRawMan;
	StudyDerivedDataManagerImpl studyDerivedMan;
	ProgramManagerImpl programMan;
	ProjectManagerImpl projectMan;

	public String formatDate(Date date) {
		if (date == null)
			return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	private List<EditStudyModel> editStudyList;

	@Init
	public void init() {

		studyMan = new StudyManagerImpl();
		studyRawMan = new StudyRawDataManagerImpl();
		studyDerivedMan = new StudyDerivedDataManagerImpl();
		programMan = new ProgramManagerImpl();
		projectMan = new ProjectManagerImpl();

		setEditStudyList(new ArrayList<EditStudyModel>());

		populateEditStudyList();
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireComponents(view, this, false);
		divUpdateStudy.setVisible(false);
		btnBack.setVisible(false);
		btnUploadNewStudy.setVisible(true);
	}

	private void populateEditStudyList() {
		// TODO Auto-generated method stub
		setEditStudyList(new ArrayList<EditStudyModel>());
		List<Study> studies = new ArrayList<Study>();
		studies = studyMan.getStudiesByUserID(SecurityUtil.getDbUser().getId());
		int ctr = 0;
		for (Study s : studies) {
			EditStudyModel e = new EditStudyModel();
			e.setIndex(ctr);
			e.setStudy(s);
			e.setPrivacy(false);
			e.setProgram(programMan.getProgramById(s.getProgramid()));
			e.setProject(projectMan.getProjectById(s.getProjectid()));

			editStudyList.add(e);
			ctr++;
		}

	}

	public List<EditStudyModel> getEditStudyList() {

		return editStudyList;
	}

	public void setEditStudyList(List<EditStudyModel> editStudyList) {
		this.editStudyList = editStudyList;
	}

	@NotifyChange("editStudyList")
	@Command
	public void loadMainDiv() {
		divUpdateStudy.setVisible(false);
		divMain.setVisible(true);
		btnBack.setVisible(false);
		btnUploadNewStudy.setVisible(true);
		populateEditStudyList();

	}

	@Command("showzulfile")
	public void showzulfile(@BindingParam("zulFileName") String zulFileName, @BindingParam("target") Tabpanel panel) {
		if (panel != null && panel.getChildren().isEmpty()) {
			Map<String, Integer> arg = new HashMap<String, Integer>();
			arg.put("userId", editStudyList.get(0).getStudy().getUserid());
			Executions.createComponents(zulFileName, panel, arg);

		}
	}

	@GlobalCommand
	@Command
	public void editStudy(@ContextParam(ContextType.VIEW) Component view, @BindingParam("studyID") Integer studyid) {
		divMain.setVisible(false);
		// divUpdateStudy.detach();
		btnBack.setVisible(true);
		btnUploadNewStudy.setVisible(true);
		divUpdateStudy.setVisible(true);
		System.out.println("studyids: " + studyid);
		Map<String, Integer> arg = new HashMap<String, Integer>();
		arg.put("studyID", studyid);
		List<Component> list = Selectors.find(view, "#divUpdateStudyContainer");
		Components.removeAllChildren(list.get(0));
		Executions.createComponents("/user/updatestudy/index.zul", list.get(0), arg);
	}

	@GlobalCommand
	@Command
	public void addStudy(@ContextParam(ContextType.VIEW) Component view) {
		btnBack.setVisible(true);
		divMain.setVisible(false);
		btnUploadNewStudy.setVisible(false);
		// divUpdateStudy.detach();
		divUpdateStudy.setVisible(true);
		Map<?, ?> arg = new HashMap<Object, Object>();
		List<Component> list = Selectors.find(view, "#divUpdateStudyContainer");
		Components.removeAllChildren(list.get(0));
		Executions.createComponents("/user/uploadstudy/index.zul", list.get(0), arg);
	}

	@Command("uploadTemplate")
	public void uploadGenotype(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx, @ContextParam(ContextType.VIEW) Component view) {

		File tempFile = FileUtilities.getFileFromUpload(ctx, view);
		if (tempFile == null)
			return;

		try {
			new CreateFieldBookManagerImpl().populateStudyFromTemplate(tempFile, SecurityUtil.getDbUser().getId(), true, "Sample");
		} catch (CreateFieldBookException e) {
			// TODO Auto-generated catch block
			Messagebox.show(e.getMessage(), "Error in Fieldbook", Messagebox.OK, org.zkoss.zul.Messagebox.EXCLAMATION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@NotifyChange("editStudyList")
	@Command("deleteStudy")
	public void deleteStudy(@BindingParam("studyId") final Integer studyId) {

		Messagebox.show("Are you sure to delete this Study?", "Confirm Dialog", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
			public void onEvent(Event evt) throws InterruptedException {
				if (evt.getName().equals("onOK")) {
					studyMan.deleteStudyById(studyId);
					populateEditStudyList();
					BindUtils.postNotifyChange(null, null, EditUploadedStudies.this, "editStudyList");

				}
			}
		});

	}

	@NotifyChange("*")
	@Command("changeStudyPrivacy")
	public void onCheck(@BindingParam("study") Study study) {
		// System.out.println("id: "+Integer.toString(study.getId()));
		// if(study.getShared()) study.setShared(1);
		System.out.println("shared:" + study.getShared());
		studyMan.updateStudyById(study);
/*		studyRawMan.setPrivacyByStudyId(study.getId(), study.getShared());
		studyDerivedMan.setPrivacyByStudyId(study.getId(), study.getShared());*/
		studyRawMan.setPrivacyByStudyId(study.getId(), study.getShared());
		Messagebox.show("Successfully shared the study", "Info Message", Messagebox.OK, Messagebox.INFORMATION);
	}
}