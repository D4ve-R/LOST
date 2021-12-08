/*
 * MacPan version 0.1
 * SWE WS 21/22
 * Authors:
 * Janosch Lentz
 * David Rechkemmer
 */

package lost.macpan.panel;

import lost.macpan.game.GameWindow;
import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Die Klasse MainMenu stellt das Haputmenü des Spiels auf dem JPanel dar.
 */
public class MainMenu extends JPanel implements ActionListener, ResourceHandler {
    // Erstellen der einzelnen Buttons
    private final JButton playBtn = new JButton("Spiel Starten");
    private final JButton loadBtn = new JButton("Spiel Laden");
    private final JButton highscoresBtn = new JButton("Highscores");
    private final JButton optionsBtn = new JButton("Optionen");
    private final JButton quitBtn = new JButton("Spiel Beenden");
    /*
        parentFrame = Frame auf dem alles abgebildet wird; mithilfe von label wird ein Bild über den Buttons gezeigt
     */
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

        // Positionierung der Buttons und Labels
        background.setBounds(0, 0, 960, 700);
        label.setBounds(175, 50, 600, 200);
        playBtn.setBounds(395, 300, 160, 50);
        loadBtn.setBounds(395, 360, 160, 50);
        highscoresBtn.setBounds(395, 420, 160, 50);
        optionsBtn.setBounds(395, 480, 160, 50);
        quitBtn.setBounds(395, 540, 160, 50);

        // Hinzufügen der Buttons und Labels auf den Frame
        add(label);
        add(playBtn);
        add(loadBtn);
        add(highscoresBtn);
        add(optionsBtn);
        add(quitBtn);
        add(background);
        //Buttons werden dem Listener zugeordnet
        playBtn.addActionListener(this);
        loadBtn.addActionListener(this);
        highscoresBtn.addActionListener(this);
        optionsBtn.addActionListener(this);
        quitBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playBtn) {
            GameWindow gameWindow = new GameWindow(parentFrame, this);
            parentFrame.setContentPane(gameWindow);
            parentFrame.revalidate();

        } else if (e.getSource() == loadBtn) {

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
}
