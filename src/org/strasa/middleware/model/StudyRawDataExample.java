package org.strasa.middleware.model;

import java.util.ArrayList;
import java.util.List;

public class StudyRawDataExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table studyrawdata
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table studyrawdata
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table studyrawdata
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studyrawdata
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public StudyRawDataExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studyrawdata
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studyrawdata
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studyrawdata
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studyrawdata
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studyrawdata
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studyrawdata
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studyrawdata
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studyrawdata
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studyrawdata
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studyrawdata
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table studyrawdata
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
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

		public Criteria andStudyidIsNull() {
			addCriterion("studyid is null");
			return (Criteria) this;
		}

		public Criteria andStudyidIsNotNull() {
			addCriterion("studyid is not null");
			return (Criteria) this;
		}

		public Criteria andStudyidEqualTo(Integer value) {
			addCriterion("studyid =", value, "studyid");
			return (Criteria) this;
		}

		public Criteria andStudyidNotEqualTo(Integer value) {
			addCriterion("studyid <>", value, "studyid");
			return (Criteria) this;
		}

		public Criteria andStudyidGreaterThan(Integer value) {
			addCriterion("studyid >", value, "studyid");
			return (Criteria) this;
		}

		public Criteria andStudyidGreaterThanOrEqualTo(Integer value) {
			addCriterion("studyid >=", value, "studyid");
			return (Criteria) this;
		}

		public Criteria andStudyidLessThan(Integer value) {
			addCriterion("studyid <", value, "studyid");
			return (Criteria) this;
		}

		public Criteria andStudyidLessThanOrEqualTo(Integer value) {
			addCriterion("studyid <=", value, "studyid");
			return (Criteria) this;
		}

		public Criteria andStudyidIn(List<Integer> values) {
			addCriterion("studyid in", values, "studyid");
			return (Criteria) this;
		}

		public Criteria andStudyidNotIn(List<Integer> values) {
			addCriterion("studyid not in", values, "studyid");
			return (Criteria) this;
		}

		public Criteria andStudyidBetween(Integer value1, Integer value2) {
			addCriterion("studyid between", value1, value2, "studyid");
			return (Criteria) this;
		}

		public Criteria andStudyidNotBetween(Integer value1, Integer value2) {
			addCriterion("studyid not between", value1, value2, "studyid");
			return (Criteria) this;
		}

		public Criteria andDatasetIsNull() {
			addCriterion("dataset is null");
			return (Criteria) this;
		}

		public Criteria andDatasetIsNotNull() {
			addCriterion("dataset is not null");
			return (Criteria) this;
		}

		public Criteria andDatasetEqualTo(Integer value) {
			addCriterion("dataset =", value, "dataset");
			return (Criteria) this;
		}

		public Criteria andDatasetNotEqualTo(Integer value) {
			addCriterion("dataset <>", value, "dataset");
			return (Criteria) this;
		}

		public Criteria andDatasetGreaterThan(Integer value) {
			addCriterion("dataset >", value, "dataset");
			return (Criteria) this;
		}

		public Criteria andDatasetGreaterThanOrEqualTo(Integer value) {
			addCriterion("dataset >=", value, "dataset");
			return (Criteria) this;
		}

		public Criteria andDatasetLessThan(Integer value) {
			addCriterion("dataset <", value, "dataset");
			return (Criteria) this;
		}

		public Criteria andDatasetLessThanOrEqualTo(Integer value) {
			addCriterion("dataset <=", value, "dataset");
			return (Criteria) this;
		}

		public Criteria andDatasetIn(List<Integer> values) {
			addCriterion("dataset in", values, "dataset");
			return (Criteria) this;
		}

		public Criteria andDatasetNotIn(List<Integer> values) {
			addCriterion("dataset not in", values, "dataset");
			return (Criteria) this;
		}

		public Criteria andDatasetBetween(Integer value1, Integer value2) {
			addCriterion("dataset between", value1, value2, "dataset");
			return (Criteria) this;
		}

		public Criteria andDatasetNotBetween(Integer value1, Integer value2) {
			addCriterion("dataset not between", value1, value2, "dataset");
			return (Criteria) this;
		}

		public Criteria andDatarowIsNull() {
			addCriterion("datarow is null");
			return (Criteria) this;
		}

		public Criteria andDatarowIsNotNull() {
			addCriterion("datarow is not null");
			return (Criteria) this;
		}

		public Criteria andDatarowEqualTo(Integer value) {
			addCriterion("datarow =", value, "datarow");
			return (Criteria) this;
		}

		public Criteria andDatarowNotEqualTo(Integer value) {
			addCriterion("datarow <>", value, "datarow");
			return (Criteria) this;
		}

		public Criteria andDatarowGreaterThan(Integer value) {
			addCriterion("datarow >", value, "datarow");
			return (Criteria) this;
		}

		public Criteria andDatarowGreaterThanOrEqualTo(Integer value) {
			addCriterion("datarow >=", value, "datarow");
			return (Criteria) this;
		}

		public Criteria andDatarowLessThan(Integer value) {
			addCriterion("datarow <", value, "datarow");
			return (Criteria) this;
		}

		public Criteria andDatarowLessThanOrEqualTo(Integer value) {
			addCriterion("datarow <=", value, "datarow");
			return (Criteria) this;
		}

		public Criteria andDatarowIn(List<Integer> values) {
			addCriterion("datarow in", values, "datarow");
			return (Criteria) this;
		}

		public Criteria andDatarowNotIn(List<Integer> values) {
			addCriterion("datarow not in", values, "datarow");
			return (Criteria) this;
		}

		public Criteria andDatarowBetween(Integer value1, Integer value2) {
			addCriterion("datarow between", value1, value2, "datarow");
			return (Criteria) this;
		}

		public Criteria andDatarowNotBetween(Integer value1, Integer value2) {
			addCriterion("datarow not between", value1, value2, "datarow");
			return (Criteria) this;
		}

		public Criteria andDatacolumnIsNull() {
			addCriterion("datacolumn is null");
			return (Criteria) this;
		}

		public Criteria andDatacolumnIsNotNull() {
			addCriterion("datacolumn is not null");
			return (Criteria) this;
		}

		public Criteria andDatacolumnEqualTo(String value) {
			addCriterion("datacolumn =", value, "datacolumn");
			return (Criteria) this;
		}

		public Criteria andDatacolumnNotEqualTo(String value) {
			addCriterion("datacolumn <>", value, "datacolumn");
			return (Criteria) this;
		}

		public Criteria andDatacolumnGreaterThan(String value) {
			addCriterion("datacolumn >", value, "datacolumn");
			return (Criteria) this;
		}

		public Criteria andDatacolumnGreaterThanOrEqualTo(String value) {
			addCriterion("datacolumn >=", value, "datacolumn");
			return (Criteria) this;
		}

		public Criteria andDatacolumnLessThan(String value) {
			addCriterion("datacolumn <", value, "datacolumn");
			return (Criteria) this;
		}

		public Criteria andDatacolumnLessThanOrEqualTo(String value) {
			addCriterion("datacolumn <=", value, "datacolumn");
			return (Criteria) this;
		}

		public Criteria andDatacolumnLike(String value) {
			addCriterion("datacolumn like", value, "datacolumn");
			return (Criteria) this;
		}

		public Criteria andDatacolumnNotLike(String value) {
			addCriterion("datacolumn not like", value, "datacolumn");
			return (Criteria) this;
		}

		public Criteria andDatacolumnIn(List<String> values) {
			addCriterion("datacolumn in", values, "datacolumn");
			return (Criteria) this;
		}

		public Criteria andDatacolumnNotIn(List<String> values) {
			addCriterion("datacolumn not in", values, "datacolumn");
			return (Criteria) this;
		}

		public Criteria andDatacolumnBetween(String value1, String value2) {
			addCriterion("datacolumn between", value1, value2, "datacolumn");
			return (Criteria) this;
		}

		public Criteria andDatacolumnNotBetween(String value1, String value2) {
			addCriterion("datacolumn not between", value1, value2, "datacolumn");
			return (Criteria) this;
		}

		public Criteria andDatavalueIsNull() {
			addCriterion("datavalue is null");
			return (Criteria) this;
		}

		public Criteria andDatavalueIsNotNull() {
			addCriterion("datavalue is not null");
			return (Criteria) this;
		}

		public Criteria andDatavalueEqualTo(String value) {
			addCriterion("datavalue =", value, "datavalue");
			return (Criteria) this;
		}

		public Criteria andDatavalueNotEqualTo(String value) {
			addCriterion("datavalue <>", value, "datavalue");
			return (Criteria) this;
		}

		public Criteria andDatavalueGreaterThan(String value) {
			addCriterion("datavalue >", value, "datavalue");
			return (Criteria) this;
		}

		public Criteria andDatavalueGreaterThanOrEqualTo(String value) {
			addCriterion("datavalue >=", value, "datavalue");
			return (Criteria) this;
		}

		public Criteria andDatavalueLessThan(String value) {
			addCriterion("datavalue <", value, "datavalue");
			return (Criteria) this;
		}

		public Criteria andDatavalueLessThanOrEqualTo(String value) {
			addCriterion("datavalue <=", value, "datavalue");
			return (Criteria) this;
		}

		public Criteria andDatavalueLike(String value) {
			addCriterion("datavalue like", value, "datavalue");
			return (Criteria) this;
		}

		public Criteria andDatavalueNotLike(String value) {
			addCriterion("datavalue not like", value, "datavalue");
			return (Criteria) this;
		}

		public Criteria andDatavalueIn(List<String> values) {
			addCriterion("datavalue in", values, "datavalue");
			return (Criteria) this;
		}

		public Criteria andDatavalueNotIn(List<String> values) {
			addCriterion("datavalue not in", values, "datavalue");
			return (Criteria) this;
		}

		public Criteria andDatavalueBetween(String value1, String value2) {
			addCriterion("datavalue between", value1, value2, "datavalue");
			return (Criteria) this;
		}

		public Criteria andDatavalueNotBetween(String value1, String value2) {
			addCriterion("datavalue not between", value1, value2, "datavalue");
			return (Criteria) this;
		}

		public Criteria andSharedIsNull() {
			addCriterion("shared is null");
			return (Criteria) this;
		}

		public Criteria andSharedIsNotNull() {
			addCriterion("shared is not null");
			return (Criteria) this;
		}

		public Criteria andSharedEqualTo(Boolean value) {
			addCriterion("shared =", value, "shared");
			return (Criteria) this;
		}

		public Criteria andSharedNotEqualTo(Boolean value) {
			addCriterion("shared <>", value, "shared");
			return (Criteria) this;
		}

		public Criteria andSharedGreaterThan(Boolean value) {
			addCriterion("shared >", value, "shared");
			return (Criteria) this;
		}

		public Criteria andSharedGreaterThanOrEqualTo(Boolean value) {
			addCriterion("shared >=", value, "shared");
			return (Criteria) this;
		}

		public Criteria andSharedLessThan(Boolean value) {
			addCriterion("shared <", value, "shared");
			return (Criteria) this;
		}

		public Criteria andSharedLessThanOrEqualTo(Boolean value) {
			addCriterion("shared <=", value, "shared");
			return (Criteria) this;
		}

		public Criteria andSharedIn(List<Boolean> values) {
			addCriterion("shared in", values, "shared");
			return (Criteria) this;
		}

		public Criteria andSharedNotIn(List<Boolean> values) {
			addCriterion("shared not in", values, "shared");
			return (Criteria) this;
		}

		public Criteria andSharedBetween(Boolean value1, Boolean value2) {
			addCriterion("shared between", value1, value2, "shared");
			return (Criteria) this;
		}

		public Criteria andSharedNotBetween(Boolean value1, Boolean value2) {
			addCriterion("shared not between", value1, value2, "shared");
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
	 * This class was generated by MyBatis Generator. This class corresponds to the database table studyrawdata
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
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
     * This class corresponds to the database table studyrawdata
     *
     * @mbggenerated do_not_delete_during_merge Fri Oct 25 13:11:54 SGT 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}