package org.strasa.middleware.manager;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.LocationMapper;
import org.strasa.middleware.mapper.ProgramMapper;
import org.strasa.middleware.mapper.StudyLocationMapper;
import org.strasa.middleware.model.Location;
import org.strasa.middleware.model.LocationExample;
import org.strasa.middleware.model.StudyLocation;
import org.strasa.middleware.model.StudyLocationExample;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class LocationManagerImpl {

	@WireVariable
	ConnectionFactory connectionFactory;
	public Location getLocationByLocationName(String locationName){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		LocationMapper mapper = session.getMapper(LocationMapper.class);
		try{
			LocationExample example = new LocationExample();
			example.createCriteria().andLocationnameEqualTo(locationName);
			if(mapper.selectByExample(example).isEmpty()) return null;
			return mapper.selectByExample(example).get(0);
		}
		finally{
			session.close();
		}
	}
	public Location getLocationById(Integer id){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		LocationMapper mapper = session.getMapper(LocationMapper.class);
		try{
			LocationExample example = new LocationExample();
			example.createCriteria().andIdEqualTo(id);
			if(mapper.selectByExample(example).isEmpty()) return null;
			return mapper.selectByExample(example).get(0);
		}
		finally{
			session.close();
		}
	}
	public List<Location> getAllLocations(){
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		LocationMapper locationMapper = session.getMapper(LocationMapper.class);
		try{
			List<Location> locations = locationMapper.selectByExample(null);
			return locations;
		}finally{
			session.close();
		}

	}
	public void addLocation(Location locationModel) {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		LocationMapper locationMapper = session.getMapper(LocationMapper.class);
		try{
			locationMapper.insert(locationModel);
		}
		finally{
			session.commit();
			session.close();
		}
	}
	public List<Location> getLocationByCountryName(String countryName) {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		LocationMapper mapper = session.getMapper(LocationMapper.class);
		try{
			LocationExample example = new LocationExample();
			example.createCriteria().andCountryEqualTo(countryName);
			if(mapper.selectByExample(example).isEmpty()) return null;
			return mapper.selectByExample(example);
		}
		finally{
			session.close();
		}
	}
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		LocationMapper mapper = session.getMapper(LocationMapper.class);


		try{
			mapper.deleteByPrimaryKey(id);
			session.commit();
		}
		finally{
			session.close();
		}
	}
	public void updateLocation(Location location) {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		LocationMapper locationMapper = session.getMapper(LocationMapper.class);
		try{	
			locationMapper.updateByPrimaryKey(location);
			session.commit();

		}finally{
			session.close();
		}

	}

}

