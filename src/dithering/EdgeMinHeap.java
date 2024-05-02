package dithering;

import java.awt.Point;

public class EdgeMinHeap {
    private Edge[] heap;
    private int size;

    public EdgeMinHeap(int n) {
        size = 0;
        heap = new Edge[n];
    }

    public EdgeMinHeap() {
        this(1024);
    }

    public void enqueue(Point s, Point e, double w) {
        heap[size] = new Edge(s,e,w);
        size++;

        heapifyParents(size-1);
    }

    public Edge dequeue() {
        Edge root = heap[0];
        swap(0,size-1);
        size--;

        heapifyChildren(0);

        return root;
    }

    public boolean isEmpty() {
        return (size==0);
    }

    public int size() {
        return size;
    }

    private void heapifyParents(int child) {
        //base case
        if(child==0) return;

        //not base case
        int parent = (child-1) / 2;
        if(heap[parent].compareTo(heap[child])>0) {
            swap(parent,child);
            heapifyParents(parent);
        }
    }

    private void heapifyChildren(int parent) {
        int left = parent*2+1;
        int right = parent*2+2;

        //base case: left and right are out of bounds
        if(left>size-1) return;

        //get lower child
        int child;
        if(right>size-1 || heap[left].compareTo(heap[right])<0) {
            child = left;
        } else {
            child = right;
        }

        if(heap[child].compareTo(heap[parent])<0) {
            swap(child,parent);
            heapifyChildren(child);
        }
    }

    private void swap(int i, int j) {
        Edge temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    

    public class Edge implements Comparable<Edge> {
        Point s;
        Point e;
        double w;

        public Edge(Point s, Point e, double w) {
            this.s = s;
            this.e = e;
            this.w = w;
        }

        public int compareTo(Edge e) {
            Double ow = w;
            return ow.compareTo(e.w);
        }

        
    }
}
