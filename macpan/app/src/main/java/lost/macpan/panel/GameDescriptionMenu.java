package lost.macpan.panel;

import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameDescriptionMenu extends JPanel implements ActionListener, ResourceHandler {
    private final JButton backBtn = new JButton("Zurück");
    private JFrame parentFrame;
    private JLabel label;
    private Image img;
    private Container before;

    public GameDescriptionMenu(JFrame frame,Container beforeMenu) {
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
        setLayout(null);
        label.setBounds(175,50,600,200);
        backBtn.setBounds(30,550,160,50);
        add(label);
        add(backBtn);
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
