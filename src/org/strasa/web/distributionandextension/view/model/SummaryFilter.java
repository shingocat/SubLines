package org.strasa.web.distributionandextension.view.model;

public class SummaryFilter {
	
	
	String programName="";
	String countryExtension="";
	String counrtyRelease="";
	String germplasmName="";
	String sumArea="";
	String sumAmountSeedDistributed="";
	String sumNumFarmersAdopted="";
	String sumAmountSeedsProduced="";
	String yearExtention="";
	String yearRelease="";
	String countVariety="";
	
	
	public String getProgramName() {
		return programName;
		
	}
	public void setProgramName(String programName) {
		this.programName = programName==null?"":programName.trim();
		
	}
	public String getCountryExtension() {
		return countryExtension;
	}
	public void setCountryExtension(String countryExtension) {
		this.countryExtension = countryExtension==null?"":countryExtension.trim();
	}
	public String getGermplasmName() {
		return germplasmName;
	}
	public void setGermplasmName(String germplasmName) {
		this.germplasmName = germplasmName==null?"":germplasmName.trim();
	}
	public String getSumArea() {
		return sumArea;
	}
	public void setSumArea(String sumArea) {
		this.sumArea = sumArea==null?"":sumArea.trim();
	}
	public String getSumAmountSeedDistributed() {
		return sumAmountSeedDistributed;
	}
	public void setSumAmountSeedDistributed(String sumAmountSeedDistributed) {
		this.sumAmountSeedDistributed = sumAmountSeedDistributed==null?"":sumAmountSeedDistributed.trim();
	}
	public String getSumNumFarmersAdopted() {
		return sumNumFarmersAdopted;
	}
	public void setSumNumFarmersAdopted(String sumNumFarmersAdopted) {
		this.sumNumFarmersAdopted = sumNumFarmersAdopted==null?"":sumNumFarmersAdopted.trim();
	}
	public String getSumAmountSeedsProduced() {
		return sumAmountSeedsProduced;
	}
	public void setSumAmountSeedsProduced(String sumAmountSeedsProduced) {
		this.sumAmountSeedsProduced = sumAmountSeedsProduced==null?"":sumAmountSeedsProduced.trim();
	}
	public String getYearExtention() {
		return yearExtention;
	}
	public void setYearExtention(String yearExtention) {
		this.yearExtention = yearExtention==null?"":yearExtention.trim();
	}
	public String getCounrtyRelease() {
		return counrtyRelease;
	}
	public void setCounrtyRelease(String counrtyRelease) {
		this.counrtyRelease = counrtyRelease==null?"":counrtyRelease.trim();
	}
	public String getYearRelease() {
		return yearRelease;
	}
	public void setYearRelease(String yearRelease) {
		this.yearRelease = yearRelease==null?"":yearRelease.trim();
	}
	public String getCountVariety() {
		return countVariety;
	}
	public void setCountVariety(String countVariety) {
		this.countVariety = countVariety==null?"":countVariety.trim();
	}
	
	
	
	
	
	
	
	
	

}
