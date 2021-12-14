package lost.macpan.panel;

import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Die Klasse HighscoreMenu zeigt die 10 besten Spieldurchläufe tabellarisch an.
 *
 * @author fatih
 */
public class HighscoreMenu extends JPanel implements ActionListener, ResourceHandler {
    private final JButton backBtn = new JButton("Zur\u00fcck");
    private JFrame parentFrame;
    private JLabel label;
    private Image img;
    private JLabel background;
    private Image backgroundImg;
    private Image HighscoresBackgroundImg;
    private String highscores;
    private InputStream inputStream;
    private JLabel highscoreBackground;
    private Container before;

    /**
     * Der Konstruktor stellt den Button sowie die Labels auf dem Frame dar.
     *
     * @param frame der Frame, auf dem alles abgebildet wird
     */
    public HighscoreMenu(JFrame frame, Container beforeMenu) {
        parentFrame = frame;
        before = beforeMenu;
        try {
            img = ImageIO.read(getFileResourcesAsStream("images/panelImages/Highscore.png"));
            backgroundImg = ImageIO.read(getFileResourcesAsStream("images/panelImages/BackgroundImage.png"));
            HighscoresBackgroundImg = ImageIO.read(getFileResourcesAsStream("images/panelImages/HighscoreBackground.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (img != null) {
            label = new JLabel(new ImageIcon(img));
        }
        if (backgroundImg != null) {
            background = new JLabel(new ImageIcon(backgroundImg));
        }
        if (HighscoresBackgroundImg != null) {
            highscoreBackground = new JLabel(new ImageIcon(HighscoresBackgroundImg));
        }

        try {
            highscores = readStringFromFile("Highscores.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] parts = highscores.split("\n");

        String[] temp;
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            temp = parts[i].split(";");
            out.append(i + 1 + "           " + temp[0] + "          " + temp[1] + "\n");
        }

        setLayout(null);
        String[] lastStringHopefully = out.toString().split("\n");
        if (lastStringHopefully.length > 0) {
            JLabel hLb1 = new JLabel(lastStringHopefully[0]);
            hLb1.setBounds(290, 190, 500, 100);
            hLb1.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            hLb1.setForeground(Color.BLACK);
            add(hLb1);
        }
        if (lastStringHopefully.length > 1) {
            JLabel hLb2 = new JLabel(lastStringHopefully[1]);
            hLb2.setBounds(290, 220, 500, 100);
            hLb2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            hLb2.setForeground(Color.BLACK);
            add(hLb2);
        }
        if (lastStringHopefully.length > 2) {
            JLabel hLb3 = new JLabel(lastStringHopefully[2]);
            hLb3.setBounds(290, 250, 500, 100);
            hLb3.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            hLb3.setForeground(Color.BLACK);
            add(hLb3);
        }
        if (lastStringHopefully.length >= 3) {
            JLabel hLb4 = new JLabel(lastStringHopefully[3]);
            hLb4.setBounds(290, 280, 500, 100);
            hLb4.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            hLb4.setForeground(Color.BLACK);
            add(hLb4);
        }
        if (lastStringHopefully.length > 4) {
            JLabel hLb5 = new JLabel(lastStringHopefully[4]);
            hLb5.setBounds(290, 310, 500, 100);
            hLb5.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            hLb5.setForeground(Color.BLACK);
            add(hLb5);
        }
        if (lastStringHopefully.length > 5) {
            JLabel hLb6 = new JLabel(lastStringHopefully[5]);
            hLb6.setBounds(290, 340, 500, 100);
            hLb6.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            hLb6.setForeground(Color.BLACK);
            add(hLb6);
        }
        if (lastStringHopefully.length > 6) {
            JLabel hLb7 = new JLabel(lastStringHopefully[6]);
            hLb7.setBounds(290, 370, 500, 100);
            hLb7.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            hLb7.setForeground(Color.BLACK);
            add(hLb7);
        }
        if (lastStringHopefully.length > 7) {
            JLabel hLb8 = new JLabel(lastStringHopefully[7]);
            hLb8.setBounds(290, 400, 500, 100);
            hLb8.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            hLb8.setForeground(Color.BLACK);
            add(hLb8);
        }
        if (lastStringHopefully.length > 8) {
            JLabel hLb9 = new JLabel(lastStringHopefully[8]);
            hLb9.setBounds(290, 430, 500, 100);
            hLb9.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            hLb9.setForeground(Color.BLACK);
            add(hLb9);
        }
        if (lastStringHopefully.length > 9) {
            JLabel hLb10 = new JLabel(lastStringHopefully[9]);
            hLb10.setBounds(280, 460, 500, 100);
            hLb10.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            hLb10.setForeground(Color.BLACK);
            add(hLb10);
        }
        highscoreBackground.setBounds(220, 200, 514, 361);
        add(highscoreBackground);
        background.setBounds(0, 0, 950, 700);
        label.setBounds(-20, 10, 950, 200);
        backBtn.setBounds(30, 550, 160, 50);
        add(label);
        add(backBtn);
        add(background);
        backBtn.addActionListener(this);
        System.out.println(highscores);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            this.revalidate();
            parentFrame.setContentPane(before);
            parentFrame.revalidate();
        }
    }
}
