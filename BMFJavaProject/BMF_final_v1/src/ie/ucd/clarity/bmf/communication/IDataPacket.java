package ie.ucd.clarity.bmf.communication;

public interface IDataPacket extends IBMFPacket  {

	public int[] getCounter();

	public int[] getRequestID();

	public long[] getResult();

	public int[] getSenderID();
	
}
