/*
 * Data Management and Analysis (DMAS) - International Rice Research Institute 2013-2015
 * 
 *   DMAS is an opensource Data management and statistical analysis mainly for STRASA Project: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *  DMAS is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with DMAS.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * 
 * 
 */
package org.strasa.web.uploadstudy.view.model;

import org.strasa.web.common.api.ProcessTabViewModel;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

public class TestTab extends ProcessTabViewModel{

	private double sampleID;

	
	public double getSampleID() {
		return sampleID;
	}


	public void setSampleID(double sampleID) {
		this.sampleID = sampleID;
	}

//	@Init
//	public void init(@ExecutionArgParam("studyID")double newVal){
//		sampleID = newVal;
//	}
	
	
	@GlobalCommand
	@NotifyChange("sampleID")
	public void testGlobalCom(@BindingParam("studyID")double newVal){
		sampleID = newVal;
	}


@Override
public boolean validateTab() {
	// TODO Auto-generated method stub
	return false;
}

@Init
public void init(@ExecutionArgParam("studyID") double studyID) {
	sampleID = studyID;	
}
	
	
	
}
