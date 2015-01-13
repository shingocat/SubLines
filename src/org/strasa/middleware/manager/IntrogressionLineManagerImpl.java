/**
 * Builder: Mao Qin
 * Email: mqin@ymail.com
 * Date: 2014 - Jul 1, 2014
 * Project: StrasaWeb
 * Package: org.strasa.middleware.manager
 * Name: IntrogressionLineManagerImpl.java
 */
package org.strasa.middleware.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.GermplasmMapper;
import org.strasa.middleware.model.Germplasm;
import org.strasa.middleware.model.GermplasmSegment;
import org.strasa.middleware.model.IntrogressionLine;
import org.strasa.middleware.model.Segment;
import org.strasa.middleware.model.enumeration.Homogenous;
import org.strasa.web.managegermplasm.view.pojos.SegmentExt;
import org.strasa.web.uploadstudy.view.pojos.IntrogressionLineFilter;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class IntrogressionLineManagerImpl
{
	@WireVariable
	ConnectionFactory connectionFactory;
	
	public List<IntrogressionLine> getIntrogressionLineBySegmentParams(
	        HashMap<String, Object> params)
	{
		List<IntrogressionLine> lstIntrogressionLine = new ArrayList<IntrogressionLine>();
		List<Segment> lstSegment = new ArrayList<Segment>();
		List<SegmentExt> lstSegmentExt = new ArrayList<SegmentExt>();
		List<GermplasmSegment> lstGermplasmSegment = new ArrayList<GermplasmSegment>();
		SegmentManagerImpl sManagerImpl = new SegmentManagerImpl();
		GermplasmSegmentManagerImpl gsManagerImpl = new GermplasmSegmentManagerImpl();
		GermplasmManagerImpl gManagerImpl = new GermplasmManagerImpl();
		
		lstSegment = sManagerImpl.getSegmentByDynamicSQL(params);
		
		if (!lstSegment.isEmpty())
		{
			for (Segment segment : lstSegment)
			{
				lstGermplasmSegment
				        .addAll(gsManagerImpl
				                .getGermplasmSegmentBySegmentId(segment
				                        .getSegmentId()));
			}
		}
		if (!lstGermplasmSegment.isEmpty())
		{
			for (GermplasmSegment germplasmSegment : lstGermplasmSegment)
			{
				IntrogressionLine il = new IntrogressionLine();
				Germplasm germplasm = gManagerImpl
				        .getGermplasmById(germplasmSegment.getGermplasmId());
				il.setGermplasmValue(germplasm);
				il.setSegmentNumber(germplasmSegment
				        .getGermplasmSegmentNumber());
				if (il.getSegmentNumber() == 1)
				{
					SegmentExt segmentExt = new SegmentExt();
					Segment segment = sManagerImpl
					        .getSegmentBySegmentId(germplasmSegment
					                .getSegmentId());
					segmentExt.setSegmentExtFromSegment(segment);
					segmentExt.setIsHomogenous(germplasmSegment
					        .getIshomogenous());
					segmentExt.setOwner(il.getGermplasmname());
					lstSegmentExt.add(segmentExt);
					il.setSegments(new ArrayList<SegmentExt>(lstSegmentExt));
					lstIntrogressionLine.add(il);
				} else if (il.getSegmentNumber() > 1)
				{
					List<GermplasmSegment> tempGermplasmSegment = gsManagerImpl
					        .getGermplasmSegmentByGermplasmId(germplasmSegment
					                .getGermplasmId());
					for (GermplasmSegment temp : tempGermplasmSegment)
					{
						
						SegmentExt segmentExt = new SegmentExt();
						Segment segment = sManagerImpl
						        .getSegmentBySegmentId(temp.getSegmentId());
						segmentExt.setSegmentExtFromSegment(segment);
						segmentExt.setIsHomogenous(temp.getIshomogenous());
						segmentExt.setOwner(il.getGermplasmname());
						lstSegmentExt.add(segmentExt);
					}
					il.setSegments(new ArrayList<SegmentExt>(lstSegmentExt));
					lstIntrogressionLine.add(il);
				} else if (il.getSegmentNumber() == 0)
				{
//					do nothing right now.
				}
				lstSegmentExt.clear();
			}
		}
		return lstIntrogressionLine;
	}
	
	public IntrogressionLine getIntrogressionLineById(Integer germplasmId)
	{
		GermplasmManagerImpl gManagerImpl = new GermplasmManagerImpl();
		Germplasm germplasm = gManagerImpl
		        .getGermplasmByIdOfIntrogressionLine(germplasmId);
		if (germplasm != null)
		{
			IntrogressionLine il = new IntrogressionLine();
			il.setIntrogressionLineBasciInfoFromGermplasm(germplasm);
			if (germplasm.getGermplasmtypeid() == 3) //using remarks files for storing germplasm type
			{
				il.setRemarks("Pyramided Line");
			} else if (germplasm.getGermplasmtypeid() == 6)
			{
				il.setRemarks("NIL");
			} else if (germplasm.getGermplasmtypeid() == 7)
			{
				il.setRemarks("IL");
			} else if (germplasm.getGermplasmtypeid() == 8)
			{
				il.setRemarks("SSSL");
			}
			GermplasmSegmentManagerImpl gsManagerImpl = new GermplasmSegmentManagerImpl();
			List<GermplasmSegment> lstGermplasmSegment = gsManagerImpl
			        .getGermplasmSegmentByGermplasmId(germplasmId);

			if (lstGermplasmSegment != null)
			{
				SegmentManagerImpl sManagerImpl = new SegmentManagerImpl();
				List<SegmentExt> segments = new ArrayList<SegmentExt>();
				for (GermplasmSegment gs : lstGermplasmSegment)
				{
					Segment segment = sManagerImpl.getSegmentBySegmentId(gs
					        .getSegmentId());
					// how to check if the value segment is null!
					if(segment == null)
						continue;
					SegmentExt segmentExt = new SegmentExt();
					segmentExt.setSegmentExtFromSegment(segment);
					segmentExt.setIsHomogenous(gs.getIshomogenous().toString()
					        .equals("YES") ? Homogenous.YES.toString()
					        : Homogenous.NO.toString());
					segmentExt.setOwner(il.getGermplasmname());
					segments.add(segmentExt);
				}
				il.setSegments(segments);
				il.setSegmentNumber(segments.size());
			}
			return il;
		}
		return null;
	}
	
	public List<IntrogressionLine> getIntrogressionLineByUserId(Integer userId)
	{
		GermplasmManagerImpl gManagerImpl = new GermplasmManagerImpl();
		List<Germplasm> lstGermplasm = gManagerImpl
		        .getGermplasmByUserIdOfIntrogressonLine(userId);
		if (lstGermplasm == null)
		{
			return null;
		} else
		{
			ArrayList<IntrogressionLine> lstIntrogressionLine = new ArrayList<IntrogressionLine>();
			for (Germplasm g : lstGermplasm)
			{
				lstIntrogressionLine.add(this.getIntrogressionLineById(g
				        .getId()));
			}
			return lstIntrogressionLine;
		}
	}
	
	public IntrogressionLine getIntrogressionLineByName(String germplasmname)
	{
		GermplasmManagerImpl gManagerImpl = new GermplasmManagerImpl();
		Germplasm germplasm = gManagerImpl
		        .getGermplasmByNameOfIntrogressionLine(germplasmname);
		if (germplasm != null)
		{
			IntrogressionLine il = new IntrogressionLine();
			il.setIntrogressionLineBasciInfoFromGermplasm(germplasm);
			GermplasmSegmentManagerImpl gsManagerImpl = new GermplasmSegmentManagerImpl();
			List<GermplasmSegment> lstGermplasmSegment = gsManagerImpl
			        .getGermplasmSegmentByGermplasmId(germplasm.getId());
			if (lstGermplasmSegment != null)
			{
				SegmentManagerImpl sManagerImpl = new SegmentManagerImpl();
				List<SegmentExt> segments = new ArrayList<SegmentExt>();
				for (GermplasmSegment gs : lstGermplasmSegment)
				{
					Segment segment = sManagerImpl.getSegmentBySegmentId(gs
					        .getSegmentId());
					SegmentExt segmentExt = new SegmentExt();
					segmentExt.setSegmentExtFromSegment(segment);
					segmentExt.setIsHomogenous(gs.getIshomogenous().toString()
					        .equals("YES") ? Homogenous.YES.toString()
					        : Homogenous.NO.toString());
					segmentExt.setOwner(il.getGermplasmname());
					segments.add(segmentExt);
				}
				il.setSegments(segments);
				il.setSegmentNumber(segments.size());
			}
			return il;
		}
		return null;
	}
	
	public List<IntrogressionLine> getFilterIntrogressionLine(
	        IntrogressionLineFilter filter)
	{
		List<IntrogressionLine> lstIntrogressionLine = new ArrayList<IntrogressionLine>();
		String gname = filter.getGname().toLowerCase();
		Integer segmentNumber = filter.getSegmentNumber();
		GermplasmManagerImpl gManagerImpl = new GermplasmManagerImpl();
		GermplasmSegmentManagerImpl gsManagerImpl = new GermplasmSegmentManagerImpl();
		if (segmentNumber == null || segmentNumber == 0)
			segmentNumber = null;
		
		if ("".equals(gname) && segmentNumber == null)
		{
			List<Germplasm> lstGermplasm = gManagerImpl
			        .getGermplasmListByNameOfIntrogressionLine(gname);
			if (lstGermplasm.isEmpty())
				return null;
			for (Germplasm g : lstGermplasm)
			{
				lstIntrogressionLine.add(this.getIntrogressionLineById(g
				        .getId()));
			}
			
		} else if (!("".equals(gname)) && segmentNumber == null)
		{
			List<Germplasm> lstGermplasm = gManagerImpl
			        .getGermplasmListByNameOfIntrogressionLine(gname);
			if (lstGermplasm == null || lstGermplasm.isEmpty())
				return null;
			for (Germplasm g : lstGermplasm)
			{
				lstIntrogressionLine.add(this.getIntrogressionLineById(g
				        .getId()));
			}
			
		} else if (("".equals(gname)) && segmentNumber != null)
		{
			List<GermplasmSegment> lstGermplasmSegment = gsManagerImpl
			        .getGermplasmSegmentBySegmentNumber(segmentNumber);
			if (lstGermplasmSegment == null || lstGermplasmSegment.isEmpty())
				return null;
			List<Germplasm> lstGermplasm = new ArrayList<Germplasm>();
			for (GermplasmSegment gs : lstGermplasmSegment)
			{
				lstGermplasm.add(gManagerImpl
				        .getGermplasmByIdOfIntrogressionLine(gs
				                .getGermplasmId()));
				
			}
			if (lstGermplasm.isEmpty())
				return null;
			for (Germplasm g : lstGermplasm)
			{
				lstIntrogressionLine.add(this.getIntrogressionLineById(g
				        .getId()));
			}
		} else if (!("".equals(gname)) && segmentNumber != null)
		{
			List<GermplasmSegment> lstGermplasmSegment = gsManagerImpl
			        .getGermplasmSegmentBySegmentNumber(segmentNumber);
			if (lstGermplasmSegment == null || lstGermplasmSegment.isEmpty())
				return null;
			List<Germplasm> lstGermplasm = new ArrayList<Germplasm>();
			
			for (GermplasmSegment gs : lstGermplasmSegment)
			{
				lstGermplasm.add(gManagerImpl
				        .getGermplasmByIdOfIntrogressionLine(gs
				                .getGermplasmId()));
			}
			if (lstGermplasm.isEmpty())
				return null;
			for (Germplasm g : lstGermplasm)
			{
				if (g.getGermplasmname().toLowerCase().contains(gname))
				{
					lstIntrogressionLine.add(this.getIntrogressionLineById(g
					        .getId()));
				}
			}
		}
		
		return lstIntrogressionLine;
	}
	
//	this method is update only segment info right now, not including adding or subtracting segment.
//	if having time, implement modify segment number of introgression line and adding/subtracting segment info simultaneously
	public void modifyIntrogressionLine(IntrogressionLine il)
	{
		List<SegmentExt> segments = il.getSegments();
//	    not checking right now, just update segment info directly
		if (segments != null && !segments.isEmpty())
		{
			SegmentManagerImpl sManagerImpl = new SegmentManagerImpl();
			IntrogressionLine temp = this.getIntrogressionLineByName(il
			        .getGermplasmname());
			GermplasmSegmentManagerImpl gsManagerImpl = new GermplasmSegmentManagerImpl();
			if (il.getId() == null)
				il.setId(temp.getId());
			List<SegmentExt> lstSegmentExt = new ArrayList<SegmentExt>(); // used to store the segment info for batching update segment and germplasm_segment table info
			
			for (SegmentExt segmentExt : segments)
			{
				Segment segment = sManagerImpl
				        .getSegmentBySegmentExtWithoutConsiderId(segmentExt);
				if (segment != null)
				{
					segmentExt.setSegmentExtFromSegment(segment);
					lstSegmentExt.add(segmentExt);
				} else
				{
					sManagerImpl.addSegmentNoRepeat(segmentExt);
					segment = sManagerImpl
					        .getSegmentBySegmentExtWithoutConsiderId(segmentExt);
					segmentExt.setSegmentExtFromSegment(segment);
					lstSegmentExt.add(segmentExt);
				}
			}
			
			if (lstSegmentExt.size() != 0)
			{
				gsManagerImpl.deleteGermplasmSegmentByGermplasmId(il.getId());
				
				for (SegmentExt segmentExt : lstSegmentExt)
				{
					GermplasmSegment gs = new GermplasmSegment();
					gs.setGermplasmId(il.getId());
					gs.setSegmentId(segmentExt.getSegmentId());
					gs.setGermplasmSegmentNumber(segments.size());
					gs.setIshomogenous(segmentExt.getIsHomogenous().toString());
					
					gsManagerImpl.addGermplasmSegment(gs);
				}
			}
		}
	}
	
	public void deleteIntrogressionLineById(Integer id)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		try
		{
			GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);
			GermplasmSegmentManagerImpl gsManagerImpl = new GermplasmSegmentManagerImpl();
			
			mapper.deleteByPrimaryKey(id);
			gsManagerImpl.deleteGermplasmSegmentByGermplasmId(id);
			session.commit();
		} finally
		{
			session.close();
			
		}
	}
	
	public void addIntrogressionLineListNoRepeat(
	        List<IntrogressionLine> lstStudyIL, Integer userID)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		try
		{
			GermplasmManagerImpl gManagerImpl = new GermplasmManagerImpl();
			GermplasmSegmentManagerImpl gsManagerImpl = new GermplasmSegmentManagerImpl();
			SegmentManagerImpl sManagerImpl = new SegmentManagerImpl();
			for (IntrogressionLine il : lstStudyIL)
			{
				Germplasm germplasm = gManagerImpl.getGermplasmByName(il
				        .getGermplasmname());
				for (SegmentExt s : il.getSegments())
				{
					
					sManagerImpl.addSegmentNoRepeat(s);
					Segment segment = sManagerImpl
					        .getSegmentBySegmentExtWithoutConsiderId(s);
					gsManagerImpl.addGermplasmSegment(germplasm.getId(),
					        segment.getSegmentId(), il.getSegmentNumber(),
					        s.getIsHomogenous());
				}
			}
			
		} finally
		{
			session.close();
		}
		
	}
	
	public List<IntrogressionLine> getIntrogressionLineBySearchParams(
	        HashMap<String, Object> searchIntrogressionLineParams)
	{
		GermplasmManagerImpl gManagerImpl = new GermplasmManagerImpl();
		GermplasmSegmentManagerImpl gsManagerImpl = new GermplasmSegmentManagerImpl();
		SegmentManagerImpl sManagerImpl = new SegmentManagerImpl();
//		String ilName = null;
		Integer segmentNumber = null;
		String recurrentParent = null;
		String donorParent = null;
		Integer userId = null;
		
//		if (searchIntrogressionLineParams.containsKey("IntorgressionLineName"))
//			ilName = (String) searchIntrogressionLineParams
//			        .get("IntrogressionLineName");
		if (searchIntrogressionLineParams.containsKey("SegmentNumber"))
			segmentNumber = (Integer) searchIntrogressionLineParams
			        .get("SegmentNumber");
		if (searchIntrogressionLineParams.containsKey("RecurrentParent"))
			recurrentParent = (String) searchIntrogressionLineParams
			        .get("RecurrentParent");
		if (searchIntrogressionLineParams.containsKey("DonorParent"))
			donorParent = (String) searchIntrogressionLineParams
			        .get("DonorParent");
		if (searchIntrogressionLineParams.containsKey("UserId"))
			userId = (Integer) searchIntrogressionLineParams.get("UserId");
		List<IntrogressionLine> lstIntrogressionLine = this
		        .getIntrogressionLineByUserId(userId);
		
		if (donorParent == null && segmentNumber == null
		        && recurrentParent == null)
		{
			return lstIntrogressionLine;
			
		} else if (donorParent != null && segmentNumber != null
		        && recurrentParent != null)
		{
			Iterator<IntrogressionLine> itr = lstIntrogressionLine.iterator();
			while (itr.hasNext())
			{
				IntrogressionLine il = itr.next();
				if (il.getSegmentNumber() != segmentNumber)
				{
					itr.remove();
					continue;
				} else
				{
					List<SegmentExt> lstSegmentExt = il.getSegments();
					Boolean isExist = false;
					for (SegmentExt segmentExt : lstSegmentExt)
					{
						if (il.getRecurrentParent() == null)
							il.setRecurrentParent(segmentExt
							        .getRecurrentParent());
						if (il.getDonorParent() == null)
						{
							il.setDonorParent(segmentExt.getDonorParent());
						} else
						{
							il.setDonorParent(il.getDonorParent() + ";"
							        + segmentExt.getDonorParent());
						}
						
						if (segmentExt.getRecurrentParent().contains(
						        recurrentParent)
						        && segmentExt.getDonorParent().contains(
						                donorParent))
						{
							isExist = true;
						}
					}
					if (!isExist)
					{
						itr.remove();
						continue;
					}
				}
			}
		} else if (donorParent != null && segmentNumber != null
		        && recurrentParent == null)
		{
			Iterator<IntrogressionLine> itr = lstIntrogressionLine.iterator();
			while (itr.hasNext())
			{
				IntrogressionLine il = itr.next();
				if (il.getSegmentNumber() != segmentNumber)
				{
					itr.remove();
					continue;
				} else
				{
					List<SegmentExt> lstSegmentExt = il.getSegments();
					Boolean isExist = false;
					for (SegmentExt segmentExt : lstSegmentExt)
					{
						if (il.getRecurrentParent() == null)
							il.setRecurrentParent(segmentExt
							        .getRecurrentParent());
						if (il.getDonorParent() == null)
						{
							il.setDonorParent(segmentExt.getDonorParent());
						} else
						{
							il.setDonorParent(il.getDonorParent() + ";"
							        + segmentExt.getDonorParent());
						}
						
						if (segmentExt.getDonorParent().contains(donorParent))
						{
							isExist = true;
						}
					}
					if (!isExist)
					{
						itr.remove();
						continue;
					}
				}
			}
		} else if (donorParent == null && segmentNumber != null
		        && recurrentParent != null)
		{
			Iterator<IntrogressionLine> itr = lstIntrogressionLine.iterator();
			while (itr.hasNext())
			{
				IntrogressionLine il = itr.next();
				if (il.getSegmentNumber() != segmentNumber)
				{
					itr.remove();
					continue;
				} else
				{
					List<SegmentExt> lstSegmentExt = il.getSegments();
					Boolean isExist = false;
					for (SegmentExt segmentExt : lstSegmentExt)
					{
						
						if (il.getRecurrentParent() == null)
							il.setRecurrentParent(segmentExt
							        .getRecurrentParent());
						if (il.getDonorParent() == null)
						{
							il.setDonorParent(segmentExt.getDonorParent());
						} else
						{
							il.setDonorParent(il.getDonorParent() + ";"
							        + segmentExt.getDonorParent());
						}
						
						if (segmentExt.getRecurrentParent().contains(
						        recurrentParent))
						{
							isExist = true;
						}
					}
					if (!isExist)
					{
						itr.remove();
						continue;
					}
				}
			}
		} else if (donorParent != null && segmentNumber == null
		        && recurrentParent != null)
		{
			Iterator<IntrogressionLine> itr = lstIntrogressionLine.iterator();
			while (itr.hasNext())
			{
				IntrogressionLine il = itr.next();
				List<SegmentExt> lstSegmentExt = il.getSegments();
				Boolean isExist = false;
				if(lstSegmentExt == null) lstSegmentExt = new ArrayList<SegmentExt>();
				for (SegmentExt segmentExt : lstSegmentExt)
				{
					if (il.getRecurrentParent() == null)
						il.setRecurrentParent(segmentExt.getRecurrentParent());
					if (il.getDonorParent() == null)
					{
						il.setDonorParent(segmentExt.getDonorParent());
					} else
					{
						il.setDonorParent(il.getDonorParent() + ";"
						        + segmentExt.getDonorParent());
					}
					
					if (segmentExt.getRecurrentParent().contains(
					        recurrentParent) && segmentExt.getDonorParent().contains(donorParent))
					{
						isExist = true;
					}
				}
				if (!isExist)
				{
					itr.remove();
					continue;
				}
			}
			
		} else if (donorParent != null && segmentNumber == null
		        && recurrentParent == null)
		{
			Iterator<IntrogressionLine> itr = lstIntrogressionLine.iterator();
			while (itr.hasNext())
			{
				IntrogressionLine il = itr.next();
				List<SegmentExt> lstSegmentExt = il.getSegments();
				if(lstSegmentExt == null) lstSegmentExt = new ArrayList<SegmentExt>();
				Boolean isExist = false;
				for (SegmentExt segmentExt : lstSegmentExt)
				{
					if (il.getRecurrentParent() == null)
						il.setRecurrentParent(segmentExt.getRecurrentParent());
					if (il.getDonorParent() == null)
					{
						il.setDonorParent(segmentExt.getDonorParent());
					} else
					{
						il.setDonorParent(il.getDonorParent() + ";"
						        + segmentExt.getDonorParent());
					}
					
					if (segmentExt.getDonorParent().contains(
					        donorParent))
					{
						isExist = true;
					}
				}
				if (!isExist)
				{
					itr.remove();
					continue;
				}
			}
		} else if (donorParent == null && segmentNumber != null
		        && recurrentParent == null)
		{
			Iterator<IntrogressionLine> itr = lstIntrogressionLine.iterator();
			while (itr.hasNext())
			{
				IntrogressionLine il = itr.next();
				if (il.getSegmentNumber() != segmentNumber)
				{
					itr.remove();
					continue;
				} else
				{
					List<SegmentExt> lstSegmentExt = il.getSegments();
					if(lstSegmentExt == null) lstSegmentExt = new ArrayList<SegmentExt>();
					for (SegmentExt segmentExt : lstSegmentExt)
					{
						if (il.getRecurrentParent() == null)
							il.setRecurrentParent(segmentExt.getRecurrentParent());
						if (il.getDonorParent() == null)
						{
							il.setDonorParent(segmentExt.getDonorParent());
						} else
						{
							il.setDonorParent(il.getDonorParent() + ";"
							        + segmentExt.getDonorParent());
						}
					}
				}
			}
		} else if (donorParent == null && segmentNumber == null
		        && recurrentParent != null)
		{
			Iterator<IntrogressionLine> itr = lstIntrogressionLine.iterator();
			while (itr.hasNext())
			{
				IntrogressionLine il = itr.next();		
				List<SegmentExt> lstSegmentExt = il.getSegments();
				if(lstSegmentExt == null) lstSegmentExt = new ArrayList<SegmentExt>();
				Boolean isExist = false;
				for (SegmentExt segmentExt : lstSegmentExt)
				{
					if (il.getRecurrentParent() == null)
						il.setRecurrentParent(segmentExt.getRecurrentParent());
					if (il.getDonorParent() == null)
					{
						il.setDonorParent(segmentExt.getDonorParent());
					} else
					{
						il.setDonorParent(il.getDonorParent() + ";"
						        + segmentExt.getDonorParent());
					}
					if (segmentExt.getRecurrentParent().contains(
					        recurrentParent))
					{
						isExist = true;
					}
				}
				if (!isExist)
				{
					itr.remove();
					continue;
				}
			}
		}
		
		return lstIntrogressionLine;
		
	}

	public List<IntrogressionLine> getIntrogressionLineBySegment(Segment s) {
		// TODO Auto-generated method stub
		List<IntrogressionLine> lstIntrogressionLine = new ArrayList<IntrogressionLine>();
		GermplasmSegmentManagerImpl gsManagerImpl = new GermplasmSegmentManagerImpl();
		List<GermplasmSegment> lstGermplasmSegment = gsManagerImpl.getGermplasmSegmentBySegmentId(s.getSegmentId());
		for(GermplasmSegment gs : lstGermplasmSegment)
		{
			IntrogressionLine il = this.getIntrogressionLineById(gs.getGermplasmId());
			lstIntrogressionLine.add(il);
		}
		return lstIntrogressionLine;
	}
}
