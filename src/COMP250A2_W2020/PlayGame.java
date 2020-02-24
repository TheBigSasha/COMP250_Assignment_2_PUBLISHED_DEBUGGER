package COMP250A2_W2020;

import javax.swing.*;

public class PlayGame {
    public static void main(String[] args) {
        if (args.length != 0) {
            play(Integer.parseInt(args[0]));
        } else {
            play(24601);
        }
    }

    public static void play(int seed) {
        RandomTrains rand = new RandomTrains(seed);
        TrainLine[] manyConnections = new TrainLine[20];
        for (int i = 0; i < 20; i++) {
            TrainLine current = rand.nextTrainLine();
            manyConnections[i] = current;
        }
        for (int i = 1; i < manyConnections.length; i++) {
            TrainLine lineA = manyConnections[i];
            TrainLine lineB = manyConnections[i - 1];
            manyConnections[i].getLeftTerminus().setConnection(lineB, manyConnections[i - 1].getLeftTerminus());
            manyConnections[i - 1].getLeftTerminus().setConnection(lineA, manyConnections[i].getLeftTerminus());
        }
        GameRide game = new GameRide(manyConnections.length);
        game.addLines(manyConnections);
        game.gameTravel(manyConnections[0].getRightTerminus().getLeft().getName(),
                game.getLines()[0].getName(),
                game.getLines()[game.getLines().length - 1].getRightTerminus().getName(),
                game.getLines()[game.getLines().length - 1].getName());

        TrainLine[] lines;
        while (true) {
            int maxSize = 2;
            while (maxSize < 25) {
                lines = rand.nextTrainLineArray(maxSize, true);
                game = new GameRide(lines.length);
                game.addLines(lines);
                game.frame.add(new JLabel("Currently at level " + (maxSize - 1)));
                //TODO: implement levels & highscores
                game.gameTravel(lines[0].getRightTerminus().getLeft().getName(),
                        game.getLines()[0].getName(),
                        game.getLines()[game.getLines().length - 1].getRightTerminus().getName(),
                        game.getLines()[game.getLines().length - 1].getName());
                maxSize++;
            }

        }
    }
}
