package lost.macpan.panel;

import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Die Klasse HighscoreMenu zeigt die 10 besten Spieldurchläufe tabellarisch an.
 *
 * @author fatih
 */
public class HighscoreMenu extends JPanel implements ActionListener, ResourceHandler {
    private final JButton backBtn = new JButton("Zurück");
    private JFrame parentFrame;
    private JLabel label;
    private Image img;
    private JLabel background;
    private Image backgroundImg;
    private String highscores;
    private InputStream inputStream;
    private JLabel highscoreBackground;

    /**
     * Der Konstruktor stellt den Button sowie die Labels auf dem Frame dar.
     *
     * @param frame der Frame, auf dem alles abgebildet wird
     */
    public HighscoreMenu(JFrame frame) {
        parentFrame = frame;
        /*
            Laden der einzenlen Bilder
         */
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
        try {
            backgroundImg = ImageIO.read(getFileResourcesAsStream("images/panelImages/HighscoreBackground.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (backgroundImg != null) {
            highscoreBackground = new JLabel(new ImageIcon(backgroundImg));
        }
        /*
            Laden der Highscores.txt Datei
         */
        try {
            inputStream = getFileResourcesAsStream("highscores/Highscores.txt");
            highscores = convertStreamToString(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] parts = highscores.split("\n");
        int length = 0;
        if (parts.length > 10) {
            length = 10;
        } else length = parts.length;
        String[] temp;
        String out = "";
        for (int i = 0; i < length; i++) {
            temp = parts[i].split(";");
            out += i + 1 + "           " + temp[0] + "          " + temp[1] + "\n";
        }
        setLayout(null);
        String[] lastStringHopefully = out.split("\n");
        if (lastStringHopefully.length > 0) {
            JLabel hLb1 = new JLabel(lastStringHopefully[0]);
            hLb1.setBounds(290, 200, 500, 100);
            hLb1.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            hLb1.setForeground(Color.BLACK);
            add(hLb1);
        }
        if (lastStringHopefully.length > 1) {
            JLabel hLb2 = new JLabel(lastStringHopefully[1]);
            hLb2.setBounds(290, 230, 500, 100);
            hLb2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            hLb2.setForeground(Color.BLACK);
            add(hLb2);
        }
        if (lastStringHopefully.length > 2) {
            JLabel hLb3 = new JLabel(lastStringHopefully[2]);
            hLb3.setBounds(290, 260, 500, 100);
            hLb3.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            hLb3.setForeground(Color.BLACK);
            add(hLb3);
        }
        if (lastStringHopefully.length > 3) {
            JLabel hLb4 = new JLabel(lastStringHopefully[3]);
            hLb4.setBounds(290, 290, 500, 100);
            hLb4.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            hLb4.setForeground(Color.BLACK);
            add(hLb4);
        }
        if (lastStringHopefully.length > 4) {
            JLabel hLb5 = new JLabel(lastStringHopefully[4]);
            hLb5.setBounds(290, 320, 500, 100);
            hLb5.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            hLb5.setForeground(Color.BLACK);
            add(hLb5);
        }
        if (lastStringHopefully.length > 5) {
            JLabel hLb6 = new JLabel(lastStringHopefully[5]);
            hLb6.setBounds(290, 350, 500, 100);
            hLb6.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            hLb6.setForeground(Color.BLACK);
            add(hLb6);
        }
        if (lastStringHopefully.length > 6) {
            JLabel hLb7 = new JLabel(lastStringHopefully[6]);
            hLb7.setBounds(290, 380, 500, 100);
            hLb7.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            hLb7.setForeground(Color.BLACK);
            add(hLb7);
        }
        if (lastStringHopefully.length > 7) {
            JLabel hLb8 = new JLabel(lastStringHopefully[7]);
            hLb8.setBounds(290, 410, 500, 100);
            hLb8.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            hLb8.setForeground(Color.BLACK);
            add(hLb8);
        }
        if (lastStringHopefully.length > 8) {
            JLabel hLb9 = new JLabel(lastStringHopefully[8]);
            hLb9.setBounds(290, 440, 500, 100);
            hLb9.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            hLb9.setForeground(Color.BLACK);
            add(hLb9);
        }
        if (lastStringHopefully.length > 9) {
            JLabel hLb10 = new JLabel(lastStringHopefully[9]);
            hLb10.setBounds(280, 470, 500, 100);
            hLb10.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            hLb10.setForeground(Color.BLACK);
            add(hLb10);
        }
        highscoreBackground.setBounds(220, 210, 514, 361);
        add(highscoreBackground);
        background.setBounds(0, 0, 950, 700);
        label.setBounds(175, 50, 600, 200);
        backBtn.setBounds(30, 550, 160, 50);
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
    /*
        http://www.java2s.com/Code/Android/File/getResourceAsStreamLoadstheresourcefromclasspath.htm
     */

    /**
     * Wandelt einen Stram zu einem String
     *
     * @param is Inputstream
     * @return String der aus Input entstanden ist
     * @throws IOException
     */
    private static String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }
}
