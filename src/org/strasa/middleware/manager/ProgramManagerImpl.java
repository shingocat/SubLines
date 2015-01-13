package org.strasa.middleware.manager;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.ProgramMapper;
import org.strasa.middleware.model.Program;
import org.strasa.middleware.model.ProgramExample;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class ProgramManagerImpl {

	@WireVariable
	ConnectionFactory connectionFactory;
	
	private int userid;
	
	
	public ProgramManagerImpl(){
		this.userid=SecurityUtil.getDbUser().getId();
	}
	
	public void addProgram(Program record){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ProgramMapper ProgramMapper = session.getMapper(ProgramMapper.class);
		
		record.setUserid(userid);
		try{
			ProgramMapper.insert(record);
			session.commit();
			
		}finally{
			session.close();
		}
		
	}
	public boolean isProgramExist(String name, int userId){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ProgramMapper ProgramMapper = session.getMapper(ProgramMapper.class);
		
		try{
			ProgramExample example= new ProgramExample();
			example.createCriteria().andUseridEqualTo(userid).andNameEqualTo(name);
			
			return (ProgramMapper.countByExample(example) != 0);
		}finally{
			session.close();
		}
	}
	public Program getProgramByName(String name, int userId){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ProgramMapper ProgramMapper = session.getMapper(ProgramMapper.class);
		
		try{
			ProgramExample example= new ProgramExample();
			example.createCriteria().andUseridEqualTo(userid).andNameEqualTo(name);
			
			return ProgramMapper.selectByExample(example).get(0);
		}finally{
			session.close();
		}
	}
	
	public void updateProgram(Program record){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ProgramMapper ProgramMapper = session.getMapper(ProgramMapper.class);
		
		try{
			ProgramMapper.updateByPrimaryKey(record);
			session.commit();
			
		}finally{
			session.close();
		}
		
	}
	
	public List<Program> getAllProgram(){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ProgramMapper ProgramMapper = session.getMapper(ProgramMapper.class);
		
		try{
			return ProgramMapper.selectByExample(null);
		}finally{
			session.close();
		}
	}
	
	public Program getProgramById(int id){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ProgramMapper ProgramMapper = session.getMapper(ProgramMapper.class);
		
		try{
			ProgramExample example= new ProgramExample();
			example.createCriteria().andIdEqualTo(id);
			
			return ProgramMapper.selectByExample(example).get(0);
		}finally{
			session.close();
		}
	}
	public List<Program> getProgramByUserId(){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ProgramMapper ProgramMapper = session.getMapper(ProgramMapper.class);
		
		try{
			ProgramExample example= new ProgramExample();
			example.createCriteria().andUseridEqualTo(userid);
			
			return ProgramMapper.selectByExample(example);
		}finally{
			session.close();
		}
	}
	
	public void deleteProgramById(Integer programId) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		ProgramMapper mapper = session.getMapper(ProgramMapper.class);
		 
		
		try{
			mapper.deleteByPrimaryKey(programId);
			session.commit();
		}
		finally{
			session.close();
		}
	}

}
