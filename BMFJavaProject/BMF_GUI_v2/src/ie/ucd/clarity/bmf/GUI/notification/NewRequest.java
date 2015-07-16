package ie.ucd.clarity.bmf.GUI.notification;

import ie.ucd.clarity.bmf.GUI.common.BMFRequestObjectGUI;
import ie.ucd.clarity.bmf.common.Constants;

/**
 * @author  Matthew
 */
public class NewRequest implements Notification{

	/**
	 * @uml.property  name="request"
	 * @uml.associationEnd  
	 */
	BMFRequestObjectGUI request;
	
	public NewRequest(BMFRequestObjectGUI req){		
		this.request=req;
		if(request.getRequest().getPACKET_TYPE()==Constants.PKT_TYPE_CONFIGURATION_UNSCHEDULE){
			this.request.setActive(false);
		}
	}

	/**
	 * @return  the request
	 * @uml.property  name="request"
	 */
	public BMFRequestObjectGUI getRequest() {
		return request;
	}
	
}
