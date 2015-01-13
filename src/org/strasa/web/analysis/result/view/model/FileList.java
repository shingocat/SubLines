package org.strasa.web.analysis.result.view.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.util.StringConstants;
import org.zkoss.zk.ui.Executions;

public class FileList {
	public final static String FolderName = "FolderName";
	public final static String FileName = "FileName";

	private FileModelTreeNode root;
	private FileModelTreeNode root2;

	@SuppressWarnings("unchecked")
	public FileList() {

		Set<String> folderNameOfSingleSite;
		Set<String> folderNameOfMultiSite;
		Set<String> folderNameOfSSSLAnalysis;
		folderNameOfSingleSite=Executions.getCurrent().getDesktop().getWebApp().getResourcePaths("/resultanalysis/"+SecurityUtil.getUserName()+"/Single-Site");
		folderNameOfMultiSite = Executions.getCurrent().getDesktop().getWebApp().getResourcePaths("/resultanalysis/"+SecurityUtil.getUserName()+"/Multi-Site");
		folderNameOfSSSLAnalysis = Executions.getCurrent().getDesktop().getWebApp().getResourcePaths("/resultanalysis/"+SecurityUtil.getUserName()+"/SSSL_Analysis");
		root = new FileModelTreeNode(null,new FileModelTreeNode[] {
				new FileModelTreeNode(new FileModel("Single-Site"),getFolderList(folderNameOfSingleSite),true),
				
				new FileModelTreeNode(new FileModel("Multi-Site"),getFolderList(folderNameOfMultiSite),true),
				
				new FileModelTreeNode(new FileModel("SSSL_Analysis"), getFolderList(folderNameOfSSSLAnalysis), true),
		},true);

	}

		



	private FileModelTreeNode[] getFolderList(Set<String> list) {
		if(list == null || list.size() == 0)
			return null;
		FileModelTreeNode[] tmp=new FileModelTreeNode[list.size()];
		List<FileModelTreeNode> fm= new ArrayList<FileModelTreeNode>();
		for(String l : list){
			String[] nf=l.split("/");
			String nfn=nf[nf.length-1];
			/*nfn=nfn.substring(0, nf.length-1);*/
			
			Set<String> files;
			files=Executions.getCurrent().getDesktop().getWebApp().getResourcePaths(l);
			FileModelTreeNode x = new FileModelTreeNode(new FileModel(nfn),getFileList(files));
			fm.add(x);
		}
		return fm.toArray(tmp);
	}
	
	
	private FileModelTreeNode[] getFileList(Set<String> files) {
		FileModelTreeNode[] tmp=new FileModelTreeNode[files.size()];
		List<FileModelTreeNode> fm= new ArrayList<FileModelTreeNode>();
		
		List<String> myList = new ArrayList(files);
		Collections.sort(myList);
		for(String name : myList){
			String icons="text.png";
			if (name.contains("png")){
				icons="graph.png";
			}else if(name.contains("txt")){
				icons="text.png";
			}else if(name.contains("pdf")){
				icons="pdf.png";
			}else if(name.contains("csv")){
				icons="csv.png";
			}
			String[] n=name.split("/");
			FileModelTreeNode x = new FileModelTreeNode(new FileModel(n[n.length-1], icons));
			fm.add(x);
		}
		return fm.toArray(tmp);
	}


	public FileModelTreeNode getRoot() {
		return root;
	}
}