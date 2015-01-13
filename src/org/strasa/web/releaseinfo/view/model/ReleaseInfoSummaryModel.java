package org.strasa.web.releaseinfo.view.model;

import java.util.List;

public class ReleaseInfoSummaryModel extends ReleaseInfoListModel{
	
	double sumArea;
	int countVariety;
	List<String> germplasmVarietyNames;

	public List<String> getGermplasmVarietyNames() {
		return germplasmVarietyNames;
	}

	public void setGermplasmVarietyNames(List<String> germplasmVarietyNames) {
		this.germplasmVarietyNames = germplasmVarietyNames;
	}

	public double getSumArea() {
		return sumArea;
	}

	public void setSumArea(double sumArea) {
		this.sumArea = sumArea;
	}

	public int getCountVariety() {
		return countVariety;
	}

	public void setCountVariety(int countVariety) {
		this.countVariety = countVariety;
	}
	
}
