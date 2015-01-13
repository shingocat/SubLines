package org.strasa.middleware.mapper.other;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.strasa.web.browsestudy.view.model.StudySummaryModel;
import org.strasa.web.distributionandextension.view.model.DistributionAndExtensionListModel;
import org.strasa.web.distributionandextension.view.model.DistributionAndExtensionSummaryModel;
import org.strasa.web.distributionandextension.view.model.SummaryModel;
import org.strasa.web.releaseinfo.view.model.ReleaseInfoSummaryModel;

public interface DistributionAndExtensionSummaryMapper {

	/*	@Select("Select countryrelease as data,count(germplasmname)as totalcount from distributionandextension group by countryrelease")
	List<distributionandextensionSummaryModel> selectExtentionDataSummaryByCountryRelease();


	@Select("Select year as data,count(germplasmname)as totalcount from distributionandextension group by year")
	List<distributionandextensionSummaryModel> selectExtentionDataSummaryByYear();


	@Select("select t1.*, t2.name as programName,t3.name as projectName  from distributionandextension as t1 left join program as t2 on t1.programid=t2.id left join project as t3 on t1.projectid=t3.id")
	List<distributionandextensionListModel> selectExtentionDataList();*/

	//Extention
	//Germplasm vs Year and Country
	//select t2.name,t1.year,t1.countryextension,t1.germplasmname,sum(t1.plantingarea) as sumPlantingArea from distributionandextension as t1 left join program as t2 on t1.programid=t2.id group by programid,germplasmname order by countryextension,year
	@Select("select t2.name as programName, t1.programid as programid,yearextension,t1.countryextension,t1.germplasmname,sum(plantingarea) as sumPlantingArea, sum(amountseeddistributed) as sumAmountSeedDistributed, sum(numfarmersadopted) as sumNumFarmersAdopted, sum(amountseedsproduced) as sumAmountSeedsProduced from distributionandextension as t1 left join program as t2 on t1.programid=t2.id group by programid,germplasmname order by programName,yearextension,countryextension")
	List<SummaryModel> selectplantingareaSummaryGermplasmByYearandCountryExtension();


	//Germplasm vs Country
	//select programid,germplasmname,countryextension,sum(plantingarea) as sumPlantingArea from distributionandextension group by programid,germplasmname,countryextension order by countryrelease,year
	@Select("select t2.name as programName, t1.programid as programid,t1.germplasmname,t1.countryextension,sum(t1.plantingarea) as sumPlantingArea, sum(amountseeddistributed) as sumAmountSeedDistributed, sum(numfarmersadopted) as sumNumFarmersAdopted, sum(amountseedsproduced) as sumAmountSeedsProduced from distributionandextension as t1 left join program as t2 on t1.programid=t2.id group by programid,germplasmname,countryextension order by countryextension,yearextension")
	List<SummaryModel> selectplantingareaSummaryGermplasmByCountryExtension();


	//Germplasm vs Year
	//select programid,germplasmname,year,sum(plantingarea) as sumPlantingArea from distributionandextension group by programid,germplasmname,year order by programid,year,germplasmname
	@Select("select t2.name as programName, t1.programid as programid,t1.germplasmname,t1.yearextension,sum(t1.plantingarea) as sumPlantingArea, sum(amountseeddistributed) as sumAmountSeedDistributed, sum(numfarmersadopted) as sumNumFarmersAdopted, sum(amountseedsproduced) as sumAmountSeedsProduced from distributionandextension as t1 left join program as t2 on t1.programid=t2.id group by programid,germplasmname,yearextension order by yearextension,programName")
	List<SummaryModel> selectplantingareaSummaryGermplasmByYear();




	//Release Information
	//No of Variety Release by country and year
	//select programid,countryrelease,year,count(germplasmname) as noOfVariety from distributionandextension group by programid,year order by programid,year,germplasmname

	@Select("select t2.name as programName,t1.programid as programid,t1.projectid as projectid, t1.countryrelease as countryrelease, t1.yearrelease as yearrelease,count(t1.germplasmname) as countVariety from distributionandextension as t1 left join program as t2 on t1.programid=t2.id group by programid,yearrelease order by programid,yearrelease,germplasmname")
	List<SummaryModel> selectNoOfVarietyReleaseByCountryAndYear();


