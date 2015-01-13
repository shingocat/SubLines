package org.analysis.rserve.manager;

import java.util.ArrayList;
import java.util.List;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.strasa.web.analysis.view.model.PyramidedLineAnalysisModel;
import org.strasa.web.analysis.view.model.SSSLAnalysisModel;
import org.strasa.web.utilities.InputTransform;

public class JRServeMangerImpl implements IJRserveManager{
	private RConnection conn;
	private InputTransform inputTransform;
	
	public JRServeMangerImpl()
	{
		inputTransform = new InputTransform();
		try {
			conn = new RConnection();
			conn.eval("library(PBTools)");
		} catch (RserveException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void doAnalysis(PyramidedLineAnalysisModel model) {
		// TODO Auto-generated method stub
		
	}
	
	public String[] getLevels(List<String> columnList, List<String[]> dataList,
			String environment) {
		int envtColumn = 0;
		for (int i = 0; i < columnList.size(); i++) {
			if (columnList.get(i).equals(environment)) {
				envtColumn = i;
			}
		}

		ArrayList<String> envts = new ArrayList<String>();
		for (int j = 0; j < dataList.size(); j++) {
			String level = dataList.get(j)[envtColumn];
			if (!envts.contains(level) && !level.isEmpty()) {
				envts.add(level);
			}
		}

		String[] envtLevels = new String[envts.size()];
		for (int k = 0; k < envts.size(); k++) {
			envtLevels[k] = (String) envts.get(k);
		}

		return envtLevels;
	}

	// The other way to get levels of factor,
	// but not implemented right now, if have time do this;
	public String[] getLevels(String dataFile, String factorName) {

		return null;
	}

	@Override
	public List<String> getVariableInfo(String fileName, int fileFormat,
			String separator) {
		String funcGetVarInfo;
		List<String> lstVarInfo = new ArrayList<String>();

		if (fileFormat == 2) {
			funcGetVarInfo = "varsAndTypes <- getVarInfo(fileName = \""
					+ fileName + "\", fileFormat = 2, separator = \""
					+ separator + "\")";
		} else {
			funcGetVarInfo = "varsAndTypes <- getVarInfo(fileName = \""
					+ fileName + "\", fileFormat = " + fileFormat
					+ ", separator = NULL)";
		}
		String[] vars;
		String[] types;

		try {
			System.out.println(System.getProperty("os.name"));
			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
				funcGetVarInfo = funcGetVarInfo.replace("\\", "//");
			}
			System.out.println("funcGetVarInfo is " + funcGetVarInfo);

			conn.eval(funcGetVarInfo);
			vars = conn.eval("as.vector(varsAndTypes$Variable)").asStrings();
			types = conn.eval("as.vector(varsAndTypes$Type)").asStrings();
			for (int i = 0; i < vars.length; i++) {
				lstVarInfo.add(vars[i] + ":" + types[i]);
			}
			for (String s : lstVarInfo) {
				System.out.println(s);
			}

		} catch (RserveException e) {
			e.printStackTrace();
		} catch (REXPMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return lstVarInfo;
	}


	public RConnection getConn() {
		return conn;
	}


	public void setConn(RConnection conn) {
		this.conn = conn;
	}


	public InputTransform getInputTransform() {
		return inputTransform;
	}


	public void setInputTransform(InputTransform inputTransform) {
		this.inputTransform = inputTransform;
	}
	
	
}
