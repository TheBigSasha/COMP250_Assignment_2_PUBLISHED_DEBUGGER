package COMP250A2_W2020;

import java.util.Random;

/**
 * Randomizer for COMP250 A2 objects
 *
 * @author Sasha Aleshchenko
 */
public class RandomTrains extends Random {
    int iterator = 0;
    TrainStation previous;

    public RandomTrains(long seed){
        this.setSeed(seed);
    }

    public RandomTrains(){
    }

    public TrainStation nextTrainStation(){
        TrainStation output = new TrainStation(iterator + " " + nextName());
        try{
            if(output.getLine().equals(previous.getLine())){
                iterator++;}}catch(NullPointerException e){iterator++;}
        previous = output;
        return output;
    }

    public TrainLine nextTrainLine(){
        TrainStation leftTerm = nextTrainStation();
        TrainStation rightTerm = nextTrainStation();
        leftTerm.setRight(rightTerm);
        rightTerm.setLeft(leftTerm);
        TrainLine output =  new TrainLine(leftTerm,rightTerm,nextName(),nextBoolean());
        leftTerm.setTrainLine(output);
        rightTerm.setTrainLine(output);
        return output;
    }

    public void addConnectingStop(TrainLine lineA, TrainLine lineB){
        String name = nextName();
        TrainStation connectionA = new TrainStation(iterator + name + " 1 / 2");
        TrainStation connectionB = new TrainStation(iterator + name + " 2 / 2");
        connectionA.setConnection(lineB, connectionB);
        connectionB.setConnection(lineA, connectionA);
        lineA.addStation(connectionA);
        lineB.addStation(connectionB);
    }

    public TrainNetwork nextTrainNetwork(){
        int numLines = 2 + nextInt(23);
        TrainNetwork output = new TrainNetwork(numLines);
        TrainLine[] lines = new TrainLine[numLines];
        for (int i = 0; i < numLines; i++) {
            lines[i] = nextTrainLine();
            for (int j = 0; j < nextInt(10); j++) {
                lines[i].addStation(nextTrainStation());
                if (nextInt(300) > 250 && i != 0) {
                    if (i > 1) {
                        addConnectingStop(lines[i], lines[nextInt(i - 1)]);
                    }
                }
                if (i >= 1) {//TODO: Make connecting stops add to random places always
                    addConnectingStop(lines[i], lines[i - 1]);
                }
            }


        }
        output.addLines(lines);
        System.out.println("Created random train network with " + output.getLines().length + " lines");
        return output;
    }

    public String nextName() {
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