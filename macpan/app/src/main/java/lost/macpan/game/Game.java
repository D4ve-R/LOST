package lost.macpan.game;


import lost.macpan.utils.ResourceHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Main Game class, handels all the actions and logic
 *
 * @author Sebastian
 */
public class Game implements Runnable, ResourceHandler {

    private int levelNr;
    private int[] playerPos = new int[2]; // playerPos[0] = x coordinate, playerPos[1] = y coordinate
    private int[] initPlayerPos = new int[2]; // initPlayerPos[0] = initial x coordinate, initPlayerPos[1] = initial y coordinate
    private int maxColumns;                 //maximum amount of tiles that can be drawn horizontally
    private int maxRows;                    //maximum amount of tiles that can be drawn vertically
    private GameWindow gameWindow;
    private final int tickrate = 4;                         //rate of which the logic is called in times per second ("tickrate = 2" means 2 times per second)
    private Thread thread;
    private boolean gamePaused = false;
    private boolean threadRunning;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private char[][] map;                            //char-array of the map (map[0 - maxColumns][0 - maxRows])
    private int score;                               //for keeping track of the score
    private char lastKey;
    private List<Enemy> enemies = new ArrayList<>(); // An ArrayList containing the Enemy objects

    /**
     * Cooldown for Boosts in seconds
     */
    private final double SpeedCooldown = 5;
    private final double DeathTouchCooldown = 5;
    private final double CoinBoostCooldown = 5;
    private final double FreezeCooldown = 5;

    /**
     * Internal Timers for reseting the Flags
     */
    private int TimerSpeed = 0;
    private int TimerDeathTouch = 0;
    private int TimerCoinBoost = 0;
    private int TimerFreeze = 0;

    /**
     * Tiles
     */
    public final char pathTile         = '.'; // Tile that marks path elements on the map
    public final char wallTile         = 'h'; // Tile that marks wall elements on the map
    public final char coinTile         = '*'; // Tile that marks coin elements on the map
    public final char exitTile         = 'x'; // Tile that marks exit elements on the map
    public final char keyTile          = 'k'; // Tile that marks key elements on the map
    public final char playerTile       = 'p'; // Tile that marks the player element on the map
    public final char enemyTile        = 'g'; // Tile that marks enemy elements on the map
    public final char speedEffectTile  = 'a'; // Tile that marks speed effect item elements on the map
    public final char freezeEffectTile = 'b'; // Tile that marks freeze item effect elements on the map
    public final char coinBoostTile    = 'c'; // Tile that marks coin boost item elements on the map
    public final char extraLifeTile    = 'd'; // Tile that marks extra life item elements on the map
    public final char deathTouchTile   = 'e'; // Tile that marks death touch effect item elements on the map

    /**
     * Flag array is built as follows: <br>
     * [0] = player____[true]>alive____[false]>dead <br>
     * [1] = armor (extra life)____[true]>collected____[false]>not collected <br>
     * [2] = speed boost____[true]>active____[false]>inactive <br>
     * [3] = key____[true]>collected____[false]>not collected <br>
     * [4] = pan (death touch)____[true]>active____[false]>inactive <br>
     * [5] = coin booster____[true]>active____[false]>inactive <br>
     * [6] = exit unlock____[true]>locked____[false]>unlocked <br>
     * [7] = enemy freeze____[true]>active____[false]>inactive   <br>
     */
    public boolean[] flags;

    /**
     * Constructor
     * @author Sebastian
     * @param pGameWindow the GameWindow
     */
    public Game(GameWindow pGameWindow){
        gameWindow = pGameWindow;
        maxRows = pGameWindow.getMaxRows();
        maxColumns = pGameWindow.getMaxColumns();
        levelNr = 1;
    }

    /**
     * Return the map
     * @author Sebastian
     * @return a char[][] array of the map
     */
    public char[][] getMap() {
        return map;
    }

    /**
     * Return the FlagsArray
     * @author Sebastian
     * @return a boolean flags array
     */
    public boolean[] getFlags() {
        return flags;
    }

    /**
     * Return the Score
     * @return int the score
     * @author Sebastian
     */
    public int getScore() {
        return score;
    }

    /**
     * Starts the new thread
     * @author Sebastian
     */
    public void startThread(){
        flags = new boolean[8];
        map = importMapArray("level_1.txt"); //import the map test
        threadRunning = true;
        thread = new Thread(this);
        thread.start();

        getPlayerPos();
        initPlayerPos[0] = playerPos[0];
        initPlayerPos[1] = playerPos[1];
        initiateEnemies();
    }

    /**
     * stops the game/whole thread
     * @author Sebastian
     */
    public void stopThread() {
        gamePaused = true;
        running.set(false);
    }

    /**
     * resumes the game
     * @author Sebastian
     */
    public void spielFortsetzen(){
        gamePaused = false;
    }

