package org.strasa.web.analysis.view.model;

import java.util.Arrays;

public class ILAnalysisModel extends AnalysisModel {
	
	private String analysisType; //chisq(CS), singlemarker(SM), multimarker(MM)
	private String genoFile;
	private String phenoFile;
	private String mapFile;
	private Boolean isIncludeHT;
	// for multi marker
	private String regMethodOnMM;
	private String pvalMethodOnMM;
	private Double alphaOnMM;
	private Integer bootstrapOnMM;
	private Integer nfoldsOnMM;
	private Double stepOnMM;
	private Integer maxTryOnMM;
	//for chisq
	private Boolean simPvalueOnCS;
	private Integer bootStrapTimesOnCS;
	private String refGenoFile;
	//for single marker
	private Integer digitsOnSM;
	private String testOnSM;
	//genotypic data(GD)
	private String genoColumnOnGD;
	private String dpCodeOnGD;
	private String rpCodeOnGD;
	private String htCodeOnGD;
	private String naCodeOnGD;
	private Integer bcnOnGD;
	private Integer fnOnGD;
	//phenotypic data (PD)
	private String dataTypeOnPD;
	private String naCodeOnPD;
	private String [] respsOnPD;
	private String exptlDesignOnPD;
	private String genoFactOnPD;
	private String envFactOnPD;
	private String blockFactOnPD;
	private String repFactOnPD;
	private String rowFactOnPD;
	private String colFactOnPD;
	//map data (MD)
	private String marColOnMD;
	private String chrColOnMD;
	private String posColOnMD;
	private String unitOnMD;
	
	public String getAnalysisType() {
		return analysisType;
	}

	public void setAnalysisType(String analysisType) {
		this.analysisType = analysisType;
	}

	public String getGenoFile() {
		return genoFile;
	}

	public void setGenoFile(String genoFile) {
		this.genoFile = genoFile;
	}

	public String getPhenoFile() {
		return phenoFile;
	}

	public void setPhenoFile(String phenoFile) {
		this.phenoFile = phenoFile;
	}

	public String getMapFile() {
		return mapFile;
	}

	public void setMapFile(String mapFile) {
		this.mapFile = mapFile;
	}

	public Boolean getIsIncludeHT() {
		return isIncludeHT;
	}

	public void setIsIncludeHT(Boolean isIncludeHT) {
		this.isIncludeHT = isIncludeHT;
	}

	public String getRegMethodOnMM() {
		return regMethodOnMM;
	}

	public void setRegMethodOnMM(String regMethodOnMM) {
		this.regMethodOnMM = regMethodOnMM;
	}

	public String getPvalMethodOnMM() {
		return pvalMethodOnMM;
	}

	public void setPvalMethodOnMM(String pvalMethodOnMM) {
		this.pvalMethodOnMM = pvalMethodOnMM;
	}

	public Double getAlphaOnMM() {
		return alphaOnMM;
	}

	public void setAlphaOnMM(Double alphaOnMM) {
		this.alphaOnMM = alphaOnMM;
	}

	public Integer getBootstrapOnMM() {
		return bootstrapOnMM;
	}

	public void setBootstrapOnMM(Integer bootstrapOnMM) {
		this.bootstrapOnMM = bootstrapOnMM;
	}

	public Integer getNfoldsOnMM() {
		return nfoldsOnMM;
	}

	public void setNfoldsOnMM(Integer nfoldsOnMM) {
		this.nfoldsOnMM = nfoldsOnMM;
	}

	public Double getStepOnMM() {
		return stepOnMM;
	}

	public void setStepOnMM(Double stepOnMM) {
		this.stepOnMM = stepOnMM;
	}

	public Integer getMaxTryOnMM() {
		return maxTryOnMM;
	}

	public void setMaxTryOnMM(Integer maxTryOnMM) {
		this.maxTryOnMM = maxTryOnMM;
	}

	public Boolean getSimPvalueOnCS() {
		return simPvalueOnCS;
	}

	public void setSimPvalueOnCS(Boolean simPvalueOnCS) {
		this.simPvalueOnCS = simPvalueOnCS;
	}

	public Integer getBootStrapTimesOnCS() {
		return bootStrapTimesOnCS;
	}

	public void setBootStrapTimesOnCS(Integer bootStrapTimesOnCS) {
		this.bootStrapTimesOnCS = bootStrapTimesOnCS;
	}

	public String getRefGenoFile() {
		return refGenoFile;
	}

	public void setRefGenoFile(String refGenoFile) {
		this.refGenoFile = refGenoFile;
	}

	public Integer getDigitsOnSM() {
		return digitsOnSM;
	}

	public void setDigitsOnSM(Integer digitsOnSM) {
		this.digitsOnSM = digitsOnSM;
	}

	public String getTestOnSM() {
		return testOnSM;
	}

	public void setTestOnSM(String testOnSM) {
		this.testOnSM = testOnSM;
	}

	public String getGenoColumnOnGD() {
		return genoColumnOnGD;
	}

	public void setGenoColumnOnGD(String genoColumnOnGD) {
		this.genoColumnOnGD = genoColumnOnGD;
	}

	public String getDpCodeOnGD() {
		return dpCodeOnGD;
	}

	public void setDpCodeOnGD(String dpCodeOnGD) {
		this.dpCodeOnGD = dpCodeOnGD;
	}

	public String getRpCodeOnGD() {
		return rpCodeOnGD;
	}

	public void setRpCodeOnGD(String rpCodeOnGD) {
		this.rpCodeOnGD = rpCodeOnGD;
	}

	public String getHtCodeOnGD() {
		return htCodeOnGD;
	}

	public void setHtCodeOnGD(String htCodeOnGD) {
		this.htCodeOnGD = htCodeOnGD;
	}

