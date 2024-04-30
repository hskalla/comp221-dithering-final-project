package dithering;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

/**
 * TODO: add javadoc
 */
public class Viewer extends JFrame {

    private ImageComponent imageComponent;

    /**
     * Construct a new main window.
     */
    public Viewer() {
        setPreferredSize(new Dimension(600, 600));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(String.format("Dithering Final Project"));

        imageComponent = new ImageComponent(randomImg());
        add(imageComponent, BorderLayout.CENTER);

        pack();
    }

    private char[][] randomImg() {
        char[][] img = new char[256][256];
        for(int x=0;x<img.length;x++) {
            for(int y=0;y<img[0].length;y++) {
                img[x][y] = (char)(Math.random() * Character.MAX_VALUE);
            }
        }
        return img;
    }

    public static void main(String[] args) {
        Viewer frame = new Viewer();
        frame.setVisible(true);
    }
}