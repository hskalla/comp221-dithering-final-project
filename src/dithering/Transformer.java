package dithering;

import java.awt.image.BufferedImage;

public class Transformer {
    BufferedImage image;
    int width;
    int height;

    public Transformer(BufferedImage image) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
    }

    //credit to: https://www.baeldung.com/java-getting-pixel-array-from-image
    private int[][] getArray(BufferedImage image) {
        int[][] array = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                array[row][col] = image.getRGB(row, col);
            }
        }

        return array;
    }

    //help manipulating rgb values from: https://www.baeldung.com/java-rgb-color-representation#:~:text=In%20programming%20languages%2C%20including%20Java,into%20a%2032%2Dbit%20integer.
    public int[][] getCompressed() {
        int[][] array = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int rgb = image.getRGB(row, col);
                int red = (rgb >> 16) & 0xFF; 
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                //rgb values are between 0-255.

                red = roundColor(red);
                green = roundColor(green);
                blue = roundColor(blue);

                array[row][col] = (red << 16) | (green << 8) | blue;
            }
        }

        return array;
    }

    private int roundColor(int color) {
        if(color<113) {
            return 0;
        } else {
            return 225;
        }
    }
}
