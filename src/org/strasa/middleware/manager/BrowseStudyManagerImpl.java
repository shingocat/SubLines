package org.strasa.middleware.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.LocationMapper;
import org.strasa.middleware.mapper.ProgramMapper;
import org.strasa.middleware.mapper.ProjectMapper;
import org.strasa.middleware.mapper.StudyDerivedDataMapper;
import org.strasa.middleware.mapper.StudyLocationMapper;
import org.strasa.middleware.mapper.StudyMapper;
import org.strasa.middleware.mapper.StudyRawDataMapper;
import org.strasa.middleware.mapper.StudyTypeMapper;
import org.strasa.middleware.mapper.other.StudySummaryMapper;
import org.strasa.middleware.model.Location;
import org.strasa.middleware.model.LocationExample;
import org.strasa.middleware.model.Program;
import org.strasa.middleware.model.ProgramExample;
import org.strasa.middleware.model.Project;
import org.strasa.middleware.model.ProjectExample;
import org.strasa.middleware.model.Study;
import org.strasa.middleware.model.StudyDataColumn;
import org.strasa.middleware.model.StudyDerivedData;
import org.strasa.middleware.model.StudyDerivedDataExample;
import org.strasa.middleware.model.StudyExample;
import org.strasa.middleware.model.StudyLocation;
import org.strasa.middleware.model.StudyLocationExample;
import org.strasa.middleware.model.StudyRawData;
import org.strasa.middleware.model.StudyRawDataExample;
import org.strasa.middleware.model.StudyType;
import org.strasa.middleware.model.StudyTypeExample;
import org.strasa.web.browsestudy.view.model.StudyDataColumnModel;
import org.strasa.web.browsestudy.view.model.StudySearchFilterModel;
import org.strasa.web.browsestudy.view.model.StudySearchResultModel;
import org.strasa.web.browsestudy.view.model.StudySummaryModel;
import org.zkoss.zk.ui.select.annotation.WireVariable;

public class BrowseStudyManagerImpl {

	@WireVariable
	ConnectionFactory connectionFactory;

	public List<StudySummaryModel> getStudySummary() {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		StudySummaryMapper mapper = session.getMapper(StudySummaryMapper.class);
		List<StudySummaryModel> s = new ArrayList<StudySummaryModel>();
		try {

			List<StudySummaryModel> distinctProgram = mapper.selectDistinctProgram(SecurityUtil.getDbUser().getId());
			for (StudySummaryModel p : distinctProgram) {
				StudySummaryModel rec = new StudySummaryModel();
				rec.setProgramId(p.getProgramId());
				rec.setProgramName(p.getProgramName());
				List<StudySummaryModel> projectCount = mapper.selectDistinctProjectByProgramId(p.getProgramId());
				rec.setProjectCount(projectCount.size());
				List<StudySummaryModel> studyCount = mapper.countStudyByProgram(p.getProgramId(), SecurityUtil.getDbUser().getId());
				rec.setStudyCount(studyCount.get(0).getStudyCount());

				// count StypeType PVS
				rec.setStudySSSL(1);
				List<StudySummaryModel> pvs = mapper.selectCountOfStudyByStudyType(p.getProgramId(), rec.getStudySSSL(), SecurityUtil.getDbUser().getId());
				rec.setCountStudySSSL(pvs.get(0).getCountStudyTypeId());

				// count StypeType OYT
				rec.setStudyPL(2);
				List<StudySummaryModel> oyt = mapper.selectCountOfStudyByStudyType(p.getProgramId(), rec.getStudyPL(), SecurityUtil.getDbUser().getId());
				rec.setCountStudyPL(oyt.get(0).getCountStudyTypeId());

				// count StypeType PYT
				rec.setStudyIL(3);
				List<StudySummaryModel> pyt = mapper.selectCountOfStudyByStudyType(p.getProgramId(), rec.getStudyIL(), SecurityUtil.getDbUser().getId());
				rec.setCountStudyIL(pyt.get(0).getCountStudyTypeId());

				// count StypeType AYT
				rec.setStudyAYT(4);
				List<StudySummaryModel> glassHouseStudyType = mapper.selectCountOfStudyByStudyType(p.getProgramId(), rec.getStudyAYT(), SecurityUtil.getDbUser().getId());
				rec.setCountStudyAYT(glassHouseStudyType.get(0).getCountStudyTypeId());

				// count StypeType IAT
				rec.setStudyIAT(5);
				List<StudySummaryModel> ayt = mapper.selectCountOfStudyByStudyType(p.getProgramId(), rec.getStudyIAT(), SecurityUtil.getDbUser().getId());
				rec.setCountStudyIAT(ayt.get(0).getCountStudyTypeId());

				// count StypeType Genetics
				rec.setStudyGenetics(6);
				List<StudySummaryModel> genetics = mapper.selectCountOfStudyByStudyType(p.getProgramId(), rec.getStudyGenetics(), SecurityUtil.getDbUser().getId());
				rec.setCountStudyGenetics(genetics.get(0).getCountStudyTypeId());

				System.out.println("rec:" + rec.toString());
				s.add(rec);

			}
			return s;

		} finally {
			session.close();
		}

	}

