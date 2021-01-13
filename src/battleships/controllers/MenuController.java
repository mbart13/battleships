package battleships.controllers;

import battleships.models.Ocean;
import battleships.models.Ship;
import battleships.util.Util;
import battleships.view.View;

import java.util.List;
import java.util.Scanner;

public class MenuController {
    private static View view = new View();
    private static Scanner scan = new Scanner(System.in);

    public static void menu() {
        boolean gameOn = true;

        while(gameOn){
            view.clearDisplay();
            printMenu();

            System.out.print("Choose one of the options: ");
            int userInput = getUserInput();
            
            if (userInput == 1) {
                view.clearDisplay();
                System.out.print("Enter name of player one: ");
                scan.nextLine();
                String playerOneName = scan.nextLine();
                view.clearDisplay();
                System.out.print("Enter name of player two: ");
                String playerTwoName = scan.nextLine();
                List<Ocean> oceans = SetupController.run(playerOneName, playerTwoName);
                GameController game = new GameController(oceans.get(0), playerOneName, oceans.get(1), playerTwoName);
                game.run();
            } else if (userInput == 2) {
                printRules(view);
            } else if (userInput == 3) {
                gameOn = false;
            } else if (userInput == 0) {
                runTestGame();
            }
        }
    }

    public static void printMenu() {

        String[] options = {" New game", " Help", " Exit"};
        System.out.println("------------------");
        System.out.println("    BATTLESHIP");
        System.out.println("------------------");
        for(int index = 0; index < options.length; index++){
            System.out.println("   " + (index + 1) + ". " + options[index]);
        }
        System.out.println("------------------");
    }

    static int getUserInput(){

        while(!scan.hasNextInt()) {
            System.out.println("Wrong input format");
            scan.next();
        }
        int input = scan.nextInt();

        return input;
    }

    static void printRules(View view){
        view.clearDisplay();
        String[] rules = {"                 ***RULES***\n\n1. Each player's fleet contains 5 different ships:\n -Carrier (5 holes)\n -Battleship (4 holes)\n -Cruiser (3 holes)\n -Submarine (3 holes)\n -Destroyer (2 holes) \n",
                          "2. Place your fleet of 5 ships on the ocean grid. Rules for placing ships: \n -Place each ship in any horizontal or vertical position but not diagonally",
                          " -Don't place a ship so that any part of it overlaps letters, numbers, the edge of the grid or another ship\n",
                          "3. Decide who will start. You and your opponent will alternate turns, calling out one shot per turn to try to hit each other's ships. If you hit you get additional shot.\n",
                          "4. Once all the squares in any one ship are marked hit, the ship will sink. If you are the first player to sink your opponent's entire fleet of 5 ships, you win the game.\n"};
        
        for(int index = 0; index < rules.length; index++){
            System.out.println(rules[index]);
        }

        Util.pressEnterToContinue("Press enter to return to the main menu ");
        
    }

    private static void runTestGame(){
        view.clearDisplay();
        System.out.print("Enter name of player one: ");
        scan.nextLine();
        String playerOneName = scan.nextLine();
        view.clearDisplay();
        System.out.print("Enter name of player two: ");
        String playerTwoName = scan.nextLine();

        Ocean ocean1 = new Ocean();
        Ship shipA = new Ship("Carrier", "vertical");
        ocean1.getShips().add(shipA);
        ocean1.placeShipOnBoard(ocean1.getShips().get(0), "A1");
        Ship shipB = new Ship("Submarine", "vertical");
        ocean1.getShips().add(shipB);
        ocean1.placeShipOnBoard(ocean1.getShips().get(1), "C1");

        Ocean ocean2 = new Ocean();
        Ship shipC = new Ship("Carrier", "vertical");
        ocean2.getShips().add(shipC);
        ocean2.placeShipOnBoard(ocean2.getShips().get(0), "B1");
        Ship shipD = new Ship("Submarine", "vertical");
        ocean2.getShips().add(shipD);
        ocean2.placeShipOnBoard(ocean2.getShips().get(1), "D1");

        GameController game = new GameController(ocean1, playerOneName, ocean2, playerTwoName);
        game.run();
    }
}