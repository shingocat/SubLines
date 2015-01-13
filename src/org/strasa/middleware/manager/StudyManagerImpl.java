package org.strasa.middleware.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.StudyAgronomyMapper;
import org.strasa.middleware.mapper.StudyDataColumnMapper;
import org.strasa.middleware.mapper.StudyDataSetMapper;
import org.strasa.middleware.mapper.StudyDerivedDataMapper;
import org.strasa.middleware.mapper.StudyDesignMapper;
import org.strasa.middleware.mapper.StudyFileMapper;
import org.strasa.middleware.mapper.StudyGermplasmMapper;
import org.strasa.middleware.mapper.StudyLocationMapper;
import org.strasa.middleware.mapper.StudyMapper;
import org.strasa.middleware.mapper.StudyRawDataMapper;
import org.strasa.middleware.mapper.StudySiteMapper;
import org.strasa.middleware.mapper.other.StudyRawDataBatch;
import org.strasa.middleware.model.Study;
import org.strasa.middleware.model.StudyAgronomyExample;
import org.strasa.middleware.model.StudyDataColumn;
import org.strasa.middleware.model.StudyDataColumnExample;
import org.strasa.middleware.model.StudyDataSetExample;
import org.strasa.middleware.model.StudyDerivedData;
import org.strasa.middleware.model.StudyDerivedDataExample;
import org.strasa.middleware.model.StudyDesignExample;
import org.strasa.middleware.model.StudyExample;
import org.strasa.middleware.model.StudyFileExample;
import org.strasa.middleware.model.StudyGermplasm;
import org.strasa.middleware.model.StudyGermplasmExample;
import org.strasa.middleware.model.StudyLocationExample;
import org.strasa.middleware.model.StudyRawData;
import org.strasa.middleware.model.StudyRawDataExample;
import org.strasa.middleware.model.StudySite;
import org.strasa.middleware.model.StudySiteExample;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class StudyManagerImpl {

	@WireVariable
	ConnectionFactory connectionFactory;

	private int userid;

	public StudyManagerImpl() {
		this.setUserid(SecurityUtil.getDbUser().getId());
	}

	public Study getStudyById(int studyid) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyMapper mapper = session.getMapper(StudyMapper.class);
		try {
			return mapper.selectByPrimaryKey(studyid);
		} finally {
			session.close();
		}
	}

	public boolean validateCSVDataForGermplasmComparision(Integer studyid, List<String[]> dataPrep, int germplasmColumn, boolean isRaw) {

		HashSet<String> noDupSet = new HashSet<String>();
		ArrayList<String> germplasmData = new ArrayList<String>();
		for (String[] row : dataPrep) {

			if (noDupSet.add(row[germplasmColumn])) {
				germplasmData.add(row[germplasmColumn]);

			}

		}
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyRawDataBatch mapper = session.getMapper(StudyRawDataBatch.class);
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("studyid", studyid);
		params.put("table", ((isRaw) ? "studyrawdata" : "studyderiveddata"));
		params.put("list", germplasmData);

		try {
			System.out.println(mapper.countGermplasmOccurence(params) + " COUNT");
			return mapper.countGermplasmOccurence(params) == germplasmData.size();

		} finally {
			session.close();
		}

	}

	public ArrayList<String> getUnknownGermplasmFromCSVData(Integer studyid, List<String[]> dataPrep, int germplasmColumn, boolean isRaw) {
		HashSet<String> noDupSet = new HashSet<String>();
		ArrayList<String> germplasmData = new ArrayList<String>();
		ArrayList<String> returnVal = new ArrayList<String>();

		for (String[] row : dataPrep) {

			if (noDupSet.add(row[germplasmColumn])) {
				germplasmData.add(row[germplasmColumn]);

			}

		}
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		if (isRaw) {
			try {
				StudyRawDataMapper mapper = session.getMapper(StudyRawDataMapper.class);
				for (String gname : germplasmData) {
					StudyRawDataExample example = new StudyRawDataExample();
					example.createCriteria().andDatacolumnEqualTo("Gname").andStudyidEqualTo(studyid).andDatavalueEqualTo(gname);
					if (mapper.countByExample(example) == 0)
						returnVal.add(gname);
				}
				return returnVal;
			}

			finally {
				session.close();
			}
		} else {
			try {
				StudyDerivedDataMapper mapper = session.getMapper(StudyDerivedDataMapper.class);
				for (String gname : germplasmData) {
					StudyDerivedDataExample example = new StudyDerivedDataExample();
					example.createCriteria().andDatacolumnEqualTo("Gname").andStudyidEqualTo(studyid).andDatavalueEqualTo(gname);
					if (mapper.countByExample(example) == 0)
						returnVal.add(gname);
				}
				return returnVal;
			}

			finally {
				session.close();
			}
		}

	}

	public void mergeStudyData(Integer studyid, boolean isRaw) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();

		StudyRawDataMapper mapperRaw = session.getMapper(StudyRawDataMapper.class);
		StudyRawDataExample exampleRaw = new StudyRawDataExample();
		StudyDerivedDataMapper mapperDerived = session.getMapper(StudyDerivedDataMapper.class);
		StudyDerivedDataExample exampleDerived = new StudyDerivedDataExample();
		StudyDataSetMapper mapperDataset = session.getMapper(StudyDataSetMapper.class);
		StudyDataSetExample exampleDataset = new StudyDataSetExample();
		StudyDataColumnMapper mapperColumn = session.getMapper(StudyDataColumnMapper.class);
		StudyDataColumnExample exampleColumn = new StudyDataColumnExample();
		StudySiteMapper mapperSite = session.getMapper(StudySiteMapper.class);
		StudySiteExample exampleSite = new StudySiteExample();
		StudyGermplasmMapper mapperGermplasm = session.getMapper(StudyGermplasmMapper.class);
		StudyGermplasmExample exampleGermplasm = new StudyGermplasmExample();

		try {
			Integer firstDatasetID;

			exampleDataset.createCriteria().andDatatypeEqualTo((isRaw) ? "rd" : "dd").andStudyidEqualTo(studyid);
			firstDatasetID = mapperDataset.selectByExample(exampleDataset).get(0).getId();

			if (isRaw) {

				StudyRawData data = new StudyRawData();
				data.setDataset(firstDatasetID);
				exampleRaw.createCriteria().andStudyidEqualTo(studyid);
				mapperRaw.updateByExampleSelective(data, exampleRaw);
			} else {
				StudyDerivedData data = new StudyDerivedData();
				data.setDataset(firstDatasetID);
				exampleDerived.createCriteria().andStudyidEqualTo(studyid);
				mapperDerived.updateByExampleSelective(data, exampleDerived);
			}
			HashSet<String> noDup = new HashSet<String>();
			exampleColumn.createCriteria().andStudyidEqualTo(firstDatasetID).andDatatypeEqualTo((isRaw) ? "rd" : "dd");

			List<StudyDataColumn> arrCol = mapperColumn.selectByExample(exampleColumn);
			mapperColumn.deleteByExample(exampleColumn);
			for (StudyDataColumn col : arrCol) {
				if (noDup.add(col.getColumnheader())) {
					mapperColumn.insert(col);
				}

			}
			exampleSite.createCriteria().andStudyidEqualTo(studyid);
			StudySite siteData = new StudySite();
			siteData.setDataset(firstDatasetID);
			mapperSite.updateByExampleSelective(siteData, exampleSite);

			exampleGermplasm.createCriteria().andStudyidEqualTo(firstDatasetID);

			StudyGermplasm germplasmData = new StudyGermplasm();
			germplasmData.setDataset(firstDatasetID);
			mapperGermplasm.updateByExampleSelective(germplasmData, exampleGermplasm);

			exampleDataset = new StudyDataSetExample();
			exampleDataset.createCriteria().andStudyidEqualTo(studyid).andDatatypeEqualTo((isRaw) ? "rd" : "dd").andIdNotEqualTo(firstDatasetID);
			mapperDataset.deleteByExample(exampleDataset);

		} finally {
			session.commit();
			session.close();
		}

	}

	public boolean isDataSetExist(Integer studyID, Integer dataset) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyRawDataMapper mapperRaw = session.getMapper(StudyRawDataMapper.class);
		StudyRawDataExample exampleRaw = new StudyRawDataExample();
		exampleRaw.createCriteria().andStudyidEqualTo(studyID).andDatasetEqualTo(dataset);
		StudyDerivedDataMapper mapperDerived = session.getMapper(StudyDerivedDataMapper.class);
		StudyDerivedDataExample exampleDerived = new StudyDerivedDataExample();
		exampleDerived.createCriteria().andStudyidEqualTo(studyID).andDatasetEqualTo(dataset).andDatacolumnEqualTo("GName");

		boolean returnVal = false;

		try {

			if (mapperRaw.countByExample(exampleRaw) > 0)
				returnVal = true;
			if (mapperDerived.countByExample(exampleDerived) > 0)
				returnVal = true;

			return returnVal;

		} finally {
			session.close();
		}
	}

	public List<Study> getStudiesByUserID(int userID) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyMapper mapper = session.getMapper(StudyMapper.class);
		try {
			StudyExample example = new StudyExample();
			example.createCriteria().andUseridEqualTo(userID);
			return mapper.selectByExample(example);
		} finally {
			session.close();
		}
	}

	public void deleteStudyById(Integer studyId) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();

		StudyMapper mapper = session.getMapper(StudyMapper.class);

		StudyDataSetMapper dateSetMapper = session.getMapper(StudyDataSetMapper.class);
		StudyDesignMapper designMapper = session.getMapper(StudyDesignMapper.class);
		StudyAgronomyMapper studyAgroMapper = session.getMapper(StudyAgronomyMapper.class);
		StudySiteMapper siteMapper = session.getMapper(StudySiteMapper.class);
		StudyLocationMapper locMapper = session.getMapper(StudyLocationMapper.class);
		StudyDataColumnMapper dataColMapper = session.getMapper(StudyDataColumnMapper.class);
		StudyDerivedDataMapper derivedMapper = session.getMapper(StudyDerivedDataMapper.class);
		StudyRawDataMapper rawMapper = session.getMapper(StudyRawDataMapper.class);
		StudyFileMapper fileMapper = session.getMapper(StudyFileMapper.class);
		StudyGermplasmMapper germplasmMapper = session.getMapper(StudyGermplasmMapper.class);

		try {

			StudySiteExample siteEx = new StudySiteExample();
			siteEx.createCriteria().andStudyidEqualTo(studyId);
			List<StudySite> studySites = siteMapper.selectByExample(siteEx);

			for (StudySite s : studySites) {
				StudyAgronomyExample agEx = new StudyAgronomyExample();
				agEx.createCriteria().andStudysiteidEqualTo(s.getId());
				// List<StudyAgronomy> lstAgronomies =
				// studyAgroMapper.selectByExample(agEx);
				studyAgroMapper.deleteByExample(agEx);

				StudyDesignExample designEx = new StudyDesignExample();
				designEx.createCriteria().andStudysiteidEqualTo(s.getId());
				designMapper.deleteByExample(designEx);
			}

			siteMapper.deleteByExample(siteEx);

			StudyLocationExample locEx = new StudyLocationExample();
			locEx.createCriteria().andStudyidEqualTo(studyId);
			locMapper.deleteByExample(locEx);

			StudyDataColumnExample dataColEx = new StudyDataColumnExample();
			dataColEx.createCriteria().andStudyidEqualTo(studyId);
			dataColMapper.deleteByExample(dataColEx);

			StudyRawDataExample rawEx = new StudyRawDataExample();
			rawEx.createCriteria().andStudyidEqualTo(studyId);
			rawMapper.deleteByExample(rawEx);

			StudyDerivedDataExample derivedEx = new StudyDerivedDataExample();
			derivedEx.createCriteria().andStudyidEqualTo(studyId);
			derivedMapper.deleteByExample(derivedEx);

			StudyFileExample fileEx = new StudyFileExample();
			fileEx.createCriteria().andStudyidEqualTo(studyId);
			fileMapper.deleteByExample(fileEx);

			StudyGermplasmExample germplasmEx = new StudyGermplasmExample();
			germplasmEx.createCriteria().andStudyidEqualTo(studyId);
			germplasmMapper.deleteByExample(germplasmEx);

			StudyDataSetExample datasetEx = new StudyDataSetExample();
			datasetEx.createCriteria().andStudyidEqualTo(studyId);
			dateSetMapper.deleteByExample(datasetEx);

			mapper.deleteByPrimaryKey(studyId);

			session.commit();
		} finally {
			session.close();
		}
	}

	public void insertStudy(Study record) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyMapper mapper = session.getMapper(StudyMapper.class);

		try {

			if (record.getId() == null)
				mapper.insert(record);
			else
				mapper.updateByPrimaryKey(record);
			session.commit();
			// mapper.updateByExample(record, example);
		} finally {
			session.close();
		}
	}

	public void updateStudyById(Study record) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyMapper mapper = session.getMapper(StudyMapper.class);

		try {
			System.out.println(record.getShared());
			mapper.updateByPrimaryKey(record);
			session.commit();
			// mapper.updateByExample(record, example);
		} finally {
			session.close();
		}
	}

	public List<Study> getStudyByProgramId(Integer programId) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyMapper mapper = session.getMapper(StudyMapper.class);
		try {
			StudyExample example = new StudyExample();
			example.createCriteria().andProgramidEqualTo(programId);
			return mapper.selectByExample(example);
		} finally {
			session.close();
		}
	}

	public List<Study> getStudyByProjectId(Integer projectId) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyMapper mapper = session.getMapper(StudyMapper.class);
		try {
			StudyExample example = new StudyExample();
			example.createCriteria().andProjectidEqualTo(projectId);
			return mapper.selectByExample(example);
		} finally {
			session.close();
		}
	}

	public void deleteStudyById(int studyId, Integer dataset) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();

		// StudyMapper mapper = session.getMapper(StudyMapper.class);
		StudySiteMapper siteMapper = session.getMapper(StudySiteMapper.class);
		StudyLocationMapper locMapper = session.getMapper(StudyLocationMapper.class);
		StudyDataColumnMapper dataColMapper = session.getMapper(StudyDataColumnMapper.class);
		StudyDerivedDataMapper derivedMapper = session.getMapper(StudyDerivedDataMapper.class);
		StudyRawDataMapper rawMapper = session.getMapper(StudyRawDataMapper.class);
		StudyFileMapper fileMapper = session.getMapper(StudyFileMapper.class);
		StudyGermplasmMapper germplasmMapper = session.getMapper(StudyGermplasmMapper.class);
		StudyDataSetMapper datasetMapper = session.getMapper(StudyDataSetMapper.class);
		StudyAgronomyMapper agronomyMapper = session.getMapper(StudyAgronomyMapper.class);
		StudyDesignMapper designMapper = session.getMapper(StudyDesignMapper.class);

		try {
			StudySiteExample siteEx = new StudySiteExample();
			siteEx.createCriteria().andStudyidEqualTo(studyId).andDatasetEqualTo(dataset);
			for (StudySite site : siteMapper.selectByExample(siteEx)) {
				StudyDesignExample designExample = new StudyDesignExample();
				StudyAgronomyExample agronomyExample = new StudyAgronomyExample();

				designExample.createCriteria().andStudysiteidEqualTo(site.getId());
				agronomyExample.createCriteria().andStudysiteidEqualTo(site.getId());
				agronomyMapper.deleteByExample(agronomyExample);
				designMapper.deleteByExample(designExample);
			}
			siteMapper.deleteByExample(siteEx);

			StudyLocationExample locEx = new StudyLocationExample();
			locEx.createCriteria().andStudyidEqualTo(studyId).andDatasetEqualTo(dataset);
			;
			locMapper.deleteByExample(locEx);

			StudyDataColumnExample dataColEx = new StudyDataColumnExample();
			dataColEx.createCriteria().andStudyidEqualTo(studyId).andDatasetEqualTo(dataset);
			;
			dataColMapper.deleteByExample(dataColEx);

			StudyRawDataExample rawEx = new StudyRawDataExample();
			rawEx.createCriteria().andStudyidEqualTo(studyId).andDatasetEqualTo(dataset);
			;
			rawMapper.deleteByExample(rawEx);

			StudyDerivedDataExample derivedEx = new StudyDerivedDataExample();
			derivedEx.createCriteria().andStudyidEqualTo(studyId).andDatasetEqualTo(dataset);
			;
			derivedMapper.deleteByExample(derivedEx);

			StudyGermplasmExample germplasmEx = new StudyGermplasmExample();
			germplasmEx.createCriteria().andStudyidEqualTo(studyId).andDatasetEqualTo(dataset);
			;

			germplasmMapper.deleteByExample(germplasmEx);

			datasetMapper.deleteByPrimaryKey(dataset);

			session.commit();
		} finally {
			session.close();
		}

	}

	public List<Study> getByStudyTypeId(Integer id) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyMapper mapper = session.getMapper(StudyMapper.class);
		try {
			StudyExample example = new StudyExample();
			example.createCriteria().andStudytypeidEqualTo(id);
			return mapper.selectByExample(example);
		} finally {
			session.close();
		}
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

}
