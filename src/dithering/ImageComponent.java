package dithering;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JComponent;

public class ImageComponent extends JComponent {
    private static final int PIXEL_SIZE = 40;
    int[][] img;

    public ImageComponent(int[][] img) {
        this.img = img;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        for(int x=0;x<img.length;x++) {
            for(int y=0;y<img[0].length;y++) {
                Color color = new Color(img[x][y]);
                g2.setPaint(color);
                g2.fillRect((y*PIXEL_SIZE), (x*PIXEL_SIZE), PIXEL_SIZE, PIXEL_SIZE);
            }
        }
    }
}
