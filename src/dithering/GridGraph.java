package dithering;

import java.util.ArrayList;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import dithering.EdgeMinHeap.Edge;

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

        EdgeMinHeap heap = new EdgeMinHeap(height*width*100);
        ArrayList<Point> visited = new ArrayList<Point>();

        visited.add(new Point(0,0));

        Set<Point> nei = getNeighbors(0,0);
        for(Point n : nei) {
            heap.enqueue(new Point(0,0), n, getEdge(new Point(0,0), n));
        }

        //boolean yes = vertices.remove(new Point(0,0));
        //System.out.println("get vertex worked?: "+yes);

        //minheap prims: modified from Henry's hw3.
        while(!heap.isEmpty()) {            
            Edge edge = heap.dequeue();
            //System.out.print("("+w.x+","+w.y+") "+contains+" ");
            if(!visited.contains(edge.e) && edge!=null) {
                //System.out.println("added point: ("+edge.e.x+","+edge.e.y+")");
                mst.setEdge(edge.e, edge.s, 1);
                cost+=edge.w;
                visited.add(edge.e);
                Set<Point> neighbors = getNeighbors(edge.e.x, edge.e.y);
                for(Point neighbor : neighbors) {
                    if(!neighbor.equals(edge.s)) {
                        heap.enqueue(edge.e, neighbor, getEdge(edge.e,neighbor));
                    }
                }
            }
        }
        System.out.println("cost of prims: "+cost);
        return mst;
    }

    public GridGraph sfcFromTree() {
        GridGraph sfc = new GridGraph(height*2, width*2);
        for(int y=0;y<sfc.height-1;y+=2) {
            sfc.setDownEdge(y,0,1);
        }
        for(int x=0;x<sfc.width-1;x+=2) {
            sfc.setRightEdge(0,x,1);
        }

        for(int y=0;y<height;y++) {
            for(int x=0;x<width;x++) {
                boolean down = (getDownEdge(y,x)==1);
                boolean right = (getRightEdge(y,x)==1);

                boolean downBound = (y==height-1);
                boolean rightBound = (x==width-1);

                //System.out.print("("+x+","+y+") is open ");

                if(down && right) {
                    //System.out.println("down and right");
                    sfc.setRightEdge(2*x+1, 2*y, 1);
                    sfc.setRightEdge(2*x+1, 2*y+1, 1);

                    sfc.setDownEdge(2*x,2*y+1,1);
                    sfc.setDownEdge(2*x+1,2*y+1,1);
                } else if(right) {
                    //System.out.println("right");
                    sfc.setRightEdge(2*x+1, 2*y, 1);
                    sfc.setRightEdge(2*x+1, 2*y+1, 1);

                    sfc.setRightEdge(2*x,2*y+1,1);
                    if(!downBound) sfc.setRightEdge(2*x,2*y+2,1);
                } else if(down) {
                    //System.out.println("down");
                    sfc.setDownEdge(2*x+1, 2*y, 1);
                    if(!rightBound) sfc.setDownEdge(2*x+2, 2*y, 1);

                    sfc.setDownEdge(2*x,2*y+1,1);
                    sfc.setDownEdge(2*x+1,2*y+1,1);
                } else { //if neither right or down
                    //System.out.println("on neither");
                    sfc.setDownEdge(2*x+1, 2*y, 1);
                    if(!rightBound) sfc.setDownEdge(2*x+2, 2*y, 1);

                    sfc.setRightEdge(2*x,2*y+1,1);
                    if(!downBound) sfc.setRightEdge(2*x,2*y+2,1);
                }
            }
        }
        return sfc;
    }

    public Point[] traverseSfc() {
        int length = height*width;
        Point[] path = new Point[length];
        
        Point currentPoint = new Point(0,0);
        path[0] = currentPoint;
        //System.out.println("visited (0,0)");

        for(int i=1;i<length;i++) {
            Set<Point> neighbors = getNeighbors(currentPoint.x, currentPoint.y);
            for(Point neighbor : neighbors) {
                if(getEdge(currentPoint, neighbor)==1) {
                    setEdge(neighbor, currentPoint, 0);
                    currentPoint = neighbor;
                    path[i] = currentPoint;
                    //System.out.println("visited ("+currentPoint.x+","+currentPoint.y+")");
                    break;
                }
            }
        }
        return path;
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
            edge = getRightEdge(p1.y,p1.x-1);
        } else if(vector.x==1) {
            edge = getRightEdge(p1.y,p1.x);
        } else if(vector.y==-1) {
            edge = getDownEdge(p1.y-1,p1.x);
        } else {
            edge = getDownEdge(p1.y,p1.x);
        }
        return edge;
    }

    public void setEdge(Point p1, Point p2, double w) {
        Point vector = new Point(p2.x,p2.y);
        vector.translate(-p1.x,-p1.y);
        if(vector.x==-1) {
            setRightEdge(p1.y,p1.x-1,w);
        } else if(vector.x==1) {
            setRightEdge(p1.y,p1.x,w);
        } else if(vector.y==-1) {
            setDownEdge(p1.y-1,p1.x,w);
        } else {
            setDownEdge(p1.y,p1.x,w);
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
