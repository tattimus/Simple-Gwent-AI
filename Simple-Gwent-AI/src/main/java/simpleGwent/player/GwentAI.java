package simpleGwent.player;

import simpleGwent.board.Board;
import simpleGwent.card.Card;
import simpleGwent.containers.WeatherList;
import simpleGwent.hand.Hand;

public class GwentAI implements Player {

    private int playerNo;
    private Board board;
    private Hand hand;
    private String stats;

    public GwentAI(int playerNo, Board board) {
        this.playerNo = playerNo;
        this.board = board;
        this.hand = new Hand();
        this.hand.createHand();
        this.stats = "" + hand.getCardCount() + hand.getWeatherCount();
    }

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Empty hand
     */
    public void resetHand() {
        this.hand = new Hand();
    }

    public void addToHand(int value, int row) {
        this.hand.addCard(value, row);
    }

    public void addWeatherToHand(int value) {
        this.hand.addWeatherCard(value);
    }

    /**
     * Choose play depending on the card count
     */
    @Override
    public Board play() {
        if (handHasPointCards()) {
            playCards();
        }
        return this.board;
    }

    @Override
    public void skipRound() {
        this.board.setSkip(playerNo, true);
    }

    @Override
    public void printHand() {
        String print = "|";
        for (int i = 0; i < hand.getHandSize(); i++) {
            print += "?|";
        }
        System.out.println(print);
    }

    /** print used in testing */
    public void printStats() {
        System.out.println(stats);
    }

    /**
     * Choose play depending on boards point situation
     */
    public void playCards() {
        if (winningInPoints()) {
            if (board.playerHasSkipped(board.getOpponentsNumber(playerNo))) {
                skipRound();
            } else {
                pointAdvantagePlay();
            }
        } else {
            pointDisadvantagePlay();
        }
    }

    /**
     * Moves to make if winning in points
     */
    public void pointAdvantagePlay() {
        if (safePointDifference()) {
            skipRound();
        } else if (winningInRounds()) { // play smallest to row with no weather
            int lane = bestLaneToPlayIfWinning();
            if (board.getWeather()[lane - 1] == 1) {
                board.playCard(playerNo, hand.getTheSmallest());
            } else {
                Card card = hand.getSmallestFromRow(lane);
                if (card != null) {
                    board.playCard(playerNo, card);
                } else {
                    board.playCard(playerNo, hand.getSmallestFromRow(bestLaneToPlay()));
                }
            }
        } else if (!checkWeatherCards()) { // check if weather play helps, if not -> play small
            int lane = bestLaneToPlay();
            if (board.getWeather()[lane - 1] == 1) {
                board.playCard(playerNo, hand.getTheSmallest());
            } else {
                board.playCard(playerNo, hand.getSmallestFromRow(lane));
            }
        }
    }

    /**
     * Moves to make if losing in points
     */
    public void pointDisadvantagePlay() {
        if (handPointsGreaterThanPointDifference()) { // Is it still possible to win this round?
            if (winningInRounds()) {
                if (!checkWeatherCards()) { // does playing weather help?
                    int pointDiff = board.getPointsOfPlayer(board.getOpponentsNumber(playerNo), true)
                            - board.getPointsOfPlayer(playerNo, true);
                    if (pointDiff > hand.getCardCount() * 2) { // dont play all cards if winning in rounds
                        skipRound();
                    } else { // if point difference small enough, play something small
                        int row = bestLaneToPlayIfWinning();
                        if (board.getWeather()[row - 1] == 0) {
                            Card card = hand.getSmallestFromRow(row);
                            if (card != null) {
                                board.playCard(playerNo, card);
                            } else {
                                board.playCard(playerNo, hand.getSmallestFromRow(bestLaneToPlay()));
                            }
                        } else if (board.playerHasSkipped(board.getOpponentsNumber(playerNo))) {
                            board.playCard(playerNo, hand.getTheSmallest());
                        } else {
                            skipRound();
                        }
                    }
                }
            } else { // Play cards to gain point lead
                int row = bestLaneToPlay();
                if (board.getWeather()[row - 1] == 1) {
                    board.playCard(playerNo, hand.getTheSmallest());
                } else {
                    int pointDiff = board.getPointsOfPlayer(board.getOpponentsNumber(playerNo), true)
                            - board.getPointsOfPlayer(playerNo, true);
                    Card card = hand.getCardEqualOrGreater(pointDiff, row);
                    if (card == null) {
                        board.playCard(playerNo, hand.getBiggestFromRow(row));
                    } else {
                        board.playCard(playerNo, card);
                    }
                }
            }
        } else if (!checkWeatherCards()) { // does weather help?
            if (winningInRounds()) { // if hopeless but winning in rounds -> give up
                skipRound();
            } else { // fight 'til the bitter end
                int row = bestLaneToPlay();
                if (board.getWeather()[row - 1] == 1) {
                    board.playCard(playerNo, hand.getTheBiggest());
                } else {
                    board.playCard(playerNo, hand.getBiggestFromRow(row));
                }
            }
        }
    }

