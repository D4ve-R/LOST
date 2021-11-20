/*
 * MacPan version 0.1
 * SWE WS 21/22
 * Authors:
 * Janosch Lentz
 * David Rechkemmer
 */

package lost.macpan.panel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import lost.macpan.utils.ResourceHandler;

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
    private JLabel label;
    private Image img;

    /**
     * Der Konstruktor MainMenu platziert die Bilder und Buttons, welche zum Hauptmenue gehören auf dem Frame
     *
     * @param frame
     */
    public MainMenu(JFrame frame) {
        parentFrame = frame;

        try {
            img = ImageIO.read(getFileResourcesAsStream("images/panelImages/HauptmenuePlatzhalter.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (img != null) {
            label = new JLabel(new ImageIcon(img));
        }

        setLayout(null);
        // Positionierung der Buttons und Labels
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

        } else if (e.getSource() == loadBtn) {

        } else if (e.getSource() == highscoresBtn) {
            HighscoreMenu highscoreMenu = new HighscoreMenu(parentFrame);
            parentFrame.setContentPane(highscoreMenu);
            parentFrame.revalidate();

        } else if (e.getSource() == optionsBtn) {
            OptionsMenu optionsMenu = new OptionsMenu(parentFrame,this.parentFrame.getContentPane());
            parentFrame.setContentPane(optionsMenu);
            parentFrame.revalidate();

        } else if (e.getSource() == quitBtn) {
            QuitMenu quitMenu = new QuitMenu(parentFrame);
            parentFrame.setContentPane(quitMenu);
            parentFrame.revalidate();
        }
    }
}