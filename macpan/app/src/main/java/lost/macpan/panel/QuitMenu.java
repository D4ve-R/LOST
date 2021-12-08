/**
 * MacPan version 0.1
 * SWE WS 21/22
 */

package lost.macpan.panel;

import lost.macpan.App;
import lost.macpan.utils.CustomButton;
import lost.macpan.utils.MenuNavigationHandler;
import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;


/**
 * Die Klasse QuitMenu erstellt das Bestätigungsfenster, wenn der Benutzer das Spiel beenden möchte
 * @author Fatih
 */
public class QuitMenu extends JPanel implements ActionListener, ResourceHandler, MenuNavigationHandler {
    private final CustomButton yesBtn = new CustomButton("Ja");
    private final CustomButton noBtn = new CustomButton("Nein");

    private App parentFrame;
    private JLabel topLabel, subLabel;
    private Image img_1, img_2;
    private JLabel background;
    private Image backgroundImg;
    private Container before;

    public QuitMenu(App frame, Container beforeMenu) {
        parentFrame = frame;
        before = beforeMenu;
        try {
            img_1 = ImageIO.read(getFileResourcesAsStream("images/panelImages/Quit.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            img_2 = ImageIO.read(getFileResourcesAsStream("images/panelImages/Quit_2.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            backgroundImg = ImageIO.read(getFileResourcesAsStream("images/panelImages/BackgroundImage.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (backgroundImg != null) {
            background = new JLabel(new ImageIcon(backgroundImg));
        }
        if (img_1 != null) {
            topLabel = new JLabel(new ImageIcon(img_1));
        }
        if (img_2 != null) {
            subLabel = new JLabel(new ImageIcon(img_2));
        }
        setLayout(null);
        background.setBounds(0, 0, 950, 700);
        topLabel.setBounds(-20, 10, 950, 200);
        subLabel.setBounds(175, 250, 600, 200);
        yesBtn.setBounds(740, 550, 160, 50);
        noBtn.setBounds(30, 550, 160, 50);
        add(subLabel);
        add(topLabel);
        add(yesBtn);
        add(noBtn);
        add(background);
        yesBtn.addActionListener(this);
        noBtn.addActionListener(this);
        setKeyBindings(getActionMap(),getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == yesBtn) {
            parentFrame.dispatchEvent(new WindowEvent(parentFrame, WindowEvent.WINDOW_CLOSING));
            parentFrame.dispose();
        } else if (e.getSource() == noBtn) {
            parentFrame.resumeMusic();
            parentFrame.setContentPane(before);
            parentFrame.revalidate();
            before.requestFocusInWindow();
        }
    }
}
