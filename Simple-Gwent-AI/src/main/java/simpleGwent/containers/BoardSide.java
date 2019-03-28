package simpleGwent.containers;

import simpleGwent.card.Card;

public class BoardSide {
    
    private CardList row1;
    private CardList row2;
    private CardList row3;
    
    public BoardSide() {
        this.row1 = new CardList();
        this.row2 = new CardList();
        this.row3 = new CardList();
    }
    
    public CardList getRow(int row) {
        if (row == 1) {
            return row1;
        } else if (row == 2) {
            return row2;
        } else {
            return row3;
        }
    }
    
    public void addToRow(Card card) {
        if (card.getRow() == 1) {
            row1.add(card);
        } else if (card.getRow() == 2) {
            row2.add(card);
        } else {
            row3.add(card);
        }
    }
    
    public void resetSide() {
        row1.empty();
        row2.empty();
        row3.empty();
    }
    
}
