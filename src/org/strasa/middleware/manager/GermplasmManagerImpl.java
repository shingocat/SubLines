package org.strasa.middleware.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.GermplasmCharacteristicsMapper;
import org.strasa.middleware.mapper.GermplasmMapper;
import org.strasa.middleware.mapper.GermplasmTypeMapper;
import org.strasa.middleware.mapper.other.GermplasmBreederMapper;
import org.strasa.middleware.model.Germplasm;
import org.strasa.middleware.model.GermplasmExample;
import org.strasa.middleware.model.GermplasmType;
import org.strasa.web.uploadstudy.view.pojos.GermplasmDeepInfoModel;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.mysql.jdbc.StringUtils;

public class GermplasmManagerImpl {

	@WireVariable
	ConnectionFactory connectionFactory;

	public Germplasm getGermplasmByName(String value) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);

		try {
			GermplasmExample example = new GermplasmExample();
			example.createCriteria().andGermplasmnameEqualTo(value);
			if (mapper.countByExample(example) < 1)
				return null;
			return mapper.selectByExample(example).get(0);

		} finally {
			session.close();
		}
	}

	public Germplasm getGermplasmById(int id) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);
		try {
			return mapper.selectByPrimaryKey(id);
		} finally {
			session.close();
		}

	}
	
//	adding by QIN MAO for retrieve Introgression Line germplasm by Id
	public Germplasm getGermplasmByIdOfIntrogressionLine(int id)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);
		GermplasmExample example = new GermplasmExample();
		GermplasmTypeManagerImpl gtManagerImpl = new GermplasmTypeManagerImpl();
		List<GermplasmType> lstGermplasmType = gtManagerImpl.getAllGermplasmType();
		try
		{
			for(GermplasmType gt: lstGermplasmType)
			{
				if(gt.getGermplasmtype().equals("Pyramided line"))
				{
					example.or().andIdEqualTo(id).andGermplasmtypeidEqualTo(gt.getId());
				} else if(gt.getGermplasmtype().equals("NIL"))
				{
					example.or().andIdEqualTo(id).andGermplasmtypeidEqualTo(gt.getId());
				} else if(gt.getGermplasmtype().equals("IL"))
				{
					example.or().andIdEqualTo(id).andGermplasmtypeidEqualTo(gt.getId());
				} else if(gt.getGermplasmtype().equals("SSSL"))
				{
					example.or().andIdEqualTo(id).andGermplasmtypeidEqualTo(gt.getId());
				}
			}
			if(mapper.selectByExample(example).isEmpty())
				return null;
			return mapper.selectByExample(example).get(0);
		} finally
		{
			session.close();
		}
	}

	public HashMap<String, Germplasm> getGermplasmBatchAsMap(List<String> germplasmList) {

		SqlSession session = connectionFactory.sqlSessionFactory.openSession();

		try {
			GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);
			GermplasmExample example = new GermplasmExample();
			HashMap<String, Germplasm> returnVal = new HashMap<String, Germplasm>();
			example.createCriteria().andGermplasmnameIn(germplasmList);
			List<Germplasm> lst = mapper.selectByExample(example);
			for (Germplasm germ : lst) {
				returnVal.put(germ.getGermplasmname(), germ);
			}
			return returnVal;
		} finally {
			session.close();
		}

	}

	public List<Germplasm> getGermplasmBatch(List<String> germList) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();

		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);
		GermplasmExample example = new GermplasmExample();

		try {
			example.createCriteria().andGermplasmnameIn(germList);
			return mapper.selectByExample(example);

		} finally {

		}
	}

	public ArrayList<String> getGermplasmBatchAsString(List<String> germList) {
		List<Germplasm> lstGerm = getGermplasmBatch(germList);
		ArrayList<String> returnVal = new ArrayList<String>();

		for (Germplasm germ : lstGerm) {
			returnVal.add(germ.getGermplasmname());
			if(germ.getGermplasmname().equals("DR017x")) System.out.println("FOUND: DR017x");
		}
		return returnVal;
	}

	public List<Germplasm> getGermplasmListByName(String value) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);

		try {
			GermplasmExample example = new GermplasmExample();
			if (value.contains("%")) {
				example.createCriteria().andGermplasmnameLike(value);
			} else {
				example.createCriteria().andGermplasmnameEqualTo(value);
			}
			if (mapper.selectByExample(example).isEmpty())
				return null;
			return mapper.selectByExample(example);

		} finally {
			session.close();
		}
	}
