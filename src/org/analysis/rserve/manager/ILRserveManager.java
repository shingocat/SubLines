package org.analysis.rserve.manager;

import java.util.HashMap;

import org.strasa.web.analysis.view.model.AnalysisModel;
import org.strasa.web.analysis.view.model.ILAnalysisModel;


public class ILRserveManager extends JRServeMangerImpl {
	
	
	@Override
	public HashMap<String, String> doAnalysis(AnalysisModel model)
	{
		ILAnalysisModel ilModel = (ILAnalysisModel) model;
		
		if(ilModel.getAnalysisType() == "Chi-sq")
			return doChisq(ilModel);
		else if(ilModel.getAnalysisType() == "SingleMarker")
			return doSingleMarker(ilModel);
		else if(ilModel.getAnalysisType() == "MultiMarker")
			return doMultiMarker(ilModel);
		
		HashMap<String, String > toreturn = new HashMap<String, String>();
		toreturn.put("Success", "FALSE");
		toreturn.put("Message", "NO SUCH METHOD!");
		return toreturn;
	}
	
	private HashMap<String, String> doChisq(ILAnalysisModel model)
	{
		return null;
	}
	
	private HashMap<String, String> doSingleMarker(ILAnalysisModel model)
	{
		return null;
	}
	
	private HashMap<String, String> doMultiMarker(ILAnalysisModel model)
	{
		return null;
	}
}
