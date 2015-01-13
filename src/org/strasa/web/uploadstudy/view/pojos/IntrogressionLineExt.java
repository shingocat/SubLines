/**
 * Builder: Mao Qin
 * Email: mqin@ymail.com
 * Date: 2014 - Jun 26, 2014
 * Project: StrasaWeb
 * Package: org.strasa.web.uploadstudy.view.pojos
 * Name: IntrogressionLineExt.java
 */
package org.strasa.web.uploadstudy.view.pojos;

import java.util.List;

import org.strasa.middleware.model.Segment;

// This Bean is used to store each record from csv file
// It will be many record with same Gname and totalSegmentNumber in the csv file;
public class IntrogressionLineExt extends GermplasmExt
{
	private String totalSegmentNumber;
	private String isHomogenous;
	private String chromosome;
	private String recurrentParent;
	private String donorParent;
	private String geneticPosition1;
	private String geneticPosition2;
	private String geneticPosition3;
	private String geneticPosition4;
	private String physicalStart;
	private String physicalEnd;
	private String geneticEstimatedLength;
	private String geneticMinimumLength;
	private String geneticMaximumLength;
	private String segmentDescription;
	
	public String getTotalSegmentNumber()
	{
		return totalSegmentNumber;
	}
	
	public void setTotalSegmentNumber(String totalSegmentNumber)
	{
		this.totalSegmentNumber = totalSegmentNumber;
	}
	
	public String getIsHomogenous()
	{
		return isHomogenous;
	}
	
	public void setIsHomogenous(String isHomogenous)
	{
		this.isHomogenous = isHomogenous;
	}
	
	public String getChromosome()
	{
		return chromosome;
	}
	
	public void setChromosome(String chromosome)
	{
		this.chromosome = chromosome;
	}
	
	public String getRecurrentParent()
	{
		return recurrentParent;
	}
	
	public void setRecurrentParent(String recurrentParent)
	{
		this.recurrentParent = recurrentParent;
	}
	
	public String getDonorParent()
	{
		return donorParent;
	}
	
	public void setDonorParent(String donorParent)
	{
		this.donorParent = donorParent;
	}
	
	public String getGeneticPosition1()
	{
		return geneticPosition1;
	}
	
	public void setGeneticPosition1(String geneticPosition1)
	{
		this.geneticPosition1 = geneticPosition1;
	}
	
	public String getGeneticPosition2()
	{
		return geneticPosition2;
	}
	
	public void setGeneticPosition2(String geneticPosition2)
	{
		this.geneticPosition2 = geneticPosition2;
	}
	
	public String getGeneticPosition3()
	{
		return geneticPosition3;
	}
	
	public void setGeneticPosition3(String geneticPosition3)
	{
		this.geneticPosition3 = geneticPosition3;
	}
	
	public String getGeneticPosition4()
	{
		return geneticPosition4;
	}
	
	public void setGeneticPosition4(String geneticPosition4)
	{
		this.geneticPosition4 = geneticPosition4;
	}
	
	public String getPhysicalStart()
	{
		return physicalStart;
	}
	
	public void setPhysicalStart(String physicalStart)
	{
		this.physicalStart = physicalStart;
	}
	
	public String getPhysicalEnd()
	{
		return physicalEnd;
	}
	
	public void setPhysicalEnd(String physicalEnd)
	{
		this.physicalEnd = physicalEnd;
	}
	
	public String getGeneticEstimatedLength()
	{
		return geneticEstimatedLength;
	}
	
	public void setGeneticEstimatedLength(String geneticEstimatedLength)
	{
		this.geneticEstimatedLength = geneticEstimatedLength;
	}
	
	public String getGeneticMinimumLength()
	{
		return geneticMinimumLength;
	}
	
	public void setGeneticMinimumLength(String geneticMinimumLength)
	{
		this.geneticMinimumLength = geneticMinimumLength;
	}
	
	public String getGeneticMaximumLength()
	{
		return geneticMaximumLength;
	}
	
	public void setGeneticMaximumLength(String geneticMaximumLength)
	{
		this.geneticMaximumLength = geneticMaximumLength;
	}
	
