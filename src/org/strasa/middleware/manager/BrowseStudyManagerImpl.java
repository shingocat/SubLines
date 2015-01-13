package org.strasa.middleware.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.spring.security.model.SecurityUtil;
import org.strasa.middleware.factory.ConnectionFactory;
import org.strasa.middleware.mapper.other.StudySummaryMapper;
import org.strasa.middleware.model.StudyDataColumn;
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
				rec.setStudyPVS(1);
				List<StudySummaryModel> pvs = mapper.selectCountOfStudyByStudyType(p.getProgramId(), rec.getStudyPVS(), SecurityUtil.getDbUser().getId());
				rec.setCountStudyPVS(pvs.get(0).getCountStudyTypeId());

				// count StypeType OYT
				rec.setStudyOYT(2);
				List<StudySummaryModel> oyt = mapper.selectCountOfStudyByStudyType(p.getProgramId(), rec.getStudyOYT(), SecurityUtil.getDbUser().getId());
				rec.setCountStudyOYT(oyt.get(0).getCountStudyTypeId());

				// count StypeType PYT
				rec.setStudyPYT(3);
				List<StudySummaryModel> pyt = mapper.selectCountOfStudyByStudyType(p.getProgramId(), rec.getStudyPYT(), SecurityUtil.getDbUser().getId());
				rec.setCountStudyPYT(pyt.get(0).getCountStudyTypeId());

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

	public List<HashMap<String, String>> getStudyData(int studyId, String dataType, Integer dataset) {
		List<HashMap<String, String>> toreturn = new ArrayList<HashMap<String, String>>();
		SqlSession session = connectionFactory.sqlSessionFactory.openSession();
		try {
			StudyDataColumnManagerImpl mgr = new StudyDataColumnManagerImpl();

			List<StudyDataColumn> studyDataColumn = mgr.getStudyDataColumnByStudyId(studyId, dataType, dataset);
			ArrayList<StudyDataColumnModel> dataColumns = new ArrayList<StudyDataColumnModel>();

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

			System.out.println(studyDataColumn.toString());

			if (!studyDataColumn.isEmpty()) {
				if (dataset != null) {
					if (dataType.equals("rd")) {
						toreturn = session.selectList("BrowseStudy.getStudyRawData", dataColumns);
					} else {
						toreturn = session.selectList("BrowseStudy.getStudyDerivedData", dataColumns);
					}
				} else {
					if (dataType.equals("rd")) {
						toreturn = session.selectList("BrowseStudy.getStudyRawDataNoDataset", dataColumns);
					} else {
						toreturn = session.selectList("BrowseStudy.getStudyDerivedDataNoDataset", dataColumns);
					}
				}
			}
			return toreturn;

		} finally {
			session.close();
		}

	}

}
