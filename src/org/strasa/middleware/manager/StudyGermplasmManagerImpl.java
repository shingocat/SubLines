package org.strasa.middleware.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.GermplasmCharacteristicsMapper;
import org.strasa.middleware.mapper.GermplasmMapper;
import org.strasa.middleware.mapper.StudyGermplasmCharacteristicsMapper;
import org.strasa.middleware.mapper.StudyGermplasmMapper;
import org.strasa.middleware.model.Germplasm;
import org.strasa.middleware.model.GermplasmCharacteristics;
import org.strasa.middleware.model.StudyGermplasm;
import org.strasa.middleware.model.StudyGermplasmExample;
import org.strasa.web.uploadstudy.view.pojos.GermplasmDeepInfoModel;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class StudyGermplasmManagerImpl {

	@WireVariable
	ConnectionFactory connectionFactory;

	public List<StudyGermplasm> getStudyGermplasmByStudyId(int studyID) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyGermplasmMapper mapper = session.getMapper(StudyGermplasmMapper.class);

		try {
			StudyGermplasmExample example = new StudyGermplasmExample();
			example.createCriteria().andStudyidEqualTo(studyID);
			return mapper.selectByExample(example);

		} finally {
			session.close();
		}
	}

	public boolean isGermplasmConflict(Germplasm data) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyGermplasmMapper mapper = session.getMapper(StudyGermplasmMapper.class);

		try {
			StudyGermplasmExample example = new StudyGermplasmExample();
			example.createCriteria().andGrefEqualTo(data.getId()).andUseridNotEqualTo(data.getUserid());
			return mapper.countByExample(example) > 0;
		} finally {
			session.close();
		}

	}

	public boolean isGermplasmGrefExist(Germplasm data) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyGermplasmMapper mapper = session.getMapper(StudyGermplasmMapper.class);
		System.out.println("ID" + data.getId());
		try {
			StudyGermplasmExample example = new StudyGermplasmExample();
			example.createCriteria().andGrefEqualTo(data.getId());
			return mapper.countByExample(example) > 0;
		} finally {
			session.close();
		}

	}

	public void updateStudyGermplasmID(Integer oldID, Integer newID, Integer userID) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyGermplasmMapper mapper = session.getMapper(StudyGermplasmMapper.class);

		try {
			StudyGermplasmExample example = new StudyGermplasmExample();
			example.createCriteria().andGrefEqualTo(oldID).andUseridEqualTo(userID);
			StudyGermplasm updateData = new StudyGermplasm();
			updateData.setGref(newID);
			mapper.updateByExampleSelective(updateData, example);
		} finally {

		}

	}

	public List<StudyGermplasm> getStudyGermplasmByStudyId(int studyID, Integer dataset) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyGermplasmMapper mapper = session.getMapper(StudyGermplasmMapper.class);

		try {
			StudyGermplasmExample example = new StudyGermplasmExample();
			example.createCriteria().andStudyidEqualTo(studyID).andDatasetEqualTo(dataset);
			return mapper.selectByExample(example);

		} finally {
			session.close();
		}
	}

	public List<Germplasm> getGermplasmFromStudy(Integer studyID, Integer dataset) {

		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);

		try {
			List<StudyGermplasm> lstStudyGerm = getStudyGermplasmByStudyId(studyID, dataset);

			List<Germplasm> returnVal = new ArrayList<Germplasm>();

			for (StudyGermplasm sgerm : lstStudyGerm) {
				returnVal.add(mapper.selectByPrimaryKey(sgerm.getGref()));
			}

			return returnVal;
		} finally {
			session.close();
		}

	}

	public List<Germplasm> getGermplasmFromStudy(Integer studyID) {

		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);

		try {
			List<StudyGermplasm> lstStudyGerm = getStudyGermplasmByStudyId(studyID);

			List<Germplasm> returnVal = new ArrayList<Germplasm>();

			for (StudyGermplasm sgerm : lstStudyGerm) {
				returnVal.add(mapper.selectByPrimaryKey(sgerm.getGref()));
			}

			return returnVal;
		} finally {
			session.close();
		}

	}

	public int addStudyGermplasm(StudyGermplasm record) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyGermplasmMapper mapper = session.getMapper(StudyGermplasmMapper.class);
		try {
			mapper.insert(record);
			session.commit();
		} finally {
			session.close();
		}
		return record.getId();
	}

	public void addStudyGermplasm(List<StudyGermplasm> lstRecord) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession(ExecutorType.BATCH);
		StudyGermplasmMapper mapper = session.getMapper(StudyGermplasmMapper.class);

		try {
			for (StudyGermplasm record : lstRecord) {
				if (record.getId() == null) {
					mapper.insert(record);
				} else {
					mapper.updateByPrimaryKey(record);
				}

			}
			session.commit();
		} finally {
			session.close();
		}
	}

	public void addStudyGermplasm(List<StudyGermplasm> lstRecord, List<GermplasmCharacteristics> lstCharRecord) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession(ExecutorType.BATCH);
		StudyGermplasmMapper mapper = session.getMapper(StudyGermplasmMapper.class);
		try {
			System.out.println("Inserting StudyGermplasms");
			for (StudyGermplasm record : lstRecord) {
				if (record.getId() == null) {
					mapper.insert(record);
				} else {
					mapper.updateByPrimaryKey(record);
				}
				System.out.println("SID: " + record.getId());
			}
			GermplasmCharacteristicsMapper charmapper = session.getMapper(GermplasmCharacteristicsMapper.class);

			for (GermplasmCharacteristics record : lstCharRecord)
				charmapper.insert(record);

			session.commit();
		} finally {
			session.close();
		}
	}

	public void addStudyGermplasmBatch(Collection<GermplasmDeepInfoModel> collection, Integer studyID, Integer dataset, Integer userid) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyGermplasmMapper mapper = session.getMapper(StudyGermplasmMapper.class);
		StudyGermplasmCharacteristicsMapper charmapper = session.getMapper(StudyGermplasmCharacteristicsMapper.class);
		try {

			if (!collection.isEmpty()) {
				StudyGermplasmExample ex = new StudyGermplasmExample();

				ex.createCriteria().andStudyidEqualTo(studyID).andDatasetEqualTo(dataset);
				mapper.deleteByExample(ex);
			}

			System.out.println("Inserting StudyGermplasms");
			for (GermplasmDeepInfoModel record : collection) {
				StudyGermplasm newRec = new StudyGermplasm();
				newRec.setDataset(dataset);
				newRec.setStudyid(studyID);
				newRec.setGref(record.getId());
				newRec.setUserid(userid);

				// newRec.setBreeder(record.getCurrBreader());
				mapper.insert(newRec);

				// for (StudyGermplasmCharacteristics records :
				// record.getCharacteristicValues()){
				//
				// records.setStudygermplasmid(newRec.getId());
				// charmapper.insert(records);
				// }
				// System.out.println("SID: " + record.getId());
			}

			session.commit();
		} finally {
			session.close();
		}
	}

	public void updateStudyGermplasm(StudyGermplasm record) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyGermplasmMapper mapper = session.getMapper(StudyGermplasmMapper.class);
		try {
			mapper.updateByPrimaryKey(record);
			session.commit();
		} finally {
			session.close();
		}
		// return record.getId();
	}

	public void removeGermplasmByStudyId(Integer id) {

		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyGermplasmMapper mapper = session.getMapper(StudyGermplasmMapper.class);
		StudyGermplasmExample ex = new StudyGermplasmExample();
		try {
			ex.createCriteria().andStudyidEqualTo(id);
			mapper.deleteByExample(ex);
			session.commit();
		} finally {
			session.close();
		}
		// TODO Auto-generated method stub

	}

	public boolean isGermplasmRecordExist(Integer gref, Integer studyID, Integer dataset) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyGermplasmMapper mapper = session.getMapper(StudyGermplasmMapper.class);
		StudyGermplasmExample ex = new StudyGermplasmExample();
		try {
			if (dataset != null) {
				ex.createCriteria().andStudyidEqualTo(studyID).andDatasetEqualTo(dataset).andGrefEqualTo(gref);
			} else {
				ex.createCriteria().andStudyidEqualTo(studyID).andGrefEqualTo(gref);

			}
			return mapper.countByExample(ex) > 0;

		} finally {
			session.close();
		}

	}

	public void removeGermplasmByStudyId(Integer id, Integer dataset) {

		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyGermplasmMapper mapper = session.getMapper(StudyGermplasmMapper.class);
		StudyGermplasmExample ex = new StudyGermplasmExample();
		try {
			ex.createCriteria().andStudyidEqualTo(id).andDatasetEqualTo(dataset);
			mapper.deleteByExample(ex);
			session.commit();
		} finally {
			session.close();
		}
		// TODO Auto-generated method stub

	}
}
