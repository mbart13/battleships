package battleships.controllers;

import battleships.models.Ai;
import battleships.models.Ocean;
import battleships.view.View;

import java.util.Scanner;

public class GameController {
    private Ocean ocean1;
    private String playerOneName;
    private Ai computer1;
    private Ocean ocean2;
    private String playerTwoName;
    private Ai computer2;
    private String coordsPattern = "[a-jA-J][1-9][0]*";
    private Scanner scan;
    private View view;
    private boolean isRunning = true;
    private String winner;

    public GameController(Ocean ocean1, String player1, Ocean ocean2, String player2) {
        this.ocean1 = ocean1;
        this.ocean2 = ocean2;
        this.playerOneName = player1;
        this.playerTwoName = player2;
        if (player1.equals("easy") || player1.equals("medium")) {
            this.computer1 = new Ai(player1);
        }
        if (player2.equals("easy") || player2.equals("medium") ) {
            this.computer2 = new Ai(player2);
        }
    }

    public GameController(Ocean ocean1, Ocean ocean2) {
        this(ocean1, "Player 1", ocean2, "Player 2");
    }

    public void run() {
        initialise();

        while (this.isRunning) {
            runTurn(ocean1, ocean2, true);
            if (isRunning)
                runTurn(ocean2, ocean1, false);
        }

        view.displayEndScreen(winner, ocean1.getMap(), ocean2.getMap());
        scan.nextLine();
    }

    void initialise() {
        scan = new Scanner(System.in);
        view = new View();
    }

    void runTurn(Ocean oceanOwner, Ocean oceanOppo, boolean isP1Turn) {
        boolean isTurnOver = false;
        String input;
        String playerName;
        if (isP1Turn)
            playerName = playerOneName;
        else
            playerName = playerTwoName;

        if (!isAiTurn(isP1Turn) && (computer1 == null && computer2 == null)){  
            view.clearDisplay();
            view.printMessage(playerName + ": Press enter to start your turn.");
            scan.nextLine();
        }

        while (!isTurnOver) {
            if (isSimulation() && isP1Turn) view.displaySimScreen(oceanOwner.getMap(), oceanOppo.getMap());
            else if (isSimulation() && !isP1Turn)  view.displaySimScreen(oceanOppo.getMap(), oceanOwner.getMap());
            else view.displayGameScreen(oceanOwner.getMap(), oceanOppo.getMap());

            input = getTarget(oceanOppo, isP1Turn);

            if (isSimulation() && isP1Turn) view.displaySimScreen(oceanOwner.getMap(), oceanOppo.getMap());
            else if (isSimulation() && !isP1Turn)  view.displaySimScreen(oceanOppo.getMap(), oceanOwner.getMap());

            if (!input.matches(this.coordsPattern)) {
                view.messageStream.add("Please enter valid coordinates (example: B5).");
            } else {
                int[] coords = oceanOppo.getSquareLocation(input);
                if (oceanOppo.getMap().get(coords[0]).get(coords[1]).getIsHit()){
                    view.messageStream.add("You've already hit this square!");
            } else {
                oceanOppo.getMap().get(coords[0]).get(coords[1]).markHit();
                
                if (!oceanOppo.getMap().get(coords[0]).get(coords[1]).hasShip()){
                    isTurnOver = true;
                    view.messageStream.add(playerName + " has hit the " + input + " and it's a miss.");
            } else {
                oceanOppo.isShipSunk(input);
                if (oceanOppo.getMap().get(coords[0]).get(coords[1]).getShip().getIsSunk()){
                    view.messageStream.add(playerName + " has sunk the " + oceanOppo.getMap().get(coords[0]).get(coords[1]).getShip().getShipName() + "!");
                } else view.messageStream.add(playerName + " has hit the " + oceanOppo.getMap().get(coords[0]).get(coords[1]).getShip().getShipName() + "!");
            }
            }
            }
        if (oceanOppo.areAllSunk()){
            isRunning = false;
            winner = playerName;
            isTurnOver = true;
        }else if (isTurnOver){
            if (!isAiTurn(isP1Turn)){            
                view.displayGameScreen(oceanOwner.getMap(), oceanOppo.getMap(), true);
                scan.nextLine();
            }
        }
        }
    }

    private String getTarget(Ocean targetOcean, boolean isP1Turn){
        String target;
        if (isP1Turn && computer1 != null){
            target = computer1.getCoordinatesToShoot(targetOcean);
        } else if (!isP1Turn && computer2 != null){
            target = computer2.getCoordinatesToShoot(targetOcean);
        }else target = scan.nextLine();
        return target;
    }

    private boolean isAiTurn(boolean isP1Turn){
        boolean result = false;
        if ((isP1Turn && computer1 != null) || (!isP1Turn && computer2 != null)) result = true;
        return result;
    }

    private boolean isSimulation(){
        if (!(computer1 == null) && !(computer2 == null)) return true;
        return false;
    }
}