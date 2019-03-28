package simpleGwent.containers;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import simpleGwent.card.Card;

public class BoardSideTest {

    BoardSide side;

    @Before
    public void setUp() {
        side = new BoardSide();
    }

    @Test
    public void newSideIsEmpty() {
        assertEquals(side.getRow(1).size(), 0);
        assertEquals(side.getRow(2).size(), 0);
        assertEquals(side.getRow(3).size(), 0);
    }

    @Test
    public void cardCanBeAddedToRow() {
        side.addToRow(new Card(3, 1));
        side.addToRow(new Card(2, 2));
        side.addToRow(new Card(1, 3));
        assertEquals(side.getRow(1).get(0).getValue(), 3);
        assertEquals(side.getRow(2).get(0).getValue(), 2);
        assertEquals(side.getRow(3).get(0).getValue(), 1);
        assertEquals(side.getRow(1).size(), 1);
        assertEquals(side.getRow(2).size(), 1);
        assertEquals(side.getRow(3).size(), 1);
    }

    @Test
    public void sideCanBeReset() {
        side.addToRow(new Card(3, 1));
        assertEquals(side.getRow(1).size(), 1);
        side.resetSide();
        assertEquals(side.getRow(1).size(), 0);
    }

}