	public List<StudySearchResultModel> getStudySearchResult(StudySearchFilterModel filter) {
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		try {

			List<StudySearchResultModel> toreturn = session.selectList("BrowseStudy.getStudySearchResult", filter);

			return toreturn;

		} finally {
			session.close();
		}

	}

	//	adding by QIN MAO to implement simple search by study name
	public List<StudySearchResultModel> getStudySearchResult(StudySearchFilterModel filter, Integer userID)
	{
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		List<StudySearchResultModel> toreturn = new ArrayList<StudySearchResultModel>();
		try{
			String shared = null;
			Integer programId = null;
			Integer projectId = null;
			String studyName = null;
			Integer studytypeId = null;
			String startYear = null;
			String endYear = null;
			String country = null;
			Integer locationId = null;
			Integer userId = userID;

			if(filter.getShared() != null)
				shared = filter.getShared();
			if(filter.getProgramid() != 0)
				programId = Integer.valueOf(filter.getProgramid());
			if(filter.getProjectid() != 0)
				projectId = Integer.valueOf(filter.getProjectid());
			if(filter.getStudyname() != null && filter.getStudyname().length() != 0)
				studyName = filter.getStudyname();
			if(filter.getStudytypeid() != 0)
				studytypeId = Integer.valueOf(filter.getStudytypeid());
			if(filter.getStartyear() != null && filter.getStartyear().length() != 0)
				startYear = filter.getStartyear();
			if(filter.getEndyear() != null && filter.getEndyear().length() != 0)
				endYear = filter.getEndyear();
			if(filter.getCountry() != null && filter.getCountry().length() != 0)
				country = filter.getCountry();
			if(filter.getLocationid() != 0)
				locationId = Integer.valueOf(filter.getLocationid());
			System.out.println("------------------------------");
			System.out.println("shared " + shared);
			System.out.println("programid " + programId);
			System.out.println("projectid " + projectId);
			System.out.println("stuydname " + studyName);
			System.out.println("studytypeid " + studytypeId);
			System.out.println("startyear " + startYear);
			System.out.println("endyear " + endYear);
			System.out.println("country " + country);
			System.out.println("locationid " + locationId);
			System.out.println("userId " + userId);
			System.out.println("------------------------------");

			//first checking whether country and location is null;
			StudyMapper sMapper = session.getMapper(StudyMapper.class);
			StudyExample sExample = new StudyExample();
			StudyLocationMapper slMapper = session.getMapper(StudyLocationMapper.class);
			StudyLocationExample slExample = new StudyLocationExample();
			LocationMapper lMapper = session.getMapper(LocationMapper.class);
			LocationExample lExample = new LocationExample();
			ProgramMapper progMapper = session.getMapper(ProgramMapper.class);
			ProgramExample progExample = new ProgramExample();
			ProjectMapper projMapper = session.getMapper(ProjectMapper.class);
			ProjectExample projExample = new ProjectExample();
			StudyTypeMapper stMapper = session.getMapper(StudyTypeMapper.class);
			StudyTypeExample stExample = new StudyTypeExample();

			if(country == null)
			{
				if(locationId != null)
				{
					lExample.createCriteria().andIdEqualTo(locationId);
					List<Location> lstLocation = lMapper.selectByExample(lExample); // should be only one case
					if(lstLocation != null && lstLocation.size() != 0)
					{	
						for(Location loc : lstLocation)
						{
							slExample.createCriteria().andLocationidEqualTo(loc.getId());
							List<StudyLocation> lstStudyLocation = slMapper.selectByExample(slExample);
							if(lstStudyLocation != null && lstStudyLocation.size() != 0)
							{
								for(StudyLocation sl : lstStudyLocation)
								{
									sExample.createCriteria().andIdEqualTo(sl.getStudyid());
									List<Study> lstStudy = sMapper.selectByExample(sExample);
									if(lstStudy != null && lstStudy.size() != 0)
									{
										List<Study> lstStudyFilter = new ArrayList<Study>(); // storing the filter study meet for required.
										for(Study s : lstStudy) // basic it should be only one records
										{
											if(shared.equals("private"))
											{
												if(s.getShared())
													continue;
												if(studyName != null && !s.getName().contains(studyName))
													continue;
												if(programId != null && s.getProgramid().equals(programId))
													continue;
												if(projectId != null && s.getProjectid().equals(projectId))
													continue;
												if(studytypeId != null && s.getStudytypeid().equals(studytypeId))
													continue;
												if(startYear != null && s.getStartyear().equalsIgnoreCase(startYear))
													continue;
												if(endYear != null && s.getEndyear().equalsIgnoreCase(endYear))
													continue;
												lstStudyFilter.add(s);
											} else if(shared.equals("public"))
											{
												if(!s.getShared())
													continue;
												if(studyName != null && !s.getName().contains(studyName))
													continue;
												if(programId != null && s.getProgramid().equals(programId))
													continue;
												if(projectId != null && s.getProjectid().equals(projectId))
													continue;
												if(studytypeId != null && s.getStudytypeid().equals(studytypeId))
													continue;
												if(startYear != null && s.getStartyear().equalsIgnoreCase(startYear))
													continue;
												if(endYear != null && s.getEndyear().equalsIgnoreCase(endYear))
													continue;
												lstStudyFilter.add(s);

											} else if(shared.equals("both"))
											{
												if(studyName != null && !s.getName().contains(studyName))
													continue;
												if(programId != null && s.getProgramid().equals(programId))
													continue;
												if(projectId != null && s.getProjectid().equals(projectId))
													continue;
												if(studytypeId != null && s.getStudytypeid().equals(studytypeId))
													continue;
												if(startYear != null && s.getStartyear().equalsIgnoreCase(startYear))
													continue;
												if(endYear != null && s.getEndyear().equalsIgnoreCase(endYear))
													continue;
												lstStudyFilter.add(s);
											}
										} //end of for(Study s : lstStudy)
										// assign filtered study to return
										for(Study s : lstStudyFilter)
										{
											StudySearchResultModel ssrm = new StudySearchResultModel();
											ssrm.setId(s.getId());
											ssrm.setProgramid(s.getProgramid());
											ssrm.setProjectid(s.getProjectid());
											ssrm.setStudyname(s.getName());
											ssrm.setStudytypeid(s.getStudytypeid());
											ssrm.setStartyear(s.getStartyear());
											ssrm.setEndyear(ssrm.getEndyear());
											ssrm.setLocationid(loc.getId());
											ssrm.setLocationname(loc.getLocationname());
											ssrm.setCountry(loc.getCountry());
											//get program name
											progExample.createCriteria().andIdEqualTo(s.getProgramid());
											List<Program> lstProgram = progMapper.selectByExample(progExample);
											if(lstProgram != null && lstProgram.size() != 0)
												ssrm.setProgramname(lstProgram.get(0).getName());
											progExample.clear();
											//get project name
											projExample.createCriteria().andIdEqualTo(s.getProjectid());
											List<Project> lstProject = projMapper.selectByExample(projExample);
											if(lstProject != null && lstProject.size() != 0)
												ssrm.setProjectname(lstProject.get(0).getName());
											projExample.clear();
											//get studytypename
											stExample.createCriteria().andIdEqualTo(s.getStudytypeid());
											List<StudyType> lstStudyType = stMapper.selectByExample(stExample);
											if(lstStudyType != null && lstStudyType.size() != 0)
												ssrm.setStudytypename(lstStudyType.get(0).getStudytype());
											stExample.clear();
											toreturn.add(ssrm);
										}
									}
									sExample.clear();
								}
							}
							slExample.clear();
						} // end of for(Location loc : lstLocation)

					} // end of if(lstLocation != null && lstLocation.size() != 0)

					lExample.clear();
				} else
				{
					// get study according to data private or public
					if(shared.equals("private"))
					{
						sExample.createCriteria().andUseridEqualTo(userID);
						List<Study> lstStudy = sMapper.selectByExample(sExample);
						if(lstStudy != null && lstStudy.size() != 0)
						{
							List<Study> lstStudyFilter = new ArrayList<Study>();
							for(Study s : lstStudy)
							{
								if(studyName != null && !s.getName().contains(studyName))
									continue;
								if(programId != null && !s.getProgramid().equals(programId))
									continue;
								if(projectId != null && !s.getProjectid().equals(projectId))
									continue;
								if(studytypeId != null && !s.getStudytypeid().equals(studytypeId))
									continue;
								if(startYear != null && !s.getStartyear().equalsIgnoreCase(startYear))
									continue;
								if(endYear != null && !s.getEndyear().equalsIgnoreCase(endYear))
									continue;
								lstStudyFilter.add(s);
							}
							// add filtered study into StudySearchResultModel
							for(Study s : lstStudyFilter)
							{
								StudySearchResultModel ssrm = new StudySearchResultModel();
								ssrm.setId(s.getId());
								ssrm.setProgramid(s.getProgramid());
								ssrm.setProjectid(s.getProjectid());
								ssrm.setStudyname(s.getName());
								ssrm.setStudytypeid(s.getStudytypeid());
								ssrm.setStartyear(s.getStartyear());
								ssrm.setEndyear(ssrm.getEndyear());
								//get location information
								slExample.createCriteria().andStudyidEqualTo(s.getId());
								List<StudyLocation> lstStudyLocation = slMapper.selectByExample(slExample);
								if(lstStudyLocation != null && lstStudyLocation.size() != 0)
								{
									lExample.createCriteria().andIdEqualTo(lstStudyLocation.get(0).getLocationid());
									List<Location> lstLocation = lMapper.selectByExample(lExample);
									if(lstLocation != null && lstLocation.size() != 0)
									{
										ssrm.setLocationid(lstLocation.get(0).getId());
										ssrm.setLocationname(lstLocation.get(0).getLocationname());
										ssrm.setCountry(lstLocation.get(0).getCountry());
									}
									lExample.clear();
								}
								slExample.clear();
								//get program name
								progExample.createCriteria().andIdEqualTo(s.getProgramid());
								List<Program> lstProgram = progMapper.selectByExample(progExample);
								if(lstProgram != null && lstProgram.size() != 0)
									ssrm.setProgramname(lstProgram.get(0).getName());
								progExample.clear();
								//get project name
								projExample.createCriteria().andIdEqualTo(s.getProjectid());
								List<Project> lstProject = projMapper.selectByExample(projExample);
								if(lstProject != null && lstProject.size() != 0)
									ssrm.setProjectname(lstProject.get(0).getName());
								projExample.clear();
								//get studytypename
								stExample.createCriteria().andIdEqualTo(s.getStudytypeid());
								List<StudyType> lstStudyType = stMapper.selectByExample(stExample);
								if(lstStudyType != null && lstStudyType.size() != 0)
									ssrm.setStudytypename(lstStudyType.get(0).getStudytype());
								stExample.clear();
								toreturn.add(ssrm);
							}
						}
						sExample.clear();
					} else if(shared.equals("public"))
					{
						sExample.createCriteria().andSharedEqualTo(true);
						List<Study> lstStudy = sMapper.selectByExample(sExample);
						if(lstStudy != null && lstStudy.size() != 0)
						{
							List<Study> lstStudyFilter = new ArrayList<Study>();
							for(Study s : lstStudy)
							{
								if(studyName != null && !s.getName().contains(studyName))
									continue;
								if(programId != null && !s.getProgramid().equals(programId))
									continue;
								if(projectId != null && !s.getProjectid().equals(projectId))
									continue;
								if(studytypeId != null && !s.getStudytypeid().equals(studytypeId))
									continue;
								if(startYear != null && !s.getStartyear().equalsIgnoreCase(startYear))
									continue;
								if(endYear != null && !s.getEndyear().equalsIgnoreCase(endYear))
									continue;
								lstStudyFilter.add(s);
							}
							// add filtered study into StudySearchResultModel
							for(Study s : lstStudyFilter)
							{
								StudySearchResultModel ssrm = new StudySearchResultModel();
								ssrm.setId(s.getId());
								ssrm.setProgramid(s.getProgramid());
								ssrm.setProjectid(s.getProjectid());
								ssrm.setStudyname(s.getName());
								ssrm.setStudytypeid(s.getStudytypeid());
								ssrm.setStartyear(s.getStartyear());
								ssrm.setEndyear(s.getEndyear());
								//get location information
								slExample.createCriteria().andStudyidEqualTo(s.getId());
								List<StudyLocation> lstStudyLocation = slMapper.selectByExample(slExample);
								if(lstStudyLocation != null && lstStudyLocation.size() != 0)
								{
									lExample.createCriteria().andIdEqualTo(lstStudyLocation.get(0).getLocationid());
									List<Location> lstLocation = lMapper.selectByExample(lExample);
									if(lstLocation != null && lstLocation.size() != 0)
									{
										ssrm.setLocationid(lstLocation.get(0).getId());
										ssrm.setLocationname(lstLocation.get(0).getLocationname());
										ssrm.setCountry(lstLocation.get(0).getCountry());
									}
									lExample.clear();
								}
								slExample.clear();
								//get program name
								progExample.createCriteria().andIdEqualTo(s.getProgramid());
								List<Program> lstProgram = progMapper.selectByExample(progExample);
								if(lstProgram != null && lstProgram.size() != 0)
									ssrm.setProgramname(lstProgram.get(0).getName());
								progExample.clear();
								//get project name
								projExample.createCriteria().andIdEqualTo(s.getProjectid());
								List<Project> lstProject = projMapper.selectByExample(projExample);
								if(lstProject != null && lstProject.size() != 0)
									ssrm.setProjectname(lstProject.get(0).getName());
								projExample.clear();
								//get studytypename
								stExample.createCriteria().andIdEqualTo(s.getStudytypeid());
								List<StudyType> lstStudyType = stMapper.selectByExample(stExample);
								if(lstStudyType != null && lstStudyType.size() != 0)
									ssrm.setStudytypename(lstStudyType.get(0).getStudytype());
								stExample.clear();
								toreturn.add(ssrm);
							}
						}
						sExample.clear();
					} else
					{
						sExample.or().andUseridEqualTo(userID);
						sExample.or().andSharedEqualTo(true);
						List<Study> lstStudy = sMapper.selectByExample(sExample);
						if(lstStudy != null && lstStudy.size() != 0)
						{
							List<Study> lstStudyFilter = new ArrayList<Study>();
							for(Study s : lstStudy)
							{
								if(studyName != null && !s.getName().contains(studyName))
									continue;
								if(programId != null && !s.getProgramid().equals(programId))
									continue;
								if(projectId != null && !s.getProjectid().equals(projectId))
									continue;
								if(studytypeId != null && !s.getStudytypeid().equals(studytypeId))
									continue;
								if(startYear != null && !s.getStartyear().equalsIgnoreCase(startYear))
									continue;
								if(endYear != null && !s.getEndyear().equalsIgnoreCase(endYear))
									continue;
								lstStudyFilter.add(s);
							}
							// add filtered study into StudySearchResultModel
							for(Study s : lstStudyFilter)
							{
								StudySearchResultModel ssrm = new StudySearchResultModel();
								ssrm.setId(s.getId());
								ssrm.setProgramid(s.getProgramid());
								ssrm.setProjectid(s.getProjectid());
								ssrm.setStudyname(s.getName());
								ssrm.setStudytypeid(s.getStudytypeid());
								ssrm.setStartyear(s.getStartyear());
								ssrm.setEndyear(s.getEndyear());
								//get location information
								slExample.createCriteria().andStudyidEqualTo(s.getId());
								List<StudyLocation> lstStudyLocation = slMapper.selectByExample(slExample);
								if(lstStudyLocation != null && lstStudyLocation.size() != 0)
								{
									lExample.createCriteria().andIdEqualTo(lstStudyLocation.get(0).getLocationid());
									List<Location> lstLocation = lMapper.selectByExample(lExample);
									if(lstLocation != null && lstLocation.size() != 0)
									{
										ssrm.setLocationid(lstLocation.get(0).getId());
										ssrm.setLocationname(lstLocation.get(0).getLocationname());
										ssrm.setCountry(lstLocation.get(0).getCountry());
									}
									lExample.clear();
								}
								slExample.clear();
								//get program name
								progExample.createCriteria().andIdEqualTo(s.getProgramid());
								List<Program> lstProgram = progMapper.selectByExample(progExample);
								if(lstProgram != null && lstProgram.size() != 0)
									ssrm.setProgramname(lstProgram.get(0).getName());
								progExample.clear();
								//get project name
								projExample.createCriteria().andIdEqualTo(s.getProjectid());
								List<Project> lstProject = projMapper.selectByExample(projExample);
								if(lstProject != null && lstProject.size() != 0)
									ssrm.setProjectname(lstProject.get(0).getName());
								projExample.clear();
								//get studytypename
								stExample.createCriteria().andIdEqualTo(s.getStudytypeid());
								List<StudyType> lstStudyType = stMapper.selectByExample(stExample);
								if(lstStudyType != null && lstStudyType.size() != 0)
									ssrm.setStudytypename(lstStudyType.get(0).getStudytype());
								stExample.clear();
								toreturn.add(ssrm);
							}
						}
						sExample.clear();
					}

				}
			} else
			{	
				if(locationId != null)
				{
					lExample.createCriteria().andCountryEqualTo(country).andIdEqualTo(locationId);
					List<Location> lstLocation = lMapper.selectByExample(lExample); // should be only one case
					if(lstLocation != null && lstLocation.size() != 0)
					{	
						for(Location loc : lstLocation)
						{
							slExample.createCriteria().andLocationidEqualTo(loc.getId());
							List<StudyLocation> lstStudyLocation = slMapper.selectByExample(slExample);
							if(lstStudyLocation != null && lstStudyLocation.size() != 0)
							{
								for(StudyLocation sl : lstStudyLocation)
								{
									sExample.createCriteria().andIdEqualTo(sl.getStudyid());
									List<Study> lstStudy = sMapper.selectByExample(sExample);
									if(lstStudy != null && lstStudy.size() != 0)
									{
										List<Study> lstStudyFilter = new ArrayList<Study>(); // storing the filter study meet for required.
										for(Study s : lstStudy) // basic it should be only one records
										{
											if(shared.equals("private"))
											{
												if(s.getShared())
													continue;
												if(studyName != null && !s.getName().contains(studyName))
													continue;
												if(programId != null && s.getProgramid().equals(programId))
													continue;
												if(projectId != null && s.getProjectid().equals(projectId))
													continue;
												if(studytypeId != null && s.getStudytypeid().equals(studytypeId))
													continue;
												if(startYear != null && s.getStartyear().equalsIgnoreCase(startYear))
													continue;
												if(endYear != null && s.getEndyear().equalsIgnoreCase(endYear))
													continue;
												lstStudyFilter.add(s);
											} else if(shared.equals("public"))
											{
												if(!s.getShared())
													continue;
												if(studyName != null && !s.getName().contains(studyName))
													continue;
												if(programId != null && s.getProgramid().equals(programId))
													continue;
												if(projectId != null && s.getProjectid().equals(projectId))
													continue;
												if(studytypeId != null && s.getStudytypeid().equals(studytypeId))
													continue;
												if(startYear != null && s.getStartyear().equalsIgnoreCase(startYear))
													continue;
												if(endYear != null && s.getEndyear().equalsIgnoreCase(endYear))
													continue;
												lstStudyFilter.add(s);

											} else if(shared.equals("both"))
											{
												if(studyName != null && !s.getName().contains(studyName))
													continue;
												if(programId != null && s.getProgramid().equals(programId))
													continue;
												if(projectId != null && s.getProjectid().equals(projectId))
													continue;
												if(studytypeId != null && s.getStudytypeid().equals(studytypeId))
													continue;
												if(startYear != null && s.getStartyear().equalsIgnoreCase(startYear))
													continue;
												if(endYear != null && s.getEndyear().equalsIgnoreCase(endYear))
													continue;
												lstStudyFilter.add(s);
											}
										} //end of for(Study s : lstStudy)
										// assign filtered study to return
										for(Study s : lstStudyFilter)
										{
											StudySearchResultModel ssrm = new StudySearchResultModel();
											ssrm.setId(s.getId());
											ssrm.setProgramid(s.getProgramid());
											ssrm.setProjectid(s.getProjectid());
											ssrm.setStudyname(s.getName());
											ssrm.setStudytypeid(s.getStudytypeid());
											ssrm.setStartyear(s.getStartyear());
											ssrm.setEndyear(s.getEndyear());
											ssrm.setLocationid(loc.getId());
											ssrm.setLocationname(loc.getLocationname());
											ssrm.setCountry(loc.getCountry());
											//get program name
											progExample.createCriteria().andIdEqualTo(s.getProgramid());
											List<Program> lstProgram = progMapper.selectByExample(progExample);
											if(lstProgram != null && lstProgram.size() != 0)
												ssrm.setProgramname(lstProgram.get(0).getName());
											progExample.clear();
											//get project name
											projExample.createCriteria().andIdEqualTo(s.getProjectid());
											List<Project> lstProject = projMapper.selectByExample(projExample);
											if(lstProject != null && lstProject.size() != 0)
												ssrm.setProjectname(lstProject.get(0).getName());
											projExample.clear();
											//get studytypename
											stExample.createCriteria().andIdEqualTo(s.getStudytypeid());
											List<StudyType> lstStudyType = stMapper.selectByExample(stExample);
											if(lstStudyType != null && lstStudyType.size() != 0)
												ssrm.setStudytypename(lstStudyType.get(0).getStudytype());
											stExample.clear();
											toreturn.add(ssrm);
										}
									}
									sExample.clear();
								}
							}
							slExample.clear();
						} // end of for(Location loc : lstLocation)

					} // end of if(lstLocation != null && lstLocation.size() != 0)

					lExample.clear();
				} else
				{
					lExample.createCriteria().andCountryEqualTo(country);
					List<Location> lstLocation = lMapper.selectByExample(lExample); // should be only one case
					if(lstLocation != null && lstLocation.size() != 0)
					{	
						for(Location loc : lstLocation)
						{
							slExample.createCriteria().andLocationidEqualTo(loc.getId());
							List<StudyLocation> lstStudyLocation = slMapper.selectByExample(slExample);
							if(lstStudyLocation != null && lstStudyLocation.size() != 0)
							{
								for(StudyLocation sl : lstStudyLocation)
								{
									sExample.createCriteria().andIdEqualTo(sl.getStudyid());
									List<Study> lstStudy = sMapper.selectByExample(sExample);
									if(lstStudy != null && lstStudy.size() != 0)
									{
										List<Study> lstStudyFilter = new ArrayList<Study>(); // storing the filter study meet for required.
										for(Study s : lstStudy) // basic it should be only one records
										{
											if(shared.equals("private"))
											{
												if(s.getShared())
													continue;
												if(studyName != null && !s.getName().contains(studyName))
													continue;
												if(programId != null && s.getProgramid().equals(programId))
													continue;
												if(projectId != null && s.getProjectid().equals(projectId))
													continue;
												if(studytypeId != null && s.getStudytypeid().equals(studytypeId))
													continue;
												if(startYear != null && s.getStartyear().equalsIgnoreCase(startYear))
													continue;
												if(endYear != null && s.getEndyear().equalsIgnoreCase(endYear))
													continue;
												lstStudyFilter.add(s);
											} else if(shared.equals("public"))
											{
												if(!s.getShared())
													continue;
												if(studyName != null && !s.getName().contains(studyName))
													continue;
												if(programId != null && s.getProgramid().equals(programId))
													continue;
												if(projectId != null && s.getProjectid().equals(projectId))
													continue;
												if(studytypeId != null && s.getStudytypeid().equals(studytypeId))
													continue;
												if(startYear != null && s.getStartyear().equalsIgnoreCase(startYear))
													continue;
												if(endYear != null && s.getEndyear().equalsIgnoreCase(endYear))
													continue;
												lstStudyFilter.add(s);

											} else if(shared.equals("both"))
											{
												if(studyName != null && !s.getName().contains(studyName))
													continue;
												if(programId != null && s.getProgramid().equals(programId))
													continue;
												if(projectId != null && s.getProjectid().equals(projectId))
													continue;
												if(studytypeId != null && s.getStudytypeid().equals(studytypeId))
													continue;
												if(startYear != null && s.getStartyear().equalsIgnoreCase(startYear))
													continue;
												if(endYear != null && s.getEndyear().equalsIgnoreCase(endYear))
													continue;
												lstStudyFilter.add(s);
											}
										} //end of for(Study s : lstStudy)
										// assign filtered study to return
										for(Study s : lstStudyFilter)
										{
											StudySearchResultModel ssrm = new StudySearchResultModel();
											ssrm.setId(s.getId());
											ssrm.setProgramid(s.getProgramid());
											ssrm.setProjectid(s.getProjectid());
											ssrm.setStudyname(s.getName());
											ssrm.setStudytypeid(s.getStudytypeid());
											ssrm.setStartyear(s.getStartyear());
											ssrm.setEndyear(s.getEndyear());
											ssrm.setLocationid(loc.getId());
											ssrm.setLocationname(loc.getLocationname());
											ssrm.setCountry(loc.getCountry());
											//get program name
											progExample.createCriteria().andIdEqualTo(s.getProgramid());
											List<Program> lstProgram = progMapper.selectByExample(progExample);
											if(lstProgram != null && lstProgram.size() != 0)
												ssrm.setProgramname(lstProgram.get(0).getName());
											progExample.clear();
											//get project name
											projExample.createCriteria().andIdEqualTo(s.getProjectid());
											List<Project> lstProject = projMapper.selectByExample(projExample);
											if(lstProject != null && lstProject.size() != 0)
												ssrm.setProjectname(lstProject.get(0).getName());
											projExample.clear();
											//get studytypename
											stExample.createCriteria().andIdEqualTo(s.getStudytypeid());
											List<StudyType> lstStudyType = stMapper.selectByExample(stExample);
											if(lstStudyType != null && lstStudyType.size() != 0)
												ssrm.setStudytypename(lstStudyType.get(0).getStudytype());
											stExample.clear();
											toreturn.add(ssrm);
										}
									}
									sExample.clear();
								}
							}
							slExample.clear();
						} // end of for(Location loc : lstLocation)

					} // end of if(lstLocation != null && lstLocation.size() != 0)

					lExample.clear();
				} // end of if(locationId != null)
			} // end of if(country.length == 0)

			return toreturn;			
		} finally{
			session.close();
		}
	}



