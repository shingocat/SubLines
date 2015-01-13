/**
 *
 * 	Builder: 	Mao Qin
 *	Email:		mqin@ymail.com
 *	Date:		2014 - Jun 24, 2014
 *	Project: 	StrasaWeb
 *  Package: 	org.strasa.middleware.factory
 *	Name:	 MySqlSessionFactory.java
 *	
 *
 */
package org.strasa.middleware.factory;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.strasa.middleware.manager.BrowseIntrogressionLineManagerImpl;

public class MySqlSessionFactory
{
	private static SqlSessionFactory sqlSessionFactory;
	
	public static SqlSessionFactory getSqlSessionFactory()
	{
		if(sqlSessionFactory == null)
		{
			InputStream inputStream;
			try
			{
				inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//				sqlSessionFactory.getConfiguration().addMapper(BrowseIntrogressionLineManagerImpl.class);
			} catch(IOException e)
			{
				e.printStackTrace();
				throw new RuntimeException(e.getCause());
			}
		}
		return sqlSessionFactory;
	}
	
	public static SqlSession openSession()
	{
		return getSqlSessionFactory().openSession();
	}
}

