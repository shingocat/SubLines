package org.analysis.rserve.manager.test;

import org.strasa.web.utilities.InputTransform;
import org.zkoss.zk.ui.Sessions;
public class TestRServeManager {

	private InputTransform inputTransform;
	
	private static String BSLASH = "\\";
	private static String FSLASH = "/";
	private static String OUTPUTFOLDER_PATH =  Sessions.getCurrent().getWebApp().getRealPath("outputfolder")+ System.getProperty("file.separator");
	public static String DATA_PATH = System.getProperty("user.dir")+ System.getProperty("file.separator") + "sample_datasets" + System.getProperty("file.separator");

//	public void doSingleEnvironmentAnalysis(String dataFileName, String outFileName, String resultFolderPath, int designIndex, String[] respvar, String environment, String[] environmentLevels,
//			String genotype, String block, String rep, String row, String column, boolean descriptiveStat, boolean varianceComponents, 
//			boolean boxplotRawData, boolean histogramRawData, boolean heatmapResiduals, String heatmapRow, String heatmapColumn, boolean diagnosticPlot, 
//			boolean genotypeFixed, boolean performPairwise, String pairwiseAlpha, String[] genotypeLevels, String[] controlLevels, boolean compareControl, boolean performAllPairwise,
//			boolean genotypeRandom, boolean excludeControls, boolean genoPhenoCorrelation, RConnection rEngine) {
//		
//		inputTransform = new InputTransform();
//		
//		String respvarVector= inputTransform.createRVector(respvar);
////		String genotypeLevelsVector= inputTransform.createRVector(genotypeLevels);
//		String controlLevelsVector= inputTransform.createRVector(controlLevels);
//		boolean runningFixedSuccess =true;
//		boolean runningRandomSuccess =true;
//		boolean printAllOutputFixed =true;
//		boolean printAllOutputRandom =true;
//		
//		try {				
//			String designUsed = new String();
//			String design = new String();
//			switch (designIndex) {
//				case 0: {
//					designUsed = "Randomized Complete Block (RCB)"; 
//					design = "RCB"; 
//					break;
//				}
//				case 1: {
//					designUsed = "Augmented RCB"; 
//					design = "AugRCB";
//					break;
//				}
//				case 2: {
//					designUsed = "Augmented Latin Square"; 
//					design = "AugLS";
//					break;
//				}
//				case 3: {
//					designUsed = "Alpha-Lattice"; 
//					design = "Alpha";
//					break;
//				}
//				case 4: {
//					designUsed = "Row-Column"; 
//					design = "RowCol";
//					break;
//				}
//				case 5: {
//					designUsed = "Latinized Alpha-Lattice"; 
//					design = "LatinAlpha";
//					break;
//				}
//				case 6: {
//					designUsed = "Latinized Row-Column"; 
//					design = "LatinRowCol";
//					break;
//				}
//				default: {
//					designUsed = "Randomized Complete Block (RCB)"; 
//					design = "RCB";
//					break;
//				}
//			}
//			
//			String readData = "data <- read.csv(\"" + dataFileName + "\", header = TRUE, na.strings = c(\"NA\",\".\",\" \",\"\"), blank.lines.skip=TRUE, sep = \",\")";
//			System.out.println(readData);
//			rEngine.eval(readData);
//			String runSuccessData = rEngine.eval("data").toString();
//			
//			if (runSuccessData != null && runSuccessData.equals("notRun")) {	
//				System.out.println("error");
//				rEngine.eval("capture.output(cat(\"\n***Error reading data.***\n\"),file=\"" + outFileName + "\",append = FALSE)"); 
//			}
//			else {
//				String setWd = "setwd(\"" + resultFolderPath + "\")";
//				System.out.println(setWd);
//				rEngine.eval(setWd);
//			}
//						
//			String usedData = "capture.output(cat(\"\nDATA FILE: " + dataFileName + "\n\",file=\"" + outFileName + "\"))";
//			String outFile = "capture.output(cat(\"\nSINGLE-ENVIRONMENT ANALYSIS\n\"),file=\"" + outFileName + "\",append = TRUE)";
//			String usedDesign = "capture.output(cat(\"\nDESIGN: " + designUsed + "\n\n\"),file=\"" + outFileName + "\",append = TRUE)";
//			String sep = "capture.output(cat(\"------------------------------\"),file=\"" + outFileName + "\",append = TRUE)";
//			String sep2 = "capture.output(cat(\"==============================\n\"),file=\"" + outFileName + "\",append = TRUE)";
//			String outspace = "capture.output(cat(\"\n\"),file=\"" + outFileName + "\",append = TRUE)"; 
//			
//			rEngine.eval(usedData);
//			rEngine.eval(outFile);
//			rEngine.eval(usedDesign);
//			
//			// OUTPUT
//			// Genotype Fixed
//			if (genotypeFixed) {
//				String funcSsaFixed = null;
//				String groupVars = null;
//				if (environment == "NULL") {
//					if (designIndex == 0 || designIndex == 1){
//						funcSsaFixed = "ssa1 <- try(ssa.test(\"" + design + "\",data,"+ respvarVector + ",\"" + genotype + "\",\"" + block+ "\",column=NULL, rep=NULL," + environment+ ", is.random = FALSE), silent = TRUE)";
//						groupVars = "c(\"" + genotype + "\", \"" + block + "\")";
//					} else if (designIndex == 2) {
//						funcSsaFixed = "ssa1 <- try(ssa.test(\"" + design + "\",data,"+ respvarVector + ",\"" + genotype + "\",\"" + row+ "\",\"" + column + "\", rep=NULL," + environment+ ", is.random = FALSE), silent = TRUE)";
//						groupVars = "c(\"" + genotype + "\", \"" + row + "\", \"" + column + "\")";
//					} else if (designIndex == 3 || designIndex == 5) {
//						funcSsaFixed = "ssa1 <- try(ssa.test(\"" + design + "\",data,"+ respvarVector + ",\"" + genotype + "\",\"" + block+ "\",column=NULL,\"" + rep + "\"," + environment+ ", is.random = FALSE), silent = TRUE)";
//						groupVars = "c(\"" + genotype + "\", \"" + block + "\", \"" + rep + "\")";
//					} else if (designIndex == 4 || designIndex == 6) {
//						funcSsaFixed = "ssa1 <- try(ssa.test(\"" + design + "\",data,"+ respvarVector + ",\"" + genotype + "\",\"" + row+ "\",\"" + column + "\",\"" + rep + "\","+ environment + ", is.random = FALSE), silent = TRUE)";
//						groupVars = "c(\"" + genotype + "\", \"" + rep + "\", \"" + row + "\", \"" + column + "\")";
//					}
//				} else {
//					if (designIndex == 0 || designIndex == 1){
//						funcSsaFixed = "ssa1 <- try(ssa.test(\"" + design + "\",data,"+ respvarVector + ",\"" + genotype + "\",\"" + block+ "\",column=NULL, rep=NULL,\"" + environment+ "\", is.random = FALSE), silent = TRUE)";
//						groupVars = "c(\"" + genotype + "\", \"" + block + "\")";
//					} else if (designIndex == 2) {
//						funcSsaFixed = "ssa1 <- try(ssa.test(\"" + design + "\",data,"+ respvarVector + ",\"" + genotype + "\",\"" + row+ "\",\"" + column + "\", rep=NULL,\"" + environment+ "\", is.random = FALSE), silent = TRUE)";
//						groupVars = "c(\"" + genotype + "\", \"" + row + "\", \"" + column + "\")";
//					} else if (designIndex == 3 || designIndex == 5) {
//						funcSsaFixed = "ssa1 <- try(ssa.test(\"" + design + "\",data,"+ respvarVector + ",\"" + genotype + "\",\"" + block+ "\",column=NULL,\"" + rep + "\",\"" + environment+ "\", is.random = FALSE), silent = TRUE)";
//						groupVars = "c(\"" + genotype + "\", \"" + block + "\", \"" + rep + "\")";
//					} else if (designIndex == 4 || designIndex == 6) {
//						funcSsaFixed = "ssa1 <- try(ssa.test(\"" + design + "\",data,"+ respvarVector + ",\"" + genotype + "\",\"" + row+ "\",\"" + column + "\",\"" + rep + "\",\""+ environment + "\", is.random = FALSE), silent = TRUE)";
//						groupVars = "c(\"" + genotype + "\", \"" + rep + "\", \"" + row + "\", \"" + column + "\")";
//					}
//				}
//				String fixedHead = "capture.output(cat(\"GENOTYPE AS: Fixed\n\"),file=\""+ outFileName + "\",append = TRUE)";
//				rEngine.eval(funcSsaFixed);
//				rEngine.eval(sep2);
//				rEngine.eval(fixedHead);
//				rEngine.eval(sep2);
//				rEngine.eval(outspace);
//				
//				System.out.println(funcSsaFixed);
//
//				String runSuccess = rEngine.eval("class(ssa1)").asString();
//				System.out.println("rs: "+runSuccess);
//				if (runSuccess != null && runSuccess.equals("try-error")) {	
//					System.out.println("ssa.test: error");
//					String checkError = "msg <- trimStrings(strsplit(ssa1, \":\")[[1]])";
//					String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//					String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//					String checkError4 = "capture.output(cat(\"*** \nERROR in ssa.test function:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//					rEngine.eval(checkError);
//					rEngine.eval(checkError2);
//					rEngine.eval(checkError3);
//					rEngine.eval(checkError4);
//					
//					runningFixedSuccess=false;
//
//				}
//				else {
//					for (int k = 0; k < respvar.length; k++) {
//						int i = k + 1; // 1-relative index;
//						String respVarHead = "capture.output(cat(\"\nRESPONSE VARIABLE: " + respvar[k] + "\n\"),file=\"" + outFileName + "\",append = TRUE)";
//						rEngine.eval(sep);
//						rEngine.eval(respVarHead);
//						rEngine.eval(sep);
//						rEngine.eval(outspace);
//						
//						// optional output: descriptive statistics
//						if (descriptiveStat) {
//							String funcDesc = null;
//							if (environment=="NULL") {
//								funcDesc = "outDesc <- try(DescriptiveStatistics(data, \"" + respvar[k] + "\", " + environment + "), silent=TRUE)";
//							} else {
//								funcDesc = "outDesc <- try(DescriptiveStatistics(data, \"" + respvar[k] + "\", \"" + environment + "\"), silent=TRUE)";
//							}
//							System.out.println(funcDesc);
//							rEngine.eval(funcDesc);
//							
//							String outDescStat = "capture.output(cat(\"DESCRIPTIVE STATISTICS:\n\n\"),file=\"" + outFileName + "\",append = TRUE)"; 
//							String outDescStat2 = "capture.output(outDesc,file=\"" + outFileName + "\",append = TRUE)";
//							
//	  						String runSuccessDescStat = rEngine.eval("class(outDesc)").toString();	
//							if (runSuccessDescStat != null && runSuccessDescStat.equals("try-error")) {	
//								System.out.println("desc stat: error");
//								String checkError = "msg <- trimStrings(strsplit(outDesc, \":\")[[1]])";
//								String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//								String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//								String checkError4 = "capture.output(cat(\"*** \nERROR in DescriptiveStatistics function:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//								rEngine.eval(checkError);
//								rEngine.eval(checkError2);
//								rEngine.eval(checkError3);
//								rEngine.eval(checkError4);
//							} 
//							else {
//								rEngine.eval(outspace);
//								rEngine.eval(outDescStat);
//								rEngine.eval(outDescStat2);
//								rEngine.eval(outspace);
//							}
//						}
//						int envLevelsLength=0;
//						if (environment == "NULL") {
//							envLevelsLength = 1;
//						} else {
//							envLevelsLength = environmentLevels.length;
//						}
//						
//						for (int m = 0; m < envLevelsLength; m++) { // no of envts or sites
//							printAllOutputFixed=true;
//							int j = m + 1; // 1-relative index;
//							if (environment != "NULL") {
//								String envtHead = "capture.output(cat(\"\nANALYSIS FOR: "+ environment + "\", \" = \" ,ssa1$output[[" + i	+ "]]$site[[" + j + "]]$env,\"\n\"),file=\""+ outFileName + "\",append = TRUE)";
//								rEngine.eval(sep);
//								System.out.println(envtHead);
////								rEngine.eval(envtHead);
//								rEngine.eval(sep);
//								rEngine.eval(outspace);
//							}
//							
//							//check if the data has too many missing observation
//							double nrowData=rEngine.eval("ssa1$output[[" + i + "]]$site[[" + j + "]]$responseRate").asDouble();
//							if (nrowData < 0.80) {
//								String allNAWarning = rEngine.eval("ssa1$output[[" + i + "]]$site[[" + j + "]]$manyNAWarning").toString();
//								String printError1 = "capture.output(cat(\"***\\n\"), file=\"" + outFileName + "\",append = TRUE)";
//								String printError2 = "capture.output(cat(\"ERROR:\\n\"), file=\"" + outFileName + "\",append = TRUE)";
//								String printError3 = "capture.output(cat(\"" + allNAWarning + "\\n\"), file=\"" + outFileName + "\",append = TRUE)";
//								
//								rEngine.eval(outspace);
//								rEngine.eval(printError1);
//								rEngine.eval(printError2);
//								rEngine.eval(printError3);
//								rEngine.eval(printError1);
//								rEngine.eval(outspace);
//								rEngine.eval(outspace);
//								printAllOutputFixed=false;
//								
//							} else {
//								String lmerRun=rEngine.eval("ssa1$output[[" + i + "]]$site[[" + j + "]]$lmerRun").toString();
//								if (lmerRun.equals("ERROR")) {
//									String lmerError = rEngine.eval("ssa1$output[[" + i + "]]$site[[" + j + "]]$lmerError").toString();
//									String printError1 = "capture.output(cat(\"***\\n\"), file=\"" + outFileName + "\",append = TRUE)";
//									String printError2 = "capture.output(cat(\"ERROR:\\n\"), file=\"" + outFileName + "\",append = TRUE)";
//									String printError3 = "capture.output(cat(\"" + lmerError + "\\n\"), file=\"" + outFileName + "\",append = TRUE)";
//									
//									rEngine.eval(outspace);
//									rEngine.eval(printError1);
//									rEngine.eval(printError2);
//									rEngine.eval(printError3);
//									rEngine.eval(printError1);
//									rEngine.eval(outspace);
//									rEngine.eval(outspace);
//									printAllOutputFixed=false;
//								}
//							}
//							
//							if (printAllOutputFixed) {
//								// default output: trial summary
//								String funcTrialSum = "funcTrialSum <- try(class.information(" + groupVars + ",ssa1$output[[" + i + "]]$site[[" + j + "]]$data), silent=TRUE)";
//								String trialSumHead = "capture.output(cat(\"\nDATA SUMMARY:\n\n\"),file=\"" + outFileName + "\",append = TRUE)";
//								String trialObsRead = "capture.output(cat(\"Number of observations read: \", ssa1$output[["	+ i	+ "]]$site[[" + j + "]]$obsread[[1]],\"\n\"),file=\"" + outFileName + "\",append = TRUE)";
//								String trialObsUsed = "capture.output(cat(\"Number of observations used: \", ssa1$output[["	+ i	+ "]]$site[[" + j + "]]$obsused[[1]],\"\n\n\"),file=\""	+ outFileName + "\",append = TRUE)";
//								String trialSum = "capture.output(funcTrialSum,file=\"" + outFileName + "\",append = TRUE)";
//
//								rEngine.eval(funcTrialSum);
////								System.out.println(funcTrialSum);
//		
//								String runSuccessTS = rEngine.eval("class(funcTrialSum)").toString();
//								if (runSuccessTS != null && runSuccessTS.equals("try-error")) {	
//									System.out.println("class info: error");
//									String checkError = "msg <- trimStrings(strsplit(funcTrialSum, \":\")[[1]])";
//									String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//									String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//									String checkError4 = "capture.output(cat(\"*** \nERROR in class.information function:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//									rEngine.eval(checkError);
//									rEngine.eval(checkError2);
//									rEngine.eval(checkError3);
//									rEngine.eval(checkError4);
//								}
//								else {
//									rEngine.eval(trialSumHead);
//									rEngine.eval(trialObsRead);
//									rEngine.eval(trialObsUsed);
//									rEngine.eval(trialSum);
//									rEngine.eval(outspace);
//								}	
//								
//								// optional output: variance components
//								if (varianceComponents) {
//									String outVarComp = "capture.output(cat(\"\nVARIANCE COMPONENTS TABLE:\n\n\"),file=\"" + outFileName + "\",append = TRUE)";
//									String outVarComp2 = "capture.output(ssa1$output[[" + i + "]]$site[[" + j + "]]$varcomp.table,file=\"" + outFileName + "\",append = TRUE)";
//		
//									rEngine.eval(outVarComp);
//									rEngine.eval(outVarComp2);
//									rEngine.eval(outspace);
//								}
//		
//								//default output: test for genotypic effect
//								String outAnovaTable1 = "capture.output(cat(\"\nTESTING FOR THE SIGNIFICANCE OF GENOTYPIC EFFECT:\n\"),file=\"" + outFileName + "\",append = TRUE)";
//								String outAnovaTable2 = "library(lmerTest)";
//								String outAnovaTable3 = "model1b <- lmer(formula(ssa1$output[[" + i + "]]$site[[" + j + "]]$formula1), data = ssa1$output[[" + i + "]]$site[[" + j + "]]$data, REML = T)";
//								String outAnovaTable4 = "a.table <- anova(model1b)";
//								String outAnovaTable5 = "pvalue <- formatC(as.numeric(format(a.table[1,6], scientific=FALSE)), format=\"f\")";
//								String outAnovaTable6 = "a.table<-cbind(round(a.table[,1:5], digits=4),pvalue)";
//								String outAnovaTable7 = "colnames(a.table)<-c(\"Df\", \"Sum Sq\", \"Mean Sq\", \"F value\", \"Denom\", \"Pr(>F)\")";
//								String outAnovaTable8 = "capture.output(cat(\"Analysis of Variance Table with Satterthwaite Denominator Df\n\"),file=\"" + outFileName + "\",append = TRUE)";
//								String outAnovaTable9 = "capture.output(a.table,file=\"" + outFileName + "\",append = TRUE)";
//								String outAnovaTable10 = "detach(\"package:lmerTest\")";
//								
//								rEngine.eval(outAnovaTable1);
//								rEngine.eval(outAnovaTable2);
//								rEngine.eval(outAnovaTable3);
//								rEngine.eval(outAnovaTable4);
//								rEngine.eval(outAnovaTable5);
//								rEngine.eval(outAnovaTable6);
//								rEngine.eval(outAnovaTable7);
//								rEngine.eval(outspace);
//								rEngine.eval(outAnovaTable8);
//								rEngine.eval(outAnovaTable9);
//								rEngine.eval(outspace);
//								rEngine.eval(outAnovaTable10);
//								
//								// default output: test for genotypic effect
////								String outAnovaTable1b = "capture.output(cat(\"\nTESTING FOR THE SIGNIFICANCE OF GENOTYPIC EFFECT:\n\n\"),file=\"" + outFileName + "\",append = TRUE)";
////								String outAnovaTable2b = "library(pbkrtest)";
////								String outAnovaTable3b = "model1b <- lmer(formula(ssa1$output[[" + i + "]]$site[[" + j + "]]$formula1), data = ssa1$output[[" + i + "]]$site[[" + j + "]]$data, REML = T)";
////								String outAnovaTable4b = "model2b <- lmer(formula(ssa1$output[[" + i + "]]$site[[" + j + "]]$formula2), data = ssa1$output[[" + i + "]]$site[[" + j + "]]$data, REML = T)";
////								String outAnovaTable5b = "anova.table1 <- KRmodcomp(model1b, model2b)[[1]][1,]";
////								String outAnovaTable6b = "anova.table1 <- anova.table1[-c(4)]";
////								String outAnovaTable7b = "rownames(anova.table1) <- " + genotype;
////								String outAnovaTable8b = "colnames(anova.table1) <- c(\"F value\", \"Num df\", \"Denom df\", \"Pr(>F)\")";
////								String outAnovaTable9b = "anova.table1[1, \"F value\"] <- format(round(anova.table1[1, \"F value\"],2), digits=2, nsmall=2, scientific=FALSE)";
////								String outAnovaTable10b = "anova.table1[1, \"Pr(>F)\"] <- formatC(as.numeric(format(anova.table1[1, \"Pr(>F)\"], scientific=FALSE)), format=\"f\")";
////								String outAnovaTable11b = "capture.output(anova.table1,file=\"" + outFileName + "\",append = TRUE)";
////								String outAnovaTable12b = "detach(\"package:pbkrtest\")";
////								
////								rEngine.eval(outAnovaTable1b);
////								rEngine.eval(outAnovaTable2b);
////								rEngine.eval(outAnovaTable3b);
////								rEngine.eval(outAnovaTable4b);
////								rEngine.eval(outAnovaTable5b);
////								rEngine.eval(outAnovaTable6b);
////								rEngine.eval(outAnovaTable7b);
////								rEngine.eval(outAnovaTable8b);
////								rEngine.eval(outAnovaTable9b);
////								rEngine.eval(outAnovaTable10b);
////								rEngine.eval(outAnovaTable11b);
////								rEngine.eval(outAnovaTable12b);
////								rEngine.eval(outspace);
//								
////								String outAnovaTable1b = "capture.output(cat(\"\nTESTING FOR THE SIGNIFICANCE OF GENOTYPIC EFFECT:\n\n\"),file=\"" + outFileName + "\",append = TRUE)";
////								String outAnovaTable2b = "capture.output(ssa1$output[[" + i + "]]$site[[" + j + "]]$model.comparison,file=\"" + outFileName + "\",append = TRUE)";
////								
////								rEngine.eval(outAnovaTable1b);
////								rEngine.eval(outAnovaTable2b);
////								rEngine.eval(outspace);
//		
//								//default output: LSMeans
//								String outDescStat = "capture.output(cat(\"\nGENOTYPE LSMEANS AND STANDARD ERRORS:\n\n\"),file=\"" + outFileName + "\",append = TRUE)";
//								String outDescStat2 = "capture.output(ssa1$output[[" + i + "]]$site[[" + j + "]]$summary.statistic,file=\"" + outFileName + "\",append = TRUE)"; 
//		
//								rEngine.eval(outDescStat);
//								rEngine.eval(outDescStat2);
//								rEngine.eval(outspace);
//								
//								//default output: standard error of the differences
//								String outsedTable = "capture.output(cat(\"\nSTANDARD ERROR OF THE DIFFERENCE (SED):\n\n\"),file=\"" + outFileName + "\",append = TRUE)";
//								String outsedTable2 = "capture.output(ssa1$output[[" + i + "]]$site[[" + j + "]]$sedTable,file=\"" + outFileName + "\",append = TRUE)";
//
//								rEngine.eval(outsedTable);
//								rEngine.eval(outsedTable2);
//								rEngine.eval(outspace);
//							
//								
//								if (performPairwise) {
//									
//									double pairwiseSig = Double.valueOf(pairwiseAlpha);
//									
////									rEngine.rniAssign("trmt.levels",	rEngine.rniPutStringArray(genotypeLevels),0); // a string array from OptionsPage
//									
//										if (compareControl) {
////											rEngine.rniAssign("controlLevels",rEngine.rniPutStringArray(controlLevels),0); // a string array from OptionsPage
//																		
//											String funcPwC = "pwControl <- try(ssa.pairwise(ssa1$output[[" + i + "]]$site[["	+ j	+ "]]$model, type = \"Dunnett\", alpha = "	+ pairwiseSig + ", control.level = " + controlLevelsVector + "), silent=TRUE)";
//											String outCompareControl = "capture.output(cat(\"\nSIGNIFICANT PAIRWISE COMPARISONS (IF ANY): \nCompared with control(s)\n\n\"),file=\"" + outFileName	+ "\",append = TRUE)";
//											String outCompareControl2n = "capture.output(pwControl$result,file=\""	+ outFileName	+ "\",append = TRUE)";
//											String outCompareControl3n = "capture.output(cat(\"\n\n\"),file=\"" + outFileName	+ "\",append = TRUE)";
//											System.out.println(funcPwC);
//											rEngine.eval(funcPwC);
//											rEngine.eval(outCompareControl);
//											
//											
//					  						String runSuccessPwC = rEngine.eval("class(pwControl)").toString();	
//											if (runSuccessPwC != null && runSuccessPwC.equals("try-error")) {	
//												System.out.println("compare with control: error");
//												String checkError = "msg <- trimStrings(strsplit(pwControl, \":\")[[1]])";
//												String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//												String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//												String checkError4 = "capture.output(cat(\"*** \nERROR in ssa.pairwise function:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//												rEngine.eval(checkError);
//												rEngine.eval(checkError2);
//												rEngine.eval(checkError3);
//												rEngine.eval(checkError4);
//											}
//											else {
//												
//												rEngine.eval(outCompareControl2n);
//												
//												// display warning generated by checkTest in ssa.test
//												String warningControlTest = rEngine.eval("pwControl$controlTestWarning").toString();
//												
//												if (!warningControlTest.equals("NONE")) {
//													String warningCheckTest2 = "capture.output(cat(\"----- \nNOTE:\\n\"), file=\"" + outFileName + "\",append = TRUE)";
//													String warningCheckTest3 = "capture.output(cat(\"" + warningControlTest + "\\n\"), file=\"" + outFileName + "\",append = TRUE)";
//													
//													rEngine.eval(warningCheckTest2);
//													rEngine.eval(warningCheckTest3);
//												}
//												
//												rEngine.eval(outCompareControl3n);
//												
//												System.out.println("pairwise control test:" + warningControlTest); 
//												
//											}
//										} else if (performAllPairwise) {
//											String outPerformAllPairwise = "capture.output(cat(\"\nSIGNIFICANT PAIRWISE COMPARISONS (IF ANY): \n\n\"),file=\""	+ outFileName	+ "\",append = TRUE)";
//											rEngine.eval(outPerformAllPairwise);
//											if (genotypeLevels.length > 0 && genotypeLevels.length < 16) {
//												String funcPwAll = "pwAll <- try(ssa.pairwise(ssa1$output[[" + i + "]]$site[[" + j + "]]$model, type = \"Tukey\", alpha = "+ pairwiseSig + ", control.level = NULL), silent=TRUE)";
//												String outPerformAllPairwise2 = "capture.output(pwAll$result,file=\"" + outFileName + "\",append = TRUE)";
//												String outPerformAllPairwise3 = "capture.output(cat(\"\n\"),file=\""	+ outFileName	+ "\",append = TRUE)";
//												rEngine.eval(funcPwAll);
////												System.out.println(funcPwAll);
//		
//												String runSuccessPwAll = rEngine.eval("class(pwAll)").toString();
//												if (runSuccessPwAll != null && runSuccessPwAll.equals("try-error")) {	
//													System.out.println("all pairwise: error");
//													String checkError = "msg <- trimStrings(strsplit(pwAll, \":\")[[1]])";
//													String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//													String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//													String checkError4 = "capture.output(cat(\"*** \nERROR in ssa.pairwise function:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//													rEngine.eval(checkError);
//													rEngine.eval(checkError2);
//													rEngine.eval(checkError3);
//													rEngine.eval(checkError4);
//												}
//												else {
//													rEngine.eval(outPerformAllPairwise2);
//													rEngine.eval(outPerformAllPairwise3);
//												}
//											} else {
//												String nLevelsLarge = "capture.output(cat(\"***\nExceeded maximum number of genotypes that can be compared. \n***\n\n\"),file=\"" + outFileName + "\",append = TRUE)";
//												rEngine.eval(nLevelsLarge);
//											}
//										}
//								} else {
//									rEngine.eval(outspace);
//								}
//							}
//							
//						} // end of for loop for diff envts
//					}
//
//					//default output: save the means, standard error of the mean, variance and no. of reps in a file
//					String checkMeanSSE = rEngine.eval("ssa1$meansseWarning").toString();
//					String checkVarRep = rEngine.eval("ssa1$varrepWarning").toString();
//					System.out.println("checkMeanSSE: " + checkMeanSSE);
//					System.out.println("checkVarRep: " + checkVarRep);
//					
//					if (checkMeanSSE.equals("empty") | checkVarRep.equals("empty")) {
//						System.out.println("Saving means not done.");
//					} else {
//						String meansFileName = "meansFileName <- paste(\"" + resultFolderPath + "\",\"summaryStats.csv\", sep=\"\")";
//						String funcSaveSesVarRep=null;
//						if (environment=="NULL") {
//							funcSaveSesVarRep = "meansVar <- merge(ssa1$meansse,ssa1$varrep, by = \"EnvLevel\")";
//						} else {
//							funcSaveSesVarRep = "meansVar <- merge(ssa1$meansse,ssa1$varrep, by = \"" + environment + "\")";
//						}
//						String funcSaveSesVarRepCsv = "saveMeans <- try(write.table(meansVar,file = meansFileName ,sep=\",\",row.names=FALSE), silent=TRUE)";
//
//						rEngine.eval(meansFileName);
//						rEngine.eval(funcSaveSesVarRep);
//						rEngine.eval(funcSaveSesVarRepCsv);
//						
//						String runSuccessSaveMeansSes = rEngine.eval("class(saveMeans)").toString();
//						if (runSuccessSaveMeansSes != null && runSuccessSaveMeansSes.equals("try-error")) {	
//							System.out.println("saving means file: error");
//							String checkError = "msg <- trimStrings(strsplit(saveMeans, \":\")[[1]])";
//							String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//							String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//							String checkError4 = "capture.output(cat(\"*** \nERROR in saving means file:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//							rEngine.eval(checkError);
//							rEngine.eval(checkError2);
//							rEngine.eval(checkError3);
//							rEngine.eval(checkError4);
//						} 
//					}
//					
//					//diagnostic plots for genotype fixed
//					if (diagnosticPlot) {
//						String diagPlotsFunc=null;
//						if (environment=="NULL") {
//							diagPlotsFunc = "diagPlots <- try(graph.sea.diagplots(data, " + respvarVector + ", env = " + environment + ", is.random = FALSE, ssa1), silent=TRUE)";
//						} else {
//							diagPlotsFunc = "diagPlots <- try(graph.sea.diagplots(data, " + respvarVector + ", env = \"" + environment + "\", is.random = FALSE, ssa1), silent=TRUE)";
//						}
//						System.out.println(diagPlotsFunc);
//						rEngine.eval(diagPlotsFunc);
//						
//						String runSuccessDiagPlots = rEngine.eval("class(diagPlots)").toString();
//						if (runSuccessDiagPlots != null && runSuccessDiagPlots.equals("try-error")) {	
//							System.out.println("diagnostic plots(genotype fixed): error");
//							String checkError = "msg <- trimStrings(strsplit(diagPlots, \":\")[[1]])";
//							String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//							String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//							String checkError4 = "capture.output(cat(\"*** \nERROR in graph.sea.diagplots function:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//							rEngine.eval(checkError);
//							rEngine.eval(checkError2);
//							rEngine.eval(checkError3);
//							rEngine.eval(checkError4);
//						}
//					}
//				}  
//			} // end of if fixed
//				
//  
//			// Genotype Random
//			if (genotypeRandom == true) {
//				String funcSsaRandom = null;
//				String groupVars = null;
//
//				if (excludeControls) {
//					if (environment == "NULL") {
//						if (designIndex == 0){
//							funcSsaRandom = "ssa2 <- try(ssa.test(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + block + "\",column=NULL, rep=NULL," + environment + ", is.random = TRUE, excludeCheck=TRUE, checkList= " + controlLevelsVector + "), silent=TRUE)";
//							groupVars = "c(\"" + genotype + "\", \"" + block + "\")";
//						} else if (designIndex == 1){
//							funcSsaRandom = "ssa2 <- try(ssa.test(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + block + "\",column=NULL, rep=NULL," + environment + ", is.random = TRUE, excludeCheck=TRUE, checkList= NULL), silent=TRUE)";
//							groupVars = "c(\"" + genotype + "\", \"" + block + "\")";
//						} else if (designIndex == 2) {
//							funcSsaRandom = "ssa2 <- try(ssa.test(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + row + "\",\"" + column + "\", rep=NULL," + environment + ", is.random = TRUE, excludeCheck=TRUE, checkList= NULL), silent=TRUE)";
//							groupVars = "c(\"" + genotype + "\", \"" + row + "\", \"" + column + "\")";
//						} else if (designIndex == 3 || designIndex == 5) {
//							funcSsaRandom = "ssa2 <- try(ssa.test(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + block + "\",column=NULL,\"" + rep + "\"," + environment + ", is.random = TRUE, excludeCheck=TRUE, checkList= " + controlLevelsVector + "), silent=TRUE)";
//							groupVars = "c(\"" + genotype + "\", \"" + block + "\", \"" + rep + "\")";
//						} else if (designIndex == 4 || designIndex == 6) {
//							funcSsaRandom = "ssa2 <- try(ssa.test(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + row + "\",\"" + column + "\",\"" + rep + "\"," + environment + ", is.random = TRUE, excludeCheck=TRUE, checkList= " + controlLevelsVector + "), silent=TRUE)";
//							groupVars = "c(\"" + genotype + "\", \"" + rep + "\", \"" + row + "\", \"" + column + "\")";
//						}
//					} else {
//						if (designIndex == 0){
//							funcSsaRandom = "ssa2 <- try(ssa.test(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + block + "\",column=NULL, rep=NULL,\"" + environment + "\", is.random = TRUE, excludeCheck=TRUE, checkList= " + controlLevelsVector + "), silent=TRUE)";
//							groupVars = "c(\"" + genotype + "\", \"" + block + "\")";
//						} else if (designIndex == 1) {
//							funcSsaRandom = "ssa2 <- try(ssa.test(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + block + "\",column=NULL, rep=NULL,\"" + environment + "\", is.random = TRUE, excludeCheck=TRUE, checkList= NULL), silent=TRUE)";
//							groupVars = "c(\"" + genotype + "\", \"" + block + "\")";
//						} else if (designIndex == 2) {
//							funcSsaRandom = "ssa2 <- try(ssa.test(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + row + "\",\"" + column + "\", rep=NULL,\"" + environment + "\", is.random = TRUE, excludeCheck=TRUE, checkList= NULL), silent=TRUE)";
//							groupVars = "c(\"" + genotype + "\", \"" + row + "\", \"" + column + "\")";
//						} else if (designIndex == 3 || designIndex == 5) {
//							funcSsaRandom = "ssa2 <- try(ssa.test(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + block + "\",column=NULL,\"" + rep + "\",\"" + environment + "\", is.random = TRUE, excludeCheck=TRUE, checkList= " + controlLevelsVector + "), silent=TRUE)";
//							groupVars = "c(\"" + genotype + "\", \"" + block + "\", \"" + rep + "\")";
//						} else if (designIndex == 4 || designIndex == 6) {
//							funcSsaRandom = "ssa2 <- try(ssa.test(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + row + "\",\"" + column + "\",\"" + rep + "\",\"" + environment + "\", is.random = TRUE, excludeCheck=TRUE, checkList= " + controlLevelsVector + "), silent=TRUE)";
//							groupVars = "c(\"" + genotype + "\", \"" + rep + "\", \"" + row + "\", \"" + column + "\")";
//						}
//					}
//				} else {
//					if (environment == "NULL") {
//						if (designIndex == 0 || designIndex == 1) {
//							funcSsaRandom = "ssa2 <- try(ssa.test(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + block + "\",column=NULL, rep=NULL," + environment + ", is.random = TRUE), silent=TRUE)";
//							groupVars = "c(\"" + genotype + "\", \"" + block + "\")";
//						} else if (designIndex == 2) {
//							funcSsaRandom = "ssa2 <- try(ssa.test(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + row + "\",\"" + column + "\", rep=NULL," + environment + ", is.random = TRUE), silent=TRUE)";
//							groupVars = "c(\"" + genotype + "\", \"" + row + "\", \"" + column + "\")";
//						} else if (designIndex == 3 || designIndex == 5) {
//							funcSsaRandom = "ssa2 <- try(ssa.test(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + block + "\",column=NULL,\"" + rep + "\"," + environment + ", is.random = TRUE), silent=TRUE)";
//							groupVars = "c(\"" + genotype + "\", \"" + block + "\", \"" + rep + "\")";
//						} else if (designIndex == 4 || designIndex == 6) {
//							funcSsaRandom = "ssa2 <- try(ssa.test(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + row + "\",\"" + column + "\",\"" + rep + "\"," + environment + ", is.random = TRUE), silent=TRUE)";
//							groupVars = "c(\"" + genotype + "\", \"" + rep + "\", \"" + row + "\", \"" + column + "\")";
//						}
//					} else {
//						if (designIndex == 0 || designIndex == 1) {
//							funcSsaRandom = "ssa2 <- try(ssa.test(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + block + "\",column=NULL, rep=NULL,\"" + environment + "\", is.random = TRUE), silent=TRUE)";
//							groupVars = "c(\"" + genotype + "\", \"" + block + "\")";
//						} else if (designIndex == 2) {
//							funcSsaRandom = "ssa2 <- try(ssa.test(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + row + "\",\"" + column + "\", rep=NULL,\"" + environment + "\", is.random = TRUE), silent=TRUE)";
//							groupVars = "c(\"" + genotype + "\", \"" + row + "\", \"" + column + "\")";
//						} else if (designIndex == 3 || designIndex == 5) {
//							funcSsaRandom = "ssa2 <- try(ssa.test(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + block + "\",column=NULL,\"" + rep + "\",\"" + environment + "\", is.random = TRUE), silent=TRUE)";
//							groupVars = "c(\"" + genotype + "\", \"" + block + "\", \"" + rep + "\")";
//						} else if (designIndex == 4 || designIndex == 6) {
//							funcSsaRandom = "ssa2 <- try(ssa.test(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + row + "\",\"" + column + "\",\"" + rep + "\",\"" + environment + "\", is.random = TRUE), silent=TRUE)";
//							groupVars = "c(\"" + genotype + "\", \"" + rep + "\", \"" + row + "\", \"" + column + "\")";
//						}
//					}
//				}
//				String randomHead = "capture.output(cat(\"GENOTYPE AS: Random\n\"),file=\"" + outFileName + "\",append = TRUE)";
//				rEngine.eval(funcSsaRandom);
//				rEngine.eval(sep2);
//				rEngine.eval(randomHead);
//				rEngine.eval(sep2);
//				rEngine.eval(outspace);
//				System.out.println(funcSsaRandom);
//				
//				String runSuccess2 = rEngine.eval("class(ssa2)").toString();	
//				if (runSuccess2 != null && runSuccess2.equals("try-error")) {	
//					System.out.println("ssa2: error");
//					String checkError = "msg <- trimStrings(strsplit(ssa2, \":\")[[1]])";
//					String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//					String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//					String checkError4 = "capture.output(cat(\"*** \nERROR in ssa.test function:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//					rEngine.eval(checkError);
//					rEngine.eval(checkError2);
//					rEngine.eval(checkError3);
//					rEngine.eval(checkError4);
//					
//					runningRandomSuccess=false;
//				}
//				else {
//
//				for (int k = 0; k < respvar.length; k++) {
//					int i = k + 1; // 1-relative index;
//					String respVarHead = "capture.output(cat(\"\nRESPONSE VARIABLE: " + respvar[k] + "\n\"),file=\"" + outFileName + "\",append = TRUE)";
//					rEngine.eval(sep);
//					rEngine.eval(respVarHead);
//					rEngine.eval(sep);
//					rEngine.eval(outspace);
//					
//					// optional output: descriptive statistics
//					if (descriptiveStat) {
//						String funcDesc = null;
//						if (environment == "NULL") {
//							funcDesc = "outDesc <- try(DescriptiveStatistics(data, \"" + respvar[k] + "\", " + environment + "), silent=TRUE)";
//						} else {
//							funcDesc = "outDesc <- try(DescriptiveStatistics(data, \"" + respvar[k] + "\", \"" + environment + "\"), silent=TRUE)";
//						}
//						rEngine.eval(funcDesc);
//						System.out.println(funcDesc);
//						String outDescStat = "capture.output(cat(\"DESCRIPTIVE STATISTICS:\n\n\"),file=\"" + outFileName + "\",append = TRUE)";
//						String outDescStat2 = "capture.output(outDesc,file=\"" + outFileName + "\",append = TRUE)"; 
//
//  						String runSuccessDescStat = rEngine.eval("class(outDesc)").toString();
//						if (runSuccessDescStat != null && runSuccessDescStat.equals("try-error")) {	
//							System.out.println("desc stat: error");
//							String checkError = "msg <- trimStrings(strsplit(outDesc, \":\")[[1]])";
//							String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//							String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//							String checkError4 = "capture.output(cat(\"*** \nERROR in DescriptiveStatistics function:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//							rEngine.eval(checkError);
//							rEngine.eval(checkError2);
//							rEngine.eval(checkError3);
//							rEngine.eval(checkError4);
//						} 
//						else {
//							rEngine.eval(outspace);
//							rEngine.eval(outDescStat);
//							rEngine.eval(outDescStat2);
//							rEngine.eval(outspace);
//						}	
//					}
//					int envLevelsLength2 = 0;
//					if (environment == "NULL") {
//						envLevelsLength2 = 1;
//					} else {
//						envLevelsLength2 = environmentLevels.length;
//					}
//					for (int m = 0; m < envLevelsLength2; m++) { // no of envts or sites
//						printAllOutputRandom=true;
//						int j = m + 1; // 1-relative index;
//						
//						if (environment != "NULL") {
//							String envtHead = "capture.output(cat(\"\nANALYSIS FOR: "+ environment + "\", \" = \" ,ssa2$output[[" + i	+ "]]$site[[" + j + "]]$env,\"\n\"),file=\""+ outFileName + "\",append = TRUE)";
//							rEngine.eval(sep);
//							rEngine.eval(envtHead);
//							rEngine.eval(sep);
//							rEngine.eval(outspace);
//						}
//						
//						//check if the data has too many missing observations
//						double responseRate=rEngine.eval("ssa2$output[[" + i + "]]$site[[" + j + "]]$responseRate").asDouble();
//						if (responseRate < 0.8) {
//							String allNAWarning2 = rEngine.eval("ssa2$output[[" + i + "]]$site[[" + j + "]]$manyNAWarning").toString();
//							String printError1 = "capture.output(cat(\"***\\n\"), file=\"" + outFileName + "\",append = TRUE)";
//							String printError2 = "capture.output(cat(\"ERROR:\\n\"), file=\"" + outFileName + "\",append = TRUE)";
//							String printError3 = "capture.output(cat(\"" + allNAWarning2 + "\\n\"), file=\"" + outFileName + "\",append = TRUE)";
//							
//							rEngine.eval(outspace);
//							rEngine.eval(printError1);
//							rEngine.eval(printError2);
//							rEngine.eval(printError3);
//							rEngine.eval(printError1);
//							rEngine.eval(outspace);
//							rEngine.eval(outspace);
//							printAllOutputRandom=false;
//						} else {
//							String lmerRun=rEngine.eval("ssa2$output[[" + i + "]]$site[[" + j + "]]$lmerRun").toString();
//							if (lmerRun.equals("ERROR")) {
//								String lmerError = rEngine.eval("ssa2$output[[" + i + "]]$site[[" + j + "]]$lmerError").toString();
//								String printError1 = "capture.output(cat(\"***\\n\"), file=\"" + outFileName + "\",append = TRUE)";
//								String printError2 = "capture.output(cat(\"ERROR:\\n\"), file=\"" + outFileName + "\",append = TRUE)";
//								String printError3 = "capture.output(cat(\"" + lmerError + "\\n\"), file=\"" + outFileName + "\",append = TRUE)";
//								
//								rEngine.eval(outspace);
//								rEngine.eval(printError1);
//								rEngine.eval(printError2);
//								rEngine.eval(printError3);
//								rEngine.eval(printError1);
//								rEngine.eval(outspace);
//								rEngine.eval(outspace);
//								printAllOutputRandom=false;
//							}
//						}
//						
//						if (printAllOutputRandom) {
//							// display warning generated by checkTest in ssa.test
//							String warningCheckTest = rEngine.eval("ssa2$output[[" + i	+ "]]$site[[" + j + "]]$checkTestWarning").toString();
//							
//							if (!warningCheckTest.equals("NONE")) {
//								String warningCheckTest2 = "capture.output(cat(\"\n*** \nWARNING:\\n\"), file=\"" + outFileName + "\",append = TRUE)";
//								String warningCheckTest3 = "capture.output(cat(\"" + warningCheckTest + "\"), file=\"" + outFileName + "\",append = TRUE)";
//								String warningCheckTest4 = "capture.output(cat(\"\n*** \\n\"), file=\"" + outFileName + "\",append = TRUE)";
//								
//								rEngine.eval(warningCheckTest2);
//								rEngine.eval(warningCheckTest3);
//								rEngine.eval(warningCheckTest4);
//							} 
//							System.out.println("check test:" + warningCheckTest);
//							
//							// default output: trial summary
//							String funcTrialSum = "funcTrialSum <- try(class.information(" + groupVars + ",ssa2$output[[" + i + "]]$site[[" + j + "]]$data), silent=TRUE)";
//							String trialSumHead = "capture.output(cat(\"\nDATA SUMMARY:\n\n\"),file=\"" + outFileName + "\",append = TRUE)";
//							String trialObsRead = "capture.output(cat(\"Number of observations read: \", ssa2$output[["	+ i	+ "]]$site[[" + j + "]]$obsread[[1]],\"\n\"),file=\"" + outFileName + "\",append = TRUE)";
//							String trialObsUsed = "capture.output(cat(\"Number of observations used: \", ssa2$output[["	+ i	+ "]]$site[[" + j + "]]$obsused[[1]],\"\n\n\"),file=\""	+ outFileName + "\",append = TRUE)";
//							String trialSum = "capture.output(funcTrialSum,file=\"" + outFileName + "\",append = TRUE)";
//
//							rEngine.eval(funcTrialSum);
//
//							String runSuccessTS = rEngine.eval("class(funcTrialSum)").toString();
//							if (runSuccessTS != null && runSuccessTS.equals("try-error")) {	
//								System.out.println("class info: error");
//								String checkError = "msg <- trimStrings(strsplit(funcTrialSum, \":\")[[1]])";
//								String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//								String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//								String checkError4 = "capture.output(cat(\"*** \nERROR in class.information function:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//								rEngine.eval(checkError);
//								rEngine.eval(checkError2);
//								rEngine.eval(checkError3);
//								rEngine.eval(checkError4);
//							}
//							else {
//								rEngine.eval(trialSumHead);
//								rEngine.eval(trialObsRead);
//								rEngine.eval(trialObsUsed);
//								rEngine.eval(trialSum);
//								rEngine.eval(outspace);
//							}
//
//							// optional output: variance components
//							if (varianceComponents) {
//								String outVarComp = "capture.output(cat(\"\nVARIANCE COMPONENTS TABLE:\n\n\"),file=\"" + outFileName + "\",append = TRUE)";
//								String outVarComp2 = "capture.output(ssa2$output[[" + i + "]]$site[[" + j + "]]$varcomp.table,file=\"" + outFileName + "\",append = TRUE)";
//
//								rEngine.eval(outVarComp);
//								rEngine.eval(outVarComp2);
//								rEngine.eval(outspace);
//							}
//							
//							//default output: test genotypic effect
//							String outTestGen1 = "capture.output(cat(\"\nTESTING FOR THE SIGNIFICANCE OF GENOTYPIC EFFECT USING -2 LOGLIKELIHOOD RATIO TEST:\n\"),file=\"" + outFileName + "\",append = TRUE)";
//							String outTestGen2 = "capture.output(cat(\"\nFormula for Model1: \", ssa2$output[["	+ i	+ "]]$site[[" + j + "]]$formula1,\"\n\"),file=\"" + outFileName + "\",append = TRUE)";
//							String outTestGen3 = "capture.output(cat(\"Formula for Model2: \", ssa2$output[["	+ i	+ "]]$site[[" + j + "]]$formula2,\"\n\n\"),file=\"" + outFileName + "\",append = TRUE)";
//							String outTestGen4 = "capture.output(ssa2$output[[" + i + "]]$site[[" + j + "]]$models.table,file=\"" + outFileName + "\",append = TRUE)";
//							rEngine.eval(outTestGen1);
//							rEngine.eval(outTestGen2);
//							rEngine.eval(outTestGen3);
//							rEngine.eval(outTestGen4);
//							rEngine.eval(outspace);
//							
//							//default output: test for check effect
//							String newExcludeCheck = rEngine.eval("ssa2$output[[" + i + "]]$site[[" + j + "]]$newExcludeCheck").toString();
//							System.out.println("newExcludeCheck: " + newExcludeCheck);
//							
//							if (newExcludeCheck.equals("TRUE")) {
//								String outAnovaTable1 = "capture.output(cat(\"\nTESTING FOR THE SIGNIFICANCE OF CHECK EFFECT:\n\"),file=\"" + outFileName + "\",append = TRUE)";
//								String outAnovaTable2 = "library(lmerTest)";
//								String outAnovaTable3 = "model2b <- lmer(formula(ssa2$output[[" + i + "]]$site[[" + j + "]]$formula1), data = ssa2$output[[" + i + "]]$site[[" + j + "]]$data, REML = T)";
//								String outAnovaTable4 = "a.table <- anova(model2b)";
//								String outAnovaTable5 = "pvalue <- formatC(as.numeric(format(a.table[1,6], scientific=FALSE)), format=\"f\")";
//								String outAnovaTable6 = "a.table<-cbind(round(a.table[,1:5], digits=4),pvalue)";
//								String outAnovaTable7 = "colnames(a.table)<-c(\"Df\", \"Sum Sq\", \"Mean Sq\", \"F value\", \"Denom\", \"Pr(>F)\")";
//								String outAnovaTable8 = "capture.output(cat(\"Analysis of Variance Table with Satterthwaite Denominator Df\n\"),file=\"" + outFileName + "\",append = TRUE)";
//								String outAnovaTable9 = "capture.output(a.table,file=\"" + outFileName + "\",append = TRUE)";
//								String outAnovaTable10 = "detach(\"package:lmerTest\")";
//								
////								rEngine.eval(outspace);
//								rEngine.eval(outAnovaTable1);
//								rEngine.eval(outAnovaTable2);
//								rEngine.eval(outAnovaTable3);
//								rEngine.eval(outAnovaTable4);
//								rEngine.eval(outAnovaTable5);
//								rEngine.eval(outAnovaTable6);
//								rEngine.eval(outAnovaTable7);
//								rEngine.eval(outspace);
//								rEngine.eval(outAnovaTable8);
//								rEngine.eval(outAnovaTable9);
//								rEngine.eval(outspace);
//								rEngine.eval(outAnovaTable10);
//							}
//							
//							//default output: predicted means
//							String outPredMeans = "capture.output(cat(\"\nPREDICTED MEANS:\n\n\"),file=\"" + outFileName + "\",append = TRUE)";
//							String outPredMeans2 = "capture.output(ssa2$output[[" + i + "]]$site[[" + j + "]]$summary.statistic,file=\"" + outFileName + "\",append = TRUE)"; 
//							rEngine.eval(outPredMeans);
//							rEngine.eval(outPredMeans2);
//							rEngine.eval(outspace);
//							
//							//default output: lsmeans of checks
//							if (excludeControls) {
//								int newCheckListLength = rEngine.eval("ssa2$output[[" + i	+ "]]$site[[" + j + "]]$newCheckListLength").asInteger();
//								
//								if (newCheckListLength > 0) {
//									String outLSMeansCheck = "capture.output(cat(\"\nCHECK/CONTROL LSMEANS:\n\n\"),file=\"" + outFileName + "\",append = TRUE)";
//									String outLSMeansCheck2 = "capture.output(ssa2$output[[" + i + "]]$site[[" + j + "]]$lsmeans.checks,file=\"" + outFileName + "\",append = TRUE)"; 
//									rEngine.eval(outLSMeansCheck);
//									rEngine.eval(outLSMeansCheck2);
//									rEngine.eval(outspace);
//								}
//							}
//
//							//default output: estimate heritability
//							String outEstHerit = "capture.output(cat(\"\nHERITABILITY:\n\"),file=\"" + outFileName + "\",append = TRUE)";
//							String outEstHerit2 = "capture.output(ssa2$output[[" + i + "]]$site[[" + j + "]]$heritability,file=\""	+ outFileName + "\",append = TRUE)";
//							String outEstHerit3 = "capture.output(cat(\"\n\"),file=\"" + outFileName + "\",append = TRUE)";
//							rEngine.eval(outEstHerit);
//							rEngine.eval(outEstHerit2);
//							rEngine.eval(outEstHerit3);
//							rEngine.eval(outspace);
//						}
//						
//					}
//				}
//				
//				//optional output: estimate genotypic and phenotypic correlations
//				if (genoPhenoCorrelation) {
//					rEngine.eval(sep2);
//					String funcEstCorr = null;
//					if (excludeControls) {
//						if (environment == "NULL") {
//							if (designIndex == 0)
//								funcEstCorr = "gpcorr <- try(genoNpheno.corr(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + block + "\",column=NULL, rep=NULL," + environment + ", excludeLevels=TRUE, excludeList = " + controlLevelsVector + "), silent=TRUE)";
//							else if (designIndex == 1)
//								funcEstCorr = "gpcorr <- try(genoNpheno.corr(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + block + "\",column=NULL, rep=NULL," + environment + ", excludeLevels=TRUE, excludeList = NULL), silent=TRUE)";
//							else if (designIndex == 2)
//								funcEstCorr = "gpcorr <- try(genoNpheno.corr(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + row + "\",\"" + column + "\", rep=NULL," + environment + ", excludeLevels=TRUE, excludeList = NULL), silent=TRUE)";
//							else if (designIndex == 3)
//								funcEstCorr = "gpcorr <- try(genoNpheno.corr(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + block + "\",column=NULL,\"" + rep + "\"," + environment + ", excludeLevels=TRUE, excludeList = " + controlLevelsVector + "), silent=TRUE)";
//							else if (designIndex == 4)
//								funcEstCorr = "gpcorr <- try(genoNpheno.corr(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + row + "\",\"" + column + "\",\"" + rep + "\"," + environment + ", excludeLevels=TRUE, excludeList = " + controlLevelsVector + "), silent=TRUE)";
//						} else {
//							if (designIndex == 0)
//								funcEstCorr = "gpcorr <- try(genoNpheno.corr(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + block + "\",column=NULL, rep=NULL,\"" + environment + "\", excludeLevels=TRUE, excludeList = " + controlLevelsVector + "), silent=TRUE)";
//							else if (designIndex == 1)
//								funcEstCorr = "gpcorr <- try(genoNpheno.corr(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + block + "\",column=NULL, rep=NULL,\"" + environment + "\", excludeLevels=TRUE, excludeList = NULL), silent=TRUE)";
//							else if (designIndex == 2)
//								funcEstCorr = "gpcorr <- try(genoNpheno.corr(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + row + "\",\"" + column + "\", rep=NULL,\"" + environment + "\", excludeLevels=TRUE, excludeList = NULL), silent=TRUE)";
//							else if (designIndex == 3)
//								funcEstCorr = "gpcorr <- try(genoNpheno.corr(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + block + "\",column=NULL,\"" + rep + "\",\"" + environment + "\", excludeLevels=TRUE, excludeList = " + controlLevelsVector + "), silent=TRUE)";
//							else if (designIndex == 4)
//								funcEstCorr = "gpcorr <- try(genoNpheno.corr(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + row + "\",\"" + column + "\",\"" + rep + "\",\"" + environment + "\", excludeLevels=TRUE, excludeList = " + controlLevelsVector + "), silent=TRUE)";
//						}
//					} else {
//						if (environment == "NULL") {
//							if (designIndex == 0 || designIndex == 1)
//								funcEstCorr = "gpcorr <- try(genoNpheno.corr(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + block + "\",column=NULL, rep=NULL," + environment + "), silent=TRUE)";
//							else if (designIndex == 2)
//								funcEstCorr = "gpcorr <- try(genoNpheno.corr(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + row + "\",\"" + column + "\", rep=NULL," + environment + "), silent=TRUE)";
//							else if (designIndex == 3)
//								funcEstCorr = "gpcorr <- try(genoNpheno.corr(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + block + "\",column=NULL,\"" + rep + "\"," + environment + "), silent=TRUE)";
//							else if (designIndex == 4)
//								funcEstCorr = "gpcorr <- try(genoNpheno.corr(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + row + "\",\"" + column + "\",\"" + rep + "\"," + environment + "), silent=TRUE)";
//						} else {
//							if (designIndex == 0 || designIndex == 1)
//								funcEstCorr = "gpcorr <- try(genoNpheno.corr(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + block + "\",column=NULL, rep=NULL,\"" + environment + "\"), silent=TRUE)";
//							else if (designIndex == 2)
//								funcEstCorr = "gpcorr <- try(genoNpheno.corr(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + row + "\",\"" + column + "\", rep=NULL,\"" + environment + "\"), silent=TRUE)";
//							else if (designIndex == 3)
//								funcEstCorr = "gpcorr <- try(genoNpheno.corr(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + block + "\",column=NULL,\"" + rep + "\",\"" + environment + "\"), silent=TRUE)";
//							else if (designIndex == 4)
//								funcEstCorr = "gpcorr <- try(genoNpheno.corr(\"" + design + "\",data," + respvarVector + ",\"" + genotype + "\",\"" + row + "\",\"" + column + "\",\"" + rep + "\",\"" + environment + "\"), silent=TRUE)";
//						}
//					}
//
//					System.out.println(funcEstCorr);
//					rEngine.eval(funcEstCorr);	
//					
//					String runSuccessGPCorr = rEngine.eval("class(gpcorr)").toString();
//					if (runSuccessGPCorr != null && runSuccessGPCorr.equals("try-error")) {	
//						System.out.println("geno pheno corr: error");
//						String checkError = "msg <- trimStrings(strsplit(gpcorr, \":\")[[1]])";
//						String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//						String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//						String checkError4 = "capture.output(cat(\"*** \nERROR in genoNpheno.corr function:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//						rEngine.eval(checkError);
//						rEngine.eval(checkError2);
//						rEngine.eval(checkError3);
//						rEngine.eval(checkError4);
//					}
//					else {
//						String outEstGenoCorr = "capture.output(cat(\"\nGENOTYPIC CORRELATIONS:\n\"),file=\"" + outFileName + "\",append = TRUE)";
//						rEngine.eval(outEstGenoCorr);
//						
//						int envLevelsLength = 0;
//						if (environment == "NULL") {
//							envLevelsLength = 1;
//						} else {
//							envLevelsLength = environmentLevels.length;
//						}
//					
//						for (int m = 0; m < envLevelsLength; m++) { // no of envts or sites
//							int j = m + 1; // 1-relative index;
//							if (environment != "NULL") {
//								String outEstGenoCorr2 = "capture.output(cat(\"" + environment + " = \", gpcorr$EnvLevels[[" + j + "]]),file=\"" + outFileName + "\",append = TRUE)";
//								rEngine.eval(outspace);
//								rEngine.eval(outEstGenoCorr2);
//								rEngine.eval(outspace);
//							}
//							String outEstGenoCorr2b = "capture.output(gpcorr$GenoCorr[[" + j + "]],file=\"" + outFileName + "\",append = TRUE)";
//							rEngine.eval(outspace);
//							rEngine.eval(outEstGenoCorr2b);
//							rEngine.eval(outspace);
//						}
//					
//						String outEstPhenoCorr = "capture.output(cat(\"\nPHENOTYPIC CORRELATIONS:\n\"),file=\"" + outFileName + "\",append = TRUE)";
//						rEngine.eval(outEstPhenoCorr);
//						
//						for (int m = 0; m < envLevelsLength; m++) { // no of envts or sites
//							int j = m + 1; // 1-relative index;
//							if (environment != "NULL") {
//								String outEstPhenoCorr2 = "capture.output(cat(\"" + environment + " = \", gpcorr$EnvLevels[[" + j + "]]),file=\"" + outFileName + "\",append = TRUE)";
//								rEngine.eval(outspace);
//								rEngine.eval(outEstPhenoCorr2);
//								rEngine.eval(outspace);
//							}
//							String outEstPhenoCorr2b = "capture.output(gpcorr$PhenoCorr[[" + j + "]],file=\"" + outFileName + "\",append = TRUE)";
//							rEngine.eval(outspace);
//							rEngine.eval(outEstPhenoCorr2b);
//							rEngine.eval(outspace);
//						}
//					} //end of else for if runSuccessGPCorr	
//				}
//				
//				//default option: save predicted means to a file
//				String checkPredMean = rEngine.eval("ssa2$meansWarning").toString();
//				System.out.println("checkPredMean: " + checkPredMean);
//				
//				if (checkPredMean.equals("empty")) {
//					System.out.println("Saving predicted means not done.");
//				} else {
//					String meansFileName2 = "meansFileName2 <- paste(\"" + resultFolderPath + "\",\"predictedMeans.csv\", sep=\"\")";
//					String funcSavePredMeansCsv = "saveDataB1 <- try(write.table(ssa2$means,file = meansFileName2 ,sep=\",\",row.names=FALSE), silent=TRUE)";
//					rEngine.eval(meansFileName2);
//					rEngine.eval(funcSavePredMeansCsv);
//					
//					String runSuccessSavePredMeans = rEngine.eval("class(saveDataB1)").toString();
//					if (runSuccessSavePredMeans != null && runSuccessSavePredMeans.equals("try-error")) {	
//						System.out.println("save pred means: error");
//						String checkError = "msg <- trimStrings(strsplit(saveDataB1, \":\")[[1]])";
//						String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//						String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//						String checkError4 = "capture.output(cat(\"*** \nERROR in saving predicted means to a file:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//						rEngine.eval(checkError);
//						rEngine.eval(checkError2);
//						rEngine.eval(checkError3);
//						rEngine.eval(checkError4);
//					}
//				}
//				
//				//optional output: diagnostic plots for genotype random
//				if (diagnosticPlot) {
//					String diagPlotsFunc = null;
//					if (environment == "NULL") {
//						diagPlotsFunc = "diagPlots <- try(graph.sea.diagplots(data, " + respvarVector + ", env = " + environment + ", is.random = TRUE, ssa2), silent=TRUE)";
//					} else {
//						diagPlotsFunc = "diagPlots <- try(graph.sea.diagplots(data, " + respvarVector + ", env = \"" + environment + "\", is.random = TRUE, ssa2), silent=TRUE)";
//					}
//					System.out.println(diagPlotsFunc);
//					rEngine.eval(diagPlotsFunc);
//					
//					String runSuccessDiagPlots = rEngine.eval("class(diagPlots)").toString();
//					if (runSuccessDiagPlots != null && runSuccessDiagPlots.equals("try-error")) {	
//						System.out.println("diagnostic plots (genotype random): error");
//						String checkError = "msg <- trimStrings(strsplit(diagPlots, \":\")[[1]])";
//						String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//						String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//						String checkError4 = "capture.output(cat(\"*** \nERROR in graph.sea.diagplots function:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//						rEngine.eval(checkError);
//						rEngine.eval(checkError2);
//						rEngine.eval(checkError3);
//						rEngine.eval(checkError4);
//					}
//				}
//				
//				} // end of else for if (runSuccess == "notRun") 
//			} // end of if random
//
//			//default output: save residuals to a file
//			if (runningFixedSuccess & runningRandomSuccess) {
//				String residFileNameFixed = "residFileNameFixed <- paste(\"" + resultFolderPath + "\",\"residuals_fixed.csv\", sep=\"\")";
//				String residFileNameRandom = "residFileNameRandom <- paste(\"" + resultFolderPath + "\",\"residuals_random.csv\", sep=\"\")";
//				if ((genotypeFixed) & (genotypeRandom == false)) {
//					String runSsaResid1 = null;
//					if (environment == "NULL") {
//						runSsaResid1 = "resid_f <- try(ssa.resid(data, ssa1, " + respvarVector + ", env = " + environment + ", is.genoRandom = FALSE), silent=TRUE)";
//					} else {
//						runSsaResid1 = "resid_f <- try(ssa.resid(data, ssa1, " + respvarVector + ", env = \"" + environment + "\", is.genoRandom = FALSE), silent=TRUE)";
//					}
//					System.out.println(runSsaResid1);
//					rEngine.eval(runSsaResid1);
//					
//					String runSuccessDiagPlots = rEngine.eval("class(resid_f)").toString();
//					if (runSuccessDiagPlots != null && runSuccessDiagPlots.equals("try-error")) {	
//						System.out.println("ssa.resid (genotype fixed): error");
//						String checkError = "msg <- trimStrings(strsplit(resid_f, \":\")[[1]])";
//						String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//						String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//						String checkError4 = "capture.output(cat(\"*** \nERROR in ssa.resid function:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//						rEngine.eval(checkError);
//						rEngine.eval(checkError2);
//						rEngine.eval(checkError3);
//						rEngine.eval(checkError4);
//					} else {
//						String checkResid1 = rEngine.eval("resid_f$residWarning").toString();
//						System.out.println("checkResid1: " + checkResid1);
//						if (checkResid1.equals("empty")) {
//							System.out.println("Saving resid (fixed) not done.");
//						} else {
//							String func1SaveResidualsCsv = "saveResid <- try(write.table(resid_f$residuals, file = residFileNameFixed ,sep=\",\",row.names=FALSE), silent=TRUE)";
//							rEngine.eval(residFileNameFixed);
//							rEngine.eval(func1SaveResidualsCsv);
//							
//							String runSuccessSaveResid = rEngine.eval("class(saveResid)").toString();
//							if (runSuccessSaveResid != null && runSuccessSaveResid.equals("try-error")) {	
//								System.out.println("save residuals: error");
//								String checkError = "msg <- trimStrings(strsplit(saveResid, \":\")[[1]])";
//								String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//								String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//								String checkError4 = "capture.output(cat(\"*** \nERROR in saving residuals to a file:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//								rEngine.eval(checkError);
//								rEngine.eval(checkError2);
//								rEngine.eval(checkError3);
//								rEngine.eval(checkError4);
//							}
//							
//							//generate heatmap
//							if (heatmapResiduals) {
//								String funcHeat=null;
//								if (environment == "NULL") {
//									funcHeat = "heat1 <- try(Heatmap(resid_f$residuals, genAs=\"fixed\", \"" + heatmapRow + "\", \"" + heatmapColumn + "\", " + respvarVector + ", \"" + designUsed + "\", " + environment + "), silent=TRUE)";
//								} else {
//									funcHeat = "heat1 <- try(Heatmap(resid_f$residuals, genAs=\"fixed\", \"" + heatmapRow + "\", \"" + heatmapColumn + "\", " + respvarVector + ", \"" + designUsed + "\", \"" + environment + "\"), silent=TRUE)";
//								}
//								System.out.println(funcHeat);
//								rEngine.eval(funcHeat);
//								
//								String runSuccessHeat = rEngine.eval("class(heat1)").toString();
//								if (runSuccessHeat != null && runSuccessHeat.equals("try-error")) {	
//									System.out.println("heatmap (fixed): error");
//									String checkError = "msg <- trimStrings(strsplit(heat1, \":\")[[1]])";
//									String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//									String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//									String checkError4 = "capture.output(cat(\"*** \nERROR in Heatmap function:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//									rEngine.eval(checkError);
//									rEngine.eval(checkError2);
//									rEngine.eval(checkError3);
//									rEngine.eval(checkError4);
//								} else {
//									for (int k = 0; k < respvar.length; k++) {
//										int i = k + 1; // 1-relative index;
//										
//										String envLevelsCommand = "length(heat1[[" + i + "]]$site)";
//										int envLevels = rEngine.eval(envLevelsCommand).asInteger();
//										for (int m = 0; m < envLevels; m++) { 
//											int j = m + 1; // 1-relative index;
//											
//											String warningListCommand = "heat1[[" + i + "]]$site[["+ j + "]]";
//											String warningList = rEngine.eval(warningListCommand).toString();
//											
//											if (warningList.equals("empty")) {
//												
//											} else if (warningList.equals("unique")) {
//												
//											} else {
//												String trialObsUsed = "capture.output(cat(\"\nERROR:\", heat1[[" + i + "]]$site[["+ j + "]],\"\n\"),file=\""	+ outFileName + "\",append = TRUE)";
//												rEngine.eval(trialObsUsed);
//											}
//										}
//									}
//								} //end (heat1 is not error)
//							}
//						}
//					}
//				}
//				else if ((genotypeFixed == false) & (genotypeRandom)) {
//					String runSsaResid2 = null;
//					if (environment == "NULL") {
//						runSsaResid2 = "resid_r <- try(ssa.resid(data, ssa2, " + respvarVector + ", env = " + environment + ", is.genoRandom = TRUE), silent=TRUE)";
//					} else {
//						runSsaResid2 = "resid_r <- try(ssa.resid(data, ssa2, " + respvarVector + ", env = \"" + environment + "\", is.genoRandom = TRUE), silent=TRUE)";
//					}
//					System.out.println(runSsaResid2);
//					rEngine.eval(runSsaResid2);
//					
//					String runSuccessDiagPlots = rEngine.eval("class(resid_r)").toString();
//					if (runSuccessDiagPlots != null && runSuccessDiagPlots.equals("try-error")) {	
//						System.out.println("ssa.resid (genotype random): error");
//						String checkError = "msg <- trimStrings(strsplit(resid_r, \":\")[[1]])";
//						String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//						String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//						String checkError4 = "capture.output(cat(\"*** \nERROR in ssa.resid function:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//						rEngine.eval(checkError);
//						rEngine.eval(checkError2);
//						rEngine.eval(checkError3);
//						rEngine.eval(checkError4);
//					} else {
//						String checkResid1 = rEngine.eval("resid_r$residWarning").toString();
//						System.out.println("checkResid2: " + checkResid1);
//						if (checkResid1.equals("empty")) {
//							System.out.println("Saving resid (random) not done.");
//						} else {
//							String func1SaveResidualsCsv = "saveResid <- try(write.table(resid_r$residuals, file = residFileNameRandom ,sep=\",\",row.names=FALSE), silent=TRUE)";
//							rEngine.eval(residFileNameRandom);
//							rEngine.eval(func1SaveResidualsCsv);
//							
//							String runSuccessSaveResid = rEngine.eval("class(saveResid)").toString();
//							if (runSuccessSaveResid != null && runSuccessSaveResid.equals("try-error")) {	
//								System.out.println("save residuals: error");
//								String checkError = "msg <- trimStrings(strsplit(saveResid, \":\")[[1]])";
//								String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//								String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//								String checkError4 = "capture.output(cat(\"*** \nERROR in saving residuals to a file:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//								rEngine.eval(checkError);
//								rEngine.eval(checkError2);
//								rEngine.eval(checkError3);
//								rEngine.eval(checkError4);
//							}
//							
//							//generate heatmap
//							if (heatmapResiduals) {
//								String funcHeat=null;
//								if (environment == "NULL") {
//									funcHeat = "heat2 <- try(Heatmap(resid_r$residuals, genAs=\"random\", \"" + heatmapRow + "\", \"" + heatmapColumn + "\", " + respvarVector + ", \"" + designUsed + "\", " + environment + "), silent=TRUE)";
//								} else {
//									funcHeat = "heat2 <- try(Heatmap(resid_r$residuals, genAs=\"random\", \"" + heatmapRow + "\", \"" + heatmapColumn + "\", " + respvarVector + ", \"" + designUsed + "\", \"" + environment + "\"), silent=TRUE)";
//								}
//								System.out.println(funcHeat);
//								rEngine.eval(funcHeat);
//								
//								String runSuccessHeat = rEngine.eval("class(heat2)").toString();
//								if (runSuccessHeat != null && runSuccessHeat.equals("try-error")) {	
//									System.out.println("heatmap (random): error");
//									String checkError = "msg <- trimStrings(strsplit(heat2, \":\")[[1]])";
//									String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//									String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//									String checkError4 = "capture.output(cat(\"*** \nERROR in Heatmap function:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//									rEngine.eval(checkError);
//									rEngine.eval(checkError2);
//									rEngine.eval(checkError3);
//									rEngine.eval(checkError4);
//								} else {
//									for (int k = 0; k < respvar.length; k++) {
//										int i = k + 1; // 1-relative index;
//										
//										String envLevelsCommand = "length(heat2[[" + i + "]]$site)";
//										int envLevels = rEngine.eval(envLevelsCommand).asInteger();
//										for (int m = 0; m < envLevels; m++) { 
//											int j = m + 1; // 1-relative index;
//											
//											String warningListCommand = "heat2[[" + i + "]]$site[["+ j + "]]";
//											String warningList = rEngine.eval(warningListCommand).toString();
//											
//											if (warningList.equals("empty")) {
//												
//											} else if (warningList.equals("unique")) {
//												
//											} else {
//												String trialObsUsed = "capture.output(cat(\"\nERROR:\", heat2[[" + i + "]]$site[["+ j + "]],\"\n\"),file=\""	+ outFileName + "\",append = TRUE)";
//												rEngine.eval(trialObsUsed);
//											}
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//				else if ((genotypeFixed) & (genotypeRandom)) {
//					String runSsaResid1 = null;
//					String runSsaResid2 = null;
//					if (environment == "NULL") {
//						runSsaResid1 = "resid_f <- try(ssa.resid(data, ssa1, " + respvarVector + ", env = " + environment + ", is.genoRandom = FALSE), silent=TRUE)";
//						runSsaResid2 = "resid_r <- try(ssa.resid(data, ssa2, " + respvarVector + ", env = " + environment + ", is.genoRandom = TRUE), silent=TRUE)";
//					} else {
//						runSsaResid1 = "resid_f <- try(ssa.resid(data, ssa1, " + respvarVector + ", env = \"" + environment + "\", is.genoRandom = FALSE), silent=TRUE)";
//						runSsaResid2 = "resid_r <- try(ssa.resid(data, ssa2, " + respvarVector + ", env = \"" + environment + "\", is.genoRandom = TRUE), silent=TRUE)";
//					}
//					System.out.println(runSsaResid1);
//					System.out.println(runSsaResid2);
//					rEngine.eval(runSsaResid1);
//					rEngine.eval(runSsaResid2);
//					
//					String runSuccessResidFixed = rEngine.eval("class(resid_f)").toString();
//					if (runSuccessResidFixed != null && runSuccessResidFixed.equals("try-error")) {	
//						System.out.println("ssa.resid (genotype fixed): error");
//						String checkError = "msg <- trimStrings(strsplit(resid_f, \":\")[[1]])";
//						String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//						String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//						String checkError4 = "capture.output(cat(\"*** \nERROR in ssa.resid function:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//						rEngine.eval(checkError);
//						rEngine.eval(checkError2);
//						rEngine.eval(checkError3);
//						rEngine.eval(checkError4);
//					} else {
//						String checkResid1 = rEngine.eval("resid_f$residWarning").toString();
//						System.out.println("checkResid1: " + checkResid1);
//						if (checkResid1.equals("empty")) {
//							System.out.println("Saving resid (fixed) not done.");
//						} else {
//							String func1SaveResidualsCsv = "saveResid <- try(write.table(resid_f$residuals, file = residFileNameFixed ,sep=\",\",row.names=FALSE), silent=TRUE)";
//							rEngine.eval(residFileNameFixed);
//							rEngine.eval(func1SaveResidualsCsv);
//							
//							String runSuccessSaveResid = rEngine.eval("class(saveResid)").toString();
//							if (runSuccessSaveResid != null && runSuccessSaveResid.equals("try-error")) {	
//								System.out.println("save residuals: error");
//								String checkError = "msg <- trimStrings(strsplit(saveResid, \":\")[[1]])";
//								String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//								String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//								String checkError4 = "capture.output(cat(\"*** \nERROR in saving residuals to a file:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//								rEngine.eval(checkError);
//								rEngine.eval(checkError2);
//								rEngine.eval(checkError3);
//								rEngine.eval(checkError4);
//							}
//							
//							//generate heatmap
//							if (heatmapResiduals) {
//								String funcHeat=null;
//								if (environment == "NULL") {
//									funcHeat = "heat1 <- try(Heatmap(resid_f$residuals, genAs=\"fixed\", \"" + heatmapRow + "\", \"" + heatmapColumn + "\", " + respvarVector + ", \"" + designUsed + "\", " + environment + "), silent=TRUE)";
//								} else {
//									funcHeat = "heat1 <- try(Heatmap(resid_f$residuals, genAs=\"fixed\", \"" + heatmapRow + "\", \"" + heatmapColumn + "\", " + respvarVector + ", \"" + designUsed + "\", \"" + environment + "\"), silent=TRUE)";
//								}
//								System.out.println(funcHeat);
//								rEngine.eval(funcHeat);
//								
//								String runSuccessHeat = rEngine.eval("class(heat1)").toString();
//								if (runSuccessHeat != null && runSuccessHeat.equals("try-error")) {	
//									System.out.println("heatmap (fixed): error");
//									String checkError = "msg <- trimStrings(strsplit(heat1, \":\")[[1]])";
//									String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//									String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//									String checkError4 = "capture.output(cat(\"*** \nERROR in Heatmap function:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//									rEngine.eval(checkError);
//									rEngine.eval(checkError2);
//									rEngine.eval(checkError3);
//									rEngine.eval(checkError4);
//								} else {
//									for (int k = 0; k < respvar.length; k++) {
//										int i = k + 1; // 1-relative index;
//										
//										String envLevelsCommand = "length(heat1[[" + i + "]]$site)";
//										int envLevels = rEngine.eval(envLevelsCommand).asInteger();
//										for (int m = 0; m < envLevels; m++) { 
//											int j = m + 1; // 1-relative index;
//											
//											String warningListCommand = "heat1[[" + i + "]]$site[["+ j + "]]";
//											String warningList = rEngine.eval(warningListCommand).toString();
//											
//											if (warningList.equals("empty")) {
//												
//											} else if (warningList.equals("unique")) {
//												
//											} else {
//												String trialObsUsed = "capture.output(cat(\"\nERROR:\", heat1[[" + i + "]]$site[["+ j + "]],\"\n\"),file=\""	+ outFileName + "\",append = TRUE)";
//												rEngine.eval(trialObsUsed);
//											}
//										}
//									}
//								}
//							}
//						}
//					}
//					
//					String runSuccessResidRandom = rEngine.eval("class(resid_r)").toString();
//					if (runSuccessResidRandom != null && runSuccessResidRandom.equals("try-error")) {	
//						System.out.println("ssa.resid (genotype random): error");
//						String checkError = "msg <- trimStrings(strsplit(resid_r, \":\")[[1]])";
//						String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//						String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//						String checkError4 = "capture.output(cat(\"*** \nERROR in ssa.resid function:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//						rEngine.eval(checkError);
//						rEngine.eval(checkError2);
//						rEngine.eval(checkError3);
//						rEngine.eval(checkError4);
//					} else {
//						String checkResid1 = rEngine.eval("resid_r$residWarning").toString();
//						System.out.println("checkResid2: " + checkResid1);
//						if (checkResid1.equals("empty")) {
//							System.out.println("Saving resid (random) not done.");
//						} else {
//							String func1SaveResidualsCsv = "saveResid2 <- try(write.table(resid_r$residuals, file = residFileNameRandom ,sep=\",\",row.names=FALSE), silent=TRUE)";
//							rEngine.eval(residFileNameRandom);
//							rEngine.eval(func1SaveResidualsCsv);
//							
//							String runSuccessSaveResid = rEngine.eval("class(saveResid2)").toString();
//							if (runSuccessSaveResid != null && runSuccessSaveResid.equals("try-error")) {	
//								System.out.println("save residuals: error");
//								String checkError = "msg <- trimStrings(strsplit(saveResid2, \":\")[[1]])";
//								String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//								String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//								String checkError4 = "capture.output(cat(\"*** \nERROR in saving residuals to a file:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//								rEngine.eval(checkError);
//								rEngine.eval(checkError2);
//								rEngine.eval(checkError3);
//								rEngine.eval(checkError4);
//							}
//							
//							if (heatmapResiduals) {
//								String funcHeat=null;
//								if (environment == "NULL") {
//									funcHeat = "heat2 <- try(Heatmap(resid_r$residuals, genAs=\"random\", \"" + heatmapRow + "\", \"" + heatmapColumn + "\", " + respvarVector + ", \"" + designUsed + "\", " + environment + "), silent=TRUE)";
//								} else {
//									funcHeat = "heat2 <- try(Heatmap(resid_r$residuals, genAs=\"random\", \"" + heatmapRow + "\", \"" + heatmapColumn + "\", " + respvarVector + ", \"" + designUsed + "\", \"" + environment + "\"), silent=TRUE)";
//								}
//								System.out.println(funcHeat);
//								rEngine.eval(funcHeat);
//								
//								String runSuccessHeat = rEngine.eval("class(heat2)").toString();
//								if (runSuccessHeat != null && runSuccessHeat.equals("try-error")) {	
//									System.out.println("heatmap (random): error");
//									String checkError = "msg <- trimStrings(strsplit(heat2, \":\")[[1]])";
//									String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//									String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//									String checkError4 = "capture.output(cat(\"*** \nERROR in Heatmap function:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//									rEngine.eval(checkError);
//									rEngine.eval(checkError2);
//									rEngine.eval(checkError3);
//									rEngine.eval(checkError4);
//								} else {
//									for (int k = 0; k < respvar.length; k++) {
//										int i = k + 1; // 1-relative index;
//										
//										String envLevelsCommand = "length(heat2[[" + i + "]]$site)";
//										int envLevels = rEngine.eval(envLevelsCommand).asInteger();
//										for (int m = 0; m < envLevels; m++) { 
//											int j = m + 1; // 1-relative index;
//											
//											String warningListCommand = "heat2[[" + i + "]]$site[["+ j + "]]";
//											String warningList = rEngine.eval(warningListCommand).toString();
//											
//											if (warningList.equals("empty")) {
//												
//											} else if (warningList.equals("unique")) {
//												
//											} else {
//												String trialObsUsed = "capture.output(cat(\"\nERROR:\", heat2[[" + i + "]]$site[["+ j + "]],\"\n\"),file=\""	+ outFileName + "\",append = TRUE)";
//												rEngine.eval(trialObsUsed);
//											}
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//			
//			//optional output: boxplot and histogram
//			String withBox = "FALSE";
//			if (boxplotRawData) withBox = "TRUE";
//			String withHist = "FALSE";
//			if (histogramRawData) withHist = "TRUE";
//			String ssaOut = "ssa1";
//			if (genotypeFixed) ssaOut = "ssa1";
//			else if (genotypeRandom) ssaOut = "ssa2";
//
//			String boxHistFunc = null;
//			if (environment =="NULL") {
//				boxHistFunc = "boxHist <- try(graph.sea.boxhist(data, " + respvarVector + ", env = " + environment + ", " + ssaOut + ", box = \"" + withBox + "\", hist = \"" + withHist + "\"), silent=TRUE)";
//			} else {
//				boxHistFunc = "boxHist <- try(graph.sea.boxhist(data, " + respvarVector + ", env = \"" + environment + "\", " + ssaOut + ", box = \"" + withBox + "\", hist = \"" + withHist + "\"), silent=TRUE)";
//			}
//			System.out.println(boxHistFunc);
//			rEngine.eval(boxHistFunc);
//			
//			String runSuccessBoxHist = rEngine.eval("class(boxHist)").toString();
//			if (runSuccessBoxHist != null && runSuccessBoxHist.equals("try-error")) {	
//				System.out.println("boxplot/histogram: error");
//				String checkError = "msg <- trimStrings(strsplit(boxHist, \":\")[[1]])";
//				String checkError2 = "msg <- trimStrings(paste(strsplit(msg, \"\\n\")[[length(msg)]], collapse = \" \"))";
//				String checkError3 ="msg <- gsub(\"\\\"\", \"\", msg)";
//				String checkError4 = "capture.output(cat(\"*** \nERROR in graph.sea.boxhist function:\\n  \",msg, \"\n***\n\n\", sep = \"\"), file=\"" + outFileName + "\",append = TRUE)";
//				rEngine.eval(checkError);
//				rEngine.eval(checkError2);
//				rEngine.eval(checkError3);
//				rEngine.eval(checkError4);
//			}
//			rEngine.eval(outspace);
//			rEngine.eval(sep2);
//
////			rEngineEnd();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public String testRServe() throws RserveException, REXPMismatchException{
//		String result = null;
//		RConnection c = new RConnection();
//		try{
//			c.eval("library(PBTools)");
//			String realpath = testSingleEnvironment(c);
//			Reader reader = new FileReader(realpath);
//			try {
//				System.out.print(realpath);
//				result = new String(org.zkoss.io.Files.readAll(reader));
//				reader.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally{
//			c.close();
//		}
//		return result;
//
//
//	}
}
