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
package org.strasa.web.uploadstudy.view.pojos;

import java.util.Arrays;
import java.util.List;

public class UploadCSVDataVariableModel {
	
	private String currentVariable;
	private String newVariable;
	private boolean isHeaderExisting = false;
	List<String> systemHeader = Arrays.asList("GName","Site","Location","Season","Year");
	public boolean modified;
	public boolean isEditable() {
		if(systemHeader.contains(currentVariable)) return false;
		
		return true;
	}

	
	public String getCurrentVariable() {
		return currentVariable;
	}

	public void setCurrentVariable(String currentVariable) {
		this.currentVariable = currentVariable;
	}

	public String getNewVariable() {
		return newVariable;
	}

	public void setNewVariable(String newVariable) {
		this.newVariable = newVariable;
	}

	public boolean isHeaderExisting() {
		return isHeaderExisting;
	}

	public void setHeaderExisting(boolean isHeaderExisting) {
		this.isHeaderExisting = isHeaderExisting;
	}

	public UploadCSVDataVariableModel(String curr, String newVar){
		currentVariable = curr;
		newVariable = newVar;
	}
	public UploadCSVDataVariableModel(String curr, String newVar,boolean ex){
		currentVariable = curr;
		newVariable = newVar;
		isHeaderExisting = ex;
	}
}
