/**
 *
 * 	Builder: 	Mao Qin
 *	Email:		mqin@ymail.com
 *	Date:		2014 - Jul 7, 2014
 *	Project: 	StrasaWeb
 *  Package: 	org.strasa.middleware.mapper
 *	Name:	 BrowseSegmentMapper.java
 *	
 *
 */
package org.strasa.middleware.mapper;

import java.util.List;

import org.strasa.middleware.model.Segment;

public interface BrowseSegmentMapper
{
	List<Segment> findSegmentByDynamicSQL();
	List<String> findDistinctDonorParent();
}

