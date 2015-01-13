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

import org.strasa.middleware.model.Germplasm;

public class GermplasmExt extends Germplasm {

	@Override
	public String toString() {
		return "GermplasmExt [germplasmtype=" + germplasmtype + ", other_name=" + other_name + "]";
	}

	private String germplasmtype;

	private String other_name;
	private String biotic, abiotic, grainQuality, majorGenes;

	public String getBiotic() {
		return biotic;
	}

	public void setBiotic(String biotic) {
		this.biotic = biotic;
	}

	public String getAbiotic() {
		return abiotic;
	}

	public void setAbiotic(String abiotic) {
		this.abiotic = abiotic;
	}

	public String getGrainQuality() {
		return grainQuality;
	}

	public void setGrainQuality(String grainQuality) {
		this.grainQuality = grainQuality;
	}

	public String getMajorGenes() {
		return majorGenes;
	}

	public void setMajorGenes(String majorGenes) {
		this.majorGenes = majorGenes;
	}

	public String getOther_name() {
		return other_name;
	}

	public void setOther_name(String other_name) {
		this.other_name = other_name;
	}

	public String getGermplasmtype() {
		return germplasmtype;
	}

	public void setGermplasmtype(String germplasmtype) {
		this.germplasmtype = germplasmtype;
	}

}