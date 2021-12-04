package lost.macpan.game;

import lost.macpan.game.HUD;
import lost.macpan.game.sprites.CoinSprite;
import lost.macpan.game.sprites.EnemySprite;
import lost.macpan.game.sprites.ExitSprite;
import lost.macpan.game.sprites.PlayerSprite;
import lost.macpan.game.sprites.Sprite;
import lost.macpan.panel.*;
import lost.macpan.utils.ResourceHandler;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.DecimalFormat;

/**
 * Class for displaying gameplay
 * @author Leon Wigro
 * @version 0.1.1
 */
public class GameWindow extends JPanel implements ResourceHandler {
    //attributes
    char lastKey;

    private Game game;

    public char[][] currentMap;
    public boolean currentFlags[];
    public int currentScore;

    protected int maxColumns = 32;                  //maximum amount of tiles that can be drawn horizontally
    protected int maxRows = 24;                       //maximum amount of tiles that can be drawn vertically


    private int originalTileSize = 16;              //corresponds to the sprite size
    private int scale = 2;                          //the scale to be used for rendering of sprites (e.g. a (16px)² sprite with scale 2 will be drawn as (32px)²
    public int tileSize = originalTileSize * scale; //tile size and effective sprite size

    private int width = maxColumns * tileSize;      //width of the window (automatically adjusted based on tileSize and maxColumns)
    private int height = maxRows * tileSize;        //height of the window (automatically adjusted based on tileSize and maxRows)



    private int hudHeight = 21;                     //determines the height of the HUD
    public JFrame parentFrame;
    public Container before;


    public BufferedImage path;
    public BufferedImage wall;

    PlayerSprite playerSprite = new PlayerSprite(this);     //handles drawing the player sprite
    EnemySprite enemySprite = new EnemySprite(this);        //handles drawing enemy sprites
    ExitSprite exitSprite = new ExitSprite(this);           //handles drawing the exit sprite
    CoinSprite coinSprite = new CoinSprite(this);           //handles drawing coin sprites
    Sprite sprite = new Sprite(this);                       //handles drawing miscellaneous sprites
    HUD hud = new HUD();                                //handles drawing the in-game HUD



    public GameWindow(JFrame frame, Container beforeMenu){
        fetchSprites();             //assigns sprites
        game = new Game(this);
        parentFrame = frame;
        before = beforeMenu;

        setKeyBindings();

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        game.startThread();
    }



    public int getMaxColumns() {
        return maxColumns;
    }

    public int getMaxRows() {
        return maxRows;
    }

    public void showDeathWindow(){
        LooserMenu looserMenu = new LooserMenu(parentFrame, before);
        parentFrame.setContentPane(looserMenu);
        parentFrame.revalidate();
        //Direkt mit Todes Bildschirm
    }

    public void showWinnerMenu(){
        WinnerMenu winnerMenu = new WinnerMenu(parentFrame, before);
        parentFrame.setContentPane(winnerMenu);
        parentFrame.revalidate();
    }

    /**
     * method for fetching sprites from  the "images" folder and assigning them to the corresponding BufferedImage sprite
     */
    public void fetchSprites() {
        try {
            path = ImageIO.read(getFileResourcesAsStream("images/Path-1.png.png"));
            wall = ImageIO.read(getFileResourcesAsStream("images/Wall.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void allesAktualisieren(){
        currentFlags = game.getFlags();
        currentMap = game.getMap();
        currentScore = game.getScore();
    }

    public void spielFortsetzen(){
        game.spielFortsetzen();
    }


    /**
     * method for drawing a frame <br>
     * can currently only draw sprites, no integration for any overly yet
     * @param g not to be edited
     */
    public void paintComponent(Graphics g){
        allesAktualisieren();
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < maxColumns; i++){       //parses the x-coordinate of "map"
            for (int j = 0; j < maxRows; j++){      //parses the y-coordinate of "map"
                char c = currentMap[i][j];                 //fetches currently examined tile identifier
                if (c == 'p')
                    playerSprite.draw(g2, i, j);    //handles player sprite
                else if (c == 'g')
                    enemySprite.draw(g2, i, j);     //handles enemy sprite
                else if (c == 'x')
                    exitSprite.draw(g2, i, j, currentFlags);      //handles exit sprite
                else if (c == '*')
                    coinSprite.draw(g2, i, j, currentFlags);      //handles coin sprite
                else
                    sprite.draw(g2, i, j, c);       //handles static sprites
            }
        }
        hud.draw(g2, hudHeight, currentScore, tileSize, currentFlags, maxColumns);
        g2.dispose();
    }

    private void setKeyBindings() {
        ActionMap actionMap = getActionMap();
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = getInputMap(condition);

        String vkLeft = "VK_LEFT";
        String vkRight = "VK_RIGHT";
        String vkUP = "VK_UP";

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), vkLeft);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), vkRight);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), vkUP);

        actionMap.put(vkLeft, new KeyAction(vkLeft));
        actionMap.put(vkRight, new KeyAction(vkRight));
        actionMap.put(vkUP, new KeyAction(vkUP));

    }

    private class KeyAction extends AbstractAction {
        public KeyAction(String actionCommand) {
            putValue(ACTION_COMMAND_KEY, actionCommand);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvt) {
            game.keyPressed(actionEvt.getActionCommand());
        }
    }


}
