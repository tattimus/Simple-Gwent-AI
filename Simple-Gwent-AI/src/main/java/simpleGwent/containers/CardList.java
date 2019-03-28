package simpleGwent.containers;

import simpleGwent.card.Card;

public class CardList {

    private Card[] cards;
    private int top;

    public CardList() {
        this.cards = new Card[10];
        this.top = -1;
    }

    public void add(Card card) {
        top++;
        cards[top] = card;
    }

    public Card get(int index) {
        return cards[index];
    }

    public int size() {
        return top + 1;
    }

    /**
     * move cards after the index to the place of its predecessor and top is
     * reduced by one
     */
    public void remove(int index) {
        for (int i = index; i < size(); i++) {
            if (i < top) {
                Card to = cards[i + 1];
                cards[i] = to;
            } else {
                top--;
            }
        }
    }

    public void empty() {
        this.cards = new Card[10];
        this.top = -1;
    }

}
