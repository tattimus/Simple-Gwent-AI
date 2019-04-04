package simpleGwent.board;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import simpleGwent.card.Card;
import simpleGwent.card.weatherCard;

public class BoardTest {

    Board board;

    @Before
    public void setUp() {
        board = new Board();
        board.playCard(1, new Card(3, 1));
        board.playCard(1, new Card(1, 2));
        board.playCard(1, new Card(7, 3));
        board.playCard(2, new Card(5, 1));
        board.playCard(2, new Card(3, 2));
        board.playCard(2, new Card(10, 3));
        board.setWeather(new weatherCard(11));
    }

    @Test
    public void pointsOfRowWithoutWeatherCorrect() {
        int points1 = board.getPointsOfRow3(1, true);
        int points2 = board.getPointsOfRow3(2, true);
        int points1w = board.getPointsOfRow3(1, false);
        int points2w = board.getPointsOfRow3(2, false);
        assertEquals(points1, 7);
        assertEquals(points2, 10);
        assertEquals(points1, points1w);
        assertEquals(points2, points2w);
    }

    @Test
    public void pointsOfRowWithWeatherCorrect() {
        int points1 = board.getPointsOfRow1(1, true);
        int points2 = board.getPointsOfRow1(2, true);
        int points1w = board.getPointsOfRow1(1, false);
        int points2w = board.getPointsOfRow1(2, false);
        assertEquals(points1, 1);
        assertEquals(points2, 1);
        assertEquals(points1w, 3);
        assertEquals(points2w, 5);
    }

    @Test
    public void pointsOfRowCorrectAfterWeatherChange() {
        int points1 = board.getPointsOfRow2(1, true);
        int points2 = board.getPointsOfRow2(2, true);
        assertEquals(points1, 1);
        assertEquals(points2, 3);
        board.setWeather(new weatherCard(12));
        int points1w = board.getPointsOfRow2(1, true);
        int points2w = board.getPointsOfRow2(2, true);
        assertEquals(points1w, 1);
        assertEquals(points2w, 1);
    }

    @Test
    public void returnsCurrentWeatherState() {
        board.setWeather(new weatherCard(13));
        int[] wet = board.getWeather();
        assertEquals(wet[0], 1);
        assertEquals(wet[1], 0);
        assertEquals(wet[2], 1);
    }

    @Test
    public void playerSkipCanBeSetAndReturnValueIsCorrect() {
        board.setSkip(1, true);
        assertEquals(board.playerHasSkipped(2), false);
        assertEquals(board.playerHasSkipped(1), true);
    }
    
    @Test
    public void returnsSumOfPlayersTotalPoints() {
        assertEquals(board.getPointsOfPlayer(2, true), 14);
        assertEquals(board.getPointsOfPlayer(2, false), 18);
    }
    
    @Test
    public void boardIsCleared() {
        board.clearBoard();
        assertEquals(board.getPointsOfPlayer(1, true), 0);
    }
    
    @Test
    public void roundWinsCanBeAddedAndRetrieved() {
        board.addWinToPlayer(1);
        board.addWinToPlayer(2);
        board.addWinToPlayer(2);
        assertEquals(board.roundsWonByPlayer(1), 1);
        assertEquals(board.roundsWonByPlayer(2), 2);
    }
    
    @Test
    public void numberOfCardsIsReturned() {
        assertEquals(board.getAmountOfCardsInRow(1, 1), 1);
        assertEquals(board.getAmountOfCardsInRow(2, 3), 1);
    }
    
    @Test
    public void weatherCanBeCleared() {
        assertEquals(board.getWeather()[0], 1);
        board.setWeather(new weatherCard(14));
        assertEquals(board.getWeather()[0], 0);
    }
    
    @Test
    public void opponentsPlayerNumberIsGiven() {
        assertEquals(board.getOpponentsNumber(1), 2);
        assertEquals(board.getOpponentsNumber(2), 1);
    }

}
