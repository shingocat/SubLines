package org.strasa.middleware.manager;

import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.StudyMapper;
import org.strasa.middleware.model.Study;
import org.strasa.middleware.model.StudyExample;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class StudyManager {
	@WireVariable
	ConnectionFactory connectionFactory;

	public boolean isProjectExist(Study study, int userID) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyMapper mapper = session.getMapper(StudyMapper.class);
		StudyExample example = new StudyExample();
		example.createCriteria().andNameEqualTo(study.getName()).andProgramidEqualTo(study.getProgramid()).andProjectidEqualTo(study.getProjectid()).andUseridEqualTo(userID);
		try {
			return (mapper.countByExample(example) != 0);
		} finally {
			session.close();
		}

	}

	public boolean isProjectExist(String studyName, Integer programID, Integer projectID, int userID) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyMapper mapper = session.getMapper(StudyMapper.class);
		StudyExample example = new StudyExample();
		example.createCriteria().andNameEqualTo(studyName).andProgramidEqualTo(programID).andProjectidEqualTo(projectID).andUseridEqualTo(userID);
		try {
			return (mapper.countByExample(example) != 0);
		} finally {
			session.close();
		}

	}

	public Study getStudyByStudyId(int studyID) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyMapper mapper = session.getMapper(StudyMapper.class);
		StudyExample example = new StudyExample();
		example.createCriteria().andIdEqualTo(studyID);
		try {
			return mapper.selectByPrimaryKey(studyID);
		} finally {
			session.close();
		}

	}

	public int getStudyByStudyName(String studyname) {
		int toreturn = 0;
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyMapper mapper = session.getMapper(StudyMapper.class);
		StudyExample example = new StudyExample();
		example.createCriteria().andNameEqualTo(studyname);
		try {
			toreturn = mapper.selectByExample(example).get(0).getId();
			return toreturn;
		} finally {
			session.close();
		}

	}
}
