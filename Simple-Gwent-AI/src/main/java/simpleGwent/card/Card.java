package simpleGwent.card;

public class Card {

    private int points;
    private int row;
    private boolean affected;

    public Card(int points, int row) {
        this.points = points;
        this.row = row;
        this.affected = false;
    }

    /**
     * Returned value is dependent on the weather situation on the board
     */
    public int getValue() {
        if (this.affected) {
            return 1;
        } else {
            return this.points;
        }
    }

    /**
     * Gives cards value without weather affecting it
     */
    public int getValueWithoutWeather() {
        return this.points;
    }

    public int getRow() {
        return this.row;
    }

    /**
     * Affected is the indicator whether cards line is under weather
     */
    public void setAffected(boolean value) {
        this.affected = value;
    }

    @Override
    public String toString() {
        return this.getValue() + "";
    }

}
