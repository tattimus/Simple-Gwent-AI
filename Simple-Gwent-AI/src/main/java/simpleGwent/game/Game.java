package simpleGwent.game;

import simpleGwent.board.Board;
import simpleGwent.player.GwentAI;
import simpleGwent.player.GwentPlayer;
import simpleGwent.player.Player;

public class Game {
    
    private Board board;
    private Player P1;
    private Player P2;
    private int P1RoundsWon;
    private int P2RoundsWon;
    
    public Game(int gameMode) {
        this.board = new Board();
        this.P1RoundsWon = 0;
        this.P2RoundsWon = 0;
        if (gameMode == 1) {
            this.P1 = new GwentPlayer(1, this.board);
            this.P2 = new GwentAI(2, this.board);
        } else {
            this.P1 = new GwentAI(1, this.board);
            this.P2 = new GwentAI(2, this.board);
        }
    }
    
    public void start() {
        this.board.printBoard();
        this.P1.printHand();
    }
    
}
