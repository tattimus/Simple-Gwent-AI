package simpleGwent.player;

import java.util.Scanner;
import simpleGwent.board.Board;
import simpleGwent.hand.Hand;

public class GwentPlayer implements Player {

    private int playerNo;
    private Board board;
    private Hand hand;
    private Scanner scanner;
    private String stats;

    public GwentPlayer(int playerNo, Board board) {
        this.playerNo = playerNo;
        this.board = board;
        this.hand = new Hand();
        this.hand.createHand();
        this.stats = hand.getCardCount() + ":" + hand.getWeatherCount();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Update board state
     */
    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Used for testing, tells the ratio of point and weathercards
     */
    @Override
    public void printStats() {
        System.out.println(stats);
    }

    /**
     * handles turn of the human player
     */
    @Override
    public Board play() {
        if (hand.getHandSize() > 0) {
            while (true) {
                int command = askForCommand();
                if (command == 0) {
                    skipRound();
                    break;
                } else if (checkIfCard(command)) {
                    this.board.playCard(playerNo, this.hand.getCardwithIndex(command - 1));
                    break;
                } else if (checkIfWeather(command)) {
                    this.board.setWeather(this.hand.getWeatherWithIndex((command - this.hand.getCardCount()) - 1));
                    break;
                } else {
                    System.out.println("that's not a card");
                }
            }
        } else {
            board.setSkip(playerNo, true);
        }
        return this.board;
    }

    /**
     * Skips this round for the player
     */
    @Override
    public void skipRound() {
        this.board.setSkip(playerNo, true);
    }

    /**
     * Text representation of players hand
     */
    @Override
    public void printHand() {
        System.out.println(this.hand.toString());
    }

    /**
     * Asks what card the player would like to play
     */
    private int askForCommand() {
        System.out.println("Give card number, zero skips");
        int command = Integer.parseInt(scanner.nextLine());
        return command;
    }

    /**
     * checks if the given number indicates card
     */
    private boolean checkIfCard(int cardNo) {
        return cardNo <= this.hand.getCardCount();
    }

    /**
     * checks if the given number indicates weather
     */
    private boolean checkIfWeather(int cardNo) {
        return cardNo > this.hand.getCardCount() && cardNo <= this.hand.getHandSize();
    }

}
