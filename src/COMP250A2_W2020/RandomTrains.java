package COMP250A2_W2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Randomizer for COMP250 A2 objects
 *
 * @author Sasha Aleshchenko
 */
public class RandomTrains extends Random {
    int iterator = 0;
    TrainStation previous;
    ArrayList<String> names = new ArrayList<String>();
    File Lastnames = new File("supportfiles/last_names.all.txt");

    public RandomTrains(long seed) {
        this.setSeed(seed);
        try {
            Scanner scanner = new Scanner(Lastnames);
            while (scanner.hasNextLine()) {
                names.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
        }
    }

    public RandomTrains() {
        try {
            Scanner scanner = new Scanner(Lastnames);
            while (scanner.hasNextLine()) {
                names.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
        }
    }

    public TrainStation nextTrainStation() {
        TrainStation output = new TrainStation(iterator + " " + nextName());
        try {
            if (output.getLine().equals(previous.getLine())) {
                iterator++;
            }
        } catch (NullPointerException e) {
            iterator++;
        }
        previous = output;
        return output;
    }

    public TrainLine nextTrainLine() {
        TrainStation leftTerm = nextTrainStation();
        TrainStation rightTerm = nextTrainStation();
        leftTerm.setRight(rightTerm);
        rightTerm.setLeft(leftTerm);
        TrainLine output = new TrainLine(leftTerm, rightTerm, nextName(), nextBoolean());
        leftTerm.setTrainLine(output);
        rightTerm.setTrainLine(output);
        return output;
    }

    public void addConnectingStop(TrainLine lineA, TrainLine lineB) {
        String name = nextName();
        TrainStation connectionA = new TrainStation(iterator + " " + name + " A");
        TrainStation connectionB = new TrainStation(iterator + " " + name + " B");
        connectionA.setConnection(lineB, connectionB);
        connectionB.setConnection(lineA, connectionA);
        lineA.addStation(connectionA);
        lineB.addStation(connectionB);
    }

    public TrainNetwork nextTrainNetwork() {
        int numLines = 1 + nextInt(25);//TODO: Add spiciness parameter
        TrainNetwork output = new TrainNetwork(numLines);
        TrainLine[] lines = new TrainLine[numLines];
        for (int i = 0; i < numLines; i++) {
            lines[i] = nextTrainLine();
            for (int j = 0; j < nextInt(10); j++) {
                lines[i].addStation(nextTrainStation());
                if (nextInt(300) > 200 && i != 0) {
                    if (i > 1) {
                        addConnectingStop(lines[i], lines[nextInt(i - 1)]);
                    }
                }
                if (i >= 1 && nextInt(799) < 750) {
                    addConnectingStop(lines[i], lines[i - 1]);
                }
            }


        }
        output.addLines(lines);
        System.out.println("Created random train network with " + output.getLines().length + " lines");
        return output;
    }

    public String nextName() {
        try {
            int numNames = nextInt(names.size());
            String output = names.get(numNames);
            names.remove(numNames);
            return output;

        } catch (Exception e) {

            int nameLength = nextInt(11);
            nameLength += 1;
            char[] nameOutChar = new char[nameLength];
            for (int i = 0; i < nameLength; i++) {
                if (nameLength <= 4) {
                    nameOutChar[i] = (char) nextInt(18500);
                    nameOutChar[i] += (char) nextInt(5000);
                } else {
                    int nameNumber = nextInt(90 - 65) + 65;
                    nameOutChar[i] = (char) nameNumber;
                    if (i == nameLength - 3) {
                        nameOutChar[i + 1] = (char) 85;
                    }
                }
            }
            //System.out.println(nameOut);
            return String.valueOf(nameOutChar);
        }


    }


    public TrainLine[] nextTrainLineArray(int size) {
        TrainLine[] lines = new TrainLine[size];
        for (int i = 0; i < size; i++) {
            lines[i] = nextTrainLine();
            for (int j = 0; j < nextInt(10); j++) {
                lines[i].addStation(nextTrainStation());
                if (nextInt(300) > 200 && i != 0) {
                    if (i > 1) {
                        addConnectingStop(lines[i], lines[nextInt(i - 1)]);
                    }
                }
                if (i >= 1 && nextInt(799) < 750) {
                    addConnectingStop(lines[i], lines[i - 1]);
                }
            }

        }
        return lines;
    }

    public TrainLine[] nextTrainLineArray(int size, boolean alwaysConnect) {
        TrainLine[] lines = new TrainLine[size];
        for (int i = 0; i < size; i++) {
            lines[i] = nextTrainLine();
            for (int j = 0; j < nextInt(10); j++) {
                lines[i].addStation(nextTrainStation());
                if (nextInt(300) > 290 && i != 0) {
                    if (i > 1) {
                        addConnectingStop(lines[i], lines[nextInt(i - 1)]);
                    }
                }
                if ((i >= 1 && nextInt(799) < 750) || (i >= 1 && alwaysConnect)) {
                    addConnectingStop(lines[i], lines[i - 1]);
                }
            }

        }
        for (TrainLine line : lines) {
            boolean passed = false;
            for (TrainStation station : line.getLineArray()) {
                if (station.hasConnection) {
                    passed = true;
                }
            }
            if (!passed) {
                TrainLine toAdd;
                do {
                    toAdd = lines[nextInt(lines.length - 1)];
                } while (toAdd.equals(line));
                addConnectingStop(line, toAdd);
            }
        }
        return lines;

    }
}