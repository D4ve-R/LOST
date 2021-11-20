/*
 * MacPan version 0.1
 * SWE WS 21/22
 * Authors:
 * Janosch Lentz
 * David Rechkemmer
 */

package lost.macpan.panel;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import lost.macpan.utils.ResourceHandler;

public class MainMenu extends JPanel implements ActionListener,ResourceHandler {
    //Buttons werden Erstellt
    private final JButton playBtn = new JButton("Spiel Starten");
    private final JButton loadBtn = new JButton("Spiel Laden");
    private final JButton highscoresBtn = new JButton("Highscores");
    private final JButton optionsBtn = new JButton("Optionen");
    private final JButton quitBtn = new JButton("Spiel Beenden");
    private JFrame parentFrame;
    private JPanel panelForButtons = new JPanel();
    private JPanel panelForImage = new JPanel();
    private JLabel label;
    private Image img;

    public MainMenu(JFrame frame){
        parentFrame = frame;
        setLayout(null);
        try{
            img = ImageIO.read(getFileResourcesAsStream("images/HauptmenüBildPlatzhalter.png"));
            img = img.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        }catch(Exception e){
            e.printStackTrace();
        }

        if(img != null){
            label = new JLabel(new ImageIcon(img));
        }
        label.setBounds(175,50,600,200);
        add(label);


        setLayout(null);
        playBtn.setBounds(395,300,160,50);
        loadBtn.setBounds(395,360,160,50);
        highscoresBtn.setBounds(395,420,160,50);
        optionsBtn.setBounds(395,480,160,50);
        quitBtn.setBounds(395,540,160,50);
        add(playBtn);
        add(loadBtn);
        add(highscoresBtn);
        add(optionsBtn);
        add(quitBtn);

        playBtn.addActionListener(this);
        loadBtn.addActionListener(this);
        highscoresBtn.addActionListener(this);
        optionsBtn.addActionListener(this);
        quitBtn.addActionListener(this);

        //setBackground(Color.white);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == playBtn){
            // start Game here
        } else if (e.getSource() == loadBtn) {
            // load game
        } else if (e.getSource() == highscoresBtn) {
            // open highscores
        } else if (e.getSource() == optionsBtn) {
            // open option menu
        } else if (e.getSource() == quitBtn) {
            // quit game
        }
    }
}