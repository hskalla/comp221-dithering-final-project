package dithering;

import java.util.ArrayList;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class GridGraph {
    private Node[][] grid;
    private int height;
    private int width;

    public GridGraph(int h, int w) {
        height = h;
        width = w;
        grid = new Node[h][w];
        for(int y=0;y<h;y++) {
            for(int x=0;x<w;x++) {
                grid[y][x] = new Node();
            }
        }
    }

    public GridGraph prims() {
        GridGraph mst = new GridGraph(height, width);
        double cost = 0;

        ArrayList<Point> visited = new ArrayList<Point>();
        Set<Point> vertices = new HashSet<Point>();

        for(int y=0;y<height;y++) {
            for(int x=0;x<width;x++) {
                vertices.add(new Point(x,y));
            }
        }

        visited.add(new Point(0,0));
        vertices.remove(new Point(0,0));

        //boolean yes = vertices.remove(new Point(0,0));
        //System.out.println("get vertex worked?: "+yes);

        //actual prims: modified from Henry's hw3.
        while(!vertices.isEmpty()) {
            Point outside = null;
            Point inside = null;
            Double minWeight = null;
            for(Point v : vertices) {

                Set<Point> neighbors = getNeighbors(v.x, v.y);

                for(Point w : neighbors) {
                    Double edge = getEdge(v, w);
                    boolean contains = false;
                    for(Point p : visited) {
                        if(p.equals(w)) {
                            contains = true;
                            break;
                        }
                    }
                    //System.out.print("("+w.x+","+w.y+") "+contains+" ");
                    if(contains && edge!=null) {
                        if(minWeight==null || edge<minWeight) {
                            outside = v;
                            inside = w;
                            minWeight = edge;
                        }
                    }
                }
            }
            if(minWeight==null) {
                return null;
            }
            System.out.println("added point: ("+outside.x+","+outside.y+")");
            mst.setEdge(outside, inside, 1);
            cost+=minWeight;
            visited.add(outside);
            vertices.remove(outside);
        }
        System.out.println("cost of prims: "+cost);
        return mst;
    }

    public void setRightEdge(int x, int y, double w) {
        grid[x][y].rightEdge = w;
    }

    public void setDownEdge(int x, int y, double w) {
        grid[x][y].downEdge = w;
    }

    public double getRightEdge(int x, int y) {
        return grid[x][y].rightEdge;
    }

    public double getDownEdge(int x, int y) {
        return grid[x][y].downEdge;
    }

    public int height() {
        return height;
    }

    public int width() {
        return width;
    }

    public double getEdge(Point p1, Point p2) {
        Point vector = new Point(p2.x,p2.y);
        vector.translate(-p1.x,-p1.y);
        Double edge;
        if(vector.x==-1) {
            edge = getRightEdge(p1.x-1,p1.y);
        } else if(vector.x==1) {
            edge = getRightEdge(p1.x,p1.y);
        } else if(vector.y==-1) {
            edge = getDownEdge(p1.x,p1.y-1);
        } else {
            edge = getDownEdge(p1.x,p1.y);
        }
        return edge;
    }

    public void setEdge(Point p1, Point p2, double w) {
        Point vector = new Point(p2.x,p2.y);
        vector.translate(-p1.x,-p1.y);
        if(vector.x==-1) {
            setRightEdge(p1.x-1,p1.y,w);
        } else if(vector.x==1) {
            setRightEdge(p1.x,p1.y,w);
        } else if(vector.y==-1) {
            setDownEdge(p1.x,p1.y-1,w);
        } else {
            setDownEdge(p1.x,p1.y,w);
        }
    }

    public Set<Point> getNeighbors(int x, int y) {
        Set<Point> neighbors = new HashSet<Point>();
        if(x<width-1) neighbors.add(new Point(x+1,y));
        if(x>0) neighbors.add(new Point(x-1,y));
        if(y<height-1) neighbors.add(new Point(x,y+1));
        if(y>0) neighbors.add(new Point(x,y-1));
        return neighbors;
    }

    private class Node {
        double rightEdge;
        double downEdge;

        public Node() {
            rightEdge = 0;
            downEdge = 0;
        }
    }
}
