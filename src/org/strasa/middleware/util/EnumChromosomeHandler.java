/**
 * Builder: Mao Qin
 * Email: mqin@ymail.com
 * Date: 2014 - Jun 25, 2014
 * Project: StrasaWeb
 * Package: org.strasa.middleware.util
 * Name: EnumChromosomeHandler.java
 */
package org.strasa.middleware.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.strasa.middleware.model.enumeration.Chromosome;

public class EnumChromosomeHandler extends BaseTypeHandler<Chromosome>
{
	private Class<Chromosome> type;
	private final Chromosome[] enums;
	
	public EnumChromosomeHandler(Class<Chromosome> type)
	{
		if (type == null)
		{
			throw new IllegalArgumentException("Type argument cannot be null.");
		}
		this.type = type;
		this.enums = type.getEnumConstants();
		if (this.enums == null)
		{
			throw new IllegalArgumentException(type.getSimpleName()
			        + "does not represent an enum type.");
		}
	}
	
	@Override
	public Chromosome getNullableResult(ResultSet rs, String columnName)
	        throws SQLException
	{
//		 according to database type, if it is storing in int type
//		 using getInt() to get value;
		int i = rs.getInt(columnName);
		
		if (rs.wasNull())
		{
			return null;
		} else
		{
//			according to the value of database, find the Chromosome type
			return locateChromosome(i);
		}
	}
	
	@Override
	public Chromosome getNullableResult(ResultSet rs, int columnIndex)
	        throws SQLException
	{
		// according database type, if it is storing in int type
		// using getInt() to get value;
		int i = rs.getInt(columnIndex);
		if (rs.wasNull())
		{
			return null;
		} else
		{
//			according to the value of database, find the Chromosome type
			return locateChromosome(i);
		}
	}
	
	@Override
	public Chromosome getNullableResult(CallableStatement cs, int columnIndex)
	        throws SQLException
	{
		// according database type, if it is storing in int type
		// using getInt() to get value;
		int i = cs.getInt(columnIndex);
		if(cs.wasNull())
		{
			return null;
		} else
		{
//			according to the value of databose, find the Chromosome type
			return locateChromosome(i);
		}
	}
	
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
	        Chromosome parameter, JdbcType jdbcType) throws SQLException
	{
//		baseTypeHandler had help us to assert weather parameter is null
		ps.setInt(i, parameter.getChr());
	}
	
	private Chromosome locateChromosome(int code)
	{
		for (Chromosome chromosome : enums)
		{
			if (chromosome.getChr().equals(Integer.valueOf(code)))
			{
				return chromosome;
			}
		}
		
		throw new IllegalArgumentException("Unknown Enum Type :" + code
		        + " , Please check " + type.getSimpleName());
	}
	
}
