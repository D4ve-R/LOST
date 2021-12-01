package lost.macpan.game;

/*
* NOTIZ AN MICH SELBST
* 'h', '\0', '\r', 'x', 'g'     = unpassable for enemy
* '.', 'p', '*', 'a - e', 'k'   = passable for enemy
*
*/

/**
 * Class that manages the enemy movement logic
 * @author Simon Bonnie
 * @version 1.0
 */
public class Enemy {
    // X and Y coordinate of the enemy entity + the facing direction of the enemy (0 = north, 1 = east, 2 = south, 3 = west)
    private int posX = 0, posY = 0, facing = 0;
    // Stores the tile the enemy is above
    private char above = '.';
    GameWindow game;

    /* CONSTRUCTOR */
    /**
     * Constructor method for Enemy. Initiates an enemy object at a given location
     * @param xCoordinate the x coordinate of the enemy object
     * @param yCoordinate the y coordinate of the enemy object
     * @param pGame the GameWindow allowing to manipulate the GameWindow.map
     */
    public Enemy(int xCoordinate, int yCoordinate, GameWindow pGame) {
        posX = xCoordinate;
        posY = yCoordinate;
        this.game = pGame;
        facing = (int)(Math.random()*3);
        above = '.'; //game.map[xCoordinate][yCoordinate];
    }

    /* GETTER & SETTER */
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public int getFacing() { return facing; }

    /**
     * Method to get the facing direction of an enemy object in form of readable text
     * @return facing direction (either "north", "east", "south" or "west")
     */
    public String getFacingDirection() {
        return switch (facing) {
            case 0 -> "north";
            case 1 -> "east";
            case 2 -> "south";
            case 3 -> "west";
            default -> "";
        };
    }
    public char getAbove() { return above; }

    /* METHODS */

    /**
     * Helping method to detect what game tile is in a specific direction relative to the enemy object
     * @param direction either "inFront", "left", "right" or "behind" depending on the request
     * @return the game tile at the given location in char form
     */
    public char detect(String direction) throws RuntimeException {
        String facingDir = getFacingDirection();
        switch (direction) {
            case "inFront", "front" -> {
                return switch (facingDir) {
                    case "north" -> game.map[posX][posY + 1];
                    case "east" -> game.map[posX + 1][posY];
                    case "south" -> game.map[posX][posY - 1];
                    case "west" -> game.map[posX - 1][posY];
                    default -> game.map[posX][posY];
                };
            } case "left" -> {
                return switch (facingDir) {
                    case "north" -> game.map[posX - 1][posY];
                    case "east" -> game.map[posX][posY + 1];
                    case "south" -> game.map[posX + 1][posY];
                    case "west" -> game.map[posX][posY - 1];
                    default -> game.map[posX][posY];
                };
            } case "right" -> {
                return switch (facingDir) {
                    case "north" -> game.map[posX + 1][posY];
                    case "east" -> game.map[posX][posY - 1];
                    case "south" -> game.map[posX - 1][posY];
                    case "west" -> game.map[posX][posY + 1];
                    default -> game.map[posX][posY];
                };
            } case "behind" -> {
                return switch (facingDir) {
                    case "north" -> game.map[posX][posY - 1];
                    case "east" -> game.map[posX - 1][posY];
                    case "south" -> game.map[posX][posY + 1];
                    case "west" -> game.map[posX + 1][posY];
                    default -> game.map[posX][posY];
                };
            } case "below" -> {
                return getAbove();
            } default -> new RuntimeException("'" + direction + "' is an invalid direction. Must be 'inFront', 'left', 'right' or 'behind'.").printStackTrace();
        }
        return '.';
    }

    /**
     * Method that makes the enemy object move once towards the facing direction and turn in case it hits a wall
     */
    public void move() {
        // Turn towards new direction in case the enemy hits a wall
        if(!isPassable(detect("front"))) { // Wall in front of enemy
            if (isPassable(detect("right")) && isPassable(detect("left"))) {
                // Right and Left path are good to go, but not in front. Enemy decides new direction randomly
                int randomTurn = (int) (Math.random() * 5);
                switch (randomTurn) {
                    // Right now enemy can do a 180 deg turn when facing a wall. Change to random()*2 and remove case 2 to only allow left and right turns
                    case 0, 1 -> turn("left");
                    case 2, 3 -> turn("right");
                    case 4 -> turn("behind");
                }
            } else if (!isPassable(detect("right")) && isPassable(detect("left"))) {
                turn("left");
            } else if (isPassable(detect("right")) && !isPassable(detect("left"))) {
                turn("right");
            } else if (!isPassable(detect("right")) && !isPassable(detect("left"))) {
                turn("behind");
            }
        } else if(isPassable(detect("right")) && isPassable(detect("left"))) { // straight ahead left and right are good to go
            int randomTurn = (int)(Math.random()*4);
            switch (randomTurn) {
                case 0 -> turn("left");
                case 1 -> turn("right");
            }
        } else if(!isPassable(detect("right")) && isPassable(detect("left"))) { // straight ahead and left are good to go
            int randomTurn = (int)(Math.random()*3);
            if (randomTurn == 0) {
                turn("left");
            }
        } else if(isPassable(detect("right")) && !isPassable(detect("left"))) { // straight ahead and right are good to go
            int randomTurn = (int)(Math.random()*3);
            if (randomTurn == 0) {
                turn("right");
            }
        }

        // Actual enemy movement
        if(isPassable(detect("inFront"))) {
            game.map[posX][posY] = above;   // restore the tile the enemy was above
            switch (getFacingDirection()) {
                case "north" -> {
                    above = game.map[posX][posY + 1];
                    game.map[posX][posY + 1] = 'g';
                    posY++;
                } case "east" -> {
                    above = game.map[posX + 1][posY];
                    game.map[posX + 1][posY] = 'g';
                    posX++;
                } case "south" -> {
                    above = game.map[posX][posY - 1];
                    game.map[posX][posY - 1] = 'g';
                    posY--;
                } case "west" -> {
                    above = game.map[posX - 1][posY];
                    game.map[posX - 1][posY] = 'g';
                    posX--;
                }
            }
        }
    }

    /**
     * Helping method to turn an enemy object towards a new direction
     * @param direction either "left", "right" or "behind" depending on what is wanted
     */
    public void turn(String direction) throws RuntimeException {
        switch (direction) {
            case "left" -> {
                if (facing > 0) facing--;
                else facing = 3;
                /*System.out.println("I did a left turn!");*/
            } case "right" -> {
                if(facing < 3) facing++;
                else facing = 0;
                /*System.out.println("I did a right turn!");*/
            } case "behind", "180" -> {
                if(facing < 2) facing += 2;
                else facing -= 2;
                /*System.out.println("I did a 180 no scope!");*/
            } case "straight" -> {
                // continue straight
            } default -> new RuntimeException("'" + direction + "' is an invalid turn direction. Must be 'left', 'right' or 'behind'.").printStackTrace();
        }
        System.out.println("Richtung nach turn: " + facing);
    }

    /**
     * Helping method to detect wether a game tile is passable or not
     * @param pTile the tile to be checked
     * @return true or false
     */
    public boolean isPassable(char pTile) {
        return (pTile != 'h' && pTile != 'x' && pTile != '\0' && pTile != '\r' && pTile != 'g');
    }
}
