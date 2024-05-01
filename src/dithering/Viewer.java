package dithering;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * TODO: add javadoc
 */
public class Viewer extends JFrame {
    private ImageComponent imageComponent;
    private BufferedImage image;
    private Transformer transformer;

    private final String imageFileName = "gradient.png";

    /**
     * Construct a new main window.
     */
    public Viewer() {
        setPreferredSize(new Dimension(600, 600));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(String.format("Dithering Final Project"));

        loadImage();
        transformer = new Transformer(image);

        imageComponent = new ImageComponent(transformer.getThreshhold());
        add(imageComponent, BorderLayout.CENTER);
        pack();
    }

    private void loadImage() {
        try {
            image = ImageIO.read(new File("images/"+imageFileName));
        } catch (IOException e) {
            System.out.println("Error loading image.");
        }
    }

    private int[][] getRandomArray() {
        int[][] img = new int[256][256];
        for(int x=0;x<img.length;x++) {
            for(int y=0;y<img[0].length;y++) {
                img[x][y] = (int)(Math.random() * Integer.MAX_VALUE);
            }
        }
        return img;
    }

    public static void main(String[] args) {
        Viewer frame = new Viewer();
        frame.setVisible(true);
    }
}