package ie.ucd.clarity.bmf.network.manager.configurationPackets;

import ie.ucd.clarity.bmf.common.Constants;
import ie.ucd.clarity.bmf.communication.IConfigurationPacket;

import java.util.Iterator;
import java.util.Vector;

public class ConfigurationPacketsManager implements IConfigurationPacketsManager{

	
	private Vector<BMFRequest> requestsVector = new Vector<BMFRequest>();
	
	private int getFirstAvailableID() {
		if(this.requestsVector.size() >= Constants.REQUEST_ID_MAX_VALUE){
            return Constants.REQUEST_ID_MAX_VALUE+1;
        }

        //naive algorithm to find the first ID available
        int[] free = new int[Constants.REQUEST_ID_MAX_VALUE]; // free[i] will be 1 if the id 1 is used
        for(int i=0; i<this.requestsVector.size();i++)
            free[requestsVector.elementAt(i).getRequestID() - 1] = 1; // zero is not an accepted value
        for(int i=0; i<Constants.REQUEST_ID_MAX_VALUE;i++)
            if(free[i]!=1) return i+1;

        return Constants.REQUEST_ID_MAX_VALUE+1;
	}
	
	public IConfigurationPacket getBMFRequestByID(int configurationID) {
        for(int i=0; i<this.requestsVector.size();i++){
            if(requestsVector.elementAt(i).getRequestID() == configurationID)
                return requestsVector.elementAt(i).getIConfiguration();
        }
        return null;
	}
	
	public int addIConfigurationPacket(String name, IConfigurationPacket iConfigurationPacket ) {
		
		int firstAvailableID = getFirstAvailableID();
		
		BMFRequest req = new BMFRequest (name, firstAvailableID, iConfigurationPacket);
		this.requestsVector.add(req);
		
		return firstAvailableID;
		
	}
	
	public int addIConfigurationPacket(IConfigurationPacket iConfigurationPacket ) {
		
		int firstAvailableID = getFirstAvailableID();
		
		BMFRequest req = new BMFRequest (firstAvailableID, iConfigurationPacket);
		this.requestsVector.add(req);
		
		return firstAvailableID;
		
	}
	
	public void activateIConfigurationTimerByID(int configurationID, IBMFRequestDurationListener listener) {
		for (Iterator<BMFRequest> iterator = this.requestsVector.iterator(); iterator.hasNext();) {
			BMFRequest req = (BMFRequest) iterator.next();
			if (req.getRequestID()==configurationID)
				req.activate(listener);
		}
	}
	
	public void deactivateIConfigurationTimerByID(int configurationID) {
		for (Iterator<BMFRequest> iterator = this.requestsVector.iterator(); iterator.hasNext();) {
			BMFRequest req = (BMFRequest) iterator.next();
			if (req.getRequestID()==configurationID)
				req.deactivate();
		}
	}

	@Override
	public String getIConfigurationNameByID(int configurationID) {
		for(int i=0; i<this.requestsVector.size();i++){
            if(requestsVector.elementAt(i).getRequestID() == configurationID)
                return requestsVector.elementAt(i).getRequestName();
        }
        return null;
	}
}
