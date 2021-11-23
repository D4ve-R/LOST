package lost.macpan.game;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import lost.macpan.game.sprites.*;
import lost.macpan.utils.ResourceHandler;
import lost.macpan.game.LevelClass;

/**
 * Class for displaying gameplay
 * @author Leon Wigro
 * @version 0.1
 */
public class GameWindow extends JPanel implements Runnable, ResourceHandler{
    //attributes
    private int originalTileSize = 16;              //corresponds to the sprite size
    private int scale = 2;                          //the scale to be used for rendering of sprites (e.g. a (16px)² sprite with scale 2 will be drawn as (32px)²
    public int tileSize = originalTileSize * scale; //tile size and effective sprite size
    private int maxColumns = 32;                    //maximum amount of tiles that can be drawn horizontally
    private int maxRows = 24;                       //maximum amount of tiles that can be drawn vertically
    private int width = maxColumns * tileSize;      //width of the window (automatically adjusted based on tileSize and maxColumns)
    private int height = maxRows * tileSize;        //height of the window (automatically adjusted based on tileSize and maxRows)
    private int framerate = 60;                     //rate of draw loop repetitions
    protected char[][] map;                         //char-array from which a frame will be drawn
    protected LevelClass level;                     //object from which the data to-be-displayed will be read
    private Thread thread;

    public BufferedImage boostedCoin;
    public BufferedImage normalCoin;
    public BufferedImage boostItem;
    public BufferedImage enemy1;
    public BufferedImage enemy2;
    public BufferedImage enemy3;
    public BufferedImage enemy4;
    public BufferedImage exit1;
    public BufferedImage exit2;
    public BufferedImage exit3;
    public BufferedImage exit4;
    public BufferedImage exit5;
    public BufferedImage exit6;
    public BufferedImage exit7;
    public BufferedImage exit8;
    public BufferedImage freeze;
    public BufferedImage key;
    public BufferedImage pan;
    public BufferedImage path;
    public BufferedImage player1;
    public BufferedImage player2;
    public BufferedImage player3;
    public BufferedImage player4;
    public BufferedImage shield;
    public BufferedImage speed;
    public BufferedImage wall;

    PlayerSprite playerSprite = new PlayerSprite(this);     //handles drawing the player sprite
    EnemySprite enemySprite = new EnemySprite(this);        //handles drawing enemy sprites
    ExitSprite exitSprite = new ExitSprite(this);           //handles drawing the exit sprite
    CoinSprite coinSprite = new CoinSprite(this);           //handles drawing coin sprites
    Sprite sprite = new Sprite(this);                       //handles drawing miscellaneous sprites

    public boolean isUnlocked = false;          //tracks whether the exit has been unlocked
    public boolean boost = false;               //tracks whether coin boost is active

