package lost.macpan.game;

/*
* NOTIZ AN MICH SELBST
* 'h', '\0', '\r', 'x'              = unpassable for enemy
* '.', 'p', 'g', '*', 'a - e', 'k'  = passable for enemy
*
*/

public class Enemy {
    // X and Y coordinate of the enemy entity + the facing direction of the enemy (0 = north, 1 = east, 2 = south, 3 = west)
    private int posX = 0, posY = 0, facing = 0;
    // Stores the tile the enemy is above
    private char above = '.';
    GameWindow game;

    /* CONSTRUCTOR */
    public Enemy(int xCoordinate, int yCoordinate, GameWindow pGame) {
        posX = xCoordinate;
        posY = yCoordinate;
        this.game = pGame;
        facing = (int)(Math.random()*3);
        above = '.';
    }

    /* GETTER & SETTER */
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public int getFacing() { return facing; }
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
        }
        return "";
    }
    public char getAbove() { return above; }

    /* METHODS */
    public char detect(String direction) {
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
        }
        return '.';
    }

    public void move() {
        if(isPassable(detect("inFront"))) {
            game.map[posX][posY] = above;   // restore the tile the enemy was above
            switch (getFacingDirection()) {
                case "north":
                    above = game.map[posX][posY+1];
                    game.map[posX][posY+1] = 'g';
                    posY++;
                    break;
                case "east":
                    above = game.map[posX+1][posY];
                    game.map[posX+1][posY] = 'g';
                    posX++;
                    break;
                case "south":
                    above = game.map[posX][posY-1];
                    game.map[posX][posY-1] = 'g';
                    posY--;
                    break;
                case "west":
                    above = game.map[posX-1][posY];
                    game.map[posX][posY-1] = 'g';
                    posX--;
                    break;
            }
        } else if(isPassable(detect("right")) && isPassable(detect("left"))) {
            // Right and Left path are good to go, but not in front. Enemy decides new direction randomly
            int randomTurn = (int)(Math.random()*3);
            switch (randomTurn) {
                case 0:
                    turn("left");
                case 1:
                    turn("right");
                case 2:
                    turn("behind");
            }
        } else if(!isPassable(detect("right")) && isPassable(detect("left"))) {

        } else if(isPassable(detect("right")) && !isPassable(detect("left"))) {

        }
    }

    public void turn(String direction) {
        switch (direction) {
            case "left":
                if(facing > 0) facing--;
                else facing = 3;
            case "right":
                if(facing < 3) facing++;
                else facing = 0;
            case "behind", "180":
                if(facing < 2) facing += 2;
                else facing -= 2;
        }
    }

    public boolean isPassable(char pTile) {
        return (pTile != 'h' && pTile != 'x' && pTile != '\0' && pTile != '\r');
    }
}
