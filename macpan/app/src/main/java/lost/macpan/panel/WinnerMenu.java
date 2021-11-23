package lost.macpan.panel;

import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Die Klasse WinnerMenu zeigt dem Spieler nach einem Spieldurchlauf den erreichten Score und bietet dem Spieler die Möglichkeit seinen Namen einzugragen
 *
 * @author fatih
 */
public class WinnerMenu extends JPanel implements ActionListener, ResourceHandler {
    private JFrame parentFrame;
    private JLabel label;
    private Image img;
    private JLabel background;
    private Image backgroundImg;

    public WinnerMenu(JFrame frame) {
        parentFrame = frame;
        try {
            img = ImageIO.read(getFileResourcesAsStream("images/panelImages/GewonnenPlatzhalter.png"));
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
        label.setBounds(175, 50, 600, 200);
        add(label);
        add(background);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
