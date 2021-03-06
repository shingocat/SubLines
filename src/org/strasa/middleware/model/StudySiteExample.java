package org.strasa.middleware.model;

import java.util.ArrayList;
import java.util.List;

public class StudySiteExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public StudySiteExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studysite
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
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studysite
	 * @mbggenerated  Fri Feb 07 10:48:01 SGT 2014
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table studysite
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

		public Criteria andYearIsNull() {
			addCriterion("year is null");
			return (Criteria) this;
		}

		public Criteria andYearIsNotNull() {
			addCriterion("year is not null");
			return (Criteria) this;
		}

		public Criteria andYearEqualTo(String value) {
			addCriterion("year =", value, "year");
			return (Criteria) this;
		}

		public Criteria andYearNotEqualTo(String value) {
			addCriterion("year <>", value, "year");
			return (Criteria) this;
		}

		public Criteria andYearGreaterThan(String value) {
			addCriterion("year >", value, "year");
			return (Criteria) this;
		}

		public Criteria andYearGreaterThanOrEqualTo(String value) {
			addCriterion("year >=", value, "year");
			return (Criteria) this;
		}

		public Criteria andYearLessThan(String value) {
			addCriterion("year <", value, "year");
			return (Criteria) this;
		}

		public Criteria andYearLessThanOrEqualTo(String value) {
			addCriterion("year <=", value, "year");
			return (Criteria) this;
		}

		public Criteria andYearLike(String value) {
			addCriterion("year like", value, "year");
			return (Criteria) this;
		}

		public Criteria andYearNotLike(String value) {
			addCriterion("year not like", value, "year");
			return (Criteria) this;
		}

		public Criteria andYearIn(List<String> values) {
			addCriterion("year in", values, "year");
			return (Criteria) this;
		}

		public Criteria andYearNotIn(List<String> values) {
			addCriterion("year not in", values, "year");
			return (Criteria) this;
		}

		public Criteria andYearBetween(String value1, String value2) {
			addCriterion("year between", value1, value2, "year");
			return (Criteria) this;
		}

		public Criteria andYearNotBetween(String value1, String value2) {
			addCriterion("year not between", value1, value2, "year");
			return (Criteria) this;
		}

		public Criteria andSeasonIsNull() {
			addCriterion("season is null");
			return (Criteria) this;
		}

		public Criteria andSeasonIsNotNull() {
			addCriterion("season is not null");
			return (Criteria) this;
		}

		public Criteria andSeasonEqualTo(String value) {
			addCriterion("season =", value, "season");
			return (Criteria) this;
		}

		public Criteria andSeasonNotEqualTo(String value) {
			addCriterion("season <>", value, "season");
			return (Criteria) this;
		}

		public Criteria andSeasonGreaterThan(String value) {
			addCriterion("season >", value, "season");
			return (Criteria) this;
		}

		public Criteria andSeasonGreaterThanOrEqualTo(String value) {
			addCriterion("season >=", value, "season");
			return (Criteria) this;
		}

		public Criteria andSeasonLessThan(String value) {
			addCriterion("season <", value, "season");
			return (Criteria) this;
		}

		public Criteria andSeasonLessThanOrEqualTo(String value) {
			addCriterion("season <=", value, "season");
			return (Criteria) this;
		}

		public Criteria andSeasonLike(String value) {
			addCriterion("season like", value, "season");
			return (Criteria) this;
		}

		public Criteria andSeasonNotLike(String value) {
			addCriterion("season not like", value, "season");
			return (Criteria) this;
		}

		public Criteria andSeasonIn(List<String> values) {
			addCriterion("season in", values, "season");
			return (Criteria) this;
		}

		public Criteria andSeasonNotIn(List<String> values) {
			addCriterion("season not in", values, "season");
			return (Criteria) this;
		}

		public Criteria andSeasonBetween(String value1, String value2) {
			addCriterion("season between", value1, value2, "season");
			return (Criteria) this;
		}

		public Criteria andSeasonNotBetween(String value1, String value2) {
			addCriterion("season not between", value1, value2, "season");
			return (Criteria) this;
		}

		public Criteria andSitenameIsNull() {
			addCriterion("sitename is null");
			return (Criteria) this;
		}

		public Criteria andSitenameIsNotNull() {
			addCriterion("sitename is not null");
			return (Criteria) this;
		}

		public Criteria andSitenameEqualTo(String value) {
			addCriterion("sitename =", value, "sitename");
			return (Criteria) this;
		}

		public Criteria andSitenameNotEqualTo(String value) {
			addCriterion("sitename <>", value, "sitename");
			return (Criteria) this;
		}

		public Criteria andSitenameGreaterThan(String value) {
			addCriterion("sitename >", value, "sitename");
			return (Criteria) this;
		}

		public Criteria andSitenameGreaterThanOrEqualTo(String value) {
			addCriterion("sitename >=", value, "sitename");
			return (Criteria) this;
		}

		public Criteria andSitenameLessThan(String value) {
			addCriterion("sitename <", value, "sitename");
			return (Criteria) this;
		}

		public Criteria andSitenameLessThanOrEqualTo(String value) {
			addCriterion("sitename <=", value, "sitename");
			return (Criteria) this;
		}

		public Criteria andSitenameLike(String value) {
			addCriterion("sitename like", value, "sitename");
			return (Criteria) this;
		}

		public Criteria andSitenameNotLike(String value) {
			addCriterion("sitename not like", value, "sitename");
			return (Criteria) this;
		}

		public Criteria andSitenameIn(List<String> values) {
			addCriterion("sitename in", values, "sitename");
			return (Criteria) this;
		}

		public Criteria andSitenameNotIn(List<String> values) {
			addCriterion("sitename not in", values, "sitename");
			return (Criteria) this;
		}

		public Criteria andSitenameBetween(String value1, String value2) {
			addCriterion("sitename between", value1, value2, "sitename");
			return (Criteria) this;
		}

		public Criteria andSitenameNotBetween(String value1, String value2) {
			addCriterion("sitename not between", value1, value2, "sitename");
			return (Criteria) this;
		}

		public Criteria andSitelocationIsNull() {
			addCriterion("sitelocation is null");
			return (Criteria) this;
		}

		public Criteria andSitelocationIsNotNull() {
			addCriterion("sitelocation is not null");
			return (Criteria) this;
		}

		public Criteria andSitelocationEqualTo(String value) {
			addCriterion("sitelocation =", value, "sitelocation");
			return (Criteria) this;
		}

		public Criteria andSitelocationNotEqualTo(String value) {
			addCriterion("sitelocation <>", value, "sitelocation");
			return (Criteria) this;
		}

		public Criteria andSitelocationGreaterThan(String value) {
			addCriterion("sitelocation >", value, "sitelocation");
			return (Criteria) this;
		}

		public Criteria andSitelocationGreaterThanOrEqualTo(String value) {
			addCriterion("sitelocation >=", value, "sitelocation");
			return (Criteria) this;
		}

		public Criteria andSitelocationLessThan(String value) {
			addCriterion("sitelocation <", value, "sitelocation");
			return (Criteria) this;
		}

		public Criteria andSitelocationLessThanOrEqualTo(String value) {
			addCriterion("sitelocation <=", value, "sitelocation");
			return (Criteria) this;
		}

		public Criteria andSitelocationLike(String value) {
			addCriterion("sitelocation like", value, "sitelocation");
			return (Criteria) this;
		}

		public Criteria andSitelocationNotLike(String value) {
			addCriterion("sitelocation not like", value, "sitelocation");
			return (Criteria) this;
		}

		public Criteria andSitelocationIn(List<String> values) {
			addCriterion("sitelocation in", values, "sitelocation");
			return (Criteria) this;
		}

		public Criteria andSitelocationNotIn(List<String> values) {
			addCriterion("sitelocation not in", values, "sitelocation");
			return (Criteria) this;
		}

		public Criteria andSitelocationBetween(String value1, String value2) {
			addCriterion("sitelocation between", value1, value2, "sitelocation");
			return (Criteria) this;
		}

		public Criteria andSitelocationNotBetween(String value1, String value2) {
			addCriterion("sitelocation not between", value1, value2,
					"sitelocation");
			return (Criteria) this;
		}

		public Criteria andLocationidIsNull() {
			addCriterion("locationid is null");
			return (Criteria) this;
		}

		public Criteria andLocationidIsNotNull() {
			addCriterion("locationid is not null");
			return (Criteria) this;
		}

		public Criteria andLocationidEqualTo(Integer value) {
			addCriterion("locationid =", value, "locationid");
			return (Criteria) this;
		}

		public Criteria andLocationidNotEqualTo(Integer value) {
			addCriterion("locationid <>", value, "locationid");
			return (Criteria) this;
		}

		public Criteria andLocationidGreaterThan(Integer value) {
			addCriterion("locationid >", value, "locationid");
			return (Criteria) this;
		}

		public Criteria andLocationidGreaterThanOrEqualTo(Integer value) {
			addCriterion("locationid >=", value, "locationid");
			return (Criteria) this;
		}

		public Criteria andLocationidLessThan(Integer value) {
			addCriterion("locationid <", value, "locationid");
			return (Criteria) this;
		}

		public Criteria andLocationidLessThanOrEqualTo(Integer value) {
			addCriterion("locationid <=", value, "locationid");
			return (Criteria) this;
		}

		public Criteria andLocationidIn(List<Integer> values) {
			addCriterion("locationid in", values, "locationid");
			return (Criteria) this;
		}

		public Criteria andLocationidNotIn(List<Integer> values) {
			addCriterion("locationid not in", values, "locationid");
			return (Criteria) this;
		}

		public Criteria andLocationidBetween(Integer value1, Integer value2) {
			addCriterion("locationid between", value1, value2, "locationid");
			return (Criteria) this;
		}

		public Criteria andLocationidNotBetween(Integer value1, Integer value2) {
			addCriterion("locationid not between", value1, value2, "locationid");
			return (Criteria) this;
		}

		public Criteria andEcotypeidIsNull() {
			addCriterion("ecotypeid is null");
			return (Criteria) this;
		}

		public Criteria andEcotypeidIsNotNull() {
			addCriterion("ecotypeid is not null");
			return (Criteria) this;
		}

		public Criteria andEcotypeidEqualTo(Integer value) {
			addCriterion("ecotypeid =", value, "ecotypeid");
			return (Criteria) this;
		}

		public Criteria andEcotypeidNotEqualTo(Integer value) {
			addCriterion("ecotypeid <>", value, "ecotypeid");
			return (Criteria) this;
		}

		public Criteria andEcotypeidGreaterThan(Integer value) {
			addCriterion("ecotypeid >", value, "ecotypeid");
			return (Criteria) this;
		}

		public Criteria andEcotypeidGreaterThanOrEqualTo(Integer value) {
			addCriterion("ecotypeid >=", value, "ecotypeid");
			return (Criteria) this;
		}

		public Criteria andEcotypeidLessThan(Integer value) {
			addCriterion("ecotypeid <", value, "ecotypeid");
			return (Criteria) this;
		}

		public Criteria andEcotypeidLessThanOrEqualTo(Integer value) {
			addCriterion("ecotypeid <=", value, "ecotypeid");
			return (Criteria) this;
		}

		public Criteria andEcotypeidIn(List<Integer> values) {
			addCriterion("ecotypeid in", values, "ecotypeid");
			return (Criteria) this;
		}

		public Criteria andEcotypeidNotIn(List<Integer> values) {
			addCriterion("ecotypeid not in", values, "ecotypeid");
			return (Criteria) this;
		}

		public Criteria andEcotypeidBetween(Integer value1, Integer value2) {
			addCriterion("ecotypeid between", value1, value2, "ecotypeid");
			return (Criteria) this;
		}

		public Criteria andEcotypeidNotBetween(Integer value1, Integer value2) {
			addCriterion("ecotypeid not between", value1, value2, "ecotypeid");
			return (Criteria) this;
		}

		public Criteria andSoiltypeIsNull() {
			addCriterion("soiltype is null");
			return (Criteria) this;
		}

		public Criteria andSoiltypeIsNotNull() {
			addCriterion("soiltype is not null");
			return (Criteria) this;
		}

		public Criteria andSoiltypeEqualTo(String value) {
			addCriterion("soiltype =", value, "soiltype");
			return (Criteria) this;
		}

		public Criteria andSoiltypeNotEqualTo(String value) {
			addCriterion("soiltype <>", value, "soiltype");
			return (Criteria) this;
		}

		public Criteria andSoiltypeGreaterThan(String value) {
			addCriterion("soiltype >", value, "soiltype");
			return (Criteria) this;
		}

		public Criteria andSoiltypeGreaterThanOrEqualTo(String value) {
			addCriterion("soiltype >=", value, "soiltype");
			return (Criteria) this;
		}

		public Criteria andSoiltypeLessThan(String value) {
			addCriterion("soiltype <", value, "soiltype");
			return (Criteria) this;
		}

		public Criteria andSoiltypeLessThanOrEqualTo(String value) {
			addCriterion("soiltype <=", value, "soiltype");
			return (Criteria) this;
		}

		public Criteria andSoiltypeLike(String value) {
			addCriterion("soiltype like", value, "soiltype");
			return (Criteria) this;
		}

		public Criteria andSoiltypeNotLike(String value) {
			addCriterion("soiltype not like", value, "soiltype");
			return (Criteria) this;
		}

		public Criteria andSoiltypeIn(List<String> values) {
			addCriterion("soiltype in", values, "soiltype");
			return (Criteria) this;
		}

		public Criteria andSoiltypeNotIn(List<String> values) {
			addCriterion("soiltype not in", values, "soiltype");
			return (Criteria) this;
		}

		public Criteria andSoiltypeBetween(String value1, String value2) {
			addCriterion("soiltype between", value1, value2, "soiltype");
			return (Criteria) this;
		}

		public Criteria andSoiltypeNotBetween(String value1, String value2) {
			addCriterion("soiltype not between", value1, value2, "soiltype");
			return (Criteria) this;
		}

		public Criteria andSoilphIsNull() {
			addCriterion("soilph is null");
			return (Criteria) this;
		}

		public Criteria andSoilphIsNotNull() {
			addCriterion("soilph is not null");
			return (Criteria) this;
		}

		public Criteria andSoilphEqualTo(String value) {
			addCriterion("soilph =", value, "soilph");
			return (Criteria) this;
		}

		public Criteria andSoilphNotEqualTo(String value) {
			addCriterion("soilph <>", value, "soilph");
			return (Criteria) this;
		}

		public Criteria andSoilphGreaterThan(String value) {
			addCriterion("soilph >", value, "soilph");
			return (Criteria) this;
		}

		public Criteria andSoilphGreaterThanOrEqualTo(String value) {
			addCriterion("soilph >=", value, "soilph");
			return (Criteria) this;
		}

		public Criteria andSoilphLessThan(String value) {
			addCriterion("soilph <", value, "soilph");
			return (Criteria) this;
		}

		public Criteria andSoilphLessThanOrEqualTo(String value) {
			addCriterion("soilph <=", value, "soilph");
			return (Criteria) this;
		}

		public Criteria andSoilphLike(String value) {
			addCriterion("soilph like", value, "soilph");
			return (Criteria) this;
		}

		public Criteria andSoilphNotLike(String value) {
			addCriterion("soilph not like", value, "soilph");
			return (Criteria) this;
		}

		public Criteria andSoilphIn(List<String> values) {
			addCriterion("soilph in", values, "soilph");
			return (Criteria) this;
		}

		public Criteria andSoilphNotIn(List<String> values) {
			addCriterion("soilph not in", values, "soilph");
			return (Criteria) this;
		}

		public Criteria andSoilphBetween(String value1, String value2) {
			addCriterion("soilph between", value1, value2, "soilph");
			return (Criteria) this;
		}

		public Criteria andSoilphNotBetween(String value1, String value2) {
			addCriterion("soilph not between", value1, value2, "soilph");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table studysite
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
     * This class corresponds to the database table studysite
     *
     * @mbggenerated do_not_delete_during_merge Fri Oct 25 13:11:54 SGT 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}