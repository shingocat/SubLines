package org.strasa.middleware.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.StudyDesignMapper;
import org.strasa.middleware.model.StudyDesign;
import org.strasa.middleware.model.StudyDesignExample;
import org.strasa.middleware.model.StudySite;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class StudyDesignManagerImpl {

	@WireVariable
	ConnectionFactory connectionFactory;
	
	public void addStudyDesign(StudyDesign record){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		StudyDesignMapper studyDesignMapper = session
				.getMapper(StudyDesignMapper.class);

		try {
			studyDesignMapper.insert(record);
			session.commit();

		} finally {
			session.close();
		}

	}

	public void addStudyDesign(ArrayList<StudyDesign> records) {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		StudyDesignMapper studyDesignMapper = session
				.getMapper(StudyDesignMapper.class);

		try {
			for (StudyDesign record : records) {
				studyDesignMapper.insert(record);
			}
			session.commit();

		} finally {
			session.close();
		}

	}

	public void updateStudyDesign(List<StudyDesign> designInfo){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		StudyDesignMapper studyDesignMapper = session.getMapper(StudyDesignMapper.class);

		try{
			for(StudyDesign record:designInfo){
				if(record.getId() == null)
				studyDesignMapper.insert(record);
				else
					studyDesignMapper.updateByPrimaryKey(record);
			}
			session.commit();

		}finally{
			session.close();
		}

	}
	public void updateStudyDesign(StudyDesign record) {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		StudyDesignMapper studyDesignMapper = session
				.getMapper(StudyDesignMapper.class);

		try {
			studyDesignMapper.updateByPrimaryKey(record);

			session.commit();

		} finally {
			session.close();
		}

	}


	public List<StudyDesign> getAllStudyDesign() {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		StudyDesignMapper studyDesignMapper = session
				.getMapper(StudyDesignMapper.class);
		try {
			List<StudyDesign> studyDesign = studyDesignMapper
					.selectByExample(null);
			return studyDesign;
		} finally {
			session.close();
		}
	}

	public StudyDesign getStudyDesign(int studysiteid) {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		StudyDesignMapper studyDesignMapper = session
				.getMapper(StudyDesignMapper.class);
		try {
			StudyDesignExample example = new StudyDesignExample();
			example.createCriteria().andStudysiteidEqualTo(studysiteid);
			List<StudyDesign> studyDesign = studyDesignMapper
					.selectByExample(example);
			return (studyDesign.isEmpty()) ? null : studyDesign.get(0);

		} finally {
			session.close();
		}
	}

	private void addEmptyRecordOnStudyDesign(int studySiteId) {
		// TODO Auto-generated method stub
		StudyDesign record = new StudyDesign();
		record.setStudysiteid(studySiteId);

		addStudyDesign(record);
		System.out.println("added empty record to studySiteId "
				+ Integer.toString(studySiteId));
	}

	public List<StudyDesign> initializeStudyDesign(List<StudySite> sites) {
		// TODO Auto-generated method stub
		try {
			List<StudyDesign> studyDesignInfoList = getAllStudyDesign();
			if (studyDesignInfoList.isEmpty()) {
				for (StudySite site : sites) {// for each site
					addEmptyRecordOnStudyDesign(site.getId());
				}
				studyDesignInfoList = getAllStudyDesign();
			}
			return studyDesignInfoList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
