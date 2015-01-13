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
public class Container{


	private String lable;
	private String value;
    
	public String getLable() {
		return lable;
	}
	public String getValue() {
		return value;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	public void setValue(String value) {
		this.value = value;
	}
	 public Container (String lable, String value) {
		 this.lable = lable;
	  this.value = value;
	 }
	  }