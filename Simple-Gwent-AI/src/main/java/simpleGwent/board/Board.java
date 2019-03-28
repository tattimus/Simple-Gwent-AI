package simpleGwent.board;

import java.util.ArrayList;
import simpleGwent.card.Card;
import simpleGwent.card.weatherCard;

public class Board {

    private boolean p1Skip;
    private boolean p2Skip;
    private int p1wins;
    private int p2wins;
    private ArrayList<Card> p1row1;
    private ArrayList<Card> p1row2;
    private ArrayList<Card> p1row3;
    private ArrayList<Card> p2row1;
    private ArrayList<Card> p2row2;
    private ArrayList<Card> p2row3;
    private int[] weather;

    public Board() {
        this.p1row1 = new ArrayList<>();
        this.p1row2 = new ArrayList<>();
        this.p1row3 = new ArrayList<>();
        this.p2row1 = new ArrayList<>();
        this.p2row2 = new ArrayList<>();
        this.p2row3 = new ArrayList<>();
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
        if (playerNo == 1 && card.getRow() == 1) {
            this.p1row1.add(card);
        } else if (playerNo == 1 && card.getRow() == 2) {
            this.p1row2.add(card);
        } else if (playerNo == 1 && card.getRow() == 3) {
            this.p1row3.add(card);
        } else if (playerNo == 2 && card.getRow() == 1) {
            this.p2row1.add(card);
        } else if (playerNo == 2 && card.getRow() == 2) {
            this.p2row2.add(card);
        } else {
            this.p2row3.add(card);
        }
    }

    public void clearBoard() {
        p1row1 = new ArrayList<>();
        p1row2 = new ArrayList<>();
        p1row3 = new ArrayList<>();
        p2row1 = new ArrayList<>();
        p2row2 = new ArrayList<>();
        p2row3 = new ArrayList<>();
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
        for (int i = 0; i < this.p1row1.size(); i++) {
            this.p1row1.get(i).setAffected(to);
        }
        for (int i = 0; i < this.p2row1.size(); i++) {
            this.p2row1.get(i).setAffected(to);
        }
    }

    private void toggleWeatherOfRow2(boolean to) {
        for (int i = 0; i < this.p1row2.size(); i++) {
            this.p1row2.get(i).setAffected(to);
        }
        for (int i = 0; i < this.p2row2.size(); i++) {
            this.p2row2.get(i).setAffected(to);
        }
    }

    private void toggleWeatherOfRow3(boolean to) {
        for (int i = 0; i < this.p1row3.size(); i++) {
            this.p1row3.get(i).setAffected(to);
        }
        for (int i = 0; i < this.p2row3.size(); i++) {
            this.p2row3.get(i).setAffected(to);
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
        ArrayList<Card> cards;
        if (player == 1) {
            cards = this.p1row1;
        } else {
            cards = this.p2row1;
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
        ArrayList<Card> cards;
        if (player == 1) {
            cards = this.p1row2;
        } else {
            cards = this.p2row2;
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
        ArrayList<Card> cards;
        if (player == 1) {
            cards = this.p1row3;
        } else {
            cards = this.p2row3;
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

    private void printLine(ArrayList<Card> cards) {
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
        printLine(this.p2row3);
        printLine(this.p2row2);
        printLine(this.p2row1);
        printWeather();
        printLine(this.p1row1);
        printLine(this.p1row2);
        printLine(this.p1row3);
    }
}
