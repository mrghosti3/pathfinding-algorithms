package org.mrghosti3.PathfindingAlgorithms;

import java.util.ArrayList;
import java.util.Stack;

public class Pathfinder {

    private final VisualPanel panel;
    private boolean diagonal, running;
    private Node start, end, parent;
    private ArrayList<Node> closedNodes, borders;
    private Stack<Node> openNodes, path;

    public enum Pathfinders {
        ASTAR ("AStar");

        private final String name;

        Pathfinders(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public Pathfinder(VisualPanel panel) {
        this.panel = panel;

        this.closedNodes = new ArrayList<>();
        this.borders = new ArrayList<>();
        this.openNodes = new Stack<>();
        this.path = new Stack<>();
    }

    // Getters, Setters
    public void reset() {
        closedNodes.clear();
        openNodes.clear();
        path.clear();
    }

    public Node getParent() {
        return parent;
    }

    public ArrayList<Node> getClosedNodes() {
        return closedNodes;
    }

    public Stack<Node> getOpenNodes() {
        return openNodes;
    }

    public ArrayList<Node> getBorders() {
        return borders;
    }

    public Stack<Node> getPath() {
        return path;
    }

    public boolean isComplete() {
        return path != null && path.size() > 0;
    }

    public boolean isRunning() {
        return running;
    }

    private boolean isStartEndSet() {
        return start != null && start.isSet() && end != null && end.isSet();
    }

    public void setStartEnd(Node start, Node end) {
        this.start = start;
        this.end = end;
    }

    public void addBorder(Node n) {
        if (n == null) return;
        if (!this.borders.contains(n))
            this.borders.add(n);
    }

    public void addClosed(Node n) {
        if (n == null) return;

        if (!this.closedNodes.contains(n))
            this.closedNodes.add(n);
    }

    public void addOpen(Node n) {
        if (n == null) return;

        if (!this.openNodes.contains(n))
            this.openNodes.add(n);
    }

    public void removeBorder(Node b) {
        this.borders.remove(b);
    }

    // Other methods
    public void start() {
        if (!isStartEndSet()) {
            return;
        }

        this.diagonal = panel.getDiagonal();
        this.running = true;

        start.setPathNodeData(start, end);
        addClosed(start);
        findPath(start);
    }

    public void start(Node start, Node end) {
        this.setStartEnd(start, end);
        this.start();
    }

    public void findPath(Node parent) {
        if (diagonal) {
            for (int y = -1; y < 2; ++y) {
                for (int x = -1; x < 2; ++x) {
                    Node openNode = new Node(parent.getX() + x, parent.getY() + y);

                    boolean invalid = openNode.getX() < 0;
                    invalid |= openNode.getY() < 0;
                    invalid |= (openNode.getX() > end.getX() + 100 && openNode.getY() > end.getY() + 100);
                    invalid |= closedNodes.contains(openNode);
                    invalid |= borders.contains(openNode);
                    invalid |= (x == 0 && y == 0);

                    if (invalid) {
                        continue;
                    }

                    openNode.setPathNodeData(parent, end);

                    if (openNodes.contains(openNode)) {
                        for (int i = 0; i < openNodes.size(); ++i) {
                            if (openNodes.get(i).equals(openNode) && openNode.getG() < openNodes.get(i).getG()) {
                                openNodes.set(i, openNode);
                            }
                        }
                    }
                    else addOpen(openNode);
                }
            }
        }
        else {
            // TODO: Implement non diagonal path finding
        }

        sortOpenList();
        parent = openNodes.pop();

        if (parent.getX() > end.getX() + 100 && parent.getY() > end.getY() + 100) {
            running = false;
            return;
        }

        addClosed(parent);

        if (end.equals(parent)) {
            running = false;
            end = parent;
            path.push(end);

            for (Node n = end.getParent(); n.getParent() != null; n = n.getParent()) {
                path.push(n);
            }

            return;
        }

        if (!panel.isShowSteps()) {
            findPath(parent);
        }
        else {
            this.parent = parent;
        }
    }

    private void sortOpenList() {
        for (int i = 0; i < openNodes.size() - 1; ++i) {
            for (int j = i + 1; j < openNodes.size(); ++j) {
                if (openNodes.get(i).getF() < openNodes.get(j).getF()) {
                    Node tmp = openNodes.get(i);
                    openNodes.set(i, openNodes.get(j));
                    openNodes.set(j, tmp);
                }
                else if (openNodes.get(i).getF() == openNodes.get(j).getF() &&
                        openNodes.get(i).getH() < openNodes.get(j).getH()) {
                    Node tmp = openNodes.get(i);
                    openNodes.set(i, openNodes.get(j));
                    openNodes.set(j, tmp);
                }
            }
        }
    }
}
