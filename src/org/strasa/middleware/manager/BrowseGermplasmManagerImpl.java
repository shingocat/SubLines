package org.strasa.middleware.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.GermplasmMapper;
import org.strasa.middleware.model.Germplasm;
import org.strasa.middleware.model.GermplasmExample;
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
