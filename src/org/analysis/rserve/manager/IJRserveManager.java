package org.analysis.rserve.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.strasa.web.analysis.view.model.AnalysisModel;
import org.strasa.web.analysis.view.model.PyramidedLineAnalysisModel;

public interface IJRserveManager {
	public HashMap<String, String> doAnalysis(AnalysisModel model);
	public List<String> getVariableInfo(String fileName, int fileFormat,
			String separator);
}
