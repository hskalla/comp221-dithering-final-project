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
    public int[][] getArray() {
        int[][] array = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                array[row][col] = image.getRGB(col, row);
            }
        }

        return array;
    }

    //help manipulating rgb values from: https://www.baeldung.com/java-rgb-color-representation#:~:text=In%20programming%20languages%2C%20including%20Java,into%20a%2032%2Dbit%20integer.
    public int[][] getThreshhold() {
        int[][] array = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int rgb = image.getRGB(col, row);
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

    public int[][] getLinearDither() {
        int[][] array = new int[height][width];

        //error variables
        int rError = 0;
        int gError = 0;
        int bError = 0;
        for (int row = 1; row < height; row++) {
            for (int col = 1; col < width; col++) {
                int rgb = image.getRGB(col, row);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                //rgb values are between 0-255.

                red+=rError;
                green+=gError;
                blue+=bError;

                if(red<113) {
                    rError = red;
                    red=0;
                } else {
                    rError = red - 255;
                    red=225;
                }

                if(green<113) {
                    gError = green;
                    green=0;
                } else {
                    gError = green - 255;
                    green=225;
                }

                if(blue<113) {
                    bError = blue;
                    blue = 0;
                } else {
                    bError = blue - 255;
                    blue = 255;
                }

                array[row][col] = (red << 16) | (green << 8) | blue;
            }
        }
        return array;
    }

    public int[][] getFloydSteinburgDither() {
        double[][][] error = new double[height][width][3];
        int[][] array = new int[height][width];

        for (int row = 1; row < height; row++) {
            for (int col = 1; col < width; col++) {
                int rgb = image.getRGB(col, row);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                //rgb values are between 0-255.

                red = (int)(red + error[row][col][0]);
                green = (int)(green + error[row][col][1]);
                blue = (int)(blue + error[row][col][2]);

                int err = 0;
                if(red<113) {
                    err = red;
                    if(row!=height-1) {
                        error[row+1][col][0] += (err*0.3125); // 5/16
                        if(col!=0) {
                            error[row+1][col-1][0] += (err*0.1875); // 3/16
                        }
                        if(col!=width-1) {
                            error[row+1][col+1][0] += (err*0.0625); // 1/16
                        }
                    }
                    if(col!=width-1) {
                        error[row][col+1][0] += (err*0.4375); // 7/16
                    }
                    red=0;
                } else {
                    err = red-255;
                    if(row!=height-1) {
                        error[row+1][col][0] += (err*0.3125); // 5/16
                        if(col!=0) {
                            error[row+1][col-1][0] += (err*0.1875); // 3/16
                        }
                        if(col!=width-1) {
                            error[row+1][col+1][0] += (err*0.0625); // 1/16
                        }
                    }
                    if(col!=width-1) {
                        error[row][col+1][0] += (err*0.4375); // 7/16
                    }
                    red=225;
                }

                if(green<113) {
                    err = green;
                    if(row!=height-1) {
                        error[row+1][col][1] += (err*0.3125); // 5/16
                        if(col!=0) {
                            error[row+1][col-1][1] += (err*0.1875); // 3/16
                        }
                        if(col!=width-1) {
                            error[row+1][col+1][1] += (err*0.0625); // 1/16
                        }
                    }
                    if(col!=width-1) {
                        error[row][col+1][1] += (err*0.4375); // 7/16
                    }
                    green=0;
                } else {
                    err = green-255;
                    if(row!=height-1) {
                        error[row+1][col][1] += (err*0.3125); // 5/16
                        if(col!=0) {
                            error[row+1][col-1][1] += (err*0.1875); // 3/16
                        }
                        if(col!=width-1) {
                            error[row+1][col+1][1] += (err*0.0625); // 1/16
                        }
                    }
                    if(col!=width-1) {
                        error[row][col+1][1] += (err*0.4375); // 7/16
                    }
                    green=225;
                }

                if(blue<113) {
                    err = blue;
                    if(row!=height-1) {
                        error[row+1][col][2] += (err*0.3125); // 5/16
                        if(col!=0) {
                            error[row+1][col-1][2] += (err*0.1875); // 3/16
                        }
                        if(col!=width-1) {
                            error[row+1][col+1][2] += (err*0.0625); // 1/16
                        }
                    }
                    if(col!=width-1) {
                        error[row][col+1][2] += (err*0.4375); // 7/16
                    }
                    blue = 0;
                } else {
                    err = blue - 255;
                    if(row!=height-1) {
                        error[row+1][col][2] += (err*0.3125); // 5/16
                        if(col!=0) {
                            error[row+1][col-1][2] += (err*0.1875); // 3/16
                        }
                        if(col!=width-1) {
                            error[row+1][col+1][2] += (err*0.0625); // 1/16
                        }
                    }
                    if(col!=width-1) {
                        error[row][col+1][2] += (err*0.4375); // 7/16
                    }
                    blue = 255;
                }

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
