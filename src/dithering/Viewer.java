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

    private String imageFileName;
    private String alg;

    /**
     * Construct a new main window.
     */
    public Viewer(String imageFileName, String alg) {
        this.imageFileName = imageFileName;
        this.alg = alg.toLowerCase();

        loadImage();

        setPreferredSize(new Dimension(image.getWidth()+13, image.getHeight()+38));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(String.format("Dithering Final Project"));

        transformer = new Transformer(image);

        if(alg.equals("none")) {
            imageComponent = new ImageComponent(transformer.getArray());
        } else if(alg.equals("threshold")) {
            imageComponent = new ImageComponent(transformer.getThreshhold());
        } else if(alg.equals("linear")) {
            imageComponent = new ImageComponent(transformer.getLinearDither());
        } else if(alg.equals("floyd-steinburg")) {
            imageComponent = new ImageComponent(transformer.getFloydSteinburgDither());
        } else if(alg.equals("cb-sfc")) {
            imageComponent = new ImageComponent(transformer.getContextCurveDither(false));
        } else if(alg.equals("cb-sfc-path")) {
            System.out.println("Note that window must be manually enlarged to view full path.");
            imageComponent = new ImageComponent(transformer.getContextCurveDither(true));
        } else {
            System.out.println("Invalid algorithm. Aborting.");
            System.exit(0);
        }
        add(imageComponent, BorderLayout.CENTER);
        pack();
    }

    public Viewer() {
        this("4.2.07.tiff","none");
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
}