package lost.macpan.game;


import lost.macpan.utils.ResourceHandler;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Main Game class, handels all the actions and logic
 *
 * @author Sebastian
 */
public class Game implements Runnable, ResourceHandler {

    private int[] playerPos = new int[2]; // playerPos[0] = x coordinate, playerPos[1] = y coordinate
    private int[] initPlayerPos = new int[2]; // initPlayerPos[0] = initial x coordinate, initPlayerPos[1] = initial y coordinate

    private int maxMapColumns;                 //maximum amount of tiles that can be drawn horizontally
    private int maxMapRows;                    //maximum amount of tiles that can be drawn vertically


    private ArrayList<Character> lastKeyList = new ArrayList<Character>();
    private GameWindow gameWindow;

    private final int framerate = 60;                       //rate of which a new frame is drawn in times per second ("framerate = 60" means 60 times per second)
    private final int tickrate = 4;                         //rate of which the logic is called in times per second ("tickrate = 2" means 2 times per second)

    private Thread thread;
    private boolean gamePaused = false;
    private boolean threadRunning;

    private int levelNr;
    private char[][] map;                            //char-array of the map (map[0 - maxColumns][0 - maxRows])

    private int score;                               //for keeping track of the score

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
    public Game(GameWindow pGameWindow, int pMaxColumns, int pMaxRows){
        gameWindow = pGameWindow;
        maxMapColumns = pMaxColumns;
        maxMapRows = pMaxRows;

        flags = new boolean[8];
        map = importMapArray("level_1.txt");
        levelNr = 1;

    }

    /**
     * Constructor
     * @author Hung
     */
    public Game(char[][] newmap, int newscore, int newTimerDeathTouch,
                int newTimerSpeed, int newTimerCoinBoost, int newTimerFreeze, int levelNr,
                boolean[] newflags){
        this.map = newmap;
        this.score = newscore;
        this.TimerSpeed = newTimerSpeed;
        this.TimerDeathTouch = newTimerDeathTouch;
        this.TimerCoinBoost = newTimerCoinBoost;
        this.TimerFreeze = newTimerFreeze;
        this.flags = newflags;
        this.levelNr = levelNr;
    }

