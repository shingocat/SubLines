package org.strasa.web.analysis.view.model;

public class PyramidedLineAnalysisModel {
	private int design;
	private String analysisType;
	private String outFileName;
	private String resultFolderPath;
	private String dataFileName;
	private String genotypeContrastFile;

	public int getDesign() {
		return design;
	}

	public void setDesign(int design) {
		this.design = design;
	} 
	
	public String getAnalysisType()
	{
		return analysisType;
	}
	
	public void setAnalysisType(String analysisType)
	{
		this.analysisType = analysisType;
	}

	public String getOutFileName() {
		// TODO Auto-generated method stub
		return outFileName;
	}
	
	public void setOutFileName(String outFileName)
	{
		this.outFileName = outFileName;
	}

	public String getResultFolderPath() {
		// TODO Auto-generated method stub
		return resultFolderPath;
	}
	
	public void setResultFolderPath(String resultFolderPath){
		this.resultFolderPath = resultFolderPath;
	}

	public void setDataFileName(String dataFileName) {
		// TODO Auto-generated method stub
		this.dataFileName = dataFileName;
	}
	
	public String getDataFileName()
	{
		return this.dataFileName;
	}

	public String getGenotypeContrastFile() {
		return genotypeContrastFile;
	}

	public void setGenotypeContrastFile(String genotypeContrastFile) {
		this.genotypeContrastFile = genotypeContrastFile;
	}
	
	
}
