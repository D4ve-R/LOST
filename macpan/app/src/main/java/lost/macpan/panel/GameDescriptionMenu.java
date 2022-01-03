/**
 * MacPan version 0.1
 * SWE WS 21/22
 */

package lost.macpan.panel;

import lost.macpan.utils.MenuNavigationHandler;
import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Die Klasse GameDescriptionMenu liefert dem Spieler eine Spielbeschreibung !!Spielbeschreibung muss noch hinzugefügt werden!!
 * @author fatih
 */
public class GameDescriptionMenu extends JPanel implements ActionListener, ResourceHandler, MenuNavigationHandler {
    private final JButton backBtn = new JButton("Zur\u00fcck");
    private JFrame parentFrame;
    private JLabel label;
    private Image img;
    private Container before;
    private JLabel background;
    private Image backgroundImg;
    private Image descriptionImg;
    private JLabel descriptionLabel;

    public GameDescriptionMenu(JFrame frame, Container beforeMenu) {
        before = beforeMenu;
        parentFrame = frame;

        try {
            img = ImageIO.read(getFileResourcesAsStream("images/panelImages/Description.png"));
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
            descriptionImg = ImageIO.read(getFileResourcesAsStream("images/panelImages/Spielbeschreibung.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (backgroundImg != null) {
            descriptionLabel = new JLabel(new ImageIcon(descriptionImg));
        }
        setLayout(null);
        descriptionLabel.setBounds(220, 200, 514, 361);
        background.setBounds(0, 0, 950, 700);
        label.setBounds(-20, 10, 950, 200);
        backBtn.setBounds(30, 550, 160, 50);
        add(descriptionLabel);
        add(label);
        add(backBtn);
        add(background);
        backBtn.addActionListener(this);

        setKeyBindings(getActionMap(),getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            parentFrame.setContentPane(before);
            parentFrame.revalidate();
            before.requestFocusInWindow();
        }
    }
}
