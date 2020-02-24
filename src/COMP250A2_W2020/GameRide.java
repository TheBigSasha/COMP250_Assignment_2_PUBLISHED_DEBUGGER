
package COMP250A2_W2020;

import javax.swing.*;
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

    public GameRide(int nLines) {
        super(nLines);
        tf = new JTextField(15);
        tf.addKeyListener(this);
        keys = new boolean[256];
        frame = new JFrame("Game Controls");
        frame.addKeyListener(this);
        frame.setSize(1, 1);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void addLines(TrainLine[] input) {
        super.addLines(input);
    }

    public int gameTravel(String startStation, String startLine, String endStation, String endLine) {
        TrainLine destinationLine = getLineByName(endLine);
        TrainStation destinationStation = destinationLine.findStation(endStation);
        TrainLine departureLine = getLineByName(startLine);
        TrainStation departureStation = departureLine.findStation(startStation);

        TrainLine curLine = null; //use this variable to store the current line.
        TrainStation curStation = null; //use this variable to store the current station.

        curLine = departureLine;
        curStation = departureStation;

        TrainStation previousStation = null;
        TrainLine previousLine = null;

        int hoursCount = 0;
        System.out.println("Departing from " + curStation.getName());

        //YOUR CODE GOES HERE
        TrainVisualizer t = new TrainVisualizer(this);
        t.paint(null);
        while (true /*you can change this*/) {
            t.passCurrent(curStation);
            t.repaint();


            if (curStation.equals(destinationStation)) {
                System.out.println("Arrived at destination after " + hoursCount + " hours!");
                return hoursCount;
            }

            if (hoursCount != 0 && hoursCount % danceFreq == 0) {
                this.dance();
            }

            if (hoursCount == 168) {
                System.out.println("Jumped off after spending a full week on the train. Might as well walk.");
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
                        previousStation = curStation;
                        previousLine = curStation.getLine();
                        if (curStation.hasConnection) {
                            curStation = curStation.getTransferStation();
                        }
                        curLine = curStation.getLine();
                        hasKeyPress = false;
                        break;
                    case "left":
                        hoursCount++;
                        previousStation = curStation;
                        previousLine = curStation.getLine();
                        try {
                            curStation = curStation.getLeft();
                            curLine = curStation.getLine();
                        } catch (NullPointerException e) {
                        }

                        hasKeyPress = false;
                        break;
                    case "right":
                        hoursCount++;
                        previousStation = curStation;
                        previousLine = curStation.getLine();
                        try {
                            curStation = curStation.getRight();
                            curLine = curStation.getLine();
                        } catch (NullPointerException e) {
                        }

                        hasKeyPress = false;
                        break;
                    default:
                        if (curStation.hasConnection && (previousStation == null || !previousStation.equals(curStation.getTransferStation()))) {
                            hoursCount++;
                            previousStation = curStation;
                            previousLine = curStation.getLine();
                            curLine = curStation.getTransferLine();
                            curStation = curStation.getTransferStation();
                        } else {
                            hoursCount++;
                            previousStation = curStation;
                            previousLine = curStation.getLine();
                            curStation = curLine.getNext(curStation);
                            curLine = curStation.getLine();
                        }
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

