package lost.macpan.panel;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Intro extends JPanel {
    private Image img;
    private JLabel label;
    public Intro(){
        try{
            img = ImageIO.read(getFileResourcesAsStream("images/LoST_Gruen.png"));
            img = img.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        }catch(Exception e){
            e.printStackTrace();
        }

        if(img != null){
            label = new JLabel(new ImageIcon(img));
        }
        add(label);
    }
}
