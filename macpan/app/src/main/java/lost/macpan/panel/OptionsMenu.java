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
 * Die Klasse OptionMenu stellt ein Menü dar, wo man das Tastenbelegungsfenster oder das Spielbeschreibungsfenster öffnen kann
 *
 * @author fatih
 */
public class OptionsMenu extends JPanel implements ActionListener, ResourceHandler, MenuNavigationHandler {
    private final CustomButton keyBtn = new CustomButton("Tastenbelegung");
    private final CustomButton descBtn = new CustomButton("Spielbeschreibung");
    private final CustomButton backBtn = new CustomButton("Zur\u00fcck");
    private JLabel label;
    private Image img;
    private JFrame parentFrame;
    private Container before;
    private JLabel background;
    private Image backgroundImg;

    public OptionsMenu(JFrame frame, Container beforeMenu) {
        parentFrame = frame;
        before = beforeMenu;
        try {
            img = ImageIO.read(getFileResourcesAsStream("images/panelImages/Options.png"));
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
        setLayout(null);
        background.setBounds(0, 0, 950, 700);
        label.setBounds(155, 50, 600, 200);
        keyBtn.setBounds(300, 300, 340, 50);
        descBtn.setBounds(300, 360, 340, 50);
        backBtn.setBounds(30, 550, 180, 50);
        add(backBtn);
        add(label);
        add(keyBtn);
        add(descBtn);
        add(background);
        keyBtn.addActionListener(this);
        descBtn.addActionListener(this);
        backBtn.addActionListener(this);

        setKeyBindings(getActionMap(),getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            if (before instanceof MainMenu) {
                parentFrame.setContentPane(before);
                parentFrame.revalidate();
                before.requestFocusInWindow();
            } else if (before instanceof PauseMenu) {
                parentFrame.setContentPane(before);
                parentFrame.revalidate();
                before.requestFocusInWindow();
            }
        } else if (e.getSource() == keyBtn) {
            KeysMenu keysMenu = new KeysMenu(parentFrame, this.parentFrame.getContentPane());
            parentFrame.setContentPane(keysMenu);
            parentFrame.revalidate();
            keysMenu.requestFocusInWindow();

        } else if (e.getSource() == descBtn) {
            GameDescriptionMenu gameDescriptionMenu = new GameDescriptionMenu(parentFrame, this.parentFrame.getContentPane());
            parentFrame.setContentPane(gameDescriptionMenu);
            parentFrame.revalidate();
            gameDescriptionMenu.requestFocusInWindow();
        }
    }
}