//	adding by QIN MAO for retrieve Introgression Line Germplasm by value
	public List<Germplasm> getGermplasmListByNameOfIntrogressionLine(String value)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);
		GermplasmExample example = new GermplasmExample();
		GermplasmTypeManagerImpl gtManager = new GermplasmTypeManagerImpl();
		List<GermplasmType> lstGermplasmType =gtManager.getAllGermplasmType();
		
		try
		{
			value = "%" + value.trim() + "%";
			for(GermplasmType ge : lstGermplasmType)
			{
				if(ge.getGermplasmtype().equals("Pyramided line"))
				{
					example.or().andGermplasmnameLike(value).andGermplasmtypeidEqualTo(ge.getId());
				} else if(ge.getGermplasmtype().equals("NIL"))
				{
					example.or().andGermplasmnameLike(value).andGermplasmtypeidEqualTo(ge.getId());
				} else if(ge.getGermplasmtype().equals("IL"))
				{
					example.or().andGermplasmnameLike(value).andGermplasmtypeidEqualTo(ge.getId());
				} else if(ge.getGermplasmtype().equals("SSSL"))
				{
					example.or().andGermplasmnameLike(value).andGermplasmtypeidEqualTo(ge.getId());
				}
				
			}
			if(mapper.selectByExample(example).isEmpty())
				return null;
			return mapper.selectByExample(example);
			
		} finally
		{
			session.close();
		}
	}

	public List<Germplasm> getGermplasmListByUserID(int userid) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);

		try {
			GermplasmExample example = new GermplasmExample();
			example.createCriteria().andUseridEqualTo(userid);

			return mapper.selectByExample(example);
		} finally {
			session.close();
		}
	}
	
	public List<Germplasm> getGermplasmListByUserIdAndGermplasmTypeOfIntrogressionLine(int userid, List<Integer> germplasmTypes)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		try
		{
			GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);
			GermplasmExample example = new GermplasmExample();
			example.or().andUseridEqualTo(userid).andGermplasmtypeidIn(germplasmTypes);
			return mapper.selectByExample(example);
		} finally
		{
			session.close();
		}
	}

	public List<Germplasm> getGermplasmListByType(int id) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);

		try {
			GermplasmExample example = new GermplasmExample();
			example.createCriteria().andGermplasmtypeidEqualTo(id);
			if (mapper.selectByExample(example).isEmpty())
				return null;
			return mapper.selectByExample(example);
		} finally {
			session.close();
		}
	}

	public boolean isGermplasmExisting(String value) {
		if (this.getGermplasmByName(value) == null)
			return false;
		return true;
	}

	public void modifyGermplasm(GermplasmDeepInfoModel data) {
		StudyGermplasmManagerImpl studyMan = new StudyGermplasmManagerImpl();

		if (!StringUtils.isNullOrEmpty(data.getNewBreeder())) {
			if (data.getId() != null && data.equalsGermplasmNoBreeder(getGermplasmById(data.getId()))) {

				data.setBreeder(data.getBreeder() + ", " + data.getNewBreeder());

				new GermplasmManagerImpl().updateGermplasm(data);
			}
		}
		if (studyMan.isGermplasmConflict(data)) {

			int oldID = data.getId();
			data.setId(null);

			new GermplasmManagerImpl().addGermplasm(data);
			new StudyGermplasmManagerImpl().updateStudyGermplasmID(oldID, data.getId(), data.getUserid());
			new GermplasmCharacteristicMananagerImpl().addCharacteristic(data);
		} else {
			new GermplasmManagerImpl().updateGermplasm(data);
			new GermplasmCharacteristicMananagerImpl().addCharacteristic(data);
		}

	}

	public int addGermplasm(Germplasm record) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);
		try {
			mapper.insert(record);
			session.commit();
		} finally {
			session.close();
		}
		return record.getId();
	}

	public int updateGermplasm(Germplasm record) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);
		try {
			mapper.updateByPrimaryKey(record);
			session.commit();
		} finally {
			session.close();
		}
		return record.getId();
	}

	public void addGermplasmList(Collection<GermplasmDeepInfoModel> collection) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession(ExecutorType.BATCH);
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);
		try {

			for (Germplasm record : collection) {
				System.out.print(record.toString());

				if (record.getId() == null)
					mapper.insert(record);
				else {
					mapper.updateByPrimaryKey(record);

				}
			}
			session.commit();
		} finally {
			session.close();
		}
		return;
	}

	public void addGermplasmListNoRepeat(Collection<GermplasmDeepInfoModel> collection, Integer userid) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);
		try {

			for (GermplasmDeepInfoModel record : collection) {
				System.out.print(record.toString());

				GermplasmExample example = new GermplasmExample();
				example.createCriteria().andGermplasmnameEqualTo(record.getGermplasmname()).andUseridEqualTo(userid);
				if (record.getId() == null) {
					if (mapper.countByExample(example) > 0) {
						record.setId(mapper.selectByExample(example).get(0).getId());
						mapper.updateByPrimaryKey(record);
					} else {
						mapper.insert(record);
					}
				} else {
					modifyGermplasm(record);

				}
			}
			session.commit();
		} finally {
			session.close();
		}
		return;
	}

	public void updateBreeders(List<GermplasmDeepInfoModel> lstRecord) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession(ExecutorType.BATCH);
		GermplasmBreederMapper mapper = session.getMapper(GermplasmBreederMapper.class);
		try {
			for (GermplasmDeepInfoModel record : lstRecord) {

				Germplasm newRec = record;

				mapper.Update(newRec);

			}
			session.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
		}
		return;
	}

	public void addGermplasmBatch(List<GermplasmDeepInfoModel> lstRecord) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession(ExecutorType.BATCH);
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);
		GermplasmCharacteristicsMapper charmapper = session.getMapper(GermplasmCharacteristicsMapper.class);
		try {
			for (GermplasmDeepInfoModel rec : lstRecord) {

				Germplasm record = rec;
				if (record.getId() == null)
					mapper.insert(record);
				else {
					mapper.updateByPrimaryKey(record);

				}

			}
			session.commit();
		} finally {
			session.close();
		}
		return;
	}

	public void deleteGermplasmById(Integer id) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);
		try {
			mapper.deleteByPrimaryKey(id);
		} finally {
			session.commit();
			session.close();

		}
	}

	public ArrayList<String> getUnknownGermplasmList(ArrayList<String> arrayList) {
		// TODO Auto-generated method stub
		ArrayList<String> returnVal = new ArrayList<String>();

		return returnVal;
	}
