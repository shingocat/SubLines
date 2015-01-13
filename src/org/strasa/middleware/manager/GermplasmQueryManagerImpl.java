package org.strasa.middleware.manager;


import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.GermplasmMapper;
import org.strasa.middleware.mapper.StudyGermplasmCharacteristicsMapper;
import org.strasa.middleware.model.Germplasm;
import org.strasa.middleware.model.GermplasmExample;
import org.strasa.middleware.model.StudyGermplasmCharacteristics;
import org.strasa.middleware.model.StudyGermplasmCharacteristicsExample;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class GermplasmQueryManagerImpl {


	@WireVariable
	ConnectionFactory connectionFactory;
	public Germplasm getGermplasmByName(String value){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);
		
		try{
			GermplasmExample example = new GermplasmExample();
			example.createCriteria().andGermplasmnameEqualTo(value);
			if(mapper.selectByExample(example).isEmpty()) return null;
			return mapper.selectByExample(example).get(0);
			
		}
		finally{
				session.close();
		}
	}
	
	public Germplasm getGermplasmById(int value){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);
		
		try{
			GermplasmExample example = new GermplasmExample();
			example.createCriteria().andGermplasmtypeidEqualTo(value);
			if(mapper.selectByExample(example).isEmpty()) return null;
			return mapper.selectByExample(example).get(0);
			
		}
		finally{
				session.close();
		}
	}
	
	public boolean isGermplasmExisting(String value){
		if(this.getGermplasmByName(value) == null) return false;
		return true;
	}
	

	public void addGermplasm(Germplasm record){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		GermplasmMapper germplasmmapper = session.getMapper(GermplasmMapper.class);
		try{
			germplasmmapper.insert(record);
			session.commit();
		}
		finally{
			session.close();
		}
	}
	
	public List<Germplasm> getAllGermplasms(int germplasmname) {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		GermplasmMapper germplasmQueryMapper = session.getMapper(GermplasmMapper.class);
		try{
			List<Germplasm> germplasms = germplasmQueryMapper.selectByExample(null);
			return germplasms;
		}finally{
			session.close();
		}

	}
	
	private void addEmptyRecordOnGermplasm(int studyId) {
		// TODO Auto-generated method stub
		Germplasm record = new Germplasm();
		record.setId(studyId);
		record.setGermplasmtypeid(1);
		addGermplasm(record);
		System.out.println("Added Empty Record on germplasm");
	}
	
	public List<Germplasm> initializeGermplasmQuerys(int studyid) {
		// TODO Auto-generated method stub
		List<Germplasm> germplasmQuerys = getAllGermplasms(studyid);
		if(germplasmQuerys.isEmpty()){
			try {
				ArrayList<StudyGermplasmCharacteristics> studyList = getGermplasmByName(studyid);
				for(StudyGermplasmCharacteristics s:studyList){
					Germplasm record = new Germplasm();
					record.setId(s.getId());
					record.setGermplasmname(s.getGermplasmname());
					record.setGid(1);
					addGermplasm(record);
					System.out.println("added" + s.getGermplasmname() + " to study id: "+ s.getId());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(getAllGermplasms(studyid).isEmpty()) addEmptyRecordOnGermplasm(studyid);
		}
		return getAllGermplasms(studyid);
		
	}
	
	public ArrayList<StudyGermplasmCharacteristics> getGermplasmByName(int studyid) throws Exception {
		GermplasmQueryManagerImpl germplasmQueryManagerImpl= new GermplasmQueryManagerImpl();
		ArrayList<StudyGermplasmCharacteristics> list= (ArrayList<StudyGermplasmCharacteristics>) germplasmQueryManagerImpl.getStudyGermplasmCharacteristics(studyid,"germplasmname");
		try{
			for(StudyGermplasmCharacteristics s:list){
				System.out.println(s.getId()+ " "+s.getAttribute()+ " "+ s.getGermplasmname());
			}
		}catch(NullPointerException npe){//if still empty since there's no site data on the rawdata table
			// TODO Auto-generated catch block
		}
		return list;
	}

	public List<StudyGermplasmCharacteristics> getStudyGermplasmCharacteristics(int studyid,
			String column) {

		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		StudyGermplasmCharacteristicsMapper StudyGermplasmCharacteristicsMapper = session
				.getMapper(StudyGermplasmCharacteristicsMapper.class);

		StudyGermplasmCharacteristicsExample example = new StudyGermplasmCharacteristicsExample();

		example.createCriteria().andIdEqualTo(studyid)
				.andAttributeEqualTo(column);
		example.setDistinct(true);
		return StudyGermplasmCharacteristicsMapper.selectByExample(example);

	}
		
}
