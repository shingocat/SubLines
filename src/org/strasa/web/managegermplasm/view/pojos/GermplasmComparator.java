package org.strasa.web.managegermplasm.view.pojos;

import java.io.Serializable;
import java.util.Comparator;

import org.strasa.web.uploadstudy.view.pojos.GermplasmDeepInfoModel;
import org.zkoss.zul.GroupComparator;

public class GermplasmComparator implements Comparator<GermplasmDeepInfoModel>, GroupComparator<GermplasmDeepInfoModel>,
		Serializable {
	private static final long serialVersionUID = 1L;

	public int compare(GermplasmDeepInfoModel o1, GermplasmDeepInfoModel o2) {
		return o1.getCategory().compareTo(o2.getCategory().toString());
	}

	public int compareGroup(GermplasmDeepInfoModel o1, GermplasmDeepInfoModel o2) {
		if (o1.getCategory().equals(o2.getCategory()))
			return 0;
		else
			return 1;
	}
}