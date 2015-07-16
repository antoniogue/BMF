package ie.ucd.clarity.bmf.network.manager;

import java.io.Serializable;



public interface IBMFDataIn extends Serializable{
	
	public int getSenderID();

	public int getRequestID();

	public long getResult();
	
	public int getCounter();
	
}