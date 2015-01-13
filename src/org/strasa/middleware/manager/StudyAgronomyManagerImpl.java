package org.strasa.middleware.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.StudyAgronomyMapper;
import org.strasa.middleware.model.StudyAgronomy;
import org.strasa.middleware.model.StudyAgronomyExample;
import org.strasa.middleware.model.StudySite;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class StudyAgronomyManagerImpl {

	@WireVariable
	ConnectionFactory connectionFactory;

	public void addStudyAgronomy(StudyAgronomy record) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyAgronomyMapper studyAgronomyMapper = session.getMapper(StudyAgronomyMapper.class);

		try {
			studyAgronomyMapper.insert(record);
			session.commit();

		} finally {
			session.close();
		}

	}

	public void addStudyAgronomy(ArrayList<StudyAgronomy> records) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyAgronomyMapper studyAgronomyMapper = session.getMapper(StudyAgronomyMapper.class);

		try {
			for (StudyAgronomy record : records) {
				studyAgronomyMapper.insert(record);
			}
			session.commit();

		} finally {
			session.close();
		}

	}

	public void updateStudyAgronomy(List<StudyAgronomy> agroInfo) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyAgronomyMapper studyAgronomyMapper = session.getMapper(StudyAgronomyMapper.class);

		try {
			for (StudyAgronomy record : agroInfo) {
				if (record.getId() == null)
					studyAgronomyMapper.insert(record);
				else
					studyAgronomyMapper.updateByPrimaryKey(record);
			}
			session.commit();

		} finally {
			session.close();
		}

	}

	public void updateStudyAgronomy(StudyAgronomy record) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyAgronomyMapper studyAgronomyMapper = session.getMapper(StudyAgronomyMapper.class);

		try {
			studyAgronomyMapper.updateByPrimaryKey(record);

			session.commit();

		} finally {
			session.close();
		}

	}

	public List<StudyAgronomy> getAllStudyAgronomy() {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyAgronomyMapper studyAgronomyMapper = session.getMapper(StudyAgronomyMapper.class);
		try {
			List<StudyAgronomy> studyAgronomy = studyAgronomyMapper.selectByExample(null);
			return studyAgronomy;
		} finally {
			session.close();
		}
	}

	private void addEmptyRecordOnStudyAgronomy(int studySiteId) {
		// TODO Auto-generated method stub
		StudyAgronomy record = new StudyAgronomy();
		record.setStudysiteid(studySiteId);
		record.setPlantingtypeid(1);
		addStudyAgronomy(record);
		System.out.println("added empty record to studySiteId " + Integer.toString(studySiteId));
	}

	public StudyAgronomy getStudyAgronomy(int studyid) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyAgronomyMapper studyAgronomyMapper = session.getMapper(StudyAgronomyMapper.class);
		try {
			StudyAgronomyExample example = new StudyAgronomyExample();
			example.createCriteria().andStudysiteidEqualTo(studyid);
			List<StudyAgronomy> studyAgronomy = studyAgronomyMapper.selectByExample(null);
			return (studyAgronomy.isEmpty()) ? null : studyAgronomy.get(0);
		} finally {
			session.close();
		}
	}

	public List<StudyAgronomy> initializeStudyAgronomy(List<StudySite> sites) {
		// TODO Auto-generated method stub
		try {
			List<StudyAgronomy> studyAgroInfoList = getAllStudyAgronomy();
			if (studyAgroInfoList.isEmpty()) {
				for (StudySite site : sites) {// for each site
					addEmptyRecordOnStudyAgronomy(site.getId());
				}
				studyAgroInfoList = getAllStudyAgronomy();
			}
			return studyAgroInfoList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<StudyAgronomy> getStudyAgronomyByPLantingTypeId(Integer id) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyAgronomyMapper studyAgronomyMapper = session.getMapper(StudyAgronomyMapper.class);
		try {
			StudyAgronomyExample example = new StudyAgronomyExample();
			example.createCriteria().andPlantingtypeidEqualTo(id);
			return studyAgronomyMapper.selectByExample(example);
		} finally {
			session.close();
		}
	}

}
