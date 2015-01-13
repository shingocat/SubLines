package org.strasa.middleware.mapper.other;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.strasa.middleware.model.Germplasm;
import org.strasa.middleware.model.Location;
import org.strasa.middleware.model.StudyRawData;
import org.strasa.middleware.model.StudySite;

public interface StudyRawDataBatch {
	String MQL_LOCATION = "SELECT DISTINCT " + "datavalue AS locationname " + " FROM ${param2} WHERE studyid = #{param1} AND dataset = #{param3}  AND datacolumn = 'Location' GROUP BY datarow";

	// String MQL_LOCATION="SELECT DISTINCT "
	// +
	// "MAX(CASE datacolumn WHEN 'Location' THEN datavalue ELSE '' END) AS locationname, "
	// +
	// "MAX(CASE datacolumn WHEN 'Country' THEN datavalue ELSE '' END) AS country, "
	// +
	// "MAX(CASE datacolumn WHEN 'Province' THEN datavalue ELSE '' END) AS province, "
	// +
	// "MAX(CASE datacolumn WHEN 'Region' THEN datavalue ELSE '' END) AS region, "
	// +
	// "MAX(CASE datacolumn WHEN 'Altitude' THEN datavalue ELSE '' END) AS altitude, "
	// +
	// "MAX(CASE datacolumn WHEN 'Latitude' THEN datavalue ELSE '' END) AS latitude, "
	// +
	// "MAX(CASE datacolumn WHEN 'Weather Station' THEN datavalue ELSE '' END) AS weatherstation"
	// +
	// " FROM ${param2} WHERE studyid = #{param1} AND dataset = #{param2} GROUP BY datarow";

	String MQL_SITE = "SELECT DISTINCT " + " MAX(CASE datacolumn WHEN 'Site' THEN datavalue ELSE '' END) AS sitename, " + " MAX(CASE datacolumn WHEN 'Location' THEN datavalue ELSE '' END) AS sitelocation, "
			+ " MAX(CASE datacolumn WHEN 'Year' THEN datavalue ELSE '' END) AS year, " + " MAX(CASE datacolumn WHEN 'Season' THEN datavalue ELSE NULL END) AS season " + " FROM ${param2} WHERE studyid = #{param1} AND dataset = #{param3}  GROUP BY datarow";

	String MQL_SITE_NO_DATASET = "SELECT DISTINCT " + " MAX(CASE datacolumn WHEN 'Site' THEN datavalue ELSE '' END) AS sitename, " + " MAX(CASE datacolumn WHEN 'Location' THEN datavalue ELSE '' END) AS sitelocation, "
			+ " MAX(CASE datacolumn WHEN 'Year' THEN datavalue ELSE '' END) AS year, " + " MAX(CASE datacolumn WHEN 'Season' THEN datavalue ELSE NULL END) AS season " + " FROM ${param2} WHERE studyid = #{param1}  GROUP BY datarow";

	String MQL_Germplasm = "SELECT DISTINCT " + " datavalue AS germplasmname " + " FROM ${param2} WHERE studyid = #{param1,jdbcType=INTEGER} AND dataset = #{param3} AND datacolumn = 'GName' GROUP BY datarow";
	String MQL_GermplasmNoDataset = "SELECT DISTINCT " + " datavalue AS germplasmname " + " FROM ${param2} WHERE studyid = #{param1,jdbcType=INTEGER}  AND datacolumn = 'GName' GROUP BY datarow";

