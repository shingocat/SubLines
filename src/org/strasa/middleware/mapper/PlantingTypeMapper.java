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
import org.strasa.middleware.model.PlantingType;
import org.strasa.middleware.model.PlantingTypeExample;

public interface PlantingTypeMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table plantingtype
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@SelectProvider(type = PlantingTypeSqlProvider.class, method = "countByExample")
	int countByExample(PlantingTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table plantingtype
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@DeleteProvider(type = PlantingTypeSqlProvider.class, method = "deleteByExample")
	int deleteByExample(PlantingTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table plantingtype
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@Delete({ "delete from plantingtype", "where id = #{id,jdbcType=INTEGER}" })
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table plantingtype
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@Insert({ "insert into plantingtype (planting)",
			"values (#{planting,jdbcType=VARCHAR})" })
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(PlantingType record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table plantingtype
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@InsertProvider(type = PlantingTypeSqlProvider.class, method = "insertSelective")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insertSelective(PlantingType record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table plantingtype
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@SelectProvider(type = PlantingTypeSqlProvider.class, method = "selectByExample")
	@Results({
			@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "planting", property = "planting", jdbcType = JdbcType.VARCHAR) })
	List<PlantingType> selectByExample(PlantingTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table plantingtype
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@Select({ "select", "id, planting", "from plantingtype",
			"where id = #{id,jdbcType=INTEGER}" })
	@Results({
			@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "planting", property = "planting", jdbcType = JdbcType.VARCHAR) })
	PlantingType selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table plantingtype
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@UpdateProvider(type = PlantingTypeSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") PlantingType record,
			@Param("example") PlantingTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table plantingtype
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@UpdateProvider(type = PlantingTypeSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") PlantingType record,
			@Param("example") PlantingTypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table plantingtype
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@UpdateProvider(type = PlantingTypeSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(PlantingType record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table plantingtype
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	@Update({ "update plantingtype",
			"set planting = #{planting,jdbcType=VARCHAR}",
			"where id = #{id,jdbcType=INTEGER}" })
	int updateByPrimaryKey(PlantingType record);
}