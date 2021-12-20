/**
 * MacPan version 0.1
 * SWE WS 21/22
 */

package lost.macpan.panel;

import lost.macpan.utils.HighscoreHandler;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


/**
 * Die LooserMenu Klasse stellt ein Menü dar, wenn ein Spieler das Spiel verloren hat
 * Update durch Janosch & William
 *
 * @author fatih
 */
public class LooserMenu extends JPanel implements HighscoreHandler {
    private JFrame parentFrame;
    private Container before;
    private JLabel loselabel;
    private Image loseImg;
    private Image backgroundImg;
    private JTextField nameInput;
    private final JLabel nameLabel = new JLabel("Name: ");
    private final JLabel scoreLabel = new JLabel("Score: ");
    private int score;
    private JLabel scoreValue;

    public LooserMenu(JFrame frame, Container beforeMenu, int currentScore) {
        parentFrame = frame;
        before = beforeMenu;
        score = currentScore;

        try {
            loseImg = ImageIO.read(getFileResourcesAsStream("images/panelImages/Loose.png"));
            backgroundImg = ImageIO.read(getFileResourcesAsStream("images/panelImages/BackgroundImage.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        loselabel = new JLabel(new ImageIcon(loseImg));
        scoreLabel.setFont(new Font(null, Font.BOLD, 26));
        scoreLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font(null, Font.BOLD, 26));
        nameLabel.setForeground(Color.WHITE);
        nameInput = new JTextField(12);
        nameInput.setForeground(Color.BLACK);
        nameInput.setBackground(Color.WHITE);
        scoreValue = new JLabel("" + score);
        scoreValue.setFont(new Font(Font.MONOSPACED, Font.BOLD, 28));
        scoreValue.setForeground(Color.WHITE);


        loselabel.setBounds(20, 10, 870, 200);
        nameLabel.setBounds(240, 250, 101, 26);
        scoreLabel.setBounds(240, 210, 101, 26);
        nameInput.setBounds(350, 249, 300, 32);
        scoreValue.setBounds(350, 210, 101, 26);
        add(loselabel);
        add(scoreValue);
        add(nameLabel);
        add(scoreLabel);
        add(nameInput);
        nameInput.setDocument(new LimitJTextField(12));
        setLayout(null);
        setKeyBindings();
    }

    /**
     * sets all the key bindings
     *
     * @author Sebastian
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
     *
     * @author Sebastian
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
     *
     * @param pKey String with the name of the key event constant (for a pKey would be "VK_A")
     * @author Sebastian
     */
    public void newKeyAction(String pKey) {
        switch (pKey) {
            case "VK_ENTER":
                saveHighscores(score ,nameInput.getText());
                break;
            case "VK_SPACE":
                saveHighscores(score, nameInput.getText());
                break;
        }
    }

    /**
     * method for saving Highscores
     * @author Sebastian
     *
     */
    public void saveHighscores(int pScore, String pName) {
        saveNewScore(pScore,pName);
        parentFrame.setContentPane(before);
        parentFrame.revalidate();
    }

    @Override
    public void paintComponent(Graphics g){
        g.drawImage(backgroundImg, 0, 0, null);
    }

}
