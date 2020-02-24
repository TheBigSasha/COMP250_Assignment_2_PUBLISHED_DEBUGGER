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
    public GameRide gRide;
    private Random rand = new Random();
    public JFrame frame;
    public TrainStation curStation;
    private long timeTracker;
    private int[] startCoords;

    public TrainVisualizer(GameRide gRide) {
        //===============================Game options============================

        //============================Create frame object=============================
        frame = gRide.frame;
        //=========================Automatic window sizing============================
        int longestLineLength = 0;
        for (TrainLine line : gRide.getLines()) {
            if (line.getSize() > longestLineLength) {
                longestLineLength = line.getSize();
            }
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int idealSizeX = 800 / 5 * longestLineLength;
        int idealSizeY = 450 / 3 * gRide.getLines().length;
        int windowSizeX;
        int windowSizeY;
        windowSizeX = Math.min(idealSizeX, screenSize.width);
        windowSizeY = Math.min(idealSizeY, screenSize.height);
        setSize(windowSizeX, windowSizeY);
        try {
            frame.setIconImage(new ImageIcon(getClass().getResource("supportfiles/TrainVisualizer.png")).getImage());
        } catch (Exception e) {
        }
        //=========================GUI Parameters============================
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //=========================Set fields============================
        timeTracker = 0;
        this.gRide = gRide;
        gRide.frame.setVisible(true);
    }

    public TrainVisualizer(TrainNetwork tNet) {
        //============================Create frame object=============================
        frame = new JFrame("Network Visualizer V1 - Visit sashaphoto.ca");
        //=========================Automatic window sizing============================
        int longestLineLength = 0;
        for (TrainLine line : tNet.getLines()) {
            if (line.getSize() > longestLineLength) {
                longestLineLength = line.getSize();
            }
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int idealSizeX = 800 / 5 * longestLineLength;
        int idealSizeY = 450 / 3 * tNet.getLines().length;
        int windowSizeX;
        int windowSizeY;
        windowSizeX = Math.min(idealSizeX, screenSize.width);
        windowSizeY = Math.min(idealSizeY, screenSize.height);
        setSize(windowSizeX, windowSizeY);
        try {
            frame.setIconImage(new ImageIcon(getClass().getResource("supportfiles/TrainVisualizer.png")).getImage());
        } catch (Exception e) {
        }
        //=========================GUI Parameters============================
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //=========================Set fields============================
        timeTracker = 0;
        this.tNet = tNet;
    }

    public static void main(String[] args) {
        TrainVisualizer t = new TrainVisualizer(generateTrainNetwork()); //This is a method in TrainRide.java, if it is an error make sure you have all of your classes in the same package.
        t.paint(null); // Not a proper way, but it still works.
    }

    public void paint(Graphics g) {
        Font f4 = new Font(Font.SERIF, Font.BOLD, 15);
        Font f3 = new Font(Font.SERIF, Font.BOLD | Font.ITALIC, 15);
        Font f1 = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        try {
            g.setColor(Color.black);
            g.clearRect(0, 0, getWidth(), getHeight());
            g.setFont(f4);
            //g.drawString("Lines", getWidth() - 100, 50);
            g.drawString("Hour " + timeTracker, 20, 50);
            g.setFont(f3);
            g.drawString("<3 - sashaphoto.ca", getWidth() - 150, getHeight() - 15);
            g.setFont(f1);
        } catch (NullPointerException e) {
        }
        TrainLine[] lines;
        if (tNet != null) {
            lines = tNet.networkLines;
        } else {
            lines = gRide.networkLines;
        }
        TrainStation[][] linesStations = new TrainStation[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            try {
                linesStations[i] = lines[i].getLineArray();
            } catch (Exception e) {
            }
        }
        try {
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
                            g.setFont(f4);
                            try {
                                g.drawString(tNet.getLines()[i].getName(),
                                        (getWidth() - 100),
                                        getHeight() * (i + 1) / (linesStations.length + 1) + 8);
                            } catch (NullPointerException e) {
                                g.drawString(gRide.getLines()[i].getName(),
                                        (getWidth() - 100),
                                        getHeight() * (i + 1) / (linesStations.length + 1) + 8);
                            }
                            g.setFont(f1);
                        }
                        if ((linesStations[i][j].equals(curStation) || linesStations[i][j].getName().equals(curStation.getName())) && timeTracker == 0) {//TODO: Implement start marker & end marker
                            startCoords = new int[]{i, j};
                            System.out.println("set start coords as " + startCoords[0] + " " + startCoords[1]);
                        } else {
                            System.out.println("drew red at " + startCoords[0] + " " + startCoords[1]);
                            g.setColor(Color.RED);
                            g.fillRect((getWidth() * (startCoords[1]) + 1) / (linesStations[startCoords[0]].length + 1) + 15,
                                    getHeight() * (startCoords[0] + 1) / (linesStations.length + 1),
                                    15, 15);
                            g.setColor(Color.black);
                        }

                    } catch (NullPointerException e) {
                    }
                }


            }

            //
        } catch (Exception e) {
            System.out.println("[ERROR] [VISUALIZER] Visualizer encountered " + e + " during execution");
        }
    }

    public void delay(long pauseTimeMillis) {
    /*This function pauses code execution for an input amount of time in milliseconds.
    It exists only because delay() isn't a thing in java. */
        try {
            Thread.sleep(pauseTimeMillis);
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
        timeTracker++;
        this.curStation = curStation;
    }
}
