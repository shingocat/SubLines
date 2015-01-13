package org.strasa.middleware.mapper.other;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.strasa.web.browsestudy.view.model.StudySummaryModel;
import org.strasa.web.releaseinfo.view.model.ReleaseInfoSummaryModel;

public interface ReleaseInfoSummaryMapper {

	//Release Information
	//No of Variety Release by country and year
	//select programid,countryrelease,year,count(germplasmname) as noOfVariety from extensiondata group by programid,year order by programid,year,germplasmname

	@Select("select t2.name as programName,t1.programid as programid,t1.projectid as projectid, t1.countryrelease as countryrelease, t1.yearrelease as yearrelease,count(t1.germplasmname) as countVariety from releaseinfo as t1 left join program as t2 on t1.programid=t2.id group by programid,yearrelease order by programid,yearrelease,germplasmname")
	List<ReleaseInfoSummaryModel> selectNoOfVarietyReleaseByCountryAndYear();


	//No of Variety by CountryRelease
	//select programid,countryrelease,count(germplasmname) as noOfVariety from extensiondata group by programid order by programid,germplasmname
	@Select("select t2.name as programName, t1.programid as programid,t1.projectid as projectid, t1.countryrelease as countryrelease, count(t1.germplasmname) as countVariety from releaseinfo as t1 left join program as t2 on t1.programid=t2.id group by programid order by programid,germplasmname")
	List<ReleaseInfoSummaryModel> selectNoOfVarietyReleaseByCountryRelease();


	//No of Variety by Year
	//select programid,year,count(germplasmname) as noOfVariety from extensiondata group by programid,year order by programid,year
	@Select("select t2.name as programName, t1.programid as programid,t1.projectid as projectid, t1.yearrelease as yearrelease, count(t1.germplasmname) as countVariety from releaseinfo as t1 left join program as t2 on t1.programid=t2.id group by programid,yearrelease order by programid,yearrelease")
	List<ReleaseInfoSummaryModel> selectNoOfVarietyReleaseByYear();

	//Variety Release by country and year
	@Select("select t2.name as programName,t1.programid as programid,t1.projectid as projectid, t1.countryrelease as countryrelease, t1.yearrelease as yearrelease,t1.germplasmname as germplasmname from releaseinfo as t1 left join program as t2 on t1.programid=t2.id group by programid,yearrelease order by programid,yearrelease,germplasmname")
	List<ReleaseInfoSummaryModel> selectVarietyReleaseByCountryAndYear();


	//Variety by CountryRelease
	@Select("select t2.name as programName, t1.programid as programid,t1.projectid as projectid, t1.countryrelease as countryrelease, t1.germplasmname as germplasmname from releaseinfo as t1 left join program as t2 on t1.programid=t2.id group by programid order by programid,germplasmname")
	List<ReleaseInfoSummaryModel> selectVarietyReleaseByCountryRelease();


	//Variety by Year
	@Select("select t2.name as programName, t1.programid as programid,t1.projectid as projectid, t1.yearrelease as yearrelease,t1.germplasmname as germplasmname from releaseinfo as t1 left join program as t2 on t1.programid=t2.id group by programid,yearrelease order by programid,yearrelease")
	List<ReleaseInfoSummaryModel> selectVarietyReleaseByYear();

	//Names of Variety by Year
	@Select("select distinct germplasmname from releaseinfo where yearrelease=#{year} and programid=#{programid}")
	List<String> selectVarietyNamesOfVarietyReleaseByYear(@Param("year")String year,@Param("programid")Integer programid);

	//Names of Variety by CountryRelease
	@Select("select distinct germplasmname from releaseinfo where countryrelease=#{countryrelease} and programid=#{programid}")
	List<String> selectVarietyNamesOfVarietyReleaseByCountry(@Param("countryrelease")String countryrelease, @Param("programid") Integer programid);

	//Names of Variety by CountryRelease and Year
	@Select("select distinct germplasmname from releaseinfo where countryrelease=#{countryrelease} and yearrelease=#{year} and programid=#{programid}")
	List<String> selectVarietyNamesOfVarietyReleaseByCountryAndYear(@Param("countryrelease")String countryrelease,@Param("year")String year, @Param("programid") Integer programid);

	//ExtensionData of Germplasm Variety by Year/Country
	@Select("select distinct germplasmname from releaseinfo where countryrelease=#{countryrelease} and yearrelease=#{year} and germplasmname=#{germplasmname} and programid=#{programid} ")
	List<ReleaseInfoSummaryModel> selectReleaseInfoDetailOfVariety(@Param("year")String year, @Param("countryrelease")String countryrelease, @Param("programid")Integer programid,@Param("germplasmname")String germplasmname);

	//Distinct Program
	@Select("select t2.name as programName from releaseinfo as t1 left join program as t2 on t1.programid=t2.id group by programid order by programid,yearrelease")
	List<ReleaseInfoSummaryModel> selectProgramList();

	//Distinct Program
	@Select("select distinct CONCAT(t2.name,'-',t1.countryrelease) as programName from releaseinfo as t1 left join program as t2 on t1.programid=t2.id group by programid,yearrelease order by programid,yearrelease,germplasmname")
	List<ReleaseInfoSummaryModel> selectProgramListWithCountry();

}
