/**
 * Builder: Mao Qin
 * Email: mqin@ymail.com
 * Date: 2014 - Jun 28, 2014
 * Project: StrasaWeb
 * Package: org.strasa.middleware.manager
 * Name: SegmentMangerImpl.java
 */
package org.strasa.middleware.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.rosuda.REngine.Rserve.RserveException;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.BrowseSegmentMapper;
import org.strasa.middleware.mapper.SegmentMapper;
import org.strasa.middleware.model.Segment;
import org.strasa.middleware.model.SegmentExample;
import org.strasa.middleware.model.SegmentExample.Criteria;
import org.strasa.middleware.util.ConditionAndValue;
import org.strasa.web.managegermplasm.view.pojos.SegmentExt;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.SimpleCategoryModel;

import com.mysql.jdbc.StringUtils;

public class SegmentManagerImpl
{
	@WireVariable
	ConnectionFactory connectionFactory;
	
	public List<Segment> getAllSegments()
	{
//		if (connectionFactory == null)
//			getConnectionFactory();
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		try
		{
			SegmentMapper mapper = session.getMapper(SegmentMapper.class);
			return mapper.selectByExample(new SegmentExample());
		} finally
		{
			session.close();
		}
	}
	
	public Segment getSegmentBySegmentExtWithoutConsiderId(SegmentExt segmentExt)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		try
		{
			SegmentMapper mapper = session.getMapper(SegmentMapper.class);
			SegmentExample example = new SegmentExample();
			// Modify by QIN MAO on Jan 9, 2015, to fix null pointer error
			// COMMENT THIS FOR NULL POINTER ERROR, IF HAVING TIME CHECKING HOW TO FIX IT
//			example.or().andChromosomeEqualTo(segmentExt.getChromosome())
//			        .andRecurrentParentEqualTo(segmentExt.getRecurrentParent())
//			        .andDonorParentEqualTo(segmentExt.getDonorParent())
//			        .andPosition1EqualTo(segmentExt.getPosition1())
//			        .andPosition2EqualTo(segmentExt.getPosition2())
//			        .andPosition3EqualTo(segmentExt.getPosition3())
//			        .andPosition4EqualTo(segmentExt.getPosition4())
//			        .andPhysicalStartEqualTo(segmentExt.getPhysicalStart())
//			        .andPhysicalEndEqualTo(segmentExt.getPhysicalEnd())
//			        .andEstimatedLengthEqualTo(segmentExt.getEstimatedLength())
//			        .andMinimumLengthEqualTo(segmentExt.getMinimumLength())
//			        .andMaximumLengthEqualTo(segmentExt.getMaximumLength())
//			        .andDescriptionEqualTo(segmentExt.getDescription());
			// define the variables for example 
			// if the string value is missing, set to be NULL
			// if the numeric value is missing, set to be -1
			Integer chr;
			String recurrent;
			String donor;
			BigDecimal p1;
			BigDecimal p2;
			BigDecimal p3;
			BigDecimal p4;
			Integer start;
			Integer end;
			BigDecimal estimatedLength;
			BigDecimal minimumLength;
			BigDecimal maximumLength;
			String desc;
			
			if(segmentExt.getChromosome() == null)
				chr = -1;
			else 
				chr = segmentExt.getChromosome();
			if(segmentExt.getRecurrentParent() == null || segmentExt.getRecurrentParent().length() == 0 || segmentExt.getRecurrentParent().equals("UNKNOWN"))
				recurrent = "NULL";
			else
				recurrent = segmentExt.getRecurrentParent();
			if(segmentExt.getDonorParent() == null || segmentExt.getDonorParent().length() == 0 || segmentExt.getDonorParent().equalsIgnoreCase("UNKNOWN"))
				donor = "NULL";
			else
				donor = segmentExt.getDonorParent();
			if(segmentExt.getPosition1() == null)
				p1 = BigDecimal.valueOf(-1);
			else
				p1 = segmentExt.getPosition1();
			if(segmentExt.getPosition2() == null)
				p2 = BigDecimal.valueOf(-1);
			else
				p2 = segmentExt.getPosition2();
			if(segmentExt.getPosition3() == null)
				p3 = BigDecimal.valueOf(-1);
			else
				p3 = segmentExt.getPosition3();
			if(segmentExt.getPosition4() == null)
				p4 = BigDecimal.valueOf(-1);
			else
				p4 = segmentExt.getPosition4();
			if(segmentExt.getPhysicalStart() == null)
				start = Integer.valueOf(-1);
			else
				start = segmentExt.getPhysicalStart();
			if(segmentExt.getPhysicalEnd() ==  null)
				end = Integer.valueOf(-1);
			else
				end = segmentExt.getPhysicalEnd();
			if(segmentExt.getEstimatedLength() == null)
				estimatedLength = BigDecimal.valueOf(-1);
			else
				estimatedLength = segmentExt.getEstimatedLength();
			if(segmentExt.getMinimumLength() == null)
				minimumLength = BigDecimal.valueOf(-1);
			else
				minimumLength = segmentExt.getMinimumLength();
			if(segmentExt.getMaximumLength() == null)
				maximumLength = BigDecimal.valueOf(-1);
			else
				maximumLength = segmentExt.getMaximumLength();
			if(segmentExt.getDescription() == null || segmentExt.getDescription().length() == 0 || segmentExt.getDescription().equalsIgnoreCase("UNKNOWN"))
				desc = "NULL";
			else
				desc = segmentExt.getDescription();
			
			example.createCriteria().andChromosomeEqualTo(chr).andRecurrentParentEqualTo(recurrent).andDonorParentEqualTo(donor)
				.andPosition1EqualTo(p1).andPosition2EqualTo(p2).andPosition3EqualTo(p3).andPosition4EqualTo(p4)
				.andPhysicalEndEqualTo(end).andPhysicalStartEqualTo(start).andEstimatedLengthEqualTo(estimatedLength)
				.andMinimumLengthEqualTo(minimumLength).andMaximumLengthEqualTo(maximumLength).andDescriptionEqualTo(desc);
			
			
//			if(segmentExt.getChromosome() != null && segmentExt.getChromosome().length() != 0 && !segmentExt.getChromosome().equalsIgnoreCase("UNKNOWN"))
//				example.or().andChromosomeEqualTo(segmentExt.getChromosome());
//			if(segmentExt.getRecurrentParent() != null && segmentExt.getRecurrentParent().length() != 0 && !segmentExt.getChromosome().equalsIgnoreCase("UNKNOWN"))
//			    example.or().andRecurrentParentEqualTo(segmentExt.getRecurrentParent());
//			if(segmentExt.getDonorParent() != null && segmentExt.getDonorParent().length() != 0 && segmentExt.getDonorParent().equalsIgnoreCase("UNKNOWN"))
//			    example.or().andDonorParentEqualTo(segmentExt.getDonorParent());
//			if(segmentExt.getPosition1() != null)     
//				example.or().andPosition1EqualTo(segmentExt.getPosition1());
//			if(segmentExt.getPosition2() != null)      
//				example.or().andPosition2EqualTo(segmentExt.getPosition2());
//			if(segmentExt.getPosition3() != null)
//				example.or().andPosition3EqualTo(segmentExt.getPosition3());
//			if(segmentExt.getPosition4() != null)    
//				example.or().andPosition4EqualTo(segmentExt.getPosition4());
//			if(segmentExt.getPhysicalStart() != null)    
//				example.or().andPhysicalStartEqualTo(segmentExt.getPhysicalStart());
//			if(segmentExt.getPhysicalEnd() != null)        
//				example.or().andPhysicalEndEqualTo(segmentExt.getPhysicalEnd());
//			if(segmentExt.getEstimatedLength() != null)        
//				example.or().andEstimatedLengthEqualTo(segmentExt.getEstimatedLength());
//			if(segmentExt.getMinimumLength() != null)
//				example.or().andMinimumLengthEqualTo(segmentExt.getMinimumLength());
//			if(segmentExt.getMaximumLength() != null)        
//				example.or().andMaximumLengthEqualTo(segmentExt.getMaximumLength());
//			if(segmentExt.getDescription() != null && segmentExt.getDescription().length() != 0 && segmentExt.getDescription().equalsIgnoreCase("UNKNOWN"))
//				example.or().andDescriptionEqualTo(segmentExt.getDescription());
			List<Segment> segments = mapper.selectByExample(example);
			if (segments == null)
				return null;
			if (segments.isEmpty())
				return null;
			return segments.get(0);
		} finally
		{
			session.close();
		}
	}
	
	public Segment getSegmentBySegmentId(Integer segment_id)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		try
		{
			SegmentMapper mapper = session.getMapper(SegmentMapper.class);
			SegmentExample example = new SegmentExample();
			example.or().andSegmentIdEqualTo(segment_id);
			List<Segment> segments = mapper.selectByExample(example);
			if(segments == null)
				return null;
			return segments.get(0);
		} finally
		{
			session.close();
		}
	}
	
	public List<Segment> getSegmentBySegmentIds(List<Integer> segment_ids)
	{
		
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		try
		{
			SegmentMapper mapper = session.getMapper(SegmentMapper.class);
			SegmentExample example = new SegmentExample();
			example.or().andSegmentIdIn(segment_ids);
			List<Segment> lstSegment = mapper.selectByExample(example);
//			for (Segment s : lstSegment)
//			{
//				System.out.println(s.toString());
//			}
			return lstSegment;
		} finally
		{
			session.close();
		}
	}
	
	public List<Segment> getAllSegmentByRecurrentName(String recurrentName)
	{
//		if (connectionFactory == null)
//			getConnectionFactory();
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		try
		{
			SegmentMapper mapper = session.getMapper(SegmentMapper.class);
			SegmentExample example = new SegmentExample();
			example.or().andRecurrentParentLike("%" + recurrentName + "%");
			return mapper.selectByExample(example);
		} finally
		{
			session.close();
		}
	}
	
	public List<Segment> getSegmentByDynamicSQL(HashMap<String, Object> params)
	{
//		if(connectionFactory == null)
//			getConnectionFactory();
		
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		SegmentMapper mapper = session.getMapper(SegmentMapper.class);
		SegmentExample example = new SegmentExample();
		Criteria criteria = example.createCriteria();
		Integer chromosome = null;
//		String recurrentParent = null;
		String donorParent = null;
		String marker = null;
		String markerCondition = null;
		Double estimatedLength = null;
		String estimatedLengthCondition = null;
		String geneticalStartCondition = null;
		Double geneticalStart = null;
		String geneticalEndCondition = null;
		Double geneticalEnd = null;
//		Double position1 = null;
//		String position1Condition = null;
//		Double position2 = null;
//		String position2Condition = null;
//		Double position3 = null;
//		String position3Condition = null;
//		Double position4 = null;
//		String position4Condition = null;
		Integer physicalStart = null;
		String physicalStartCondition = null;
		Integer physicalEnd = null;
		String physicalEndCondition = null;
		
		if (params.size() == 0)
		{
			try
			{
				return mapper.selectByExample(null);
			} finally
			{
				session.close();
			}
		} else
		{
			if (params.containsKey("Chromosome"))
			{
				chromosome = Integer.valueOf(String.valueOf(params.get("Chromosome")));
				criteria.andChromosomeEqualTo(chromosome);
			}
//		if (params.containsKey("RecurrentParent"))
//		{
//			recurrentParent = (String) params.get("RecurrentParent");
//		}
			if (params.containsKey("DonorParent"))
			{
				donorParent = (String) params.get("DonorParent");
//			criteria.andDonorParentLike("%" + donorParent + "%");
				criteria.andDonorParentLikeInsensitive("%" + donorParent + "%");
			}
			if (params.containsKey("SegmentMarker"))
			{
				HashMap<String, String> temp = (HashMap<String, String>) params
				        .get("SegmentMarker");
				if (temp.containsKey("Include"))
				{
					marker = temp.get("Include");
					markerCondition = "Include";
//				criteria.andDescriptionLike(marker);
					criteria.andDescriptionLikeInsensitive("%" + marker + "%");
				} else
				{
					marker = temp.get("Exclude");
					markerCondition = "Exclude";
					criteria.andDescriptionNotLike("%" + marker + "%");
				}
			}
			if (params.containsKey("EstimatedLength"))
			{
				HashMap<String, Double> temp = (HashMap<String, Double>) params
				        .get("EstimatedLength");
				if (temp.containsKey("="))
				{
					estimatedLength = temp.get("=");
					estimatedLengthCondition = "=";
					criteria.andEstimatedLengthEqualTo(BigDecimal
					        .valueOf(estimatedLength));
				} else if (temp.containsKey(">"))
				{
					estimatedLength = temp.get(">");
					estimatedLengthCondition = ">";
					criteria.andEstimatedLengthGreaterThan(BigDecimal
					        .valueOf(estimatedLength));
				} else if (temp.containsKey(">="))
				{
					estimatedLength = temp.get(">=");
					estimatedLengthCondition = ">=";
					criteria.andEstimatedLengthGreaterThanOrEqualTo(BigDecimal
					        .valueOf(estimatedLength));
				} else if (temp.containsKey("<"))
				{
					estimatedLength = temp.get("<");
					estimatedLengthCondition = "<";
					criteria.andEstimatedLengthLessThan(BigDecimal
					        .valueOf(estimatedLength));
				} else if (temp.containsKey("<="))
				{
					estimatedLength = temp.get("<=");
					estimatedLengthCondition = "<=";
					criteria.andEstimatedLengthLessThanOrEqualTo(BigDecimal
					        .valueOf(estimatedLength));
				}
			}
//		if (params.containsKey("Position1"))
//		{
//			HashMap<String, Double> temp = (HashMap<String, Double>) params
//			        .get("Position1");
//			if (temp.containsKey("="))
//			{
//				position1 = temp.get("=");
//				position1Condition = "=";
//			} else if (temp.containsKey(">"))
//			{
//				position1 = temp.get(">");
//				position1Condition = ">";
//			} else if (temp.containsKey(">="))
//			{
//				position1 = temp.get(">=");
//				position1Condition = ">=";
//			} else if (temp.containsKey("<"))
//			{
//				position1 = temp.get("<");
//				position1Condition = "<";
//			} else if (temp.containsKey("<="))
//			{
//				position1 = temp.get("<=");
//				position1Condition = "<=";
//			}
//		}
//		if (params.containsKey("Position2"))
//		{
//			HashMap<String, Double> temp = (HashMap<String, Double>) params
//			        .get("Position2");
//			if (temp.containsKey("="))
//			{
//				position2 = temp.get("=");
//				position2Condition = "=";
//			} else if (temp.containsKey(">"))
//			{
//				position2 = temp.get(">");
//				position2Condition = ">";
//			} else if (temp.containsKey(">="))
//			{
//				position2 = temp.get(">=");
//				position2Condition = ">=";
//			} else if (temp.containsKey("<"))
//			{
//				position2 = temp.get("<");
//				position2Condition = "<";
//			} else if (temp.containsKey("<="))
//			{
//				position2 = temp.get("<=");
//				position2Condition = "<=";
//			}
//		}
//		if (params.containsKey("Position3"))
//		{
//			HashMap<String, Double> temp = (HashMap<String, Double>) params
//			        .get("Position3");
//			if (temp.containsKey("="))
//			{
//				position3 = temp.get("=");
//				position3Condition = "=";
//			} else if (temp.containsKey(">"))
//			{
//				position3 = temp.get(">");
//				position3Condition = ">";
//			} else if (temp.containsKey(">="))
//			{
//				position3 = temp.get(">=");
//				position3Condition = ">=";
//			} else if (temp.containsKey("<"))
//			{
//				position3 = temp.get("<");
//				position3Condition = "<";
//			} else if (temp.containsKey("<="))
//			{
//				position3 = temp.get("<=");
//				position3Condition = "<=";
//			}
//		}
//		if (params.containsKey("Position4"))
//		{
//			HashMap<String, Double> temp = (HashMap<String, Double>) params
//			        .get("Position4");
//			if (temp.containsKey("="))
//			{
//				position4 = temp.get("=");
//				position4Condition = "=";
//			} else if (temp.containsKey(">"))
//			{
//				position4 = temp.get(">");
//				position4Condition = ">";
//			} else if (temp.containsKey(">="))
//			{
//				position4 = temp.get(">=");
//				position4Condition = ">=";
//			} else if (temp.containsKey("<"))
//			{
//				position4 = temp.get("<");
//				position4Condition = "<";
//			} else if (temp.containsKey("<="))
//			{
//				position4 = temp.get("<=");
//				position4Condition = "<=";
//			}
//		}
			if (params.containsKey("GeneticalStart"))
			{
				HashMap<String, Double> temp = (HashMap<String, Double>) params
				        .get("GeneticalStart");
				if (temp.containsKey("="))
				{
					geneticalStart = temp.get("=");
					geneticalStartCondition = "=";
					criteria.andPosition2EqualTo(BigDecimal
					        .valueOf(geneticalStart));
				} else if (temp.containsKey(">"))
				{
					geneticalStart = temp.get(">");
					geneticalStartCondition = ">";
					criteria.andPosition2GreaterThan(BigDecimal
					        .valueOf(geneticalStart));
				} else if (temp.containsKey(">="))
				{
					geneticalStart = temp.get(">=");
					geneticalStartCondition = ">=";
					criteria.andPosition2GreaterThanOrEqualTo(BigDecimal
					        .valueOf(geneticalStart));
				} else if (temp.containsKey("<"))
				{
					geneticalStart = temp.get("<");
					geneticalStartCondition = "<";
					criteria.andPosition2LessThan(BigDecimal
					        .valueOf(geneticalStart));
				} else if (temp.containsKey("<="))
				{
					geneticalStart = temp.get("<=");
					geneticalStartCondition = "<=";
					criteria.andPosition2LessThanOrEqualTo(BigDecimal
					        .valueOf(geneticalStart));
				}
			}
			if (params.containsKey("GeneticalEnd"))
			{
				HashMap<String, Double> temp = (HashMap<String, Double>) params
				        .get("GeneticalEnd");
				if (temp.containsKey("="))
				{
					geneticalEnd = temp.get("=");
					geneticalEndCondition = "=";
					criteria.andPosition3EqualTo(BigDecimal
					        .valueOf(geneticalEnd));
				} else if (temp.containsKey(">"))
				{
					geneticalEnd = temp.get(">");
					geneticalEndCondition = ">";
					criteria.andPosition3GreaterThan(BigDecimal
					        .valueOf(geneticalEnd));
				} else if (temp.containsKey(">="))
				{
					geneticalEnd = temp.get(">=");
					geneticalEndCondition = ">=";
					criteria.andPosition3GreaterThanOrEqualTo(BigDecimal
					        .valueOf(geneticalEnd));
				} else if (temp.containsKey("<"))
				{
					geneticalEnd = temp.get("<");
					geneticalEndCondition = "<";
					criteria.andPosition3LessThan(BigDecimal
					        .valueOf(geneticalEnd));
				} else if (temp.containsKey("<="))
				{
					geneticalEnd = temp.get("<=");
					geneticalEndCondition = "<=";
					criteria.andPosition3LessThanOrEqualTo(BigDecimal
					        .valueOf(geneticalEnd));
				}
			}
			if (params.containsKey("PhysicalStart"))
			{
				HashMap<String, Integer> temp = (HashMap<String, Integer>) params
				        .get("PhysicalStart");
				if (temp.containsKey("="))
				{
					physicalStart = temp.get("=");
					physicalStartCondition = "=";
					criteria.andPhysicalStartEqualTo(physicalStart);
				} else if (temp.containsKey(">"))
				{
					physicalStart = temp.get(">");
					physicalStartCondition = ">";
					criteria.andPhysicalStartGreaterThan(physicalStart);
				} else if (temp.containsKey(">="))
				{
					physicalStart = temp.get(">=");
					physicalStartCondition = ">=";
					criteria.andPhysicalStartGreaterThanOrEqualTo(physicalStart);
				} else if (temp.containsKey("<"))
				{
					physicalStart = temp.get("<");
					physicalStartCondition = "<";
					criteria.andPhysicalStartLessThan(physicalStart);
				} else if (temp.containsKey("<="))
				{
					physicalStart = temp.get("<=");
					physicalStartCondition = "<=";
					criteria.andPhysicalStartLessThanOrEqualTo(physicalStart);
				}
			}
			if (params.containsKey("PhysicalEnd"))
			{
				HashMap<String, Integer> temp = (HashMap<String, Integer>) params
				        .get("PhysicalEnd");
				if (temp.containsKey("="))
				{
					physicalEnd = temp.get("=");
					physicalEndCondition = "=";
					criteria.andPhysicalEndEqualTo(physicalEnd);
				} else if (temp.containsKey(">"))
				{
					physicalEnd = temp.get(">");
					physicalEndCondition = ">";
					criteria.andPhysicalEndGreaterThan(physicalEnd);
				} else if (temp.containsKey(">="))
				{
					physicalEnd = temp.get(">=");
					physicalEndCondition = ">=";
					criteria.andPhysicalEndGreaterThanOrEqualTo(physicalEnd);
				} else if (temp.containsKey("<"))
				{
					physicalEnd = temp.get("<");
					physicalEndCondition = "<";
					criteria.andPhysicalEndLessThan(physicalEnd);
				} else if (temp.containsKey("<="))
				{
					physicalEnd = temp.get("<=");
					physicalEndCondition = "<=";
					criteria.andPhysicalEndLessThanOrEqualTo(physicalEnd);
				}
			}
//			System.out.println("chromosome is "
//			        + chromosome
////				+ " , recurrent is " + recurrentParent 
//			        + " , donor is "
//			        + donorParent
//			        + " marker is "
//			        + markerCondition
//			        + " "
//			        + marker
//			        + " estimated length "
//			        + estimatedLengthCondition
//			        + " "
//			        + estimatedLength
////		        + " position1 is " + position1Condition + position1 
////		        + " position2 is " + position2Condition + position2
////		        + " position3 is " + position3Condition + position3
////		        + " position4 is " + position4Condition + position4
//			        + " Genetical start is " + geneticalStartCondition + " "
//			        + geneticalStart + " Genetical End is "
//			        + geneticalEndCondition + " " + geneticalEnd
//			        + " Physical start is " + physicalStartCondition + " "
//			        + physicalStart + " Physical End is "
//			        + physicalEndCondition + " " + physicalEnd
//			
//			);
			
//		for(Criteria c :example.getOredCriteria())
//			System.out.println(c);
			try
			{
				List<Segment> lstSegment = mapper.selectByExample(example);
				return lstSegment;
			} finally
			{
				session.close();
				
			}
		}
	}
	
	public int updateSegmentFromSegmentExt(SegmentExt segment)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		SegmentMapper mapper = session.getMapper(SegmentMapper.class);
		SegmentExample example = new SegmentExample();
		
		try
		{
			example.or().andSegmentIdEqualTo(segment.getSegmentId());
			mapper.updateByExampleSelective(segment, example);
			session.commit();
		} finally
		{
			session.close();
		}
		return segment.getSegmentId();
	}
	
	public void updateSegmentBySegmentList(List<SegmentExt> segments)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		SegmentMapper mapper = session.getMapper(SegmentMapper.class);
		try
		{
			for (Segment s : segments)
			{
				mapper.updateByPrimaryKey(s);
			}
			session.commit();
		} finally
		{
			session.close();
		}
	}
	
	public void addSegmentNoRepeat(SegmentExt segmentExt)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		Segment segment;
		try
		{
			SegmentMapper mapper = session.getMapper(SegmentMapper.class);
			SegmentExample example = new SegmentExample();
			// Modify by QIN MAO on Jan 9, 2015, to fix null pointer error when some value is null or unknown or unspecify
			// comment this now, and if having time, thinking how to fix the null value problem
			// and set string value if null or unknown to NULL
			// and set numeric value if null or unknown to -1
			// and set logical value if null or unknown to TRUE
//			if(segmentExt.getChromosome() != null && segmentExt.getChromosome().length() != 0 && !segmentExt.getChromosome().equalsIgnoreCase("UNKNOWN"))
//				example.or().andChromosomeEqualTo(segmentExt.getChromosome());
//			if(segmentExt.getRecurrentParent() != null && segmentExt.getRecurrentParent().length() != 0 && !segmentExt.getChromosome().equalsIgnoreCase("UNKNOWN"))
//			    example.or().andRecurrentParentEqualTo(segmentExt.getRecurrentParent());
//			if(segmentExt.getDonorParent() != null && segmentExt.getDonorParent().length() != 0 && segmentExt.getDonorParent().equalsIgnoreCase("UNKNOWN"))
//			    example.or().andDonorParentEqualTo(segmentExt.getDonorParent());
//			if(segmentExt.getPosition1() != null)     
//				example.or().andPosition1EqualTo(segmentExt.getPosition1());
//			if(segmentExt.getPosition2() != null)      
//				example.or().andPosition2EqualTo(segmentExt.getPosition2());
//			if(segmentExt.getPosition3() != null)
//				example.or().andPosition3EqualTo(segmentExt.getPosition3());
//			if(segmentExt.getPosition4() != null)    
//				example.or().andPosition4EqualTo(segmentExt.getPosition4());
//			if(segmentExt.getPhysicalStart() != null)    
//				example.or().andPhysicalStartEqualTo(segmentExt.getPhysicalStart());
//			if(segmentExt.getPhysicalEnd() != null)        
//				example.or().andPhysicalEndEqualTo(segmentExt.getPhysicalEnd());
//			if(segmentExt.getEstimatedLength() != null)        
//				example.or().andEstimatedLengthEqualTo(segmentExt.getEstimatedLength());
//			if(segmentExt.getMinimumLength() != null)
//				example.or().andMinimumLengthEqualTo(segmentExt.getMinimumLength());
//			if(segmentExt.getMaximumLength() != null)        
//				example.or().andMaximumLengthEqualTo(segmentExt.getMaximumLength());
//			if(segmentExt.getDescription() != null && segmentExt.getDescription().length() != 0 && segmentExt.getDescription().equalsIgnoreCase("UNKNOWN"))
//				example.or().andDescriptionEqualTo(segmentExt.getDescription());
			
//			example.or().andChromosomeEqualTo(segmentExt.getChromosome() == null || segmentExt.getChromosome().length() == 0 || 
//					segmentExt.getChromosome().equalsIgnoreCase("UNKNOWN") ? "-1" : segmentExt.getChromosome())
//	        .andRecurrentParentEqualTo(segmentExt.getRecurrentParent() == null || segmentExt.getRecurrentParent().length() == 0 || 
//	        		segmentExt.getRecurrentParent().equalsIgnoreCase("UNKNOWN") ? "NULL" : segmentExt.getRecurrentParent())
//	        .andDonorParentEqualTo(segmentExt.getDonorParent() == null ||segmentExt.getDonorParent().length() == 0 ||
//	        		segmentExt.getDonorParent().equalsIgnoreCase("UNKNOWN") ? "NULL" : segmentExt.getDonorParent())
//	        .andPosition1EqualTo(segmentExt.getPosition1() == null ? BigDecimal.valueOf(-1) : segmentExt.getPosition1())
//	        .andPosition2EqualTo(segmentExt.getPosition2() == null ? BigDecimal.valueOf(-1) : segmentExt.getPosition2())
//	        .andPosition3EqualTo(segmentExt.getPosition3() == null ? BigDecimal.valueOf(-1) : segmentExt.getPosition3())
//	        .andPosition4EqualTo(segmentExt.getPosition4() == null ? BigDecimal.valueOf(-1) : segmentExt.getPosition4())
//	        .andPhysicalStartEqualTo(segmentExt.getPhysicalStart() == null ? Integer.valueOf(-1) : segmentExt.getPhysicalStart())
//	        .andPhysicalEndEqualTo(segmentExt.getPhysicalEnd() == null ? Integer.valueOf(-1) : segmentExt.getPhysicalEnd())
//	        .andEstimatedLengthEqualTo(segmentExt.getEstimatedLength() == null ? BigDecimal.valueOf(-1) : segmentExt.getEstimatedLength())
//	        .andMinimumLengthEqualTo(segmentExt.getMinimumLength() == null ? BigDecimal.valueOf(-1) : segmentExt.getMinimumLength())
//	        .andMaximumLengthEqualTo(segmentExt.getMaximumLength() == null ? BigDecimal.valueOf(-1) : segmentExt.getMaximumLength())
//	        .andDescriptionEqualTo(segmentExt.getDescription() == null || segmentExt.getDescription().length() == 0 ||
//	        		segmentExt.getDescription().equalsIgnoreCase("UNKNOWN") ? "NULL" : segmentExt.getDescription());
			
			
			segment = this.getSegmentBySegmentExtWithoutConsiderId(segmentExt);
			// if the segment is not in the database, it will insert it
			// and missing string change to NULL;
			// and missing numeric change to -1;
			if (segment == null)
			{
				segment = new Segment();
				if (segmentExt.getChromosome() != null)
					segment.setChromosome(segmentExt.getChromosome());
				else
					segment.setChromosome(-1);
				if (segmentExt.getRecurrentParent() != null)
					segment.setRecurrentParent(segmentExt.getRecurrentParent());
				else
					segment.setRecurrentParent("NULL");
				if (segmentExt.getDonorParent() != null)
					segment.setDonorParent(segmentExt.getDonorParent());
				else
					segment.setDonorParent("NULL");
				if (segmentExt.getPosition1() != null)
					segment.setPosition1(segmentExt.getPosition1());
				else
					segment.setPosition1(BigDecimal.valueOf(-1));
				if (segmentExt.getPosition2() != null)
					segment.setPosition2(segmentExt.getPosition2());
				else
					segment.setPosition2(BigDecimal.valueOf(-1));
				if (segmentExt.getPosition3() != null)
					segment.setPosition3(segmentExt.getPosition3());
				else
					segment.setPosition3(BigDecimal.valueOf(-1));
				if (segmentExt.getPosition4() != null)
					segment.setPosition4(segmentExt.getPosition4());
				else
					segment.setPosition4(BigDecimal.valueOf(-1));
				if (segmentExt.getPhysicalStart() != null)
					segment.setPhysicalStart(segmentExt.getPhysicalStart());
				else
					segment.setPhysicalStart(Integer.valueOf(-1));
				if (segmentExt.getPhysicalEnd() != null)
					segment.setPhysicalEnd(segmentExt.getPhysicalEnd());
				else
					segment.setPhysicalEnd(Integer.valueOf(-1));
				if (segmentExt.getEstimatedLength() != null)
					segment.setEstimatedLength(segmentExt.getEstimatedLength());
				else
					segment.setEstimatedLength(BigDecimal.valueOf(-1));
				if (segmentExt.getMaximumLength() != null)
					segment.setMaximumLength(segmentExt.getMaximumLength());
				else
					segment.setMaximumLength(BigDecimal.valueOf(-1));
				if (segmentExt.getMinimumLength() != null)
					segment.setMinimumLength(segmentExt.getMinimumLength());
				else
					segment.setMinimumLength(BigDecimal.valueOf(-1));
				if (segmentExt.getDescription() != null)
					segment.setDescription(segmentExt.getDescription());
				else
					segment.setDescription("NULL");
				
				mapper.insert(segment);
				session.commit();
			}
		} finally
		{
			session.close();
		}
	}
	
	public List<Segment> addSegmentListNoRepeat(List<SegmentExt> segments)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		ArrayList<Segment> returnList = new ArrayList<Segment>();
		try
		{
			SegmentMapper mapper = session.getMapper(SegmentMapper.class);
			for (SegmentExt segment : segments)
			{
				SegmentExample example = new SegmentExample();
				example.or()
				        .andChromosomeEqualTo(segment.getChromosome())
				        .andRecurrentParentEqualTo(segment.getRecurrentParent())
				        .andDonorParentEqualTo(segment.getDonorParent())
				        .andPosition1EqualTo(segment.getPosition1())
				        .andPosition2EqualTo(segment.getPosition2())
				        .andPosition3EqualTo(segment.getPosition3())
				        .andPosition4EqualTo(segment.getPosition4())
				        .andPhysicalStartEqualTo(segment.getPhysicalStart())
				        .andPhysicalEndEqualTo(segment.getPhysicalEnd())
				        .andEstimatedLengthEqualTo(segment.getEstimatedLength())
				        .andMinimumLengthEqualTo(segment.getMinimumLength())
				        .andMaximumLengthEqualTo(segment.getMaximumLength())
				        .andDescriptionEqualTo(segment.getDescription());
				if (segment.getSegmentId() == null)
				{
					if (mapper.countByExample(example) > 0)
					{
						segment.setSegmentId(mapper.selectByExample(example)
						        .get(0).getSegmentId());
						mapper.updateByPrimaryKey(segment);
						returnList.add(segment);
					} else
					{
						mapper.insert(segment);
						returnList.add(mapper.selectByExample(example).get(0));
					}
				} else
				{
					updateSegmentFromSegmentExt(segment);
					returnList.add(mapper.selectByExample(example).get(0));
				}
			}
			session.commit();
		} finally
		{
			session.close();
		}
		return returnList;
	}
	
