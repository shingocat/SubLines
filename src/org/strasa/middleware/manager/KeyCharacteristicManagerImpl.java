package org.strasa.middleware.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.GermplasmCharacteristicsMapper;
import org.strasa.middleware.mapper.KeyAbioticMapper;
import org.strasa.middleware.mapper.KeyBioticMapper;
import org.strasa.middleware.mapper.KeyGrainQualityMapper;
import org.strasa.middleware.mapper.KeyMajorGenesMapper;
import org.strasa.middleware.model.GermplasmCharacteristics;
import org.strasa.middleware.model.KeyAbiotic;
import org.strasa.middleware.model.KeyBiotic;
import org.strasa.middleware.model.KeyGrainQuality;
import org.strasa.middleware.model.KeyMajorGenes;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class KeyCharacteristicManagerImpl {

	@WireVariable
	ConnectionFactory connectionFactory;

	public void getGermplasmCharacteristicById(GermplasmCharacteristics record) {

		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		GermplasmCharacteristicsMapper GermplasmCharacteristicsMapper = session.getMapper(GermplasmCharacteristicsMapper.class);

		try {
			GermplasmCharacteristicsMapper.insert(record);
			session.commit();

		} finally {
			session.close();
		}

	}

	public List<KeyBiotic> getAllBiotic() {

		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		KeyBioticMapper mapper = session.getMapper(KeyBioticMapper.class);
		try {
			return mapper.selectByExample(null);
		} finally {
			session.close();
		}
	}

	public List<KeyAbiotic> getAllAbiotic() {

		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		KeyAbioticMapper mapper = session.getMapper(KeyAbioticMapper.class);
		try {
			return mapper.selectByExample(null);
		} finally {
			session.close();
		}
	}

	public List<KeyGrainQuality> getAllGrainQuality() {

		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		KeyGrainQualityMapper mapper = session.getMapper(KeyGrainQualityMapper.class);
		try {
			return mapper.selectByExample(null);
		} finally {
			session.close();
		}
	}

	public List<KeyMajorGenes> getAllMajorGenes() {

		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		KeyMajorGenesMapper mapper = session.getMapper(KeyMajorGenesMapper.class);
		try {
			return mapper.selectByExample(null);
		} finally {
			session.close();
		}
	}

	public List<String> getAllAbioticAsString() {

		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		KeyAbioticMapper mapper = session.getMapper(KeyAbioticMapper.class);
		try {
			ArrayList<String> strReturn = new ArrayList<String>();
			for (KeyAbiotic key : mapper.selectByExample(null)) {
				strReturn.add(key.getValue());
			}

			return strReturn;
		} finally {
			session.close();
		}
	}

	public List<String> getAllBioticAsString() {

		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		KeyBioticMapper mapper = session.getMapper(KeyBioticMapper.class);
		try {
			List<String> strReturn = new ArrayList<String>();
			for (KeyBiotic key : mapper.selectByExample(null)) {
				strReturn.add(key.getValue());
			}

			return strReturn;
		} finally {
			session.close();
		}
	}

	public List<String> getAllGrainQualityAsString() {

		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		KeyGrainQualityMapper mapper = session.getMapper(KeyGrainQualityMapper.class);
		try {
			List<String> strReturn = new ArrayList<String>();
			for (KeyGrainQuality key : mapper.selectByExample(null)) {
				strReturn.add(key.getValue());
			}

			return strReturn;
		} finally {
			session.close();
		}
	}

	public List<String> getAllMajorGenesAsString() {

		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		KeyMajorGenesMapper mapper = session.getMapper(KeyMajorGenesMapper.class);
		try {
			List<String> strReturn = new ArrayList<String>();
			for (KeyMajorGenes key : mapper.selectByExample(null)) {
				strReturn.add(key.getValue());
			}

			return strReturn;
		} finally {
			session.close();
		}
	}

	public void updateBiotic(KeyBiotic record) {
		// TODO Auto-generated method stub

		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		KeyBioticMapper mapper = session.getMapper(KeyBioticMapper.class);
		try {
			mapper.updateByPrimaryKey(record);
			session.commit();

		} finally {
			session.close();
		}

	}

	public void deleteBioticById(Integer id) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		KeyBioticMapper mapper = session.getMapper(KeyBioticMapper.class);
		try {
			mapper.deleteByPrimaryKey(id);
			session.commit();
		} finally {
			session.close();
		}
	}

	public void addBioticKey(KeyBiotic record) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		KeyBioticMapper mapper = session.getMapper(KeyBioticMapper.class);

		try {
			mapper.insert(record);
			session.commit();

		} finally {
			session.close();
		}
	}

	public void addAbioticKey(KeyAbiotic record) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		KeyAbioticMapper mapper = session.getMapper(KeyAbioticMapper.class);

		try {
			mapper.insert(record);
			session.commit();

		} finally {
			session.close();
		}
	}

	public void updateAbiotic(KeyAbiotic record) {
		// TODO Auto-generated method stub

		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		KeyAbioticMapper mapper = session.getMapper(KeyAbioticMapper.class);
		try {
			mapper.updateByPrimaryKey(record);
			session.commit();

		} finally {
			session.close();
		}
	}

	public void deleteAbioticById(Integer id) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		KeyAbioticMapper mapper = session.getMapper(KeyAbioticMapper.class);
		try {
			mapper.deleteByPrimaryKey(id);
			session.commit();
		} finally {
			session.close();
		}
	}

	public void deleteGrainQualityById(Integer id) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		KeyGrainQualityMapper mapper = session.getMapper(KeyGrainQualityMapper.class);
		try {
			mapper.deleteByPrimaryKey(id);
			session.commit();
		} finally {
			session.close();
		}
	}

	public void addGrainQualityKey(KeyGrainQuality record) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		KeyGrainQualityMapper mapper = session.getMapper(KeyGrainQualityMapper.class);

		try {
			mapper.insert(record);
			session.commit();

		} finally {
			session.close();
		}
	}

	public void deleteMajorGenesById(Integer id) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		KeyMajorGenesMapper mapper = session.getMapper(KeyMajorGenesMapper.class);
		try {
			mapper.deleteByPrimaryKey(id);
			session.commit();
		} finally {
			session.close();
		}
	}

	public void addMajorGeneKey(KeyMajorGenes record) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		KeyMajorGenesMapper mapper = session.getMapper(KeyMajorGenesMapper.class);

		try {
			mapper.insert(record);
			session.commit();

		} finally {
			session.close();
		}

	}

}
