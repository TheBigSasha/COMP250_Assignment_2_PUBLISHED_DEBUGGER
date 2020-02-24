
package COMP250A2_W2020;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public
class GameRide extends TrainNetwork implements KeyListener, ActionListener {
    JFrame frame;
    private boolean[] keys;
    private JTextField tf;
    private boolean hasKeyPress;
    private String nextMove;
    private int danceFreq = 3;
    JLabel label;

    public GameRide(int nLines) {
        super(nLines);
        tf = new JTextField(15);
        tf.addKeyListener(this);
        keys = new boolean[256];
        frame = new JFrame("Game! [WASD] to begin!");
        frame.addKeyListener(this);
        frame.setSize(1, 1);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        label = new JLabel();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void addLines(TrainLine[] input) {
        super.addLines(input);
    }

    public int gameTravel(String startStation, String startLine, String endStation, String endLine) {
        System.out.println("your destination is " + endStation + " on line " + endLine);

        Font f4 = new Font(Font.SERIF, Font.BOLD, 25);
        label.setFont(f4);
        label.setText("your destination is " + endStation + " on line " + endLine);
        frame.add(label);
        frame.setSize(700, 60);
        frame.setLocationRelativeTo(null);

        TrainLine destinationLine = getLineByName(endLine);
        TrainStation destinationStation = destinationLine.findStation(endStation);
        TrainLine departureLine = getLineByName(startLine);
        TrainStation departureStation = departureLine.findStation(startStation);

        TrainStation curStation; //use this variable to store the current station.

        curStation = departureStation;

        TrainStation previousStation = null;
        TrainLine previousLine = null;

        int hoursCount = 0;
        System.out.println("Departing from " + curStation.getName());

        //YOUR CODE GOES HERE
        TrainVisualizer t = new TrainVisualizer(this);
        frame.setLocationRelativeTo(t.frame);
        frame.setLocation(t.frame.getLocation().x,0);
        t.paint(null);
        while (true /*you can change this*/) {
            t.passCurrent(curStation);
            t.repaint();

            if (curStation != null && curStation.equals(destinationStation)) {
                System.out.println("Arrived at destination after " + hoursCount + " hours!");
                label.setText("Arrived at destination after " + hoursCount + " hours!");
                frame.setSize(500,100);
                t.delay(2000);
                t.dispose();
                return hoursCount;
            }

            if (hoursCount != 0 && hoursCount % danceFreq == 0) {
                this.dance();
            }

            if (hoursCount == 168) {
                System.out.println("Jumped off after spending a full week on the train. Might as well walk.");
                label.setText("Jumped off after spending a full week on the train. Might as well walk.");
                frame.setSize(500,100);
                t.delay(2000);
                t.dispose();
                return hoursCount;

            }

            while (!hasKeyPress) {
                update();
                t.delay(10);
            }
            {
                t.delay(200);
                switch (nextMove) {
                    case "up":
                    case "down":
                        hoursCount++;
                        try{
                        if (curStation.hasConnection) {
                            curStation = curStation.getTransferStation();
                        }}catch (NullPointerException e) {
                            System.out.println("you died at hour " + hoursCount);
                            label.setText("you died at hour " + hoursCount);
                            t.delay(500);
                            t.dispose();
                            frame.dispose();
                            return hoursCount;
                        }

                        hasKeyPress = false;
                        break;
                    case "left":
                        hoursCount++;
                        try {
                        previousStation = curStation;
                        previousLine = curStation.getLine();
                            curStation = curStation.getLeft();
                        } catch (NullPointerException e) {
                            System.out.println("you died at hour " + hoursCount);
                            label.setText("you died at hour " + hoursCount);
                            t.delay(500);
                            frame.dispose();
                            t.dispose();
                            return hoursCount;
                        }

                        hasKeyPress = false;
                        break;
                    case "right":
                        hoursCount++;
                        try {
                            curStation = curStation.getRight();
                        } catch (NullPointerException e) {
                            System.out.println("you died at hour " + hoursCount);
                            label.setText("you died at hour " + hoursCount);
                            t.delay(500);
                            frame.dispose();
                            t.dispose();
                            return hoursCount;
                        }

                        hasKeyPress = false;
                        break;
                    default:
                        hasKeyPress = false;
                        break;
                }
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        update();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void update() {
        if (keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]) {
            nextMove = "up";
            hasKeyPress = true;
        }

        if (keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]) {
            nextMove = "down";
            hasKeyPress = true;
        }

        if (keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]) {
            nextMove = "left";
            hasKeyPress = true;
        }

        if (keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]) {
            nextMove = "right";
            hasKeyPress = true;
        }
    }
}

