/**
 * MacPan version 0.1
 * SWE WS 21/22
 */

package lost.macpan.panel;

import lost.macpan.App;
import lost.macpan.utils.ResourceHandler;
import lost.macpan.utils.HighscoreHandler;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Die Klasse WinnerMenu zeigt dem Spieler nach einem Spieldurchlauf den erreichten Score und bietet dem Spieler die Möglichkeit seinen Namen einzugragen
 * Update durch Janosch & William
 *
 * @author fatih
 */
public class WinnerMenu extends JPanel implements HighscoreHandler {
    private App parentFrame;
    private JLabel winLabel;
    private Image winLogo;
    private Image backgroundImg;
    private Container before;
    private JTextField nameInput;
    private final JLabel nameLabel = new JLabel("Name: ");
    private final JLabel scoreLabel = new JLabel("Score: ");
    private int score;
    private JLabel scoreValue;

    public WinnerMenu(App frame, Container beforeMenu, int currentScore) {
        before = beforeMenu;
        parentFrame = frame;
        score = currentScore;

        try {
            winLogo = ImageIO.read(getFileResourcesAsStream("images/panelImages/Win.png"));
            backgroundImg = ImageIO.read(getFileResourcesAsStream("images/panelImages/BackgroundImage.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        winLabel = new JLabel(new ImageIcon(winLogo));

        scoreLabel.setFont(new Font(null, Font.BOLD, 26));
        scoreLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font(null, Font.BOLD, 26));
        nameLabel.setForeground(Color.WHITE);
        nameInput = new JTextField(12);
        scoreValue = new JLabel(""+score);
        scoreValue.setFont(new Font(Font.MONOSPACED,Font.BOLD,28));
        scoreValue.setForeground(Color.WHITE);

        setLayout(null);

        winLabel.setBounds(0, 50, 910, 200);
        nameLabel.setBounds(240,250,101,26);
        scoreLabel.setBounds(240,210,101,26);
        nameInput.setBounds(350, 249,300,40);
        scoreValue.setBounds(350,210,101,26);
        add(winLabel);
        add(scoreValue);
        add(nameLabel);
        add(scoreLabel);
        add(nameInput);
        nameInput.setDocument(new LimitJTextField(12));

        setKeyBindings();
    }

    /**
     * sets the focus on the nameInput Field
     * @author Sebastian
     *
     */
    public void setFocusOnInput(){
        nameInput.requestFocusInWindow();
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

        String vkEnter = "VK_ENTER";
        String vkSpace = "VK_SPACE";


        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), vkEnter);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), vkSpace);


        actionMap.put(vkEnter, new KeyAction(vkEnter));
        actionMap.put(vkSpace, new KeyAction(vkSpace));

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
            newKeyAction(actionEvt.getActionCommand());
        }
    }

    /**
     * method for key Actions, gets called every time a mapped Key is pressed
     * To add new Keys they first have to be added to the keymap in the setKeyBindings() function
     * @author Sebastian
     * @param pKey String with the name of the key event constant (for a pKey would be "VK_A")
     *
     */
    public void newKeyAction(String pKey) {
        switch (pKey){
            case "VK_ENTER":
                saveHighscores(score ,nameInput.getText());
                break;
            case "VK_SPACE":
                saveHighscores(score ,nameInput.getText());
                break;
        }
    }

    /**
     * method for saving Highscores
     * @author Sebastian
     *
     */
    public void saveHighscores(int pScore, String pName) {
        saveNewScore(pScore, pName);
        parentFrame.stopMusic();
        parentFrame.playMusicLooped(0);
        parentFrame.setContentPane(before);
        parentFrame.revalidate();
        before.requestFocusInWindow();
    }

    @Override
    public void paintComponent(Graphics g){
        g.drawImage(backgroundImg, 0, 0, null);
    }

}
