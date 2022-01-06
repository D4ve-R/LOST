/**
 * MacPan version 0.1
 * SWE WS 21/22
 */

package lost.macpan.panel;

import lost.macpan.utils.CustomButton;
import lost.macpan.utils.MenuNavigationHandler;
import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Die Klasse KeysMenu liefert dem Spieler ein Menü, wo man die Tastaturbelegung einsehen kann
 * @author fatih
 */
public class KeysMenu extends JPanel implements ActionListener, ResourceHandler, MenuNavigationHandler {
    private final CustomButton backBtn = new CustomButton("Zur\u00fcck");
    private JFrame parentFrame;
    private JLabel topLabel;
    private JLabel subLabel;
    private Image img_1;
    private Image img_2;
    private Container before;
    private JLabel background;
    private Image backgroundImg;

    public KeysMenu(JFrame frame, Container beforeMenu) {
        before = beforeMenu;
        parentFrame = frame;
        try {
            img_1 = ImageIO.read(getFileResourcesAsStream("images/panelImages/Keys.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            img_2 = ImageIO.read(getFileResourcesAsStream("images/panelImages/description_1.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (img_1 != null) {
            topLabel = new JLabel(new ImageIcon(img_1));
        }
        if (img_2 != null) {
            subLabel = new JLabel(new ImageIcon(img_2));
        }
        try {
            backgroundImg = ImageIO.read(getFileResourcesAsStream("images/panelImages/BackgroundImage.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (backgroundImg != null) {
            background = new JLabel(new ImageIcon(backgroundImg));
        }
        setLayout(null);
        background.setBounds(0, 0, 950, 700);
        topLabel.setBounds(-20, 10, 950, 200);
        subLabel.setBounds(175, 150, 600, 400);
        backBtn.setBounds(30, 550, 180, 50);
        add(topLabel);
        add(backBtn);
        add(subLabel);
        add(background);
        backBtn.addActionListener(this);
        setKeyBindings(getActionMap(),getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            parentFrame.setContentPane(before);
            parentFrame.revalidate();
            before.requestFocusInWindow();
        }
    }
}
