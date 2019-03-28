package simpleGwent.hand;

import simpleGwent.card.Card;
import simpleGwent.card.weatherCard;
import java.util.Random;
import simpleGwent.containers.CardList;
import simpleGwent.containers.WeatherList;

public class Hand {

    private WeatherList wcards;
    private CardList cards;
    private int handSize;

    public Hand() {
        this.wcards = new WeatherList();
        this.cards = new CardList();
        this.handSize = 0;
    }

    /**
     * Create random hand of cards, values ten and below create normal cards,
     * eleven and above create weather cards.
     */
    public void createHand() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int value = random.nextInt(14) + 1;
            if (value > 10) {
                this.addWeatherCard(value);
            } else {
                int row = random.nextInt(3) + 1;
                this.addCard(value, row);
            }
        }
    }

    public int getHandSize() {
        return this.handSize;
    }
    
    public int getCardCount() {
        return this.cards.size();
    }
    
    public int getWeatherCount() {
        return this.wcards.size();
    }

    public void addCard(int points, int row) {
        Card card = new Card(points, row);
        this.cards.add(card);
        this.handSize += 1;
    }

    public void addWeatherCard(int value) {
        weatherCard card = new weatherCard(value);
        this.wcards.add(card);
        this.handSize += 1;
    }

    public int rowPoints(int row) {
        int points = 0;
        for (int i = 0; i < this.cards.size(); i++) {
            if (this.cards.get(i).getRow() == row) {
                points += this.cards.get(i).getValue();
            }
        }
        return points;
    }

    public int rowCards(int row) {
        int amount = 0;
        for (int i = 0; i < this.cards.size(); i++) {
            if (this.cards.get(i).getRow() == row) {
                amount++;
            }
        }
        return amount;
    }

    /**
     * returns first card that fits the description, bigger or equal and from
     * specified row
     */
    public Card getCardEqualOrGreater(int points, int row) {
        Card returned = null;
        int i = 0;
        while (i < this.cards.size()) {
            if (this.cards.get(i).getValue() >= points && this.cards.get(i).getRow() == row) {
                returned = this.cards.get(i);
                this.cards.remove(i);
                this.handSize--;
                return returned;
            }
            i++;
        }
        return returned;
    }

    /**
     * returns first card that is smaller than first parameter and from the same
     * row as second parameter
     */
    public Card getCardSmaller(int points, int row) {
        Card returned = null;
        int i = 0;
        while (i < this.cards.size()) {
            if (this.cards.get(i).getValue() < points && this.cards.get(i).getRow() == row) {
                returned = this.cards.get(i);
                this.cards.remove(i);
                this.handSize--;
                return returned;
            }
            i++;
        }
        return returned;
    }

    public Card getTheBiggest() {
        Card biggest = this.cards.get(0);
        int biggestIndex = 0;
        for (int i = 0; i < this.cards.size(); i++) {
            if (this.cards.get(i).getValue() > biggest.getValue()) {
                biggest = this.cards.get(i);
                biggestIndex = i;
            }
        }
        this.cards.remove(biggestIndex);
        this.handSize--;
        return biggest;
    }

    public Card getTheSmallest() {
        Card smallest = this.cards.get(0);
        int smallestIndex = 0;
        for (int i = 0; i < this.cards.size(); i++) {
            if (this.cards.get(i).getValue() < smallest.getValue()) {
                smallest = this.cards.get(i);
                smallestIndex = i;
            }
        }
        this.cards.remove(smallestIndex);
        this.handSize--;
        return smallest;
    }

    /**
     * returns first card found that goes to specified row
     */
    public Card getRandomFromRow(int row) {
        Card returned = null;
        for (int i = 0; i < this.cards.size(); i++) {
            if (this.cards.get(i).getRow() == row) {
                returned = this.cards.get(i);
                this.cards.remove(i);
                this.handSize--;
                return returned;
            }
        }
        return returned;
    }

    public weatherCard getWeather(int row) {
        weatherCard card = null;
        int i = 0;
        while (i < this.wcards.size()) {
            if (this.wcards.get(i).getRow() == row) {
                card = this.wcards.get(i);
                this.wcards.remove(i);
                this.handSize--;
                return card;
            }
            i++;
        }
        return card;
    }

    public Card getCardwithIndex(int index) {
        Card card = this.cards.get(index);
        this.cards.remove(index);
        this.handSize--;
        return card;
    }

    public weatherCard getWeatherWithIndex(int index) {
        weatherCard wc = wcards.get(index);
        this.wcards.remove(index);
        this.handSize--;
        return wc;
    }

    @Override
    public String toString() {
        String returned = "|";
        for (int i = 0; i < this.cards.size(); i++) {
            returned += this.cards.get(i).toString() + "|";
        }
        for (int i = 0; i < this.wcards.size(); i++) {
            returned += this.wcards.get(i).toString() + "|";
        }
        return returned;
    }
}
