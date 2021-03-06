package org.strasa.middleware.model;

public class GermplasmSegment {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column germplasm_segment.id
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column germplasm_segment.germplasm_id
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    private Integer germplasmId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column germplasm_segment.segment_id
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    private Integer segmentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column germplasm_segment.germplasm_segment_number
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    private Integer germplasmSegmentNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column germplasm_segment.isHomogenous
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    private String ishomogenous;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column germplasm_segment.id
     *
     * @return the value of germplasm_segment.id
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column germplasm_segment.id
     *
     * @param id the value for germplasm_segment.id
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column germplasm_segment.germplasm_id
     *
     * @return the value of germplasm_segment.germplasm_id
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    public Integer getGermplasmId() {
        return germplasmId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column germplasm_segment.germplasm_id
     *
     * @param germplasmId the value for germplasm_segment.germplasm_id
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    public void setGermplasmId(Integer germplasmId) {
        this.germplasmId = germplasmId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column germplasm_segment.segment_id
     *
     * @return the value of germplasm_segment.segment_id
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    public Integer getSegmentId() {
        return segmentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column germplasm_segment.segment_id
     *
     * @param segmentId the value for germplasm_segment.segment_id
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    public void setSegmentId(Integer segmentId) {
        this.segmentId = segmentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column germplasm_segment.germplasm_segment_number
     *
     * @return the value of germplasm_segment.germplasm_segment_number
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    public Integer getGermplasmSegmentNumber() {
        return germplasmSegmentNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column germplasm_segment.germplasm_segment_number
     *
     * @param germplasmSegmentNumber the value for germplasm_segment.germplasm_segment_number
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    public void setGermplasmSegmentNumber(Integer germplasmSegmentNumber) {
        this.germplasmSegmentNumber = germplasmSegmentNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column germplasm_segment.isHomogenous
     *
     * @return the value of germplasm_segment.isHomogenous
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    public String getIshomogenous() {
        return ishomogenous;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column germplasm_segment.isHomogenous
     *
     * @param ishomogenous the value for germplasm_segment.isHomogenous
     *
     * @mbggenerated Thu Jun 26 14:16:41 PHT 2014
     */
    public void setIshomogenous(String ishomogenous) {
        this.ishomogenous = ishomogenous;
    }

	@Override
    public String toString()
    {
	    return "GermplasmSegment [id=" + id + ", germplasmId=" + germplasmId
	            + ", segmentId=" + segmentId + ", germplasmSegmentNumber="
	            + germplasmSegmentNumber + ", ishomogenous=" + ishomogenous
	            + "]";
    }
    
    
}