    /**
     * load game window onto the screen
     * @author Hung
     * @param pGameWindow the GameWindow
     */
    public void loadWindow(GameWindow pGameWindow){
            gameWindow = pGameWindow;
            maxMapRows = pGameWindow.getMaxRows();
            maxMapColumns = pGameWindow.getMaxColumns();
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
     * Return current levelNr
     * @ Dave
     * @return current levelno
     */
    public int getLevelNr(){
        return levelNr;
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
     * returns the maxColumns
     * @author Sebastian
     * @return int maxColumns
     */
    public int getMaxColumns() {
        return maxMapColumns;
    }

    /**
     * returns the maxRows
     * @author Sebastian
     * @return int maxRows
     */
    public int getMaxRows() {
        return maxMapRows;
    }

    /**
     * Return  the Timers
     * @author Hung
     */
    public int getTimerSpeed() { return TimerSpeed;}
    public int getTimerDeathTouch() { return TimerDeathTouch;}
    public int getTimerCoinBoost() {return TimerCoinBoost;}
    public int getTimerFreeze() {return TimerFreeze;}

    /**
     * Starts the new thread
     * @author Sebastian
     */
    public void startThread(){
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
        threadRunning = false;
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
        //System.out.println("Spiel pausiert");
    }

    /**
     * Game Loop
     * @author Leon
     * @author Sebastian
     */
    @Override
    public void run() {
        double frametime = 1000 / (double)framerate;                       //determines the time span any frame should be displayed
        double nextDrawTime = System.currentTimeMillis() + frametime;    //determines at which point in time the next frame should start to be drawn
        long timeOld = System.currentTimeMillis();
        long contframeCounter = 0;
        int frameCounter = 0;
        int tickCounter = 0;

        while(threadRunning) {
            try {
                double remainingTime = nextDrawTime - System.currentTimeMillis();    //determines for how long the current frame should continue to be displayed
                if (remainingTime < 0) {                  //determines how long the thread should sleep for
                    remainingTime = 0;                  //with negative or 0 remaining time the thread should sleep for 0ns
                }

                thread.sleep((long) remainingTime);     //puts thread to sleep for the allotted time
                nextDrawTime += frametime;              //determines when the next frame should finish

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!gamePaused) {

                if (contframeCounter % (framerate / tickrate) == 0) {
                    gameLogic((int)contframeCounter / (framerate/tickrate)); //Game Logic is called with a iterating number, starts with 0
                    tickCounter++;
                }

                gameWindow.repaint(); //draws the frame

                if (contframeCounter % framerate == 0) { //every framerate Frames the average FPS and TPS is calculated over the last framerate Frames
                    double AverageTimeForOneFrame = ((double) (System.currentTimeMillis() - timeOld) / frameCounter);
                    double AverageTimeForOneTick = ((double) (System.currentTimeMillis() - timeOld) / tickCounter);
                    double TPS = 1000 / AverageTimeForOneTick;
                    double FPS = 1000 / AverageTimeForOneFrame;
                    System.out.println("FPS: " + new DecimalFormat("#0.00").format(FPS));
                    System.out.println("TPS: " + new DecimalFormat("#0.00").format(TPS));
                    frameCounter = 0;
                    tickCounter = 0;
                    timeOld = System.currentTimeMillis();
                }
                contframeCounter++;
                frameCounter++;
            } else {
                timeOld = System.currentTimeMillis();
            }

        }
        //System.out.println("Loop beendet");
    }

    /**
     * method for the Logic of the game, gets called every tickrate times per second
     * @author Sebastian
     *
     * @param pContLogicCounter should be called with a continuously rising number
     */
    public void gameLogic(int pContLogicCounter){

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

        if(flags[2]){
            move();

        } else {
            if(pContLogicCounter % 2 == 0){
                move();
            }
        }

        if(pContLogicCounter % 2 == 0){                 //Stuff in here only gets called every second time the GameLogic gets called
            for(Enemy enemy : enemies) {                // TODO: Enemy movement flexible
                if(!flags[7]) enemy.move();             // Move Enemy objects unless freeze effect is active
                if(enemyDetection(enemy)) break;        // Detect whether the player collides with the enemy
            }

            //Other Stuff that has the be called only every second tick
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
                addKeyToList('w');
                break;
            case "VK_W_released":
                lastKeyList.remove((Character) 'w');
                break;
            case "VK_A":
                addKeyToList('a');
                break;
            case "VK_A_released":
                lastKeyList.remove((Character) 'a');
                break;
            case "VK_S":
                addKeyToList('s');
                break;
            case "VK_S_released":
                lastKeyList.remove((Character) 's');
                break;
            case "VK_D":
                addKeyToList('d');
                break;
            case "VK_D_released":
                lastKeyList.remove((Character) 'd');
                break;
        }
    }

    /**
     * Method that adds a character to the lastKeyInput List if it isnt already in the List.
     * @author Benedikt
     */
    public void addKeyToList(char theKey){
        if(!lastKeyList.contains((Character) theKey))
        {
            lastKeyList.add((Character) theKey);
        }
    }
    /**
     * Method that calls the 'moveToNew(int x, int y)' method depending on the users input
     * @author Benedikt
     */
    public void move(){
        if(!lastKeyList.isEmpty())
        {
            switch (lastKeyList.get(lastKeyList.size() -1)) {
                case 'w' -> moveToNew(0,-1);
                case 's' -> moveToNew(0,1);
                case 'a' -> moveToNew(-1,0);
                case 'd' -> moveToNew(1 ,0);
            }
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
                } case enemyTile -> {
                    enemyDetection();
                } case keyTile -> {
                    flags[3] = true;
                    flags[6] = true;
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
                        initiateEnemies();
                    }
                }
                case speedEffectTile    -> flags[2] = true; // Geschwindigkeitsbuff
                case freezeEffectTile   -> flags[7] = true; // Gegner einfrieren
                case coinBoostTile      -> flags[5] = true; // Münzboost
                case extraLifeTile      -> flags[1] = true; // Zusatzleben
                case deathTouchTile     -> flags[4] = true; // Todesberührung
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
        for (int i = 0; i < maxMapColumns; i++){
            for (int j = 0; j < maxMapRows; j++) {
                if(map[i][j] == playerTile){
                    playerPos[0] = i;
                    playerPos[1] = j;
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
        for(int i = 0; i < maxMapColumns; i++)
            for (int j = 0; j < maxMapRows; j++)
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
                    stopThread();
                    gameWindow.showDeathWindow();
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Extension to method 'enemyDetection(Enemy)'
     * Instead of checking for one specific enemy, it looks through the whole list
     * @return whether a collision was detected or not
     * @author Simon Bonnie
     */
    public boolean enemyDetection() {
        boolean rueckgabe = false;
        for (Enemy enemy : enemies) {
            rueckgabe = enemyDetection(enemy);
            if(rueckgabe) break;
        }
        return rueckgabe;
    }

    /**
     * method for importing a map as a char array
     * @author Sebastian
     *
     * @param pFileName name of the map to load (has to be in the levels folder)
     * @return charArray of the map at the filename
     */
    private char[][] importMapArray(String pFileName){
        char[][] map = new char[maxMapColumns][maxMapRows];
        String mapString = "";
        try {
            InputStream inputStream = getFileResourcesAsStream("levels/"+pFileName);
            mapString = convertStreamToString(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] rows = mapString.split("\n"); //Split String into String Array consisting of single Rows
        for(int i = 0; i < Math.min(rows.length, maxMapRows); i++ )              //For every row
            for (int o = 0; o < Math.min(rows[i].length(), maxMapColumns); o++) //for every char in the row
                map[o][i] = rows[i].charAt(o);  //insert char into the map array
        return map;
    }
}