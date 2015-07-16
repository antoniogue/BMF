package ie.ucd.clarity.bmf.network.manager.configurationPackets;

import ie.ucd.clarity.bmf.communication.IConfigurationPacket;

/**
 *
 * @author Antonio
 */
public interface IBMFRequestDurationListener {

    public void requestExpired(IConfigurationPacket iConfiguration);

}
