package simpleGwent.player;

import java.util.Scanner;
import simpleGwent.board.Board;
import simpleGwent.hand.Hand;

public class GwentPlayer implements Player {

    private int playerNo;
    private Board board;
    private Hand hand;
    private Scanner scanner;

    public GwentPlayer(int playerNo, Board board) {
        this.playerNo = playerNo;
        this.board = board;
        this.hand = new Hand();
        this.hand.createHand();
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }
    
    @Override
    public void printStats() {
        System.out.println("");
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

    @Override
    public void skipRound() {
        this.board.setSkip(playerNo, true);
    }

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
