package battleships.models;

public class Ship {

    private int x;
    private int y;
    private int length;
    private String shipName;
    private String position;
    private boolean isSunk;

    public Ship(String shipName, String position) {

        if (shipName.equals("Carrier")) this.length = 5;
        else if(shipName.equals("Battleship")) this.length = 4;
        else if(shipName.equals("Cruiser")) this.length = 3;
        else if(shipName.equals("Submarine")) this.length = 3;
        else this.length = 2;

        this.shipName = shipName;
        this.position = position;
        this.isSunk = false;
    }

    public void setIsSunk(){
        this.isSunk = true;
    }

    public int getLength() {
        return this.length;
    }

    public String getPosition() {
        return this.position;
    } 

    public boolean getIsSunk() {
        return this.isSunk;
    }

    public int getCoordX(){
        return this.x;
    }

    public int getCoordY(){
        return this.y;
    }

    public String getShipName(){
        return this.shipName;
    }

    public void setCoordX(int x) {
        this.x = x;
    }

    public void setCoordY(int y){
        this.y = y;
    }

    public void setShipName(String shipName){
        this.shipName = shipName;
    }
}