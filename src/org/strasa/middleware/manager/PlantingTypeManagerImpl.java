package org.strasa.middleware.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.PlantingTypeMapper;
import org.strasa.middleware.model.PlantingType;
import org.strasa.middleware.model.PlantingTypeExample;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class PlantingTypeManagerImpl {

	@WireVariable
	ConnectionFactory connectionFactory;

	public PlantingTypeManagerImpl() {
		// TODO Auto-generated constructor stub
	}

	public List<PlantingType> getAllPlantingTypes() {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		PlantingTypeMapper PlantingTypeMapper = session.getMapper(PlantingTypeMapper.class);

		try {
			List<PlantingType> PlantingTypes = PlantingTypeMapper.selectByExample(null);

			return PlantingTypes;

		} finally {
			session.close();
		}

	}

	public PlantingType getPlantingTypeById(int id) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		PlantingTypeMapper PlantingTypeMapper = session.getMapper(PlantingTypeMapper.class);

		try {
			return PlantingTypeMapper.selectByPrimaryKey(id);

		} finally {
			session.close();
		}

	}

	@SuppressWarnings("null")
	public List<String> getAllPlantingTypesAsString() {
		List<String> PlantingTypes = new ArrayList<String>();
		;
		List<PlantingType> PlantingTypeList = getAllPlantingTypes();
		for (PlantingType e : PlantingTypeList) {
			System.out.println(e.getPlanting());
			PlantingTypes.add(e.getPlanting());
		}
		// TODO Auto-generated method stub
		return PlantingTypes;
	}

	public PlantingType getPlantingTypeById(Integer plantingtypeid) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		PlantingTypeMapper PlantingTypeMapper = session.getMapper(PlantingTypeMapper.class);

		try {
			PlantingType PlantingType = PlantingTypeMapper.selectByPrimaryKey(plantingtypeid);

			return PlantingType;

		} finally {
			session.close();
		}
	}

	public PlantingType getPlantingTypeByName(String plantingtype) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		PlantingTypeMapper PlantingTypeMapper = session.getMapper(PlantingTypeMapper.class);

		try {
			PlantingTypeExample example = new PlantingTypeExample();
			example.createCriteria().andPlantingEqualTo(plantingtype);
			List<PlantingType> lstPlantingType = PlantingTypeMapper.selectByExample(example);
			if (lstPlantingType.isEmpty())
				return null;

			return lstPlantingType.get(0);

		} finally {
			session.close();
		}
	}

	public void update(PlantingType value) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		PlantingTypeMapper mapper = session.getMapper(PlantingTypeMapper.class);
		try {
			mapper.updateByPrimaryKey(value);
			session.commit();

		} finally {
			session.close();
		}
	}

	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		PlantingTypeMapper mapper = session.getMapper(PlantingTypeMapper.class);
		try {
			mapper.deleteByPrimaryKey(id);
			session.commit();
		} finally {
			session.close();
		}
	}

	public void addPlantingType(PlantingType record) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		PlantingTypeMapper mapper = session.getMapper(PlantingTypeMapper.class);
		try {
			mapper.insert(record);
			session.commit();

		} finally {
			session.close();
		}
	}
}
