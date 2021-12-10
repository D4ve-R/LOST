package lost.macpan.panel;

import lost.macpan.utils.ResourceHandler;

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
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Writer;

/**
 * Die Klasse WinnerMenu zeigt dem Spieler nach einem Spieldurchlauf den erreichten Score und bietet dem Spieler die Möglichkeit seinen Namen einzugragen
 * Update durch Janosch & William
 *
 * @author fatih
 */
public class WinnerMenu extends JPanel implements ActionListener, ResourceHandler {
    private JFrame parentFrame;
    private JLabel label;
    private Image img;
    private JLabel background;
    private Image backgroundImg;
    private Timer timer;
    private Container before;
    private JTextField nameInput;
    private JLabel nameLabel;
    private Image nameImage;
    private Image scoreImage;
    private JLabel scoreLabel;
    private int score;
    private JLabel scoreValue;
    private InputStream inputStream;
    private String highscores;
    private String[] parts;


    public WinnerMenu(JFrame frame, Container beforeMenu, int currentScore) {
        int delay = 5000;
        timer = new Timer(delay, this);
        before = beforeMenu;
        parentFrame = frame;
        score = currentScore;
        try {
            img = ImageIO.read(getFileResourcesAsStream("images/panelImages/Win.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (img != null) {
            label = new JLabel(new ImageIcon(img));
        }
        try {
            backgroundImg = ImageIO.read(getFileResourcesAsStream("images/panelImages/BackgroundImage.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (backgroundImg != null) {
            background = new JLabel(new ImageIcon(backgroundImg));
        }
        try {
            nameImage = ImageIO.read(getFileResourcesAsStream("images/panelImages/nameLabel.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (nameImage != null) {
            nameLabel = new JLabel(new ImageIcon(nameImage));
        }
        try {
            scoreImage = ImageIO.read(getFileResourcesAsStream("images/panelImages/scoreLabel.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (scoreImage != null) {
            scoreLabel = new JLabel(new ImageIcon(scoreImage));
        }
        nameInput = new JTextField();
        scoreValue = new JLabel(""+score);
        scoreValue.setFont(new Font(Font.MONOSPACED,Font.BOLD,28));
        scoreValue.setForeground(Color.WHITE);


        setLayout(null);
        background.setBounds(0, 0, 950, 700);
        label.setBounds(175, 50, 600, 200);
        nameLabel.setBounds(290,250,101,26);
        scoreLabel.setBounds(290,210,101,26);
        nameInput.setBounds(400, 249,200,20);
        scoreValue.setBounds(400,210,101,26);
        add(label);
        add(scoreValue);
        add(nameLabel);
        add(scoreLabel);
        add(background);
        add(nameInput);
        timer.start();

        setKeyBindings();
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
                //saveHighscores();
                System.out.println("EnterPressed"); //TODO: Remove debugging output
                break;
            case "VK_SPACE":
                //saveHighscores();
                System.out.println("Space Pressed");//TODO: Remove debugging output
                break;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            inputStream = getFileResourcesAsStream("highscores/Highscores.txt");
            highscores = convertStreamToString(inputStream);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        parts = highscores.split("\n"); //txt Inhalt als Array
        String[] all = new String[10];
        for (int i = 0; i < parts.length; i++) { //Alle einträge in ein Array mit 10 stellen überschreiben
            all[i] = parts[i];
        }
        if (parts.length == 10) { //Bereits 10 Einträge
            if (getScoreVal(parts[parts.length-1]) < score) {//neuer Highscore
                if (nameInput.getText().equals("")) { //Name eingegeben?
                    all[parts.length-1] = score+";"+"Anonym";
                } else all[parts.length-1] = score+";"+nameInput.getText();
                //hier ggf sortieren !!!!!
                sortScore(all);
            }
        } else { //noch keine 10 Einträge
            if (nameInput.getText().equals("")) {
                all[parts.length] = score+";"+"Anonym";
            } else all[parts.length] = score+";"+nameInput.getText();
            //hier sortieren !!!!!!
            sortScore(all);
        }
        //Txt überschreiben mit neuen Werten
        //inhalt löschen
        try {
            Writer writer = new FileWriter("macpan/app/src/main/resources/highscores/Highscores.txt", false);
            BufferedWriter writer1 = new BufferedWriter(writer);
            writer1.write("");
            writer1.close();
            writer.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //Werte von all in txt übernehmen
        try {
            Writer writer = new FileWriter("macpan/app/src/main/resources/highscores/Highscores.txt", true);
            for (int i = 0; i < all.length; i++) {
                if (all[i] != null) {
                    writer.write(all[i] + "\n");
                } else break;
            }
            writer.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        timer.stop();
        parentFrame.setContentPane(before);
        parentFrame.revalidate();
    }
    private int getScoreVal(String score) {
        if (score.equals("")) return 0;
        String num = score.substring(0, score.indexOf(';'));
        int numInt = Integer.parseInt(num);
        return numInt;
    }
    private void sortScore(String[] scores) {
        int n = 0;
        for (int i = 0; i < scores.length; i++) {
            if (scores[i] == null) {
                break;
            } else n++;
        }
        if (scores[0] == null) {
            return;
        }
        String temp = "";
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n-i); j++) {
                if (getScoreVal(scores[j-1]) < getScoreVal(scores[j])) {
                    temp = scores[j-1];
                    scores[j-1] = scores[j];
                    scores[j] = temp;
                }
            }
        }
    }
}
