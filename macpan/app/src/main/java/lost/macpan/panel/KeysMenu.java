package lost.macpan.panel;

import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KeysMenu extends JPanel implements ActionListener, ResourceHandler {
    private final JButton backBtn = new JButton("Zurück");
    private JFrame parentFrame;
    private JLabel topLabel;
    private JLabel subLabel;
    private Image img_1;
    private Image img_2;
    private Container before;

    public KeysMenu(JFrame frame,Container beforeMenu) {
        before = beforeMenu;
        parentFrame = frame;
        try {
            img_1 = ImageIO.read(getFileResourcesAsStream("images/panelImages/TastenbelegungPlatzhalter.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            img_2 = ImageIO.read(getFileResourcesAsStream("images/panelImages/Tastenbelegung_1.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (img_1 != null) {
            topLabel = new JLabel(new ImageIcon(img_1));
        }
        if (img_2 != null) {
            subLabel = new JLabel(new ImageIcon(img_2));
        }
        setLayout(null);
        topLabel.setBounds(175,50,600,200);
        subLabel.setBounds(175,250,600,400);
        backBtn.setBounds(30,550,160,50);
        add(topLabel);
        add(backBtn);
        add(subLabel);
        backBtn.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            OptionsMenu optionsMenu = new OptionsMenu(parentFrame,before);
            parentFrame.setContentPane(optionsMenu);
            parentFrame.revalidate();
        }
    }
}