	//No of Variety by CountryRelease
	//select programid,countryrelease,count(germplasmname) as noOfVariety from distributionandextension group by programid order by programid,germplasmname
	@Select("select t2.name as programName, t1.programid as programid,t1.projectid as projectid, t1.countryrelease as countryrelease, count(t1.germplasmname) as countVariety from distributionandextension as t1 left join program as t2 on t1.programid=t2.id group by programid order by programid,germplasmname")
	List<SummaryModel> selectNoOfVarietyReleaseByCountryRelease();


	//No of Variety by Year
	//select programid,year,count(germplasmname) as noOfVariety from distributionandextension group by programid,year order by programid,year
	@Select("select t2.name as programName, t1.programid as programid,t1.projectid as projectid, t1.yearrelease as yearrelease, count(t1.germplasmname) as countVariety from distributionandextension as t1 left join program as t2 on t1.programid=t2.id group by programid,yearrelease order by programid,yearrelease")
	List<SummaryModel> selectNoOfVarietyReleaseByYear();

	//Variety Release by country and year
	@Select("select t2.name as programName,t1.programid as programid,t1.projectid as projectid, t1.countryrelease as countryrelease, t1.yearrelease as yearrelease,t1.germplasmname as germplasmname from distributionandextension as t1 left join program as t2 on t1.programid=t2.id group by programid,yearrelease order by programid,yearrelease,germplasmname")
	List<SummaryModel> selectVarietyReleaseByCountryAndYear();


	//Variety by CountryRelease
	@Select("select t2.name as programName, t1.programid as programid,t1.projectid as projectid, t1.countryrelease as countryrelease, t1.germplasmname as germplasmname from distributionandextension as t1 left join program as t2 on t1.programid=t2.id group by programid order by programid,germplasmname")
	List<SummaryModel> selectVarietyReleaseByCountryRelease();


	//Variety by Year
	@Select("select t2.name as programName, t1.programid as programid,t1.projectid as projectid, t1.yearrelease as yearrelease,t1.germplasmname as germplasmname from distributionandextension as t1 left join program as t2 on t1.programid=t2.id group by programid,yearrelease order by programid,yearrelease")
	List<SummaryModel> selectVarietyReleaseByYear();

	//Names of Variety by Year
	@Select("select distinct germplasmname from distributionandextension where yearrelease=#{year} and programid=#{programid}")
	List<String> selectVarietyNamesOfVarietyReleaseByYear(@Param("year")String year,@Param("programid")Integer programid);

	//Names of Variety by CountryRelease
	@Select("select distinct germplasmname from distributionandextension where countryrelease=#{countryrelease} and programid=#{programid}")
	List<String> selectVarietyNamesOfVarietyReleaseByCountry(@Param("countryrelease")String countryrelease, @Param("programid") Integer programid);

	//Names of Variety by CountryRelease and Year
	@Select("select distinct germplasmname from distributionandextension where countryrelease=#{countryrelease} and yearrelease=#{year} and programid=#{programid}")
	List<String> selectVarietyNamesOfVarietyReleaseByCountryAndYear(@Param("countryrelease")String countryrelease,@Param("year")String year, @Param("programid") Integer programid);

	//distributionandextension of Germplasm Variety by Year/Country
	@Select("select distinct germplasmname from distributionandextension where countryrelease=#{countryrelease} and yearrelease=#{year} and germplasmname=#{germplasmname} and programid=#{programid} ")
	List<SummaryModel> selectdistributionandextensionDetailOfVariety(@Param("year")String year, @Param("countryrelease")String countryrelease, @Param("programid")Integer programid,@Param("germplasmname")String germplasmname);

	@Select("select distinct CONCAT(t2.name,'-',t1.countryextension ) as programName from distributionandextension as t1 left join program as t2 on t1.programid=t2.id group by programid,germplasmname,countryextension order by t2.name,countryextension,yearextension")
	List<SummaryModel> selectCategoryByCountry();
	
	@Select("select distinct CONCAT(t2.name) as programName from distributionandextension as t1 left join program as t2 on t1.programid=t2.id group by programid,germplasmname,yearextension order by yearextension")
	List<SummaryModel> selectCategoryByYear();
}
