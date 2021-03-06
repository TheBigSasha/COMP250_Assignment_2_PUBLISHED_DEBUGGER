package COMP250A2_W2020;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

/**
 * Simple randomized tester for COMP250 A2
 *
 * @author Sasha Aleshchenko
 */
public class SashaTrainRide {

    public static void main(String[] args) {
        //====================================== Error checking code from Maya's Tester, moved here to check package private errors ===================================
        TrainNetwork tNet = generateTrainNetwork();
        try {
            tNet.getLineByName("Scarlet").getNext(tNet.getLineByName("Scarlet").findStation("FakeStaion"));
        } catch (StationNotFoundException e) {
            System.out.println("Your getNext function the appropriate exception\n");
        }
        try {
            tNet.getLineByName("Scarlet").findStation("FakeStation");
        } catch (StationNotFoundException e) {
            System.out.println("Your findStation function the appropriate exception\n");
        }
        try {
            tNet.getLineByName("Scarlet").travelOneStation(tNet.getLineByName("Scarlet").findStation("FakeStaion"), null);
        } catch (StationNotFoundException e) {
            System.out.println("Your travelOneStation function the appropriate exception\n");
        }
        //====================================== Good luck on the tester! ===================================
        System.out.println("Welcome to the Confusing Railroad! Enter a seed (long)");
        Scanner scanner = new Scanner(System.in);
        long seed = 0;
        if (args.length != 0) {
            seed = parseInt(args[0]);
        } else {
            seed = scanner.nextLong();
        }
        RandomTrains rand = new RandomTrains(seed);

        // Constructs a train network
        tNet = rand.nextTrainNetwork();

        // Prints the train network plan
        tNet.printPlan();

        // Travels from Little Whinging to Hogwarts.
        TrainLine startLine = tNet.getLines()[rand.nextInt(tNet.getLines().length - 1)];
        TrainLine endLine = tNet.getLines()[rand.nextInt(tNet.getLines().length - 1)];

        TrainStation startStation = startLine.getLeftTerminus();
        TrainStation endStation = endLine.getLeftTerminus();

        tNet.travel(startStation.getName(),startLine.getName(),endStation.getName(),endLine.getName());
        System.out.println("Done!");
        tNet.printPlan();

        // Resets the network to its initial position
        System.out.println("Resetting the network");
        tNet.undance();
        tNet.printPlan();
    }

    // Calls constructors and methods to implement the network shown in the handout
    // map.
    public static TrainNetwork generateTrainNetwork() {
        // creating line 1
        TrainStation s1 = new TrainStation("1.Little Whinging");
        TrainStation s5 = new TrainStation("5.St Mungo's"); //TODO: Terminus on right side goes to null upon add :<

        s1.setRight(s5);
        s5.setLeft(s1);

        TrainLine l1 = new TrainLine(s1, s5, "Scarlet", true);

        TrainStation s2 = new TrainStation("2.Wizard Hat");
        l1.addStation(s2);
        TrainStation s3 = new TrainStation("3.Hogsmeade");
        l1.addStation(s3);
        TrainStation s4 = new TrainStation("4.Diagon Alley- 1/3");
        l1.addStation(s4);

        // creating line 2
        TrainStation t1 = new TrainStation("1.Gringotts");
        TrainStation t5 = new TrainStation("5.Leaky Cauldron");

        t1.setRight(t5);
        t5.setLeft(t1);

        TrainLine l2 = new TrainLine(t1, t5, "Grey", true);

        TrainStation t2 = new TrainStation("2.Diagon Alley - 2/3");
        l2.addStation(t2);
        TrainStation t3 = new TrainStation("3.Ollivanders");
        l2.addStation(t3);
        TrainStation t4 = new TrainStation("4.King's Cross - 3/5");
        l2.addStation(t4);

        s4.setConnection(l2, t2);
        t2.setConnection(l1, s4);

        // creating line 3
        TrainStation u1 = new TrainStation("1.King's Cross - 4/5");
        TrainStation u5 = new TrainStation("5.Hogwarts");

        u1.setRight(u5);
        u5.setLeft(u1);

        TrainLine l3 = new TrainLine(u1, u5, "Purple", true);

        TrainStation u2 = new TrainStation("2.Ministry of Magic");
        l3.addStation(u2);
        TrainStation u3 = new TrainStation("3.Snowy Owl");
        l3.addStation(u3);
        TrainStation u4 = new TrainStation("4.Godric's Hollow");
        l3.addStation(u4);

        u1.setConnection(l2, t4);
        t4.setConnection(l3, u1);

        TrainNetwork tNet = new TrainNetwork(1);
        TrainLine[] lines = { l1, l2, l3 };
        tNet.addLines(lines);

        return tNet;
    }
}