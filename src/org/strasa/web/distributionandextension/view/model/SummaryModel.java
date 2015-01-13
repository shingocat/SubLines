package org.strasa.web.distributionandextension.view.model;

import java.util.List;

public class SummaryModel extends DistributionAndExtensionListModel {
	
	double sumPlantingArea;
	double sumAmountSeedDistributed;
	double sumNumFarmersAdopted;
	double sumAmountSeedsProduced;

	int countVariety;
	List<String> germplasmVarietyNames;

	public List<String> getGermplasmVarietyNames() {
		return germplasmVarietyNames;
	}

	public void setGermplasmVarietyNames(List<String> germplasmVarietyNames) {
		this.germplasmVarietyNames = germplasmVarietyNames;
	}

	public double getSumPlantingArea() {
		return sumPlantingArea;
	}

	public void setSumPlantingArea(double sumPlantingArea) {
		this.sumPlantingArea = sumPlantingArea;
	}
	public double getSumAmountSeedDistributed() {
		return sumAmountSeedDistributed;
	}

	public void setSumAmountSeedDistributed(double sumAmountSeedDistributed) {
		this.sumAmountSeedDistributed = sumAmountSeedDistributed;
	}

	public double getSumNumFarmersAdopted() {
		return sumNumFarmersAdopted;
	}

	public void setSumNumFarmersAdopted(double sumNumFarmersAdopted) {
		this.sumNumFarmersAdopted = sumNumFarmersAdopted;
	}

	public double getSumAmountSeedsProduced() {
		return sumAmountSeedsProduced;
	}

	public void setSumAmountSeedsProduced(double sumAmountSeedsProduced) {
		this.sumAmountSeedsProduced = sumAmountSeedsProduced;
	}

	public int getCountVariety() {
		return countVariety;
	}

	public void setCountVariety(int countVariety) {
		this.countVariety = countVariety;
	}
	
	

}
