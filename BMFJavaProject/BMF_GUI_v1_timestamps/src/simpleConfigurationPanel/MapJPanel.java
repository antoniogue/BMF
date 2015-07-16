/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simpleConfigurationPanel;

import ie.ucd.clarity.bmf.network.manager.BMFNode;
import ie.ucd.clarity.bmf.common.Constants;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MapJPanel extends JPanel {
    BufferedImage image;

    Vector<JLabel>  jLabels = new Vector();

    public MapJPanel(BufferedImage image) {
        this.image = image;
    }

    public void newNode(BMFNode newNode){
         final JLabel jl1 = new javax.swing.JLabel(getMoteIcon(newNode.getSensorBoard()));
         jl1.setText(""+newNode.getNodeID());
         jLabels.addElement(jl1);


         jl1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                System.out.println("LABEL MOUSE CLICKED");
                jLabelMoteMouseClicked(evt, jl1.getText());
            }

            public void mousePressed(java.awt.event.MouseEvent evt){
//                System.out.println("LABEL MOUSE PRESSED");
                jl1.getBounds();
            }

            public void mouseReleased(java.awt.event.MouseEvent evt){
//                System.out.println("LABEL MOUSE RELEASED");
                Dimension size = jl1.getPreferredSize();

                jl1.setBounds(evt.getX() + jl1.getX(), evt.getY() + jl1.getY(), size.width, size.height);
            }

            public void mouseDragged(java.awt.event.MouseEvent evt){
//                System.out.println("LABEL MOUSE DRAGGED");
            }
            public void mouseMoved(java.awt.event.MouseEvent evt){
//                System.out.println("LABEL MOUSE MOVED");
            }


        });

        Dimension size = jl1.getPreferredSize();
        jl1.setBounds((jLabels.size()*size.width)%this.getWidth(), 0, size.width, size.height);
        jl1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(jl1);

        this.updateUI();


    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        // Draw image, centered.
        int x = (w - image.getWidth())/2;
        int y = (h - image.getHeight())/2;
        g2.drawImage(image, x, y, this);
    }

    public static void main(String[] args) throws IOException {
        String path = "C:/Users/Antonio/Desktop/officePlan.bmp";
        BufferedImage image = ImageIO.read(new File(path));
        MapJPanel test = new MapJPanel(image);
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(test);
        f.setSize(500,400);
        f.setLocation(200,200);
        f.setVisible(true);
    }
    
    public ImageIcon getMoteIcon(int moteCode){
        try {

            switch(moteCode){
                case Constants.SENSORBOARD_TYPE_SBT80_TELOSB:
                case Constants.SENSORBOARD_TYPE_STD_TELOSB:
                     return new ImageIcon((ImageIO.read(this.getClass().getResource("/bmf/images/telosb.gif"))).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
                case Constants.SENSORBOARD_TYPE_TYNSENSORBRD:
                    return new ImageIcon((ImageIO.read(this.getClass().getResource("/bmf/images/tyndall.jpg"))).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
                case Constants.SENSORBOARD_TYPE_BLACKPLUG:
                case Constants.SENSORBOARD_TYPE_ACMEX2:
                    return new ImageIcon((ImageIO.read(this.getClass().getResource("/bmf/images/ACme.jpg"))).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
                case Constants.SENSORBOARD_TYPE_NO_SENSORBOARD:
                default:
                    return new ImageIcon((ImageIO.read(this.getClass().getResource("/bmf/images/telosb.gif"))).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
            }


        } catch (IOException ex) {
            ex.printStackTrace();

        }
        return null;
    }




    private void jLabelMoteMouseClicked(MouseEvent evt, String text) {
        //System.out.println("CLICKED "+text);
    }


}
