package lost.macpan.panel;

import lost.macpan.utils.ResourceHandler;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Die Klasse OptionMenu stellt ein Menü dar, wo man das Tastenbelegungsfenster oder das Spielbeschreibungsfenster öffnen kann
 *
 * @author fatih
 */
public class OptionsMenu extends JPanel implements ActionListener, ResourceHandler {
    private final JButton keyBtn = new JButton("Tastenbelegung");
    private final JButton descBtn = new JButton("Spielbeschreibung");
    private final JButton backBtn = new JButton("Zurück");
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
            img = ImageIO.read(getFileResourcesAsStream("images/panelImages/OptionenPlatzhalter.png"));
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
        label.setBounds(175, 50, 600, 200);
        keyBtn.setBounds(395, 300, 160, 50);
        descBtn.setBounds(395, 360, 160, 50);
        backBtn.setBounds(30, 550, 160, 50);
        add(backBtn);
        add(label);
        add(keyBtn);
        add(descBtn);
        add(background);
        keyBtn.addActionListener(this);
        descBtn.addActionListener(this);
        backBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            if (before instanceof MainMenu) {
                MainMenu mainMenu = new MainMenu(parentFrame);
                parentFrame.setContentPane(mainMenu);
                parentFrame.revalidate();
            } else if (before instanceof PauseMenu) {
                //Spiel weiter Spielen
            }
        } else if (e.getSource() == keyBtn) {
            KeysMenu keysMenu = new KeysMenu(parentFrame, before);
            parentFrame.setContentPane(keysMenu);
            parentFrame.revalidate();

        } else if (e.getSource() == descBtn) {
            GameDescriptionMenu gameDescriptionMenu = new GameDescriptionMenu(parentFrame, before);
            parentFrame.setContentPane(gameDescriptionMenu);
            parentFrame.revalidate();
        }
    }
}