	public String getNaCodeOnGD() {
		return naCodeOnGD;
	}

	public void setNaCodeOnGD(String naCodeOnGD) {
		this.naCodeOnGD = naCodeOnGD;
	}

	public Integer getBcnOnGD() {
		return bcnOnGD;
	}

	public void setBcnOnGD(Integer bcnOnGD) {
		this.bcnOnGD = bcnOnGD;
	}

	public Integer getFnOnGD() {
		return fnOnGD;
	}

	public void setFnOnGD(Integer fnOnGD) {
		this.fnOnGD = fnOnGD;
	}

	public String getDataTypeOnPD() {
		return dataTypeOnPD;
	}

	public void setDataTypeOnPD(String dataTypeOnPD) {
		this.dataTypeOnPD = dataTypeOnPD;
	}

	public String getNaCodeOnPD() {
		return naCodeOnPD;
	}

	public void setNaCodeOnPD(String naCodeOnPD) {
		this.naCodeOnPD = naCodeOnPD;
	}

	public String[] getRespsOnPD() {
		return respsOnPD;
	}

	public void setRespsOnPD(String[] respsOnPD) {
		this.respsOnPD = respsOnPD;
	}

	public String getExptlDesignOnPD() {
		return exptlDesignOnPD;
	}

	public void setExptlDesignOnPD(String exptlDesignOnPD) {
		this.exptlDesignOnPD = exptlDesignOnPD;
	}

	public String getGenoFactOnPD() {
		return genoFactOnPD;
	}

	public void setGenoFactOnPD(String genoFactOnPD) {
		this.genoFactOnPD = genoFactOnPD;
	}

	public String getEnvFactOnPD() {
		return envFactOnPD;
	}

	public void setEnvFactOnPD(String envFactOnPD) {
		this.envFactOnPD = envFactOnPD;
	}

	public String getBlockFactOnPD() {
		return blockFactOnPD;
	}

	public void setBlockFactOnPD(String blockFactOnPD) {
		this.blockFactOnPD = blockFactOnPD;
	}

	public String getRepFactOnPD() {
		return repFactOnPD;
	}

	public void setRepFactOnPD(String repFactOnPD) {
		this.repFactOnPD = repFactOnPD;
	}

	public String getRowFactOnPD() {
		return rowFactOnPD;
	}

	public void setRowFactOnPD(String rowFactOnPD) {
		this.rowFactOnPD = rowFactOnPD;
	}

	public String getColFactOnPD() {
		return colFactOnPD;
	}

	public void setColFactOnPD(String colFactOnPD) {
		this.colFactOnPD = colFactOnPD;
	}

	public String getMarColOnMD() {
		return marColOnMD;
	}

	public void setMarColOnMD(String marColOnMD) {
		this.marColOnMD = marColOnMD;
	}

	public String getChrColOnMD() {
		return chrColOnMD;
	}

	public void setChrColOnMD(String chrColOnMD) {
		this.chrColOnMD = chrColOnMD;
	}

	public String getPosColOnMD() {
		return posColOnMD;
	}

	public void setPosColOnMD(String posColOnMD) {
		this.posColOnMD = posColOnMD;
	}

	public String getUnitOnMD() {
		return unitOnMD;
	}

	public void setUnitOnMD(String unitOnMD) {
		this.unitOnMD = unitOnMD;
	}

	@Override
	public String toString() {
		return "ILAnalysisModel [analysisType=" + analysisType + "\n, genoFile="
				+ genoFile + "\n, phenoFile=" + phenoFile + "\n, mapFile="
				+ mapFile + "\n, isIncludeHT=" + isIncludeHT + "\n, regMethodOnMM="
				+ regMethodOnMM + "\n, pvalMethodOnMM=" + pvalMethodOnMM
				+ "\n, alphaOnMM=" + alphaOnMM + "\n, bootstrapOnMM="
				+ bootstrapOnMM + "\n, nfoldsOnMM=" + nfoldsOnMM + "\n, stepOnMM="
				+ stepOnMM + "\n, maxTryOnMM=" + maxTryOnMM + "\n, simPvalueOnCS="
				+ simPvalueOnCS + "\n, bootStrapTimesOnCS=" + bootStrapTimesOnCS
				+ "\n, refGenoFile=" + refGenoFile + "\n, digitsOnSM=" + digitsOnSM
				+ "\n, testOnSM=" + testOnSM + "\n, genoColumnOnGD="
				+ genoColumnOnGD + "\n, dpCodeOnGD=" + dpCodeOnGD
				+ "\n, rpCodeOnGD=" + rpCodeOnGD + "\n, htCodeOnGD=" + htCodeOnGD
				+ "\n, naCodeOnGD=" + naCodeOnGD + "\n, bcnOnGD=" + bcnOnGD
				+ "\n, fnOnGD=" + fnOnGD + "\n, dataTypeOnPD=" + dataTypeOnPD
				+ "\n, naCodeOnPD=" + naCodeOnPD + "\n, respsOnPD="
				+ Arrays.toString(respsOnPD) + ", exptlDesignOnPD="
				+ exptlDesignOnPD + "\n, genoFactOnPD=" + genoFactOnPD
				+ "\n, envFactOnPD=" + envFactOnPD + "\n, blockFactOnPD="
				+ blockFactOnPD + "\n, repFactOnPD=" + repFactOnPD
				+ "\n, rowFactOnPD=" + rowFactOnPD + "\n, colFactOnPD="
				+ colFactOnPD + "\n, marColOnMD=" + marColOnMD + "\n, chrColOnMD="
				+ chrColOnMD + "\n, posColOnMD=" + posColOnMD + "\n, unitOnMD="
				+ unitOnMD + "]";
	}
	
	
}
