package battleships.util;

import battleships.models.Ocean;
import battleships.models.Ship;
import battleships.view.View;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;


public class Util {

    private static Scanner scan = new Scanner(System.in);

    public static Map<String, String> createShipsDict() {
        Map<String, String> shipsDict = new LinkedHashMap<>();
        String[] shipTypes = {"Carrier", "Battleship", "Cruiser", "Submarine", "Destroyer"};
        String[] description = {"occupies 5 squares", "occupies 4 squares", "occupies 3 squares", "occupies 3 squares", "occupies 2 squares"};

        for (int i = 0; i < shipTypes.length; i++) {
            shipsDict.put(shipTypes[i], description[i]);
        }
        return shipsDict;
    }

    public static String getPositionFromInput(String input) {
        if (input.toUpperCase().equals("Y")) {
            return "horizontal";
        } else {
            return "vertical";
        }
    }

    public static String validateUserInput(String pattern, String input, String message) {
        input = scan.nextLine();  

        while (!input.matches(pattern)) {
            System.out.print(message);
            input = scan.nextLine();  
        } 
        return input;
    }

    public static void pressEnterToContinue(String message)  { 
        System.out.print(message);
        try {
            System.in.read();
        }
        catch(Exception e) {               
        }  
    }

    public static Ocean aiPlaceShipsOnBoard () {

        Map<String, String> shipsDict = createShipsDict();
        Ocean ocean = new Ocean();
        View view = new View();
        Random random = new Random();
        view.clearDisplay();
        view.displayBoard(ocean.getMap());
        int counter = 0;

        for (String key: shipsDict.keySet()) {            

            String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
            String[] choicePosition = {"horizontal", "vertical"};
            
            String letter = letters[random.nextInt(letters.length - 1)];
            int number = getRandomNumberFromRange(1, 10);
            String position = choicePosition[random.nextInt(2)];
            Ship ship = new Ship(key, position);
            String coords = letter + number;
            boolean validCoords = false;
            while (!validCoords) {
                if (ocean.checkIfWithinBounds(ship, coords) && ocean.checkIfSpaceFreeForShip(ship, coords)) {
                    ocean.getShips().add(ship);
                    ocean.placeShipOnBoard(ocean.getShips().get(counter), coords);   
                    view.clearDisplay();
                    view.displayBoard(ocean.getMap());
                    counter++;
                    validCoords = true;
                } else {
                    letter = letters[random.nextInt(letters.length - 1)];
                    number = getRandomNumberFromRange(1, 10);
                    coords = letter + number;
                }
            }
        }

        return ocean;
    }

    public static int getRandomNumberFromRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}