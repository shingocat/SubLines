package org.analysis.rserve.manager;

import java.util.HashMap;

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
		String resultFolderPath = model.getResultFolderPath();
		String outFile = model.getOutFileName();
		String genoFile = model.getGenoFile();
		String genoCol = model.getGenoColumnOnGD();
		String dpCode = model.getDpCodeOnGD();
		String rpCode = model.getRpCodeOnGD();
		String htCode = model.getHtCodeOnGD();
		String naCode = model.getNaCodeOnGD();
		Integer bcn = model.getBcnOnGD();
		Integer fn = model.getFnOnGD();
		Boolean isIncludeHt = model.getIsIncludeHT();
		Boolean isSimPvalue = model.getSimPvalueOnCS();
		Integer times = model.getBootStrapTimesOnCS();
		String refGeno = model.getRefGenoFile();
		if(refGeno == null || refGeno.isEmpty())
		{

			try {

				String setWd = "setwd(\"" + resultFolderPath + "\");";
				getConn().eval(setWd);

				StringBuilder data = new StringBuilder();
				data.append("data <- try(read.csv(\"" + genoFile + 
						"\",header = TRUE),silent=TRUE);");
				getConn().eval(data.toString());
				String run = getConn().eval("class(data);").asString();
				if(run != null && run.equals("try-error"))
				{
					StringBuilder error = new StringBuilder();
					error.append("msg <- trimStrings(strsplit(data,\":\")[[1]]);");
					error.append("msg <- trimStrings(paste(strsplit(msg,\"\\n\")[[length(msg)]], collapse=\" \"));");
					error.append("msg <- gsub(\"\\\"\", \"\", msg);");
					error.append("capture.output(cat(\"***\nError in read.csv function:\n\"\n,msg, \"\n***\n\n\", sep=\"\"),"
							+ "file=\"" + logFile + "\",append = TRUE);");
					getConn().eval(error.toString());
					toreturn.put("Success", "FALSE");
					toreturn.put("Message", "Error Reading CSV File Data!");
					return toreturn;
				} else
				{
					StringBuilder sb = new StringBuilder();
					sb.append("geno <- try(read.geno.data(data, dp.code=\"" + dpCode + "\"" +
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

					} else
					{
						sb = new StringBuilder();
						sb.append("chisq <- try(doChisqTest(geno),silent=TRUE);");
						getConn().eval(sb.toString());
						run = getConn().eval("class(chisq);").asString();
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
				}
			} catch (RserveException e) {
				toreturn.put("Success", "FALSE");
				toreturn.put("Message", "R Serve Exception!");
				e.printStackTrace();
			} catch (REXPMismatchException e) {
				toreturn.put("Success", "FALSE");
				toreturn.put("Message", "R Expression Exception!");
				e.printStackTrace();
			} finally
			{
				getConn().close();
			}
		} else
		{

		}
		toreturn.put("Success", "TRUE");
		if(toreturn.get("Message") == null)
			toreturn.put("Message", "Do Chisq Test Done!");
		return toreturn;
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
