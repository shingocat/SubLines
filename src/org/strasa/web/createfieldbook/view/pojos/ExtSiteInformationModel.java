package org.strasa.web.createfieldbook.view.pojos;

import org.strasa.web.uploadstudy.view.pojos.StudySiteInfoModel;

public class ExtSiteInformationModel extends StudySiteInfoModel {

	private String str_locationid;
	private String str_sowingdate;
	private String str_harvestdate;
	private int int_plotsize;

	public String getStr_locationid() {
		return str_locationid;
	}

	public void setStr_locationid(String str_locationid) {
		this.str_locationid = str_locationid;
	}

	public String getStr_sowingdate() {
		return str_sowingdate;
	}

	public void setStr_sowingdate(String str_sowingdate) {
		this.str_sowingdate = str_sowingdate;
	}

	public String getStr_harvestdate() {
		return str_harvestdate;
	}

	public void setStr_harvestdate(String str_harvestdate) {
		this.str_harvestdate = str_harvestdate;
	}

	public int getInt_plotsize() {
		return int_plotsize;
	}

	public void setInt_plotsize(int int_plotsize) {
		this.int_plotsize = int_plotsize;
	}
}
