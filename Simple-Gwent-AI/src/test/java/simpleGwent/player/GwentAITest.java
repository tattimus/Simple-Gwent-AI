package simpleGwent.player;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import simpleGwent.board.Board;
import simpleGwent.card.Card;
import simpleGwent.card.weatherCard;

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
        board.playCard(1, new Card(3, 2));
        AI.setBoard(board);
        assertEquals(AI.playingWeatherHelps(2), false);
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

    @Test
    public void AISkipsifWinningInPointsAndOpponentSkipped() {
        board.playCard(1, new Card(8, 1));
        board.setSkip(2, true);
        AI.setBoard(board);
        AI.playCards();
        assertEquals(board.playerHasSkipped(1), true);
    }

    @Test
    public void AIPlaysWeatherIfInDisadvantageAndItHelps() {
        assertEquals(board.getWeather()[2], 0);
        board = AI.play();
        assertEquals(board.getWeather()[2], 1);
    }

    @Test
    public void correctLaneIsSuggestedWhenWinning() {
        assertEquals(AI.bestLaneToPlayIfWinning(), 3);
    }

    @Test
    public void correctCardPlayedWhenPointAdvantage() {
        board.playCard(1, new Card(3, 1));
        AI.setBoard(board);
        board = AI.play();
        assertEquals(board.getPointsOfPlayer(1, true), 21);
    }

    @Test
    public void AISkipsIfSafeThresWhenWinningInPointsAndRounds() {
        board.playCard(1, new Card(6, 1));
        AI.setBoard(board);
        board = AI.play();
        assertEquals(board.playerHasSkipped(1), true);
    }

    @Test
    public void AIPlaysCardWhenPointAdvantageAndNoWeather() {
        board.playCard(1, new Card(5, 3));
        board.addWinToPlayer(2);
        AI.setBoard(board);
        board = AI.play();
        assertEquals(board.getAmountOfCardsInRow(1, 1), 2);
    }

    @Test
    public void AISkipsIfRoundAdvantageAndPointDiffTooBig() {
        board.playCard(2, new Card(10, 1));
        board = AI.play();
        assertEquals(board.playerHasSkipped(1), true);
    }

    @Test
    public void AIPlaysCardWhenWinningInRoundsButLosingInPoints() {
        board.playCard(2, new Card(1, 3));
        board.playCard(2, new Card(1, 2));
        AI.setBoard(board);
        board = AI.play();
        assertEquals(board.getAmountOfCardsInRow(1, 3), 2);
    }

    @Test
    public void AIPlaysSmallestIfRoundAdAndPointDisAdAndOptimalRowHasWeatherAndOpponentSkipped() {
        board.playCard(2, new Card(1, 3));
        board.playCard(2, new Card(1, 2));
        board.setWeather(new weatherCard(13));
        board.setSkip(2, true);
        AI.setBoard(board);
        board = AI.play();
        assertEquals(board.getAmountOfCardsInRow(1, 2), 2);
    }
    
    @Test
    public void IfLosingInRoundAndPointsPlayCard() {
        board.addWinToPlayer(2);
        AI.setBoard(board);
        board = AI.play();
        assertEquals(board.getAmountOfCardsInRow(1, 1), 2);
    }
    
    @Test
    public void IfNoPossibilityToWinRoundSkipIfWinningInRounds() {
        AI.resetHand();
        AI.addToHand(1, 1);
        board = AI.play();
        assertEquals(board.playerHasSkipped(1), true);
    }
    
    @Test
    public void IfNoPossibilityToWinRoundAndLosingInRoundsPlayBigCard() {
        board.addWinToPlayer(2);
        AI.setBoard(board);
        AI.resetHand();
        AI.addToHand(1, 1);
        board = AI.play();
        assertEquals(board.getAmountOfCardsInRow(1, 1), 2);
    }
}
