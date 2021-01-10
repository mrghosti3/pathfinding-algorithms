package org.mrghosti3.PathfindingAlgorithms;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;

import org.mrghosti3.PathfindingAlgorithms.Pathfinder.Pathfinders;

public class VisualPanel extends JPanel
        implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {

    private Pathfinders pType;
    private Pathfinder pathfinder;
    private Node start, end;
    private int size;
    //private int xOffset, yOffset;
    private boolean hover, setStart, setEnd;

    ControlPanel cp;
    Timer timer;

    public VisualPanel() {
        cp = new ControlPanel(this);
        pType = Pathfinders.ASTAR;

        switch (pType) {
            case ASTAR:
                pathfinder = new Pathfinder(this);
                break;
        }

        start = new Node();
        end = new Node();

        size = 25;

        hover = false;
        setStart = false;
        setEnd = false;

        setLayout(null);
        timer = new Timer(60, this);

        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        cp.add();
        this.revalidate();
        this.repaint();
    }

    // Implementations
    @Override
    public void actionPerformed(ActionEvent e) {
        // Continues pathfinding
        if (pathfinder.isRunning() && isShowSteps()) {
            pathfinder.findPath(pathfinder.getParent());
        }
        else if (!pathfinder.isRunning() && isShowSteps()) {
            timer.stop();
            cp.getButton("run-btn").setText("Clear");
        }

        // Starts path finding
        if (e.getActionCommand() != null && isStartEndSet()) {
            if (e.getActionCommand().equals("Start") && !pathfinder.isRunning()) {
                cp.getButton("run-btn").setText("Stop");
                start();
            }
            else if (e.getActionCommand().equals("Clear")) {
                pathfinder.reset();
                cp.getButton("run-btn").setText("Start");
            }
            else if (e.getActionCommand().equals("Stop")) {
                cp.getButton("run-btn").setText("Run");
                if (isShowSteps())
                    timer.stop();
            }
            else if (e.getActionCommand().equals("Run")) {
                cp.getButton("run-btn").setText("Stop");
                if (isShowSteps())
                    timer.start();
            }
        }

        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        updateMap(e);
        nodeInfoPanel(e);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        if (setStart) {
            pathfinder.removeBorder(start);
        }

        if (setEnd) {
            pathfinder.removeBorder(end);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        updateMap(e);
        nodeInfoPanel(e);
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        nodeInfoPanel(e);

        int x = e.getX(), y = e.getY();
        hover = ( x >= 10 && x <= 332 && y >= (this.getHeight() - 96) && y <= (this.getHeight() - 6) );

        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rotation = e.getWheelRotation();
        int scroll = 3;

        //
        if (rotation == -1 && size + scroll < 200) {
            size += scroll;
        }
        else if (rotation == 1 && size - scroll > 10) {
            size -= scroll;
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                setStart = true;
                break;
            case KeyEvent.VK_S:
                setEnd = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        setStart = false;
        setEnd = false;
    }

    // Getters, setters
    public boolean getDiagonal() {
        return cp.getCheck("check-diagonal");
    }

    public boolean isShowSteps() {
        return cp.getCheck("check-steps");
    }

    private boolean isStartEndSet() {
        return start != null && start.isSet() && end != null && end.isSet();
    }

    // Other methods
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = this.getWidth();
        int height = this.getHeight();

        // Draws WALLS
        for (Node n : pathfinder.getBorders()) {
            g.setColor(Color.black);
            g.fillRect(n.getX() * size, n.getY() * size, size, size);
        }

        // Draws OPEN nodes
        for (Node n : pathfinder.getOpenNodes()) {
            g.setColor(Style.openColor);
            g.fillRect(n.getX() * size, n.getY() * size, size, size);
        }

        // Draws CLOSED nodes
        for (Node n : pathfinder.getClosedNodes()) {
            g.setColor(Style.closedColor);
            g.fillRect(n.getX() * size, n.getY() * size, size, size);
        }

        // Draws PATH nodes
        for (Node n : pathfinder.getPath()) {
            g.setColor(Style.pathColor);
            g.fillRect(n.getX() * size, n.getY() * size, size, size);
        }

        // Draws starting node
        if (start != null && start.isSet()) {
            g.setColor(Color.green);
            g.fillRect(start.getX() * size, start.getY() * size, size, size);
        }

        // Draws end node
        if (end != null && end.isSet()) {
            g.setColor(Color.red);
            g.fillRect(end.getX() * size, end.getY() * size, size, size);
        }

        // Draws grid
        g.setColor(Color.lightGray);
        for (int y = 0; y < height; y += size) {
            for (int x = 0; x < width; x += size) {
                g.drawRect(x, y, size, size);
            }
        }

        if (hover) {
            g.setColor(Style.darkText);
            cp.hoverStyle();
        }
        else {
            g.setColor(Style.btnPanel);
            cp.nonHoverStyle();
        }

        cp.getLabel("label-open").setText(
                String.format(ControlPanel.OPEN_LABEL_FORMAT, pathfinder.getOpenNodes().size())
        );

        cp.getLabel("label-closed").setText(
                String.format(ControlPanel.CLOSED_LABEL_FORMAT, pathfinder.getClosedNodes().size())
        );

        cp.getLabel("label-path").setText(
                String.format(ControlPanel.PATH_LABEL_FORMAT, pathfinder.getPath().size())
        );

        g.fillRect(10, height-96, 330, 90);
        cp.position();
    }

    private void start() {
        if (isShowSteps()) {
            pathfinder.start(start, end);
            timer.start();
        }
        else {
            pathfinder.start(start, end);
            cp.getButton("run-btn").setText("Clear");
        }
    }

    /** Converts single Pos to Node Pos
     * @param pos single int from position (X, Y or Z)
     * @return Converted position point for Node
     */
    private int calcNodePos(int pos) {
        return (int) Math.floor( pos / (double)size );
    }

    /** Updates hovered node info on control panel
     * @param e mouse event data
     */
    private void nodeInfoPanel(MouseEvent e) {
        Node n = new Node(
                calcNodePos(e.getX()),
                calcNodePos(e.getY())
        );

        String nType = "EMPTY";

        if (n.equals(start)) {
            nType = "START";
        }
        else if (n.equals(end)) {
            nType = "END";
        }
        else if (pathfinder.getClosedNodes().contains(n)) {
            for (Node c : pathfinder.getClosedNodes()) {
                if (c.equals(n)) {
                    n = c;
                }
            }
            nType = "CLOSED";
        }
        else if (pathfinder.getOpenNodes().contains(n)) {
            for (Node c : pathfinder.getOpenNodes()) {
                if (c.equals(n)) {
                    n = c;
                }
            }
            nType = "OPEN";
        }
        else if (pathfinder.getBorders().contains(n)) {
            nType = "BORDER";
        }

        cp.getLabel("label-node").setText(
                String.format(ControlPanel.NODE_LABEL_FORMAT,
                        n.getX(), n.getY(), n.getG(), n.getH(), n.getF(), nType)
        );
    }

    /** Updates borders, start, end
     * @param e mouse event data
     */
    private void updateMap(MouseEvent e) {
        int x = calcNodePos(e.getX());
        int y = calcNodePos(e.getY());

        if (SwingUtilities.isLeftMouseButton(e)) {
            Node b = new Node(x, y);

            if (setStart) {
                start.setXY(x, y);
            }
            else if (setEnd) {
                end.setXY(x, y);
            }
            else if (!pathfinder.getBorders().contains(b) && !b.equals(start) && !b.equals(end) &&
                        !pathfinder.getClosedNodes().contains(b)) {
                pathfinder.addBorder(b);
            }
        }
        else if (SwingUtilities.isRightMouseButton(e)) {
            pathfinder.removeBorder(new Node(x, y));
        }
    }
}
