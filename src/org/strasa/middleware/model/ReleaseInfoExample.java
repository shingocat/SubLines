package org.strasa.middleware.model;

import java.util.ArrayList;
import java.util.List;

public class ReleaseInfoExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table releaseinfo
	 * @mbggenerated  Wed Mar 12 09:07:47 SGT 2014
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table releaseinfo
	 * @mbggenerated  Wed Mar 12 09:07:47 SGT 2014
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table releaseinfo
	 * @mbggenerated  Wed Mar 12 09:07:47 SGT 2014
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table releaseinfo
	 * @mbggenerated  Wed Mar 12 09:07:47 SGT 2014
	 */
	public ReleaseInfoExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table releaseinfo
	 * @mbggenerated  Wed Mar 12 09:07:47 SGT 2014
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table releaseinfo
	 * @mbggenerated  Wed Mar 12 09:07:47 SGT 2014
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table releaseinfo
	 * @mbggenerated  Wed Mar 12 09:07:47 SGT 2014
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table releaseinfo
	 * @mbggenerated  Wed Mar 12 09:07:47 SGT 2014
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table releaseinfo
	 * @mbggenerated  Wed Mar 12 09:07:47 SGT 2014
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table releaseinfo
	 * @mbggenerated  Wed Mar 12 09:07:47 SGT 2014
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table releaseinfo
	 * @mbggenerated  Wed Mar 12 09:07:47 SGT 2014
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table releaseinfo
	 * @mbggenerated  Wed Mar 12 09:07:47 SGT 2014
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table releaseinfo
	 * @mbggenerated  Wed Mar 12 09:07:47 SGT 2014
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table releaseinfo
	 * @mbggenerated  Wed Mar 12 09:07:47 SGT 2014
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table releaseinfo
	 * @mbggenerated  Wed Mar 12 09:07:47 SGT 2014
	 */
	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andIdIsNull() {
			addCriterion("id is null");
			return (Criteria) this;
		}

		public Criteria andIdIsNotNull() {
			addCriterion("id is not null");
			return (Criteria) this;
		}

		public Criteria andIdEqualTo(Integer value) {
			addCriterion("id =", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotEqualTo(Integer value) {
			addCriterion("id <>", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThan(Integer value) {
			addCriterion("id >", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("id >=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThan(Integer value) {
			addCriterion("id <", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThanOrEqualTo(Integer value) {
			addCriterion("id <=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdIn(List<Integer> values) {
			addCriterion("id in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotIn(List<Integer> values) {
			addCriterion("id not in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdBetween(Integer value1, Integer value2) {
			addCriterion("id between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotBetween(Integer value1, Integer value2) {
			addCriterion("id not between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andProgramidIsNull() {
			addCriterion("programid is null");
			return (Criteria) this;
		}

		public Criteria andProgramidIsNotNull() {
			addCriterion("programid is not null");
			return (Criteria) this;
		}

		public Criteria andProgramidEqualTo(Integer value) {
			addCriterion("programid =", value, "programid");
			return (Criteria) this;
		}

		public Criteria andProgramidNotEqualTo(Integer value) {
			addCriterion("programid <>", value, "programid");
			return (Criteria) this;
		}

		public Criteria andProgramidGreaterThan(Integer value) {
			addCriterion("programid >", value, "programid");
			return (Criteria) this;
		}

		public Criteria andProgramidGreaterThanOrEqualTo(Integer value) {
			addCriterion("programid >=", value, "programid");
			return (Criteria) this;
		}

		public Criteria andProgramidLessThan(Integer value) {
			addCriterion("programid <", value, "programid");
			return (Criteria) this;
		}

		public Criteria andProgramidLessThanOrEqualTo(Integer value) {
			addCriterion("programid <=", value, "programid");
			return (Criteria) this;
		}

		public Criteria andProgramidIn(List<Integer> values) {
			addCriterion("programid in", values, "programid");
			return (Criteria) this;
		}

		public Criteria andProgramidNotIn(List<Integer> values) {
			addCriterion("programid not in", values, "programid");
			return (Criteria) this;
		}

		public Criteria andProgramidBetween(Integer value1, Integer value2) {
			addCriterion("programid between", value1, value2, "programid");
			return (Criteria) this;
		}

		public Criteria andProgramidNotBetween(Integer value1, Integer value2) {
			addCriterion("programid not between", value1, value2, "programid");
			return (Criteria) this;
		}

		public Criteria andProjectidIsNull() {
			addCriterion("projectid is null");
			return (Criteria) this;
		}

		public Criteria andProjectidIsNotNull() {
			addCriterion("projectid is not null");
			return (Criteria) this;
		}

		public Criteria andProjectidEqualTo(Integer value) {
			addCriterion("projectid =", value, "projectid");
			return (Criteria) this;
		}

		public Criteria andProjectidNotEqualTo(Integer value) {
			addCriterion("projectid <>", value, "projectid");
			return (Criteria) this;
		}

		public Criteria andProjectidGreaterThan(Integer value) {
			addCriterion("projectid >", value, "projectid");
			return (Criteria) this;
		}

		public Criteria andProjectidGreaterThanOrEqualTo(Integer value) {
			addCriterion("projectid >=", value, "projectid");
			return (Criteria) this;
		}

		public Criteria andProjectidLessThan(Integer value) {
			addCriterion("projectid <", value, "projectid");
			return (Criteria) this;
		}

		public Criteria andProjectidLessThanOrEqualTo(Integer value) {
			addCriterion("projectid <=", value, "projectid");
			return (Criteria) this;
		}

		public Criteria andProjectidIn(List<Integer> values) {
			addCriterion("projectid in", values, "projectid");
			return (Criteria) this;
		}

		public Criteria andProjectidNotIn(List<Integer> values) {
			addCriterion("projectid not in", values, "projectid");
			return (Criteria) this;
		}

		public Criteria andProjectidBetween(Integer value1, Integer value2) {
			addCriterion("projectid between", value1, value2, "projectid");
			return (Criteria) this;
		}

		public Criteria andProjectidNotBetween(Integer value1, Integer value2) {
			addCriterion("projectid not between", value1, value2, "projectid");
			return (Criteria) this;
		}

		public Criteria andGermplasmnameIsNull() {
			addCriterion("germplasmname is null");
			return (Criteria) this;
		}

		public Criteria andGermplasmnameIsNotNull() {
			addCriterion("germplasmname is not null");
			return (Criteria) this;
		}

		public Criteria andGermplasmnameEqualTo(String value) {
			addCriterion("germplasmname =", value, "germplasmname");
			return (Criteria) this;
		}

		public Criteria andGermplasmnameNotEqualTo(String value) {
			addCriterion("germplasmname <>", value, "germplasmname");
			return (Criteria) this;
		}

		public Criteria andGermplasmnameGreaterThan(String value) {
			addCriterion("germplasmname >", value, "germplasmname");
			return (Criteria) this;
		}

		public Criteria andGermplasmnameGreaterThanOrEqualTo(String value) {
			addCriterion("germplasmname >=", value, "germplasmname");
			return (Criteria) this;
		}

		public Criteria andGermplasmnameLessThan(String value) {
			addCriterion("germplasmname <", value, "germplasmname");
			return (Criteria) this;
		}

		public Criteria andGermplasmnameLessThanOrEqualTo(String value) {
			addCriterion("germplasmname <=", value, "germplasmname");
			return (Criteria) this;
		}

		public Criteria andGermplasmnameLike(String value) {
			addCriterion("germplasmname like", value, "germplasmname");
			return (Criteria) this;
		}

		public Criteria andGermplasmnameNotLike(String value) {
			addCriterion("germplasmname not like", value, "germplasmname");
			return (Criteria) this;
		}

		public Criteria andGermplasmnameIn(List<String> values) {
			addCriterion("germplasmname in", values, "germplasmname");
			return (Criteria) this;
		}

		public Criteria andGermplasmnameNotIn(List<String> values) {
			addCriterion("germplasmname not in", values, "germplasmname");
			return (Criteria) this;
		}

		public Criteria andGermplasmnameBetween(String value1, String value2) {
			addCriterion("germplasmname between", value1, value2,
					"germplasmname");
			return (Criteria) this;
		}

		public Criteria andGermplasmnameNotBetween(String value1, String value2) {
			addCriterion("germplasmname not between", value1, value2,
					"germplasmname");
			return (Criteria) this;
		}

		public Criteria andDatasourceIsNull() {
			addCriterion("datasource is null");
			return (Criteria) this;
		}

		public Criteria andDatasourceIsNotNull() {
			addCriterion("datasource is not null");
			return (Criteria) this;
		}

		public Criteria andDatasourceEqualTo(String value) {
			addCriterion("datasource =", value, "datasource");
			return (Criteria) this;
		}

		public Criteria andDatasourceNotEqualTo(String value) {
			addCriterion("datasource <>", value, "datasource");
			return (Criteria) this;
		}

		public Criteria andDatasourceGreaterThan(String value) {
			addCriterion("datasource >", value, "datasource");
			return (Criteria) this;
		}

		public Criteria andDatasourceGreaterThanOrEqualTo(String value) {
			addCriterion("datasource >=", value, "datasource");
			return (Criteria) this;
		}

		public Criteria andDatasourceLessThan(String value) {
			addCriterion("datasource <", value, "datasource");
			return (Criteria) this;
		}

		public Criteria andDatasourceLessThanOrEqualTo(String value) {
			addCriterion("datasource <=", value, "datasource");
			return (Criteria) this;
		}

		public Criteria andDatasourceLike(String value) {
			addCriterion("datasource like", value, "datasource");
			return (Criteria) this;
		}

		public Criteria andDatasourceNotLike(String value) {
			addCriterion("datasource not like", value, "datasource");
			return (Criteria) this;
		}

		public Criteria andDatasourceIn(List<String> values) {
			addCriterion("datasource in", values, "datasource");
			return (Criteria) this;
		}

		public Criteria andDatasourceNotIn(List<String> values) {
			addCriterion("datasource not in", values, "datasource");
			return (Criteria) this;
		}

		public Criteria andDatasourceBetween(String value1, String value2) {
			addCriterion("datasource between", value1, value2, "datasource");
			return (Criteria) this;
		}

		public Criteria andDatasourceNotBetween(String value1, String value2) {
			addCriterion("datasource not between", value1, value2, "datasource");
			return (Criteria) this;
		}

		public Criteria andYearreleaseIsNull() {
			addCriterion("yearrelease is null");
			return (Criteria) this;
		}

		public Criteria andYearreleaseIsNotNull() {
			addCriterion("yearrelease is not null");
			return (Criteria) this;
		}

		public Criteria andYearreleaseEqualTo(String value) {
			addCriterion("yearrelease =", value, "yearrelease");
			return (Criteria) this;
		}

		public Criteria andYearreleaseNotEqualTo(String value) {
			addCriterion("yearrelease <>", value, "yearrelease");
			return (Criteria) this;
		}

		public Criteria andYearreleaseGreaterThan(String value) {
			addCriterion("yearrelease >", value, "yearrelease");
			return (Criteria) this;
		}

		public Criteria andYearreleaseGreaterThanOrEqualTo(String value) {
			addCriterion("yearrelease >=", value, "yearrelease");
			return (Criteria) this;
		}

		public Criteria andYearreleaseLessThan(String value) {
			addCriterion("yearrelease <", value, "yearrelease");
			return (Criteria) this;
		}

		public Criteria andYearreleaseLessThanOrEqualTo(String value) {
			addCriterion("yearrelease <=", value, "yearrelease");
			return (Criteria) this;
		}

		public Criteria andYearreleaseLike(String value) {
			addCriterion("yearrelease like", value, "yearrelease");
			return (Criteria) this;
		}

		public Criteria andYearreleaseNotLike(String value) {
			addCriterion("yearrelease not like", value, "yearrelease");
			return (Criteria) this;
		}

		public Criteria andYearreleaseIn(List<String> values) {
			addCriterion("yearrelease in", values, "yearrelease");
			return (Criteria) this;
		}

		public Criteria andYearreleaseNotIn(List<String> values) {
			addCriterion("yearrelease not in", values, "yearrelease");
			return (Criteria) this;
		}

		public Criteria andYearreleaseBetween(String value1, String value2) {
			addCriterion("yearrelease between", value1, value2, "yearrelease");
			return (Criteria) this;
		}

		public Criteria andYearreleaseNotBetween(String value1, String value2) {
			addCriterion("yearrelease not between", value1, value2,
					"yearrelease");
			return (Criteria) this;
		}

		public Criteria andCountryreleaseIsNull() {
			addCriterion("countryrelease is null");
			return (Criteria) this;
		}

		public Criteria andCountryreleaseIsNotNull() {
			addCriterion("countryrelease is not null");
			return (Criteria) this;
		}

		public Criteria andCountryreleaseEqualTo(String value) {
			addCriterion("countryrelease =", value, "countryrelease");
			return (Criteria) this;
		}

		public Criteria andCountryreleaseNotEqualTo(String value) {
			addCriterion("countryrelease <>", value, "countryrelease");
			return (Criteria) this;
		}

		public Criteria andCountryreleaseGreaterThan(String value) {
			addCriterion("countryrelease >", value, "countryrelease");
			return (Criteria) this;
		}

		public Criteria andCountryreleaseGreaterThanOrEqualTo(String value) {
			addCriterion("countryrelease >=", value, "countryrelease");
			return (Criteria) this;
		}

		public Criteria andCountryreleaseLessThan(String value) {
			addCriterion("countryrelease <", value, "countryrelease");
			return (Criteria) this;
		}

		public Criteria andCountryreleaseLessThanOrEqualTo(String value) {
			addCriterion("countryrelease <=", value, "countryrelease");
			return (Criteria) this;
		}

		public Criteria andCountryreleaseLike(String value) {
			addCriterion("countryrelease like", value, "countryrelease");
			return (Criteria) this;
		}

		public Criteria andCountryreleaseNotLike(String value) {
			addCriterion("countryrelease not like", value, "countryrelease");
			return (Criteria) this;
		}

		public Criteria andCountryreleaseIn(List<String> values) {
			addCriterion("countryrelease in", values, "countryrelease");
			return (Criteria) this;
		}

		public Criteria andCountryreleaseNotIn(List<String> values) {
			addCriterion("countryrelease not in", values, "countryrelease");
			return (Criteria) this;
		}

		public Criteria andCountryreleaseBetween(String value1, String value2) {
			addCriterion("countryrelease between", value1, value2,
					"countryrelease");
			return (Criteria) this;
		}

		public Criteria andCountryreleaseNotBetween(String value1, String value2) {
			addCriterion("countryrelease not between", value1, value2,
					"countryrelease");
			return (Criteria) this;
		}

		public Criteria andSeedavailabilityIsNull() {
			addCriterion("seedavailability is null");
			return (Criteria) this;
		}

		public Criteria andSeedavailabilityIsNotNull() {
			addCriterion("seedavailability is not null");
			return (Criteria) this;
		}

		public Criteria andSeedavailabilityEqualTo(Integer value) {
			addCriterion("seedavailability =", value, "seedavailability");
			return (Criteria) this;
		}

		public Criteria andSeedavailabilityNotEqualTo(Integer value) {
			addCriterion("seedavailability <>", value, "seedavailability");
			return (Criteria) this;
		}

		public Criteria andSeedavailabilityGreaterThan(Integer value) {
			addCriterion("seedavailability >", value, "seedavailability");
			return (Criteria) this;
		}

		public Criteria andSeedavailabilityGreaterThanOrEqualTo(Integer value) {
			addCriterion("seedavailability >=", value, "seedavailability");
			return (Criteria) this;
		}

		public Criteria andSeedavailabilityLessThan(Integer value) {
			addCriterion("seedavailability <", value, "seedavailability");
			return (Criteria) this;
		}

		public Criteria andSeedavailabilityLessThanOrEqualTo(Integer value) {
			addCriterion("seedavailability <=", value, "seedavailability");
			return (Criteria) this;
		}

		public Criteria andSeedavailabilityIn(List<Integer> values) {
			addCriterion("seedavailability in", values, "seedavailability");
			return (Criteria) this;
		}

		public Criteria andSeedavailabilityNotIn(List<Integer> values) {
			addCriterion("seedavailability not in", values, "seedavailability");
			return (Criteria) this;
		}

		public Criteria andSeedavailabilityBetween(Integer value1,
				Integer value2) {
			addCriterion("seedavailability between", value1, value2,
					"seedavailability");
			return (Criteria) this;
		}

		public Criteria andSeedavailabilityNotBetween(Integer value1,
				Integer value2) {
			addCriterion("seedavailability not between", value1, value2,
					"seedavailability");
			return (Criteria) this;
		}

		public Criteria andUseridIsNull() {
			addCriterion("userid is null");
			return (Criteria) this;
		}

		public Criteria andUseridIsNotNull() {
			addCriterion("userid is not null");
			return (Criteria) this;
		}

		public Criteria andUseridEqualTo(Integer value) {
			addCriterion("userid =", value, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridNotEqualTo(Integer value) {
			addCriterion("userid <>", value, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridGreaterThan(Integer value) {
			addCriterion("userid >", value, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridGreaterThanOrEqualTo(Integer value) {
			addCriterion("userid >=", value, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridLessThan(Integer value) {
			addCriterion("userid <", value, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridLessThanOrEqualTo(Integer value) {
			addCriterion("userid <=", value, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridIn(List<Integer> values) {
			addCriterion("userid in", values, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridNotIn(List<Integer> values) {
			addCriterion("userid not in", values, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridBetween(Integer value1, Integer value2) {
			addCriterion("userid between", value1, value2, "userid");
			return (Criteria) this;
		}

		public Criteria andUseridNotBetween(Integer value1, Integer value2) {
			addCriterion("userid not between", value1, value2, "userid");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table releaseinfo
	 * @mbggenerated  Wed Mar 12 09:07:47 SGT 2014
	 */
	public static class Criterion {
		private String condition;
		private Object value;
		private Object secondValue;
		private boolean noValue;
		private boolean singleValue;
		private boolean betweenValue;
		private boolean listValue;
		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue,
				String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table releaseinfo
     *
     * @mbggenerated do_not_delete_during_merge Tue Feb 18 13:50:57 SGT 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}