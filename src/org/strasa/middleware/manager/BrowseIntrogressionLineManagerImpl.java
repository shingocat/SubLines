/**
 *
 * 	Builder: 	Mao Qin
 *	Email:		mqin@ymail.com
 *	Date:		2014 - Jun 24, 2014
 *	Project: 	StrasaWeb
 *  Package: 	org.strasa.middleware.manager
 *	Name:	 BrowserIntrogressionLineManagerImpl.java
 *	
 *
 */
package org.strasa.middleware.manager;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.rosuda.REngine.Rserve.RserveException;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.factory.MySqlSessionFactory;
import org.strasa.middleware.mapper.BrowseIntrogressionLineMapper;
import org.strasa.middleware.model.Germplasm;
import org.strasa.middleware.model.Segment;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class BrowseIntrogressionLineManagerImpl
{
	
	@WireVariable
	ConnectionFactory connectionFactory;
	
//	public List<IntrogressionLine> findAllIntrogressionLine()
//	{
//		SqlSession sqlSession = MySqlSessionFactory.openSession();
//		try{
//			
////			BrowseIntrogressionLineMapper browseIntrogressionLineMapper = sqlSession.getMapper(BrowseIntrogressionLineMapper.class);
//			if(connectionFactory == null)
//			{
//				this.initConnectionFactory();
//			}
//			BrowseIntrogressionLineMapper browseIntrogressionLineMapper = connectionFactory.sqlSessionFactory.openSession().getMapper(BrowseIntrogressionLineMapper.class);
//			return browseIntrogressionLineMapper.findAllIntrogressionLines();
//		} finally
//		{
//			sqlSession.close();
//		}
//	}
	
	public List<Germplasm> findAllGermplasms()
	{
		SqlSession session = MySqlSessionFactory.openSession();
		try
		{
//			BrowseIntrogressionLineMapper mapper = session.getMapper(BrowseIntrogressionLineMapper.class);
			if(connectionFactory == null)
			{
				this.initConnectionFactory();
			}
			BrowseIntrogressionLineMapper mapper = connectionFactory.sqlSessionFactory.openSession().getMapper(BrowseIntrogressionLineMapper.class);
			return mapper.findAllGermplasms();
		} finally
		{
			session.close();
		}
	}
	
	public List<Segment> findAllSegments()
	{
		SqlSession session = MySqlSessionFactory.openSession();
		try
		{
			BrowseIntrogressionLineMapper mapper = session.getMapper(BrowseIntrogressionLineMapper.class);
			return mapper.findAllSegments();
		} finally
		{
			session.close();
		}
	}
	
	public void initConnectionFactory()
	{
		try{
			connectionFactory = new ConnectionFactory();
		} catch (RserveException e)
		{
			e.printStackTrace();
		}
	}
}

