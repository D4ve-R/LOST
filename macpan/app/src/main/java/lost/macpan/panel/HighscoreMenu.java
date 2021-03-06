/**
 * MacPan version 0.1
 * SWE WS 21/22
 */

package lost.macpan.panel;

import lost.macpan.utils.CustomButton;
import lost.macpan.utils.HighscoreHandler;
import lost.macpan.utils.MenuNavigationHandler;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Die Klasse HighscoreMenu zeigt die 10 besten Spieldurchläufe tabellarisch an.
 * @author fatih
 */
public class HighscoreMenu extends JPanel implements ActionListener, HighscoreHandler, MenuNavigationHandler {
    private final CustomButton backBtn = new CustomButton("Zur\u00fcck");
    private JFrame parentFrame;
    private JLabel label;
    private Image img;
    private JLabel background;
    private Image backgroundImg;
    private Image HighscoresBackgroundImg;
    private JLabel highscoreBackground;
    private Container before;

    /**
     * Der Konstruktor stellt den Button sowie die Labels auf dem Frame dar.
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

        String[] highScores = getHighscoresAsArray();

        for (int i = 0; i < highScores.length; i++) {
            String[] temp = highScores[i].split(";");
            //Fancy String Formatierung, % ArgumentNr $ ("-" for left align) width Type (s String)
            highScores[i] = String.format("%1$2s" + "       " + "%2$5s" + "         " +"%3$-12s", (i + 1),temp[0] ,temp[1]);
        }

        setLayout(null);
        for(int i = 0;i < highScores.length; i++){
            JLabel tempLabel = new JLabel(highScores[i]);
            tempLabel.setBounds(290, 190 + i * 30, 500, 100);
            tempLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            tempLabel.setForeground(Color.BLACK);
            add(tempLabel);
        }

        highscoreBackground.setBounds(220, 200, 514, 361);
        add(highscoreBackground);
        background.setBounds(0, 0, 950, 700);
        label.setBounds(-20, 10, 950, 200);
        backBtn.setBounds(30, 550, 180, 50);
        add(label);
        add(backBtn);
        add(background);
        backBtn.addActionListener(this);

        setKeyBindings(getActionMap(),getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            this.revalidate();
            parentFrame.setContentPane(before);
            parentFrame.revalidate();
            before.requestFocusInWindow();
        }
    }
}
