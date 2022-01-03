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
import lost.macpan.utils.MenuNavigationHandler;
import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Die Klasse MainMenu stellt das Hauptmenü des Spiels auf dem JPanel dar.
 */
public class MainMenu extends JPanel implements ActionListener, ResourceHandler, MenuNavigationHandler {

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

        setKeyBindings(getActionMap(),getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playBtn) {
            GameWindow gameWindow = new GameWindow(parentFrame, this);
            parentFrame.setContentPane(gameWindow);
            parentFrame.revalidate();
            gameWindow.requestFocusInWindow();

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
                gameWindow.requestFocusInWindow();
            }

        } else if (e.getSource() == highscoresBtn) {
            HighscoreMenu highscoreMenu = new HighscoreMenu(parentFrame, this.parentFrame.getContentPane());
            parentFrame.setContentPane(highscoreMenu);
            parentFrame.revalidate();
            highscoreMenu.requestFocusInWindow();

        } else if (e.getSource() == optionsBtn) {
            OptionsMenu optionsMenu = new OptionsMenu(parentFrame, this.parentFrame.getContentPane());
            parentFrame.setContentPane(optionsMenu);
            parentFrame.revalidate();
            optionsMenu.requestFocusInWindow();

        } else if (e.getSource() == quitBtn) {
            QuitMenu quitMenu = new QuitMenu(parentFrame, this.parentFrame.getContentPane());
            parentFrame.setContentPane(quitMenu);
            parentFrame.revalidate();
            quitMenu.requestFocusInWindow();
        }
    }

}
