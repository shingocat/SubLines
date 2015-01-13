package org.strasa.web.analysis.view.model;

import java.util.Arrays;
import java.util.List;

public class SSSLAnalysisModel {
	private String dataFileName;
	private String resultFolderPath;
	private String outFileName;

	// for model selection part
	private int design;
	private String analysisEnvType; // single- or multi- environment;
	private String[] responseVars;
	private String genotypeFactor;
	private String[] genotypeFactorLevels;
	private String envFactor;
	private String[] envFactorLevels;
	private String blockFactor;
	private String[] blockFactorLevels;
	private String replicateFactor;
	private String[] replicateFactorLevels;
	private String rowFactor;
	private String[] rowFactorLevels;
	private String columnFactor;
	private String[] columnFactorLevels;

	// for other options part
	private double significantAlpha = 0.05; // setting by default right now.
	private boolean isDescriptiveStat;
	private boolean isVarComponent;
	private boolean isCompareWithRecurrent;
	private String recurrentParent;
	private String genotypeContrastFile;
	private String envContrastFile;
	private boolean isFinlayWikinson;
	private boolean isShukla;
	private boolean isAMMI;
	private boolean isGGE;

	// for graph options
	private boolean isBoxplotOnSingleEnv;
	private boolean isHistogramOnSingleEnv;
	private boolean isDiagnosticPlotOnSingleEnv;
	private boolean isBoxplotOnAcrossEnv;
	private boolean isHistogramOnAcrossEnv;
	private boolean isDiagnosticPlotOnAcrossEnv;
	private boolean isResponsePlot;
	private boolean isAdaptationMap;
	private boolean isAMMI1Biplot;
	private boolean isAMMIBiplotPC1VsPC2;
	private boolean isAMMIBiplotPC1VsPC3;
	private boolean isAMMIBiplotPC2VsPC3;
	private boolean isGGEBiplotSymmetricView;
	private boolean isGGEBiplotEnvironmentView;
	private boolean isGGEBiplotGenotypeView;

	public String getDataFileName() {
		return dataFileName;
	}

	public void setDataFileName(String dataFileName) {
		this.dataFileName = dataFileName;
	}

	public String getResultFolderPath() {
		return resultFolderPath;
	}

	public void setResultFolderPath(String resultFolderPath) {
		this.resultFolderPath = resultFolderPath;
	}

	public String getOutFileName() {
		return outFileName;
	}

	public void setOutFileName(String outFileName) {
		this.outFileName = outFileName;
	}

	public int getDesign() {
		return design;
	}

	public void setDesign(int design) {
		this.design = design;
	}

	public String getAnalysisEnvType() {
		return analysisEnvType;
	}

	public void setAnalysisEnvType(String analysisEnvType) {
		this.analysisEnvType = analysisEnvType;
	}

	public String getGenotypeFactor() {
		return genotypeFactor;
	}

	public void setGenotypeFactor(String genotypeFactor) {
		this.genotypeFactor = genotypeFactor;
	}

	public String getEnvFactor() {
		return envFactor;
	}

	public void setEnvFactor(String envFactor) {
		this.envFactor = envFactor;
	}

	public String getBlockFactor() {
		return blockFactor;
	}

	public void setBlockFactor(String blockFactor) {
		this.blockFactor = blockFactor;
	}

	public String getReplicateFactor() {
		return replicateFactor;
	}

	public void setReplicateFactor(String replicateFactor) {
		this.replicateFactor = replicateFactor;
	}

	public String getRowFactor() {
		return rowFactor;
	}

	public void setRowFactor(String rowFactor) {
		this.rowFactor = rowFactor;
	}

	public String getColumnFactor() {
		return columnFactor;
	}

	public void setColumnFactor(String columnFactor) {
		this.columnFactor = columnFactor;
	}

	public boolean isDescriptiveStat() {
		return isDescriptiveStat;
	}

	public void setDescriptiveStat(boolean isDescriptiveStat) {
		this.isDescriptiveStat = isDescriptiveStat;
	}

	public boolean isVarComponent() {
		return isVarComponent;
	}

	public void setVarComponent(boolean isVarComponent) {
		this.isVarComponent = isVarComponent;
	}

	public boolean isCompareWithRecurrent() {
		return isCompareWithRecurrent;
	}

	public void setCompareWithRecurrent(boolean isCompareWithRecurrent) {
		this.isCompareWithRecurrent = isCompareWithRecurrent;
	}

	public String getRecurrentParent() {
		return recurrentParent;
	}

	public void setRecurrentParent(String recurrentParent) {
		this.recurrentParent = recurrentParent;
	}

	public String getGenotypeContrastFile() {
		return genotypeContrastFile;
	}

	public void setGenotypeContrastFile(String genotypeContrastFile) {
		this.genotypeContrastFile = genotypeContrastFile;
	}

	public String getEnvContrastFile() {
		return envContrastFile;
	}

	public void setEnvContrastFile(String envContrastFile) {
		this.envContrastFile = envContrastFile;
	}

	public boolean isFinlayWikinson() {
		return isFinlayWikinson;
	}

