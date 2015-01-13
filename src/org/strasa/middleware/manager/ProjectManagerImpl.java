package org.strasa.middleware.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.ProjectMapper;
import org.strasa.middleware.model.Program;
import org.strasa.middleware.model.Project;
import org.strasa.middleware.model.ProjectExample;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class ProjectManagerImpl {

	@WireVariable
	ConnectionFactory connectionFactory;
	private int userid;
	
	
	public ProjectManagerImpl(){
		this.userid=SecurityUtil.getDbUser().getId();
	}
	
	public void addProject(Project record){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ProjectMapper projectMapper = session.getMapper(ProjectMapper.class);
		record.setUserid(userid);
		try{
			projectMapper.insert(record);
			session.commit();
			
		}finally{
			session.close();
		}
		
	}
	
	
	public ArrayList<Project> getProjectList(Program selected){
	
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ProjectMapper projectMapper = session.getMapper(ProjectMapper.class);
		try{
			ProjectExample example = new ProjectExample();
			example.createCriteria().andUseridEqualTo(userid).andProgramidEqualTo(selected.getId());
			return new ArrayList<Project>(projectMapper.selectByExample(example));
		}finally{
			session.close();
		}
		
		
	}
	
	public void updateProject(Project record){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ProjectMapper projectMapper = session.getMapper(ProjectMapper.class);
		
		try{
			projectMapper.updateByPrimaryKey(record);
			session.commit();
			
		}finally{
			session.close();
		}
		
	}
	
	public List<Project> getAllProject(){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ProjectMapper projectMapper = session.getMapper(ProjectMapper.class);
		
		try{
			return projectMapper.selectByExample(null);
		}finally{
			session.close();
		}
	}
	
	public Project getProjectById(int id){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ProjectMapper projectMapper = session.getMapper(ProjectMapper.class);
		
		try{
			ProjectExample example= new ProjectExample();
			example.createCriteria().andIdEqualTo(id);
			
			return projectMapper.selectByExample(example).get(0);
		}finally{
			session.close();
		}
	}
	
	public List<Project> getProjectByUserId(){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ProjectMapper projectMapper = session.getMapper(ProjectMapper.class);
		
		try{
			ProjectExample example= new ProjectExample();
			example.createCriteria().andUseridEqualTo(userid);
			
			return projectMapper.selectByExample(example);
		}finally{
			session.close();
		}
	}
	public Project getProjectByName(String name,int id){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ProjectMapper projectMapper = session.getMapper(ProjectMapper.class);
		
		try{
			ProjectExample example= new ProjectExample();
			example.createCriteria().andUseridEqualTo(id).andNameEqualTo(name);
			
			return projectMapper.selectByExample(example).get(0);
		}finally{
			session.close();
		}
	}
	
	public boolean isProjectExist(String name,int userID, int programID){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ProjectMapper projectMapper = session.getMapper(ProjectMapper.class);
		
		try{
			ProjectExample example= new ProjectExample();
			example.createCriteria().andUseridEqualTo(userid).andProgramidEqualTo(programID).andNameEqualTo(name);
			
			return (projectMapper.countByExample(example) != 0 );
		}finally{
			session.close();
		}
	}


	public List<Project>  getProjectByProgramId(Integer programId) {
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ProjectMapper projectMapper = session.getMapper(ProjectMapper.class);
		
		try{
			ProjectExample example= new ProjectExample();
			example.createCriteria().andProgramidEqualTo(programId);
			
			return projectMapper.selectByExample(example);
		}finally{
			session.close();
		}
	}


	public void deleteProjectById(Integer projectId) {
		// TODO Auto-generated method stub
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		ProjectMapper mapper = session.getMapper(ProjectMapper.class);
		
		try{
			mapper.deleteByPrimaryKey(projectId);
			session.commit();
		}finally{
			session.close();
		}
	}


}
