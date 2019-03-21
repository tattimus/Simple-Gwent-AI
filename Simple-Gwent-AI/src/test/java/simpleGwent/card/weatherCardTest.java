package simpleGwent.card;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class weatherCardTest {
    
    weatherCard card1;
    weatherCard card2;
    weatherCard card3;
    weatherCard card4;
    
    @Before
    public void setUp() {
        card1 = new weatherCard(11);
        card2 = new weatherCard(12);
        card3 = new weatherCard(13);
        card4 = new weatherCard(14);
    }
    
    @Test
    public void returnsCorrectRow() {
        assertEquals(card1.getRow(), 1);
        assertEquals(card2.getRow(), 2);
        assertEquals(card3.getRow(), 3);
        assertEquals(card4.getRow(), 0);
    }
    
    @Test
    public void returnsCorrectToString() {
        assertEquals(card1.toString(), "*");
        assertEquals(card2.toString(), "#");
        assertEquals(card3.toString(), ";");
        assertEquals(card4.toString(), "^");
    }
    
}