//	for JUnit Testing, to creating a SqlSession instance by manually. 
//	But if it run on the server, it should not be create a SqlSession by ourself, it should be create by Spring framework
	public ConnectionFactory getConnectionFactory()
	{
		try
		{
			return new ConnectionFactory();
		} catch (RserveException re)
		{
			re.printStackTrace();
		}
		return null;
	}
	
	public List<ConditionAndValue> getSegmentSummary()
    {
	    SqlSession session = connectionFactory.sqlSessionFactory.openSession();	    
	    List<ConditionAndValue> lstConditionAndValue  = new ArrayList<ConditionAndValue>();
	    try{
	    	SegmentMapper mapper = session.getMapper(SegmentMapper.class);
    		SegmentExample example = new SegmentExample();
    		int total = 0;
	    	for(int i = 1 ; i <= 12; i++)
	    	{
	    		example.clear();
	    		example.or().andChromosomeEqualTo(Integer.valueOf(i));
	    		int number = mapper.countByExample(example);
	    		ConditionAndValue cav = new ConditionAndValue();
	    		cav.setCondition("Chromosome " + i);
	    		cav.setValue(String.valueOf(number));
	    		total = total + number;
	    		lstConditionAndValue.add(cav);
	    	}
	    	lstConditionAndValue.add(new ConditionAndValue("Total",String.valueOf(total)));
	    } finally
	    {
	    	session.close();
	    }
	    return lstConditionAndValue;
    }

	public CategoryModel getSegmentSummaryOnChromosomeAndDonor() {
		// TODO Auto-generated method stub
		SimpleCategoryModel model = new SimpleCategoryModel();
		if(connectionFactory == null)
			connectionFactory = this.getConnectionFactory();
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		try
		{
			SegmentMapper mapper = session.getMapper(SegmentMapper.class);
			SegmentExample example = new SegmentExample();		
			BrowseSegmentMapper browseMapper = session.getMapper(BrowseSegmentMapper.class);
			List<String> lstDonorParent = browseMapper.findDistinctDonorParent();

//			System.out.println("Return size " + lstDonorParent.size());
			for(String s : lstDonorParent)
			{
//				System.out.println(s.toString());
				for(int i = 1; i <= 12 ; i++)
				{
					example.clear();
					example.or().andChromosomeEqualTo(Integer.valueOf(i)).andDonorParentEqualTo(s);
					model.setValue(s, "Chromosome " + i, mapper.countByExample(example));
				}
			}

		} finally
		{
			session.close();
		}
		
		return model;
	}
}
