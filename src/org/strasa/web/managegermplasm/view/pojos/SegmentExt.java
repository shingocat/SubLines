/**
 * Builder: Mao Qin
 * Email: mqin@ymail.com
 * Date: 2014 - Jul 1, 2014
 * Project: StrasaWeb
 * Package: org.strasa.web.managegermplasm.view.pojos
 * Name: SegmentExt.java
 */
package org.strasa.web.managegermplasm.view.pojos;

import java.math.BigDecimal;
import java.util.List;

import org.strasa.middleware.model.IntrogressionLine;
import org.strasa.middleware.model.Segment;
import org.strasa.middleware.model.enumeration.Homogenous;

public class SegmentExt extends Segment
{
	private String isHomogenous;
	private String owner; // the IL germplasm name;
	private List<IntrogressionLine> harborer; // the list of introgression line harbor this segment;
	
	public SegmentExt()
	{
		
	}
	
	public SegmentExt(Segment s, String isHomogenous, String owner)
	{
		this.setSegmentExtFromSegment(s);
		this.setIsHomogenous(isHomogenous);
		this.setOwner(owner);
	}
	
	public void setSegmentExtFromSegment(Segment s)
	{
		// modify by QIN MAO on Jan 9, 2015 for the numeric value is -1 
		// and for the string vlaue is NULL
//		if (s.getSegmentId() != null)
//			this.setSegmentId(s.getSegmentId());
//		if (s.getChromosome() != null)
//			this.setChromosome(s.getChromosome());
//		if (s.getPosition1() != null)
//			this.setPosition1(s.getPosition1());
//		if (s.getPosition2() != null)
//			this.setPosition2(s.getPosition2());
//		if (s.getPosition3() != null)
//			this.setPosition3(s.getPosition3());
//		if (s.getPosition4() != null)
//			this.setPosition4(s.getPosition4());
//		if (s.getRecurrentParent() != null)
//			this.setRecurrentParent(s.getRecurrentParent());
//		if (s.getDonorParent() != null)
//			this.setDonorParent(s.getDonorParent());
//		if (s.getEstimatedLength() != null)
//			this.setEstimatedLength(s.getEstimatedLength());
//		if (s.getMinimumLength() != null)
//			this.setMinimumLength(s.getMinimumLength());
//		if (s.getMaximumLength() != null)
//			this.setMaximumLength(s.getMaximumLength());
//		if (s.getPhysicalStart() != null)
//			this.setPhysicalStart(s.getPhysicalStart());
//		if (s.getPhysicalEnd() != null)
//			this.setPhysicalEnd(s.getPhysicalEnd());
//		if (s.getDescription() != null)
//			this.setDescription(s.getDescription());
		
		if(s.getSegmentId() != null && s.getSegmentId() != Integer.valueOf(-1))
			this.setSegmentId(s.getSegmentId());
		if(s.getChromosome() != null && s.getChromosome() != -1)
			this.setChromosome(s.getChromosome());
		if(s.getPosition1() != null && s.getPosition1().intValue() != -1)
			this.setPosition1(s.getPosition1());
		if(s.getPosition2() != null && s.getPosition2().intValue() != -1)
			this.setPosition2(s.getPosition2());
		if(s.getPosition3() != null && s.getPosition3().intValue() != -1)
			this.setPosition3(s.getPosition3());
		if(s.getPosition4() != null && s.getPosition4().intValue() != -1)
			this.setPosition4(s.getPosition4());
		if(s.getRecurrentParent() != null && 
				!s.getRecurrentParent().equalsIgnoreCase("UNKNOWN") && 
				!s.getRecurrentParent().equalsIgnoreCase("NULL") && 
				s.getRecurrentParent().length() != 0)
			this.setRecurrentParent(s.getRecurrentParent());
		if(s.getDonorParent() != null && 
				!s.getDonorParent().equalsIgnoreCase("UNKNOWN") &&
				!s.getDonorParent().equalsIgnoreCase("NULL") && 
				s.getDonorParent().length() != 0)
			this.setDonorParent(s.getDonorParent());
		if(s.getEstimatedLength() != null &&
				s.getEstimatedLength().intValue() != -1)
			this.setEstimatedLength(s.getEstimatedLength());
		if(s.getMinimumLength() != null &&
				s.getMinimumLength().intValue() != -1)
			this.setMinimumLength(s.getMinimumLength());
		if(s.getMaximumLength() != null && 
				s.getMaximumLength().intValue() != -1)
			this.setMaximumLength(s.getMaximumLength());
		if(s.getPhysicalStart() != null &&
				s.getPhysicalStart() != -1)
			this.setPhysicalStart(s.getPhysicalStart());
		if(s.getPhysicalEnd() != null &&
				s.getPhysicalEnd() != -1)
			this.setPhysicalEnd(s.getPhysicalEnd());
		if(s.getDescription() != null &&
				!s.getDescription().equalsIgnoreCase("UNKNOWN") &&
				!s.getDescription().equalsIgnoreCase("NULL") &&
				s.getDescription().length() != 0)
			this.setDescription(s.getDescription());
	}
	
	public String getIsHomogenous()
	{
		return isHomogenous.equals("YES") ? Homogenous.YES.toString()
		        : Homogenous.NO.toString();
	}
	
	public void setIsHomogenous(String isHomogenous)
	{
		this.isHomogenous = isHomogenous;
	}
	
	public String getOwner()
	{
		return owner;
	}
	
	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	
	public List<IntrogressionLine> getHarborer() {
		return harborer;
	}

	public void setHarborer(List<IntrogressionLine> harborer) {
		this.harborer = harborer;
	}

	@Override
	public String toString()
	{
		return "SegmentExt [isHomogenous=" + isHomogenous + ", owner=" + owner
		        + "]" + super.toString();
	}
	
}
