package battleships.controllers;

import battleships.models.Ocean;
import battleships.models.Ship;
import battleships.util.Util;
import battleships.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SetupController {

    public static List<Ocean> run(String player1, String player2) {

        List<Ocean> oceans = new ArrayList<>();
        String[] players = {player1, player2};
        View view = new View();
        Map<String, String> shipsDict = Util.createShipsDict();

        String coords = "";
        String position = "";
        String coordsPattern = "[a-jA-J][1-9][0]*";
        String yesOrNoPattern = "(?i)[yn]";

        for (int i = 0; i < players.length; i++) {
            int counter = 0;
            
            if (players[i].equals("easy") || players[i].equals("medium") || players[i].equals("hard")) {
                Ocean ocean  = Util.aiPlaceShipsOnBoard();
                oceans.add(ocean);                
            } else {
                Ocean ocean = new Ocean();
                view.clearDisplay();
                view.displayBoard(ocean.getMap());
    
                view.printMessage("\n" + players[i] +": random board generation? [Y/N]: ");
                String decision = Util.validateUserInput(yesOrNoPattern, position, "Type Y or N: ");
                if (decision.toUpperCase().equals("Y")) {
                    ocean  = Util.aiPlaceShipsOnBoard();
                    oceans.add(ocean);
                    Util.pressEnterToContinue("\n" + players[i] + ": press enter to continue ");
                } else {
                    ocean = new Ocean();
                    for (String key: shipsDict.keySet()) {                
                        view.clearDisplay();
                        view.displayBoard(ocean.getMap());

                        boolean validInput = false;
            
                        while (!validInput) {
                            view.printMessage("\n" + players[i] + ": enter " + key + " coordinates (" + shipsDict.get(key) + "): ");
                            coords = Util.validateUserInput(coordsPattern, coords, "Enter valid coordinates: ");
            
                            view.printMessage("Is horizontal? [Y/N]: ");
                            position = Util.validateUserInput(yesOrNoPattern, position, "Type Y or N: ");            
                            position = Util.getPositionFromInput(position);
                            Ship ship = new Ship(key, position);
            
                            if (ocean.checkIfWithinBounds(ship, coords) && ocean.checkIfSpaceFreeForShip(ship, coords)) {
                                ocean.getShips().add(ship);
                                validInput = true;
                            } else {
                                System.out.println("Ships cannot be placed outside the board, they cannot overlap or touch");
                            }
                        }
            
                        ocean.placeShipOnBoard(ocean.getShips().get(counter), coords);         
                        counter++;
                    }
                    oceans.add(ocean);
                    view.clearDisplay();
                    view.displayBoard(ocean.getMap());
                    Util.pressEnterToContinue("\n" + players[i] + ": press enter to continue ");
                }
            }
        }

        return oceans;
    }
}