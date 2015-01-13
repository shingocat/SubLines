package org.strasa.middleware.mapper.other;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface ExtendedStudyDataColumnMapper {

	String UPDATE_COLUMN_RAW = "UPDATE studyrawdata SET datacolumn = #{newcolumn} WHERE datacolumn=#{oldcolumn} AND studyid=#{studyid} AND dataset=#{dataset}";
	String UPDATE_COLUMN_DERIVED = "UPDATE studyderiveddata SET datacolumn = #{newcolumn} WHERE datacolumn=#{oldcolumn} AND studyid=#{studyid} AND dataset=#{dataset}";
	
	
	@Update(UPDATE_COLUMN_RAW)
	public void updateRawDataColumn(@Param("newcolumn") String newcolumn, @Param("oldcolumn") String oldcolumn, @Param("studyid") Integer studyid, @Param("dataset") Integer dataset );
	@Update(UPDATE_COLUMN_DERIVED)
	public void updateDerivedDataColumn(@Param("newcolumn") String newcolumn, @Param("oldcolumn") String oldcolumn, @Param("studyid") Integer studyid, @Param("dataset") Integer dataset );
	
}
