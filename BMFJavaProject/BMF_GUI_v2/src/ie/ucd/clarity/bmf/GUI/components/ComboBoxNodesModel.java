/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ie.ucd.clarity.bmf.GUI.components;

import ie.ucd.clarity.bmf.GUI.common.BMFNodeGUI;

import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Andrea
 */
public class ComboBoxNodesModel extends DefaultComboBoxModel {

        ArrayList<BMFNodeGUI> array;

        public ComboBoxNodesModel(ArrayList<BMFNodeGUI> array){
            this.array=array;
        }
        @Override
        public int getSize() {
            return array.size();
        }

        @Override
        public Object getElementAt(int index) {
            return array.get(index);
        }
    }
