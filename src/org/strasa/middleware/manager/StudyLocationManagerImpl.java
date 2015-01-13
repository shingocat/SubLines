package org.strasa.middleware.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.LocationMapper;
import org.strasa.middleware.mapper.StudyLocationMapper;
import org.strasa.middleware.mapper.StudySiteMapper;
import org.strasa.middleware.model.Location;
import org.strasa.middleware.model.LocationExample;
import org.strasa.middleware.model.StudyLocation;
import org.strasa.middleware.model.StudyLocationExample;
import org.strasa.middleware.model.StudyRawDataByDataColumn;
import org.strasa.middleware.model.StudySite;
import org.strasa.middleware.model.StudySiteExample;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class StudyLocationManagerImpl {

	@WireVariable
	ConnectionFactory connectionFactory;

	private boolean isRaw = true;

	public StudyLocationManagerImpl(boolean isRaw) {
		this.isRaw = isRaw;
	}

	public StudyLocationManagerImpl() {
		// TODO Auto-generated constructor stub
	}

	public void addStudyLocation(StudyLocation record) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyLocationMapper studyLocationMapper = session.getMapper(StudyLocationMapper.class);

		try {
			studyLocationMapper.insert(record);
			session.commit();

		} finally {
			session.close();
		}

	}

	public List<Location> getLocationsFromStudySite(Integer studyID, Integer dataset) {

		List<Location> lstLocations = new ArrayList<Location>();
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		LocationMapper studyLocationMapper = session.getMapper(LocationMapper.class);
		StudySiteMapper studySiteMapper = session.getMapper(StudySiteMapper.class);
		Set uniqueEntries = new HashSet();
		Set uniqueEntriesLoc = new HashSet();
		try {
			StudySiteExample siteEx = new StudySiteExample();
			if (dataset != null)
				siteEx.createCriteria().andStudyidEqualTo(studyID).andDatasetEqualTo(dataset);
			else
				siteEx.createCriteria().andStudyidEqualTo(studyID);
			List<StudySite> lstSites = studySiteMapper.selectByExample(siteEx);

			for (StudySite site : lstSites) {
				Location locData = studyLocationMapper.selectByPrimaryKey(site.getLocationid());
				if (uniqueEntries.add(site.getId()) && uniqueEntriesLoc.add(locData.getId())) {

					lstLocations.add(locData);
				}

			}

			return lstLocations;
		} finally {
			session.close();
		}

	}

	public void addStudyLocation(ArrayList<StudyLocation> records) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyLocationMapper studyLocationMapper = session.getMapper(StudyLocationMapper.class);

		try {
			for (StudyLocation record : records) {
				studyLocationMapper.insert(record);
			}
			session.commit();

		} finally {
			session.close();
		}

	}

	public boolean isStudyLocationExist(int studyid, int locationid) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyLocationMapper studyLocationMapper = session.getMapper(StudyLocationMapper.class);

		try {
			StudyLocationExample example = new StudyLocationExample();
			example.createCriteria().andStudyidEqualTo(studyid).andLocationidEqualTo(locationid);

			return (!studyLocationMapper.selectByExample(example).isEmpty());
		} finally {

		}
	}

	public boolean LocationExist(Location location) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		LocationMapper studyLocationMapper = session.getMapper(LocationMapper.class);

		try {
			LocationExample example = new LocationExample();
			example.createCriteria().andAltitudeEqualTo(location.getAltitude()).andCountryEqualTo(location.getCountry());

			return (!studyLocationMapper.selectByExample(example).isEmpty());
		} finally {

		}
	}

	public void updateStudyLocation(List<Location> lstKnowLocations, int studyid) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyLocationMapper studyLocationMapper = session.getMapper(StudyLocationMapper.class);
		LocationMapper locationMapper = session.getMapper(LocationMapper.class);
		StudyLocationExample ex = new StudyLocationExample();
		ex.createCriteria().andStudyidEqualTo(studyid);
		studyLocationMapper.deleteByExample(ex);
		try {
			for (Location record : lstKnowLocations) {
				if (record.getId() == null) {
					locationMapper.insert(record);
				} else {
					locationMapper.updateByPrimaryKey(record);
				}
				StudyLocation newData = new StudyLocation();
				newData.setLocationid(record.getId());
				newData.setStudyid(studyid);
				studyLocationMapper.insert(newData);
			}
			session.commit();

		} finally {
			session.close();
		}

	}

	public List<StudyLocation> getStudyLocationsByLocId(Integer id) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyLocationMapper studyLocationMapper = session.getMapper(StudyLocationMapper.class);
		try {
			StudyLocationExample example = new StudyLocationExample();
			example.createCriteria().andLocationidEqualTo(id);
			List<StudyLocation> studyLocations = studyLocationMapper.selectByExample(example);
			return studyLocations;
		} finally {
			session.close();
		}
	}

	public List<StudyLocation> getAllStudyLocations(int studyId) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyLocationMapper studyLocationMapper = session.getMapper(StudyLocationMapper.class);
		try {
			List<StudyLocation> studyLocations = studyLocationMapper.selectByExample(null);
			return studyLocations;
		} finally {
			session.close();
		}

	}

	public List<StudyLocation> getStudyLocationsById(int studyId) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyLocationMapper studyLocationMapper = session.getMapper(StudyLocationMapper.class);
		try {
			StudyLocationExample example = new StudyLocationExample();
			example.createCriteria().andStudyidEqualTo(studyId);
			List<StudyLocation> studyLocations = studyLocationMapper.selectByExample(example);
			return studyLocations;
		} finally {
			session.close();
		}

	}

	public List<StudyLocation> getUnknownStudyLocations(int studyId) {
		// TODO Auto-generated method stub
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyLocationMapper studyLocationMapper = session.getMapper(StudyLocationMapper.class);
		try {
			List<StudyLocation> studyLocations = studyLocationMapper.selectByExample(null);
			return studyLocations;
		} finally {
			session.close();
		}

	}

	private void addEmptyRecordOnStudyLocation(int studyId) {
		// TODO Auto-generated method stub
		StudyLocation record = new StudyLocation();
		// record.setId(studyId);
		record.setStudyid(studyId);
		addStudyLocation(record);
		System.out.println("Added Empty Record on Location");
	}

	public List<List<Location>> initializeStudyLocations(int studyId) {
		// TODO Auto-generated method stub
		List<List<Location>> returnVal = new ArrayList<List<Location>>();
		List<Location> lstKnownLocations = new ArrayList<Location>();
		List<Location> lstUnKnownLocations = new ArrayList<Location>();
		ArrayList<StudyRawDataByDataColumn> studyList = getStudyLocationByStudy(studyId);
		LocationManagerImpl locMan = new LocationManagerImpl();
		System.out.println("STUDYLIST : " + studyList.size());
		for (StudyRawDataByDataColumn s : studyList) {
			System.out.println(s.toString());
			if (locMan.getLocationByLocationName(s.getDatavalue()) == null) {
				Location location = new Location();
				location.setLocationname(s.getDatavalue());
				lstUnKnownLocations.add(location);
			} else
				lstKnownLocations.add(locMan.getLocationByLocationName(s.getDatavalue()));
		}
		returnVal.add(lstKnownLocations);
		returnVal.add(lstUnKnownLocations);
		return returnVal;

	}

	public List<StudyLocation> initializeUnknownStudyLocations(int studyId) {
		// TODO Auto-generated method stub
		List<StudyLocation> studyLocations = getAllStudyLocations(studyId);
		if (studyLocations.isEmpty()) {
			try {
				ArrayList<StudyRawDataByDataColumn> studyList = getStudyLocationByStudy(studyId);
				for (StudyRawDataByDataColumn s : studyList) {
					StudyLocation record = new StudyLocation();
					record.setStudyid(s.getStudyid());
					record.setLocationid(new LocationManagerImpl().getLocationByLocationName(s.getDatavalue()).getId());

					addStudyLocation(record);
					System.out.println("added" + s.getDatavalue() + " to study id: " + s.getStudyid());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (getAllStudyLocations(studyId).isEmpty())
				addEmptyRecordOnStudyLocation(studyId);
		}
		return getAllStudyLocations(studyId);

	}

	public ArrayList<StudyRawDataByDataColumn> getStudyLocationByStudy(int studyId) {
		StudyRawDataManagerImpl studyRawDataManagerImpl = new StudyRawDataManagerImpl(isRaw);
		return (ArrayList<StudyRawDataByDataColumn>) studyRawDataManagerImpl.getStudyRawDataColumn(studyId, "Location");

	}

	public void removeLocationByStudyId(Integer id) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudyLocationMapper studySiteMapper = session.getMapper(StudyLocationMapper.class);

		try {
			StudyLocationExample example = new StudyLocationExample();
			example.createCriteria().andStudyidEqualTo(id);
			studySiteMapper.deleteByExample(example);
			session.commit();
		} finally {
			session.close();

		}

	}

}