	public void setFinlayWikinson(boolean isFinlayWikinson) {
		this.isFinlayWikinson = isFinlayWikinson;
	}

	public boolean isShukla() {
		return isShukla;
	}

	public void setShukla(boolean isShukla) {
		this.isShukla = isShukla;
	}

	public boolean isAMMI() {
		return isAMMI;
	}

	public void setAMMI(boolean isAMMI) {
		this.isAMMI = isAMMI;
	}

	public boolean isGGE() {
		return isGGE;
	}

	public void setGGE(boolean isGGE) {
		this.isGGE = isGGE;
	}

	public boolean isBoxplotOnSingleEnv() {
		return isBoxplotOnSingleEnv;
	}

	public void setBoxplotOnSingleEnv(boolean isBoxplotOnSingleEnv) {
		this.isBoxplotOnSingleEnv = isBoxplotOnSingleEnv;
	}

	public boolean isHistogramOnSingleEnv() {
		return isHistogramOnSingleEnv;
	}

	public void setHistogramOnSingleEnv(boolean isHistogramOnSingleEnv) {
		this.isHistogramOnSingleEnv = isHistogramOnSingleEnv;
	}

	public boolean isDiagnosticPlotOnSingleEnv() {
		return isDiagnosticPlotOnSingleEnv;
	}

	public void setDiagnosticPlotOnSingleEnv(boolean isDiagnosticPlotOnSingleEnv) {
		this.isDiagnosticPlotOnSingleEnv = isDiagnosticPlotOnSingleEnv;
	}

	public boolean isBoxplotOnAcrossEnv() {
		return isBoxplotOnAcrossEnv;
	}

	public void setBoxplotOnAcrossEnv(boolean isBoxplotOnAcrossEnv) {
		this.isBoxplotOnAcrossEnv = isBoxplotOnAcrossEnv;
	}

	public boolean isHistogramOnAcrossEnv() {
		return isHistogramOnAcrossEnv;
	}

	public void setHistogramOnAcrossEnv(boolean isHistogramOnAcrossEnv) {
		this.isHistogramOnAcrossEnv = isHistogramOnAcrossEnv;
	}

	public boolean isDiagnosticPlotOnAcrossEnv() {
		return isDiagnosticPlotOnAcrossEnv;
	}

	public void setDiagnosticPlotOnAcrossEnv(boolean isDiagnosticPlotOnAcrossEnv) {
		this.isDiagnosticPlotOnAcrossEnv = isDiagnosticPlotOnAcrossEnv;
	}

	public boolean isResponsePlot() {
		return isResponsePlot;
	}

	public void setResponsePlot(boolean isResponsePlot) {
		this.isResponsePlot = isResponsePlot;
	}

	public boolean isAdaptationMap() {
		return isAdaptationMap;
	}

	public void setAdaptationMap(boolean isAdaptationMap) {
		this.isAdaptationMap = isAdaptationMap;
	}

	public boolean isAMMI1Biplot() {
		return isAMMI1Biplot;
	}

	public void setAMMI1Biplot(boolean isAMMI1Biplot) {
		this.isAMMI1Biplot = isAMMI1Biplot;
	}

	public boolean isAMMIBiplotPC1VsPC2() {
		return isAMMIBiplotPC1VsPC2;
	}

	public void setAMMIBiplotPC1VsPC2(boolean isAMMIBiplotPC1VsPC2) {
		this.isAMMIBiplotPC1VsPC2 = isAMMIBiplotPC1VsPC2;
	}

	public boolean isAMMIBiplotPC1VsPC3() {
		return isAMMIBiplotPC1VsPC3;
	}

	public void setAMMIBiplotPC1VsPC3(boolean isAMMIBiplotPC1VsPC3) {
		this.isAMMIBiplotPC1VsPC3 = isAMMIBiplotPC1VsPC3;
	}

	public boolean isAMMIBiplotPC2VsPC3() {
		return isAMMIBiplotPC2VsPC3;
	}

	public void setAMMIBiplotPC2VsPC3(boolean isAMMIBiplotPC2VsPC3) {
		this.isAMMIBiplotPC2VsPC3 = isAMMIBiplotPC2VsPC3;
	}

	public boolean isGGEBiplotSymmetricView() {
		return isGGEBiplotSymmetricView;
	}

	public void setGGEBiplotSymmetricView(boolean isGGEBiplotSymmetricView) {
		this.isGGEBiplotSymmetricView = isGGEBiplotSymmetricView;
	}

	public boolean isGGEBiplotEnvironmentView() {
		return isGGEBiplotEnvironmentView;
	}

	public void setGGEBiplotEnvironmentView(boolean isGGEBiplotEnvironmentView) {
		this.isGGEBiplotEnvironmentView = isGGEBiplotEnvironmentView;
	}

	public boolean isGGEBiplotGenotypeView() {
		return isGGEBiplotGenotypeView;
	}

	public void setGGEBiplotGenotypeView(boolean isGGEBiplotGenotypeView) {
		this.isGGEBiplotGenotypeView = isGGEBiplotGenotypeView;
	}

