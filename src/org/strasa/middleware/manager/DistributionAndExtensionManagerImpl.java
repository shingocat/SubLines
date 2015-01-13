package org.strasa.middleware.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.EcotypeMapper;
import org.strasa.middleware.mapper.DistributionAndExtensionMapper;
import org.strasa.middleware.mapper.LocationMapper;
import org.strasa.middleware.mapper.ProgramMapper;
import org.strasa.middleware.mapper.StudySiteMapper;
import org.strasa.middleware.mapper.other.DistributionAndExtensionSummaryMapper;
import org.strasa.middleware.mapper.other.ReleaseInfoSummaryMapper;
import org.strasa.middleware.mapper.other.StudySummaryMapper;
import org.strasa.middleware.model.DistributionAndExtension;
import org.strasa.middleware.model.DistributionAndExtensionExample;
import org.strasa.middleware.model.Ecotype;
import org.strasa.middleware.model.Program;
import org.strasa.middleware.model.StudySite;
import org.strasa.web.browsestudy.view.model.StudySearchFilterModel;
import org.strasa.web.browsestudy.view.model.StudySearchResultModel;
import org.strasa.web.browsestudy.view.model.StudySummaryModel;
import org.strasa.web.distributionandextension.view.model.SummaryModel;
import org.strasa.web.releaseinfo.view.model.ReleaseInfoSummaryModel;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class DistributionAndExtensionManagerImpl {


	@WireVariable
	ConnectionFactory connectionFactory;

	public DistributionAndExtensionManagerImpl() {
		// TODO Auto-generated constructor stub
	}

	public void addDistributionAndExtension(DistributionAndExtension record) {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		DistributionAndExtensionMapper mapper = session.getMapper(DistributionAndExtensionMapper.class);

		try{
			record.setUserid(SecurityUtil.getDbUser().getId());
			mapper.insert(record);
			session.commit();

		}finally{
			session.close();
		}

	}

	public List<DistributionAndExtension> getAllDistributionAndExtension(){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		DistributionAndExtensionMapper mapper = session.getMapper(DistributionAndExtensionMapper.class);


		try{
			DistributionAndExtensionExample example = new DistributionAndExtensionExample();
			example.createCriteria().andUseridEqualTo(SecurityUtil.getDbUser().getId());
			List<DistributionAndExtension> distributionAndExtension = mapper.selectByExample(example);

			return distributionAndExtension;

		}finally{
			session.close();
		}

	}
	public DistributionAndExtension getDistributionAndExtensionById(int id){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		DistributionAndExtensionMapper mapper = session.getMapper(DistributionAndExtensionMapper.class);

		try{
			DistributionAndExtension distributionAndExtension = mapper.selectByPrimaryKey(id);

			return distributionAndExtension;

		}finally{
			session.close();
		}
	}
	
	@SuppressWarnings("null")
	public List<String> getAllDistributionAndExtensionAsString() {
		List<String> extData = new ArrayList<String>();;
		List<DistributionAndExtension> distributionAndExtensionList = getAllDistributionAndExtension();
		for(DistributionAndExtension e : distributionAndExtensionList){
			System.out.println(e.getDatasource());
			extData.add(e.getDatasource());
		}
		// TODO Auto-generated method stub
		return extData;
	}

	public void updateDistributionAndExtension(DistributionAndExtension value) {
		// TODO Auto-generated method stub

		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		DistributionAndExtensionMapper mapper = session.getMapper(DistributionAndExtensionMapper.class);

		try{	
			mapper.updateByPrimaryKey(value);
			session.commit();

		}finally{
			session.close();
		}

	}

	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		DistributionAndExtensionMapper mapper = session.getMapper(DistributionAndExtensionMapper.class);
		try{
			mapper.deleteByPrimaryKey(id);
			session.commit();
		}
		finally{
			session.close();
		}
	}
	
	public List<SummaryModel> getAreaSummaryGermplasmByYearandCountryExtension(){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		DistributionAndExtensionSummaryMapper mapper = session.getMapper(DistributionAndExtensionSummaryMapper.class);
		List<SummaryModel> s= new ArrayList<SummaryModel>();
		try{
			List<SummaryModel> toreturn= mapper.selectplantingareaSummaryGermplasmByYearandCountryExtension();

			return toreturn;

		}finally{
			session.close();
		}
	}
	
	public List<SummaryModel> getAreaSummaryGermplasmByYear(){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		DistributionAndExtensionSummaryMapper mapper = session.getMapper(DistributionAndExtensionSummaryMapper.class);
		List<SummaryModel> s= new ArrayList<SummaryModel>();
		try{
			List<SummaryModel> toreturn= mapper.selectplantingareaSummaryGermplasmByYear();

			return toreturn;

		}finally{
			session.close();
		}
	}
	
	public List<SummaryModel> getAreaSummaryGermplasmByCountryExtension(){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		DistributionAndExtensionSummaryMapper mapper = session.getMapper(DistributionAndExtensionSummaryMapper.class);
		List<SummaryModel> s= new ArrayList<SummaryModel>();
		try{
			List<SummaryModel> toreturn= mapper.selectplantingareaSummaryGermplasmByCountryExtension();

			return toreturn;

		}finally{
			session.close();
		}
	}
	
	
	public List<DistributionAndExtension> getProgramGermplasmByYear(String yearextension,
			Integer programid, String germplasmName) {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		DistributionAndExtensionMapper mapper = session.getMapper(DistributionAndExtensionMapper.class);
		try{
			DistributionAndExtensionExample example = new DistributionAndExtensionExample();
			example.createCriteria().andProgramidEqualTo(programid).andGermplasmnameEqualTo(germplasmName).andYearextensionEqualTo(yearextension); 
			List<DistributionAndExtension> toreturn = mapper.selectByExample(example);
			return toreturn;

		}finally{
			session.close();
		}
	}

	public List<DistributionAndExtension> getProgramGermplasmByCountry(
			String countryxtension, Integer programid, String germplasmName) {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		DistributionAndExtensionMapper mapper = session.getMapper(DistributionAndExtensionMapper.class);
		try{
			DistributionAndExtensionExample example = new DistributionAndExtensionExample();
			example.createCriteria().andProgramidEqualTo(programid).andGermplasmnameEqualTo(germplasmName).andCountryextensionEqualTo(countryxtension);
			List<DistributionAndExtension> toreturn = mapper.selectByExample(example);
			return toreturn;

		}finally{
			session.close();
		}
	}

	public List<DistributionAndExtension> getProgramGermplasmByYearandCountry(
			String yearextension, String countryxtension, Integer programid,
			String germplasmName) {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		DistributionAndExtensionMapper mapper = session.getMapper(DistributionAndExtensionMapper.class);
		try{
			DistributionAndExtensionExample example = new DistributionAndExtensionExample();
			example.createCriteria().andProgramidEqualTo(programid).andGermplasmnameEqualTo(germplasmName).andCountryextensionEqualTo(countryxtension).andYearextensionEqualTo(yearextension); 
			List<DistributionAndExtension> toreturn = mapper.selectByExample(example);
			return toreturn;

		}finally{
			session.close();
		}
	}

	public String[] getCategoryByCountry() {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		DistributionAndExtensionSummaryMapper mapper = session.getMapper(DistributionAndExtensionSummaryMapper.class);
		
		int i=0;
		try{
			List<SummaryModel> toreturn= mapper.selectCategoryByCountry();
			String[] program = new String[toreturn.size()];
			for(SummaryModel sm: toreturn){
				System.out.println(sm.getProgramName());
				program[i]=sm.getProgramName();
				i++;
			}
			return program;

		}finally{
			session.close();
		}
	}

	public String[] getCategoryByYear() {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		DistributionAndExtensionSummaryMapper mapper = session.getMapper(DistributionAndExtensionSummaryMapper.class);
		
		int i=0;
		try{
			List<SummaryModel> toreturn= mapper.selectCategoryByYear();
			String[] program = new String[toreturn.size()];
			for(SummaryModel sm: toreturn){
				System.out.println(sm.getProgramName());
				program[i]=sm.getProgramName();
				i++;
			}
			return program;

		}finally{
			session.close();
		}
	}

}