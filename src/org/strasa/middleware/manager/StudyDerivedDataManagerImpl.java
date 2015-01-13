package org.strasa.middleware.manager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.StudyDerivedDataMapper;
import org.strasa.middleware.mapper.StudyDerivedRawDataMapper;
import org.strasa.middleware.mapper.StudyMapper;
import org.strasa.middleware.mapper.StudyRawDataByDataColumnMapper;
import org.strasa.middleware.mapper.StudyRawDataMapper;
import org.strasa.middleware.model.Study;
import org.strasa.middleware.model.StudyDerivedData;
import org.strasa.middleware.model.StudyDerivedDataExample;
import org.strasa.middleware.model.StudyRawData;
import org.strasa.middleware.model.StudyRawDataByDataColumnExample;
import org.strasa.middleware.model.StudyRawDataExample;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class StudyDerivedDataManagerImpl {

	@WireVariable
	ConnectionFactory connectionFactory;

	public int addStudy(Study record){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		StudyMapper studyMapper = session.getMapper(StudyMapper.class);
		try{
			studyMapper.insert(record);
			session.commit();
		}
		finally{
			
		}
		return record.getId();
	}


	public void addStudyDerivedData(Study study, List<StudyDerivedData> studyData){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		StudyDerivedDataMapper studyDataMapper = session.getMapper(StudyDerivedDataMapper.class);
		StudyMapper studyMapper = session.getMapper(StudyMapper.class);
		try{
			studyMapper.insert(study);
			for(StudyDerivedData sdata:studyData){
				sdata.setStudyid(study.getId());
				studyDataMapper.insert(sdata);
			}
			session.commit();

		}finally{
			session.close();
		}

	}
	public void addStudyDerivedDataByRawCsvList(Study study, List<String[]> rawCSVData){
		String[] header = rawCSVData.get(0);
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		StudyDerivedDataMapper studyDataMapper = session.getMapper(StudyDerivedDataMapper.class);
		StudyMapper studyMapper = session.getMapper(StudyMapper.class);
		try{
			studyMapper.insert(study);
			for(int i = 1; i < rawCSVData.size(); i++){
				String[] row = rawCSVData.get(i);
				for(int j = 0; j < header.length; j++){
					
					if(row.length == header.length){
						StudyDerivedData record = new StudyDerivedData();
					
					record.setDatacolumn(header[j]);
					record.setDatarow(i);
					record.setDatavalue(row[j]);
					record.setStudyid(study.getId());
					studyDataMapper.insert(record);
					}
				}
			}
			session.commit();

		}finally{
			session.close();
		}
		
	}
	
	public boolean hasSiteColumnData(int studyid){
		
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		StudyRawDataByDataColumnMapper studySiteByStudyMapper = session.getMapper(StudyRawDataByDataColumnMapper.class);
		StudyRawDataByDataColumnExample example= new StudyRawDataByDataColumnExample();
		example.setDistinct(true);
		example.createCriteria().andStudyidEqualTo(studyid).andDatacolumnEqualTo("Site");
		studySiteByStudyMapper.selectByExample(example);
		
		return false;
	}
	
	
	public boolean hasLocationColumnData(int studyid){
		
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		StudyRawDataByDataColumnMapper studySiteByStudyMapper = session.getMapper(StudyRawDataByDataColumnMapper.class);
		StudyRawDataByDataColumnExample example= new StudyRawDataByDataColumnExample();
		example.setDistinct(true);
		example.createCriteria().andStudyidEqualTo(studyid).andDatacolumnEqualTo("Location");
		studySiteByStudyMapper.selectByExample(example);
		
		return false;
	}



	public List<StudyDerivedData> getAllStudyRawData() {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		StudyDerivedDataMapper StudyDerivedDataMapper = session.getMapper(StudyDerivedDataMapper.class);
		
		try{
			List<StudyDerivedData> studyrawdata = StudyDerivedDataMapper.selectByExample(null);
			return studyrawdata;
		}finally{
			session.close();
		}
	}
	
	public ArrayList<ArrayList<String>> constructDataRaw(int studyid,
			String[] columns, String baseColumn, boolean isDistinct) {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		StudyDerivedDataMapper mapper = session.getMapper(StudyDerivedDataMapper.class);

		StudyDerivedDataExample example = new StudyDerivedDataExample();
		example.createCriteria().andStudyidEqualTo(studyid)
				.andDatacolumnEqualTo(baseColumn);

		List<StudyDerivedData> lstBaseRaw = mapper
				.selectByExample(example);
		ArrayList<ArrayList<String>> returnVal = new ArrayList<ArrayList<String>>();
		for (StudyDerivedData rowData : lstBaseRaw) {
			ArrayList<String> rowConstructed = new ArrayList<String>();
			for (String col : columns) {
					example.clear();
//					System.out.println("StudyID: " + studyid + " DataCol:  " + col + " Row: " + rowData.getDatarow() );
					example.createCriteria().andDatacolumnEqualTo(col).andStudyidEqualTo(studyid).andDatarowEqualTo(rowData.getDatarow());
					List<StudyDerivedData> subList = mapper.selectByExample(example);
					if(subList.isEmpty()) rowConstructed.add("");
					else
					rowConstructed.add(subList.get(0).getDatavalue());
			}
			if(isDistinct){
				if(!returnVal.contains(rowConstructed)) returnVal.add(rowConstructed);
			}
			else returnVal.add(rowConstructed);
			
		}
		System.out.println("TOTAL ARR: " + returnVal.size());
		return returnVal;

	}
	public HashMap<String,ArrayList<String>> constructDataRawAsMap(int studyid,
			String[] columns, String baseColumn, boolean isDistinct) {
		

			ArrayList<ArrayList<String>>lstBaseData = constructDataRaw(studyid, columns, baseColumn, isDistinct);
			HashMap<String,ArrayList<String>> returnVal = new HashMap<String,ArrayList<String>> ();
			for(ArrayList<String> row : lstBaseData){
				returnVal.put(row.get(0), row);
			}
			return returnVal;
		
	}


	public void setPrivacyByStudyId(Integer studyid, Boolean shared) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyDerivedDataMapper studyDataMapper = session.getMapper(StudyDerivedDataMapper.class);
		
		try {
			StudyDerivedDataExample example = new StudyDerivedDataExample();
			example.createCriteria().andStudyidEqualTo(studyid);
			
			List<StudyDerivedData> studyData = studyDataMapper.selectByExample(example);
			for (StudyDerivedData sdata : studyData) {
				sdata.setShared(shared);
				studyDataMapper.updateByPrimaryKey(sdata);
			}
			session.commit();

		} finally {
			session.close();
		}
	}


	
}
