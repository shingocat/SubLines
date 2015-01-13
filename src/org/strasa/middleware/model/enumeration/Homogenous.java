/**
 *
 * 	Builder: 	Mao Qin
 *	Email:		mqin@ymail.com
 *	Date:		2014 - Jun 25, 2014
 *	Project: 	StrasaWeb
 *  Package: 	org.strasa.middleware.model.enumeration
 *	Name:	 Homogenous.java
 *	
 *
 */
package org.strasa.middleware.model.enumeration;

import java.util.List;

public enum Homogenous
{
	YES("YES"),
	NO("NO");
	
	String status;
	
	Homogenous(String status)
	{
		this.status = status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	@Override
	public String toString()
	{
		return status;
	}
}

