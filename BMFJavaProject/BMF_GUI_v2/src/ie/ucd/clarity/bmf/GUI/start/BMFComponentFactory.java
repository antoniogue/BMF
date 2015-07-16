package ie.ucd.clarity.bmf.GUI.start;

import java.util.LinkedList;

import ie.ucd.clarity.bmf.GUI.Consumer;
import ie.ucd.clarity.bmf.aggregator.AggregatorManager;
import ie.ucd.clarity.bmf.communication.MessageBuilder;
import ie.ucd.clarity.bmf.communication.MessageParser;
import ie.ucd.clarity.bmf.communication.PacketBuilder;
import ie.ucd.clarity.bmf.configurationfile.ConfigurationFileReader;
import ie.ucd.clarity.bmf.data.file.FileDataSaverBuilder;
import ie.ucd.clarity.bmf.network.manager.NetworkManager;
import ie.ucd.clarity.bmf.network.manager.configurationPackets.ConfigurationPacketsManager;
import ie.ucd.clarity.bmf.network.manager.nodesAndGroups.BMFNodesAndGroupsManager;
import ie.ucd.clarity.bmf.network.platform.IPlatform;

public class BMFComponentFactory {

	public static final int TYPE_CONSUMER = 0;

	public static BMFMarker create(LinkedList<IPlatform> platform, int type) {

		if (type == TYPE_CONSUMER) {
			return inizializzaConsumer(platform);

		} else {
			return null;
		}

	}

	private static BMFMarker inizializzaConsumer(LinkedList<IPlatform> platformList) {
		Consumer c = new Consumer();
		NetworkManager nm = new NetworkManager();
		BMFNodesAndGroupsManager bmf = new BMFNodesAndGroupsManager();
		PacketBuilder pb = new PacketBuilder();
		FileDataSaverBuilder fdsb = new FileDataSaverBuilder();
		AggregatorManager ag = new AggregatorManager();
		ConfigurationPacketsManager cpm = new ConfigurationPacketsManager();
		ConfigurationFileReader cfr = new ConfigurationFileReader();
		c.setINetworkManager(nm);
		nm.setIMessageParser(new MessageParser());
		nm.setIMessageBuilder(new MessageBuilder());
		c.setIFileDataSaverBuilder(fdsb);
		c.setIAggregatorManager(ag);
		c.setIPacketBuilder(pb);
		c.setIBMFNodesAndGroupsManager(bmf);
		bmf.setINetworkManager(nm);
		bmf.setIPacketBuilder(pb);
		c.setIConfigurationPacketsManager(cpm);
		c.setIConfigurationFileReader(cfr);
		// TinyOSBMFManager t=new TinyOSBMFManager();
		for (IPlatform plat : platformList) {
			nm.addIPlatform(plat);
		}

		return c;
	}

}
