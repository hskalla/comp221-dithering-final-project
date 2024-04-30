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

    private final String imageFileName = "4.2.03.tiff";

    /**
     * Construct a new main window.
     */
    public Viewer() {
        setPreferredSize(new Dimension(600, 600));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(String.format("Dithering Final Project"));

        loadImage();

        imageComponent = new ImageComponent(getImageArray());
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

    private int[][] getImageArray() {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] array = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                array[row][col] = image.getRGB(row, col);
            }
        }

        return array;
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