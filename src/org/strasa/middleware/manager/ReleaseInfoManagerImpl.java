package org.strasa.middleware.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.EcotypeMapper;
import org.strasa.middleware.mapper.ReleaseInfoMapper;
import org.strasa.middleware.mapper.LocationMapper;
import org.strasa.middleware.mapper.ProgramMapper;
import org.strasa.middleware.mapper.StudySiteMapper;
import org.strasa.middleware.mapper.other.ReleaseInfoSummaryMapper;
import org.strasa.middleware.mapper.other.StudySummaryMapper;
import org.strasa.middleware.model.Ecotype;
import org.strasa.middleware.model.ReleaseInfo;
import org.strasa.middleware.model.ReleaseInfoExample;
import org.strasa.middleware.model.Program;
import org.strasa.middleware.model.ReleaseInfo;
import org.strasa.middleware.model.StudySite;
import org.strasa.web.browsestudy.view.model.StudySearchFilterModel;
import org.strasa.web.browsestudy.view.model.StudySearchResultModel;
import org.strasa.web.releaseinfo.view.model.ReleaseInfoListModel;
import org.strasa.web.releaseinfo.view.model.ReleaseInfoSummaryModel;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class ReleaseInfoManagerImpl {


	@WireVariable
	ConnectionFactory connectionFactory;

	public ReleaseInfoManagerImpl() {
		// TODO Auto-generated constructor stub
	}

	public void addReleaseInfo(ReleaseInfo record) {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ReleaseInfoMapper mapper = session.getMapper(ReleaseInfoMapper.class);

		try{
			record.setUserid(SecurityUtil.getDbUser().getId());
			mapper.insert(record);
			session.commit();

		}finally{
			session.close();
		}

	}

	public List<ReleaseInfo> getAllReleaseInfo(){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ReleaseInfoMapper mapper = session.getMapper(ReleaseInfoMapper.class);


		try{
			ReleaseInfoExample example = new ReleaseInfoExample();
			example.createCriteria().andUseridEqualTo(SecurityUtil.getDbUser().getId());
			List<ReleaseInfo> ReleaseInfo = mapper.selectByExample(example);

			return ReleaseInfo;

		}finally{
			session.close();
		}

	}
	public ReleaseInfo getReleaseInfoById(int id){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ReleaseInfoMapper mapper = session.getMapper(ReleaseInfoMapper.class);

		try{
			ReleaseInfo ReleaseInfo = mapper.selectByPrimaryKey(id);

			return ReleaseInfo;

		}finally{
			session.close();
		}
	}
	
	@SuppressWarnings("null")
	public List<String> getAllReleaseInfoAsString() {
		List<String> extData = new ArrayList<String>();;
		List<ReleaseInfo> ReleaseInfoList = getAllReleaseInfo();
		for(ReleaseInfo e : ReleaseInfoList){
			System.out.println(e.getDatasource());
			extData.add(e.getDatasource());
		}
		// TODO Auto-generated method stub
		return extData;
	}

	public void updateReleaseInfo(ReleaseInfo value) {
		// TODO Auto-generated method stub

		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ReleaseInfoMapper mapper = session.getMapper(ReleaseInfoMapper.class);

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
		ReleaseInfoMapper mapper = session.getMapper(ReleaseInfoMapper.class);
		try{
			mapper.deleteByPrimaryKey(id);
			session.commit();
		}
		finally{
			session.close();
		}
	}
	
	public List<ReleaseInfoSummaryModel> getNoOfVarietyReleaseByCountryAndYear(){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ReleaseInfoSummaryMapper mapper = session.getMapper(ReleaseInfoSummaryMapper.class);
		List<ReleaseInfoSummaryModel> s= new ArrayList<ReleaseInfoSummaryModel>();
		try{
			List<ReleaseInfoSummaryModel> toreturn= mapper.selectNoOfVarietyReleaseByCountryAndYear();
			for(ReleaseInfoSummaryModel sm: toreturn){
				sm.setGermplasmVarietyNames(mapper.selectVarietyNamesOfVarietyReleaseByCountryAndYear(sm.getCountryrelease(),sm.getYearrelease(),  sm.getProgramid()));
			}
			return toreturn;

		}finally{
			session.close();
		}
	}
	
	public List<ReleaseInfoSummaryModel> getNoOfVarietyReleaseByCountryRelease(){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ReleaseInfoSummaryMapper mapper = session.getMapper(ReleaseInfoSummaryMapper.class);
		List<ReleaseInfoSummaryModel> s= new ArrayList<ReleaseInfoSummaryModel>();
		try{
			List<ReleaseInfoSummaryModel> toreturn= mapper.selectNoOfVarietyReleaseByCountryRelease();
			for(ReleaseInfoSummaryModel sm: toreturn){
				sm.setGermplasmVarietyNames(mapper.selectVarietyNamesOfVarietyReleaseByCountry(sm.getCountryrelease(), sm.getProgramid()));
			}
			return toreturn;

		}finally{
			session.close();
		}
	}
	
	@SuppressWarnings("null")
	public String[] getProgramList(){
		
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ReleaseInfoSummaryMapper mapper = session.getMapper(ReleaseInfoSummaryMapper.class);
		
		int i=0;
		try{
			List<ReleaseInfoSummaryModel> toreturn= mapper.selectProgramList();
			String[] program = new String[toreturn.size()];
			for(ReleaseInfoSummaryModel sm: toreturn){
				program[i]=sm.getProgramName();
				System.out.println(sm.getProgramName());
				i++;
			}
			return program;

		}finally{
			session.close();
		}
	}
	
	@SuppressWarnings("null")
	public String[] getProgramListWithCountry(){
		
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ReleaseInfoSummaryMapper mapper = session.getMapper(ReleaseInfoSummaryMapper.class);
		
		int i=0;
		try{
			List<ReleaseInfoSummaryModel> toreturn= mapper.selectProgramListWithCountry();
			String[] program = new String[toreturn.size()];
			for(ReleaseInfoSummaryModel sm: toreturn){
				program[i]=sm.getProgramName();
				i++;
			}
			return program;

		}finally{
			session.close();
		}
	}
	
	public List<ReleaseInfoSummaryModel> getNoOfVarietyReleaseByYear(){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ReleaseInfoSummaryMapper mapper = session.getMapper(ReleaseInfoSummaryMapper.class);
		try{
			List<ReleaseInfoSummaryModel> toreturn = mapper.selectNoOfVarietyReleaseByYear();
			for(ReleaseInfoSummaryModel sm: toreturn){
				sm.setGermplasmVarietyNames(mapper.selectVarietyNamesOfVarietyReleaseByYear(sm.getYearrelease(), sm.getProgramid()));
			}
			return toreturn;

		}finally{
			session.close();
		}
	}

	
	
	public List<ReleaseInfo> getVarietyReleaseByCountryRelease(
			String year, String country, Integer programid, String germplasmName) {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ReleaseInfoMapper mapper = session.getMapper(ReleaseInfoMapper.class);
		try{
			ReleaseInfoExample example = new ReleaseInfoExample();
			example.createCriteria().andProgramidEqualTo(programid).andCountryreleaseEqualTo(country); //search by country only
			List<ReleaseInfo> toreturn = mapper.selectByExample(example);
			return toreturn;

		}finally{
			session.close();
		}
	}
	
	public List<ReleaseInfo> getVarietyReleaseByYear(
			String year, String country, Integer programid, String germplasmName) {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ReleaseInfoMapper mapper = session.getMapper(ReleaseInfoMapper.class);
		try{
			ReleaseInfoExample example = new ReleaseInfoExample();
			example.createCriteria().andProgramidEqualTo(programid).andYearreleaseEqualTo(year); //search by year only
			List<ReleaseInfo> toreturn = mapper.selectByExample(example);
			return toreturn;

		}finally{
			session.close();
		}
	}
	
	public List<ReleaseInfo> getVarietyReleaseByCountryAndYear(
			String year, String country, Integer programid, String germplasmName) {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ReleaseInfoMapper mapper = session.getMapper(ReleaseInfoMapper.class);
		try{
			ReleaseInfoExample example = new ReleaseInfoExample();
			example.createCriteria().andProgramidEqualTo(programid).andCountryreleaseEqualTo(country).andYearreleaseEqualTo(year); 
			List<ReleaseInfo> toreturn = mapper.selectByExample(example);
			return toreturn;

		}finally{
			session.close();
		}
	}
	
	public List<ReleaseInfo> getReleaseInfoByNoOfVarietyReleaseByCountryRelease(
			String year, String country, Integer programid, String germplasmName) {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ReleaseInfoMapper mapper = session.getMapper(ReleaseInfoMapper.class);
		try{
			ReleaseInfoExample example = new ReleaseInfoExample();
			example.createCriteria().andProgramidEqualTo(programid).andGermplasmnameEqualTo(germplasmName).andCountryreleaseEqualTo(country); //search by country only
			List<ReleaseInfo> toreturn = mapper.selectByExample(example);
			return toreturn;

		}finally{
			session.close();
		}
	}
	
	public List<ReleaseInfo> getReleaseInfoByNoOfVarietyReleaseByYearRelease(
			String year, String country, Integer programid, String germplasmName) {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ReleaseInfoMapper mapper = session.getMapper(ReleaseInfoMapper.class);
		try{
			ReleaseInfoExample example = new ReleaseInfoExample();
			example.createCriteria().andProgramidEqualTo(programid).andGermplasmnameEqualTo(germplasmName).andYearreleaseEqualTo(year); //search by year only
			List<ReleaseInfo> toreturn = mapper.selectByExample(example);
			return toreturn;

		}finally{
			session.close();
		}
	}
	
	public List<ReleaseInfo> getReleaseInfoByNoOfVarietyReleaseByYearAndCountryRelease(
			String year, String country, Integer programid, String germplasmName) {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ReleaseInfoMapper mapper = session.getMapper(ReleaseInfoMapper.class);
		try{
			ReleaseInfoExample example = new ReleaseInfoExample();
			example.createCriteria().andProgramidEqualTo(programid).andGermplasmnameEqualTo(germplasmName).andCountryreleaseEqualTo(country).andYearreleaseEqualTo(year); 
			List<ReleaseInfo> toreturn = mapper.selectByExample(example);
			return toreturn;

		}finally{
			session.close();
		}
	}

	public List<ReleaseInfo> getProgramGermplasmByYear(String yearrelease,
			Integer programid, String germplasmName) {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ReleaseInfoMapper mapper = session.getMapper(ReleaseInfoMapper.class);
		try{
			ReleaseInfoExample example = new ReleaseInfoExample();
			example.createCriteria().andProgramidEqualTo(programid).andGermplasmnameEqualTo(germplasmName).andYearreleaseEqualTo(yearrelease); 
			List<ReleaseInfo> toreturn = mapper.selectByExample(example);
			return toreturn;

		}finally{
			session.close();
		}
	}

	public List<ReleaseInfo> getProgramGermplasmByCountry(
			String countryrelease, Integer programid, String germplasmName) {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ReleaseInfoMapper mapper = session.getMapper(ReleaseInfoMapper.class);
		try{
			ReleaseInfoExample example = new ReleaseInfoExample();
			example.createCriteria().andProgramidEqualTo(programid).andGermplasmnameEqualTo(germplasmName).andCountryreleaseEqualTo(countryrelease);
			List<ReleaseInfo> toreturn = mapper.selectByExample(example);
			return toreturn;

		}finally{
			session.close();
		}
	}

	public List<ReleaseInfo> getProgramGermplasmByYearandCountry(
			String yearrelease, String countryxtension, Integer programid,
			String germplasmName) {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ReleaseInfoMapper mapper = session.getMapper(ReleaseInfoMapper.class);
		try{
			ReleaseInfoExample example = new ReleaseInfoExample();
			example.createCriteria().andProgramidEqualTo(programid).andGermplasmnameEqualTo(germplasmName).andCountryreleaseEqualTo(countryxtension).andYearreleaseEqualTo(yearrelease); 
			List<ReleaseInfo> toreturn = mapper.selectByExample(example);
			return toreturn;

		}finally{
			session.close();
		}
	}

}