	public List<HashMap<String, String>> getStudyData(int studyId, String dataType, Integer dataset) {
		List<HashMap<String, String>> toreturn = new ArrayList<HashMap<String, String>>();
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		try {
			StudyDataColumnManagerImpl mgr = new StudyDataColumnManagerImpl();

			List<StudyDataColumn> studyDataColumn = mgr.getStudyDataColumnByStudyId(studyId, dataType, dataset);
			ArrayList<StudyDataColumnModel> dataColumns = new ArrayList<StudyDataColumnModel>();
			//			on JAN 16, 2015, checking whether the return studyDataColumn is null
			if(studyDataColumn == null || studyDataColumn.size() == 0)
				return toreturn;
			int count = 1;
			int columnCount = studyDataColumn.size();
			for (StudyDataColumn s : studyDataColumn) {
				StudyDataColumnModel m = new StudyDataColumnModel();
				m.setColumnheader(s.getColumnheader());
				m.setStudyid(s.getStudyid());
				m.setOrder(count);
				m.setCount(columnCount);
				m.setDataset(s.getDataset());
				dataColumns.add(m);
				count++;
			}
			StudyRawDataMapper srdMapper = session.getMapper(StudyRawDataMapper.class);
			StudyRawDataExample srdExample = new StudyRawDataExample();
			StudyDerivedDataMapper sddMapper = session.getMapper(StudyDerivedDataMapper.class);
			StudyDerivedDataExample sddExample = new StudyDerivedDataExample();
			if(!dataColumns.isEmpty())
			{
				if(dataset != null)
				{
					if(dataType.equals("rd"))
					{
						srdExample.createCriteria().andStudyidEqualTo(studyId).andDatasetEqualTo(dataset);
						List<StudyRawData> lstStudyRawData = srdMapper.selectByExample(srdExample);
						if(lstStudyRawData != null && lstStudyRawData.size() != 0)
						{
							//get the rows records of raw data;
							int rows = lstStudyRawData.size() / dataColumns.size();
							//columns number of raw data;
							
							//set each element of temp to be null;
							for(int i = 0; i < rows; i++)
								toreturn.add(i, null);
							for(StudyRawData srd : lstStudyRawData)
							{
								if(toreturn.get(srd.getDatarow() - 1) != null)
								{
									HashMap<String, String> tempHM = toreturn.get(srd.getDatarow() - 1);
									tempHM.put(String.valueOf(srd.getDatacolumn()), srd.getDatavalue());
									toreturn.set(srd.getDatarow() - 1, tempHM);
								} else
								{
									HashMap<String, String> tempHM = new HashMap<String, String>();
									tempHM.put(String.valueOf(srd.getDatacolumn()), srd.getDatavalue());
									toreturn.set(srd.getDatarow() - 1, tempHM);
								}
							}
						}
						srdExample.clear();
					} else
					{
						sddExample.createCriteria().andStudyidEqualTo(studyId).andDatasetEqualTo(dataset);
						List<StudyDerivedData> lstStudyDerivedData = sddMapper.selectByExample(sddExample);
						if(lstStudyDerivedData != null && lstStudyDerivedData.size() != 0)
						{
							//get the rows records of raw data;
							int rows = lstStudyDerivedData.size() / dataColumns.size();
							//set each element of temp to be null;
							for(int i = 0; i < rows; i++)
								toreturn.add(i, null);
							for(StudyDerivedData sdd : lstStudyDerivedData)
							{
								if(toreturn.get(sdd.getDatarow() - 1) != null)
								{
									HashMap<String, String> hm = toreturn.get(sdd.getDatarow() -1);
									hm.put(String.valueOf(sdd.getDatacolumn()), sdd.getDatavalue());
									toreturn.set(sdd.getDatarow() - 1, hm);
								} else
								{
									HashMap<String, String> hm = new HashMap<String, String>();
									hm.put(String.valueOf(sdd.getDatacolumn()), sdd.getDatavalue());
									toreturn.set(sdd.getDatarow() - 1, hm);
								}
							}
						}
						sddExample.clear();
					}
				} else
				{
					if(dataType.equals("rd"))
					{
						srdExample.createCriteria().andStudyidEqualTo(studyId);
						List<StudyRawData> lstStudyRawData = srdMapper.selectByExample(srdExample);
						if(lstStudyRawData != null && lstStudyRawData.size() != 0)
						{
							//get the rows records of raw data;
							int rows = lstStudyRawData.size() / dataColumns.size();
							//columns number of raw data;
							
							//set each element of temp to be null;
							for(int i = 0; i < rows; i++)
								toreturn.add(i, null);
							for(StudyRawData srd : lstStudyRawData)
							{
								if(toreturn.get(srd.getDatarow() - 1) != null)
								{
									HashMap<String, String> tempHM = toreturn.get(srd.getDatarow() - 1);
									tempHM.put(String.valueOf(srd.getDatacolumn()), srd.getDatavalue());
									toreturn.set(srd.getDatarow() - 1, tempHM);
								} else
								{
									HashMap<String, String> tempHM = new HashMap<String, String>();
									tempHM.put(String.valueOf(srd.getDatacolumn()), srd.getDatavalue());
									toreturn.set(srd.getDatarow() - 1, tempHM);
								}
							}
						}
						srdExample.clear();
					} else
					{
						sddExample.createCriteria().andStudyidEqualTo(studyId);
						List<StudyDerivedData> lstStudyDerivedData = sddMapper.selectByExample(sddExample);
						if(lstStudyDerivedData != null && lstStudyDerivedData.size() != 0)
						{
							//get the rows records of raw data;
							int rows = lstStudyDerivedData.size() / dataColumns.size();
							//set each element of temp to be null;
							for(int i = 0; i < rows; i++)
								toreturn.add(i, null);
							for(StudyDerivedData sdd : lstStudyDerivedData)
							{
								if(toreturn.get(sdd.getDatarow() - 1) != null)
								{
									HashMap<String, String> hm = toreturn.get(sdd.getDatarow() -1);
									hm.put(String.valueOf(sdd.getDatacolumn()), sdd.getDatavalue());
									toreturn.set(sdd.getDatarow() - 1, hm);
								} else
								{
									HashMap<String, String> hm = new HashMap<String, String>();
									hm.put(String.valueOf(sdd.getDatacolumn()), sdd.getDatavalue());
									toreturn.set(sdd.getDatarow() - 1, hm);
								}
							}
						}
						sddExample.clear();
					}
				}
			}
			// original implemented
			//			if (!dataColumns.isEmpty()) {
			//				if (dataset != null) {
			//					if (dataType.equals("rd")) {
			//						toreturn = session.selectList("BrowseStudy.getStudyRawData", dataColumns);
			//					} else {
			//						toreturn = session.selectList("BrowseStudy.getStudyDerivedData", dataColumns);
			//					}
			//				} else {
			//					if (dataType.equals("rd")) {
			//						toreturn = session.selectList("BrowseStudy.getStudyRawDataNoDataset", dataColumns);
			//					} else {
			//						toreturn = session.selectList("BrowseStudy.getStudyDerivedDataNoDataset", dataColumns);
			//					}
			//				}
			//			}
			return toreturn;

		} finally {
			session.close();
		}

	}

}
