package simpleGwent.player;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import simpleGwent.board.Board;
import simpleGwent.card.Card;

public class GwentAITest {

    GwentAI AI;
    Board board;

    @Before
    public void setUp() {
        board = new Board();
        board.playCard(1, new Card(3, 1));
        board.playCard(2, new Card(1, 1));
        board.playCard(1, new Card(1, 2));
        board.playCard(2, new Card(2, 2));
        board.playCard(1, new Card(5, 3));
        board.playCard(2, new Card(8, 3));
        board.addWinToPlayer(1);
        this.AI = new GwentAI(1, board);
        this.AI.resetHand();
        AI.addToHand(5, 1);
        AI.addToHand(1, 2);
        AI.addToHand(9, 3);
        AI.addWeatherToHand(13);
    }

    @Test
    public void roundAdvantageIsCorrect() {
        assertEquals(AI.winningInRounds(), true);
    }

    @Test
    public void pointAdvantageReturnsFalseWhenLoosing() {
        assertEquals(AI.winningInPoints(), false);
    }

    @Test
    public void weatherPlayIsRecommendedWhenItGivesLead() {
        assertEquals(AI.playingWeatherHelps(3), true);
    }

    @Test
    public void weatherPlayIsNotRecommendedWhenItsNegative() {
        assertEquals(AI.playingWeatherHelps(1), false);
    }

    @Test
    public void correctLaneIsRecommendedForThePlay() {
        assertEquals(AI.bestLaneToPlay(), 1);
    }

    @Test
    public void clearingWeatherIsntRecommendedIfDoesntHelp() {
        assertEquals(AI.clearingWeatherHelps(), false);
    }

    @Test
    public void AIIsToldIfItHasWeatherPlay() {
        assertEquals(AI.checkWeatherCards(), true);
    }

    @Test
    public void AIMakesRightPlayIfNoPointCards() {
        AI.resetHand();
        AI.addWeatherToHand(13);
        assertEquals(AI.handHasPointCards(), false);
    }

    @Test
    public void AIMakesRightPlayIfItHasPointCards() {
        assertEquals(AI.handHasPointCards(), true);
    }

    @Test
    public void safePointDifferenceReturnedCorrectly() {
        board = new Board();
        board.playCard(1, new Card(3, 1));
        board.playCard(1, new Card(3, 3));
        board.playCard(1, new Card(1, 2));
        board.playCard(2, new Card(2, 2));
        board.addWinToPlayer(1);
        AI.setBoard(board);
        AI.resetHand();
        AI.addToHand(1, 1);
        assertEquals(AI.safePointDifference(), true);
    }
    
    @Test
    public void handPointsOverPointDifferenceReturnedCorrectly() {
        assertEquals(AI.handPointsGreaterThanPointDifference(), true); 
    }
}
