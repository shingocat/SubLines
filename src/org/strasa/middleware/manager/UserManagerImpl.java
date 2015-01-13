package org.strasa.middleware.manager;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.DbUserMapper;
import org.strasa.middleware.model.DbUser;
import org.strasa.middleware.model.DbUserExample;
import org.zkoss.zk.ui.select.annotation.WireVariable;


public class UserManagerImpl {
	@WireVariable
	ConnectionFactory connectionFactory;
	
	public void addUser(DbUser record){
		 
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		DbUserMapper DbUserMapper = session.getMapper(DbUserMapper.class);
		try{
			DbUserMapper.insert(record);
			session.commit();
			
		}finally{
			session.close();
		}
		
	}
	
	public void updateUser(DbUser record){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		DbUserMapper DbUserMapper = session.getMapper(DbUserMapper.class);
		try{
	
			DbUserMapper.updateByPrimaryKey(record);
			session.commit();
		}finally{
			session.close();
		}
		
	}
	
	
	public void deleteUser(DbUser record){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		DbUserMapper DbUserMapper = session.getMapper(DbUserMapper.class);
		try{
			DbUserMapper.deleteByPrimaryKey(record.getId());
			session.commit();
		}finally{
			session.close();
		}
		
	}
	
	public List<DbUser> getUser(String username, String password){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		DbUserMapper DbUserMapper = session.getMapper(DbUserMapper.class);
		try{
			DbUserExample example = new DbUserExample();
			example.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password);
			return  DbUserMapper.selectByExample(example);
			
		}finally{
			session.close();
		}
		
	}
	public DbUser getUserById(){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		DbUserMapper DbUserMapper = session.getMapper(DbUserMapper.class);
		try{
			return  DbUserMapper.selectByPrimaryKey(SecurityUtil.getDbUser().getId());
			
		}finally{
			session.close();
		}
		
	}

	public List<DbUser> getAllRegisteredUser() {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		DbUserMapper DbUserMapper = session.getMapper(DbUserMapper.class);
		try{
			DbUserExample example = new DbUserExample();
			example.setOrderByClause("status");
			return  DbUserMapper.selectByExample(example);
			
		}finally{
			session.close();
		}
		
	}
	
	public DbUser getUserByName(String name){

		System.out.println("Testing");
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		try{
			DbUserMapper dbUserMapper = session.getMapper(DbUserMapper.class);
			DbUserExample example= new DbUserExample();
			example.createCriteria().andUsernameEqualTo(name);

			if (dbUserMapper.selectByExample(example).isEmpty())
				return null;
			return dbUserMapper.selectByExample(example).get(0);

		}finally{
			session.close();
		}
	}

	

}
