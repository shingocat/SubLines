package org.strasa.middleware.manager;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.UserDataFileMapper;
import org.strasa.middleware.model.UserDataFile;
import org.strasa.middleware.model.UserDataFileExample;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class UserDataFileManagerImpl {

	@WireVariable
	ConnectionFactory connectionFactory;
	public void addRecord(UserDataFile record){

		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		UserDataFileMapper mapper = session.getMapper(UserDataFileMapper.class);
		try{
				mapper.insert(record);
		}
		finally{
			session.commit();
			session.close();
		}
	}
	
	public void updateRecord(UserDataFile record){

		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		UserDataFileMapper mapper = session.getMapper(UserDataFileMapper.class);
		try{
				mapper.updateByPrimaryKey(record);
		}
		finally{
			session.commit();
			session.close();
		}
	}
	public void deleteRecord(UserDataFile record){

		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		UserDataFileMapper mapper = session.getMapper(UserDataFileMapper.class);
		try{
				mapper.deleteByPrimaryKey(record.getId());
		}
		finally{
			session.commit();
			session.close();
		}
	}
	
	public List<UserDataFile> getFileByStudyId(int studyid){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		UserDataFileMapper mapper = session.getMapper(UserDataFileMapper.class);
		try{
			UserDataFileExample example = new UserDataFileExample();
			example.createCriteria().andStudyidEqualTo(studyid);
			return mapper.selectByExample(example);
		}
		finally{
			session.close();
		}
	}
	
	public List<UserDataFile> getFileByFileName(String filename){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		UserDataFileMapper mapper = session.getMapper(UserDataFileMapper.class);
		try{
			UserDataFileExample example = new UserDataFileExample();
			example.createCriteria().andFilenameEqualTo(filename);
			return mapper.selectByExample(example);
		}
		finally{
			session.close();
		}
	}
	
	public List<UserDataFile> getFile(String filename, int studyid, int userid){
		SqlSession session =connectionFactory.sqlSessionFactory.openSession();
		UserDataFileMapper mapper = session.getMapper(UserDataFileMapper.class);
		try{
			UserDataFileExample example = new UserDataFileExample();
			example.createCriteria().andFilenameEqualTo(filename).andStudyidEqualTo(studyid).andUseridEqualTo(userid);
			return mapper.selectByExample(example);
		}
		finally{
			session.close();
		}
	}

	
}
