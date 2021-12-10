package lost.macpan.panel;

import lost.macpan.Main;
import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Writer;


/**
 * Die LooserMenu Klasse stellt ein Menü dar, wenn ein Spieler das Spiel verloren hat
 * Update durch Janosch & William
 *
 * @author fatih
 */
public class LooserMenu extends JPanel implements ActionListener, ResourceHandler {
    private JFrame parentFrame;
    private Container before;
    private JLabel label;
    private Image img;
    private JLabel background;
    private Image backgroundImg;
    private Timer timer;
    private JTextField nameInput;
    private JLabel nameLabel;
    private Image nameImage;
    private Image scoreImage;
    private JLabel scoreLabel;
    private int score;
    private JLabel scoreValue;
    private InputStream inputStream;
    private String highscores;
    private String[] parts;
    private boolean newScore;
    private String lastScoreWithName;

    public LooserMenu(JFrame frame, Container beforeMenu, int currentScore) {

        int delay = 5000;
        timer = new Timer(delay, this);
        parentFrame = frame;
        before = beforeMenu;
        score = currentScore;

        try {
            img = ImageIO.read(getFileResourcesAsStream("images/panelImages/Loose.png"));
            backgroundImg = ImageIO.read(getFileResourcesAsStream("images/panelImages/BackgroundImage.png"));
            nameImage = ImageIO.read(getFileResourcesAsStream("images/panelImages/nameLabel.png"));
            scoreImage = ImageIO.read(getFileResourcesAsStream("images/panelImages/scoreLabel.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (img != null) {
            label = new JLabel(new ImageIcon(img));
        }
        if (backgroundImg != null) {
            background = new JLabel(new ImageIcon(backgroundImg));
        }
        if (nameImage != null) {
            nameLabel = new JLabel(new ImageIcon(nameImage));
        }
        if (scoreImage != null) {
            scoreLabel = new JLabel(new ImageIcon(scoreImage));
        }

        nameInput = new JTextField();
        scoreValue = new JLabel("" + score);
        scoreValue.setFont(new Font(Font.MONOSPACED, Font.BOLD, 28));
        scoreValue.setForeground(Color.WHITE);

        setLayout(null);

        background.setBounds(0, 0, 950, 700);
        label.setBounds(-20, 10, 950, 200);
        nameLabel.setBounds(290, 250, 101, 26);
        scoreLabel.setBounds(290, 210, 101, 26);
        nameInput.setBounds(400, 249, 200, 20);
        scoreValue.setBounds(400, 210, 101, 26);
        add(label);
        add(scoreValue);
        add(nameLabel);
        add(scoreLabel);
        add(background);
        add(nameInput);
        timer.start();
        /*
        try {
            inputStream = getFileResourcesAsStream("highscores/Highscores.txt");
            highscores = convertStreamToString(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        parts = highscores.split("\n");
        lastScoreWithName = parts[parts.length-1];

        if (lastScoreWithName.equals("")) {
            newScore = true;
        } else newScore = false;
        if (getScoreVal(lastScoreWithName) < score) {
            newScore = true;
        } else newScore = false;
        if (parts.length < 10) {
            newScore = true;
        }else newScore = false;

        if (parts.length > 9) {
            try {
                Writer writer = new FileWriter("macpan/app/src/main/resources/highscores/Highscores.txt", false);
                BufferedWriter writer1 = new BufferedWriter(writer);
                writer1.write("");
                writer1.close();
                writer.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            try {
                Writer writer = new FileWriter("macpan/app/src/main/resources/highscores/Highscores.txt", true);
                for (int i = 0; i < parts.length-1; i++) {
                    writer.write(parts[i] + "\n");
                }
                writer.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
         */

    }
    /*
    try {
        Writer writer = new FileWriter("macpan/app/src/main/resources/highscores/Highscores.txt", true);
        if (newScore) {
            if (nameInput.getText().equals("")) {
                writer.write(score + ";" + "Spieler" + "\n");
            } else writer.write(score + ";" + nameInput.getText() + "\n");
        }else {
            writer.write(lastScoreWithName);
        }
        writer.close();
    } catch (Exception e1) {
        e1.printStackTrace();
    }
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            inputStream = getFileResourcesAsStream("highscores/Highscores.txt");
            highscores = convertStreamToString(inputStream);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        parts = highscores.split("\n"); //txt Inhalt als Array
        String[] all = new String[10];
        for (int i = 0; i < parts.length; i++) { //Alle einträge in ein Array mit 10 stellen überschreiben
            all[i] = parts[i];
        }
        if (parts.length == 10) { //Bereits 10 Einträge
            if (getScoreVal(parts[parts.length-1]) < score) {//neuer Highscore
                if (nameInput.getText().equals("")) { //Name eingegeben?
                    all[parts.length-1] = score+";"+"Anonym";
                } else all[parts.length-1] = score+";"+nameInput.getText();
                //hier ggf sortieren !!!!!
            }
        } else { //noch keine 10 Einträge
            if (nameInput.getText().equals("")) {
                all[parts.length] = score+";"+"Anonym";
            } else all[parts.length] = score+";"+nameInput.getText();
            //hier sortieren !!!!!!
        }
        //Txt überschreiben mit neuen Werten
        //inhalt löschen
        try {
            Writer writer = new FileWriter("macpan/app/src/main/resources/highscores/Highscores.txt", false);
            BufferedWriter writer1 = new BufferedWriter(writer);
            writer1.write("");
            writer1.close();
            writer.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //Werte von all in txt übernehmen
        try {
            Writer writer = new FileWriter("macpan/app/src/main/resources/highscores/Highscores.txt", true);
            for (int i = 0; i < all.length; i++) {
                if (all[i] != null) {
                    writer.write(all[i] + "\n");
                } else break;
            }
            writer.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        timer.stop();
        parentFrame.setContentPane(before);
        parentFrame.revalidate();
    }

    private int getScoreVal(String score) {
        if (score.equals("")) return 0;
        String num = score.substring(0, score.indexOf(';'));
        int numInt = Integer.parseInt(num);
        return numInt;
    }

}
