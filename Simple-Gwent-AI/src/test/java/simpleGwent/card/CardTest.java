package simpleGwent.card;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class CardTest {
    
    Card card;
    
    @Before
    public void setUp() {
        card = new Card(3, 1);
    }
    
    @Test
    public void returnsRightValueWhenNotAffected() {
        assertEquals(card.getValue(), 3);
    }
    
    @Test
    public void returns1WhenAffectedByWeather() {
        card.setAffected(true);
        assertEquals(card.getValue(), 1);
    }
    
    @Test
    public void returnsTrueValueEvenIfAffectedByWeather() {
        card.setAffected(true);
        assertEquals(card.getValueWithoutWeather(), 3);
    }
    
    @Test
    public void returnsRightRow() {
        assertEquals(card.getRow(), 1);
    }
    
    @Test
    public void returnsProperToString() {
        assertEquals(card.toString(), "3/1");
    }
    
}
