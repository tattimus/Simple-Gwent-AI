package simpleGwent.card;

public class weatherCard {

    private int row;
    private char mark;

    /**
     * Create card with correct symbol and row
     */
    public weatherCard(int value) {
        if (value == 11) {
            this.row = 1;
            this.mark = '*';
        } else if (value == 12) {
            this.row = 2;
            this.mark = '#';
        } else if (value == 13) {
            this.row = 3;
            this.mark = ';';
        } else {
            this.row = 0;
            this.mark = '^';
        }
    }

    /**
     * Return the row where this weather can be played
     */
    public int getRow() {
        return this.row;
    }

    @Override
    public String toString() {
        return this.mark + "";
    }

}
