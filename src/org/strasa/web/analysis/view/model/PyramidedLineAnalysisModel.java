package org.strasa.web.analysis.view.model;

import java.util.Arrays;
import java.util.HashMap;

public class PyramidedLineAnalysisModel extends SSSLAnalysisModel{
	private Integer geneNumber;
	private boolean isDefaultGenoContrast;
	private HashMap<String, String> defaultGenoContrastFiles;

	public Integer getGeneNumber() {
		return geneNumber;
	}

	public void setGeneNumber(Integer geneNumber) {
		this.geneNumber = geneNumber;
	}

	public boolean isDefaultGenoContrast() {
		return isDefaultGenoContrast;
	}

	public void setDefaultGenoContrast(boolean isDefaultGenoContrast) {
		this.isDefaultGenoContrast = isDefaultGenoContrast;
	}

	public HashMap<String, String> getDefaultGenoContrastFile() {
		return defaultGenoContrastFiles;
	}

	public void setDefaultGenoContrastFile(HashMap<String, String> defaultGenoContrastFile) {
		this.defaultGenoContrastFiles = defaultGenoContrastFile;
	}

	@Override
	public String toString() {
		return "PyramidedLineAnalysisModel [geneNumber=" + geneNumber
				+ ", isDefaultContrast=" + isDefaultGenoContrast
				+ ", defaultContrastFile=" + defaultGenoContrastFiles
				+ ", getDesign()=" + getDesign() + ", getAnalysisEnvType()="
				+ getAnalysisEnvType() + ", getGenotypeFactor()="
				+ getGenotypeFactor() + ", getEnvFactor()=" + getEnvFactor()
				+ ", getBlockFactor()=" + getBlockFactor()
				+ ", getReplicateFactor()=" + getReplicateFactor()
				+ ", getRowFactor()=" + getRowFactor() + ", getColumnFactor()="
				+ getColumnFactor() + ", isDescriptiveStat()="
				+ isDescriptiveStat() + ", isVarComponent()="
				+ isVarComponent() + ", isCompareWithRecurrent()="
				+ isCompareWithRecurrent() + ", getRecurrentParent()="
				+ getRecurrentParent() + ", getGenotypeContrastFile()="
				+ getGenotypeContrastFile() + ", getEnvContrastFile()="
				+ getEnvContrastFile() + ", isFinlayWikinson()="
				+ isFinlayWikinson() + ", isShukla()=" + isShukla()
				+ ", isAMMI()=" + isAMMI() + ", isGGE()=" + isGGE()
				+ ", isBoxplotOnSingleEnv()=" + isBoxplotOnSingleEnv()
				+ ", isHistogramOnSingleEnv()=" + isHistogramOnSingleEnv()
				+ ", isDiagnosticPlotOnSingleEnv()="
				+ isDiagnosticPlotOnSingleEnv() + ", isBoxplotOnAcrossEnv()="
				+ isBoxplotOnAcrossEnv() + ", isHistogramOnAcrossEnv()="
				+ isHistogramOnAcrossEnv() + ", isDiagnosticPlotOnAcrossEnv()="
				+ isDiagnosticPlotOnAcrossEnv() + ", isResponsePlot()="
				+ isResponsePlot() + ", isAdaptationMap()=" + isAdaptationMap()
				+ ", isAMMI1Biplot()=" + isAMMI1Biplot()
				+ ", isAMMIBiplotPC1VsPC2()=" + isAMMIBiplotPC1VsPC2()
				+ ", isAMMIBiplotPC1VsPC3()=" + isAMMIBiplotPC1VsPC3()
				+ ", isAMMIBiplotPC2VsPC3()=" + isAMMIBiplotPC2VsPC3()
				+ ", isGGEBiplotSymmetricView()=" + isGGEBiplotSymmetricView()
				+ ", isGGEBiplotEnvironmentView()="
				+ isGGEBiplotEnvironmentView() + ", isGGEBiplotGenotypeView()="
				+ isGGEBiplotGenotypeView() + ", getResponseVars()="
				+ Arrays.toString(getResponseVars())
				+ ", getGenotypeFactorLevels()="
				+ Arrays.toString(getGenotypeFactorLevels())
				+ ", getEnvFactorLevels()="
				+ Arrays.toString(getEnvFactorLevels())
				+ ", getBlockFactorLevels()="
				+ Arrays.toString(getBlockFactorLevels())
				+ ", getReplicateFactorLevels()="
				+ Arrays.toString(getReplicateFactorLevels())
				+ ", getRowFactorLevels()="
				+ Arrays.toString(getRowFactorLevels())
				+ ", getColumnFactorLevels()="
				+ Arrays.toString(getColumnFactorLevels())
				+ ", getSignificantAlpha()=" + getSignificantAlpha()
				+ ", isSpecifiedGenoContrast()=" + isSpecifiedGenoContrast()
				+ ", isSpecifiedEnvContrast()=" + isSpecifiedEnvContrast()
				+ ", isAcrossEnv()=" + isAcrossEnv() + ", toString()="
				+ super.toString() + ", getDataFileName()=" + getDataFileName()
				+ ", getResultFolderPath()=" + getResultFolderPath()
				+ ", getOutFileName()=" + getOutFileName() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
	
}
