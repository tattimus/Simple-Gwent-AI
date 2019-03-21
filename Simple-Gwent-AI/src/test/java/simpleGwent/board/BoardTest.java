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
        board.toggleWeatherOfRow1(true);
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
        board.toggleWeatherOfRow2(true);
        int points1w = board.getPointsOfRow2(1, true);
        int points2w = board.getPointsOfRow2(2, true);
        assertEquals(points1w, 1);
        assertEquals(points2w, 1);
    }

    @Test
    public void returnsCurrentWeatherState() {
        board.setWeather(new weatherCard(13));
        board.toggleWeatherOfRow3(true);
        int[] wet = board.getWeather();
        assertEquals(wet[0], 1);
        assertEquals(wet[1], 0);
        assertEquals(wet[2], 1);
    }

    @Test
    public void playerSkipCanBeSetAndReturnValueIsCorrect() {
        board.setSkip(1, true);
        assertEquals(board.opponentHasSkipped(2), true);
        assertEquals(board.opponentHasSkipped(1), false);
    }

}
