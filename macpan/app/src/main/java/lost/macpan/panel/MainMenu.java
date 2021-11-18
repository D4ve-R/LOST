/*
 * MacPan version 0.1
 * SWE WS 21/22
 * Authors:
 * Janosch Lentz
 * David Rechkemmer
 */

package lost.macpan.panel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu extends JPanel implements ActionListener {
    private final JButton playBtn = new JButton("Start");
    private JFrame parentFrame;

    public MainMenu(JFrame frame){
        parentFrame = frame;
        playBtn.addActionListener(this);
        add(playBtn);
        setBackground(Color.green);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == playBtn){
            // start Game here
        }
    }
}