	// String MQL_Germplasm = "SELECT DISTINCT " +
	// " MAX(CASE datacolumn WHEN 'GID' THEN datavalue ELSE '' END) AS gid, " +
	// " MAX(CASE datacolumn WHEN 'GName' THEN datavalue ELSE '' END) AS germplasmname, "
	// +
	// " MAX(CASE datacolumn WHEN 'OtherName' THEN datavalue ELSE '' END) AS othername, "
	// +
	// " MAX(CASE datacolumn WHEN 'Breeder' THEN datavalue ELSE '' END) AS breeder, "
	// +
	// " MAX(CASE datacolumn WHEN 'IRNumber' THEN datavalue ELSE '' END) AS irnumber, "
	// +
	// " MAX(CASE datacolumn WHEN 'Parentage' THEN datavalue ELSE '' END) AS parentage, "
	// +
	// " MAX(CASE datacolumn WHEN 'FemaleParent' THEN datavalue ELSE '' END) AS femaleparent, "
	// +
	// " MAX(CASE datacolumn WHEN 'MaleParent' THEN datavalue ELSE '' END) AS maleparent, "
	// +
	// " MAX(CASE datacolumn WHEN 'SelectionHistory' THEN datavalue ELSE '' END) AS selectionhistory, "
	// +
	// " MAX(CASE datacolumn WHEN 'Source' THEN datavalue ELSE '' END) AS source, "
	// +
	// " MAX(CASE datacolumn WHEN 'Remarks' THEN datavalue ELSE '' END) AS remarks "
	// +
	// " FROM ${param2} WHERE studyid = #{param1,jdbcType=INTEGER} GROUP BY datarow";

	public void insertBatchRaw(List<StudyRawData> datalist);

	public void insertBatchDerived(List<StudyRawData> datalist);

	@Select(MQL_LOCATION)
	public List<Location> getRawLocation(int studyid, String tname, int dataset);

	@Select(MQL_SITE)
	public List<StudySite> getRawSite(int studyid, String tname, int dataset);

	@Select(MQL_SITE_NO_DATASET)
	public List<StudySite> getRawSiteNoDataset(int studyid, String tname);

	@Results({ @Result(column = "gid", property = "gid", jdbcType = JdbcType.INTEGER), @Result(column = "germplasmname", property = "germplasmname", jdbcType = JdbcType.VARCHAR), @Result(column = "othername", property = "othername", jdbcType = JdbcType.VARCHAR),
			@Result(column = "breeder", property = "breeder", jdbcType = JdbcType.VARCHAR), @Result(column = "germplasmtypeid", property = "germplasmtypeid", jdbcType = JdbcType.INTEGER), @Result(column = "irnumber", property = "irnumber", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ircross", property = "ircross", jdbcType = JdbcType.VARCHAR), @Result(column = "parentage", property = "parentage", jdbcType = JdbcType.VARCHAR), @Result(column = "femaleparent", property = "femaleparent", jdbcType = JdbcType.VARCHAR),
			@Result(column = "maleparent", property = "maleparent", jdbcType = JdbcType.VARCHAR), @Result(column = "selectionhistory", property = "selectionhistory", jdbcType = JdbcType.VARCHAR), @Result(column = "source", property = "source", jdbcType = JdbcType.VARCHAR),
			@Result(column = "remarks", property = "remarks", jdbcType = JdbcType.VARCHAR) })
	@Select(MQL_Germplasm)
	public List<Germplasm> getRawGermplasm(int studyid, String tname, int dataset);

	@Results({ @Result(column = "gid", property = "gid", jdbcType = JdbcType.INTEGER), @Result(column = "germplasmname", property = "germplasmname", jdbcType = JdbcType.VARCHAR), @Result(column = "othername", property = "othername", jdbcType = JdbcType.VARCHAR),
			@Result(column = "breeder", property = "breeder", jdbcType = JdbcType.VARCHAR), @Result(column = "germplasmtypeid", property = "germplasmtypeid", jdbcType = JdbcType.INTEGER), @Result(column = "irnumber", property = "irnumber", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ircross", property = "ircross", jdbcType = JdbcType.VARCHAR), @Result(column = "parentage", property = "parentage", jdbcType = JdbcType.VARCHAR), @Result(column = "femaleparent", property = "femaleparent", jdbcType = JdbcType.VARCHAR),
			@Result(column = "maleparent", property = "maleparent", jdbcType = JdbcType.VARCHAR), @Result(column = "selectionhistory", property = "selectionhistory", jdbcType = JdbcType.VARCHAR), @Result(column = "source", property = "source", jdbcType = JdbcType.VARCHAR),
			@Result(column = "remarks", property = "remarks", jdbcType = JdbcType.VARCHAR) })
	@Select(MQL_GermplasmNoDataset)
	public List<Germplasm> getRawGermplasmNoDataset(int studyid, String tname);

	public int countGermplasmOccurence(Map<String, Object> param);

}
