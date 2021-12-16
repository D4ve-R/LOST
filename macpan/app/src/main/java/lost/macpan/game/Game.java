package lost.macpan.game;


import lost.macpan.utils.ResourceHandler;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    private final int frameRate = 20;                       //rate of which a new frame is drawn in times per second ("framerate = 60" means 60 times per second)
    private int tickRate;                                   //rate of which the logic is called in times per second ("tickrate = 2" means 2 times per second)

    private Thread thread;
    private boolean gamePaused = false;
    private boolean threadRunning;

    private int levelNr;
    private char[][] map;                            //char-array of the map (map[0 - maxColumns][0 - maxRows])

    private int score;                               //for keeping track of the score

    private List<Enemy> enemies = new ArrayList<>(); // An ArrayList containing the Enemy objects

    /**
     * Internal Timers for reseting the Flags
     */
    private int timerSpeed = 0;
    private int timerDeathTouch = 0;
    private int timerCoinBoost = 0;
    private int timerFreeze = 0;

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
        tickRate = 4;
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
        this.timerSpeed = newTimerSpeed;
        this.timerDeathTouch = newTimerDeathTouch;
        this.timerCoinBoost = newTimerCoinBoost;
        this.timerFreeze = newTimerFreeze;
        this.flags = newflags;
        this.levelNr = levelNr;
        switch(levelNr){
            case 2:
                tickRate = 5;
                break;
            case 3:
                tickRate = 6;
                break;
            default:
                tickRate = 4;
        }
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
    public int getTimerSpeed() { return timerSpeed;}
    public int getTimerDeathTouch() { return timerDeathTouch;}
    public int getTimerCoinBoost() {return timerCoinBoost;}
    public int getTimerFreeze() {return timerFreeze;}

    /**
     * Starts the new thread
     * @author Sebastian
     */
    public void startThread(){
        threadRunning = true;
        thread = new Thread(this);
        thread.start();

        initPlayerPos = getPlayerPos();
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
    }

    /**
     * Game Loop
     * @author Leon
     * @author Sebastian
     */
    @Override
    public void run() {
        double frameTime = 1000 / (double) frameRate;                       //determines the time span any frame should be displayed
        double nextDrawTime = System.currentTimeMillis() + frameTime;    //determines at which point in time the next frame should start to be drawn
        long frameCounter = 0;

        while(threadRunning) {
            double remainingTime = nextDrawTime - System.currentTimeMillis();    //determines for how long the current frame should continue to be displayed
            if (remainingTime < 0)                  //determines how long the thread should sleep for
                remainingTime = 0;                  //with negative or 0 remaining time the thread should sleep for 0ns

            nextDrawTime += frameTime;              //determines when the next frame should finish
            try {
                thread.sleep((long) remainingTime);     //puts thread to sleep for the allotted time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!gamePaused) {
                checkBooster();
                if (frameCounter % (frameRate / tickRate) == 0) {
                    // e.g. 20fps 4 ticks = 50ms per frame,
                    // but only every (20/4) 5th frame gamelogic is called,
                    // so gamelogic has 4 fps
                    // gamelogic() updates movement only every 2th frame
                    // so movement happens at 2fps
                    gameLogic((int)(frameCounter/(frameRate / tickRate))); //Game Logic is called with a iterating number, starts with 0
                }
                gameWindow.repaint(); //draws the frame
                frameCounter++;
                if(frameCounter == frameRate * 2)
                    frameCounter = 0;
            }
        }
    }

    /**
     * method for the Logic of the game, gets called every tickrate times per second
     * @author Sebastian
     */
    private void gameLogic(int tick){
        if(tick % 2 == 0 || flags[2])
            move();

        // Move Enemy objects unless freeze effect is active
        if(!flags[7] && tick % 2 == 0) {
            for(Enemy enemy : enemies) {
                enemy.move();
                if(enemyDetection(enemy)) break;
            }
        }
    }

    /**
     * Handles Boost timer, checks flags
     * @author Dave & Sebastian
     */
    private void checkBooster(){
        //SpeedBoost
        if(flags[2] && timerSpeed == 0) timerSpeed = 5 * frameRate;
        if(flags[2] && timerSpeed == 1) flags[2] = false;
        if(timerSpeed > 0) timerSpeed--;

        //Death Touch
        if(flags[4] && timerDeathTouch == 0) timerDeathTouch = 5 * frameRate;
        if(flags[4] && timerDeathTouch == 1) flags[4] = false;
        if(timerDeathTouch > 0) timerDeathTouch--;

        //CoinBoost
        if(flags[5] && timerCoinBoost == 0) timerCoinBoost = 5 * frameRate;
        if(flags[5] && timerCoinBoost == 1) flags[5] = false;
        if(timerCoinBoost > 0) timerCoinBoost--;

        //Freeze
        if(flags[7] && timerFreeze == 0) timerFreeze = 5 * frameRate;
        if(flags[7] && timerFreeze == 1) flags[7] = false;
        if(timerFreeze > 0) timerFreeze--;
    }

    /**
     * Method that calls the 'moveToNew(int x, int y)' method depending on the users input
     * @author Benedikt
     */
    private void move(){
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
    private void moveToNew(int x, int y) {
        char onNewPos = map[playerPos[0]+x][playerPos[1]+y];
        if(onNewPos == wallTile) {}
        else {
            boolean step = true;
            switch (onNewPos) {
                case coinTile -> {
                    if(flags[5]) score += 20;
                    else score +=10;
                } case keyTile -> {
                    flags[3] = true;
                } case exitTile -> {
                    if(!flags[3]) {
                        step = false;
                    }
                    else {
                        levelNr++;
                        changeLevel(levelNr);
                        step = false;
                    }
                }
                case speedEffectTile -> flags[2] = true; // Geschwindigkeitsbuff
                case freezeEffectTile -> flags[7] = true; // Gegner einfrieren
                case coinBoostTile -> flags[5] = true; // Münzboost
                case extraLifeTile -> flags[1] = true; // Zusatzleben
                case deathTouchTile -> flags[4] = true; // Todesberührung
            }
            if(step) {
                map[playerPos[0]][playerPos[1]] = pathTile;
                playerPos[0] += x;
                playerPos[1] += y;
                map[playerPos[0]][playerPos[1]] = playerTile;
                enemyDetection();
            }
        }
    }

    /**
     * Handles level changing
     * @param level levelNr to change to
     * @author Dave
     */
    private void changeLevel(int level){
        for(int i = 1; i < 8; ++i)
            flags[i] = false;

        timerSpeed = 0;
        timerFreeze = 0;
        timerCoinBoost = 0;
        timerDeathTouch = 0;

        switch(levelNr){
            case 2:
                map = importMapArray("level_2.txt");
                tickRate = 6;
                initPlayerPos = getPlayerPos();
                initiateEnemies();
                break;
            case 3:
                map = importMapArray("level_3.txt");
                tickRate = 7;
                initPlayerPos = getPlayerPos();
                initiateEnemies();
                break;
            default:
                gameWindow.showWinnerMenu();
                stopThread();
        }
    }

    /**
     * Gets the Players coordinates in the grid and stores them in 'int[2] playerPos'
     * @author Benedikt
     */
    protected int[] getPlayerPos(){
        for (int i = 0; i < maxMapColumns; i++){
            for (int j = 0; j < maxMapRows; j++) {
                if(map[i][j] == playerTile){
                    playerPos[0] = i;
                    playerPos[1] = j;
                }
            }
        }
        int[] result = {playerPos[0], playerPos[1]};
        return result;
    }

    /**
     * Method to overwrite the enemies ArrayList with the enemies position given by the 'g's in the map grid
     * @author Simon Bonnie
     */
    private void initiateEnemies() {
        enemies.clear(); // Remove Enemy objects from last session
        for(int i = 0; i < maxMapColumns; i++)
            for (int j = 0; j < maxMapRows; j++)
                if(map[i][j] == enemyTile) enemies.add(new Enemy(i, j, this));
    }

    /**
     * Method to detect if a player collides with an Enemy object and act accordingly
     * @param enemy the Enemy object to be checked for collision
     * @return whether a collision between enemy and player was detected or not
     * @author Simon Bonnie
     */
    private boolean enemyDetection(Enemy enemy) {
        if(playerPos[0] == enemy.getPosX() && playerPos[1] == enemy.getPosY()) { // If players coordinates match with an enemies one ...
            if(flags[4]) { // If death touch is active
                map[enemy.getPosX()][enemy.getPosY()] = playerTile;
                enemies.remove(enemy);
            } else {
                // If player has got an extra life
                if (flags[1]) {
                    flags[1] = false; // Reset extra life
                    map[playerPos[0]][playerPos[1]] = enemyTile;
                    playerPos = initPlayerPos;
                } else {
                    flags[0] = false; // Kill player
                    gameWindow.repaint();
                    gameWindow.showDeathWindow();
                    stopThread();
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
    private void enemyDetection() {
        for (Enemy enemy : enemies)
            if(enemyDetection(enemy)) break;
    }

    /**
     * method for key Actions, gets called every time a mapped Key is pressed
     * To add new Keys they first have to be added to the keymap in the setKeyBindings() function in GameWindow
     * @author Sebastian
     * @param pKey String with the name of the key event constant (for a pKey would be "VK_A")
     *
     */
    protected void newKeyAction(String pKey) {
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
    private void addKeyToList(char theKey){
        if(!lastKeyList.contains(theKey))
        {
            lastKeyList.add(theKey);
        }
    }

    /**
     * method for importing a map as a char array
     * @author Sebastian & Dave
     *
     * @param fileName name of the map to load (has to be in the levels folder)
     * @return charArray of the map at the filename
     */
    private char[][] importMapArray(String fileName){
        char[][] newMap = new char[maxMapColumns][maxMapRows];
        try {
            String content = Files.readString(Paths.get(pathToDataDirectory + File.separator + fileName));
            String[] rows = content.split("\n"); //Split String into String Array consisting of single Rows
            for(int i = 0; i < Math.min(rows.length, maxMapRows); i++ )              //For every row
                for (int o = 0; o < Math.min(rows[i].length(), maxMapColumns); o++) //for every char in the row
                    newMap[o][i] = rows[i].charAt(o);  //insert char into the map array
        } catch(Exception e){e.printStackTrace();}
        return newMap;
    }


}