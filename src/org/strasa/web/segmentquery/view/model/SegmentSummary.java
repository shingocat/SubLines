package org.strasa.web.segmentquery.view.model;

import org.strasa.middleware.manager.SegmentManagerImpl;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.Chart;

public class SegmentSummary {
	
	@Wire //using view model, it don't need to wire component, but bind attributes
	Chart chart; 
	
	private String type;
	private CategoryModel model;
	private SegmentManagerImpl sManagerImpl = new SegmentManagerImpl();
	
	@Init
	public void init()
	{
		type = "column";
		model = sManagerImpl.getSegmentSummaryOnChromosomeAndDonor();
	}
	
	public CategoryModel getModel()
	{
		return model;
	}
	
	public String getType()
	{
		return type;
	}
	
}
