package simpleGwent.containers;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import simpleGwent.card.weatherCard;

public class WeatherListTest {

    WeatherList cards;

    @Before
    public void setUp() {
        cards = new WeatherList();
    }

    @Test
    public void newListIsEmpty() {
        assertEquals(cards.size(), 0);
    }

    @Test
    public void weatherCanBeAdded() {
        cards.add(new weatherCard(12));
        assertEquals(cards.size(), 1);
        assertEquals(cards.get(0).getRow(), 2);
    }

    @Test
    public void weatherCanBeRemoved() {
        cards.add(new weatherCard(12));
        cards.add(new weatherCard(13));
        assertEquals(cards.size(), 2);
        cards.remove(0);
        assertEquals(cards.size(), 1);
        assertEquals(cards.get(0).getRow(), 3);
    }

    @Test
    public void listCanBeEmptied() {
        cards.add(new weatherCard(12));
        cards.add(new weatherCard(13));
        assertEquals(cards.size(), 2);
        cards.empty();
        assertEquals(cards.size(), 0);
    }

}
