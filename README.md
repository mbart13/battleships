# Battleship in the OOP way

## The story
Take old time pen&paper classic Battleships to your computer! 
With this version both boards will always be clearly presented 
and you will be able to focus on taking out this pesky, 2-field
little bugger. 

## Specification
* Game for 2 players
* Each player can see his board with all information and opponent's board with un-hit ships hidden
* Players place the ships themselves before the game
* There are 5 ships to be placed: 
    * Carrier (5 holes)
    * Battleship (4 holes)
    * Cruiser (3 holes)
    * Submarine (3 holes)
    * Destroyer (2 holes)
* Ships can be placed according to the rules:
    * battleships.models.Ship can be in horizontal or vertical position, but not diagonal,
    * All parts of ship are placed within the map,
    * Ships can't overlap or be placed next to each other.
* Players take turns trying to hit opponents ships by guessing coordinates
* After hitting a ship player continues his turn
* Players receive messages about the results of their hits
* Ships that has been sunk are marked as such
* The player that sinks all opponent's ships is immidiately declared a winner
* There is a main menu from which you can start new game, view rules or exit the game
* Game has to handle wrong input in every place


__main__
Starts menu from battleships.controllers.MenuController class

__square__
Object representing one square of the map

__ship__
Object representing a single ship

__ocean__
Object representing one board (one for each player)

__util__
Class containing utility functions used by other classess

__view__
Class with methods for printing data on screen

__MenuController__
Controller for main menu

__SetupController__
Controller for the process of placing ships. Guides both players through it and returns a list of two oceans

__GameController__
Controller for the game itself, requires two battleships.models.Ocean objects to be created. Ends with presenting who is the winner of the game