package org.analysis.rserve.manager;

import java.util.List;

import org.strasa.web.analysis.view.model.PyramidedLineAnalysisModel;

public interface IJRserveManager {
	public void doAnalysis(PyramidedLineAnalysisModel model);
	public List<String> getVariableInfo(String fileName, int fileFormat,
			String separator);
}
