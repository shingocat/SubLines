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

public class GermplasmFilter {

	private String gname = "", germplasmType = "", breeder = "", maleParent = "", femaleParent = "";

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = "".equals(gname) ? "" : gname.toLowerCase().trim();
	}

	public String getGermplasmType() {
		return germplasmType;
	}

	public void setGermplasmType(String germplasmType) {
		this.germplasmType = germplasmType;
	}

	public String getBreeder() {
		return breeder;
	}

	public void setBreeder(String breeder) {
		this.breeder = breeder;
	}

	public String getMaleParent() {
		return maleParent;
	}

	public void setMaleParent(String maleParent) {
		this.maleParent = maleParent;
	}

	public String getFemaleParent() {
		return femaleParent;
	}

	public void setFemaleParent(String femaleParent) {
		this.femaleParent = femaleParent;
	}

	public boolean equals(GermplasmDeepInfoModel data) {

		return data.getGermplasmname().startsWith(gname) && data.getSelectedGermplasmType().getGermplasmtype().startsWith(germplasmType) && data.getBreeder().startsWith(breeder) && data.getMaleparent().startsWith(maleParent) && data.getFemaleparent().startsWith(femaleParent);
	}

}
