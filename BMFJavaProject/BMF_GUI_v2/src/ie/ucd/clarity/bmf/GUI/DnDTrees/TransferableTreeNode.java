/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ie.ucd.clarity.bmf.GUI.DnDTrees;

import ie.ucd.clarity.bmf.GUI.common.BMFGroupGUI;
import ie.ucd.clarity.bmf.GUI.common.BMFNodeGUI;
import ie.ucd.clarity.bmf.GUI.common.BMFObjectGUI;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * @author  Andrea
 */
public class TransferableTreeNode implements Transferable {

  public static DataFlavor NODE_OBJECT_FLAVOR = new DataFlavor(BMFNodeGUI.class,
      "Node Object");

  DataFlavor flavors[] = { NODE_OBJECT_FLAVOR};

  /**
 * @uml.property  name="node"
 * @uml.associationEnd  
 */
BMFObjectGUI node;

  public TransferableTreeNode(BMFObjectGUI tp) {
    node = tp;
  }

  public synchronized DataFlavor[] getTransferDataFlavors() {
    return flavors;
  }

  public boolean isDataFlavorSupported(DataFlavor flavor) {
    return ((flavor.getRepresentationClass() == BMFNodeGUI.class));
  }

  public synchronized Object getTransferData(DataFlavor flavor)
      throws UnsupportedFlavorException, IOException {
    if (isDataFlavorSupported(flavor)) {
      return (Object) node;
    } else {
      throw new UnsupportedFlavorException(flavor);
    }
  }
}
