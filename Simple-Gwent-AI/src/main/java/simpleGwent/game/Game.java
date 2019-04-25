package simpleGwent.game;

import simpleGwent.board.Board;
import simpleGwent.player.GwentAI;
import simpleGwent.player.GwentPlayer;
import simpleGwent.player.Player;

public class Game {

    private Board board;
    private Player P1;
    private Player P2;

    public Game(int gameMode) {
        this.board = new Board();
        if (gameMode == 1) {
            this.P1 = new GwentPlayer(1, this.board);
            this.P2 = new GwentAI(2, this.board);
        } else {
            this.P1 = new GwentAI(1, this.board);
            this.P2 = new GwentAI(2, this.board);
        }
    }

    /**
     * games frame, play round -> if winner -> announce winner and end game ->
     * else play next round
     */
    public void start() {
        while (true) {
            round();
            if (weHaveAWinner()) {
                announceWinner();
                break;
            } else {
                resetBoard();
            }
        }
    }

    /**
     * rounds frame, print board -> P1 plays -> P2 plays -> if both skipped,
     * point to winner -> else from start
     */
    private void round() {
        System.out.println("Round starts");
        System.out.println("P1:" + board.roundsWonByPlayer(1) + " P2:" + board.roundsWonByPlayer(2));
        while (!board.playerHasSkipped(1) || !board.playerHasSkipped(2)) {
            P2.printHand();
            board.printBoard();
            P1.printHand();
            System.out.println("");
            if (!board.playerHasSkipped(1)) {
                P1.setBoard(board);
                board = P1.play();
            }
            if (!board.playerHasSkipped(2)) {
                P2.setBoard(board);
                board = P2.play();
            }
        }
        givePointToWinner();
    }

    /**
     * checks if one of the players has won
     */
    private boolean weHaveAWinner() {
        return board.roundsWonByPlayer(1) == 2 || board.roundsWonByPlayer(2) == 2;
    }

    private void announceWinner() {
        P1.printStats();
        P2.printStats();
        if (board.roundsWonByPlayer(1) == 2 && board.roundsWonByPlayer(2) == 2) {
            System.out.println("Game ends in tie !");
        } else if (board.roundsWonByPlayer(1) == 2) {
            System.out.println("Player 1 wins the game !");
        } else {
            System.out.println("Player 2 wins the game !");
        }
        System.out.println("Thank you for playing.");
    }

    private void resetBoard() {
        board.clearBoard();
    }

    /**
     * give point to winner of the round, if tie award both
     */
    private void givePointToWinner() {
        if (board.getPointsOfPlayer(1, true) > board.getPointsOfPlayer(2, true)) {
            System.out.println("P1 wins the round");
            board.addWinToPlayer(1);
        } else if (board.getPointsOfPlayer(2, true) > board.getPointsOfPlayer(1, true)) {
            System.out.println("P2 wins the round");
            board.addWinToPlayer(2);
        } else {
            System.out.println("Tie");
            board.addWinToPlayer(1);
            board.addWinToPlayer(2);
        }
    }

}
