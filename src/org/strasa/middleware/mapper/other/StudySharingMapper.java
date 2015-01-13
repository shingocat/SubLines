package org.strasa.middleware.mapper.other;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface StudySharingMapper {
	
	//Names of Variety by Year
		@Select("update studyrawdata set shared=#{shared} where studyid=#{studyid}")
		public void setSharingStudyRawData(@Param("studyid")int studyid,@Param("shared")boolean shared);
		
		@Select("update studyrawdata set shared=#{shared} where studyid=#{studyid}")
		public void setSharingStudyDerivedData(@Param("studyid")int studyid,@Param("shared")boolean shared);

}
