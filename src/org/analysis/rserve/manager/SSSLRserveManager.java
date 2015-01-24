package org.analysis.rserve.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.strasa.middleware.util.StringConstants;
import org.strasa.web.analysis.view.model.SSSLAnalysisModel;
import org.strasa.web.analysis.view.model.SingleSiteAnalysisModel;
import org.strasa.web.utilities.InputTransform;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

public class SSSLRserveManager extends JRServeMangerImpl {
	
	String logFile = "Log.txt";
	String contrastFile = "Contrast.txt";

	public SSSLRserveManager() {
		super();
	}

	public HashMap<String, String> doAnalysis(SSSLAnalysisModel model) {
		System.out.println("SSSL MODEL INFO : " + model.toString());
		if (model.getAnalysisEnvType().equals("Multi-Environment"))
			return this.doMEA(model);
		else
			return this.doSEA(model);
	}

	// single environment analysis for my redesign PBTools, JAN 19, 2015
	public HashMap<String, String> doSEA(SSSLAnalysisModel model)
	{
		HashMap<String, String> toreturn = new HashMap<String, String>();
		String resultFolderPath = model.getResultFolderPath().replace(
				StringConstants.BSLASH, StringConstants.FSLASH);
		String outFileName = model.getOutFileName().replace(
				StringConstants.BSLASH, StringConstants.FSLASH);
		String dataFileName = model.getDataFileName().replace(
				StringConstants.BSLASH, StringConstants.FSLASH);
		int designIndex = model.getDesign();
		String[] respvars = model.getResponseVars();
		String environment = model.getEnvFactor() == null ? "NULL" : model.getEnvFactor();
		String[] environmentLevels = model.getEnvFactorLevels();
		String genotype = model.getGenotypeFactor();
		String[] genotypeLevels = model.getGenotypeFactorLevels();
		String block = model.getBlockFactor();
		String rep = model.getReplicateFactor();
		String row = model.getRowFactor();
		String column = model.getColumnFactor();
		boolean isDescriptiveStat = model.isDescriptiveStat();
		boolean isVarComponent = model.isVarComponent();
		boolean isBoxplotOnSingleEnv = model.isBoxplotOnSingleEnv();
		boolean isHistogramOnSingleEnv = model.isHistogramOnSingleEnv();
		boolean isDiagnosticPlotOnSingleEnv = model.isDiagnosticPlotOnSingleEnv();
		// comparing with recurrent 
		String alpha = String.valueOf(model.getSignificantAlpha()); // default is 0.05
		String recurrent = model.getRecurrentParent();
		boolean isCompareWithRecurrent = model.isCompareWithRecurrent();
		// specifying contrast
		boolean isSpecifiedGenoContrast = model.isSpecifiedGenoContrast();
		HashMap<String, String> genoContrastFiles = model.getGenotypeContrastFile();
		String respvarVector = getInputTransform().createRVector(respvars);

		String designUsed = new String();
		String design = new String();
		String designString = null;

		switch (designIndex) {
		case 0: {
			designUsed = "Randomized Complete Block (RCB)";
			design = "RCB";
			designString = "exptl.design = \"RCB\", block = \"" + block + "\", ";
			break;
		}
		case 1: {
			designUsed = "Augmented RCB";
			design = "AugRCB";
			designString = "exptl.design = \"AugRCB\", block = \"" + block + "\", ";
			break;
		}
		case 2: {
			designUsed = "Augmented Latin Square";
			design = "AugLS";
			designString = "exptl.design = \"AugLS\", row = \"" + row + "\", column = \"" + column + "\", ";
			break;
		}
		case 3: {
			designUsed = "Alpha-Lattice";
			design = "Alpha";
			designString = "exptl.design = \"Alpha\", block = \"" + block + "\", rep = \"" + rep + "\", ";
			break;
		}
		case 4: {
			designUsed = "Row-Column";
			design = "RowCol";
			designString = "exptl.design = \"RowCol\", rep = \"" + rep + "\", row = \"" + row + "\", column = \"" + column + "\", ";
			break;
		}
		case 5: {
			designUsed = "Latinized Alpha-Lattice";
			design = "LatinAlpha";
			designString = "exptl.design = \"LatinAlpha\", block = \"" + block + "\", rep = \"" + rep + "\", ";
			break;
		}
		case 6: {
			designUsed = "Latinized Row-Column";
			design = "LatinRowCol";
			designString = "exptl.design = \"LatinRowCol\", block = \"" + block + "\", rep = \"" + rep + "\", ";
			break;
		}
		default: {
			designUsed = "Randomized Complete Block (RCB)";
			design = "RCB";
			designString = "exptl.design = \"RCB\", block = \"" + block + "\", ";
			break;
		}
		}

		try{
			String setWd = "setwd(\"" + resultFolderPath + "\")";
			getConn().eval(setWd);
			
			String data = null;
			data = "data <- try(read.csv(file=\"" + dataFileName + "\", header = TRUE, " +
					"na.strings = c(\"NA\",\".\",\" \",\"\"), blank.lines.skip = TRUE),silent = TRUE);";
			getConn().eval(data);
			String run = getConn().eval("class(data)").asString();
			//checking whether read data to be PhenotypicData object is successful.
			if (run != null && run.equals("try-error")) {
				StringBuilder error = new StringBuilder();
				error.append("msg <- trimStrings(strsplit(data,\":\")[[1]]);");
				error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg]], collapse=\" \"));");
				error.append("msg <- gsub(\"\\\"\", \"\", msg);");
				error.append("capture.output(cat(\"***\nError in read.csv function:\n\"\n,msg, \"\n***\n\n\", sep=\"\"),"
						+ "file=\"" + logFile + "\",append = TRUE);");
				getConn().eval(error.toString());
				toreturn.put("Success", "FALSE");
				toreturn.put("Message", "Error Reading CSV File Data!");
				return toreturn;
			} else {
				String pheno = null;
				if(environment.equalsIgnoreCase("NULL"))
				{
					pheno = "pheno <- try(read.pheno.data(data," 
							+ "type=\"RAW\", "
							+ "pop.type=\"SSSL\", "
							+ "resp.var = c(" + respvarVector + "), "
							+ "geno = \"" + genotype + "\", "
							+ designString
							+ "na.code = NA),silent = TRUE)";
				} else
				{
					pheno = "pheno <- try(read.pheno.data(data," 
							+ "type=\"RAW\", "
							+ "pop.type=\"SSSL\", "
							+ "resp.var = " + respvarVector + ", "
							+ "geno = \"" + genotype + "\", "
							+ designString
							+ "env = \"" + environment + "\", "
							+ "na.code = NA),silent = TRUE)";
				}
				getConn().eval(pheno);
				run = getConn().eval("class(pheno)").asString();
				//checking whether read data to be PhenotypicData object is successful.
				if (run != null && run.equals("try-error")) {
					StringBuilder error = new StringBuilder();
					error.append("msg <- trimStrings(strsplit(pheno,\":\")[[1]]);");
					error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]],collapse=\" \"));");
					error.append("msg <- gsub(\"\\\"\",\"\",msg);");
					error.append("capture.output(cat(\"****\nERROR in read.pheno.data() function:\n\",msg,"
							+ "\"\n***\n\n\", sep=\"\"), file=\"" + logFile + "\",append=TRUE);");
					getConn().eval(error.toString());
					toreturn.put("Success", "FALSE");
					toreturn.put("Message", "Error Reading Phenotypic Data!");
					return toreturn;
				}

				String usedData = "capture.output(cat(\"\nDATA FILE: "
						+ new File(dataFileName).getName() + "\"), file=\"" + outFileName + "\");";
				String outFile = "capture.output(cat(\"\nSINGLE-ENVIRONMENT ANALYSIS On SSSL\n\"), file=\""
						+ outFileName + "\", append = TRUE);";
				String usedDesign = "capture.output(cat(\"DESIGN: " + designUsed + "\n\"), file=\""
						+ outFileName + "\", append = TRUE);";
				String sep = "capture.output(cat(\"---------------------------------------------\"),file=\""
						+ outFileName + "\", append = TRUE);";
				String sep2 = "capture.output(cat(\"============================================\"),file=\""
						+ outFileName + "\", append = TRUE);";
				String outspace = "capture.output(cat(\"\n\"), file=\"" + outFileName + "\", append = TRUE);";

				getConn().eval(usedData);
				getConn().eval(outFile);
				getConn().eval(usedDesign);
				getConn().eval(outspace);

				//print the descriptive statistic when the descriptiveStat is true;
				String descriptiveStatString = "capture.output(DescriptiveStatistics(data," + respvarVector + 
						",,c(\"max\",\"min\",\"mean\",\"sd\")), file=\"" + outFileName + "\", append = TRUE);";
				getConn().eval(descriptiveStatString);
				getConn().eval(outspace);
				getConn().eval(outspace);

				String dataRestricted = "dataRestricted <- try(restrict.pheno.data(pheno),silent=TRUE);";
				getConn().eval(dataRestricted);
				run = getConn().eval("class(dataRestricted)").asString();
				if(run != null && run.equalsIgnoreCase("try-error"))
				{
					StringBuilder error = new StringBuilder();
					error.append("msg <- trimStrings(strsplit(dataRestricted,\":\")[[1]]);");
					error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]],collapse=\" \"));");
					error.append("msg <- gsub(\"\\\"\",\"\",msg);");
					error.append("capture.output(cat(\"****\nERROR in restrict.pheno.data() function:\n\",msg,"
							+ "\"\n***\n\n\", sep=\"\"), file=\"" + logFile + "\",append=TRUE);");
					getConn().eval(error.toString());
					toreturn.put("Success", "FALSE");
					toreturn.put("Message", "Restricted Phenotypic Data Error!");
					return toreturn;
				}

				String doSEA = "outcomes <- try(doSEA(dataRestricted),silent=TRUE);";
				getConn().eval(doSEA);
				run = getConn().eval("class(outcomes)").asString();
				if(run != null && run.equalsIgnoreCase("try-error"))
				{
					StringBuilder error = new StringBuilder();
					error.append("msg <- trimStrings(strsplit(outcomes,\":\")[[1]]);");
					error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]],collapse=\" \"));");
					error.append("msg <- gsub(\"\\\"\",\"\",msg);");
					error.append("capture.output(cat(\"****\nERROR in do.SEA() function:\n\",msg,"
							+ "\"\n***\n\n\", sep=\"\"), file=\"" + logFile + "\",append=TRUE);");
					getConn().eval(error.toString());
					toreturn.put("Success", "FALSE");
					toreturn.put("Message", "Do Single Environment Analysis Error!");
					return toreturn;
				}
				//this if for contrast analysis
				if(isCompareWithRecurrent)
				{
					String doContrast = "outcomes.contrast <- try(doContrast(outcomes, contrastOpt = \"RecurrentParent\","
							+ "recurrentParent = \"" + recurrent + "\",alpha = as.numeric(\"" + alpha + "\")),silent=TRUE);";
					getConn().eval(doContrast);
					run = getConn().eval("class(outcomes.contrast)").asString();
					if(run != null && run.equalsIgnoreCase("try-error"))
					{
						getConn().eval("capture.output(print(outcomes,2),file=\"" + outFileName + "\", append=TRUE);");
						StringBuilder error = new StringBuilder();
						error.append("msg <- trimStrings(strsplit(outcomes.contrast,\":\")[[1]]);");
						error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]],collapse=\" \"));");
						error.append("msg <- gsub(\"\\\"\",\"\",msg);");
						error.append("capture.output(cat(\"****\nERROR in do.SEA() function:\n\",msg,"
								+ "\"\n***\n\n\", sep=\"\"), file=\"" + logFile + "\",append=TRUE);");
						getConn().eval(error.toString());
						toreturn.put("Success", "TRUE");
						toreturn.put("Message", "Do contrast Failure!");
					} else
					{
						getConn().eval("capture.output(print.ContrastOutcomes(outcomes.contrast),file=\"" + contrastFile + "\", append=TRUE);");
					}
				}
				if(isSpecifiedGenoContrast)
				{
					
				} 
					
				getConn().eval("capture.output(print(outcomes,2),file=\"" + outFileName + "\", append=TRUE);");

				// the is for plot funcitons
				if(isBoxplotOnSingleEnv)
				{
					String boxplot = "boxplot <- try(graph.boxplot(outcomes, single.env = FALSE), silent=TRUE);";
					getConn().eval(boxplot);
					run = getConn().eval("class(boxplot)").asString();
					if(run != null && run.equalsIgnoreCase("try-error"))
					{
						StringBuilder error = new StringBuilder();
						error.append("msg <- trimStrings(strsplit(boxplot,\":\")[[1]]);");
						error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]],collapse=\" \"));");
						error.append("msg <- gsub(\"\\\"\",\"\",msg);");
						error.append("capture.output(cat(\"****\nERROR in graph.boxplot function:\n\",msg,"
								+ "\"\n***\n\n\", sep=\"\"), file=\"" + logFile + "\",append=TRUE);");
						getConn().eval(error.toString());
						toreturn.put("Success", "TRUE");
						toreturn.put("Message", toreturn.get("Message") + " Fail to graph boxplot!");
					}
				} 
				if(isHistogramOnSingleEnv)
				{
					String histplot = "hist <- try(graph.hist(outcomes, single.env = TRUE), silent=TRUE);";
					getConn().eval(histplot);
					run = getConn().eval("class(hist)").asString();
					if(run != null && run.equalsIgnoreCase("try-error"))
					{
						StringBuilder error = new StringBuilder();
						error.append("msg <- trimStrings(strsplit(hist,\":\")[[1]]);");
						error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]],collapse=\" \"));");
						error.append("msg <- gsub(\"\\\"\",\"\",msg);");
						error.append("capture.output(cat(\"****\nERROR in graph.hist function:\n\",msg,"
								+ "\"\n***\n\n\", sep=\"\"), file=\"" + logFile + "\",append=TRUE);");
						getConn().eval(error.toString());
						toreturn.put("Success", "TRUE");
						toreturn.put("Message", toreturn.get("Message") + " Fail to graph histogram!");
					}
				}

				//diagnosticPlot for genotype fixed
				if(isDiagnosticPlotOnSingleEnv)
				{
					String diagnostic = "diag <- try(graph.diag(outcomes), silent=TRUE);";
					getConn().eval(diagnostic);
					run = getConn().eval("class(diag)").asString();
					if(run != null && run.equalsIgnoreCase("try-error"))
					{
						StringBuilder error = new StringBuilder();
						error.append("msg <- trimStrings(strsplit(diag,\":\")[[1]]);");
						error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]],collapse=\" \"));");
						error.append("msg <- gsub(\"\\\"\",\"\",msg);");
						error.append("capture.output(cat(\"****\nERROR in graph.diag function:\n\",msg,"
								+ "\"\n***\n\n\", sep=\"\"), file=\"" + logFile + "\",append=TRUE);");
						getConn().eval(error.toString());
						toreturn.put("Success", "TRUE");
						toreturn.put("Message", toreturn.get("Message") + " Fail to graph diagnostic!");
					}
				}
			} // end of checking whether could read the csv file into R.
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			toreturn.put("Success", "FALSE");
			toreturn.put("Message", "Rserve Exception!");
			return toreturn;
		} catch (REXPMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			toreturn.put("Success", "FALSE");
			toreturn.put("Message", "Rserve Evaluation Exception!");
			return toreturn;
		} finally{
			getConn().close();
		}
		toreturn.put("Success", "TURE");
		toreturn.put("Message", "Successful Do Single Environment Analysis!");
		return toreturn;

	}

	// using my design doMEA R funciton
	public HashMap<String, String> doMEA(SSSLAnalysisModel model)
	{
		HashMap<String, String> toreturn = new HashMap<String, String>();
		String resultFolderPath = model.getResultFolderPath().replace(
				StringConstants.BSLASH, StringConstants.FSLASH);
		String outFileName = model.getOutFileName().replace(
				StringConstants.BSLASH, StringConstants.FSLASH);
		String dataFileName = model.getDataFileName().replace(
				StringConstants.BSLASH, StringConstants.FSLASH);
		int designIndex = model.getDesign();
		String[] respvars = model.getResponseVars();
		String environment = model.getEnvFactor() == null ? "NULL" : model.getEnvFactor();
		String[] environmentLevels = model.getEnvFactorLevels();
		String genotype = model.getGenotypeFactor();
		String[] genotypeLevels = model.getGenotypeFactorLevels();
		String block = model.getBlockFactor();
		String rep = model.getReplicateFactor();
		String row = model.getRowFactor();
		String column = model.getColumnFactor();
		boolean isDescriptStat = model.isDescriptiveStat();
		boolean isVarComponent = model.isVarComponent();
		boolean isBoxplotOnSingleEnv = model.isBoxplotOnSingleEnv();
		boolean isBoxplotOnAcrossEnv = model.isBoxplotOnAcrossEnv();
		boolean isHistogramOnSingleEnv = model.isHistogramOnSingleEnv();
		boolean isHistogramOnAcrossEnv = model.isHistogramOnAcrossEnv();
		boolean isDiagnosticPlotOnSingleEnv = model.isDiagnosticPlotOnSingleEnv();
		boolean isDiagnosticPlotOnAcrossEnv = model.isDiagnosticPlotOnAcrossEnv();
		String alpha = String.valueOf(model.getSignificantAlpha());
		String[] recurrent = new String[] { model.getRecurrentParent() };
		boolean isCcompareWithRecurrent = model.isCompareWithRecurrent();
		boolean isSpecifiedGenoContrast = model.isSpecifiedGenoContrast();;
		boolean isSpecifiedEnvContrast = model.isSpecifiedEnvContrast();
		HashMap<String, String> genoContrastFiles = model.getGenotypeContrastFile();
		HashMap<String, String> envContrastFiles = model.getEnvContrastFile();
		String respvarVector = getInputTransform().createRVector(respvars);
		String controlLevelsVector = getInputTransform().createRVector(recurrent);

		String designUsed = new String();
		String design = new String();
		String designString = null;

		switch (designIndex) {
		case 0: {
			designUsed = "Randomized Complete Block (RCB)";
			design = "RCB";
			designString = "exptl.design = \"RCB\", block = \"" + block + "\", ";
			break;
		}
		case 1: {
			designUsed = "Augmented RCB";
			design = "AugRCB";
			designString = "exptl.design = \"AugRCB\", block = \"" + block + "\", ";
			break;
		}
		case 2: {
			designUsed = "Augmented Latin Square";
			design = "AugLS";
			designString = "exptl.design = \"AugLS\", row = \"" + row + "\", column = \"" + column + "\", ";
			break;
		}
		case 3: {
			designUsed = "Alpha-Lattice";
			design = "Alpha";
			designString = "exptl.design = \"Alpha\", block = \"" + block + "\", rep = \"" + rep + "\", ";
			break;
		}
		case 4: {
			designUsed = "Row-Column";
			design = "RowCol";
			designString = "exptl.design = \"RowCol\", rep = \"" + rep + "\", row = \"" + row + "\", column = \"" 
			+ column + "\", ";
			break;
		}
		case 5: {
			designUsed = "Latinized Alpha-Lattice";
			design = "LatinAlpha";
			designString = "exptl.design = \"LatinAlpha\", block = \"" + block + "\", rep = \"" + rep + "\", ";
			break;
		}
		case 6: {
			designUsed = "Latinized Row-Column";
			design = "LatinRowCol";
			designString = "exptl.design = \"LatinRowCol\", block = \"" + block + "\", rep = \"" + rep + "\", ";
			break;
		}
		default: {
			designUsed = "Randomized Complete Block (RCB)";
			design = "RCB";
			designString = "exptl.design = \"RCB\", block = \"" + block + "\", ";
			break;
		}
		}

		try{
			String data = null;
			data = "data <- try(read.csv(file=\"" + dataFileName + "\", header = TRUE, " +
					"na.strings = c(\"NA\",\".\",\" \",\"\"), blank.lines.skip = TRUE),silent = TRUE);";
			System.out.println(data);
			getConn().eval(data);
			String run = getConn().eval("class(data)").asString();
			//checking whether read data to be PhenotypicData object is successful.
			if (run != null && run.equals("try-error")) {
				StringBuilder error = new StringBuilder();
				error.append("msg <- trimStrings(strsplit(data,\":\")[[1]]);");
				error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]],collapse=\" \"));");
				error.append("msg <- gsub(\"\\\"\",\"\",msg);");
				error.append("capture.output(cat(\"****\nERROR in read.csv function:\n\",msg,"
						+ "\"\n***\n\n\", sep=\"\"), file=\"" + logFile + "\",append=TRUE);");
				getConn().eval(error.toString());
				toreturn.put("Success", "FALSE");
				toreturn.put("Message", "Error Reading CSV File Data!");
				return toreturn;
			} else {
				String pheno = null;
				pheno = "pheno <- try(read.pheno.data(data," 
						+ "type=\"RAW\", "
						+ "pop.type=\"SSSL\", "
						+ "resp.var = " + respvarVector + ", "
						+ "geno = \"" + genotype + "\", "
						+ designString
						+ "env = \"" + environment + "\", "
						+ "na.code = NA),silent = TRUE)";

				getConn().eval(pheno);
				run = getConn().eval("class(pheno)").asString();
				//checking whether read data to be PhenotypicData object is successful.
				if (run != null && run.equals("try-error")) {
					StringBuilder error = new StringBuilder();
					error.append("msg <- trimStrings(strsplit(pheno,\":\")[[1]]);");
					error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]],collapse=\" \"));");
					error.append("msg <- gsub(\"\\\"\",\"\",msg);");
					error.append("capture.output(cat(\"****\nERROR in read.pheno.data() function:\n\",msg,"
							+ "\"\n***\n\n\", sep=\"\"), file=\"" + logFile + "\",append=TRUE);");
					getConn().eval(error.toString());
					toreturn.put("Success", "FALSE");
					toreturn.put("Message", "Error Reading Phenootypic Data!");
					return toreturn;
				} else {
					String setWd = "setwd(\"" + resultFolderPath + "\")";
					getConn().eval(setWd);
					String usedData = "capture.output(cat(\"\nDATA FILE: "
							+ new File(dataFileName).getName() + "\"), file=\"" + outFileName + "\");";
					String outFile = "capture.output(cat(\"\nMulti-ENVIRONMENT ANALYSIS On SSSL\n\"), file=\""
							+ outFileName + "\", append = TRUE);";
					String usedDesign = "capture.output(cat(\"DESIGN: " + designUsed + "\n\"), file=\""
							+ outFileName + "\", append = TRUE);";
					String sep = "capture.output(cat(\"---------------------------------------------\"),file=\""
							+ outFileName + "\", append = TRUE);";
					String sep2 = "capture.output(cat(\"============================================\"),file=\""
							+ outFileName + "\", append = TRUE);";
					String outspace = "capture.output(cat(\"\n\"), file=\"" + outFileName + "\", append = TRUE);";

					getConn().eval(usedData);
					getConn().eval(outFile);
					getConn().eval(usedDesign);

					String dataRestricted = "dataRestricted <- try(restrict.pheno.data(pheno), silent=TRUE);";
					getConn().eval(dataRestricted);
					run = getConn().eval("class(dataRestricted)").asString();
					if(run != null && run.equalsIgnoreCase("try-error"))
					{
						StringBuilder error = new StringBuilder();
						error.append("msg <- trimStrings(strsplit(dataRestricted,\":\")[[1]]);");
						error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]],collapse=\" \"));");
						error.append("msg <- gsub(\"\\\"\",\"\",msg);");
						error.append("capture.output(cat(\"****\nERROR in reastrict.pheno.data() function:\n\",msg,"
								+ "\"\n***\n\n\", sep=\"\"), file=\"" + logFile + "\",append=TRUE);");
						getConn().eval(error.toString());
						toreturn.put("Success", "FALSE");
						toreturn.put("Message", "Restricted Phenotypic Data Error!");
						return toreturn;
					}

					String doMEA = "outcomes <- try(doMEA(dataRestricted, is.EnvFixed = TRUE), silent=TRUE);";
					getConn().eval(doMEA);
					run = getConn().eval("class(outcomes)").asString();
					if(run != null && run.equalsIgnoreCase("try-error"))
					{
						StringBuilder error = new StringBuilder();
						error.append("msg <- trimStrings(strsplit(outcomes,\":\")[[1]]);");
						error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]],collapse=\" \"));");
						error.append("msg <- gsub(\"\\\"\",\"\",msg);");
						error.append("capture.output(cat(\"****\nERROR in doMEA function:\n\",msg,"
								+ "\"\n***\n\n\", sep=\"\"), file=\"" + logFile + "\",append=TRUE);");
						getConn().eval(error.toString());
						toreturn.put("Success", "FALSE");
						toreturn.put("Message", "Do Multi Environment Analysis Error!");
						return toreturn;
					}
					getConn().eval("capture.output(print(outcomes),file=\"" + outFileName + "\", append=TRUE);");

				} //end stmt of read.pheno.data error 
			}// end stmt of read.csv error
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			toreturn.put("Success", "FALSE");
			toreturn.put("Message", "Rserve Exception!");
			return toreturn;
		} catch (REXPMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			toreturn.put("Success", "FALSE");
			toreturn.put("Message", "Rserve Evaluation Exception!");
			return toreturn;
		} finally{
			getConn().close();
		}
		toreturn.put("Success", "TRUE");
		toreturn.put("Message", "Do Multi-Environment Analysis on SSSL successfully!");
		return toreturn;
	}

}
