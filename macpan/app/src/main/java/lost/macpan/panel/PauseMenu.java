/**
 * MacPan version 0.1
 * SWE WS 21/22
 */

package lost.macpan.panel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lost.macpan.game.Game;
import lost.macpan.utils.CustomButton;
import lost.macpan.utils.GameSerializer;
import lost.macpan.game.GameWindow;

import lost.macpan.utils.MenuNavigationHandler;
import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Die Klasse MainMenu stellt das Pausemenü des Spiels auf dem JPanel dar.
 *
 * @author Fatih
 */
public class PauseMenu extends JPanel implements ActionListener, ResourceHandler, MenuNavigationHandler {
    private final CustomButton playBtn = new CustomButton("Spiel Fortsetzen");
    private final CustomButton loadBtn = new CustomButton("Spiel Laden");
    private final CustomButton saveBtn = new CustomButton("Spiel Speichern");
    private final CustomButton highscoresBtn = new CustomButton("Highscores");
    private final CustomButton optionsBtn = new CustomButton("Optionen");
    private final CustomButton backBtn = new CustomButton("Zur\u00fcck zum Hauptmen\u00fc");
    private JFrame parentFrame;
    private JLabel label;
    private Image img;
    private JLabel background;
    private Image backgroundImg;
    private GameWindow gameWindow;

    /**
     * Der Konstruktor MainMenu platziert die Bilder und Buttons, welche zum Pausemenue gehören auf dem Frame
     * Update durch Janosch & William
     *
     * @param frame
     */
    public PauseMenu(JFrame frame, GameWindow gameWindows) {
        parentFrame = frame;
        gameWindow = gameWindows;
        try {
            img = ImageIO.read(getFileResourcesAsStream("images/panelImages/Pause.png"));
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
        playBtn.setBounds(345, 250, 300, 50);
        loadBtn.setBounds(345, 310, 300, 50);
        saveBtn.setBounds(345, 370, 300, 50);
        highscoresBtn.setBounds(345, 430, 300, 50);
        optionsBtn.setBounds(345, 490, 300, 50);
        backBtn.setBounds(295, 550, 400, 50);
        add(label);
        add(playBtn);
        add(loadBtn);
        add(saveBtn);
        add(highscoresBtn);
        add(optionsBtn);
        add(backBtn);
        add(background);
        playBtn.addActionListener(this);
        loadBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        highscoresBtn.addActionListener(this);
        optionsBtn.addActionListener(this);
        backBtn.addActionListener(this);

        setKeyBindings(getActionMap(),getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playBtn) {
            parentFrame.setContentPane(gameWindow);
            parentFrame.revalidate();

            gameWindow.spielFortsetzen();
            gameWindow.setFocusable(true);
            gameWindow.grabFocus();

        } else if (e.getSource() == loadBtn) {
            if(Files.exists(Paths.get(System.getProperty("user.home") + File.separator + "LOST" + File.separator + "MacPan.json"))) {
                Gson gameJson = new GsonBuilder()
                        .registerTypeAdapter(gameWindow.getGame().getClass(), new GameSerializer())
                        .create();

                String inFile = "";
                try {
                    inFile = Files.readString(Paths.get(System.getProperty("user.home") + File.separator + "LOST" + File.separator + "MacPan.json")); //get game data from user.home/LOST/MacPan.json
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

                gameWindow.spielFortsetzen();
                gameWindow.setFocusable(true);
                gameWindow.grabFocus();
            }

        } else if (e.getSource() == saveBtn) {
            Gson gameJson = new GsonBuilder()
                    .registerTypeAdapter(gameWindow.getGame().getClass(), new GameSerializer())
                    .setPrettyPrinting()
                    .create();
            String data = gameJson.toJson(gameWindow.getGame());

            if (Files.notExists(Path.of(System.getProperty("user.home")  + File.separator + "LOST"))) {
                try{
                    Files.createDirectories(Path.of(System.getProperty("user.home")  + File.separator +  "LOST")); //create user's gamedata in folder user.home/LOST
                } catch (IOException a) {
                    a.printStackTrace();}
            }

            try{
                FileWriter writer = new FileWriter(System.getProperty("user.home") + File.separator + "LOST" + File.separator + "MacPan.json"); //save game data in user.home/LOST/MacPan.json
                writer.write(data);
                writer.close();
            } catch (IOException a){
                a.printStackTrace();
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

        } else if (e.getSource() == backBtn) {
            MainMenu mainMenu = new MainMenu(parentFrame);
            parentFrame.setContentPane(mainMenu);
            parentFrame.revalidate();
            mainMenu.requestFocusInWindow();
        }
    }
}
