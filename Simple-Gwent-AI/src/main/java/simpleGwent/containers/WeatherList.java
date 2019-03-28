package simpleGwent.containers;

import simpleGwent.card.weatherCard;

public class WeatherList {

    private weatherCard[] cards;
    private int top;

    public WeatherList() {
        this.cards = new weatherCard[10];
        this.top = -1;
    }

    public void add(weatherCard card) {
        top++;
        cards[top] = card;
    }

    public weatherCard get(int index) {
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
                weatherCard next = cards[i + 1];
                cards[i] = next;
            } else {
                top--;
            }
        }
    }

    public void empty() {
        this.cards = new weatherCard[10];
        this.top = -1;
    }

}
