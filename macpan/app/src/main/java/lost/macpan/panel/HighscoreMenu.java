package lost.macpan.panel;

import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  Die Klasse HighscoreMenu zeigt die 10 besten Spieldurchläufe tabellarisch anzeigen
 * @author fatih
 */
public class HighscoreMenu extends JPanel implements ActionListener, ResourceHandler {
    // Zurück Button
    private final JButton backBtn = new JButton("Zuruck");
    private JFrame parentFrame;
    private JLabel label;
    private Image img;

    public HighscoreMenu(JFrame frame) {
        parentFrame = frame;
        try {
            img = ImageIO.read(getFileResourcesAsStream("images/panelImages/HighscoresPlatzhalter.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (img != null) {
            label = new JLabel(new ImageIcon(img));
        }


        //Hier fehlt noch die Ausgabe der Highscores auf dem Bildschirm


        setLayout(null);
        label.setBounds(175,50,600,200);
        backBtn.setBounds(30,550,160,50);
        add(label);
        add(backBtn);
        backBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            MainMenu mainMenu = new MainMenu(parentFrame);
            parentFrame.setContentPane(mainMenu);
            parentFrame.revalidate();
        }
    }
}
