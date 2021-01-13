package battleships.models;

import java.util.ArrayList;

public class Ocean {
    private ArrayList<ArrayList<Square>> map;
    private ArrayList<Ship> ships;
    final private int WIDTH = 10;
    final private int HEIGHT = 10;

    public Ocean(){
        this.map = new ArrayList<>();
        this.ships = new ArrayList<>();
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

        for (int i=0; i<HEIGHT; i++){
            map.add(new ArrayList<Square>());
        }

        for (int i=0; i < WIDTH; i++){
            for (String letter : letters) {
                map.get(i).add(new Square(letter + (i + 1)));
            }
        }
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public void placeShipOnBoard(Ship ship, String coords) {

        int[] squareLocation = getSquareLocation(coords);
        int x = squareLocation[0];
        int y = squareLocation[1];

        ship.setCoordX(x);
        ship.setCoordY(y);

        if (ship.getPosition().equals("vertical")) {
            for (int i = 0; i<ship.getLength(); i++) {
                this.map.get(x + i).get(y).setShip(ship);                
            }
        }
        else {
            for (int i = 0; i<ship.getLength(); i++){
                this.map.get(x).get(y + i).setShip(ship);
            }
        }
    }

    public boolean checkIfSpaceFreeForShip(Ship ship, String coords) {  
        int[] squareLocation = getSquareLocation(coords);
        int x = squareLocation[0];
        int y = squareLocation[1];

        if (ship.getPosition().equals("vertical")) {
            for (int i = 0; i<ship.getLength(); i++) {
                if (checkIfSpaceIsTaken(x + i, y)) {
                    return false;
                }  
            }
        } else {
            for (int i = 0; i<ship.getLength(); i++) {
                if (checkIfSpaceIsTaken(x, y + i)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkIfSpaceIsTaken(int x, int y) {

        if (x == 0 && y == 0) {
            return checkIfShipExists(x, y, 1, 0) || checkIfShipExists(x, y, 1, 1) || checkIfShipExists(x, y, 0, 1);
        }
        else if (x == 0 && y != WIDTH - 1) {
            return checkIfShipExists(x, y, 0, -1) || checkIfShipExists(x, y, 1, -1) || checkIfShipExists(x, y, 1, 0) || checkIfShipExists(x, y, 1, 1) || checkIfShipExists(x, y, 0, 1);
        }
        else if (x == 0) {
            return checkIfShipExists(x, y, 0, -1) || checkIfShipExists(x, y, 1, -1) || checkIfShipExists(x, y, 1, 0);
        }
        else if (x != HEIGHT - 1 && y == 0) {
            return checkIfShipExists(x, y, -1, 0) || checkIfShipExists(x, y, -1, 1) || checkIfShipExists(x, y, 0, 1) || checkIfShipExists(x, y, 1, 1) || checkIfShipExists(x, y, 1, 0);
        }
        else if (x == HEIGHT - 1  && y == 0) {
            return checkIfShipExists(x, y, -1, 0) || checkIfShipExists(x, y, -1, 1) || checkIfShipExists(x, y, 0, 1);
        }
        else if (x == HEIGHT - 1 && y != WIDTH - 1) {
            return checkIfShipExists(x, y, 0, -1) || checkIfShipExists(x, y, -1, -1) || checkIfShipExists(x, y, -1, 0) || checkIfShipExists(x, y, -1, 1) || checkIfShipExists(x, y, 0, 1);
        }
        else if (x == HEIGHT - 1) {
            return checkIfShipExists(x, y, 0, -1) || checkIfShipExists(x, y, -1, -1) || checkIfShipExists(x, y, -1, 0);
        }
        else if (y == WIDTH - 1) {
            return checkIfShipExists(x, y, 1, 0) || checkIfShipExists(x, y, 1, -1) || checkIfShipExists(x, y, 0, -1) || checkIfShipExists(x, y, -1, -1) || checkIfShipExists(x, y, -1, 0);
        }
        else {
            return checkIfShipExists(x, y, 0, -1) || checkIfShipExists(x, y, -1, -1) || checkIfShipExists(x, y, -1, 0) || checkIfShipExists(x, y, -1, 1) || checkIfShipExists(x, y, 0, 1) || checkIfShipExists(x, y, 1, 1) || checkIfShipExists(x, y, 1, 0) || checkIfShipExists(x, y, 1, -1);
        }
    }

    public boolean checkIfShipExists(int x, int y, int x_offset, int y_offset) {
        return this.map.get(x + x_offset).get(y + y_offset).getShip() != null;
    }

    public int[] getSquareLocation(String coords) {
        int[] squareLocation = new int[2];
        for (int i=0; i < HEIGHT; i++) {
            for (int j=0; j <WIDTH; j++) {
                if (this.map.get(i).get(j).getCoords().equals(coords.toUpperCase())) {
                    squareLocation[0] = i;
                    squareLocation[1] = j;                    
                    return squareLocation;
                }
            }
        }
        return null;
    }

    public boolean checkIfWithinBounds(Ship ship, String coords) {
        int[] squareLocation = getSquareLocation(coords);
        int x = squareLocation[0];
        int y = squareLocation[1];

        if (ship.getPosition().equals("vertical") && x + ship.getLength() > HEIGHT) {
            return false;
        } 
        else return !ship.getPosition().equals("horizontal") || y + ship.getLength() <= WIDTH;
    }

    public void isShipSunk(String coords) {

        int[] squareLocation = getSquareLocation(coords);
        int x = squareLocation[0];
        int y = squareLocation[1];

        Square square = this.map.get(x).get(y);
        Ship ship = square.getShip();

        if (ship != null){
            int x_ship= ship.getCoordX();
            int y_ship= ship.getCoordY();

            int count = 0;
            if (square.getIsHit() && ship.getPosition().equals("vertical")) {                
                for (int i = 0; i < ship.getLength(); i++) {                
                    if (this.map.get(x_ship + i).get(y_ship).getIsHit()) {
                        count++;
                    };
                }
            }
            else if (square.getIsHit() && ship.getPosition().equals("horizontal")) {
                for (int i = 0; i < ship.getLength(); i++) {                
                    if (this.map.get(x_ship).get(y_ship + i).getIsHit()) {
                        count++;
                    };
                }
            }
            if (ship.getLength() == count) {
                ship.setIsSunk();
            } 
        }
    }

    public ArrayList<ArrayList<Square>> getMap(){
        return this.map;
    }

    public boolean areAllSunk(){
        boolean result = true;
        for (ArrayList<Square> line : map) {
            for (Square square : line) {
                if (square.hasShip() && !square.getIsHit()) result = false;
            }
        }
        return result;
    }
}
