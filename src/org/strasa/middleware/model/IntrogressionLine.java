/**
 * Builder: Mao Qin
 * Email: mqin@ymail.com
 * Date: 2014 - Jul 1, 2014
 * Project: StrasaWeb
 * Package: org.strasa.middleware.model
 * Name: IntrogressionLine.java
 */
package org.strasa.middleware.model;

import java.util.List;

import org.strasa.web.managegermplasm.view.pojos.SegmentExt;
import org.strasa.web.uploadstudy.view.pojos.GermplasmDeepInfoModel;

import com.mysql.jdbc.StringUtils;

public class IntrogressionLine extends GermplasmDeepInfoModel
{
	private List<SegmentExt> segments;
	private Integer segmentNumber;
	private Integer newSegmentNumber;
	private String recurrentParent;
	private String donorParent;
	private String germplasmType;
	
	public IntrogressionLine()
	{
		
	}
	
	public IntrogressionLine(Germplasm germplasm)
	{
		super(germplasm);
//		this.setGermplasmtypeid(7); // setting the default germplasm of introgression line instant is IL type (id = 7 in the germplasmtype table in strasa db) 
	}
	
	public IntrogressionLine(Germplasm germplasm, Integer segmentNumber,
	        List<SegmentExt> segments)
	{
		super(germplasm);
		this.setSegmentNumber(segmentNumber);
		this.setSegments(segments);
//		this.setGermplasmtypeid(7); // setting the default germplasm of introgression line instant is IL type (id = 7 in the germplasmtype table in strasa db) 
	}
	
	public List<SegmentExt> getSegments()
	{
		return segments;
	}
	
	public void setSegments(List<SegmentExt> segments)
	{
		this.segments = segments;
	}
	
	public Integer getSegmentNumber()
	{
		return segmentNumber;
	}
	
	public void setSegmentNumber(Integer segmentNumber)
	{
		this.segmentNumber = segmentNumber;
	}
	
	
	
	public Integer getNewSegmentNumber()
	{
		return newSegmentNumber;
	}

	public void setNewSegmentNumber(Integer newSegmentNumber)
	{
		this.newSegmentNumber = newSegmentNumber;
	}

	public void setIntrogressionLineBasciInfoFromGermplasm(Germplasm germplasm)
	{
		this.setGermplasmValue(germplasm);
	}
	
	public String validate() {
		// if(true) return true;
		this.setStyleBG("background-color: #ff6666");
		if (StringUtils.isNullOrEmpty(getGermplasmname())) {
			// styleBG = "background-color: #ff6666";
			return "Error: GName cannot be empty";
		}
		if(StringUtils.isNullOrEmpty(getSegmentNumber().toString()))
		{
			return "Error: Segment Number cannot be empty";
		}
		if(StringUtils.isNullOrEmpty(getBreeder()) && StringUtils.isNullOrEmpty(getFemaleparent()) 
				&& StringUtils.isNullOrEmpty(getMaleparent()) && getGermplasmtypeid() == null)
		{
			return "Error: Basic info (Breeder, Female Parent, Male Parent, Germplasm type) of Introgression Line must not be empty in " + getGermplasmname();
		}
//		if (StringUtils.isNullOrEmpty(getBreeder())) {
//			// styleBG = "background-color: #ff6666";
//			return "Error: Breeder must not be empty in GName: " + getGermplasmname();
//		}
//
//		if (StringUtils.isNullOrEmpty(getFemaleparent())) {
//			// styleBG = "background-color: #ff6666";
//			return "Error: Female Parent must not be empty in GName: " + getGermplasmname();
//		}
//
//		;
//		if (StringUtils.isNullOrEmpty(getMaleparent())) {
//			// styleBG = "background-color: #ff6666";
//			return "Error: Male Parent must not be empty in GName: " + getGermplasmname();
//		}
//
//		if (getGermplasmtypeid() == null) {
//			// styleBG = "background-color: #ff6666";
//			return "Error: Germplasm Type must not be empty in GName: " + getGermplasmname();
//		}
		this.setStyleBG("background-color: #FFF");
		return null;
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

	public String getGermplasmType()
	{
		return germplasmType;
	}

	public void setGermplasmType(String germplasmType)
	{
		this.germplasmType = germplasmType;
	}

	@Override
    public String toString()
    {
	    return "IntrogressionLine [ Introgression name=" + getGermplasmname() + ", segments=" + segments + ", segmentNumber="
	            + segmentNumber + ", newSegmentNumber=" + newSegmentNumber
	            + "]" + super.toString();
    }
	
	
}
