package org.strasa.middleware.manager;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.SoilTypeMapper;
import org.strasa.middleware.model.SoilType;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class SoilTypeManagerImpl {

	@WireVariable
	ConnectionFactory connectionFactory;

	public ArrayList<String> getAllSoilType() {
		ArrayList<String> returnVal = new ArrayList<String>();

		SqlSession session = connectionFactory.sqlSessionFactory.openSession();

		try {
			SoilTypeMapper mapper = session.getMapper(SoilTypeMapper.class);
			for (SoilType soil : mapper.selectByExample(null)) {
				returnVal.add(soil.getType());
			}
			return returnVal;
		} finally {
			session.close();
		}

	}
}