package org.strasa.middleware.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.strasa.middleware.model.StudyGermplasmCharacteristics;
import org.strasa.middleware.model.StudyGermplasmCharacteristicsExample;

public interface StudyGermplasmCharacteristicsMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studygermplasmcharacteristics
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@SelectProvider(type = StudyGermplasmCharacteristicsSqlProvider.class, method = "countByExample")
	int countByExample(StudyGermplasmCharacteristicsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studygermplasmcharacteristics
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@DeleteProvider(type = StudyGermplasmCharacteristicsSqlProvider.class, method = "deleteByExample")
	int deleteByExample(StudyGermplasmCharacteristicsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studygermplasmcharacteristics
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@Delete({ "delete from studygermplasmcharacteristics",
			"where id = #{id,jdbcType=INTEGER}" })
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studygermplasmcharacteristics
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@Insert({
			"insert into studygermplasmcharacteristics (studygermplasmid, germplasmname, ",
			"attribute, value)",
			"values (#{studygermplasmid,jdbcType=INTEGER}, #{germplasmname,jdbcType=VARCHAR}, ",
			"#{attribute,jdbcType=VARCHAR}, #{value,jdbcType=VARCHAR})" })
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(StudyGermplasmCharacteristics record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studygermplasmcharacteristics
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@InsertProvider(type = StudyGermplasmCharacteristicsSqlProvider.class, method = "insertSelective")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insertSelective(StudyGermplasmCharacteristics record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studygermplasmcharacteristics
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@SelectProvider(type = StudyGermplasmCharacteristicsSqlProvider.class, method = "selectByExample")
	@Results({
			@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "studygermplasmid", property = "studygermplasmid", jdbcType = JdbcType.INTEGER),
			@Result(column = "germplasmname", property = "germplasmname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "attribute", property = "attribute", jdbcType = JdbcType.VARCHAR),
			@Result(column = "value", property = "value", jdbcType = JdbcType.VARCHAR) })
	List<StudyGermplasmCharacteristics> selectByExample(
			StudyGermplasmCharacteristicsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studygermplasmcharacteristics
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@Select({ "select",
			"id, studygermplasmid, germplasmname, attribute, value",
			"from studygermplasmcharacteristics",
			"where id = #{id,jdbcType=INTEGER}" })
	@Results({
			@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "studygermplasmid", property = "studygermplasmid", jdbcType = JdbcType.INTEGER),
			@Result(column = "germplasmname", property = "germplasmname", jdbcType = JdbcType.VARCHAR),
			@Result(column = "attribute", property = "attribute", jdbcType = JdbcType.VARCHAR),
			@Result(column = "value", property = "value", jdbcType = JdbcType.VARCHAR) })
	StudyGermplasmCharacteristics selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studygermplasmcharacteristics
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@UpdateProvider(type = StudyGermplasmCharacteristicsSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(
			@Param("record") StudyGermplasmCharacteristics record,
			@Param("example") StudyGermplasmCharacteristicsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studygermplasmcharacteristics
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@UpdateProvider(type = StudyGermplasmCharacteristicsSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") StudyGermplasmCharacteristics record,
			@Param("example") StudyGermplasmCharacteristicsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studygermplasmcharacteristics
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@UpdateProvider(type = StudyGermplasmCharacteristicsSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(StudyGermplasmCharacteristics record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studygermplasmcharacteristics
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@Update({ "update studygermplasmcharacteristics",
			"set studygermplasmid = #{studygermplasmid,jdbcType=INTEGER},",
			"germplasmname = #{germplasmname,jdbcType=VARCHAR},",
			"attribute = #{attribute,jdbcType=VARCHAR},",
			"value = #{value,jdbcType=VARCHAR}",
			"where id = #{id,jdbcType=INTEGER}" })
	int updateByPrimaryKey(StudyGermplasmCharacteristics record);
}