package lost.macpan.panel;

import lost.macpan.Main;
import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Die LooserMenu Klasse stellt ein Menü dar, wenn ein Spieler das Spiel verloren hat
 *
 * @author fatih
 */
public class LooserMenu extends JPanel implements ActionListener, ResourceHandler {
    private JFrame parentFrame;
    private JLabel label;
    private Image img;
    private JLabel background;
    private Image backgroundImg;
    private Timer timer;

    public LooserMenu(JFrame frame) {
        int delay = 5000;
        timer = new Timer(delay, this);
        parentFrame = frame;
        try {
            img = ImageIO.read(getFileResourcesAsStream("images/panelImages/Loose.png"));
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
        label.setBounds(-20, 10, 950, 200);
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
