package lost.macpan.panel;
import com.google.gson.*;
import lost.macpan.game.Game;
import lost.macpan.game.GameSerializer;
import lost.macpan.game.GameWindow;

import lost.macpan.utils.ResourceHandler;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Die Klasse MainMenu stellt das Pausemenü des Spiels auf dem JPanel dar.
 *
 * @author Fatih
 */
public class PauseMenu extends JPanel implements ActionListener, ResourceHandler {
    // Erstellen der einzelnen Buttons
    private final JButton playBtn = new JButton("Spiel Fortsetzen");
    private final JButton loadBtn = new JButton("Spiel Laden");
    private final JButton saveBtn = new JButton("Spiel Speichern");
    private final JButton highscoresBtn = new JButton("Highscores");
    private final JButton optionsBtn = new JButton("Optionen");
    private final JButton backBtn = new JButton("Zurück zum Hauptmenü");
    /*
        parentFrame = Frame auf dem alles abgebildet wird; mithilfe von label wird ein Bild über den Buttons gezeigt
     */
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
        // Positionierung der Buttons und Labels
        background.setBounds(0, 0, 960, 700);
        label.setBounds(175, 50, 600, 200);
        playBtn.setBounds(395, 250, 160, 50);
        loadBtn.setBounds(395, 310, 160, 50);
        saveBtn.setBounds(395, 370, 160, 50);
        highscoresBtn.setBounds(395, 430, 160, 50);
        optionsBtn.setBounds(395, 490, 160, 50);
        backBtn.setBounds(395, 550, 160, 50);
        // Hinzufügen der Buttons und Labels auf den Frame
        add(label);
        add(playBtn);
        add(loadBtn);
        add(saveBtn);
        add(highscoresBtn);
        add(optionsBtn);
        add(backBtn);
        add(background);
        //Buttons werden dem Listener zugeordnet
        playBtn.addActionListener(this);
        loadBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        highscoresBtn.addActionListener(this);
        optionsBtn.addActionListener(this);
        backBtn.addActionListener(this);
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
            Gson gameJson = new GsonBuilder()
                    .registerTypeAdapter(gameWindow.getGame().getClass(), new GameSerializer())
                    .create();

            String inFile = "";
            try{
                inFile = Files.readString(Paths.get(System.getProperty("user.home") + File.separator + "LOST" + File.separator + "MacPan.json")); //get game data from user.home/LOST/MacPan.json
            } catch (IOException a){
                a.printStackTrace();
            }

            if(!inFile.equals("")){
                Game savedGame = gameJson.fromJson(inFile, Game.class);
                System.out.println(savedGame.getScore());
                System.out.println(savedGame.getLevelNr());
                for(int i=0;i<savedGame.getMaxRows(); i++){
                    for(int j=0;j<savedGame.getMaxColumns();j++){
                        System.out.print(savedGame.getMap()[j][i]);
                    }
                    System.out.println();
                }
                gameWindow.setGame(savedGame);
            }
            parentFrame.setContentPane(gameWindow);
            parentFrame.revalidate();

            gameWindow.spielFortsetzen();
            gameWindow.setFocusable(true);
            gameWindow.grabFocus();



        } else if (e.getSource() == saveBtn) {
            Gson gameJson = new GsonBuilder()
                    .registerTypeAdapter(gameWindow.getGame().getClass(), new GameSerializer())
                    .setPrettyPrinting()
                    .create();;
            String data = gameJson.toJson(gameWindow.getGame());

            if (Files.notExists(Path.of(System.getProperty("user.home")  + File.separator + "LOST"))) {
                try{
                Files.createDirectories(Path.of(System.getProperty("user.home")  + File.separator +  "LOST")); //save game data in folder user.home/LOST
                } catch (IOException a) {
                a.printStackTrace();}
            }

            try{
                FileWriter writer = new FileWriter(System.getProperty("user.home") + File.separator + "LOST" + File.separator + "MacPan.json");
                writer.write(data);
                writer.close();
            } catch (IOException a){
                a.printStackTrace();
            }

        } else if (e.getSource() == highscoresBtn) {
            HighscoreMenu highscoreMenu = new HighscoreMenu(parentFrame, this.parentFrame.getContentPane());
            parentFrame.setContentPane(highscoreMenu);
            parentFrame.revalidate();

        } else if (e.getSource() == optionsBtn) {
            OptionsMenu optionsMenu = new OptionsMenu(parentFrame, this.parentFrame.getContentPane());
            parentFrame.setContentPane(optionsMenu);
            parentFrame.revalidate();

        } else if (e.getSource() == backBtn) {
            MainMenu mainMenu = new MainMenu(parentFrame);
            parentFrame.setContentPane(mainMenu);
            parentFrame.revalidate();
        }
    }
}
