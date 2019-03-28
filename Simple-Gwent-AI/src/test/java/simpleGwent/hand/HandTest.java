package simpleGwent.hand;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import simpleGwent.card.Card;
import simpleGwent.card.weatherCard;

public class HandTest {

    Hand hand;

    @Before
    public void setUp() {
        hand = new Hand();
        hand.addCard(3, 1);
        hand.addCard(10, 3);
        hand.addCard(1, 1);
        hand.addCard(7, 2);
        hand.addCard(5, 2);
        hand.addWeatherCard(11);
        hand.addWeatherCard(14);
    }

    @Test
    public void returnsCorrectHandSize() {
        assertEquals(hand.getHandSize(), 7);
    }

    @Test
    public void returnsCorrectRowPoints() {
        assertEquals(hand.rowPoints(2), 12);
    }

    @Test
    public void returnsCorrectNumberOfRowCards() {
        assertEquals(hand.rowCards(1), 2);
    }
    
    @Test
    public void returnsSmallestCard() {
        Card small = hand.getTheSmallest();
        assertEquals(small.getValue(), 1);
        assertEquals(small.getRow(), 1);
    }
    
    @Test
    public void returnsBiggestCar() {
        Card big = hand.getTheBiggest();
        assertEquals(big.getValue(), 10);
        assertEquals(big.getRow(), 3);
    }
    
    @Test
    public void returnsGreaterOrEqual() {
        Card card = hand.getCardEqualOrGreater(6, 2);
        assertEquals(card.getValue(), 7);
        assertEquals(card.getRow(), 2);
    }
    
    @Test
    public void returnsSmaller() {
        Card card = hand.getCardSmaller(3, 1);
        Card none = hand.getCardSmaller(9, 3);
        assertEquals(card.getValue(), 1);
        assertEquals(card.getRow(), 1);
        assertEquals(none, null);
    }
    
    @Test
    public void returnsRandomCardFromRightRow() {
        Card card = hand.getRandomFromRow(2);
        assertEquals(card.getRow(), 2);
    }
    
    @Test
    public void returnsCorrectWeatherCard() {
        weatherCard card = hand.getWeather(1);
        assertEquals(card.getRow(), 1);
        assertEquals(card.toString(), "*");
    }
    
    @Test
    public void removesCardWithIndex() {
        hand.getCardwithIndex(1);
        assertEquals(hand.getHandSize(), 6);
        assertEquals(hand.getTheBiggest().getValue(), 7);
    }
    
    @Test
    public void removesWeatherCardWithIndex() {
        hand.getWeatherWithIndex(0);
        assertEquals(hand.getHandSize(), 6);
        assertEquals(hand.getWeather(1), null);
    }
}
