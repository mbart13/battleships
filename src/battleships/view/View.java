package battleships.view;

import battleships.models.Square;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class View {
    private static final int SIM_PERIOD = 300;
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";
    public ArrayList<String> messageStream;

    public View(){
        messageStream = new ArrayList<String>();
    }

    public void displayBoard(ArrayList<ArrayList<Square>> map){
        displayBoard(map, true, 0);
    }

    public void clearDisplay(){
        System.out.print("\033[H\033[2J");
        System.out.print("\033[H\033[2J");
    }

    public void displayBoard(ArrayList<ArrayList<Square>> map, boolean forOwner, int offset){
        System.out.print("\033[0;" + offset + "H");
        System.out.println("   A B C D E F G H I J");
        int i = 1;
        for (ArrayList<Square> row : map) {
            System.out.print("\033[" + (i+1) + ";" + offset + "H");
            if (i < 10){
                System.out.print(" " + i + " ");
            }else System.out.print(i + " ");
            
            for (Square square : row) {
                if (square.hasShip() && square.getShip().getIsSunk()) System.out.print(RED + square.getIconSquare(forOwner) + " " + RESET);
                else System.out.print(square.getIconSquare(forOwner) + " ");
            }
            System.out.println();
            i++;
        }
    }

    public void printMessage(String message){
        System.out.print(message);
    }

    public void displayGameScreen(ArrayList<ArrayList<Square>> mapOwner,
                           ArrayList<ArrayList<Square>> mapOpponent,
                           boolean isTurnOver){
        clearDisplay();
        displayBoard(mapOwner);
        displayBoard(mapOpponent, false, 30);
        if (!isTurnOver) {
            printMessage("\nEnter coordinates to attack: \n\n");
        } else {
            printMessage("\nPress enter to end this turn.\n\n");
        }
        trimMessageStream(6);


        printMessageStream();

        System.out.print("\033[13;30H");
        
    }

    public void displayGameScreen(ArrayList<ArrayList<Square>> mapOwner,
                           ArrayList<ArrayList<Square>> mapOpponent){
                            displayGameScreen(mapOwner, mapOpponent, false);
                            }

    public void displayOptions(ArrayList<String> options){
        int i = 1;
        for (String option : options) {
            if (i<10){
                System.out.print(i + ". ");
            }else System.out.print(i + ".");
            System.out.println(option);
        }
    }

    public void displaySetup(ArrayList<ArrayList<Square>> map,
                      String shipname,
                      String question){
        clearDisplay();
        displayBoard(map);
        printMessage("\n");
        printMessage(shipname + ":\n");
        printMessage(question + ":\n");
        printMessageStream();
        trimMessageStream(0);
        System.out.print("\033[13" + (question.length()+2) + "H"); // this might require calibration
    }

    public void trimMessageStream(int limit){
        /**
         * Reduces number of element in messageStream
         * @param int limit Number of messages that will be left in messageStream
         */
        while (this.messageStream.size() > limit){
            this.messageStream.remove(0);
        }
    }

    public void printMessageStream(){
        /**
         * Prints all messages from messageStream line by line, starting with latest
         */
        int i = messageStream.size() - 1;
        while (i >= 0){
            System.out.println(messageStream.get(i));
            i--;
        }
    }

    public void displayEndScreen(String winner,
                          ArrayList<ArrayList<Square>> mapOwner,
                          ArrayList<ArrayList<Square>> mapOpponent){
        clearDisplay();
        displayBoard(mapOwner);
        displayBoard(mapOpponent, true, 30);
        printMessage("\nThe winner is " + winner + "\n");
        printMessage("Press enter to return to main menu.");
    }

    public void displaySimScreen(ArrayList<ArrayList<Square>> mapP1,
                          ArrayList<ArrayList<Square>> mapP2){
                            clearDisplay();
                            displayBoardSim(mapP1, true, 0);
                            displayBoardSim(mapP2, true, 30);
                            System.out.println();
                            trimMessageStream(6);
                            printMessageStream();
                            try {
                                TimeUnit.MILLISECONDS.sleep(SIM_PERIOD);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            }

    public void displayBoardSim(ArrayList<ArrayList<Square>> map, boolean forOwner, int offset){
        System.out.print("\033[0;" + offset + "H");
        System.out.println("   A B C D E F G H I J");
        int i = 1;
        for (ArrayList<Square> row : map) {
            System.out.print("\033[" + (i+1) + ";" + offset + "H");
            if (i < 10){
                System.out.print(" " + i + " ");
            }else System.out.print(i + " ");
            
            for (Square square : row) {
                if (square.hasShip() && square.getShip().getIsSunk()) System.out.print(RED + square.getIconSquare(forOwner) + " " + RESET);
                else if (square.getIsGoodTarget()) System.out.print(GREEN + square.getIconSquare(forOwner) + " " + RESET);
                else System.out.print(square.getIconSquare(forOwner) + " ");
            }
            System.out.println();
            i++;
        }
    }
}