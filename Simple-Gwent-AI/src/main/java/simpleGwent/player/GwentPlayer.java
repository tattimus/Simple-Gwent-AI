package simpleGwent.player;

import simpleGwent.board.Board;
import simpleGwent.hand.Hand;

public class GwentPlayer implements Player {

    private int playerNo;
    private Board board;
    private Hand hand;
    
    public GwentPlayer(int playerNo, Board board) {
        this.playerNo = playerNo;
        this.board = board;
        this.hand = new Hand();
        this.hand.createHand();
    }
    
    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public Board play() {
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

}
