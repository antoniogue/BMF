package ie.ucd.clarity.bmf.communication;

import ie.ucd.clarity.bmf.common.InvalidPacketTypeException;

public interface IMessageParser {

	public IBMFPacket parse(byte[] pkt) throws InvalidPacketTypeException;
	
}
