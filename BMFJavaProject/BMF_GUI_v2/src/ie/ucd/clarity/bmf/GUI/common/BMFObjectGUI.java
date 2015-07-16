/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ie.ucd.clarity.bmf.GUI.common;

import javax.swing.tree.TreePath;

/**
 *
 * @author Andrea
 */
public interface BMFObjectGUI {
    public String getName();
    public BMFObjectGUI setPath(TreePath path);
}
