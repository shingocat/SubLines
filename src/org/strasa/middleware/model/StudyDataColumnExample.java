package org.strasa.middleware.model;

import java.util.ArrayList;
import java.util.List;

public class StudyDataColumnExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table studydatacolumn
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table studydatacolumn
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table studydatacolumn
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studydatacolumn
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public StudyDataColumnExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studydatacolumn
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studydatacolumn
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studydatacolumn
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studydatacolumn
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studydatacolumn
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studydatacolumn
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studydatacolumn
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studydatacolumn
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
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studydatacolumn
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studydatacolumn
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table studydatacolumn
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

		public Criteria andColumnheaderIsNull() {
			addCriterion("columnheader is null");
			return (Criteria) this;
		}

		public Criteria andColumnheaderIsNotNull() {
			addCriterion("columnheader is not null");
			return (Criteria) this;
		}

		public Criteria andColumnheaderEqualTo(String value) {
			addCriterion("columnheader =", value, "columnheader");
			return (Criteria) this;
		}

		public Criteria andColumnheaderNotEqualTo(String value) {
			addCriterion("columnheader <>", value, "columnheader");
			return (Criteria) this;
		}

		public Criteria andColumnheaderGreaterThan(String value) {
			addCriterion("columnheader >", value, "columnheader");
			return (Criteria) this;
		}

		public Criteria andColumnheaderGreaterThanOrEqualTo(String value) {
			addCriterion("columnheader >=", value, "columnheader");
			return (Criteria) this;
		}

		public Criteria andColumnheaderLessThan(String value) {
			addCriterion("columnheader <", value, "columnheader");
			return (Criteria) this;
		}

		public Criteria andColumnheaderLessThanOrEqualTo(String value) {
			addCriterion("columnheader <=", value, "columnheader");
			return (Criteria) this;
		}

		public Criteria andColumnheaderLike(String value) {
			addCriterion("columnheader like", value, "columnheader");
			return (Criteria) this;
		}

		public Criteria andColumnheaderNotLike(String value) {
			addCriterion("columnheader not like", value, "columnheader");
			return (Criteria) this;
		}

		public Criteria andColumnheaderIn(List<String> values) {
			addCriterion("columnheader in", values, "columnheader");
			return (Criteria) this;
		}

		public Criteria andColumnheaderNotIn(List<String> values) {
			addCriterion("columnheader not in", values, "columnheader");
			return (Criteria) this;
		}

		public Criteria andColumnheaderBetween(String value1, String value2) {
			addCriterion("columnheader between", value1, value2, "columnheader");
			return (Criteria) this;
		}

		public Criteria andColumnheaderNotBetween(String value1, String value2) {
			addCriterion("columnheader not between", value1, value2,
					"columnheader");
			return (Criteria) this;
		}

		public Criteria andDatatypeIsNull() {
			addCriterion("datatype is null");
			return (Criteria) this;
		}

		public Criteria andDatatypeIsNotNull() {
			addCriterion("datatype is not null");
			return (Criteria) this;
		}

		public Criteria andDatatypeEqualTo(String value) {
			addCriterion("datatype =", value, "datatype");
			return (Criteria) this;
		}

		public Criteria andDatatypeNotEqualTo(String value) {
			addCriterion("datatype <>", value, "datatype");
			return (Criteria) this;
		}

		public Criteria andDatatypeGreaterThan(String value) {
			addCriterion("datatype >", value, "datatype");
			return (Criteria) this;
		}

		public Criteria andDatatypeGreaterThanOrEqualTo(String value) {
			addCriterion("datatype >=", value, "datatype");
			return (Criteria) this;
		}

		public Criteria andDatatypeLessThan(String value) {
			addCriterion("datatype <", value, "datatype");
			return (Criteria) this;
		}

		public Criteria andDatatypeLessThanOrEqualTo(String value) {
			addCriterion("datatype <=", value, "datatype");
			return (Criteria) this;
		}

		public Criteria andDatatypeLike(String value) {
			addCriterion("datatype like", value, "datatype");
			return (Criteria) this;
		}

		public Criteria andDatatypeNotLike(String value) {
			addCriterion("datatype not like", value, "datatype");
			return (Criteria) this;
		}

		public Criteria andDatatypeIn(List<String> values) {
			addCriterion("datatype in", values, "datatype");
			return (Criteria) this;
		}

		public Criteria andDatatypeNotIn(List<String> values) {
			addCriterion("datatype not in", values, "datatype");
			return (Criteria) this;
		}

		public Criteria andDatatypeBetween(String value1, String value2) {
			addCriterion("datatype between", value1, value2, "datatype");
			return (Criteria) this;
		}

		public Criteria andDatatypeNotBetween(String value1, String value2) {
			addCriterion("datatype not between", value1, value2, "datatype");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table studydatacolumn
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
     * This class corresponds to the database table studydatacolumn
     *
     * @mbggenerated do_not_delete_during_merge Wed Nov 06 13:23:38 SGT 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}