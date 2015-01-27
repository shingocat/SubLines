package org.strasa.web.analysis.view.model;

public class AnalysisModel {
	private String dataFileName;
	private String resultFolderPath;
	private String outFileName;
	
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
	@Override
	public String toString() {
		return "AnalysisModel [dataFileName=" + dataFileName
				+ ", resultFolderPath=" + resultFolderPath + ", outFileName="
				+ outFileName + "]";
	}
}
