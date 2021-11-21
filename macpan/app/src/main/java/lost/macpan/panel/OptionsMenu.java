package lost.macpan.panel;

import lost.macpan.utils.ResourceHandler;
import org.checkerframework.checker.units.qual.K;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.awt.*;

public class OptionsMenu extends JPanel implements ActionListener, ResourceHandler {
    private final JButton keyBtn = new JButton("Tastenbelegung");
    private final JButton descBtn = new JButton("Spielbeschreibung");
    private final JButton backBtn = new JButton("Zurück");
    private JLabel label;
    private Image img;
    private JFrame parentFrame;
    private Container before;

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
        setLayout(null);
        label.setBounds(175, 50, 600, 200);
        keyBtn.setBounds(395, 300, 160, 50);
        descBtn.setBounds(395, 360, 160, 50);
        backBtn.setBounds(30, 550, 160, 50);
        add(backBtn);
        add(label);
        add(keyBtn);
        add(descBtn);
        keyBtn.addActionListener(this);
        descBtn.addActionListener(this);
        backBtn.addActionListener(this);
        setBackground(Color.DARK_GRAY);
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
            KeysMenu keysMenu = new KeysMenu(parentFrame,before);
            parentFrame.setContentPane(keysMenu);
            parentFrame.revalidate();

        } else if (e.getSource() == descBtn) {
            GameDescriptionMenu gameDescriptionMenu = new GameDescriptionMenu(parentFrame,before);
            parentFrame.setContentPane(gameDescriptionMenu);
            parentFrame.revalidate();
        }
    }
}