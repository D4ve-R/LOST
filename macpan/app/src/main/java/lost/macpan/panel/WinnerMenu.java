package lost.macpan.panel;

import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinnerMenu extends JPanel implements ActionListener, ResourceHandler {
    private JFrame parentFrame;
    private JLabel label;
    private Image img;
    public WinnerMenu(JFrame frame) {
        parentFrame = frame;
        try {
            img = ImageIO.read(getFileResourcesAsStream("images/panelImages/GewonnenPlatzhalter.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (img != null) {
            label = new JLabel(new ImageIcon(img));
        }

        //!Funktionalität Namen einzutragen und Score auszugeben fehlt noch

        setLayout(null);
        label.setBounds(175,50,600,200);
        add(label);
        setBackground(Color.DARK_GRAY);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
