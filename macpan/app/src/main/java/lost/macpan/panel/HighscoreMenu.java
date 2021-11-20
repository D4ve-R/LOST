package lost.macpan.panel;

import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HighscoreMenu extends JPanel implements ActionListener, ResourceHandler {
    private final JButton backBtn = new JButton("Zuruck");
    private JFrame parentFrame;
    private JLabel label;
    private Image img;

    public HighscoreMenu(JFrame frame) {
        parentFrame = frame;
        try {
            img = ImageIO.read(getFileResourcesAsStream("images/panelImages/PausemenuePlatzhalter.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (img != null) {
            label = new JLabel(new ImageIcon(img));
        }
        setLayout(null);
        backBtn.setBounds(30,550,160,50);
        add(backBtn);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            MainMenu mainMenu = new MainMenu(parentFrame);
            parentFrame.setContentPane(mainMenu);
            parentFrame.revalidate();
        }
    }
}
