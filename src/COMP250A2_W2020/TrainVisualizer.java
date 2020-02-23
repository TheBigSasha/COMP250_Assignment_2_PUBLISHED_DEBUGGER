package COMP250A2_W2020;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static COMP250A2_W2020.TrainRide.generateTrainNetwork;

/**
 * Visualizer for COMP250 A2 W2020
 *
 * @author Sasha Aleshchenko
 */
public class TrainVisualizer extends JFrame {
    public TrainNetwork tNet;
    private Random rand = new Random();
    public JFrame frame;
    public TrainStation curStation;

    public TrainVisualizer(TrainNetwork tNet){
        frame = new JFrame("Network Visualizer V1 - Visit sashaphoto.ca");
        setTitle("Network Visualizer V1");
        setSize(800, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.tNet = tNet;
    }

    public static void main(String[] args) {
        TrainVisualizer t = new TrainVisualizer(generateTrainNetwork());
        t.paint(null); // Not a proper way, but it still works.
    }

    public void paint(Graphics g) {
        try {
            g.clearRect(0, 0, getWidth(), getHeight());
        } catch (NullPointerException e) {
        }
        TrainLine[] lines = tNet.networkLines;
        TrainStation[][] linesStations = new TrainStation[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            linesStations[i] = lines[i].getLineArray();
        }
        for (int i = 0; i < linesStations.length; i++) {
            for (int j = 0; j < linesStations[i].length; j++) {
                try {
                    g.drawString(linesStations[i][j].getName(),
                            (getWidth() * (j) + 1) / (linesStations[i].length + 1) + 15,
                            getHeight() * (i + 1) / (linesStations.length + 1));

                    g.drawRect((getWidth() * (j) + 1) / (linesStations[i].length + 1) + 15,
                            getHeight() * (i + 1) / (linesStations.length + 1),
                            15, 15);


                    if (linesStations[i][j].hasConnection) {
                        g.setColor(Color.gray);
                        g.fillRect((getWidth() * (j) + 1) / (linesStations[i].length + 1) + 15,
                                getHeight() * (i + 1) / (linesStations.length + 1),
                                15, 15);
                        g.setColor(Color.black);
                        TrainLine transfersTo = linesStations[i][j].getTransferLine();
                        TrainStation transfersStation = linesStations[i][j].getTransferStation();
                        int coordYtrans = 0;
                        for (int k = 0; k < lines.length; k++) {
                            if (lines[k].getName().equals(transfersTo.getName())) {
                                coordYtrans = k;
//                                System.out.println("Y coord of transfer station of " + linesStations[i][j].getName() + " is " + coordYtrans);
                            }
                        }
                        int coordXtrans = 0;
                        TrainStation temp = lines[coordYtrans].getLeftTerminus();
                        for (int k = 0; k < lines[coordYtrans].getSize(); k++) {
                            if (temp.getName().equals(transfersStation.getName())) {
                                coordXtrans = k;
//                                System.out.println("X coord of transfer station of " + linesStations[i][j].getName() + " is " + coordXtrans);
                            }
                            temp = temp.getRight();
                        }


                        g.drawLine(
                                //Location of original station
                                (getWidth() * (j) + 1) / (linesStations[i].length + 1) + 15,
                                getHeight() * (i + 1) / (linesStations.length + 1) + 8,
                                //Location of transfer station
                                (getWidth() * (coordXtrans) + 1) / (linesStations[coordYtrans].length + 1) + 15,
                                getHeight() * (coordYtrans + 1) / (linesStations.length + 1) + 8);
                    }
                    if (linesStations[i][j].equals(curStation) || linesStations[i][j].getName().equals(curStation.getName())) {
                        g.setColor(Color.cyan);
                        g.fillRect((getWidth() * (j) + 1) / (linesStations[i].length + 1) + 15,
                                getHeight() * (i + 1) / (linesStations.length + 1),
                                15, 15);
                        g.setColor(Color.black);
                    }
                    if (j != linesStations[i].length - 1) {
                        g.drawLine((getWidth() * (j) + 1) / (linesStations[i].length + 1) + 15,
                                getHeight() * (i + 1) / (linesStations.length + 1) + 8,
                                (getWidth() * (j + 1) + 1) / (linesStations[i].length + 1) + 15,
                                getHeight() * (i + 1) / (linesStations.length + 1) + 8);
                    } else {
                        g.drawString(tNet.getLines()[i].getName(),
                                (getWidth() * (j + 1) + 2) / (linesStations[i].length + 1),
                                getHeight() * (i + 1) / (linesStations.length + 1) + 8);
                    }
                } catch (NullPointerException e) {
                }
            }


        }

        //
    }

    public void delay(long pauseTimeMillis) {
    /*This function pauses code execution for an input amount of time in milliseconds.
    It exists only because delay() isn't a thing in java. */
        try {
            Thread.currentThread().sleep(pauseTimeMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void empty() {
        invalidate();
        validate();
        repaint();
        this.removeAll();

    }

    public void passCurrent(TrainStation curStation) {
        this.curStation = curStation;
    }
}