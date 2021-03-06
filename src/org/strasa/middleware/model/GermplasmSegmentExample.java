package org.strasa.middleware.model;

import java.util.ArrayList;
import java.util.List;

public class GermplasmSegmentExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table germplasm_segment
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table germplasm_segment
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table germplasm_segment
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table germplasm_segment
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    public GermplasmSegmentExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table germplasm_segment
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table germplasm_segment
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table germplasm_segment
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table germplasm_segment
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table germplasm_segment
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table germplasm_segment
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table germplasm_segment
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table germplasm_segment
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table germplasm_segment
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table germplasm_segment
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table germplasm_segment
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
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

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
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

        public Criteria andGermplasmIdIsNull() {
            addCriterion("germplasm_id is null");
            return (Criteria) this;
        }

        public Criteria andGermplasmIdIsNotNull() {
            addCriterion("germplasm_id is not null");
            return (Criteria) this;
        }

        public Criteria andGermplasmIdEqualTo(Integer value) {
            addCriterion("germplasm_id =", value, "germplasmId");
            return (Criteria) this;
        }

        public Criteria andGermplasmIdNotEqualTo(Integer value) {
            addCriterion("germplasm_id <>", value, "germplasmId");
            return (Criteria) this;
        }

        public Criteria andGermplasmIdGreaterThan(Integer value) {
            addCriterion("germplasm_id >", value, "germplasmId");
            return (Criteria) this;
        }

        public Criteria andGermplasmIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("germplasm_id >=", value, "germplasmId");
            return (Criteria) this;
        }

        public Criteria andGermplasmIdLessThan(Integer value) {
            addCriterion("germplasm_id <", value, "germplasmId");
            return (Criteria) this;
        }

        public Criteria andGermplasmIdLessThanOrEqualTo(Integer value) {
            addCriterion("germplasm_id <=", value, "germplasmId");
            return (Criteria) this;
        }

        public Criteria andGermplasmIdIn(List<Integer> values) {
            addCriterion("germplasm_id in", values, "germplasmId");
            return (Criteria) this;
        }

        public Criteria andGermplasmIdNotIn(List<Integer> values) {
            addCriterion("germplasm_id not in", values, "germplasmId");
            return (Criteria) this;
        }

        public Criteria andGermplasmIdBetween(Integer value1, Integer value2) {
            addCriterion("germplasm_id between", value1, value2, "germplasmId");
            return (Criteria) this;
        }

        public Criteria andGermplasmIdNotBetween(Integer value1, Integer value2) {
            addCriterion("germplasm_id not between", value1, value2, "germplasmId");
            return (Criteria) this;
        }

        public Criteria andSegmentIdIsNull() {
            addCriterion("segment_id is null");
            return (Criteria) this;
        }

        public Criteria andSegmentIdIsNotNull() {
            addCriterion("segment_id is not null");
            return (Criteria) this;
        }

        public Criteria andSegmentIdEqualTo(Integer value) {
            addCriterion("segment_id =", value, "segmentId");
            return (Criteria) this;
        }

        public Criteria andSegmentIdNotEqualTo(Integer value) {
            addCriterion("segment_id <>", value, "segmentId");
            return (Criteria) this;
        }

        public Criteria andSegmentIdGreaterThan(Integer value) {
            addCriterion("segment_id >", value, "segmentId");
            return (Criteria) this;
        }

        public Criteria andSegmentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("segment_id >=", value, "segmentId");
            return (Criteria) this;
        }

        public Criteria andSegmentIdLessThan(Integer value) {
            addCriterion("segment_id <", value, "segmentId");
            return (Criteria) this;
        }

        public Criteria andSegmentIdLessThanOrEqualTo(Integer value) {
            addCriterion("segment_id <=", value, "segmentId");
            return (Criteria) this;
        }

        public Criteria andSegmentIdIn(List<Integer> values) {
            addCriterion("segment_id in", values, "segmentId");
            return (Criteria) this;
        }

        public Criteria andSegmentIdNotIn(List<Integer> values) {
            addCriterion("segment_id not in", values, "segmentId");
            return (Criteria) this;
        }

        public Criteria andSegmentIdBetween(Integer value1, Integer value2) {
            addCriterion("segment_id between", value1, value2, "segmentId");
            return (Criteria) this;
        }

        public Criteria andSegmentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("segment_id not between", value1, value2, "segmentId");
            return (Criteria) this;
        }

        public Criteria andGermplasmSegmentNumberIsNull() {
            addCriterion("germplasm_segment_number is null");
            return (Criteria) this;
        }

        public Criteria andGermplasmSegmentNumberIsNotNull() {
            addCriterion("germplasm_segment_number is not null");
            return (Criteria) this;
        }

        public Criteria andGermplasmSegmentNumberEqualTo(Integer value) {
            addCriterion("germplasm_segment_number =", value, "germplasmSegmentNumber");
            return (Criteria) this;
        }

        public Criteria andGermplasmSegmentNumberNotEqualTo(Integer value) {
            addCriterion("germplasm_segment_number <>", value, "germplasmSegmentNumber");
            return (Criteria) this;
        }

        public Criteria andGermplasmSegmentNumberGreaterThan(Integer value) {
            addCriterion("germplasm_segment_number >", value, "germplasmSegmentNumber");
            return (Criteria) this;
        }

        public Criteria andGermplasmSegmentNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("germplasm_segment_number >=", value, "germplasmSegmentNumber");
            return (Criteria) this;
        }

        public Criteria andGermplasmSegmentNumberLessThan(Integer value) {
            addCriterion("germplasm_segment_number <", value, "germplasmSegmentNumber");
            return (Criteria) this;
        }

        public Criteria andGermplasmSegmentNumberLessThanOrEqualTo(Integer value) {
            addCriterion("germplasm_segment_number <=", value, "germplasmSegmentNumber");
            return (Criteria) this;
        }

        public Criteria andGermplasmSegmentNumberIn(List<Integer> values) {
            addCriterion("germplasm_segment_number in", values, "germplasmSegmentNumber");
            return (Criteria) this;
        }

        public Criteria andGermplasmSegmentNumberNotIn(List<Integer> values) {
            addCriterion("germplasm_segment_number not in", values, "germplasmSegmentNumber");
            return (Criteria) this;
        }

        public Criteria andGermplasmSegmentNumberBetween(Integer value1, Integer value2) {
            addCriterion("germplasm_segment_number between", value1, value2, "germplasmSegmentNumber");
            return (Criteria) this;
        }

        public Criteria andGermplasmSegmentNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("germplasm_segment_number not between", value1, value2, "germplasmSegmentNumber");
            return (Criteria) this;
        }

        public Criteria andIshomogenousIsNull() {
            addCriterion("isHomogenous is null");
            return (Criteria) this;
        }

        public Criteria andIshomogenousIsNotNull() {
            addCriterion("isHomogenous is not null");
            return (Criteria) this;
        }

        public Criteria andIshomogenousEqualTo(String value) {
            addCriterion("isHomogenous =", value, "ishomogenous");
            return (Criteria) this;
        }

        public Criteria andIshomogenousNotEqualTo(String value) {
            addCriterion("isHomogenous <>", value, "ishomogenous");
            return (Criteria) this;
        }

        public Criteria andIshomogenousGreaterThan(String value) {
            addCriterion("isHomogenous >", value, "ishomogenous");
            return (Criteria) this;
        }

        public Criteria andIshomogenousGreaterThanOrEqualTo(String value) {
            addCriterion("isHomogenous >=", value, "ishomogenous");
            return (Criteria) this;
        }

        public Criteria andIshomogenousLessThan(String value) {
            addCriterion("isHomogenous <", value, "ishomogenous");
            return (Criteria) this;
        }

        public Criteria andIshomogenousLessThanOrEqualTo(String value) {
            addCriterion("isHomogenous <=", value, "ishomogenous");
            return (Criteria) this;
        }

        public Criteria andIshomogenousLike(String value) {
            addCriterion("isHomogenous like", value, "ishomogenous");
            return (Criteria) this;
        }

        public Criteria andIshomogenousNotLike(String value) {
            addCriterion("isHomogenous not like", value, "ishomogenous");
            return (Criteria) this;
        }

        public Criteria andIshomogenousIn(List<String> values) {
            addCriterion("isHomogenous in", values, "ishomogenous");
            return (Criteria) this;
        }

        public Criteria andIshomogenousNotIn(List<String> values) {
            addCriterion("isHomogenous not in", values, "ishomogenous");
            return (Criteria) this;
        }

        public Criteria andIshomogenousBetween(String value1, String value2) {
            addCriterion("isHomogenous between", value1, value2, "ishomogenous");
            return (Criteria) this;
        }

        public Criteria andIshomogenousNotBetween(String value1, String value2) {
            addCriterion("isHomogenous not between", value1, value2, "ishomogenous");
            return (Criteria) this;
        }

        public Criteria andIshomogenousLikeInsensitive(String value) {
            addCriterion("upper(isHomogenous) like", value.toUpperCase(), "ishomogenous");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table germplasm_segment
     *
     * @mbggenerated do_not_delete_during_merge Thu Jun 26 14:16:41 PHT 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table germplasm_segment
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
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

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
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
}