package org.strasa.middleware.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.EcotypeMapper;
import org.strasa.middleware.mapper.LocationMapper;
import org.strasa.middleware.mapper.PlantingTypeMapper;
import org.strasa.middleware.mapper.StudyAgronomyMapper;
import org.strasa.middleware.mapper.StudyDerivedDataMapper;
import org.strasa.middleware.mapper.StudyDesignMapper;
import org.strasa.middleware.mapper.StudyLocationMapper;
import org.strasa.middleware.mapper.StudyRawDataMapper;
import org.strasa.middleware.mapper.StudySiteMapper;
import org.strasa.middleware.model.StudyAgronomy;
import org.strasa.middleware.model.StudyAgronomyExample;
import org.strasa.middleware.model.StudyDerivedDataExample;
import org.strasa.middleware.model.StudyDesignExample;
import org.strasa.middleware.model.StudyLocation;
import org.strasa.middleware.model.StudyRawDataByDataColumn;
import org.strasa.middleware.model.StudyRawDataExample;
import org.strasa.middleware.model.StudySite;
import org.strasa.middleware.model.StudySiteExample;
import org.strasa.web.uploadstudy.view.pojos.StudySiteInfoModel;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class StudySiteManagerImpl {

	private boolean isRaw = true;
	@WireVariable
	ConnectionFactory connectionFactory;

	public StudySiteManagerImpl(boolean isRaw) {
		this.isRaw = isRaw;
	}

	public StudySiteManagerImpl() {
		// TODO Auto-generated constructor stub
	}

	public void addStudySite(StudySite record) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudySiteMapper studySiteMapper = session.getMapper(StudySiteMapper.class);

		try {
			System.out.println("Inserting!");
			studySiteMapper.insert(record);
			session.commit();

		} finally {
			session.close();
		}

	}

	public boolean hasSiteHeader(int studyID, Integer dataset) {

		if (isRaw) {
			System.out.println("Checking for Raw...");
			StudyRawDataMapper mapper = connectionFactory.sqlSessionFactory.openSession().getMapper(StudyRawDataMapper.class);

			StudyRawDataExample example = new StudyRawDataExample();
			if (dataset != null)
				example.createCriteria().andStudyidEqualTo(studyID).andDatasetEqualTo(dataset).andDatacolumnEqualTo("Site");
			else
				example.createCriteria().andStudyidEqualTo(studyID).andDatacolumnEqualTo("Site");
			return mapper.countByExample(example) > 0;

		} else {

			StudyDerivedDataMapper mapper = connectionFactory.sqlSessionFactory.openSession().getMapper(StudyDerivedDataMapper.class);
			StudyDerivedDataExample example = new StudyDerivedDataExample();
			if (dataset != null)
				example.createCriteria().andStudyidEqualTo(studyID).andDatasetEqualTo(dataset).andDatacolumnEqualTo("Site");
			else
				example.createCriteria().andStudyidEqualTo(studyID).andDatacolumnEqualTo("Site");
			return mapper.countByExample(example) > 0;
		}
	}

	public void removeSiteByStudyId(int studyID, Integer dataset) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudySiteMapper studySiteMapper = session.getMapper(StudySiteMapper.class);

		StudyAgronomyMapper agroMapper = session.getMapper(StudyAgronomyMapper.class);
		StudyDesignMapper designMapper = session.getMapper(StudyDesignMapper.class);
		PlantingTypeMapper plantMapper = session.getMapper(PlantingTypeMapper.class);

		try {

			StudySiteExample example = new StudySiteExample();
			if (dataset != null)
				example.createCriteria().andStudyidEqualTo(studyID).andDatasetEqualTo(dataset);
			else
				example.createCriteria().andStudyidEqualTo(studyID);
			List<StudySite> lstSite = studySiteMapper.selectByExample(example);
			for (StudySite site : lstSite) {
				StudyAgronomyExample agEx = new StudyAgronomyExample();
				agEx.createCriteria().andStudysiteidEqualTo(site.getId());
				List<StudyAgronomy> lstAgronomies = agroMapper.selectByExample(agEx);

				agroMapper.deleteByExample(agEx);
				StudyDesignExample designEx = new StudyDesignExample();
				designEx.createCriteria().andStudysiteidEqualTo(site.getId());
				designMapper.deleteByExample(designEx);
			}

			studySiteMapper.deleteByExample(example);
			session.commit();
		} finally {
			session.close();

		}
	}

	public void addStudySite(ArrayList<StudySite> records) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudySiteMapper studySiteMapper = session.getMapper(StudySiteMapper.class);

		try {
			for (StudySite record : records) {
				studySiteMapper.insert(record);
			}
			session.commit();

		} finally {
			session.close();
		}

	}

	public void updateStudySite(List<StudySite> sites) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudySiteMapper studySiteMapper = session.getMapper(StudySiteMapper.class);

		try {
			for (StudySite record : sites) {
				if (record.getId() == null)
					studySiteMapper.insert(record);
				else {
					studySiteMapper.updateByPrimaryKey(record);
				}
			}
			session.commit();

		} finally {
			session.close();
		}

	}

	public void updateStudySite(StudySite record) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudySiteMapper studySiteMapper = session.getMapper(StudySiteMapper.class);

		try {

			studySiteMapper.updateByPrimaryKey(record);

			session.commit();

		} finally {
			session.close();
		}

	}

	public List<StudySite> getAllStudySites(int studyId) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudySiteMapper studySiteMapper = session.getMapper(StudySiteMapper.class);
		try {
			StudySiteExample example = new StudySiteExample();
			example.createCriteria().andStudyidEqualTo(studyId);
			List<StudySite> studySites = studySiteMapper.selectByExample(example);
			return studySites;
		} finally {
			session.close();
		}

	}

	private void addEmptyRecordOnStudySite(int studyId) {
		// TODO Auto-generated method stub
		StudySite record = new StudySite();
		// record.setId(studyId);
		record.setStudyid(studyId);
		record.setEcotypeid(1);
		addStudySite(record);
		System.out.println("Added Empty Record on site");
	}

	public List<StudySite> initializeStudySites(int studyId) {
		// TODO Auto-generated method stub
		List<StudySite> studySites = getAllStudySites(studyId);
		if (studySites.isEmpty()) {
			try {
				ArrayList<StudyRawDataByDataColumn> studyList = getStudySiteByStudy(studyId);
				for (StudyRawDataByDataColumn s : studyList) {
					StudySite record = new StudySite();
					record.setStudyid(s.getStudyid());
					record.setSitename(s.getDatavalue());
					record.setEcotypeid(1);
					addStudySite(record);
					System.out.println("added" + s.getDatavalue() + " to study id: " + s.getStudyid());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (getAllStudySites(studyId).isEmpty())
				addEmptyRecordOnStudySite(studyId);
		}
		return studySites;

	}

	public boolean isSiteRecordExist(int studyID) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudySiteMapper siteMapper = session.getMapper(StudySiteMapper.class);
		StudySiteExample ex = new StudySiteExample();
		ex.createCriteria().andStudyidEqualTo(studyID);
		System.out.println("Count: " + siteMapper.countByExample(ex));
		return (siteMapper.countByExample(ex) > 0);
	}

	public ArrayList<StudySiteInfoModel> getStudySiteByStudyId(int studyID, Integer dataset) {
		ArrayList<StudySiteInfoModel> returnVal = new ArrayList<StudySiteInfoModel>();
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudySiteMapper siteMapper = session.getMapper(StudySiteMapper.class);
		StudyAgronomyMapper agroMapper = session.getMapper(StudyAgronomyMapper.class);
		StudyDesignMapper designMapper = session.getMapper(StudyDesignMapper.class);
		PlantingTypeMapper plantMapper = session.getMapper(PlantingTypeMapper.class);
		LocationMapper locationMapper = session.getMapper(LocationMapper.class);
		EcotypeMapper ecoMapper = session.getMapper(EcotypeMapper.class);
		StudySiteExample ex = new StudySiteExample();
		if (dataset != null) {
			ex.createCriteria().andStudyidEqualTo(studyID).andDatasetEqualTo(dataset);
		} else {
			ex.createCriteria().andStudyidEqualTo(studyID);

		}
		List<StudySite> lstSites = siteMapper.selectByExample(ex);
		for (StudySite site : lstSites) {
			StudySiteInfoModel newData = new StudySiteInfoModel(site);

			// Site

			// Design
			StudyDesignExample desEx = new StudyDesignExample();
			desEx.createCriteria().andStudysiteidEqualTo(site.getId());
			newData.selectedDesignInfo = designMapper.selectByExample(desEx).get(0);
			// Agro
			StudyAgronomyExample agroEx = new StudyAgronomyExample();
			agroEx.createCriteria().andStudysiteidEqualTo(site.getId());
			newData.selectedAgroInfo = agroMapper.selectByExample(agroEx).get(0);

			// PlantingType
			newData.selectedSitePlantingType = plantMapper.selectByPrimaryKey(newData.selectedAgroInfo.getPlantingtypeid());

			// Ecotype
			newData.selectedEcotype = ecoMapper.selectByPrimaryKey(newData.getEcotypeid());
			returnVal.add(newData);

		}

		return returnVal;

	}

	public ArrayList<StudyRawDataByDataColumn> getStudySiteByStudy(int studyId) throws Exception {
		StudyRawDataManagerImpl studyRawDataManagerImpl = new StudyRawDataManagerImpl(isRaw);
		ArrayList<StudyRawDataByDataColumn> list = (ArrayList<StudyRawDataByDataColumn>) studyRawDataManagerImpl.getStudyRawDataColumn(studyId, "site");
		try {
			for (StudyRawDataByDataColumn s : list) {
				System.out.println(s.getStudyid() + " " + s.getDatacolumn() + " " + s.getDatavalue());
			}
		} catch (NullPointerException npe) {// if still empty since there's no
											// site data on the rawdata table
			// TODO Auto-generated catch block
		}
		return list;
	}

	public List<StudySite> getSiteByEcotypeId(Integer id) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudySiteMapper mapper = session.getMapper(StudySiteMapper.class);
		try {
			StudySiteExample example = new StudySiteExample();
			example.createCriteria().andEcotypeidEqualTo(id);
			return mapper.selectByExample(example);
		} finally {
			session.close();
		}
	}

	public void addSiteBatch(ArrayList<StudySiteInfoModel> lstSites) {

		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudySiteMapper studySiteMapper = session.getMapper(StudySiteMapper.class);
		StudyDesignMapper design = session.getMapper(StudyDesignMapper.class);
		StudyAgronomyMapper agro = session.getMapper(StudyAgronomyMapper.class);
		StudyLocationMapper loc = session.getMapper(StudyLocationMapper.class);
		try {
			for (StudySite record : lstSites) {
				studySiteMapper.insert(record);
			}
			session.commit();
			for (StudySiteInfoModel record : lstSites) {
				record.getSelectedDesignInfo().setStudyid(record.getStudyid());
				record.getSelectedDesignInfo().setStudysiteid(record.getId());
				record.getSelectedAgroInfo().setStudysiteid(record.getId());
				record.getSelectedAgroInfo().setPlantingtypeid(record.getSelectedSitePlantingType().getId());
				design.insert(record.getSelectedDesignInfo());
				agro.insert(record.getSelectedAgroInfo());

				StudyLocation newloc = new StudyLocation();

				newloc.setDataset(record.getDataset());
				newloc.setLocationid(record.getLocationid());
				newloc.setStudyid(record.getStudyid());

			}
			session.commit();

		} finally {
			session.close();
		}

	}
}
