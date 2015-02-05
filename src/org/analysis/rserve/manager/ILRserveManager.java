package org.analysis.rserve.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RserveException;
import org.strasa.web.analysis.view.model.AnalysisModel;
import org.strasa.web.analysis.view.model.ILAnalysisModel;


public class ILRserveManager extends JRServeMangerImpl {

	public ILRserveManager()
	{
		super();
	}

	@Override
	public HashMap<String, String> doAnalysis(AnalysisModel model)
	{
		ILAnalysisModel ilModel = (ILAnalysisModel) model;
		System.out.println("Model" + model);
		if(ilModel.getAnalysisType() == "Chisq")
			return doChisq(ilModel);
		else if(ilModel.getAnalysisType() == "SMA")
			return doSingleMarker(ilModel);
		else if(ilModel.getAnalysisType() == "MMA")
			return doMultiMarker(ilModel);

		HashMap<String, String > toreturn = new HashMap<String, String>();
		toreturn.put("Success", "FALSE");
		toreturn.put("Message", "NO SUCH METHOD!");
		return toreturn;
	}

	private HashMap<String, String> doChisq(ILAnalysisModel model)
	{
		HashMap<String, String> toreturn = new HashMap<String, String>();
		Boolean isIncludeHt = model.getIsIncludeHT();
		Boolean isSimPvalue = model.getSimPvalueOnCS();
		Integer times = model.getBootStrapTimesOnCS();
		String refGenoFile = model.getRefGenoFile();
		String resultFolderPath = model.getResultFolderPath();
		String outFile = model.getOutFileName();
		String mapFile = model.getMapFile();
		try{
			if(refGenoFile == null || refGenoFile.isEmpty())
			{
				toreturn = readGeno(model, false);
				if(toreturn.get("Success").equals("FALSE"))
				{
					return toreturn;
				} else
				{
					if(mapFile != null && !mapFile.isEmpty())
						readMap(model);
					String setWd = "setwd(\"" + resultFolderPath + "\");";
					getConn().eval(setWd);
					getConn().eval("load(\".RData\");");
					StringBuilder sb = new StringBuilder();
					String temp = null;
					if(isIncludeHt)
						temp = "inc.ht = TRUE,";
					else
						temp = "inc.ht = FALSE,";
					if(isSimPvalue)
						temp = temp + "simulate.p.value = TRUE, B = " + times;
					else
						temp = temp + "simulate.p.value = FALSE, B = " + times;
					sb.append("chisq <- try(doChisqTest(geno,"
							+ temp +
							"),silent=TRUE);");
					getConn().eval(sb.toString());
					String run = getConn().eval("class(chisq);").asString();
					if(run != null && run.equals("try-error"))
					{
						StringBuilder error = new StringBuilder();
						error.append("msg <- trimStrings(strsplit(chisq,\":\")[[1]]);");
						error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]], collapse=\" \"));");
						error.append("msg <- gsub(\"\\\"\", \"\", msg);");
						error.append("capture.output(cat(\"***\nError in doChisq function:\n\"\n,msg, \"\n***\n\n\", sep=\"\"),"
								+ "file=\"" + logFile + "\",append = TRUE);");
						getConn().eval(error.toString());
						toreturn.put("Success", "FALSE");
						toreturn.put("Message", "Error in doChisqTest!");
						return toreturn;
					} else
					{
						sb = new StringBuilder();
						sb.append("capture.output(print(chisq), file=\"" + outFile + "\",append=TRUE)");
						getConn().eval(sb.toString());
						if(mapFile != null && !mapFile.isEmpty())
							plotIL();
					}
				}
			} else
			{
				toreturn = readGeno(model, false);
				if(toreturn.get("Success").equals("FALSE"))
					return toreturn;
				toreturn = readRefGeno(model);
				if(toreturn.get("Success").equals("FALSE"))
					return toreturn;
				if(mapFile != null && !mapFile.isEmpty())
					readMap(model);
				String setWd = "setwd(\"" + resultFolderPath + "\");";
				getConn().eval(setWd);
				getConn().eval("load(\".RData\")");
				StringBuilder sb = new StringBuilder();
				String temp = null;
				if(isIncludeHt)
					temp = "inc.ht = TRUE,";
				else
					temp = "inc.ht = FALSE,";
				if(isSimPvalue)
					temp = temp + "simulate.p.value = TRUE, B = " + times;
				else
					temp = temp + "simulate.p.value = FALSE, B = " + times;
				sb.append("chisq <- try(doChisqTest(geno, ref.matrix = genoRef, "
						+ temp +
						"),silent=TRUE);");
				getConn().eval(sb.toString());
				String run = getConn().eval("class(chisq);").asString();
				if(run != null && run.equals("try-error"))
				{
					StringBuilder error = new StringBuilder();
					error.append("msg <- trimStrings(strsplit(chisq,\":\")[[1]]);");
					error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]], collapse=\" \"));");
					error.append("msg <- gsub(\"\\\"\", \"\", msg);");
					error.append("capture.output(cat(\"***\nError in doChisq function:\n\"\n,msg, \"\n***\n\n\", sep=\"\"),"
							+ "file=\"" + logFile + "\",append = TRUE);");
					getConn().eval(error.toString());
					toreturn.put("Success", "FALSE");
					toreturn.put("Message", "Error in doChisqTest!");
					return toreturn;
				} else
				{
					sb = new StringBuilder();
					sb.append("capture.output(print(chisq), file=\"" + outFile + "\",append=TRUE)");
					getConn().eval(sb.toString());
					if(mapFile != null && !mapFile.isEmpty())
						plotIL();
				}
			}
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (REXPMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			getConn().close();
		}
		toreturn.put("Success", "TRUE");
		toreturn.put("Message", "Do Chisq Test Done!");
		return toreturn;
	}

	private HashMap<String, String> doSingleMarker(ILAnalysisModel model)
	{
		HashMap<String, String> toreturn = new HashMap<String, String>();
		String resultFolderPath = model.getResultFolderPath();
		String outFile = model.getOutFileName();
		Boolean isIncludeHt = model.getIsIncludeHT();
		Integer digits = model.getDigitsOnSM();
		String test = model.getTestOnSM();
		String mapFile = model.getMapFile();
		try {
			toreturn = readPheno(model);
			if(toreturn.get("Success").equals("FALSE"))
				return toreturn;
			toreturn = readGeno(model, true);
			if(toreturn.get("Success").equals("FALSE"))
				return toreturn;
			if(mapFile != null && !mapFile.isEmpty())
				readMap(model);
			String setWd = "setwd(\"" + resultFolderPath + "\");";
			getConn().eval(setWd);
			getConn().eval("load(\".RData\");");
			String temp = null;
			if(isIncludeHt)
				temp = "include.ht = TRUE,";
			else
				temp = "include.ht = FALSE,";
			if(digits != null)
				temp = temp + "digits = " + digits;
			StringBuilder sb = new StringBuilder();
			sb.append("sma <- try(doSMA(pheno, geno, " + temp + "),silent=TRUE);");
			getConn().eval(sb.toString());
			String run = getConn().eval("class(sma);").asString();
			if(run != null && run.equals("try-error"))
			{
				StringBuilder error = new StringBuilder();
				error.append("msg <- trimStrings(strsplit(sma,\":\")[[1]]);");
				error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]], collapse=\" \"));");
				error.append("msg <- gsub(\"\\\"\", \"\", msg);");
				error.append("capture.output(cat(\"***\nError in doSMA function:\n\"\n,msg, \"\n***\n\n\", sep=\"\"),"
						+ "file=\"" + logFile + "\",append = TRUE);");
				getConn().eval(error.toString());
				toreturn.put("Success", "FALSE");
				toreturn.put("Message", "Error in doSMA!");
				return toreturn;
			} 
			sb = new StringBuilder();
			sb.append("capture.output(print(sma), file=\"" + outFile + "\",append=TRUE)");
			getConn().eval(sb.toString());
			if(mapFile != null && !mapFile.isEmpty())
				plotIL();
		} catch (RserveException e) {
			e.printStackTrace();
			toreturn.put("Success", "FALSE");
			toreturn.put("Message", "R Serve Exception!");
			return toreturn;
		} catch (REXPMismatchException e) {
			e.printStackTrace();
			toreturn.put("Success", "FALSE");
			toreturn.put("Message", "R Expression Exception!");
			return toreturn;
		} finally
		{
			getConn().close();
		}
		toreturn.put("Success", "TRUE");
		toreturn.put("Message", "Do Single Marker Analysis Done!");
		return toreturn;
	}

	private HashMap<String, String> doMultiMarker(ILAnalysisModel model)
	{
		HashMap<String, String> toreturn = new HashMap<String, String>();
		String resultFolderPath = model.getResultFolderPath();
		String outFile = model.getOutFileName();
		String  method = model.getRegMethodOnMM();
		Double siglevel = model.getAlphaOnMM();
		Integer bootstrap = model.getBootstrapOnMM();
		String pvalMet = model.getPvalMethodOnMM();
		String family = "gaussian";
		Boolean isIncludeHt = model.getIsIncludeHT();
		Integer nfolds = model.getNfoldsOnMM();
		Double step = model.getStepOnMM();
		Integer maxTry = model.getMaxTryOnMM();
		String mapFile = model.getMapFile();
		try{
			toreturn = readPheno(model);
			if(toreturn.get("Success").equals("FALSE"))
				return toreturn;
			toreturn = readGeno(model, true);
			if(toreturn.get("Success").equals("FALSE"))
				return toreturn;
			if(mapFile != null && !mapFile.isEmpty())
				readMap(model);
			String setWd = "setwd(\"" + resultFolderPath + "\");";
			getConn().eval(setWd);
			getConn().eval("load(\".RData\");");
			StringBuilder sb = new StringBuilder();
			sb.append("mma <- try(doMMA(");
			if(method != null)
				sb.append("method = \"" + method + "\",");
			if(siglevel != null)
				sb.append("siglevel = " + siglevel + ",");
			if(bootstrap != null)
				sb.append("bootstrap = " + bootstrap + ",");
			if(pvalMet != null)
				sb.append("pval.method = \"" + pvalMet + "\",");
			if(family != null)
				sb.append("family = \"" +  family + "\",");
			if(isIncludeHt)
				sb.append("include.ht = TRUE,");
			else
				sb.append("include.ht = FALSE,");
			if(nfolds != null)
				sb.append("nfolds = " + nfolds + ",");
			if(step != null)
				sb.append("step = " + step + ",");
			if(maxTry != null)
				sb.append("max.try = " + maxTry + ",");
			sb.append("phenotypicData = pheno,");
			sb.append("genotypicData = geno");
			sb.append("), silent=TRUE);");
			getConn().eval(sb.toString());
			String run = getConn().eval("class(mma);").asString();
			if(run != null && run.equals("try-error"))
			{
				StringBuilder error = new StringBuilder();
				error.append("msg <- trimStrings(strsplit(mma,\":\")[[1]]);");
				error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]], collapse=\" \"));");
				error.append("msg <- gsub(\"\\\"\", \"\", msg);");
				error.append("capture.output(cat(\"***\nError in do.MMA function:\n\"\n,msg, \"\n***\n\n\", sep=\"\"),"
						+ "file=\"" + logFile + "\",append = TRUE);");
				getConn().eval(error.toString());
				toreturn.put("Success", "FALSE");
				toreturn.put("Message", "Error in doMMA!");
				return toreturn;
			} 
			sb = new StringBuilder();
			sb.append("capture.output(print(mma, p.value=" + siglevel + "), file=\"" + outFile + "\",append=TRUE)");
			getConn().eval(sb.toString());
			if(mapFile != null && !mapFile.isEmpty())
				plotIL();
		} catch (RserveException e) {
			e.printStackTrace();
			toreturn.put("Success", "FALSE");
			toreturn.put("Message", "R Serve Exception!");
			return toreturn;
		} catch (REXPMismatchException e) {
			e.printStackTrace();
			toreturn.put("Success", "FALSE");
			toreturn.put("Message", "R Expression Exception!");
			return toreturn;
		}  finally
		{
			getConn().close();
		}
		toreturn.put("Success", "TRUE");
		toreturn.put("Message", "Do Multi-Marker Analysis Done!");
		return toreturn;
	}

	private HashMap<String, String> readPheno(ILAnalysisModel model)
	{
		HashMap<String, String> toreturn = new HashMap<String, String>();
		String resultFolderPath = model.getResultFolderPath();
		String phenoFile = model.getPhenoFile();
		String dataType = model.getDataTypeOnPD();
		String naCode = model.getNaCodeOnPD();
		String genotype = model.getGenoFactOnPD();
		String environment = model.getEnvFactOnPD();
		String expDesign = model.getExptlDesignOnPD();
		String block = model.getBlockFactOnPD();
		String replicate = model.getRepFactOnPD();
		String row = model.getRowFactOnPD();
		String column = model.getColFactOnPD();
		String envAnalysisType = model.getEnvAnalysisType();
		ArrayList<String> respvars = (ArrayList<String>) model.getRespsOnPD();
		String[] respVars = new String[respvars.size()];
		for(int i = 0 ; i < respvars.size() ; i++)
		{
			respVars[i] = respvars.get(i);
		}
		
		try {
			String setWd = "setwd(\"" + resultFolderPath + "\");";
			getConn().eval(setWd);
			StringBuilder phenoData = new StringBuilder();
			phenoData.append("phenoData <- try(read.csv(\"" + phenoFile + 
					"\",header = TRUE, na.strings = \""+ naCode + 
					"\"),silent=TRUE);");
			getConn().eval(phenoData.toString());
			String run = getConn().eval("class(phenoData);").asString();
			if(run != null && run.equals("try-error"))
			{
				StringBuilder error = new StringBuilder();
				error.append("msg <- trimStrings(strsplit(phenoData,\":\")[[1]]);");
				error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]], collapse=\" \"));");
				error.append("msg <- gsub(\"\\\"\", \"\", msg);");
				error.append("capture.output(cat(\"***\nError in read.csv function:\n\"\n,msg, \"\n***\n\n\", sep=\"\"),"
						+ "file=\"" + logFile + "\",append = TRUE);");
				getConn().eval(error.toString());
				toreturn.put("Success", "FALSE");
				toreturn.put("Message", "Error Reading CSV File Phenotypic Data!");
				return toreturn;
			}

			if(dataType.equals("MEAN"))
			{
				StringBuilder pheno = new StringBuilder();
				String temp = null;
				temp = "resp.var = " + this.getInputTransform().createRVector(respVars);
				temp = temp + ", na.code = NA, geno = \"" + genotype + "\"";
				pheno.append("pheno <- try(read.pheno.data(phenoData, type=\"MEAN\", pop.type=\"IL\","
						+ temp + "),silent=TRUE);");
				getConn().eval(pheno.toString());
				run = getConn().eval("class(pheno);").asString();
				if(run != null && run.equals("try-error"))
				{
					StringBuilder error = new StringBuilder();
					error.append("msg <- trimStrings(strsplit(pheno,\":\")[[1]]);");
					error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]], collapse=\" \"));");
					error.append("msg <- gsub(\"\\\"\", \"\", msg);");
					error.append("capture.output(cat(\"***\nError in read.pheno.data function:\n\"\n,msg, \"\n***\n\n\", sep=\"\"),"
							+ "file=\"" + logFile + "\",append = TRUE);");
					getConn().eval(error.toString());
					toreturn.put("Success", "FALSE");
					toreturn.put("Message", "Error Reading Pheno Data!");
					return toreturn;
				} 
			}else if(dataType.equals("RAW"))
			{
				StringBuilder sb = new StringBuilder();
				sb.append("pheno <- try(read.pheno.data(phenoData, type=\"RAW\",");
				sb.append("pop.type=\"IL\", geno = \"" + genotype + "\",");
				sb.append("resp.var = " + this.getInputTransform().createRVector(respVars) + ",");
				if(environment == null || environment.isEmpty())
					sb.append("env = NULL,");
				else
					sb.append("env = \"" + environment + "\",");
				if(expDesign.equals("Randomized Complete Block(RCB)"))
				{//0
					sb.append("exptl.design = \"RCB\",");
					sb.append("block = \"" + block + "\",");
				} else if(expDesign.equals("Augmented RCB"))
				{//1
					sb.append("exptl.design = \"AugRCB\",");
					sb.append("block = \"" + block + "\",");
				} else if(expDesign.equals("Augmented Latin Square"))
				{//2
					sb.append("exptl.design = \"AugLS\",");
					sb.append("row = \"" + row + "\",");
					sb.append("column = \"" + column + "\",");
				} else if(expDesign.equals("Alpha-Lattice"))
				{//3
					sb.append("exptl.design = \"Alpha\",");
					sb.append("block = \"" + block + "\",");
					sb.append("rep = \"" + replicate + "\",");
				} else if(expDesign.equals("Row-Column"))
				{//4
					sb.append("exptl.design = \"RowCol\",");
					sb.append("block = \"" + block + "\",");
					sb.append("row = \"" + row + "\",");
					sb.append("column = \"" + column + "\",");
				} else if(expDesign.equals("Latinized Alpha-Lattice"))
				{//5
					sb.append("exptl.design = \"LatinAlpha\",");
					sb.append("block = \"" + block + "\",");
					sb.append("rep = \"" + replicate + "\",");
				} else if(expDesign.equals("Latinized Row-Column"))
				{//6
					sb.append("exptl.design = \"LatinRowCol\",");
					sb.append("block = \"" + block + "\",");
					sb.append("row = \"" + row + "\",");
					sb.append("column = \"" + column + "\",");
				}
				sb.append("na.code = NA), silent=TRUE);");
				getConn().eval(sb.toString());
				run = getConn().eval("class(pheno);").asString();
				if(run != null && run.equals("try-error"))
				{
					StringBuilder error = new StringBuilder();
					error.append("msg <- trimStrings(strsplit(pheno,\":\")[[1]]);");
					error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]], collapse=\" \"));");
					error.append("msg <- gsub(\"\\\"\", \"\", msg);");
					error.append("capture.output(cat(\"***\nError in read.pheno.data function:\n\"\n,msg, \"\n***\n\n\", sep=\"\"),"
							+ "file=\"" + logFile + "\",append = TRUE);");
					getConn().eval(error.toString());
					toreturn.put("Success", "FALSE");
					toreturn.put("Message", "Error Reading Pheno Data!");
					return toreturn;
				} 
				sb = new StringBuilder();
				if(envAnalysisType.equals("Single-Environment Analysis"))
				{
					sb.append("pheno <- try(doSEA(pheno), silent=T);");
					getConn().eval(sb.toString());
					run = getConn().eval("class(pheno);").asString();
					if(run != null && run.equals("try-error"))
					{
						StringBuilder error = new StringBuilder();
						error.append("msg <- trimStrings(strsplit(pheno,\":\")[[1]]);");
						error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]], collapse=\" \"));");
						error.append("msg <- gsub(\"\\\"\", \"\", msg);");
						error.append("capture.output(cat(\"***\nError in doSMA function:\n\"\n,msg, \"\n***\n\n\", sep=\"\"),"
								+ "file=\"" + logFile + "\",append = TRUE);");
						getConn().eval(error.toString());
						toreturn.put("Success", "FALSE");
						toreturn.put("Message", "Error doSMA!");
						return toreturn;
					} 
				} else
				{
					sb.append("pheno <- try(doMEA(pheno, is.EnvFixed=TRUE), silent=TRUE);");
					getConn().eval(sb.toString());
					run = getConn().eval("class(pheno);").asString();
					if(run != null && run.equals("try-error"))
					{
						StringBuilder error = new StringBuilder();
						error.append("msg <- trimStrings(strsplit(pheno,\":\")[[1]]);");
						error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]], collapse=\" \"));");
						error.append("msg <- gsub(\"\\\"\", \"\", msg);");
						error.append("capture.output(cat(\"***\nError in doMEA function:\n\"\n,msg, \"\n***\n\n\", sep=\"\"),"
								+ "file=\"" + logFile + "\",append = TRUE);");
						getConn().eval(error.toString());
						toreturn.put("Success", "FALSE");
						toreturn.put("Message", "Error doMEA!");
						return toreturn;
					} 
				}
			}
			getConn().eval("save.image();");
		} catch (RserveException e) {
			e.printStackTrace();
			toreturn.put("Success", "FALSE");
			toreturn.put("Message", "R Serve Error!");
			return toreturn;
		} catch (REXPMismatchException e) {
			e.printStackTrace();
			toreturn.put("Success", "FALSE");
			toreturn.put("Message", "R Expression Error!");
			return toreturn;
		}
		toreturn.put("Success", "TRUE");
		toreturn.put("Message", "Read Pheno Done!");
		return toreturn;
	}

	private HashMap<String, String> readGeno(ILAnalysisModel model, Boolean isRDataExist)
	{	
		HashMap<String, String> toreturn = new HashMap<String, String>();
		String resultFolderPath = model.getResultFolderPath();
		String genoFile = model.getGenoFile();
		String genoCol = model.getGenoColumnOnGD();
		String dpCode = model.getDpCodeOnGD();
		String rpCode = model.getRpCodeOnGD();
		String htCode = model.getHtCodeOnGD();
		String naCode = model.getNaCodeOnGD();
		Integer bcn = model.getBcnOnGD();
		Integer fn = model.getFnOnGD();

		try {

			String setWd = "setwd(\"" + resultFolderPath + "\");";
			getConn().eval(setWd);
			if(isRDataExist)
				getConn().eval("load(\".RData\");");
			StringBuilder data = new StringBuilder();
			data.append("genoData <- try(read.csv(\"" + genoFile + 
					"\",header = TRUE, na.strings = \"" + naCode + "\"),silent=TRUE);");
			getConn().eval(data.toString());
			String run = getConn().eval("class(genoData);").asString();
			if(run != null && run.equals("try-error"))
			{
				StringBuilder error = new StringBuilder();
				error.append("msg <- trimStrings(strsplit(genoData,\":\")[[1]]);");
				error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]], collapse=\" \"));");
				error.append("msg <- gsub(\"\\\"\", \"\", msg);");
				error.append("capture.output(cat(\"***\nError in read.csv function:\n\"\n,msg, \"\n***\n\n\", sep=\"\"),"
						+ "file=\"" + logFile + "\",append = TRUE);");
				getConn().eval(error.toString());
				toreturn.put("Success", "FALSE");
				toreturn.put("Message", "Error Reading CSV File Geno Data!");
				return toreturn;
			} 

			StringBuilder sb = new StringBuilder();
			sb.append("geno <- try(read.geno.data(genoData, dp.code=\"" + dpCode + "\"" +
					",rp.code=\"" + rpCode + "\", ht.code = \"" + htCode + "\"" +
					",na.code= \"NA\",BCn =" + bcn + ",Fn=" + fn +", geno=\"" + genoCol + "\"),silent=TRUE);");
			getConn().eval(sb.toString());
			run = getConn().eval("class(geno);").asString();
			if(run != null && run.equals("try-error"))
			{
				StringBuilder error = new StringBuilder();
				error.append("msg <- trimStrings(strsplit(geno,\":\")[[1]]);");
				error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]], collapse=\" \"));");
				error.append("msg <- gsub(\"\\\"\", \"\", msg);");
				error.append("capture.output(cat(\"***\nError in read.geno.data function:\n\"\n,msg, \"\n***\n\n\", sep=\"\"),"
						+ "file=\"" + logFile + "\",append = TRUE);");
				getConn().eval(error.toString());
				toreturn.put("Success", "FALSE");
				toreturn.put("Message", "Error Reading Geno Data!");
				return toreturn;
			}
			getConn().eval("save.image();");
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			toreturn.put("Success", "FALSE");
			toreturn.put("Message", "R Serve Error!");
			return toreturn;
		} catch (REXPMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			toreturn.put("Success", "FALSE");
			toreturn.put("Message", "R Expression Error!");
			return toreturn;
		}
		toreturn.put("Success", "TRUE");
		toreturn.put("Message", "Read Geno Done!");
		return toreturn;
	}

	private HashMap<String, String> readRefGeno(ILAnalysisModel model)
	{
		HashMap<String, String> toreturn = new HashMap<String, String>();
		String resultFolderPath = model.getResultFolderPath();
		String genoRefFile = model.getRefGenoFile();
		String genoRefCol = model.getGenoColumnOnRGD();
		String dpRefCode = model.getDpCodeOnRGD();
		String rpRefCode = model.getRpCodeOnRGD();
		String htRefCode = model.getHtCodeOnRGD();
		String naRefCode = model.getNaCodeOnRGD();
		Integer bcnRef = model.getBcnOnRGD();
		Integer fnRef = model.getFnOnRGD();

		try{
			String setWd = "setwd(\"" + resultFolderPath + "\");";
			getConn().eval(setWd);
			getConn().eval("load(\".RData\")");
			StringBuilder dataRef = new StringBuilder();
			dataRef.append("genoRefData <- try(read.csv(\"" + genoRefFile + 
					"\",header = TRUE, na.strings = \"" + naRefCode + "\"),silent=TRUE);");
			getConn().eval(dataRef.toString());
			String run = getConn().eval("class(genoRefData);").asString();
			if(run != null && run.equals("try-error"))
			{
				StringBuilder error = new StringBuilder();
				error.append("msg <- trimStrings(strsplit(genoRefData,\":\")[[1]]);");
				error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]], collapse=\" \"));");
				error.append("msg <- gsub(\"\\\"\", \"\", msg);");
				error.append("capture.output(cat(\"***\nError in read.csv function:\n\"\n,msg, \"\n***\n\n\", sep=\"\"),"
						+ "file=\"" + logFile + "\",append = TRUE);");
				getConn().eval(error.toString());
				toreturn.put("Success", "FALSE");
				toreturn.put("Message", "Error Reading CSV File Reference Genotypic Data!");
				return toreturn;
			}

			StringBuilder sb = new StringBuilder();
			sb.append("genoRef <- try(read.geno.data(genoRefData, dp.code=\"" + dpRefCode + "\"" +
					",rp.code=\"" + rpRefCode + "\", ht.code = \"" + htRefCode + "\"" +
					",na.code= \"NA\",BCn =" + bcnRef + ",Fn=" + fnRef +", geno=\"" + genoRefCol + "\"),silent=TRUE);");
			getConn().eval(sb.toString());
			run = getConn().eval("class(genoRef);").asString();
			if(run != null && run.equals("try-error"))
			{
				StringBuilder error = new StringBuilder();
				error.append("msg <- trimStrings(strsplit(genoRef,\":\")[[1]]);");
				error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]], collapse=\" \"));");
				error.append("msg <- gsub(\"\\\"\", \"\", msg);");
				error.append("capture.output(cat(\"***\nError in read.geno.data function:\n\"\n,msg, \"\n***\n\n\", sep=\"\"),"
						+ "file=\"" + logFile + "\",append = TRUE);");
				getConn().eval(error.toString());
				toreturn.put("Success", "FALSE");
				toreturn.put("Message", "Error Reading Geno Ref Data!");
				return toreturn;
			}
			getConn().eval("save.image();");
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			toreturn.put("Success", "FALSE");
			toreturn.put("Message", "R Serve Error!");
			return toreturn;
		} catch (REXPMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			toreturn.put("Success", "FALSE");
			toreturn.put("Message", "R Expression Error!");
			return toreturn;
		} 
		toreturn.put("Success", "TRUE");
		toreturn.put("Message", "Read Ref Geno Done!");
		return toreturn;
	}

	private HashMap<String, String> readMap(ILAnalysisModel model)
	{
		HashMap<String, String> toreturn = new HashMap<String, String>();
		String resultFolderPath = model.getResultFolderPath();
		String mapFile = model.getMapFile();
		String marker = model.getMarColOnMD();
		String chr = model.getChrColOnMD();
		String pos = model.getPosColOnMD();
		String unit = model.getUnitOnMD();
		try
		{
			String setWd = "setwd(\"" + resultFolderPath + "\");";
			getConn().eval(setWd);
			getConn().eval("load(\".RData\")");
			StringBuilder dataRef = new StringBuilder();
			dataRef.append("mapData <- try(read.csv(\"" + mapFile + 
					"\",header = TRUE),silent=TRUE);");
			getConn().eval(dataRef.toString());
			String run = getConn().eval("class(mapData);").asString();
			if(run != null && run.equals("try-error"))
			{
				StringBuilder error = new StringBuilder();
				error.append("msg <- trimStrings(strsplit(mapData,\":\")[[1]]);");
				error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]], collapse=\" \"));");
				error.append("msg <- gsub(\"\\\"\", \"\", msg);");
				error.append("capture.output(cat(\"***\nError in read.csv function:\n\"\n,msg, \"\n***\n\n\", sep=\"\"),"
						+ "file=\"" + logFile + "\",append = TRUE);");
				getConn().eval(error.toString());
				toreturn.put("Success", "FALSE");
				toreturn.put("Message", "Error Reading CSV File map Data!");
				return toreturn;
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("map <- try(read.map.data(mapData, marker = \"" + marker + "\"" +
					",chr = \"" + chr + "\", pos = \"" + pos + "\"" +
					",units = \"" + unit + "\"),silent=TRUE);");
			getConn().eval(sb.toString());
			run = getConn().eval("class(map);").asString();
			if(run != null && run.equals("try-error"))
			{
				StringBuilder error = new StringBuilder();
				error.append("msg <- trimStrings(strsplit(map,\":\")[[1]]);");
				error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]], collapse=\" \"));");
				error.append("msg <- gsub(\"\\\"\", \"\", msg);");
				error.append("capture.output(cat(\"***\nError in read.map.data function:\n\"\n,msg, \"\n***\n\n\", sep=\"\"),"
						+ "file=\"" + logFile + "\",append = TRUE);");
				getConn().eval(error.toString());
				toreturn.put("Success", "FALSE");
				toreturn.put("Message", "Error Reading Map Data!");
				return toreturn;
			}
			getConn().eval("save.image();");
			
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			toreturn.put("Success", "FALSE");
			toreturn.put("Message", "R Serve Error!");
			return toreturn;
		} catch (REXPMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			toreturn.put("Success", "FALSE");
			toreturn.put("Message", "R Expression Error!");
			return toreturn;
		} 
		
		toreturn.put("Success", "TRUE");
		toreturn.put("Message", "Read Map Done!");
		return toreturn;
	}
	
	private void plotIL()
	{
		try{
			StringBuilder sb = new StringBuilder();
			sb.append("graph.il(geno, map, plt.png=TRUE)");
			getConn().eval(sb.toString());
		}catch (RserveException e) {
			e.printStackTrace();
		}
	}
}
