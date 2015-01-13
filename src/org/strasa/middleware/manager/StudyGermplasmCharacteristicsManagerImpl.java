package org.strasa.middleware.manager;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.StudyGermplasmCharacteristicsMapper;
import org.strasa.middleware.model.StudyGermplasmCharacteristics;
import org.strasa.middleware.model.StudyGermplasmCharacteristicsExample;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class StudyGermplasmCharacteristicsManagerImpl {

	@WireVariable
	ConnectionFactory connectionFactory;
	
	public boolean isCharacteristicExist(String attribute, String value, int id, String germplasmName) {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();

		try {
			StudyGermplasmCharacteristicsMapper mapper = session
					.getMapper(StudyGermplasmCharacteristicsMapper.class);
			StudyGermplasmCharacteristicsExample example = new StudyGermplasmCharacteristicsExample();
			example.createCriteria().andStudygermplasmidEqualTo(id).andAttributeEqualTo(attribute).andValueEqualTo(value).andGermplasmnameEqualTo(germplasmName);
			return !mapper.selectByExample(example).isEmpty();
			
		} finally {
			session.close();
		}
	}

	public List<StudyGermplasmCharacteristics> getAllCharacteristic() {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();

		try {
			StudyGermplasmCharacteristicsMapper mapper = session
					.getMapper(StudyGermplasmCharacteristicsMapper.class);

			return mapper.selectByExample(null);

		} finally {
			session.close();
		}
	}

	public List<StudyGermplasmCharacteristics> getAbioticStress(
			String germplasmName, int studyID) {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();

		try {
			StudyGermplasmCharacteristicsMapper mapper = session
					.getMapper(StudyGermplasmCharacteristicsMapper.class);
			StudyGermplasmCharacteristicsExample example = new StudyGermplasmCharacteristicsExample();
			example.createCriteria().andAttributeEqualTo("Abiotic Stress")
					.andStudygermplasmidEqualTo(studyID)
					.andGermplasmnameEqualTo(germplasmName);
			return mapper.selectByExample(example);

		} finally {
			session.close();
		}
	}

	public List<StudyGermplasmCharacteristics> getBioticStress(
			String germplasmName, int studyID) {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();

		try {
			StudyGermplasmCharacteristicsMapper mapper = session
					.getMapper(StudyGermplasmCharacteristicsMapper.class);
			StudyGermplasmCharacteristicsExample example = new StudyGermplasmCharacteristicsExample();
			example.createCriteria().andAttributeEqualTo("Abiotic Stress")
					.andStudygermplasmidEqualTo(studyID)
					.andGermplasmnameEqualTo(germplasmName);
			return mapper.selectByExample(example);

		} finally {
			session.close();
		}
	}

	public List<StudyGermplasmCharacteristics> getGrainQualityStress(
			String germplasmName, int studyID) {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();

		try {
			StudyGermplasmCharacteristicsMapper mapper = session
					.getMapper(StudyGermplasmCharacteristicsMapper.class);
			StudyGermplasmCharacteristicsExample example = new StudyGermplasmCharacteristicsExample();
			example.createCriteria().andAttributeEqualTo("Grain Quality")
					.andStudygermplasmidEqualTo(studyID)
					.andGermplasmnameEqualTo(germplasmName);
			return mapper.selectByExample(example);

		} finally {
			session.close();
		}
	}

	public List<StudyGermplasmCharacteristics> getMajorGenesStress() {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();

		try {
			StudyGermplasmCharacteristicsMapper mapper = session
					.getMapper(StudyGermplasmCharacteristicsMapper.class);
			StudyGermplasmCharacteristicsExample example = new StudyGermplasmCharacteristicsExample();
			example.createCriteria().andAttributeEqualTo("Major Genes");
			return mapper.selectByExample(example);

		} finally {
			session.close();
		}
	}
	
	public List<StudyGermplasmCharacteristics> getCharacteristicsByStudyID( int id) {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();

		try {
			StudyGermplasmCharacteristicsMapper mapper = session
					.getMapper(StudyGermplasmCharacteristicsMapper.class);
			StudyGermplasmCharacteristicsExample example = new StudyGermplasmCharacteristicsExample();
			example.createCriteria().andStudygermplasmidEqualTo(id);
			return mapper.selectByExample(example);

		} finally {
			session.close();
		}
	}

	public void deleteCharacteristicsByStudyID(int studyID) {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		try {
			StudyGermplasmCharacteristicsMapper mapper = session
					.getMapper(StudyGermplasmCharacteristicsMapper.class);
			StudyGermplasmCharacteristicsExample example = new StudyGermplasmCharacteristicsExample();
			example.createCriteria().andStudygermplasmidEqualTo(studyID);
			mapper.deleteByExample(example);
			session.commit();
		} finally {
			session.close();
		}
	}

	public void addCharacteristics(StudyGermplasmCharacteristics record) {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();

		try {
			StudyGermplasmCharacteristicsMapper mapper = session
					.getMapper(StudyGermplasmCharacteristicsMapper.class);

			mapper.insert(record);
			session.commit();
		} finally {
			session.close();
		}
	}

}
