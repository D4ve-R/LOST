package lost.macpan.panel;

import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public WinnerMenu(JFrame frame, Container beforeWindow) {
        int delay = 5000;
        timer = new Timer(delay, this);
        before = beforeWindow;
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
        background.setBounds(0, 0, 960, 700);
        label.setBounds(-20, 50, 950, 200);
        add(label);
        add(background);

        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.stop();

        parentFrame.setContentPane(before);
        parentFrame.revalidate();
    }
}