// adding by QIN MAO for retrieve Introgression Line Germplasm by using user id
	public List<Germplasm> getGermplasmByUserIdOfIntrogressonLine(Integer userId)
    {
	    // TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		List<GermplasmType> lstGermplasmType = new GermplasmTypeManagerImpl().getAllGermplasmType();
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);
		GermplasmExample example = new GermplasmExample();
		try{
			for(GermplasmType gt : lstGermplasmType)
			{
				if(gt.getGermplasmtype().equals("Pyramided line"))
				{
					example.or().andUseridEqualTo(userId).andGermplasmtypeidEqualTo(gt.getId());
				} else if(gt.getGermplasmtype().equals("NIL"))
				{
					example.or().andUseridEqualTo(userId).andGermplasmtypeidEqualTo(gt.getId());
				} else if(gt.getGermplasmtype().equals("IL"))
				{
					example.or().andUseridEqualTo(userId).andGermplasmtypeidEqualTo(gt.getId());
				} else if(gt.getGermplasmtype().equals("SSSL"))
				{
					example.or().andUseridEqualTo(userId).andGermplasmtypeidEqualTo(gt.getId());
				}
			}
			List<Germplasm> lstGermplasm = mapper.selectByExample(example);
			if(lstGermplasm.isEmpty())
				return null;
			return lstGermplasm;
			
		}finally
		{
			session.close();
		}
		
    }
	
	public Germplasm getGermplasmByNameOfIntrogressionLine(String germplasmname)
    {
	    // TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		List<GermplasmType> lstGermplasmType = new GermplasmTypeManagerImpl().getAllGermplasmType();
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);
		GermplasmExample example = new GermplasmExample();
		try{
			for(GermplasmType gt : lstGermplasmType)
			{
				if(gt.getGermplasmtype().equals("Pyramided line"))
				{
					example.or().andGermplasmnameEqualTo(germplasmname).andGermplasmtypeidEqualTo(gt.getId());
				} else if(gt.getGermplasmtype().equals("NIL"))
				{
					example.or().andGermplasmnameEqualTo(germplasmname).andGermplasmtypeidEqualTo(gt.getId());
				} else if(gt.getGermplasmtype().equals("IL"))
				{
					example.or().andGermplasmnameEqualTo(germplasmname).andGermplasmtypeidEqualTo(gt.getId());
				} else if(gt.getGermplasmtype().equals("SSSL"))
				{
					example.or().andGermplasmnameEqualTo(germplasmname).andGermplasmtypeidEqualTo(gt.getId());
				}
			}
			List<Germplasm> lstGermplasm = mapper.selectByExample(example);
			if(lstGermplasm.isEmpty())
				return null;
			return lstGermplasm.get(0);
			
		}finally
		{
			session.close();
		}
    }
	
//	adding by QIN MAO for retrieve Introgression Line Germplasm by value
	public List<Germplasm> getGermplasmListByNameAndUserIdOfIntrogressionLine(String value,Integer userId)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		GermplasmMapper mapper = session.getMapper(GermplasmMapper.class);
		GermplasmExample example = new GermplasmExample();
		GermplasmTypeManagerImpl gtManager = new GermplasmTypeManagerImpl();
		List<GermplasmType> lstGermplasmType =gtManager.getAllGermplasmType();
		
		try
		{
			value = "%" + value.trim() + "%";
			for(GermplasmType ge : lstGermplasmType)
			{
				if(ge.getGermplasmtype().equals("Pyramided line"))
				{
					example.or().andGermplasmnameLike(value).andGermplasmtypeidEqualTo(ge.getId()).andUseridEqualTo(userId);
				} else if(ge.getGermplasmtype().equals("NIL"))
				{
					example.or().andGermplasmnameLike(value).andGermplasmtypeidEqualTo(ge.getId()).andUseridEqualTo(userId);
				} else if(ge.getGermplasmtype().equals("IL"))
				{
					example.or().andGermplasmnameLike(value).andGermplasmtypeidEqualTo(ge.getId()).andUseridEqualTo(userId);
				} else if(ge.getGermplasmtype().equals("SSSL"))
				{
					example.or().andGermplasmnameLike(value).andGermplasmtypeidEqualTo(ge.getId()).andUseridEqualTo(userId);
				}
				
			}
			return mapper.selectByExample(example);
			
		} finally
		{
			session.close();
		}
	}
}
