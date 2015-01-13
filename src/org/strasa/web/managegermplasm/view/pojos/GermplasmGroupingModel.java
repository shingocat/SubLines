package org.strasa.web.managegermplasm.view.pojos;

import java.util.Comparator;
import java.util.List;

import org.strasa.web.uploadstudy.view.pojos.GermplasmDeepInfoModel;
import org.zkoss.zul.GroupsModelArray;

public class GermplasmGroupingModel extends
		GroupsModelArray<GermplasmDeepInfoModel, String, String, Object> {

	private static final String footerString = "Total %d items";

	private boolean showGroup;

	public GermplasmGroupingModel(List<GermplasmDeepInfoModel> data,
			Comparator<GermplasmDeepInfoModel> cmpr, boolean showGroup) {
		super(data.toArray(new GermplasmDeepInfoModel[0]), cmpr);

		this.showGroup = showGroup;
	}

	protected String createGroupHead(GermplasmDeepInfoModel[] groupdata,
			int index, int col) {
		String ret = "";
		if (groupdata.length > 0) {
			ret = "Known Germplasm";
			if (groupdata[0].getId() == null)
				ret = "Unknown Germplasm";
		}

		return ret;
	}

	protected String createGroupFoot(GermplasmDeepInfoModel[] groupdata,
			int index, int col) {
		return String.format(footerString, groupdata.length);
	}

	public boolean hasGroupfoot(int groupIndex) {
		boolean retBool = false;

		if (showGroup) {
			retBool = super.hasGroupfoot(groupIndex);
		}

		return retBool;
	}
}
