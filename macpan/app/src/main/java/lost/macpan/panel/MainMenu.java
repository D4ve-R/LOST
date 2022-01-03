/**
 * MacPan version 0.1
 * SWE WS 21/22
 * @authors dave
 */

package lost.macpan.panel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lost.macpan.game.Game;
import lost.macpan.utils.GameSerializer;
import lost.macpan.game.GameWindow;
import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Die Klasse MainMenu stellt das Hauptmenü des Spiels auf dem JPanel dar.
 */
public class MainMenu extends JPanel implements ActionListener, ResourceHandler {

    private final JButton playBtn = new JButton("Spiel Starten");
    private final JButton loadBtn = new JButton("Spiel Laden");
    private final JButton highscoresBtn = new JButton("Highscores");
    private final JButton optionsBtn = new JButton("Optionen");
    private final JButton quitBtn = new JButton("Spiel Beenden");
    private JFrame parentFrame;
    private Image img;
    private JLabel label;
    private JLabel background;
    private Image backgroundImg;


    /**
     * Der Konstruktor MainMenu platziert die Bilder und Buttons, welche zum Hauptmenue gehören auf dem Frame
     * Update durch Janosch & William
     *
     * @param frame
     */
    public MainMenu(JFrame frame) {
        parentFrame = frame;

        try {
            img = ImageIO.read(getFileResourcesAsStream("images/panelImages/MacPan.png"));
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
        setLayout(null);

        background.setBounds(0, 0, 950, 700);
        label.setBounds(175, 50, 600, 200);
        playBtn.setBounds(350, 300, 240, 50);
        loadBtn.setBounds(350, 360, 240, 50);
        highscoresBtn.setBounds(350, 420, 240, 50);
        optionsBtn.setBounds(350, 480, 240, 50);
        quitBtn.setBounds(350, 540, 240, 50);

        add(label);
        add(playBtn);
        add(loadBtn);
        add(highscoresBtn);
        add(optionsBtn);
        add(quitBtn);
        add(background);
        playBtn.addActionListener(this);
        loadBtn.addActionListener(this);
        highscoresBtn.addActionListener(this);
        optionsBtn.addActionListener(this);
        quitBtn.addActionListener(this);
        setKeyBindings();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playBtn) {
            GameWindow gameWindow = new GameWindow(parentFrame, this);
            parentFrame.setContentPane(gameWindow);
            parentFrame.revalidate();

        } else if (e.getSource() == loadBtn) {
            if(Files.exists(Paths.get(System.getProperty("user.home") + File.separator + "LOST" + File.separator + "MacPan.json"))) {
                GameWindow gameWindow = new GameWindow(parentFrame, this);

                Gson gameJson = new GsonBuilder()
                        .registerTypeAdapter(gameWindow.getGame().getClass(), new GameSerializer())
                        .create();

                String inFile = "";
                try {
                    inFile = new String(Files.readAllBytes(Paths.get(System.getProperty("user.home") + File.separator + "LOST" + File.separator + "MacPan.json")));
                } catch (IOException a) {
                    a.printStackTrace();
                }

                if (!inFile.equals("")) {
                    Game savedGame = gameJson.fromJson(inFile, Game.class);

                    gameWindow.getGame().stopThread();
                    savedGame.loadWindow(gameWindow);

                    gameWindow.setGame(savedGame);

                    gameWindow.getGame().startThread();
                }
                parentFrame.setContentPane(gameWindow);
                parentFrame.revalidate();
            }

        } else if (e.getSource() == highscoresBtn) {
            HighscoreMenu highscoreMenu = new HighscoreMenu(parentFrame, this.parentFrame.getContentPane());
            parentFrame.setContentPane(highscoreMenu);
            parentFrame.revalidate();

        } else if (e.getSource() == optionsBtn) {
            OptionsMenu optionsMenu = new OptionsMenu(parentFrame, this.parentFrame.getContentPane());
            parentFrame.setContentPane(optionsMenu);
            parentFrame.revalidate();

        } else if (e.getSource() == quitBtn) {
            QuitMenu quitMenu = new QuitMenu(parentFrame, this.parentFrame.getContentPane());
            parentFrame.setContentPane(quitMenu);
            parentFrame.revalidate();
        }
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

        String vkW = "VK_W";
        String vkS = "VK_S";

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), vkW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), vkS);

        actionMap.put(vkW, new KeyAction(vkW));
        actionMap.put(vkS, new KeyAction(vkS));
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
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        switch (pKey) {
            case "VK_W":
                manager.focusPreviousComponent();
                break;
            case "VK_S":
                manager.focusNextComponent();
                break;
        }
    }

}
