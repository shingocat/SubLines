package applications2;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zhtml.Div;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;

@SuppressWarnings("rawtypes")
public class NavigationMenuForward extends GenericForwardComposer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void onClickMenu(MouseEvent event) {

		String zulFilePathName;
		Div bl = (Div) Path.getComponent("/mainlayout");
		/* get an instance of the searched CENTER layout area */
/*		Center center = bl.getCenter();

		 clear the center child comps 
		center.getChildren().clear();

//		Messagebox.show("inside"  + event.getTarget().getId());
		zulFilePathName = event.getTarget().getId() + ".zul";
		 create the page and put it in the center layout area 

		Executions.createComponents(zulFilePathName, center, null);*/
		bl.detach();
		System.out.println("Test");
		
	}
	
	@Command("open")
	public void open(@ContextParam(ContextType.COMPONENT) Component component){
		
		System.out.println("Test");
	}
	
	
	

}
