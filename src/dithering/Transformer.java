package dithering;

import java.awt.image.BufferedImage;
import java.awt.Point;

public class Transformer {
    BufferedImage image;
    int width;
    int height;

    public Transformer(BufferedImage image) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
        System.out.println("height="+height);
        System.out.println("width="+width);
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

    public int[][] getContextCurveDither(boolean showPath) {
        int[][] array = new int[height][width];

        //only works for even dimension pictures
        if(height%2!=0 || width%2!=0) {
            return null;
        }

        //create array of rgb values from image
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                array[row][col] = image.getRGB(col, row);
            }
        }

        /////////// Context-based SFC algorithm ////////////////

        // 1. constructing cycle graph

        GridGraph grid = new GridGraph(width/2,height/2);

        for(int y=0;y<height/2;y++) { 
            for(int x=0;x<width/2;x++) {
                //find cost of down edge if there exists a down edge.
                if(y!=height/2-1) {
                    int a = array[2*y+1][2*x];
                    int b = array[2*y+1][2*x+1];
                    int c = array[2*y+2][2*x];
                    int d = array[2*y+2][2*x+1];

                    double same = compareColors(a, b) + compareColors(c, d);
                    double diff = compareColors(a, c) + compareColors(b, d);

                    grid.setDownEdge(x, y, diff-same);

                    //debugging
                }

                //find cost of right edge if there exists a down edge.
                if(x!=width/2-1) {
                    int a = array[2*y][2*x+1];
                    int b = array[2*y][2*x+2];
                    int c = array[2*y+1][2*x+1];
                    int d = array[2*y+1][2*x+2];

                    double same = compareColors(a, c) + compareColors(b, d);
                    double diff = compareColors(a, b) + compareColors(c, d);

                    grid.setRightEdge(x, y, diff-same);
                }

                //System.out.println("\tx:"+x+" y:"+y+" down:"+grid.getDownEdge(x,y)+" right:"+grid.getRightEdge(x,y));
            }
        }

        // 2. Use prim's agorithm to get a minimum spanning tree.

        System.out.println("started prims");
        GridGraph mst = grid.prims();
        System.out.println("completed prims");

        // 3. Create sfc from minimum spanning tree.

        System.out.println("started sfc");
        GridGraph sfc = mst.sfcFromTree();
        System.out.println("completed sfc");

        System.out.println("started traversal");
        Point[] path = sfc.traverseSfc();
        System.out.println("completed traversal");

        // 4. Linear dither!!!!

        if(showPath) {
            double change = 255.0/path.length;
            System.out.println("change is "+change);

            for(int i=0;i<path.length;i++) {
                Point p = path[i];
                //System.out.println("visiting ("+p.x+","+p.y+")");
                array[p.x][p.y] = ((int)(255-(change*i)) & 0xFF);
            }
            return array;
        }

        int rError = 0;
        int gError = 0;
        int bError = 0;
        for(int i=0;i<path.length;i++) {
            Point p = path[i];

            int rgb = image.getRGB(p.y, p.x);
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

            array[p.x][p.y] = (red << 16) | (green << 8) | blue;
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

    private int getRed(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    private int getGreen(int rgb) {
        return (rgb >> 8) & 0xFF;
    }

    private int getBlue(int rgb) {
        return rgb & 0xFF;
    }

    private double compareColors(int c1, int c2) {
        int red1 = (c1 >> 16) & 0xFF;
        int green1 = (c1 >> 8) & 0xFF;
        int blue1 = c1 & 0xFF;

        int red2 = (c2 >> 16) & 0xFF;
        int green2 = (c2 >> 8) & 0xFF;
        int blue2 = c2 & 0xFF;

        double difference = Math.sqrt(
            Math.pow(red1-red2,2)+
            Math.pow(green1-green2,2)+
            Math.pow(blue1-blue2,2));

        return difference;
    }
}
