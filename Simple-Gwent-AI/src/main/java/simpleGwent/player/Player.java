package simpleGwent.player;

import simpleGwent.board.Board;

public interface Player {
    
    Board play();
    void setBoard(Board board);
    void skipRound();
    void printHand();
    void printStats();
}
