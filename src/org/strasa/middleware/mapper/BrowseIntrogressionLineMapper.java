/**
 *
 * 	Builder: 	Mao Qin
 *	Email:		mqin@ymail.com
 *	Date:		2014 - Jun 24, 2014
 *	Project: 	StrasaWeb
 *  Package: 	org.strasa.middleware.mapper
 *	Name:	 BrowseIntrogressionLineMapper.java
 *	
 *
 */
package org.strasa.middleware.mapper;

import java.util.List;

import org.strasa.middleware.model.Germplasm;
import org.strasa.middleware.model.Segment;

public interface BrowseIntrogressionLineMapper
{
//	List<IntrogressionLine> findAllIntrogressionLines();
	List<Germplasm> findAllGermplasms();
	List<Segment> findAllSegments();
}

