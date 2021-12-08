package lost.macpan.game;

import lost.macpan.game.sprites.*;
import lost.macpan.panel.*;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Class for displaying gameplay
 * @author Leon Wigro
 * @version 0.1.1
 */
public class GameWindow extends JPanel {
    //attributes

    private Game game;

    private char[][] currentMap;
    private boolean currentFlags[];
    private int currentScore;

    private int maxColumns = 32;                    //maximum amount of tiles that can be drawn horizontally
    private int maxRows = 24;                       //maximum amount of tiles that can be drawn vertically


    private int originalTileSize = 16;              //corresponds to the sprite size
    private int scale = 2;                          //the scale to be used for rendering of sprites (e.g. a (16px)² sprite with scale 2 will be drawn as (32px)²
    private int tileSize = originalTileSize * scale; //tile size and effective sprite size

    private int width = maxColumns * tileSize;      //width of the window (automatically adjusted based on tileSize and maxColumns)
    private int height = maxRows * tileSize;        //height of the window (automatically adjusted based on tileSize and maxRows)



    private int hudHeight = 21;                     //determines the height of the HUD
    private JFrame parentFrame;
    private Container before;


    PlayerSprite playerSprite = new PlayerSprite(tileSize);     //handles drawing the player sprite
    EnemySprite enemySprite = new EnemySprite(tileSize);        //handles drawing enemy sprites
    ExitSprite exitSprite = new ExitSprite(tileSize);           //handles drawing the exit sprite
    CoinSprite coinSprite = new CoinSprite(tileSize);           //handles drawing coin sprites
    Sprite sprite = new Sprite(tileSize);                       //handles drawing miscellaneous sprites
    HUD hud = new HUD();                                        //handles drawing the in-game HUD


    /**
     * Constructor
     *
     * @param frame the parent Jframe
     * @param beforeMenu the container of the previous menu
     */
    public GameWindow(JFrame frame, Container beforeMenu){
        game = new Game(this);
        parentFrame = frame;
        before = beforeMenu;

        setKeyBindings();               //Set all Key bindings

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        game.startThread();
    }


    /**
     * returns the maxColumns
     * @author Sebastian
     * @return int maxColumns
     */
    public int getMaxColumns() {
        return maxColumns;
    }

    /**
     * returns the maxRows
     * @author Sebastian
     * @return int maxRows
     */
    public int getMaxRows() {
        return maxRows;
    }

    /**
     * shows the death window
     * @author Sebastian
     *
     */
    public void showDeathWindow(){
        LooserMenu looserMenu = new LooserMenu(parentFrame, before);
        parentFrame.setContentPane(looserMenu);
        parentFrame.revalidate();
    }

    /**
     * shows the Winner window
     * @author Sebastian
     *
     */
    public void showWinnerMenu(){
        WinnerMenu winnerMenu = new WinnerMenu(parentFrame, before);
        parentFrame.setContentPane(winnerMenu);
        parentFrame.revalidate();
    }

    /**
     * shows the pause window
     * @author Sebastian
     *
     */
    public void showPauseMenu() {
        PauseMenu pauseMenu = new PauseMenu(parentFrame, this);
        parentFrame.setContentPane(pauseMenu);
        parentFrame.revalidate();
    }

    /**
     * gets the current score, map and flags from the game Object
     * @author Sebastian
     *
     */
    public void allesAktualisieren(){
        currentFlags = game.getFlags();
        currentMap = game.getMap();
        currentScore = game.getScore();
    }

    /**
     * Calls the resume game method on the game Object
     * @author Sebastian
     *
     */
    public void spielFortsetzen(){
        game.spielFortsetzen();
    }


    /**
     * method for drawing a frame
     * can currently only draw sprites, no integration for any overly yet
     * @param g not to be edited
     */
    public void paintComponent(Graphics g){
        allesAktualisieren();                       //Updates all the infromation
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

    /**
     * sets all the key bindings
     * @author Sebastian
     *
     */
    private void setKeyBindings() {
        ActionMap actionMap = getActionMap();
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = getInputMap(condition);

        String vkW = "VK_W";
        String vkA = "VK_A";
        String vkS = "VK_S";
        String vkD = "VK_D";
        String vkESCAPE = "VK_ESCAPE";

        String released = "_released";

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0,false), vkW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0,false), vkA);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0,false), vkS);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0,false), vkD);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0,false), vkESCAPE);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0,true), vkW + released);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0,true), vkA+ released);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0,true), vkS+ released);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0,true), vkD+ released);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0,true), vkESCAPE+ released);

        actionMap.put(vkW, new KeyAction(vkW));
        actionMap.put(vkA, new KeyAction(vkA));
        actionMap.put(vkS, new KeyAction(vkS));
        actionMap.put(vkD, new KeyAction(vkD));
        actionMap.put(vkESCAPE, new KeyAction(vkESCAPE));

        actionMap.put(vkW + released, new KeyAction(vkW+ released));
        actionMap.put(vkA+ released, new KeyAction(vkA+ released));
        actionMap.put(vkS+ released, new KeyAction(vkS+ released));
        actionMap.put(vkD+ released, new KeyAction(vkD+ released));
        actionMap.put(vkESCAPE+ released, new KeyAction(vkESCAPE+ released));


    }

    /**
     * Class for handling the Key actions and calling the newKeyAction method of the game object to pass the action allong
     * @author Sebastian
     *
     */
    private class KeyAction extends AbstractAction {
        public KeyAction(String actionCommand) {
            putValue(ACTION_COMMAND_KEY, actionCommand);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvt) {
            game.newKeyAction(actionEvt.getActionCommand());
        }
    }

}
