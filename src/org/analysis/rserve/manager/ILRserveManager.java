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
		try{
			if(refGenoFile == null || refGenoFile.isEmpty())
			{
				toreturn = readGeno(model, false);
				if(toreturn.get("Success").equals("FALSE"))
				{
					return toreturn;
				} else
				{
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
					}
				}
			} else
			{
				toreturn = readGeno(model, false);
				if(toreturn.get("Success").equals("FALSE"))
				{
					return toreturn;
				}
				toreturn = readRefGeno(model);
				if(toreturn.get("Success").equals("FALSE"))
				{
					return toreturn;
				}
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
		try {
			toreturn = readPheno(model);
			if(toreturn.get("Success").equals("FALSE"))
				return toreturn;
			toreturn = readGeno(model, true);
			if(toreturn.get("Success").equals("FALSE"))
				return toreturn;
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
		return null;
	}

	private HashMap<String, String> readPheno(ILAnalysisModel model)
	{
		HashMap<String, String> toreturn = new HashMap<String, String>();
		String resultFolderPath = model.getResultFolderPath();
		String phenoFile = model.getPhenoFile();
		String dataType = model.getDataTypeOnPD();
		String genotype = model.getGenoFactOnPD();
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
					"\",header = TRUE),silent=TRUE);");
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
			} else
			{

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
		return null;
	}
}
