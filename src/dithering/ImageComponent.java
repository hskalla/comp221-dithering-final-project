package dithering;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JComponent;

public class ImageComponent extends JComponent {
    private static final int PIXEL_SIZE = 2;
    char[][] img;

    public ImageComponent(char[][] img) {
        this.img = img;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        for(int x=0;x<img.length;x++) {
            for(int y=0;y<img[0].length;y++) {
                int value = (int)(img[x][y]/258.0);
                Color color = new Color(0,0,value);
                g2.setPaint(color);
                g2.fillRect((x*PIXEL_SIZE), (y*PIXEL_SIZE), PIXEL_SIZE, PIXEL_SIZE);
            }
        }
    }
}
