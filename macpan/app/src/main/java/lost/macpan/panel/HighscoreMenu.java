package lost.macpan.panel;

import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 *  Die Klasse HighscoreMenu zeigt die 10 besten Spieldurchläufe tabellarisch anzeigen
 * @author fatih
 */
public class HighscoreMenu extends JPanel implements ActionListener, ResourceHandler {
    // Zurück Button
    private final JButton backBtn = new JButton("Zurück");
    private JFrame parentFrame;
    private JLabel label;
    private Image img;
    private JLabel background;
    private Image backgroundImg;

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
        try {
            backgroundImg = ImageIO.read(getFileResourcesAsStream("images/panelImages/BackgroundImage.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (backgroundImg != null) {
            background = new JLabel(new ImageIcon(backgroundImg));
        }
        //Hier fehlt noch die Ausgabe der Highscores auf dem Bildschirm

        try {
            Scanner scan = new Scanner(new File("resources/highscores/Highscores.txt"));
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        setLayout(null);
        background.setBounds(0, 0, 950, 700);
        label.setBounds(175,50,600,200);
        backBtn.setBounds(30,550,160,50);
        add(label);
        add(backBtn);
        add(background);
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
