/**
 *
 * 	Builder: 	Mao Qin
 *	Email:		mqin@ymail.com
 *	Date:		2014 - Jun 24, 2014
 *	Project: 	StrasaWeb
 *  Package: 	org.strasa.middleware.model.enumeration
 *	Name:	 Chromosome.java
 *	
 *
 */
package org.strasa.middleware.model.enumeration;

public enum Chromosome
{
	
	Chromosome_1(1),
	Chromosome_2(2),
	Chromosome_3(3),
	Chromosome_4(4),
	Chromosome_5(5),
	Chromosome_6(6),
	Chromosome_7(7),
	Chromosome_8(8),
	Chromosome_9(9),
	Chromosome_10(10),
	Chromosome_11(11),
	Chromosome_12(12),
	Chromosome_ALL(0);
	
	Integer chr;
	
	Chromosome(int chr)
	{
		this.chr = chr;
	}
	
	public Integer getChr()
	{
		return chr;
	}
	
	@Override
	public String toString()
	{
		return String.valueOf(chr);
	}
}

