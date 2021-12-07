package lost.macpan.game;


import lost.macpan.utils.ResourceHandler;

import java.io.InputStream;
import java.text.DecimalFormat;

/**
 * Main Game class, handels all the actions and logic
 *
 * @author Sebastian
 */
public class Game implements Runnable, ResourceHandler {

    private  int[] playerPos;

    private int maxColumns;                 //maximum amount of tiles that can be drawn horizontally
    private int maxRows;                    //maximum amount of tiles that can be drawn vertically

    private GameWindow gameWindow;

    private final int framerate = 60;                       //rate of which a new frame is drawn in times per second ("framerate = 60" means 60 times per second)
    private final int tickrate = 4;                         //rate of which the logic is called in times per second ("tickrate = 2" means 2 times per second)

    private Thread thread;
    private boolean gamePaused = false;
    private boolean threadRunning;

    private char[][] map;                            //char-array of the map

    private int score;                               //for keeping track of the score

    private char lastKey;

    //Cooldown for Boosts in seconds
    private final double SpeedCooldown = 5;
    private final double DeathTouchCooldown = 5;
    private final double CoinBoostCooldown = 5;
    private final double FreezeCooldown = 5;


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
    public boolean flags[];

    /**
     * Constructor
     * @author Sebastian
     * @param pGameWindow the GameWindow
     */
    public Game(GameWindow pGameWindow){
        gameWindow = pGameWindow;
        maxRows = pGameWindow.getMaxRows();
        maxColumns = pGameWindow.getMaxColumns();
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
        map = importMapArray("test.txt"); //import the map test
        threadRunning = true;
        thread = new Thread(this);
        thread.start();

        getPlayerPos();

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
                    gameLogic((int)contframeCounter / (framerate/tickrate));                        //Game Logic is called with a iterating number, starts with 0
                    tickCounter++;
                }

                gameWindow.repaint();                   //draws the frame

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
            }
            else {
                timeOld = System.currentTimeMillis();
            }

        }
        //System.out.println("Loop beendet");
    }


    /**
     * Internal Timers for reseting the Flags
     *
     */
    private int TimerSpeed = 0;
    private int TimerDeathTouch = 0;
    private int TimerCoinBoost = 0;
    private int TimerFreeze = 0;

    /**
     * method for the Logic of the game, gets called every tickrate times per second
     * @author Sebastian
     *
     * @param pContLogicCounter should be called with a continuously rising number
     */
    public void gameLogic(int pContLogicCounter){

        //SpeedBoost
        if(flags[2] && TimerSpeed == 0){
            TimerSpeed = (int)(SpeedCooldown * tickrate) + 1;
        }
        if(flags[2] && TimerSpeed == 1){
            flags[2] = false;
        }
        if(TimerSpeed > 0){
            TimerSpeed = TimerSpeed -1;
        }

        //Death Touch
        if(flags[4] && TimerDeathTouch == 0){
            TimerDeathTouch = (int)(DeathTouchCooldown * tickrate) + 1;
        }
        if(flags[4] && TimerDeathTouch == 1){
            flags[4] = false;
        }
        if(TimerDeathTouch > 0){
            TimerDeathTouch = TimerDeathTouch -1;
        }

        //CoinBoost
        if(flags[5] && TimerCoinBoost == 0){
            TimerCoinBoost = (int)(CoinBoostCooldown * tickrate) + 1;
        }
        if(flags[5] && TimerCoinBoost == 1){
            flags[5] = false;
        }
        if(TimerCoinBoost > 0){
            TimerCoinBoost = TimerCoinBoost -1;
        }

        //Freeze
        if(flags[7] && TimerFreeze == 0){
            TimerFreeze = (int)(FreezeCooldown * tickrate) +1;
        }
        if(flags[7] && TimerFreeze == 1){
            flags[7] = false;
        }
        if(TimerFreeze > 0){
            TimerFreeze = TimerFreeze -1;
        }

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
            move(lastKey);
            lastKey = 'o';
        }
        else {
            if(pContLogicCounter % 2 == 0){
                move(lastKey);
                lastKey = 'o';
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
        switch (pKey) {
            case "VK_ESCAPE" -> spielPausieren();
            case "VK_W" -> lastKey = 'w';
            case "VK_A" -> lastKey = 'a';
            case "VK_S" -> lastKey = 's';
            case "VK_D" -> lastKey = 'd';
        }
    }


    /**
     *
     * @author Benedikt
     */
    public void move(char key){
        if(key == 'w') {
            moveToNew(0,-1);
        } else if(key == 's'){
            moveToNew(0,1);
        } else if(key == 'a'){
            moveToNew(-1,0);
        } else if(key == 'd'){
            moveToNew(1 ,0);
        }
    }

    /**
     *
     * @author Benedikt
     */
    public void moveToNew(int x, int y) {
        char onNewPos = map[playerPos[0]+x][playerPos[1]+y];
        if(onNewPos == 'h') { // Momentan
            System.out.println("Wand im weg");
        }
        else{
            if(onNewPos == '*'){
                if(flags[5]){
                    score += 20;
                }else{
                    score +=10;
                }

            }
            else if(onNewPos == 'g'){
                if(!flags[4]) {     //if not death touch
                    if (flags[1]) {
                        flags[1] = false;
                    } else {
                        flags[0] = false;
                        stopThread();
                        gameWindow.showDeathWindow();
                    }
                }
                else {      //if death touch
                            //currently nothing happens
                }
            }
            else if(onNewPos == 'k'){
                flags[3] = true;
                flags[6] = true;
            }else if(onNewPos == 'x') {
                if(!flags[3] == true){
                    return;
                }else{
                    stopThread();
                    gameWindow.showWinnerMenu();
                    //Bildschirm (Todes oder Erfolgs)
                }
            }
            else if(onNewPos == 'a') {//Geschwindigkeitsbuff
                flags[2] = true;
            } else if(onNewPos == 'b') {//Gegner einfrieren
                flags[7] = true;
            } else if(onNewPos == 'c'){//Münzboost
                flags[5] = true;
            } else if(onNewPos == 'd'){//Zusatzleben
                flags[1] = true;
            }else if(onNewPos == 'e'){//Todesberührung
                flags[4] = true;
            }
            geh(x,y);
        }
    }

    /**
     *
     * @author Benedikt
     */
    public void geh(int x, int y){
        map[playerPos[0]][playerPos[1]] = '.';
        playerPos[0] += x;
        playerPos[1] += y;
        map[playerPos[0]][playerPos[1]] = 'p';
    }

    /**
     *
     * @author Benedikt
     */
    public void getPlayerPos(){
        playerPos = new int [2];

        for (int i = 0; i < maxColumns; i++){
            for (int j = 0; j < maxRows; j++) {
                if(map[i][j] == 'p'){
                    playerPos[0] = i;
                    playerPos[1] = j;
                    System.out.println(i + "und" + j);
                    return;
                }
            }
        }
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

        for(int i = 0; i < Math.min(rows.length,maxRows);i++ ) {           //For every row
            for (int o = 0; o < Math.min(rows[i].length(),maxColumns); o++) {  //for every char in the row
                map[o][i] = rows[i].charAt(o);            //insert char into the map array
            }
        }
        return map;
    }
}
