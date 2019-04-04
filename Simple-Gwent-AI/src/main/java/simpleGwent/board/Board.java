package simpleGwent.board;

import simpleGwent.card.Card;
import simpleGwent.card.weatherCard;
import simpleGwent.containers.BoardSide;
import simpleGwent.containers.CardList;

public class Board {

    private boolean p1Skip;
    private boolean p2Skip;
    private int p1wins;
    private int p2wins;
    private BoardSide p1Side;
    private BoardSide p2Side;
    private int[] weather;

    public Board() {
        this.p1Side = new BoardSide();
        this.p2Side = new BoardSide();
        this.weather = new int[]{0, 0, 0};
        this.p1Skip = false;
        this.p2Skip = false;
        this.p1wins = 0;
        this.p2wins = 0;
    }

    /**
     * play card given as parameter to the selected players side of the board
     */
    public void playCard(int playerNo, Card card) {
        if (this.getWeather()[card.getRow() - 1] == 1) {
            card.setAffected(true);
        }
        if (playerNo == 1) {
            this.p1Side.addToRow(card);
        } else {
            this.p2Side.addToRow(card);
        }
    }

    public void clearBoard() {
        this.p1Side.resetSide();
        this.p2Side.resetSide();
        this.weather[0] = 0;
        this.weather[1] = 0;
        this.weather[2] = 0;
        this.p1Skip = false;
        this.p2Skip = false;
    }

    /**
     * puts weather effect on the board and on the cards it affects
     */
    public void setWeather(weatherCard card) {
        if (card.getRow() == 1) {
            toggleWeatherOfRow1(true);
            this.weather[0] = 1;
        } else if (card.getRow() == 2) {
            toggleWeatherOfRow2(true);
            this.weather[1] = 1;
        } else if (card.getRow() == 3) {
            toggleWeatherOfRow3(true);
            this.weather[2] = 1;
        } else {
            toggleWeatherOfAll();
            this.weather[0] = 0;
            this.weather[1] = 0;
            this.weather[2] = 0;
        }
    }

    /**
     * Used to pass the weather effect to the cards on first row
     */
    private void toggleWeatherOfRow1(boolean to) {
        CardList rowp1 = this.p1Side.getRow(1);
        CardList rowp2 = this.p2Side.getRow(1);
        for (int i = 0; i < rowp1.size(); i++) {
            rowp1.get(i).setAffected(to);
        }
        for (int i = 0; i < rowp2.size(); i++) {
            rowp2.get(i).setAffected(to);
        }
    }

    private void toggleWeatherOfRow2(boolean to) {
        CardList rowp1 = this.p1Side.getRow(2);
        CardList rowp2 = this.p2Side.getRow(2);
        for (int i = 0; i < rowp1.size(); i++) {
            rowp1.get(i).setAffected(to);
        }
        for (int i = 0; i < rowp2.size(); i++) {
            rowp2.get(i).setAffected(to);
        }
    }

    private void toggleWeatherOfRow3(boolean to) {
        CardList rowp1 = this.p1Side.getRow(3);
        CardList rowp2 = this.p2Side.getRow(3);
        for (int i = 0; i < rowp1.size(); i++) {
            rowp1.get(i).setAffected(to);
        }
        for (int i = 0; i < rowp2.size(); i++) {
            rowp2.get(i).setAffected(to);
        }
    }

    private void toggleWeatherOfAll() {
        toggleWeatherOfRow1(false);
        toggleWeatherOfRow2(false);
        toggleWeatherOfRow3(false);
    }

    /**
     * returns points of selected players first row either with or without
     * weather effect
     */
    public int getPointsOfRow1(int player, boolean weather) {
        int sum = 0;
        CardList cards;
        if (player == 1) {
            cards = p1Side.getRow(1);
        } else {
            cards = p2Side.getRow(1);
        }
        for (int i = 0; i < cards.size(); i++) {
            if (weather) {
                sum += cards.get(i).getValue();
            } else {
                sum += cards.get(i).getValueWithoutWeather();
            }
        }
        return sum;
    }

    /**
     * returns points of selected players second row either with or without
     * weather effect
     */
    public int getPointsOfRow2(int player, boolean weather) {
        int sum = 0;
        CardList cards;
        if (player == 1) {
            cards = p1Side.getRow(2);
        } else {
            cards = p2Side.getRow(2);
        }
        for (int i = 0; i < cards.size(); i++) {
            if (weather) {
                sum += cards.get(i).getValue();
            } else {
                sum += cards.get(i).getValueWithoutWeather();
            }
        }
        return sum;
    }

    /**
     * returns points of selected players third row either with or without
     * weather effect
     */
    public int getPointsOfRow3(int player, boolean weather) {
        int sum = 0;
        CardList cards;
        if (player == 1) {
            cards = p1Side.getRow(3);
        } else {
            cards = p2Side.getRow(3);
        }
        for (int i = 0; i < cards.size(); i++) {
            if (weather) {
                sum += cards.get(i).getValue();
            } else {
                sum += cards.get(i).getValueWithoutWeather();
            }
        }
        return sum;
    }
    
    public int getAmountOfCardsInRow(int player, int row) {
        int cards;
        if (player == 1) {
            cards = this.p1Side.getRow(row).size();
        } else {
            cards = this.p2Side.getRow(row).size();
        }
        return cards;
    }

    /**
     * returns total points of the selected player with or without weather
     * effect
     */
    public int getPointsOfPlayer(int player, boolean weather) {
        int points = this.getPointsOfRow1(player, weather);
        points += this.getPointsOfRow2(player, weather);
        points += this.getPointsOfRow3(player, weather);
        return points;
    }

    public int[] getWeather() {
        return this.weather;
    }

    /**
     * Used when player doesn't want to play anymore cards this round
     */
    public void setSkip(int player, boolean to) {
        if (player == 1) {
            this.p1Skip = to;
        } else {
            this.p2Skip = to;
        }
    }

    /**
     * Tells to the player who asked if the opponent has skipped
     */
    public boolean playerHasSkipped(int player) {
        if (player == 1) {
            return p1Skip;
        } else {
            return p2Skip;
        }
    }
    
    public int getOpponentsNumber(int player) {
        if (player == 1) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * used when player wins a round
     */
    public void addWinToPlayer(int player) {
        if (player == 1) {
            p1wins++;
        } else {
            p2wins++;
        }
    }

    public int roundsWonByPlayer(int player) {
        if (player == 1) {
            return p1wins;
        } else {
            return p2wins;
        }
    }

    private void printLine(CardList cards) {
        System.out.print("|");
        for (int i = 0; i < cards.size(); i++) {
            System.out.print("-" + cards.get(i).getValue());
        }
        System.out.println("-|");
    }

    private void printWeather() {
        String print = "|";
        if (this.weather[0] == 1) {
            print += "* ";
        }
        if (this.weather[1] == 1) {
            print += "# ";
        }
        if (this.weather[2] == 1) {
            print += ";";
        }
        print += "|";
        System.out.println(print);
    }

    /**
     * Text representation of the board
     */
    public void printBoard() {
        printLine(p2Side.getRow(3));
        printLine(p2Side.getRow(2));
        printLine(p2Side.getRow(1));
        printWeather();
        printLine(p1Side.getRow(1));
        printLine(p1Side.getRow(2));
        printLine(p1Side.getRow(3));
    }
}
