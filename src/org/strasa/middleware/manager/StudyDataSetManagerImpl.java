package org.strasa.middleware.manager;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.StudyDataSetMapper;
import org.strasa.middleware.model.StudyDataSet;
import org.strasa.middleware.model.StudyDataSetExample;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class StudyDataSetManagerImpl {

	@WireVariable
	ConnectionFactory connectionFactory;

	public void addDataSet(StudyDataSet dataset) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyDataSetMapper mapper = session.getMapper(StudyDataSetMapper.class);

		try {
			mapper.insert(dataset);
			session.commit();
		} finally {
			session.close();
		}

	}

	public void updateDataSet(StudyDataSet dataset) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyDataSetMapper mapper = session.getMapper(StudyDataSetMapper.class);

		try {
			mapper.updateByPrimaryKey(dataset);
			session.commit();
		} finally {
			session.close();
		}

	}

	public List<StudyDataSet> getDataSetsByStudyId(Integer studyID) {

		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyDataSetMapper mapper = session.getMapper(StudyDataSetMapper.class);

		try {
			StudyDataSetExample example = new StudyDataSetExample();
			example.createCriteria().andStudyidEqualTo(studyID);
			System.out.println("STUDYID : + " + studyID);
			return mapper.selectByExample(example);
		} finally {
			session.close();
		}

	}

	public StudyDataSet getDataSet(Integer datasetID) {

		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyDataSetMapper mapper = session.getMapper(StudyDataSetMapper.class);

		try {

			return mapper.selectByPrimaryKey(datasetID);
		} finally {
			session.close();
		}

	}

	public void removeDatasetByStudyId(Integer id) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyDataSetMapper mapper = session.getMapper(StudyDataSetMapper.class);

		try {

			StudyDataSetExample example = new StudyDataSetExample();
			example.createCriteria().andStudyidEqualTo(id);
			mapper.deleteByExample(example);

		} finally {
			session.commit();
			session.close();
		}
	}
}
