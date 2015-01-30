package org.analysis.rserve.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.strasa.web.analysis.view.model.AnalysisModel;
import org.strasa.web.analysis.view.model.PyramidedLineAnalysisModel;
import org.strasa.web.analysis.view.model.SSSLAnalysisModel;
import org.strasa.web.utilities.InputTransform;

public class JRServeMangerImpl implements IJRserveManager{
	private RConnection conn;
	private InputTransform inputTransform;
	String logFile = "Log.txt";
	String contrastFile = "ContrastAnalysis.txt";
	String stabilityFile = "StabilityAnalysis.txt";
	String multiplicativeFile = "MultiplicativeAnalysis.txt";
	
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
	
	public HashMap<String, List<String>> getLevelsOnOtherLevels(List<String> columnList,
			List<String[]> dataList, String geno, String env) {
		HashMap<String, List<String>> toreturn = new HashMap<String, List<String>>();
		int genoColumn = 0;
		int envColumn = 0;
		for(int i = 0; i < columnList.size(); i++)
		{
			if(columnList.get(i).equals(geno))
			{	
				genoColumn = i;
				continue;
			} else if(columnList.get(i).equals(env))
			{
				envColumn = i;
				continue;
			}
		}
		
		// return a hashmap of env levels as key, geno levels as values;
		for(int i = 0; i < dataList.size(); i++)
		{
			String envLevel = dataList.get(i)[envColumn];
			if(toreturn.containsKey(envLevel))
			{
				ArrayList<String> lstGenoLevel = (ArrayList<String>) toreturn.get(envLevel);
				if(!lstGenoLevel.contains(dataList.get(i)[genoColumn]))
				{
					lstGenoLevel.add(dataList.get(i)[genoColumn]);
					toreturn.put(envLevel, lstGenoLevel);
				}
			} else
			{
				ArrayList<String> lstGenoLevel = new ArrayList<String>();
				lstGenoLevel.add(dataList.get(i)[genoColumn]);
				toreturn.put(envLevel, lstGenoLevel);
			}
		}
		
		return toreturn;
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


	@Override
	public HashMap<String, String> doAnalysis(AnalysisModel model) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	public String getDefaultContrast(Integer geneNumber, String outFilePath)
	{
		String fileName = null;
		if(geneNumber == 2)
			fileName = outFilePath + File.separator + "Default_Bi_Genes_Contrast.csv";
		else if(geneNumber == 3)
			fileName = outFilePath + File.separator + "Default_Tri_Genes_Contrast.csv";
		else if(geneNumber == 4)
			fileName = outFilePath + File.separator + "Default_Quadra_Genes_Contrast.csv";
		StringBuilder sb = new StringBuilder();
		sb.append("default.contrast <- getDefGeneOrthogonalContrastMatrix("
				+ geneNumber + ");");
		sb.append("write.csv(default.contrast$orthogonal.contrast.matrix,file=\"" +
				fileName + "\", row.names=TRUE);");
		try {
			conn.eval(sb.toString());
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally
		{
			conn.close();
		}
		return fileName;
	}
}