	public String[] getResponseVars() {
		return responseVars;
	}

	public void setResponseVars(String[] responseVars) {
		this.responseVars = responseVars;
	}

	public String[] getGenotypeFactorLevels() {
		return genotypeFactorLevels;
	}

	public void setGenotypeFactorLevels(String[] genotypeFactorLevels) {
		this.genotypeFactorLevels = genotypeFactorLevels;
	}

	public String[] getEnvFactorLevels() {
		return envFactorLevels;
	}

	public void setEnvFactorLevels(String[] envFactorLevels) {
		this.envFactorLevels = envFactorLevels;
	}

	public String[] getBlockFactorLevels() {
		return blockFactorLevels;
	}

	public void setBlockFactorLevels(String[] blockFactorLevels) {
		this.blockFactorLevels = blockFactorLevels;
	}

	public String[] getReplicateFactorLevels() {
		return replicateFactorLevels;
	}

	public void setReplicateFactorLevels(String[] replicateFactorLevels) {
		this.replicateFactorLevels = replicateFactorLevels;
	}

	public String[] getRowFactorLevels() {
		return rowFactorLevels;
	}

	public void setRowFactorLevels(String[] rowFactorLevels) {
		this.rowFactorLevels = rowFactorLevels;
	}

	public String[] getColumnFactorLevels() {
		return columnFactorLevels;
	}

	public void setColumnFactorLevels(String[] columnFactorLevels) {
		this.columnFactorLevels = columnFactorLevels;
	}

	public double getSignificantAlpha() {
		return significantAlpha;
	}

	public void setSignificantAlpha(double significantAlpha) {
		this.significantAlpha = significantAlpha;
	}

	@Override
	public String toString() {
		return "SSSLAnalysisModel [\ndataFileName=" + dataFileName
				+ ", \nresultFolderPath=" + resultFolderPath + ", \noutFileName="
				+ outFileName + ", \ndesign=" + design + ", \nanalysisEnvType="
				+ analysisEnvType + ", \nresponseVars="
				+ Arrays.toString(responseVars) + ", \ngenotypeFactor="
				+ genotypeFactor + ", \ngenotypeFactorLevels="
				+ Arrays.toString(genotypeFactorLevels) + ", \nenvFactor="
				+ envFactor + ", \nenvFactorLevels="
				+ Arrays.toString(envFactorLevels) + ", \nblockFactor="
				+ blockFactor + ", \nblockFactorLevels="
				+ Arrays.toString(blockFactorLevels) + ", \nreplicateFactor="
				+ replicateFactor + ", \nreplicateFactorLevels="
				+ Arrays.toString(replicateFactorLevels) + ", \nrowFactor="
				+ rowFactor + ", \nrowFactorLevels="
				+ Arrays.toString(rowFactorLevels) + ", \ncolumnFactor="
				+ columnFactor + ", \ncolumnFactorLevels="
				+ Arrays.toString(columnFactorLevels) + ", \nisDescriptiveStat="
				+ isDescriptiveStat + ", \nisVarComponent=" + isVarComponent
				+ ", \nisCompareWithRecurrent=" + isCompareWithRecurrent
				+ ", \nrecurrentParent=" + recurrentParent
				+ ", \ngenotypeContrastFile=" + genotypeContrastFile
				+ ", \nenvContrastFile=" + envContrastFile
				+ ", \nisFinlayWikinson=" + isFinlayWikinson + ", \nisShukla="
				+ isShukla + ", \nisAMMI=" + isAMMI + ", \nisGGE=" + isGGE
				+ ", \nisBoxplotOnSingleEnv=" + isBoxplotOnSingleEnv
				+ ", \nisHistogramOnSingleEnv=" + isHistogramOnSingleEnv
				+ ", \nisDiagnosticPlotOnSingleEnv="
				+ isDiagnosticPlotOnSingleEnv + ", \nisBoxplotOnAcrossEnv="
				+ isBoxplotOnAcrossEnv + ", \nisHistogramOnAcrossEnv="
				+ isHistogramOnAcrossEnv + ", \nisDiagnosticPlotOnAcrossEnv="
				+ isDiagnosticPlotOnAcrossEnv + ", \nisResponsePlot="
				+ isResponsePlot + ", \nisAdaptationMap=" + isAdaptationMap
				+ ", \nisAMMI1Biplot=" + isAMMI1Biplot
				+ ", \nisAMMIBiplotPC1VsPC2=" + isAMMIBiplotPC1VsPC2
				+ ", \nisAMMIBiplotPC1VsPC3=" + isAMMIBiplotPC1VsPC3
				+ ", \nisAMMIBiplotPC2VsPC3=" + isAMMIBiplotPC2VsPC3
				+ ", \nisGGEBiplotSymmetricView=" + isGGEBiplotSymmetricView
				+ ", \nisGGEBiplotEnvironmentView=" + isGGEBiplotEnvironmentView
				+ ", \nisGGEBiplotGenotypeView=" + isGGEBiplotGenotypeView + "]";
	}

}
