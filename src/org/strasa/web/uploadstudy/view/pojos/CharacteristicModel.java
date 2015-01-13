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

public class CharacteristicModel {
	private String name;
	private boolean value = true;
	private int primaryid;
	private boolean exist;
	private String attribute;

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}

	public int getPrimaryid() {
		return primaryid;
	}

	public void setPrimaryid(int primaryid) {
		this.primaryid = primaryid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;

	}

	public CharacteristicModel(String name, Integer id, String attr) {
		this.name = name;
		this.primaryid = id;
		attribute = attr;
	}

	public CharacteristicModel(String name, String attr) {
		this.name = name;
		attribute = attr;
	}

	public CharacteristicModel(CharacteristicModel charRecord) {
		// TODO Auto-generated constructor stub

	}

}