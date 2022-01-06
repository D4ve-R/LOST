/**
 * MacPan version 0.1
 * SWE WS 21/22
 */

package lost.macpan.game;

/**
 * Class that manages the enemy movement logic
 * @author Simon Bonnie
 */
public class Enemy {
    /**
     * The game class object needed to access and edit the map grid
     */
    Game game;
    /**
     * X and Y coordinate of the enemy entity + the facing direction of the enemy (0 = north, 1 = east, 2 = south, 3 = west)
     */
    private int posX = 0, posY = 0, facing = 0;
    /**
     * Stores the tile the enemy is above
     */
    private char above = '.'; // pathTile below Enemy

    protected int index;
    protected boolean frozen;
    int timer;

    /**
     * Constructor method for Enemy. Initiates an enemy object at a given location
     * @param xCoordinate the x coordinate of the enemy object
     * @param yCoordinate the y coordinate of the enemy object
     * @param pGame the GameWindow allowing to manipulate the GameWindow.getMap()
     */
    public Enemy(int xCoordinate, int yCoordinate, Game pGame, int index) {
        posX = xCoordinate;
        posY = yCoordinate;
        this.game = pGame;
        facing = (int)(Math.random()*3);
        above = game.pathTile;
        this.index = index;
    }

    /* GETTER & SETTER */
    /**
     * Method to be able to read the x coordinate of an enemy object externally
     * @return the x coordinate as an integer
     */
    public int getPosX() { return posX; }
    /**
     * Method to be able to read the y coordinate of an enemy object externally
     * @return the y coordinate as an integer
     */
    public int getPosY() { return posY; }
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

