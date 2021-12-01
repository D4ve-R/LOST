package lost.macpan.game;

/*
* NOTIZ AN MICH SELBST
* 'h', '\0', '\r', 'x'              = unpassable for enemy
* '.', 'p', 'g', '*', 'a - e', 'k'  = passable for enemy
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
        switch (facing) {
            case 0:
                return "north";
            case 1:
                return "east";
            case 2:
                return "south";
            case 3:
                return "west";
            default:
                return "";
        }
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
            case "inFront", "front":
                switch (facingDir) {
                    case "north":
                        return game.map[posX][posY+1];
                    case "east":
                        return game.map[posX+1][posY];
                    case "south":
                        return game.map[posX][posY-1];
                    case "west":
                        return game.map[posX-1][posY];
                }
                break;
            case "left":
                switch (facingDir) {
                    case "north":
                        return game.map[posX-1][posY];
                    case "east":
                        return game.map[posX][posY+1];
                    case "south":
                        return game.map[posX+1][posY];
                    case "west":
                        return game.map[posX][posY-1];
                }
                break;
            case "right":
                switch (facingDir) {
                    case "north":
                        return game.map[posX+1][posY];
                    case "east":
                        return game.map[posX][posY-1];
                    case "south":
                        return game.map[posX-1][posY];
                    case "west":
                        return game.map[posX][posY+1];
                }
            case "behind":
                switch (facingDir) {
                    case "north":
                        return game.map[posX][posY-1];
                    case "east":
                        return game.map[posX-1][posY];
                    case "south":
                        return game.map[posX][posY+1];
                    case "west":
                        return game.map[posX+1][posY];
                }
            case "below":
                return getAbove();
            default:
                new RuntimeException("'" + direction + "' is an invalid direction. Must be 'inFront', 'left', 'right' or 'behind'.").printStackTrace();
                break;
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
                    case 0, 1:
                        turn("left");
                        break;
                    case 2, 3:
                        turn("right");
                        break;
                    case 4:
                        turn("behind");
                        break;
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
                case 0, 1:
                    // continue straight
                    break;
                case 2:
                    turn("left");
                    break;
                case 3:
                    turn("right");
                    break;
            }
        } else if(!isPassable(detect("right")) && isPassable(detect("left"))) { // straight ahead and left are good to go
            int randomTurn = (int)(Math.random()*3);
            switch (randomTurn) {
                case 0, 1:
                    // continue straight
                    break;
                case 2:
                    turn("left");
                    break;
            }
        } else if(isPassable(detect("right")) && !isPassable(detect("left"))) { // straight ahead and right are good to go
            int randomTurn = (int)(Math.random()*3);
            switch (randomTurn) {
                case 0, 1:
                    // continue straight
                    break;
                case 2:
                    turn("right");
                    break;
            }
        }

        // Actual enemy movement
        if(isPassable(detect("inFront"))) {
            game.map[posX][posY] = above;   // restore the tile the enemy was above
            switch (getFacingDirection()) {
                case "north":
                    above = game.map[posX][posY + 1];
                    game.map[posX][posY + 1] = 'g';
                    posY++;
                    break;
                case "east":
                    above = game.map[posX + 1][posY];
                    game.map[posX + 1][posY] = 'g';
                    posX++;
                    break;
                case "south":
                    above = game.map[posX][posY - 1];
                    game.map[posX][posY - 1] = 'g';
                    posY--;
                    break;
                case "west":
                    above = game.map[posX - 1][posY];
                    game.map[posX][posY - 1] = 'g';
                    posX--;
                    break;
            }
        }
    }

    /**
     * Helping method to turn an enemy object towards a new direction
     * @param direction either "left", "right" or "behind" depending on what is wanted
     */
    public void turn(String direction) throws RuntimeException {
        switch (direction) {
            case "left":
                if(facing > 0) facing--;
                else facing = 3;
                System.out.println("I did a left turn!");
                break;
            case "right":
                if(facing < 3) facing++;
                else facing = 0;
                System.out.println("I did a right turn!");
                break;
            case "behind", "180":
                if(facing < 2) facing += 2;
                else facing -= 2;
                System.out.println("I did a 180 no scope!");
                break;
            default:
                new RuntimeException("'" + direction + "' is an invalid turn direction. Must be 'left', 'right' or 'behind'.").printStackTrace();
                break;
        }
        System.out.println("Richtung nach turn: " + facing);
    }

    /**
     * Helping method to detect wether a game tile is passable or not
     * @param pTile the tile to be checked
     * @return true or false
     */
    public boolean isPassable(char pTile) {
        return (pTile != 'h' && pTile != 'x' && pTile != '\0' && pTile != '\r');
    }
}
