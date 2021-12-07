package lost.macpan.panel;

import lost.macpan.App;
import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Die Klasse WinnerMenu zeigt dem Spieler nach einem Spieldurchlauf den erreichten Score und bietet dem Spieler die Möglichkeit seinen Namen einzugragen
 *
 * @author fatih
 */
public class WinnerMenu extends JPanel implements ActionListener, ResourceHandler {
    private App parentFrame;
    private JLabel label;
    private Image img;
    private JLabel background;
    private Image backgroundImg;
    private Timer timer;

    public WinnerMenu(App frame) {
        int delay = 5000;
        timer = new Timer(delay, this);
        parentFrame = frame;
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
        //!Funktionalität Namen einzutragen und Score auszugeben fehlt noch

        setLayout(null);
        background.setBounds(0, 0, 950, 700);
        label.setBounds(0, 50, 900, 200);
        add(label);
        add(background);

        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.stop();
        MainMenu mainMenu = new MainMenu(parentFrame);
        parentFrame.setContentPane(mainMenu);
        parentFrame.revalidate();
    }
}
