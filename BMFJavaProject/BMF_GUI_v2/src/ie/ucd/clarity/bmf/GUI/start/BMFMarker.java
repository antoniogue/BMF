package ie.ucd.clarity.bmf.GUI.start;

import ie.ucd.clarity.bmf.network.manager.INetworkBMFListener;
import ie.ucd.clarity.bmf.network.manager.configurationPackets.IBMFRequestDurationListener;

import java.util.Observable;

public interface  BMFMarker extends INetworkBMFListener,
IBMFRequestDurationListener{

}
