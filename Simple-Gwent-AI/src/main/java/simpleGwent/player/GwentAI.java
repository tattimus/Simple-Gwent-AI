package simpleGwent.player;

import simpleGwent.board.Board;
import simpleGwent.containers.WeatherList;
import simpleGwent.hand.Hand;

public class GwentAI implements Player {

    private int playerNo;
    private Board board;
    private Hand hand;

    public GwentAI(int playerNo, Board board) {
        this.playerNo = playerNo;
        this.board = board;
        this.hand = new Hand();
        this.hand.createHand();
    }

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }
    
    public void resetHand() {
        this.hand = new Hand();
    }
    
    public void addToHand(int value, int row) {
        this.hand.addCard(value, row);
    }
    
    public void addWeatherToHand(int value) {
        this.hand.addWeatherCard(value);
    }

    @Override
    public Board play() {
        skipRound();
        return this.board;
    }

    @Override
    public void skipRound() {
        this.board.setSkip(playerNo, true);
    }

    @Override
    public void printHand() {
        String line = "|";
        for (int i = 0; i < hand.getHandSize(); i++) {
            line += "?|";
        }
        System.out.println(line);
    }

    public void roundAdvantagePlay() {

    }

    public void roundDisadvantagePlay() {

    }

    public void pointAdvantagePlay() {

    }

    public void pointDisadvantagePlay() {

    }

    /**
     * returns true if AI is winning
     */
    public boolean winningInRounds() {
        return board.roundsWonByPlayer(playerNo)
                > board.roundsWonByPlayer(board.getOpponentsNumber(playerNo));
    }

    /**
     * returns true if AI has point advantage
     */
    public boolean winningInPoints() {
        return board.getPointsOfPlayer(playerNo, true)
                > board.getPointsOfPlayer(board.getOpponentsNumber(playerNo), true);
    }

    /**
     * checks if playing any of the AI's weather cards has positive impact,
     * First positive weather is played
     */
    public boolean checkWeatherCards() {
        WeatherList wl = hand.getWeatherList();
        for (int i = 0; i < wl.size(); i++) {
            if (wl.get(i).getRow() == 0) {
                if (clearingWeatherHelps()) {
                    board.setWeather(hand.getWeather(0));
                    return true;
                }
            } else if (playingWeatherHelps(wl.get(i).getRow())) {
                board.setWeather(hand.getWeather(wl.get(i).getRow()));
                return true;
            }
        }
        return false;
    }

    /**
     * returns number of the lane thats best for the AI
     */
    public int bestLaneToPlay() {
        int lane1 = pointsForLane1();
        int lane2 = pointsForLane2();
        int lane3 = pointsForLane3();
        if (lane1 >= lane2 && lane1 >= lane3) {
            return 1;
        } else if (lane2 >= lane1 && lane2 >= lane3) {
            return 2;
        } else if (lane3 >= lane1 && lane3 >= lane2) {
            return 3;
        } else {
            return 1;
        }
    }

    /**
     * counts the value of the lane, bigger value means better
     */
    private int pointsForLane1() {
        int sum = 0;
        if (hand.rowCards(1) == 0) {
            return 0;
        } else {
            sum++;
        }
        if (board.getWeather()[0] == 0) {
            sum += 2;
        }
        if (hand.rowPoints(1) >= hand.rowPoints(2)) {
            sum++;
        }
        if (hand.rowPoints(1) >= hand.rowPoints(3)) {
            sum++;
        }
        if (hand.rowCards(1) >= hand.rowCards(2)
                && hand.rowCards(1) >= hand.rowCards(3)) {
            sum++;
        }
        if (hand.hasWeatherForRow(1)) {
            sum--;
        }
        if (hand.hasWeatherForRow(0)) {
            sum++;
        }
        return sum;
    }

    private int pointsForLane2() {
        int sum = 0;
        if (hand.rowCards(2) == 0) {
            return 0;
        } else {
            sum++;
        }
        if (board.getWeather()[1] == 0) {
            sum += 2;
        }
        if (hand.rowPoints(2) >= hand.rowPoints(1)) {
            sum++;
        }
        if (hand.rowPoints(2) >= hand.rowPoints(3)) {
            sum++;
        }
        if (hand.rowCards(2) >= hand.rowCards(1)
                && hand.rowCards(2) >= hand.rowCards(3)) {
            sum++;
        }
        if (hand.hasWeatherForRow(2)) {
            sum--;
        }
        if (hand.hasWeatherForRow(0)) {
            sum++;
        }
        return sum;
    }

    private int pointsForLane3() {
        int sum = 0;
        if (hand.rowCards(3) == 0) {
            return 0;
        } else {
            sum++;
        }
        if (board.getWeather()[2] == 0) {
            sum += 2;
        }
        if (hand.rowPoints(3) >= hand.rowPoints(2)) {
            sum++;
        }
        if (hand.rowPoints(3) >= hand.rowPoints(1)) {
            sum++;
        }
        if (hand.rowCards(3) >= hand.rowCards(1)
                && hand.rowCards(3) >= hand.rowCards(2)) {
            sum++;
        }
        if (hand.hasWeatherForRow(3)) {
            sum--;
        }
        if (hand.hasWeatherForRow(0)) {
            sum++;
        }
        return sum;
    }

    /**
     * checks if chosen weather increases the point advatage of the AI, if so ->
     * return true, else false
     */
    public boolean playingWeatherHelps(int weather) {
        int ownPoints;
        int opponentsPoints;
        int ownCards;
        int opponentsCards;

        if (board.getWeather()[weather - 1] == 1) {
            return false;
        }

        if (weather == 1) {
            ownPoints = board.getPointsOfRow1(playerNo, true);
            opponentsPoints = board.getPointsOfRow1(board.getOpponentsNumber(playerNo), true);
            ownCards = board.getAmountOfCardsInRow(playerNo, 1);
            opponentsCards = board.getAmountOfCardsInRow(board.getOpponentsNumber(playerNo), 1);
        } else if (weather == 2) {
            ownPoints = board.getPointsOfRow2(playerNo, true);
            opponentsPoints = board.getPointsOfRow2(board.getOpponentsNumber(playerNo), true);
            ownCards = board.getAmountOfCardsInRow(playerNo, 2);
            opponentsCards = board.getAmountOfCardsInRow(board.getOpponentsNumber(playerNo), 2);
        } else {
            ownPoints = board.getPointsOfRow3(playerNo, true);
            opponentsPoints = board.getPointsOfRow3(board.getOpponentsNumber(playerNo), true);
            ownCards = board.getAmountOfCardsInRow(playerNo, 3);
            opponentsCards = board.getAmountOfCardsInRow(board.getOpponentsNumber(playerNo), 3);
        }

        int oldPointDiff = board.getPointsOfPlayer(playerNo, true)
                - board.getPointsOfPlayer(board.getOpponentsNumber(playerNo), true);
        int newPointDiff = ((board.getPointsOfPlayer(playerNo, true) - ownPoints) + ownCards)
                - ((board.getPointsOfPlayer(board.getOpponentsNumber(playerNo), true)
                - opponentsPoints) + opponentsCards);

        return oldPointDiff < newPointDiff && newPointDiff >= 0;
    }

    /**
     * checks if clearing all weather effects from the board gives the AI point
     * advantage or increases it
     */
    public boolean clearingWeatherHelps() {
        int ownPointsNoWeather = board.getPointsOfPlayer(playerNo, false);
        int opponentsPointsNoWeather = board.getPointsOfPlayer(board.getOpponentsNumber(playerNo), false);
        int ownPoints = board.getPointsOfPlayer(playerNo, true);
        int opponentsPoints = board.getPointsOfPlayer(board.getOpponentsNumber(playerNo), true);
        return (ownPointsNoWeather - opponentsPointsNoWeather) > (ownPoints - opponentsPoints)
                && ownPointsNoWeather - opponentsPointsNoWeather > 0;
    }
}