    /**
     * Method to access the tile the enemy object is currently above externally
     * @return the tile as a character symbol
     */
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
                    case "north"    -> game.getMap()[posX][posY + 1];
                    case "east"     -> game.getMap()[posX + 1][posY];
                    case "south"    -> game.getMap()[posX][posY - 1];
                    case "west"     -> game.getMap()[posX - 1][posY];
                    default         -> game.getMap()[posX][posY]; // just in case. should not come true
                };
            } case "left" -> {
                return switch (facingDir) {
                    case "north"    -> game.getMap()[posX - 1][posY];
                    case "east"     -> game.getMap()[posX][posY + 1];
                    case "south"    -> game.getMap()[posX + 1][posY];
                    case "west"     -> game.getMap()[posX][posY - 1];
                    default         -> game.getMap()[posX][posY]; // just in case. should not come true
                };
            } case "right" -> {
                return switch (facingDir) {
                    case "north"    -> game.getMap()[posX + 1][posY];
                    case "east"     -> game.getMap()[posX][posY - 1];
                    case "south"    -> game.getMap()[posX - 1][posY];
                    case "west"     -> game.getMap()[posX][posY + 1];
                    default         -> game.getMap()[posX][posY]; // just in case. should not come true
                };
            } case "behind" -> {
                return switch (facingDir) {
                    case "north"    -> game.getMap()[posX][posY - 1];
                    case "east"     -> game.getMap()[posX - 1][posY];
                    case "south"    -> game.getMap()[posX][posY + 1];
                    case "west"     -> game.getMap()[posX + 1][posY];
                    default         -> game.getMap()[posX][posY]; // just in case. should not come true
                };
            }
            case "below" -> {
                return getAbove();
            } default -> new RuntimeException("'" + direction + "' is an invalid direction. Must be 'inFront', 'left', 'right' or 'behind'.").printStackTrace();
        }
        return game.pathTile;
    }


    /**
     * Method that makes the enemy move considering the surrounding terrain. (Random turns at forks or crossroads)
     */
    public void move() {
        if(timer == 0) frozen = false;
        if(frozen) {
            timer--;
            return;
        }
        // Turn towards new direction in case the enemy hits a wall
        if(!isPassable(detect("front"))) { // Wall in front of enemy
            if (isPassable(detect("right")) && isPassable(detect("left"))) {
                // Right and Left path are good to go, but not in front. Enemy decides new direction randomly
                int randomTurn = (int) (Math.random() * 5); // 80% chance of turning left or right, 20% chance of turning 180 degrees
                switch (randomTurn) {
                    // Right now enemy can do a 180 deg turn when facing a wall. Change to random()*4 and remove case 4 to only allow left and right turns
                    case 0, 1 -> turn("left");
                    case 2, 3 -> turn("right");
                    case 4 -> turn("behind");
                }
            } else if (!isPassable(detect("right")) && isPassable(detect("left")))
                turn("left");
            else if (isPassable(detect("right")) && !isPassable(detect("left")))
                turn("right");
            else if (!isPassable(detect("right")) && !isPassable(detect("left")))
                turn("behind");
        } else if(isPassable(detect("right")) && isPassable(detect("left"))) { // straight ahead left and right are good to go
            int randomTurn = (int)(Math.random()*4); // 50% chance of continuing straight
            switch (randomTurn) {
                case 0 -> turn("left");
                case 1 -> turn("right");
            }
        } else if(!isPassable(detect("right")) && isPassable(detect("left"))) { // straight ahead and left are good to go
            int randomTurn = (int)(Math.random()*3); // 2/3 chance of continuing straight
            if (randomTurn == 0)
                turn("left");
        } else if(isPassable(detect("right")) && !isPassable(detect("left"))) { // straight ahead and right are good to go
            int randomTurn = (int)(Math.random()*3); // 2/3 chance of continuing straight
            if (randomTurn == 0)
                turn("right");
        }

        // Actual enemy movement
        if(isPassable(detect("inFront"))) {
            if(above != game.playerTile)
                game.getMap()[posX][posY] = above;   // restore the tile the enemy was above
            else game.getMap()[posX][posY] = game.pathTile;
            switch (getFacingDirection()) {
                case "north" -> {
                    above = game.getMap()[posX][posY + 1];
                    game.getMap()[posX][posY + 1] = game.enemyTile;
                    posY++;
                } case "east" -> {
                    above = game.getMap()[posX + 1][posY];
                    game.getMap()[posX + 1][posY] = game.enemyTile;
                    posX++;
                } case "south" -> {
                    above = game.getMap()[posX][posY - 1];
                    game.getMap()[posX][posY - 1] = game.enemyTile;
                    posY--;
                } case "west" -> {
                    above = game.getMap()[posX - 1][posY];
                    game.getMap()[posX - 1][posY] = game.enemyTile;
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
            case "left", "-90", "270" -> {
                if (facing > 0) facing--;
                else facing = 3;
            } case "right", "90" -> {
                if(facing < 3) facing++;
                else facing = 0;
            } case "behind", "180" -> {
                if(facing < 2) facing += 2;
                else facing -= 2;
            } case "straight", "360", "0" -> {
                // continue straight
            } default -> new RuntimeException("'" + direction + "' is an invalid turn direction. Must be 'left', 'right' or 'behind'.").printStackTrace();
        }
    }

    /**
     * Helping method to detect whether a game tile is passable for the enemy or not
     * @param pTile the tile to be checked
     * @return true or false
     */
    public boolean isPassable(char pTile) {
        /*
         * NOTE:
         * 'h', '\0', '\r', 'x', game.enemyTile     = unpassable for enemy
         * game.pathTile, game.playerTile, '*', 'a - e', 'k'   = passable for enemy
         */
        return (pTile != game.wallTile && pTile != game.exitTile && pTile != '\0' && pTile != '\r' && pTile != game.enemyTile);
    }

    /**
     * Method to properly compare to enemy objects
     * @param o the Enemy object to compare with
     * @return true or false whether the objects are the same or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Enemy enemy = (Enemy) o;
        return (
                this.posX == enemy.posX &&
                this.posY == enemy.posY &&
                this.facing == enemy.facing &&
                this.above == enemy.above
        );
    }
    /**
     * Method that returns the hash code of an enemy object
     * @return the hash code as an integer
     */
    @Override
    public int hashCode() {
        int result = game.hashCode();
        result = 31 * result + posX;
        result = 31 * result + posY;
        result = 31 * result + facing;
        result = 31 * result + (int) above;
        return result;
    }

    /**
     * Method that returns the enemy object in form of readable text
     * @return a String containing all relevant information
     */
    @Override
    public String toString() {
        return "Enemy {\n" +
                "\tcoordinate (X/Y):\t[" + getPosX() + "|" + getPosY() + "]\n" +
                "\tfacing direction:\t'" + getFacingDirection() + "'\n" +
                "\tabove tile:\t\t\t'" + getAbove() + "'\n" +
                "\tindex:\t\t\t'" + index + "'\n" +
                "\tfrozen:\t\t\t'" + frozen + "'\n" +
                '}';
    }
}
