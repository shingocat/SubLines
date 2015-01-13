package org.strasa.middleware.factory;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.rosuda.REngine.Rserve.RserveException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.strasa.middleware.mapper.BrowseIntrogressionLineMapper;
import org.strasa.middleware.mapper.CountryMapper;
import org.strasa.middleware.mapper.DbUserMapper;
import org.strasa.middleware.mapper.DistributionAndExtensionMapper;
import org.strasa.middleware.mapper.EcotypeMapper;
import org.strasa.middleware.mapper.ExtensionDataMapper;
import org.strasa.middleware.mapper.GeorefMapper;
import org.strasa.middleware.mapper.GermplasmCharacteristicsMapper;
import org.strasa.middleware.mapper.GermplasmMapper;
import org.strasa.middleware.mapper.GermplasmReleaseInfoMapper;
import org.strasa.middleware.mapper.GermplasmSegmentMapper;
import org.strasa.middleware.mapper.GermplasmTypeMapper;
import org.strasa.middleware.mapper.KeyAbioticMapper;
import org.strasa.middleware.mapper.KeyBioticMapper;
import org.strasa.middleware.mapper.KeyGrainQualityMapper;
import org.strasa.middleware.mapper.KeyMajorGenesMapper;
import org.strasa.middleware.mapper.LocationMapper;
import org.strasa.middleware.mapper.PlantingTypeMapper;
import org.strasa.middleware.mapper.ProgramMapper;
import org.strasa.middleware.mapper.ProjectMapper;
import org.strasa.middleware.mapper.ReleaseInfoMapper;
import org.strasa.middleware.mapper.SegmentMapper;
import org.strasa.middleware.mapper.SoilTypeMapper;
import org.strasa.middleware.mapper.StudyAgronomyMapper;
import org.strasa.middleware.mapper.StudyDataColumnMapper;
import org.strasa.middleware.mapper.StudyDataSetMapper;
import org.strasa.middleware.mapper.StudyDerivedDataMapper;
import org.strasa.middleware.mapper.StudyDerivedRawDataMapper;
import org.strasa.middleware.mapper.StudyDesignMapper;
import org.strasa.middleware.mapper.StudyFileMapper;
import org.strasa.middleware.mapper.StudyGermplasmCharacteristicsMapper;
import org.strasa.middleware.mapper.StudyGermplasmMapper;
import org.strasa.middleware.mapper.StudyLocationMapper;
import org.strasa.middleware.mapper.StudyMapper;
import org.strasa.middleware.mapper.StudyRawDataByDataColumnMapper;
import org.strasa.middleware.mapper.StudyRawDataMapper;
import org.strasa.middleware.mapper.StudyRawDerivedDataByDataColumnMapper;
import org.strasa.middleware.mapper.StudySiteMapper;
import org.strasa.middleware.mapper.StudyTypeMapper;
import org.strasa.middleware.mapper.StudyVariableMapper;
import org.strasa.middleware.mapper.UserDataFileMapper;
import org.strasa.middleware.mapper.other.DistributionAndExtensionSummaryMapper;
import org.strasa.middleware.mapper.other.ExtendedStudyDataColumnMapper;
import org.strasa.middleware.mapper.other.GermplasmBreederMapper;
import org.strasa.middleware.mapper.other.ReleaseInfoSummaryMapper;
import org.strasa.middleware.mapper.other.StudySharingMapper;
import org.strasa.middleware.mapper.other.StudySummaryMapper;
import org.strasa.middleware.model.StudyGermplasmCharacteristics;
import org.strasa.middleware.model.StudySiteByStudy;

@Service("connectionFactory")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ConnectionFactory {

	public static SqlSessionFactory sqlSessionFactory;

	// public static RConnection rServerConnection;
	public ConnectionFactory() throws RserveException {

		String resource = "SqlMapConfig.xml";
		Reader reader;
		try {
			reader = Resources.getResourceAsReader(resource);
			if (sqlSessionFactory == null) {
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
				sqlSessionFactory.getConfiguration().addMapper(CountryMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(DistributionAndExtensionMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(EcotypeMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(ExtensionDataMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(GeorefMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(GermplasmCharacteristicsMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(GermplasmMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(GermplasmReleaseInfoMapper.class);
				// sqlSessionFactory.getConfiguration().addMapper(GermplasmMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(LocationMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(PlantingTypeMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(ProgramMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(ProjectMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(StudyAgronomyMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(StudyDerivedDataMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(StudyDesignMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(StudyFileMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(StudyLocationMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(StudyMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(StudyRawDataMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(StudyRawDataByDataColumnMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(StudySiteMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(StudySiteByStudy.class);
				sqlSessionFactory.getConfiguration().addMapper(StudyTypeMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(StudyVariableMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(StudyGermplasmMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(DbUserMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(KeyBioticMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(KeyAbioticMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(KeyGrainQualityMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(KeyMajorGenesMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(GermplasmTypeMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(ReleaseInfoMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(StudyGermplasmCharacteristics.class);
				sqlSessionFactory.getConfiguration().addMapper(StudyGermplasmCharacteristicsMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(StudySummaryMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(UserDataFileMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(StudyDataColumnMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(StudyDerivedRawDataMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(StudyRawDerivedDataByDataColumnMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(GermplasmBreederMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(StudyDataSetMapper.class);

				sqlSessionFactory.getConfiguration().addMapper(ExtendedStudyDataColumnMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(DistributionAndExtensionSummaryMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(ReleaseInfoSummaryMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(SoilTypeMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(StudySharingMapper.class);
				
//				adding by QIN MAO
//				sqlSessionFactory.getConfiguration().addMapper(BrowseIntrogressionLineMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(SegmentMapper.class);
				sqlSessionFactory.getConfiguration().addMapper(GermplasmSegmentMapper.class);
			}
			// rServerConnection= new RConnection();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// public SqlSessionFactory getSqlSessionFactory() {
	//
	// return sqlSessionFactory;
	// }
	/**
	 * Returns a DataSource object.
	 * 
	 * @return a DataSource.
	 */

}