	public String getSegmentDescription()
	{
		return segmentDescription;
	}
	
	public void setSegmentDescription(String segmentDescription)
	{
		this.segmentDescription = segmentDescription;
	}
	
	@Override
	public String toString()
	{
		return "IntrogressionLineExt [totalSegmentNumber=" + totalSegmentNumber
		        + ", isHomogenous=" + isHomogenous + ", chromosome="
		        + chromosome + ", recurrentParent=" + recurrentParent
		        + ", donorParent=" + donorParent + ", geneticPosition1="
		        + geneticPosition1 + ", geneticPosition2=" + geneticPosition2
		        + ", geneticPosition3=" + geneticPosition3
		        + ", geneticPosition4=" + geneticPosition4 + ", physicalStart="
		        + physicalStart + ", physicalEnd=" + physicalEnd
		        + ", geneticEstimatedLength=" + geneticEstimatedLength
		        + ", geneticMinimumLength=" + geneticMinimumLength
		        + ", geneticMaximumLength=" + geneticMaximumLength
		        + ", segmentDescription=" + segmentDescription + "]";
	}
	
//	return true only with the same gname and segment number but with different segment info
	public boolean equalsWithGnameSegmentNumberWithoutOtherInfo(IntrogressionLineExt ilExt)
	{
		
		return this.getGermplasmname().equals(ilExt.getGermplasmname()) && this.getTotalSegmentNumber().equals(ilExt.getTotalSegmentNumber())
				&& !(this.getIsHomogenous().equals(ilExt.getIsHomogenous())) && !(this.getChromosome().equals(ilExt.getChromosome()))
				&& !(this.getRecurrentParent().equals(ilExt.getRecurrentParent())) && !(this.getDonorParent().equals(ilExt.getDonorParent()))
				&& !(this.getGeneticPosition1().equals(ilExt.getGeneticPosition1())) && !(this.getGeneticPosition2().equals(ilExt.getGeneticPosition2()))
				&& !(this.getGeneticPosition3().equals(ilExt.getGeneticPosition3())) && !(this.getGeneticPosition4().equals(ilExt.getGeneticPosition4()))
				&& !(this.getPhysicalStart().equals(ilExt.getPhysicalStart())) && !(this.getPhysicalEnd().equals(ilExt.getPhysicalEnd()))
				&& !(this.getGeneticEstimatedLength().equals(ilExt.getGeneticEstimatedLength())) && !(this.getGeneticMinimumLength().equals(ilExt.getGeneticMinimumLength()))
				&& !(this.getGeneticMaximumLength().equals(ilExt.getGeneticMaximumLength())) && !(this.getSegmentDescription().equals(ilExt.getSegmentDescription()));
				
	}
	
	public boolean equals(IntrogressionLineExt ilExt)
	{

		return this.getGermplasmname().equals(ilExt.getGermplasmname()) && this.getTotalSegmentNumber().equals(ilExt.getTotalSegmentNumber())
				&& this.getIsHomogenous().equals(ilExt.getIsHomogenous()) && this.getChromosome().equals(ilExt.getChromosome())
				&& this.getRecurrentParent().equals(ilExt.getRecurrentParent()) && this.getDonorParent().equals(ilExt.getDonorParent())
				&& this.getGeneticPosition1().equals(ilExt.getGeneticPosition1()) && this.getGeneticPosition2().equals(ilExt.getGeneticPosition2())
				&& this.getGeneticPosition3().equals(ilExt.getGeneticPosition3()) && this.getGeneticPosition4().equals(ilExt.getGeneticPosition4())
				&& this.getPhysicalStart().equals(ilExt.getPhysicalStart()) && this.getPhysicalEnd().equals(ilExt.getPhysicalEnd())
				&& this.getGeneticEstimatedLength().equals(ilExt.getGeneticEstimatedLength()) && this.getGeneticMinimumLength().equals(ilExt.getGeneticMinimumLength())
				&& this.getGeneticMaximumLength().equals(ilExt.getGeneticMaximumLength()) && this.getSegmentDescription().equals(ilExt.getSegmentDescription());
				
	}
}
