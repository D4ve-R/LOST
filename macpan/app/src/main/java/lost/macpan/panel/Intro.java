/**
 * MacPan version 0.1
 * SWE WS 21/22
 * @authors dave & janosch
 */

package lost.macpan.panel;

import lost.macpan.utils.ResourceHandler;

import java.awt.Color;
import java.awt.GridBagLayout;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Image;

/**
 * Intro Class to display for 4 seconds when the app starts
 */
public class Intro extends JPanel implements ResourceHandler {
    private Image img;
    private JLabel label;

    /**
     * Constructor method for Intro
     * loads the image and adds it to label
     */
    public Intro() {
        setLayout(new GridBagLayout());
        try {
            img = ImageIO.read(getFileResourcesAsStream("images/LoST_splashscreen.png"));
            img = img.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (img != null) {
            label = new JLabel(new ImageIcon(img));
        }
        add(label);
        setBackground(Color.DARK_GRAY);
    }
}
