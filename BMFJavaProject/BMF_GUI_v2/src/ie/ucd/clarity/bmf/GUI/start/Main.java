package ie.ucd.clarity.bmf.GUI.start;

import ie.ucd.clarity.bmf.GUI.Consumer;
import ie.ucd.clarity.bmf.GUI.InitialFrame;
import ie.ucd.clarity.bmf.network.platform.IPlatform;
import ie.ucd.clarity.bmf.network.platform.tinyOS.TinyOSBMFManager;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			LinkedList<IPlatform> list=new LinkedList<IPlatform>();
			TinyOSBMFManager t=new TinyOSBMFManager();
			list.add(t);
			
			Consumer tc=(Consumer)BMFComponentFactory.create(list,BMFComponentFactory.TYPE_CONSUMER);
			
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new InitialFrame(tc).setVisible(true);
            tc.openDataSaverConnections();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InitialFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(InitialFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(InitialFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(InitialFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

	}

}
