package org.mrghosti3.PathfindingAlgorithms;

public class Node {

    public static final int MOVE_COST = 10;
    public static final int DIAGONAL_MOVE_COST = 14;

    private boolean isSet; // is node pos set
    private int x, y; // Location data
    private Node parent;
    private int g, h, f; // Additional node data

    private Node(int x, int y, boolean isSet) {
        this.x = x;
        this.y = y;
        this.g = 0;
        this.h = 0;
        this.isSet = isSet;
        setParent(null);
    }

    public Node() {
        this(0, 0, false);
    }

    public Node(int x, int y) {
        this(x, y, true);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }

    @Override
    public String toString() {
        return String.format("Node ( %d ; %d ) (%d %d %d) P( %d ; %d)",
                this.x, this.y, this.g, this.h, this.f, parent.x, parent.y
        );
    }

    // Getters, Setters
    public boolean isSet() {
        return isSet;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Node getParent() {
        return parent;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public int getF() {
        return f;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
        this.isSet = true;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    // Other methods
    public void setPathNodeData(Node parent, Node destination) {
        int xDiff, yDiff;

        if (this.equals(parent)) {
            this.g = 0;
        }
        else {
            setParent(parent);
            xDiff = Math.abs(this.x - parent.x);
            yDiff = Math.abs(this.y - parent.y);
            this.g = parent.g + ((xDiff != 0 && yDiff != 0)?DIAGONAL_MOVE_COST:MOVE_COST);
        }

        xDiff = Math.abs(this.x - destination.x);
        yDiff = Math.abs(this.y - destination.y);
        this.h = ( (xDiff > yDiff) ?
                DIAGONAL_MOVE_COST * yDiff + MOVE_COST * (xDiff - yDiff) :
                DIAGONAL_MOVE_COST * xDiff + MOVE_COST * (yDiff - xDiff)
        );

        this.f = this.g + this.h;
    }
}
