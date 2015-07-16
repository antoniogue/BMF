/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simpleConfigurationPanel;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Antonio
 */
public class GetIcon {

    public ImageIcon getOKIcon(){
        try {

            return new ImageIcon((ImageIO.read(this.getClass().getResource("/bmf/images/Ok.png"))).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
            //ImageIcon newIcon = new ImageIcon(newimg);

        } catch (IOException ex) {
            ex.printStackTrace();

        }
        return null;
    }

    public ImageIcon getCancelIcon(){
        try {

            return new ImageIcon((ImageIO.read(this.getClass().getResource("/bmf/images/Cancel.png"))).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
            //ImageIcon newIcon = new ImageIcon(newimg);

        } catch (IOException ex) {
            ex.printStackTrace();

        }
        return null;
    }

    public ImageIcon getInfoIcon(){
        try {
System.out.println(this.getClass().getResource("/bmf/images/info.png"));
            //return new ImageIcon((ImageIO.read(this.getClass().getResource("/bmf/images/info.png"))).getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH));
return new ImageIcon((ImageIO.read(new File("/images/info.png"))).getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH));
            //ImageIcon newIcon = new ImageIcon(newimg);

        } catch (IOException ex) {
            ex.printStackTrace();

        }
        return null;
    }

}
