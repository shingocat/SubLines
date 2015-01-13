/**
 *
 * 	Builder: 	Mao Qin
 *	Email:		mqin@ymail.com
 *	Date:		2014 - Jun 30, 2014
 *	Project: 	StrasaWeb
 *  Package: 	org.strasa.web.uploadstudy.view.pojos
 *	Name:	 IntrogressionLineFilter.java
 *	
 *
 */
package org.strasa.web.uploadstudy.view.pojos;

import org.strasa.middleware.model.IntrogressionLine;

public class IntrogressionLineFilter extends GermplasmFilter
{
	private Integer segmentNumber = null;

	public Integer getSegmentNumber()
	{
		return segmentNumber;
	}

	public void setSegmentNumber(Integer segmentNumber)
	{
		this.segmentNumber = segmentNumber == null ? 0 : segmentNumber;
	}
	
	public Boolean equals(IntrogressionLine data)
	{
		if(("".equals(this.getGname()) || this.getGname().equals(null)) && this.getSegmentNumber() == null)
		{
			return Boolean.TRUE;
		} else
		{
			return data.getGermplasmname().startsWith(getGname()) && data.getSegmentNumber().toString().equals(segmentNumber);
		}
	}
}

