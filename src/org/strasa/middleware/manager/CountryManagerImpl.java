package org.strasa.middleware.manager;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.CountryMapper;
import org.strasa.middleware.model.Country;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class CountryManagerImpl {

	@WireVariable
	ConnectionFactory connectionFactory;

	public List<Country> getAllCountry(){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		CountryMapper countryMapper = session.getMapper(CountryMapper.class);
		
		try{
			
			return countryMapper.selectByExample(null);
		}finally{
			session.close();
		}
	}
	
	
}
