package simpleGwent.main;

import java.util.Scanner;
import simpleGwent.game.Game;

public class Main {
    
    public static void main(String[] args) {
        Scanner lukija = new Scanner(System.in);
        System.out.println("Select gamemode:");
        System.out.println("1. player vs. AI");
        System.out.println("2. AI vs. AI");
        String mode = lukija.nextLine();
        int number = Integer.parseInt(mode);
        Game game = new Game(number);
        game.start();
    }
    
}
