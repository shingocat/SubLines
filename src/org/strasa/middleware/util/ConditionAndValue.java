/**
 *
 * 	Builder: 	Mao Qin
 *	Email:		mqin@ymail.com
 *	Date:		2014 - Jun 29, 2014
 *	Project: 	StrasaWeb
 *  Package: 	org.strasa.middleware.util
 *	Name:	 ConditionAndValue.java
 *	
 *
 */
package org.strasa.middleware.util;

public class ConditionAndValue
{
	private String condition;
	private String value;
	
	public ConditionAndValue()
	{
		
	}
	
	public ConditionAndValue(String condition, String value)
	{
		this.condition = condition;
		this.value = value;
	}


	public String getCondition()
	{
		return condition;
	}

	public void setCondition(String condition)
	{
		this.condition = condition;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	@Override
    public String toString()
    {
	    return "ConditionAndValue [condition=" + condition + ", value=" + value
	            + "]";
    }
	
}

