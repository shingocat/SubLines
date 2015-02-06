package org.strasa.middleware.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.GermplasmMapper;
import org.strasa.middleware.mapper.LocationMapper;
import org.strasa.middleware.mapper.ProgramMapper;
import org.strasa.middleware.mapper.ProjectMapper;
import org.strasa.middleware.mapper.StudyGermplasmMapper;
import org.strasa.middleware.mapper.StudyLocationMapper;
import org.strasa.middleware.mapper.StudyMapper;
import org.strasa.middleware.mapper.StudyTypeMapper;
import org.strasa.middleware.model.Germplasm;
import org.strasa.middleware.model.GermplasmExample;
import org.strasa.middleware.model.Location;
import org.strasa.middleware.model.LocationExample;
import org.strasa.middleware.model.Program;
import org.strasa.middleware.model.ProgramExample;
import org.strasa.middleware.model.Project;
import org.strasa.middleware.model.ProjectExample;
import org.strasa.middleware.model.Study;
import org.strasa.middleware.model.StudyExample;
import org.strasa.middleware.model.StudyGermplasm;
import org.strasa.middleware.model.StudyGermplasmExample;
import org.strasa.middleware.model.StudyLocation;
import org.strasa.middleware.model.StudyLocationExample;
import org.strasa.middleware.model.StudyType;
import org.strasa.middleware.model.StudyTypeExample;
import org.strasa.web.browsestudy.view.model.StudySearchResultModel;
import org.strasa.web.germplasmquery.view.model.KeyCharacteristicQueryModel;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class BrowseGermplasmManagerImpl {

	private final static String ABIOTIC="Abiotic";
	private final static String BIOTIC="Biotic";
	private final static String GRAIN_QUALITY="Grain Quality";
	private final static String MAJOR_GENES="Major Genes";

	@WireVariable
	ConnectionFactory connectionFactory;

	public List<StudySearchResultModel> getStudyWithGemrplasmTested(String gname) {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		try{
			
			List<StudySearchResultModel> toreturn= session.selectList("BrowseStudy.getStudyWithGemrplasmTested",gname);

			return toreturn;

		}finally{
			session.close();
		}

	}
	
	//Add by QIN MAO on JAN 13, implement the same function of getStudyWithGermplamTest(String gname)
	//but adding the user id and change the logical
	public List<StudySearchResultModel> getStudyWithGermplasmTested(String gname, Integer userId)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		GermplasmMapper  gMapper = session.getMapper(GermplasmMapper.class);
		GermplasmExample gExample = new GermplasmExample();
		gExample.createCriteria().andGermplasmnameEqualTo(gname);
		List<Germplasm> lstGermplasm = gMapper.selectByExample(gExample);
		List<StudySearchResultModel> lstSSRM = new ArrayList<StudySearchResultModel>();
		if(lstGermplasm != null && lstGermplasm.size() != 0)
			lstSSRM =  this.getStudyWithGermplasmTested(lstGermplasm.get(0), userId);
		if(lstSSRM == null || lstSSRM.size() == 0)
			return null;
		else
			return lstSSRM;
		
	}
	//for introgressionline 
	public List<StudySearchResultModel> getStudyWithGermplasmTested(Germplasm g, Integer userId)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		List<StudySearchResultModel> toreturn = new ArrayList<StudySearchResultModel>();
		try{
			// get records in studygermplasm table
			StudyGermplasmMapper sgMapper = session.getMapper(StudyGermplasmMapper.class);
			StudyGermplasmExample sgExample = new StudyGermplasmExample();
			sgExample.createCriteria().andUseridEqualTo(userId).andGrefEqualTo(g.getId());
			List<StudyGermplasm> lstStudyGermplasm = sgMapper.selectByExample(sgExample);
			if(lstStudyGermplasm == null)
				return null;
			//get study information according the records above
			StudyMapper sMapper = session.getMapper(StudyMapper.class);
			ProjectMapper projMapper = session.getMapper(ProjectMapper.class);
			ProgramMapper progMapper = session.getMapper(ProgramMapper.class);
			StudyTypeMapper stMapper = session.getMapper(StudyTypeMapper.class);
			StudyLocationMapper slMapper = session.getMapper(StudyLocationMapper.class);
			LocationMapper lMapper = session.getMapper(LocationMapper.class);
			
			for(StudyGermplasm sg : lstStudyGermplasm)
			{
				StudyExample sExample = new StudyExample();
				ProjectExample projExample  = new ProjectExample();
				ProgramExample progExample = new ProgramExample();
				StudyTypeExample stExample = new StudyTypeExample();
				StudyLocationExample slExample = new StudyLocationExample();
				LocationExample lExample = new LocationExample();
				// study information
				sExample.createCriteria().andUseridEqualTo(userId).andIdEqualTo(sg.getStudyid());
				List<Study> lstStudy = sMapper.selectByExample(sExample);
				if(lstStudy != null && lstStudy.size() != 0)
				{
					// it will be only one study return if exist
					StudySearchResultModel ssrm = new StudySearchResultModel();

					Study study = lstStudy.get(0);
					ssrm.setId(sg.getStudyid());
					ssrm.setStudyname(lstStudy.get(0).getName());
					ssrm.setStartyear(lstStudy.get(0).getStartyear());
					ssrm.setEndyear(lstStudy.get(0).getEndyear());
					// program information
					progExample.createCriteria().andUseridEqualTo(userId).andIdEqualTo(study.getProgramid());
					List<Program> lstProgram = progMapper.selectByExample(progExample);
					if(lstProgram != null && lstProgram.size() != 0)
					{
						ssrm.setProgramid(lstProgram.get(0).getId());
						ssrm.setProgramname(lstProgram.get(0).getName());
					}
					//project inforamtion
					projExample.createCriteria().andUseridEqualTo(userId).andIdEqualTo(study.getProjectid());
					List<Project> lstProject = projMapper.selectByExample(projExample);
					if(lstProject != null && lstProject.size() != 0)
					{
						ssrm.setProjectid(lstProject.get(0).getId());
						ssrm.setProjectname(lstProject.get(0).getName());
					}
					//studylocation information
					slExample.createCriteria().andStudyidEqualTo(study.getId());
					List<StudyLocation> lstStudyLocation = slMapper.selectByExample(slExample);
					if(lstStudyLocation != null && lstStudyLocation.size() != 0)
					{
						StudyLocation studyLocation = lstStudyLocation.get(0);
						//location information
						lExample.createCriteria().andIdEqualTo(studyLocation.getLocationid());
						List<Location> lstLocation = lMapper.selectByExample(lExample);
						if(lstLocation != null && lstLocation.size() != 0)
						{
							ssrm.setLocationid(lstLocation.get(0).getId());
							ssrm.setLocationname(lstLocation.get(0).getLocationname());
							ssrm.setCountry(lstLocation.get(0).getCountry());
						}
					}
					//studytype information
					stExample.createCriteria().andIdEqualTo(study.getStudytypeid());
					List<StudyType> lstStudyType = stMapper.selectByExample(stExample);
					if(lstStudyType != null && lstStudyType.size() != 0)
					{
						ssrm.setStudytypeid(lstStudyType.get(0).getId());
						ssrm.setStudytypename(lstStudyType.get(0).getStudytype());
					}
					
					toreturn.add(ssrm);
				}
				
			}
			if(toreturn.size() == 0)
				return null;
			return toreturn;
		} finally
		{
			session.close();
		}
	}
	


	public List<Germplasm> getGermplasmKeyCharacteristicsAbiotic(ArrayList<String> keyCharList, String keyChar) {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		try{
			List<Germplasm> toreturn = null;
			if(keyChar.equals(ABIOTIC)){
				toreturn= session.selectList("BrowseGermplasm.getGermplasmByKeyCharacteristicsAbiotic",keyCharList);
			}else if(keyChar.equals(BIOTIC)){
				toreturn= session.selectList("BrowseGermplasm.getGermplasmByKeyCharacteristicsBiotic",keyCharList);
			}else if(keyChar.equals(GRAIN_QUALITY)){
				toreturn= session.selectList("BrowseGermplasm.getGermplasmByKeyCharacteristicsGrainQuality",keyCharList);
			}else{
				toreturn= session.selectList("BrowseGermplasm.getGermplasmByKeyCharacteristicsMajorGenes",keyCharList);
			}
			return toreturn;

		}finally{
			session.close();
		}

	}


	public List<Germplasm> getGermplasmByNameEqual(String gname) {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		try{
			List<Germplasm> toreturn = null;
			toreturn= session.selectList("BrowseGermplasm.getGermplasmByNameEqual",gname);
			return toreturn;
		}finally{
			session.close();
		}
	}

	public List<Germplasm> getGermplasmByNameLike(String gname) {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		try{
			List<Germplasm> toreturn = null;
			toreturn= session.selectList("BrowseGermplasm.getGermplasmByNameLike",gname);
			return toreturn;
		}finally{
			session.close();
		}
	}


	public List<Germplasm> getGermplasmByType(int typeid) {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		try{
			List<Germplasm> toreturn = null;
			toreturn= session.selectList("BrowseGermplasm.getGermplasmByType",typeid);
			return toreturn;
		}finally{
			session.close();
		}
	}


	public List<Germplasm> getGermplasmKeyCharacteristics(KeyCharacteristicQueryModel keyQueryCriteria) {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		try{
			List<Germplasm> toreturn = new ArrayList<Germplasm>();
			List<Germplasm> germplasm = new ArrayList<Germplasm>();
			germplasm= session.selectList("BrowseGermplasm.getGermplasmByKeyCharacteristics",keyQueryCriteria);

			List<String> germplasmList= new ArrayList<String>();
			for(Germplasm g: germplasm){
				germplasmList.add(g.getGermplasmname());
			}
			if(germplasmList.size() > 0){
				toreturn= session.selectList("BrowseGermplasm.getGermplasmByKeyCharacteristicsQuery",germplasmList);
			}
			return toreturn;
		}finally{
			session.close();
		}
	}


	public List<Germplasm> getGermplasmListByType(int id){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);

		try{
			GermplasmExample example = new GermplasmExample();
			example.createCriteria().andGermplasmtypeidEqualTo(id);
			if(mapper.selectByExample(example).isEmpty()) return null;
			return mapper.selectByExample(example);
		}
		finally{
			session.close();
		}
	}
}
