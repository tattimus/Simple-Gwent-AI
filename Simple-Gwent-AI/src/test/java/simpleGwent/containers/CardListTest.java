package simpleGwent.containers;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import simpleGwent.card.Card;

public class CardListTest {
    
    CardList cards;
    
    @Before
    public void setUp() {
        cards = new CardList();
    }
    
    @Test
    public void newListIsEmpty() {
        assertEquals(cards.size(), 0);
    }
    
    @Test
    public void cardCanBeAdded() {
        cards.add(new Card(3, 1));
        assertEquals(cards.size(), 1);
        assertEquals(cards.get(0).getValue(), 3);
        assertEquals(cards.get(0).getRow(), 1);
    }
    
    @Test
    public void cardCanBeRemoved() {
        cards.add(new Card(3, 1));
        cards.add(new Card(2, 2));
        assertEquals(cards.size(), 2);
        cards.remove(0);
        assertEquals(cards.size(), 1);
    }
    
    @Test
    public void listCanBeEmptied() {
        cards.add(new Card(3, 1));
        cards.add(new Card(2, 3));
        assertEquals(cards.size(), 2);
        cards.empty();
        assertEquals(cards.size(), 0);
    }
    
}
