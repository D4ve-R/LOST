package lost.macpan.panel;

import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Die Klasse GameDescriptionMenu liefert dem Spieler eine Spielbeschreibung !!Spielbeschreibung muss noch hinzugefügt werden!!
 *
 * @author fatih
 */
public class GameDescriptionMenu extends JPanel implements ActionListener, ResourceHandler {
    private final JButton backBtn = new JButton("Zurück");
    private JFrame parentFrame;
    private JLabel label;
    private Image img;
    private Container before;
    private JLabel background;
    private Image backgroundImg;

    public GameDescriptionMenu(JFrame frame, Container beforeMenu) {
        before = beforeMenu;
        parentFrame = frame;

        try {
            img = ImageIO.read(getFileResourcesAsStream("images/panelImages/SpielbeschreibungPlatzhalter.png"));
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
        backBtn.setBounds(30, 550, 160, 50);
        add(label);
        add(backBtn);
        add(background);
        backBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            OptionsMenu optionsMenu = new OptionsMenu(parentFrame, before);
            parentFrame.setContentPane(optionsMenu);
            parentFrame.revalidate();
        }
    }
}