    /**
     * pauses the game
     * @author Sebastian
     */
    public void spielPausieren() {
        gamePaused = true;
        gameWindow.showPauseMenu();
    }

    /**
     * Game Loop
     */
    @Override
    public void run() {
        long timer = System.currentTimeMillis();
        long timer2 = timer;
        long timer3 = timer;
        int frames = 0 ;
        while(running.get()) {
            while (!gamePaused) {
                gameWindow.repaint();
                frames++;

                if (System.currentTimeMillis() - timer2 > 250) {
                    gameLogic();
                    timer2 = System.currentTimeMillis();
                }

                int speed = 1;
                if(flags[2])
                    speed = 5;
                if (System.currentTimeMillis() - timer3 > 250/speed) {
                    movePlayer();
                    timer3 = System.currentTimeMillis();
                }

                // prints out framerate to terminal
                if (System.currentTimeMillis() - timer > 1000) {
                    System.out.println("fps: " + frames);
                    frames = 0;
                    timer = System.currentTimeMillis();
                }

                try {
                    thread.sleep(45);     //15 =  roughly 60 fps
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                thread.sleep(40);
            }catch(Exception e) {e.printStackTrace();}
        }
    }

    /**
     * method for the Logic of the game, gets called every tickrate times per second
     * @author Sebastian
     */
    public void gameLogic(){

        //SpeedBoost
        if(flags[2] && TimerSpeed == 0) TimerSpeed = (int)(SpeedCooldown * tickrate) + 1;
        if(flags[2] && TimerSpeed == 1) flags[2] = false;
        if(TimerSpeed > 0) TimerSpeed--;

        //Death Touch
        if(flags[4] && TimerDeathTouch == 0) TimerDeathTouch = (int)(DeathTouchCooldown * tickrate) + 1;
        if(flags[4] && TimerDeathTouch == 1) flags[4] = false;
        if(TimerDeathTouch > 0) TimerDeathTouch--;

        //CoinBoost
        if(flags[5] && TimerCoinBoost == 0) TimerCoinBoost = (int)(CoinBoostCooldown * tickrate) + 1;
        if(flags[5] && TimerCoinBoost == 1) flags[5] = false;
        if(TimerCoinBoost > 0) TimerCoinBoost--;

        //Freeze
        if(flags[7] && TimerFreeze == 0) TimerFreeze = (int)(FreezeCooldown * tickrate) +1;
        if(flags[7] && TimerFreeze == 1) flags[7] = false;
        if(TimerFreeze > 0) TimerFreeze--;

        /*  For Debugging the Game Loop and Items
        System.out.println("Gameloop");
        System.out.println("TimerSpeed: " +TimerSpeed);
        System.out.println("TimerDeathTouch: " + TimerDeathTouch);
        System.out.println("TimerCoinBoost: " +TimerCoinBoost);
        System.out.println("TimerFreeze: " +TimerFreeze);
         */

        /*
        if(flags[7]){   //Not yet implemented
            gegnerEinfrieren();
        }
        */
        moveEnemies();
    }

    public void movePlayer(){
        if(flags[2]){
            move(lastKey);
            lastKey = 'o';
        } else {
            move(lastKey);
            lastKey = 'o';
        }
    }

    public void moveEnemies(){
        if(!flags[7]) {
            for (Enemy enemy : enemies) { // TODO: Enemy movement flexible
                enemy.move(); // Move Enemy objects unless freeze effect is active
                if (enemyDetection(enemy)) break; // Detect whether the player collides with the enemy
            }
        }
    }

    /**
     * method for key Actions, gets called every time a mapped Key is pressed
     * To add new Keys they first have to be added to the keymap in the setKeyBindings() function in GameWindow
     * @author Sebastian
     * @param pKey String with the name of the key event constant (for a pKey would be "VK_A")
     *
     */
    public void newKeyAction(String pKey) {
        switch (pKey){
            case "VK_ESCAPE":
                spielPausieren();
                break;
            case "VK_W":
                lastKey = 'w';
                break;
            case "VK_W_released":
                break;
            case "VK_A":
                lastKey = 'a';
                break;
            case "VK_A_released":

                break;
            case "VK_S":
                lastKey = 's';
                break;
            case "VK_S_released":

                break;
            case "VK_D":
                lastKey = 'd';
                break;
            case "VK_D_released":

                break;
        }
    }

    /**
     * Method that calls the 'moveToNew(int x, int y)' method depending on the users input
     * @author Benedikt
     */
    public void move(char key){
        switch (key) {
            case 'w' -> moveToNew(0,-1);
            case 's' -> moveToNew(0,1);
            case 'a' -> moveToNew(-1,0);
            case 'd' -> moveToNew(1,0);
        }
    }

    /**
     * Trys to move the player to a new position relatively.
     * Detects what will be at given position and acts accordingly.
     * Finally calls the 'geh(int x, int y)' method to actually move the player.
     * @author Benedikt // Abgeändert zu switch cases von Simon
     */
    public void moveToNew(int x, int y) {
        char onNewPos = map[playerPos[0]+x][playerPos[1]+y];
        if(onNewPos != wallTile) {
            geh(x, y);
            switch (onNewPos) {
                case coinTile -> {
                    if(flags[5]) score += 20;
                    else score +=10;
                    break;
                } case enemyTile -> {
                    for (Enemy enemy : enemies) {
                        if (enemyDetection(enemy))
                            break;
                    }
                    break;
                } case keyTile -> {
                    flags[3] = true;
                    flags[6] = true;
                    break;
                } case exitTile -> {
                    if(!flags[3]) return; // TODO: Wofür?
                    else {
                        levelNr++;
                        flags[3] = false;
                        flags[6] = false;
                        switch(levelNr){
                            case 2:
                                map = importMapArray("level_2.txt");
                                break;
                            case 3:
                                map = importMapArray("level_3.txt");
                                break;
                            default:
                                gameWindow.showWinnerMenu();
                                stopThread();
                        }
                    }
                    break;
                }
                case speedEffectTile -> {
                    flags[2] = true;
                    break;
                }
                case freezeEffectTile -> {
                    flags[7] = true;
                    break;
                }
                case coinBoostTile -> {
                    flags[5] = true;
                    break;
                }
                case extraLifeTile -> {
                    flags[1] = true;
                    break;
                }
                case deathTouchTile -> {
                    flags[4] = true;
                    break;
                }
            }
        }
    }

    /**
     * Moves the Player to a relative coordinate in the grid
     * @param x coordinate on the X axes
     * @param y coordinate on the Y axes
     * @author Benedikt
     */
    public void geh(int x, int y){
        map[playerPos[0]][playerPos[1]] = pathTile;
        playerPos[0] += x;
        playerPos[1] += y;
        map[playerPos[0]][playerPos[1]] = playerTile;
    }

    /**
     * Gets the Players coordinates in the grid and stores them in 'int[2] playerPos'
     * @author Benedikt
     */
    public void getPlayerPos(){
        playerPos = new int [2];
        for (int i = 0; i < maxColumns; i++){
            for (int j = 0; j < maxRows; j++) {
                if(map[i][j] == playerTile){
                    playerPos[0] = i;
                    playerPos[1] = j;
                    System.out.println(i + "und" + j);
                    return;
                }
            }
        }
    }

    /**
     * Method to overwrite the enemies ArrayList with the enemies position given by the 'g's in the map grid
     * @author Simon Bonnie
     */
    public void initiateEnemies() {
        enemies.clear(); // Remove Enemy objects from last session
        for(int i = 0; i < maxColumns; i++)
            for (int j = 0; j < maxRows; j++)
                if(this.getMap()[i][j] == enemyTile) enemies.add(new Enemy(i, j, this));
    }

    /**
     * Method to detect if a player collides with an Enemy object and act accordingly
     * @param enemy the Enemy object to be checked for collision
     * @return whether a collision between enemy and player was detected or not
     * @author Simon Bonnie
     */
    public boolean enemyDetection(Enemy enemy) {
        if(playerPos[0] == enemy.getPosX() && playerPos[1] == enemy.getPosY()) { // If players coordinates match with an enemies one ...
            if(flags[4]) { // If death touch is active
                this.getMap()[enemy.getPosX()][enemy.getPosY()] = enemy.getAbove();
                enemies.remove(enemy);
            } else {
                if (flags[1]) { // If player has got an extra life
                    flags[1] = false; // Reset extra life
                    map[playerPos[0]][playerPos[1]] = pathTile; // Reset the player
                    playerPos[0] = initPlayerPos[0];            // position to the
                    playerPos[1] = initPlayerPos[1];            // starting position
                    map[initPlayerPos[0]][initPlayerPos[1]] = playerTile;
                } else {
                    flags[0] = false; // Kill player
                    gameWindow.showDeathWindow();
                    stopThread();
                }
            }
            return true;
        }
        return false;
    }

    /**
     * method for importing a map as a char array
     * @author Sebastian
     *
     * @param pFileName name of the map to load (has to be in the levels folder)
     * @return charArray of the map at the filename
     */
    private char[][] importMapArray(String pFileName){
        char[][] map = new char[maxColumns][maxRows];
        String mapString = "";
        try {
            InputStream inputStream = getFileResourcesAsStream("levels/"+pFileName);
            mapString = convertStreamToString(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] rows = mapString.split("\n"); //Split String into String Array consisting of single Rows
        for(int i = 0; i < Math.min(rows.length,maxRows);i++ )              //For every row
            for (int o = 0; o < Math.min(rows[i].length(),maxColumns); o++) //for every char in the row
                map[o][i] = rows[i].charAt(o);  //insert char into the map array
        return map;
    }
}