package lost.macpan.panel;

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
import java.io.FileWriter;
import java.io.Writer;

/**
 * Die Klasse WinnerMenu zeigt dem Spieler nach einem Spieldurchlauf den erreichten Score und bietet dem Spieler die Möglichkeit seinen Namen einzugragen
 * Update durch Janosch & William
 *
 * @author fatih
 */
public class WinnerMenu extends JPanel implements ActionListener, ResourceHandler {
    private JFrame parentFrame;
    private JLabel label;
    private Image img;
    private JLabel background;
    private Image backgroundImg;
    private Timer timer;
    private Container before;
    private JTextField nameInput;
    private JLabel nameLabel;
    private Image nameImage;
    private Image scoreImage;
    private JLabel scoreLabel;
    private int score;
    private JLabel scoreValue;

    public WinnerMenu(JFrame frame, Container beforeMenu, int currentScore) {
        int delay = 5000;
        timer = new Timer(delay, this);
        before = beforeMenu;
        parentFrame = frame;
        score = currentScore;
        try {
            img = ImageIO.read(getFileResourcesAsStream("images/panelImages/Win.png"));
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
            nameImage = ImageIO.read(getFileResourcesAsStream("images/panelImages/nameLabel.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (nameImage != null) {
            nameLabel = new JLabel(new ImageIcon(nameImage));
        }
        try {
            scoreImage = ImageIO.read(getFileResourcesAsStream("images/panelImages/scoreLabel.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (scoreImage != null) {
            scoreLabel = new JLabel(new ImageIcon(scoreImage));
        }
        nameInput = new JTextField();
        scoreValue = new JLabel(""+score);
        scoreValue.setFont(new Font(Font.MONOSPACED,Font.BOLD,28));
        scoreValue.setForeground(Color.WHITE);


        setLayout(null);
        background.setBounds(0, 0, 950, 700);
        label.setBounds(175, 50, 600, 200);
        nameLabel.setBounds(290,250,101,26);
        scoreLabel.setBounds(290,210,101,26);
        nameInput.setBounds(400, 249,200,20);
        scoreValue.setBounds(400,210,101,26);
        add(label);
        add(scoreValue);
        add(nameLabel);
        add(scoreLabel);
        add(background);
        add(nameInput);

        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Writer writer = new FileWriter("macpan/app/src/main/resources/highscores/Highscores.txt",true);
            writer.write(score+";"+nameInput.getText()+"\n");
            writer.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        timer.stop();
        parentFrame.setContentPane(before);
        parentFrame.revalidate();
    }
}
