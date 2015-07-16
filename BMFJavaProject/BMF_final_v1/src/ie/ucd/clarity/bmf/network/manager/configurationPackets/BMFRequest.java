package ie.ucd.clarity.bmf.network.manager.configurationPackets;

import ie.ucd.clarity.bmf.common.CodeConversion;
import ie.ucd.clarity.bmf.common.Constants;
import ie.ucd.clarity.bmf.communication.IConfigurationPacket;

/**
 * @author  Matthew
 */
public class BMFRequest extends Thread {
	
    private String configurationName;
    /**
	 * @uml.property  name="active"
	 */
    private boolean active;
    /**
	 * @uml.property  name="iConfiguration"
	 * @uml.associationEnd  
	 */
    private IConfigurationPacket iConfiguration;
    /**
	 * @uml.property  name="listener"
	 * @uml.associationEnd  
	 */
    private IBMFRequestDurationListener listener;
    
	public BMFRequest(String configurationName, int configurationId, 
			IConfigurationPacket iConfiguration) {
		this.configurationName = configurationName;
		this.active = false;
		this.iConfiguration = iConfiguration;
		this.iConfiguration.setRequestID(configurationId);
	}
	
	public BMFRequest(int configurationId,
			IConfigurationPacket iConfiguration) {
		this.configurationName = null;
		this.active = false;
		this.iConfiguration = iConfiguration;
		this.iConfiguration.setRequestID(configurationId);
	}

	public String getRequestName() {
		return this.configurationName;
	}

	/**
	 * @return
	 * @uml.property  name="active"
	 */
	public boolean isActive() {
		return this.active;
	}
	
	public void activate(IBMFRequestDurationListener listener) {
		this.active = true;
		this.listener = listener;
		if(this.iConfiguration.getLifetimeValue() != 0) this.start();
	}
	
	public void deactivate() {
		this.active = false;
	}
	
	public IConfigurationPacket getIConfiguration() {
		return iConfiguration;
	}
	
	public void setIConfiguration(IConfigurationPacket iConfiguration) {
		this.iConfiguration = iConfiguration;
	}
	
    @Override
    public void run(){

        try {
            Thread.sleep(CodeConversion.getMillisecMultiplierFromTimescale(iConfiguration.getLifetimeTimescale())
                    * iConfiguration.getLifetimeValue());
        } catch (InterruptedException ex) {
           ex.printStackTrace();
        }
        
        if (this.isActive())
        	this.listener.requestExpired(this.iConfiguration);

    }
    
    @Override
    public boolean equals(Object o){
        if(!(o instanceof BMFRequest)) return false;
        if(((BMFRequest)o).getRequestID() == this.getRequestID()) return true;
        return false;
    }
    
    public String getStringDetails(){
        String s = "BMF REQUEST name = "+getRequestName()+" ID = "+this.getRequestID()+"\n";
        if(this.isActive()) s += "ACTIVE REQUEST\n";
        else s += "NOT ACTIVE REQUEST\n";
        s += "     ConfigurationType "+ this.iConfiguration.getConfigurationType()+"\n";
        if(this.iConfiguration.getConfigurationType() == Constants.PKT_TYPE_CONFIGURATION_SCHEDULE){
            s += "     PeriodTimescale "+ this.iConfiguration.getPeriodTimescale()+"\n";
            s += "     PeriodValue "+ this.iConfiguration.getPeriodValue()+"\n";
            s += "     LifetimeTimescale "+ this.iConfiguration.getLifetimeTimescale()+"\n";
            s += "     LifetimeValue "+ this.iConfiguration.getLifetimeValue()+"\n";
            s += "     Action "+ this.iConfiguration.getAction()+"\n";
            s += "     Sensor_actuatorType "+ this.iConfiguration.getSensor_actuatorType()+"\n";
            s += "     ActuatorParams "+ this.iConfiguration.getActuatorParams()+"\n";
            s += "     DataType "+ this.iConfiguration.getDataType()+"\n";
            s += "     SyntheticData "+ this.iConfiguration.getSyntheticData()+"\n";
            s += "     ThresholdNumber "+ this.iConfiguration.getThresholdNumber()+"\n";
            s += "     SensorIfThreshold "+ this.iConfiguration.getSensorIfThreshold()+"\n";

            if(this.iConfiguration.getThresholdNumber() > 0){
                for(int i=0;i<this.iConfiguration.getThresholdType().length;i++){
                    s += "     ThresholdType["+i+"] = " + this.iConfiguration.getThresholdType()[i]+"\n";
                }

                for(int i=0;i<this.iConfiguration.getThresholdValue().length;i++){
                    s += "     ThresholdValue["+i+"] = " + this.iConfiguration.getThresholdValue()[i]+"\n";
                }
            }

            if(this.iConfiguration.getThresholdNumber() > 1){
                for(int i=0;i<this.iConfiguration.getSensorTypeMoreThreshold().length;i++){
                    s += "     SensorTypeMoreThreshold["+i+"] = " + this.iConfiguration.getSensorTypeMoreThreshold()[i]+"\n";
                }
            }
        }

        return s;
    }

	public int getRequestID() {
		return this.iConfiguration.getRequestID();
	}


}
