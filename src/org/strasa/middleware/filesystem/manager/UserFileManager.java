package org.strasa.middleware.filesystem.manager;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.manager.StudyFileManagerImpl;
import org.strasa.middleware.manager.StudyManagerImpl;
import org.strasa.middleware.manager.UserManagerImpl;
import org.strasa.middleware.model.StudyFile;
import org.strasa.middleware.util.StringConstants;
import org.strasa.web.common.api.Encryptions;
import org.zkoss.zk.ui.Sessions;

public class UserFileManager {
	public static String FILE_TYPE_GENOTYPE = "GENOTYPE"; 
	public static String FILE_TYPE_PHENOTYPE = "PHENOTYPE";
	public static String BASE_PATH = Sessions.getCurrent().getWebApp().getRealPath( File.separator + "UPLOADS" + File.separator );

	public void createNewFileFromUpload(int userid, int studyid, String realName,File dataFile, String fileType){

		File BASE_FOLDER = new File(buildUserPath(userid, studyid));
		if(!BASE_FOLDER.exists()) BASE_FOLDER.mkdirs();
		try {
			FileUtils.copyFileToDirectory(dataFile, new File(BASE_FOLDER.getAbsolutePath() + File.separator ),true);
			File movedFile = new File(BASE_FOLDER.getAbsolutePath() + File.separator + dataFile.getName() );
			File renamedFile = new File(BASE_FOLDER.getAbsolutePath() + File.separator + String.valueOf(  Calendar.getInstance().getTimeInMillis()+ realName));
			movedFile.renameTo(renamedFile);
			StudyFile fileRec = new StudyFile();
			fileRec.setFilename(realName);
			fileRec.setFilenamegen(renamedFile.getName());
			fileRec.setFilepath(renamedFile.getAbsolutePath());
			fileRec.setStudyid(studyid);
			fileRec.setTypeofdata(fileType);
			new StudyFileManagerImpl().addRecord(fileRec);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		dataFile.renameTo(new File(BASE_FOLDER.getAbsolutePath() + dataFile.getName())); 
		System.out.println("File moved to: " + BASE_FOLDER.getAbsolutePath());

	}

	public String uploadFileForAnalysis(String realName,File dataFile){
		File movedFile= null;
		File renamedFile = null;
		File BASE_FOLDER = new File(buildUserPath(SecurityUtil.getDbUser().getId(), 0, realName.replaceAll(".csv", "")));
		if(!BASE_FOLDER.exists()) BASE_FOLDER.mkdirs();
		try {
			FileUtils.copyFileToDirectory(dataFile, new File(BASE_FOLDER.getAbsolutePath() + File.separator ),true);
			movedFile = new File(BASE_FOLDER.getAbsolutePath() + File.separator + dataFile.getName());
			renamedFile = new File(BASE_FOLDER.getAbsolutePath() + File.separator + 
					realName.replaceAll(".csv", "") + "_(Dataset).csv");
			movedFile.renameTo(renamedFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return renamedFile.getAbsolutePath();
	}
	
	public  String uploadContrastFileForAnalysis(String fileName, File dataFile, String folderPath, String label)
	{
		File movedFile = null;
		File renamedFile = null;
		File BASE_FOLDER = new File(folderPath);
		if(!BASE_FOLDER.exists())
			BASE_FOLDER.mkdirs();
		try{
			FileUtils.copyFileToDirectory(dataFile, new File(BASE_FOLDER.getAbsolutePath() + File.separator), true);
			movedFile = new File(BASE_FOLDER.getAbsolutePath() + File.separator + dataFile.getName());
			renamedFile = new File(BASE_FOLDER.getAbsolutePath() + File.separator + 
					fileName.replaceAll(".csv","") + "_" + label + "_(Contrast).csv");
			movedFile.renameTo(renamedFile);
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		return renamedFile.getAbsolutePath();
	}
	
	public String uploadFileForAnalysis(String fileName, File dataFile, String folderPath, String postfix)
	{
		File movedFile = null;
		File renamedFile = null;
		File BASE_FOLDER = new File(folderPath);
		if(!BASE_FOLDER.exists())
			BASE_FOLDER.mkdirs();
		try{
			FileUtils.copyFileToDirectory(dataFile, new File(BASE_FOLDER.getAbsolutePath() + File.separator), true);
			movedFile = new File(BASE_FOLDER.getAbsolutePath() + File.separator + dataFile.getName());
			renamedFile = new File(BASE_FOLDER.getAbsolutePath() + File.separator + 
					fileName.replaceAll(".csv","") + "_" + postfix + ".csv");
			movedFile.renameTo(renamedFile);
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		return renamedFile.getAbsolutePath();
	}
	
	public void moveUploadedFileToOutputFolder(String resultFolderPath, String realName,File dataFile){
		File movedFile= null;
		File renamedFile = null;
		try {
			FileUtils.copyFileToDirectory(dataFile, new File(resultFolderPath + File.separator ),true);
			movedFile = new File(resultFolderPath + File.separator + realName+ ".csv" );
			String filePath = resultFolderPath + File.separator + realName+ "(dataset).csv" ;
			renamedFile = new File(filePath.replaceAll(".csv", "_(Dataset).csv"));
			movedFile.renameTo(renamedFile);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void moveContrastFileToOutputFolder(String resultFolderPath, String dataFile)
	{
		try {
			File file = new File(dataFile);
			FileUtils.copyFileToDirectory(file, new File(resultFolderPath + File.separator), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
//	Create by QIN MAO
//	it is used to copy user uploaded file to result folder
	public File copyUploadedFileToOutputFolder(String resultFolderPath, String realName, File dataFile)
	{
		if(!realName.endsWith(".csv"))
			realName = realName + ".csv";
		File file = null;
		try {
			file = new File(resultFolderPath + StringConstants.SYSTEM_FILE_SEPARATOR + realName);
			FileUtils.copyFile(dataFile, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}
	
	public static String buildUserPath(int userid, int studyid){

		String userBasePath =  String.valueOf(userid);//Encryptions.encryptStringToNumber(new UserManagerImpl().getUserById(userid).getUsername(),userid); 
		String studyBasePath;
		if(studyid == 0){
			studyBasePath = "tmp";
		}
		else{
			studyBasePath = String.valueOf(studyid);//Encryptions.encryptStringToNumber(new StudyManagerImpl().getStudyById(studyid).getName(), studyid);
		}

		return BASE_PATH + File.separator  + userBasePath + File.separator  + studyBasePath + File.separator +
				String.valueOf(Calendar.getInstance().getTimeInMillis()) + File.separator;
	}
	
	public static String buildUserPath(int userid, int studyid, String filename)
	{
		String userBasePath = String.valueOf(userid);
		String studyBasePath = null;
		if(studyid == 0)
		{
			studyBasePath = "tmp";
		} else
		{
			studyBasePath = String.valueOf(studyid);
		}
		return BASE_PATH + File.separator + userBasePath + File.separator + studyBasePath + File.separator +
				filename + File.separator + String.valueOf(Calendar.getInstance().getTimeInMillis()) + File.separator;
	}
	
}