    /**
     * method for fetching Sprites from  the "images" folder and assigning them to the corresponding BufferedImage sprite
     */
    public void fetchSprites() {
        try {
            boostedCoin = ImageIO.read(getFileResourcesAsStream("images/Coin boost.png"));
            normalCoin = ImageIO.read(getFileResourcesAsStream("images/Coin normal.png"));
            boostItem = ImageIO.read(getFileResourcesAsStream("images/Boost-1.png.png"));
            enemy1 = ImageIO.read(getFileResourcesAsStream("images/enemy-1.png.png"));
            enemy2 = ImageIO.read(getFileResourcesAsStream("images/enemy-2.png.png"));
            enemy3 = ImageIO.read(getFileResourcesAsStream("images/enemy-3.png.png"));
            enemy4 = ImageIO.read(getFileResourcesAsStream("images/enemy-4.png.png"));
            exit1 = ImageIO.read(getFileResourcesAsStream("images/Exit-1.png.png"));
            exit2 = ImageIO.read(getFileResourcesAsStream("images/Exit-2.png.png"));
            exit3 = ImageIO.read(getFileResourcesAsStream("images/Exit-3.png.png"));
            exit4 = ImageIO.read(getFileResourcesAsStream("images/Exit-4.png.png"));
            exit5 = ImageIO.read(getFileResourcesAsStream("images/Exit-5.png.png"));
            exit6 = ImageIO.read(getFileResourcesAsStream("images/Exit-6.png.png"));
            exit7 = ImageIO.read(getFileResourcesAsStream("images/Exit-7.png.png"));
            exit8 = ImageIO.read(getFileResourcesAsStream("images/Exit-8.png.png"));
            freeze = ImageIO.read(getFileResourcesAsStream("images/Freeze.png"));
            key = ImageIO.read(getFileResourcesAsStream("images/Key.png"));
            pan = ImageIO.read(getFileResourcesAsStream("images/Pan.png"));
            path = ImageIO.read(getFileResourcesAsStream("images/Path.png"));
            player1 = ImageIO.read(getFileResourcesAsStream("images/Player-1.png.png"));
            player2 = ImageIO.read(getFileResourcesAsStream("images/Player-2.png.png"));
            player3 = ImageIO.read(getFileResourcesAsStream("images/Player-3.png.png"));
            player4 = ImageIO.read(getFileResourcesAsStream("images/Player-4.png.png"));
            shield = ImageIO.read(getFileResourcesAsStream("images/Shield.png"));
            speed = ImageIO.read(getFileResourcesAsStream("images/Speed.png"));
            wall = ImageIO.read(getFileResourcesAsStream("images/Wall.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public GameWindow(){
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
    }

    /**
     * Thread starter
     */
    public void start(){
        thread = new Thread(this);
        thread.start();
    }


    /**
     * draw loop
     */
    @Override
    public void run() {
        level = gameLogic();                                    //TO BE REPLACED currently sets "level" to be a debug LevelClass
        map = level.map;                                        //updates "map" from "level"
        isUnlocked = level.flags[6];                            //updates "isUnlocked" from "level"
        fetchSprites();                                         //assigns sprites



        double frametime = 1000000000 / framerate;              //determines the time span any frame should be displayed
        double nextDrawTime = System.nanoTime() + frametime;    //determines at which point in time the next frame should start to be drawn
        while(thread != null){                                 //start of the draw loop
            gameLogic();                                        //TO BE REPLACED see above
            repaint();                                          //draws the frame
            try {
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000;    //determines for how long the current frame should continue to be displayed
                /*
                System.out.println("Maximum possible framerate (only up to " +
                        framerate + " displayed): " +
                        1000000000 / (framerate - remainingTime));      //TO BE REMOVED returns maximum possible frame rate going by current frame time
                 */
                if (remainingTime < 0)                  //determines how long the thread should sleep for
                    remainingTime = 0;                  //with negative or 0 remaining time the thread should sleep for 0ns
                thread.sleep((long) remainingTime);     //puts thread to sleep for the allotted time
                nextDrawTime += frametime;              //determines when the next frame should finish
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * generic method TO BE REPLACED
     * @return levelClass example
     */
    public LevelClass gameLogic(){
        char[][] newMap = new char[32][24];
        int newScore = 42069;
        boolean[] newFlags = new boolean[8];
        double newTimer = 69.0;
        double newFreezeTimer = 1.1666666;

        /*
        for (int i = 0; i < 32; i++){
            newMap[i][0] = 'g';
            newMap[i][1] = '0';
            newMap[i][2] = '.';
            newMap[i][3] = '*';
            newMap[i][4] = 'k';
            newMap[i][5] = 'x';
            newMap[i][6] = 'a';
            newMap[i][7] = 'b';
            newMap[i][8] = 'c';
            newMap[i][9] = 'd';
            newMap[i][10] = 'e';
            newMap[i][11] = 'p';
            for (int j = 12; j < 24; j++){
                newMap[i][j] = 'h';
            }
        }
        */

        if (map == null) {
            newMap = importMapArray("test.txt"); //import the map test
        }else{
            newMap = map;
        }


        newFlags[0] = true;
        for (int i = 1; i < 8; i++) {
            newFlags[i] = true;
        }

        return new LevelClass(newMap, newScore, newFlags, newTimer, newFreezeTimer);
    }

    /**
     * method for drawing a frame <br>
     * can currently only draw sprites, no integration for any overly yet
     * @param g not to be edited
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < maxColumns; i++){       //parses the x-coordinate of "map"
            for (int j = 0; j < maxRows; j++){      //parses the y-coordinate of "map"
                char c = map[i][j];                 //fetches currently examined tile identifier
                if (c == 'p')
                    playerSprite.draw(g2, i, j);    //handles player sprite
                else if (c == 'g')
                    enemySprite.draw(g2, i, j);     //handles enemy sprite
                else if (c == 'x')
                    exitSprite.draw(g2, i, j);      //handles exit sprite
                else if (c == 'h')
                    sprite.draw(g2, i, j, wall);    //draws wall sprite
                else if (c == '*')
                    coinSprite.draw(g2, i, j);      //handles coin sprite
                else if (c == 'k') {
                    sprite.draw(g2, i, j, path);    //underlays path sprite
                    sprite.draw(g2, i, j, key);     //draws key sprite
                }
                else if (c == 'a'){
                    sprite.draw(g2, i, j, path);    //underlays path sprite
                    sprite.draw(g2, i, j, speed);   //draws speed boost sprite
                }
                else if (c == 'b') {
                    sprite.draw(g2, i, j, path);    //underlays path sprite
                    sprite.draw(g2, i, j, freeze);  //draws freeze sprite
                }
                else if (c == 'c') {
                    sprite.draw(g2, i, j, path);    //underlays path sprite
                    sprite.draw(g2, i, j, boostItem);//draws coin boost sprite
                }
                else if (c == 'd') {
                    sprite.draw(g2, i, j, path);    //underlays path sprite
                    sprite.draw(g2, i, j, shield);  //draws shield sprite
                }
                else if (c == 'e') {
                    sprite.draw(g2, i, j, path);    //underlays path sprite
                    sprite.draw(g2, i, j, pan);     //draws pan sprite
                }
                else if (c == '0')
                    sprite.draw(g2, i, j, path);    //draws path sprite
            }
        }
    }

    private static String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
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

        char[][] map = new char[32][24];
        String mapString = "";

        try {
            InputStream inputStream = getFileResourcesAsStream("levels/"+pFileName);
            mapString = convertStreamToString(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] rows = mapString.split("\n"); //Split String into String Array consisting of single Rows

        for(int i = 0; i < rows.length;i++ ){           //For every row
            for(int o = 0; o < rows[i].length();o++ ){  //for every char in the row
                map[o][i]=rows[i].charAt(o);            //insert char into the map array
            }
        }
        System.out.println("map geladen");
        return map;
    }

}
