package lost.macpan.panel;

import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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

    /**
     * Der Konstruktor MainMenu platziert die Bilder und Buttons, welche zum Pausemenue gehören auf dem Frame
     *
     * @param frame
     */
    public PauseMenu(JFrame frame) {
        parentFrame = frame;
        try {
            img = ImageIO.read(getFileResourcesAsStream("images/panelImages/PausemenuePlatzhalter.png"));
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
        background.setBounds(0, 0, 950, 700);
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

        } else if (e.getSource() == loadBtn) {

        } else if (e.getSource() == saveBtn) {

        } else if (e.getSource() == highscoresBtn) {

        } else if (e.getSource() == optionsBtn) {

        } else if (e.getSource() == backBtn) {
            MainMenu mainMenu = new MainMenu(parentFrame);
            parentFrame.setContentPane(mainMenu);
            parentFrame.revalidate();
        }
    }
}