    /**
     * returns true if AI is winning or if in first round
     */
    public boolean winningInRounds() {
        if (board.roundsWonByPlayer(1) == 0 && board.roundsWonByPlayer(2) == 0) {
            return true;
        }
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
                    board.setWeather(hand.getWeather(wl.get(i).getRow()));
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
     * suggests playing cards that might later prevent AI from playing its
     * weather cards, only used when winning in rounds or during first round
     */
    public int bestLaneToPlayIfWinning() {
        int row1 = pointsForLane1();
        int row2 = pointsForLane2();
        int row3 = pointsForLane3();
        if (row1 > 0) {
            if (hand.hasWeatherForRow(1)) {
                row1 += 3;
            }
        }
        if (row2 > 0) {
            if (hand.hasWeatherForRow(2)) {
                row2 += 3;
            }
        }
        if (row3 > 0) {
            if (hand.hasWeatherForRow(3)) {
                row3 += 3;
            }
        }
        if (row1 > row2 && row1 > row3) {
            return 1;
        } else if (row2 > row1 && row2 > row3) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * counts the value of the lane, bigger value means higher priority
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
        if (hand.hasWeatherForRow(2) || hand.hasWeatherForRow(3)) {
            sum++;
        }
        if (hand.rowPoints(1) >= hand.rowPoints(2)) {
            sum++;
        }
        if (hand.rowPoints(1) >= hand.rowPoints(3)) {
            sum++;
        }
        if (hand.hasWeatherForRow(1)) {
            sum--;
        }
        if (hand.hasWeatherForRow(0)) {
            sum++;
            if (hand.rowCards(1) >= hand.rowCards(2)
                    && hand.rowCards(1) >= hand.rowCards(3)) {
                sum++;
            }
            if (hand.rowPoints(1) >= hand.rowPoints(2)) {
                sum++;
            }
            if (hand.rowPoints(1) >= hand.rowPoints(3)) {
                sum++;
            }
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
        if (hand.hasWeatherForRow(1) || hand.hasWeatherForRow(3)) {
            sum++;
        }
        if (hand.rowPoints(2) >= hand.rowPoints(1)) {
            sum++;
        }
        if (hand.rowPoints(2) >= hand.rowPoints(3)) {
            sum++;
        }
        if (hand.hasWeatherForRow(2)) {
            sum--;
        }
        if (hand.hasWeatherForRow(0)) {
            sum++;
            if (hand.rowCards(2) >= hand.rowCards(1)
                    && hand.rowCards(2) >= hand.rowCards(3)) {
                sum++;
            }
            if (hand.rowPoints(2) >= hand.rowPoints(1)) {
                sum++;
            }
            if (hand.rowPoints(2) >= hand.rowPoints(3)) {
                sum++;
            }
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
        if (hand.hasWeatherForRow(1) || hand.hasWeatherForRow(2)) {
            sum++;
        }
        if (hand.rowPoints(3) >= hand.rowPoints(2)) {
            sum++;
        }
        if (hand.rowPoints(3) >= hand.rowPoints(1)) {
            sum++;
        }
        if (hand.hasWeatherForRow(3)) {
            sum--;
        }
        if (hand.hasWeatherForRow(0)) {
            sum++;
            if (hand.rowCards(3) >= hand.rowCards(1)
                    && hand.rowCards(3) >= hand.rowCards(2)) {
                sum++;
            }
            if (hand.rowPoints(3) >= hand.rowPoints(2)) {
                sum++;
            }
            if (hand.rowPoints(3) >= hand.rowPoints(1)) {
                sum++;
            }
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

    /**
     * check if hand has point cards, if not -> play weather or skip
     */
    public boolean handHasPointCards() {
        if (hand.getCardCount() > 0) {
            return true;
        } else {
            if (!checkWeatherCards()) {
                skipRound();
            }
            return false;
        }
    }

    /**
     * Is the point difference big enough to skip
     */
    public boolean safePointDifference() {
        int safeThres;
        int pointDiff = board.getPointsOfPlayer(playerNo, true)
                - board.getPointsOfPlayer(board.getOpponentsNumber(playerNo), true);
        if (winningInRounds()) {
            safeThres = 1;
        } else {
            safeThres = 8;
        }
        return pointDiff > (safeThres * hand.getCardCount());
    }

    /**
     * Is the sum of card points enough to gain lead, used when behind in points
     */
    public boolean handPointsGreaterThanPointDifference() {
        int row1;
        int row2;
        int row3;
        if (board.getWeather()[0] == 1) {
            row1 = hand.rowCards(1);
        } else {
            row1 = hand.rowPoints(1);
        }
        if (board.getWeather()[1] == 1) {
            row2 = hand.rowCards(2);
        } else {
            row2 = hand.rowPoints(2);
        }
        if (board.getWeather()[2] == 1) {
            row3 = hand.rowCards(3);
        } else {
            row3 = hand.rowPoints(3);
        }
        int handPoints = row1 + row2 + row3;
        int pointDiff = board.getPointsOfPlayer(board.getOpponentsNumber(playerNo), true)
                - board.getPointsOfPlayer(playerNo, true);
        return handPoints > pointDiff;
    }
}
