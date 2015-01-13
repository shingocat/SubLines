package org.strasa.middleware.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT_DISTINCT;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import java.util.List;
import java.util.Map;

import org.strasa.middleware.model.StudySite;
import org.strasa.middleware.model.StudySiteExample;
import org.strasa.middleware.model.StudySiteExample.Criteria;
import org.strasa.middleware.model.StudySiteExample.Criterion;

public class StudySiteSqlProvider {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public String countByExample(StudySiteExample example) {
		BEGIN();
		SELECT("count(*)");
		FROM("studysite");
		applyWhere(example, false);
		return SQL();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public String deleteByExample(StudySiteExample example) {
		BEGIN();
		DELETE_FROM("studysite");
		applyWhere(example, false);
		return SQL();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public String insertSelective(StudySite record) {
		BEGIN();
		INSERT_INTO("studysite");
		if (record.getStudyid() != null) {
			VALUES("studyid", "#{studyid,jdbcType=INTEGER}");
		}
		if (record.getDataset() != null) {
			VALUES("dataset", "#{dataset,jdbcType=INTEGER}");
		}
		if (record.getYear() != null) {
			VALUES("year", "#{year,jdbcType=VARCHAR}");
		}
		if (record.getSeason() != null) {
			VALUES("season", "#{season,jdbcType=VARCHAR}");
		}
		if (record.getSitename() != null) {
			VALUES("sitename", "#{sitename,jdbcType=VARCHAR}");
		}
		if (record.getSitelocation() != null) {
			VALUES("sitelocation", "#{sitelocation,jdbcType=VARCHAR}");
		}
		if (record.getLocationid() != null) {
			VALUES("locationid", "#{locationid,jdbcType=INTEGER}");
		}
		if (record.getEcotypeid() != null) {
			VALUES("ecotypeid", "#{ecotypeid,jdbcType=INTEGER}");
		}
		if (record.getSoiltype() != null) {
			VALUES("soiltype", "#{soiltype,jdbcType=VARCHAR}");
		}
		if (record.getSoilph() != null) {
			VALUES("soilph", "#{soilph,jdbcType=VARCHAR}");
		}
		return SQL();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public String selectByExample(StudySiteExample example) {
		BEGIN();
		if (example != null && example.isDistinct()) {
			SELECT_DISTINCT("id");
		} else {
			SELECT("id");
		}
		SELECT("studyid");
		SELECT("dataset");
		SELECT("year");
		SELECT("season");
		SELECT("sitename");
		SELECT("sitelocation");
		SELECT("locationid");
		SELECT("ecotypeid");
		SELECT("soiltype");
		SELECT("soilph");
		FROM("studysite");
		applyWhere(example, false);
		if (example != null && example.getOrderByClause() != null) {
			ORDER_BY(example.getOrderByClause());
		}
		return SQL();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public String updateByExampleSelective(Map<String, Object> parameter) {
		StudySite record = (StudySite) parameter.get("record");
		StudySiteExample example = (StudySiteExample) parameter.get("example");
		BEGIN();
		UPDATE("studysite");
		if (record.getId() != null) {
			SET("id = #{record.id,jdbcType=INTEGER}");
		}
		if (record.getStudyid() != null) {
			SET("studyid = #{record.studyid,jdbcType=INTEGER}");
		}
		if (record.getDataset() != null) {
			SET("dataset = #{record.dataset,jdbcType=INTEGER}");
		}
		if (record.getYear() != null) {
			SET("year = #{record.year,jdbcType=VARCHAR}");
		}
		if (record.getSeason() != null) {
			SET("season = #{record.season,jdbcType=VARCHAR}");
		}
		if (record.getSitename() != null) {
			SET("sitename = #{record.sitename,jdbcType=VARCHAR}");
		}
		if (record.getSitelocation() != null) {
			SET("sitelocation = #{record.sitelocation,jdbcType=VARCHAR}");
		}
		if (record.getLocationid() != null) {
			SET("locationid = #{record.locationid,jdbcType=INTEGER}");
		}
		if (record.getEcotypeid() != null) {
			SET("ecotypeid = #{record.ecotypeid,jdbcType=INTEGER}");
		}
		if (record.getSoiltype() != null) {
			SET("soiltype = #{record.soiltype,jdbcType=VARCHAR}");
		}
		if (record.getSoilph() != null) {
			SET("soilph = #{record.soilph,jdbcType=VARCHAR}");
		}
		applyWhere(example, true);
		return SQL();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public String updateByExample(Map<String, Object> parameter) {
		BEGIN();
		UPDATE("studysite");
		SET("id = #{record.id,jdbcType=INTEGER}");
		SET("studyid = #{record.studyid,jdbcType=INTEGER}");
		SET("dataset = #{record.dataset,jdbcType=INTEGER}");
		SET("year = #{record.year,jdbcType=VARCHAR}");
		SET("season = #{record.season,jdbcType=VARCHAR}");
		SET("sitename = #{record.sitename,jdbcType=VARCHAR}");
		SET("sitelocation = #{record.sitelocation,jdbcType=VARCHAR}");
		SET("locationid = #{record.locationid,jdbcType=INTEGER}");
		SET("ecotypeid = #{record.ecotypeid,jdbcType=INTEGER}");
		SET("soiltype = #{record.soiltype,jdbcType=VARCHAR}");
		SET("soilph = #{record.soilph,jdbcType=VARCHAR}");
		StudySiteExample example = (StudySiteExample) parameter.get("example");
		applyWhere(example, true);
		return SQL();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public String updateByPrimaryKeySelective(StudySite record) {
		BEGIN();
		UPDATE("studysite");
		if (record.getStudyid() != null) {
			SET("studyid = #{studyid,jdbcType=INTEGER}");
		}
		if (record.getDataset() != null) {
			SET("dataset = #{dataset,jdbcType=INTEGER}");
		}
		if (record.getYear() != null) {
			SET("year = #{year,jdbcType=VARCHAR}");
		}
		if (record.getSeason() != null) {
			SET("season = #{season,jdbcType=VARCHAR}");
		}
		if (record.getSitename() != null) {
			SET("sitename = #{sitename,jdbcType=VARCHAR}");
		}
		if (record.getSitelocation() != null) {
			SET("sitelocation = #{sitelocation,jdbcType=VARCHAR}");
		}
		if (record.getLocationid() != null) {
			SET("locationid = #{locationid,jdbcType=INTEGER}");
		}
		if (record.getEcotypeid() != null) {
			SET("ecotypeid = #{ecotypeid,jdbcType=INTEGER}");
		}
		if (record.getSoiltype() != null) {
			SET("soiltype = #{soiltype,jdbcType=VARCHAR}");
		}
		if (record.getSoilph() != null) {
			SET("soilph = #{soilph,jdbcType=VARCHAR}");
		}
		WHERE("id = #{id,jdbcType=INTEGER}");
		return SQL();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	protected void applyWhere(StudySiteExample example,
			boolean includeExamplePhrase) {
		if (example == null) {
			return;
		}
		String parmPhrase1;
		String parmPhrase1_th;
		String parmPhrase2;
		String parmPhrase2_th;
		String parmPhrase3;
		String parmPhrase3_th;
		if (includeExamplePhrase) {
			parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
			parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
			parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
			parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
			parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
			parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
		} else {
			parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
			parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
			parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
			parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
			parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
			parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
		}
		StringBuilder sb = new StringBuilder();
		List<Criteria> oredCriteria = example.getOredCriteria();
		boolean firstCriteria = true;
		for (int i = 0; i < oredCriteria.size(); i++) {
			Criteria criteria = oredCriteria.get(i);
			if (criteria.isValid()) {
				if (firstCriteria) {
					firstCriteria = false;
				} else {
					sb.append(" or ");
				}
				sb.append('(');
				List<Criterion> criterions = criteria.getAllCriteria();
				boolean firstCriterion = true;
				for (int j = 0; j < criterions.size(); j++) {
					Criterion criterion = criterions.get(j);
					if (firstCriterion) {
						firstCriterion = false;
					} else {
						sb.append(" and ");
					}
					if (criterion.isNoValue()) {
						sb.append(criterion.getCondition());
					} else if (criterion.isSingleValue()) {
						if (criterion.getTypeHandler() == null) {
							sb.append(String.format(parmPhrase1,
									criterion.getCondition(), i, j));
						} else {
							sb.append(String.format(parmPhrase1_th,
									criterion.getCondition(), i, j,
									criterion.getTypeHandler()));
						}
					} else if (criterion.isBetweenValue()) {
						if (criterion.getTypeHandler() == null) {
							sb.append(String.format(parmPhrase2,
									criterion.getCondition(), i, j, i, j));
						} else {
							sb.append(String.format(parmPhrase2_th,
									criterion.getCondition(), i, j,
									criterion.getTypeHandler(), i, j,
									criterion.getTypeHandler()));
						}
					} else if (criterion.isListValue()) {
						sb.append(criterion.getCondition());
						sb.append(" (");
						List<?> listItems = (List<?>) criterion.getValue();
						boolean comma = false;
						for (int k = 0; k < listItems.size(); k++) {
							if (comma) {
								sb.append(", ");
							} else {
								comma = true;
							}
							if (criterion.getTypeHandler() == null) {
								sb.append(String.format(parmPhrase3, i, j, k));
							} else {
								sb.append(String.format(parmPhrase3_th, i, j,
										k, criterion.getTypeHandler()));
							}
						}
						sb.append(')');
					}
				}
				sb.append(')');
			}
		}
		if (sb.length() > 0) {
			WHERE(sb.toString());
		}
	}
}