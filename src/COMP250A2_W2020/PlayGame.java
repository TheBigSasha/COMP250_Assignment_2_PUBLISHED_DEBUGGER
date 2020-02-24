package COMP250A2_W2020;

public class PlayGame {
    public static void main(String[] args) {
        RandomTrains rand = new RandomTrains();
        TrainLine[] lines = rand.nextTrainLineArray(5);
        GameRide game = new GameRide(lines.length);
        game.addLines(lines);
        //TODO: implement levels & highscores
        game.gameTravel(lines[0].getRightTerminus().getLeft().getName(),
                game.getLines()[0].getName(),
                game.getLines()[game.getLines().length - 1].getRightTerminus().getName(),
                game.getLines()[game.getLines().length - 1].getName());
    }
}
