/**
 * Builder: Mao Qin
 * Email: mqin@ymail.com
 * Date: 2014 - Jun 28, 2014
 * Project: StrasaWeb
 * Package: org.strasa.middleware.manager
 * Name: GermplasmSegmentMangerImpl.java
 */
package org.strasa.middleware.manager;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.GermplasmMapper;
import org.strasa.middleware.mapper.GermplasmSegmentMapper;
import org.strasa.middleware.model.GermplasmSegment;
import org.strasa.middleware.model.GermplasmSegmentExample;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class GermplasmSegmentManagerImpl
{
	@WireVariable
	ConnectionFactory connectionFactory;
	
	public List<GermplasmSegment> getGermplasmSegmentByGermplasmId(
	        Integer germplasm_id)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		try
		{
			GermplasmSegmentMapper mapper = session
			        .getMapper(GermplasmSegmentMapper.class);
			GermplasmSegmentExample example = new GermplasmSegmentExample();
			example.or().andGermplasmIdEqualTo(germplasm_id);
			List<GermplasmSegment> lstGermplasmSegment = mapper
			        .selectByExample(example);
			if (lstGermplasmSegment.isEmpty())
				return null;
			return lstGermplasmSegment;
		} finally
		{
			session.close();
		}
	}
	
	public List<GermplasmSegment> getGermplasmSegmentBySegmentNumber(
	        Integer segmentNumber)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		try
		{
			GermplasmSegmentMapper mapper = session
			        .getMapper(GermplasmSegmentMapper.class);
			GermplasmSegmentExample example = new GermplasmSegmentExample();
			example.or().andGermplasmSegmentNumberEqualTo(segmentNumber);
			if (mapper.selectByExample(example).isEmpty())
				return null;
			return mapper.selectByExample(example);
		} finally
		{
			session.close();
		}
	}
	
	public void updateGermplasmSegement(GermplasmSegment gs)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		try
		{
			GermplasmSegmentMapper mapper = session
			        .getMapper(GermplasmSegmentMapper.class);
			mapper.updateByPrimaryKey(gs);
			session.commit();
		} finally
		{
			session.close();
		}
		
	}
	
	public GermplasmSegment getGermplasmSegmentByGermplasmIdAndSegmentId(
	        Integer germplasmId, Integer segmentId)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		try
		{
			GermplasmSegmentMapper mapper = session
			        .getMapper(GermplasmSegmentMapper.class);
			GermplasmSegmentExample example = new GermplasmSegmentExample();
			example.or().andGermplasmIdEqualTo(germplasmId)
			        .andSegmentIdEqualTo(segmentId);
			List<GermplasmSegment> lstGermplasmSegment = mapper
			        .selectByExample(example);
			if (lstGermplasmSegment.isEmpty())
				return null;
			return lstGermplasmSegment.get(0);
		} finally
		{
			session.close();
		}
		
	}
	
//	Not delete segment info right now, but in the really world if the segment didn't connect any introgression line germplasm
//	it should be to delete also.
//	if having time, implement this function and requirement
	public void deleteGermplasmSegmentByGermplasmId(Integer germplasmId)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		try
		{
			GermplasmSegmentMapper mapper = session
			        .getMapper(GermplasmSegmentMapper.class);
			GermplasmSegmentExample example = new GermplasmSegmentExample();
			example.or().andGermplasmIdEqualTo(germplasmId);
			mapper.deleteByExample(example);
			session.commit();
		} finally
		{
			session.close();
		}
		
	}
	
	public void addGermplasmSegment(Integer germplasmId, Integer segmentId,
	        Integer segmentNumber, String isHomogenous)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		try
		{
			GermplasmSegmentMapper mapper = session
			        .getMapper(GermplasmSegmentMapper.class);
			GermplasmSegment record = new GermplasmSegment();
			record.setGermplasmId(germplasmId);
			record.setSegmentId(segmentId);
			record.setGermplasmSegmentNumber(segmentNumber);
			record.setIshomogenous(isHomogenous);
			mapper.insert(record);
			session.commit();
		} finally
		{
			session.close();
		}
	}
	
	public void addGermplasmSegment(GermplasmSegment germplasmSegment)
	{
		addGermplasmSegment(germplasmSegment.getGermplasmId(),
		        germplasmSegment.getSegmentId(),
		        germplasmSegment.getGermplasmSegmentNumber(),
		        germplasmSegment.getIshomogenous());
	}
	
	public List<GermplasmSegment> getGermplasmSegmentBySegmentId(
	        Integer segmentId)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		try
		{
			GermplasmSegmentMapper mapper = session
			        .getMapper(GermplasmSegmentMapper.class);
			GermplasmSegmentExample example = new GermplasmSegmentExample();
			example.createCriteria().andSegmentIdEqualTo(segmentId);
			return mapper.selectByExample(example);
		} finally
		{
			session.close();
		}
	}
}
