package ie.ucd.clarity.bmf.GUI.common;

import java.util.ArrayList;

import javax.swing.tree.TreePath;

import ie.ucd.clarity.bmf.common.Constants;
import ie.ucd.clarity.bmf.common.InvalidPacketParametersException;
import ie.ucd.clarity.bmf.communication.IConfigurationPacket;


/**
 * @author  Matthew
 */
public class BMFRequestObjectGUI implements BMFObjectGUI{

	/**
	 * @uml.property  name="request"
	 * @uml.associationEnd  
	 */
	IConfigurationPacket request;
	ArrayList<BMFNodeGUI> nodes;
	/**
	 * @uml.property  name="name"
	 */
	String name;
    /**
	 * @uml.property  name="charts"
	 * @uml.associationEnd  
	 */
    ChartsObject charts;
	/**
	 * @uml.property  name="active"
	 */
	boolean active;
	/**
	 * @uml.property  name="isAggregation"
	 */
	boolean isAggregation;
	
	

	public BMFRequestObjectGUI(BMFRequestObjectGUI req, IConfigurationPacket newReq) {
		this.active=req.active;
		this.isAggregation=req.isAggregation;
		this.name=req.name;
		this.nodes=req.nodes;
		this.request=clone(req.request, newReq);
	}

	private IConfigurationPacket clone(IConfigurationPacket request2, IConfigurationPacket newReq) {
		try {
			newReq.setAction(request2.getAction());
		newReq.setActuatorParams(request2.getActuatorParams());
		newReq.setConfigurationType(request2.getConfigurationType());
		newReq.setDataType(request2.getDataType());
		newReq.setDestination(request2.getDestination());
		newReq.setLifetimeTimescale(request2.getLifetimeTimescale());
		newReq.setLifetimeValue(request2.getLifetimeValue());
		newReq.setPeriodTimescale(request2.getPeriodTimescale());
		
			newReq.setPeriodValue(request2.getPeriodValue());
		} catch (InvalidPacketParametersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newReq.setSensor_actuatorType(request2.getSensor_actuatorType());
		newReq.setSensorIfThreshold(request2.getSensorIfThreshold());
		if(request2.getSensorTypeMoreThreshold()!=null)
		newReq.setSensorTypeMoreThreshold(request2.getSensorTypeMoreThreshold());
		newReq.setSyntheticData(request2.getSyntheticData());
		newReq.setThresholdNumber(request2.getThresholdNumber());
		if(request2.getThresholdType()!=null)
		newReq.setThresholdType(request2.getThresholdType());
		if(request2.getThresholdValue()!=null)
		newReq.setThresholdValue(request2.getThresholdValue());
		return newReq;
	}

	public BMFRequestObjectGUI(String name, IConfigurationPacket request, ArrayList<BMFNodeGUI> nodes, boolean isAggregation) {
		super();
		this.request = request;
		this.name=name;		
		this.nodes=nodes;
		active=true;
                this.isAggregation=isAggregation;
	}

        /**
		 * @return
		 * @uml.property  name="isAggregation"
		 */
        public boolean isAggregation(){
            return isAggregation;
        }
	
	/**
	 * @return  the request
	 * @uml.property  name="request"
	 */
	public IConfigurationPacket getRequest() {
		return request;
	}

	public ArrayList<BMFNodeGUI> getResult() {
		// TODO Auto-generated method stub
		return nodes;
	}

    /**
	 * @return
	 * @uml.property  name="charts"
	 */
    public ChartsObject getCharts() {
        return charts;
    }

    /**
	 * @param charts
	 * @uml.property  name="charts"
	 */
    public void setCharts(ChartsObject charts) {
        this.charts = charts;
    }
	
	


	/**
	 * @return  the active
	 * @uml.property  name="active"
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active  the active to set
	 * @uml.property  name="active"
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	public String toString(){
		if(this.isActive())
			return "*"+this.name+" ID:"+request.getRequestID();
		else return name+" ID:"+request.getRequestID();
	}
	
	public String getStringDetails(){
        String s = "BMF REQUEST name = "+name+" ID = "+request.getRequestID()+"\n";
        if(this.isActive()) s += "ACTIVE REQUEST\n";
        else s += "NOT ACTIVE REQUEST\n";
        s += "     ConfigurationType "+ request.getConfigurationType()+"\n";
        if(request.getConfigurationType() == Constants.PKT_TYPE_CONFIGURATION_SCHEDULE){
            s += "     PeriodTimescale "+ request.getPeriodTimescale()+"\n";
            s += "     PeriodValue "+ request.getPeriodValue()+"\n";
            s += "     LifetimeTimescale "+ request.getLifetimeTimescale()+"\n";
            s += "     LifetimeValue "+ request.getLifetimeValue()+"\n";
            s += "     Action "+ request.getAction()+"\n";
            s += "     Sensor_actuatorType "+ request.getSensor_actuatorType()+"\n";
            s += "     ActuatorParams "+ request.getActuatorParams()+"\n";
            s += "     DataType "+ request.getDataType()+"\n";
            s += "     SyntheticData "+ request.getSyntheticData()+"\n";
            s += "     ThresholdNumber "+ request.getThresholdNumber()+"\n";
            s += "     SensorIfThreshold "+ request.getSensorIfThreshold()+"\n";

            if(request.getThresholdNumber() > 0){
                for(int i=0;i<request.getThresholdType().length;i++){
                    s += "     ThresholdType["+i+"] = " + request.getThresholdType()[i]+"\n";
                }

                for(int i=0;i<request.getThresholdValue().length;i++){
                    s += "     ThresholdValue["+i+"] = " + request.getThresholdValue()[i]+"\n";
                }
            }

            if(request.getThresholdNumber() > 1){
                for(int i=0;i<request.getSensorTypeMoreThreshold().length;i++){
                    s += "     SensorTypeMoreThreshold["+i+"] = " + request.getSensorTypeMoreThreshold()[i]+"\n";
                }
            }
        }

        return s;
    }

	/**
	 * @return
	 * @uml.property  name="name"
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public BMFObjectGUI setPath(TreePath path) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
