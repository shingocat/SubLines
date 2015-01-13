package org.strasa.middleware.mapper.other;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;
import org.strasa.middleware.model.Germplasm;

public interface GermplasmBreederMapper {
	 String UPDATE_BREEDER = "UPDATE germplasm SET breeder=#{breeder} WHERE germplasmname = #{germplasmname}";
	 	 @Update(UPDATE_BREEDER)
	     @Options(flushCache=true,useCache=true)
	     public int Update(Germplasm record) throws Exception;
	 
	}
